<?php
include_once("IEDR-API/bus/commands/DomainCreateCommand.php");
include_once("IEDR-API/bus/commands/DomainNs.php");
include_once("IEDR-API/bus/commands/DomainHolder.php");
include_once("IEDR-API/bus/commands/TicketModifyCommand.php");
include_once("IEDR-API/bus/commands/TicketInfoCommand.php");
include_once("IEDR_BPR.php");

class TicketModifySuccess extends IEDR_BPR_Test

    //test ref: 1.60.1.2

{

    /**
     * @group AmendTicketSuccess
     */
    public function testTicketModifyHolderSuccess() {

        $command = new TicketModifyCommand();
        $command->setName("xyzlimited.ie");
        $command->setStatus("renew");

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        $ReasonCode = $result->getReasonCode();
        $ReasonMsg = $result->getReasonMsg();
        $ReasonCode = $result->getReasonCode();
        $ReasonMsg = $result->getReasonMsg();

        /*$this->assertEquals($ReasonCode, '701');
                      $this->assertEquals($ReasonMsg, 'Ticket name does not exist');*/
        $this->assertEquals($code, '1000'/*'2303'*/);
        $this->assertEquals($msg, 'Command completed successfully'/*'Object does not exist'*/);
    }

    /**
     * @group CreateTicketSuccessDbTest
     */
    /*public function testDomainCreateSucessDbTest()
{

$arr = DBUtils::getTicketData("xyzlimited.ie", "1");

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
$astatus=$arr['Admin_Status'];
$checkout=$arr['CheckedOut'];

$this->assertEquals($dom , 'xyzlimited.ie');
           $this->assertEquals($type, 'Registration');
           $this->assertEquals($owner, 'XBC189-IEDR');
           $this->assertEquals($holder, 'Test holder - Name');
           $this->assertEquals($astatus, '5');
           $this->assertEquals($admin, 'XOE550-IEDR');
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
