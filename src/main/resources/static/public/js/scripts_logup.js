const WAITING_SERVER_TIME = 100;    // milliseconds

$(function() {

    function setValid(id, isValid) {
        const classes = isValid ? ['is-invalid', 'is-valid'] : ['is-valid', 'is-invalid'];
        $(id).removeClass('is-checking').removeClass(classes[0]).addClass(classes[1]);
    }

    function setChecking(id) {
        $(id).removeClass('is-valid').removeClass('is-invalid').addClass('is-checking');
    }

    function CRUDProfileHide(id) {
        for (const part of ['Body', 'Footer']){
            $(id + part).removeClass('d-block').addClass('d-none');
        }
    }

    function CRUDProfileShow(id) {
        for (const part of ['Body', 'Footer']){
            $(id + part).removeClass('d-none').addClass('d-block');
        }
    }

    function CRUDProfileProgress(a, b) {
        CRUDProfileProgressbar.removeClass('w-' + a).addClass('w-' + b);
    }

    function CRUDProfileState() {
        this.index = 0;
        this.state = [
            [ '1', '#CRUDProfileUser', '#inputUserName'],
            ['16', '#CRUDProfilePerson', '#inputFirstName'],
            ['32', '#CRUDProfileLocation', '#inputCountry'],
            ['48', '#CRUDProfileEducation', '#inputEducation'],
            ['64', '#CRUDProfileSkills', '#inputSkills'],
            ['80', '#CRUDProfileAvailability', '#inputFullTime']
        ];

        this.goto = function(prevIndex, nextIndex) {
            const prev = this.state[prevIndex];
            const next = this.state[nextIndex];
            CRUDProfileProgress(prev[0], next[0]);
            CRUDProfileHide(prev[1]);
            CRUDProfileShow(next[1]);
            $(next[2]).trigger('focus')
        };

        this.prev = function() {
            this.goto(this.index, --this.index);
        };

        this.next = function() {
            this.goto(this.index, ++this.index);
        };
    }

    function checkUsername(username) {
        if (username.length === 0) {
            setValid('#inputUserName', false);
            $('#invalid-feedback-UserName').html(document.texts.notEmpty);
        } else {
            setChecking('#inputUserName');
            fetch('/api/existsByUsername/' + username).then(function(response) {
                response.json().then(function(data) {
                    if (data) {
                        setValid('#inputUserName', false);
                        $('#invalid-feedback-UserName').html(document.texts.usernameAlreadyExists);
                    } else {
                        setValid('#inputUserName', true);
                    }
                });
            });
        }
    }

    function checkEMail(email) {
        if (email.length === 0) {
            setValid('#inputEMail', false);
            $('#invalid-feedback-EMail').html(document.texts.notEmpty);
        } else {
            setChecking('#inputEMail');
            fetch('/api/existsByEMail/' + email).then(function(response) {
                response.json().then(function(data) {
                    if (data) {
                        setValid('#inputEMail', false);
                        $('#invalid-feedback-EMail').html(document.texts.emailAlreadyExists);
                    } else {
                        setValid('#inputEMail', true);
                    }
                });
            });
        }
    }

    function CRUDProfileSetCheckbox(id) {
        // https://developer.mozilla.org/en-US/docs/Web/HTML/Element/Input/checkbox#Note_2
        const checkbox = document.getElementById(id);
        if (!checkbox.checked) {
            const input = document.createElement('input');
            input.type = 'hidden';
            input.name = id[5].toLocaleLowerCase() + id.slice(6);
            input.value = false;
            document.getElementById('CRUDProfileSubmit').appendChild(input);
            return false;
        }
        return true;
    }

    /* --- Events --- */

    $('#inputBirthDate').datepicker({format: 'dd/mm/yyyy'});
    $('#inputCountry').selectpicker({liveSearch: true});
    $('#inputState').selectpicker({liveSearch: true});
    $('#inputCity').selectpicker({liveSearch: true});

    $('#inputUserName').focusout(function() {
        checkUsername($(this).val());
    });

    $('#inputEMail').focusout(function() {
        checkEMail($(this).val());
    });

    $('#ModalCRUDProfile').on('shown.bs.modal', function () {
        $('#inputUserName').trigger('focus')
    })

    /* --- Actions --- */

    const CRUDProfileProgressbar = $('#CRUDProfileProgressbar');
    const CRUDProfile = new CRUDProfileState();

    $('#nextCRUDProfileUser').click(function() {
        document.getElementById('nextCRUDProfileUser').setAttribute('disabled', 'disabled');

        checkUsername($('#inputUserName').val());
        checkEMail($('#inputEMail').val());

        if ($('#inputPassword').val().length === 0) {
            setValid('#inputPassword', false);
            $('#invalid-feedback-password').html(document.texts.notEmpty);
            return;
        }

        setValid('#inputPassword', true);

        if ($('#inputPassword').val() !== $('#inputPassword2').val()) {
            setValid('#inputPassword2', false);
            $('#invalid-feedback-password2').html(document.texts.passwordsNotEqual);
            return;
        }

        setValid('#inputPassword2', true);

        // Setting a timer to wait for "inputUserName" and "inputEMail" checking from api,
        // when the inputs have been verified, the next button is activated.
        const timer = setInterval(function() {
            if (!($('#inputUserName').hasClass('is-checking') || $('#inputEMail').hasClass('is-checking'))) {
                clearInterval(timer);
                CRUDProfile.next();
                document.getElementById('nextCRUDProfileUser').removeAttribute('disabled');
            }
        }, WAITING_SERVER_TIME);
    });

    $('#prevCRUDProfilePerson').click(function() {
        CRUDProfile.prev();
    });

    $('#nextCRUDProfilePerson').click(function() {

        if ($('#inputFirstName').val().length === 0) {
            setValid('#inputFirstName', false);
            return;
        }
        setValid('#inputFirstName', true);

        if($('#inputLastName').val().length === 0) {
            setValid('#inputLastName', false);
            return
        }
        setValid('#inputLastName', true);

        if (!moment($('#inputBirthDate').val(), 'DD/MM/YYYY', true).isValid()) {
            setValid('#inputBirthDate', false);
            return
        }
        setValid('#inputBirthDate', true);

        CRUDProfile.next();
    });

    $('#prevCRUDProfileLocation').click(function() {
        CRUDProfile.prev();
    });

    $('#nextCRUDProfileLocation').click(function() {
        CRUDProfile.next();
    });

    $('#prevCRUDProfileEducation').click(function() {
        CRUDProfile.prev();
    });

    $('#nextCRUDProfileEducation').click(function() {
        CRUDProfile.next();
    });

    $('#prevCRUDProfileSkills').click(function() {
        CRUDProfile.prev();
    });

    $('#nextCRUDProfileSkills').click(function() {
        CRUDProfile.next();
    });

    $('#prevCRUDProfileAvailability').click(function() {
        CRUDProfile.prev();
    });

    $('#nextCRUDProfileAvailability').click(function() {
        let activatedAbility = false

        activatedAbility |= CRUDProfileSetCheckbox('inputFullTime');
        activatedAbility |= CRUDProfileSetCheckbox('inputPartTime');
        activatedAbility |= CRUDProfileSetCheckbox('inputHomeWork');

        if (activatedAbility || $('#inputEducation').val() !== '' || $('#inputSkills').val() !== '') {
            $('#inputApplyForJob').val(true);
        }
        $('#CRUDProfileSubmit').submit();
    });

});
