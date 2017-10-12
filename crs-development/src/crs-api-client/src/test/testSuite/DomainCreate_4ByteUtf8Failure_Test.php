<?php
include_once("IEDR-API/bus/commands/DomainCreateCommand.php");
include_once("IEDR-API/bus/commands/DomainNs.php");
include_once("IEDR-API/bus/commands/DomainHolder.php");

include_once("IEDR_BPR.php");

class DomainReg4ByteUtf8 extends IEDR_BPR_Test {

    /**
     * @group CreateTicketFailure
     */
    public function testDomainCreateFailure() {

        $command = new DomainCreateCommand("4byte-utf8.ie");
        $command->addAdmin("XOE550-IEDR");
        $command->addTech("XBC189-IEDR");
        $command->addNs(new DomainNs("ns1.example0005.ie"));
        $command->addNs(new DomainNs("ns2.example0005.ie"));
        $holder = new DomainHolder();
        $holder->setName("\xe0\x90\x8d\x88");
        $holder->setType("\xe0\x90\x8d\x88");
        $holder->setRemark("\xe0\x90\x8d\x8f");
        $command->setHolder($holder);

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();

        $this->assertEquals($code, '2005');
        $this->assertEquals($msg, 'UTF-8 validation error');

    }
}
