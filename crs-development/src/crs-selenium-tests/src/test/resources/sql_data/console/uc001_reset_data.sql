DELETE FROM Access WHERE Nic_Handle LIKE 'uc001%';
DELETE FROM AccessHist WHERE Nic_Handle_Chng_ID IN (SELECT Chng_ID FROM NicHandleHist WHERE Nic_Handle LIKE 'uc001%');
DELETE FROM NicHandle WHERE Nic_Handle LIKE 'uc001%';
DELETE FROM NicHandleHist WHERE Nic_Handle LIKE 'uc001%';

DELETE FROM Access WHERE Nic_Handle IN (SELECT Nic_Handle FROM NicHandle WHERE NH_Name LIKE 'uc001%');
DELETE FROM AccessHist WHERE Nic_Handle_Chng_ID IN (SELECT Chng_ID FROM NicHandleHist WHERE NH_Name LIKE 'uc001%');
DELETE FROM NicHandle WHERE NH_Name LIKE 'uc001%';
DELETE FROM NicHandleHist WHERE NH_Name LIKE 'uc001%';

DELETE FROM TicketNameserver WHERE T_Number IN (SELECT T_Number FROM Ticket WHERE D_Name LIKE  'uc001%');
DELETE FROM TicketNameserverHist WHERE T_Number IN (SELECT T_Number FROM Ticket WHERE D_Name LIKE  'uc001%');
DELETE FROM Ticket WHERE D_Name LIKE 'uc001%';
DELETE FROM TicketHist WHERE D_Name LIKE 'uc001%';

DELETE FROM Transaction WHERE ID IN (SELECT Transaction_ID FROM Reservation WHERE Domain_Name LIKE 'uc001%');
DELETE FROM Reservation where Domain_Name LIKE 'uc001%';

DELETE FROM TransactionHist WHERE ID IN (SELECT Transaction_ID FROM ReservationHist rh LEFT JOIN DomainHist dh ON dh.Chng_ID = rh.Domain_Chng_ID WHERE dh.D_Name LIKE 'uc001%');
DELETE FROM ReservationHist WHERE Domain_Chng_ID IN (SELECT Chng_ID FROM DomainHist WHERE D_Name LIKE 'uc001%');

DELETE FROM Contact WHERE D_Name LIKE 'uc001%';
DELETE FROM ContactHist WHERE D_Name LIKE 'uc001%';
DELETE FROM DNS WHERE D_Name LIKE 'uc001%';
DELETE FROM DNSHist WHERE D_Name LIKE 'uc001%';
DELETE FROM Domain WHERE D_Name LIKE 'uc001%';
DELETE FROM DomainHist WHERE D_Name LIKE 'uc001%';
