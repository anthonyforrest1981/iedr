<?php

class UploaderResultModelWrapper {
    public static $status_to_human_readable = array(CRSDocumentAppService_uploadStatusVO::_OK => "Uploaded OK",
        CRSDocumentAppService_uploadStatusVO::_FILE_TOO_BIG => "File too big",
        CRSDocumentAppService_uploadStatusVO::_WRONG_FILE_TYPE => "Wrong file type",
        CRSDocumentAppService_uploadStatusVO::_UPLOAD_INSUFFICIENT_PERMISSIONS_FOR_DOMAIN => "Uploader is not bill_c",
        CRSDocumentAppService_uploadStatusVO::_UPLOAD_INSUFFICIENT_PERMISSIONS_FOR_BUY_REQUEST => "You don't have permission to upload to this buy request",
        DocumentsModel::MISSING_DOMAINNAMES_UPLOAD_STATUS => "No valid domains selected for document",
        DocumentsModel::CANNOT_CREATE_TMP_FILE_UPLOAD_STATUS => "Could not upload file, try again later");
    public $model;

    public function __construct($new_model = null) {
        $this->model = $new_model;
    }

    public function isEmpty() {
        return is_null($this->model) || (isset($this->model['result']) && empty($this->model['result']));
    }

    public function hasErrors() {
        if (is_null($this->model))
            return false;
        return isset($this->model['error']) || count($this->getDocsWithErrors());
    }

    public function getDocsWithErrors() {
        return array_filter($this->model['result'], function ($e) {
            return $e->status != CRSDocumentAppService_uploadStatusVO::_OK;
        });
    }

    public function getHumanReadableResults() {
        return array_map(function ($elem) {
            return array('documentName' => $elem->documentName,
                'status' => isset(UploaderResultModelWrapper::$status_to_human_readable[$elem->status]) ? UploaderResultModelWrapper::$status_to_human_readable[$elem->status] : "Unknown error");
        }, $this->model['result']);
    }

    public function getSuccessSummary() {
        return 'Documents uploaded successfully';
    }

    public function getErrorSummary() {
        $message = 'There were errors uploading some documents:<ul>';
        if (isset($this->model['error'])) {
            $message = $message . '<li>' . WSAPIError::getErrorsNotEmpty($this->model['error']) . '</li>';
        } else {
            $message = $message . implode(array_map(function ($e) {
                return "<li>" . $e['documentName'] . ": " . $e['status'] . "</li>";
            }, $this->getHumanReadableResults()));
        }
        $message = $message . '</ul>';
        return $message;
    }

}
