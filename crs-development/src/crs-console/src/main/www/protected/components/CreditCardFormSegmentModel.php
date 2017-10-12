<?php

/**
 * defines CreditCardFormSegmentModel class
 *
 * @category  NRC
 * @package   IEDR_New_Registrars_Console
 * @author    IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license   http://www.iedr.ie/ (C) IEDR 2011
 * @version   CVS: $Id:$
 * @link      https://statistics.iedr.ie/
 * @see       References to other sections (if any)...
 */
/**
 * a form model for use with credit-card input
 *
 * To used used as a 'mixin' class in other form-models (based on @DynamicModelBase) whose views contain the widget @CreditCardFormSegmentWidget
 *
 * @category NRC
 * @package IEDR_New_Registrars_Console
 * @author IEDR <asd@iedr.ie>
 * @copyright 2011 IEDR
 * @license http://www.iedr.ie/ (C) IEDR 2011
 * @version Release: @package_version@
 * @link https://statistics.iedr.ie/
 * @see References to other sections (if any)...
 */
class CreditCardFormSegmentModel extends FormModelBase {
    public $rules = array();

    public function __construct() {
        $appConfig = Utility::getApplicationConfiguration();
        $userMin = $appConfig->depositMinLimit;
        $userMax = $appConfig->depositMaxLimit;
        $setupScenarios = function ($singleRule) {
            return array_merge($singleRule, array('on' => 'topup,' . CreditCardFormSegmentModel::$without_amount_scenario));
        };
        $this->rules = array_map($setupScenarios, CreditCardValidationRules::rules());
        $this->rules[] = array('euros_amount','required','on' => 'topup');
        $this->rules[] = array('euros_amount','numerical','min' => $userMin,
            'tooSmall' => 'Minimum Transaction is ' . Utility::currencyAmount($userMin),'max' => $userMax,
            'tooBig' => 'Maximum Transaction is ' . Utility::currencyAmount($userMax),
            'numberPattern' => '/^[0-9]+(\.[0-9]{1,2}){0,1}$/iu',
            'message' => '{attribute} can have only two decimal places','on' => 'topup');
    }
    /**
     * yii scenario where the amount is not entered by the client
     *
     * @var string
     * @access public
     * @static
     *
     * @see http://www.yiiframework.com/doc/api/1.1/CModel#scenario-detail
     */
    public static $without_amount_scenario = 'without_amount_scenario';
    /**
     * entered credit card number
     *
     * @var string
     * @access public
     */
    public $creditcardno;
    /**
     * entered euro amount
     *
     * @var string
     * @access public
     */
    public $euros_amount;
    /**
     * entered cardholder full name
     *
     * @var string
     * @access public
     */
    public $cardholder;
    /**
     * entered card expiry year
     *
     * @var string
     * @access public
     */
    public $exp_year;
    /**
     * Description for public
     *
     * @var string
     * @access public
     */
    public $exp_month;
    /**
     * entered card validation number
     *
     * @var string
     * @access public
     */
    public $cvn;
    public $cardtype;

    /**
     * returns yii validation rules for form validation
     *
     * Some of the rules are only active if the form scenario is the @without_amount_scenario
     *
     * @return array
     * @access public
     */
    public function rules() {
        return $this->rules;
    }

    /**
     * returns yii attribute labels for form rendering
     *
     * @return array
     * @access public
     */
    public function attributeLabels() {
        return array('cardtype' => 'Card Type','creditcardno' => 'Card Number','euros_amount' => 'Top-up Amount',
            'cardholder' => 'Card Holder','exp_year' => 'Card Expiration Year',
            'exp_month' => 'Card Expiration Month',
            // no field named 'exp_date', this is just a label to put beside the pair of Mon/Yr inputs
            'exp_date' => 'Card Expiration Month / Year','cvn' => 'Card Verification Number');
    }

    /**
     * returns an object instance of class CRSPaymentAppService_creditCardtVO, filled with the model's credit-card data
     *
     * @param DynamicModelBase $model
     *            model whose data will be copied
     * @return CRSPaymentAppService_creditCardtVO object filled with the model's credit-card data
     * @access public
     * @static
     *
     */
    static public function asWSAPICreditCardObject($model) {
        // call with a parameter being either an instance of this class or a class that has its values mixed in
        // e.g. $p = CreditCardFormSegmentModel::asWSAPICreditCardObject($m);
        $card = new CRSPaymentAppService_creditCardVO();
        $card->cardNumber = $model->creditcardno;
        $card->cardExpDate = sprintf('%02u%02u', $model->exp_month, $model->exp_year);
        $card->cardHolderName = $model->cardholder;
        switch ($model->cardtype) {
            case "MC":
                $card->cardType = 'MC';
                $card->cvnPresenceIndicator = 1;
                break;
            case "VISA":
                $card->cardType = 'VISA';
                $card->cvnPresenceIndicator = 1;
                break;
        }
        $card->cvnNumber = $model->cvn;
        return $card;
    }

}
