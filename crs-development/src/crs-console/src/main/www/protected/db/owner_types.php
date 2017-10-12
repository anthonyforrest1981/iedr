<?php

function getAllOwnerTypes() {
    static $ownerTypes = null;
    if ($ownerTypes === null) {
        $errors = null;
        CRSCommonAppService_service::getOwnerTypes($ownerTypes, $errors, Yii::app()->user->authenticatedUser);
        if ($errors || $ownerTypes === null) {
            throw new Exception('Error while fetching owner types. ' . print_r($errors, true));
        }
        if (!is_array($ownerTypes)) {
            $ownerTypes = array($ownerTypes);
        }
    }
    return $ownerTypes;
}

function getCharityOwnerTypeIds() {
    $ownerTypes = getAllOwnerTypes();
    $charityOwnerTypes = array();
    foreach ($ownerTypes as $i => $ownerType) {
        if ($ownerType->charity) {
            $charityOwnerTypes[] = $ownerType->id;
        }
    }
    return $charityOwnerTypes;
}

function getOwnerTypeOptions($forRegistration) {
    $ownerTypes = getAllOwnerTypes();
    $options = array();
    foreach ($ownerTypes as $i => $ownerType) {
        if ($forRegistration || !$ownerType->charity) {
            $options[$ownerType->id] = $ownerType->name;
        }
    }
    return $options;
}
