const $ = jQuery;
const blog = {};
blog.common = {
  SETTING: {LOCATION: 'http://localhost:8081'},
  MOCK_DATA: true,

  ajaxForPromise: params => new Promise((resolve, reject) => {
    const progressCircle = params ? params.progress : true;

    if (progressCircle !== false) {
      blog.common.curtainToggle(true);
    }
    console.log(params);
    const p = {};

    p.url = `${blog.common.SETTING.LOCATION}${params.url}`;
    p.type = typeof params.type === 'undefined' ? 'GET' : params.type;
    p.contentType = typeof params.contentType === 'undefined' ? 'application/json' : params.contentType;
    p.async = typeof params.async === 'undefined' ? true : params.type;
    p.success = resp => {
      console.log('Communication ok\n response: ', resp);
      if (progressCircle !== false) {
        blog.common.curtainToggle(false);
      }
      resolve(resp);
    };

    if (typeof params.data !== 'undefined') {
      p.data = params.data;
    }

    if (typeof params.dataType !== 'undefined') {
      p.dataType = params.dataType;
    }

    if (typeof params.processData !== 'undefined') {
      p.processData = params.processData;
    }

    $.ajax(p);
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
  },

  openPopup: (url, name, width, height, scrollAble) => {
    const x = (screen.width - width) / 2;
    const y = (screen.height - height) / 2;

    if (typeof scrollAble === 'undefined') {
      scrollAble = 'no';
    }

    if (screen.width >= 800 && screen.height >= 600) {
      scrollAble = 'yes';
    }

    let o = `toolbar=no, channelmode=no, location=no, directories=no, menubar=no`;
    o = `${o}, top=${y}, left=${x}, scrollbars=${scrollAble}, width=${width}, height=${height}, resizable=no`;
    const win = window.open(url, name, o);

    try {
      win.moveTo(x, y);
      win.focus();
    } catch (e) {
      console.log(e);
    }

    return win;
  }
};

