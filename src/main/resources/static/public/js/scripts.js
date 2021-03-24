const NAVBAR_HEIGHT = 100;              // pixel
const ANIMATION_TIME = 500;             // milliseconds
const RESIZE_DEBOUNCING_TIMER = 100;    // milliseconds

$(document).ready(function() {

    // Put the footer navbar at the end if there is space under it
    const setFixedBottom = function() {
        const main = document.getElementById('mainVerticalAdjFooter');
        const footer = document.getElementById('verticalAdjFooter');

        if (main !== null && footer !== null) {
            const clientHeight = window.innerHeight || document.documentElement.clientHeight;
            const availableSpace = clientHeight - main.offsetHeight - main.offsetTop - footer.offsetHeight;

            if (availableSpace < 0) {
                footer.classList.remove('fixed-bottom');
            } else {
                footer.classList.add('fixed-bottom');
            }
        }
    };
    setFixedBottom();
    // Debouncing, avoid too many calls in too little time
    var resizeDebouncingTimer;
    $(window).resize(function() {
        clearTimeout(resizeDebouncingTimer);
        resizeDebouncingTimer = setTimeout(setFixedBottom, RESIZE_DEBOUNCING_TIMER);
    });

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

$(document).ready(function(){
    $('[data-toggle="tooltip"]').tooltip({
        placement : 'top'
    });
});

function hide (elements) {
    elements = elements.length ? elements : [elements];
    for (var index = 0; index < elements.length; index++) {
        elements[index].style.display = 'none';
    }
}

function show (elements, specifiedDisplay) {
    elements = elements.length ? elements : [elements];
    for (var index = 0; index < elements.length; index++) {
        elements[index].style.display = specifiedDisplay || 'block';
    }
}

/*
function showModalJob(){
    const idJob = this.id.replace("btn_show_job_","");
    $.ajax({
        method:"GET",
        url:"/api/bag/job/" + idJob,
        success: function(){
           //$("#fila_" + idEncuesta).remove();
           //alert(idEncuesta + " quitado con exito");
        },
        error: function( response ) {
            //alert("error")
        },

    });

}
$(".botonBorrar").click(borrar);
*/

//$(document).ready(function(){
//    refreshInterestButtonClass();
//});
