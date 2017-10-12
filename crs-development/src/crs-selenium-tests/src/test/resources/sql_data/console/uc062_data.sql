INSERT INTO NicHandleHist VALUES (NULL, 'UC062A-IEDR', 'Use Case 001 - Direct - VAT Extempt', (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), 'Registrar co Limited', '1 The Road, Some Street', 7, 119, 'uc062a@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'UC062-IEDR', 'B', NULL, 'YES', 'PORTAL', NOW());
INSERT INTO NicHandle VALUES ('UC062A-IEDR', 'Use Case 001 - Direct - VAT Extempt', 1, 'Registrar co Limited', '1 The Road, Some Street', 7, 119, 'uc062a@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'UC062-IEDR', 'B', NULL, 'YES', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC062A-IEDR'));
INSERT into AccessHist VALUES (NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC062A-IEDR'), 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 2048, 'NO', 'PORTAL', NOW(), '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '');
INSERT into Access VALUES ('UC062A-IEDR', 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 2048, 'NO', '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '', (SELECT MAX(A.Chng_Id) FROM AccessHist A JOIN NicHandleHist NH  ON A.Nic_Handle_Chng_Id = NH.Chng_Id WHERE NH.Nic_Handle = 'UC062A-IEDR'));

INSERT INTO NicHandleHist VALUES (NULL, 'UC062B-IEDR', 'Newly Created Direct', (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), 'Registrar co Limited', '1 The Road, Some Street', 7, 119, 'uc062b@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'UC062-IEDR', 'B', NULL, 'NO', 'PORTAL', NOW());
INSERT INTO NicHandle VALUES ('UC062B-IEDR', 'Newly Created Direct', 1, 'Registrar co Limited', '1 The Road, Some Street', 7, 119, 'uc062b@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'UC062-IEDR', 'B', NULL, 'NO', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC062B-IEDR'));
INSERT into AccessHist VALUES (NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC062B-IEDR'), 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 2048, 'NO', 'PORTAL', NOW(), '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '');
INSERT into Access VALUES ('UC062B-IEDR', 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 2048, 'NO', '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '', (SELECT MAX(A.Chng_Id) FROM AccessHist A JOIN NicHandleHist NH  ON A.Nic_Handle_Chng_Id = NH.Chng_Id WHERE NH.Nic_Handle = 'UC062B-IEDR'));

INSERT INTO DomainHist VALUES (NULL, 'uc062-sc01.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1),  CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'UC062A-IEDR', NOW(), 'NO', '25', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc062-sc01.ie', 'Test Holder 0001', 3, 2, NULL, 1,  CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', '25', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc062-sc01.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc062-sc01.ie'), 'uc062-sc01.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC062A-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc062-sc01.ie'), 'uc062-sc01.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC062A-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc062-sc01.ie'), 'uc062-sc01.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC062A-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc062-sc01.ie'), 'uc062-sc01.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC062A-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc062-sc01.ie', 'UC062A-IEDR', 'Admin'), ('uc062-sc01.ie', 'UC062A-IEDR', 'Billing'), ('uc062-sc01.ie', 'UC062A-IEDR', 'Creator'), ('uc062-sc01.ie', 'UC062A-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc062-sc01.ie'), 'uc062-sc01.ie', 'ns1.uc062-sc01.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc062-sc01.ie'), 'uc062-sc01.ie', 'ns2.uc062-sc01.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc062-sc01.ie'), 'uc062-sc01.ie', 'ns3.uc062-sc01.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc062-sc01.ie', 'ns1.uc062-sc01.ie', '10.10.121.23', NULL, '1'), ('uc062-sc01.ie', 'ns2.uc062-sc01.ie', '1.3.4.2', NULL, '2'), ('uc062-sc01.ie', 'ns3.uc062-sc01.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc062-nosc01.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '18', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc062-nosc01.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', '18', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc062-nosc01.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc062-nosc01.ie'), 'uc062-nosc01.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc062-nosc01.ie'), 'uc062-nosc01.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc062-nosc01.ie'), 'uc062-nosc01.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc062-nosc01.ie'), 'uc062-nosc01.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc062-nosc01.ie', 'XOE550-IEDR', 'Admin'), ('uc062-nosc01.ie', 'XBC189-IEDR', 'Billing'), ('uc062-nosc01.ie', 'XBC189-IEDR', 'Creator'), ('uc062-nosc01.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc062-nosc01.ie'), 'uc062-nosc01.ie', 'ns1.uc062-nosc01.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc062-nosc01.ie'), 'uc062-nosc01.ie', 'ns2.uc062-nosc01.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc062-nosc01.ie'), 'uc062-nosc01.ie', 'ns3.uc062-nosc01.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc062-nosc01.ie', 'ns1.uc062-nosc01.ie', '10.10.121.23', NULL, '1'), ('uc062-nosc01.ie', 'ns2.uc062-nosc01.ie', '1.3.4.2', NULL, '2'), ('uc062-nosc01.ie', 'ns3.uc062-nosc01.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc062-nosc03.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1),  CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'UC062B-IEDR', NOW(), 'NO', '25', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc062-nosc03.ie', 'Test Holder 0001', 3, 2, NULL, 1,  CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', '25', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc062-nosc03.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc062-nosc03.ie'), 'uc062-nosc03.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC062B-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc062-nosc03.ie'), 'uc062-nosc03.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC062B-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc062-nosc03.ie'), 'uc062-nosc03.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC062B-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc062-nosc03.ie'), 'uc062-nosc03.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC062B-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc062-nosc03.ie', 'UC062B-IEDR', 'Admin'), ('uc062-nosc03.ie', 'UC062B-IEDR', 'Billing'), ('uc062-nosc03.ie', 'UC062B-IEDR', 'Creator'), ('uc062-nosc03.ie', 'UC062B-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc062-nosc03.ie'), 'uc062-nosc03.ie', 'ns1.uc062-nosc03.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc062-nosc03.ie'), 'uc062-nosc03.ie', 'ns2.uc062-nosc03.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc062-nosc03.ie'), 'uc062-nosc03.ie', 'ns3.uc062-nosc03.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc062-nosc03.ie', 'ns1.uc062-nosc03.ie', '10.10.121.23', NULL, '1'), ('uc062-nosc03.ie', 'ns2.uc062-nosc03.ie', '1.3.4.2', NULL, '2'), ('uc062-nosc03.ie', 'ns3.uc062-nosc03.ie', '10.12.13.14', NULL, '3');

