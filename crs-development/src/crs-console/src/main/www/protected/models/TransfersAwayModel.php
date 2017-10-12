<?php

class TransfersAwayModel extends TransfersAwayAndTo {

    protected function initDomainField() {
        $this->columns['A'] = array('resultfield' => 'name', 'criteriafield' => 'domainName',
            'sortby' => 'name', 'label' => 'Domain Name', 'width' => 60, 'type' => 'string');
    }

}