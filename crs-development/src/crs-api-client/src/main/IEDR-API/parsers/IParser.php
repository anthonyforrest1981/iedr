<?php

/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */

interface IParser {

    static function fromDomNode($node);

    function toXml($object);

}
