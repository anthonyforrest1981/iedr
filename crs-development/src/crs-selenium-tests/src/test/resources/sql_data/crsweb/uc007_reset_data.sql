DELETE i.*, rh.*, th.* FROM ReservationHist rh LEFT JOIN TransactionHist th ON th.ID = rh.Transaction_ID LEFT JOIN Invoice i ON i.ID = th.Invoice_ID LEFT JOIN Transaction t ON i.ID = t.Invoice_ID LEFT JOIN DomainHist dh ON dh.Chng_ID = rh.Domain_Chng_ID WHERE dh.D_Name LIKE 'uc007%' AND t.ID IS NULL;
DELETE rh.*, th.* FROM ReservationHist rh LEFT JOIN TransactionHist th ON rh.Transaction_ID = th.ID LEFT JOIN Invoice i ON th.Invoice_ID = i.ID LEFT JOIN DomainHist dh ON dh.Chng_ID = rh.Domain_Chng_ID WHERE dh.D_Name LIKE 'uc007%';
DELETE r.*, t.*, th.*, i.* FROM Reservation r LEFT JOIN Transaction t ON r.Transaction_ID = t.ID LEFT JOIN TransactionHist th ON r.Transaction_ID = th.ID LEFT JOIN Invoice i ON t.Invoice_ID = i.ID WHERE r.Domain_Name LIKE 'uc007%';

DELETE FROM Contact WHERE D_Name LIKE 'uc007%';
DELETE FROM Contact WHERE D_Name LIKE 'uc007%';
DELETE FROM DNS WHERE D_Name LIKE 'uc007%';
DELETE FROM DNS WHERE D_Name LIKE 'uc007%';
DELETE FROM Domain WHERE D_Name LIKE 'uc007%';
DELETE FROM DomainHist WHERE D_Name LIKE 'uc007%';

DELETE FROM Access WHERE Nic_Handle LIKE 'UC007%';
DELETE FROM AccessHist WHERE Nic_Handle_Chng_ID IN (SELECT Chng_ID FROM NicHandleHist WHERE Nic_Handle LIKE 'UC007%');
DELETE FROM NicHandle WHERE Nic_Handle LIKE 'UC007%';
DELETE FROM NicHandleHist WHERE Nic_Handle LIKE 'UC007%';

DELETE FROM TicketNameserver WHERE T_Number IN (SELECT T_Number FROM Ticket WHERE D_Name LIKE 'uc007%');
DELETE FROM TicketNameserverHist WHERE T_Number IN (SELECT T_Number FROM Ticket WHERE D_Name LIKE 'uc007%');
DELETE FROM Ticket WHERE D_Name LIKE 'uc007%';
DELETE FROM TicketHist WHERE D_Name LIKE 'uc007%';

DELETE FROM TransfersHist WHERE D_Name like 'uc007%';

