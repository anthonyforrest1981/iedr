INSERT INTO NicHandleHist VALUES (NULL, 'YYY001-IEDR', 'Use Case 082 - Registrar - Test Account', (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc082_registrar_main@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'XBC189-IEDR', 'A', NULL, 'YES', 'XBC189-IEDR', NOW());
INSERT INTO NicHandle VALUES ('YYY001-IEDR', 'Use Case 082 - Registrar - Test Account', 600, 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc082_registrar_main@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'XBC189-IEDR', 'A', NULL, 'YES', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'YYY001-IEDR'));
INSERT INTO AccessHist VALUES (NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'YYY001-IEDR'), 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 0, 'NO', 'XBC189-IEDR', NOW(), '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '');
INSERT INTO Access VALUES ('YYY001-IEDR', 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 0, 'NO', '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '', (SELECT MAX(A.Chng_Id) FROM AccessHist A JOIN NicHandleHist NH  ON A.Nic_Handle_Chng_Id = NH.Chng_Id WHERE NH.Nic_Handle = 'YYY001-IEDR'));

INSERT INTO NicHandleHist VALUES (NULL, 'YYY002-IEDR', 'Use Case 082 - Direct - Test Account', (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc082_direct_main@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'XDD274-IEDR', 'A', NULL, 'YES', 'XDD274-IEDR', NOW());
INSERT INTO NicHandle VALUES ('YYY002-IEDR', 'Use Case 082 - Direct - Test Account', 1, 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc082_direct_main@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'XDD274-IEDR', 'A', NULL, 'YES', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'YYY002-IEDR'));
INSERT INTO AccessHist VALUES (NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'YYY002-IEDR'), 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 0, 'NO', 'XDD274-IEDR', NOW(), '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '');
INSERT INTO Access VALUES ('YYY002-IEDR', 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 0, 'NO', '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '', (SELECT MAX(A.Chng_Id) FROM AccessHist A JOIN NicHandleHist NH  ON A.Nic_Handle_Chng_Id = NH.Chng_Id WHERE NH.Nic_Handle = 'YYY002-IEDR'));

INSERT INTO DomainHist VALUES (NULL, 'uc082-billable-registrar.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600),  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc082-billable-registrar.ie', 'Test Holder 0001', 3, 2, NULL, 600,  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc082-billable-registrar.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc082-billable-registrar.ie'), 'uc082-billable-registrar.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'YYY001-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc082-billable-registrar.ie'), 'uc082-billable-registrar.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc082-billable-registrar.ie'), 'uc082-billable-registrar.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc082-billable-registrar.ie'), 'uc082-billable-registrar.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc082-billable-registrar.ie', 'YYY001-IEDR', 'Tech'), ('uc082-billable-registrar.ie', 'XBC189-IEDR', 'Billing'), ('uc082-billable-registrar.ie', 'XBC189-IEDR', 'Creator'), ('uc082-billable-registrar.ie', 'XBC189-IEDR', 'Admin');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc082-billable-registrar.ie'), 'uc082-billable-registrar.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc082-billable-registrar.ie'), 'uc082-billable-registrar.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc082-billable-registrar.ie'), 'uc082-billable-registrar.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('uc082-billable-registrar.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('uc082-billable-registrar.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('uc082-billable-registrar.ie', 'ns3.abc1.ie', NULL, NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc082-charity-registrar.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600),  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '113', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc082-charity-registrar.ie', 'Test Holder 0001', 3, 2, NULL, 600,  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '113', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc082-charity-registrar.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc082-charity-registrar.ie'), 'uc082-charity-registrar.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'YYY001-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc082-charity-registrar.ie'), 'uc082-charity-registrar.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc082-charity-registrar.ie'), 'uc082-charity-registrar.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc082-charity-registrar.ie'), 'uc082-charity-registrar.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc082-charity-registrar.ie', 'YYY001-IEDR', 'Tech'), ('uc082-charity-registrar.ie', 'XBC189-IEDR', 'Billing'), ('uc082-charity-registrar.ie', 'XBC189-IEDR', 'Creator'), ('uc082-charity-registrar.ie', 'XBC189-IEDR', 'Admin');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc082-charity-registrar.ie'), 'uc082-charity-registrar.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc082-charity-registrar.ie'), 'uc082-charity-registrar.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc082-charity-registrar.ie'), 'uc082-charity-registrar.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('uc082-charity-registrar.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('uc082-charity-registrar.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('uc082-charity-registrar.ie', 'ns3.abc1.ie', NULL, NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc082-billable-direct.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1),  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XDD274-IEDR', NOW(), 'NO', '25', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc082-billable-direct.ie', 'Test Holder 0001', 3, 2, NULL, 1,  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '25', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc082-billable-direct.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc082-billable-direct.ie'), 'uc082-billable-direct.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'YYY002-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc082-billable-direct.ie'), 'uc082-billable-direct.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc082-billable-direct.ie'), 'uc082-billable-direct.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc082-billable-direct.ie'), 'uc082-billable-direct.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc082-billable-direct.ie', 'YYY002-IEDR', 'Tech'), ('uc082-billable-direct.ie', 'XDD274-IEDR', 'Billing'), ('uc082-billable-direct.ie', 'XDD274-IEDR', 'Creator'), ('uc082-billable-direct.ie', 'XDD274-IEDR', 'Admin');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc082-billable-direct.ie'), 'uc082-billable-direct.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc082-billable-direct.ie'), 'uc082-billable-direct.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc082-billable-direct.ie'), 'uc082-billable-direct.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('uc082-billable-direct.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('uc082-billable-direct.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('uc082-billable-direct.ie', 'ns3.abc1.ie', NULL, NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc082-charity-direct.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1),  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XDD274-IEDR', NOW(), 'NO', '121', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc082-charity-direct.ie', 'Test Holder 0001', 3, 2, NULL, 1,  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '121', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc082-charity-direct.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc082-charity-direct.ie'), 'uc082-charity-direct.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'YYY002-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc082-charity-direct.ie'), 'uc082-charity-direct.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc082-charity-direct.ie'), 'uc082-charity-direct.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc082-charity-direct.ie'), 'uc082-charity-direct.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc082-charity-direct.ie', 'YYY002-IEDR', 'Tech'), ('uc082-charity-direct.ie', 'XDD274-IEDR', 'Billing'), ('uc082-charity-direct.ie', 'XDD274-IEDR', 'Creator'), ('uc082-charity-direct.ie', 'XDD274-IEDR', 'Admin');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc082-charity-direct.ie'), 'uc082-charity-direct.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc082-charity-direct.ie'), 'uc082-charity-direct.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc082-charity-direct.ie'), 'uc082-charity-direct.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('uc082-charity-direct.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('uc082-charity-direct.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('uc082-charity-direct.ie', 'ns3.abc1.ie', NULL, NULL, '3');

