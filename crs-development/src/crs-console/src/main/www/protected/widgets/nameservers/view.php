<?php
$maxRows = $model->maxRows;
$minRows = $model->minRows;
?>
<script type="text/javascript" src="js/jquery-update-value.js"></script>
<script type="text/javascript" src="js/tooltip.js"></script>
<script type="text/javascript" src="js/punycode.min.js"></script>
<script type="text/javascript" src="js/idn-tools.js"></script>
<script type="text/javascript" src="js/idn-tooltip.js"></script>
<script>

    function clearAndHideRow(row) {
        $(".nameserver input[type='text']", row).each(function() {
            // Use updateValue() instead of val(), so that change event is triggered.
            $(this).updateValue('');
        });
        row.hide();
    }

    function appendRow() {
        var count = $('#ns_count');
        var countValue = parseInt(count.val());
        if (countValue < <?php echo $maxRows; ?>) {
            $('#row_' + countValue).css('display', 'table-row');
            count.val(countValue + 1);
        } else {
            alert('There should be <?php echo $maxRows; ?> nameservers at most.');
        }
    }

    function removeRow(deleteButton) {
        var count = $('#ns_count');
        var countValue = parseInt(count.val());
        if (parseInt(countValue) > <?php echo $minRows; ?>) {
            var buttonId = deleteButton.id;
            var id = parseInt(buttonId.replace('ns_del_buttton_', ''));
            countValue = countValue - 1;
            copyNextFieldsValues(id, countValue);
            count.val(countValue);
            clearAndHideRow($('#row_' + countValue));
        } else {
            alert('There should be at least <?php echo $minRows; ?> nameservers.');
        }
    }

    function copyNextFieldsValues(fieldId, lastFieldId) {
        for (var i = fieldId; i < lastFieldId; i++) {
            $.each(['ns_', 'ipv4_', 'ipv6_'], function(j, prefix) {
                // Use updateValue() instead of val(), so that change event is triggered.
                var current = $('#' + prefix + i);
                var next = $('#' + prefix + (i + 1));
                current.updateValue(next.val());
            });
        }
    }

    function verifyDNS(formId, domains, nameservers) {
        var DNS_CHECK_NOTICE_ID = "DNSCheckNotificationResult";
        var formData = $.param({
            YII_CSRF_TOKEN: $("#"+formId+" input[name=YII_CSRF_TOKEN]").val(),
            domains: domains,
            nameservers: nameservers});
        $("#"+DNS_CHECK_NOTICE_ID).remove();
        var flash = function(flashType, message, additionalData) {
            var flashDiv = $("<div>").attr('id', DNS_CHECK_NOTICE_ID).addClass("flash-" + flashType).text(message);
            if (additionalData) flashDiv.append(additionalData);
            flashDiv.prepend($("<a>").text("Close").css("float", "right").click(function(e){
                e.preventDefault();
                $("#"+DNS_CHECK_NOTICE_ID).remove();
            }));
            $("#content > h2:first").after(flashDiv);
        };

        $.ajax({
            type: 'POST',
            url: "<?php $host = Yii::app()->request->hostInfo; $host .= '/index.php?r=dnscheck/verifyDns'; echo $host; ?>",
            data: formData,
            success: function(response) {
                if (response == "") {
                    flash("notice", "DNS Check passed");
                } else {
                    var dnsFailure = response.indexOf('FATAL') >= 0;
                    if (dnsFailure) {
                        flash("error", "DNS Check failed", $('<pre>').css({maxHeight: "150px", overflow:"auto"}).text(response));
                    } else {
                        flash("error", response);
                    }
                }
            }
        });
    }

    function collectDomains() {
        var domains = new Array();
        <?php if ($domainsAsArray) { ?>
            var domainList = $('#<?php echo $domainId; ?> option:selected');
            domains = $.map(domainList, function(x) {
                return x.value;
            });
        <?php } else { ?>
            domains = $('#<?php echo $domainId; ?>').val().split(',');
        <?php } ?>
        return domains;
    }

    function collectNameservers() {
        var result = new Array();
        var count = $('#ns_count');
        var countValue = parseInt(count.val());
        for (var i = 0; i < countValue; i++) {
            var nsName = $('#ns_' + i).val();
            if ($.trim(nsName) != '') {
                var $ipv4 = $('#ipv4_' + i);
                var $ipv6 = $('#ipv6_' + i);
                result.push({
                    name: nsName,
                    ipv4Address: $ipv4.length > 0 ? $ipv4.val() : '',
                    ipv6Address: $ipv6.length > 0 ? $ipv6.val() : '',
                });
            }
        }
        return result;
    }

    function updateNameserversErrors() {
        var any = function(arrayOfPredicates, value) {
            var i;
            for (i = 0; i < arrayOfPredicates.length; i++) {
                if (arrayOfPredicates[i](value)) {
                    return true;
                }
            }
            return false;
        };
        // curried function (needle) => (haystack) => boolean
        var startsWith = function startsWith(needle) {
            return function(haystack) {
                return haystack.substr(0, needle.length) === needle;
            }
        };
        var nameserverAttribName = "<?php echo $nameservers ?>",
            ipv4AttribName = "<?php echo $ipv4Addresses ?>",
            ipv6AttribName = "<?php echo $ipv6Addresses ?>",
            attributePredicates = [startsWith(nameserverAttribName), startsWith(ipv4AttribName), startsWith(ipv6AttribName)];

        return function(form, attribute, data, hasError) {
            var settings = form.data("settings");
            var attributes = settings.attributes;
            $.each(attributes, function(index,attribute) {
                if (any(attributePredicates, attribute.name)) {
                    $.fn.yiiactiveform.updateInput(attribute, data, form);
                }
            });
        }
    }

    $(function() {
        $("td.nameserver .has-idn-tooltip").idnTooltip({
            type: "Nameserver"
        });
    });

</script>
<?php
echo $form->hiddenField($model, "$nameserversCount", array('id' => 'ns_count'));
echo "<table>";
for ($i = 0; $i < $maxRows; $i++) {
    if ($i < $model->$nameserversCount) {
        echo "<tr id='row_$i' style='table-row'>";
    } else {
        echo "<tr id='row_$i' style='display:none'>";
    }
    if ($indent) {
        echo "<td>&nbsp;</td>";
    }
    $nsParams = array('id' => 'ns_' . $i, 'class' => 'has-idn-tooltip');
    $nserrParams = array('id' => 'ns_' . $i . '_err', 'inputID' => 'ns_' . $i,
        'afterValidateAttribute' => "js:updateNameserversErrors()");
    $ipv4Params = array('id' => 'ipv4_' . $i);
    $ipv4errParams = array('id' => 'ipv4_' . $i . '_err', 'inputID' => 'ipv4_' . $i,
        'afterValidateAttribute' => "js:updateNameserversErrors()");
    $ipv6Params = array('id' => 'ipv6_' . $i);
    $ipv6errParams = array('id' => 'ipv6_' . $i . '_err', 'inputID' => 'ipv6_' . $i,
        'afterValidateAttribute' => "js:updateNameserversErrors()");
    $delButtonParams = array('id' => 'ns_del_buttton_' . $i,'onclick' => "javascript:removeRow(this);");
    $addButtonParams = array('id' => 'ns_add_buttton','onclick' => "javascript:appendRow()");
    $verButtonParams = array('id' => 'ns_ver_buttton',
        'onclick' => 'verifyDNS("' . $form->id . '", collectDomains(), collectNameservers()); return false');
    if ($disabled || $readonly) {
        $delButtonParams['style'] = 'display:none';
        $addButtonParams['style'] = 'display:none';
        $verButtonParams['style'] = 'display:none';
    }
    if ($disabled) {
        $disabledOpts = array('disabled' => 'disabled');
        $nsParams = array_merge($nsParams, $disabledOpts);
        $ipv4Params = array_merge($ipv4Params, $disabledOpts);
        $ipv6Params = array_merge($ipv6Params, $disabledOpts);
    }
    if ($readonly) {
        $readonlyOpts = array('readonly' => 'readonly', 'tabindex' => -1);
        $nsParams = array_merge($nsParams, $readonlyOpts);
        $ipv4Params = array_merge($ipv4Params, $readonlyOpts);
        $ipv6Params = array_merge($ipv6Params, $readonlyOpts);
    }
    if ($labels) {
        echo "<td class='nameserver-label'>";
        echo $form->label($model, "$nameservers" . "[$i]", array('for' => 'ns_' . $i,
            'label' => 'Nameserver ' . ($i + 1)));
    } else {
        echo "<td class='nameserver'>";
    }
    echo $form->error($model, "$nameservers" . "[$i]", $nserrParams);
    if ($labels) {
        echo "</td><td class='nameserver'>";
    }
    echo "<div class='idn-tooltip-container'>";
    echo ' ' . $form->textField($model, "$nameservers" . "[$i]", $nsParams);
    echo "</div></td>";
    if (isset($model->$ipv4Addresses) && isset($model->$ipv6Addresses)) {
        if ($labels) {
            echo "<td class='nameserver-label'>";
            echo $form->label($model, "$ipv4Addresses" . "[$i]", array('for' => 'ipv4_' . $i,
                'label' => 'IPv4 Address ' . ($i + 1)));
        } else {
            echo "<td class='nameserver'>";
        }
        echo $form->error($model, "$ipv4Addresses" . "[$i]", $ipv4errParams);
        if ($labels) {
            echo "</td><td class='nameserver'>";
        }
        echo ' ' . $form->textField($model, "$ipv4Addresses" . "[$i]", $ipv4Params) . "</td>";
        if ($labels) {
            echo "<td class='nameserver-label'>";
            echo $form->label($model, "$ipv6Addresses" . "[$i]", array('for' => 'ipv6_' . $i,
                'label' => 'IPv6 Address ' . ($i + 1)));
        } else {
            echo "<td class='nameserver'>";
        }
        echo $form->error($model, "$ipv6Addresses" . "[$i]", $ipv6errParams);
        if ($labels) {
            echo "</td><td class='nameserver'>";
        }
        echo ' ' . $form->textField($model, "$ipv6Addresses" . "[$i]", $ipv6Params) . "</td>";
    }
    echo "<td>";
    echo CHtml::button('Delete', $delButtonParams);
    echo "</td></tr>";
}
echo "</table>";
?>
<div class="row buttons">
    <?php
    echo CHtml::button('Add entry', $addButtonParams);
    if ($dnsCheck) {
        echo CHtml::button('Verify', $verButtonParams);
    }
    ?>
</div>
