DROP TABLE IF EXISTS example_data;

DELETE FROM SecondaryMarketSellRequest WHERE Creator_NH IN ('XBC189-IEDR', 'XBC199-IEDR', 'XNV498-IEDR');
DELETE SH.* FROM SecondaryMarketSellRequestHist SH JOIN NicHandleHist NHH ON SH.Creator_NH_Chng_ID = NHH.Chng_ID WHERE NHH.Nic_Handle IN ('XBC189-IEDR', 'XBC199-IEDR', 'XNV498-IEDR');
DELETE FROM SecondaryMarketBuyRequest WHERE Creator_NH IN ('XBC189-IEDR', 'XBC199-IEDR', 'XNV498-IEDR');
DELETE BRH.* FROM SecondaryMarketBuyRequestHist BRH JOIN NicHandleHist NHH ON BRH.Creator_NH_Chng_ID = NHH.Chng_ID WHERE NHH.Nic_Handle IN ('XBC189-IEDR', 'XBC199-IEDR', 'XNV498-IEDR');
# BuyRequest's telecoms are automatically deleted by database due to FOREIGN KEY ON DELETE CASCADE

DELETE FROM Transaction WHERE ID IN (SELECT Transaction_ID FROM Reservation WHERE Nic_Handle IN ('XBC189-IEDR', 'XBC199-IEDR', 'XNV498-IEDR'));
DELETE FROM TransactionHist WHERE ID IN (SELECT Transaction_ID FROM Reservation WHERE Nic_Handle IN ('XBC189-IEDR', 'XBC199-IEDR', 'XNV498-IEDR'));
DELETE FROM Reservation WHERE Nic_Handle IN ('XBC189-IEDR', 'XBC199-IEDR', 'XNV498-IEDR');
DELETE FROM ReservationHist WHERE Nic_Handle_Chng_Id IN (SELECT Chng_Id FROM NicHandleHist WHERE Nic_Handle IN ('XBC189-IEDR', 'XBC199-IEDR', 'XNV498-IEDR'));
DELETE FROM Invoice WHERE Account_Number IN (600, 602, 603);
DELETE FROM Access WHERE Nic_Handle IN (SELECT Nic_Handle FROM NicHandle WHERE A_Number IN (600, 602, 603));
DELETE FROM AccessHist WHERE Nic_Handle_Chng_Id IN (SELECT NH.Chng_Id FROM NicHandleHist NH JOIN AccountHist A ON A.Chng_Id = NH.Account_Chng_Id WHERE A.A_Number IN (600, 602, 603));
DELETE FROM Telecom WHERE Nic_Handle IN (SELECT Nic_Handle FROM NicHandle WHERE A_Number IN (600, 602, 603));
DELETE FROM TelecomHist WHERE Nic_Handle IN (SELECT Nic_Handle FROM NicHandle WHERE A_Number IN (600, 602, 603));
DELETE FROM Deposit WHERE Nic_Handle IN ('XBC189-IEDR', 'XBC199-IEDR', 'XNV498-IEDR');
DELETE DH.* FROM DepositHist DH JOIN NicHandleHist NHH ON DH.Nic_Handle_Chng_ID = NHH.Chng_ID WHERE NHH.Nic_Handle IN ('XBC189-IEDR', 'XBC199-IEDR', 'XNV498-IEDR');
DELETE FROM Reseller_Defaults WHERE Nic_handle IN ('XBC189-IEDR', 'XBC199-IEDR', 'XNV498-IEDR');
DELETE FROM NicHandle WHERE A_Number IN (600, 602, 603);
DELETE NHH.* FROM NicHandleHist NHH JOIN AccountHist AH ON NHH.Account_Chng_ID = AH.Chng_ID WHERE AH.A_Number IN (600, 602, 603);
DELETE FROM Contact WHERE D_Name IN (SELECT D_Name FROM Domain WHERE A_Number IN (600, 602, 603));
DELETE FROM ContactHist WHERE D_Name IN (SELECT D_Name FROM Domain WHERE A_Number IN (600, 602, 603));
DELETE FROM DNS WHERE D_Name IN (SELECT D_Name FROM Domain WHERE A_Number IN (600, 602, 603));
DELETE FROM DNSHist WHERE D_Name IN (SELECT D_Name FROM Domain WHERE A_Number IN (600, 602, 603));
UPDATE Domain set DSM_State = 17 WHERE D_Name IN ('love.ie', 'store-it.ie', 'holistictherapy.ie', 'ireland.ie');
UPDATE Domain set D_Authcode = '68OM3LQV7C3F', D_Authc_Exp_Dt = CURDATE() + Interval 12 day WHERE D_Name = 'store-it.ie';
DELETE FROM Domain WHERE A_Number IN (600, 602, 603);
DELETE DH.* FROM DomainHist DH JOIN AccountHist AH ON DH.Account_Chng_ID = AH.Chng_ID WHERE AH.A_Number IN (600, 602, 603);
DELETE FROM TicketNameserver WHERE T_Number IN (SELECT T_Number FROM Ticket WHERE A_Number IN (600, 602, 603));
DELETE FROM TicketNameserverHist WHERE T_Number IN (SELECT T_Number FROM Ticket WHERE A_Number IN (600, 602, 603));
DELETE TH.* FROM TicketHist TH JOIN AccountHist AH ON TH.Account_Chng_ID = AH.Chng_ID WHERE AH.A_Number IN (600, 602, 603);
DELETE FROM Ticket WHERE A_Number IN (600, 602, 603);
DELETE FROM Account WHERE A_Number IN (600, 602, 603);
DELETE FROM AccountHist WHERE A_Number IN (600, 602, 603);

DELETE FROM NicHandle WHERE Nic_handle = 'XDD274-IEDR';
DELETE FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR';
DELETE FROM Contact WHERE D_Name LIKE 'direct-account-%';
DELETE FROM ContactHist WHERE D_Name LIKE 'direct-account-%';
DELETE FROM DNS WHERE D_Name LIKE 'direct-account-%';
DELETE FROM DNSHist WHERE D_Name LIKE 'direct-account-%';
DELETE FROM Domain WHERE D_Name LIKE 'direct-account-%';
DELETE FROM DomainHist WHERE D_Name LIKE 'direct-account-%';
DELETE FROM TicketNameserver WHERE T_Number IN (SELECT T_Number FROM Ticket WHERE Creator_NH = 'XDD274-IEDR');
DELETE FROM TicketNameserverHist WHERE T_Number IN (SELECT T_Number FROM Ticket WHERE Creator_NH = 'XDD274-IEDR');
DELETE FROM Ticket WHERE Creator_NH = 'XDD274-IEDR';
DELETE TH.* FROM TicketHist TH JOIN NicHandleHist NHH ON TH.Creator_NH_Chng_ID = NHH.Chng_ID WHERE NHH.Nic_Handle = 'XDD274-IEDR';
DELETE FROM Access WHERE Nic_Handle = 'XDD274-IEDR';
DELETE FROM AccessHist WHERE Nic_Handle_Chng_Id IN (SELECT Chng_Id FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR');
DELETE FROM Telecom WHERE Nic_Handle = 'XDD274-IEDR';
DELETE FROM TelecomHist WHERE Nic_Handle = 'XDD274-IEDR';
DELETE FROM Product WHERE P_Code LIKE 'RRe%';
-- TODO: Remove this after CRS-1256 is solved.
UPDATE Product SET P_Active = 'YES' WHERE P_Code IN ('Std1Year', 'RM2Yr');

DELETE FROM Access WHERE Nic_Handle = 'XDD275-IEDR';
DELETE FROM AccessHist WHERE Nic_Handle_Chng_Id IN (SELECT Chng_Id FROM NicHandleHist WHERE Nic_Handle = 'XDD275-IEDR');
DELETE FROM Telecom WHERE Nic_Handle = 'XDD275-IEDR';
DELETE FROM NicHandle WHERE Nic_handle = 'XDD275-IEDR';
DELETE FROM TelecomHist WHERE Nic_Handle = 'XDD275-IEDR';

