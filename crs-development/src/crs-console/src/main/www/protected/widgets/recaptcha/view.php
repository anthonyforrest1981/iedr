<script src="https://www.google.com/recaptcha/api.js" async defer></script>
<script type="text/javascript">
    function recaptchaResponseCallback(recaptchaResponse) {
        var responseInputId =
            "<?php echo CHtml::activeId($this->model, $this->recaptchaResponseField) ?>";
        $("#" + responseInputId).val(recaptchaResponse);
        $("#" + responseInputId + "_em_").hide();
    };
</script>
<div class="g-recaptcha" data-sitekey="<?php echo Yii::app()->params['recaptcha_site_key']; ?>"
    data-callback="recaptchaResponseCallback"></div>
<?php echo $this->form->textarea($this->model, $this->recaptchaResponseField,
    array('style' => 'display: none')); ?>
<?php echo $this->form->error($this->model, $this->recaptchaResponseField); ?>
