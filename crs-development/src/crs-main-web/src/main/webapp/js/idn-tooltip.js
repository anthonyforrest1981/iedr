(function($) {
    var IdnTooltip = function(options) {
        this.getValue = options.getValue;
        this.iconContainer = options.iconContainer;
        this.iconImgSrc = options.iconImgSrc;
        this.type = options.type;
        this.icon = null;
    };
    IdnTooltip.prototype.renderIcon = function() {
        var elementValue = this.getValue();
        var shouldShowIcon = $.CRS.idnTools.isIdnString(elementValue);
        if (this.icon === null && shouldShowIcon) {
            this.icon = this.createIcon();
            this.icon.tooltip({
                content: this.generateContent.bind(this),
                css: {
                    "max-width": "300px"
                }
            });
        } else if (this.icon !== null && !shouldShowIcon) {
            this.icon.remove();
            this.icon = null;
        }
    };
    IdnTooltip.prototype.createIcon = function() {
        var icon = $("<img></img>")
            .attr("src", this.iconImgSrc)
            .addClass("tooltip-icon");
        icon.appendTo(this.iconContainer());
        return icon;
    };
    IdnTooltip.prototype.generateContent = function() {
        var elementValue = this.getValue();
        var info = this.type + " contains non-ASCII characters. Its punycode transcription is as follows:";
        return $("<div></div>")
           .append($("<p></p>")
               .text(info)
               .addClass("idn-tooltip-info"))
           .append($("<p></p>")
               .text($.CRS.idnTools.convertToPunycode(elementValue))
               .addClass("idn-tooltip-punycode"))
           .html();
    };

    /*
    * Options:
    * getValue - function for extracting value from a jQuery object tied to target element
    * iconContainer - function for calculating an element, which should contain the tooltip icon
    * iconImgSrc - path for tooltip icon
    * type - string value displayed to the user in the tooltip, describes the type of the input, should probably be one
    *     of: "Domain", "Nameserver", "Email address"
    */
    var initIdnTooltip = function(element, o) {
        var options = {
            getValue: function (element) {
                if (element.is("input") || element.is("textarea")) {
                    return element.val().trim();
                } else {
                    return element.text().trim();
                }
            },
            iconContainer: function (element) { return element.parent(); },
            iconImgSrc: "images/skin-default/action-icons/info.png",
            type: "Value"
        };
        $.extend(options, o);
        options.getValue = options.getValue.bind(undefined, element);
        options.iconContainer = options.iconContainer.bind(undefined, element);
        var idnTooltip = new IdnTooltip(options);
        element.bind("keyup elementValueUpdatedProgrammatically", function() {
            idnTooltip.renderIcon();
        });
        idnTooltip.renderIcon();
    };
    $.fn.idnTooltip = function(options) {
        this.each(function() {
            var element = $(this);
            initIdnTooltip(element, options);
        });
        return this;
    }
}(jQuery));