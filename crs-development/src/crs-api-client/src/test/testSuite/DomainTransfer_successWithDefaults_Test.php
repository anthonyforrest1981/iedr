<?php

include_once("IEDR-API/bus/commands/DomainTransferCommand.php");
include_once("IEDR-API/bus/commands/DomainNs.php");
include_once("IEDR_BPR.php");

//test ref: 1.6.1.17

class DomainTransferSuccessWithDefaults extends IEDR_BPR_Test {

    /**
     * @group DomainTransferSuccess
     */
    public function testDomainTransfer() {

        $command = new DomainTransferCommand("nv-domain0998.ie");
        $command->setAuthcode("68OM3LQV7C3F");
        $command->setDefaults("true");
        $command->setPeriodWithUnit("y", "5");

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        $ReasonCode = $result->getReasonCode();
        $ReasonMsg = $result->getReasonMsg();

        /*$this->assertEquals($ReasonCode, '301');
                      $this->assertEquals($ReasonMsg, '"registrar defaults not defined"');*/
        $this->assertEquals($code, '1000'/*'2303'*/);
        $this->assertEquals($msg, 'Command completed successfully'/*'Object does not exist'*/);
    }

    /**
     * @depends testDomainTransfer
     * @group CreateTicketSuccessDbTest
     */
    /*public function testDomainTransferDbTest()
{

$arr = DBUtils::getTicketData("nv-domain0998.ie", "5");

$dom=$arr['D_Name'];
$type=$arr['T_Type'];
$owner=$arr['Bill_NH'];
$remark=$arr['T_Remark'];
$holder=$arr['D_Holder'];
$class=$arr['T_Class'];
$cat=$arr['T_Category'];
$period=$arr['Period'];
$expPeriod=$arr['expPeriod'];
$admin=$arr['Admin_NH1'];
$tech=$arr['Tech_NH'];
$ns1=$arr['DNS_Name1'];
$ns2=$arr['DNS_Name2'];
$ns3=$arr['DNS_Name3'];
$ip1=$arr['DNS_IP1'];
$ip2=$arr['DNS_IP2'];
$ip3=$arr['DNS_IP3'];
$checkout=$arr['CheckedOut'];

$this->assertEquals($dom , 'nv-domain0998.ie');
           $this->assertEquals($type, 'Transfer');
           $this->assertEquals($owner, 'XBC189-IEDR');
           $this->assertEquals($holder, 'Test Holder 0998');
           $this->assertEquals($remark, 'Domain transfer request - API.');
           $this->assertEquals($class, 'Unincorporated Association');
           $this->assertEquals($cat, 'Registered Trade Mark Name');
           $this->assertEquals($period, $expPeriod);
           $this->assertEquals($tech, 'XBC189-IEDR');
           $this->assertEquals($ns1, 'ns1.example0005.ie');
           $this->assertEquals($ns2, 'ns2.example0005.ie');
           $this->assertEquals($ns3, NULL);
           $this->assertEquals($ip1, NULL);
           $this->assertEquals($ip2, NULL);
           $this->assertEquals($ip3, NULL);
           $this->assertEquals($checkout, 'NO');

}*/

}
