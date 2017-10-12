<?php

/**
 * Helper model for dealing with documents upload
 */
class DocumentsModel extends CFormModel {
    const FREE_FILENAME_LIMIT = 100;
    const MISSING_DOMAINNAMES_UPLOAD_STATUS = "MISSING_DOMAINNAMES";
    const CANNOT_CREATE_TMP_FILE_UPLOAD_STATUS = "CANNOT_CREATE_TMP_FILE";

    const DOMAIN_UPLOAD_SCENARIO = "domainUpload";
    const BUY_REQUEST_UPLOAD_SCENARIO = "buyRequestUpload";

    private $validated = false;
    private $allowedDomains = array();
    private $documentsDomains = array();
    private $buyRequestId;
    public $documents;
    public $uploadedFiles;
    private $docSettings;

    public function __construct() {
        $this->setDocuments(CUploadedFile::getInstancesByName('documents'));
    }

    public function getDocumentSizeLimit() {
        return $this->getDocumentSettings()->documentSizeLimit;
    }

    public function getDocumentCountLimit() {
        return $this->getDocumentSettings()->documentCountLimit;
    }

    public function getAllowedTypes() {
        return $this->getDocumentSettings()->documentAllowedTypes;
    }

    public function setAllowedDomains($allowedDomains) {
        $this->allowedDomains = $this->getValidAndNormalizedDomains($allowedDomains);
    }

    public function setBuyRequestId($buyRequestId) {
        $this->buyRequestId = $buyRequestId;
    }

    public function getDocumentsDomains() {
        return $this->documentsDomains;
    }

    public function setDocumentsDomains($documentsDomains) {
        // The browser sends selected checkboxes as array( document => array(domain => "on") ),
        // but we want this setter to also work for array( document => array(domain) ).
        $this->documentsDomains = array();
        foreach ($documentsDomains as $documentName => $domains) {
            $isArrayAssociative = array_values($domains) !== $domains;
            if ($isArrayAssociative) {
                $domains = array_keys($domains);
            }
            $this->documentsDomains[$documentName] = $domains;
        }
    }

    private function setDocuments($uploadedFiles) {
        $this->uploadedFiles = $uploadedFiles;
        $this->documents = array();
        foreach ($uploadedFiles as $file) {
            $this->documents[] = new UploadedDocument($file);
        }
    }

    public function rules() {
        return array(
            array('documents', 'checkAndNormalizeFileNames'),
            array('documents', 'checkFiles',
                'filesAttribute' => 'uploadedFiles',
                'allowEmpty' => true,
                'types' => $this->getAllowedTypes(),
                'maxFiles' => $this->getDocumentCountLimit(),
                'maxSize' => $this->getDocumentSizeLimit()),
            array('documentsDomains', 'filter',
                'filter' => array($this, 'filterDisallowedDocumentsAndDomains')));
    }

    public function onAfterValidate() {
        $this->validated = true;
    }

    public function isSetup() {
        if ($this->scenario === DocumentsModel::BUY_REQUEST_UPLOAD_SCENARIO) {
            return $this->validated && !is_null($this->buyRequestId);
        } else {
            return $this->validated;
        }
    }

    public function uploadDocuments($purpose) {
        if (!$this->isSetup()) {
            throw new CException("Trying to upload documents without first initializing documents model");
        }
        $serviceResult = array();
        $notUploadedDocumentsResult = array();
        $uploads = array();
        $this->prepareUploads($uploads, $notUploadedDocumentsResult);
        if (!empty($uploads)) {
            $backend_errors = array();
            CRSDocumentAppService_service::handleUpload($serviceResult, $backend_errors,
                Yii::app()->user->authenticatedUser, $uploads, $purpose);
            if (count($backend_errors) > 0) {
                Yii::log("Failed to upload documents: " . WSAPIError::getErrorsNotEmpty($backend_errors), "error", ' ');
                $this->addError("documents", "Error while saving documents on the server. Please notify IEDR of the problem you've just encountered.");
                return array('error' => $backend_errors);
            }
        } else {
            $uploadCount = count($this->documents);
            Yii::log("There were $uploadCount documents uploaded, but none was suitable for upload", "warning");
        }
        if (!is_array($serviceResult)) {
            $serviceResult = array($serviceResult);
        }
        return array('result' => array_merge($serviceResult, $notUploadedDocumentsResult));
    }

    private function prepareUploads(&$uploads, &$notUploadedDocumentsResult) {
        foreach ($this->documents as $document) {
            $documentName = $document->getName();
            if ($this->scenario === DocumentsModel::DOMAIN_UPLOAD_SCENARIO &&
                    empty($this->documentsDomains[$documentName])) {
                Yii::log("Document $documentName was uploaded with no matching domain names, was ignored as a result", "warning");
                $notUploadedDocumentsResult[] = $this->createUploaderResult($documentName,
                        DocumentsModel::MISSING_DOMAINNAMES_UPLOAD_STATUS);
                continue;
            }
            $filename = $this->prepareFileForUpload($document);
            if (is_null($filename)) {
                Yii::log("Could not prepare upload of file $documentName", "error");
                $notUploadedDocumentsResult[] = $this->createUploaderResult($documentName,
                    DocumentsModel::CANNOT_CREATE_TMP_FILE_UPLOAD_STATUS);
                continue;
            }
            $domains = $this->scenario === DocumentsModel::DOMAIN_UPLOAD_SCENARIO ?
                $this->documentsDomains[$documentName] : null;
            $uploads[] = $this->prepareUpload($filename, $documentName, $domains,
                $this->buyRequestId);
        }
    }

    private function prepareUpload($filename, $documentName, $domains, $buyRequestId) {
        $upload = new CRSDocumentAppService_documentUploadVO();
        $upload->filename = $filename;
        $upload->domains = $domains;
        $upload->buyRequestId = $buyRequestId;
        return $upload;
    }

    private function getValidAndNormalizedDomains($domainsAllowedForAssociation) {
        $allowedDomains = is_array($domainsAllowedForAssociation) ?
            $domainsAllowedForAssociation : array($domainsAllowedForAssociation);
        $allowedDomains = array_map(function ($domain) {
            try {
                return Utf8Validator::validateAndNormalizeUtf8($domain);
            } catch (Exception $e) {
                Yii::log("Invalid allowed domain passed: " . $domain, "error");
                return null;
            }
        }, $allowedDomains);
        $allowedDomains = array_filter($allowedDomains, function ($domain) {
            return $domain !== null;
        });
        return $allowedDomains;
    }

    private function getDocumentSettings() {
        if (is_null($this->docSettings)) {
            $response = null;
            $backend_errors;
            CRSDocumentAppService_service::getDocumentSettings($response, $backend_errors,
                Yii::app()->user->authenticatedUser);
            if (empty($backend_errors)) {
                $this->docSettings = $response;
            } else {
                Yii::log("Failed to get document settings: " . WSAPIError::getErrorsNotEmpty($backend_errors), "error", ' ');
                throw new CException("Filed to get document settings");
            }
        }
        return $this->docSettings;
    }

    private function prepareFileForUpload($document) {
        $upload_dir = Yii::app()->params['uploader_share_dir'];
        if (!is_dir($upload_dir)) {
            Yii::log("Mount for uploading document is not a directory: $upload_dir", "error", ' ');
            throw new CException("Missing upload directory setting");
        }
        $path_parts = pathinfo($document->getName());
        // protection against path traversal
        $filename = $path_parts['basename'];
        $filename_fullpath = join(DIRECTORY_SEPARATOR, array($upload_dir,$filename));
        $i = 1;
        while (file_exists($filename_fullpath)) {
            $filename = $path_parts['filename'] . "-" . $i++ . "." . $path_parts['extension'];
            $filename_fullpath = join(DIRECTORY_SEPARATOR, array($upload_dir,$filename));
            if ($i > DocumentsModel::FREE_FILENAME_LIMIT) {
                return NULL;
            }
        }
        move_uploaded_file($document->getTempName(), $filename_fullpath);
        chmod($filename_fullpath, 0644);
        return $filename;
    }

    private function createUploaderResult($docName, $status) {
        $tmpResult = new CRSDocumentAppService_uploadResultVO();
        $tmpResult->documentName = $docName;
        $tmpResult->status = $status;
        return $tmpResult;
    }

    public function checkFiles($attribute, $params) {
        // Use CFile validator to validate uploaded files (stored in $filesAttribute field),
        // then assign errors array (might be empty) as errors of $attribute field.
        $filesAttribute = $params['filesAttribute'];
        unset($params['filesAttribute']);
        $validator = CValidator::createValidator('file', $this, $filesAttribute, $params);
        $validator->validate($this);
        $this->addErrors(array($attribute => $this->getErrors($filesAttribute)));
    }

    public function checkAndNormalizeFileNames($attribute) {
        foreach ($this->$attribute as $document) {
            $documentName = $document->getName();
            try {
                $documentName = Utf8Validator::validateAndNormalizeUtf8($documentName);
                $document->setName($documentName);
            } catch (Exception $e) {
                $this->addError($attribute, "You can upload only files with valid UTF-8 names");
                break;
            }
        }
    }

    public function filterDisallowedDocumentsAndDomains($documentsDomains) {
        $filtered = array();
        $filenames = array_map(function ($file) {
            return $file->getName();
        }, $this->documents);
        foreach ($documentsDomains as $documentName => $listOfDomains) {
            try {
                $documentName = Utf8Validator::validateAndNormalizeUtf8($documentName);
            } catch (Exception $e) {
                continue;
            }
            if (in_array($documentName, $filenames)) {
                // We expect $domains to be normalized (if they're not, it means the user has
                // tampered with POST data), so we can safely intersect.
                $domains = array_intersect($listOfDomains, $this->allowedDomains);
                $filtered[$documentName] = $domains;
            }
        }
        return $filtered;
    }

    public function forceFilterDisallowedDocumentsAndDomains() {
        $documentsDomains = $this->filterDisallowedDocumentsAndDomains($this->documentsDomains);
        $this->documentsDomains = $documentsDomains;
    }

}
