<?php

include_once("IEDR-API/bus/commands/ContactQueryCommand.php");
include_once("IEDR-API/bus/commands/DomainNs.php");
include_once("IEDR_BPR.php");

class ContactQueryTech extends IEDR_BPR_Test {

    //Test: ContactQueryTech successfull
    /**
     * @group InfoSuccess
     */

    public function testContactQuery() {
        //todo: check number of records in responce and create test for each query type

        $command = new ContactQueryCommand();
        $command->setQueryType("tech");
        // other types of query
        //$command->setQueryType("admin");
        //$command->setQueryType("all");
        //$command->setPage(1);

        $response = CommandProcessor::process($command);
        $result = $response->getResult();
        $code = $result->getCode();
        $msg = $result->getMsg();

        $this->assertEquals($code, '1000');
        $this->assertEquals($msg, 'Command completed successfully');

        //todo: add db test here
    }

}
