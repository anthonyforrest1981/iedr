INSERT INTO NicHandleHist VALUES (NULL, 'UC017AA-IEDR', 'Use Case 017 - Registrar - Test Account', (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc017_registrar_main@iedr.ie', 'Active', NOW(), NOW(), NOW(),'NO', 'test data generation', 'XBC189-IEDR', 'A', NULL, 'YES', 'XBC189-IEDR', NOW());
INSERT INTO NicHandle VALUES ('UC017AA-IEDR', 'Use Case 017 - Registrar - Test Account', '600', 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc017_registrar_main@iedr.ie', 'Active', NOW(), NOW(), NOW(),'NO', 'test data generation', 'XBC189-IEDR', 'A', NULL, 'YES', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC017AA-IEDR'));
INSERT INTO AccessHist VALUES (NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC017AA-IEDR'), 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 0, 'NO', 'XBC189-IEDR', NOW(), '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '');
INSERT INTO Access VALUES ('UC017AA-IEDR', 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 0, 'NO', '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '', (SELECT MAX(A.Chng_Id) FROM AccessHist A JOIN NicHandleHist NH  ON A.Nic_Handle_Chng_Id = NH.Chng_Id WHERE NH.Nic_Handle = 'UC017AA-IEDR'));

INSERT INTO NicHandleHist VALUES (NULL, 'UC017AB-IEDR', 'Use Case 017 - Direct - Test Account', (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc017_direct_main@iedr.ie', 'Active', NOW(), NOW(), NOW(),'NO', 'test data generation', 'XDD274-IEDR', 'A', NULL, 'YES', 'XDD274-IEDR', NOW());
INSERT INTO NicHandle VALUES ('UC017AB-IEDR', 'Use Case 017 - Direct - Test Account', '001', 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc017_direct_main@iedr.ie', 'Active', NOW(), NOW(), NOW(),'NO', 'test data generation', 'XDD274-IEDR', 'A', NULL, 'YES', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC017AB-IEDR'));
INSERT INTO AccessHist VALUES (NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC017AB-IEDR'), 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 0, 'NO', 'XDD274-IEDR', NOW(), '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '');
INSERT INTO Access VALUES ('UC017AB-IEDR', 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 0, 'NO', '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '', (SELECT MAX(A.Chng_Id) FROM AccessHist A JOIN NicHandleHist NH  ON A.Nic_Handle_Chng_Id = NH.Chng_Id WHERE NH.Nic_Handle = 'UC017AB-IEDR'));

INSERT INTO NicHandleHist VALUES (NULL, 'UC017SU-IEDR', 'Use Case 017 - Direct - Suspended', (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc017_su@iedr.ie', 'Suspended', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'UC017SU-IEDR', 'B', NULL, 'YES', 'XDD274-IEDR', NOW());
INSERT INTO NicHandle VALUES ('UC017SU-IEDR', 'Use Case 017 - Direct - Suspended', '001', 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc017_su@iedr.ie', 'Suspended', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'UC017SU-IEDR', 'B', NULL, 'YES', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC017SU-IEDR'));
INSERT INTO AccessHist VALUES (NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC017SU-IEDR'), 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 0, 'NO', 'XDD274-IEDR', NOW(), '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '');
INSERT INTO Access VALUES ('UC017SU-IEDR', 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 0, 'NO', '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '', (SELECT MAX(A.Chng_Id) FROM AccessHist A JOIN NicHandleHist NH  ON A.Nic_Handle_Chng_Id = NH.Chng_Id WHERE NH.Nic_Handle = 'UC017SU-IEDR'));

# UC#017: Request Domain Modification - UC017-SC05: Direct requests invalid namserver change
INSERT INTO DomainHist VALUES (NULL, 'uc017-sc05.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XDD274-IEDR', NOW(), 'NO', '25', CURDATE() + Interval 14 Day, CURDATE() + Interval 28 Day, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc017-sc05.ie', 'Test Holder 0001', 3, 2, NULL, 1, CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '25', CURDATE() + Interval 14 Day, CURDATE() + Interval 28 Day, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-sc05.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-sc05.ie'), 'uc017-sc05.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-sc05.ie'), 'uc017-sc05.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-sc05.ie'), 'uc017-sc05.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-sc05.ie'), 'uc017-sc05.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc017-sc05.ie', 'XDD274-IEDR', 'Admin'), ('uc017-sc05.ie', 'XDD274-IEDR', 'Billing'), ('uc017-sc05.ie', 'XDD274-IEDR', 'Creator'), ('uc017-sc05.ie', 'XDD274-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-sc05.ie'), 'uc017-sc05.ie', 'ns1.uc017-sc05.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-sc05.ie'), 'uc017-sc05.ie', 'ns2.uc017-sc05.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-sc05.ie'), 'uc017-sc05.ie', 'ns3.uc017-sc05.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc017-sc05.ie', 'ns1.uc017-sc05.ie', '10.10.121.23', NULL, '1'), ('uc017-sc05.ie', 'ns2.uc017-sc05.ie', '1.3.4.2', NULL, '2'), ('uc017-sc05.ie', 'ns3.uc017-sc05.ie', '10.12.13.14', NULL, '3');

# UC#017: Request Domain Modification - UC017-SC06: Direct requests domain contact change
INSERT INTO DomainHist VALUES (NULL, 'uc017-sc06.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XDD274-IEDR', NOW(), 'NO', '25', CURDATE() + Interval 14 Day, CURDATE() + Interval 28 Day, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc017-sc06.ie', 'Test Holder 0001', 3, 2, NULL, 1, CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '25', CURDATE() + Interval 14 Day, CURDATE() + Interval 28 Day, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-sc06.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-sc06.ie'), 'uc017-sc06.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-sc06.ie'), 'uc017-sc06.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-sc06.ie'), 'uc017-sc06.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-sc06.ie'), 'uc017-sc06.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc017-sc06.ie', 'XDD274-IEDR', 'Admin'), ('uc017-sc06.ie', 'XDD274-IEDR', 'Billing'), ('uc017-sc06.ie', 'XDD274-IEDR', 'Creator'), ('uc017-sc06.ie', 'XDD274-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-sc06.ie'), 'uc017-sc06.ie', 'ns1.uc017-sc06.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-sc06.ie'), 'uc017-sc06.ie', 'ns2.uc017-sc06.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-sc06.ie'), 'uc017-sc06.ie', 'ns3.uc017-sc06.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc017-sc06.ie', 'ns1.uc017-sc06.ie', '10.10.121.23', NULL, '1'), ('uc017-sc06.ie', 'ns2.uc017-sc06.ie', '1.3.4.2', NULL, '2'), ('uc017-sc06.ie', 'ns3.uc017-sc06.ie', '10.12.13.14', NULL, '3');

# UC#017: Request Domain Modification - UC017-SC07: Registrar requests renewal mode change via Console
INSERT INTO DomainHist VALUES (NULL, 'uc017-sc07.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '81', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc017-sc07.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '81', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-sc07.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-sc07.ie'), 'uc017-sc07.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-sc07.ie'), 'uc017-sc07.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-sc07.ie'), 'uc017-sc07.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-sc07.ie'), 'uc017-sc07.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc017-sc07.ie', 'XOE550-IEDR', 'Admin'), ('uc017-sc07.ie', 'XBC189-IEDR', 'Billing'), ('uc017-sc07.ie', 'XBC189-IEDR', 'Creator'), ('uc017-sc07.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-sc07.ie'), 'uc017-sc07.ie', 'ns1.uc017-sc07.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-sc07.ie'), 'uc017-sc07.ie', 'ns2.uc017-sc07.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-sc07.ie'), 'uc017-sc07.ie', 'ns3.uc017-sc07.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc017-sc07.ie', 'ns1.uc017-sc07.ie', '10.10.121.23', NULL, '1'), ('uc017-sc07.ie', 'ns2.uc017-sc07.ie', '1.3.4.2', NULL, '2'), ('uc017-sc07.ie', 'ns3.uc017-sc07.ie', '10.12.13.14', NULL, '3');

# UC#017: Request Domain Modification - UC017-SC09: Registrar requests holder details change via Console
INSERT INTO DomainHist VALUES (NULL, 'uc017-sc09.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '49', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc017-sc09.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '49', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-sc09.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-sc09.ie'), 'uc017-sc09.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-sc09.ie'), 'uc017-sc09.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-sc09.ie'), 'uc017-sc09.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-sc09.ie'), 'uc017-sc09.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc017-sc09.ie', 'XOE550-IEDR', 'Admin'), ('uc017-sc09.ie', 'XBC189-IEDR', 'Billing'), ('uc017-sc09.ie', 'XBC189-IEDR', 'Creator'), ('uc017-sc09.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-sc09.ie'), 'uc017-sc09.ie', 'ns1.uc017-sc09.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-sc09.ie'), 'uc017-sc09.ie', 'ns2.uc017-sc09.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-sc09.ie'), 'uc017-sc09.ie', 'ns3.uc017-sc09.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc017-sc09.ie', 'ns1.uc017-sc09.ie', '10.10.121.23', NULL, '1'), ('uc017-sc09.ie', 'ns2.uc017-sc09.ie', '1.3.4.2', NULL, '2'), ('uc017-sc09.ie', 'ns3.uc017-sc09.ie', '10.12.13.14', NULL, '3');

# UC#017: Request Domain Modification - qa_plan: 1.17.2.14 Direct (Charity) sucessfully changes DNS via DNS section
INSERT INTO DomainHist VALUES (NULL, 'uc017-q117214.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XDD274-IEDR', NOW(), 'NO', '121', CURDATE() + Interval 14 Day, CURDATE() + Interval 28 Day, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc017-q117214.ie', 'Test Holder 0001', 3, 2, NULL, 1, CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '121', CURDATE() + Interval 14 Day, CURDATE() + Interval 28 Day, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-q117214.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-q117214.ie'), 'uc017-q117214.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-q117214.ie'), 'uc017-q117214.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-q117214.ie'), 'uc017-q117214.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-q117214.ie'), 'uc017-q117214.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc017-q117214.ie', 'XDD274-IEDR', 'Admin'), ('uc017-q117214.ie', 'XDD274-IEDR', 'Billing'), ('uc017-q117214.ie', 'XDD274-IEDR', 'Creator'), ('uc017-q117214.ie', 'XDD274-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-q117214.ie'), 'uc017-q117214.ie', 'ns1.uc017-q117214.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-q117214.ie'), 'uc017-q117214.ie', 'ns2.uc017-q117214.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-q117214.ie'), 'uc017-q117214.ie', 'ns3.uc017-q117214.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc017-q117214.ie', 'ns1.uc017-q117214.ie', '10.10.121.23', NULL, '1'), ('uc017-q117214.ie', 'ns2.uc017-q117214.ie', '1.3.4.2', NULL, '2'), ('uc017-q117214.ie', 'ns3.uc017-q117214.ie', '10.12.13.14', NULL, '3');

# UC#017: Request Domain Modification - qa_plan: 1.17.2.12 Direct locked domain attempts to change holder
INSERT INTO DomainHist VALUES (NULL, 'uc017-q117212.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XDD274-IEDR', NOW(), 'NO', '9', CURDATE() + Interval 14 Day, CURDATE() + Interval 28 Day, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, CURDATE() - Interval 9 Month, CURDATE() + Interval 3 Month);
INSERT INTO Domain VALUES ('uc017-q117212.ie', 'Test Holder 0001', 3, 2, NULL, 1, CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '9', CURDATE() + Interval 14 Day, CURDATE() + Interval 28 Day, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, CURDATE() - Interval 9 Month, CURDATE() + Interval 3 Month, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-q117212.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-q117212.ie'), 'uc017-q117212.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-q117212.ie'), 'uc017-q117212.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-q117212.ie'), 'uc017-q117212.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-q117212.ie'), 'uc017-q117212.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc017-q117212.ie', 'XDD274-IEDR', 'Admin'), ('uc017-q117212.ie', 'XDD274-IEDR', 'Billing'), ('uc017-q117212.ie', 'XDD274-IEDR', 'Creator'), ('uc017-q117212.ie', 'XDD274-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-q117212.ie'), 'uc017-q117212.ie', 'ns1.uc017-q117212.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-q117212.ie'), 'uc017-q117212.ie', 'ns2.uc017-q117212.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-q117212.ie'), 'uc017-q117212.ie', 'ns3.uc017-q117212.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc017-q117212.ie', 'ns1.uc017-q117212.ie', '10.10.121.23', NULL, '1'), ('uc017-q117212.ie', 'ns2.uc017-q117212.ie', '1.3.4.2', NULL, '2'), ('uc017-q117212.ie', 'ns3.uc017-q117212.ie', '10.12.13.14', NULL, '3');

# UC#017: Request Domain Modification - qa_plan: 1.17.2.15 Direct XPA status attempts to submit contact change
INSERT INTO DomainHist VALUES (NULL, 'uc017-q117215.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XDD274-IEDR', NOW(), 'NO', '391', CURDATE() + Interval 14 Day, CURDATE() + Interval 28 Day, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc017-q117215.ie', 'Test Holder 0001', 3, 2, NULL, 1, CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '391', CURDATE() + Interval 14 Day, CURDATE() + Interval 28 Day, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-q117215.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-q117215.ie'), 'uc017-q117215.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-q117215.ie'), 'uc017-q117215.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-q117215.ie'), 'uc017-q117215.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-q117215.ie'), 'uc017-q117215.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc017-q117215.ie', 'XDD274-IEDR', 'Admin'), ('uc017-q117215.ie', 'XDD274-IEDR', 'Billing'), ('uc017-q117215.ie', 'XDD274-IEDR', 'Creator'), ('uc017-q117215.ie', 'XDD274-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-q117215.ie'), 'uc017-q117215.ie', 'ns1.uc017-q117215.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-q117215.ie'), 'uc017-q117215.ie', 'ns2.uc017-q117215.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-q117215.ie'), 'uc017-q117215.ie', 'ns3.uc017-q117215.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc017-q117215.ie', 'ns1.uc017-q117215.ie', '10.10.121.23', NULL, '1'), ('uc017-q117215.ie', 'ns2.uc017-q117215.ie', '1.3.4.2', NULL, '2'), ('uc017-q117215.ie', 'ns3.uc017-q117215.ie', '10.12.13.14', NULL, '3');

# UC#017: Request Domain Modification - qa_plan: 1.17.2.2: Registrar attempts to set Charity domain to autorenewal mode
INSERT INTO DomainHist VALUES (NULL, 'uc017-q11722.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '113', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc017-q11722.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '113', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-q11722.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-q11722.ie'), 'uc017-q11722.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-q11722.ie'), 'uc017-q11722.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-q11722.ie'), 'uc017-q11722.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-q11722.ie'), 'uc017-q11722.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc017-q11722.ie', 'XOE550-IEDR', 'Admin'), ('uc017-q11722.ie', 'XBC189-IEDR', 'Billing'), ('uc017-q11722.ie', 'XBC189-IEDR', 'Creator'), ('uc017-q11722.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-q11722.ie'), 'uc017-q11722.ie', 'ns1.uc017-q11722.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-q11722.ie'), 'uc017-q11722.ie', 'ns2.uc017-q11722.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-q11722.ie'), 'uc017-q11722.ie', 'ns3.uc017-q11722.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc017-q11722.ie', 'ns1.uc017-q11722.ie', '10.10.121.23', NULL, '1'), ('uc017-q11722.ie', 'ns2.uc017-q11722.ie', '1.3.4.2', NULL, '2'), ('uc017-q11722.ie', 'ns3.uc017-q11722.ie', '10.12.13.14', NULL, '3');

# UC#017: Request Domain Modification - qa_plan: 1.17.2.8: Registrar submits holder and contact change for IS domain
INSERT INTO DomainHist VALUES (NULL, 'uc017-q11728.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '19', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc017-q11728.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '19', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-q11728.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-q11728.ie'), 'uc017-q11728.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-q11728.ie'), 'uc017-q11728.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-q11728.ie'), 'uc017-q11728.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-q11728.ie'), 'uc017-q11728.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc017-q11728.ie', 'XOE550-IEDR', 'Admin'), ('uc017-q11728.ie', 'XBC189-IEDR', 'Billing'), ('uc017-q11728.ie', 'XBC189-IEDR', 'Creator'), ('uc017-q11728.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-q11728.ie'), 'uc017-q11728.ie', 'ns1.uc017-q11728.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-q11728.ie'), 'uc017-q11728.ie', 'ns2.uc017-q11728.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-q11728.ie'), 'uc017-q11728.ie', 'ns3.uc017-q11728.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc017-q11728.ie', 'ns1.uc017-q11728.ie', '10.10.121.23', NULL, '1'), ('uc017-q11728.ie', 'ns2.uc017-q11728.ie', '1.3.4.2', NULL, '2'), ('uc017-q11728.ie', 'ns3.uc017-q11728.ie', '10.12.13.14', NULL, '3');

# UC#017: Request Domain Modification - qa_plan: 1.17.2.6 Registrar attempts to change contacts on locked domain
INSERT INTO DomainHist VALUES (NULL, 'uc017-q11726.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '1', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, CURDATE() - Interval 9 Month, CURDATE() + Interval 3 Month);
INSERT INTO Domain VALUES ('uc017-q11726.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '1', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, CURDATE() - Interval 9 Month, CURDATE() + Interval 3 Month, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-q11726.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-q11726.ie'), 'uc017-q11726.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-q11726.ie'), 'uc017-q11726.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-q11726.ie'), 'uc017-q11726.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-q11726.ie'), 'uc017-q11726.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc017-q11726.ie', 'XOE550-IEDR', 'Admin'), ('uc017-q11726.ie', 'XBC189-IEDR', 'Billing'), ('uc017-q11726.ie', 'XBC189-IEDR', 'Creator'), ('uc017-q11726.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-q11726.ie'), 'uc017-q11726.ie', 'ns1.uc017-q11726.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-q11726.ie'), 'uc017-q11726.ie', 'ns2.uc017-q11726.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-q11726.ie'), 'uc017-q11726.ie', 'ns3.uc017-q11726.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc017-q11726.ie', 'ns1.uc017-q11726.ie', '10.10.121.23', NULL, '1'), ('uc017-q11726.ie', 'ns2.uc017-q11726.ie', '1.3.4.2', NULL, '2'), ('uc017-q11726.ie', 'ns3.uc017-q11726.ie', '10.12.13.14', NULL, '3');

# UC#017: Request Domain Modification - qa_plan: 1.17.2.14 (Alt) Direct sucessfully changes DNS from "View Domain"
INSERT INTO DomainHist VALUES (NULL, 'uc017-q117214alt.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XDD274-IEDR', NOW(), 'NO', '25', CURDATE() + Interval 14 Day, CURDATE() + Interval 28 Day, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc017-q117214alt.ie', 'Test Holder 0001', 3, 2, NULL, 1, CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '25', CURDATE() + Interval 14 Day, CURDATE() + Interval 28 Day, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-q117214alt.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-q117214alt.ie'), 'uc017-q117214alt.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-q117214alt.ie'), 'uc017-q117214alt.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-q117214alt.ie'), 'uc017-q117214alt.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-q117214alt.ie'), 'uc017-q117214alt.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc017-q117214alt.ie', 'XDD274-IEDR', 'Admin'), ('uc017-q117214alt.ie', 'XDD274-IEDR', 'Billing'), ('uc017-q117214alt.ie', 'XDD274-IEDR', 'Creator'), ('uc017-q117214alt.ie', 'XDD274-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-q117214alt.ie'), 'uc017-q117214alt.ie', 'ns1.uc017-q117214alt.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-q117214alt.ie'), 'uc017-q117214alt.ie', 'ns2.uc017-q117214alt.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-q117214alt.ie'), 'uc017-q117214alt.ie', 'ns3.uc017-q117214alt.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc017-q117214alt.ie', 'ns1.uc017-q117214alt.ie', '10.10.121.23', NULL, '1'), ('uc017-q117214alt.ie', 'ns2.uc017-q117214alt.ie', '1.3.4.2', NULL, '2'), ('uc017-q117214alt.ie', 'ns3.uc017-q117214alt.ie', '10.12.13.14', NULL, '3');

# UC#017: Request Domain Modification - UC017-SC02 (Alt): Registrar successfully changes nameserver configuration from "View Domain"
INSERT INTO DomainHist VALUES (NULL, 'uc017-sc02alt1.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc017-sc02alt1.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-sc02alt1.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-sc02alt1.ie'), 'uc017-sc02alt1.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-sc02alt1.ie'), 'uc017-sc02alt1.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-sc02alt1.ie'), 'uc017-sc02alt1.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-sc02alt1.ie'), 'uc017-sc02alt1.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc017-sc02alt1.ie', 'XOE550-IEDR', 'Admin'), ('uc017-sc02alt1.ie', 'XBC189-IEDR', 'Billing'), ('uc017-sc02alt1.ie', 'XBC189-IEDR', 'Creator'), ('uc017-sc02alt1.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-sc02alt1.ie'), 'uc017-sc02alt1.ie', 'ns1.uc017-sc02alt1.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-sc02alt1.ie'), 'uc017-sc02alt1.ie', 'ns2.uc017-sc02alt1.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-sc02alt1.ie'), 'uc017-sc02alt1.ie', 'ns3.uc017-sc02alt1.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc017-sc02alt1.ie', 'ns1.uc017-sc02alt1.ie', '10.10.121.23', NULL, '1'), ('uc017-sc02alt1.ie', 'ns2.uc017-sc02alt1.ie', '1.3.4.2', NULL, '2'), ('uc017-sc02alt1.ie', 'ns3.uc017-sc02alt1.ie', '10.12.13.14', NULL, '3');

# UC#017: Request Domain Modification - UC017-SC02 (Alt): Registrar successfully changes nameserver configuration from "DNS" section
INSERT INTO DomainHist VALUES (NULL, 'uc017-sc02alt2.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc017-sc02alt2.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-sc02alt2.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-sc02alt2.ie'), 'uc017-sc02alt2.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-sc02alt2.ie'), 'uc017-sc02alt2.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-sc02alt2.ie'), 'uc017-sc02alt2.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-sc02alt2.ie'), 'uc017-sc02alt2.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc017-sc02alt2.ie', 'XOE550-IEDR', 'Admin'), ('uc017-sc02alt2.ie', 'XBC189-IEDR', 'Billing'), ('uc017-sc02alt2.ie', 'XBC189-IEDR', 'Creator'), ('uc017-sc02alt2.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-sc02alt2.ie'), 'uc017-sc02alt2.ie', 'ns1.uc017-sc02alt2.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-sc02alt2.ie'), 'uc017-sc02alt2.ie', 'ns2.uc017-sc02alt2.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-sc02alt2.ie'), 'uc017-sc02alt2.ie', 'ns3.uc017-sc02alt2.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc017-sc02alt2.ie', 'ns1.uc017-sc02alt2.ie', '10.10.121.23', NULL, '1'), ('uc017-sc02alt2.ie', 'ns2.uc017-sc02alt2.ie', '1.3.4.2', NULL, '2'), ('uc017-sc02alt2.ie', 'ns3.uc017-sc02alt2.ie', '10.12.13.14', NULL, '3');

# UC#017: Request Domain Modification - UC001-NS: Submit Domain modification when domain in Pending Payment state
INSERT INTO DomainHist VALUES (NULL, 'uc017-uc001ns.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XDD274-IEDR', NOW(), 'NO', '26', CURDATE() + Interval 14 Day, CURDATE() + Interval 28 Day, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc017-uc001ns.ie', 'Test Holder 0001', 3, 2, NULL, 1, CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '26', CURDATE() + Interval 14 Day, CURDATE() + Interval 28 Day, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-uc001ns.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-uc001ns.ie'), 'uc017-uc001ns.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-uc001ns.ie'), 'uc017-uc001ns.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-uc001ns.ie'), 'uc017-uc001ns.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-uc001ns.ie'), 'uc017-uc001ns.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc017-uc001ns.ie', 'XDD274-IEDR', 'Admin'), ('uc017-uc001ns.ie', 'XDD274-IEDR', 'Billing'), ('uc017-uc001ns.ie', 'XDD274-IEDR', 'Creator'), ('uc017-uc001ns.ie', 'XDD274-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-uc001ns.ie'), 'uc017-uc001ns.ie', 'ns1.uc017-uc001ns.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-uc001ns.ie'), 'uc017-uc001ns.ie', 'ns2.uc017-uc001ns.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-uc001ns.ie'), 'uc017-uc001ns.ie', 'ns3.uc017-uc001ns.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc017-uc001ns.ie', 'ns1.uc017-uc001ns.ie', '10.10.121.23', NULL, '1'), ('uc017-uc001ns.ie', 'ns2.uc017-uc001ns.ie', '1.3.4.2', NULL, '2'), ('uc017-uc001ns.ie', 'ns3.uc017-uc001ns.ie', '10.12.13.14', NULL, '3');

# UC#017: Request Domain Modification - UC017-NS: Ajax verification
INSERT INTO DomainHist VALUES (NULL, 'uc017-nosc01.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc017-nosc01.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-nosc01.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-nosc01.ie'), 'uc017-nosc01.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-nosc01.ie'), 'uc017-nosc01.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-nosc01.ie'), 'uc017-nosc01.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-nosc01.ie'), 'uc017-nosc01.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc017-nosc01.ie', 'XOE550-IEDR', 'Admin'), ('uc017-nosc01.ie', 'XBC189-IEDR', 'Billing'), ('uc017-nosc01.ie', 'XBC189-IEDR', 'Creator'), ('uc017-nosc01.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-nosc01.ie'), 'uc017-nosc01.ie', 'ns1.uc017-nosc01.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-nosc01.ie'), 'uc017-nosc01.ie', 'ns2.uc017-nosc01.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc017-nosc01.ie'), 'uc017-nosc01.ie', 'ns3.uc017-nosc01.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc017-nosc01.ie', 'ns1.uc017-nosc01.ie', '10.10.121.23', NULL, '1'), ('uc017-nosc01.ie', 'ns2.uc017-nosc01.ie', '1.3.4.2', NULL, '2'), ('uc017-nosc01.ie', 'ns3.uc017-nosc01.ie', '10.12.13.14', NULL, '3');

