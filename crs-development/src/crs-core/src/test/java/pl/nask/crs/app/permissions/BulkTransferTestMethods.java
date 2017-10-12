package pl.nask.crs.app.permissions;

import pl.nask.crs.security.authentication.AccessDeniedException;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public interface BulkTransferTestMethods {

    void createBulkTransferProcess() throws AccessDeniedException;

    void addDomains() throws AccessDeniedException;

    void findTransfers() throws AccessDeniedException;

    void getTransferRequest() throws AccessDeniedException;

    void removeDomain() throws AccessDeniedException;

    void closeTransferRequest() throws AccessDeniedException;

    void forceCloseTransferRequest() throws AccessDeniedException;

    void transferAll() throws AccessDeniedException;

    void transferValid() throws AccessDeniedException;
}
