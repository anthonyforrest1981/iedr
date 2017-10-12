(function($) {
    var convertToPunycode = function(str) {
        return punycode.toASCII(str);
    };
    var isIdnString = function(str) {
        return str !== convertToPunycode(str);
    };
    $.CRS = $.CRS || {};
    $.CRS.idnTools = {
        isIdnString: function(str) {
            return isIdnString(str);
        },
        convertToPunycode: function(str) {
            return convertToPunycode(str);
        }
    };
}(jQuery));