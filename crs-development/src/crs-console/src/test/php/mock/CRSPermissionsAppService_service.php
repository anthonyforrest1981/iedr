<?php

class CRSPermissionsAppService_service {

    static public function getUserLevel(&$result, &$errs, $user) {
        $result = 2;
        $errs = null;
        return true;
    }

}