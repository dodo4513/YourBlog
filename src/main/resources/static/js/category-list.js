$(() => {
  const URI = {
    SAVE: '/categories',
    GET: '/categories'
  };

  const defaultTemplate = [
    {
      text: 'Root',
      children: []
    }
  ];

  const MESSAGE = {
    SAVE_SUCCESS: '저장되었습니다.',
    CATEGORY_LIMIT: '4단계까지만 추가가능합니다.',
    NOT_VALID_REMOVE_CATEGORY: '포스트가 있는 카테고리는 삭제할 수 없습니다.',
    NOT_SELECTED_NODE: '카테고리를 먼저 선택해주세요.',
    SELECT_MOVE_WANT_CATEGORY: '어디로 옮길까요? 카테고리를 선택해주세요.',
    MODIFY_SUCCESS: '변경 되었습니다.',
    NOT_MOVE_MYSELF: '자기 자신으로 이전할 수 없습니다.',
    NOT_YET_SAVED_CATEGORY: '아직 저장되지 않은 카테고리는 이전할 수 없습니다.',
    THERE_IS_NO_POSTS: '이전할 포스트가 없습니다.'
  };

  function getTemplate(isPublic) {
    return {
      customInternalNode:
          `<div class="tui-tree-content-wrapper tui-tree-root-btn">
            <button type="button" class="fa fa-laptop tui-tree-toggle-btn tui-js-tree-toggle-btn">
              <span class="tui-ico-tree"></span>
              {{stateLabel}}
            </button>
            <span class="tui-tree-text tui-js-tree-text ${isPublic ? '' : 'cancel-line'}">
              {{text}}
              <span class="badge badge-secondary">{{totalNumberOfPosts}}</span>
            </span>
          <ul class="tui-tree-subtree tui-js-tree-subtree">{{{children}}}</ul></div>`,
      customLeafNode: `<div class="tui-tree-content-wrapper tui-tree-root-btn">
            <span class="tui-tree-text tui-js-tree-text ${isPublic ? '' : 'cancel-line'}">
              {{text}}
              <span class="badge badge-secondary">{{totalNumberOfPosts}}</span>
            </span>
          </div>`
    };
  }

  blog.category = {
    tree: null,
    newTreeIndex: 1,
    isNotPublicNodes: [],
    isRemovedNodes: [],
    selectedNodeIsPublic: false,
    movePostInfo: {
      isMoveStatus: false,
      targetId: ''
    },
    moveCategoryNos: [],

    init() {
      this.addEvent();
      this.initTree();
      this.fetchCategories();
    },

    fetchCategories() {
      blog.common.ajaxForPromise({
        type: 'get',
        url: URI.GET,
        data: {publicType: 'ALL'}
      }).then(resp => {
        const responses = resp.categoryResponses
          .map(response => this.getEntityToJSON(response));
        this.tree.resetAllData(responses);
        this.initCategoryInPublicYn(this.tree.getChildIds(this.tree.getRootNodeId()));
      });
    },

    initCategoryInPublicYn(nodes) {
      nodes.forEach(node => {
        if (this.tree.getNodeData(node).isPublic === false) {
          this.isNotPublicNodes.push(Number(node.split('-')[3]));
        }

        if (this.tree.getChildIds(node).length > 0) {
          this.initCategoryInPublicYn(this.tree.getChildIds(node));
        }
      });

      this.setPublicCSS();
    },

    initTree() {
      const that = this;
      this.tree = new tui.Tree('tree', {
        defaultTemplate,
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
        }).on('select', evt => {
          if (this.movePostInfo.isMoveStatus) {
            this.movePosts2Step(evt.nodeId);
          }
        });

      this.tree.on('afterDraw', () => {
        this.setPublicCSS();
      });
    },

    addEvent() {
      const $body = $('body');
      $body.on('click', '.action', $.proxy(this.onClickAction, this));
    },

    saveTree() {
      const data = {};
      data.categoryRequests = this.getCategoryToJSON(this.tree.getRootNodeId());
      data.removedCategoryNo = this.isRemovedNodes;
      data.moveCategoryNos = this.moveCategoryNos;

      blog.common.ajaxForPromise({
        type: 'post',
        url: URI.SAVE,
        data: JSON.stringify(data)
      }).then(() => {
        alert(MESSAGE.SAVE_SUCCESS);
        location.reload();
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
        category.publicYn = !$(`#tui-tree-node-${childId.split('-')[3]} > .tui-tree-content-wrapper > .tui-tree-text`).hasClass('cancel-line');
        category.categoryNo = tree.getNodeData(childId).categoryNo
          ? tree.getNodeData(childId).categoryNo : 0;
        categories.push(category);
      }

      return categories;
    },

    getEntityToJSON(categoryEntity) {
      const category = {};
      const childCategories = [];
      if (categoryEntity.children && categoryEntity.children.length > 0) {
        categoryEntity.children.forEach(childEntity => {
          childCategories.push(this.getEntityToJSON(childEntity));
        });
      }
      category.text = categoryEntity.title;
      category.children = childCategories;
      category.isPublic = categoryEntity.publicYn;
      category.categoryNo = categoryEntity.categoryNo;
      category.totalNumberOfPosts = categoryEntity.totalNumberOfPosts;

      return category;
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
        case 'movePosts':
          this.movePosts1Step();
          break;
        default:
          // nothing..
      }
    },

    disableAllBtn(status) {
      if (status) {
        $('.action').addClass('disable-btn');
      } else {
        $('.action').removeClass('disable-btn');
      }
    },

    movePosts1Step() {
      const selectedNodeId = this.tree.getSelectedNodeId();
      if (selectedNodeId === null) {
        alert(MESSAGE.NOT_SELECTED_NODE);

        return;
      }

      const selectedNodePostsCount = this.tree.getNodeData(selectedNodeId).totalNumberOfPosts;
      if (!selectedNodePostsCount || selectedNodePostsCount === 0) {
        alert(MESSAGE.THERE_IS_NO_POSTS);

        return;
      }

      this.tree.deselect(selectedNodeId);
      this.movePostInfo.isMoveStatus = true;
      this.movePostInfo.targetId = selectedNodeId;

      alert(MESSAGE.SELECT_MOVE_WANT_CATEGORY);
      this.disableAllBtn(true);
    },

    movePosts2Step(selectedNodeId) {
      this.movePostInfo.isMoveStatus = false;
      this.disableAllBtn(false);

      if (this.movePostInfo.targetId === selectedNodeId) {
        alert(MESSAGE.NOT_MOVE_MYSELF);

        return;
      }
      const targetNodeData = this.tree.getNodeData(this.movePostInfo.targetId);
      const selectedNodeData = this.tree.getNodeData(selectedNodeId);

      if (!targetNodeData.categoryNo || !selectedNodeData.categoryNo) {
        alert(MESSAGE.NOT_YET_SAVED_CATEGORY);

        return;
      }

      selectedNodeData.totalNumberOfPosts += targetNodeData.totalNumberOfPosts;
      targetNodeData.totalNumberOfPosts = 0;

      this.moveCategoryNos.push({
        preCategoryNo: targetNodeData.categoryNo,
        postCategoryNo: selectedNodeData.categoryNo
      });

      this.tree.setNodeData(this.movePostInfo.targetId, targetNodeData);
      this.tree.setNodeData(selectedNodeId, selectedNodeData);

      alert(MESSAGE.MODIFY_SUCCESS);
    },

    togglePublic() {
      const selectedNodeId = this.tree.getSelectedNodeId();
      if (selectedNodeId !== null) {
        const childIds = this.getMeAndAllChildIds(selectedNodeId);
        const parentNodeIsPublic = this.isNotPublicNodes.indexOf(
          Number(selectedNodeId.split('-')[3])) === -1;

        for (let i = 0; i < childIds.length; i++) {
          const targetNodeId = Number(childIds[i].split('-')[3]);

          if (this.isNotPublicNodes.indexOf(targetNodeId) === -1 && parentNodeIsPublic) {
            this.isNotPublicNodes.push(targetNodeId);
          } else if (!parentNodeIsPublic) {
            this.isNotPublicNodes.splice(
              this.isNotPublicNodes.indexOf(targetNodeId), 1);
          }
        }
      }

      this.setPublicCSS();
    },

    getMeAndAllChildIds(treeId) {
      let ids = [];
      const childIds = this.tree.getChildIds(treeId);
      ids = ids.concat(treeId);

      if (!!childIds && childIds.length > 0) {
        for (let i = 0; i < childIds.length; i++) {
          ids = ids.concat(this.getMeAndAllChildIds(childIds[i]));
        }
      }

      return ids;
    },

    setPublicCSS() {
      $('.tui-tree-text').removeClass('cancel-line');

      this.isNotPublicNodes.forEach(notPublicNode => {
        $(`#tui-tree-node-${notPublicNode} > .tui-tree-content-wrapper > .tui-tree-text`).addClass(
          'cancel-line');
      });
    },

    isPublic(nodeId) {
      const $TextNode = $(
        `#${nodeId} > .tui-tree-content-wrapper > .tui-tree-text`);
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

      this.tree.add({text: `신규 카테고리${this.newTreeIndex++}`},
        this.tree.getSelectedNodeId());
      this.setPublicCSS();
    },

    removeChild() {
      const selectedNodeId = this.tree.getSelectedNodeId();
      const meAndChildNodeIds = this.getMeAndAllChildIds(selectedNodeId);

      // valid
      for (let i = 0; i < meAndChildNodeIds.length; i++) {
        if (this.tree.getNodeData(meAndChildNodeIds[i]).totalNumberOfPosts > 0) {
          alert(MESSAGE.NOT_VALID_REMOVE_CATEGORY);

          return;
        }
      }

      for (let i = 0; i < meAndChildNodeIds.length; i++) {
        const categoryNo = this.tree.getNodeData(meAndChildNodeIds[i]).categoryNo;

        if (categoryNo) {
          this.isRemovedNodes.push(categoryNo);
        }
      }

      for (let i = 0; i < meAndChildNodeIds.length; i++) {
        this.tree.remove(meAndChildNodeIds[i]);
      }
    }
  };

  blog.category.init();
});
