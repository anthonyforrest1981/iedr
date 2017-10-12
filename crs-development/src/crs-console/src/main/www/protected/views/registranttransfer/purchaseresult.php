<?php
$pg = 'Purchase Request Received';
$this->pageTitle = Yii::app()->name . ' - ' . $pg;
?>
<h2><?php echo printBreadcrumbTrail($pg); ?></h2>
<table>
<?php
if (isset($model->errors)) {
    $returnUrl = 'Card declined. ' . CHtml::link("Click here to try again/another card.",
        Yii::app()->createUrl('registranttransfer/purchasedetails', array('errorReturnData' => true)));
    echo '<td>' . $model->domainName . '</td><td>' . WSAPIError::getErrorsNotEmpty($model->errors, $returnUrl) . '</td>';
} else {
    $link = $this->createUrl('registranttransfer/viewbuyrequest', array('id' => $model->requestId));
    echo '<tr><td>Thank you for your request. A member of our Registration Services Team will review this ticket and respond within 1-2 working days.</td>';
    echo '<td>Click here <a href="' . $link . '">' . $model->requestId . '</a> to view/modify the request.</td></tr>';
}
if ($uploadResult->hasErrors()) {
    if (isset($uploadResult->model['error'])) {
        echo '<tr><td colspan="2">' . WSAPIError::getErrorsNotEmpty($uploadResult->model['error']) . '</td></tr>';
    } else
        foreach ($uploadResult->getHumanReadableResults() as $docResult) {
            echo '<tr><td>' . $docResult['documentName'] . '</td><td>' . $docResult['status'] . '</td></tr>';
        }
} else if (!$uploadResult->isEmpty()) {
    echo '<tr><td colspan="2">All documents uploaded successfully</td></tr>';
}
?>
</table>
