<?php
include_once("IEDR-API/bus/commands/AccountPayCommand.php");
include_once("IEDR-API/bus/responses/AccountPayDataResponse.php");
include_once("IEDR_BPR.php");
require_once("paymentCheck.php");

//test req 234
class AccountPayDepositRenewOnce extends IEDR_BPR_Test {

    /**
     * @group PaymentSuccess
     */
    public function testAccountPayRenewOnce() {

        $command = new AccountPayCommand();

        $command->addDomain('example0242.ie');
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
     * @depends testAccountPayRenewOnce
     * @group PaymentSuccessDbTest
     */

    public function testAccountPayRenewOncedbTest() {
        $dom = 'example0242.ie';
        $arr = DBUtils::getAccountPayStatus("XBC189-IEDR", $dom, "RM1Yr", "renewal", "Deposit");

        validatePaymentDate($arr);
    }

    /**
     * @depends testAccountPayRenewOnce
     * @group PaymentSuccessDbTest
     */

    public function testAccountPayRenewOncedbTest2() {

        $arr = DBUtils::getState("example0169.ie");

        $state = $arr['State'];

        $this->assertEquals($state, '49');

    }

}
