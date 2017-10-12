<?php
/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */
include_once("ResponseParser.php");

class ResDataParser extends ResponseParser {

    function fromDomNode($node) {
        $node = self::findElementNode($node);
        switch ($node->nodeName) {
            case "domain:chkData":
                include_once("DomainChkDataResponseParser.php");
                $obj = DomainChkDataResponseParser::fromDomNode($node);
                break;
            case "domain:appData":
                include_once("DomainAppDataResponseParser.php");
                $obj = DomainAppDataResponseParser::fromDomNode($node);
                break;
            case "domain:creData":
                include_once("DomainCreDataResponseParser.php");
                $obj = DomainCreDataResponseParser::fromDomNode($node);
                break;
            case "domain:infData":
                include_once("DomainInfDataResponseParser.php");
                $obj = DomainInfDataResponseParser::fromDomNode($node);
                break;
            case "domain:resData":
                include_once("DomainResDataResponseParser.php");
                $obj = DomainResDataResponseParser::fromDomNode($node);
                break;
            case "ticket:infData":
                include_once("TicketInfDataResponseParser.php");
                $obj = TicketInfDataResponseParser::fromDomNode($node);
                break;
            case "ticket:resData":
                include_once("TicketResDataResponseParser.php");
                $obj = TicketResDataResponseParser::fromDomNode($node);
                break;
            case "contact:infData":
                include_once("ContactInfDataResponseParser.php");
                $obj = ContactInfDataResponseParser::fromDomNode($node);
                break;
            case "contact:creData":
                include_once("ContactCreDataResponseParser.php");
                $obj = ContactCreDataResponseParser::fromDomNode($node);
                break;
            case "contact:resData":
                include_once("ContactResDataResponseParser.php");
                $obj = ContactResDataResponseParser::fromDomNode($node);
                break;
            case "account:payOfflineData":
                include_once("AccountPayOfflineDataResponseParser.php");
                $obj = AccountPayOfflineDataResponseParser::fromDomNode($node);
                break;
            case "account:payOnlineData":
                include_once("AccountPayOnlineDataResponseParser.php");
                $obj = AccountPayOnlineDataResponseParser::fromDomNode($node);
                break;
            case "account:payFromDepositData":
                include_once("AccountPayFromDepositDataResponseParser.php");
                $obj = AccountPayFromDepositDataResponseParser::fromDomNode($node);
                break;
            case "account:depositFundsData":
                include_once("AccountDepositFundsDataResponseParser.php");
                $obj = AccountDepositFundsDataResponseParser::fromDomNode($node);
                break;
            case "account:chkDepositData":
                include_once("AccountChkDepositDataResponseParser.php");
                $obj = AccountChkDepositDataResponseParser::fromDomNode($node);
                break;
            case "account:msdReActivationData":
                include_once("AccountPayMsdReActivationDataResponseParser.php");
                $obj = AccountPayMsdReActivationDataResponseParser::fromDomNode($node);
                break;
            case "account:payData":
                include_once("AccountPayDataResponseParser.php");
                $obj = AccountPayDataResponseParser::fromDomNode($node);
                break;
            case "account:resData":
                include_once("AccountResDataResponseParser.php");
                $obj = AccountResDataResponseParser::fromDomNode($node);
                break;
            case "registrantTransferBuyRequest:requestData":
                include_once("RegistrantTransferBuyRequestRequestDataResponseParser.php");
                $obj = RegistrantTransferBuyRequestRequestDataResponseParser::fromDomNode($node);
                break;
            case "registrantTransferBuyRequest:resData":
                include_once("RegistrantTransferBuyRequestResDataResponseParser.php");
                $obj = RegistrantTransferBuyRequestResDataResponseParser::fromDomNode($node);
                break;
            case "registrantTransferBuyRequest:infoData":
                include_once("RegistrantTransferBuyRequestInfoDataResponseParser.php");
                $obj = RegistrantTransferBuyRequestInfoDataResponseParser::fromDomNode($node);
                break;
            case "registrantTransferSellRequest:resData":
                include_once("RegistrantTransferSellRequestResDataResponseParser.php");
                $obj = RegistrantTransferSellRequestResDataResponseParser::fromDomNode($node);
                break;
            case "registrantTransferSellRequest:requestData":
                include_once("RegistrantTransferSellRequestDataResponseParser.php");
                $obj = RegistrantTransferSellRequestDataResponseParser::fromDomNode($node);
                break;
            case "registrantTransferSellRequest:infoData":
                include_once("RegistrantTransferSellRequestInfoDataResponseParser.php");
                $obj = RegistrantTransferSellRequestInfoDataResponseParser::fromDomNode($node);
                break;
            default:
                throw new Exception("Unknown resData tag: ". $node->nodeName);
        }

        return $obj;
    }
}
