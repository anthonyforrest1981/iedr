<?php
include_once("IEDR-API/bus/commands/AccountPayCommand.php");
include_once("IEDR-API/bus/responses/AccountPayDataResponse.php");

include_once("IEDR_BPR.php");
require_once("paymentCheck.php");

//test ref: 1.4.1.1
class AccountPayDeposit1Year extends IEDR_BPR_Test {

    /**
     * @group PaymentSuccess
     */
    public function testAccountPay1Year() {

        $command = new AccountPayCommand();

        $command->addDomain('example0079.ie');
        $command->setPeriod("1");

        $response = CommandProcessor::process($command);

        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();

        $this->assertEquals($code, '1000');
        $this->assertEquals($msg, 'Command completed successfully');

        $fee = $response->getFee();
        $vat = $response->getVat();
        $total = $response->getTotal();
        $price = DBUtils::getProductPrice('RM1Yr');
        $vatrate = DBUtils::getVatRate();
        $vat_amt = $price * $vatrate;
        $vat_format = number_format($vat_amt, 2);
        $this_price = $price;
        $this_vat = $vat_format;
        $this_total = $this_price + $this_vat;
        settype($this_vat, "string");
        settype($this_price, "string");
        settype($this_total, "string");

        $this->assertEquals($fee, $price);
        $this->assertEquals($vat, $this_vat);
        $this->assertEquals($total, $this_total);

    }

    /**
     * @depends testAccountPay1Year
     * @group PaymentSuccessDbTest
     */

    public function testAccountPay1YeardbTest() {

        $dom = 'example0079.ie';
        $arr = DBUtils::getAccountPayStatus("XBC189-IEDR", $dom, "RM1Yr", "renewal", "Deposit");

        validatePaymentDate($arr);
    }

}
