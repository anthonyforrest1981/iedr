DELETE FROM TicketNameserver WHERE T_Number IN (SELECT T_Number FROM Ticket WHERE D_Name LIKE 'uc064%');
DELETE FROM TicketNameserverHist WHERE T_Number IN (SELECT T_Number FROM Ticket WHERE D_Name LIKE 'uc064%');
DELETE FROM Ticket WHERE D_Name LIKE 'uc064%';
DELETE FROM TicketHist WHERE D_Name LIKE 'uc064%';

DELETE FROM Transaction WHERE ID IN (SELECT Transaction_ID FROM Reservation WHERE Domain_Name LIKE 'uc064%');
DELETE FROM Reservation WHERE Domain_Name LIKE 'uc064%';

DELETE FROM TransactionHist WHERE ID IN (SELECT Transaction_ID FROM ReservationHist JOIN DomainHist ON Domain_Chng_Id = Chng_Id WHERE D_Name LIKE 'uc064%');
DELETE FROM ReservationHist WHERE Domain_Chng_Id IN (SELECT Chng_Id FROM DomainHist WHERE D_Name LIKE 'uc064%');

DELETE FROM Contact WHERE D_Name LIKE 'uc064%';
DELETE FROM Contact WHERE D_Name LIKE 'uc064%';
DELETE FROM DNS WHERE D_Name LIKE 'uc064%';
DELETE FROM DNS WHERE D_Name LIKE 'uc064%';
DELETE FROM Domain WHERE D_Name LIKE 'uc064%';
DELETE FROM DomainHist WHERE D_Name LIKE 'uc064%';

