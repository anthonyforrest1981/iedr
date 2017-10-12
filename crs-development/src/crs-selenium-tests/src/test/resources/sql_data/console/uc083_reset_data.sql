DELETE FROM EmailDisabler WHERE ED_Nic_Handle LIKE 'UC083%';
DELETE edh.* FROM EmailDisablerHist edh LEFT JOIN NicHandleHist nhh ON edh.ED_Nic_Handle_Chng_ID = nhh.Chng_ID WHERE nhh.NH_Name LIKE 'UC083%';

DELETE FROM TicketNameserver WHERE T_Number IN (SELECT T_Number FROM Ticket WHERE D_Name LIKE 'uc083%');
DELETE FROM TicketNameserverHist WHERE T_Number IN (SELECT T_Number FROM Ticket WHERE D_Name LIKE 'uc083%');
DELETE FROM Ticket WHERE D_Name LIKE 'uc083%';
DELETE FROM TicketHist WHERE D_Name LIKE 'uc083%';

DELETE i.*, rh.*, th.* FROM ReservationHist rh LEFT JOIN TransactionHist th ON th.ID = rh.Transaction_ID LEFT JOIN Invoice i ON i.ID = th.Invoice_ID LEFT JOIN Transaction t ON i.ID = t.Invoice_ID LEFT JOIN DomainHist dh ON dh.Chng_ID = rh.Domain_Chng_ID WHERE dh.D_Name LIKE 'uc083%' AND t.ID IS NULL;
DELETE rh.*, th.* FROM ReservationHist rh LEFT JOIN TransactionHist th ON rh.Transaction_ID = th.ID LEFT JOIN Invoice i ON th.Invoice_ID = i.ID LEFT JOIN DomainHist dh ON dh.Chng_ID = rh.Domain_Chng_ID WHERE dh.D_Name LIKE 'uc083%';
DELETE r.*, t.*, th.*, i.* FROM Reservation r LEFT JOIN Transaction t ON r.Transaction_ID = t.ID LEFT JOIN TransactionHist th ON r.Transaction_ID = th.ID LEFT JOIN Invoice i ON t.Invoice_ID = i.ID WHERE r.Domain_Name LIKE 'uc083%';

DELETE FROM Contact WHERE D_Name LIKE 'uc083%';
DELETE FROM ContactHist WHERE D_Name LIKE 'uc083%';
DELETE FROM DNS WHERE D_Name LIKE 'uc083%';
DELETE FROM DNSHist WHERE D_Name LIKE 'uc083%';
DELETE FROM Domain WHERE D_Name LIKE 'uc083%';
DELETE FROM DomainHist WHERE D_Name LIKE 'uc083%';
DELETE FROM TransfersHist WHERE D_Name LIKE 'uc083%';

DELETE FROM Telecom WHERE Nic_Handle LIKE 'UC083%';
DELETE FROM TelecomHist WHERE Nic_Handle LIKE 'UC083%';
DELETE FROM Reseller_Defaults WHERE Nic_Handle LIKE 'UC083%';
DELETE FROM ResellerDefaultNameservers WHERE Nic_Handle LIKE 'UC083%';
DELETE FROM Deposit WHERE Nic_Handle LIKE 'UC083%';
DELETE dh.* FROM DepositHist dh LEFT JOIN NicHandleHist nhh ON dh.Nic_Handle_Chng_ID = nhh.Chng_ID WHERE nhh.NH_Name LIKE 'UC083%';
DELETE FROM Access WHERE Nic_Handle LIKE 'UC083%';
DELETE FROM AccessHist WHERE Nic_Handle_Chng_ID IN (SELECT Chng_ID FROM NicHandleHist WHERE Nic_Handle LIKE 'UC083%');
DELETE FROM NicHandle WHERE Nic_Handle LIKE 'UC083%';
DELETE FROM NicHandleHist WHERE Nic_Handle LIKE 'UC083%';

DELETE FROM INCOMING_DOC WHERE ACCOUNT_NUMBER IN (SELECT A_Number FROM Account WHERE A_Name LIKE 'UC083%');

DELETE FROM Account WHERE Billing_NH LIKE 'UC083%';
DELETE FROM AccountHist WHERE Billing_NH_Chng_ID IN (SELECT Chng_ID FROM NicHandleHist WHERE Nic_Handle LIKE 'UC083%');

DELETE t.* FROM Telecom t LEFT JOIN NicHandle nh ON t.Nic_Handle = nh.Nic_Handle WHERE nh.NH_Name LIKE 'UC083%';
DELETE th.* FROM TelecomHist th LEFT JOIN NicHandle nh ON th.Nic_Handle = nh.Nic_Handle WHERE nh.NH_Name LIKE 'UC083%';
DELETE a.* FROM Access a LEFT JOIN NicHandle nh ON a.Nic_Handle = nh.Nic_Handle WHERE nh.NH_Name LIKE 'UC083%';
DELETE ah.* FROM AccessHist ah LEFT JOIN NicHandleHist nhh ON ah.Nic_Handle_Chng_ID = nhh.Chng_ID WHERE nhh.NH_Name LIKE 'UC083%';
DELETE FROM NicHandle WHERE NH_Name LIKE 'UC083%';
DELETE FROM NicHandleHist WHERE NH_Name LIKE 'UC083%';

