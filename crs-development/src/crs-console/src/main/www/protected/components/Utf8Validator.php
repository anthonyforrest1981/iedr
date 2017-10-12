<?php

class Utf8Validator extends CValidator {

    public function validateAttribute($model, $attr) {
        $value = $model->$attr;
        try {
            if (is_array($value)) {
                $normalized = self::validateAndNormalizeUtf8Array($value);
            } else {
                $normalized = self::validateAndNormalizeUtf8($value);
            }
            $model->$attr = $normalized;
        } catch (Exception $e) {
            $model->addError($attr, $e->getMessage());
        }
    }

    private static function validateAndNormalizeUtf8Array($array) {
        $normalizedArray = array();
        foreach ($array as $key => $value) {
            // breaking on the first exception thrown
            $normalizedArray[$key] = self::validateAndNormalizeUtf8($value);
        }
        return $normalizedArray;
    }

    public static function validateAndNormalizeUtf8($value) {
        if (!mb_check_encoding($value, "UTF-8")) {
            throw new Exception("Not a valid UTF-8");
        }
        $normalized = self::normalizeUtf8($value);
        if (self::is4ByteUtf8($normalized)) {
            throw new Exception("Forbidden UTF-8 character");
        }
        return $normalized;
    }

    private static function normalizeUtf8($value) {
        return Normalizer::normalize($value);
    }

    /**
     * Normalization and validation - 4-byte UTF-8 is not allowed
     *
     * Byte ranges used by UTF-8:
     * 00 to 7F - ASCII
     * 80 to BF - non-initial bytes of multi-byte UTF-8
     * C0 to DF - initial bytes of 2-byte UTF-8
     * E0 to EF - initial bytes of 3-byte UTF-8
     * F0 to F7 - initial bytes of 4-byte UTF-8
     * The last ranges clearly indicates that the 4-byte UTF-8 is used
     */
    private static function is4ByteUtf8($value) {
        return (preg_match("/[\xf0-\xf7]/", $value) === 1);
    }

}
