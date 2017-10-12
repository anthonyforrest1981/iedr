<?php

class CRSCommonAppService_service {

    static public function getApplicationConfiguration(&$result, &$errs) {
        $errs = null;
        $result = new MockedAppConfig();
    }

    static public function isTransferPossible(&$result, &$errs, $user, $domainName) {
        $errs = null;
        $result = true;
    }

    static public function getOwnerTypes(&$result, &$errs) {
        $errs = null;
        $result = array();
    }

}
