<?php

class RecaptchaValidator extends CValidator {

    public static $RECAPTCHA_ERROR = 'Please confirm that you\'re not a robot';

    protected function validateAttribute($model, $attribute) {
        try {
            $result = $this->sendVerificationRequest($model->$attribute);
            if (!$result->success) {
                $model->addError($attribute, self::$RECAPTCHA_ERROR);
            }
        } catch (Exception $e) {
            $model->addError($attribute, "We couldn't verify your response, please try again. " .
                "If the problem persists, please contact us.");
        }
        // Recaptcha response can be validated only once, so no matter the result, we want to clear it.
        $model->$attribute = null;
    }

    private function sendVerificationRequest($recaptchaResponse) {
        $url = Yii::app()->params['recaptcha_verification_url'];
        $secret = Yii::app()->params['recaptcha_secret_key'];
        $data = array('secret' => $secret, 'response' => $recaptchaResponse);
        $options = array(
            'http' => array(
                'header' => "Content-type: application/x-www-form-urlencoded\r\n",
                'method' => 'POST',
                'content' => http_build_query($data)
            )
        );
        $proxy = Yii::app()->params['proxyServerUri'];
        if (!empty($proxy)) {
            $options['http']['proxy'] = $proxy;
            $options['http']['request_fulluri'] = true;
        }
        $context = stream_context_create($options);
        $resultString = file_get_contents($url, false, $context);
        if ($resultString === false) {
            throw new Exception("Failed to connect to the verification service.");
        } else {
            return json_decode($resultString);
        }
    }

}
