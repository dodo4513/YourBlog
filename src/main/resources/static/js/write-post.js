/* eslint-disable no-eval */
$(() => {
  const URI = {
    POST: '/posts',
      LIST: '/admin/post-list'
  };

  const MESSAGE = {
    SAVE_SUCCESS: '저장되었습니다.'
  };

  blog.writePost = {
    editor: null,
    grid: null,
    init() {
      this.initEditor();
      this.addEvent();
      blog.autocomplete.init($('#tags'), '/tags');
    },

    initEditor() {
      this.editor = new tui.Editor({
        el: document.querySelector('#tui-editor'),
        initialEditType: 'markdown',
        previewStyle: 'vertical',
        height: '300px'
      });

      this.setExtraDataGrid();

      this.grid = new tui.Grid({
        el: $('#extra-data-grid'),
        scrollX: false,
        columns: blog.column.extraData(),
        bodyHeight: 100,
        header: {height: 25}
      });
      // tui.Grid.applyTheme('clean');
      this.addEmptyRowAtExtraData();
    },

    addEmptyRowAtExtraData() {
      const row = this.grid.getRows();
      row.push({
        key: '',
        value: ''
      });
      this.grid.setData(row);
    },

    removeRow(e) {
      blog.writePost.grid.removeRow(String($(e.target).closest('tr').data('row-key')));
    },

    addEvent() {
      const $body = $('body');
      $body.on('click', '.action', $.proxy(this.onClickAction, this));
      $body.on('change', '#extra-data-use-check', $.proxy(this.setExtraDataGrid, this));
      $body.on('click', '.removeRow', $.proxy(this.removeRow, this));
    },

    setExtraDataGrid() {
      if ($('#extra-data-use-check:checked').val() === 'on') {
        $('#extra-data-box').show();
      } else {
        $('#extra-data-box').hide();
      }
    },

    onClickAction(e) {
      const action = $(e.target).data('action');
      console.log(action);

      switch (action) {
        case 'register':
          this.registerPost();
          break;
        case 'addRow':
          this.addEmptyRowAtExtraData();
          break;
        default:
        // nothing..
      }
    },

    validation(param) {
      if (!param.title) {
        alert('제목을 입력해주세요.');

        return false;
      }

      if (param.title.length > 100) {
        alert('최대 100자 이내로 입력해주세요.');

        return false;
      }

      if (param.body === '') {
        alert('내용을 입력해주세요.');

        return false;
      }

      return true;
    },

    registerPost() {
      const postObject = this.makePostObject();

      if (!this.validation(postObject)) {
        return;
      }

      blog.common.ajaxForPromise({
        type: 'post',
        url: URI.POST,
        data: JSON.stringify(postObject)
      }).then(() => {
        alert(MESSAGE.SAVE_SUCCESS);
        location.href = URI.LIST;
      });
    },

    makePostObject() {
      return {
        title: $('#title').val(),
        body: this.editor.getMarkdown(),
        tags: this.makeTagsObject(),
        publicYn: $('[name=publicYn]:checked').val() === 'on',
        extraData: this.makeExtraData()
      };
    },

    makeTagsObject() {
      const tags = [];
      $('#tags').val().split(',').forEach(tag => tags.push({name: tag}));

      return tags;
    },

    makeExtraData() {
      if ($('#extra-data-use-check:checked').val() === 'on') {
        return eval(`({${blog.writePost.grid.getRows().map(row => `${row.key}:'${row.value}'`).join(',')}})`);
      }

      return null;
    }
  };

  blog.writePost.init();
});
