<?php
include_once("IEDR-API/bus/commands/DomainCreateCommand.php");
include_once("IEDR-API/bus/commands/DomainNs.php");
include_once("IEDR-API/bus/commands/DomainHolder.php");
include_once("IEDR-API/bus/commands/TicketModifyCommand.php");
include_once("IEDR-API/bus/commands/TicketHolder.php");
include_once("IEDR-API/bus/commands/TicketInfoCommand.php");
include_once("IEDR_BPR.php");

class TicketModifyHolderSuccess extends IEDR_BPR_Test

    //test ref: 1.60.1.1
    //CREAT TICKET BEFORE RUNNING TEST

{

    /**
     * @group CreateTicketSuccess
     */
    public function testDomainCreateForTest() {

        $command = new DomainCreateCommand("new-example-0035.ie");
        $command->addAdmin("XBC189-IEDR");
        $command->addTech("XBC189-IEDR");
        $command->addNs(new DomainNs("ns1.onet.pl"));
        $command->setUnitPeriod("y", "1");
        $command->addNs(new DomainNs("ns1.new-example-0082.ie"));
        $holder = new DomainHolder();
        $holder->setName("Ala Kowalska");
        $holder->setType("Blogger/Other");
        $holder->setRemark("Bla bla bla");
        $command->setHolder($holder);

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();

        $this->assertEquals($code, '1000');
        $this->assertEquals($msg, 'Command completed successfully');
    }


    //Test: Ticket Modify successfully, change holder new-example-0035.ie
    /**
     * @group AmendTicketSuccess
     */
    public function testTicketModifyHolderSuccess() {

        $command = new TicketModifyCommand();
        $command->setName("new-example-0035.ie");
        $holder = new TicketHolder();
        $holder->setName("Tree Falling Limited");
        $command->setHolder($holder);

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        $ReasonCode = $result->getReasonCode();
        $ReasonMsg = $result->getReasonMsg();

        $this->assertEquals($code, '1000');
        $this->assertEquals($msg, 'Command completed successfully');
    }

    /**
     * @group CreateTicketSuccessDbTest
     * @depends testTicketModifyHolderSuccess
     */
    public function testTicketModifyHolderSucessDbTest() {

        $arr = DBUtils::getTicketData("new-example-0035.ie", "1");

        $dom = $arr['D_Name'];
        $type = $arr['T_Type'];
        $owner = $arr['Bill_NH'];
        $astatus = $arr['Admin_Status'];
        $remark = $arr['T_Remark'];
        $holder = $arr['D_Holder'];
        $class = $arr['T_Class'];
        $cat = $arr['T_Category'];
        $period = $arr['Period'];
        $expPeriod = $arr['expPeriod'];
        $admin = $arr['Admin_NH1'];
        $tech = $arr['Tech_NH'];
        /*$ns1=$arr['DNS_Name1'];
        $ns2=$arr['DNS_Name2'];
        $ns3=$arr['DNS_Name3'];
        $ip1=$arr['DNS_IP1'];
        $ip2=$arr['DNS_IP2'];
        $ip3=$arr['DNS_IP3'];*/
        $checkout = $arr['CheckedOut'];

        $this->assertEquals($dom, 'new-example-0035.ie');
        $this->assertEquals($type, 'Registration');
        $this->assertEquals($astatus, 'Renew');
        $this->assertEquals($owner, 'XBC189-IEDR');
        $this->assertEquals($holder, 'Tree Falling Limited');
        $this->assertEquals($class, 'Natural Person');
        $this->assertEquals($cat, 'Discretionary Name');
        $this->assertEquals($period, $expPeriod);
        $this->assertEquals($admin, 'XBC189-IEDR');
        $this->assertEquals($tech, 'XBC189-IEDR');
        /*$this->assertEquals($ns1, 'ns1.onet.pl');
        $this->assertEquals($ns2, 'ns1.new-example-0082.ie');
        $this->assertEquals($ns3, NULL);
        $this->assertEquals($ip1, NULL);
        $this->assertEquals($ip2, NULL);
        $this->assertEquals($ip3, NULL);*/
        $this->assertEquals($checkout, 'NO');

    }

}
