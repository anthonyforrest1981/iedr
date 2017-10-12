(function($) {
    var dialogInstance = null;

    var openDialog = function(loginForm, pinFormContainer, acceptLabel, validationDoneCallback, cancelDialogCallback) {
        var pinForm = pinFormContainer.children();
        $.CRS.dialog({
            html: pinForm.clone(),
            acceptOptions: {
                text: acceptLabel,
                callback: function(dialog) {
                    dialogInstance = dialog;
                    loginForm.submit();
                },
                closeOnClick: false
            },
            cancelOptions: {
                callback: function(dialog) {
                    dialogInstance = null;
                    cancelDialogCallback();
                }
            },
            dialogOpenCallback: validationDoneCallback,
            acceptVisibleAfterScrolled: false,
            acceptOnEnterKey: true,
            cancelOnEscapeKey: true
        });
    };

    var cloneContent = function(src, dst) {
        dst.empty();
        dst.append(src.children().clone());
    };

    var refreshDialog = function(pinFormContainer, validationDoneCallback) {
        var dialogContent = dialogInstance.data('crs-dialog-content');
        cloneContent(pinFormContainer, dialogContent);
        validationDoneCallback(dialogInstance);
    };

    $.CRS = $.CRS || {};
    $.CRS.tfaValidation = {
        clonePinFromDialog: function(pinFormContainer) {
            if (dialogInstance !== null) {
                var dialogContent = dialogInstance.data('crs-dialog-content');
                cloneContent(dialogContent, pinFormContainer);
            }
        },
        clonePinToDialog: function(pinFormContainer, loginForm, acceptLabel, validationDoneCallback,
                cancelDialogCallback) {
            if (dialogInstance === null) {
                openDialog(loginForm, pinFormContainer, acceptLabel, validationDoneCallback, cancelDialogCallback);
            } else {
                refreshDialog(pinFormContainer, validationDoneCallback);
            }
        },
        cleanDialog: function() {
            if (dialogInstance !== null) {
                dialogInstance.dialog('destroy').remove();
                dialogInstance = null;
            }
        },
        isDialogOpen: function() {
            return dialogInstance !== null;
        }
    };
}(jQuery));