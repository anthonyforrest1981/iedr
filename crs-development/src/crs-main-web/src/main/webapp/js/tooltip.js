(function($) {
    var Tooltip = function(tooltipIconImg, options) {
        this.tooltipIconImg = tooltipIconImg;
        this.content = options.content;
        this.coordinates = options.coordinates;
        this.hideTimeout = options.hideTimeout;
        this.css = options.css;
        this.tooltip = null;
        this.hideTimeoutObject = null;
        this.bindHoverHandler(tooltipIconImg);
        this.bindRemoveHandler(tooltipIconImg)
    };
    Tooltip.prototype.bindHoverHandler = function(element) {
        element.hover(this.show.bind(this), this.hide.bind(this));
    };
    Tooltip.prototype.bindRemoveHandler = function(element) {
        var that = this;
        element.bind("iconDestroyed", function() {
            if (that.tooltip !== null) {
                that.tooltip.remove();
            }
        });
    };
    Tooltip.prototype.attachToDom = function() {
        var tooltip = $("<div></div>")
            .hide()
            .addClass("tooltip")
            .css("position", "absolute")
            .css("z-index", "999")
            .css(this.css);
        this.tooltip = tooltip.appendTo($("body"));
        this.bindHoverHandler(this.tooltip);
    };
    Tooltip.prototype.show = function() {
        if (this.hideTimeoutObject === null) {
            this.instantShow();
        } else {
            clearTimeout(this.hideTimeoutObject);
            this.hideTimeoutObject = null;
        }
    };
    Tooltip.prototype.hide = function() {
        var that = this;
        this.hideTimeoutObject = setTimeout(function() {
            that.instantHide();
            that.hideTimeoutObject = null;
        }, this.hideTimeout);
    };
    Tooltip.prototype.instantShow = function() {
        if (this.tooltip === null) {
            this.attachToDom();
        }
        this.positionTooltip();
        this.setTooltipContent();
        this.tooltip.show();
    };
    Tooltip.prototype.instantHide = function() {
        this.tooltip.hide();
    };
    Tooltip.prototype.positionTooltip = function() {
        var coordinates = typeof this.coordinates === "function" ? this.coordinates() : this.coordinates;
        coordinates = $.extend({}, coordinates);
        coordinates.top = coordinates.top + this.tooltipIconImg.offset().top + this.tooltipIconImg.height();
        coordinates.left = coordinates.left + this.tooltipIconImg.offset().left + this.tooltipIconImg.width();
        this.tooltip.css({
            top: coordinates.top + "px",
            left: coordinates.left + "px"
        });
    };
    Tooltip.prototype.setTooltipContent = function() {
        var content = typeof this.content === "function" ? this.content() : this.content;
        this.tooltip.html(content);
    };

    // $.event.special configures the meta behavior of a javascript event. The following code is a hack to detect when
    // the tooltip icon is removed. It works as follows:
    // 1. We subscribe to the "iconDestroyed" event on the input element, but this event is actually never triggered.
    // 2. When the tooltip icon is removed, the handler for "iconDestroyed" event is removed along with it.
    // 3. When handler is removed, remove function from the object below is called, triggering the real handler.
    // setup and teardown functions have to be defined, because jQuery in the current version (1.2.6) will try to call
    // them no matter if they are defined or not.
    $.event.special.iconDestroyed = {
        remove: function(handleObj) {
            if (handleObj.handler !== null) {
                handleObj.handler();
            }
        },
        setup: function() {},
        teardown: function() {}
    };

    /*
    * This function creates a tooltip and and binds it to provided tooltip icon.
    * Options:
    * content - string or a function returning a string, which will be used to fill the tooltip (interpreted as html)
    * coordinates - object or a function returning an object, which should have "top" and "left" properties defined, can
    *     be negative; they are interpreted as offset relative to default tooltip position, which is right bottom corner
    *     of the tooltip icon
    * css - additional css attributes for the tooltip
    * hideTimeout - decides how much time (in milliseconds) can pass after mouse cursor moves off the tooltip or the
    *     tooltip icon, before the tooltip is hidden
    */
    var initTooltip = function(tooltipIconImg, options) {
        var d = {
            content: "",
            coordinates: {
                top: 0,
                left: 0
            },
            css : {},
            hideTimeout: 250
        };
        $.extend(d, options);
        var tooltip = new Tooltip(tooltipIconImg, d);
    };

    $.fn.tooltip = function(options) {
        this.each(function() {
            var tooltipIconImg = $(this);
            initTooltip(tooltipIconImg, options);
        });
        return this;
    };

    $(document).ready(function() {
        $("img[data-tooltip-icon='true']").each(function() {
            $(this).tooltip({
                content: $(this).attr("data-tooltip-content")
            });
        });
    });
}(jQuery));