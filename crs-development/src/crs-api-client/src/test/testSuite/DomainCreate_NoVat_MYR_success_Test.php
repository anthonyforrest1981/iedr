<?php
include_once("IEDR-API/bus/commands/DomainCreateCommand.php");
include_once("IEDR-API/bus/commands/DomainNs.php");
include_once("IEDR-API/bus/commands/DomainHolder.php");
include_once("IEDR_BPR_II.php");

class DomainRegMYR_XOE551 extends IEDR_BPR_Test_II {

    //test ref: 1.1.1.3
    /**
     * @group CreateTicketSuccess
     */

    public function testDomainCreateMYR() {

        $command = new DomainCreateCommand("no-vat-myr.ie");
        $command->addAdmin("XOE551-IEDR");
        $command->addTech("XOE551-IEDR");
        $command->addNs(new DomainNs("ns1.example0005.ie"));
        $command->addNs(new DomainNs("ns2.example0005.ie"));
        $command->setUnitPeriod("y", "6");
        $holder = new DomainHolder();
        $holder->setName("Some Limited Company Name");
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

        $arr = DBUtils::getTicketData("no-vat-myr.ie", "6");

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

        $this->assertEquals($dom, 'no-vat-myr.ie');
        $this->assertEquals($type, 'Registration');
        $this->assertEquals($owner, 'XNV498-IEDR');
        $this->assertEquals($holder, 'Some Limited Company Name');
        $this->assertEquals($remark, 'Bla bla bla');
        //TODO find out if the fact that API doesnt correct the case is an issue
        $this->assertEquals($class, 'Body Corporate (Ltd,PLC,Company)');
        $this->assertEquals($cat, 'Discretionary Name');
        $this->assertEquals($period, $expPeriod);
        $this->assertEquals($admin, 'XOE551-IEDR');
        $this->assertEquals($tech, 'XOE551-IEDR');
        /*$this->assertEquals($ns1, 'ns1.example0005.ie');
        $this->assertEquals($ns2, 'ns2.example0005.ie');
        $this->assertEquals($ip1, NULL);
        $this->assertEquals($ip2, NULL);
        $this->assertEquals($ip3, NULL);*/
        $this->assertEquals($checkout, 'NO');

    }

}
