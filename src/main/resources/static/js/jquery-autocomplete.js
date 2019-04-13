blog.autocomplete = {
  activeEventType: null,
  split(val) {
    return val.split(/,\s*/);
  },

  extractLast(term) {
    return this.split(term).pop();
  },

  init($tags, sourceUrl, fetchHandler) {
    const that = this;
    $tags.on('keydown focus', event => {
      that.activeEventType = event.originalEvent.type;
      if (event.keyCode === $.ui.keyCode.TAB) {
        event.preventDefault();
      }
    }).autocomplete({
      source(request, response) {
        blog.common.ajaxForPromise({
          url: sourceUrl,
          progress: false
        }).then(resp => {
          response($.ui.autocomplete.filter(
            fetchHandler(resp),
            that.activeEventType === 'focus' ? '' : blog.autocomplete.extractLast(request.term)));
        });
      },
      focus() {
        return false;
      },

      select(event, ui) {
        const terms = blog.autocomplete.split(this.value);
        terms.pop();
        terms.push(ui.item.value);
        terms.push('');
        this.value = terms.join(', ');

        return false;
      }
    }).focus(function() {
      $(this).autocomplete('search', ' ');
    });
  }
};
