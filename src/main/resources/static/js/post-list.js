$(() => {
  const URI = {POST: '/posts'};
  const MAX_ROW_PER_POST = 10;

  blog.post = {
    grid: null,
    pageNumber: 1,
    afterCall: false,
    init() {
      $('[name=pageSize]').val(MAX_ROW_PER_POST);
      this.addEvent();
      this.initGrind()
        .then(this.searchPosts());
      blog.autocomplete.init(
        $('#tags'),
        '/tags',
        resp => resp.tagResponses.map(tagResponse => tagResponse.name));
    },
    initGrind() {
      return new Promise(() => {
        this.grid = new tui.Grid({
          el: $('#wrapper'),
          scrollX: false,
          columns: blog.column.post(),
          useClientSort: true,
          bodyHeight: 440,
          pagination: {
            itemsPerPage: MAX_ROW_PER_POST,
            visiblePages: 5
          }
        });
        tui.Grid.applyTheme('clean');

        const that = this;

        this.grid.getPagination().on('beforeMove', ev => { // 버튼 클릭시 핸들러 지정
          if (!that.afterCall) {
            that.pageNumber = ev.page;
            that.searchPosts();
          }
        });

        this.grid.getPagination().on('afterMove', () => {
          that.afterCall = false;
        });
      });
    },
    searchPosts() {
      // Set hidden value
      $('[name=pageNumber]').val(this.pageNumber);

      if ($('#publicY:checked').val() && !$('#publicN:checked').val()) {
        $('[name=publicYn]').val(true);
      } else if (!$('#publicY:checked').val() && $('#publicN:checked').val()) {
        $('[name=publicYn]').val(false);
      } else {
        $('[name=publicYn]').val('');
      }

      blog.common.ajaxForPromise({
        url: URI.POST,
        data: $('#search-form').serialize()
      })
        .then(resp => {
          console.log(resp);
          this.grid.setData(resp.postResponses);

          const pagination = this.grid.getPagination();
          pagination.setTotalItems(resp.totalCount);
          pagination.reset();
          this.afterCall = true;
          pagination.movePageTo(this.pageNumber);

          $('#total-count-badge').text(resp.totalCount);
        });
    },

    addEvent() {
      const $body = $('body');
      $body.on('click', '#menuToggle', $.proxy(this.onClickMenuToggle, this));
      $body.on('click', '.action', $.proxy(this.onClickAction, this));
      $body.on('keypress', '.search-condition', $.proxy(this.checkEnterKey, this));
    },

    checkEnterKey(e) {
      if (e.which === 13) {
        this.searchPosts();
      }
    },

    onClickAction(e) {
      const action = $(e.target).data('action');

      switch (action) {
        case 'search':
          this.searchPosts();
          break;
        case 'clear':
          $('#search-form')[0].reset();
          break;
        default:
        // nothing..
      }
    },

    onClickMenuToggle() {
      this.grid.refreshLayout();
    }

  };

  blog.post.init();
});
