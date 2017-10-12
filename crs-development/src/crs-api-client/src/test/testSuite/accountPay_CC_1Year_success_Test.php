<?php
include_once("IEDR-API/bus/commands/AccountPayCommand.php");
include_once("IEDR-API/bus/responses/AccountPayDataResponse.php");
include_once("IEDR-API/bus/commands/CreditCard.php");
include_once("IEDR_BPR.php");
require_once("paymentCheck.php");

class AccountPayCC1YearTest extends IEDR_BPR_Test

    //test ref: 1.4.1.2

{

    /**
     * @group PaymentSuccess
     */
    public function testAccountPayCC1YearMultiDomains() {

        $command = new AccountPayCommand();

        $creditCard = new CreditCard();
        $creditCard->setCardHolder("joe bloggs");
        $creditCard->setCardNumber("42639719 21001307");
        $creditCard->setExpiryDate(date("Y-m"), time() + 365 * 24 * 60 * 60);
        $creditCard->setCardType("VISA");
        //$creditCard->setCvnNumber("123");
        //$creditCard->setCvnPresInd("1");
        $command->setCreditCard($creditCard);
        $command->addDomain('example0010.ie');
        $command->addDomain('example0022.ie');
        $command->addDomain('example0188.ie');
        $command->addDomain('example0224.ie');
        $command->addDomain('example0324.ie');
        $command->addDomain('example0424.ie');
        $command->addDomain('example0634.ie');
        $command->setPeriod('1');

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

        $this->assertEquals($code, '1000');
        $this->assertEquals($msg, 'Command completed successfully');
        $this->assertEquals($fee, $price * 7);
        $this->assertEquals($vat, $this_vat * 7);
        $this->assertEquals($total, $this_total * 7);
    }

    /**
     * @depends testAccountPayCC1YearMultiDomains
     * @group PaymentSuccessDbTest
     */
    public function testAccountPayCC1YearMultiDomainsdbTest() {

        $arr = array('example0010.ie', 'example0022.ie', 'example0188.ie', 'example0224.ie',
                'example0324.ie', 'example0424.ie', 'example0634.ie');

        foreach ($arr as $dom) {
            $payment = DBUtils::getAccountPayStatus("XBC189-IEDR", $dom, "RM1Yr", "renewal", "Credit Card");
            validatePaymentDate($payment);
        }
    }

}
