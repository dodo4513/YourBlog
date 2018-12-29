$(() => {
    const URI = {POST: '/posts'};

    blog.post = {
        grid: null,
        init() {
            this.addEvent();
            this.initGrind()
                .then(this.searchPosts());
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
                url: URI.POST
            })
                .then(resp => {
                    this.grid.setData(resp);
                });
        },

        addEvent() {
            const $body = $('body');
            $body.on('click', '#menuToggle', $.proxy(this.onClickMenuToggle, this));
        },

        onClickMenuToggle() {
            this.grid.refreshLayout();
        }

    };

    blog.post.init();
});
