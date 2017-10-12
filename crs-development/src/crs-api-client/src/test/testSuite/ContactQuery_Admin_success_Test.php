<?php

include_once("IEDR-API/bus/commands/ContactQueryCommand.php");
include_once("IEDR-API/bus/commands/DomainNs.php");
include_once("IEDR_BPR.php");

class ContactQueryAdmin extends IEDR_BPR_Test {

    //Test: ContactQuery successfull
    /**
     * @group InfoSuccess
     */

    public function testContactQuery() {
        //todo: check number of records in responce and create test for each query type

        $command = new ContactQueryCommand();
        $command->setQueryType("admin");
        // other types of query
        //$command->setQueryType("all");
        //$command->setQueryType("tech");
        //$command->setPage(1);
        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        //TODO verify results
    }

}