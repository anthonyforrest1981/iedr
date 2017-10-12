<?php
$pg = 'Sale Request Received';
$this->pageTitle = Yii::app()->name . ' - ' . $pg;
?>
<h2><?php echo printBreadcrumbTrail($pg); ?></h2>
<?php
if (isset($model->errors)) {
    $returnUrl = 'Card declined. ' . CHtml::link("Click here to try again/another card.",
        Yii::app()->createUrl('registranttransfer/saledetails', array('errorReturnData' => true)));
    echo '<table><td>' . $model->domainName . '</td><td>' . WSAPIError::getErrorsNotEmpty($model->errors, $returnUrl) . '</td></table>';
} else {
    $countdownPeriod = Utility::getApplicationConfiguration()->secondaryMarketCountdownPeriod;
    echo 'Your Registrant Transfer request has been approved.<br><br>';
    echo 'Please note that the domain has entered the Registrant Transfer ';
    echo $countdownPeriod . '-day countdown period, ';
    echo 'during which time no modifications can be made to the domain.<br><br>';
    echo 'The new holder will be able to edit the domain at the 3pm activation time, ';
    echo $countdownPeriod . ($countdownPeriod == 1 ? ' day' : ' days') . ' from now.<br><br>';
    echo 'After the transfer has fully completed, you will be able to view the new domain holder ';
    echo 'using our WHOIS search facility <a target="_blank" href="http://www.iedr.ie/">here</a>.';
}
?>
