<?php
$pg = 'Current NRP Statuses Results';
$this->pageTitle = Yii::app()->name . ' - ' . $pg;
?>
<h2><?php printBreadcrumbTrail($pg); ?></h2>
<div class="form">
   <?php
$errors = $model->getErrors('domainlist');
$whitelistedUrl = AuthOnlyBaseController::whitelistReturnUrl($model->returnurl);
$returnUrl = 'Card declined. ' . CHtml::link("Click here to try again/another card.", $whitelistedUrl);
if ($errors != null) {
    echo WSAPIError::getErrorsNotEmpty($errors, $returnUrl);
} else {
    CommandResultUtility::renderResult($this, $model);
}
if ($model->command != 'removefromvoluntary') {
    echo '<form><input type=\'button\' value=\'Print This Page\' onClick=\'window.print()\' /></form>';
}
?>
</div>
<br>
<?php echo AuthOnlyBaseController::returnToUrlButton($model->returnurl); ?>