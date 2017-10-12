INSERT INTO NicHandleHist VALUES (NULL, 'UC006AA-IEDR', 'Use Case 006 - Direct', (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc006_aa@iedr.ie', 'Active', NOW(), NOW(), NOW(),'NO', 'test data generation', 'UC006AA-IEDR', 'B', NULL, 'YES', 'PORTAL', NOW());
INSERT INTO NicHandle VALUES ('UC006AA-IEDR', 'Use Case 006 - Direct', '001', 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc006_aa@iedr.ie', 'Active', NOW(), NOW(), NOW(),'NO', 'test data generation', 'UC006AA-IEDR', 'B', NULL, 'YES', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC006AA-IEDR'));
INSERT INTO AccessHist VALUES (NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC006AA-IEDR'), 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 2048, 'NO', 'PORTAL', NOW(), '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '');
INSERT INTO Access VALUES ('UC006AA-IEDR', 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 2048, 'NO', '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '', (SELECT MAX(A.Chng_Id) FROM AccessHist A JOIN NicHandleHist NH  ON A.Nic_Handle_Chng_Id = NH.Chng_Id WHERE NH.Nic_Handle = 'UC006AA-IEDR'));

INSERT INTO NicHandleHist VALUES (NULL, 'UC006SU-IEDR', 'Use Case 006 - Direct - Suspended', (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc006_su@iedr.ie', 'Suspended', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'UC006SU-IEDR', 'B', NULL, 'YES', 'UC006AA-IEDR', NOW());
INSERT INTO NicHandle VALUES ('UC006SU-IEDR', 'Use Case 006 - Direct - Suspended', '001', 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc006_su@iedr.ie', 'Suspended', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'UC006SU-IEDR', 'B', NULL, 'YES', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC006SU-IEDR'));
INSERT INTO AccessHist VALUES (NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC006SU-IEDR'), 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 0, 'NO', 'UC006AA-IEDR', NOW(), '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '');
INSERT INTO Access VALUES ('UC006SU-IEDR', 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 0, 'NO', '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '', (SELECT MAX(A.Chng_Id) FROM AccessHist A JOIN NicHandleHist NH  ON A.Nic_Handle_Chng_Id = NH.Chng_Id WHERE NH.Nic_Handle = 'UC006SU-IEDR'));

# UC#006: Request Domain Billing Transfer - UC006-SC01: (Alt) Registrar - Registrar Transfer Basic - CC
# L: XNV498-IEDR, G: XBC189-IEDR, uc006-sc01alt1.ie
INSERT INTO DomainHist VALUES (NULL, 'uc006-sc01alt1.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 603), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XNV498-IEDR', NOW(), 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc006-sc01alt1.ie', 'Test Holder 0001', 3, 2, NULL, 603, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-sc01alt1.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-sc01alt1.ie'), 'uc006-sc01alt1.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-sc01alt1.ie'), 'uc006-sc01alt1.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-sc01alt1.ie'), 'uc006-sc01alt1.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-sc01alt1.ie'), 'uc006-sc01alt1.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc006-sc01alt1.ie', 'XOE550-IEDR', 'Admin'), ('uc006-sc01alt1.ie', 'XNV498-IEDR', 'Billing'), ('uc006-sc01alt1.ie', 'XNV498-IEDR', 'Creator'), ('uc006-sc01alt1.ie', 'XNV498-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-sc01alt1.ie'), 'uc006-sc01alt1.ie', 'ns1.uc006-sc01alt1.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-sc01alt1.ie'), 'uc006-sc01alt1.ie', 'ns2.uc006-sc01alt1.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-sc01alt1.ie'), 'uc006-sc01alt1.ie', 'ns3.uc006-sc01alt1.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc006-sc01alt1.ie', 'ns1.uc006-sc01alt1.ie', '10.10.121.23', NULL, '1'), ('uc006-sc01alt1.ie', 'ns2.uc006-sc01alt1.ie', '1.3.4.2', NULL, '2'), ('uc006-sc01alt1.ie', 'ns3.uc006-sc01alt1.ie', '10.12.13.14', NULL, '3');

# UC#006: Request Domain Billing Transfer - UC006-SC01: (Alt) Registrar Charity -Registrar Transfer Basic
# L: XNV498-IEDR, G: XBC189-IEDR, uc006-sc01alt2.ie
INSERT INTO DomainHist VALUES (NULL, 'uc006-sc01alt2.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 603), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XNV498-IEDR', NOW(), 'NO', 113, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc006-sc01alt2.ie', 'Test Holder 0001', 3, 2, NULL, 603, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', 113, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-sc01alt2.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-sc01alt2.ie'), 'uc006-sc01alt2.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-sc01alt2.ie'), 'uc006-sc01alt2.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-sc01alt2.ie'), 'uc006-sc01alt2.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-sc01alt2.ie'), 'uc006-sc01alt2.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc006-sc01alt2.ie', 'XOE550-IEDR', 'Admin'), ('uc006-sc01alt2.ie', 'XNV498-IEDR', 'Billing'), ('uc006-sc01alt2.ie', 'XNV498-IEDR', 'Creator'), ('uc006-sc01alt2.ie', 'XNV498-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-sc01alt2.ie'), 'uc006-sc01alt2.ie', 'ns1.uc006-sc01alt2.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-sc01alt2.ie'), 'uc006-sc01alt2.ie', 'ns2.uc006-sc01alt2.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-sc01alt2.ie'), 'uc006-sc01alt2.ie', 'ns3.uc006-sc01alt2.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc006-sc01alt2.ie', 'ns1.uc006-sc01alt2.ie', '10.10.121.23', NULL, '1'), ('uc006-sc01alt2.ie', 'ns2.uc006-sc01alt2.ie', '1.3.4.2', NULL, '2'), ('uc006-sc01alt2.ie', 'ns3.uc006-sc01alt2.ie', '10.12.13.14', NULL, '3');

# UC#006: Request Domain Billing Transfer - UC006-SC02: Registrar to Direct Transfer - Basic
# L: XBC189-IEDR, G: XDD274-IEDR, uc006-sc02.ie
INSERT INTO DomainHist VALUES (NULL, 'uc006-sc02.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc006-sc02.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-sc02.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-sc02.ie'), 'uc006-sc02.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-sc02.ie'), 'uc006-sc02.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-sc02.ie'), 'uc006-sc02.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-sc02.ie'), 'uc006-sc02.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc006-sc02.ie', 'XOE550-IEDR', 'Admin'), ('uc006-sc02.ie', 'XBC189-IEDR', 'Billing'), ('uc006-sc02.ie', 'XBC189-IEDR', 'Creator'), ('uc006-sc02.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-sc02.ie'), 'uc006-sc02.ie', 'ns1.uc006-sc02.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-sc02.ie'), 'uc006-sc02.ie', 'ns2.uc006-sc02.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-sc02.ie'), 'uc006-sc02.ie', 'ns3.uc006-sc02.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc006-sc02.ie', 'ns1.uc006-sc02.ie', '10.10.121.23', NULL, '1'), ('uc006-sc02.ie', 'ns2.uc006-sc02.ie', '1.3.4.2', NULL, '2'), ('uc006-sc02.ie', 'ns3.uc006-sc02.ie', '10.12.13.14', NULL, '3');

# UC#006: Request Domain Billing Transfer - UC006-SC06: Direct to Direct Charity Transfer
# L: UC006AA-IEDR, G: XDD274-IEDR, uc006-sc06.ie
INSERT INTO DomainHist VALUES (NULL, 'uc006-sc06.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XDD274-IEDR', NOW(), 'NO', 121, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc006-sc06.ie', 'Test Holder 0001', 3, 2, NULL, 1, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', 121, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-sc06.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-sc06.ie'), 'uc006-sc06.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-sc06.ie'), 'uc006-sc06.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC006AA-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-sc06.ie'), 'uc006-sc06.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC006AA-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-sc06.ie'), 'uc006-sc06.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC006AA-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc006-sc06.ie', 'XOE550-IEDR', 'Admin'), ('uc006-sc06.ie', 'UC006AA-IEDR', 'Billing'), ('uc006-sc06.ie', 'UC006AA-IEDR', 'Creator'), ('uc006-sc06.ie', 'UC006AA-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-sc06.ie'), 'uc006-sc06.ie', 'ns1.uc006-sc06.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-sc06.ie'), 'uc006-sc06.ie', 'ns2.uc006-sc06.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-sc06.ie'), 'uc006-sc06.ie', 'ns3.uc006-sc06.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc006-sc06.ie', 'ns1.uc006-sc06.ie', '10.10.121.23', NULL, '1'), ('uc006-sc06.ie', 'ns2.uc006-sc06.ie', '1.3.4.2', NULL, '2'), ('uc006-sc06.ie', 'ns3.uc006-sc06.ie', '10.12.13.14', NULL, '3');

# UC#006: Request Domain Billing Transfer - UC006-SC08: Registrar to Registrar Transfer - NRC / CC Authorisation Failure multi-year
# L: XNV498-IEDR, G: XBC189-IEDR, uc006-sc08.ie
INSERT INTO DomainHist VALUES (NULL, 'uc006-sc08.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 603), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XNV498-IEDR', NOW(), 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc006-sc08.ie', 'Test Holder 0001', 3, 2, NULL, 603, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-sc08.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-sc08.ie'), 'uc006-sc08.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-sc08.ie'), 'uc006-sc08.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-sc08.ie'), 'uc006-sc08.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-sc08.ie'), 'uc006-sc08.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc006-sc08.ie', 'XOE550-IEDR', 'Admin'), ('uc006-sc08.ie', 'XNV498-IEDR', 'Billing'), ('uc006-sc08.ie', 'XNV498-IEDR', 'Creator'), ('uc006-sc08.ie', 'XNV498-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-sc08.ie'), 'uc006-sc08.ie', 'ns1.uc006-sc08.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-sc08.ie'), 'uc006-sc08.ie', 'ns2.uc006-sc08.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-sc08.ie'), 'uc006-sc08.ie', 'ns3.uc006-sc08.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc006-sc08.ie', 'ns1.uc006-sc08.ie', '10.10.121.23', NULL, '1'), ('uc006-sc08.ie', 'ns2.uc006-sc08.ie', '1.3.4.2', NULL, '2'), ('uc006-sc08.ie', 'ns3.uc006-sc08.ie', '10.12.13.14', NULL, '3');

# UC#006: Request Domain Billing Transfer - UC006-SC09: Non-billbable Direct to Registrar Transfer
# L: XDD274-IEDR, G: XBC189-IEDR, uc006-sc09.ie
INSERT INTO DomainHist VALUES (NULL, 'uc006-sc09.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XDD274-IEDR', NOW(), 'NO', 313, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc006-sc09.ie', 'Test Holder 0001', 3, 2, NULL, 1, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', 313, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-sc09.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-sc09.ie'), 'uc006-sc09.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-sc09.ie'), 'uc006-sc09.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-sc09.ie'), 'uc006-sc09.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-sc09.ie'), 'uc006-sc09.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc006-sc09.ie', 'XOE550-IEDR', 'Admin'), ('uc006-sc09.ie', 'XDD274-IEDR', 'Billing'), ('uc006-sc09.ie', 'XDD274-IEDR', 'Creator'), ('uc006-sc09.ie', 'XDD274-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-sc09.ie'), 'uc006-sc09.ie', 'ns1.uc006-sc09.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-sc09.ie'), 'uc006-sc09.ie', 'ns2.uc006-sc09.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-sc09.ie'), 'uc006-sc09.ie', 'ns3.uc006-sc09.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc006-sc09.ie', 'ns1.uc006-sc09.ie', '10.10.121.23', NULL, '1'), ('uc006-sc09.ie', 'ns2.uc006-sc09.ie', '1.3.4.2', NULL, '2'), ('uc006-sc09.ie', 'ns3.uc006-sc09.ie', '10.12.13.14', NULL, '3');

# UC#006: Request Domain Billing Transfer - UC006-SC10: Direct to Direct Transfer
# L: UC006AA, G: XDD274-IEDR, uc006-sc10.ie
INSERT INTO DomainHist VALUES (NULL, 'uc006-sc10.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XDD274-IEDR', NOW(), 'NO', 25, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc006-sc10.ie', 'Test Holder 0001', 3, 2, NULL, 1, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', 25, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-sc10.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-sc10.ie'), 'uc006-sc10.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-sc10.ie'), 'uc006-sc10.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC006AA-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-sc10.ie'), 'uc006-sc10.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC006AA-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-sc10.ie'), 'uc006-sc10.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC006AA-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc006-sc10.ie', 'XOE550-IEDR', 'Admin'), ('uc006-sc10.ie', 'UC006AA-IEDR', 'Billing'), ('uc006-sc10.ie', 'UC006AA-IEDR', 'Creator'), ('uc006-sc10.ie', 'UC006AA-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-sc10.ie'), 'uc006-sc10.ie', 'ns1.uc006-sc10.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-sc10.ie'), 'uc006-sc10.ie', 'ns2.uc006-sc10.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-sc10.ie'), 'uc006-sc10.ie', 'ns3.uc006-sc10.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc006-sc10.ie', 'ns1.uc006-sc10.ie', '10.10.121.23', NULL, '1'), ('uc006-sc10.ie', 'ns2.uc006-sc10.ie', '1.3.4.2', NULL, '2'), ('uc006-sc10.ie', 'ns3.uc006-sc10.ie', '10.12.13.14', NULL, '3');

# UC#006: Request Domain Billing Transfer - AT - DC-Fail-4 - Transfer to Direct - Fail- DC
# L: XBC189-IEDR, G: XDD274-IEDR, uc006-dcfail4.ie
INSERT INTO DomainHist VALUES (NULL, 'uc006-dcfail4.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc006-dcfail4.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-dcfail4.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-dcfail4.ie'), 'uc006-dcfail4.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-dcfail4.ie'), 'uc006-dcfail4.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-dcfail4.ie'), 'uc006-dcfail4.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-dcfail4.ie'), 'uc006-dcfail4.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc006-dcfail4.ie', 'XOE550-IEDR', 'Admin'), ('uc006-dcfail4.ie', 'XBC189-IEDR', 'Billing'), ('uc006-dcfail4.ie', 'XBC189-IEDR', 'Creator'), ('uc006-dcfail4.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-dcfail4.ie'), 'uc006-dcfail4.ie', 'ns1.uc006-dcfail4.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-dcfail4.ie'), 'uc006-dcfail4.ie', 'ns2.uc006-dcfail4.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-dcfail4.ie'), 'uc006-dcfail4.ie', 'ns3.uc006-dcfail4.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc006-dcfail4.ie', 'ns1.uc006-dcfail4.ie', '10.10.121.23', NULL, '1'), ('uc006-dcfail4.ie', 'ns2.uc006-dcfail4.ie', '1.3.4.2', NULL, '2'), ('uc006-dcfail4.ie', 'ns3.uc006-dcfail4.ie', '10.12.13.14', NULL, '3');

# UC#006: Request Domain Billing Transfer - AT - XNPR-IM-RC-C-26 - Transfers to Registrar IM Domain by CC
# DSM: 26
# UC#006: Request Domain Billing Transfer - AT - XNPR-IS-RC-C-27 - Transfers to Registrar IS Domain by CC
# DSM: 27
# UC#006: Request Domain Billing Transfer - AT - XNPR-VM-RC-C-28 - Transfers to Registrar VM Domain by CC
# DSM: 28
# UC#006: Request Domain Billing Transfer - AT - XNPR-VS-RC-C-29 - Transfers to Registrar VS Domain by CC
# DSM: 29
# UC#006: Request Domain Billing Transfer - AT - XNPR-IM-RC-D-30 - Transfers to Registrar IM Domain by Dep
# DSM: 26
# UC#006: Request Domain Billing Transfer - AT - XNPR-IS-RC-D-31 - Transfers to Registrar IS Domain by Dep
# DSM: 27
# UC#006: Request Domain Billing Transfer - AT - XNPR-VM-RC-D-32 - Transfers to Registrar VM Domain by Dep
# DSM: 28
# UC#006: Request Domain Billing Transfer - AT - XNPR-VS-RC-D-33 - Transfers to Registrar VS Domain by Dep
# DSM: 29
# UC#006: Request Domain Billing Transfer - AT - XNPR-IM-DC-C-34 - Transfers to Direct IM Domain by CC
# DSM: 18
# UC#006: Request Domain Billing Transfer - AT - XNPR-IS-DC-C-35 - Transfers to Direct IS Domain by CC
# DSM: 19
# UC#006: Request Domain Billing Transfer - AT - XNPR-VM-DC-C-36 - Transfers to Direct VM Domain by CC
# DSM: 20
# UC#006: Request Domain Billing Transfer - AT - XNPR-VS-DC-C-36 - Transfers to Direct VS Domain by CC
# DSM: 21
# UC#006: Request Domain Billing Transfer - AT - XNPR-IM-DC-Fail-37 - Transfers to Direct IM Domain by DC
# DSM: 18
# UC#006: Request Domain Billing Transfer - AT - XNPR-IS-DC-Fail-37 - Transfers to Direct IS Domain by DC
# DSM: 19
# UC#006: Request Domain Billing Transfer - AT - XNPR-VM-DC-Fail-37 - Transfers to Direct VM Domain by DC
# DSM: 20
# UC#006: Request Domain Billing Transfer - AT - XNPR-VS-DC-Fail-37 - Transfers to Direct VS Domain by DC
# DSM: 21
# UC#006: Request Domain Billing Transfer - AT - XNRP-RC-CHY-38 - Transfers to Registrar VM Charity Domain
# DSM: 124
# UC#006: Request Domain Billing Transfer - AT - XNRP-RC-CHY-39 - Transfers to Registrar VS Charity Domain
# DSM: 125
# UC#006: Request Domain Billing Transfer - AT - XNRP-DC-CHY-40 - Transfers to Direct VM Charity Domain
# DSM: 116
# UC#006: Request Domain Billing Transfer - AT - XNRP-DC-CHY-41 - Transfers to Direct VS Charity Domain
# DSM: 117

INSERT INTO DomainHist VALUES (NULL, 'uc006-xnrprc.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XDD274-IEDR', NOW(), 'NO', 25, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc006-xnrprc.ie', 'Test Holder 0001', 3, 2, NULL, 1, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', 25, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-xnrprc.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-xnrprc.ie'), 'uc006-xnrprc.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-xnrprc.ie'), 'uc006-xnrprc.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-xnrprc.ie'), 'uc006-xnrprc.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-xnrprc.ie'), 'uc006-xnrprc.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc006-xnrprc.ie', 'XOE550-IEDR', 'Admin'), ('uc006-xnrprc.ie', 'XDD274-IEDR', 'Billing'), ('uc006-xnrprc.ie', 'XDD274-IEDR', 'Creator'), ('uc006-xnrprc.ie', 'XDD274-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-xnrprc.ie'), 'uc006-xnrprc.ie', 'ns1.uc006-xnrprc.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-xnrprc.ie'), 'uc006-xnrprc.ie', 'ns2.uc006-xnrprc.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-xnrprc.ie'), 'uc006-xnrprc.ie', 'ns3.uc006-xnrprc.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc006-xnrprc.ie', 'ns1.uc006-xnrprc.ie', '10.10.121.23', NULL, '1'), ('uc006-xnrprc.ie', 'ns2.uc006-xnrprc.ie', '1.3.4.2', NULL, '2'), ('uc006-xnrprc.ie', 'ns3.uc006-xnrprc.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc006-xnrpdc.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc006-xnrpdc.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-xnrpdc.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-xnrpdc.ie'), 'uc006-xnrpdc.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-xnrpdc.ie'), 'uc006-xnrpdc.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-xnrpdc.ie'), 'uc006-xnrpdc.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-xnrpdc.ie'), 'uc006-xnrpdc.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc006-xnrpdc.ie', 'XOE550-IEDR', 'Admin'), ('uc006-xnrpdc.ie', 'XBC189-IEDR', 'Billing'), ('uc006-xnrpdc.ie', 'XBC189-IEDR', 'Creator'), ('uc006-xnrpdc.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-xnrpdc.ie'), 'uc006-xnrpdc.ie', 'ns1.uc006-xnrpdc.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-xnrpdc.ie'), 'uc006-xnrpdc.ie', 'ns2.uc006-xnrpdc.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-xnrpdc.ie'), 'uc006-xnrpdc.ie', 'ns3.uc006-xnrpdc.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc006-xnrpdc.ie', 'ns1.uc006-xnrpdc.ie', '10.10.121.23', NULL, '1'), ('uc006-xnrpdc.ie', 'ns2.uc006-xnrpdc.ie', '1.3.4.2', NULL, '2'), ('uc006-xnrpdc.ie', 'ns3.uc006-xnrpdc.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc006-nosc01.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 603), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XNV498-IEDR', NOW(), 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc006-nosc01.ie', 'Test Holder 0001', 3, 2, NULL, 603, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc01.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc01.ie'), 'uc006-nosc01.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc01.ie'), 'uc006-nosc01.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc01.ie'), 'uc006-nosc01.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc01.ie'), 'uc006-nosc01.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc006-nosc01.ie', 'XOE550-IEDR', 'Admin'), ('uc006-nosc01.ie', 'XNV498-IEDR', 'Billing'), ('uc006-nosc01.ie', 'XNV498-IEDR', 'Creator'), ('uc006-nosc01.ie', 'XNV498-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc01.ie'), 'uc006-nosc01.ie', 'ns1.uc006-nosc01.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc01.ie'), 'uc006-nosc01.ie', 'ns2.uc006-nosc01.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc01.ie'), 'uc006-nosc01.ie', 'ns3.uc006-nosc01.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc006-nosc01.ie', 'ns1.uc006-nosc01.ie', '10.10.121.23', NULL, '1'), ('uc006-nosc01.ie', 'ns2.uc006-nosc01.ie', '1.3.4.2', NULL, '2'), ('uc006-nosc01.ie', 'ns3.uc006-nosc01.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc006-nosc02.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 603), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XNV498-IEDR', NOW(), 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc006-nosc02.ie', 'Test Holder 0001', 3, 2, NULL, 603, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc02.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc02.ie'), 'uc006-nosc02.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc02.ie'), 'uc006-nosc02.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc02.ie'), 'uc006-nosc02.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc02.ie'), 'uc006-nosc02.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc006-nosc02.ie', 'XOE550-IEDR', 'Admin'), ('uc006-nosc02.ie', 'XNV498-IEDR', 'Billing'), ('uc006-nosc02.ie', 'XNV498-IEDR', 'Creator'), ('uc006-nosc02.ie', 'XNV498-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc02.ie'), 'uc006-nosc02.ie', 'ns1.uc006-nosc02.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc02.ie'), 'uc006-nosc02.ie', 'ns2.uc006-nosc02.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc02.ie'), 'uc006-nosc02.ie', 'ns3.uc006-nosc02.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc006-nosc02.ie', 'ns1.uc006-nosc02.ie', '10.10.121.23', NULL, '1'), ('uc006-nosc02.ie', 'ns2.uc006-nosc02.ie', '1.3.4.2', NULL, '2'), ('uc006-nosc02.ie', 'ns3.uc006-nosc02.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc006-nosc03ones.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc006-nosc03ones.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc03ones.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc03ones.ie'), 'uc006-nosc03ones.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc03ones.ie'), 'uc006-nosc03ones.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc03ones.ie'), 'uc006-nosc03ones.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc03ones.ie'), 'uc006-nosc03ones.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc006-nosc03ones.ie', 'XBC189-IEDR', 'Admin'), ('uc006-nosc03ones.ie', 'XBC189-IEDR', 'Billing'), ('uc006-nosc03ones.ie', 'XBC189-IEDR', 'Creator'), ('uc006-nosc03ones.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc03ones.ie'), 'uc006-nosc03ones.ie', 'ns1.uc006-nosc03ones.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc03ones.ie'), 'uc006-nosc03ones.ie', 'ns2.uc006-nosc03ones.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc03ones.ie'), 'uc006-nosc03ones.ie', 'ns3.uc006-nosc03ones.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc006-nosc03ones.ie', 'ns1.uc006-nosc03ones.ie', '10.10.121.23', NULL, '1'), ('uc006-nosc03ones.ie', 'ns2.uc006-nosc03ones.ie', '1.3.4.2', NULL, '2'), ('uc006-nosc03ones.ie', 'ns3.uc006-nosc03ones.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc006-nosc03pend.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 603),  CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XNV498-IEDR', NOW(), 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc006-nosc03pend.ie', 'Test Holder 0001', 3, 2, NULL, 603,  CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc03pend.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc03pend.ie'), 'uc006-nosc03pend.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc03pend.ie'), 'uc006-nosc03pend.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc03pend.ie'), 'uc006-nosc03pend.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc03pend.ie'), 'uc006-nosc03pend.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc006-nosc03pend.ie', 'XOE550-IEDR', 'Admin'), ('uc006-nosc03pend.ie', 'XNV498-IEDR', 'Billing'), ('uc006-nosc03pend.ie', 'XNV498-IEDR', 'Creator'), ('uc006-nosc03pend.ie', 'XNV498-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc03pend.ie'), 'uc006-nosc03pend.ie', 'ns1.uc006-nosc03pend.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc03pend.ie'), 'uc006-nosc03pend.ie', 'ns2.uc006-nosc03pend.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc03pend.ie'), 'uc006-nosc03pend.ie', 'ns3.uc006-nosc03pend.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc006-nosc03pend.ie', 'ns1.uc006-nosc03pend.ie', '10.10.121.23', NULL, '1'), ('uc006-nosc03pend.ie', 'ns2.uc006-nosc03pend.ie', '1.3.4.2', NULL, '2'), ('uc006-nosc03pend.ie', 'ns3.uc006-nosc03pend.ie', '10.12.13.14', NULL, '3');

INSERT INTO TicketIndex VALUES (NULL);
INSERT INTO Ticket VALUES ((SELECT MAX(id) FROM TicketIndex), 'Transfer', 'uc006-nosc03pend.ie', NULL, 'Test Holder 0001', NULL, 600, NULL, 3, 2, NULL, 'Domain reg request.', 'XDD274-IEDR', NULL, NULL, NULL, 'XDD274-IEDR', NULL, 'XDD274-IEDR', NULL, 'XNV498-IEDR', 'New', NOW(), 'New', NOW(), 'NO', NULL, NOW(), NOW(), '', NULL, NULL, NOW(), 'NO', 1, NULL, 'NO', 'New', NOW(), 'New', NOW(), 1);
INSERT INTO TicketHist VALUES (NULL, (SELECT T_Number FROM Ticket WHERE D_Name = 'uc006-nosc03pend.ie'), 'Transfer', 'uc006-nosc03pend.ie', NULL, 'Test Holder 0001', NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), NULL, 3, 2, NULL, 'Domain reg request.', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), NULL, NULL, NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'New', NOW(), 'New', NOW(), 'NO', NULL, NOW(), NOW(), '', NOW(), 'XBC189-IEDR', NULL, NULL, NOW(), 'NO', 1, NULL, 'NO', 'New', NOW(), 'New', NOW());
UPDATE Ticket T SET Chng_Id = (SELECT MAX(Chng_Id) FROM TicketHist WHERE T_Number = T.T_Number) WHERE D_Name = 'uc006-nosc03pend.ie';
INSERT INTO TicketNameserverHist VALUES
((SELECT Chng_Id FROM Ticket WHERE D_Name = 'uc006-nosc03pend.ie'), (SELECT T_Number FROM Ticket WHERE D_Name = 'uc006-nosc03pend.ie'), 'ns1.abc1.ie', NULL, NULL, NULL, NULL, NULL, 0, NOW()),
((SELECT Chng_Id FROM Ticket WHERE D_Name = 'uc006-nosc03pend.ie'), (SELECT T_Number FROM Ticket WHERE D_Name = 'uc006-nosc03pend.ie'), 'ns2.abc1.ie', NULL, NULL, NULL, NULL, NULL, 1, NOW());
INSERT INTO TicketNameserver VALUES
((SELECT T_Number FROM Ticket WHERE D_Name = 'uc006-nosc03pend.ie'), 'ns1.abc1.ie', NULL, NULL, NULL, NULL, NULL, 0, NOW()),
((SELECT T_Number FROM Ticket WHERE D_Name = 'uc006-nosc03pend.ie'), 'ns2.abc1.ie', NULL, NULL, NULL, NULL, NULL, 1, NOW());

INSERT INTO DomainHist VALUES (NULL, 'uc006-nosc04.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 603),  CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XNV498-IEDR', NOW(), 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc006-nosc04.ie', 'Test Holder 0001', 3, 2, NULL, 603,  CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc04.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc04.ie'), 'uc006-nosc04.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc04.ie'), 'uc006-nosc04.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc04.ie'), 'uc006-nosc04.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc04.ie'), 'uc006-nosc04.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc006-nosc04.ie', 'XOE550-IEDR', 'Admin'), ('uc006-nosc04.ie', 'XNV498-IEDR', 'Billing'), ('uc006-nosc04.ie', 'XNV498-IEDR', 'Creator'), ('uc006-nosc04.ie', 'XNV498-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc04.ie'), 'uc006-nosc04.ie', 'ns1.uc006-nosc04.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc04.ie'), 'uc006-nosc04.ie', 'ns2.uc006-nosc04.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc04.ie'), 'uc006-nosc04.ie', 'ns3.uc006-nosc04.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc006-nosc04.ie', 'ns1.uc006-nosc04.ie', '10.10.121.23', NULL, '1'), ('uc006-nosc04.ie', 'ns2.uc006-nosc04.ie', '1.3.4.2', NULL, '2'), ('uc006-nosc04.ie', 'ns3.uc006-nosc04.ie', '10.12.13.14', NULL, '3');

INSERT INTO TicketIndex VALUES (NULL);
INSERT INTO Ticket VALUES ((SELECT MAX(id) FROM TicketIndex), 'Modification', 'uc006-nosc04.ie', NULL, 'Test Holder 0001 - Modified', NULL, 603, NULL, 3, 2, NULL, 'Domain reg request.', 'XDD274-IEDR', NULL, NULL, NULL, 'XDD274-IEDR', NULL, 'XDD274-IEDR', NULL, 'XNV498-IEDR', 'New', NOW(), 'New', NOW(), 'NO', NULL, NOW(), NOW(), '', NULL, NULL, NOW(), 'NO', 1, NULL, 'NO', 'New', NOW(), 'New', NOW(), 1);
INSERT INTO TicketHist VALUES (NULL, (SELECT T_Number FROM Ticket WHERE D_Name = 'uc006-nosc04.ie'), 'Modification', 'uc006-nosc04.ie', NULL, 'Test Holder 0001 - Modified', NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 603), NULL, 3, 2, NULL, 'Domain reg request.', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), NULL, NULL, NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'New', NOW(), 'New', NOW(), 'NO', NULL, NOW(), NOW(), '', NOW(), 'XNV498-IEDR', NULL, NULL, NOW(), 'NO', 1, NULL, 'NO', 'New', NOW(), 'New', NOW());
UPDATE Ticket T SET Chng_Id = (SELECT MAX(Chng_Id) FROM TicketHist WHERE T_Number = T.T_Number) WHERE D_Name = 'uc006-nosc04.ie';
INSERT INTO TicketNameserverHist VALUES
((SELECT Chng_Id FROM Ticket WHERE D_Name = 'uc006-nosc04.ie'), (SELECT T_Number FROM Ticket WHERE D_Name = 'uc006-nosc04.ie'), 'ns1.uc006-nosc04.ie', NULL, '10.10.121.23', NULL, NULL, NULL, 0, NOW()),
((SELECT Chng_Id FROM Ticket WHERE D_Name = 'uc006-nosc04.ie'), (SELECT T_Number FROM Ticket WHERE D_Name = 'uc006-nosc04.ie'), 'ns2.uc006-nosc04.ie', NULL, '1.3.4.2', NULL, NULL, NULL, 1, NOW()),
((SELECT Chng_Id FROM Ticket WHERE D_Name = 'uc006-nosc04.ie'), (SELECT T_Number FROM Ticket WHERE D_Name = 'uc006-nosc04.ie'), 'ns3.uc006-nosc04.ie', NULL, '10.12.13.14', NULL, NULL, NULL, 2, NOW());
INSERT INTO TicketNameserver VALUES
((SELECT T_Number FROM Ticket WHERE D_Name = 'uc006-nosc04.ie'), 'ns1.uc006-nosc04.ie', NULL, '10.10.121.23', NULL, NULL, NULL, 0, NOW()),
((SELECT T_Number FROM Ticket WHERE D_Name = 'uc006-nosc04.ie'), 'ns2.uc006-nosc04.ie', NULL, '1.3.4.2', NULL, NULL, NULL, 1, NOW()),
((SELECT T_Number FROM Ticket WHERE D_Name = 'uc006-nosc04.ie'), 'ns3.uc006-nosc04.ie', NULL, '10.12.13.14', NULL, NULL, NULL, 2, NOW());

INSERT INTO DomainHist VALUES (NULL, 'uc006-nosc05.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc006-nosc05.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc05.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc05.ie'), 'uc006-nosc05.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc05.ie'), 'uc006-nosc05.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc05.ie'), 'uc006-nosc05.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc05.ie'), 'uc006-nosc05.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc006-nosc05.ie', 'XOE550-IEDR', 'Admin'), ('uc006-nosc05.ie', 'XBC189-IEDR', 'Billing'), ('uc006-nosc05.ie', 'XBC189-IEDR', 'Creator'), ('uc006-nosc05.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc05.ie'), 'uc006-nosc05.ie', 'ns1.uc006-nosc05.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc05.ie'), 'uc006-nosc05.ie', 'ns2.uc006-nosc05.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc05.ie'), 'uc006-nosc05.ie', 'ns3.uc006-nosc05.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc006-nosc05.ie', 'ns1.uc006-nosc05.ie', '10.10.121.23', NULL, '1'), ('uc006-nosc05.ie', 'ns2.uc006-nosc05.ie', '1.3.4.2', NULL, '2'), ('uc006-nosc05.ie', 'ns3.uc006-nosc05.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc006-nosc06.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 603), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XNV498-IEDR', NOW(), 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc006-nosc06.ie', 'Test Holder 0001', 3, 2, NULL, 603, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc06.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc06.ie'), 'uc006-nosc06.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc06.ie'), 'uc006-nosc06.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc06.ie'), 'uc006-nosc06.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc06.ie'), 'uc006-nosc06.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc006-nosc06.ie', 'XOE550-IEDR', 'Admin'), ('uc006-nosc06.ie', 'XNV498-IEDR', 'Billing'), ('uc006-nosc06.ie', 'XNV498-IEDR', 'Creator'), ('uc006-nosc06.ie', 'XNV498-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc06.ie'), 'uc006-nosc06.ie', 'ns1.uc006-nosc06.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc06.ie'), 'uc006-nosc06.ie', 'ns2.uc006-nosc06.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc06.ie'), 'uc006-nosc06.ie', 'ns3.uc006-nosc06.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc006-nosc06.ie', 'ns1.uc006-nosc06.ie', '10.10.121.23', NULL, '1'), ('uc006-nosc06.ie', 'ns2.uc006-nosc06.ie', '1.3.4.2', NULL, '2'), ('uc006-nosc06.ie', 'ns3.uc006-nosc06.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc006-nosc07.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 603), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XNV498-IEDR', NOW(), 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc006-nosc07.ie', 'Test Holder 0001', 3, 2, NULL, 603, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc07.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc07.ie'), 'uc006-nosc07.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc07.ie'), 'uc006-nosc07.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc07.ie'), 'uc006-nosc07.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc07.ie'), 'uc006-nosc07.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc006-nosc07.ie', 'XOE550-IEDR', 'Admin'), ('uc006-nosc07.ie', 'XBC189-IEDR', 'Billing'), ('uc006-nosc07.ie', 'XNV498-IEDR', 'Creator'), ('uc006-nosc07.ie', 'XNV498-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc07.ie'), 'uc006-nosc07.ie', 'ns1.uc006-nosc07.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc07.ie'), 'uc006-nosc07.ie', 'ns2.uc006-nosc07.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc07.ie'), 'uc006-nosc07.ie', 'ns3.uc006-nosc07.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc006-nosc07.ie', 'ns1.uc006-nosc07.ie', '10.10.121.23', NULL, '1'), ('uc006-nosc07.ie', 'ns2.uc006-nosc07.ie', '1.3.4.2', NULL, '2'), ('uc006-nosc07.ie', 'ns3.uc006-nosc07.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc006-nosc08.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 603), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XNV498-IEDR', NOW(), 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc006-nosc08.ie', 'Test Holder 0001', 3, 2, NULL, 603, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc08.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc08.ie'), 'uc006-nosc08.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc08.ie'), 'uc006-nosc08.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc08.ie'), 'uc006-nosc08.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc08.ie'), 'uc006-nosc08.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc006-nosc08.ie', 'XOE550-IEDR', 'Admin'), ('uc006-nosc08.ie', 'XBC189-IEDR', 'Billing'), ('uc006-nosc08.ie', 'XNV498-IEDR', 'Creator'), ('uc006-nosc08.ie', 'XNV498-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc08.ie'), 'uc006-nosc08.ie', 'ns1.uc006-nosc08.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc08.ie'), 'uc006-nosc08.ie', 'ns2.uc006-nosc08.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc08.ie'), 'uc006-nosc08.ie', 'ns3.uc006-nosc08.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc006-nosc08.ie', 'ns1.uc006-nosc08.ie', '10.10.121.23', NULL, '1'), ('uc006-nosc08.ie', 'ns2.uc006-nosc08.ie', '1.3.4.2', NULL, '2'), ('uc006-nosc08.ie', 'ns3.uc006-nosc08.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc006-nosc09.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 603), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XNV498-IEDR', NOW(), 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc006-nosc09.ie', 'Test Holder 0001', 3, 2, NULL, 603, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc09.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc09.ie'), 'uc006-nosc09.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc09.ie'), 'uc006-nosc09.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc09.ie'), 'uc006-nosc09.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc09.ie'), 'uc006-nosc09.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc006-nosc09.ie', 'XOE550-IEDR', 'Admin'), ('uc006-nosc09.ie', 'XBC189-IEDR', 'Billing'), ('uc006-nosc09.ie', 'XNV498-IEDR', 'Creator'), ('uc006-nosc09.ie', 'XNV498-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc09.ie'), 'uc006-nosc09.ie', 'ns1.uc006-nosc09.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc09.ie'), 'uc006-nosc09.ie', 'ns2.uc006-nosc09.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc006-nosc09.ie'), 'uc006-nosc09.ie', 'ns3.uc006-nosc09.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc006-nosc09.ie', 'ns1.uc006-nosc09.ie', '10.10.121.23', NULL, '1'), ('uc006-nosc09.ie', 'ns2.uc006-nosc09.ie', '1.3.4.2', NULL, '2'), ('uc006-nosc09.ie', 'ns3.uc006-nosc09.ie', '10.12.13.14', NULL, '3');

