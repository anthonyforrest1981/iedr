<?php
require_once("IEDR-API/bus/commands/AccountPayCommand.php");
require_once("IEDR-API/bus/responses/AccountPayDataResponse.php");
require_once("IEDR-API/bus/commands/CreditCard.php");
require_once("IEDR_BPR.php");
require_once("paymentCheck.php");

class AccountPayIM extends IEDR_BPR_Test

    //test ref: 1.4.1.14

{

    /**
     * @group PaymentSuccess
     */
    public function testAccountPay1() {

        $command = new AccountPayCommand();

        $creditCard = new CreditCard();
        $creditCard->setCardHolder("joe bloggs");
        $creditCard->setCardNumber("4263971921001307");
        $creditCard->setExpiryDate(date("Y-m"), time() + 365 * 24 * 60 * 60);
        $creditCard->setCardType("VISA");
        $command->setCreditCard($creditCard);
        $command->addDomain('example0586.ie');
        $command->setPeriod('9');

        $response = CommandProcessor::process($command);

        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();

        $this->assertEquals($code, '1000');
        $this->assertEquals($msg, 'Command completed successfully');

        $fee = $response->getFee();
        $vat = $response->getVat();
        $total = $response->getTotal();
        $price = DBUtils::getProductPrice('RM9Yr');
        $vatrate = DBUtils::getVatRate();
        $vat_amt = $price * $vatrate;
        $vat_format = number_format($vat_amt, 2);

        $this->assertEquals($fee, $price);
        $this->assertEquals($vat, $vat_format);
        $this->assertEquals($total, ($price + $vat_format));

    }

    /**
     * @depends testAccountPay1
     * @group PaymentSuccessDbTest
     */
    public function testAccountPay1dbTest() {

        $dom = 'example0586.ie';
        $arr = DBUtils::getAccountPayStatus("XBC189-IEDR", $dom, "RM9Yr", "renewal", "Credit Card");

        validatePaymentDate($arr);
    }

    /**
     * @depends testAccountPay1
     * @group PaymentSuccessDbTest
     */
    public function testAccountPay3dbTest() {

        $arr = DBUtils::getState("example0586.ie");
        $state = $arr['State'];
        $this->assertEquals($state, '534');

    }

}
