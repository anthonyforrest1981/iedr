<?php

include_once("IEDR-API/bus/commands/DomainTransferCommand.php");
include_once("IEDR-API/bus/commands/DomainNs.php");
include_once("IEDR_BPR.php");

//test ref: 1.6.1.16
//check that if defaults are not set that the NS remains the same as what is currently listed
class DomainTransferSuccessWithNoDefaults extends IEDR_BPR_Test {

    /**
     * @group DomainTransferSuccess
     */
    public function testDomainTransfer() {

        $command = new DomainTransferCommand("nv-domain0119.ie");
        $command->setAuthcode("68OM3LQV7C3F");
        $command->addTechC("XBC189-IEDR");
        $command->setPeriodWithUnit("y", "10");

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();

        $this->assertEquals($code, '1000');
        $this->assertEquals($msg, 'Command completed successfully');
    }

    /**
     * @depends testDomainTransfer
     * @group DomainTransferSuccess
     */
    public function testDomainTransferDbTest() {

        $arr = DBUtils::getTicketData("nv-domain0119.ie", "10");
        $dnses = DBUtils::getTicketDNS("nv-domain0119.ie");

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
        $checkout = $arr['CheckedOut'];

        $this->assertEquals($dom, 'nv-domain0119.ie');
        $this->assertEquals($type, 'Transfer');
        $this->assertEquals($owner, 'XBC189-IEDR');
        $this->assertEquals($holder, 'Test Holder 0119');
        $this->assertEquals($remark, 'Domain transfer request - API.');
        $this->assertEquals($class, 'Discretionary Applicant');
        $this->assertEquals($cat, 'Discretionary Name');
        $this->assertEquals($period, $expPeriod);
        $this->assertEquals($tech, 'XBC189-IEDR');
        $this->assertEquals($checkout, 'NO');

        $this->assertEquals(count($dnses), 3);
        $this->assertEquals($dnses[0]['dns'], 'ns1.abc1.ie');
        $this->assertNull($dnses[0]['ipv4']);
        $this->assertNull($dnses[0]['ipv6']);
        $this->assertEquals($dnses[1]['dns'], 'ns2.abc1.ie');
        $this->assertNull($dnses[1]['ipv4']);
        $this->assertNull($dnses[1]['ipv6']);
        $this->assertEquals($dnses[2]['dns'], 'ns3.abc1.ie');
        $this->assertNull($dnses[2]['ipv4']);
        $this->assertNull($dnses[2]['ipv6']);
    }

}
