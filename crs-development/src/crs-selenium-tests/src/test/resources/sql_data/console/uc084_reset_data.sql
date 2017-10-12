DELETE rh.*, th.* FROM ReservationHist rh LEFT JOIN TransactionHist th ON rh.Transaction_ID = th.ID LEFT JOIN Invoice i ON th.Invoice_ID = i.ID LEFT JOIN DomainHist dh ON dh.Chng_ID = rh.Domain_Chng_ID WHERE dh.D_Name LIKE 'uc084%';
DELETE r.*, t.*, th.*, i.* FROM Reservation r LEFT JOIN Transaction t ON r.Transaction_ID = t.ID LEFT JOIN TransactionHist th ON r.Transaction_ID = th.ID LEFT JOIN Invoice i ON t.Invoice_ID = i.ID WHERE r.Domain_Name LIKE 'uc084%';

DELETE FROM Contact WHERE D_Name LIKE 'uc084%';
DELETE FROM ContactHist WHERE D_Name LIKE 'uc084%';
DELETE FROM DNS WHERE D_Name LIKE 'uc084%';
DELETE FROM DNSHist WHERE D_Name LIKE 'uc084%';
DELETE FROM Domain WHERE D_Name LIKE 'uc084%';
DELETE FROM DomainHist WHERE D_Name LIKE 'uc084%';

DELETE FROM TicketNameserver WHERE T_Number IN (SELECT T_Number FROM Ticket WHERE D_Name LIKE 'uc084%');
DELETE FROM TicketNameserverHist WHERE T_Number IN (SELECT T_Number FROM Ticket WHERE D_Name LIKE 'uc084%');
DELETE FROM Ticket WHERE D_Name LIKE 'uc084%';
DELETE FROM TicketHist WHERE D_Name LIKE 'uc084%';

