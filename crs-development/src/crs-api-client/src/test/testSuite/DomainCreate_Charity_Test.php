<?php
include_once("IEDR-API/bus/commands/DomainCreateCommand.php");
include_once("IEDR-API/bus/commands/DomainNs.php");
include_once("IEDR-API/bus/commands/DomainHolder.php");
include_once("IEDR_BPR.php");

//test ref: 1.1.1.2

class DomainReg10Charity extends IEDR_BPR_Test {

    /**
     * @group CreateTicketSuccess
     */
    public function testDomainCreateSuccess() {
        $command = new DomainCreateCommand("picture-cd.ie");
        $command->addAdmin("XOE550-IEDR");
        $command->addTech("XBC189-IEDR");
        $command->addNs(new DomainNs("ns1.example0005.ie"));
        $command->addNs(new DomainNs("ns2.example0005.ie"));
        $holder = new DomainHolder();
        $holder->setName("Big Charity Organisation");
        $holder->setType("Charity");
        $holder->setRemark("Charity Reg");
        $command->setHolder($holder);
        $command->setCharity("CHY12345");

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();

        $this->assertEquals($code, '1000');
        $this->assertEquals($msg, 'Command completed successfully');

    }

    /**
     * @depends testDomainCreateSuccess
     * @group CreateTicketSuccessDbTest
     */
    public function testDomainCreateSucessDbTest() {
        $arr = array('picture-cd.ie');

        foreach ($arr as $dom) {
            $arr = DBUtils::getTicketData("$dom", "1");
        }

        $dom1 = $arr['D_Name'];
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
        $CharityCode = $arr['CharityCode'];
        $checkout = $arr['CheckedOut'];

        $this->assertEquals($dom, $dom1);
        $this->assertEquals($type, 'Registration');
        $this->assertEquals($owner, 'XBC189-IEDR');
        $this->assertEquals($holder, 'Big Charity Organisation');
        $this->assertEquals($remark, 'Charity Reg');
        $this->assertEquals($class, 'Unincorporated Association');
        $this->assertEquals($cat, 'Discretionary Name');
        $this->assertEquals($period, $expPeriod);
        $this->assertEquals($admin, 'XOE550-IEDR');
        $this->assertEquals($tech, 'XBC189-IEDR');
        $this->assertEquals($CharityCode, 'CHY12345');
        /*$this->assertEquals($ns1, 'ns1.example0005.ie');
        $this->assertEquals($ns2, 'ns2.example0005.ie');
        $this->assertEquals($ns3, NULL);
        $this->assertEquals($ip1, NULL);
        $this->assertEquals($ip2, NULL);
        $this->assertEquals($ip3, NULL);*/
        $this->assertEquals($checkout, 'NO');

    }

}
