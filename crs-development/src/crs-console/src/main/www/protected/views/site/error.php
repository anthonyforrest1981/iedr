<?php
/**
 * View page for Errors
 *
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   CVS: $Id:$
 * @link      https://statistics.iedr.ie/
 * @see       SiteController::actionError()
 */
$this->pageTitle = Yii::app()->name . ' - Error';
?>
<h2>Error <?php /*echo $code;*/ ?></h2>
<div class="error">
    <?php /*echo CHtml::encode($message);*/ ?>
</div>
<pre>
    <?php
// print_r($model);
Yii::app()->end();
?>
</pre>