<?php
$pg = 'DNS Modification';
$this->pageTitle = Yii::app()->name . ' - ' . $pg;
?>
<h2><?php echo printBreadcrumbTrail($pg); ?></h2>
<?php
if (Yii::app()->user->hasFlash('error')) {
    Utility::printFlashError(Yii::app()->user->getFlash('error'));
}
if (Yii::app()->user->hasFlash('success')) {
    Utility::printFlashNotice(Yii::app()->user->getFlash('success'));
}
?>
<p>
    This tool is designed to give Registrars the facility to modify DNS
    records associated with .ie domain names. All domains must have a
    zone configured correctly on each of the Nameservers you are
    modifying the domains to. Correctly configured DNS must adhere to <a
        target="_blank" href="http://www.ietf.org/rfc/rfc1035.txt">RFC1035</a>.
    This system will not permit domain names be modified that are deemed
    to be incorrectly configured.
</p>
<p>
    <b>Please Note:</b> Some Nameservers may not respond in a timely
    fashion. As such, this facility can take some time in verifying the
    DNS for multiple domain names. Please be patient.
</p>
<?php
$form = $this->beginWidget('CActiveForm', array('id' => 'BulkDNSModForm','enableAjaxValidation' => true,
    'clientOptions' => array('validateOnSubmit' => true)));
$form->focus = array($model,'domainlist');
?>
<div class="form">
    <table border="1" class="formfields">
        <tbody>
            <tr>
                <td><b>Please enter the new Nameservers in the text boxes below - Glue text fields will be disabled/not used for bulk updates</b></td>
            </tr>
            <tr>
                <td>
                    <?php echo CHtml::errorSummary($model); ?>
                </td>
            </tr>
            <tr>
                <td><?php
                echo $form->labelEx($model, 'Domains: ');
                echo $form->hiddenField($model, 'domainlist', array('id' => 'domainlist',
                    'value' => $model->domainlistAsString()));
                echo CHtml::encode(join(', ', $model->domainlist));
                ?></td>
            </tr>
            <tr>
                <td>
                    <?php echo $form->labelEx($model,'Nameservers'); ?>
                </td>
            </tr>
            <tr>
                <td>
                <?php
                $this->widget('application.widgets.nameservers.nameserversWidget', array('form' => $form,'model' => $model,
                    'labels' => false,'indent' => true,'domainId' => 'domainlist',
                    ));
                ?>
                </td>
            </tr>
    </tbody>
    </table>
</div>
<div class="row buttons">
    <?php echo CHtml::submitButton('Save'); ?>
</div>
<?php $this->endWidget(); ?>
