<?php

/*
 * Copyright (C) 2006 NASK
 * http://www.nask.pl
 * http://www.dns.pl
 */

class ResultCodes {

    static public $COMMAND_SUCCESSFULLY = 1000;
    static public $COMMAND_SUCCESSFULLY_END_SESSION = 1500;
    static public $COMMAND_SUCCESSFULLY_NO_RESULT = 1400;

    static public $COMMAND_SYNTAX_ERROR = 2000;
    static public $COMMAND_USE_ERROR = 2001;
    static public $REQUIRED_PARAMETER_MISSING = 2002;
    static public $PARAMETER_VALUE_RANGE_ERROR = 2003;
    static public $PARAMETER_VALUE_SYNTAX_ERROR = 2004;
    static public $UTF8_VALIDATION_ERROR = 2005;

    static public $AUTHENTICATION_ERROR = 2100;
    static public $UNIMPLEMENTED_COMMAND = 2101;
    static public $PARAMETER_VALUE_POLICY_ERROR = 2102;
    static public $BILLING_FAILURE = 2104;
    static public $AUTHORIZATION_ERROR = 2201;
    static public $OBJECT_EXISTS = 2302;
    static public $OBJECT_DOES_NOT_EXIST = 2303;
    static public $OBJECT_STATUS_PROHIBITS_OPERATION = 2304;
    static public $DATA_MANAGEMENT_POLICY_VIOLATION = 2308;
    static public $COMMAND_FAILED = 2400;

    static public $MSGS_LIST = array(
        1000 => "Command completed successfully",
        1400 => "Command completed successfully; no result",
        1500 => "Command completed successfully; ending session",

        2000 => "Command syntax error", 2001 => "Command use error",
        2002 => "Required parameter missing",
        2003 => "Parameter value range error",
        2004 => "Parameter value syntax error",
        2005 => "UTF-8 validation error", 2100 => "Authentication error",
        2101 => "Unimplemented command",
        2102 => "Parameter value policy error", 2104 => "Billing failure",
        2201 => "Authorization error", 2302 => "Object exists",
        2303 => "Object does not exist",
        2304 => "Object status prohibits operation",
        2308 => "Data management policy violation",
        2400 => "Command failed");

}
