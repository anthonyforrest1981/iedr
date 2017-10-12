<?php

include_once("IEDR-API/bus/commands/AccountCheckDepositCommand.php");
include_once("IEDR-API/bus/responses/AccountChkDepositDataResponse.php");
include_once("IEDR_BPR.php");

class AccountCheckFunds extends IEDR_BPR_Test

    //test ref: 1.38.1.1, 1.38.1.2

{

    //Test: Check that the deposit balance command repsonds correctly EUR1 (+ dbTest)
    /**
     * @group InfoSuccess
     */

    public function testCheckDepBalance() {

        $command = new AccountCheckDepositCommand();

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();

        $this->assertEquals($code, '1000');
        $this->assertEquals($msg, 'Command completed successfully');
    }

    /**
     * @depends testCheckDepBalance
     * @group PaymentSuccessDbTest
     */
//    public function testCheckDepBalanceDbTest() {
//
//        $arr = DBUtils::getDepositValue("XBC189-IEDR");
//
//        //TODO set dep values as global vars
//
//        $open = $arr['o'];
//        $close = $arr['c'];
//
//        $this->assertEquals($open, 2000 /*22000*/);
//        $this->assertEquals($close, 2000 /*24000*/);
//
//    }
}
