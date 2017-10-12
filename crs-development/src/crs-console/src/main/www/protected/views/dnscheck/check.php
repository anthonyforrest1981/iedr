<?php
$pg = 'DNS Check';
$this->pageTitle = Yii::app()->name . ' - ' . $pg;
?>
<h2><?php echo printBreadcrumbTrail($pg); ?></h2>

<script type="text/javascript" src="js/jquery-update-value.js"></script>
<script type="text/javascript" src="js/tooltip.js"></script>
<script type="text/javascript" src="js/punycode.min.js"></script>
<script type="text/javascript" src="js/idn-tools.js"></script>
<script type="text/javascript" src="js/idn-tooltip.js"></script>
<script>

$(document).ready(function() {
    $("#DnsCheckModel_domainName").idnTooltip({
        type: "Domain"
    });
});

</script>

<?php
if (Yii::app()->user->hasFlash('error')) {
    Utility::printFlashError(Yii::app()->user->getFlash('error'));
}
if (Yii::app()->user->hasFlash('failure')) {
    $failureMessage = "DNS Check failed" . '<pre style="max-height: 150px; overflow: auto;">'
        . Yii::app()->user->getFlash('failure') . '</pre>';
    Utility::printFlashError($failureMessage);
}
if (Yii::app()->user->hasFlash('notice')) {
    Utility::printFlashNotice(Yii::app()->user->getFlash('notice'));
}
?>
<p><b>To check if your DNS records are correctly configured, please fill in the fields below.</b></p>

<div class="form">
    <?php
    $form = $this->beginWidget('CActiveForm', array('id' => 'DnsCheckForm',
        'enableAjaxValidation' => true,
        'clientOptions' => array(
            'validateOnSubmit' => true,
            'validateOnChange' => true)));
    ?>
    <table border="1" class="formfields">
        <tbody>
            <tr>
                <td><center><?php
                    echo $form->label($model, 'domainName');
                    echo $form->error($model, 'domainName');
                ?></center></td>
                <td><div class="row idn-tooltip-container"><?php echo $form->textField($model, 'domainName'); ?>
                </div></td>
            </tr>
        </tbody>
    </table>
    <?php $this->widget('application.widgets.nameservers.nameserversWidget', array('form' => $form,
        'model' => $model, 'nameservers' => 'nameservers', 'ipv4Addresses' => 'ipv4Addresses',
        'ipv6Addresses' => 'ipv6Addresses', 'nameserversCount' => 'nameserversCount',
        'domainId' => 'DnsCheckModel_domainName', 'dnsCheck' => false)); ?>
    <br>
    <br>
    <?php $this->widget('application.widgets.recaptcha.RecaptchaWidget', array('form' => $form,
        'model' => $model, 'recaptchaResponseField' => 'recaptchaResponse'));?>
    <br>
    <div class="row buttons">
        <?php echo CHtml::submitButton('Test', array('style' => 'width:200px')); ?>
    </div>
    <?php $this->endWidget(); ?>
    <br>
    <p><b>Important Technical Information</b></p>
    <ul>
        <li><p>To host your dot IE website, you’ll need at least <?php echo $model->minRows ?> DNS records.</p></li>
        <li><p>The DNS records you provide must be configured with authoritative zones to host your website,
            in accordance with RFC protocols <a target="_blank" href="https://tools.ietf.org/html/rfc1034">1034</a>
            & <a target="_blank" href="https://tools.ietf.org/html/rfc1035">1035</a>.</p></li>
        <li><p>If you have any questions, or your DNS records fail the test, please contact your DNS provider
            in the first instance, as they will need to correct the issue for you.</p></li>
        <li><p>Otherwise, please contact our Registrations Department by telephone on 01-2365400,
            or by email to <a href="mailto:registrations@iedr.ie">registrations@iedr.ie</a>.</p></li>
    </ul>
</div>
