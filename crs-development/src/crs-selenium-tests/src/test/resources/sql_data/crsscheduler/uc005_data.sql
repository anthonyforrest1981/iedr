# UC005: Domain with renewal date set to current date
INSERT INTO DomainHist VALUES (NULL, 'uc005-autorenew-current.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '49', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc005-autorenew-current.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', '49', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-autorenew-current.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-autorenew-current.ie'), 'uc005-autorenew-current.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-autorenew-current.ie'), 'uc005-autorenew-current.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-autorenew-current.ie'), 'uc005-autorenew-current.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-autorenew-current.ie'), 'uc005-autorenew-current.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc005-autorenew-current.ie', 'XOE550-IEDR', 'Admin'), ('uc005-autorenew-current.ie', 'XBC189-IEDR', 'Billing'), ('uc005-autorenew-current.ie', 'XBC189-IEDR', 'Creator'), ('uc005-autorenew-current.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-autorenew-current.ie'), 'uc005-autorenew-current.ie', 'ns1.uc005-autorenew-current.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-autorenew-current.ie'), 'uc005-autorenew-current.ie', 'ns2.uc005-autorenew-current.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-autorenew-current.ie'), 'uc005-autorenew-current.ie', 'ns3.uc005-autorenew-current.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc005-autorenew-current.ie', 'ns1.uc005-autorenew-current.ie', '10.10.121.23', NULL, '1'), ('uc005-autorenew-current.ie', 'ns2.uc005-autorenew-current.ie', '1.3.4.2', NULL, '2'), ('uc005-autorenew-current.ie', 'ns3.uc005-autorenew-current.ie', '10.12.13.14', NULL, '3');

# UC005: Domains with renewal date set to future date
INSERT INTO DomainHist VALUES (NULL, 'uc005-autorenew-future-a.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '49', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc005-autorenew-future-a.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '49', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-autorenew-future-a.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-autorenew-future-a.ie'), 'uc005-autorenew-future-a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-autorenew-future-a.ie'), 'uc005-autorenew-future-a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-autorenew-future-a.ie'), 'uc005-autorenew-future-a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-autorenew-future-a.ie'), 'uc005-autorenew-future-a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc005-autorenew-future-a.ie', 'XOE550-IEDR', 'Admin'), ('uc005-autorenew-future-a.ie', 'XBC189-IEDR', 'Billing'), ('uc005-autorenew-future-a.ie', 'XBC189-IEDR', 'Creator'), ('uc005-autorenew-future-a.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-autorenew-future-a.ie'), 'uc005-autorenew-future-a.ie', 'ns1.uc005-autorenew-future-a.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-autorenew-future-a.ie'), 'uc005-autorenew-future-a.ie', 'ns2.uc005-autorenew-future-a.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-autorenew-future-a.ie'), 'uc005-autorenew-future-a.ie', 'ns3.uc005-autorenew-future-a.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc005-autorenew-future-a.ie', 'ns1.uc005-autorenew-future-a.ie', '10.10.121.23', NULL, '1'), ('uc005-autorenew-future-a.ie', 'ns2.uc005-autorenew-future-a.ie', '1.3.4.2', NULL, '2'), ('uc005-autorenew-future-a.ie', 'ns3.uc005-autorenew-future-a.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc005-autorenew-future-b.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '49', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc005-autorenew-future-b.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '49', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-autorenew-future-b.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-autorenew-future-b.ie'), 'uc005-autorenew-future-b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-autorenew-future-b.ie'), 'uc005-autorenew-future-b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-autorenew-future-b.ie'), 'uc005-autorenew-future-b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-autorenew-future-b.ie'), 'uc005-autorenew-future-b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc005-autorenew-future-b.ie', 'XOE550-IEDR', 'Admin'), ('uc005-autorenew-future-b.ie', 'XBC189-IEDR', 'Billing'), ('uc005-autorenew-future-b.ie', 'XBC189-IEDR', 'Creator'), ('uc005-autorenew-future-b.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-autorenew-future-b.ie'), 'uc005-autorenew-future-b.ie', 'ns1.uc005-autorenew-future-b.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-autorenew-future-b.ie'), 'uc005-autorenew-future-b.ie', 'ns2.uc005-autorenew-future-b.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-autorenew-future-b.ie'), 'uc005-autorenew-future-b.ie', 'ns3.uc005-autorenew-future-b.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc005-autorenew-future-b.ie', 'ns1.uc005-autorenew-future-b.ie', '10.10.121.23', NULL, '1'), ('uc005-autorenew-future-b.ie', 'ns2.uc005-autorenew-future-b.ie', '1.3.4.2', NULL, '2'), ('uc005-autorenew-future-b.ie', 'ns3.uc005-autorenew-future-b.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc005-autorenew-future-c.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '49', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc005-autorenew-future-c.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '49', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-autorenew-future-c.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-autorenew-future-c.ie'), 'uc005-autorenew-future-c.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-autorenew-future-c.ie'), 'uc005-autorenew-future-c.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-autorenew-future-c.ie'), 'uc005-autorenew-future-c.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-autorenew-future-c.ie'), 'uc005-autorenew-future-c.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc005-autorenew-future-c.ie', 'XOE550-IEDR', 'Admin'), ('uc005-autorenew-future-c.ie', 'XBC189-IEDR', 'Billing'), ('uc005-autorenew-future-c.ie', 'XBC189-IEDR', 'Creator'), ('uc005-autorenew-future-c.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-autorenew-future-c.ie'), 'uc005-autorenew-future-c.ie', 'ns1.uc005-autorenew-future-c.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-autorenew-future-c.ie'), 'uc005-autorenew-future-c.ie', 'ns2.uc005-autorenew-future-c.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-autorenew-future-c.ie'), 'uc005-autorenew-future-c.ie', 'ns3.uc005-autorenew-future-c.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc005-autorenew-future-c.ie', 'ns1.uc005-autorenew-future-c.ie', '10.10.121.23', NULL, '1'), ('uc005-autorenew-future-c.ie', 'ns2.uc005-autorenew-future-c.ie', '1.3.4.2', NULL, '2'), ('uc005-autorenew-future-c.ie', 'ns3.uc005-autorenew-future-c.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc005-autorenew-future-d.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '49', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc005-autorenew-future-d.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '49', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-autorenew-future-d.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-autorenew-future-d.ie'), 'uc005-autorenew-future-d.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-autorenew-future-d.ie'), 'uc005-autorenew-future-d.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-autorenew-future-d.ie'), 'uc005-autorenew-future-d.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-autorenew-future-d.ie'), 'uc005-autorenew-future-d.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc005-autorenew-future-d.ie', 'XOE550-IEDR', 'Admin'), ('uc005-autorenew-future-d.ie', 'XBC189-IEDR', 'Billing'), ('uc005-autorenew-future-d.ie', 'XBC189-IEDR', 'Creator'), ('uc005-autorenew-future-d.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-autorenew-future-d.ie'), 'uc005-autorenew-future-d.ie', 'ns1.uc005-autorenew-future-d.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-autorenew-future-d.ie'), 'uc005-autorenew-future-d.ie', 'ns2.uc005-autorenew-future-d.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-autorenew-future-d.ie'), 'uc005-autorenew-future-d.ie', 'ns3.uc005-autorenew-future-d.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc005-autorenew-future-d.ie', 'ns1.uc005-autorenew-future-d.ie', '10.10.121.23', NULL, '1'), ('uc005-autorenew-future-d.ie', 'ns2.uc005-autorenew-future-d.ie', '1.3.4.2', NULL, '2'), ('uc005-autorenew-future-d.ie', 'ns3.uc005-autorenew-future-d.ie', '10.12.13.14', NULL, '3');

# UC005: Domains with Renewal_Mode=N
INSERT INTO DomainHist VALUES (NULL, 'uc005-norenew-a.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc005-norenew-a.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-norenew-a.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-norenew-a.ie'), 'uc005-norenew-a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-norenew-a.ie'), 'uc005-norenew-a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-norenew-a.ie'), 'uc005-norenew-a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-norenew-a.ie'), 'uc005-norenew-a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc005-norenew-a.ie', 'XOE550-IEDR', 'Admin'), ('uc005-norenew-a.ie', 'XBC189-IEDR', 'Billing'), ('uc005-norenew-a.ie', 'XBC189-IEDR', 'Creator'), ('uc005-norenew-a.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-norenew-a.ie'), 'uc005-norenew-a.ie', 'ns1.uc005-norenew-a.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-norenew-a.ie'), 'uc005-norenew-a.ie', 'ns2.uc005-norenew-a.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-norenew-a.ie'), 'uc005-norenew-a.ie', 'ns3.uc005-norenew-a.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc005-norenew-a.ie', 'ns1.uc005-norenew-a.ie', '10.10.121.23', NULL, '1'), ('uc005-norenew-a.ie', 'ns2.uc005-norenew-a.ie', '1.3.4.2', NULL, '2'), ('uc005-norenew-a.ie', 'ns3.uc005-norenew-a.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc005-norenew-b.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc005-norenew-b.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-norenew-b.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-norenew-b.ie'), 'uc005-norenew-b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-norenew-b.ie'), 'uc005-norenew-b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-norenew-b.ie'), 'uc005-norenew-b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-norenew-b.ie'), 'uc005-norenew-b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc005-norenew-b.ie', 'XOE550-IEDR', 'Admin'), ('uc005-norenew-b.ie', 'XBC189-IEDR', 'Billing'), ('uc005-norenew-b.ie', 'XBC189-IEDR', 'Creator'), ('uc005-norenew-b.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-norenew-b.ie'), 'uc005-norenew-b.ie', 'ns1.uc005-norenew-b.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-norenew-b.ie'), 'uc005-norenew-b.ie', 'ns2.uc005-norenew-b.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-norenew-b.ie'), 'uc005-norenew-b.ie', 'ns3.uc005-norenew-b.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc005-norenew-b.ie', 'ns1.uc005-norenew-b.ie', '10.10.121.23', NULL, '1'), ('uc005-norenew-b.ie', 'ns2.uc005-norenew-b.ie', '1.3.4.2', NULL, '2'), ('uc005-norenew-b.ie', 'ns3.uc005-norenew-b.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc005-norenew-c.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc005-norenew-c.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-norenew-c.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-norenew-c.ie'), 'uc005-norenew-c.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-norenew-c.ie'), 'uc005-norenew-c.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-norenew-c.ie'), 'uc005-norenew-c.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-norenew-c.ie'), 'uc005-norenew-c.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc005-norenew-c.ie', 'XOE550-IEDR', 'Admin'), ('uc005-norenew-c.ie', 'XBC189-IEDR', 'Billing'), ('uc005-norenew-c.ie', 'XBC189-IEDR', 'Creator'), ('uc005-norenew-c.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-norenew-c.ie'), 'uc005-norenew-c.ie', 'ns1.uc005-norenew-c.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-norenew-c.ie'), 'uc005-norenew-c.ie', 'ns2.uc005-norenew-c.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-norenew-c.ie'), 'uc005-norenew-c.ie', 'ns3.uc005-norenew-c.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc005-norenew-c.ie', 'ns1.uc005-norenew-c.ie', '10.10.121.23', NULL, '1'), ('uc005-norenew-c.ie', 'ns2.uc005-norenew-c.ie', '1.3.4.2', NULL, '2'), ('uc005-norenew-c.ie', 'ns3.uc005-norenew-c.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc005-norenew-d.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc005-norenew-d.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-norenew-d.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-norenew-d.ie'), 'uc005-norenew-d.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-norenew-d.ie'), 'uc005-norenew-d.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-norenew-d.ie'), 'uc005-norenew-d.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-norenew-d.ie'), 'uc005-norenew-d.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc005-norenew-d.ie', 'XOE550-IEDR', 'Admin'), ('uc005-norenew-d.ie', 'XBC189-IEDR', 'Billing'), ('uc005-norenew-d.ie', 'XBC189-IEDR', 'Creator'), ('uc005-norenew-d.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-norenew-d.ie'), 'uc005-norenew-d.ie', 'ns1.uc005-norenew-d.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-norenew-d.ie'), 'uc005-norenew-d.ie', 'ns2.uc005-norenew-d.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-norenew-d.ie'), 'uc005-norenew-d.ie', 'ns3.uc005-norenew-d.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc005-norenew-d.ie', 'ns1.uc005-norenew-d.ie', '10.10.121.23', NULL, '1'), ('uc005-norenew-d.ie', 'ns2.uc005-norenew-d.ie', '1.3.4.2', NULL, '2'), ('uc005-norenew-d.ie', 'ns3.uc005-norenew-d.ie', '10.12.13.14', NULL, '3');

# UC005: Renew Autorenew Domains - AT - FAIL(A)-I-NRP-8 - AUTORENEW(A & R) REN DATE PASSED, SCHEDULER FAILED TO I-NRP
INSERT INTO DomainHist VALUES (NULL, 'uc005-failainrp8-a.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE() - Interval 1 Day, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '49', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc005-failainrp8-a.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE() - Interval 1 Day, NOW(), 'Test Remark', 'NO', '49', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-failainrp8-a.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-failainrp8-a.ie'), 'uc005-failainrp8-a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-failainrp8-a.ie'), 'uc005-failainrp8-a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-failainrp8-a.ie'), 'uc005-failainrp8-a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-failainrp8-a.ie'), 'uc005-failainrp8-a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc005-failainrp8-a.ie', 'XOE550-IEDR', 'Admin'), ('uc005-failainrp8-a.ie', 'XBC189-IEDR', 'Billing'), ('uc005-failainrp8-a.ie', 'XBC189-IEDR', 'Creator'), ('uc005-failainrp8-a.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-failainrp8-a.ie'), 'uc005-failainrp8-a.ie', 'ns1.uc005-failainrp8-a.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-failainrp8-a.ie'), 'uc005-failainrp8-a.ie', 'ns2.uc005-failainrp8-a.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-failainrp8-a.ie'), 'uc005-failainrp8-a.ie', 'ns3.uc005-failainrp8-a.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc005-failainrp8-a.ie', 'ns1.uc005-failainrp8-a.ie', '10.10.121.23', NULL, '1'), ('uc005-failainrp8-a.ie', 'ns2.uc005-failainrp8-a.ie', '1.3.4.2', NULL, '2'), ('uc005-failainrp8-a.ie', 'ns3.uc005-failainrp8-a.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc005-failainrp8-b.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE() - Interval 1 Day, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '81', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc005-failainrp8-b.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE() - Interval 1 Day, NOW(), 'Test Remark', 'NO', '81', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-failainrp8-b.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-failainrp8-b.ie'), 'uc005-failainrp8-b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-failainrp8-b.ie'), 'uc005-failainrp8-b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-failainrp8-b.ie'), 'uc005-failainrp8-b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-failainrp8-b.ie'), 'uc005-failainrp8-b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc005-failainrp8-b.ie', 'XOE550-IEDR', 'Admin'), ('uc005-failainrp8-b.ie', 'XBC189-IEDR', 'Billing'), ('uc005-failainrp8-b.ie', 'XBC189-IEDR', 'Creator'), ('uc005-failainrp8-b.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-failainrp8-b.ie'), 'uc005-failainrp8-b.ie', 'ns1.uc005-failainrp8-b.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-failainrp8-b.ie'), 'uc005-failainrp8-b.ie', 'ns2.uc005-failainrp8-b.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-failainrp8-b.ie'), 'uc005-failainrp8-b.ie', 'ns3.uc005-failainrp8-b.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc005-failainrp8-b.ie', 'ns1.uc005-failainrp8-b.ie', '10.10.121.23', NULL, '1'), ('uc005-failainrp8-b.ie', 'ns2.uc005-failainrp8-b.ie', '1.3.4.2', NULL, '2'), ('uc005-failainrp8-b.ie', 'ns3.uc005-failainrp8-b.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc005-failainrp8-c.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE() - Interval 1 Day, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc005-failainrp8-c.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE() - Interval 1 Day, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-failainrp8-c.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-failainrp8-c.ie'), 'uc005-failainrp8-c.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-failainrp8-c.ie'), 'uc005-failainrp8-c.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-failainrp8-c.ie'), 'uc005-failainrp8-c.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-failainrp8-c.ie'), 'uc005-failainrp8-c.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc005-failainrp8-c.ie', 'XOE550-IEDR', 'Admin'), ('uc005-failainrp8-c.ie', 'XBC189-IEDR', 'Billing'), ('uc005-failainrp8-c.ie', 'XBC189-IEDR', 'Creator'), ('uc005-failainrp8-c.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-failainrp8-c.ie'), 'uc005-failainrp8-c.ie', 'ns1.uc005-failainrp8-c.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-failainrp8-c.ie'), 'uc005-failainrp8-c.ie', 'ns2.uc005-failainrp8-c.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-failainrp8-c.ie'), 'uc005-failainrp8-c.ie', 'ns3.uc005-failainrp8-c.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc005-failainrp8-c.ie', 'ns1.uc005-failainrp8-c.ie', '10.10.121.23', NULL, '1'), ('uc005-failainrp8-c.ie', 'ns2.uc005-failainrp8-c.ie', '1.3.4.2', NULL, '2'), ('uc005-failainrp8-c.ie', 'ns3.uc005-failainrp8-c.ie', '10.12.13.14', NULL, '3');

# UC005: Domains to transfer
INSERT INTO DomainHist VALUES (NULL, 'uc005-transfer-a.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 603), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XNV498-IEDR', NOW(), 'NO', '49', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc005-transfer-a.ie', 'Test Holder 0001', 3, 2, NULL, 603, CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '49', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-transfer-a.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-transfer-a.ie'), 'uc005-transfer-a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-transfer-a.ie'), 'uc005-transfer-a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-transfer-a.ie'), 'uc005-transfer-a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-transfer-a.ie'), 'uc005-transfer-a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc005-transfer-a.ie', 'XNV498-IEDR', 'Admin'), ('uc005-transfer-a.ie', 'XNV498-IEDR', 'Billing'), ('uc005-transfer-a.ie', 'XNV498-IEDR', 'Creator'), ('uc005-transfer-a.ie', 'XNV498-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-transfer-a.ie'), 'uc005-transfer-a.ie', 'ns1.uc005-transfer-a.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-transfer-a.ie'), 'uc005-transfer-a.ie', 'ns2.uc005-transfer-a.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-transfer-a.ie'), 'uc005-transfer-a.ie', 'ns3.uc005-transfer-a.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc005-transfer-a.ie', 'ns1.uc005-transfer-a.ie', '10.10.121.23', NULL, '1'), ('uc005-transfer-a.ie', 'ns2.uc005-transfer-a.ie', '1.3.4.2', NULL, '2'), ('uc005-transfer-a.ie', 'ns3.uc005-transfer-a.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc005-transfer-b.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 603), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XNV498-IEDR', NOW(), 'NO', '81', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc005-transfer-b.ie', 'Test Holder 0001', 3, 2, NULL, 603, CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '81', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-transfer-b.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-transfer-b.ie'), 'uc005-transfer-b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-transfer-b.ie'), 'uc005-transfer-b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-transfer-b.ie'), 'uc005-transfer-b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-transfer-b.ie'), 'uc005-transfer-b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc005-transfer-b.ie', 'XNV498-IEDR', 'Admin'), ('uc005-transfer-b.ie', 'XNV498-IEDR', 'Billing'), ('uc005-transfer-b.ie', 'XNV498-IEDR', 'Creator'), ('uc005-transfer-b.ie', 'XNV498-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-transfer-b.ie'), 'uc005-transfer-b.ie', 'ns1.uc005-transfer-b.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-transfer-b.ie'), 'uc005-transfer-b.ie', 'ns2.uc005-transfer-b.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc005-transfer-b.ie'), 'uc005-transfer-b.ie', 'ns3.uc005-transfer-b.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc005-transfer-b.ie', 'ns1.uc005-transfer-b.ie', '10.10.121.23', NULL, '1'), ('uc005-transfer-b.ie', 'ns2.uc005-transfer-b.ie', '1.3.4.2', NULL, '2'), ('uc005-transfer-b.ie', 'ns3.uc005-transfer-b.ie', '10.12.13.14', NULL, '3');

