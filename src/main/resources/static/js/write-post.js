$(() => {
    const URI = {POST: '/posts'};

    blog.writePost = {
        editor: null,
        init() {
            this.initEditor();
            this.addEvent();
            this.testing(); // FIXME
        },

        testing() {
            if (blog.common.MOCK_DATA) {
                $('#title').val('제목');
                this.editor.setMarkdown('### hihi');
            }
        },

        initEditor() {
            this.editor = new tui.Editor({
                el: document.querySelector('#tui-editor'),
                initialEditType: 'markdown',
                previewStyle: 'vertical',
                height: '300px'
            });
        },

        addEvent() {
            const $body = $('body');
            $body.on('click', '.action', $.proxy(this.onClickAction, this));
        },

        onClickAction(e) {
            const action = $(e.target).data('action');

            switch (action) {
                case 'register':
                    this.registerPost();
                    break;
                default:
                // nothing..
            }
        },

        registerPost() {
            const post = this.makePostObject();
            const param = {
                type: 'post',
                url: URI.POST,
                data: JSON.stringify(post)
            };

            blog.common.ajaxForPromise(param);
        },

        makePostObject() {
            return {
                title: $('#title').val(),
                body: this.editor.getMarkdown(),
                tags: this.makeTagsObject(),
                extraData: {one: 1,
                    two: '2'}
            };
        },

        makeTagsObject() {
            const tags = [];
            $('#tags').val().split(',').forEach(tag => tags.push({name: tag}));

            return tags;
        }
    };

    blog.writePost.init();
});
