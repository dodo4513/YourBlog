$(() => {
    const URI = {BLOG_INFO: '/info/blog'};

    blog.main = {
        init() {
            this.initBlogInfo();
        },

        initBlogInfo() {
            blog.common.ajaxForPromise({
                url: URI.BLOG_INFO
            }).then(resp => this.renderBlogInfo(resp));
        },

        renderBlogInfo(blogInfoResponse) {
            $('#totalPost').text(blogInfoResponse.postInfoResponse.totalPost);
            $('#postFor7Days').text(blogInfoResponse.postInfoResponse.postFor7Days);
            $('#todayVisit').text(blogInfoResponse.visitInfoResponse.todayVisit);
            $('#totalVisit').text(blogInfoResponse.visitInfoResponse.totalVisit);
        }
    };

    blog.main.init();
});
