<?php
include_once("IEDR-API/bus/commands/DomainCreateCommand.php");
include_once("IEDR-API/bus/commands/DomainNs.php");
include_once("IEDR-API/bus/commands/DomainHolder.php");
include_once("IEDR_BPR.php");

//test ref: 1.1.1.11
class DomainReg11 extends IEDR_BPR_Test {

    /**
     * @group CreateTicketSuccess
     */

    public function testDomainCreateMYR() {

        $command = new DomainCreateCommand("shoe-boxes.ie");
        $command->addAdmin("XBC189-IEDR");
        $command->addTech("XBC189-IEDR");
        $command->addNs(new DomainNs("ns1.example0005.ie"));
        $command->addNs(new DomainNs("ns2.example0005.ie"));
        $command->setUnitPeriod("y", "5");
        $holder = new DomainHolder();
        $holder->setName("Ala Kowalska");
        $holder->setType("Company");
        $holder->setRemark("Bla bla bla");
        $command->setHolder($holder);

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();

        $this->assertEquals($code, '1000');
        $this->assertEquals($msg, 'Command completed successfully');
    }

    /**
     * @depends testDomainCreateMYR
     * @group CreateTicketSuccessDbTest
     */
    public function testDomainCreateMYRDbTest() {

        $arr = DBUtils::getTicketData("shoe-boxes.ie", "5");

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

        $this->assertEquals($dom, 'shoe-boxes.ie');
        $this->assertEquals($type, 'Registration');
        $this->assertEquals($owner, 'XBC189-IEDR');
        $this->assertEquals($holder, 'Ala Kowalska');
        $this->assertEquals($remark, 'Bla bla bla');
        //TODO find out if the fact that API doesnt correct the case is an issue
        $this->assertEquals($class, 'Body Corporate (Ltd,PLC,Company)');
        $this->assertEquals($cat, 'Discretionary Name');
        $this->assertEquals($period, $expPeriod);
        $this->assertEquals($admin, 'XBC189-IEDR');
        $this->assertEquals($tech, 'XBC189-IEDR');
        /*$this->assertEquals($ns1, 'ns1.example0005.ie');
        $this->assertEquals($ns2, 'ns2.example0005.ie');
        $this->assertEquals($ip1, NULL);
        $this->assertEquals($ip2, NULL);
        $this->assertEquals($ip3, NULL);*/
        $this->assertEquals($checkout, 'NO');

    }

}
