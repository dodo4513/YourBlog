console.log('popup.js');

const blog = {};
blog.popup = {
  init() {
    const $body = $('body');
    window.resizeTo($body.width() + 20, $body.height() + 70);
    this.addEvent();
  },

  addEvent() {
    const $body = $('body');
    $body.on('click', '.action', $.proxy(this.onClickAction, this));
  },

  onClickAction(e) {
    const action = $(e.target).data('action');
    console.log(action);

    switch (action) {
      case 'cancel':
        self.close();
        break;
      default:
        // nothing..
    }
  }
};

$(() => {
  blog.popup.init();
});
