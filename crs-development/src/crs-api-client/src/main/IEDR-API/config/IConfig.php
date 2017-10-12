<?php

/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */

interface IConfig {

    function getAttribute($key);

    function addAttribute($key, $value);

}
