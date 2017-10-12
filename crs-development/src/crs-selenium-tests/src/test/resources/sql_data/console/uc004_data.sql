# UC#004: Make Payment for Existing Domain - UC004-SC01: Registrar Happy Path
INSERT INTO DomainHist VALUES (NULL, 'uc004-sc01a.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc004-sc01a.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01a.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01a.ie'), 'uc004-sc01a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01a.ie'), 'uc004-sc01a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01a.ie'), 'uc004-sc01a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01a.ie'), 'uc004-sc01a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc004-sc01a.ie', 'XOE550-IEDR', 'Admin'), ('uc004-sc01a.ie', 'XBC189-IEDR', 'Billing'), ('uc004-sc01a.ie', 'XBC189-IEDR', 'Creator'), ('uc004-sc01a.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01a.ie'), 'uc004-sc01a.ie', 'ns1.uc004-sc01a.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01a.ie'), 'uc004-sc01a.ie', 'ns2.uc004-sc01a.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01a.ie'), 'uc004-sc01a.ie', 'ns3.uc004-sc01a.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc004-sc01a.ie', 'ns1.uc004-sc01a.ie', '10.10.121.23', NULL, '1'), ('uc004-sc01a.ie', 'ns2.uc004-sc01a.ie', '1.3.4.2', NULL, '2'), ('uc004-sc01a.ie', 'ns3.uc004-sc01a.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc004-sc01b.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc004-sc01b.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01b.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01b.ie'), 'uc004-sc01b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01b.ie'), 'uc004-sc01b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01b.ie'), 'uc004-sc01b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01b.ie'), 'uc004-sc01b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc004-sc01b.ie', 'XOE550-IEDR', 'Admin'), ('uc004-sc01b.ie', 'XBC189-IEDR', 'Billing'), ('uc004-sc01b.ie', 'XBC189-IEDR', 'Creator'), ('uc004-sc01b.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01b.ie'), 'uc004-sc01b.ie', 'ns1.uc004-sc01b.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01b.ie'), 'uc004-sc01b.ie', 'ns2.uc004-sc01b.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01b.ie'), 'uc004-sc01b.ie', 'ns3.uc004-sc01b.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc004-sc01b.ie', 'ns1.uc004-sc01b.ie', '10.10.121.23', NULL, '1'), ('uc004-sc01b.ie', 'ns2.uc004-sc01b.ie', '1.3.4.2', NULL, '2'), ('uc004-sc01b.ie', 'ns3.uc004-sc01b.ie', '10.12.13.14', NULL, '3');

# UC#004: Make Payment for Existing Domain - UC004-SC03: Direct Happy Path
INSERT INTO DomainHist VALUES (NULL, 'uc004-sc03a.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XDD274-IEDR', NOW(), 'NO', 25, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc004-sc03a.ie', 'Test Holder 0001', 3, 2, NULL, 1, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', 25, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc03a.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc03a.ie'), 'uc004-sc03a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc03a.ie'), 'uc004-sc03a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc03a.ie'), 'uc004-sc03a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc03a.ie'), 'uc004-sc03a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc004-sc03a.ie', 'XOE550-IEDR', 'Admin'), ('uc004-sc03a.ie', 'XDD274-IEDR', 'Billing'), ('uc004-sc03a.ie', 'XDD274-IEDR', 'Creator'), ('uc004-sc03a.ie', 'XDD274-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc03a.ie'), 'uc004-sc03a.ie', 'ns1.uc004-sc03a.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc03a.ie'), 'uc004-sc03a.ie', 'ns2.uc004-sc03a.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc03a.ie'), 'uc004-sc03a.ie', 'ns3.uc004-sc03a.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc004-sc03a.ie', 'ns1.uc004-sc03a.ie', '10.10.121.23', NULL, '1'), ('uc004-sc03a.ie', 'ns2.uc004-sc03a.ie', '1.3.4.2', NULL, '2'), ('uc004-sc03a.ie', 'ns3.uc004-sc03a.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc004-sc03b.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XDD274-IEDR', NOW(), 'NO', 25, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc004-sc03b.ie', 'Test Holder 0001', 3, 2, NULL, 1, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', 25, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc03b.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc03b.ie'), 'uc004-sc03b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc03b.ie'), 'uc004-sc03b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc03b.ie'), 'uc004-sc03b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc03b.ie'), 'uc004-sc03b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc004-sc03b.ie', 'XOE550-IEDR', 'Admin'), ('uc004-sc03b.ie', 'XDD274-IEDR', 'Billing'), ('uc004-sc03b.ie', 'XDD274-IEDR', 'Creator'), ('uc004-sc03b.ie', 'XDD274-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc03b.ie'), 'uc004-sc03b.ie', 'ns1.uc004-sc03b.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc03b.ie'), 'uc004-sc03b.ie', 'ns2.uc004-sc03b.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc03b.ie'), 'uc004-sc03b.ie', 'ns3.uc004-sc03b.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc004-sc03b.ie', 'ns1.uc004-sc03b.ie', '10.10.121.23', NULL, '1'), ('uc004-sc03b.ie', 'ns2.uc004-sc03b.ie', '1.3.4.2', NULL, '2'), ('uc004-sc03b.ie', 'ns3.uc004-sc03b.ie', '10.12.13.14', NULL, '3');

# UC#004: Make Payment for Existing Domain - UC004-SC04: Direct using Debit Card
INSERT INTO DomainHist VALUES (NULL, 'uc004-sc04a.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XDD274-IEDR', NOW(), 'NO', 25, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc004-sc04a.ie', 'Test Holder 0001', 3, 2, NULL, 1, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', 25, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc04a.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc04a.ie'), 'uc004-sc04a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc04a.ie'), 'uc004-sc04a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc04a.ie'), 'uc004-sc04a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc04a.ie'), 'uc004-sc04a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc004-sc04a.ie', 'XOE550-IEDR', 'Admin'), ('uc004-sc04a.ie', 'XDD274-IEDR', 'Billing'), ('uc004-sc04a.ie', 'XDD274-IEDR', 'Creator'), ('uc004-sc04a.ie', 'XDD274-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc04a.ie'), 'uc004-sc04a.ie', 'ns1.uc004-sc04a.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc04a.ie'), 'uc004-sc04a.ie', 'ns2.uc004-sc04a.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc04a.ie'), 'uc004-sc04a.ie', 'ns3.uc004-sc04a.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc004-sc04a.ie', 'ns1.uc004-sc04a.ie', '10.10.121.23', NULL, '1'), ('uc004-sc04a.ie', 'ns2.uc004-sc04a.ie', '1.3.4.2', NULL, '2'), ('uc004-sc04a.ie', 'ns3.uc004-sc04a.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc004-sc04b.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XDD274-IEDR', NOW(), 'NO', 25, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc004-sc04b.ie', 'Test Holder 0001', 3, 2, NULL, 1, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', 25, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc04b.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc04b.ie'), 'uc004-sc04b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc04b.ie'), 'uc004-sc04b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc04b.ie'), 'uc004-sc04b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc04b.ie'), 'uc004-sc04b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc004-sc04b.ie', 'XOE550-IEDR', 'Admin'), ('uc004-sc04b.ie', 'XDD274-IEDR', 'Billing'), ('uc004-sc04b.ie', 'XDD274-IEDR', 'Creator'), ('uc004-sc04b.ie', 'XDD274-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc04b.ie'), 'uc004-sc04b.ie', 'ns1.uc004-sc04b.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc04b.ie'), 'uc004-sc04b.ie', 'ns2.uc004-sc04b.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc04b.ie'), 'uc004-sc04b.ie', 'ns3.uc004-sc04b.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc004-sc04b.ie', 'ns1.uc004-sc04b.ie', '10.10.121.23', NULL, '1'), ('uc004-sc04b.ie', 'ns2.uc004-sc04b.ie', '1.3.4.2', NULL, '2'), ('uc004-sc04b.ie', 'ns3.uc004-sc04b.ie', '10.12.13.14', NULL, '3');

# UC#004: Make Payment for Existing Domain - UC004-SC07: ADP Payment failure - insufficient funds for all domains
INSERT INTO DomainHist VALUES (NULL, 'uc004-sc07a.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc004-sc07a.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc07a.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc07a.ie'), 'uc004-sc07a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc07a.ie'), 'uc004-sc07a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc07a.ie'), 'uc004-sc07a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc07a.ie'), 'uc004-sc07a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc004-sc07a.ie', 'XOE550-IEDR', 'Admin'), ('uc004-sc07a.ie', 'XBC189-IEDR', 'Billing'), ('uc004-sc07a.ie', 'XBC189-IEDR', 'Creator'), ('uc004-sc07a.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc07a.ie'), 'uc004-sc07a.ie', 'ns1.uc004-sc07a.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc07a.ie'), 'uc004-sc07a.ie', 'ns2.uc004-sc07a.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc07a.ie'), 'uc004-sc07a.ie', 'ns3.uc004-sc07a.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc004-sc07a.ie', 'ns1.uc004-sc07a.ie', '10.10.121.23', NULL, '1'), ('uc004-sc07a.ie', 'ns2.uc004-sc07a.ie', '1.3.4.2', NULL, '2'), ('uc004-sc07a.ie', 'ns3.uc004-sc07a.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc004-sc07b.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc004-sc07b.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc07b.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc07b.ie'), 'uc004-sc07b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc07b.ie'), 'uc004-sc07b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc07b.ie'), 'uc004-sc07b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc07b.ie'), 'uc004-sc07b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc004-sc07b.ie', 'XOE550-IEDR', 'Admin'), ('uc004-sc07b.ie', 'XBC189-IEDR', 'Billing'), ('uc004-sc07b.ie', 'XBC189-IEDR', 'Creator'), ('uc004-sc07b.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc07b.ie'), 'uc004-sc07b.ie', 'ns1.uc004-sc07b.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc07b.ie'), 'uc004-sc07b.ie', 'ns2.uc004-sc07b.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc07b.ie'), 'uc004-sc07b.ie', 'ns3.uc004-sc07b.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc004-sc07b.ie', 'ns1.uc004-sc07b.ie', '10.10.121.23', NULL, '1'), ('uc004-sc07b.ie', 'ns2.uc004-sc07b.ie', '1.3.4.2', NULL, '2'), ('uc004-sc07b.ie', 'ns3.uc004-sc07b.ie', '10.12.13.14', NULL, '3');

# UC#004: Make Payment for Existing Domain - UC004-SC11: Debit Card payment failure
INSERT INTO DomainHist VALUES (NULL, 'uc004-sc11.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XDD274-IEDR', NOW(), 'NO', 25, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc004-sc11.ie', 'Test Holder 0001', 3, 2, NULL, 1, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', 25, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc11.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc11.ie'), 'uc004-sc11.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc11.ie'), 'uc004-sc11.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc11.ie'), 'uc004-sc11.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc11.ie'), 'uc004-sc11.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc004-sc11.ie', 'XOE550-IEDR', 'Admin'), ('uc004-sc11.ie', 'XDD274-IEDR', 'Billing'), ('uc004-sc11.ie', 'XDD274-IEDR', 'Creator'), ('uc004-sc11.ie', 'XDD274-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc11.ie'), 'uc004-sc11.ie', 'ns1.uc004-sc11.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc11.ie'), 'uc004-sc11.ie', 'ns2.uc004-sc11.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc11.ie'), 'uc004-sc11.ie', 'ns3.uc004-sc11.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc004-sc11.ie', 'ns1.uc004-sc11.ie', '10.10.121.23', NULL, '1'), ('uc004-sc11.ie', 'ns2.uc004-sc11.ie', '1.3.4.2', NULL, '2'), ('uc004-sc11.ie', 'ns3.uc004-sc11.ie', '10.12.13.14', NULL, '3');

# UC#004: Make Payment for Existing Domain - UC004-SC15: Voluntary Suspended
INSERT INTO DomainHist VALUES (NULL, 'uc004-sc15a.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', 21, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc004-sc15a.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', 21, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc15a.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc15a.ie'), 'uc004-sc15a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc15a.ie'), 'uc004-sc15a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc15a.ie'), 'uc004-sc15a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc15a.ie'), 'uc004-sc15a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc004-sc15a.ie', 'XOE550-IEDR', 'Admin'), ('uc004-sc15a.ie', 'XBC189-IEDR', 'Billing'), ('uc004-sc15a.ie', 'XBC189-IEDR', 'Creator'), ('uc004-sc15a.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc15a.ie'), 'uc004-sc15a.ie', 'ns1.uc004-sc15a.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc15a.ie'), 'uc004-sc15a.ie', 'ns2.uc004-sc15a.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc15a.ie'), 'uc004-sc15a.ie', 'ns3.uc004-sc15a.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc004-sc15a.ie', 'ns1.uc004-sc15a.ie', '10.10.121.23', NULL, '1'), ('uc004-sc15a.ie', 'ns2.uc004-sc15a.ie', '1.3.4.2', NULL, '2'), ('uc004-sc15a.ie', 'ns3.uc004-sc15a.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc004-sc15b.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', 21, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc004-sc15b.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', 21, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc15b.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc15b.ie'), 'uc004-sc15b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc15b.ie'), 'uc004-sc15b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc15b.ie'), 'uc004-sc15b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc15b.ie'), 'uc004-sc15b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc004-sc15b.ie', 'XOE550-IEDR', 'Admin'), ('uc004-sc15b.ie', 'XBC189-IEDR', 'Billing'), ('uc004-sc15b.ie', 'XBC189-IEDR', 'Creator'), ('uc004-sc15b.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc15b.ie'), 'uc004-sc15b.ie', 'ns1.uc004-sc15b.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc15b.ie'), 'uc004-sc15b.ie', 'ns2.uc004-sc15b.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc15b.ie'), 'uc004-sc15b.ie', 'ns3.uc004-sc15b.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc004-sc15b.ie', 'ns1.uc004-sc15b.ie', '10.10.121.23', NULL, '1'), ('uc004-sc15b.ie', 'ns2.uc004-sc15b.ie', '1.3.4.2', NULL, '2'), ('uc004-sc15b.ie', 'ns3.uc004-sc15b.ie', '10.12.13.14', NULL, '3');

# UC#004: Make Payment for Existing Domain - UC004-SC16: Autorenewed
INSERT INTO DomainHist VALUES (NULL, 'uc004-sc16.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc004-sc16.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc16.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc16.ie'), 'uc004-sc16.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc16.ie'), 'uc004-sc16.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc16.ie'), 'uc004-sc16.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc16.ie'), 'uc004-sc16.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc004-sc16.ie', 'XOE550-IEDR', 'Admin'), ('uc004-sc16.ie', 'XBC189-IEDR', 'Billing'), ('uc004-sc16.ie', 'XBC189-IEDR', 'Creator'), ('uc004-sc16.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc16.ie'), 'uc004-sc16.ie', 'ns1.uc004-sc16.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc16.ie'), 'uc004-sc16.ie', 'ns2.uc004-sc16.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc16.ie'), 'uc004-sc16.ie', 'ns3.uc004-sc16.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc004-sc16.ie', 'ns1.uc004-sc16.ie', '10.10.121.23', NULL, '1'), ('uc004-sc16.ie', 'ns2.uc004-sc16.ie', '1.3.4.2', NULL, '2'), ('uc004-sc16.ie', 'ns3.uc004-sc16.ie', '10.12.13.14', NULL, '3');

# UC#004: Make Payment for Existing Domain - UC004-SC17: Locked
INSERT INTO DomainHist VALUES (NULL, 'uc004-sc17.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', 1, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, CURDATE() - Interval 9 Month, CURDATE() + Interval 3 Month);
INSERT INTO Domain VALUES ('uc004-sc17.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', 1, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, CURDATE() - Interval 9 Month, CURDATE() + Interval 3 Month, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc17.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc17.ie'), 'uc004-sc17.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc17.ie'), 'uc004-sc17.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc17.ie'), 'uc004-sc17.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc17.ie'), 'uc004-sc17.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc004-sc17.ie', 'XOE550-IEDR', 'Admin'), ('uc004-sc17.ie', 'XBC189-IEDR', 'Billing'), ('uc004-sc17.ie', 'XBC189-IEDR', 'Creator'), ('uc004-sc17.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc17.ie'), 'uc004-sc17.ie', 'ns1.uc004-sc17.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc17.ie'), 'uc004-sc17.ie', 'ns2.uc004-sc17.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc17.ie'), 'uc004-sc17.ie', 'ns3.uc004-sc17.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc004-sc17.ie', 'ns1.uc004-sc17.ie', '10.10.121.23', NULL, '1'), ('uc004-sc17.ie', 'ns2.uc004-sc17.ie', '1.3.4.2', NULL, '2'), ('uc004-sc17.ie', 'ns3.uc004-sc17.ie', '10.12.13.14', NULL, '3');

# UC#004: Make Payment for Existing Domain - UC004-SC18: Locked - Autorenew
INSERT INTO DomainHist VALUES (NULL, 'uc004-sc18.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', 65, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, CURDATE() - Interval 9 Month, CURDATE() + Interval 3 Month);
INSERT INTO Domain VALUES ('uc004-sc18.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', 65, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, CURDATE() - Interval 9 Month, CURDATE() + Interval 3 Month, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc18.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc18.ie'), 'uc004-sc18.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc18.ie'), 'uc004-sc18.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc18.ie'), 'uc004-sc18.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc18.ie'), 'uc004-sc18.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc004-sc18.ie', 'XOE550-IEDR', 'Admin'), ('uc004-sc18.ie', 'XBC189-IEDR', 'Billing'), ('uc004-sc18.ie', 'XBC189-IEDR', 'Creator'), ('uc004-sc18.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc18.ie'), 'uc004-sc18.ie', 'ns1.uc004-sc18.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc18.ie'), 'uc004-sc18.ie', 'ns2.uc004-sc18.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc18.ie'), 'uc004-sc18.ie', 'ns3.uc004-sc18.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc004-sc18.ie', 'ns1.uc004-sc18.ie', '10.10.121.23', NULL, '1'), ('uc004-sc18.ie', 'ns2.uc004-sc18.ie', '1.3.4.2', NULL, '2'), ('uc004-sc18.ie', 'ns3.uc004-sc18.ie', '10.12.13.14', NULL, '3');

# UC#004: Make Payment for Existing Domain - UC004-SC03 (Alt): Direct Involuntary NRP
INSERT INTO DomainHist VALUES (NULL, 'uc004-sc03alta.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XDD274-IEDR', NOW(), 'NO', 26, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc004-sc03alta.ie', 'Test Holder 0001', 3, 2, NULL, 1, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', 26, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc03alta.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc03alta.ie'), 'uc004-sc03alta.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc03alta.ie'), 'uc004-sc03alta.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc03alta.ie'), 'uc004-sc03alta.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc03alta.ie'), 'uc004-sc03alta.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc004-sc03alta.ie', 'XOE550-IEDR', 'Admin'), ('uc004-sc03alta.ie', 'XDD274-IEDR', 'Billing'), ('uc004-sc03alta.ie', 'XDD274-IEDR', 'Creator'), ('uc004-sc03alta.ie', 'XDD274-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc03alta.ie'), 'uc004-sc03alta.ie', 'ns1.uc004-sc03alta.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc03alta.ie'), 'uc004-sc03alta.ie', 'ns2.uc004-sc03alta.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc03alta.ie'), 'uc004-sc03alta.ie', 'ns3.uc004-sc03alta.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc004-sc03alta.ie', 'ns1.uc004-sc03alta.ie', '10.10.121.23', NULL, '1'), ('uc004-sc03alta.ie', 'ns2.uc004-sc03alta.ie', '1.3.4.2', NULL, '2'), ('uc004-sc03alta.ie', 'ns3.uc004-sc03alta.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc004-sc03altb.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XDD274-IEDR', NOW(), 'NO', 27, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc004-sc03altb.ie', 'Test Holder 0001', 3, 2, NULL, 1, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', 27, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc03altb.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc03altb.ie'), 'uc004-sc03altb.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc03altb.ie'), 'uc004-sc03altb.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc03altb.ie'), 'uc004-sc03altb.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc03altb.ie'), 'uc004-sc03altb.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc004-sc03altb.ie', 'XOE550-IEDR', 'Admin'), ('uc004-sc03altb.ie', 'XDD274-IEDR', 'Billing'), ('uc004-sc03altb.ie', 'XDD274-IEDR', 'Creator'), ('uc004-sc03altb.ie', 'XDD274-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc03altb.ie'), 'uc004-sc03altb.ie', 'ns1.uc004-sc03altb.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc03altb.ie'), 'uc004-sc03altb.ie', 'ns2.uc004-sc03altb.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc03altb.ie'), 'uc004-sc03altb.ie', 'ns3.uc004-sc03altb.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc004-sc03altb.ie', 'ns1.uc004-sc03altb.ie', '10.10.121.23', NULL, '1'), ('uc004-sc03altb.ie', 'ns2.uc004-sc03altb.ie', '1.3.4.2', NULL, '2'), ('uc004-sc03altb.ie', 'ns3.uc004-sc03altb.ie', '10.12.13.14', NULL, '3');

# UC#004: Make Payment for Existing Domain - UC004-SC04 (Alt): Direct using Debit Card
INSERT INTO DomainHist VALUES (NULL, 'uc004-sc04alt.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XDD274-IEDR', NOW(), 'NO', 25, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc004-sc04alt.ie', 'Test Holder 0001', 3, 2, NULL, 1, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', 25, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc04alt.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc04alt.ie'), 'uc004-sc04alt.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc04alt.ie'), 'uc004-sc04alt.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc04alt.ie'), 'uc004-sc04alt.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc04alt.ie'), 'uc004-sc04alt.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc004-sc04alt.ie', 'XOE550-IEDR', 'Admin'), ('uc004-sc04alt.ie', 'XDD274-IEDR', 'Billing'), ('uc004-sc04alt.ie', 'XDD274-IEDR', 'Creator'), ('uc004-sc04alt.ie', 'XDD274-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc04alt.ie'), 'uc004-sc04alt.ie', 'ns1.uc004-sc04alt.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc04alt.ie'), 'uc004-sc04alt.ie', 'ns2.uc004-sc04alt.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc04alt.ie'), 'uc004-sc04alt.ie', 'ns3.uc004-sc04alt.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc004-sc04alt.ie', 'ns1.uc004-sc04alt.ie', '10.10.121.23', NULL, '1'), ('uc004-sc04alt.ie', 'ns2.uc004-sc04alt.ie', '1.3.4.2', NULL, '2'), ('uc004-sc04alt.ie', 'ns3.uc004-sc04alt.ie', '10.12.13.14', NULL, '3');

# UC#004: Make Payment for Existing Domain - UC004-SC04 (Alt): Direct using Debit Card (NRP)
INSERT INTO DomainHist VALUES (NULL, 'uc004-sc04alta.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XDD274-IEDR', NOW(), 'NO', 27, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc004-sc04alta.ie', 'Test Holder 0001', 3, 2, NULL, 1, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', 27, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc04alta.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc04alta.ie'), 'uc004-sc04alta.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc04alta.ie'), 'uc004-sc04alta.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc04alta.ie'), 'uc004-sc04alta.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc04alta.ie'), 'uc004-sc04alta.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc004-sc04alta.ie', 'XOE550-IEDR', 'Admin'), ('uc004-sc04alta.ie', 'XDD274-IEDR', 'Billing'), ('uc004-sc04alta.ie', 'XDD274-IEDR', 'Creator'), ('uc004-sc04alta.ie', 'XDD274-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc04alta.ie'), 'uc004-sc04alta.ie', 'ns1.uc004-sc04alta.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc04alta.ie'), 'uc004-sc04alta.ie', 'ns2.uc004-sc04alta.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc04alta.ie'), 'uc004-sc04alta.ie', 'ns3.uc004-sc04alta.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc004-sc04alta.ie', 'ns1.uc004-sc04alta.ie', '10.10.121.23', NULL, '1'), ('uc004-sc04alta.ie', 'ns2.uc004-sc04alta.ie', '1.3.4.2', NULL, '2'), ('uc004-sc04alta.ie', 'ns3.uc004-sc04alta.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc004-sc04altb.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XDD274-IEDR', NOW(), 'NO', 26, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc004-sc04altb.ie', 'Test Holder 0001', 3, 2, NULL, 1, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', 26, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc04altb.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc04altb.ie'), 'uc004-sc04altb.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc04altb.ie'), 'uc004-sc04altb.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc04altb.ie'), 'uc004-sc04altb.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc04altb.ie'), 'uc004-sc04altb.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc004-sc04altb.ie', 'XOE550-IEDR', 'Admin'), ('uc004-sc04altb.ie', 'XDD274-IEDR', 'Billing'), ('uc004-sc04altb.ie', 'XDD274-IEDR', 'Creator'), ('uc004-sc04altb.ie', 'XDD274-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc04altb.ie'), 'uc004-sc04altb.ie', 'ns1.uc004-sc04altb.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc04altb.ie'), 'uc004-sc04altb.ie', 'ns2.uc004-sc04altb.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc04altb.ie'), 'uc004-sc04altb.ie', 'ns3.uc004-sc04altb.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc004-sc04altb.ie', 'ns1.uc004-sc04altb.ie', '10.10.121.23', NULL, '1'), ('uc004-sc04altb.ie', 'ns2.uc004-sc04altb.ie', '1.3.4.2', NULL, '2'), ('uc004-sc04altb.ie', 'ns3.uc004-sc04altb.ie', '10.12.13.14', NULL, '3');

# UC#004: Make Payment for Existing Domain - UC004-SC01 (Alt): Registrar Happy Path
INSERT INTO DomainHist VALUES (NULL, 'uc004-sc01alt1a.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc004-sc01alt1a.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1a.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1a.ie'), 'uc004-sc01alt1a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1a.ie'), 'uc004-sc01alt1a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1a.ie'), 'uc004-sc01alt1a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1a.ie'), 'uc004-sc01alt1a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc004-sc01alt1a.ie', 'XOE550-IEDR', 'Admin'), ('uc004-sc01alt1a.ie', 'XBC189-IEDR', 'Billing'), ('uc004-sc01alt1a.ie', 'XBC189-IEDR', 'Creator'), ('uc004-sc01alt1a.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1a.ie'), 'uc004-sc01alt1a.ie', 'ns1.uc004-sc01alt1a.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1a.ie'), 'uc004-sc01alt1a.ie', 'ns2.uc004-sc01alt1a.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1a.ie'), 'uc004-sc01alt1a.ie', 'ns3.uc004-sc01alt1a.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc004-sc01alt1a.ie', 'ns1.uc004-sc01alt1a.ie', '10.10.121.23', NULL, '1'), ('uc004-sc01alt1a.ie', 'ns2.uc004-sc01alt1a.ie', '1.3.4.2', NULL, '2'), ('uc004-sc01alt1a.ie', 'ns3.uc004-sc01alt1a.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc004-sc01alt1b.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc004-sc01alt1b.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1b.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1b.ie'), 'uc004-sc01alt1b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1b.ie'), 'uc004-sc01alt1b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1b.ie'), 'uc004-sc01alt1b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1b.ie'), 'uc004-sc01alt1b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc004-sc01alt1b.ie', 'XOE550-IEDR', 'Admin'), ('uc004-sc01alt1b.ie', 'XBC189-IEDR', 'Billing'), ('uc004-sc01alt1b.ie', 'XBC189-IEDR', 'Creator'), ('uc004-sc01alt1b.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1b.ie'), 'uc004-sc01alt1b.ie', 'ns1.uc004-sc01alt1b.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1b.ie'), 'uc004-sc01alt1b.ie', 'ns2.uc004-sc01alt1b.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1b.ie'), 'uc004-sc01alt1b.ie', 'ns3.uc004-sc01alt1b.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc004-sc01alt1b.ie', 'ns1.uc004-sc01alt1b.ie', '10.10.121.23', NULL, '1'), ('uc004-sc01alt1b.ie', 'ns2.uc004-sc01alt1b.ie', '1.3.4.2', NULL, '2'), ('uc004-sc01alt1b.ie', 'ns3.uc004-sc01alt1b.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc004-sc01alt1c.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc004-sc01alt1c.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1c.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1c.ie'), 'uc004-sc01alt1c.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1c.ie'), 'uc004-sc01alt1c.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1c.ie'), 'uc004-sc01alt1c.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1c.ie'), 'uc004-sc01alt1c.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc004-sc01alt1c.ie', 'XOE550-IEDR', 'Admin'), ('uc004-sc01alt1c.ie', 'XBC189-IEDR', 'Billing'), ('uc004-sc01alt1c.ie', 'XBC189-IEDR', 'Creator'), ('uc004-sc01alt1c.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1c.ie'), 'uc004-sc01alt1c.ie', 'ns1.uc004-sc01alt1c.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1c.ie'), 'uc004-sc01alt1c.ie', 'ns2.uc004-sc01alt1c.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1c.ie'), 'uc004-sc01alt1c.ie', 'ns3.uc004-sc01alt1c.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc004-sc01alt1c.ie', 'ns1.uc004-sc01alt1c.ie', '10.10.121.23', NULL, '1'), ('uc004-sc01alt1c.ie', 'ns2.uc004-sc01alt1c.ie', '1.3.4.2', NULL, '2'), ('uc004-sc01alt1c.ie', 'ns3.uc004-sc01alt1c.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc004-sc01alt1d.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc004-sc01alt1d.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1d.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1d.ie'), 'uc004-sc01alt1d.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1d.ie'), 'uc004-sc01alt1d.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1d.ie'), 'uc004-sc01alt1d.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1d.ie'), 'uc004-sc01alt1d.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc004-sc01alt1d.ie', 'XOE550-IEDR', 'Admin'), ('uc004-sc01alt1d.ie', 'XBC189-IEDR', 'Billing'), ('uc004-sc01alt1d.ie', 'XBC189-IEDR', 'Creator'), ('uc004-sc01alt1d.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1d.ie'), 'uc004-sc01alt1d.ie', 'ns1.uc004-sc01alt1d.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1d.ie'), 'uc004-sc01alt1d.ie', 'ns2.uc004-sc01alt1d.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1d.ie'), 'uc004-sc01alt1d.ie', 'ns3.uc004-sc01alt1d.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc004-sc01alt1d.ie', 'ns1.uc004-sc01alt1d.ie', '10.10.121.23', NULL, '1'), ('uc004-sc01alt1d.ie', 'ns2.uc004-sc01alt1d.ie', '1.3.4.2', NULL, '2'), ('uc004-sc01alt1d.ie', 'ns3.uc004-sc01alt1d.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc004-sc01alt1e.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc004-sc01alt1e.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1e.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1e.ie'), 'uc004-sc01alt1e.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1e.ie'), 'uc004-sc01alt1e.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1e.ie'), 'uc004-sc01alt1e.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1e.ie'), 'uc004-sc01alt1e.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc004-sc01alt1e.ie', 'XOE550-IEDR', 'Admin'), ('uc004-sc01alt1e.ie', 'XBC189-IEDR', 'Billing'), ('uc004-sc01alt1e.ie', 'XBC189-IEDR', 'Creator'), ('uc004-sc01alt1e.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1e.ie'), 'uc004-sc01alt1e.ie', 'ns1.uc004-sc01alt1e.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1e.ie'), 'uc004-sc01alt1e.ie', 'ns2.uc004-sc01alt1e.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1e.ie'), 'uc004-sc01alt1e.ie', 'ns3.uc004-sc01alt1e.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc004-sc01alt1e.ie', 'ns1.uc004-sc01alt1e.ie', '10.10.121.23', NULL, '1'), ('uc004-sc01alt1e.ie', 'ns2.uc004-sc01alt1e.ie', '1.3.4.2', NULL, '2'), ('uc004-sc01alt1e.ie', 'ns3.uc004-sc01alt1e.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc004-sc01alt1f.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc004-sc01alt1f.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1f.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1f.ie'), 'uc004-sc01alt1f.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1f.ie'), 'uc004-sc01alt1f.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1f.ie'), 'uc004-sc01alt1f.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1f.ie'), 'uc004-sc01alt1f.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc004-sc01alt1f.ie', 'XOE550-IEDR', 'Admin'), ('uc004-sc01alt1f.ie', 'XBC189-IEDR', 'Billing'), ('uc004-sc01alt1f.ie', 'XBC189-IEDR', 'Creator'), ('uc004-sc01alt1f.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1f.ie'), 'uc004-sc01alt1f.ie', 'ns1.uc004-sc01alt1f.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1f.ie'), 'uc004-sc01alt1f.ie', 'ns2.uc004-sc01alt1f.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1f.ie'), 'uc004-sc01alt1f.ie', 'ns3.uc004-sc01alt1f.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc004-sc01alt1f.ie', 'ns1.uc004-sc01alt1f.ie', '10.10.121.23', NULL, '1'), ('uc004-sc01alt1f.ie', 'ns2.uc004-sc01alt1f.ie', '1.3.4.2', NULL, '2'), ('uc004-sc01alt1f.ie', 'ns3.uc004-sc01alt1f.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc004-sc01alt1g.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc004-sc01alt1g.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1g.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1g.ie'), 'uc004-sc01alt1g.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1g.ie'), 'uc004-sc01alt1g.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1g.ie'), 'uc004-sc01alt1g.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1g.ie'), 'uc004-sc01alt1g.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc004-sc01alt1g.ie', 'XOE550-IEDR', 'Admin'), ('uc004-sc01alt1g.ie', 'XBC189-IEDR', 'Billing'), ('uc004-sc01alt1g.ie', 'XBC189-IEDR', 'Creator'), ('uc004-sc01alt1g.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1g.ie'), 'uc004-sc01alt1g.ie', 'ns1.uc004-sc01alt1g.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1g.ie'), 'uc004-sc01alt1g.ie', 'ns2.uc004-sc01alt1g.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1g.ie'), 'uc004-sc01alt1g.ie', 'ns3.uc004-sc01alt1g.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc004-sc01alt1g.ie', 'ns1.uc004-sc01alt1g.ie', '10.10.121.23', NULL, '1'), ('uc004-sc01alt1g.ie', 'ns2.uc004-sc01alt1g.ie', '1.3.4.2', NULL, '2'), ('uc004-sc01alt1g.ie', 'ns3.uc004-sc01alt1g.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc004-sc01alt1h.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc004-sc01alt1h.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1h.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1h.ie'), 'uc004-sc01alt1h.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1h.ie'), 'uc004-sc01alt1h.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1h.ie'), 'uc004-sc01alt1h.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1h.ie'), 'uc004-sc01alt1h.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc004-sc01alt1h.ie', 'XOE550-IEDR', 'Admin'), ('uc004-sc01alt1h.ie', 'XBC189-IEDR', 'Billing'), ('uc004-sc01alt1h.ie', 'XBC189-IEDR', 'Creator'), ('uc004-sc01alt1h.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1h.ie'), 'uc004-sc01alt1h.ie', 'ns1.uc004-sc01alt1h.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1h.ie'), 'uc004-sc01alt1h.ie', 'ns2.uc004-sc01alt1h.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1h.ie'), 'uc004-sc01alt1h.ie', 'ns3.uc004-sc01alt1h.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc004-sc01alt1h.ie', 'ns1.uc004-sc01alt1h.ie', '10.10.121.23', NULL, '1'), ('uc004-sc01alt1h.ie', 'ns2.uc004-sc01alt1h.ie', '1.3.4.2', NULL, '2'), ('uc004-sc01alt1h.ie', 'ns3.uc004-sc01alt1h.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc004-sc01alt1i.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc004-sc01alt1i.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1i.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1i.ie'), 'uc004-sc01alt1i.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1i.ie'), 'uc004-sc01alt1i.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1i.ie'), 'uc004-sc01alt1i.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1i.ie'), 'uc004-sc01alt1i.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc004-sc01alt1i.ie', 'XOE550-IEDR', 'Admin'), ('uc004-sc01alt1i.ie', 'XBC189-IEDR', 'Billing'), ('uc004-sc01alt1i.ie', 'XBC189-IEDR', 'Creator'), ('uc004-sc01alt1i.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1i.ie'), 'uc004-sc01alt1i.ie', 'ns1.uc004-sc01alt1i.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1i.ie'), 'uc004-sc01alt1i.ie', 'ns2.uc004-sc01alt1i.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1i.ie'), 'uc004-sc01alt1i.ie', 'ns3.uc004-sc01alt1i.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc004-sc01alt1i.ie', 'ns1.uc004-sc01alt1i.ie', '10.10.121.23', NULL, '1'), ('uc004-sc01alt1i.ie', 'ns2.uc004-sc01alt1i.ie', '1.3.4.2', NULL, '2'), ('uc004-sc01alt1i.ie', 'ns3.uc004-sc01alt1i.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc004-sc01alt1j.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc004-sc01alt1j.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1j.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1j.ie'), 'uc004-sc01alt1j.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1j.ie'), 'uc004-sc01alt1j.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1j.ie'), 'uc004-sc01alt1j.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1j.ie'), 'uc004-sc01alt1j.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc004-sc01alt1j.ie', 'XOE550-IEDR', 'Admin'), ('uc004-sc01alt1j.ie', 'XBC189-IEDR', 'Billing'), ('uc004-sc01alt1j.ie', 'XBC189-IEDR', 'Creator'), ('uc004-sc01alt1j.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1j.ie'), 'uc004-sc01alt1j.ie', 'ns1.uc004-sc01alt1j.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1j.ie'), 'uc004-sc01alt1j.ie', 'ns2.uc004-sc01alt1j.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt1j.ie'), 'uc004-sc01alt1j.ie', 'ns3.uc004-sc01alt1j.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc004-sc01alt1j.ie', 'ns1.uc004-sc01alt1j.ie', '10.10.121.23', NULL, '1'), ('uc004-sc01alt1j.ie', 'ns2.uc004-sc01alt1j.ie', '1.3.4.2', NULL, '2'), ('uc004-sc01alt1j.ie', 'ns3.uc004-sc01alt1j.ie', '10.12.13.14', NULL, '3');

# UC#004: Make Payment for Existing Domain - UC004-SC01 (Alt): Registrar Happy Path (Future Renewals)
INSERT INTO DomainHist VALUES (NULL, 'uc004-sc01alt2a.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 11 Month, CURDATE() + Interval 1 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc004-sc01alt2a.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 11 Month, CURDATE() + Interval 1 Month, NOW(), 'Test Remark', 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2a.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2a.ie'), 'uc004-sc01alt2a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2a.ie'), 'uc004-sc01alt2a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2a.ie'), 'uc004-sc01alt2a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2a.ie'), 'uc004-sc01alt2a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc004-sc01alt2a.ie', 'XOE550-IEDR', 'Admin'), ('uc004-sc01alt2a.ie', 'XBC189-IEDR', 'Billing'), ('uc004-sc01alt2a.ie', 'XBC189-IEDR', 'Creator'), ('uc004-sc01alt2a.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2a.ie'), 'uc004-sc01alt2a.ie', 'ns1.uc004-sc01alt2a.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2a.ie'), 'uc004-sc01alt2a.ie', 'ns2.uc004-sc01alt2a.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2a.ie'), 'uc004-sc01alt2a.ie', 'ns3.uc004-sc01alt2a.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc004-sc01alt2a.ie', 'ns1.uc004-sc01alt2a.ie', '10.10.121.23', NULL, '1'), ('uc004-sc01alt2a.ie', 'ns2.uc004-sc01alt2a.ie', '1.3.4.2', NULL, '2'), ('uc004-sc01alt2a.ie', 'ns3.uc004-sc01alt2a.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc004-sc01alt2b.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 11 Month, CURDATE() + Interval 1 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc004-sc01alt2b.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 11 Month, CURDATE() + Interval 1 Month, NOW(), 'Test Remark', 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2b.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2b.ie'), 'uc004-sc01alt2b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2b.ie'), 'uc004-sc01alt2b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2b.ie'), 'uc004-sc01alt2b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2b.ie'), 'uc004-sc01alt2b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc004-sc01alt2b.ie', 'XOE550-IEDR', 'Admin'), ('uc004-sc01alt2b.ie', 'XBC189-IEDR', 'Billing'), ('uc004-sc01alt2b.ie', 'XBC189-IEDR', 'Creator'), ('uc004-sc01alt2b.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2b.ie'), 'uc004-sc01alt2b.ie', 'ns1.uc004-sc01alt2b.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2b.ie'), 'uc004-sc01alt2b.ie', 'ns2.uc004-sc01alt2b.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2b.ie'), 'uc004-sc01alt2b.ie', 'ns3.uc004-sc01alt2b.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc004-sc01alt2b.ie', 'ns1.uc004-sc01alt2b.ie', '10.10.121.23', NULL, '1'), ('uc004-sc01alt2b.ie', 'ns2.uc004-sc01alt2b.ie', '1.3.4.2', NULL, '2'), ('uc004-sc01alt2b.ie', 'ns3.uc004-sc01alt2b.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc004-sc01alt2c.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 11 Month, CURDATE() + Interval 1 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc004-sc01alt2c.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 11 Month, CURDATE() + Interval 1 Month, NOW(), 'Test Remark', 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2c.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2c.ie'), 'uc004-sc01alt2c.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2c.ie'), 'uc004-sc01alt2c.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2c.ie'), 'uc004-sc01alt2c.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2c.ie'), 'uc004-sc01alt2c.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc004-sc01alt2c.ie', 'XOE550-IEDR', 'Admin'), ('uc004-sc01alt2c.ie', 'XBC189-IEDR', 'Billing'), ('uc004-sc01alt2c.ie', 'XBC189-IEDR', 'Creator'), ('uc004-sc01alt2c.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2c.ie'), 'uc004-sc01alt2c.ie', 'ns1.uc004-sc01alt2c.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2c.ie'), 'uc004-sc01alt2c.ie', 'ns2.uc004-sc01alt2c.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2c.ie'), 'uc004-sc01alt2c.ie', 'ns3.uc004-sc01alt2c.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc004-sc01alt2c.ie', 'ns1.uc004-sc01alt2c.ie', '10.10.121.23', NULL, '1'), ('uc004-sc01alt2c.ie', 'ns2.uc004-sc01alt2c.ie', '1.3.4.2', NULL, '2'), ('uc004-sc01alt2c.ie', 'ns3.uc004-sc01alt2c.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc004-sc01alt2d.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 11 Month, CURDATE() + Interval 1 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc004-sc01alt2d.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 11 Month, CURDATE() + Interval 1 Month, NOW(), 'Test Remark', 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2d.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2d.ie'), 'uc004-sc01alt2d.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2d.ie'), 'uc004-sc01alt2d.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2d.ie'), 'uc004-sc01alt2d.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2d.ie'), 'uc004-sc01alt2d.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc004-sc01alt2d.ie', 'XOE550-IEDR', 'Admin'), ('uc004-sc01alt2d.ie', 'XBC189-IEDR', 'Billing'), ('uc004-sc01alt2d.ie', 'XBC189-IEDR', 'Creator'), ('uc004-sc01alt2d.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2d.ie'), 'uc004-sc01alt2d.ie', 'ns1.uc004-sc01alt2d.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2d.ie'), 'uc004-sc01alt2d.ie', 'ns2.uc004-sc01alt2d.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2d.ie'), 'uc004-sc01alt2d.ie', 'ns3.uc004-sc01alt2d.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc004-sc01alt2d.ie', 'ns1.uc004-sc01alt2d.ie', '10.10.121.23', NULL, '1'), ('uc004-sc01alt2d.ie', 'ns2.uc004-sc01alt2d.ie', '1.3.4.2', NULL, '2'), ('uc004-sc01alt2d.ie', 'ns3.uc004-sc01alt2d.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc004-sc01alt2e.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 11 Month, CURDATE() + Interval 1 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc004-sc01alt2e.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 11 Month, CURDATE() + Interval 1 Month, NOW(), 'Test Remark', 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2e.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2e.ie'), 'uc004-sc01alt2e.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2e.ie'), 'uc004-sc01alt2e.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2e.ie'), 'uc004-sc01alt2e.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2e.ie'), 'uc004-sc01alt2e.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc004-sc01alt2e.ie', 'XOE550-IEDR', 'Admin'), ('uc004-sc01alt2e.ie', 'XBC189-IEDR', 'Billing'), ('uc004-sc01alt2e.ie', 'XBC189-IEDR', 'Creator'), ('uc004-sc01alt2e.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2e.ie'), 'uc004-sc01alt2e.ie', 'ns1.uc004-sc01alt2e.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2e.ie'), 'uc004-sc01alt2e.ie', 'ns2.uc004-sc01alt2e.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2e.ie'), 'uc004-sc01alt2e.ie', 'ns3.uc004-sc01alt2e.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc004-sc01alt2e.ie', 'ns1.uc004-sc01alt2e.ie', '10.10.121.23', NULL, '1'), ('uc004-sc01alt2e.ie', 'ns2.uc004-sc01alt2e.ie', '1.3.4.2', NULL, '2'), ('uc004-sc01alt2e.ie', 'ns3.uc004-sc01alt2e.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc004-sc01alt2f.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 11 Month, CURDATE() + Interval 1 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc004-sc01alt2f.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 11 Month, CURDATE() + Interval 1 Month, NOW(), 'Test Remark', 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2f.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2f.ie'), 'uc004-sc01alt2f.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2f.ie'), 'uc004-sc01alt2f.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2f.ie'), 'uc004-sc01alt2f.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2f.ie'), 'uc004-sc01alt2f.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc004-sc01alt2f.ie', 'XOE550-IEDR', 'Admin'), ('uc004-sc01alt2f.ie', 'XBC189-IEDR', 'Billing'), ('uc004-sc01alt2f.ie', 'XBC189-IEDR', 'Creator'), ('uc004-sc01alt2f.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2f.ie'), 'uc004-sc01alt2f.ie', 'ns1.uc004-sc01alt2f.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2f.ie'), 'uc004-sc01alt2f.ie', 'ns2.uc004-sc01alt2f.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2f.ie'), 'uc004-sc01alt2f.ie', 'ns3.uc004-sc01alt2f.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc004-sc01alt2f.ie', 'ns1.uc004-sc01alt2f.ie', '10.10.121.23', NULL, '1'), ('uc004-sc01alt2f.ie', 'ns2.uc004-sc01alt2f.ie', '1.3.4.2', NULL, '2'), ('uc004-sc01alt2f.ie', 'ns3.uc004-sc01alt2f.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc004-sc01alt2g.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 11 Month, CURDATE() + Interval 1 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc004-sc01alt2g.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 11 Month, CURDATE() + Interval 1 Month, NOW(), 'Test Remark', 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2g.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2g.ie'), 'uc004-sc01alt2g.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2g.ie'), 'uc004-sc01alt2g.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2g.ie'), 'uc004-sc01alt2g.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2g.ie'), 'uc004-sc01alt2g.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc004-sc01alt2g.ie', 'XOE550-IEDR', 'Admin'), ('uc004-sc01alt2g.ie', 'XBC189-IEDR', 'Billing'), ('uc004-sc01alt2g.ie', 'XBC189-IEDR', 'Creator'), ('uc004-sc01alt2g.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2g.ie'), 'uc004-sc01alt2g.ie', 'ns1.uc004-sc01alt2g.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2g.ie'), 'uc004-sc01alt2g.ie', 'ns2.uc004-sc01alt2g.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2g.ie'), 'uc004-sc01alt2g.ie', 'ns3.uc004-sc01alt2g.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc004-sc01alt2g.ie', 'ns1.uc004-sc01alt2g.ie', '10.10.121.23', NULL, '1'), ('uc004-sc01alt2g.ie', 'ns2.uc004-sc01alt2g.ie', '1.3.4.2', NULL, '2'), ('uc004-sc01alt2g.ie', 'ns3.uc004-sc01alt2g.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc004-sc01alt2h.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 11 Month, CURDATE() + Interval 1 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc004-sc01alt2h.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 11 Month, CURDATE() + Interval 1 Month, NOW(), 'Test Remark', 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2h.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2h.ie'), 'uc004-sc01alt2h.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2h.ie'), 'uc004-sc01alt2h.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2h.ie'), 'uc004-sc01alt2h.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2h.ie'), 'uc004-sc01alt2h.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc004-sc01alt2h.ie', 'XOE550-IEDR', 'Admin'), ('uc004-sc01alt2h.ie', 'XBC189-IEDR', 'Billing'), ('uc004-sc01alt2h.ie', 'XBC189-IEDR', 'Creator'), ('uc004-sc01alt2h.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2h.ie'), 'uc004-sc01alt2h.ie', 'ns1.uc004-sc01alt2h.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2h.ie'), 'uc004-sc01alt2h.ie', 'ns2.uc004-sc01alt2h.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2h.ie'), 'uc004-sc01alt2h.ie', 'ns3.uc004-sc01alt2h.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc004-sc01alt2h.ie', 'ns1.uc004-sc01alt2h.ie', '10.10.121.23', NULL, '1'), ('uc004-sc01alt2h.ie', 'ns2.uc004-sc01alt2h.ie', '1.3.4.2', NULL, '2'), ('uc004-sc01alt2h.ie', 'ns3.uc004-sc01alt2h.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc004-sc01alt2i.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 11 Month, CURDATE() + Interval 1 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc004-sc01alt2i.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 11 Month, CURDATE() + Interval 1 Month, NOW(), 'Test Remark', 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2i.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2i.ie'), 'uc004-sc01alt2i.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2i.ie'), 'uc004-sc01alt2i.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2i.ie'), 'uc004-sc01alt2i.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2i.ie'), 'uc004-sc01alt2i.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc004-sc01alt2i.ie', 'XOE550-IEDR', 'Admin'), ('uc004-sc01alt2i.ie', 'XBC189-IEDR', 'Billing'), ('uc004-sc01alt2i.ie', 'XBC189-IEDR', 'Creator'), ('uc004-sc01alt2i.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2i.ie'), 'uc004-sc01alt2i.ie', 'ns1.uc004-sc01alt2i.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2i.ie'), 'uc004-sc01alt2i.ie', 'ns2.uc004-sc01alt2i.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2i.ie'), 'uc004-sc01alt2i.ie', 'ns3.uc004-sc01alt2i.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc004-sc01alt2i.ie', 'ns1.uc004-sc01alt2i.ie', '10.10.121.23', NULL, '1'), ('uc004-sc01alt2i.ie', 'ns2.uc004-sc01alt2i.ie', '1.3.4.2', NULL, '2'), ('uc004-sc01alt2i.ie', 'ns3.uc004-sc01alt2i.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc004-sc01alt2j.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 11 Month, CURDATE() + Interval 1 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc004-sc01alt2j.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 11 Month, CURDATE() + Interval 1 Month, NOW(), 'Test Remark', 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2j.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2j.ie'), 'uc004-sc01alt2j.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2j.ie'), 'uc004-sc01alt2j.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2j.ie'), 'uc004-sc01alt2j.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2j.ie'), 'uc004-sc01alt2j.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc004-sc01alt2j.ie', 'XOE550-IEDR', 'Admin'), ('uc004-sc01alt2j.ie', 'XBC189-IEDR', 'Billing'), ('uc004-sc01alt2j.ie', 'XBC189-IEDR', 'Creator'), ('uc004-sc01alt2j.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2j.ie'), 'uc004-sc01alt2j.ie', 'ns1.uc004-sc01alt2j.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2j.ie'), 'uc004-sc01alt2j.ie', 'ns2.uc004-sc01alt2j.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt2j.ie'), 'uc004-sc01alt2j.ie', 'ns3.uc004-sc01alt2j.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc004-sc01alt2j.ie', 'ns1.uc004-sc01alt2j.ie', '10.10.121.23', NULL, '1'), ('uc004-sc01alt2j.ie', 'ns2.uc004-sc01alt2j.ie', '1.3.4.2', NULL, '2'), ('uc004-sc01alt2j.ie', 'ns3.uc004-sc01alt2j.ie', '10.12.13.14', NULL, '3');

# UC#004: Make Payment for Existing Domain - UC004-SC01 (Alt): Registrar Happy Path (Future Renewals II)
INSERT INTO DomainHist VALUES (NULL, 'uc004-sc01alt3a.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 1 Month, CURDATE() + Interval 11 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc004-sc01alt3a.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 1 Month, CURDATE() + Interval 11 Month, NOW(), 'Test Remark', 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3a.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3a.ie'), 'uc004-sc01alt3a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3a.ie'), 'uc004-sc01alt3a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3a.ie'), 'uc004-sc01alt3a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3a.ie'), 'uc004-sc01alt3a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc004-sc01alt3a.ie', 'XOE550-IEDR', 'Admin'), ('uc004-sc01alt3a.ie', 'XBC189-IEDR', 'Billing'), ('uc004-sc01alt3a.ie', 'XBC189-IEDR', 'Creator'), ('uc004-sc01alt3a.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3a.ie'), 'uc004-sc01alt3a.ie', 'ns1.uc004-sc01alt3a.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3a.ie'), 'uc004-sc01alt3a.ie', 'ns2.uc004-sc01alt3a.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3a.ie'), 'uc004-sc01alt3a.ie', 'ns3.uc004-sc01alt3a.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc004-sc01alt3a.ie', 'ns1.uc004-sc01alt3a.ie', '10.10.121.23', NULL, '1'), ('uc004-sc01alt3a.ie', 'ns2.uc004-sc01alt3a.ie', '1.3.4.2', NULL, '2'), ('uc004-sc01alt3a.ie', 'ns3.uc004-sc01alt3a.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc004-sc01alt3b.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 1 Month, CURDATE() + Interval 11 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc004-sc01alt3b.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 1 Month, CURDATE() + Interval 11 Month, NOW(), 'Test Remark', 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3b.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3b.ie'), 'uc004-sc01alt3b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3b.ie'), 'uc004-sc01alt3b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3b.ie'), 'uc004-sc01alt3b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3b.ie'), 'uc004-sc01alt3b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc004-sc01alt3b.ie', 'XOE550-IEDR', 'Admin'), ('uc004-sc01alt3b.ie', 'XBC189-IEDR', 'Billing'), ('uc004-sc01alt3b.ie', 'XBC189-IEDR', 'Creator'), ('uc004-sc01alt3b.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3b.ie'), 'uc004-sc01alt3b.ie', 'ns1.uc004-sc01alt3b.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3b.ie'), 'uc004-sc01alt3b.ie', 'ns2.uc004-sc01alt3b.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3b.ie'), 'uc004-sc01alt3b.ie', 'ns3.uc004-sc01alt3b.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc004-sc01alt3b.ie', 'ns1.uc004-sc01alt3b.ie', '10.10.121.23', NULL, '1'), ('uc004-sc01alt3b.ie', 'ns2.uc004-sc01alt3b.ie', '1.3.4.2', NULL, '2'), ('uc004-sc01alt3b.ie', 'ns3.uc004-sc01alt3b.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc004-sc01alt3c.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 1 Month, CURDATE() + Interval 11 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc004-sc01alt3c.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 1 Month, CURDATE() + Interval 11 Month, NOW(), 'Test Remark', 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3c.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3c.ie'), 'uc004-sc01alt3c.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3c.ie'), 'uc004-sc01alt3c.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3c.ie'), 'uc004-sc01alt3c.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3c.ie'), 'uc004-sc01alt3c.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc004-sc01alt3c.ie', 'XOE550-IEDR', 'Admin'), ('uc004-sc01alt3c.ie', 'XBC189-IEDR', 'Billing'), ('uc004-sc01alt3c.ie', 'XBC189-IEDR', 'Creator'), ('uc004-sc01alt3c.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3c.ie'), 'uc004-sc01alt3c.ie', 'ns1.uc004-sc01alt3c.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3c.ie'), 'uc004-sc01alt3c.ie', 'ns2.uc004-sc01alt3c.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3c.ie'), 'uc004-sc01alt3c.ie', 'ns3.uc004-sc01alt3c.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc004-sc01alt3c.ie', 'ns1.uc004-sc01alt3c.ie', '10.10.121.23', NULL, '1'), ('uc004-sc01alt3c.ie', 'ns2.uc004-sc01alt3c.ie', '1.3.4.2', NULL, '2'), ('uc004-sc01alt3c.ie', 'ns3.uc004-sc01alt3c.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc004-sc01alt3d.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 1 Month, CURDATE() + Interval 11 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc004-sc01alt3d.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 1 Month, CURDATE() + Interval 11 Month, NOW(), 'Test Remark', 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3d.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3d.ie'), 'uc004-sc01alt3d.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3d.ie'), 'uc004-sc01alt3d.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3d.ie'), 'uc004-sc01alt3d.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3d.ie'), 'uc004-sc01alt3d.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc004-sc01alt3d.ie', 'XOE550-IEDR', 'Admin'), ('uc004-sc01alt3d.ie', 'XBC189-IEDR', 'Billing'), ('uc004-sc01alt3d.ie', 'XBC189-IEDR', 'Creator'), ('uc004-sc01alt3d.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3d.ie'), 'uc004-sc01alt3d.ie', 'ns1.uc004-sc01alt3d.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3d.ie'), 'uc004-sc01alt3d.ie', 'ns2.uc004-sc01alt3d.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3d.ie'), 'uc004-sc01alt3d.ie', 'ns3.uc004-sc01alt3d.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc004-sc01alt3d.ie', 'ns1.uc004-sc01alt3d.ie', '10.10.121.23', NULL, '1'), ('uc004-sc01alt3d.ie', 'ns2.uc004-sc01alt3d.ie', '1.3.4.2', NULL, '2'), ('uc004-sc01alt3d.ie', 'ns3.uc004-sc01alt3d.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc004-sc01alt3e.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 1 Month, CURDATE() + Interval 11 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc004-sc01alt3e.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 1 Month, CURDATE() + Interval 11 Month, NOW(), 'Test Remark', 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3e.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3e.ie'), 'uc004-sc01alt3e.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3e.ie'), 'uc004-sc01alt3e.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3e.ie'), 'uc004-sc01alt3e.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3e.ie'), 'uc004-sc01alt3e.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc004-sc01alt3e.ie', 'XOE550-IEDR', 'Admin'), ('uc004-sc01alt3e.ie', 'XBC189-IEDR', 'Billing'), ('uc004-sc01alt3e.ie', 'XBC189-IEDR', 'Creator'), ('uc004-sc01alt3e.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3e.ie'), 'uc004-sc01alt3e.ie', 'ns1.uc004-sc01alt3e.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3e.ie'), 'uc004-sc01alt3e.ie', 'ns2.uc004-sc01alt3e.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3e.ie'), 'uc004-sc01alt3e.ie', 'ns3.uc004-sc01alt3e.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc004-sc01alt3e.ie', 'ns1.uc004-sc01alt3e.ie', '10.10.121.23', NULL, '1'), ('uc004-sc01alt3e.ie', 'ns2.uc004-sc01alt3e.ie', '1.3.4.2', NULL, '2'), ('uc004-sc01alt3e.ie', 'ns3.uc004-sc01alt3e.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc004-sc01alt3f.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 1 Month, CURDATE() + Interval 11 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc004-sc01alt3f.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 1 Month, CURDATE() + Interval 11 Month, NOW(), 'Test Remark', 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3f.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3f.ie'), 'uc004-sc01alt3f.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3f.ie'), 'uc004-sc01alt3f.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3f.ie'), 'uc004-sc01alt3f.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3f.ie'), 'uc004-sc01alt3f.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc004-sc01alt3f.ie', 'XOE550-IEDR', 'Admin'), ('uc004-sc01alt3f.ie', 'XBC189-IEDR', 'Billing'), ('uc004-sc01alt3f.ie', 'XBC189-IEDR', 'Creator'), ('uc004-sc01alt3f.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3f.ie'), 'uc004-sc01alt3f.ie', 'ns1.uc004-sc01alt3f.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3f.ie'), 'uc004-sc01alt3f.ie', 'ns2.uc004-sc01alt3f.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3f.ie'), 'uc004-sc01alt3f.ie', 'ns3.uc004-sc01alt3f.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc004-sc01alt3f.ie', 'ns1.uc004-sc01alt3f.ie', '10.10.121.23', NULL, '1'), ('uc004-sc01alt3f.ie', 'ns2.uc004-sc01alt3f.ie', '1.3.4.2', NULL, '2'), ('uc004-sc01alt3f.ie', 'ns3.uc004-sc01alt3f.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc004-sc01alt3g.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 1 Month, CURDATE() + Interval 11 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc004-sc01alt3g.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 1 Month, CURDATE() + Interval 11 Month, NOW(), 'Test Remark', 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3g.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3g.ie'), 'uc004-sc01alt3g.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3g.ie'), 'uc004-sc01alt3g.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3g.ie'), 'uc004-sc01alt3g.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3g.ie'), 'uc004-sc01alt3g.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc004-sc01alt3g.ie', 'XOE550-IEDR', 'Admin'), ('uc004-sc01alt3g.ie', 'XBC189-IEDR', 'Billing'), ('uc004-sc01alt3g.ie', 'XBC189-IEDR', 'Creator'), ('uc004-sc01alt3g.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3g.ie'), 'uc004-sc01alt3g.ie', 'ns1.uc004-sc01alt3g.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3g.ie'), 'uc004-sc01alt3g.ie', 'ns2.uc004-sc01alt3g.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3g.ie'), 'uc004-sc01alt3g.ie', 'ns3.uc004-sc01alt3g.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc004-sc01alt3g.ie', 'ns1.uc004-sc01alt3g.ie', '10.10.121.23', NULL, '1'), ('uc004-sc01alt3g.ie', 'ns2.uc004-sc01alt3g.ie', '1.3.4.2', NULL, '2'), ('uc004-sc01alt3g.ie', 'ns3.uc004-sc01alt3g.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc004-sc01alt3h.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 1 Month, CURDATE() + Interval 11 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc004-sc01alt3h.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 1 Month, CURDATE() + Interval 11 Month, NOW(), 'Test Remark', 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3h.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3h.ie'), 'uc004-sc01alt3h.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3h.ie'), 'uc004-sc01alt3h.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3h.ie'), 'uc004-sc01alt3h.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3h.ie'), 'uc004-sc01alt3h.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc004-sc01alt3h.ie', 'XOE550-IEDR', 'Admin'), ('uc004-sc01alt3h.ie', 'XBC189-IEDR', 'Billing'), ('uc004-sc01alt3h.ie', 'XBC189-IEDR', 'Creator'), ('uc004-sc01alt3h.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3h.ie'), 'uc004-sc01alt3h.ie', 'ns1.uc004-sc01alt3h.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3h.ie'), 'uc004-sc01alt3h.ie', 'ns2.uc004-sc01alt3h.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3h.ie'), 'uc004-sc01alt3h.ie', 'ns3.uc004-sc01alt3h.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc004-sc01alt3h.ie', 'ns1.uc004-sc01alt3h.ie', '10.10.121.23', NULL, '1'), ('uc004-sc01alt3h.ie', 'ns2.uc004-sc01alt3h.ie', '1.3.4.2', NULL, '2'), ('uc004-sc01alt3h.ie', 'ns3.uc004-sc01alt3h.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc004-sc01alt3i.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 1 Month, CURDATE() + Interval 11 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc004-sc01alt3i.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 1 Month, CURDATE() + Interval 11 Month, NOW(), 'Test Remark', 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3i.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3i.ie'), 'uc004-sc01alt3i.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3i.ie'), 'uc004-sc01alt3i.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3i.ie'), 'uc004-sc01alt3i.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3i.ie'), 'uc004-sc01alt3i.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc004-sc01alt3i.ie', 'XOE550-IEDR', 'Admin'), ('uc004-sc01alt3i.ie', 'XBC189-IEDR', 'Billing'), ('uc004-sc01alt3i.ie', 'XBC189-IEDR', 'Creator'), ('uc004-sc01alt3i.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3i.ie'), 'uc004-sc01alt3i.ie', 'ns1.uc004-sc01alt3i.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3i.ie'), 'uc004-sc01alt3i.ie', 'ns2.uc004-sc01alt3i.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3i.ie'), 'uc004-sc01alt3i.ie', 'ns3.uc004-sc01alt3i.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc004-sc01alt3i.ie', 'ns1.uc004-sc01alt3i.ie', '10.10.121.23', NULL, '1'), ('uc004-sc01alt3i.ie', 'ns2.uc004-sc01alt3i.ie', '1.3.4.2', NULL, '2'), ('uc004-sc01alt3i.ie', 'ns3.uc004-sc01alt3i.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc004-sc01alt3j.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 1 Month, CURDATE() + Interval 11 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc004-sc01alt3j.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 1 Month, CURDATE() + Interval 11 Month, NOW(), 'Test Remark', 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3j.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3j.ie'), 'uc004-sc01alt3j.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3j.ie'), 'uc004-sc01alt3j.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3j.ie'), 'uc004-sc01alt3j.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3j.ie'), 'uc004-sc01alt3j.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc004-sc01alt3j.ie', 'XOE550-IEDR', 'Admin'), ('uc004-sc01alt3j.ie', 'XBC189-IEDR', 'Billing'), ('uc004-sc01alt3j.ie', 'XBC189-IEDR', 'Creator'), ('uc004-sc01alt3j.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3j.ie'), 'uc004-sc01alt3j.ie', 'ns1.uc004-sc01alt3j.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3j.ie'), 'uc004-sc01alt3j.ie', 'ns2.uc004-sc01alt3j.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-sc01alt3j.ie'), 'uc004-sc01alt3j.ie', 'ns3.uc004-sc01alt3j.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc004-sc01alt3j.ie', 'ns1.uc004-sc01alt3j.ie', '10.10.121.23', NULL, '1'), ('uc004-sc01alt3j.ie', 'ns2.uc004-sc01alt3j.ie', '1.3.4.2', NULL, '2'), ('uc004-sc01alt3j.ie', 'ns3.uc004-sc01alt3j.ie', '10.12.13.14', NULL, '3');

# UC#004: Make Payment for Existing Domain - AT - NRP(IM)-RC-D-1 - IM Renewal Re-activation (Registrar by Dep)
# DSM: 18
INSERT INTO DomainHist VALUES (NULL, 'uc004-rcatnrp.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', 18, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc004-rcatnrp.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', 18, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-rcatnrp.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-rcatnrp.ie'), 'uc004-rcatnrp.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-rcatnrp.ie'), 'uc004-rcatnrp.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-rcatnrp.ie'), 'uc004-rcatnrp.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-rcatnrp.ie'), 'uc004-rcatnrp.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc004-rcatnrp.ie', 'XOE550-IEDR', 'Admin'), ('uc004-rcatnrp.ie', 'XBC189-IEDR', 'Billing'), ('uc004-rcatnrp.ie', 'XBC189-IEDR', 'Creator'), ('uc004-rcatnrp.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-rcatnrp.ie'), 'uc004-rcatnrp.ie', 'ns1.uc004-rcatnrp.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-rcatnrp.ie'), 'uc004-rcatnrp.ie', 'ns2.uc004-rcatnrp.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-rcatnrp.ie'), 'uc004-rcatnrp.ie', 'ns3.uc004-rcatnrp.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc004-rcatnrp.ie', 'ns1.uc004-rcatnrp.ie', '10.10.121.23', NULL, '1'), ('uc004-rcatnrp.ie', 'ns2.uc004-rcatnrp.ie', '1.3.4.2', NULL, '2'), ('uc004-rcatnrp.ie', 'ns3.uc004-rcatnrp.ie', '10.12.13.14', NULL, '3');

# UC#004: Make Payment for Existing Domain - AT - NRP(IS)-RC-D-2 - IS Renewal Re-activation (Registrar by Dep)
# DSM: 19
# UC#004: Make Payment for Existing Domain - AT - NRP(VM)-RC-D-3 - VM Renewal Re-activation (Registrar by Dep)
# DSM: 20
# UC#004: Make Payment for Existing Domain - AT - NRP(IM)-RC-C-5 - IM Renewal Re-activation (Registrars by CC)
# DSM: 18
# UC#004: Make Payment for Existing Domain - AT - NRP(IS)-RC-C-6 - IS Renewal Re-activation (Registrar by CC)
# DSM: 19
# UC#004: Make Payment for Existing Domain - AT - NRP(VM)-RC-C-7 - VM Renewal Re-activation (Registrar by CC)
# DSM: 20
# UC#004: Make Payment for Existing Domain - AT - NRP(VS)-RC-C-8 - VS Renewal Re-activation (Registrar by CC)
# DSM: 21
# UC#004: Make Payment for Existing Domain - AT - NRP(IM)-DC-C-9 - IM Renewal Re-activation (Directs by CC)

# DSM: 26
INSERT INTO DomainHist VALUES (NULL, 'uc004-dcatnrp.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XDD274-IEDR', NOW(), 'NO', 26, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc004-dcatnrp.ie', 'Test Holder 0001', 3, 2, NULL, 1, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', 26, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-dcatnrp.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-dcatnrp.ie'), 'uc004-dcatnrp.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-dcatnrp.ie'), 'uc004-dcatnrp.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-dcatnrp.ie'), 'uc004-dcatnrp.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-dcatnrp.ie'), 'uc004-dcatnrp.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc004-dcatnrp.ie', 'XOE550-IEDR', 'Admin'), ('uc004-dcatnrp.ie', 'XDD274-IEDR', 'Billing'), ('uc004-dcatnrp.ie', 'XDD274-IEDR', 'Creator'), ('uc004-dcatnrp.ie', 'XDD274-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-dcatnrp.ie'), 'uc004-dcatnrp.ie', 'ns1.uc004-dcatnrp.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-dcatnrp.ie'), 'uc004-dcatnrp.ie', 'ns2.uc004-dcatnrp.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-dcatnrp.ie'), 'uc004-dcatnrp.ie', 'ns3.uc004-dcatnrp.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc004-dcatnrp.ie', 'ns1.uc004-dcatnrp.ie', '10.10.121.23', NULL, '1'), ('uc004-dcatnrp.ie', 'ns2.uc004-dcatnrp.ie', '1.3.4.2', NULL, '2'), ('uc004-dcatnrp.ie', 'ns3.uc004-dcatnrp.ie', '10.12.13.14', NULL, '3');

# UC#004: Make Payment for Existing Domain - AT - NRP(IS)-DC-C-10 - IS Renewal Re-activation (Directs by CC)
# DSM: 27
# UC#004: Make Payment for Existing Domain - AT - NRP(VM)-DC-C-11 - VM Renewal Re-activation (Directs by CC)
# DSM: 28
# UC#004: Make Payment for Existing Domain - AT - NRP(VS)-DC-C-12 - VS Renewal Re-activation (Directs by CC)
# DSM: 29
# UC#004: Make Payment for Existing Domain - AT - NRP(IM)-DC-L-13 - IM Renewal Re-activation (Directs by DC)
# DSM: 26
# UC#004: Make Payment for Existing Domain - AT - NRP(IS)-DC-L-14 - IS Renewal Re-activation (Directs by DC)
# DSM: 27
# UC#004: Make Payment for Existing Domain - AT - NRP(VM)-DC-L-15 - VM Renewal Re-activation (Directs by DC)
# DSM: 28
# UC#004: Make Payment for Existing Domain - AT - NRP(VS)-DC-L-16 - VS Renewal Re-activation (Directs by DC)
# DSM: 29
# UC#004: Make Payment for Existing Domain - AT - NRPW(IM)-RC-D-42 - IM Renewal Re-activation (Registrar by Dep) - WIPO
# DSM: 2
# UC#004: Make Payment for Existing Domain - AT - NRPW(IS)-RC-D-43 - IS Renewal Re-activation (Registrar by Dep) - WIPO
# DSM: 3
# UC#004: Make Payment for Existing Domain - AT - NRPW(VM)-RC-D-44 - VM Renewal Re-activation (Registrar by Dep) - WIPO
# DSM: 4
# UC#004: Make Payment for Existing Domain - AT - NRPW(VS)-RC-D-45 - VS Renewal Re-activation (Registrars by Dep) - WIPO
# DSM: 5
# UC#004: Make Payment for Existing Domain - AT - NRPW(IM)-RC-C-46 - IM Renewal Re-activation (Registrar by CC) - WIPO
# DSM: 2
# UC#004: Make Payment for Existing Domain - AT - NRPW(IS)-RC-C-47 - IS Renewal Re-activation (Registrar by CC) - WIPO
# DSM: 3
# UC#004: Make Payment for Existing Domain - AT - NRPW(VM)-RC-C-48 - VM Renewal Re-activation (Registrar by CC) - WIPO
# DSM: 4
# UC#004: Make Payment for Existing Domain - AT - NRPW(VS)-RC-C-49 - VS Renewal Re-activation (Registrar by CC) - WIPO
# DSM: 5
# UC#004: Make Payment for Existing Domain - AT - NRPW(IM)-DC-C-50 - IM Renewal Re-activation (Directs by CC) - WIPO
# DSM: 10
# UC#004: Make Payment for Existing Domain - AT - NRPW(IS)-DC-C-51 - IS Renewal Re-activation (Directs by CC) - WIPO
# DSM: 11
# UC#004: Make Payment for Existing Domain - AT - NRPW(VM)-DC-C-52 - VM Renewal Re-activation (Directs by CC) - WIPO
# DSM: 12
# UC#004: Make Payment for Existing Domain - AT - NRPW(VS)-DC-C-53 - VS Renewal Re-activation (Directs by CC) - WIPO
# DSM: 13
# UC#004: Make Payment for Existing Domain - AT - NRPW(IM)-DC-L-54 - IM Renewal Re-activation (Directs by DC) - WIPO
# DSM: 10
# UC#004: Make Payment for Existing Domain - AT - NRPW(IS)-DC-L-55 - IS Renewal Re-activation (Directs by DC) - WIPO
# DSM: 11
# UC#004: Make Payment for Existing Domain - AT - NRPW(VM)-DC-L-56 - VM Renewal Re-activation (Directs by DC) - WIPO
# DSM: 12
# UC#004: Make Payment for Existing Domain - AT - NRPW(VS)-DC-L-57 - VS Renewal Re-activation (Directs by DC) - WIPO
# DSM: 13

DROP PROCEDURE IF EXISTS insertbigdata;
DELIMITER $$
CREATE PROCEDURE insertbigdata()
BEGIN
    DECLARE x INT;
    DECLARE domain VARCHAR(66);
    SET x = 0;
    WHILE x < 20 DO
        SET domain = CONCAT('uc004-nosc02', '-', x, '.ie');
        INSERT INTO DomainHist VALUES (NULL, domain, 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
        INSERT INTO Domain VALUES (domain, 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = domain));
        INSERT INTO ContactHist VALUES
	((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = domain), domain, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
	((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = domain), domain, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
	((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = domain), domain, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
	((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = domain), domain, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
	INSERT INTO Contact VALUES (domain, 'XOE550-IEDR', 'Admin'), (domain, 'XBC189-IEDR', 'Billing'), (domain, 'XBC189-IEDR', 'Creator'), (domain, 'XBC189-IEDR', 'Tech');
	INSERT INTO DNSHist VALUES
        ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = domain), domain, 'ns1.dns.ie', NULL, NULL, '1'),
	((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = domain), domain, 'ns2.dns.ie', NULL, NULL, '2'),
	((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = domain), domain, 'ns3.dns.ie', NULL, NULL, '3');
	INSERT INTO DNS VALUES (domain, 'ns1.dns.ie', NULL, NULL, '1'), (domain, 'ns2.dns.ie', NULL, NULL, '2'), (domain, 'ns3.dns.ie', NULL, NULL, '3');
        SET x = x + 1;
    END WHILE;
END;$$
DELIMITER ;
CALL insertbigdata();

INSERT INTO DomainHist VALUES (NULL, 'uc004-nosc03.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc004-nosc03.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-nosc03.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-nosc03.ie'), 'uc004-nosc03.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-nosc03.ie'), 'uc004-nosc03.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-nosc03.ie'), 'uc004-nosc03.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-nosc03.ie'), 'uc004-nosc03.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc004-nosc03.ie', 'XOE550-IEDR', 'Admin'), ('uc004-nosc03.ie', 'XBC189-IEDR', 'Billing'), ('uc004-nosc03.ie', 'XBC189-IEDR', 'Creator'), ('uc004-nosc03.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-nosc03.ie'), 'uc004-nosc03.ie', 'ns1.uc004-nosc03.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-nosc03.ie'), 'uc004-nosc03.ie', 'ns2.uc004-nosc03.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-nosc03.ie'), 'uc004-nosc03.ie', 'ns3.uc004-nosc03.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc004-nosc03.ie', 'ns1.uc004-nosc03.ie', '10.10.121.23', NULL, '1'), ('uc004-nosc03.ie', 'ns2.uc004-nosc03.ie', '1.3.4.2', NULL, '2'), ('uc004-nosc03.ie', 'ns3.uc004-nosc03.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc004-nosc04.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', 18, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc004-nosc04.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', 18, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-nosc04.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-nosc04.ie'), 'uc004-nosc04.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-nosc04.ie'), 'uc004-nosc04.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-nosc04.ie'), 'uc004-nosc04.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-nosc04.ie'), 'uc004-nosc04.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc004-nosc04.ie', 'XOE550-IEDR', 'Admin'), ('uc004-nosc04.ie', 'XBC189-IEDR', 'Billing'), ('uc004-nosc04.ie', 'XBC189-IEDR', 'Creator'), ('uc004-nosc04.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-nosc04.ie'), 'uc004-nosc04.ie', 'ns1.uc004-nosc04.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-nosc04.ie'), 'uc004-nosc04.ie', 'ns2.uc004-nosc04.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-nosc04.ie'), 'uc004-nosc04.ie', 'ns3.uc004-nosc04.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc004-nosc04.ie', 'ns1.uc004-nosc04.ie', '10.10.121.23', NULL, '1'), ('uc004-nosc04.ie', 'ns2.uc004-nosc04.ie', '1.3.4.2', NULL, '2'), ('uc004-nosc04.ie', 'ns3.uc004-nosc04.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc004-nosc05.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', 18, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc004-nosc05.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', 18, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-nosc05.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-nosc05.ie'), 'uc004-nosc05.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-nosc05.ie'), 'uc004-nosc05.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-nosc05.ie'), 'uc004-nosc05.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-nosc05.ie'), 'uc004-nosc05.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc004-nosc05.ie', 'XOE550-IEDR', 'Admin'), ('uc004-nosc05.ie', 'XBC189-IEDR', 'Billing'), ('uc004-nosc05.ie', 'XBC189-IEDR', 'Creator'), ('uc004-nosc05.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-nosc05.ie'), 'uc004-nosc05.ie', 'ns1.uc004-nosc05.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-nosc05.ie'), 'uc004-nosc05.ie', 'ns2.uc004-nosc05.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-nosc05.ie'), 'uc004-nosc05.ie', 'ns3.uc004-nosc05.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc004-nosc05.ie', 'ns1.uc004-nosc05.ie', '10.10.121.23', NULL, '1'), ('uc004-nosc05.ie', 'ns2.uc004-nosc05.ie', '1.3.4.2', NULL, '2'), ('uc004-nosc05.ie', 'ns3.uc004-nosc05.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc004-nosc06a.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XDD274-IEDR', NOW(), 'NO', 25, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc004-nosc06a.ie', 'Test Holder 0001', 3, 2, NULL, 1, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', 25, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-nosc06a.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-nosc06a.ie'), 'uc004-nosc06a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-nosc06a.ie'), 'uc004-nosc06a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-nosc06a.ie'), 'uc004-nosc06a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-nosc06a.ie'), 'uc004-nosc06a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc004-nosc06a.ie', 'XOE550-IEDR', 'Admin'), ('uc004-nosc06a.ie', 'XDD274-IEDR', 'Billing'), ('uc004-nosc06a.ie', 'XDD274-IEDR', 'Creator'), ('uc004-nosc06a.ie', 'XDD274-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-nosc06a.ie'), 'uc004-nosc06a.ie', 'ns1.uc004-nosc06a.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-nosc06a.ie'), 'uc004-nosc06a.ie', 'ns2.uc004-nosc06a.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-nosc06a.ie'), 'uc004-nosc06a.ie', 'ns3.uc004-nosc06a.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc004-nosc06a.ie', 'ns1.uc004-nosc06a.ie', '10.10.121.23', NULL, '1'), ('uc004-nosc06a.ie', 'ns2.uc004-nosc06a.ie', '1.3.4.2', NULL, '2'), ('uc004-nosc06a.ie', 'ns3.uc004-nosc06a.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc004-nosc06b.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XDD274-IEDR', NOW(), 'NO', 25, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc004-nosc06b.ie', 'Test Holder 0001', 3, 2, NULL, 1, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', 25, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-nosc06b.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-nosc06b.ie'), 'uc004-nosc06b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-nosc06b.ie'), 'uc004-nosc06b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-nosc06b.ie'), 'uc004-nosc06b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-nosc06b.ie'), 'uc004-nosc06b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc004-nosc06b.ie', 'XOE550-IEDR', 'Admin'), ('uc004-nosc06b.ie', 'XDD274-IEDR', 'Billing'), ('uc004-nosc06b.ie', 'XDD274-IEDR', 'Creator'), ('uc004-nosc06b.ie', 'XDD274-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-nosc06b.ie'), 'uc004-nosc06b.ie', 'ns1.uc004-nosc06b.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-nosc06b.ie'), 'uc004-nosc06b.ie', 'ns2.uc004-nosc06b.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc004-nosc06b.ie'), 'uc004-nosc06b.ie', 'ns3.uc004-nosc06b.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc004-nosc06b.ie', 'ns1.uc004-nosc06b.ie', '10.10.121.23', NULL, '1'), ('uc004-nosc06b.ie', 'ns2.uc004-nosc06b.ie', '1.3.4.2', NULL, '2'), ('uc004-nosc06b.ie', 'ns3.uc004-nosc06b.ie', '10.12.13.14', NULL, '3');

