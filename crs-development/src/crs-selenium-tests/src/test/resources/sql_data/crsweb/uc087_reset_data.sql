DELETE FROM Domain WHERE D_Name LIKE 'uc087%';
DELETE FROM DomainHist WHERE D_Name LIKE 'uc087%';
DELETE FROM Contact WHERE D_Name LIKE 'uc087%';
DELETE FROM ContactHist WHERE D_Name LIKE 'uc087%';
DELETE FROM DNS WHERE D_Name LIKE 'uc087%';
DELETE FROM DNSHist WHERE D_Name LIKE 'uc087%';

DELETE FROM TicketNameserver WHERE T_Number IN (SELECT T_Number FROM Ticket WHERE D_Name LIKE 'uc087%');
DELETE FROM TicketNameserverHist WHERE Chng_ID IN (SELECT Chng_Id FROM TicketHist WHERE D_Name LIKE 'uc087%');
DELETE FROM Ticket WHERE D_Name LIKE 'uc087%';
DELETE FROM TicketHist WHERE D_Name LIKE 'uc087%';

DELETE FROM Access WHERE Nic_Handle LIKE 'UC087%';
DELETE FROM AccessHist WHERE Nic_Handle_Chng_ID IN (SELECT Chng_ID FROM NicHandleHist WHERE Nic_Handle LIKE 'UC087%');
DELETE FROM NicHandle WHERE Nic_Handle LIKE 'UC087%';
DELETE FROM NicHandleHist WHERE Nic_Handle LIKE 'UC087%';

DELETE FROM Account WHERE A_Number = '00000601';
DELETE FROM AccountHist WHERE A_Number = '00000601';
DELETE FROM Account WHERE A_Number = '00000602';
DELETE FROM AccountHist WHERE A_Number = '00000602';
