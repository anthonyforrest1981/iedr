(function($) {
    $.fn.updateValue = function(value) {
        this.val(value);
        this.trigger('elementValueUpdatedProgrammatically');
    }
}(jQuery));
