let eventHandler;
blog.autocomplete = {
  split(val) {
    return val.split(/,\s*/);
  },

  extractLast(term) {
    return this.split(term).pop();
  },

  init($tags, sourceUrl, handler) {
    $tags.on(handler, function(event) {
      eventHandler = event.originalEvent.type;
      if (event.keyCode === $.ui.keyCode.TAB &&
            $(this).autocomplete('instance').menu.active) {
        event.preventDefault();
      }
    })
      .autocomplete({
        source(request, response) {
          blog.common.ajaxForPromise({url: sourceUrl[eventHandler]}, {progress: false}).then(resp => {
            const tagNames = resp.map(tagResponse => tagResponse.name);
            response($.ui.autocomplete.filter(
              tagNames, (eventHandler == 'focus') ? '' : blog.autocomplete.extractLast(request.term)));
          });
        },

        search() {
          /*if (term.length < 1) {
            return false;
          }*/
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
      }).focus(function () {
        $(this).autocomplete("search", " ");
      });
  }
};
