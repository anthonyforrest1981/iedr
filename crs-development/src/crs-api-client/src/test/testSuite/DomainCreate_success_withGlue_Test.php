<?php
include_once("IEDR-API/bus/commands/DomainCreateCommand.php");
include_once("IEDR-API/bus/commands/DomainNs.php");
include_once("IEDR-API/bus/commands/DomainHolder.php");
include_once("IEDR_BPR.php");

//test ref: 1.1.1.22
class DomainReg14 extends IEDR_BPR_Test {

    /**
     * @group CreateTicketSuccess
     */
    public function testDomainCreateWithGlue() {

        $command = new DomainCreateCommand("new-example-0085.ie");
        $command->addAdmin("XBC189-IEDR");
        $command->addTech("XBC189-IEDR");
        $command->addNs(new DomainNs("ns1.onet.pl"));
        $command->setUnitPeriod("y", "1");
        $command->addNs(new DomainNs("ns1.new-example-0085.ie", "12.13.13.13"));
        $holder = new DomainHolder();
        $holder->setName("Ala Kowalska");
        $holder->setType("Company");
        //$holder->setRemark("Bla bla bla");
        $command->setHolder($holder);

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();

        $this->assertEquals($code, '1000');
        $this->assertEquals($msg, 'Command completed successfully');
    }

    /**
     * @depends testDomainCreateWithGlue
     * @group CreateTicketSuccessDbTest
     */
    public function testDomainCreateWithGlueDbTest() {

        $arr = DBUtils::getTicketData("new-example-0085.ie", "1");

        $dom = $arr['D_Name'];
        $type = $arr['T_Type'];
        $owner = $arr['Bill_NH'];
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

        $this->assertEquals($dom, 'new-example-0085.ie');
        $this->assertEquals($type, 'Registration');
        $this->assertEquals($owner, 'XBC189-IEDR');
        $this->assertEquals($holder, 'Ala Kowalska');
        //$this->assertEquals($remark, 'Bla bla bla');
        //TODO find out if the fact that API doesnt correct the case is an issue
        $this->assertEquals($class, 'Body Corporate (Ltd,PLC,Company)');
        $this->assertEquals($cat, 'Discretionary Name');
        $this->assertEquals($period, $expPeriod);
        $this->assertEquals($admin, 'XBC189-IEDR');
        $this->assertEquals($tech, 'XBC189-IEDR');
        /*$this->assertEquals($ns1, 'ns1.onet.pl');
        $this->assertEquals($ns2, 'ns1.new-example-0085.ie');
        $this->assertEquals($ip1, NULL);
        $this->assertEquals($ip2, '12.13.13.13');
        $this->assertEquals($ip3, NULL);*/
        $this->assertEquals($checkout, 'NO');

    }

    //Test: TicketInfo Success on a valid ticket

    /**
     * @group InfoSuccess
     */

    /*public function testTicketInfo()
    {
$dom = "new-example-0082.ie";

$command = new TicketInfoCommand("$dom");

$response = CommandProcessor::process($command);
$result = $response->getResult();
$code = $result->getCode();
$msg = $result->getMsg();

$this->assertEquals($code , '1000');
           $this->assertEquals($msg , 'Command completed successfully');
    }*/

}
