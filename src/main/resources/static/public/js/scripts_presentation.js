$(function() {

    const bit_presentation = $("#bit-presentation");
    const bit_presentation_prev = $("#bit-presentation-prev");
    const bit_presentation_next = $("#bit-presentation-next");
    const bit_presentation_section = $("#bit-presentation-section");

    bit_presentation.carousel();
    bit_presentation_prev.hide();

    function PresentationState() {
        this.titles = [
            '',
            'Equipo de trabajo',
            'Origen de la idea',
            'Tecnologías utilizadas'
        ];
        this.index = 0;
        this.stop = this.titles.length - 1;
        this.frozen = false;

        this.set_title = function() {
            bit_presentation_section.text(this.titles[this.index]);
        }

        this.prev = function() {
            if (this.frozen) {
                return;
            }
            if (this.index > 0) {
                this.frozen = true;
                bit_presentation.carousel('prev');
                this.index--;
            }
            if (this.index === 0) {
                bit_presentation_prev.hide();
            } else {
                bit_presentation_prev.show();
            }
            bit_presentation_next.show();
            this.set_title();
        };

        this.next = function() {
            if (this.frozen) {
                return
            }
            if (this.index < this.stop) {
                this.frozen = true;
                bit_presentation.carousel('next');
                this.index++;
            }
            if (this.index === this.stop) {
                bit_presentation_next.hide();
            } else {
                bit_presentation_next.show();
            }
            bit_presentation_prev.show();
            this.set_title();
        };
    }
    presentation_state = new PresentationState();

    bit_presentation.on('slid.bs.carousel', function () {
        presentation_state.frozen = false;
    })

    document.addEventListener('keyup', function(event) {

        if (event.key === 'ArrowLeft' || event.key === 'a' || event.key === '4') {
            presentation_state.prev();
            event.preventDefault();
        }

        if (event.key === 'ArrowRight' || event.key === 'd' || event.key === '6') {
            presentation_state.next();
            event.preventDefault();
        }

    });

    bit_presentation_prev.click(function(){
        presentation_state.prev();
    });

    bit_presentation_next.click(function(){
        presentation_state.next();
    });

});
