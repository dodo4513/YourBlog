$(() => {
    const URI = {ME: '/info/me'};

    blog.setMe = {
        init() {
            this.addEvent();
            this.testing(); // FIXME
        },

        testing() {
            if (blog.common.MOCK_DATA) {
                $('#name').val('황도');
                $('#email').val('dpdp@namver.com');
            }
        },

        addEvent() {
            const $body = $('body');
            $body.on('click', '.action', $.proxy(this.onClickAction, this));
        },

        onClickAction(e) {
            const action = $(e.target).data('action');

            switch (action) {
                case 'register':
                    this.registerMe();
                    break;
                default:
                // nothing..
            }
        },

        registerMe() {
            const post = this.makeMeObject();
            const param = {
                type: 'post',
                url: URI.ME,
                data: JSON.stringify(post)
            };
            blog.common.ajaxForPromise(param);
        },

        makeMeObject() {
            return {
                name: $('#name').val(),
                email: $('#email').val(),
                introduction: $('#introduction').val(),
                extraData: {github: 'dodo4513@gmail.com',
                    naver: 'xavier@naver.com',
                    introduce: '개개발'}

            };
        }
    };

    blog.setMe.init();
});
