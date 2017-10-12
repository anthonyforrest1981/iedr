<?php
include_once("IEDR-API/bus/commands/DomainCreateCommand.php");
include_once("IEDR-API/bus/commands/DomainNs.php");
include_once("IEDR-API/bus/commands/DomainHolder.php");
include_once("IEDR-API/bus/commands/TicketInfoCommand.php");

include_once("IEDR_BPR.php");

class DomainRegUnnormalizedUtf8 extends IEDR_BPR_Test {

    /**
     * @group CreateTicketSuccess
     */
    public function testDomainCreateSuccess() {

        $command = new DomainCreateCommand("unnormalized.ie");
        $command->addAdmin("XOE550-IEDR");
        $command->addTech("XBC189-IEDR");
        $command->addNs(new DomainNs("ns1.example0005.ie"));
        $command->addNs(new DomainNs("ns2.example0005.ie"));
        $holder = new DomainHolder();
        $holder->setName("Unnorma\xcc\x88lized ho\xcc\xa3lder");
        $holder->setType("Blogger/Other");
        $holder->setRemark("Unnorma\xcc\xa5lized re\xcc\x8fmark");
        $command->setHolder($holder);

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();

        $this->assertEquals($code, '1000');
        $this->assertEquals($msg, 'Command completed successfully');

    }

    public function testTicketInfoSuccess() {
        $command = new TicketInfoCommand("unnormalized.ie");

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        $holderName = $response->getHolder()->getName();
        $holderRemark = $response->getHolder()->getRemark();

        $this->assertEquals($code, '1000');
        $this->assertEquals($msg, 'Command completed successfully');

        $this->assertEquals($holderName, "Unnorm\xc3\xa4lized h\xe1\xbb\x8dlder");
        $this->assertEquals($holderRemark, "Unnorm\xe1\xb8\x81lized r\xc8\x85mark");
    }

}
