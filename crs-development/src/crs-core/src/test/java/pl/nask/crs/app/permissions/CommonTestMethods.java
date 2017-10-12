package pl.nask.crs.app.permissions;

import pl.nask.crs.security.authentication.AccessDeniedException;

/**
 * (C) Copyright 2012 NASK
 * Software Research & Development Department
 */
public interface CommonTestMethods {

    void registerDomain() throws AccessDeniedException;

    void transfer() throws AccessDeniedException;

    void isTransferPossible() throws AccessDeniedException;

    void cancelTicketAsOwner() throws AccessDeniedException;

    void cancelNotOwnTicket() throws AccessDeniedException;

    void modifyDomain() throws AccessDeniedException;

    void modifyDomainNameservers() throws AccessDeniedException;

    void modifyNotOwnDomain() throws AccessDeniedException;

    void modifyNotOwnDomainNameservers() throws AccessDeniedException;

    void reauthoriseCCTransaction() throws AccessDeniedException;

    void reauthoriseNotOwnCCTransaction() throws AccessDeniedException;

    void zoneCommit() throws AccessDeniedException;

    void zonePublished() throws AccessDeniedException;

    void zoneUnpublished() throws AccessDeniedException;

    void cleanupTicket() throws AccessDeniedException;

    void generateOrProlongAuthCode() throws AccessDeniedException;

    void generateOrProlongAuthCodeNotOwn() throws AccessDeniedException;

    void sendAuthCodeByEmail() throws AccessDeniedException;

    void sendAuthCodeByEmailNotOwn() throws AccessDeniedException;

    void verifyAuthCode() throws AccessDeniedException;

    void getOwnerTypeByName() throws AccessDeniedException;

    void getOwnerTypes() throws AccessDeniedException;

}
