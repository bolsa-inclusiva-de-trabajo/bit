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

   		$('#inputExpiration').datepicker({
   		    format: "dd/mm/yyyy"
   		});
   		$("#SubmitCRUDJob").click(function() {
   			let noError=0;
   			noError|=checkEmpty('#inputTitleJob');
   			noError|=checkEmpty('#inputDescription');
   			if(moment($("#inputExpiration").val(), "DD/MM/YYYY", true).isValid())
       		{
        		$("#inputExpiration").removeClass("is-invalid");
        		$("#inputExpiration").addClass("is-valid");
       		}
        	else
       		{
        		$("#inputExpiration").removeClass("is-valid");
        		$("#inputExpiration").addClass("is-invalid");
        		noError=3;
       		}
   			if(noError===0)
        	{
        		$('.modal-dialog').hide();
      			$('#CRUDJobLastModal').show();
        	}

   		});
      $("#submitCRUDJobLastModal").click(function() {
        CRUDJobSetCheckbox('JobFullTime','CRUDJobForm','fullTime');
        CRUDJobSetCheckbox('JobPartTime','CRUDJobForm','partTime');
        CRUDJobSetCheckbox('JobHomeWork','CRUDJobForm','homeWork');
        CRUDJobSetCheckbox('JobDependent','CRUDJobForm','dependent');
        CRUDJobSetCheckbox('JobIndependent','CRUDJobForm','independent');
        $("#CRUDJobForm").submit();
      });
      $("#prevCRUDJobLastModal").click(function() {
        $('.modal-dialog').hide();
        $('#CRUDJob').show();
      });
	  $('#inputBirthDateEditUser').datepicker({
   		    format: "dd/mm/yyyy"
   		});
	$('#inputCountryEditUser').selectpicker({liveSearch:true});
	$('#inputStateEditUser').selectpicker({liveSearch:true});
	$('#inputCityEditUser').selectpicker({liveSearch:true});
//	$('#inputEMailEditUser').focusout(function() {
//		checkEMail(this);
//	});
	$("#nextEditProfileUser").click(function() {
		let noError=0;
		noError|=checkEmpty('#inputNameEditUser');
		noError|=checkEmpty('#inputLastNameEditUser');


		if(moment($("#inputBirthDateEditUser").val(), "DD/MM/YYYY", true).isValid())
		{
			$("#inputBirthDateEditUser").removeClass("is-invalid");
			$("#inputBirthDateEditUser").addClass("is-valid");
		}
		else
		{
			$("#inputBirthDateEditUser").removeClass("is-valid");
			$("#inputBirthDateEditUser").addClass("is-invalid");
			noError=3;
		}
//		noError|=checkEMail('#inputEMailEditUser');
		if(noError==0)
		{
			$('.modal-dialog').hide();
	    	$('#editProfileCity').show();
		}
	  });
		$("#prevEditProfileCity").click(function() {
        	$('.modal-dialog').hide();
        	$('#EditProfileUser').show();
  		});
		$("#nextEditProfileCity").click(function() {
        	$('.modal-dialog').hide();
        	$('#editProfileStudy').show();
  		});
		$("#prevEditProfileStudy").click(function() {
        	$('.modal-dialog').hide();
        	$('#EditProfileCity').show();
  		});
		$("#nextEditProfileStudy").click(function() {
			if(checkEmpty("#inputStudyEditUser")==0)
			{
				$('.modal-dialog').hide();
        		$('#editProfileSkill').show();
			}
  		});
		$("#prevEditProfileSkill").click(function() {
        	$('.modal-dialog').hide();
        	$('#EditProfileStudy').show();
  		});
		$("#nextEditProfileSkill").click(function() {
			if(checkEmpty("#inputSkillEditUser")==0)
			{
	        	$('.modal-dialog').hide();
	        	$('#editProfileAvailability').show();
			}
  		});
		$("#prevEditProfileAvailability").click(function() {
        	$('.modal-dialog').hide();
        	$('#EditProfileSkill').show();
  		});
		$("#nextEditProfileAvailability").click(function() {
			CRUDJobSetCheckbox('inputFullTimeEditUser','formEditProfileUser','fullTime');
	        CRUDJobSetCheckbox('inputPartTimeEditUser','formEditProfileUser','partTime');
	        CRUDJobSetCheckbox('inputHomeWorkEditUser','formEditProfileUser','homeWork');
	        CRUDJobSetCheckbox('inputapplyForJobEditUser','formEditProfileUser','applyForJob');
			$("#formEditProfileUser").submit();
  		});
		$("#savePassword").click(function() {
			if(checkEmpty('#inputPasswordEditUser'))
			{
				$("#invalid-feedback-password").html("No puede dejar el campo vacio.");
			}
			if(checkEmpty('#inputPassword2EditUser'))
			{
				$("#invalid-feedback-password2").html("No puede dejar el campo vacio.");
			}
			else if($("#inputPasswordEditUser").val()!=$("#inputPassword2EditUser").val())
			{
				$("#inputPassword2EditUser").removeClass("is-valid");
				$("#inputPassword2EditUser").addClass("is-invalid");
				$("#invalid-feedback-password2").html("Las contraseñas deben ser iguales");
			}
			else{
				$("#formEditPassword").submit();
			}
  		});

});

$(document).keyup(function (e) {
    if ($(".btnSearch:focus") && (e.keyCode === 13)) {
       bagFilterText();
    }
 });

$(document).keyup(function (e) {
    if ($(".btnSearchUser:focus") && (e.keyCode === 13)) {
       bagApplicantsFilterText();
    }
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

 function setValid(id, isValid) {
        const classes = isValid ? ['is-invalid', 'is-valid'] : ['is-valid', 'is-invalid'];
        $(id).removeClass('is-checking').removeClass(classes[0]).addClass(classes[1]);
    }

    function setChecking(id) {
        $(id).removeClass('is-valid').removeClass('is-invalid').addClass('is-checking');
    }


function bagFilterText(e) {
      setChecking(e.id);
      if ($('#txtSearch').val() == "") {
            setValid("#txtSearch", false);
      } else {
          var urlFilter = "/api/bag/job/text/" + $('#txtSearch').val();
          fetch(urlFilter).then(function(response) {
                if (response.ok) {
                     setValid("#txtSearch", true);
                     window.location.replace("/bagoffers/text/"+$('#txtSearch').val());
                } else {
                    setValid("#txtSearch", false);
                }
          });
      }
}

function closePanelFilter() {
    $('#panelFilter').hide();
     window.location.replace("/bagoffers");
}

function bagFilterCity(e) {
    window.location.replace("/bagoffers/city/true");
}

function bagFilterFullTime(e) {
    window.location.replace("/bagoffers/fullparthome/full");
}
function bagFilterPartTime(e) {
    window.location.replace("/bagoffers/fullparthome/part");
}
function bagFilterRemote(e) {
    window.location.replace("/bagoffers/fullparthome/home");
}

function bagFilterMyOffers(e) {
    window.location.replace("/bagoffers/my_offers/true");
}

function checkEmpty(input)
{
  let empty=0;
  console.log($(input));
  if($(input).val().length === 0)
  {
    $(input).removeClass("is-valid");
    $(input).addClass("is-invalid");
    empty=1;
  }
  else
{
    $(input).removeClass("is-invalid");
    $(input).addClass("is-valid");
}
  return empty;
}

function CRUDJobSetCheckbox(id,form,name) {
    // https://developer.mozilla.org/en-US/docs/Web/HTML/Element/Input/checkbox#Note_2
    const checkbox = document.getElementById(id);
    if (!checkbox.checked) {
        const input = document.createElement('input');
        input.type = 'hidden';
        input.name = name;
        input.value = false;
        document.getElementById(form).appendChild(input);
        return false;
    }
    return true;
}
function showModalEditJob(e) {
  const ids = e.id.replace("edit_job_","");
  var idJob = ids.split("_")[0];
  var idUser = ids.split("_")[1];
  const urlJob = "/api/bag/job/" + idJob;
  fetch(urlJob).then( function(response) {
      if (response.ok) {
          response.json().then(function(data) {
          if(data) {
              clearModalJob();
              $("#TitleModalCRUDJob").text("Editar oferta laboral");
              $("#submitCRUDJobLastModal").text("Editar Oferta");
              $("#inputTitleJob").val(data.title);
              $("#inputDescription").val(data.description);
              $("#inputExpiration").val(data.expiration);
              if(data.fullTime)
              {
                $("#JobFullTime").attr('checked', true);
              }
              if (data.partTime)
              {
                $("#JobPartTime").attr('checked', true);
              }
              if (data.homeWork)
              {
                $("#JobHomeWork").attr('checked', true);
              }
              if (data.dependent)
              {
                $("#JobDependent").attr('checked', true);
              }
              if (data.independent)
              {
                $("#JobIndependent").attr('checked', true);
              }
              $("#inputIdJob").val(data.id);
              $('#ModalCRUDJob').modal('show');
          }
          });
      }
  });
}
function clearModalJob() {
  $("#inputTitleJob").val("");
  $("#inputDescription").val("");
  $("#inputExpiration").val("");
  $("#inputTitleJob").removeClass("is-valid");
  $("#inputTitleJob").removeClass("is-invalid");
  $("#inputDescription").removeClass("is-valid");
  $("#inputDescription").removeClass("is-invalid");
  $("#inputExpiration").removeClass("is-valid");
  $("#inputExpiration").removeClass("is-invalid");
  $("#JobFullTime").attr('checked', false);
  $("#JobPartTime").attr('checked', false);
  $("#JobHomeWork").attr('checked', false);
  $("#JobDependent").attr('checked', false);
}
function clearModalEditPassword() {
  $("#inputPasswordEditUser").val("");
  $("#inputPassword2EditUser").val("");
  $("#inputPasswordEditUser").removeClass("is-valid");
  $("#inputPasswordEditUser").removeClass("is-invalid");
  $("#inputPassword2EditUser").removeClass("is-valid");
  $("#inputPassword2EditUser").removeClass("is-invalid");

}
function showModalCreateJob() {
  clearModalJob();
  $("#TitleModalCRUDJob").text("Alta de oferta laboral");
  $("#submitCRUDJobLastModal").text("Guardar");
  $("#inputIdJob").val(0);
  $('#ModalCRUDJob').modal('show');
}
function deleteJob(e) {
  const ids = e.id.replace("delete_job_","");
  var idJob = ids.split("_")[0];
  var idUser = ids.split("_")[1];
  const urlJob = "/api/job/" + idJob;
  fetch(urlJob,{
          method: 'DELETE',
          headers: {
            'Content-Type': 'application/json'
          },
          body: null}
      ).then( function(response) {
      if (response.ok) {
          response.json().then(function(data) {
          $("#job_row_"+data).hide();
          });
      }
  });
}
function checkEMail(elementEmail)
   	    {
   			let noError=0;
			let email = $(elementEmail).val();

   			if(email.length==0)
			{
   				$(elementEmail).removeClass("is-valid");
				$(elementEmail).addClass("is-invalid");
				$("#invalid-feedback").html("No puede dejar el campo vacio.");
				noError=1;
			}
   			else
   			{
   				fetch('/api/existsByEMail/' + email).then(function(response) {
   	   				response.json().then(function(data) {
   	   					if(data)
   	 					{
   	   						$(elementEmail).removeClass("is-valid");
   	   						$(elementEmail).addClass("is-invalid");
   	   						$("#invalid-feedback").html("El correo electrónico ya existe");
   	   						noError=2;
   	   					}
   	   					else
 	   					{
   	   						$(elementEmail).removeClass("is-invalid");
	   						$(elementEmail).addClass("is-valid");
   	   					}
   	   				});
   				});
  			}
   			return noError;
   	    }
function edit_user_profile(uid)
{
	 const urlUser = "/api/user/" + uid;
  	fetch(urlUser).then( function(response) {
		if (response.ok) {
			response.json().then(function(data) {
				console.log(data)
			  $("#inputNameEditUser").val(data.firstName);
		      $("#inputLastNameEditUser").val(data.lastName);
		      $("#inputBirthDateEditUser").val(data.birthDate);
//		      $("#inputEMailEditUser").val(data.email);
			  $("#inputCityEditUser").val(data.city.id)
 			  $("#inputCityEditUser").selectpicker('refresh');
			  $("#inputStudyEditUser").text(data.education);
			  $("#inputSkillEditUser").text(data.skills);
			  $("#idEditUser").val(data.id);
		      data.fullTime ? $("#inputFullTimeEditUser").attr('checked', true) : $("#inputFullTimeEditUser").attr('checked', false);
		      data.partTime ? $("#inputPartTimeEditUser").attr('checked', true) : $("#inputPartTimeEditUser").attr('checked', false);
		      data.homeWork ? $("#inputHomeWorkEditUser").attr('checked', true) : $("#inputHomeWorkEditUser").attr('checked', false);
			  data.applyForJob ? $("#inputapplyForJobEditUser").attr('checked', true) : $("#inputapplyForJobEditUser").attr('checked', false);

			});
		}
	});
	$('.modal-dialog').hide();
	$('#EditProfileUser').show();
	$("#ModalEditProfileUser").modal('show');

}
function edit_user_password(uid)
{
	clearModalEditPassword();
	$("#ModalEditPasswordUser").modal('show');
}
function enable_user_profile(uid)
{

	$("#disabledEditUser").val(false);
	$("#formDisabledProfile").submit();
}
function disable_user_profile(uid)
{

	$("#disabledEditUser").val(true);
	$("#formDisabledProfile").submit();
}

function createDeleteContact(e) {

        const ids = e.id.replace("contact_","");
        var idUsr = ids.split("_")[0];
        var idUser = ids.split("_")[1];
        ///api/bag/user/{uid1}/user/{uid2}/contact
        const urlContact = "/api/bag/user/"+idUser+"/user/"+idUsr+"/contact";

        fetch(urlContact).then(function(response) {

            if (response.ok) {
                            fetch(urlContact, {method: 'DELETE'}).then( function(response) {
                               if (response.ok) {
                                    var ee = document.getElementById(e.id);
                                   $(ee).removeClass("bi-telephone-plus-fill");
                                   $(ee).addClass("bi-telephone-plus");

                                    var objId = "contact_row_"+idUsr+"_"+idUser;
                                    var element = document.getElementById(objId);
                                    element.remove();


                               } else {
                                    var ee = document.getElementById(e.id);
                                    $(ee).removeClass("bi-telephone-plus");
                                   $(ee).addClass("bi-telephone-plus-fill");
                               }
                            });

            } else {
                fetch(urlContact, {method: 'POST'}).then( function(response) {
                   if (response.ok) {
                        var ee = document.getElementById(e.id);
                       $(ee).removeClass("bi-telephone-plus");
                       $(ee).addClass("bi-telephone-plus-fill");


                     var urlUser = "/api/bag/user/" + idUsr;

                        fetch(urlUser).then( function(response) {
                            if (response.ok) {
                                response.json().then(function(data) {
                                    if(data) {

                                          var t = document.getElementById('contactTable'),
                                              tr = document.createElement('tr');

                                              tr.id= "contact_row_" + idUsr  + '_'+ idUser;

                                              tr.innerHTML = "<td style=\"width: auto;\" class=\"align-middle\">"
                                                +"<p class=\"contact_content align-middle\" text=\"\">"+data.username+"</p>"
                                                +"</td>"
                                                +"<td style=\"width: 40px;\" class=\"align-middle\">"
                                                +"<i id=\"btn_show_contact_"+idUsr+"_"+idUser+"\""
                                                +"class=\"bi bi-eye\""
                                                +"data-toggle=\"tooltip\""
                                                +"data-original-title=\"Ver contacto\""
                                                +"onclick=\"showModalContact(this)\">"
                                                +"</i>"
                                                +"</td>"
                                                +"<td style=\"width: 40px;\" class=\"align-middle\">"
                                                +"<i id=\"btn_delete_contact_"+idUsr+"_"+idUser+"\" "
                                                +"class=\"bi bi-trash\" data-toggle=\"tooltip\" data-original-title=\"Eliminar\" onclick=\"deleteContact(this)\"></i>"
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


function showModalContact(e) {

  const ids = e.id.replace("btn_show_contact_","");
  var idUsr = ids.split("_")[0];
  var idUser = ids.split("_")[1];
  const urlUser = "/api/bag/user/" + idUsr;

    fetch(urlUser).then( function(response) {
        if (response.ok) {
            response.json().then(function(data) {
   	   			if(data) {
   	   			    var contactTitle = document.getElementById("modalContactTitle");
                    contactTitle.innerText = data.username;

   	   			    var userSkills = document.getElementById("modalContactSkillsText");
                    userSkills.innerText = data.skills;
   	   			    var userStudies = document.getElementById("modalContactStudiesText");
                    userStudies.innerText = data.education;

                    var userFullTime = document.getElementById("modalContactFullTime");
                    userFullTime.innerText = data.fullTime ? "Tiempo Completo" : "";
                    var userPartTime = document.getElementById("modalContactPartTime");
                    userPartTime.innerText = data.partTime ? "Medio Tiempo" : "";
                    var userRemote = document.getElementById("modalContactRemote");
                    userRemote.innerText = data.homeWork ? "Trabajo remoto" : "";

                    var userContacted = document.getElementById("modalContacted");
                    userContacted.innerText = data.contactsCount > 0 ?  data.contactsCount +
                    (data.contactsCount > 1 ? " Contactaron a esta persona" : " Contacto a esta persona")
                    : "";

                    var userNominees = document.getElementById("modalNominees");
                    userNominees.innerText = data.applyJobsCount > 0 ?  data.applyJobsCount +
                     (data.applyJobsCount > 1 ? " Postulaciones" : " postulación")
                     : "";

                    $('#modalContact').modal('show');
   	   			}
            });
        }
    });
}


function deleteContact(e) {

        const ids = e.id.replace("btn_delete_contact_","");
        var idUsr = ids.split("_")[0];
        var idUser = ids.split("_")[1];
        const urlContact = "/api/bag/user/"+idUser+"/user/"+idUsr+"/contact";


        fetch(urlContact).then(function(response) {

            if (response.ok) {
                            fetch(urlContact, {method: 'DELETE'}).then( function(response) {
                               if (response.ok) {

                                    var objId = "contact_row_"+idUsr+"_"+idUser;
                                    var element = document.getElementById(objId);
                                    element.remove();

                                     var idContactButton =  "contact_"+idUsr+"_"+idUser;
                                     var contactButton = document.getElementById(idContactButton);
                                     if (typeof contactButton !== 'undefined' && contactButton !== null) {
                                        $(contactButton).removeClass("bi-hand-thumbs-up-fill");
                                        $(contactButton).addClass("bi-hand-thumbs-up");
                                    }
                               }
                            });
            }
        });

}

function bagApplicantsFilterText() {
      setChecking("txtSearch");
      if ($('#txtSearch').val() == "") {
            setValid("#txtSearch", false);
      } else {
          var urlFilter = "/api/bag/user/text/" + $('#txtSearch').val();
          fetch(urlFilter).then(function(response) {
                if (response.ok) {
                     setValid("#txtSearch", true);
                     window.location.replace("/bagapplicants/text/"+$('#txtSearch').val());
                } else {
                    setValid("#txtSearch", false);
                }
          });
      }
}

function bagApplicantsFilterCity(e) {
    window.location.replace("/bagapplicants/city/true");
}

function bagApplicantsFilterFullTime(e) {
    window.location.replace("/bagapplicants/fullparthome/full");
}
function bagApplicantsFilterPartTime(e) {
    window.location.replace("/bagapplicants/fullparthome/part");
}
function bagApplicantsFilterRemote(e) {
    window.location.replace("/bagapplicants/fullparthome/home");
}

