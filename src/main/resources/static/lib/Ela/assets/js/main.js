$.noConflict();

jQuery(document).ready($ => {
    [].slice.call(document.querySelectorAll('select.cs-select')).forEach(el => {
        new SelectFx(el);
    });

    jQuery('.selectpicker').selectpicker;

    $('.search-trigger').on('click', event => {
        event.preventDefault();
        event.stopPropagation();
        $('.search-trigger').parent('.header-left').addClass('open');
    });

    $('.search-close').on('click', event => {
        event.preventDefault();
        event.stopPropagation();
        $('.search-trigger').parent('.header-left').removeClass('open');
    });

    $('.equal-height').matchHeight({
        property: 'max-height'
    });

    // var chartsheight = $('.flotRealtime2').height();
    // $('.traffic-chart').css('height', chartsheight-122);

    // Counter Number
    $('.count').each(function() {
        $(this).prop('Counter', 0).animate({
            Counter: $(this).text()
        }, {
            duration: 3000,
            easing: 'swing',
            step(now) {
                $(this).text(Math.ceil(now));
            }
        });
    });

    // Menu Trigger
    $('#menuToggle').on('click', event => {
        const windowWidth = $(window).width();
        if (windowWidth < 1010) {
            $('body').removeClass('open');
            if (windowWidth < 760) {
                $('#left-panel').slideToggle();
            } else {
                $('#left-panel').toggleClass('open-menu');
            }
        } else {
            $('body').toggleClass('open');
            $('#left-panel').removeClass('open-menu');
        }
    });

    $('.menu-item-has-children.dropdown').each(function() {
        $(this).on('click', function() {
            const $temp_text = $(this).children('.dropdown-toggle').html();
            $(this).children('.sub-menu').prepend(`<li class="subtitle">${$temp_text}</li>`);
        });
    });

    // Load Resize
    $(window).on('load resize', event => {
        const windowWidth = $(window).width();
        if (windowWidth < 1010) {
            $('body').addClass('small-device');
        } else {
            $('body').removeClass('small-device');
        }
    });
});
