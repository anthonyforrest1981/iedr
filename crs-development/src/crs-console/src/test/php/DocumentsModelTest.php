<?php

class DocumentsModelTest extends PHPUnit_Framework_TestCase {

    public function testFilterDisallowedDomainsAndFiles() {
        $allowedFile = 'filename1.pdf';
        $disallowedFile = 'filename2.pdf';
        $allowedDomain = 'domainname1.ie';
        $disallowedDomain = 'domainname2.ie';
        // Upload just one file.
        $this->prepareFiles(array($allowedFile));
        // Allow just one domain.
        $model = new DocumentsModel();
        $uploaderModel->scenario = DocumentsModel::DOMAIN_UPLOAD_SCENARIO;
        $model->setAllowedDomains($allowedDomain);
        $documentsDomains = array($allowedFile => array($allowedDomain => 'on',$disallowedDomain => 'on'),
            $disallowedFile => array($allowedDomain => 'on',$disallowedDomain => 'on'));
        $model->documentsDomains = $documentsDomains;
        // Files and domains will be filtered on validation.
        $this->assertTrue(isset($model->documentsDomains[$allowedFile]));
        $this->assertTrue(isset($model->documentsDomains[$disallowedFile]));
        $this->assertEquals(array($allowedDomain,$disallowedDomain), $model->documentsDomains[$allowedFile]);
        $this->assertEquals(array($allowedDomain,$disallowedDomain), $model->documentsDomains[$disallowedFile]);
        $model->validate();
        $this->assertTrue($model->isSetup());
        $this->assertTrue(isset($model->documentsDomains[$allowedFile]));
        $this->assertFalse(isset($model->documentsDomains[$disallowedFile]));
        $this->assertEquals(array($allowedDomain), $model->documentsDomains[$allowedFile]);
    }

    public function testUtf8() {
        $this->_testFilesError(Utf8ValidationTestUtil::$FOUR_BYTES . '.pdf');
        $this->_testFilesError(Utf8ValidationTestUtil::$NON_UTF8 . '.pdf');
        $this->_testFilesNormalization(Utf8ValidationTestUtil::$UNNORMALIZED . '.pdf', Utf8ValidationTestUtil::$NORMALIZED . '.pdf');
    }

    private function _testFilesError($filename) {
        $model = $this->prepareAndValidateModel($filename);
        $errorMessage = $model->getError('documents');
        $this->assertTrue(isset($errorMessage), 'Error should be detected in documents field');
        $this->assertEquals('You can upload only files with valid UTF-8 names', $errorMessage);
    }

    private function _testFilesNormalization($unnormalized, $normalized) {
        $model = $this->prepareAndValidateModel($unnormalized);
        $errorMessage = $model->getError('documents');
        $this->assertFalse(isset($errorMessage), 'Documents field shouldn\'t have any errors: ' . $errorMessage);
        $this->assertEquals(1, count($model->documents));
        $this->assertEquals($normalized, $model->documents[0]->getName());
    }

    private function prepareAndValidateModel($filename) {
        $this->prepareFiles(array($filename));
        $domainName = 'domain.ie';
        $model = new DocumentsModel();
        $uploaderModel->scenario = DocumentsModel::DOMAIN_UPLOAD_SCENARIO;
        $model->setAllowedDomains($domainName);
        $documentsDomains = array($filename => array($domainName => 'on'));
        $model->documentsDomains = $documentsDomains;
        $model->validate();
        $this->assertTrue($model->isSetup());
        return $model;
    }

    private function prepareFiles($filenames) {
        $_FILES = array();
        CUploadedFile::reset();
        $fileInstanceName = 'documents';
        $tmp_names = array();
        $types = array();
        $sizes = array();
        $errors = array();
        foreach ($filenames as $name) {
            $tmp_names[] = null;
            $types[] = null;
            $sizes[] = null;
            $errors[] = null;
        }
        $files = array('name' => $filenames,'tmp_name' => $tmp_names,'type' => $types,'size' => $sizes,
            'error' => $errors);
        $_FILES[$fileInstanceName] = $files;
    }

}