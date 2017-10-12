(function($) {

    var getFullOptions = function(clientOptions) {
        var defaultOptions = {
            url: null,
            html: null,
            acceptOptions: {
                present: true,
                text: "OK",
                callback: function(dialog) {},
                closeOnClick: true
            },
            cancelOptions: {
                present: true,
                text: "Cancel",
                callback: function(dialog) {},
                closeOnClick: true
            },
            failureCallback: function(dialog) {
                dialog.dialog("close");
            },
            dialogOpenCallback: function(dialog) {},
            acceptVisibleAfterScrolled: true,
            acceptOnEnterKey: false,
            cancelOnEscapeKey: false
        };
        return $.extend(true, defaultOptions, clientOptions);
    };

    var getContentAsync = function(options) {
        if (options.url !== null) {
            return $.get(options.url);
        } else if (options.html !== null) {
            return $.Deferred().resolve(options.html).promise();
        } else {
            return $.Deferred().reject().promise();
        }
    };

    var getButtonCallbackFromOptions = function(buttonOptions) {
        return function() {
            var dialog = $(this);
            if (buttonOptions.closeOnClick) {
                dialog.dialog("close");
            }
            buttonOptions.callback(dialog);
            if (buttonOptions.closeOnClick) {
                dialog.dialog("destroy").remove();
            }
        };
    };

    var addButtonFromOptions = function(buttons, buttonOptions) {
        if (buttonOptions.present) {
            buttons[buttonOptions.text] = getButtonCallbackFromOptions(buttonOptions);
        }
    };

    var createButtonsFromOptions = function(options) {
        var buttons = {};
        addButtonFromOptions(buttons, options.acceptOptions);
        addButtonFromOptions(buttons, options.cancelOptions);
        return buttons;
    };

    var createLoadingImage = function() {
        var loadingImage = $("<img/>", {
            src: "images/loading.gif",
        });
        loadingImage
            .appendTo($("body"))
            .load(function() {
                $(this).css({
                    position: "absolute",
                    left: "50%",
                    top: "50%",
                    "margin-left": "-" + loadingImage.width() / 2 + "px",
                    "margin-right": "-" + loadingImage.height() / 2 + "px"
                });
            });
        return loadingImage;
    };

    var showDialog = function(dialog, content) {
        var buttonPane = dialog.parent().find(".ui-dialog-buttonpane");
        // We're adjusting the width twice, because the first adjustment doesn't take into account
        // the width of the vertical scrollbar. The difference between scrollWidth and width is the
        // size of the scrollbar (it will be 0 if the scrollbar is not present).
        dialog
            .dialog({
                width: content.prop("scrollWidth"),
                height: Math.min(content.height() + buttonPane.height(), window.innerHeight - 200)
            })
            .dialog({
                width: content.prop("scrollWidth") + (content.prop("scrollWidth") - content.width())
            })
            .dialog({
                position: {
                    my: "center",
                    at: "center",
                    of: window
                }
            });
        dialog.parent().css('visibility', 'visible');
    };

    var createDialog = function(content, buttons) {
        var dialog = $("<div></div>")
            .hide()
            .append(content)
            .appendTo("body")
            .dialog({
                modal: true,
                draggable: false,
                resizable: false,
                closeOnEscape: false,
                dialogClass: "crs-dialog-dialog",
                minHeight: 0,
                width: 0,
                buttons: buttons
            });
        dialog.parent().css('visibility', 'hidden');
        return dialog;
    };

    var createAndShowDialog = function(html, buttons) {
        var content = $("<div></div>", { class: "crs-dialog-content" });
        var dialog = createDialog(content, buttons);
        dialog.data("crs-dialog-content", content);
        var loadingImage = createLoadingImage();
        var deferred = $.Deferred();
        html.done(function(html) {
            if (html instanceof jQuery) {
                content.append(html);
            } else {
                content.html(html);
            }
            showDialog(dialog, content);
        }).always(function() {
            loadingImage.remove();
        }).done(function(html) {
            deferred.resolve(dialog);
        }).fail(function() {
            deferred.reject(dialog);
        });
        return deferred.promise();
    };

    var shouldAttachScrollDownHandler = function(dialog, options) {
        var scrollbarPresent = dialog.get(0) ?
            dialog.get(0).scrollHeight > dialog.innerHeight() : false;
        return scrollbarPresent && options.acceptVisibleAfterScrolled;
    };

    var attachScrollDownHandler = function(dialog, options) {
        var acceptButton = $(".ui-button-text", dialog.parent()).filter(function() {
            return $(this).text() === options.acceptOptions.text;
        }).parent();
        dialog.bind("scroll", function() {
            if ($(this).scrollTop() + $(this).innerHeight() >= this.scrollHeight) {
                acceptButton.button("enable");
                dialog.unbind("scroll");
            }
        });
        acceptButton.button("disable");
    };

    var attachKeyHandler = function(keyCode, buttonOptions, dialog) {
        dialog.keyup(function(e) {
            if (e.keyCode === keyCode) {
                e.stopPropagation();
                getButtonCallbackFromOptions(buttonOptions).call(dialog);
            }
        });
    };

    var attachEnterKeyHandler = function(dialog, options) {
        attachKeyHandler(13, options.acceptOptions, dialog);
    };

    var attachEscapeKeyHandler = function(dialog, options) {
        attachKeyHandler(27, options.cancelOptions, dialog);
    };

    var attachHandlers = function(dialog, options) {
        if (shouldAttachScrollDownHandler(dialog, options)) {
            attachScrollDownHandler(dialog, options);
        }
        if (options.acceptOnEnterKey) {
            attachEnterKeyHandler(dialog, options);
        }
        if (options.cancelOnEscapeKey) {
            attachEscapeKeyHandler(dialog, options);
        }
    };

    var crsDialog = function(clientOptions) {
        var options = getFullOptions(clientOptions);
        var buttons = createButtonsFromOptions(options);
        var html = getContentAsync(options);
        createAndShowDialog(html, buttons).done(function(dialog) {
            attachHandlers(dialog, options);
        }).done(options.dialogOpenCallback).fail(options.failureCallback);
        // This is to account for other content being loaded via ajax, which causes the document
        // to change its size, making the dialog overlay too small.
        setTimeout(function() {
            $(window).resize();
        }, 0);
    };

    $.CRS = $.CRS || {};
    $.CRS.dialog = function(options) {
        crsDialog(options);
    };

}(jQuery));
