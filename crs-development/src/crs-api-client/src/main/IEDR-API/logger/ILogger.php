<?php

/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */

interface ILogger {

    function error($string);

    function debug($string);

    function warn($string);

    function info($string);
}
