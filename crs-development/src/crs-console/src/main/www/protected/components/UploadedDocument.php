<?php

class UploadedDocument {
    private $name;
    private $tempName;

    public function __construct($uploadedFile) {
        $this->name = $uploadedFile->getName();
        $this->tempName = $uploadedFile->getTempName();
    }

    public function getName() {
        return $this->name;
    }

    public function setName($name) {
        $this->name = $name;
    }

    public function getTempName() {
        return $this->tempName;
    }

}