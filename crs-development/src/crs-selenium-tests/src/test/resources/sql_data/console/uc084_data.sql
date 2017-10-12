INSERT INTO DomainHist VALUES (NULL, 'uc084-sc01-a.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '81', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc084-sc01-a.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '81', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc01-a.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc01-a.ie'), 'uc084-sc01-a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc01-a.ie'), 'uc084-sc01-a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc01-a.ie'), 'uc084-sc01-a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc01-a.ie'), 'uc084-sc01-a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc084-sc01-a.ie', 'XBC189-IEDR', 'Admin'), ('uc084-sc01-a.ie', 'XBC189-IEDR', 'Billing'), ('uc084-sc01-a.ie', 'XBC189-IEDR', 'Creator'), ('uc084-sc01-a.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc01-a.ie'), 'uc084-sc01-a.ie', 'ns1.uc084-sc01-a.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc01-a.ie'), 'uc084-sc01-a.ie', 'ns2.uc084-sc01-a.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc01-a.ie'), 'uc084-sc01-a.ie', 'ns3.uc084-sc01-a.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc084-sc01-a.ie', 'ns1.uc084-sc01-a.ie', '10.10.121.23', NULL, '1'), ('uc084-sc01-a.ie', 'ns2.uc084-sc01-a.ie', '1.3.4.2', NULL, '2'), ('uc084-sc01-a.ie', 'ns3.uc084-sc01-a.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc084-sc01-b.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '81', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc084-sc01-b.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '81', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc01-b.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc01-b.ie'), 'uc084-sc01-b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc01-b.ie'), 'uc084-sc01-b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc01-b.ie'), 'uc084-sc01-b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc01-b.ie'), 'uc084-sc01-b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc084-sc01-b.ie', 'XBC189-IEDR', 'Admin'), ('uc084-sc01-b.ie', 'XBC189-IEDR', 'Billing'), ('uc084-sc01-b.ie', 'XBC189-IEDR', 'Creator'), ('uc084-sc01-b.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc01-b.ie'), 'uc084-sc01-b.ie', 'ns1.uc084-sc01-b.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc01-b.ie'), 'uc084-sc01-b.ie', 'ns2.uc084-sc01-b.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc01-b.ie'), 'uc084-sc01-b.ie', 'ns3.uc084-sc01-b.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc084-sc01-b.ie', 'ns1.uc084-sc01-b.ie', '10.10.121.23', NULL, '1'), ('uc084-sc01-b.ie', 'ns2.uc084-sc01-b.ie', '1.3.4.2', NULL, '2'), ('uc084-sc01-b.ie', 'ns3.uc084-sc01-b.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc084-sc02.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 603), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XNV498-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc084-sc02.ie', 'Test Holder 0001', 3, 2, NULL, 603, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc02.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc02.ie'), 'uc084-sc02.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc02.ie'), 'uc084-sc02.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc02.ie'), 'uc084-sc02.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc02.ie'), 'uc084-sc02.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc084-sc02.ie', 'XOE550-IEDR', 'Admin'), ('uc084-sc02.ie', 'XNV498-IEDR', 'Billing'), ('uc084-sc02.ie', 'XNV498-IEDR', 'Creator'), ('uc084-sc02.ie', 'XNV498-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc02.ie'), 'uc084-sc02.ie', 'ns1.uc084-sc02.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc02.ie'), 'uc084-sc02.ie', 'ns2.uc084-sc02.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc02.ie'), 'uc084-sc02.ie', 'ns3.uc084-sc02.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc084-sc02.ie', 'ns1.uc084-sc02.ie', '10.10.121.23', NULL, '1'), ('uc084-sc02.ie', 'ns2.uc084-sc02.ie', '1.3.4.2', NULL, '2'), ('uc084-sc02.ie', 'ns3.uc084-sc02.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc084-sc03-a.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '81', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc084-sc03-a.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '81', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc03-a.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc03-a.ie'), 'uc084-sc03-a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc03-a.ie'), 'uc084-sc03-a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc03-a.ie'), 'uc084-sc03-a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc03-a.ie'), 'uc084-sc03-a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc084-sc03-a.ie', 'XBC189-IEDR', 'Admin'), ('uc084-sc03-a.ie', 'XBC189-IEDR', 'Billing'), ('uc084-sc03-a.ie', 'XBC189-IEDR', 'Creator'), ('uc084-sc03-a.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc03-a.ie'), 'uc084-sc03-a.ie', 'ns1.uc084-sc03-a.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc03-a.ie'), 'uc084-sc03-a.ie', 'ns2.uc084-sc03-a.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc03-a.ie'), 'uc084-sc03-a.ie', 'ns3.uc084-sc03-a.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc084-sc03-a.ie', 'ns1.uc084-sc03-a.ie', '10.10.121.23', NULL, '1'), ('uc084-sc03-a.ie', 'ns2.uc084-sc03-a.ie', '1.3.4.2', NULL, '2'), ('uc084-sc03-a.ie', 'ns3.uc084-sc03-a.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc084-sc03-b.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '81', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc084-sc03-b.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '81', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc03-b.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc03-b.ie'), 'uc084-sc03-b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc03-b.ie'), 'uc084-sc03-b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc03-b.ie'), 'uc084-sc03-b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc03-b.ie'), 'uc084-sc03-b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc084-sc03-b.ie', 'XBC189-IEDR', 'Admin'), ('uc084-sc03-b.ie', 'XBC189-IEDR', 'Billing'), ('uc084-sc03-b.ie', 'XBC189-IEDR', 'Creator'), ('uc084-sc03-b.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc03-b.ie'), 'uc084-sc03-b.ie', 'ns1.uc084-sc03-b.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc03-b.ie'), 'uc084-sc03-b.ie', 'ns2.uc084-sc03-b.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc03-b.ie'), 'uc084-sc03-b.ie', 'ns3.uc084-sc03-b.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc084-sc03-b.ie', 'ns1.uc084-sc03-b.ie', '10.10.121.23', NULL, '1'), ('uc084-sc03-b.ie', 'ns2.uc084-sc03-b.ie', '1.3.4.2', NULL, '2'), ('uc084-sc03-b.ie', 'ns3.uc084-sc03-b.ie', '10.12.13.14', NULL, '3');

# AuthCode present
INSERT INTO DomainHist VALUES (NULL, 'uc084-sc05-a.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '81', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc084-sc05-a.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '81', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc05-a.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc05-a.ie'), 'uc084-sc05-a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc05-a.ie'), 'uc084-sc05-a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc05-a.ie'), 'uc084-sc05-a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc05-a.ie'), 'uc084-sc05-a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc084-sc05-a.ie', 'XBC189-IEDR', 'Admin'), ('uc084-sc05-a.ie', 'XBC189-IEDR', 'Billing'), ('uc084-sc05-a.ie', 'XBC189-IEDR', 'Creator'), ('uc084-sc05-a.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc05-a.ie'), 'uc084-sc05-a.ie', 'ns1.uc084-sc05-a.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc05-a.ie'), 'uc084-sc05-a.ie', 'ns2.uc084-sc05-a.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc05-a.ie'), 'uc084-sc05-a.ie', 'ns3.uc084-sc05-a.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc084-sc05-a.ie', 'ns1.uc084-sc05-a.ie', '10.10.121.23', NULL, '1'), ('uc084-sc05-a.ie', 'ns2.uc084-sc05-a.ie', '1.3.4.2', NULL, '2'), ('uc084-sc05-a.ie', 'ns3.uc084-sc05-a.ie', '10.12.13.14', NULL, '3');

# AuthCode present with older date (overdue)
INSERT INTO DomainHist VALUES (NULL, 'uc084-sc05-b.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '81', null, null, null, 'authcode', CURDATE() - Interval 1 Day, 0, null, null);
INSERT INTO Domain VALUES ('uc084-sc05-b.ie', 'Test Holder 0001', 3, 2, NULL, '00000600', CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '81', null, null, null, 'authcode', CURDATE() - Interval 1 Day, 0, null, null, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc05-b.ie'));
INSERT INTO ContactHist VALUES
  ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc05-b.ie'), 'uc084-sc05-b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Admin'),
  ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc05-b.ie'), 'uc084-sc05-b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
  ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc05-b.ie'), 'uc084-sc05-b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
  ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc05-b.ie'), 'uc084-sc05-b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc084-sc05-b.ie','XBC189-IEDR', 'Admin'), ('uc084-sc05-b.ie','XBC189-IEDR', 'Billing'), ('uc084-sc05-b.ie','XBC189-IEDR', 'Creator'), ('uc084-sc05-b.ie','XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
  ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc05-b.ie'), 'uc084-sc05-b.ie', 'ns1.uc084-sc05-b.ie', '10.10.121.23', NULL, '1'),
  ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc05-b.ie'), 'uc084-sc05-b.ie', 'ns2.uc084-sc05-b.ie', '1.3.4.2', NULL, '2'),
  ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc05-b.ie'), 'uc084-sc05-b.ie', 'ns3.uc084-sc05-b.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc084-sc05-b.ie', 'ns1.uc084-sc05-b.ie', '10.10.121.23', NULL, '1'), ('uc084-sc05-b.ie', 'ns2.uc084-sc05-b.ie', '1.3.4.2', NULL, '2'), ('uc084-sc05-b.ie', 'ns3.uc084-sc05-b.ie', '10.12.13.14', NULL, '3');

# AuthCode not present
INSERT INTO DomainHist VALUES (NULL, 'uc084-sc05-c.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '81', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc084-sc05-c.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '81', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc05-c.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc05-c.ie'), 'uc084-sc05-c.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc05-c.ie'), 'uc084-sc05-c.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc05-c.ie'), 'uc084-sc05-c.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc05-c.ie'), 'uc084-sc05-c.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc084-sc05-c.ie', 'XBC189-IEDR', 'Admin'), ('uc084-sc05-c.ie', 'XBC189-IEDR', 'Billing'), ('uc084-sc05-c.ie', 'XBC189-IEDR', 'Creator'), ('uc084-sc05-c.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc05-c.ie'), 'uc084-sc05-c.ie', 'ns1.uc084-sc05-c.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc05-c.ie'), 'uc084-sc05-c.ie', 'ns2.uc084-sc05-c.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc05-c.ie'), 'uc084-sc05-c.ie', 'ns3.uc084-sc05-c.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc084-sc05-c.ie', 'ns1.uc084-sc05-c.ie', '10.10.121.23', NULL, '1'), ('uc084-sc05-c.ie', 'ns2.uc084-sc05-c.ie', '1.3.4.2', NULL, '2'), ('uc084-sc05-c.ie', 'ns3.uc084-sc05-c.ie', '10.12.13.14', NULL, '3');

# AuthCode present but not selected for download
INSERT INTO DomainHist VALUES (NULL, 'uc084-sc05-d.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '81', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc084-sc05-d.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '81', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc05-d.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc05-d.ie'), 'uc084-sc05-d.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc05-d.ie'), 'uc084-sc05-d.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc05-d.ie'), 'uc084-sc05-d.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc05-d.ie'), 'uc084-sc05-d.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc084-sc05-d.ie', 'XBC189-IEDR', 'Admin'), ('uc084-sc05-d.ie', 'XBC189-IEDR', 'Billing'), ('uc084-sc05-d.ie', 'XBC189-IEDR', 'Creator'), ('uc084-sc05-d.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc05-d.ie'), 'uc084-sc05-d.ie', 'ns1.uc084-sc05-d.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc05-d.ie'), 'uc084-sc05-d.ie', 'ns2.uc084-sc05-d.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc084-sc05-d.ie'), 'uc084-sc05-d.ie', 'ns3.uc084-sc05-d.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc084-sc05-d.ie', 'ns1.uc084-sc05-d.ie', '10.10.121.23', NULL, '1'), ('uc084-sc05-d.ie', 'ns2.uc084-sc05-d.ie', '1.3.4.2', NULL, '2'), ('uc084-sc05-d.ie', 'ns3.uc084-sc05-d.ie', '10.12.13.14', NULL, '3');

