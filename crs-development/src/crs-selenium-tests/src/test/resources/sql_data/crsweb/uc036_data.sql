INSERT INTO NicHandleHist VALUES (NULL, 'UC036A-IEDR', 'Use Case 036 - Irish admin', (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc036_a@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'UC036A-IEDR', 'B', NULL, 'NO', 'XBC189-IEDR', NOW());
INSERT INTO NicHandle VALUES ('UC036A-IEDR', 'Use Case 036 - Irish admin', 1, 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc036_a@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'UC036A-IEDR', 'B', NULL, 'NO', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC036A-IEDR'));
INSERT INTO AccessHist VALUES (NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC036A-IEDR'), 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 0, 'NO', 'XBC189-IEDR', NOW(), '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '');
INSERT INTO Access VALUES ('UC036A-IEDR', 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 0, 'NO', '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '', (SELECT MAX(A.Chng_Id) FROM AccessHist A JOIN NicHandleHist NH  ON A.Nic_Handle_Chng_Id = NH.Chng_Id WHERE NH.Nic_Handle = 'UC036A-IEDR'));

INSERT INTO NicHandleHist VALUES (NULL, 'UC036B-IEDR', 'Use Case 036 - non-Irish admin', (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), 'Registrar co Limited', '1 The Road, Some Street', 162, 265, 'uc036_b@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'UC036B-IEDR', 'B', NULL, 'NO', 'XBC189-IEDR', NOW());
INSERT INTO NicHandle VALUES ('UC036B-IEDR', 'Use Case 036 - non-Irish admin', 1, 'Registrar co Limited', '1 The Road, Some Street', 162, 265, 'uc036_b@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'UC036B-IEDR', 'B', NULL, 'NO', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC036B-IEDR'));
INSERT INTO AccessHist VALUES (NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC036B-IEDR'), 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 0, 'NO', 'XBC189-IEDR', NOW(), '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '');
INSERT INTO Access VALUES ('UC036B-IEDR', 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 0, 'NO', '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '', (SELECT MAX(A.Chng_Id) FROM AccessHist A JOIN NicHandleHist NH  ON A.Nic_Handle_Chng_Id = NH.Chng_Id WHERE NH.Nic_Handle = 'UC036B-IEDR'));

INSERT INTO DomainHist VALUES (NULL, 'uc036-sc06a.ie', 'Test Holder 0001', 2, 3, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '2', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, CURDATE() - Interval 9 Month, CURDATE() + Interval 3 Month);
INSERT INTO Domain VALUES ('uc036-sc06a.ie', 'Test Holder 0001', 2, 3, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', '2', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, CURDATE() - Interval 9 Month, CURDATE() + Interval 3 Month, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06a.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06a.ie'), 'uc036-sc06a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06a.ie'), 'uc036-sc06a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06a.ie'), 'uc036-sc06a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06a.ie'), 'uc036-sc06a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc036-sc06a.ie', 'XBC189-IEDR', 'Admin'), ('uc036-sc06a.ie', 'XBC189-IEDR', 'Billing'), ('uc036-sc06a.ie', 'XBC189-IEDR', 'Creator'), ('uc036-sc06a.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06a.ie'), 'uc036-sc06a.ie', 'ns1.uc036-sc06a.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06a.ie'), 'uc036-sc06a.ie', 'ns2.uc036-sc06a.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06a.ie'), 'uc036-sc06a.ie', 'ns3.uc036-sc06a.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc036-sc06a.ie', 'ns1.uc036-sc06a.ie', '10.10.121.23', NULL, '1'), ('uc036-sc06a.ie', 'ns2.uc036-sc06a.ie', '1.3.4.2', NULL, '2'), ('uc036-sc06a.ie', 'ns3.uc036-sc06a.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc036-sc06b.ie', 'Test Holder 0001', 2, 3, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '18', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc036-sc06b.ie', 'Test Holder 0001', 2, 3, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', '18', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06b.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06b.ie'), 'uc036-sc06b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06b.ie'), 'uc036-sc06b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06b.ie'), 'uc036-sc06b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06b.ie'), 'uc036-sc06b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc036-sc06b.ie', 'XBC189-IEDR', 'Admin'), ('uc036-sc06b.ie', 'XBC189-IEDR', 'Billing'), ('uc036-sc06b.ie', 'XBC189-IEDR', 'Creator'), ('uc036-sc06b.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06b.ie'), 'uc036-sc06b.ie', 'ns1.uc036-sc06b.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06b.ie'), 'uc036-sc06b.ie', 'ns2.uc036-sc06b.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06b.ie'), 'uc036-sc06b.ie', 'ns3.uc036-sc06b.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc036-sc06b.ie', 'ns1.uc036-sc06b.ie', '10.10.121.23', NULL, '1'), ('uc036-sc06b.ie', 'ns2.uc036-sc06b.ie', '1.3.4.2', NULL, '2'), ('uc036-sc06b.ie', 'ns3.uc036-sc06b.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc036-sc06c.ie', 'Test Holder 0001', 2, 3, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '66', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, CURDATE() - Interval 9 Month, CURDATE() + Interval 3 Month);
INSERT INTO Domain VALUES ('uc036-sc06c.ie', 'Test Holder 0001', 2, 3, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', '66', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, CURDATE() - Interval 9 Month, CURDATE() + Interval 3 Month, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06c.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06c.ie'), 'uc036-sc06c.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06c.ie'), 'uc036-sc06c.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06c.ie'), 'uc036-sc06c.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06c.ie'), 'uc036-sc06c.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc036-sc06c.ie', 'XBC189-IEDR', 'Admin'), ('uc036-sc06c.ie', 'XBC189-IEDR', 'Billing'), ('uc036-sc06c.ie', 'XBC189-IEDR', 'Creator'), ('uc036-sc06c.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06c.ie'), 'uc036-sc06c.ie', 'ns1.uc036-sc06c.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06c.ie'), 'uc036-sc06c.ie', 'ns2.uc036-sc06c.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06c.ie'), 'uc036-sc06c.ie', 'ns3.uc036-sc06c.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc036-sc06c.ie', 'ns1.uc036-sc06c.ie', '10.10.121.23', NULL, '1'), ('uc036-sc06c.ie', 'ns2.uc036-sc06c.ie', '1.3.4.2', NULL, '2'), ('uc036-sc06c.ie', 'ns3.uc036-sc06c.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc036-sc06d.ie', 'Test Holder 0001', 2, 3, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '82', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc036-sc06d.ie', 'Test Holder 0001', 2, 3, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', '82', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06d.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06d.ie'), 'uc036-sc06d.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06d.ie'), 'uc036-sc06d.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06d.ie'), 'uc036-sc06d.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06d.ie'), 'uc036-sc06d.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc036-sc06d.ie', 'XBC189-IEDR', 'Admin'), ('uc036-sc06d.ie', 'XBC189-IEDR', 'Billing'), ('uc036-sc06d.ie', 'XBC189-IEDR', 'Creator'), ('uc036-sc06d.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06d.ie'), 'uc036-sc06d.ie', 'ns1.uc036-sc06d.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06d.ie'), 'uc036-sc06d.ie', 'ns2.uc036-sc06d.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06d.ie'), 'uc036-sc06d.ie', 'ns3.uc036-sc06d.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc036-sc06d.ie', 'ns1.uc036-sc06d.ie', '10.10.121.23', NULL, '1'), ('uc036-sc06d.ie', 'ns2.uc036-sc06d.ie', '1.3.4.2', NULL, '2'), ('uc036-sc06d.ie', 'ns3.uc036-sc06d.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc036-sc06g.ie', 'Test Holder 0001', 2, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '105', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, CURDATE() - Interval 9 Month, CURDATE() + Interval 3 Month);
INSERT INTO Domain VALUES ('uc036-sc06g.ie', 'Test Holder 0001', 2, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', '105', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, CURDATE() - Interval 9 Month, CURDATE() + Interval 3 Month, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06g.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06g.ie'), 'uc036-sc06g.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06g.ie'), 'uc036-sc06g.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06g.ie'), 'uc036-sc06g.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06g.ie'), 'uc036-sc06g.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc036-sc06g.ie', 'XBC189-IEDR', 'Admin'), ('uc036-sc06g.ie', 'XBC189-IEDR', 'Billing'), ('uc036-sc06g.ie', 'XBC189-IEDR', 'Creator'), ('uc036-sc06g.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06g.ie'), 'uc036-sc06g.ie', 'ns1.uc036-sc06g.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06g.ie'), 'uc036-sc06g.ie', 'ns2.uc036-sc06g.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06g.ie'), 'uc036-sc06g.ie', 'ns3.uc036-sc06g.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc036-sc06g.ie', 'ns1.uc036-sc06g.ie', '10.10.121.23', NULL, '1'), ('uc036-sc06g.ie', 'ns2.uc036-sc06g.ie', '1.3.4.2', NULL, '2'), ('uc036-sc06g.ie', 'ns3.uc036-sc06g.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc036-sc06i.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '113', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc036-sc06i.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', '113', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06i.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06i.ie'), 'uc036-sc06i.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06i.ie'), 'uc036-sc06i.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06i.ie'), 'uc036-sc06i.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06i.ie'), 'uc036-sc06i.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc036-sc06i.ie', 'XBC189-IEDR', 'Admin'), ('uc036-sc06i.ie', 'XBC189-IEDR', 'Billing'), ('uc036-sc06i.ie', 'XBC189-IEDR', 'Creator'), ('uc036-sc06i.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06i.ie'), 'uc036-sc06i.ie', 'ns1.uc036-sc06i.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06i.ie'), 'uc036-sc06i.ie', 'ns2.uc036-sc06i.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06i.ie'), 'uc036-sc06i.ie', 'ns3.uc036-sc06i.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc036-sc06i.ie', 'ns1.uc036-sc06i.ie', '10.10.121.23', NULL, '1'), ('uc036-sc06i.ie', 'ns2.uc036-sc06i.ie', '1.3.4.2', NULL, '2'), ('uc036-sc06i.ie', 'ns3.uc036-sc06i.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc036-sc06j.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '116', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc036-sc06j.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', '116', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06j.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06j.ie'), 'uc036-sc06j.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06j.ie'), 'uc036-sc06j.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06j.ie'), 'uc036-sc06j.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06j.ie'), 'uc036-sc06j.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc036-sc06j.ie', 'XBC189-IEDR', 'Admin'), ('uc036-sc06j.ie', 'XBC189-IEDR', 'Billing'), ('uc036-sc06j.ie', 'XBC189-IEDR', 'Creator'), ('uc036-sc06j.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06j.ie'), 'uc036-sc06j.ie', 'ns1.uc036-sc06j.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06j.ie'), 'uc036-sc06j.ie', 'ns2.uc036-sc06j.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06j.ie'), 'uc036-sc06j.ie', 'ns3.uc036-sc06j.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc036-sc06j.ie', 'ns1.uc036-sc06j.ie', '10.10.121.23', NULL, '1'), ('uc036-sc06j.ie', 'ns2.uc036-sc06j.ie', '1.3.4.2', NULL, '2'), ('uc036-sc06j.ie', 'ns3.uc036-sc06j.ie', '10.12.13.14', NULL, '3');

# Deleted domains
INSERT INTO DomainHist VALUES (NULL, 'uc036-sc06k.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark Hist', 'CIARA-IEDR', CURDATE(), 'NO', '387', NULL, CURDATE(), NULL, 'authcode', CURDATE(), 0, NULL, NULL);
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06k.ie'), 'uc036-sc06k.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06k.ie'), 'uc036-sc06k.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06k.ie'), 'uc036-sc06k.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06k.ie'), 'uc036-sc06k.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06k.ie'), 'uc036-sc06k.ie', 'ns1.uc036-sc06k.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06k.ie'), 'uc036-sc06k.ie', 'ns2.uc036-sc06k.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06k.ie'), 'uc036-sc06k.ie', 'ns3.uc036-sc06k.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc036-sc06l.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark Hist', 'CIARA-IEDR', CURDATE(), 'NO', '387', NULL, CURDATE(), NULL, 'authcode', CURDATE(), 0, NULL, NULL);
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06l.ie'), 'uc036-sc06l.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06l.ie'), 'uc036-sc06l.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06l.ie'), 'uc036-sc06l.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06l.ie'), 'uc036-sc06l.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06l.ie'), 'uc036-sc06l.ie', 'ns1.uc036-sc06l.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06l.ie'), 'uc036-sc06l.ie', 'ns2.uc036-sc06l.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06l.ie'), 'uc036-sc06l.ie', 'ns3.uc036-sc06l.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc036-sc06m.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark Hist', 'CIARA-IEDR', CURDATE(), 'NO', '387', NULL, CURDATE(), NULL, 'authcode', CURDATE(), 0, NULL, NULL);
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06m.ie'), 'uc036-sc06m.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06m.ie'), 'uc036-sc06m.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06m.ie'), 'uc036-sc06m.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06m.ie'), 'uc036-sc06m.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06m.ie'), 'uc036-sc06m.ie', 'ns1.uc036-sc06m.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06m.ie'), 'uc036-sc06m.ie', 'ns2.uc036-sc06m.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc06m.ie'), 'uc036-sc06m.ie', 'ns3.uc036-sc06m.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc036-sc13a.ie', 'Test Holder 0001', 2, 3, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '65', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, CURDATE() - Interval 9 Month, CURDATE() + Interval 3 Month);
INSERT INTO Domain VALUES ('uc036-sc13a.ie', 'Test Holder 0001', 2, 3, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', '65', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, CURDATE() - Interval 9 Month, CURDATE() + Interval 3 Month, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc13a.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc13a.ie'), 'uc036-sc13a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc13a.ie'), 'uc036-sc13a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc13a.ie'), 'uc036-sc13a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc13a.ie'), 'uc036-sc13a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc036-sc13a.ie', 'XBC189-IEDR', 'Admin'), ('uc036-sc13a.ie', 'XBC189-IEDR', 'Billing'), ('uc036-sc13a.ie', 'XBC189-IEDR', 'Creator'), ('uc036-sc13a.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc13a.ie'), 'uc036-sc13a.ie', 'ns1.uc036-sc13a.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc13a.ie'), 'uc036-sc13a.ie', 'ns2.uc036-sc13a.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc13a.ie'), 'uc036-sc13a.ie', 'ns3.uc036-sc13a.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc036-sc13a.ie', 'ns1.uc036-sc13a.ie', '10.10.121.23', NULL, '1'), ('uc036-sc13a.ie', 'ns2.uc036-sc13a.ie', '1.3.4.2', NULL, '2'), ('uc036-sc13a.ie', 'ns3.uc036-sc13a.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc036-sc13b.ie', 'Test Holder 0001', 2, 3, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '66', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, CURDATE() - Interval 9 Month, CURDATE() + Interval 3 Month);
INSERT INTO Domain VALUES ('uc036-sc13b.ie', 'Test Holder 0001', 2, 3, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', '66', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, CURDATE() - Interval 9 Month, CURDATE() + Interval 3 Month, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc13b.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc13b.ie'), 'uc036-sc13b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc13b.ie'), 'uc036-sc13b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc13b.ie'), 'uc036-sc13b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc13b.ie'), 'uc036-sc13b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc036-sc13b.ie', 'XBC189-IEDR', 'Admin'), ('uc036-sc13b.ie', 'XBC189-IEDR', 'Billing'), ('uc036-sc13b.ie', 'XBC189-IEDR', 'Creator'), ('uc036-sc13b.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc13b.ie'), 'uc036-sc13b.ie', 'ns1.uc036-sc13b.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc13b.ie'), 'uc036-sc13b.ie', 'ns2.uc036-sc13b.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc13b.ie'), 'uc036-sc13b.ie', 'ns3.uc036-sc13b.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc036-sc13b.ie', 'ns1.uc036-sc13b.ie', '10.10.121.23', NULL, '1'), ('uc036-sc13b.ie', 'ns2.uc036-sc13b.ie', '1.3.4.2', NULL, '2'), ('uc036-sc13b.ie', 'ns3.uc036-sc13b.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc036-sc13c.ie', 'Test Holder 0001', 2, 3, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '67', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, CURDATE() - Interval 9 Month, CURDATE() + Interval 3 Month);
INSERT INTO Domain VALUES ('uc036-sc13c.ie', 'Test Holder 0001', 2, 3, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', '67', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, CURDATE() - Interval 9 Month, CURDATE() + Interval 3 Month, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc13c.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc13c.ie'), 'uc036-sc13c.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc13c.ie'), 'uc036-sc13c.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc13c.ie'), 'uc036-sc13c.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc13c.ie'), 'uc036-sc13c.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc036-sc13c.ie', 'XBC189-IEDR', 'Admin'), ('uc036-sc13c.ie', 'XBC189-IEDR', 'Billing'), ('uc036-sc13c.ie', 'XBC189-IEDR', 'Creator'), ('uc036-sc13c.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc13c.ie'), 'uc036-sc13c.ie', 'ns1.uc036-sc13c.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc13c.ie'), 'uc036-sc13c.ie', 'ns2.uc036-sc13c.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc13c.ie'), 'uc036-sc13c.ie', 'ns3.uc036-sc13c.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc036-sc13c.ie', 'ns1.uc036-sc13c.ie', '10.10.121.23', NULL, '1'), ('uc036-sc13c.ie', 'ns2.uc036-sc13c.ie', '1.3.4.2', NULL, '2'), ('uc036-sc13c.ie', 'ns3.uc036-sc13c.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc036-sc13f.ie', 'Test Holder 0001', 2, 3, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 24 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '81', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc036-sc13f.ie', 'Test Holder 0001', 2, 3, NULL, 600, CURDATE() - Interval 24 Month, CURDATE(), NOW(), 'Test Remark', 'NO', '81', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc13f.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc13f.ie'), 'uc036-sc13f.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc13f.ie'), 'uc036-sc13f.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc13f.ie'), 'uc036-sc13f.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc13f.ie'), 'uc036-sc13f.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc036-sc13f.ie', 'XBC189-IEDR', 'Admin'), ('uc036-sc13f.ie', 'XBC189-IEDR', 'Billing'), ('uc036-sc13f.ie', 'XBC189-IEDR', 'Creator'), ('uc036-sc13f.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc13f.ie'), 'uc036-sc13f.ie', 'ns1.uc036-sc13f.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc13f.ie'), 'uc036-sc13f.ie', 'ns2.uc036-sc13f.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc13f.ie'), 'uc036-sc13f.ie', 'ns3.uc036-sc13f.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc036-sc13f.ie', 'ns1.uc036-sc13f.ie', '10.10.121.23', NULL, '1'), ('uc036-sc13f.ie', 'ns2.uc036-sc13f.ie', '1.3.4.2', NULL, '2'), ('uc036-sc13f.ie', 'ns3.uc036-sc13f.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc036-sc13g.ie', 'Test Holder 0001', 2, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '82', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc036-sc13g.ie', 'Test Holder 0001', 2, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', '82', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc13g.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc13g.ie'), 'uc036-sc13g.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc13g.ie'), 'uc036-sc13g.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc13g.ie'), 'uc036-sc13g.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc13g.ie'), 'uc036-sc13g.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc036-sc13g.ie', 'XBC189-IEDR', 'Admin'), ('uc036-sc13g.ie', 'XBC189-IEDR', 'Billing'), ('uc036-sc13g.ie', 'XBC189-IEDR', 'Creator'), ('uc036-sc13g.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc13g.ie'), 'uc036-sc13g.ie', 'ns1.uc036-sc13g.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc13g.ie'), 'uc036-sc13g.ie', 'ns2.uc036-sc13g.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc13g.ie'), 'uc036-sc13g.ie', 'ns3.uc036-sc13g.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc036-sc13g.ie', 'ns1.uc036-sc13g.ie', '10.10.121.23', NULL, '1'), ('uc036-sc13g.ie', 'ns2.uc036-sc13g.ie', '1.3.4.2', NULL, '2'), ('uc036-sc13g.ie', 'ns3.uc036-sc13g.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc036-sc13h.ie', 'Test Holder 0001', 2, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '83', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc036-sc13h.ie', 'Test Holder 0001', 2, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', '83', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc13h.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc13h.ie'), 'uc036-sc13h.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc13h.ie'), 'uc036-sc13h.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc13h.ie'), 'uc036-sc13h.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc13h.ie'), 'uc036-sc13h.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc036-sc13h.ie', 'XBC189-IEDR', 'Admin'), ('uc036-sc13h.ie', 'XBC189-IEDR', 'Billing'), ('uc036-sc13h.ie', 'XBC189-IEDR', 'Creator'), ('uc036-sc13h.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc13h.ie'), 'uc036-sc13h.ie', 'ns1.uc036-sc13h.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc13h.ie'), 'uc036-sc13h.ie', 'ns2.uc036-sc13h.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc036-sc13h.ie'), 'uc036-sc13h.ie', 'ns3.uc036-sc13h.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc036-sc13h.ie', 'ns1.uc036-sc13h.ie', '10.10.121.23', NULL, '1'), ('uc036-sc13h.ie', 'ns2.uc036-sc13h.ie', '1.3.4.2', NULL, '2'), ('uc036-sc13h.ie', 'ns3.uc036-sc13h.ie', '10.12.13.14', NULL, '3');

# Invoices
DROP PROCEDURE IF EXISTS insertbigdata;
DELIMITER $$
CREATE PROCEDURE insertbigdata()
BEGIN
    DECLARE x INT;
    DECLARE invoice VARCHAR(66);
    SET x = 0;
    WHILE x < 50 DO
        SET invoice = CONCAT('SELENIUM-INVOICE-', x);
        INSERT INTO Invoice VALUES (NULL, invoice, 'Test Registrar', 600, '1 The Road, Some Street', NULL, NULL, 'XBC189-IEDR', 'YES', 121, 14, 9, NOW(), 'd41d8cd98f00b204e9800998ecf8427e', 1080.00, 900.00, 180.00);
        SET x = x + 1;
    END WHILE;
END;$$
DELIMITER ;
CALL insertbigdata();

