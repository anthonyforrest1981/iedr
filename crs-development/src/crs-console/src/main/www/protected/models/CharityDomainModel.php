<?php

class CharityDomainModel extends AllDomainsModel {

    public function getSearchBase() {
        $criteria = new CRSDomainAppService_plainDomainSearchCriteriaVO();
        $criteria->accountId = Yii::app()->user->id;
        $criteria->holderTypes = CRSDomainAppService_domainHolderType::_Charity;
        return $criteria;
    }

}
