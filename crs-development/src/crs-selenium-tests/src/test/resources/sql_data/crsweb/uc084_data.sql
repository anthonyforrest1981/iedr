INSERT INTO DomainHist VALUES (NULL, 'uc084-sc04-a.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc084-sc04-a.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc04-a.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc04-a.ie'), 'uc084-sc04-a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc04-a.ie'), 'uc084-sc04-a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc04-a.ie'), 'uc084-sc04-a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc04-a.ie'), 'uc084-sc04-a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc084-sc04-a.ie', 'XBC189-IEDR', 'Admin'), ('uc084-sc04-a.ie', 'XBC189-IEDR', 'Billing'), ('uc084-sc04-a.ie', 'XBC189-IEDR', 'Creator'), ('uc084-sc04-a.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc04-a.ie'), 'uc084-sc04-a.ie', 'ns1.uc084-sc04-a.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc04-a.ie'), 'uc084-sc04-a.ie', 'ns2.uc084-sc04-a.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc04-a.ie'), 'uc084-sc04-a.ie', 'ns3.uc084-sc04-a.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc084-sc04-a.ie', 'ns1.uc084-sc04-a.ie', '10.10.121.23', NULL, '1'), ('uc084-sc04-a.ie', 'ns2.uc084-sc04-a.ie', '1.3.4.2', NULL, '2'), ('uc084-sc04-a.ie', 'ns3.uc084-sc04-a.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc084-sc04-b.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc084-sc04-b.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc04-b.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc04-b.ie'), 'uc084-sc04-b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc04-b.ie'), 'uc084-sc04-b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc04-b.ie'), 'uc084-sc04-b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc04-b.ie'), 'uc084-sc04-b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc084-sc04-b.ie', 'XBC189-IEDR', 'Admin'), ('uc084-sc04-b.ie', 'XBC189-IEDR', 'Billing'), ('uc084-sc04-b.ie', 'XBC189-IEDR', 'Creator'), ('uc084-sc04-b.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc04-b.ie'), 'uc084-sc04-b.ie', 'ns1.uc084-sc04-b.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc04-b.ie'), 'uc084-sc04-b.ie', 'ns2.uc084-sc04-b.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc04-b.ie'), 'uc084-sc04-b.ie', 'ns3.uc084-sc04-b.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc084-sc04-b.ie', 'ns1.uc084-sc04-b.ie', '10.10.121.23', NULL, '1'), ('uc084-sc04-b.ie', 'ns2.uc084-sc04-b.ie', '1.3.4.2', NULL, '2'), ('uc084-sc04-b.ie', 'ns3.uc084-sc04-b.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc084-sc06-a.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc084-sc06-a.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc06-a.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc06-a.ie'), 'uc084-sc06-a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc06-a.ie'), 'uc084-sc06-a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc06-a.ie'), 'uc084-sc06-a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc06-a.ie'), 'uc084-sc06-a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc084-sc06-a.ie', 'XOE550-IEDR', 'Admin'), ('uc084-sc06-a.ie', 'XBC189-IEDR', 'Billing'), ('uc084-sc06-a.ie', 'XBC189-IEDR', 'Creator'), ('uc084-sc06-a.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc06-a.ie'), 'uc084-sc06-a.ie', 'ns1.uc084-sc06-a.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc06-a.ie'), 'uc084-sc06-a.ie', 'ns2.uc084-sc06-a.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc06-a.ie'), 'uc084-sc06-a.ie', 'ns3.uc084-sc06-a.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc084-sc06-a.ie', 'ns1.uc084-sc06-a.ie', '10.10.121.23', NULL, '1'), ('uc084-sc06-a.ie', 'ns2.uc084-sc06-a.ie', '1.3.4.2', NULL, '2'), ('uc084-sc06-a.ie', 'ns3.uc084-sc06-a.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc084-sc06-b.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc084-sc06-b.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc06-b.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc06-b.ie'), 'uc084-sc06-b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc06-b.ie'), 'uc084-sc06-b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc06-b.ie'), 'uc084-sc06-b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc06-b.ie'), 'uc084-sc06-b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc084-sc06-b.ie', 'XOE550-IEDR', 'Admin'), ('uc084-sc06-b.ie', 'XBC189-IEDR', 'Billing'), ('uc084-sc06-b.ie', 'XBC189-IEDR', 'Creator'), ('uc084-sc06-b.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc06-b.ie'), 'uc084-sc06-b.ie', 'ns1.uc084-sc06-b.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc06-b.ie'), 'uc084-sc06-b.ie', 'ns2.uc084-sc06-b.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc06-b.ie'), 'uc084-sc06-b.ie', 'ns3.uc084-sc06-b.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc084-sc06-b.ie', 'ns1.uc084-sc06-b.ie', '10.10.121.23', NULL, '1'), ('uc084-sc06-b.ie', 'ns2.uc084-sc06-b.ie', '1.3.4.2', NULL, '2'), ('uc084-sc06-b.ie', 'ns3.uc084-sc06-b.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc084-sc06-c.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 603), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XNV498-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc084-sc06-c.ie', 'Test Holder 0001', 3, 2, NULL, 603, CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc06-c.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc06-c.ie'), 'uc084-sc06-c.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc06-c.ie'), 'uc084-sc06-c.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc06-c.ie'), 'uc084-sc06-c.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc06-c.ie'), 'uc084-sc06-c.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc084-sc06-c.ie', 'XOE550-IEDR', 'Admin'), ('uc084-sc06-c.ie', 'XNV498-IEDR', 'Billing'), ('uc084-sc06-c.ie', 'XNV498-IEDR', 'Creator'), ('uc084-sc06-c.ie', 'XNV498-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc06-c.ie'), 'uc084-sc06-c.ie', 'ns1.uc084-sc06-c.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc06-c.ie'), 'uc084-sc06-c.ie', 'ns2.uc084-sc06-c.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc06-c.ie'), 'uc084-sc06-c.ie', 'ns3.uc084-sc06-c.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc084-sc06-c.ie', 'ns1.uc084-sc06-c.ie', '10.10.121.23', NULL, '1'), ('uc084-sc06-c.ie', 'ns2.uc084-sc06-c.ie', '1.3.4.2', NULL, '2'), ('uc084-sc06-c.ie', 'ns3.uc084-sc06-c.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc084-sc06-d.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 603), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XNV498-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc084-sc06-d.ie', 'Test Holder 0001', 3, 2, NULL, 603, CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc06-d.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc06-d.ie'), 'uc084-sc06-d.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc06-d.ie'), 'uc084-sc06-d.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc06-d.ie'), 'uc084-sc06-d.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc06-d.ie'), 'uc084-sc06-d.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc084-sc06-d.ie', 'XOE550-IEDR', 'Admin'), ('uc084-sc06-d.ie', 'XNV498-IEDR', 'Billing'), ('uc084-sc06-d.ie', 'XNV498-IEDR', 'Creator'), ('uc084-sc06-d.ie', 'XNV498-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc06-d.ie'), 'uc084-sc06-d.ie', 'ns1.uc084-sc06-d.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc06-d.ie'), 'uc084-sc06-d.ie', 'ns2.uc084-sc06-d.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc06-d.ie'), 'uc084-sc06-d.ie', 'ns3.uc084-sc06-d.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc084-sc06-d.ie', 'ns1.uc084-sc06-d.ie', '10.10.121.23', NULL, '1'), ('uc084-sc06-d.ie', 'ns2.uc084-sc06-d.ie', '1.3.4.2', NULL, '2'), ('uc084-sc06-d.ie', 'ns3.uc084-sc06-d.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc084-sc06-e.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc084-sc06-e.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc06-e.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc06-e.ie'), 'uc084-sc06-e.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc06-e.ie'), 'uc084-sc06-e.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc06-e.ie'), 'uc084-sc06-e.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc06-e.ie'), 'uc084-sc06-e.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc084-sc06-e.ie', 'XBC189-IEDR', 'Admin'), ('uc084-sc06-e.ie', 'XBC189-IEDR', 'Billing'), ('uc084-sc06-e.ie', 'XBC189-IEDR', 'Creator'), ('uc084-sc06-e.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc06-e.ie'), 'uc084-sc06-e.ie', 'ns1.uc084-sc06-e.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc06-e.ie'), 'uc084-sc06-e.ie', 'ns2.uc084-sc06-e.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc06-e.ie'), 'uc084-sc06-e.ie', 'ns3.uc084-sc06-e.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc084-sc06-e.ie', 'ns1.uc084-sc06-e.ie', '10.10.121.23', NULL, '1'), ('uc084-sc06-e.ie', 'ns2.uc084-sc06-e.ie', '1.3.4.2', NULL, '2'), ('uc084-sc06-e.ie', 'ns3.uc084-sc06-e.ie', '10.12.13.14', NULL, '3');

INSERT INTO NicHandleHist VALUES (NULL, 'UC084R1-IEDR', 'Use Case 084 - Registrar - Losing Account', (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc084r1@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'UC084R1-IEDR', 'A', NULL, 'YES', 'PORTAL', NOW());
INSERT INTO NicHandle VALUES ('UC084R1-IEDR', 'Use Case 084 - Registrar - Losing Account', 1, 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc084r1@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'UC084R1-IEDR', 'A', NULL, 'YES', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC084R1-IEDR'));
INSERT INTO AccessHist VALUES (NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC084R1-IEDR'), 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 2, 'NO', 'PORTAL', NOW(), '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '');
INSERT INTO Access VALUES ('UC084R1-IEDR', 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 2, 'NO', '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '', (SELECT MAX(A.Chng_Id) FROM AccessHist A JOIN NicHandleHist NH  ON A.Nic_Handle_Chng_Id = NH.Chng_Id WHERE NH.Nic_Handle = 'UC084R1-IEDR'));
INSERT INTO AccountHist VALUES (NULL, 1111, 'UC084 Losing Account', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC084R1-IEDR'), 'www.testregistrar.ie', 'Active', '2011-12-08', '2011-12-08', 'NO', 'NO', '2011-12-08 15:00:49', 'test data generation script', NULL, NOW(), 'UC084R1-IEDR');
INSERT INTO Account VALUES (1111, 'UC084 Losing Account', 'UC084R1-IEDR', 'www.testregistrar.ie', 'Active', '2011-12-08', '2011-12-08', 'NO', 'NO', '2011-12-08 15:00:49', 'test data generation script', NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1111));
UPDATE NicHandleHist SET Account_Chng_Id = (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1111) WHERE Nic_Handle = 'UC084R1-IEDR';
UPDATE NicHandle SET A_Number = 1111 WHERE Nic_Handle = 'UC084R1-IEDR';

INSERT INTO NicHandleHist VALUES (NULL, 'UC084R2-IEDR', 'Use Case 084 - Registrar - Gaining Account', (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc084r2@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'UC084R2-IEDR', 'A', NULL, 'YES', 'PORTAL', NOW());
INSERT INTO NicHandle VALUES ('UC084R2-IEDR', 'Use Case 084 - Registrar - Gaining Account', 1, 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc084r2@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'UC084R2-IEDR', 'A', NULL, 'YES', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC084R2-IEDR'));
INSERT INTO AccessHist VALUES (NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC084R2-IEDR'), 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 2, 'NO', 'PORTAL', NOW(), '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '');
INSERT INTO Access VALUES ('UC084R2-IEDR', 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 2, 'NO', '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '', (SELECT MAX(A.Chng_Id) FROM AccessHist A JOIN NicHandleHist NH  ON A.Nic_Handle_Chng_Id = NH.Chng_Id WHERE NH.Nic_Handle = 'UC084R2-IEDR'));
INSERT INTO AccountHist VALUES (NULL, 1113, 'UC084 Gaining Account', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC084R2-IEDR'), 'www.testregistrar.ie', 'Active', '2011-12-08', '2011-12-08', 'NO', 'NO', '2011-12-08 15:00:49', 'test data generation script', NULL, NOW(), 'UC084R2-IEDR');
INSERT INTO Account VALUES (1113, 'UC084 Gaining Account', 'UC084R2-IEDR', 'www.testregistrar.ie', 'Active', '2011-12-08', '2011-12-08', 'NO', 'NO', '2011-12-08 15:00:49', 'test data generation script', NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1113));
UPDATE NicHandleHist SET Account_Chng_Id = (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1113) WHERE Nic_Handle = 'UC084R2-IEDR';
UPDATE NicHandle SET A_Number = 1113 WHERE Nic_Handle = 'UC084R2-IEDR';

INSERT INTO NicHandleHist VALUES (NULL, 'UC084C1-IEDR', 'Use Case 045 - Contact', (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1113), 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc045c6@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'UC084R2-IEDR', 'A', NULL, 'NO', 'UC084R2-IEDR', NOW());
INSERT INTO NicHandle VALUES ('UC084C1-IEDR', 'Use Case 045 - Contact', 1113, 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc045c6@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'UC084R2-IEDR', 'A', NULL, 'NO', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC084C1-IEDR'));
INSERT INTO AccessHist VALUES (NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC084C1-IEDR'), 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 0, 'NO', 'UC084R2-IEDR', NOW(), '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '');
INSERT INTO Access VALUES ('UC084C1-IEDR', 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 0, 'NO', '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '', (SELECT MAX(A.Chng_Id) FROM AccessHist A JOIN NicHandleHist NH  ON A.Nic_Handle_Chng_Id = NH.Chng_Id WHERE NH.Nic_Handle = 'UC084C1-IEDR'));

INSERT INTO Reseller_Defaults VALUES ('UC084R1-IEDR', 'UC084R1-IEDR', NULL, 'pdf');
INSERT INTO ResellerDefaultNameservers VALUES ('UC084R1-IEDR', 'dns.ns1.ie', 0, NOW());
INSERT INTO ResellerDefaultNameservers VALUES ('UC084R1-IEDR', 'dns.ns2.ie', 1, NOW());

INSERT INTO DomainHist VALUES (NULL, 'uc084-sc07-a.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1113), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'UC084R2-IEDR', NOW(), 'NO', '18', CURDATE() + Interval 6 Month, CURDATE() + Interval 6 Month, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc084-sc07-a.ie', 'Test Holder 0001', 3, 2, NULL, 1113,  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '18', CURDATE() + Interval 6 Month, CURDATE() + Interval 6 Month, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc07-a.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc07-a.ie'), 'uc084-sc07-a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC084C1-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc07-a.ie'), 'uc084-sc07-a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC084R2-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc07-a.ie'), 'uc084-sc07-a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC084R2-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc07-a.ie'), 'uc084-sc07-a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC084R2-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc084-sc07-a.ie', 'UC084C1-IEDR', 'Admin'), ('uc084-sc07-a.ie', 'UC084R2-IEDR', 'Billing'), ('uc084-sc07-a.ie', 'UC084R2-IEDR', 'Creator'), ('uc084-sc07-a.ie', 'UC084C1-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc07-a.ie'), 'uc084-sc07-a.ie', 'ns1.dns.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc07-a.ie'), 'uc084-sc07-a.ie', 'ns2.dns.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc07-a.ie'), 'uc084-sc07-a.ie', 'ns3.dns.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('uc084-sc07-a.ie', 'ns1.dns.ie', NULL, NULL, '1'), ('uc084-sc07-a.ie', 'ns2.dns.ie', NULL, NULL, '2'), ('uc084-sc07-a.ie', 'ns3.dns.ie', NULL, NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc084-sc07-b.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1113), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'UC084R2-IEDR', NOW(), 'NO', '18', CURDATE() + Interval 6 Month, CURDATE() + Interval 6 Month, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc084-sc07-b.ie', 'Test Holder 0001', 3, 2, NULL, 1113,  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '18', CURDATE() + Interval 6 Month, CURDATE() + Interval 6 Month, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc07-b.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc07-b.ie'), 'uc084-sc07-b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC084C1-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc07-b.ie'), 'uc084-sc07-b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC084R2-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc07-b.ie'), 'uc084-sc07-b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC084R2-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc07-b.ie'), 'uc084-sc07-b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC084R2-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc084-sc07-b.ie', 'UC084C1-IEDR', 'Admin'), ('uc084-sc07-b.ie', 'UC084R2-IEDR', 'Billing'), ('uc084-sc07-b.ie', 'UC084R2-IEDR', 'Creator'), ('uc084-sc07-b.ie', 'UC084C1-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc07-b.ie'), 'uc084-sc07-b.ie', 'ns1.dns.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc07-b.ie'), 'uc084-sc07-b.ie', 'ns2.dns.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc07-b.ie'), 'uc084-sc07-b.ie', 'ns3.dns.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('uc084-sc07-b.ie', 'ns1.dns.ie', NULL, NULL, '1'), ('uc084-sc07-b.ie', 'ns2.dns.ie', NULL, NULL, '2'), ('uc084-sc07-b.ie', 'ns3.dns.ie', NULL, NULL, '3');

