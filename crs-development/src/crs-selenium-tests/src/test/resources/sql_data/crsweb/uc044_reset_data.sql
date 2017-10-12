UPDATE Product SET P_Active = 'YES' WHERE P_Duration = 10 AND P_Reg = 'YES' AND P_Valid_From_Dt <= CURDATE() AND P_Valid_To_Dt >= CURDATE();
DELETE FROM Product WHERE P_Code = 'UC04410YR';

DELETE FROM Contact WHERE D_Name LIKE 'uc044%';
DELETE FROM Contact WHERE D_Name LIKE 'uc044%';
DELETE FROM DNS WHERE D_Name LIKE 'uc044%';
DELETE FROM DNS WHERE D_Name LIKE 'uc044%';
DELETE FROM Domain WHERE D_Name LIKE 'uc044%';
DELETE FROM DomainHist WHERE D_Name LIKE 'uc044%';

DELETE FROM TicketNameserver WHERE T_Number IN (SELECT T_Number FROM Ticket WHERE D_Name LIKE 'uc044%');
DELETE FROM TicketNameserverHist WHERE T_Number IN (SELECT T_Number FROM Ticket WHERE D_Name LIKE 'uc044%');
DELETE FROM Ticket WHERE D_Name LIKE 'uc044%';
DELETE FROM TicketHist WHERE D_Name LIKE 'uc044%';

