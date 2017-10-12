<?php
include_once("IEDR-API/bus/commands/DomainCreateCommand.php");
include_once("IEDR-API/bus/commands/DomainNs.php");
include_once("IEDR-API/bus/commands/DomainHolder.php");

include_once("IEDR_BPR.php");

class DomainRegNonAscii extends IEDR_BPR_Test {

    /**
     * @group CreateTicketSuccess
     */
    public function testDomainCreateSuccess() {

        $command = new DomainCreateCommand("nonascii.ie");
        $command->addAdmin("XOE550-IEDR");
        $command->addTech("XBC189-IEDR");
        $command->addNs(new DomainNs("ns1.example0005.ie"));
        $command->addNs(new DomainNs("ns2.example0005.ie"));
        $holder = new DomainHolder();
        $holder->setName("Eoin \xc3\x93 Conch\xc3\xbair");
        $holder->setType("Blogger/Other");
        $holder->setRemark("Eoin \xc3\x93 Conch\xc3\xbair Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
        $command->setHolder($holder);

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

        $arr = DBUtils::getTicketData("nonascii.ie", "1");

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

        $this->assertEquals($dom, "nonascii.ie");
        $this->assertEquals($type, "Registration");
        $this->assertEquals($owner, "XBC189-IEDR");
        $this->assertEquals($holder, "Eoin \xc3\x93 Conch\xc3\xbair");
        $this->assertEquals($remark,
            "Eoin \xc3\x93 Conch\xc3\xbair Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
        $this->assertEquals($class, "Natural Person");
        $this->assertEquals($cat, "Discretionary Name");
        $this->assertEquals($period, $expPeriod);
        $this->assertEquals($admin, "XOE550-IEDR");
        $this->assertEquals($tech, "XBC189-IEDR");
        /*$this->assertEquals($ns1, "ns1.example0005.ie");
        $this->assertEquals($ns2, "ns2.example0005.ie");
        $this->assertEquals($ns3, NULL);
        $this->assertEquals($ip1, NULL);
        $this->assertEquals($ip2, NULL);
        $this->assertEquals($ip3, NULL);*/
        $this->assertEquals($checkout, "N");

    }

}
