INSERT INTO NicHandleHist VALUES (NULL, 'ZZZ001-IEDR', 'Use Case 081 - Registrar - Test Account', (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc081_registrar_main@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'XBC189-IEDR', 'A', NULL, 'YES', 'XBC189-IEDR', NOW());
INSERT INTO NicHandle VALUES ('ZZZ001-IEDR', 'Use Case 081 - Registrar - Test Account', 600, 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc081_registrar_main@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'XBC189-IEDR', 'A', NULL, 'YES', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'ZZZ001-IEDR'));
INSERT INTO AccessHist VALUES (NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'ZZZ001-IEDR'), 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 0, 'NO', 'XBC189-IEDR', NOW(), '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '');
INSERT INTO Access VALUES ('ZZZ001-IEDR', 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 0, 'NO', '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '', (SELECT MAX(A.Chng_Id) FROM AccessHist A JOIN NicHandleHist NH  ON A.Nic_Handle_Chng_Id = NH.Chng_Id WHERE NH.Nic_Handle = 'ZZZ001-IEDR'));

INSERT INTO NicHandleHist VALUES (NULL, 'ZZZ002-IEDR', 'Use Case 081 - Direct - Test Account', (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc081_direct_main@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'XDD274-IEDR', 'A', NULL, 'YES', 'XDD274-IEDR', NOW());
INSERT INTO NicHandle VALUES ('ZZZ002-IEDR', 'Use Case 081 - Direct - Test Account', 1, 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc081_direct_main@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'XDD274-IEDR', 'A', NULL, 'YES', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'ZZZ002-IEDR'));
INSERT INTO AccessHist VALUES (NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'ZZZ002-IEDR'), 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 0, 'NO', 'XDD274-IEDR', NOW(), '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '');
INSERT INTO Access VALUES ('ZZZ002-IEDR', 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 0, 'NO', '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '', (SELECT MAX(A.Chng_Id) FROM AccessHist A JOIN NicHandleHist NH  ON A.Nic_Handle_Chng_Id = NH.Chng_Id WHERE NH.Nic_Handle = 'ZZZ002-IEDR'));

INSERT INTO NicHandleHist VALUES (NULL, 'ZZZ003-IEDR', 'Use Case 081 - Registrar - Helper', (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc081_registrar_helper@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'ZZZ001-IEDR', 'A', NULL, 'YES', 'XBC189-IEDR', NOW());
INSERT INTO NicHandle VALUES ('ZZZ003-IEDR', 'Use Case 081 - Registrar - Helper', 600, 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc081_registrar_helper@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'ZZZ001-IEDR', 'A', NULL, 'YES', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'ZZZ003-IEDR'));
INSERT INTO AccessHist VALUES (NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'ZZZ003-IEDR'), 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 0, 'NO', 'XBC189-IEDR', NOW(), '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '');
INSERT INTO Access VALUES ('ZZZ003-IEDR', 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 0, 'NO', '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '', (SELECT MAX(A.Chng_Id) FROM AccessHist A JOIN NicHandleHist NH  ON A.Nic_Handle_Chng_Id = NH.Chng_Id WHERE NH.Nic_Handle = 'ZZZ003-IEDR'));

INSERT INTO NicHandleHist VALUES (NULL, 'ZZZ004-IEDR', 'Use Case 081 - Direct - Helper', (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc081_direct_helper@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'ZZZ002-IEDR', 'A', NULL, 'YES', 'XDD274-IEDR', NOW());
INSERT INTO NicHandle VALUES ('ZZZ004-IEDR', 'Use Case 081 - Direct - Helper', 1, 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc081_direct_helper@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'ZZZ002-IEDR', 'A', NULL, 'YES', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'ZZZ004-IEDR'));
INSERT INTO AccessHist VALUES (NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'ZZZ004-IEDR'), 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 0, 'NO', 'XDD274-IEDR', NOW(), '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '');
INSERT INTO Access VALUES ('ZZZ004-IEDR', 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 0, 'NO', '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '', (SELECT MAX(A.Chng_Id) FROM AccessHist A JOIN NicHandleHist NH  ON A.Nic_Handle_Chng_Id = NH.Chng_Id WHERE NH.Nic_Handle = 'ZZZ004-IEDR'));

INSERT INTO DomainHist VALUES (NULL, 'uc081-billable-registrar.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600),  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc081-billable-registrar.ie', 'Test Holder 0001', 3, 2, NULL, 600,  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc081-billable-registrar.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc081-billable-registrar.ie'), 'uc081-billable-registrar.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'ZZZ001-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc081-billable-registrar.ie'), 'uc081-billable-registrar.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc081-billable-registrar.ie'), 'uc081-billable-registrar.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc081-billable-registrar.ie'), 'uc081-billable-registrar.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES
 ('uc081-billable-registrar.ie', 'ZZZ001-IEDR', 'Admin'), ('uc081-billable-registrar.ie', 'XBC189-IEDR', 'Billing'), ('uc081-billable-registrar.ie', 'XBC189-IEDR', 'Creator'), ('uc081-billable-registrar.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc081-billable-registrar.ie'), 'uc081-billable-registrar.ie', 'ns1.dns.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc081-billable-registrar.ie'), 'uc081-billable-registrar.ie', 'ns2.dns.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc081-billable-registrar.ie'), 'uc081-billable-registrar.ie', 'ns3.dns.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('uc081-billable-registrar.ie', 'ns1.dns.ie', NULL, NULL, '1'), ('uc081-billable-registrar.ie', 'ns2.dns.ie', NULL, NULL, '2'), ('uc081-billable-registrar.ie', 'ns3.dns.ie', NULL, NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc081-charity-registrar.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600),  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '113', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc081-charity-registrar.ie', 'Test Holder 0001', 3, 2, NULL, 600,  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '113', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc081-charity-registrar.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc081-charity-registrar.ie'), 'uc081-charity-registrar.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'ZZZ001-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc081-charity-registrar.ie'), 'uc081-charity-registrar.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc081-charity-registrar.ie'), 'uc081-charity-registrar.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc081-charity-registrar.ie'), 'uc081-charity-registrar.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES
 ('uc081-charity-registrar.ie', 'ZZZ001-IEDR', 'Admin'), ('uc081-charity-registrar.ie', 'XBC189-IEDR', 'Billing'), ('uc081-charity-registrar.ie', 'XBC189-IEDR', 'Creator'), ('uc081-charity-registrar.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc081-charity-registrar.ie'), 'uc081-charity-registrar.ie', 'ns1.dns.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc081-charity-registrar.ie'), 'uc081-charity-registrar.ie', 'ns2.dns.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc081-charity-registrar.ie'), 'uc081-charity-registrar.ie', 'ns3.dns.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('uc081-charity-registrar.ie', 'ns1.dns.ie', NULL, NULL, '1'), ('uc081-charity-registrar.ie', 'ns2.dns.ie', NULL, NULL, '2'), ('uc081-charity-registrar.ie', 'ns3.dns.ie', NULL, NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc081-charity-registrar-2.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600),  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '113', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc081-charity-registrar-2.ie', 'Test Holder 0001', 3, 2, NULL, 600,  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '113', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc081-charity-registrar-2.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc081-charity-registrar-2.ie'), 'uc081-charity-registrar-2.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc081-charity-registrar-2.ie'), 'uc081-charity-registrar-2.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc081-charity-registrar-2.ie'), 'uc081-charity-registrar-2.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc081-charity-registrar-2.ie'), 'uc081-charity-registrar-2.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'ZZZ001-IEDR'), 'Tech');
INSERT INTO Contact VALUES
 ('uc081-charity-registrar-2.ie', 'XBC189-IEDR', 'Admin'), ('uc081-charity-registrar-2.ie', 'XBC189-IEDR', 'Billing'), ('uc081-charity-registrar-2.ie', 'XBC189-IEDR', 'Creator'), ('uc081-charity-registrar-2.ie', 'ZZZ001-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc081-charity-registrar-2.ie'), 'uc081-charity-registrar-2.ie', 'ns1.dns.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc081-charity-registrar-2.ie'), 'uc081-charity-registrar-2.ie', 'ns2.dns.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc081-charity-registrar-2.ie'), 'uc081-charity-registrar-2.ie', 'ns3.dns.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('uc081-charity-registrar-2.ie', 'ns1.dns.ie', NULL, NULL, '1'), ('uc081-charity-registrar-2.ie', 'ns2.dns.ie', NULL, NULL, '2'), ('uc081-charity-registrar-2.ie', 'ns3.dns.ie', NULL, NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc081-billable-direct.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1),  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XDD274-IEDR', NOW(), 'NO', '25', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc081-billable-direct.ie', 'Test Holder 0001', 3, 2, NULL, 1,  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '25', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc081-billable-direct.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc081-billable-direct.ie'), 'uc081-billable-direct.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'ZZZ002-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc081-billable-direct.ie'), 'uc081-billable-direct.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc081-billable-direct.ie'), 'uc081-billable-direct.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc081-billable-direct.ie'), 'uc081-billable-direct.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Tech');
INSERT INTO Contact VALUES
 ('uc081-billable-direct.ie', 'ZZZ002-IEDR', 'Admin'), ('uc081-billable-direct.ie', 'XDD274-IEDR', 'Billing'), ('uc081-billable-direct.ie', 'XDD274-IEDR', 'Creator'), ('uc081-billable-direct.ie', 'XDD274-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc081-billable-direct.ie'), 'uc081-billable-direct.ie', 'ns1.dns.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc081-billable-direct.ie'), 'uc081-billable-direct.ie', 'ns2.dns.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc081-billable-direct.ie'), 'uc081-billable-direct.ie', 'ns3.dns.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('uc081-billable-direct.ie', 'ns1.dns.ie', NULL, NULL, '1'), ('uc081-billable-direct.ie', 'ns2.dns.ie', NULL, NULL, '2'), ('uc081-billable-direct.ie', 'ns3.dns.ie', NULL, NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc081-charity-direct.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1),  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XDD274-IEDR', NOW(), 'NO', '121', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc081-charity-direct.ie', 'Test Holder 0001', 3, 2, NULL, 1,  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '121', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc081-charity-direct.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc081-charity-direct.ie'), 'uc081-charity-direct.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'ZZZ002-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc081-charity-direct.ie'), 'uc081-charity-direct.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc081-charity-direct.ie'), 'uc081-charity-direct.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc081-charity-direct.ie'), 'uc081-charity-direct.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Tech');
INSERT INTO Contact VALUES
 ('uc081-charity-direct.ie', 'ZZZ002-IEDR', 'Admin'), ('uc081-charity-direct.ie', 'XDD274-IEDR', 'Billing'), ('uc081-charity-direct.ie', 'XDD274-IEDR', 'Creator'), ('uc081-charity-direct.ie', 'XDD274-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc081-charity-direct.ie'), 'uc081-charity-direct.ie', 'ns1.dns.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc081-charity-direct.ie'), 'uc081-charity-direct.ie', 'ns2.dns.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc081-charity-direct.ie'), 'uc081-charity-direct.ie', 'ns3.dns.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('uc081-charity-direct.ie', 'ns1.dns.ie', NULL, NULL, '1'), ('uc081-charity-direct.ie', 'ns2.dns.ie', NULL, NULL, '2'), ('uc081-charity-direct.ie', 'ns3.dns.ie', NULL, NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc081-charity-direct-2.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1),  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XDD274-IEDR', NOW(), 'NO', '121', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc081-charity-direct-2.ie', 'Test Holder 0001', 3, 2, NULL, 1,  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '121', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc081-charity-direct-2.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc081-charity-direct-2.ie'), 'uc081-charity-direct-2.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc081-charity-direct-2.ie'), 'uc081-charity-direct-2.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc081-charity-direct-2.ie'), 'uc081-charity-direct-2.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc081-charity-direct-2.ie'), 'uc081-charity-direct-2.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'ZZZ002-IEDR'), 'Tech');
INSERT INTO Contact VALUES
 ('uc081-charity-direct-2.ie', 'XDD274-IEDR', 'Admin'), ('uc081-charity-direct-2.ie', 'XDD274-IEDR', 'Billing'), ('uc081-charity-direct-2.ie', 'XDD274-IEDR', 'Creator'), ('uc081-charity-direct-2.ie', 'ZZZ002-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc081-charity-direct-2.ie'), 'uc081-charity-direct-2.ie', 'ns1.dns.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc081-charity-direct-2.ie'), 'uc081-charity-direct-2.ie', 'ns2.dns.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc081-charity-direct-2.ie'), 'uc081-charity-direct-2.ie', 'ns3.dns.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('uc081-charity-direct-2.ie', 'ns1.dns.ie', NULL, NULL, '1'), ('uc081-charity-direct-2.ie', 'ns2.dns.ie', NULL, NULL, '2'), ('uc081-charity-direct-2.ie', 'ns3.dns.ie', NULL, NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc081-sc07a.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1),  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XDD274-IEDR', NOW(), 'NO', '25', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc081-sc07a.ie', 'Test Holder 0001', 3, 2, NULL, 1,  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '25', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc081-sc07a.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc081-sc07a.ie'), 'uc081-sc07a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'ZZZ002-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc081-sc07a.ie'), 'uc081-sc07a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc081-sc07a.ie'), 'uc081-sc07a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc081-sc07a.ie'), 'uc081-sc07a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Tech');
INSERT INTO Contact VALUES
 ('uc081-sc07a.ie', 'ZZZ002-IEDR', 'Admin'), ('uc081-sc07a.ie', 'XDD274-IEDR', 'Billing'), ('uc081-sc07a.ie', 'XDD274-IEDR', 'Creator'), ('uc081-sc07a.ie', 'XDD274-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc081-sc07a.ie'), 'uc081-sc07a.ie', 'ns1.dns.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc081-sc07a.ie'), 'uc081-sc07a.ie', 'ns2.dns.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc081-sc07a.ie'), 'uc081-sc07a.ie', 'ns3.dns.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('uc081-sc07a.ie', 'ns1.dns.ie', NULL, NULL, '1'), ('uc081-sc07a.ie', 'ns2.dns.ie', NULL, NULL, '2'), ('uc081-sc07a.ie', 'ns3.dns.ie', NULL, NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc081-sc07b.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1),  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XDD274-IEDR', NOW(), 'NO', '25', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc081-sc07b.ie', 'Test Holder 0001', 3, 2, NULL, 1,  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '25', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc081-sc07b.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc081-sc07b.ie'), 'uc081-sc07b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'ZZZ002-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc081-sc07b.ie'), 'uc081-sc07b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc081-sc07b.ie'), 'uc081-sc07b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc081-sc07b.ie'), 'uc081-sc07b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Tech');
INSERT INTO Contact VALUES
 ('uc081-sc07b.ie', 'ZZZ002-IEDR', 'Admin'), ('uc081-sc07b.ie', 'XDD274-IEDR', 'Billing'), ('uc081-sc07b.ie', 'XDD274-IEDR', 'Creator'), ('uc081-sc07b.ie', 'XDD274-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc081-sc07b.ie'), 'uc081-sc07b.ie', 'ns1.dns.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc081-sc07b.ie'), 'uc081-sc07b.ie', 'ns2.dns.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc081-sc07b.ie'), 'uc081-sc07b.ie', 'ns3.dns.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('uc081-sc07b.ie', 'ns1.dns.ie', NULL, NULL, '1'), ('uc081-sc07b.ie', 'ns2.dns.ie', NULL, NULL, '2'), ('uc081-sc07b.ie', 'ns3.dns.ie', NULL, NULL, '3');

INSERT INTO TicketIndex VALUES (NULL);
INSERT INTO Ticket VALUES ((SELECT MAX(id) FROM TicketIndex), 'Modification', 'uc081-sc07a.ie', NULL, 'Test Holder 0001 - Modified', NULL, 1, NULL, 2, 3, NULL, 'Domain modify request.', 'ZZZ002-IEDR', NULL, NULL, NULL, 'XDD274-IEDR', NULL, 'XDD274-IEDR', NULL, 'ZZZ002-IEDR', 'New', NOW(), 'New', NOW(), 'NO', NULL, NOW(), NOW(), '', NULL, NULL, NOW(), 'NO', 1, NULL, 'NO', 'New', NOW(), 'New', CURDATE(), 1);
INSERT INTO TicketHist VALUES (NULL, (SELECT T_Number FROM Ticket WHERE D_Name = 'uc081-sc07a.ie'), 'Modification', 'uc081-sc07a.ie', NULL, 'Test Holder 0001 - Modified', NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), NULL, 2, 3, NULL, 'Domain modify request.', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'ZZZ002-IEDR'), NULL, NULL, NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'ZZZ002-IEDR'), 'New', NOW(), 'New', NOW(), 'NO', NULL, NOW(), NOW(), '', NOW(), 'XDD274-IEDR', NULL, NULL, NOW(), 'NO', 1, NULL, 'NO', 'New', NOW(), 'New', CURDATE());
UPDATE Ticket T SET Chng_Id = (SELECT MAX(Chng_Id) FROM TicketHist WHERE T_Number = T.T_Number) WHERE D_Name = 'uc081-sc07a.ie';
INSERT INTO TicketNameserverHist VALUES
((SELECT Chng_Id FROM Ticket WHERE D_Name = 'uc081-sc07a.ie'), (SELECT T_Number FROM Ticket WHERE D_Name = 'uc081-sc07a.ie'), 'ns1.abc1.ie', NULL, NULL, NULL, NULL, NULL, 0, NOW()),
((SELECT Chng_Id FROM Ticket WHERE D_Name = 'uc081-sc07a.ie'), (SELECT T_Number FROM Ticket WHERE D_Name = 'uc081-sc07a.ie'), 'ns2.abc1.ie', NULL, NULL, NULL, NULL, NULL, 1, NOW());
INSERT INTO TicketNameserver VALUES ((SELECT T_Number FROM Ticket WHERE D_Name = 'uc081-sc07a.ie'), 'ns1.abc1.ie', NULL, NULL, NULL, NULL, NULL, 0, NOW()), ((SELECT T_Number FROM Ticket WHERE D_Name = 'uc081-sc07a.ie'), 'ns2.abc1.ie', NULL, NULL, NULL, NULL, NULL, 1, NOW());

INSERT INTO TicketIndex VALUES (NULL);
INSERT INTO Ticket VALUES ((SELECT MAX(id) FROM TicketIndex), 'Modification', 'uc081-sc07b.ie', NULL, 'Test Holder 0001 - Modified', NULL, 1, NULL, 2, 3, NULL, 'Domain modify request.', 'ZZZ002-IEDR', NULL, NULL, NULL, 'XDD274-IEDR', NULL, 'XDD274-IEDR', NULL, 'XDD274-IEDR', 'New', NOW(), 'New', NOW(), 'NO', NULL, NOW(), NOW(), '', NULL, NULL, NOW(), 'NO', 1, NULL, 'NO', 'New', NOW(), 'New', CURDATE(), 1);
INSERT INTO TicketHist VALUES (NULL, (SELECT T_Number FROM Ticket WHERE D_Name = 'uc081-sc07b.ie'), 'Modification', 'uc081-sc07b.ie', NULL, 'Test Holder 0001 - Modified', NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), NULL, 2, 3, NULL, 'Domain modify request.', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'ZZZ002-IEDR'), NULL, NULL, NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'New', NOW(), 'New', NOW(), 'NO', NULL, NOW(), NOW(), '', NOW(), 'XDD274-IEDR', NULL, NULL, NOW(), 'NO', 1, NULL, 'NO', 'New', NOW(), 'New', CURDATE());
UPDATE Ticket T SET Chng_Id = (SELECT MAX(Chng_Id) FROM TicketHist WHERE T_Number = T.T_Number) WHERE D_Name = 'uc081-sc07b.ie';
INSERT INTO TicketNameserverHist VALUES
((SELECT Chng_Id FROM Ticket WHERE D_Name = 'uc081-sc07b.ie'), (SELECT T_Number FROM Ticket WHERE D_Name = 'uc081-sc07b.ie'), 'ns1.abc1.ie', NULL, NULL, NULL, NULL, NULL, 0, NOW()),
((SELECT Chng_Id FROM Ticket WHERE D_Name = 'uc081-sc07b.ie'), (SELECT T_Number FROM Ticket WHERE D_Name = 'uc081-sc07b.ie'), 'ns2.abc1.ie', NULL, NULL, NULL, NULL, NULL, 1, NOW());
INSERT INTO TicketNameserver VALUES ((SELECT T_Number FROM Ticket WHERE D_Name = 'uc081-sc07b.ie'), 'ns1.abc1.ie', NULL, NULL, NULL, NULL, NULL, 0, NOW()), ((SELECT T_Number FROM Ticket WHERE D_Name = 'uc081-sc07b.ie'), 'ns2.abc1.ie', NULL, NULL, NULL, NULL, NULL, 1, NOW());

INSERT INTO DomainHist VALUES (NULL, 'uc081-nosc03.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600),  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc081-nosc03.ie', 'Test Holder 0001', 3, 2, NULL, 600,  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc081-nosc03.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc081-nosc03.ie'), 'uc081-nosc03.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'ZZZ001-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc081-nosc03.ie'), 'uc081-nosc03.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc081-nosc03.ie'), 'uc081-nosc03.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc081-nosc03.ie'), 'uc081-nosc03.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES
 ('uc081-nosc03.ie', 'ZZZ001-IEDR', 'Admin'), ('uc081-nosc03.ie', 'XBC189-IEDR', 'Billing'), ('uc081-nosc03.ie', 'XBC189-IEDR', 'Creator'), ('uc081-nosc03.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc081-nosc03.ie'), 'uc081-nosc03.ie', 'ns1.dns.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc081-nosc03.ie'), 'uc081-nosc03.ie', 'ns2.dns.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc081-nosc03.ie'), 'uc081-nosc03.ie', 'ns3.dns.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('uc081-nosc03.ie', 'ns1.dns.ie', NULL, NULL, '1'), ('uc081-nosc03.ie', 'ns2.dns.ie', NULL, NULL, '2'), ('uc081-nosc03.ie', 'ns3.dns.ie', NULL, NULL, '3');

