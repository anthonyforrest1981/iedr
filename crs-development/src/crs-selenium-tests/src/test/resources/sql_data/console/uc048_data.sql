INSERT INTO DomainHist VALUES (NULL, 'uc048-sc06.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc048-sc06.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc048-sc06.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc048-sc06.ie'), 'uc048-sc06.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc048-sc06.ie'), 'uc048-sc06.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc048-sc06.ie'), 'uc048-sc06.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc048-sc06.ie'), 'uc048-sc06.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc048-sc06.ie', 'XBC189-IEDR', 'Admin'), ('uc048-sc06.ie', 'XBC189-IEDR', 'Billing'), ('uc048-sc06.ie', 'XBC189-IEDR', 'Creator'), ('uc048-sc06.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc048-sc06.ie'), 'uc048-sc06.ie', 'ns1.uc048-sc06.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc048-sc06.ie'), 'uc048-sc06.ie', 'ns2.uc048-sc06.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc048-sc06.ie'), 'uc048-sc06.ie', 'ns3.uc048-sc06.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc048-sc06.ie', 'ns1.uc048-sc06.ie', '10.10.121.23', NULL, '1'), ('uc048-sc06.ie', 'ns2.uc048-sc06.ie', '1.3.4.2', NULL, '2'), ('uc048-sc06.ie', 'ns3.uc048-sc06.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc048-sc08.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc048-sc08.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc048-sc08.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc048-sc08.ie'), 'uc048-sc08.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc048-sc08.ie'), 'uc048-sc08.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc048-sc08.ie'), 'uc048-sc08.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc048-sc08.ie'), 'uc048-sc08.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc048-sc08.ie', 'XBC189-IEDR', 'Admin'), ('uc048-sc08.ie', 'XBC189-IEDR', 'Billing'), ('uc048-sc08.ie', 'XBC189-IEDR', 'Creator'), ('uc048-sc08.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc048-sc08.ie'), 'uc048-sc08.ie', 'ns1.uc048-sc08.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc048-sc08.ie'), 'uc048-sc08.ie', 'ns2.uc048-sc08.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc048-sc08.ie'), 'uc048-sc08.ie', 'ns3.uc048-sc08.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc048-sc08.ie', 'ns1.uc048-sc08.ie', '10.10.121.23', NULL, '1'), ('uc048-sc08.ie', 'ns2.uc048-sc08.ie', '1.3.4.2', NULL, '2'), ('uc048-sc08.ie', 'ns3.uc048-sc08.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc048-sc10.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 603), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XNV498-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc048-sc10.ie', 'Test Holder 0001', 3, 2, NULL, 603, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc048-sc10.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc048-sc10.ie'), 'uc048-sc10.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc048-sc10.ie'), 'uc048-sc10.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc048-sc10.ie'), 'uc048-sc10.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc048-sc10.ie'), 'uc048-sc10.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc048-sc10.ie', 'XNV498-IEDR', 'Admin'), ('uc048-sc10.ie', 'XNV498-IEDR', 'Billing'), ('uc048-sc10.ie', 'XNV498-IEDR', 'Creator'), ('uc048-sc10.ie', 'XNV498-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc048-sc10.ie'), 'uc048-sc10.ie', 'ns1.uc048-sc10.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc048-sc10.ie'), 'uc048-sc10.ie', 'ns2.uc048-sc10.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc048-sc10.ie'), 'uc048-sc10.ie', 'ns3.uc048-sc10.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc048-sc10.ie', 'ns1.uc048-sc10.ie', '10.10.121.23', NULL, '1'), ('uc048-sc10.ie', 'ns2.uc048-sc10.ie', '1.3.4.2', NULL, '2'), ('uc048-sc10.ie', 'ns3.uc048-sc10.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc048-qa14832.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XDD274-IEDR', NOW(), 'NO', '25', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc048-qa14832.ie', 'Test Holder 0001', 3, 2, NULL, 1, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', '25', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc048-qa14832.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc048-qa14832.ie'), 'uc048-qa14832.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc048-qa14832.ie'), 'uc048-qa14832.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc048-qa14832.ie'), 'uc048-qa14832.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc048-qa14832.ie'), 'uc048-qa14832.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc048-qa14832.ie', 'XDD274-IEDR', 'Admin'), ('uc048-qa14832.ie', 'XDD274-IEDR', 'Billing'), ('uc048-qa14832.ie', 'XDD274-IEDR', 'Creator'), ('uc048-qa14832.ie', 'XDD274-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc048-qa14832.ie'), 'uc048-qa14832.ie', 'ns1.uc048-qa14832.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc048-qa14832.ie'), 'uc048-qa14832.ie', 'ns2.uc048-qa14832.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc048-qa14832.ie'), 'uc048-qa14832.ie', 'ns3.uc048-qa14832.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc048-qa14832.ie', 'ns1.uc048-qa14832.ie', '10.10.121.23', NULL, '1'), ('uc048-qa14832.ie', 'ns2.uc048-qa14832.ie', '1.3.4.2', NULL, '2'), ('uc048-qa14832.ie', 'ns3.uc048-qa14832.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc048-qa14835.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc048-qa14835.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc048-qa14835.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc048-qa14835.ie'), 'uc048-qa14835.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc048-qa14835.ie'), 'uc048-qa14835.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc048-qa14835.ie'), 'uc048-qa14835.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc048-qa14835.ie'), 'uc048-qa14835.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc048-qa14835.ie', 'XBC189-IEDR', 'Admin'), ('uc048-qa14835.ie', 'XBC189-IEDR', 'Billing'), ('uc048-qa14835.ie', 'XBC189-IEDR', 'Creator'), ('uc048-qa14835.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc048-qa14835.ie'), 'uc048-qa14835.ie', 'ns1.uc048-qa14835.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc048-qa14835.ie'), 'uc048-qa14835.ie', 'ns2.uc048-qa14835.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc048-qa14835.ie'), 'uc048-qa14835.ie', 'ns3.uc048-qa14835.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc048-qa14835.ie', 'ns1.uc048-qa14835.ie', '10.10.121.23', NULL, '1'), ('uc048-qa14835.ie', 'ns2.uc048-qa14835.ie', '1.3.4.2', NULL, '2'), ('uc048-qa14835.ie', 'ns3.uc048-qa14835.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc048-nosc01.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '18', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc048-nosc01.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', '18', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc048-nosc01.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc048-nosc01.ie'), 'uc048-nosc01.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc048-nosc01.ie'), 'uc048-nosc01.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc048-nosc01.ie'), 'uc048-nosc01.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc048-nosc01.ie'), 'uc048-nosc01.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc048-nosc01.ie', 'XBC189-IEDR', 'Admin'), ('uc048-nosc01.ie', 'XBC189-IEDR', 'Billing'), ('uc048-nosc01.ie', 'XBC189-IEDR', 'Creator'), ('uc048-nosc01.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc048-nosc01.ie'), 'uc048-nosc01.ie', 'ns1.uc048-nosc01.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc048-nosc01.ie'), 'uc048-nosc01.ie', 'ns2.uc048-nosc01.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc048-nosc01.ie'), 'uc048-nosc01.ie', 'ns3.uc048-nosc01.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc048-nosc01.ie', 'ns1.uc048-nosc01.ie', '10.10.121.23', NULL, '1'), ('uc048-nosc01.ie', 'ns2.uc048-nosc01.ie', '1.3.4.2', NULL, '2'), ('uc048-nosc01.ie', 'ns3.uc048-nosc01.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc048-nosc02.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '20', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc048-nosc02.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', '20', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc048-nosc02.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc048-nosc02.ie'), 'uc048-nosc02.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc048-nosc02.ie'), 'uc048-nosc02.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc048-nosc02.ie'), 'uc048-nosc02.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc048-nosc02.ie'), 'uc048-nosc02.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc048-nosc02.ie', 'XBC189-IEDR', 'Admin'), ('uc048-nosc02.ie', 'XBC189-IEDR', 'Billing'), ('uc048-nosc02.ie', 'XBC189-IEDR', 'Creator'), ('uc048-nosc02.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc048-nosc02.ie'), 'uc048-nosc02.ie', 'ns1.uc048-nosc02.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc048-nosc02.ie'), 'uc048-nosc02.ie', 'ns2.uc048-nosc02.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc048-nosc02.ie'), 'uc048-nosc02.ie', 'ns3.uc048-nosc02.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc048-nosc02.ie', 'ns1.uc048-nosc02.ie', '10.10.121.23', NULL, '1'), ('uc048-nosc02.ie', 'ns2.uc048-nosc02.ie', '1.3.4.2', NULL, '2'), ('uc048-nosc02.ie', 'ns3.uc048-nosc02.ie', '10.12.13.14', NULL, '3');

