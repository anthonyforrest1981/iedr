<?php

include_once("IEDR-API/bus/commands/DomainTransferCommand.php");
include_once("IEDR-API/bus/commands/DomainNs.php");
include_once("IEDR_BPR.php");

//test ref: 1.6.1.1
class DomainTransferSuccessWithNoDefaultSet extends IEDR_BPR_Test {

    /**
     * @group DomainTransferSuccess
     */
    public function testDomainTransfer() {

        $command = new DomainTransferCommand("direct-account-0483.ie");
        $command->setAuthcode("68OM3LQV7C3F");
        $command->addTechC("XBC189-IEDR");
        $command->addNs(new DomainNs("ns1.example0005.ie"));
        $command->addNs(new DomainNs("ns2.example0005.ie"));
        $command->setPeriodWithUnit("y", "1");

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();

        $this->assertEquals($code, '1000');
        $this->assertEquals($msg, 'Command completed successfully');
    }

    /**
     * @depends testDomainTransfer
     * @group CreateTicketSuccessDbTest
     */
    public function testDomainTransferDbTest() {

        $arr = DBUtils::getTicketData("direct-account-0483.ie", "1");

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

        $this->assertEquals($dom, 'direct-account-0483.ie');
        $this->assertEquals($type, 'Transfer');
        $this->assertEquals($owner, 'XBC189-IEDR');
        $this->assertEquals($holder, 'Test Holder 0483');
        $this->assertEquals($remark, 'Domain transfer request - API.');
        $this->assertEquals($class, 'Sole Trader');
        $this->assertEquals($cat, 'Registered Business Name');
        $this->assertEquals($period, $expPeriod);
        $this->assertEquals($tech, 'XBC189-IEDR');
        /*$this->assertEquals($ns1, 'ns1.example0005.ie');
        $this->assertEquals($ns2, 'ns2.example0005.ie');
        $this->assertEquals($ns3, NULL);
        $this->assertEquals($ip1, NULL);
        $this->assertEquals($ip2, NULL);
        $this->assertEquals($ip3, NULL);*/
        $this->assertEquals($checkout, 'NO');

    }

}
