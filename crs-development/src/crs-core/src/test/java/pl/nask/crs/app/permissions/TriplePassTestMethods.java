package pl.nask.crs.app.permissions;

import pl.nask.crs.security.authentication.AccessDeniedException;

public interface TriplePassTestMethods {

    void getAdminPassedTickets() throws AccessDeniedException;

    void performFinancialCheck() throws AccessDeniedException;

    void performTechnicalCheck() throws AccessDeniedException;

    void performTicketCancellation() throws AccessDeniedException;

    void promoteModificationTicket() throws AccessDeniedException;

    void promoteTicketToDomain() throws AccessDeniedException;

    void promoteTransferTicket() throws AccessDeniedException;

}
