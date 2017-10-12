INSERT INTO DomainHist VALUES (NULL, 'uc025-sc01.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XDD274-IEDR', NOW(), 'NO', '25', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc025-sc01.ie', 'Test Holder 0001', 3, 2, NULL, 1, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', '25', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc025-sc01.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc025-sc01.ie'), 'uc025-sc01.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc025-sc01.ie'), 'uc025-sc01.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc025-sc01.ie'), 'uc025-sc01.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc025-sc01.ie'), 'uc025-sc01.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc025-sc01.ie', 'XDD274-IEDR', 'Admin'), ('uc025-sc01.ie', 'XDD274-IEDR', 'Billing'), ('uc025-sc01.ie', 'XDD274-IEDR', 'Creator'), ('uc025-sc01.ie', 'XDD274-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc025-sc01.ie'), 'uc025-sc01.ie', 'ns1.uc025-sc01.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc025-sc01.ie'), 'uc025-sc01.ie', 'ns2.uc025-sc01.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc025-sc01.ie'), 'uc025-sc01.ie', 'ns3.uc025-sc01.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc025-sc01.ie', 'ns1.uc025-sc01.ie', '10.10.121.23', NULL, '1'), ('uc025-sc01.ie', 'ns2.uc025-sc01.ie', '1.3.4.2', NULL, '2'), ('uc025-sc01.ie', 'ns3.uc025-sc01.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc025-nosc02.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '25', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc025-nosc02.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', '25', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc025-nosc02.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc025-nosc02.ie'), 'uc025-nosc02.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc025-nosc02.ie'), 'uc025-nosc02.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc025-nosc02.ie'), 'uc025-nosc02.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc025-nosc02.ie'), 'uc025-nosc02.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc025-nosc02.ie', 'XBC189-IEDR', 'Admin'), ('uc025-nosc02.ie', 'XBC189-IEDR', 'Billing'), ('uc025-nosc02.ie', 'XBC189-IEDR', 'Creator'), ('uc025-nosc02.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc025-nosc02.ie'), 'uc025-nosc02.ie', 'ns1.uc025-nosc02.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc025-nosc02.ie'), 'uc025-nosc02.ie', 'ns2.uc025-nosc02.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc025-nosc02.ie'), 'uc025-nosc02.ie', 'ns3.uc025-nosc02.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc025-nosc02.ie', 'ns1.uc025-nosc02.ie', '10.10.121.23', NULL, '1'), ('uc025-nosc02.ie', 'ns2.uc025-nosc02.ie', '1.3.4.2', NULL, '2'), ('uc025-nosc02.ie', 'ns3.uc025-nosc02.ie', '10.12.13.14', NULL, '3');

INSERT INTO TransactionIndex VALUES (NULL);
INSERT INTO Transaction VALUES ((SELECT MAX(id) FROM TransactionIndex), 'NO', 'NO', '7800', 'NO', NULL, CURDATE() - Interval 28 day, NULL, '6500', '1300', '20150507140846-C-4336038', 'NO', NULL, NULL, '12345', '14310031020574821');

INSERT INTO ReservationIndex VALUES (NULL);
INSERT INTO Reservation VALUES ((SELECT MAX(id) FROM ReservationIndex), 'XDD274-IEDR', 'uc025-sc01.ie', '12', CURDATE() - Interval 28 day, '65.00', '2', '13.00', 'YES', 'NO', NULL, NULL, (SELECT t.ID FROM Transaction t WHERE t.Order_ID = '20150507140846-C-4336038'), (SELECT Id FROM Product WHERE P_Active='YES' AND CURDATE() BETWEEN P_Valid_From_Dt AND P_Valid_To_Dt AND P_Reg='YES' and P_Guest='NO' AND P_Duration=1), 'renewal', NULL, NULL, 'Credit Card');

