<!-- START protected/views/grid/results.php -->
<?php
/**
 * View page for Grid Domain-Operation Results
 *
 * @category NRC
 * @package IEDR_New_Registrars_Console
 * @author IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license http://www.iedr.ie/ (C) IEDR 2011
 * @version CVS: $Id:$
 * @link https://statistics.iedr.ie/
 * @see GridController, GridSelectionModel
 */
$pg = 'Confirmation';
$this->pageTitle = Yii::app()->name . ' - ' . $pg;
Yii::log('RESUL IN GRID');
?>
<h2><?php echo printBreadcrumbTrail($pg); ?></h2>
<div class="form">
<?php
$errs = $model->getErrors('domainlist');
if ($errs != null) {
    echo WSAPIError::getErrorsNotEmpty($errs);
} else {
    CommandResultUtility::renderResult($this, $model);
}
?>
</div>
<br>
<?php echo AuthOnlyBaseController::returnToUrlButton($model->returnurl); ?>
<!-- END protected/views/grid/results.php -->
