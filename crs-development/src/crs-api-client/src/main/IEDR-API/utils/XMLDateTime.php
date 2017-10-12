<?php

/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */

class XMLDateTime {

    function toXmlDateTime($string) {
        if ($string == "0000-00-00") {
            return "0001-01-01T00:00:00";
        }
        if ($string == "0000-00-00 00:00:00") {
            return "0001-01-01T00:00:00";
        }

        return preg_replace("/(.*) (.*)/", "\$1T\$2", $string);
    }

    function fromXmlDateTime($string) {
        return preg_replace("/(.*)T(.*)/", "\$1 \$2", $string);
    }
}
