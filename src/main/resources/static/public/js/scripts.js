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

                    var jobExpiration = document.getElementById("modalExpirationDate");
                    jobExpiration.innerText =  data.expiration;

                    var jobInterested = document.getElementById("modalInterested");
                    jobInterested.innerText = data.interestedUsersCount > 0 ?  data.interestedUsersCount +
                    (data.interestedUsersCount > 1 ? " Personas interesadas" : " Persona Interesada")
                    : "";

                    var jobNominees = document.getElementById("modalNominees");
                    jobNominees.innerText = data.applyUsersCount > 0 ?  data.applyUsersCount +
                     (data.applyUsersCount > 1 ? " Personas postuladas" : " Persona postulada")
                     : "";

                    $('#modalJob').modal('show');
   	   			}
            });
        }
    });
}


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

                                    var objId = "interest_row_"+idJob+"_"+idUser;
                                    var element = document.getElementById(objId);
                                    element.remove();


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


                     var urlJob = "/api/bag/job/" + idJob;

                        fetch(urlJob).then( function(response) {
                            if (response.ok) {
                                response.json().then(function(data) {
                                    if(data) {

                                          var t = document.getElementById('interestsTable'),
                                              tr = document.createElement('tr');

                                              tr.id= "interest_row_" + idJob  + '_'+ idUser;

                                              tr.innerHTML = "<td style=\"width: auto;\" class=\"align-middle\">"
                                                +"<p class=\"contact_content align-middle\" text=\"\">"+data.title+"</p>"
                                                +"</td>"
                                                +"<td style=\"width: 40px;\" class=\"align-middle\">"
                                                +"<i id=\"btn_show_job_"+idJob+"_"+idUser+"\""
                                                +"class=\"bi bi-eye\""
                                                +"data-toggle=\"tooltip\""
                                                +"data-original-title=\"Ver trabajo\""
                                                +"onclick=\"showModalJob(this)\">"
                                                +"</i>"
                                                +"</td>"
                                                +"<td style=\"width: 40px;\" class=\"align-middle\">"
                                                +"<i id=\"btn_delete_interest_"+idJob+"_"+idUser+"\" "
                                                +"class=\"bi bi-trash\" data-toggle=\"tooltip\" data-original-title=\"Eliminar\" onclick=\"deleteInterest(this)\"></i>"
                                                +"</td>";
                                          t.appendChild(tr);



                                    }
                                });
                            }
                        });



                   } else {
                        var ee = document.getElementById(e.id);
                       $(ee).removeClass("bi-hand-thumbs-up-fill");
                       $(ee).addClass("bi-hand-thumbs-up");
                   }
                });
            }
        });

}

function deleteInterest(e) {

        const ids = e.id.replace("btn_delete_interest_","");
        var idJob = ids.split("_")[0];
        var idUser = ids.split("_")[1];
        const urlInterested = "/api/bag/user/"+idUser+"/job/"+idJob+"/interested";


        fetch(urlInterested).then(function(response) {

            if (response.ok) {
                            fetch(urlInterested, {method: 'DELETE'}).then( function(response) {
                               if (response.ok) {

                                    var objId = "interest_row_"+idJob+"_"+idUser;
                                    var element = document.getElementById(objId);
                                    element.remove();

                                     var idInterestButton =  "interest_"+idJob+"_"+idUser;
                                     var interestButton = document.getElementById(idInterestButton);
                                     if (typeof interestButton !== 'undefined' && interestButton !== null) {
                                        $(interestButton).removeClass("bi-hand-thumbs-up-fill");
                                        $(interestButton).addClass("bi-hand-thumbs-up");
                                    }
                               }
                            });
            }
        });

}


function showModalApply(e) {

  const ids = e.id.replace("btn_show_apply_","");
  var idJob = ids.split("_")[0];
  var idUser = ids.split("_")[1];
  const urlJob = "/api/bag/job/" + idJob;

    fetch(urlJob).then(function(response) {
        if (response.ok) {
            response.json().then(function(data) {
   	   			if(data) {
   	   			    var jobTitle = document.getElementById("modalApplyTitle");
                    jobTitle.innerText = data.title;
   	   			    var jobDescription = document.getElementById("modalApplyDescription");
                    jobDescription.innerText = data.description;

                    var jobFullTime = document.getElementById("modalApplyFullTime");
                    jobFullTime.innerText = data.fullTime ? "Tiempo Completo" : "";
                    var jobPartTime = document.getElementById("modalApplyPartTime");
                    jobPartTime.innerText = data.partTime ? "Medio Tiempo" : "";
                    var jobRemote = document.getElementById("modalApplyRemote");
                    jobRemote.innerText = data.homeWork ? "Trabajo remoto" : "";
                    var jobIndependent = document.getElementById("modalApplyIndependent");
                    jobIndependent.innerText = data.independent ? "Independiente" : "";
                    var jobDependent = document.getElementById("modalApplyDependent");
                    jobDependent.innerText = data.dependent ? "Relación de dependencia" : "";

                    var jobExpiration = document.getElementById("modalApplyExpirationDate");
                    jobExpiration.innerText =  data.expiration;

                    var jobInterested = document.getElementById("modalApplyInterested");
                    jobInterested.innerText = data.interestedUsersCount > 0 ?  data.interestedUsersCount +
                    (data.interestedUsersCount > 1 ? " Personas interesadas" : " Persona Interesada")
                    : "";

                    var jobNominees = document.getElementById("modalApplyNominees");
                    jobNominees.innerText = data.applyUsersCount > 0 ?  data.applyUsersCount +
                     (data.applyUsersCount > 1 ? " Personas postuladas" : " Persona postulada")
                     : "";

                    $('#modalApply').modal('show');
   	   			}
            });
        }
    });
}


function createDeleteApply(e) {

        const ids = e.id.replace("apply_","");
        var idJob = ids.split("_")[0];
        var idUser = ids.split("_")[1];
        const urlApply = "/api/bag/user/"+idUser+"/job/"+idJob+"/apply";


        fetch(urlApply).then(function(response) {

            if (response.ok) {
                            fetch(urlApply, {method: 'DELETE'}).then( function(response) {
                               if (response.ok) {
                                    var ee = document.getElementById(e.id);
                                   $(ee).removeClass("bi-telephone-plus-fill");
                                   $(ee).addClass("bi-telephone-plus");

                                    var objId = "apply_row_"+idJob+"_"+idUser;
                                    var element = document.getElementById(objId);
                                    element.remove();


                               } else {
                                    var ee = document.getElementById(e.id);
                                    $(ee).removeClass("bi-telephone-plus");
                                   $(ee).addClass("bi-telephone-plus-fill");
                               }
                            });

            } else {
                fetch(urlApply, {method: 'POST'}).then( function(response) {
                   if (response.ok) {
                        var ee = document.getElementById(e.id);
                       $(ee).removeClass("bi-telephone-plus");
                       $(ee).addClass("bi-telephone-plus-fill");


                     var urlJob = "/api/bag/job/" + idJob;

                        fetch(urlJob).then( function(response) {
                            if (response.ok) {
                                response.json().then(function(data) {
                                    if(data) {

                                          var t = document.getElementById('applyTable'),
                                              tr = document.createElement('tr');

                                              tr.id= "apply_row_" + idJob  + '_'+ idUser;

                                              tr.innerHTML = "<td style=\"width: auto;\" class=\"align-middle\">"
                                                +"<p class=\"contact_content align-middle\" text=\"\">"+data.title+"</p>"
                                                +"</td>"
                                                +"<td style=\"width: 40px;\" class=\"align-middle\">"
                                                +"<i id=\"btn_show_job_"+idJob+"_"+idUser+"\""
                                                +"class=\"bi bi-eye\""
                                                +"data-toggle=\"tooltip\""
                                                +"data-original-title=\"Ver trabajo\""
                                                +"onclick=\"showModalApply(this)\">"
                                                +"</i>"
                                                +"</td>"
                                                +"<td style=\"width: 40px;\" class=\"align-middle\">"
                                                +"<i id=\"btn_delete_apply_"+idJob+"_"+idUser+"\" "
                                                +"class=\"bi bi-trash\" data-toggle=\"tooltip\" data-original-title=\"Eliminar\" onclick=\"deleteApply(this)\"></i>"
                                                +"</td>";
                                          t.appendChild(tr);



                                    }
                                });
                            }
                        });



                   } else {
                        var ee = document.getElementById(e.id);
                       $(ee).removeClass("bi-telephone-plus-fill");
                       $(ee).addClass("bi-telephone-plus");
                   }
                });
            }
        });

}

function deleteApply(e) {

        const ids = e.id.replace("btn_delete_apply_","");
        var idJob = ids.split("_")[0];
        var idUser = ids.split("_")[1];
        const urlApply = "/api/bag/user/"+idUser+"/job/"+idJob+"/apply";


        fetch(urlApply).then(function(response) {

            if (response.ok) {
                            fetch(urlApply, {method: 'DELETE'}).then( function(response) {
                               if (response.ok) {

                                    var objId = "apply_row_"+idJob+"_"+idUser;
                                    var element = document.getElementById(objId);
                                    element.remove();

                                     var idApplyButton =  "apply_"+idJob+"_"+idUser;
                                     var applyButton = document.getElementById(idApplyButton);
                                     if (typeof applyButton !== 'undefined' && applyButton !== null) {
                                        $(applyButton).removeClass("bi-telephone-plus-fill");
                                        $(applyButton).addClass("bi-telephone-plus");
                                    }
                               }
                            });
            }
        });

}





