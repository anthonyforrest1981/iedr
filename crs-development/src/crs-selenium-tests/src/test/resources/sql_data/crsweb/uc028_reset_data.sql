DELETE FROM ResetPass WHERE Nic_Handle LIKE 'UC028%';
DELETE FROM UserPermissions WHERE Nic_Handle LIKE 'UC028%';

DELETE FROM Access WHERE Nic_Handle LIKE 'UC028%';
DELETE FROM AccessHist WHERE Nic_Handle_Chng_ID IN (SELECT Chng_ID FROM NicHandleHist WHERE Nic_Handle LIKE 'UC028%');
DELETE FROM NicHandle WHERE Nic_Handle LIKE 'UC028%';
DELETE FROM NicHandleHist WHERE Nic_Handle LIKE 'UC028%';

DELETE FROM TicketNameserver WHERE T_Number IN (SELECT T_Number FROM Ticket WHERE D_Name LIKE 'uc028%');
DELETE FROM TicketNameserverHist WHERE T_Number IN (SELECT T_Number FROM Ticket WHERE D_Name LIKE 'uc028%');
DELETE FROM Ticket WHERE D_Name LIKE 'uc028%';
DELETE FROM TicketHist WHERE D_Name LIKE 'uc028%';



