<?php
include_once("IEDR-API/bus/commands/DomainCreateCommand.php");
include_once("IEDR-API/bus/commands/DomainNs.php");
include_once("IEDR-API/bus/commands/DomainHolder.php");
include_once("IEDR-API/bus/commands/TicketDeleteCommand.php");
include_once("IEDR_BPR.php");

class TicketDeleteSuccess extends IEDR_BPR_Test {

    //Test: Successfully create a ticket (1 dependancy) ticket-to-delete.ie (+dbTest)
    /**
     * @group CreateTicketSuccess
     */
    public function testDomainCreateforTestTicketDelete() {

        $command = new DomainCreateCommand("ticket-to-delete.ie");
        $command->addAdmin("XBC189-IEDR");
        $command->addTech("XBC189-IEDR");
        $command->addNs(new DomainNs("ns1.onet.pl"));
        $command->addNs(new DomainNs("ns1.ntg1-new.ie"));
        $command->addNs(new DomainNs("ns3.ntg1-new.ie"));
        $holder = new DomainHolder();
        $holder->setName("Extra Long name for the ticket holder name goes here");
        $holder->setType("Blogger/Other");
        $holder->setRemark("aklsjdlk aslkjalksj dalkjaslkdjlk asdlkjaslkdjlaksjd aslkdjasld aslkdjalskdjsd");
        $command->setHolder($holder);

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        //TODO db test
        $this->assertEquals($code, '1000');
        $this->assertEquals($msg, 'Command completed successfully');
    }
    //Test: Successfully delete the ticket ticket-to-delete.ie
    /**
     * @group AmendTicketSuccess
     * @depends testDomainCreateforTestTicketDelete
     */
    public function testTicketDelete() {

        $command = new TicketDeleteCommand("ticket-to-delete.ie");

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();
        //TODO db test
        $this->assertEquals($code, '1000');
        $this->assertEquals($msg, 'Command completed successfully');
    }
    //TODO dbTest to check ticket deleted

}
