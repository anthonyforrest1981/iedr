<tr>
    <td>
        <?php
        echo $this->form->labelEx($this->model, 'ownerType');
        echo $this->form->error($this->model, 'ownerType');
        ?>
    </td>
    <td>
        <div class="row">
            <?php echo $this->form->dropDownList($this->model, 'ownerType',
                getOwnerTypeOptions($this->paymentForRegistration), array('class' => 'expand')); ?>
        </div>
    </td>
</tr>
<?php
    if ($this->paymentForRegistration) {
?>
<tr id="charityRow" style="display: none">
    <td>
        <?php
        echo $this->form->label($this->model, 'charitycode', array('required' => true));
        echo $this->form->error($this->model, 'charitycode');
        ?>
    </td>
    <td>
        <div class="row"><?php echo $this->form->textField($this->model, 'charitycode', array('maxlength'=>'')); ?></div>
        <div class="hint" style="display:none">Please note that charity domains can only be registered for a one year period at a time.</div>
    </td>
</tr>
<?php
    }
?>
<tr>
    <td>
        <?php
        echo $this->form->label($this->model, 'isOwnerFromIreland');
        echo $this->form->error($this->model, 'isOwnerFromIreland');
        ?>
    </td>
    <td>
        <div class="row"><?php echo $this->form->checkbox($this->model,'isOwnerFromIreland'); ?></div>
    </td>
</tr>
<tr>
    <td><div class="lastRowOfSegment"><label>We will need</label></div></td>
    <td><div class="lastRowOfSegment"><div class="strong" id="weWillNeed"></div></div></td>
</tr>

<script type="text/javascript">
    (function() {
        var modelName = '<?php echo get_class($this->model); ?>';
        var weWillNeed = {
            1: [
                'Your verifiable company number or a copy of your certificate of incorporation.',
                'Your verifiable company number or a copy of your certificate of incorporation. Proof that you are offering services or products to people in the island of Ireland (e.g. trade invoices).'
            ],
            2: [
                'One item to show that you are registered for trading (e.g. RBN or VAT number or tax clearance certificate). If you do not have this, you should provide proof of ID (e.g. passport) and proof of Irish address (e.g. utility bill) instead.',
                'One item to show that you are registered for trading (e.g. VAT number or tax certificate). Proof that you are offering services or products to people in the island of Ireland (e.g. trade invoices).'
            ],
            3: [
                'A copy of your letterhead or a screenshot/link from a social media page.',
                'A copy of your letterhead or a screenshot/link from a social media page. Proof that you are offering services or products to people in the island of Ireland (e.g. trade invoices).'
            ],
            4: [
                'Your roll number or a copy of your letterhead.',
                'Your roll number or a copy of your letterhead. Proof that you are offering services or products to people in the island of Ireland (e.g. trade invoices).'
            ],
            5: [
                'If you already hold a .ie domain, you don\'t need to include anything. If this is your first registration, please submit a copy of your letterhead.',
                'A copy of your letterhead. Proof that you are offering services or products to people in the island of Ireland.'
            ],
            6: [
                'Your charity number/certificate.',
                'Your charity number/certificate. Proof that you are offering services or products to people in the island of Ireland (e.g. trade invoices). NOTE Only Irish charities can avail of waived domain registration fees under our Charity Policy.'
            ],
            7: [
                'Proof of Irish ID or Irish address.',
                'Proof of Irish ID or Irish address.'
            ]
        };

        var updateWeWillNeedAndPaymentSection = function() {
            var ownerType = parseInt($('#' + modelName + '_ownerType').get(0).value, 10);
            var ownerFromIreland = $('#' + modelName + '_isOwnerFromIreland').get(0).checked;
            var ownerFromIrelandIndex = ownerFromIreland ? 0 : 1;
            var newWeWillNeed = weWillNeed[ownerType][ownerFromIrelandIndex];
            $('#weWillNeed').html(newWeWillNeed);
            <?php
            if ($this->paymentForRegistration) {
            ?>
            var charityOwnerTypes = [<?php echo join(', ', getCharityOwnerTypeIds()); ?>];
            if ($.inArray(ownerType, charityOwnerTypes) !== -1) {
                if (ownerFromIreland) {
                    $('#paymentWidget').hide();
                    $('#charityRow .hint').show();
                } else {
                    $('#paymentWidget').show();
                    $('#charityRow .hint').hide();
                }
                $('#charityRow').show();
            } else {
                $('#charityRow').hide();
                $('#paymentWidget').show();
            }
            <?php
            }
            ?>
        }

        $(document).ready(function() {
            updateWeWillNeedAndPaymentSection();
            $('#' + modelName + '_ownerType, #' + modelName + '_isOwnerFromIreland').bind('change', function() {
                updateWeWillNeedAndPaymentSection();
            });
        });
    })();
</script>
