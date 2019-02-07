const $ = jQuery;
const blog = {};
blog.common = {
  SETTING: {LOCATION: 'http://localhost:8081'},
  MOCK_DATA: true,

  ajaxForPromise: (params, option) => new Promise((resolve, reject) => {
    const progressCircle = option ? option.progress : true;

    if (progressCircle) {
      blog.common.curtainToggle(true);
    }
    console.log(params);

    $.ajax({
      url: `${blog.common.SETTING.LOCATION}${params.url}`,
      type: params.type || 'GET',
      data: params.data,
      contentType: 'application/json',
      success(resp) {
        console.log('Communication ok\n response: ', resp);
        if (progressCircle) {
          blog.common.curtainToggle(false);
        }
        resolve(resp);
      }
    });
  }),
  getDateInKR(date) {
    const r = new Date(date);

    return `${r.getFullYear()}년 ${r.getMonth() + 1}월 ${r.getDate()}일`;
  },

  curtainToggle(flag) {
    if (flag) {
      $('body').append('<div id="curtain"></div>');
    } else {
      $('#curtain').remove();
    }
  }
};

