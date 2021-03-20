const NAVBAR_HEIGHT = 100;  // pixel
const ANIMATION_TIME = 500;  // milliseconds

$(document).ready(function() {

    // Put the footer navbar at the end if there is space under it
    const setFixedBottom = function() {
        const main = document.getElementById('main');
        const footer = document.getElementById('footer');

        if (main !== null && footer !== null) {
            const clientHeight = window.innerHeight || document.documentElement.clientHeight;
            const availableSpace = clientHeight - footer.offsetHeight;

            if (availableSpace - footer.offsetTop > 0) {
                footer.classList.add('fixed-bottom');
            } else if (availableSpace - main.offsetTop - main.offsetHeight < 0) {
                footer.classList.remove('fixed-bottom');
            }
        }
    };
    setFixedBottom();
    window.onresize = setFixedBottom;

    // Smooth displacement with fragments
    $('a[href^="#"]').click(function() {
        var target = $(this.hash);
        if (target.length === 0) {
            target = $(`a[name="${this.hash.substr(1)}"]`);
        }
        if (target.length === 0) {
            target = $('html');
        }
        $('html, body').animate({ scrollTop: target.offset().top - NAVBAR_HEIGHT}, ANIMATION_TIME);
        return false;
    });

    // Hide menu on click
    $('#headerNavbar').find('a').on('click', function() {
        if ($('#navbar-toggler-button').css('display') !== 'none'){
            $('#headerNavbar').collapse('toggle');
        }
    });

    // Focusing on the first field in the modal
    $('#logIn').on('shown.bs.modal', function () {
        $('#logInInputEmail').trigger('focus');
    });

    // Needs for validation form (works without js :)
    Array.prototype.filter.call($('.needs-validation'), function(form) {
        form.setAttribute('novalidate', null);
        form.addEventListener('submit', function(event) {
            if (form.checkValidity() === false) {
                event.preventDefault();
                event.stopPropagation();
            }
            form.classList.add('was-validated');
        }, false);
    });
});
