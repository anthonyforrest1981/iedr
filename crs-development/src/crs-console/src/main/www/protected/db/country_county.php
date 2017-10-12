<?php

function getCountryCountyData() {
    $user = null;
    $errs = '';
    $result = null;
    CRSDomainAppService_service::getCountries($result, $errs, $user);
    if ($result == null || count($errs) > 0) {
        Yii::log('GET COUNTRIES ERROR ' . print_r($errs, true));
    }
    return $result;
}

function createMap($data) {
    $arr = array();
    if ($data != null) {
        if (is_array($data)) {
            foreach ($data as $obj) {
                $arr[$obj->id] = $obj;
            }
        } else if (is_object($data)) {
            $arr[$data->id] = $data;
        }
    }
    return $arr;
}

function createMapForNames($data) {
    $arr = array();
    $arr[''] = '';
    if ($data != null) {
        if (is_array($data)) {
            foreach ($data as $obj) {
                $arr[$obj->id] = $obj->name;
            }
        } else if (is_object($data)) {
            $arr[$data->id] = $data->name;
        }
    }
    return $arr;
}

function getCountryCountyMap() {
    $arr = array();
    $data = getCountryCountyData();
    if ($data != null) {
        if (is_array($data)) {
            for ($i = 0; $i < count($data); $i++) {
                if (isset($data[$i]->counties)) {
                    $arr[$data[$i]->id] = createMap($data[$i]->counties);
                } else {
                    $arr[$data[$i]->id] = null;
                }
            }
        } else if (is_object($data)) {
            if (isset($data->counties)) {
                $arr[$data->id] = createMap($data[$i]->counties);
            }
        }
    }
    return $arr;
}

/**
 * @return an array: id -> name
 */
function getCountryOptions() {
    return createMapForNames(getCountryCountyData());
}

/**
 * @return an array: id -> name
 */
function getCountyOptions($countryId) {
    $data = getCountryCountyMap();
    if (empty($data[$countryId])) {
        return array(0 => 'N/A');
    }
    return createMapForNames($data[$countryId]);
}

/**
 * @return country id
 */
function getCountryByName($countryName) {
    $data = getCountryOptions();
    return array_search($countryName, $data);
}

/**
 * @return county id
 */
function getCountyByName($countryId, $countyName) {
    $data = getCountyOptions($countryId);
    return array_search($countyName, $data);
}

function getCountryName($countryId) {
    $data = getCountryOptions();
    return $data[$countryId];
}

function getCountyName($countryId, $countyId) {
    $data = getCountyOptions($countryId);
    return $data[$countyId];
}
