<?php
include_once("IEDR-API/bus/commands/AccountPayCommand.php");
include_once("IEDR-API/bus/responses/AccountPayDataResponse.php");
include_once("IEDR-API/bus/commands/CreditCard.php");
include_once("IEDR_BPR_II.php");
require_once("paymentCheck.php");

//test ref: 1.4.1.4
class AccountPayCC1YearXNV498 extends IEDR_BPR_Test_II {

    /**
     * @group PaymentSuccess
     */
    public function testAccountPayCC1YearMultiDomains() {

        $command = new AccountPayCommand();

        $creditCard = new CreditCard();
        $creditCard->setCardHolder("joe bloggs");
        $creditCard->setCardNumber("4263971921001307");
        $creditCard->setExpiryDate(date("Y-m"), time() + 365 * 24 * 60 * 60);
        $creditCard->setCardType("VISA");
        $command->setCreditCard($creditCard);
        $command->addDomain('nv-domain0014.ie');
        $command->addDomain('nv-domain0015.ie');
        $command->addDomain('nv-domain0017.ie');
        $command->addDomain('nv-domain0018.ie');
        $command->addDomain('nv-domain0038.ie');
        $command->addDomain('nv-domain0040.ie');
        $command->addDomain('nv-domain0041.ie');
        $command->setPeriod('6');

        $response = CommandProcessor::process($command);

        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();

        $this->assertEquals($code, '1000');
        $this->assertEquals($msg, 'Command completed successfully');

        $fee = $response->getFee();
        $vat = $response->getVat();
        $total = $response->getTotal();
        $price = DBUtils::getProductPrice('RM6Yr');
        $vatrate = DBUtils::getVatRateForUser('XNV498-IEDR');
        $vat_amt = $price * $vatrate;
        $vat_format = number_format($vat_amt, 2);
        $this_price = $price * 7;
        $this_vat = $vat_format * 7;
        $this_total = $this_price + $this_vat;
        settype($this_vat, "string");
        settype($this_price, "string");
        settype($this_total, "string");

        $this->assertEquals($fee, $price * 7);
        $this->assertEquals($vat, $this_vat);
        $this->assertEquals($total, $this_total);

    }

    /**
     * @depends testAccountPayCC1YearMultiDomains
     * @group PaymentSuccessDbTest
     */
    public function testAccountPayCC1YearMultiDomainsdbTest() {

        $arr = array('nv-domain0014.ie', 'nv-domain0015.ie', 'nv-domain0017.ie', 'nv-domain0018.ie',
                'nv-domain0038.ie', 'nv-domain0041.ie', 'nv-domain0040.ie');

        foreach ($arr as $dom) {
            $payment = DBUtils::getAccountPayStatus('XNV498-IEDR', $dom, "RM6Yr", "renewal", "Credit Card");
            validatePaymentDate($payment);
        }
    }

}
