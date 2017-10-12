package pl.nask.crs.nichandle.dao.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.nask.crs.accounts.AccountStatus;
import pl.nask.crs.accounts.search.AccountSearchCriteria;
import pl.nask.crs.commons.dao.ibatis.GenericIBatisDAO;
import pl.nask.crs.nichandle.dao.ibatis.objects.InternalNicHandle;

public class InternalNicHandleIBatisDAO extends GenericIBatisDAO<InternalNicHandle, String> {

    private static final Map<String, String> sortMap = new HashMap<>();
    {
        sortMap.put("nicHandleId", "nicHandleId");
        sortMap.put("name", "name");
        sortMap.put("accountNumber", "accountNumber");
        sortMap.put("accountName", "accountName");
        sortMap.put("accountBillingContact", "accountBillingContact");
        sortMap.put("agreementSigned", "agreementSigned");
        sortMap.put("ticketEdit", "ticketEdit");
        sortMap.put("companyName", "companyName");
        sortMap.put("address", "address");
        sortMap.put("Telecom_number", "Telecom_number");
        sortMap.put("Telecom_type", "Telecom_type");
        sortMap.put("Telecom_order", "Telecom_order");
        sortMap.put("countryName", "countryName");
        sortMap.put("countryVatCategory", "countryVatCategory");
        sortMap.put("countyName", "countyName");
        sortMap.put("email", "email");
        sortMap.put("status", "status");
        sortMap.put("statusChangeDate", "statusChangeDate");
        sortMap.put("registrationDate", "registrationDate");
        sortMap.put("changeDate", "changeDate");
        sortMap.put("billCInd", "billCInd");
        sortMap.put("creator", "creator");
        sortMap.put("vatCategory", "vatCategory");
        sortMap.put("exported", "exported");
    }

    public InternalNicHandleIBatisDAO() {
        setGetQueryId("nicHandle.getNicHandleByNicHandleId");
        setFindQueryId("nicHandle.findNicHandle");
        setCountFindQueryId("nicHandle.countTotalSearchResult");
        setLockQueryId("nicHandle.getLockedNicHandleByNicHandleId");
        setSortMapping(sortMap);
    }

    /**
     * Updates nic handle using historical entries.
     * Deletes old phones and faxes, and inserts new ones.
     * @param changeId
     */
    public void updateUsingHistory(long changeId) {
        performUpdate("nicHandle.updateNicHandleUsingHistory", changeId);
        performUpdate("nicHandle.deleteTelecomsByChangeId", changeId);
        performInsert("nicHandle.insertTelecomsUsingHistory", changeId);
    }

    public void create(InternalNicHandle internalNicHandle) {
        performInsert("nicHandle.insertNicHandle", internalNicHandle);
    }

    @Override
    public void deleteById(String id) {
        logger.debug("Deleting " + id);
        performDelete("nicHandle.deleteTelecoms", id);
        performDelete("nicHandle.deleteContacts", id);
        performDelete("nicHandle.deleteAccess", id);
        performDelete("nicHandle.deleteNicHandle", id);
    }

    public void deleteMarkedNichandles() {
        logger.debug("Looking for nic handles to be deleted");
        List<String> list = performQueryForList("nicHandle.findDeleted", "");
        logger.debug("Found " + list.size() + " nichandles marked for deletion");
        for (String nh : list) {
            deleteById(nh);
        }
    }

    public String getBillingNhForContact(String nicHandleId) {
        List<String> res = performQueryForList("nicHandle.findBillingNH", nicHandleId);
        if (res.size() == 0) {
            return null;
        } else if (res.size() > 1) {
            logger.warn("nicHandle.findBillingNH: Expected one result to be found, but got " + res.size()
                    + " results for nh=" + nicHandleId + ". Results are: " + res + ", will return null.");
            return null;
        } else {
            return res.get(0);
        }
    }
}
