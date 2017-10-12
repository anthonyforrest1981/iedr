<?php
include_once("IEDR-API/bus/commands/AccountDepositFundsCommand.php");
include_once("IEDR-API/bus/responses/AccountDepositFundsDataResponse.php");
include_once("IEDR-API/bus/commands/CreditCard.php");
include_once("IEDR-API/bus/commands/AccountPayCommand.php");
include_once("IEDR-API/bus/responses/AccountPayDataResponse.php");
include_once("IEDR_BPR.php");
require_once("paymentCheck.php");

//test ref: 1.24.1.1

class AccountPayMulti extends IEDR_BPR_Test {

    const PERIOD = 10;
    const PRODUCT = "RM10Yr";

    private function getMultipleDomainsPayCommand() {
        $command = new AccountPayCommand();
        for ($i = 800; $i < 902; $i++) {
            $command->addDomain('example0' . $i . '.ie');
        }
        $command->setPeriod(self::PERIOD);
        return $command;
    }

    private function countRequiredFunds($payCommand) {
        $domainsCount = count($payCommand->getDomains());
        $period = intval($payCommand->getPeriod());
        $periodPrice = DBUtils::getProductPrice(self::PRODUCT);
        return $domainsCount * $periodPrice;
    }

    /**
     * @group PaymentSuccess
     */
    public function testAccountPayFAIL2() {
        $command = $this->getMultipleDomainsPayCommand();
        $requiredFunds = $this->countRequiredFunds($command);
        $depositRow = DBUtils::getDepositValue("XBC189-IEDR");
        $initialDeposit = floatval($depositRow['c']);
        $this->assertLessThan($requiredFunds, $initialDeposit);

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        $ReasonCode = $result->getReasonCode();
        $ReasonMsg = $result->getReasonMsg();

        $this->assertEquals($ReasonCode, '813');
        $this->assertEquals($ReasonMsg, 'Not enough deposit funds');
        $this->assertEquals($code, '2104');
        $this->assertEquals($msg, 'Billing failure');

    }

    /**
     * @group PaymentSuccess
     */
    public function testDepositFundsSuccess() {
        $payCommand = $this->getMultipleDomainsPayCommand();
        $requiredFunds = $this->countRequiredFunds($payCommand);

        $depositRow = DBUtils::getDepositValue("XBC189-IEDR");
        $initialDeposit = floatval($depositRow['c']);

        $command = new AccountDepositFundsCommand();
        $command->setValue($requiredFunds);
        $creditCard = new CreditCard();
        $creditCard->setCardHolder("John Doe");
        $creditCard->setCardNumber("4263971921001307");
        $creditCard->setExpiryDate(date("Y-m"), time() + 365 * 24 * 60 * 60);
        $creditCard->setCardType("VISA");
        $command->setCreditCard($creditCard);

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();

        $this->assertEquals($code, '1000');
        $this->assertEquals($msg, 'Command completed successfully');

        $depositRow = DBUtils::getDepositValue("XBC189-IEDR");
        $open = floatval($depositRow['o']);
        $amt = floatval($depositRow['a']);
        $type = $depositRow['type'];
        $close = floatval($depositRow['c']);

        $this->assertEquals($open, $initialDeposit);
        $this->assertEquals($close, $initialDeposit + $requiredFunds);
        $this->assertEquals($type, 'TOPUP');
        $this->assertEquals($amt, $requiredFunds);
    }

    /**
     * @depends testAccountPayFAIL2
     * @group PaymentSuccess
     */
    public function testAccountPay2() {
        $command = $this->getMultipleDomainsPayCommand();
        $domainsCount = count($command->getDomains());

        $response = CommandProcessor::process($command);

        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();

        $this->assertEquals($code, '1000');
        $this->assertEquals($msg, 'Command completed successfully');

        $fee = $response->getFee();
        $vat = $response->getVat();
        $total = $response->getTotal();
        $price = DBUtils::getProductPrice(self::PRODUCT);
        $vatrate = DBUtils::getVatRate();
        $vat_amt = $price * $vatrate;
        $vat_format = number_format($vat_amt, 2);
        $this_price = $price * $domainsCount;
        $this_vat = $vat_format * $domainsCount;
        $this_total = $this_price + $this_vat;
        settype($this_vat, "string");
        settype($this_price, "string");
        settype($this_total, "string");

        $this->assertEquals($fee, $price * $domainsCount);
        $this->assertEquals($vat, $this_vat);
        $this->assertEquals($total, $this_total);
    }

    /**
     * @depends testAccountPay2
     * @group PaymentSuccess
     */
    public function testAccountPay2dbTest() {
        $payCommand = $this->getMultipleDomainsPayCommand();
        $domains = $payCommand->getDomains();
        foreach ($domains as $domain) {
            $payment = DBUtils::getAccountPayStatus("XBC189-IEDR", $domain, self::PRODUCT,
                "renewal", "Deposit");
            validatePaymentDate($payment);
        }
    }

}
