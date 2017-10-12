<?php

class CRSDomainAppService_service {

    static public function isCharity(&$result, &$errs, $user, $domainName) {
        $errs = null;
        $result = false;
    }

    static public function checkAvailability(&$result, &$errs, $user, $domainName) {
        $errs = null;
    }

    static public function getCountries(&$result, &$errs, $user) {
        $result = array((object) array('name' => 'Ireland','id' => 121,'counties' => array(
            (object) array('id' => 8, 'name' => 'Co. Carlow'), (object) array('id' => 14, 'name' => 'Co. Dublin'))));
        $errs = null;
        return true;
    }

}