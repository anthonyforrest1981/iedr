<?php

class NoBillableDomainModel extends AllDomainsModel {

    public function getSearchBase() {
        $criteria = new CRSDomainAppService_plainDomainSearchCriteriaVO();
        $criteria->accountId = Yii::app()->user->id;
        $criteria->holderTypes = CRSDomainAppService_domainHolderType::_NonBillable;
        return $criteria;
    }

}
