<?php
include_once("IEDR-API/bus/commands/DomainCreateCommand.php");
include_once("IEDR-API/bus/commands/DomainNs.php");
include_once("IEDR-API/bus/commands/DomainHolder.php");
include_once("IEDR-API/bus/commands/TicketModifyCommand.php");
include_once("IEDR-API/bus/commands/TicketInfoCommand.php");
include_once("IEDR_BPR.php");

class TicketModifyNsFailure extends IEDR_BPR_Test {

    public function testTicketCreateForTest() {
        $command = new DomainCreateCommand("ticketmodifynsfailure.ie");
        $command->addAdmin("XBC189-IEDR");
        $command->addTech("XBC189-IEDR");
        $command->addNs(new DomainNs("ns1.dns.ie"));
        $command->addNs(new DomainNs("ns2.dns.ie"));
        $holder = new DomainHolder();
        $holder->setName("Holder");
        $holder->setType("Company");
        $holder->setRemark("Remark");
        $command->setHolder($holder);

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();

        $this->assertEquals($code, '1000');
        $this->assertEquals($msg, 'Command completed successfully');
    }

    public function testTicketModifyEmptyNs() {
        $command = new TicketModifyCommand();
        $command->setName("ticketmodifynsfailure.ie");
        $command->setNssRems(array(new DomainNs("ns2.dns.ie")));
        $command->setNssAdds(array(new DomainNs("")));

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        $ReasonCode = $result->getReasonCode();
        $ReasonMsg = $result->getReasonMsg();

        $this->assertEquals($ReasonCode, '162');
        $this->assertEquals($ReasonMsg, 'Nameserver name syntax error');
        $this->assertEquals($code, '2004');
        $this->assertEquals($msg, 'Parameter value syntax error');
    }

}
