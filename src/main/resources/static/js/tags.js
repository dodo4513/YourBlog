$(() => {

    const URI = {POST: '/getTags', LIST: '/admin/tags'};
    const SAVE_URI = {POST: '/saveTags'};
    const MAX_ROW_PER_POST = 10;
    const MESSAGE = {
        SAVE_SUCCESS: '저장되었습니다.'
    };

blog.tags = {
        init() {
            $('[name=pageSize]').val(MAX_ROW_PER_POST);
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
                columns: blog.column.tag(),
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
            }).then(resp => {
                this.grid.setData(resp);

                const pagination = this.grid.getPagination();
                //pagination.setTotalItems(resp.totalCount);
                pagination.reset();
                this.afterCall = true;
                pagination.movePageTo(this.pageNumber);

                $('#total-count-badge').text(resp.totalCount);
            });
        },
        saveTag() {
            const tagObject = this.makeTagObject();

            if (!this.validation(tagObject)) {
                return;
            }

            blog.common.ajaxForPromise({
                type: 'post',
                url: SAVE_URI.POST,
                data: JSON.stringify(tagObject)
            }).then(() => {
                alert(MESSAGE.SAVE_SUCCESS);
                location.href = URI.LIST;
            });


        },
        makeTagObject() {
            return {
                name: document.getElementById('name').value
            };
        },
        validation(param) {
            if (!param.name) {
                alert('태그명 입력해주세요.');
                return false;
            }
            return true;
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
                case 'save':
                    this.saveTag();
                    break;
                default:
                // nothing..
            }
        },

        onClickMenuToggle() {
            this.grid.refreshLayout();
        }


    }
    blog.tags.init();
});