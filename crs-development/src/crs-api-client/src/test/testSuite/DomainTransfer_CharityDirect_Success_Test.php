<?php

include_once("IEDR-API/bus/commands/DomainTransferCommand.php");
include_once("IEDR-API/bus/commands/DomainNs.php");
include_once("IEDR_BPR_II.php");

//test ref: 1.6.1.2 (alt)
class DomainTransferCharityDirectState extends IEDR_BPR_Test_II {

    /**
     * @group DomainTransferSuccess
     */
    public function testDomainTransfer() {

    $command = new DomainTransferCommand("direct-account-0401.ie");
    $command->setAuthcode("68OM3LQV7C3F");
    $command->setDefaults("true");

    $response = CommandProcessor::process($command);
    $result = $response->getResult();
    $code = $result->getCode();
    $msg = $result->getMsg();
    $ReasonCode = $result->getReasonCode();
    $ReasonMsg = $result->getReasonMsg();

    /* $this->assertEquals($ReasonCode, '301');
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

$arr = DBUtils::getTicketData("direct-account-0401.ie", "1");

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

$this->assertEquals($dom , 'direct-account-0401.ie');
           $this->assertEquals($type, 'Transfer');
           $this->assertEquals($owner, 'XNV498-IEDR');
           $this->assertEquals($holder, 'Test Holder 0401');
           $this->assertEquals($class, 'Sole Trader');
           $this->assertEquals($cat, 'Personal Trading Name');
           $this->assertEquals($remark, 'Domain transfer request - API.');
           $this->assertEquals($period, NULL);
           $this->assertEquals($tech, 'XNV498-IEDR');
           $this->assertEquals($ns1, 'ns1.nv-domain0001.ie');
           $this->assertEquals($ns2, 'ns2.nv-domain0001.ie');
           $this->assertEquals($ns3, NULL);
           $this->assertEquals($ip1, NULL);
           $this->assertEquals($ip2, NULL);
           $this->assertEquals($ip3, NULL);
           $this->assertEquals($checkout, 'NO');

}*/

}
