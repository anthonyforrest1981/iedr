<?php

class TransferQuery {

    private $direction;
    private $dateRangeEnd;
    private $dateRangeStart;

    private function __construct($direction, $dateRangeStart, $dateRangeEnd) {
        $this->direction = $direction;
        $this->dateRangeStart = $dateRangeStart;
        $this->dateRangeEnd = $dateRangeEnd;
    }

    /**
     * Construct a query for the transfer. The direction depends on the second argument.
     *
     * @param unknown $direction if 'inbound' is passed, query for the incoming transfers will be
     *     returned. If other string is passed, query for outgoiing transfers will be returned.
     * @param unknown $dateRangeStart
     * @param unknown $dateRangeEnd
     *
     * @return TransferQuery
     */
    static function forDirection($direction, $dateRangeStart = '', $dateRangeEnd = '') {
        if ($direction == 'inbound') {
            return TransferQuery::inbound($dateRangeStart, $dateRangeEnd);
        } else {
            return TransferQuery::outbound($dateRangeStart, $dateRangeEnd);
        }
    }

    /**
     * Construct a query for the incoming transfers
     *
     * @param unknown $dateRangeStart
     * @param unknown $dateRangeEnd
     *
     * @return TransferQuery
     */
    static function inbound($dateRangeStart = '', $dateRangeEnd = '') {
        return new TransferQuery("inbound", $dateRangeStart, $dateRangeEnd);
    }

    /**
     * Construct a query for the outgoing transfers
     *
     * @param unknows $dateRangeStart
     * @param unknown $dateRangeEnd
     *
     * @return TransferQuery
     */
    static function outbound($dateRangeStart = '', $dateRangeEnd = '') {
        return new TransferQuery("outbound", $dateRangeStart, $dateRangeEnd);
    }

    function getDateRangeStart() {
        return $this->dateRangeStart;
    }

    function setDateRangeStart($date) {
        $this->dateRangeStart = $date;
    }

    function getDateRangeEnd() {
        return $this->dateRangeEnd;
    }

    function setDateRangeEnd($date) {
        $this->dateRangeEnd = $date;
    }

    function getDirection() {
        return $this->direction;
    }
}
