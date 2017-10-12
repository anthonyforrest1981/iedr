package pl.nask.crs.app.permissions;

import pl.nask.crs.security.authentication.AccessDeniedException;

public interface TicketTestMethods {

    void viewTicket() throws AccessDeniedException;

    void viewNotOwnTicket() throws AccessDeniedException;

    void historyTicket() throws AccessDeniedException;

    void reviseTicket() throws AccessDeniedException;

    void editTicket() throws AccessDeniedException;

    void editNotOwnTicket() throws AccessDeniedException;

    void checkOutTicket() throws AccessDeniedException;

    void checkInTicket() throws AccessDeniedException;

    void alterStatusTicket() throws AccessDeniedException;

    void reassignTicket() throws AccessDeniedException;

    void acceptTicket() throws AccessDeniedException;

    void rejectTicket() throws AccessDeniedException;

    void updateAsAdminTicket() throws AccessDeniedException;

    void updateAsOwnerTicket() throws AccessDeniedException;

    void updateAsOwnerNotOwnTicket() throws AccessDeniedException;

    void findTicketsToCleanup() throws AccessDeniedException;

    void findTicketNotifications() throws AccessDeniedException;

    void sendTicketExpirationEmail() throws AccessDeniedException;

    void getTicketForDomain() throws AccessDeniedException;

    void getTicketForDomainNotOwn() throws AccessDeniedException;

    void findOwnTickets() throws AccessDeniedException;

    void searchForTickets() throws AccessDeniedException;

    void saveTicket() throws AccessDeniedException;

    void saveTicketNotOwn() throws AccessDeniedException;

}
