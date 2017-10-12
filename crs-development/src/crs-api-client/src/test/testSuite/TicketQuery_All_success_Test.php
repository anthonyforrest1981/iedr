<?php

include_once("IEDR-API/bus/commands/TicketQueryCommand.php");
include_once("IEDR-API/bus/commands/DomainNs.php");
include_once("IEDR_BPR.php");

class TicketQueryAll extends IEDR_BPR_Test

    //test ref: 1.69.1.4

{

    //Test: TicketQuery successfull
    /**
     * @group InfoSuccess
     */

    public function testTicketQuery() {
        //todo: check number of records in responce and create test for each query type

        $command = new TicketQueryCommand();
        $command->setQueryType("all");
        // other types of query
        //$command->setQueryType("registrations");
        //$command->setQueryType("modifications");
        //$command->setQueryType("deletions");
        //$command->setPage(1);
        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        //TODO verify results
    }

}
