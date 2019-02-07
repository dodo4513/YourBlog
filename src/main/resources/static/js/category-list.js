
$(() => {
  const URI = {
    SAVE: '/categories',
    GET: '/categories'
  };

  // const data = [
  //   {
  //     text: '아빠',
  //     children: [
  //       {
  //         text: '자식',
  //         children: []
  //       }
  //     ]
  //   }, {
  //     text: '친구',
  //     children: []
  //   }
  // ];

  const data = [
    {
      text: 'Root',
      children: []
    }
  ];

  const MESSAGE = {
    SAVE_SUCCESS: '저장되었습니다.',
    CATEGORY_LIMIT: '4단계까지만 추가가능합니다.'
  };

  function getTemplate(isPublic) {
    return {
      customInternalNode:
          `<div class="tui-tree-content-wrapper tui-tree-root-btn">
            <button type="button" class="fa fa-laptop tui-tree-toggle-btn tui-js-tree-toggle-btn">
              {{stateLabel}}
            </button>
            <span class="tui-tree-text tui-js-tree-text ${isPublic ? '' : 'cancel-line'}">{{text}}</span>
          <ul class="tui-tree-subtree tui-js-tree-subtree">{{{children}}}</ul></div>`,
      customLeafNode: `<div class="tui-tree-content-wrapper tui-tree-root-btn">
            <span class="tui-tree-text tui-js-tree-text ${isPublic ? '' : 'cancel-line'}">{{text}}</span>
          </div>`
    };
  }

  // function getTemplate(isPublic) {
  //   return {
  //     customInternalNode:
  //
  //
  //     `<div class="tui-tree-content-wrapper tui-tree-root-btn">
  //               <button type="button" class="fa fa-laptop tui-tree-toggle-btn tui-js-tree-toggle-btn">
  //                 {{stateLabel}}
  //               </button>
  //               <span class="tui-tree-text tui-js-tree-text ${isPublic ? '' : 'cancel-line'}">{{text}}</span>
  //             <ul class="tui-tree-subtree tui-js-tree-subtree">{{{children}}}</ul></div>`,
  //
  //
  //     //   <span class="tui-tree-text tui-js-tree-text ${isPublic ? '' : 'cancel-line'}">{{text}}</span>
  //     // </div>
  //     // <ul class="tui-tree-subtree tui-js-tree-subtree">{{{children}}}</ul>`,
  //     customLeafNode:
  //     // `<span class="{{textClass}}">{{text}}</span>`
  //         `<div class="tui-tree-content-wrapper tui-tree-root-btn">
  //           <span class="tui-tree-text tui-js-tree-text ${isPublic ? '' : 'cancel-line'}">{{text}}</span>
  //         </div>`
  //   };
  // }


  blog.category = {
    tree: null,
    newTreeIndex: 1,
    isNotPublicNodes: [],

    init() {
      this.addEvent();
      this.initTree();
      this.fetchCategories();
    },

    fetchCategories() {
      blog.common.ajaxForPromise({
        type: 'get',
        url: URI.GET
      }).then(resp => {
        const a = resp.categoryResponses
          .map(response => this.getEntityToJSON(response));
        console.log(JSON.stringify(a));
        this.tree.resetAllData(a);
      });
    },

    initTree() {
      const that = this;
      this.tree = new tui.Tree('tree', {
        // data: [],
        data,
        nodeDefaultState: 'opened',
        template: {
          internalNode: getTemplate(true).customInternalNode,
          leafNode: getTemplate(true).customLeafNode
        },
        renderTemplate(tmpl, props) {
          const id = props.id;
          const isPublic = that.isPublic(id);
          const depth = this.getDepth(id);
          const isFirstDepth = (this.getDepth(id) === 1);
          const isLeaf = this.isLeaf(id);

          if (isFirstDepth) { // customizing node template
            if (isLeaf) {
              tmpl = getTemplate(isPublic).customLeafNode;
            } else {
              tmpl = getTemplate(isPublic).customInternalNode;
            }
          }

          props.indent = 10 * depth; // customizing indent

          return Mustache.render(tmpl, props);
        },
        usageStatistics: false
      });

      this.tree
        .enableFeature('Selectable')
        .enableFeature('Draggable', {
          isSortable: true,
          lineBoundary: {
            top: 10,
            bottom: 10
          }
        })
        .enableFeature('Editable', {
          editableClassName: this.tree.classNames.textClass,
          dataKey: 'text'
        });
    },

    addEvent() {
      const $body = $('body');
      $body.on('click', '.action', $.proxy(this.onClickAction, this));
    },

    saveTree() {
      const categories = this.getCategoryToJSON(this.tree.getRootNodeId());

      blog.common.ajaxForPromise({
        type: 'post',
        url: URI.SAVE,
        data: JSON.stringify(categories)
      }).then(() => {
        alert(MESSAGE.SAVE_SUCCESS);
      });
    },

    getCategoryToJSON(parentId) {
      const categories = [];
      const tree = this.tree;
      const childIds = tree.getChildIds(parentId);

      for (const childId of childIds) {
        const category = {};
        category.title = tree.getNodeData(childId).text;
        category.displayOrder = categories.length + 1;
        category.children = this.getCategoryToJSON(childId);
        category.isPublic = !$(`#tui-tree-node-${childId.split('-')[3]} > .tui-tree-content-wrapper > .tui-tree-text`).hasClass('cancel-line');
        categories.push(category);
      }

      return categories;
    },

    getEntityToJSON(category) {
      if (category.length === 0) {
        return [];
      }

      const parentCategory = {};
      const categories = [];
      if (!!category.children && category.children.length > 0) {
        category.children.forEach(child => {
          const _category = {};
          _category.text = child.title;
          _category.children = this.getEntityToJSON(child.children);
          categories.push(_category);
        });
      }
      parentCategory.text = category.title;
      parentCategory.children = categories;

      return parentCategory;
    },

    onClickAction(e) {
      const action = $(e.target).data('action');
      console.log(action);

      switch (action) {
        case 'addChild':
          this.addChild();
          break;
        case 'removeChild':
          this.removeChild();
          break;
        case 'save':
          this.saveTree();
          break;
        case 'togglePublic':
          this.togglePublic();
          break;
        default:
          // nothing..
      }
    },

    togglePublic() {
      const selectedNode = this.tree.getSelectedNodeId();
      if (selectedNode !== null) {
        const selectedNodeNo = Number(selectedNode.split('-')[3]);

        if (this.isNotPublicNodes.indexOf(selectedNodeNo) === -1) {
          this.isNotPublicNodes.push(selectedNodeNo);
        } else {
          this.isNotPublicNodes.splice(this.isNotPublicNodes.indexOf(selectedNodeNo), 1);
        }
      }

      this.setPublicCSS();
    },

    setPublicCSS() {
      $('.tui-tree-text').removeClass('cancel-line');

      this.isNotPublicNodes.forEach(notPublicNode => {
        $(`#tui-tree-node-${notPublicNode} > .tui-tree-content-wrapper > .tui-tree-text`).addClass('cancel-line');
      });
    },

    isPublic(nodeId) {
      const $TextNode = $(`#${nodeId} > .tui-tree-content-wrapper > .tui-tree-text`);
      if ($TextNode.length === 1) {
        return !$TextNode.hasClass('cancel-line');
      }

      return true;
    },

    addChild() {
      const selectedNodeId = this.tree.getSelectedNodeId();
      const depth = this.tree.getDepth(selectedNodeId);
      if (depth === 4) {
        alert(MESSAGE.CATEGORY_LIMIT);

        return;
      }

      this.tree.add({text: `신규 카테고리${this.newTreeIndex++}`}, this.tree.getSelectedNodeId());
      this.setPublicCSS();
    },

    removeChild() {
      this.tree.remove(this.tree.getSelectedNodeId());
    }
  };

  blog.category.init();
});
