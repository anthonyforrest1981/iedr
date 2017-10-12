<?php

require_once("IEDR-API/bus/commands/DomainTransferCommand.php");
require_once("IEDR-API/bus/commands/DomainNs.php");
require_once("IEDR-API/bus/commands/LoginCommand.php");
require_once("IEDR-API/bus/commands/LogoutCommand.php");
require_once("comm/CommandProcessor.php");
require_once("ClientClassConfig.php");
require_once("DBUtils.php");

ConfigFactory::setConfig(new ClientClassConfig());

//test ref: ????
//check that if defaults are not set that the NS remains the same as what is currently listed, even though
// reseller does have ResellerDefaultNameservers filled.
class DomainTransferSuccessWithoutDefaults extends PHPUnit_Framework_TestCase {

    public static function setUpBeforeClass() {

        $user = "XBC199-IEDR";

        $command = new LoginCommand();
        $command->setUsername($user);
        $command->setPassword("Passw0rd!");

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();

        self::assertEquals($code, '1000');
        self::assertEquals($msg, 'Command completed successfully');

    }

    public static function tearDownAfterClass() {

        $command = new LogoutCommand();
        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        $ReasonCode = $result->getReasonCode();
        $ReasonMsg = $result->getReasonMsg();

        self::assertEquals($code, '1500');
        self::assertEquals($msg, 'Command completed successfully; ending session');

    }

    /**
     * @group DomainTransferSuccess
     */
    public function testDomainTransfer() {

        $command = new DomainTransferCommand("nv-domain0120.ie");
        $command->setAuthcode("68OM3LQV7C3F");
        $command->addTechC("XBC199-IEDR");
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

        $arr = DBUtils::getTicketData("nv-domain0120.ie", "10");
        $dnses = DBUtils::getTicketDNS("nv-domain0120.ie");

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

        $this->assertEquals($dom, 'nv-domain0120.ie');
        $this->assertEquals($type, 'Transfer');
        $this->assertEquals($owner, 'XBC199-IEDR');
        $this->assertEquals($holder, 'Test Holder 0120');
        $this->assertEquals($remark, 'Domain transfer request - API.');
        $this->assertEquals($class, 'Unincorporated Association');
        $this->assertEquals($cat, 'Discretionary Name');
        $this->assertEquals($period, $expPeriod);
        $this->assertEquals($tech, 'XBC199-IEDR');
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
