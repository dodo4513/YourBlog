const $ = jQuery;
const blog = {};
blog.common = {
    SETTING: {LOCATION: 'http://localhost:8081'},
    MOCK_DATA: true,

    ajaxForPromise: params => new Promise((resolve, reject) => {
        $.ajax({
            url: `${blog.common.SETTING.LOCATION}${params.url}`,
            type: params.type || 'GET',
            data: params.data,
            contentType: 'application/json',
            success(resp) {
                console.log('Communication ok\n response: ', resp);
                resolve(resp);
            }
        });
    }),
    getDateInKR(date) {
        const r = new Date(date);

        return `${r.getFullYear()}년 ${r.getMonth()}월 ${r.getDay()}일`;
    }
};

