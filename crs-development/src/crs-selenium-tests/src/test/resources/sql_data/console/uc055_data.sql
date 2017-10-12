INSERT INTO NicHandleHist VALUES (NULL, 'UC055AA-IEDR', 'Use Case 055 - Direct', (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc055_aa@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'UC055AA-IEDR', 'A', NULL, 'YES', 'PORTAL', NOW());
INSERT INTO NicHandle VALUES ('UC055AA-IEDR', 'Use Case 055 - Direct', 1, 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc055_aa@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'UC055AA-IEDR', 'A', NULL, 'YES', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC055AA-IEDR'));
INSERT INTO AccessHist VALUES (NULL,(SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC055AA-IEDR'), 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 2048, 'NO', 'PORTAL', NOW(), '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '');
INSERT INTO Access VALUES ('UC055AA-IEDR', 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 2048, 'NO', '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '', (SELECT MAX(A.Chng_Id) FROM AccessHist A JOIN NicHandleHist NH  ON A.Nic_Handle_Chng_Id = NH.Chng_Id WHERE NH.Nic_Handle = 'UC055AA-IEDR'));

INSERT INTO NicHandleHist VALUES (NULL, 'UC055AB-IEDR', 'Use Case 055 - Registrar', (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc055_ab@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'UC055AB-IEDR', 'A', NULL, 'YES', 'PORTAL', NOW());
INSERT INTO NicHandle VALUES ('UC055AB-IEDR', 'Use Case 055 - Registrar', 1, 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc055_ab@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'UC055AB-IEDR', 'A', NULL, 'YES', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC055AB-IEDR'));
INSERT INTO AccessHist VALUES (NULL,(SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC055AB-IEDR'), 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 2, 'NO', 'PORTAL', NOW(), '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '');
INSERT INTO Access VALUES ('UC055AB-IEDR', 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 2, 'NO', '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '', (SELECT MAX(A.Chng_Id) FROM AccessHist A JOIN NicHandleHist NH  ON A.Nic_Handle_Chng_Id = NH.Chng_Id WHERE NH.Nic_Handle = 'UC055AB-IEDR'));
INSERT INTO AccountHist VALUES (NULL, 700, 'Test Registrar', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC055AA-IEDR'), 'www.testregistrar.ie', 'Active', '2011-12-08', '2011-12-08', 'NO', 'NO', '2011-12-08 15:00:49', 'test data generation script', NULL, NOW(), 'UC055AA-IEDR');
INSERT INTO Account VALUES (700, 'Test Registrar', 'UC055AA-IEDR', 'www.testregistrar.ie', 'Active', '2011-12-08', '2011-12-08', 'NO', 'NO', '2011-12-08 15:00:49', 'test data generation script', NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 700));
UPDATE NicHandleHist SET Account_Chng_Id = (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 700) WHERE Nic_Handle = 'UC055AA-IEDR';
UPDATE NicHandle SET A_Number = 700 WHERE Nic_Handle = 'UC055AA-IEDR';

# UC#055: View Transaction History - UC055-SC01: View Actual Deposit Account Transaction History
INSERT INTO DepositHist VALUES (NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC055AB-IEDR'), NOW() - Interval 10 Day, 0.00, 0.00, 'UC055-SC01', 0.00, 'INIT', NULL, NULL, NOW() - Interval 10 Day);
INSERT INTO DepositHist VALUES (NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC055AB-IEDR'), NOW() - Interval 5 Day, 0.00, 2000.00, 'UC055-SC01', 2000.00, 'TOPUP', NULL, NULL, NOW() - Interval 5 Day);
INSERT INTO DepositHist VALUES (NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC055AB-IEDR'), NOW() - Interval 1 Day, 2000.00, 1000.00, 'UC055-SC01', -1000.00, 'SETTLEMENT', NULL, NULL, NOW() - Interval 1 Day);
INSERT INTO DepositHist VALUES (NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC055AB-IEDR'), NOW(), 1000.00, 1500.00, 'UC055-SC01', +500.00, 'MANUAL', NULL, NULL, NOW());

# UC#055: View Transaction History - UC055-SC02: View Available Deposit Account Transaction History

INSERT INTO DomainHist VALUES (NULL, 'uc055-sc02a.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '25', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc055-sc02a.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '25', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc055-sc02a.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc055-sc02a.ie'), 'uc055-sc02a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc055-sc02a.ie'), 'uc055-sc02a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc055-sc02a.ie'), 'uc055-sc02a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc055-sc02a.ie'), 'uc055-sc02a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc055-sc02a.ie', 'XOE550-IEDR', 'Admin'), ('uc055-sc02a.ie', 'UC055AB-IEDR', 'Billing'), ('uc055-sc02a.ie', 'UC055AB-IEDR', 'Creator'), ('uc055-sc02a.ie', 'UC055AB-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc055-sc02a.ie'), 'uc055-sc02a.ie', 'ns1.uc055-sc02a.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc055-sc02a.ie'), 'uc055-sc02a.ie', 'ns2.uc055-sc02a.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc055-sc02a.ie'), 'uc055-sc02a.ie', 'ns3.uc055-sc02a.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc055-sc02a.ie', 'ns1.uc055-sc02a.ie', '10.10.121.23', NULL, '1'), ('uc055-sc02a.ie', 'ns2.uc055-sc02a.ie', '1.3.4.2', NULL, '2'), ('uc055-sc02a.ie', 'ns3.uc055-sc02a.ie', '10.12.13.14', NULL, '3');

INSERT INTO Invoice VALUES (NULL, 'INVUC055a', 'GUEST ACCOUNT', 600, '1 The Road, Some Street', NULL, NULL, 'UC055AB-IEDR', 'YES', 121, 14, 9, NOW(), 'd41d8cd98f00b204e9800998ecf8427e', 108000, 90000, 18000);
INSERT INTO TransactionIndex VALUES (NULL);
INSERT INTO Transaction VALUES ((SELECT MAX(id) FROM TransactionIndex), 'YES', 'YES', '44160', 'NO', NULL, NOW(), (SELECT ID FROM Invoice WHERE Invoice_Number = 'INVUC055a'), '36800', '7360', '20130515121643-C-3505172', 'NO', NULL, NULL, '111723', '13686130436804');
INSERT INTO TransactionHist VALUES ((SELECT t.ID FROM Transaction t JOIN Invoice i on t.Invoice_ID = i.ID WHERE i.Invoice_Number = 'INVUC055a'), 'YES', 'YES', '44160', 'NO', NULL, NOW(), (SELECT ID FROM Invoice WHERE Invoice_Number = 'INVUC055a'), '36800', '7360', '20130515121643-C-3505172', 'NO', NULL, NULL);
INSERT INTO ReservationIndex VALUES (NULL);
INSERT INTO Reservation VALUES ((SELECT MAX(id) FROM ReservationIndex), 'UC055AB-IEDR', 'uc055-sc02a.ie', 24, NOW(), 184.00, 2, 36.80, 'YES', 'NO', NULL, NULL, (SELECT t.ID FROM Transaction t JOIN Invoice i on t.Invoice_ID = i.ID WHERE i.Invoice_Number = 'INVUC055a'), (SELECT Id FROM Product WHERE P_Active = 'YES' AND CURDATE() BETWEEN P_Valid_From_Dt AND P_Valid_To_Dt AND P_Reg = 'YES' AND P_Guest = 'NO' AND P_Duration = 2), 'renewal', NULL, NULL, 'Deposit');
INSERT INTO ReservationHist VALUES ((SELECT ID FROM Reservation WHERE Domain_Name = 'uc055-sc02a.ie'), (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC055AB-IEDR'), (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc055-sc02a.ie'), 24, NOW(), 184.00, 2, 36.80, 'YES', 'NO', NOW(), NULL, (SELECT t.ID FROM Transaction t JOIN Invoice i on t.Invoice_ID = i.ID WHERE i.Invoice_Number = 'INVUC055a'), (SELECT Id FROM Product WHERE P_Active = 'YES' AND CURDATE() BETWEEN P_Valid_From_Dt AND P_Valid_To_Dt AND P_Reg = 'YES' AND P_Guest = 'NO' AND P_Duration = 2), 'renewal', NULL, NULL, 'Deposit');

INSERT INTO DomainHist VALUES (NULL, 'uc055-sc02b.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '25', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc055-sc02b.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '25', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc055-sc02b.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc055-sc02b.ie'), 'uc055-sc02b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc055-sc02b.ie'), 'uc055-sc02b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc055-sc02b.ie'), 'uc055-sc02b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc055-sc02b.ie'), 'uc055-sc02b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc055-sc02b.ie', 'XOE550-IEDR', 'Admin'), ('uc055-sc02b.ie', 'UC055AB-IEDR', 'Billing'), ('uc055-sc02b.ie', 'UC055AB-IEDR', 'Creator'), ('uc055-sc02b.ie', 'UC055AB-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc055-sc02b.ie'), 'uc055-sc02b.ie', 'ns1.uc055-sc02b.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc055-sc02b.ie'), 'uc055-sc02b.ie', 'ns2.uc055-sc02b.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc055-sc02b.ie'), 'uc055-sc02b.ie', 'ns3.uc055-sc02b.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc055-sc02b.ie', 'ns1.uc055-sc02b.ie', '10.10.121.23', NULL, '1'), ('uc055-sc02b.ie', 'ns2.uc055-sc02b.ie', '1.3.4.2', NULL, '2'), ('uc055-sc02b.ie', 'ns3.uc055-sc02b.ie', '10.12.13.14', NULL, '3');

INSERT INTO Invoice VALUES (NULL, 'INVUC055b', 'GUEST ACCOUNT', 600, '1 The Road, Some Street', NULL, NULL, 'UC055AB-IEDR', 'YES', 121, 14, 9, NOW(), 'd41d8cd98f00b204e9800998ecf8427e', 108000, 90000, 18000);
INSERT INTO TransactionIndex VALUES (NULL);
INSERT INTO Transaction VALUES ((SELECT MAX(id) FROM TransactionIndex), 'YES', 'YES', '44160', 'NO', NULL, NOW(), (SELECT ID FROM Invoice WHERE Invoice_Number = 'INVUC055b'), '36800', '7360', '20130515121643-C-3505172', 'NO', NULL, NULL, '111723', '13686130436804');
INSERT INTO TransactionHist VALUES ((SELECT t.ID FROM Transaction t JOIN Invoice i on t.Invoice_ID = i.ID WHERE i.Invoice_Number = 'INVUC055b'), 'YES', 'YES', '44160', 'NO', NULL, NOW(), (SELECT ID FROM Invoice WHERE Invoice_Number = 'INVUC055b'), '36800', '7360', '20130515121643-C-3505172', 'NO', NULL, NULL);
INSERT INTO ReservationIndex VALUES (NULL);
INSERT INTO Reservation VALUES ((SELECT MAX(id) FROM ReservationIndex), 'UC055AB-IEDR', 'uc055-sc02b.ie', 24, NOW(), 184.00, 2, 36.80, 'YES', 'NO', NOW(), NULL, (SELECT t.ID FROM Transaction t JOIN Invoice i on t.Invoice_ID = i.ID WHERE i.Invoice_Number = 'INVUC055b'), (SELECT Id FROM Product WHERE P_Active = 'YES' AND CURDATE() BETWEEN P_Valid_From_Dt AND P_Valid_To_Dt AND P_Reg = 'YES' AND P_Guest = 'NO' AND P_Duration = 2), 'renewal', NULL, NULL, 'Deposit');
INSERT INTO ReservationHist VALUES ((SELECT ID FROM Reservation WHERE Domain_Name = 'uc055-sc02b.ie'), (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC055AB-IEDR'), (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc055-sc02b.ie'), 24, NOW(), 184.00, 2, 36.80, 'YES', 'NO', NOW(), NULL, (SELECT t.ID FROM Transaction t JOIN Invoice i on t.Invoice_ID = i.ID WHERE i.Invoice_Number = 'INVUC055b'), (SELECT Id FROM Product WHERE P_Active = 'YES' AND CURDATE() BETWEEN P_Valid_From_Dt AND P_Valid_To_Dt AND P_Reg = 'YES' AND P_Guest = 'NO' AND P_Duration = 2), 'renewal', NULL, NULL, 'Deposit');

# UC#055: View Transaction History - UC055-SC03: View Credit Card Transaction History

INSERT INTO DomainHist VALUES (NULL, 'uc055-sc03.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '25', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc055-sc03.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '25', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc055-sc03.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc055-sc03.ie'), 'uc055-sc03.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc055-sc03.ie'), 'uc055-sc03.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc055-sc03.ie'), 'uc055-sc03.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc055-sc03.ie'), 'uc055-sc03.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc055-sc03.ie', 'XOE550-IEDR', 'Admin'), ('uc055-sc03.ie', 'UC055AB-IEDR', 'Billing'), ('uc055-sc03.ie', 'UC055AB-IEDR', 'Creator'), ('uc055-sc03.ie', 'UC055AB-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc055-sc03.ie'), 'uc055-sc03.ie', 'ns1.uc055-sc03.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc055-sc03.ie'), 'uc055-sc03.ie', 'ns2.uc055-sc03.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc055-sc03.ie'), 'uc055-sc03.ie', 'ns3.uc055-sc03.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc055-sc03.ie', 'ns1.uc055-sc03.ie', '10.10.121.23', NULL, '1'), ('uc055-sc03.ie', 'ns2.uc055-sc03.ie', '1.3.4.2', NULL, '2'), ('uc055-sc03.ie', 'ns3.uc055-sc03.ie', '10.12.13.14', NULL, '3');

INSERT INTO Invoice VALUES (NULL, 'INVUC055c', 'GUEST ACCOUNT', 600, '1 The Road, Some Street', NULL, NULL, 'UC055AB-IEDR', 'YES', 121, 14, 9, NOW(), 'd41d8cd98f00b204e9800998ecf8427e', 108000, 90000, 18000);
INSERT INTO TransactionIndex VALUES (NULL);
INSERT INTO Transaction VALUES ((SELECT MAX(id) FROM TransactionIndex), 'YES', 'YES', '44160', 'NO', NULL, NOW(), (SELECT ID FROM Invoice WHERE Invoice_Number = 'INVUC055c'), '36800', '7360', '20130515121643-C-3505172', 'NO', NULL, NULL, '111723', '13686130436804');
INSERT INTO TransactionHist VALUES ((SELECT t.ID FROM Transaction t JOIN Invoice i on t.Invoice_ID = i.ID WHERE i.Invoice_Number = 'INVUC055c'), 'YES', 'YES', '44160', 'NO', NULL, NOW(), (SELECT ID FROM Invoice WHERE Invoice_Number = 'INVUC055c'), '36800', '7360', '20130515121643-C-3505172', 'NO', NULL, NULL);
INSERT INTO ReservationIndex VALUES (NULL);
INSERT INTO Reservation VALUES ((SELECT MAX(id) FROM ReservationIndex), 'UC055AB-IEDR', 'uc055-sc03.ie', 24, NOW(), 184.00, 2, 36.80, 'YES', 'NO', NOW(), NULL, (SELECT t.ID FROM Transaction t JOIN Invoice i on t.Invoice_ID = i.ID WHERE i.Invoice_Number = 'INVUC055c'), (SELECT Id FROM Product WHERE P_Active = 'YES' AND CURDATE() BETWEEN P_Valid_From_Dt AND P_Valid_To_Dt AND P_Reg = 'YES' AND P_Guest = 'NO' AND P_Duration = 2), 'renewal', NULL, NULL, 'Deposit');
INSERT INTO ReservationHist VALUES ((SELECT ID FROM Reservation WHERE Domain_Name = 'uc055-sc03.ie'), (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC055AB-IEDR'), (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc055-sc03.ie'), 24, NOW(), 184.00, 2, 36.80, 'YES', 'NO', NOW(), NULL, (SELECT t.ID FROM Transaction t JOIN Invoice i on t.Invoice_ID = i.ID WHERE i.Invoice_Number = 'INVUC055c'), (SELECT Id FROM Product WHERE P_Active = 'YES' AND CURDATE() BETWEEN P_Valid_From_Dt AND P_Valid_To_Dt AND P_Reg = 'YES' AND P_Guest = 'NO' AND P_Duration = 2), 'renewal', NULL, NULL, 'Deposit');

# UC#055: View Transaction History - UC055-SC04: View Debit Card Transaction History

INSERT INTO DomainHist VALUES (NULL, 'uc055-sc04.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'UC055AA-IEDR', NOW(), 'NO', '25', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc055-sc04.ie', 'Test Holder 0001', 3, 2, NULL, 1, CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '25', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc055-sc04.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc055-sc04.ie'), 'uc055-sc04.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc055-sc04.ie'), 'uc055-sc04.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc055-sc04.ie'), 'uc055-sc04.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc055-sc04.ie'), 'uc055-sc04.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc055-sc04.ie', 'XOE550-IEDR', 'Admin'), ('uc055-sc04.ie', 'UC055AA-IEDR', 'Billing'), ('uc055-sc04.ie', 'UC055AA-IEDR', 'Creator'), ('uc055-sc04.ie', 'UC055AA-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc055-sc04.ie'), 'uc055-sc04.ie', 'ns1.uc055-sc04.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc055-sc04.ie'), 'uc055-sc04.ie', 'ns2.uc055-sc04.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc055-sc04.ie'), 'uc055-sc04.ie', 'ns3.uc055-sc04.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc055-sc04.ie', 'ns1.uc055-sc04.ie', '10.10.121.23', NULL, '1'), ('uc055-sc04.ie', 'ns2.uc055-sc04.ie', '1.3.4.2', NULL, '2'), ('uc055-sc04.ie', 'ns3.uc055-sc04.ie', '10.12.13.14', NULL, '3');

INSERT INTO Invoice VALUES (NULL, 'INVUC055d', 'GUEST ACCOUNT', 001, '1 The Road, Some Street', NULL, NULL, 'UC055AA-IEDR', 'YES', 121, 14, 9, NOW(), 'd41d8cd98f00b204e9800998ecf8427e', 108000, 90000, 18000);
INSERT INTO TransactionIndex VALUES (NULL);
INSERT INTO Transaction VALUES ((SELECT MAX(id) FROM TransactionIndex), 'YES', 'YES', '44160', 'NO', NULL, NOW(), (SELECT ID FROM Invoice WHERE Invoice_Number = 'INVUC055d'), '36800', '7360', '20130515121643-C-3505172', 'NO', NULL, NULL, '111723', '13686130436804');
INSERT INTO TransactionHist VALUES ((SELECT t.ID FROM Transaction t JOIN Invoice i on t.Invoice_ID = i.ID WHERE i.Invoice_Number = 'INVUC055d'), 'YES', 'YES', '44160', 'NO', NULL, NOW(), (SELECT ID FROM Invoice WHERE Invoice_Number = 'INVUC055d'), '36800', '7360', '20130515121643-C-3505172', 'NO', NULL, NULL);
INSERT INTO ReservationIndex VALUES (NULL);
INSERT INTO Reservation VALUES ((SELECT MAX(id) FROM ReservationIndex), 'UC055AA-IEDR', 'uc055-sc04.ie', 24, NOW(), 184.00, 2, 36.80, 'YES', 'NO', NOW(), NULL, (SELECT t.ID FROM Transaction t JOIN Invoice i on t.Invoice_ID = i.ID WHERE i.Invoice_Number = 'INVUC055d'), (SELECT Id FROM Product WHERE P_Active = 'YES' AND CURDATE() BETWEEN P_Valid_From_Dt AND P_Valid_To_Dt AND P_Reg = 'YES' AND P_Guest = 'YES' AND P_Duration = 2), 'renewal', NULL, NULL, 'Debit Card');
INSERT INTO ReservationHist VALUES ((SELECT ID FROM Reservation WHERE Domain_Name = 'uc055-sc04.ie'), (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC055AA-IEDR'), (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc055-sc04.ie'), 24, NOW(), 184.00, 2, 36.80, 'YES', 'NO', NOW(), NULL, (SELECT t.ID FROM Transaction t JOIN Invoice i on t.Invoice_ID = i.ID WHERE i.Invoice_Number = 'INVUC055d'), (SELECT Id FROM Product WHERE P_Active = 'YES' AND CURDATE() BETWEEN P_Valid_From_Dt AND P_Valid_To_Dt AND P_Reg = 'YES' AND P_Guest = 'NO' AND P_Duration = 2), 'renewal', NULL, NULL, 'Debit Card');

