DELETE FROM TicketNameserver WHERE T_Number IN (SELECT T_Number FROM Ticket WHERE D_Name LIKE 'uc085%');
DELETE FROM TicketNameserverHist WHERE T_Number IN (SELECT T_Number FROM Ticket WHERE D_Name LIKE 'uc085%');
DELETE FROM Ticket WHERE D_Name LIKE 'uc085%';
DELETE FROM TicketHist WHERE D_Name LIKE 'uc085%';

DELETE FROM INCOMING_DOC WHERE DOC_ID IN (SELECT DOC_ID FROM INCOMING_DOC_DOMAINS WHERE DOMAIN_NAME LIKE 'uc085%');
