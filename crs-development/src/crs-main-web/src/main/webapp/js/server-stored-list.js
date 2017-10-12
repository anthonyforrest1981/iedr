(function($){
    /*
    * Term _links_ is used to denote DOM elements that cause an element to be added to the list (like 'Add to export'
    * buttons on export authcodes page or checkboxes on invoices report).
    * Options:
    * listContainerClass - used to locate DOM element which will be used to hold the list
    * linksClass - used to locate all links on the page
    * linksValueAttribute - name of the attribute that is used to store list value in the link
    * selectedValues - list of values to populate the list
    * addHandler - handler for adding values to the list
    * removeHandler - handler for removing values from the list
    */
    var initList = function(klass, options) {
        var d = {
            listContainerClass: "server-stored-list",
            linksClass: "server-stored-list-link",
            linksValueAttribute: "server-stored-list-value",
            selectedValues: [],
            addHandler: null,
            removeHandler: null,
            maxSelected: null,
            getMaxSelectedMessage: function (toSelectLength, selectedLength, limit) {
                return "You have selected too many items (limit: " + limit + ")";
            }
        };
        $.extend(d, options);
        return $("." + d.listContainerClass).map(function() {
            d.listContainer = $(this);
            var list = new klass(d);
            list.init();
            return list;
        });
    };

    var ServerStoredList = function(options) {
        this.listContainer = options.listContainer;
        this.linksClass = options.linksClass;
        this.linksValueAttribute = options.linksValueAttribute;
        this.initialSelectedValues = options.selectedValues;
        this.addHandler = options.addHandler;
        this.removeHandler = options.removeHandler;
        this.maxSelected = options.maxSelected;
        this.getMaxSelectedMessage = options.getMaxSelectedMessage;
        this.selectedValues = [];
        this.links = {};
        this.actionButton = null;
        this.clearButton = null;
    };

    ServerStoredList.prototype.init = function() {
        this.addMainButtonHandlers();
        this.addLinksHandlers();
        this.addItemsLocally(this.initialSelectedValues);
    };
    ServerStoredList.prototype.addMainButtonHandlers = function() {
        var that = this;
        this.actionButton = $('.action-button', this.listContainer);
        this.clearButton = $('.clear-button', this.listContainer);
        this.clearButton.click(function(event) {
            if (that.clearHandler === null) {
                return true;
            }
            event.preventDefault();
            that.removeHandler(that.selectedValues, function() {
                that.removeAllItems();
            });
        });
    };
    ServerStoredList.prototype.addLinksHandlers = function() {
        var that = this;
        $("." + this.linksClass).each(function() {
            var link = $(this);
            var value = link.attr(that.linksValueAttribute);
            that.links[value] = link;
            link.mouseup(function(event) {
                that.toggleItem(event, value);
            });
        });
    };

    ServerStoredList.prototype.isValueSelected = function(value) {
        return $.inArray(value, this.selectedValues) !== -1;
    };
    ServerStoredList.prototype.toggleItem = function(event, value) {
        var success;
        if (this.isValueSelected(value)) {
            success = this.removeItem(value);
        } else {
            success = this.addItem(value);
        }
        this.decideCheckboxAction(success, event);
    };
    ServerStoredList.prototype.addItems = function(values) {
        var that = this;
        if (this.limitExceeded(values)) {
            alert(this.getMaxSelectedMessage(values.length, this.selectedValues.length, this.maxSelected));
            return false;
        }
        this.addHandler(values, function() {
            that.addItemsLocally(values);
        });
        return true;
    };
    ServerStoredList.prototype.addItem = function(value) {
        return this.addItems([value]);
    };
    ServerStoredList.prototype.removeItems = function(values) {
        var that = this;
        this.removeHandler(values, function() {
            that.removeItemsLocally(values);
        });
        return true;
    };
    ServerStoredList.prototype.removeItem = function(value) {
        return this.removeItems([value]);
    };
    ServerStoredList.prototype.removeAllItems = function() {
        return this.removeItems(this.selectedValues);
    };
    ServerStoredList.prototype.addItemsLocally = function(values) {
        var that = this;
        this.selectedValues = this.selectedValues.concat(values);
        $.each(values, function(i, value) {
            that.renderAddedItem(value);
        });
        this.render();
    };
    ServerStoredList.prototype.removeItemsLocally = function(values) {
        var that = this;
        this.selectedValues = $.grep(this.selectedValues, function(current) {
            return $.inArray(current, values) === -1;
        });
        $.each(values, function(i, value) {
            that.renderRemovedItem(value);
        });
        this.render();
    };
    ServerStoredList.prototype.limitExceeded = function(values) {
        return (this.maxSelected !== null &&
            this.selectedValues.length + values.length > this.maxSelected);
    };

    ServerStoredList.prototype.render = function() {
        this.beforeRender();
        this.renderLinks();
        this.renderMainButtons();
        this.afterRender();
    };
    ServerStoredList.prototype.renderLinks = function() {
        var that = this;
        $.each(this.links, function(value, link) {
            that.renderLink(link, that.isValueSelected(value));
        });
    };
    ServerStoredList.prototype.renderMainButtons = function() {
        if (this.selectedValues.length > 0) {
            this.actionButton.attr("disabled", false);
            this.clearButton.attr("disabled", false);
        } else {
            this.actionButton.attr("disabled", true);
            this.clearButton.attr("disabled", true);
        }
    };
    ServerStoredList.prototype.decideCheckboxAction = function(success, event) {
        if (!success) {
            // Checkbox will be checked in "click" event, we have to prevent it.
            $(event.currentTarget).one("click", function(clickEvent) {
                clickEvent.preventDefault();
            });
        }
    };
    // To be overriden if necessary.
    ServerStoredList.prototype.renderAddedItem = function(value) {};
    ServerStoredList.prototype.renderRemovedItem = function(value) {};
    ServerStoredList.prototype.renderLink = function(link, isLinksValueSelected) {};
    ServerStoredList.prototype.beforeRender = function() {};
    ServerStoredList.prototype.afterRender = function() {};

    var BubbleList = function(options) {
        ServerStoredList.call(this, options);
        this.listElement = $("<ul></ul>").addClass("bubble-list").prependTo(this.listContainer);
    };
    BubbleList.prototype = Object.create(ServerStoredList.prototype);
    BubbleList.prototype.constructor = BubbleList;
    BubbleList.LIST_DATA_ATTRIBUTE = "bubble-list-value";

    BubbleList.prototype.renderAddedItem = function(value) {
        var that = this;
        this.listElement.append(
            $("<li></li>").attr(BubbleList.LIST_DATA_ATTRIBUTE, value).text(value).append(
                $('<a title="remove">X</a>').click(function() {
                    that.removeItem(value);
                })
            )
        );
    };
    BubbleList.prototype.renderRemovedItem = function(value) {
        $('li[' + BubbleList.LIST_DATA_ATTRIBUTE + '="' + value + '"]', this.listElement).remove();
    };
    BubbleList.prototype.renderLink = function(link, isLinksValueSelected) {
        link.attr("disabled", isLinksValueSelected);
    };

    var CheckboxList = function(options) {
        var d = {
            selectAllPlaceholderClass: "checkbox-list-selectall-placeholder",
            getCountMessage: function (listLength) {
                return "";
            }
        };
        $.extend(d, options);
        ServerStoredList.call(this, d);
        this.selectAll = this.createSelectAllCheckbox(d.selectAllPlaceholderClass);
        this.getCountMessage = d.getCountMessage;
        this.countContainer = $("<p></p>").prependTo(this.listContainer);
    };
    CheckboxList.prototype = Object.create(ServerStoredList.prototype);
    CheckboxList.prototype.constructor = CheckboxList;

    CheckboxList.prototype.createSelectAllCheckbox = function(selectAllPlaceholderClass) {
        return $("<input type=\"checkbox\"/>")
            .appendTo($("." + selectAllPlaceholderClass))
            .mouseup(this.toggleSelectAll.bind(this));
    };
    CheckboxList.prototype.updateCount = function() {
        this.countContainer.html(this.getCountMessage(this.selectedValues.length));
    };
    CheckboxList.prototype.toggleSelectAll = function(event) {
        // This is binded to mouseup event, which is triggered before the checkbox changes its state.
        var checked = this.selectAll.attr("checked");
        var indeterminate = this.selectAll.attr("indeterminate");
        var uncheckAll = checked || indeterminate;
        var valuesToChange = [];
        $.each(this.links, function(value, link) {
            if (link.attr("checked") === uncheckAll) {
                valuesToChange.push(value);
            }
        });
        var success;
        if (uncheckAll) {
            success = this.removeItems(valuesToChange);
            if (indeterminate) {
                // Default browser behavior is to go to "checked" state from here. We want the opposite.
                event.preventDefault();
            }
        } else {
            success = this.addItems(valuesToChange);
        }
        this.decideCheckboxAction(success, event);
    };
    CheckboxList.prototype.renderSelectAll = function() {
        var that = this;
        var allSelected = true;
        var noneSelected = true;
        $.each(this.links, function(value, link) {
            if (that.isValueSelected(value)) {
                noneSelected = false;
            } else {
                allSelected = false;
            }
        });
        if (allSelected) {
            this.selectAll.attr("indeterminate", false);
            this.selectAll.attr("checked", true);
        } else if (noneSelected) {
            this.selectAll.attr("indeterminate", false);
            this.selectAll.attr("checked", false);
        } else {
            this.selectAll.attr("indeterminate", true);
            this.selectAll.attr("checked", false);
        }
    };

    CheckboxList.prototype.renderLink = function(link, isLinksValueSelected) {
        var checked = isLinksValueSelected;
        link.attr("checked", checked);
    };
    CheckboxList.prototype.afterRender = function() {
        this.updateCount();
        this.renderSelectAll();
    };

    $.CRS = $.CRS || {};
    $.CRS.serverStoredLists = {
        bubbleList: function(options) {
            initList(BubbleList, options);
        },
        checkboxList: function(options) {
            initList(CheckboxList, options);
        }
    };
}(jQuery));
