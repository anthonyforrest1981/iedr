<?php

class CRSNicHandleAppService_service {

    static public function get(&$result, &$errs, $user, $nic) {
        $errs = null;
        $result = new CRSNicHandleAppService_nicHandleVO();
        $result->nicHandleId = $nic;
        $result->status = "Active";
        if ($nic === "XY999-IEDR") {
            $errs = "Nic handle not found: " . $nic;
        }
    }

}