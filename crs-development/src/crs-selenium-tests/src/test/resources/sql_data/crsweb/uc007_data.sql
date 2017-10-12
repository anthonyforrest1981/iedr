INSERT INTO NicHandleHist VALUES (NULL, 'UC007AA-IEDR', 'Use Case 007 - Direct', (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc007_aa@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'UC007AA-IEDR', 'B', NULL, 'YES', 'PORTAL', NOW());
INSERT INTO NicHandle VALUES ('UC007AA-IEDR', 'Use Case 007 - Direct', 1, 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc007_aa@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'UC007AA-IEDR', 'B', NULL, 'YES', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC007AA-IEDR'));
INSERT INTO AccessHist VALUES (NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC007AA-IEDR'), 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 2048, 'NO', 'PORTAL', NOW(), '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '');
INSERT INTO Access VALUES ('UC007AA-IEDR', 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 2048, 'NO', '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '', (SELECT MAX(A.Chng_Id) FROM AccessHist A JOIN NicHandleHist NH  ON A.Nic_Handle_Chng_Id = NH.Chng_Id WHERE NH.Nic_Handle = 'UC002AA-IEDR'));

# UC#007: Triple-PASS Domain Billing Transfer Ticket - UC007-SC01: Registrar to Registrar Transfer Happy Path
# L: XNV498-IEDR, G: XBC189-IEDR, DSM 17
INSERT INTO DomainHist VALUES (NULL, 'uc007-sc01.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 603),  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XNV498-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc007-sc01.ie', 'Test Holder 0001', 3, 2, NULL, 603,  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc01.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc01.ie'), 'uc007-sc01.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc01.ie'), 'uc007-sc01.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc01.ie'), 'uc007-sc01.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc01.ie'), 'uc007-sc01.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc007-sc01.ie', 'XOE550-IEDR', 'Admin'), ('uc007-sc01.ie', 'XNV498-IEDR', 'Billing'), ('uc007-sc01.ie', 'XNV498-IEDR', 'Creator'), ('uc007-sc01.ie', 'XNV498-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc01.ie'), 'uc007-sc01.ie', 'ns1.uc007-sc01.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc01.ie'), 'uc007-sc01.ie', 'ns2.uc007-sc01.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc01.ie'), 'uc007-sc01.ie', 'ns3.uc007-sc01.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc007-sc01.ie', 'ns1.uc007-sc01.ie', '10.10.121.23', NULL, '1'), ('uc007-sc01.ie', 'ns2.uc007-sc01.ie', '1.3.4.2', NULL, '2'), ('uc007-sc01.ie', 'ns3.uc007-sc01.ie', '10.12.13.14', NULL, '3');

# UC#007: Triple-PASS Domain Billing Transfer Ticket - UC007-SC02: Registrar to Direct Transfer Happy Path
# L: XNV498-IEDR, G: XDD274-IEDR, DSM 17
INSERT INTO DomainHist VALUES (NULL, 'uc007-sc02.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 603),  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XNV498-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc007-sc02.ie', 'Test Holder 0001', 3, 2, NULL, 603,  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc02.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc02.ie'), 'uc007-sc02.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc02.ie'), 'uc007-sc02.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc02.ie'), 'uc007-sc02.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc02.ie'), 'uc007-sc02.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc007-sc02.ie', 'XOE550-IEDR', 'Admin'), ('uc007-sc02.ie', 'XNV498-IEDR', 'Billing'), ('uc007-sc02.ie', 'XNV498-IEDR', 'Creator'), ('uc007-sc02.ie', 'XNV498-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc02.ie'), 'uc007-sc02.ie', 'ns1.uc007-sc02.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc02.ie'), 'uc007-sc02.ie', 'ns2.uc007-sc02.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc02.ie'), 'uc007-sc02.ie', 'ns3.uc007-sc02.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc007-sc02.ie', 'ns1.uc007-sc02.ie', '10.10.121.23', NULL, '1'), ('uc007-sc02.ie', 'ns2.uc007-sc02.ie', '1.3.4.2', NULL, '2'), ('uc007-sc02.ie', 'ns3.uc007-sc02.ie', '10.12.13.14', NULL, '3');

# UC#007: Triple-PASS Domain Billing Transfer Ticket - UC007-SC03: Direct to Registrar Transfer Happy Path
# L: XDD274-IEDR, G: XBC189-IEDR, DSM 25
INSERT INTO DomainHist VALUES (NULL, 'uc007-sc03.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1),  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XDD274-IEDR', NOW(), 'NO', '25', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc007-sc03.ie', 'Test Holder 0001', 3, 2, NULL, 1,  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '25', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc03.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc03.ie'), 'uc007-sc03.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc03.ie'), 'uc007-sc03.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc03.ie'), 'uc007-sc03.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc03.ie'), 'uc007-sc03.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc007-sc03.ie', 'XOE550-IEDR', 'Admin'), ('uc007-sc03.ie', 'XDD274-IEDR', 'Billing'), ('uc007-sc03.ie', 'XDD274-IEDR', 'Creator'), ('uc007-sc03.ie', 'XDD274-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc03.ie'), 'uc007-sc03.ie', 'ns1.uc007-sc03.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc03.ie'), 'uc007-sc03.ie', 'ns2.uc007-sc03.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc03.ie'), 'uc007-sc03.ie', 'ns3.uc007-sc03.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc007-sc03.ie', 'ns1.uc007-sc03.ie', '10.10.121.23', NULL, '1'), ('uc007-sc03.ie', 'ns2.uc007-sc03.ie', '1.3.4.2', NULL, '2'), ('uc007-sc03.ie', 'ns3.uc007-sc03.ie', '10.12.13.14', NULL, '3');

# UC#007: Triple-PASS Domain Billing Transfer Ticket - UC007-SC04: Registrar to Registrar Charity Transfer
# L: XNV498-IEDR, G: XBC189-IEDR, DSM 113
INSERT INTO DomainHist VALUES (NULL, 'uc007-sc04.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 603),  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XNV498-IEDR', NOW(), 'NO', '113', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc007-sc04.ie', 'Test Holder 0001', 3, 2, NULL, 603,  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '113', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc04.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc04.ie'), 'uc007-sc04.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc04.ie'), 'uc007-sc04.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc04.ie'), 'uc007-sc04.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc04.ie'), 'uc007-sc04.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc007-sc04.ie', 'XOE550-IEDR', 'Admin'), ('uc007-sc04.ie', 'XNV498-IEDR', 'Billing'), ('uc007-sc04.ie', 'XNV498-IEDR', 'Creator'), ('uc007-sc04.ie', 'XNV498-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc04.ie'), 'uc007-sc04.ie', 'ns1.uc007-sc04.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc04.ie'), 'uc007-sc04.ie', 'ns2.uc007-sc04.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc04.ie'), 'uc007-sc04.ie', 'ns3.uc007-sc04.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc007-sc04.ie', 'ns1.uc007-sc04.ie', '10.10.121.23', NULL, '1'), ('uc007-sc04.ie', 'ns2.uc007-sc04.ie', '1.3.4.2', NULL, '2'), ('uc007-sc04.ie', 'ns3.uc007-sc04.ie', '10.12.13.14', NULL, '3');

# UC#007: Triple-PASS Domain Billing Transfer Ticket - UC007-SC05: Involuntary Mailed Registrar to Registrar Transfer
# L: XNV498-IEDR, G: XBC189-IEDR, DSM 82
INSERT INTO DomainHist VALUES (NULL, 'uc007-sc05.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 603),  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XNV498-IEDR', NOW(), 'NO', '82', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc007-sc05.ie', 'Test Holder 0001', 3, 2, NULL, 603,  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '82', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc05.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc05.ie'), 'uc007-sc05.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc05.ie'), 'uc007-sc05.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc05.ie'), 'uc007-sc05.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc05.ie'), 'uc007-sc05.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc007-sc05.ie', 'XOE550-IEDR', 'Admin'), ('uc007-sc05.ie', 'XNV498-IEDR', 'Billing'), ('uc007-sc05.ie', 'XNV498-IEDR', 'Creator'), ('uc007-sc05.ie', 'XNV498-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc05.ie'), 'uc007-sc05.ie', 'ns1.uc007-sc05.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc05.ie'), 'uc007-sc05.ie', 'ns2.uc007-sc05.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc05.ie'), 'uc007-sc05.ie', 'ns3.uc007-sc05.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc007-sc05.ie', 'ns1.uc007-sc05.ie', '10.10.121.23', NULL, '1'), ('uc007-sc05.ie', 'ns2.uc007-sc05.ie', '1.3.4.2', NULL, '2'), ('uc007-sc05.ie', 'ns3.uc007-sc05.ie', '10.12.13.14', NULL, '3');

# UC#007: Triple-PASS Domain Billing Transfer Ticket - UC007-SC06: Involuntary Suspended Registrar to Registrar Transfer Multi-year
# L: XNV498-IEDR, G: XBC189-IEDR, DSM 83
INSERT INTO DomainHist VALUES (NULL, 'uc007-sc06.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 603),  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XNV498-IEDR', NOW(), 'NO', '83', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc007-sc06.ie', 'Test Holder 0001', 3, 2, NULL, 603,  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '83', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc06.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc06.ie'), 'uc007-sc06.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc06.ie'), 'uc007-sc06.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc06.ie'), 'uc007-sc06.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc06.ie'), 'uc007-sc06.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc007-sc06.ie', 'XOE550-IEDR', 'Admin'), ('uc007-sc06.ie', 'XNV498-IEDR', 'Billing'), ('uc007-sc06.ie', 'XNV498-IEDR', 'Creator'), ('uc007-sc06.ie', 'XNV498-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc06.ie'), 'uc007-sc06.ie', 'ns1.uc007-sc06.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc06.ie'), 'uc007-sc06.ie', 'ns2.uc007-sc06.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc06.ie'), 'uc007-sc06.ie', 'ns3.uc007-sc06.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc007-sc06.ie', 'ns1.uc007-sc06.ie', '10.10.121.23', NULL, '1'), ('uc007-sc06.ie', 'ns2.uc007-sc06.ie', '1.3.4.2', NULL, '2'), ('uc007-sc06.ie', 'ns3.uc007-sc06.ie', '10.12.13.14', NULL, '3');

# UC#007: Triple-PASS Domain Billing Transfer Ticket - UC007-SC10: Renew Once Multi-year Registrar to Registrar Transfer
# L: XNV498-IEDR, G: XBC189-IEDR, DSM 49
INSERT INTO DomainHist VALUES (NULL, 'uc007-sc10.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 603),  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XNV498-IEDR', NOW(), 'NO', '49', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc007-sc10.ie', 'Test Holder 0001', 3, 2, NULL, 603,  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '49', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc10.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc10.ie'), 'uc007-sc10.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc10.ie'), 'uc007-sc10.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc10.ie'), 'uc007-sc10.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc10.ie'), 'uc007-sc10.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc007-sc10.ie', 'XOE550-IEDR', 'Admin'), ('uc007-sc10.ie', 'XNV498-IEDR', 'Billing'), ('uc007-sc10.ie', 'XNV498-IEDR', 'Creator'), ('uc007-sc10.ie', 'XNV498-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc10.ie'), 'uc007-sc10.ie', 'ns1.uc007-sc10.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc10.ie'), 'uc007-sc10.ie', 'ns2.uc007-sc10.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc10.ie'), 'uc007-sc10.ie', 'ns3.uc007-sc10.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc007-sc10.ie', 'ns1.uc007-sc10.ie', '10.10.121.23', NULL, '1'), ('uc007-sc10.ie', 'ns2.uc007-sc10.ie', '1.3.4.2', NULL, '2'), ('uc007-sc10.ie', 'ns3.uc007-sc10.ie', '10.12.13.14', NULL, '3');

# UC#007: Triple-PASS Domain Billing Transfer Ticket - UC007-SC09: Autorenew Transfer Registrar to Registrar Multi-year
# L: XNV498-IEDR, G: XBC189-IEDR, DSM 81
INSERT INTO DomainHist VALUES (NULL, 'uc007-sc09.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 603),  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XNV498-IEDR', NOW(), 'NO', '81', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc007-sc09.ie', 'Test Holder 0001', 3, 2, NULL, 603,  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '81', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc09.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc09.ie'), 'uc007-sc09.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc09.ie'), 'uc007-sc09.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc09.ie'), 'uc007-sc09.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc09.ie'), 'uc007-sc09.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc007-sc09.ie', 'XOE550-IEDR', 'Admin'), ('uc007-sc09.ie', 'XNV498-IEDR', 'Billing'), ('uc007-sc09.ie', 'XNV498-IEDR', 'Creator'), ('uc007-sc09.ie', 'XNV498-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc09.ie'), 'uc007-sc09.ie', 'ns1.uc007-sc09.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc09.ie'), 'uc007-sc09.ie', 'ns2.uc007-sc09.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc09.ie'), 'uc007-sc09.ie', 'ns3.uc007-sc09.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc007-sc09.ie', 'ns1.uc007-sc09.ie', '10.10.121.23', NULL, '1'), ('uc007-sc09.ie', 'ns2.uc007-sc09.ie', '1.3.4.2', NULL, '2'), ('uc007-sc09.ie', 'ns3.uc007-sc09.ie', '10.12.13.14', NULL, '3');

# UC#007: Triple-PASS Domain Billing Transfer Ticket - UC007-SC08: Voluntary Suspended Transfer Registrar to Registrar Multi-year
# L: XNV498-IEDR, G: XBC189-IEDR, DSM 21
INSERT INTO DomainHist VALUES (NULL, 'uc007-sc08.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 603),  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XNV498-IEDR', NOW(), 'NO', '21', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc007-sc08.ie', 'Test Holder 0001', 3, 2, NULL, 603,  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '21', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc08.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc08.ie'), 'uc007-sc08.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc08.ie'), 'uc007-sc08.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc08.ie'), 'uc007-sc08.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc08.ie'), 'uc007-sc08.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc007-sc08.ie', 'XOE550-IEDR', 'Admin'), ('uc007-sc08.ie', 'XNV498-IEDR', 'Billing'), ('uc007-sc08.ie', 'XNV498-IEDR', 'Creator'), ('uc007-sc08.ie', 'XNV498-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc08.ie'), 'uc007-sc08.ie', 'ns1.uc007-sc08.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc08.ie'), 'uc007-sc08.ie', 'ns2.uc007-sc08.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc08.ie'), 'uc007-sc08.ie', 'ns3.uc007-sc08.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc007-sc08.ie', 'ns1.uc007-sc08.ie', '10.10.121.23', NULL, '1'), ('uc007-sc08.ie', 'ns2.uc007-sc08.ie', '1.3.4.2', NULL, '2'), ('uc007-sc08.ie', 'ns3.uc007-sc08.ie', '10.12.13.14', NULL, '3');

# UC#007: Triple-PASS Domain Billing Transfer Ticket - UC007-SC07: Voluntary Mailed Registrar to Registrar Transfer
# L: XNV498-IEDR, G: XBC189-IEDR, DSM 20
INSERT INTO DomainHist VALUES (NULL, 'uc007-sc07.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 603),  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XNV498-IEDR', NOW(), 'NO', '20', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc007-sc07.ie', 'Test Holder 0001', 3, 2, NULL, 603,  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '20', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc07.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc07.ie'), 'uc007-sc07.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc07.ie'), 'uc007-sc07.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc07.ie'), 'uc007-sc07.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc07.ie'), 'uc007-sc07.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc007-sc07.ie', 'XOE550-IEDR', 'Admin'), ('uc007-sc07.ie', 'XNV498-IEDR', 'Billing'), ('uc007-sc07.ie', 'XNV498-IEDR', 'Creator'), ('uc007-sc07.ie', 'XNV498-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc07.ie'), 'uc007-sc07.ie', 'ns1.uc007-sc07.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc07.ie'), 'uc007-sc07.ie', 'ns2.uc007-sc07.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc07.ie'), 'uc007-sc07.ie', 'ns3.uc007-sc07.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc007-sc07.ie', 'ns1.uc007-sc07.ie', '10.10.121.23', NULL, '1'), ('uc007-sc07.ie', 'ns2.uc007-sc07.ie', '1.3.4.2', NULL, '2'), ('uc007-sc07.ie', 'ns3.uc007-sc07.ie', '10.12.13.14', NULL, '3');

# UC#007: Triple-PASS Domain Billing Transfer Ticket - UC007-SC11: Invalidated Credit Card Transaction.
# L: XNV498-IEDR, G: XDD274-IEDR, DSM 17
INSERT INTO DomainHist VALUES (NULL, 'uc007-sc11.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 603),  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XNV498-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc007-sc11.ie', 'Test Holder 0001', 3, 2, NULL, 603,  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc11.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc11.ie'), 'uc007-sc11.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc11.ie'), 'uc007-sc11.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc11.ie'), 'uc007-sc11.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc11.ie'), 'uc007-sc11.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc007-sc11.ie', 'XOE550-IEDR', 'Admin'), ('uc007-sc11.ie', 'XNV498-IEDR', 'Billing'), ('uc007-sc11.ie', 'XNV498-IEDR', 'Creator'), ('uc007-sc11.ie', 'XNV498-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc11.ie'), 'uc007-sc11.ie', 'ns1.uc007-sc11.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc11.ie'), 'uc007-sc11.ie', 'ns2.uc007-sc11.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc11.ie'), 'uc007-sc11.ie', 'ns3.uc007-sc11.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc007-sc11.ie', 'ns1.uc007-sc11.ie', '10.10.121.23', NULL, '1'), ('uc007-sc11.ie', 'ns2.uc007-sc11.ie', '1.3.4.2', NULL, '2'), ('uc007-sc11.ie', 'ns3.uc007-sc11.ie', '10.12.13.14', NULL, '3');

# UC#007: Triple-PASS Domain Billing Transfer Ticket - UC007-SC12: Tech Stall During Transfer
# L: XNV498-IEDR, G: XBC189-IEDR, DSM 17
INSERT INTO DomainHist VALUES (NULL, 'uc007-sc12.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 603),  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XNV498-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc007-sc12.ie', 'Test Holder 0001', 3, 2, NULL, 603,  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc12.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc12.ie'), 'uc007-sc12.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc12.ie'), 'uc007-sc12.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc12.ie'), 'uc007-sc12.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc12.ie'), 'uc007-sc12.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc007-sc12.ie', 'XOE550-IEDR', 'Admin'), ('uc007-sc12.ie', 'XNV498-IEDR', 'Billing'), ('uc007-sc12.ie', 'XNV498-IEDR', 'Creator'), ('uc007-sc12.ie', 'XNV498-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc12.ie'), 'uc007-sc12.ie', 'ns1.uc007-sc12.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc12.ie'), 'uc007-sc12.ie', 'ns2.uc007-sc12.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc12.ie'), 'uc007-sc12.ie', 'ns3.uc007-sc12.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc007-sc12.ie', 'ns1.uc007-sc12.ie', '10.10.121.23', NULL, '1'), ('uc007-sc12.ie', 'ns2.uc007-sc12.ie', '1.3.4.2', NULL, '2'), ('uc007-sc12.ie', 'ns3.uc007-sc12.ie', '10.12.13.14', NULL, '3');

# UC#007: Triple-PASS Domain Billing Transfer Ticket - UC007-SC13: Admin Fail During Transfer - Return to Active
# L: XNV498-IEDR, G: XBC189-IEDR, DSM 17
INSERT INTO DomainHist VALUES (NULL, 'uc007-sc13.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 603),  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XNV498-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc007-sc13.ie', 'Test Holder 0001', 3, 2, NULL, 603,  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc13.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc13.ie'), 'uc007-sc13.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc13.ie'), 'uc007-sc13.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc13.ie'), 'uc007-sc13.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc13.ie'), 'uc007-sc13.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc007-sc13.ie', 'XOE550-IEDR', 'Admin'), ('uc007-sc13.ie', 'XNV498-IEDR', 'Billing'), ('uc007-sc13.ie', 'XNV498-IEDR', 'Creator'), ('uc007-sc13.ie', 'XNV498-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc13.ie'), 'uc007-sc13.ie', 'ns1.uc007-sc13.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc13.ie'), 'uc007-sc13.ie', 'ns2.uc007-sc13.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc13.ie'), 'uc007-sc13.ie', 'ns3.uc007-sc13.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc007-sc13.ie', 'ns1.uc007-sc13.ie', '10.10.121.23', NULL, '1'), ('uc007-sc13.ie', 'ns2.uc007-sc13.ie', '1.3.4.2', NULL, '2'), ('uc007-sc13.ie', 'ns3.uc007-sc13.ie', '10.12.13.14', NULL, '3');

# UC#007: Triple-PASS Domain Billing Transfer Ticket - UC007-SC14: Direct to Direct Transfer Happy Path
# L: UC007AA-IEDR, G: XDD274-IEDR, DSM 25
INSERT INTO DomainHist VALUES (NULL, 'uc007-sc14.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1),  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'UC007AA-IEDR', NOW(), 'NO', '25', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc007-sc14.ie', 'Test Holder 0001', 3, 2, NULL, 1,  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '25', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc14.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc14.ie'), 'uc007-sc14.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc14.ie'), 'uc007-sc14.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC007AA-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc14.ie'), 'uc007-sc14.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC007AA-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc14.ie'), 'uc007-sc14.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC007AA-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc007-sc14.ie', 'XOE550-IEDR', 'Admin'), ('uc007-sc14.ie', 'UC007AA-IEDR', 'Billing'), ('uc007-sc14.ie', 'UC007AA-IEDR', 'Creator'), ('uc007-sc14.ie', 'UC007AA-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc14.ie'), 'uc007-sc14.ie', 'ns1.uc007-sc14.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc14.ie'), 'uc007-sc14.ie', 'ns2.uc007-sc14.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc14.ie'), 'uc007-sc14.ie', 'ns3.uc007-sc14.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc007-sc14.ie', 'ns1.uc007-sc14.ie', '10.10.121.23', NULL, '1'), ('uc007-sc14.ie', 'ns2.uc007-sc14.ie', '1.3.4.2', NULL, '2'), ('uc007-sc14.ie', 'ns3.uc007-sc14.ie', '10.12.13.14', NULL, '3');

# UC#007: Triple-PASS Domain Billing Transfer Ticket - UC007-SC15: Insufficient Available ADP Funds for Transfer
# L: XNV498-IEDR, G: XBC189-IEDR, DSM 17
INSERT INTO DomainHist VALUES (NULL, 'uc007-sc15.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 603),  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XNV498-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc007-sc15.ie', 'Test Holder 0001', 3, 2, NULL, 603,  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc15.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc15.ie'), 'uc007-sc15.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc15.ie'), 'uc007-sc15.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc15.ie'), 'uc007-sc15.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc15.ie'), 'uc007-sc15.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc007-sc15.ie', 'XOE550-IEDR', 'Admin'), ('uc007-sc15.ie', 'XNV498-IEDR', 'Billing'), ('uc007-sc15.ie', 'XNV498-IEDR', 'Creator'), ('uc007-sc15.ie', 'XNV498-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc15.ie'), 'uc007-sc15.ie', 'ns1.uc007-sc15.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc15.ie'), 'uc007-sc15.ie', 'ns2.uc007-sc15.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc15.ie'), 'uc007-sc15.ie', 'ns3.uc007-sc15.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc007-sc15.ie', 'ns1.uc007-sc15.ie', '10.10.121.23', NULL, '1'), ('uc007-sc15.ie', 'ns2.uc007-sc15.ie', '1.3.4.2', NULL, '2'), ('uc007-sc15.ie', 'ns3.uc007-sc15.ie', '10.12.13.14', NULL, '3');

# UC#007: Triple-PASS Domain Billing Transfer Ticket - UC007-SC16: Admin Fail - Return to Involuntary NRP
# L: XNV498-IEDR, G: XBC189-IEDR, DSM 18
INSERT INTO DomainHist VALUES (NULL, 'uc007-sc16.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 603),  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XNV498-IEDR', NOW(), 'NO', '18', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc007-sc16.ie', 'Test Holder 0001', 3, 2, NULL, 603,  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '18', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc16.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc16.ie'), 'uc007-sc16.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc16.ie'), 'uc007-sc16.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc16.ie'), 'uc007-sc16.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc16.ie'), 'uc007-sc16.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc007-sc16.ie', 'XOE550-IEDR', 'Admin'), ('uc007-sc16.ie', 'XNV498-IEDR', 'Billing'), ('uc007-sc16.ie', 'XNV498-IEDR', 'Creator'), ('uc007-sc16.ie', 'XNV498-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc16.ie'), 'uc007-sc16.ie', 'ns1.uc007-sc16.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc16.ie'), 'uc007-sc16.ie', 'ns2.uc007-sc16.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc16.ie'), 'uc007-sc16.ie', 'ns3.uc007-sc16.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc007-sc16.ie', 'ns1.uc007-sc16.ie', '10.10.121.23', NULL, '1'), ('uc007-sc16.ie', 'ns2.uc007-sc16.ie', '1.3.4.2', NULL, '2'), ('uc007-sc16.ie', 'ns3.uc007-sc16.ie', '10.12.13.14', NULL, '3');

# UC#007: Triple-PASS Domain Billing Transfer Ticket - UC007-SC17: Admin Fail - Return to Voluntary NRP
# L: XNV498-IEDR, G: XBC189-IEDR, DSM 20
INSERT INTO DomainHist VALUES (NULL, 'uc007-sc17.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 603),  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XNV498-IEDR', NOW(), 'NO', '20', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc007-sc17.ie', 'Test Holder 0001', 3, 2, NULL, 603,  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '20', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc17.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc17.ie'), 'uc007-sc17.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc17.ie'), 'uc007-sc17.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc17.ie'), 'uc007-sc17.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc17.ie'), 'uc007-sc17.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc007-sc17.ie', 'XOE550-IEDR', 'Admin'), ('uc007-sc17.ie', 'XNV498-IEDR', 'Billing'), ('uc007-sc17.ie', 'XNV498-IEDR', 'Creator'), ('uc007-sc17.ie', 'XNV498-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc17.ie'), 'uc007-sc17.ie', 'ns1.uc007-sc17.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc17.ie'), 'uc007-sc17.ie', 'ns2.uc007-sc17.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-sc17.ie'), 'uc007-sc17.ie', 'ns3.uc007-sc17.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc007-sc17.ie', 'ns1.uc007-sc17.ie', '10.10.121.23', NULL, '1'), ('uc007-sc17.ie', 'ns2.uc007-sc17.ie', '1.3.4.2', NULL, '2'), ('uc007-sc17.ie', 'ns3.uc007-sc17.ie', '10.12.13.14', NULL, '3');

# UC#007: Triple-PASS Domain Billing Transfer Ticket - qa:1.2.3.4: Realex authorisation no longer valid
# L: XNV498-IEDR, G: XBC189-IEDR, DSM 17
INSERT INTO DomainHist VALUES (NULL, 'uc007-qa1234.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 603),  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XNV498-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc007-qa1234.ie', 'Test Holder 0001', 3, 2, NULL, 603,  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-qa1234.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-qa1234.ie'), 'uc007-qa1234.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-qa1234.ie'), 'uc007-qa1234.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-qa1234.ie'), 'uc007-qa1234.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-qa1234.ie'), 'uc007-qa1234.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc007-qa1234.ie', 'XOE550-IEDR', 'Admin'), ('uc007-qa1234.ie', 'XNV498-IEDR', 'Billing'), ('uc007-qa1234.ie', 'XNV498-IEDR', 'Creator'), ('uc007-qa1234.ie', 'XNV498-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-qa1234.ie'), 'uc007-qa1234.ie', 'ns1.uc007-qa1234.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-qa1234.ie'), 'uc007-qa1234.ie', 'ns2.uc007-qa1234.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-qa1234.ie'), 'uc007-qa1234.ie', 'ns3.uc007-qa1234.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc007-qa1234.ie', 'ns1.uc007-qa1234.ie', '10.10.121.23', NULL, '1'), ('uc007-qa1234.ie', 'ns2.uc007-qa1234.ie', '1.3.4.2', NULL, '2'), ('uc007-qa1234.ie', 'ns3.uc007-qa1234.ie', '10.12.13.14', NULL, '3');

# UC#007: Triple-PASS Domain Billing Transfer Ticket - highlighting changes
INSERT INTO DomainHist VALUES (NULL, 'uc007-nosc01.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 603),  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XNV498-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc007-nosc01.ie', 'Test Holder 0001', 3, 2, NULL, 603,  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-nosc01.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-nosc01.ie'), 'uc007-nosc01.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-nosc01.ie'), 'uc007-nosc01.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-nosc01.ie'), 'uc007-nosc01.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-nosc01.ie'), 'uc007-nosc01.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc007-nosc01.ie', 'XOE550-IEDR', 'Admin'), ('uc007-nosc01.ie', 'XNV498-IEDR', 'Billing'), ('uc007-nosc01.ie', 'XNV498-IEDR', 'Creator'), ('uc007-nosc01.ie', 'XNV498-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-nosc01.ie'), 'uc007-nosc01.ie', 'ns1.uc007-nosc01.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-nosc01.ie'), 'uc007-nosc01.ie', 'ns2.uc007-nosc01.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-nosc01.ie'), 'uc007-nosc01.ie', 'ns3.uc007-nosc01.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc007-nosc01.ie', 'ns3.uc007-nosc01.ie', '10.10.121.23', NULL, '1'), ('uc007-nosc01.ie', 'ns4.uc007-nosc01.ie', '1.3.4.2', NULL, '2'), ('uc007-nosc01.ie', 'ns5.uc007-nosc01.ie', '10.12.13.14', NULL, '3');

# UC#007: Triple-PASS Domain Billing Transfer Ticket - IPv6 support
INSERT INTO DomainHist VALUES (NULL, 'uc007-nosc04.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 603),  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XNV498-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc007-nosc04.ie', 'Test Holder 0001', 3, 2, NULL, 603,  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-nosc04.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-nosc04.ie'), 'uc007-nosc04.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-nosc04.ie'), 'uc007-nosc04.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-nosc04.ie'), 'uc007-nosc04.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-nosc04.ie'), 'uc007-nosc04.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc007-nosc04.ie', 'XOE550-IEDR', 'Admin'), ('uc007-nosc04.ie', 'XNV498-IEDR', 'Billing'), ('uc007-nosc04.ie', 'XNV498-IEDR', 'Creator'), ('uc007-nosc04.ie', 'XNV498-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-nosc04.ie'), 'uc007-nosc04.ie', 'ns1.uc007-nosc04.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-nosc04.ie'), 'uc007-nosc04.ie', 'ns2.uc007-nosc04.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc007-nosc04.ie'), 'uc007-nosc04.ie', 'ns3.uc007-nosc04.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc007-nosc04.ie', 'ns3.uc007-nosc04.ie', '10.10.121.23', NULL, '1'), ('uc007-nosc04.ie', 'ns4.uc007-nosc04.ie', '1.3.4.2', NULL, '2'), ('uc007-nosc04.ie', 'ns5.uc007-nosc04.ie', '10.12.13.14', NULL, '3');

