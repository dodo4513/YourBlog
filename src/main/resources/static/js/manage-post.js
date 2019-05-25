/* eslint-disable no-eval */
$(() => {
  const URI = {
    REGISTER_POST: '/posts',
    LIST: '/admin/post/list',
    CATEGORY: '/categories',
    IMAGE_UPLOAD: '/images',
    GET_POST: '/posts/{{postNo}}'
  };

  const MESSAGE = {
    SAVE_SUCCESS: '저장되었습니다.'
  };

  const MODE = {
    MODIFY: 'MODIFY',
    WRITE: 'WRITE'
  };

  blog.managePost = {
    editor: null,
    grid: null,
    usedImageNos: [],
    mode: MODE.WRITE,

    init() {
      if (this.getSelectedPostNo() > 0) {
        this.mode = MODE.MODIFY;
        console.log('Page mode: ', this.mode);
      }

      this.initTui();
      this.initEvent();
      this.initTagAutoComplete();

      Promise.all([this.initCategory()])
        .then(() => this.setUpPostAtModify());
    },

    setUpPostAtModify() {
      if (this.mode === MODE.MODIFY) {
        blog.common.ajaxForPromise({
          type: 'get',
          url: URI.GET_POST.replace('{{postNo}}', this.getSelectedPostNo)
        }).then(post => {
          console.log(post);
          $('#title').val(post.title);
          $('#description').val(post.description);
          $('#splashImage').val(post.splashImage);
          $('#category-selector').val(post.category.categoryNo);
          this.editor.setValue(post.body);
          this.collectImageFromBody();
          $('#tags').val(post.tags.map(t => t.name).join(','));
          $('#publicYn').prop('checked', post.publicYn);
          if (post.extraData !== null) {
            $('#extraDataCheck').prop('checked', true);

            for (const key in post.extraData) {
              this.addRowAtExtraData({
                key,
                value: post.extraData[key]
              });
            }
          }
          this.setExtraDataGrid();
        });
      }
    },

    getSelectedPostNo() {
      return $('#selectedPostNo').val();
    },

    initTagAutoComplete() {
      blog.autocomplete.init(
        $('#tags'),
        '/tags',
        resp => resp.tagResponses.map(tagResponse => tagResponse.name));
    },

    initCategory() {
      return new Promise((resolve, reject) => {
        blog.common.ajaxForPromise({
          type: 'get',
          url: URI.CATEGORY
        }).then(resp => {
          if (resp.totalCount > 0) {
            let categoryTitlesAndNos = [];
            resp.categoryResponses.forEach(categoryEntity => {
              categoryTitlesAndNos = categoryTitlesAndNos.concat(this.makeCategoryTitle(categoryEntity, 0));
            });
            $('#category-selector').html(categoryTitlesAndNos);
          } else {
            $('#no-category-alert').show();
            this.notWriteablePostStatus();
          }
          resolve();
        });
      });
    },

    notWriteablePostStatus() {
      $('input').prop('disabled', true);
      const $saveBtn = $('#save'); // FIXME ID 써야하나?
      $saveBtn.removeClass('btn-success');
      $saveBtn.addClass('btn-secondary');
      $saveBtn.attr('data-action', '');
    },

    makeCategoryTitle(categoryEntity, depth) {
      let selector = [];
      let name = '';
      for (let i = 0; i < depth; i++) {
        name += '&nbsp&nbsp&nbsp';
      }
      name += `${depth > 0 ? 'ㄴ' : ''}${categoryEntity.name}`;
      selector.push(`<option value="${categoryEntity.categoryNo}">${name}</option>`);

      for (let i = 0; i < categoryEntity.children.length; i++) {
        selector = selector.concat(this.makeCategoryTitle(categoryEntity.children[i], depth + 1));
      }

      return selector;
    },

    initTui() {
      this.editor = new tui.Editor({
        el: document.querySelector('#tui-editor'),
        initialEditType: 'markdown',
        previewStyle: 'vertical',
        height: '300px',
        exts: ['scrollSync', 'colorSyntax'],
        hooks: {
          'addImageBlobHook': (blob, callback) => {
            const formData = new FormData();
            formData.append('file', blob);

            blog.common.ajaxForPromise({
              url: URI.IMAGE_UPLOAD,
              data: formData,
              dataType: 'text',
              processData: false,
              contentType: false,
              type: 'POST'
            }).then(uri => callback(uri));
          }
        }
      });

      this.editor.on('change', () => {
        this.collectImageFromBody();
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

      if (this.mode === MODE.WRITE) {
        this.addRowAtExtraData({
          key: '',
          value: ''
        });
      }
    },

    collectImageFromBody() {
      const contents = this.editor.getMarkdown();
      const regex = /!\[*.*\(*\/images\/\d{0,}/gi;

      const images = [];
      let image;
      while ((image = regex.exec(contents)) !== null) {
        images.push(image[0]);
      }

      console.log(images)

      const imageNos = [...new Set(images.map(i => i.split('/images/')[1]).sort())];

      console.log(imageNos)

      // 이미지 목록이 변경 됐다면
      if (imageNos.length !== this.usedImageNos.length ||
          !imageNos.every((value, index) => value === this.usedImageNos[index])) {
        this.usedImageNos = imageNos;

        let html = '';
        imageNos.forEach(i =>
          html += ` <li class='image'><img id='abc${i}' src="/images/${i}" alt="${i} image"/>
                        <div class="overlay"><div class="text"></div></div>
                      </li>`
        );
        $('#image-box').empty().append(html);
      }
    },

    addRowAtExtraData(content) {
      const row = this.grid.getRows();
      row.push(content);
      this.grid.setData(row);
    },

    removeRow(e) {
      blog.managePost.grid.removeRow(String($(e.target).closest('tr').data('row-key')));
    },

    initEvent() {
      const $body = $('body');
      $body.on('click', '.action', $.proxy(this.onClickAction, this));
      $body.on('change', '#extraDataCheck', $.proxy(this.setExtraDataGrid, this));
      $body.on('click', '.removeRow', $.proxy(this.removeRow, this));
      $body.on('mouseover', '.image > .overlay', $.proxy(this.hoverOnImage, this));
    },

    hoverOnImage(e) {
      const $img = $(e.target).siblings('img');
      const $text = $(e.target).find('.text');
      if ($text.html() !== '') {
        return;
      }

      this.getImageFileSize($img[0].src).then(resp => {
        $text.html(`${$img[0].naturalWidth}x${$img[0].naturalHeight}<br/>${resp.type}<br/>${resp.size / 1000}kb`);
      });
    },

    setExtraDataGrid() {
      if ($('#extraDataCheck:checked').val() === 'on') {
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
          this.addRowAtExtraData({
            key: '',
            value: ''
          });
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
        url: URI.REGISTER_POST,
        data: JSON.stringify(postObject)
      }).then(() => {
        alert(MESSAGE.SAVE_SUCCESS);
        location.href = URI.LIST;
      });
    },

    makePostObject() {
      return {
        postNo: this.getSelectedPostNo(),
        title: $('#title').val(),
        description: $('#description').val(),
        splashImage: $('#splashImage').val(),
        body: this.editor.getMarkdown(),
        tags: this.makeTagsObject(),
        publicYn: $('[name=publicYn]:checked').val() === 'on',
        extraData: this.makeExtraData(),
        categoryNo: $('#category-selector').val()
      };
    },

    makeTagsObject() {
      const tags = [];
      $('#tags').val().split(',').forEach(tag => tags.push({name: tag}));

      return tags;
    },

    makeExtraData() {
      if ($('#extraDataCheck:checked').val() === 'on') {
        return eval(`({${blog.managePost.grid.getRows().map(row => `${row.key}:'${row.value}'`).join(',')}})`);
      }

      return null;
    },

    getImageFileSize(url) {
      return new Promise(resolve => {
        const imageUrl = url;
        let blob = null;
        const xhr = new XMLHttpRequest();
        xhr.open('GET', imageUrl, true);
        xhr.responseType = 'blob';
        xhr.onload = function() {
          blob = xhr.response;
          resolve(blob);
        };
        xhr.send();
      });
    }
  };

  blog.managePost.init();
});
