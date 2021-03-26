//const NAVBAR_HEIGHT = 100;              // pixel
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

    // Tooltip position
    $('[data-toggle="tooltip"]').tooltip({
        placement : 'top'
    });




});

/*
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
*/

$(function() {
    function checkUsername(username)
    {
        let noError = 0;
        if(username.length === 0)
        {
            $('#inputUserName').removeClass('is-valid');
            $('#inputUserName').addClass('is-invalid');
            $('#invalid-feedback-UserName').html(document.texts.INVALID_EMPTY);
            noError = 1;
        }
        else
        {
            fetch('/api/existsByUsername/' + username).then(function(response) {
                response.json().then(function(data) {
                    if(data)
                    {
                        $('#inputUserName').removeClass('is-valid');
                        $('#inputUserName').addClass('is-invalid');
                        $('#invalid-feedback-UserName').html(document.texts.INVALID_USERNAME_ALREADY_EXISTS);
                        noError = 2;
                    }
                    else
                    {
                        $('#inputUserName').removeClass('is-invalid');
                        $('#inputUserName').addClass('is-valid');
                    }
                });
            });
        }
        return noError;
    }

    function checkEMail(email)
    {
        let noError = 0;
        if(email.length === 0)
        {
            $('#inputEMail').removeClass('is-valid');
            $('#inputEMail').addClass('is-invalid');
            $('#invalid-feedback-EMail').html(document.texts.INVALID_EMPTY);
            noError = 1;
        }
        else
        {
            fetch('/api/existsByEMail/' + email).then(function(response) {
                response.json().then(function(data) {
                    if(data)
                    {
                        $('#inputEMail').removeClass('is-valid');
                        $('#inputEMail').addClass('is-invalid');
                        $('#invalid-feedback-EMail').html(document.texts.INVALID_EMAIL_ALREADY_EXISTS);
                        noError = 2;
                    }
                    else
                    {
                        $('#inputEMail').removeClass('is-invalid');
                        $('#inputEMail').addClass('is-valid');
                    }
                });
            });
        }
        return noError;
    }

    $('#inputBirthDate').datepicker({
        format: 'dd/mm/yyyy'
    });

    $('#inputCountry').selectpicker({liveSearch:true});
    $('#inputState').selectpicker({liveSearch:true});
    $('#inputCity').selectpicker({liveSearch:true});

    $('#inputUserName').focusout(function() {
        checkUsername($(this).val());
    });

    $('#inputEMail').focusout(function() {
        checkEMail($(this).val());
    });

    $('#SubmitCRUDProfileUser').click(function() {
        const username = $('#inputUserName').val();
        const email = $('#inputEMail').val();
        let noError = 0;
        noError |= checkUsername(username);
        noError |= checkEMail(email);
        if($('#inputPassword').val().length === 0)
        {
            $('#inputPassword').addClass('is-invalid');
            $('#invalid-feedback-password').html(document.texts.INVALID_EMPTY);
            noError = 3;
        }
        else
        {
            $('#inputPassword').removeClass('is-invalid');
            if($('#inputPassword').val() !== $('#inputPassword2').val())
            {
                noError = 4;
                $('#inputPassword2').addClass('is-invalid');
                $('#invalid-feedback-').html(document.texts.INVALID_PASSWORD_NOT_EQUAL);
            }
            else
            {
                $('#inputPassword2').removeClass('is-invalid');
                $('.modal-dialog').hide();
                $('#CRUDProfileUserData').show();
            }
        }

        console.log('fin ' + noError);
    });

    $('#PrevCRUDProfileUserData').click(function() {
        $('.modal-dialog').hide();
        $('#CRUDProfileUser').show();
    });

    $('#SubmitCRUDProfileUserData').click(function() {
        let noError = 0;

        console.log('inicio ' + noError);

        if($('#inputName').val().length === 0)
        {
            $('#inputName').removeClass('is-valid');
            $('#inputName').addClass('is-invalid');
            noError = 1;
        }
        else
        {
            $('#inputName').removeClass('is-invalid');
            $('#inputName').addClass('is-valid');
        }
        if($('#inputLastName').val().length === 0)
        {
            $('#inputName').removeClass('is-valid');
            $('#inputLastName').addClass('is-invalid');
            noError = 2;
        }
        else
        {
            $('#inputLastName').removeClass('is-invalid');
            $('#inputLastName').addClass('is-valid');
        }
        if(moment($('#inputBirthDate').val(), 'DD/MM/YYYY', true).isValid())
        {
            $('#inputBirthDate').removeClass('is-invalid');
            $('#inputBirthDate').addClass('is-valid');
        }
        else
        {
            $('#inputBirthDate').removeClass('is-valid');
            $('#inputBirthDate').addClass('is-invalid');
            noError = 3;
        }

        console.log('fin ' + noError);

        if(noError === 0)
        {
            $('.modal-dialog').hide();
            $('#CRUDProfileCity').show();
        }
    });

    $('#prevCRUDProfileCity').click(function() {
        $('.modal-dialog').hide();
        $('#CRUDProfileUserData').show();
    });

    $('#submitCRUDProfileCity').click(function() {
        $('.modal-dialog').hide();
        $('#CRUDProfileStudy').show();
    });

    $('#prevCRUDProfileStudy').click(function() {
        $('.modal-dialog').hide();
        $('#CRUDProfileCity').show();
    });

    $('#submitCRUDProfileStudy').click(function() {
        if($('#inputStudy').val().length === 0)
        {
            $('#inputStudy').removeClass('is-valid');
            $('#inputStudy').addClass('is-invalid');
        }
        else
        {
            $('#inputStudy').removeClass('is-invalid');
            $('#inputStudy').addClass('is-valid');
            $('.modal-dialog').hide();
            $('#CRUDProfileSkill').show();
        }
    });

    $('#prevCRUDProfileSkill').click(function() {
        $('.modal-dialog').hide();
        $('#CRUDProfileStudy').show();
    });

    $('#submitCRUDProfileSkill').click(function() {
        if($('#inputSkill').val().length === 0)
        {
            $('#inputSkill').removeClass('is-valid');
            $('#inputSkill').addClass('is-invalid');
        }
        else
        {
            $('#inputSkill').removeClass('is-invalid');
            $('#inputSkill').addClass('is-valid');
            $('.modal-dialog').hide();
            $('#CRUDProfileLastModal').show();
        }
    });

    $('#prevCRUDProfileLastModal').click(function() {
        $('.modal-dialog').hide();
        $('#CRUDProfileSkill').show();
    });



});



function createDeleteInterest(e) {


        const ids = e.id.replace("interest_","");
        var idJob = ids.split("_")[0];
        var idUser = ids.split("_")[1];
        const urlInterested = "/api/bag/user/"+idUser+"/job/"+idJob+"/interested";


        fetch(urlInterested).then(function(response) {

            if (response.ok) {
                            fetch(urlInterested, {method: 'DELETE'}).then( function(response) {
                               if (response.ok) {
                                    var ee = document.getElementById(e.id);
                                   $(ee).removeClass("bi-hand-thumbs-up-fill");
                                   $(ee).addClass("bi-hand-thumbs-up");



                               } else {
                                    var ee = document.getElementById(e.id);
                                    $(ee).removeClass("bi-hand-thumbs-up");
                                   $(ee).addClass("bi-hand-thumbs-up-fill");
                               }
                            });

            } else {
                fetch(urlInterested, {method: 'POST'}).then( function(response) {
                   if (response.ok) {
                        var ee = document.getElementById(e.id);
                       $(ee).removeClass("bi-hand-thumbs-up");
                       $(ee).addClass("bi-hand-thumbs-up-fill");

                   } else {
                        var ee = document.getElementById(e.id);
                       $(ee).removeClass("bi-hand-thumbs-up-fill");
                       $(ee).addClass("bi-hand-thumbs-up");
                   }
                });
            }
        });

   }



function showModalJob(e) {

  const ids = e.id.replace("btn_show_job_","");
  var idJob = ids.split("_")[0];
  var idUser = ids.split("_")[1];
  const urlJob = "/api/bag/job/" + idJob;



    fetch(urlJob).then( function(response) {
        if (response.ok) {
            response.json().then(function(data) {
   	   			if(data) {
   	   			    var jobTitle = document.getElementById("modalJobTitle");
                    jobTitle.innerText = data.title;
   	   			    var jobDescription = document.getElementById("modalJobDescription");
                    jobDescription.innerText = data.description;

                    var jobFullTime = document.getElementById("modalJobFullTime");
                    jobFullTime.innerText = data.fullTime ? "Tiempo Completo" : "";
                    var jobPartTime = document.getElementById("modalJobPartTime");
                    jobPartTime.innerText = data.partTime ? "Medio Tiempo" : "";
                    var jobRemote = document.getElementById("modalJobRemote");
                    jobRemote.innerText = data.homeWork ? "Trabajo remoto" : "";
                    var jobIndependent = document.getElementById("modalJobIndependent");
                    jobIndependent.innerText = data.independent ? "Independiente" : "";
                    var jobDependent = document.getElementById("modalJobDependent");
                    jobDependent.innerText = data.dependent ? "Relacion de dependencia" : "";

                    var jobExpirationDate = document.getElementById("modalExpirationDate");
                    jobExpirationDate.innerText =  data.expiration;




                    $('#modalJob').modal('show');
   	   			} else {
        		}
            });

           // var ee = document.getElementById(e.id);
            //$(ee).removeClass("bi-hand-thumbs-up-fill");
            //alert(e.id);

             //$(ee).modal('show')
        } else {
        }

    });
}
