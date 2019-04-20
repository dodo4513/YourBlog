/* eslint-disable no-eval */
$(() => {
  const URI = {
    POST: '/posts',
    LIST: '/admin/post/list',
    CATEGORY: '/categories',
    IMAGE_UPLOAD: '/images'
  };

  const MESSAGE = {
    SAVE_SUCCESS: '저장되었습니다.'
  };

  blog.writePost = {
    editor: null,
    grid: null,
    usedImageNos: [],
    init() {
      this.initTui();
      this.addEvent();
      this.initTagAutoComplete();
      this.initCategory();
    },

    initTagAutoComplete() {
      blog.autocomplete.init(
        $('#tags'),
        '/tags',
        resp => resp.tagResponses.map(tagResponse => tagResponse.name));
    },

    initCategory() {
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
        const contents = this.editor.getMarkdown();
        const regex = /!\[image.png\]\(\/images\/\d{0,}/g;
        const images = [];
        let image;
        while ((image = regex.exec(contents)) !== null) {
          images.push(image[0]);
        }

        const imageNos = [...new Set(images.map(i => i.split('/')[2]).sort())];

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
      if ($('#extra-data-use-check:checked').val() === 'on') {
        return eval(`({${blog.writePost.grid.getRows().map(row => `${row.key}:'${row.value}'`).join(',')}})`);
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

  blog.writePost.init();
});
