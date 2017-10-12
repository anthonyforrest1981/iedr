<?php
include_once("IEDR-API/bus/commands/AccountPayCommand.php");
include_once("IEDR-API/bus/responses/AccountPayDataResponse.php");
include_once("IEDR-API/bus/commands/CreditCard.php");
include_once("IEDR_BPR.php");
require_once("paymentCheck.php");

class AccountPayCC extends IEDR_BPR_Test {

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
        $command->addDomain('example0070.ie');
        $command->addDomain('example0082.ie');
        $command->addDomain('example0108.ie');
        $command->addDomain('example0124.ie');
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

        $this->assertEquals($fee, $price * 4);
        $this->assertEquals($vat, $vat_format * 4);
        $this->assertEquals($total, ($price + $vat_format) * 4);

    }

    /**
     * @depends testAccountPay1
     * @group PaymentSuccessDbTest
     */
    public function testAccountPay1dbTest() {

        $arr = array('example0070.ie', 'example0082.ie', 'example0108.ie', 'example0124.ie');

        foreach ($arr as $dom) {
            $payment = DBUtils::getAccountPayStatus("XBC189-IEDR", $dom, "RM9Yr", "renewal", "Credit Card");
            validatePaymentDate($payment);
        }
    }

}
