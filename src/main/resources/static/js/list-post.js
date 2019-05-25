$(() => {
  const URI = {GET_POST: '/posts'};
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

      this.initAutocomplete();
    },

    initAutocomplete() {
      blog.autocomplete.init(
        $('#tags'),
        '/tags',
        resp => resp.tagResponses.map(t => t.name));

      blog.autocomplete.init(
        $('#categories'),
        '/categories?publicType=ONLY_PUBLIC',
        resp => resp.categoryResponses.map(c => c.name));
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
      const data = {};
      data.pageNumber = this.pageNumber;

      if ($('#publicY:checked').val() && !$('#publicN:checked').val()) {
        data.publicYn = true;
      } else if (!$('#publicY:checked').val() && $('#publicN:checked').val()) {
        data.publicYn = false;
      }

      if ($('#tags').val().length > 0) {
        data.tags = $('#tags').val()
          .split(',')
          .map(t => t.trim())
          .filter(t => t.length > 0);
      }

      if ($('#categories').val().length > 0) {
        data.categories = $('#categories').val()
          .split(',')
          .map(t => t.trim())
          .filter(c => c.length > 0);
      }

      switch ($('[name=orderingMethod]:checked').val()) {
        case 'LASTED':
          data.orderingMethod = 'LASTED';
          break;
        case 'POPULAR':
          data.orderingMethod = 'POPULAR';
          break;
      }

      console.log('data :', data);

      blog.common.ajaxForPromise({
        url: URI.GET_POST,
        data
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
