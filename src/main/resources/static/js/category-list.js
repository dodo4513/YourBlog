$(() => {
  const data = [
    {
      text: 'Root',
      children: []
    }
  ];

  const customInternalNode =
      `<div class="tui-tree-content-wrapper tui-tree-root-btn">
            <button type="button" class="fa fa-laptop tui-tree-toggle-btn tui-js-tree-toggle-btn">
              <span class="tui-ico-tree"></span>
              {{stateLabel}}
            </button>
            <span class="tui-tree-text tui-js-tree-text">{{text}}</span>
          </div>
          <ul class="tui-tree-subtree tui-js-tree-subtree">{{{children}}}</ul>`;

  const customLeafNode =
      `<div class="tui-tree-content-wrapper tui-tree-root-btn">
            <span class="tui-tree-text tui-js-tree-text">{{text}}</span>
          </div>`;

  blog.category = {
    tree: null,
    newTreeIndex: 1,

    init() {
      this.addEvent();
      this.initTree();
    },

    initTree() {
      this.tree = new tui.Tree('tree', {
        data,
        nodeDefaultState: 'opened',
        template: {
          internalNode: // Change to Mustache's format
              '<div class="tui-tree-content-wrapper" style="padding-left: {{indent}}px">'
              + // Example for using indent value
              '<button type="button" class="tui-tree-toggle-btn tui-js-tree-toggle-btn">'
              +
              '<span class="tui-ico-tree"></span>' +
              '{{stateLabel}}' +
              '</button>' +
              '<span class="tui-tree-text tui-js-tree-text">' +
              '{{text}}' +
              '</span>' +
              '</div>' +
              '<ul class="tui-tree-subtree tui-js-tree-subtree">' +
              '{{{children}}}' + // Mustache's format
              '</ul>',
          leafNode:
              '<div class="tui-tree-content-wrapper" style="padding-left: {{indent}}px">'
              + // Example for using indent value
              '<span class="tui-tree-text {{textClass}}">' +
              '{{text}}' +
              '</span>' +
              '</div>'
        },
        renderTemplate(tmpl, props) {
          const id = props.id;
          const depth = this.getDepth(id);
          const isFirstDepth = (this.getDepth(id) === 1);
          const isLeaf = this.isLeaf(id);

          if (isFirstDepth) { // customizing node template
            if (isLeaf) {
              tmpl = customLeafNode;
            } else {
              tmpl = customInternalNode;
            }
          }

          props.indent = 20 * depth; // customizing indent

          return Mustache.render(tmpl, props);
        }
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
        default:
          // nothing..
      }
    },

    addChild() {
      this.tree.add({text: `신규 카테고리${this.newTreeIndex++}`}, this.tree.getSelectedNodeId());
    },

    removeChild() {
      this.tree.remove(this.tree.getSelectedNodeId());
    }
  };
  blog.category.init();
});
