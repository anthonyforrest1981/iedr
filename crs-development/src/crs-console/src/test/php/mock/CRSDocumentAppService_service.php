<?php

class CRSDocumentAppService_service {

    static public function getDocumentSettings(&$result, &$errs, $user) {
        $errs = null;
        $result = (object) array('documentAllowedTypes' => 'pdf,doc,docx,jpeg,jpg,gif,png,tiff,tif,odt,bmp',
            'documentCountLimit' => 5,'documentSizeLimit' => 5);
        return true;
    }

}