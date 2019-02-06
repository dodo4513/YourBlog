$(() => {
  const data = [
    {
      text: 'Root',
      children: []
    }
  ];

  function getTemplate(isPublic) {
    return {
      customInternalNode: `<div class="tui-tree-content-wrapper tui-tree-root-btn">
            <button type="button" class="fa fa-laptop tui-tree-toggle-btn tui-js-tree-toggle-btn">
              {{stateLabel}}
            </button>
            <span class="tui-tree-text tui-js-tree-text ${isPublic ? '' : 'cancel-line'}">{{text}}</span>
          </div>
          <ul class="tui-tree-subtree tui-js-tree-subtree">{{{children}}}</ul>`,
      customLeafNode: `<div class="tui-tree-content-wrapper tui-tree-root-btn">
            <span class="tui-tree-text tui-js-tree-text ${isPublic ? '' : 'cancel-line'}">{{text}}</span>
          </div>`
    };
  }

  blog.category = {
    tree: null,
    newTreeIndex: 1,
    isNotPublicNodes: [],

    init() {
      this.addEvent();
      this.initTree();
    },

    initTree() {
      const that = this;
      this.tree = new tui.Tree('tree', {
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

          props.indent = 20 * depth; // customizing indent

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
      const category = this.getCategoryToJSON(this.tree.getRootNodeId());
      console.log(category);
    },

    getCategoryToJSON(parentId) {
      const categories = [];
      const tree = this.tree;
      const childIds = tree.getChildIds(parentId);

      for (const childId of childIds) {
        const category = {};
        category.title = tree.getNodeData(childId).text;
        category.displayOrder = categories.length + 1;
        category.subCategories = this.getCategoryToJSON(childId);
        categories.push(category);
      }

      return categories;
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
        alert('4단계까지만 추가가능합니다.');

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
