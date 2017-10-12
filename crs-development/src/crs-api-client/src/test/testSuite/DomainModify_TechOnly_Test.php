<?php
include_once("IEDR-API/config/ConfigFactory.php");
include_once("IEDR-API/bus/commands/LoginCommand.php");
include_once("IEDR-API/bus/commands/LogoutCommand.php");
include_once("IEDR-API/bus/commands/DomainInfoCommand.php");
include_once("IEDR-API/bus/commands/DomainModifyCommand.php");
include_once("comm/CommandProcessor.php");
include_once("ClientClassConfig.php");
include_once("IEDR_BPR.php");

//test ref: 1.17.1.8
class DomainModifyTechContactSuccess extends IEDR_BPR_Test {

    /**
     * @group DomainModifySuccess
     */
    public function testDomainModifyContact() {
    $domainName = "example0999.ie";

    $command = new DomainModifyCommand();
    $command->setName($domainName);
    $command->addTechCAdd("XOE550-IEDR");
    $command->addTechCRem("XBC189-IEDR");
    $response = CommandProcessor::process($command);
    $result = $response->getResult();
    $code = $result->getCode();
    $msg = $result->getMsg();

    $this->assertEquals($code, '1000');
    $this->assertEquals($msg, 'Command completed successfully');
}

    /**
     * @depends testDomainModifyContact
     * @group DomainModifySuccessDbTest
     */
    public function testDomainModifyContactDbTest() {

    $arr = DBUtils::getModTicketData("example0999.ie");

    $type = $arr['T_Type'];
    $DHolder = $arr['DHolder'];
    $THolder = $arr['THolder'];
    $Tclass = $arr['T_Class'];
    $Tcat = $arr['T_Category'];
    $Dclass = $arr['D_Class'];
    $Dcat = $arr['D_Category'];
    $period = $arr['TPeriod'];
    $admin = $arr['Admin_NH1'];
    $tech = $arr['Tech_NH'];
    $crea = $arr['Creator_NH'];
    $real_admin = $arr['AdminNic'];
    $real_tech = $arr['TechNic'];
    $real_bill = $arr['BillNic'];
    $real_crea = $arr['CreatorNic'];
    $remark = $arr['T_Remark'];

    $this->assertEquals($type, 'Modification');
    $this->assertEquals($DHolder, $THolder);
    $this->assertEquals($remark, 'Domain modify.');
    $this->assertEquals($Tclass, $Dclass);
    $this->assertEquals($Tcat, $Dcat);
    $this->assertEquals($period, NULL);
    $this->assertEquals($admin, $real_admin);
    $this->assertEquals($crea, $real_crea);
    $this->assertEquals($tech, 'XOE550-IEDR');
}

    /**
     * @group DomainModifyFail
     * @depends testDomainModifyContact
     */
    public function testDomainModifyContactPending() {

    $command = new DomainModifyCommand('example0999.ie');
    $command->addTechCAdd("XOE550-IEDR");
    $command->addTechCRem("XBC189-IEDR");

    $response = CommandProcessor::process($command);
    $result = $response->getResult();
    $code = $result->getCode();
    $msg = $result->getMsg();
    $ReasonCode = $result->getReasonCode();
    $ReasonMsg = $result->getReasonMsg();

    $this->assertEquals($ReasonCode, '202');
    $this->assertEquals($ReasonMsg, 'Domain modification pending');
    $this->assertEquals($code, '2304');
    $this->assertEquals($msg, 'Object status prohibits operation');

}

}
