<!-- START protected/views/layouts/main.php -->
<?php
/**
 * Main NRC Application Page
 *
 * @category NRC
 * @package IEDR_New_Registrars_Console
 * @author IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license http://www.iedr.ie/ (C) IEDR 2011
 * @version CVS: $Id:$
 * @link https://statistics.iedr.ie/
 */
?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="language" content="en" />
<link rel="shortcut icon" href="/images/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="css/formalize.css" />
<!-- blueprint CSS framework -->
<link rel="stylesheet" type="text/css" href="css/screen.css"
    media="screen, projection" />
<link rel="stylesheet" type="text/css" href="css/print.css"
    media="print" />
<!--[if lt IE 8]>
    <link rel="stylesheet" type="text/css" href="css/ie.css" media="screen, projection" />
    <![endif]-->
<link rel="stylesheet" type="text/css" href="css/main.css" />
<link rel="stylesheet" type="text/css" href="css/dialog.css" />
<link rel="stylesheet" type="text/css" href="css/form.css" />
<link rel="stylesheet" type="text/css" href="css/progress-bar.css" />
<link rel="stylesheet" type="text/css" href="css/tooltip.css" />

    <?php Yii::app()->clientScript->registerCoreScript('jquery'); ?>

    <title><?php echo CHtml::encode($this->pageTitle); ?></title>
<!-- stuff for JqGrid4 -->
<link type="text/css" rel="stylesheet" media="screen"
    href="jqGrid4/css/redmond/jquery-ui-1.8.13.custom.css" />
<link type="text/css" rel="stylesheet" media="screen"
    href="jqGrid4/css/ui.jqgrid.css" />
<script type="text/javascript" src="jqGrid4/js/i18n/grid.locale-en.js"></script>
<script type="text/javascript" src="jqGrid4/js/jquery.jqGrid.src.js"></script>
<!-- selectric plugin -->
<script type="text/javascript" src="js/jquery.selectric.js"></script>
<link rel="stylesheet" media="screen" href="css/selectric.css" />
<!-- stuff for superfish (menu) -->
<link rel="stylesheet" media="screen" href="superfish/superfish.css" />
<link rel="stylesheet" media="screen"
    href="superfish/superfish-vertical.css" />
<link rel="stylesheet" media="screen"
    href="superfish/superfish-navbar.css" />
<script src="superfish/hoverIntent.js"></script>
<script src="superfish/superfish.js"></script>
<script>
        $(document).ready(function(){
            // ==== superfish css menu ========
            // widen the 2nd-level (navbar) superfish-menu
            $("ul.sf-menu > li > ul").width("210px");
            // before superfish-menu : set to 'active' the parents of active the menu item
            // (affects 2nd-level-navbar selection persistence)
            //$("li.active").parents("li").addClass("active");
            // do superfish-menu thing
            $("ul.sf-menu").superfish({
                //animation: {height:'show'}, // slide-down effect without fade-in
                speed: '0', // animation speed
                delay: 0, // m-seconds delay on mouseout
                pathClass: 'active'
                });
            // =================================
            // form field alignment
            $("table.formfields > tbody > tr > td").css("align","left").css("vertical-align","top");
            $("select:not([multiple]):not(.expand)").selectric({
                inheritOriginalWidth: true
            });
            $("select:not([multiple]).expand").selectric({
                inheritOriginalWidth: false
            });
        });
        $.ajaxSetup ({ cache: false });
    </script>
</head>
<body>
    <div class="container" id="page">
        <table class="header">
            <tr>
                <td rowspan="2" class="no_padding logo"><img
                    src="images/ielogo.jpg"></td>
                <td class="no_padding top">
                <?php
    if (isset(Yii::app()->user->authenticatedUser) && (Utility::getPermission() != UserPermissionLevel::UNDEFINED)) {
        ?>
                <iframe scrolling="NO" height="26" width="700px"
                        src="<?php echo $this->createUrl('site/summaryframe'); ?>"
                        class="user_panel"></iframe>
                <?php
    }
    ?>
            </td>
            </tr>
            <tr>
                <td class="bottom no_padding">
                <?php
                    $this->widget('zii.widgets.CMenu',
                        array('htmlOptions' => array('class' => 'sf-menu'),'items' => getMenu()));
                ?>
                </td>
            </tr>
            <tr>
                <td id="center" colspan="2">
                <?php $this->widget('application.widgets.userSwitch.UserSwitchWidget'); ?>
            </td>
            </tr>
        </table>
        <span class="CONTENT_STARTS" />
    <?php echo $content; ?>
    <span class="CONTENT_ENDS" />
    <?php require('protected/views/site/footer.php'); ?>
</div>
</body>
</html>
<!-- END protected/views/layouts/main.php -->
