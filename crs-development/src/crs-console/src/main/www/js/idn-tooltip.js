(function($) {
    var IdnTooltip = function(element, options) {
        this.element = element;
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
            this.createIconAndTooltip();
        } else if (this.icon !== null && !shouldShowIcon) {
            this.adjustElement(true);
            this.icon.remove();
            this.icon = null;
        }
    };
    IdnTooltip.prototype.createIconAndTooltip = function() {
        var that = this;
        this.icon = $("<img src='" + this.iconImgSrc + "'></img>");
        this.icon.load(function() {
            $(this)
                .css({
                    visibility: "hidden",
                    position: "absolute",
                    "z-index": "998"
                })
                .appendTo(that.iconContainer());
            that.adjustIcon();
            that.adjustElement(false);
            $(this).tooltip(that.getTooltipOptions());
        });
    };
    IdnTooltip.prototype.adjustIcon = function() {
        // We center the icon vertically relative to the element's interior.
        var top = (this.element.outerHeight() - this.icon.height()) / 2;
        // We position the icon horizontally right before the element's right padding, then we give it a left margin
        // equal to this padding (but at least 1px).
        var right = (this.element.outerWidth() - this.element.width()) / 2;
        var elementPaddingRight = parseInt(this.element.css("padding-right").replace("px", ""));
        this.icon.css({
            top: top + "px",
            right: right + "px",
            "margin-left": Math.max(1, elementPaddingRight) + "px",
            visibility: "visible"
        });
    };
    IdnTooltip.prototype.adjustElement = function(willIconBeRemoved) {
        var currentPaddingRight = parseInt(this.element.css("padding-right").replace("px", ""));
        var additionalPaddingRight = this.icon.outerWidth(true);
        if (willIconBeRemoved) {
            additionalPaddingRight = -additionalPaddingRight;
        }
        var padding = {
            right: currentPaddingRight + additionalPaddingRight + "px"
        };
        var boxSizing = this.element.css("box-sizing");
        if (boxSizing === "border-box") {
            // The element's width attribute will be treated as outer width (including padding and border), so
            // increasing the padding will not resize the element. Still, we have to explicitly set it, otherwise
            // changing the padding will change the element's width.
            this.element.width(this.element.outerWidth());
        } else {
            // Here, box-sizing is default, so width attribute is treated as content width (not including padding and
            // border). We have to adjust it before changing the padding.
            this.element.width(this.element.width() - additionalPaddingRight);
        }
        this.changeElementPadding(this.element, padding);
    };
    IdnTooltip.prototype.changeElementPadding = function(element, padding) {
        // We have to explicitly set all paddings, since setting only one of them turned out to be affecting all others
        // (i.e. changing them from 2px to 1px).
        var currentPadding = {
            top: element.css("padding-top"),
            right: element.css("padding-right"),
            bottom: element.css("padding-bottom"),
            left: element.css("padding-left")
        };
        $.extend(currentPadding, padding);
        $.each(currentPadding, function(key, value) {
            element.css("padding-" + key, value);
        });
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
    IdnTooltip.prototype.getTooltipOptions = function() {
        var tooltipOptions = {
            content: this.generateContent.bind(this),
            coordinates: {
                top: 1,
                left: -0.8 * this.element.outerWidth()
            },
            css: {
                "max-width": "300px"
            }
        };
        return tooltipOptions;
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
            iconImgSrc: "images/tooltipicon.png",
            type: "Value"
        };
        $.extend(options, o);
        options.getValue = options.getValue.bind(undefined, element);
        options.iconContainer = options.iconContainer.bind(undefined, element);
        var idnTooltip = new IdnTooltip(element, options);
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