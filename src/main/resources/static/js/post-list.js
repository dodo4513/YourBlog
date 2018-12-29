$(() => {
    const URI = {POST: '/posts'};

    blog.post = {
        grid: null,
        init() {
            this.addEvent();
            this.initGrind()
                .then(this.searchPosts());
            blog.autocomplete.init($('#tags'), '/tags');
        },
        initGrind() {
            return new Promise(() => {
                this.grid = new tui.Grid({
                    el: $('#wrapper'),
                    scrollX: false,
                    columns: blog.column.post(),
                    pagination: true
                });
            });
        },
        searchPosts() {
            blog.common.ajaxForPromise({
                url: URI.POST,
                data: $('#search-form').serialize()
            })
                .then(resp => {
                    this.grid.setData(resp);
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
