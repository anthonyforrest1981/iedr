DELETE FROM TicketNameserver WHERE T_Number IN (SELECT T_Number FROM Ticket WHERE D_Name LIKE 'ef-nosc%');
DELETE FROM TicketNameserverHist WHERE T_Number IN (SELECT T_Number FROM Ticket WHERE D_Name LIKE 'ef-nosc%');
DELETE FROM Ticket WHERE D_Name LIKE 'ef-nosc%';
DELETE FROM TicketHist WHERE D_Name LIKE 'ef-nosc%';

DELETE FROM Contact WHERE D_Name LIKE 'ef-nosc%';
DELETE FROM ContactHist WHERE D_Name LIKE 'ef-nosc%';
DELETE FROM DNS WHERE D_Name LIKE 'ef-nosc%';
DELETE FROM DNSHist WHERE D_Name LIKE 'ef-nosc%';
DELETE FROM Domain WHERE D_Name LIKE 'ef-nosc%';
DELETE FROM DomainHist WHERE D_Name LIKE 'ef-nosc%';

DELETE FROM Access WHERE Nic_Handle LIKE 'EFA%';
DELETE FROM AccessHist WHERE Nic_Handle_Chng_ID IN (SELECT Chng_ID FROM NicHandleHist WHERE Nic_Handle LIKE 'EFA%');
DELETE FROM NicHandle WHERE Nic_Handle LIKE 'EFA%';
DELETE FROM NicHandleHist WHERE Nic_Handle LIKE 'EFA%';

DELETE FROM INCOMING_DOC WHERE DOC_ID IN (SELECT DOC_ID FROM INCOMING_DOC_DOMAINS WHERE DOMAIN_NAME LIKE 'ef-nosc%');
DELETE FROM INCOMING_DOC_DOMAINS WHERE DOMAIN_NAME LIKE 'ef-nosc%';

