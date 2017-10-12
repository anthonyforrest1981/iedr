<?php
$pg = 'Request Transfer Result';
$this->pageTitle = Yii::app()->name . ' - ' . $pg;
?>
<h2><?php echo printBreadcrumbTrail($pg); ?></h2>
<table>
<?php
$model = AuthOnlyBaseController::safeDeserializeObjFromFromSession('requesttransferresult');
$returnUrl = 'Card declined.' . CHtml::link("Click here to try again/another card.", Yii::app()->createUrl('domains/requesttransferdetails', array(
    'errorReturnData' => true)));
if (isset($model->errors)) {
    echo '<td>' . $model->domainName . '</td><td>' . WSAPIError::getErrorsNotEmpty($model->errors, $returnUrl) . '</td>';
} else {
    $link = $this->createUrl('tickets/viewticket', array('id' => $model->ticket));
    echo '<tr><td> Your ticket for ' . $model->domainName . ' has been received and will be processed by our hostmasters in due course.</td>';
    echo "<td>Click here <a href=" . $link . ">" . $model->ticket . "</a> to view/modify the ticket.</td></tr>";
}
?>
</table>
