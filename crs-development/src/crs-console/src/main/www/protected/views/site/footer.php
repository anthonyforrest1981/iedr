<!-- START protected/views/site/footer.php -->
<?php
/**
 * View page for shared page footer
 *
 * @category NRC
 * @package IEDR_New_Registrars_Console
 * @author IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license http://www.iedr.ie/ (C) IEDR 2011
 * @version CVS: $Id:$
 * @link https://statistics.iedr.ie/
 * @see view/layouts/main.php
 */
?>
<div id="footer">
    IE Domain Registry CLG. Registered Office:
    2 Harbour Square, Crofton Road, D&uacute;n Laoghaire,
    Co. Dublin, A96 D6R0, Ireland.<br />
    Registered No: 315315 ::
    VAT No: IE 6335315V ::
    Phone: +353 1 2365400 ::
    Fax: +353 1 2300365 ::
    IE Domain Registry CLG &copy; <?php echo date('Y'); ?>
    <br />
</div>
<?php
if (isset($_GET['permission'])) {
    if ($_GET['permission'] == 'true') {
        ?>
<script>
      alert('You don`t have permissions to use this module or functionality');
   </script>
<?php
    }
}
?>
<!-- END protected/views/site/footer.php -->
