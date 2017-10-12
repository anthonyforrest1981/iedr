DELETE i.*, rh.*, th.* FROM ReservationHist rh LEFT JOIN TransactionHist th ON th.ID = rh.Transaction_ID LEFT JOIN Invoice i ON i.ID = th.Invoice_ID LEFT JOIN Transaction t ON i.ID = t.Invoice_ID LEFT JOIN DomainHist dh ON dh.Chng_ID = rh.Domain_Chng_ID WHERE dh.D_Name LIKE 'ef-%' AND t.ID IS NULL;
DELETE rh.*, th.* FROM ReservationHist rh LEFT JOIN TransactionHist th ON rh.Transaction_ID = th.ID LEFT JOIN Invoice i ON th.Invoice_ID = i.ID LEFT JOIN DomainHist dh ON dh.Chng_ID = rh.Domain_Chng_ID WHERE dh.D_Name LIKE 'ef-%';
DELETE r.*, t.*, th.*, i.* FROM Reservation r LEFT JOIN Transaction t ON r.Transaction_ID = t.ID LEFT JOIN TransactionHist th ON r.Transaction_ID = th.ID LEFT JOIN Invoice i ON t.Invoice_ID = i.ID WHERE r.Domain_Name LIKE 'ef-%';

DELETE FROM Contact WHERE D_Name LIKE 'ef-%';
DELETE FROM ContactHist WHERE D_Name LIKE 'ef-%';
DELETE FROM DNS WHERE D_Name LIKE 'ef-%';
DELETE FROM DNSHist WHERE D_Name LIKE 'ef-%';
DELETE FROM Domain WHERE D_Name LIKE 'ef-%';
DELETE FROM DomainHist WHERE D_Name LIKE 'ef-%';

DELETE FROM TransfersHist WHERE D_Name LIKE 'ef-%';

DELETE FROM TicketNameserver WHERE T_Number IN (SELECT T_Number FROM Ticket WHERE D_Name LIKE 'ef-%');
DELETE FROM TicketNameserverHist WHERE T_Number IN (SELECT T_Number FROM Ticket WHERE D_Name LIKE 'ef-%');
DELETE FROM Ticket WHERE D_Name LIKE 'ef-%';
DELETE FROM TicketHist WHERE D_Name LIKE 'ef-%';

DELETE FROM Access WHERE Nic_Handle = 'EF10AA-IEDR';
DELETE FROM AccessHist WHERE Nic_Handle_Chng_ID IN (SELECT Chng_ID FROM NicHandleHist WHERE Nic_Handle = 'EF10AA-IEDR');
DELETE FROM NicHandle WHERE Nic_Handle = 'EF10AA-IEDR';
DELETE FROM NicHandleHist WHERE Nic_Handle = 'EF10AA-IEDR';
DELETE FROM Reseller_Defaults WHERE Nic_Handle = 'EF10AA-IEDR';
DELETE FROM ResellerDefaultNameservers WHERE Nic_Handle = 'EF10AA-IEDR';
