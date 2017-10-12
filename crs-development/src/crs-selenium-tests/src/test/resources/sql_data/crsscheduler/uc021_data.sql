INSERT INTO DomainHist VALUES (NULL, 'uc021-qa12142.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XDD274-IEDR', NOW(), 'NO', '25', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc021-qa12142.ie', 'Test Holder 0001', 3, 2, NULL, 1, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', '25', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc021-qa12142.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc021-qa12142.ie'), 'uc021-qa12142.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc021-qa12142.ie'), 'uc021-qa12142.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc021-qa12142.ie'), 'uc021-qa12142.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc021-qa12142.ie'), 'uc021-qa12142.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc021-qa12142.ie', 'XDD274-IEDR', 'Admin'), ('uc021-qa12142.ie', 'XDD274-IEDR', 'Billing'), ('uc021-qa12142.ie', 'XDD274-IEDR', 'Creator'), ('uc021-qa12142.ie', 'XDD274-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc021-qa12142.ie'), 'uc021-qa12142.ie', 'ns1.uc021-qa12142.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc021-qa12142.ie'), 'uc021-qa12142.ie', 'ns2.uc021-qa12142.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc021-qa12142.ie'), 'uc021-qa12142.ie', 'ns3.uc021-qa12142.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc021-qa12142.ie', 'ns1.uc021-qa12142.ie', '10.10.121.23', NULL, '1'), ('uc021-qa12142.ie', 'ns2.uc021-qa12142.ie', '1.3.4.2', NULL, '2'), ('uc021-qa12142.ie', 'ns3.uc021-qa12142.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc021-qa12143.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc021-qa12143.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc021-qa12143.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc021-qa12143.ie'), 'uc021-qa12143.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc021-qa12143.ie'), 'uc021-qa12143.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc021-qa12143.ie'), 'uc021-qa12143.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc021-qa12143.ie'), 'uc021-qa12143.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc021-qa12143.ie', 'XBC189-IEDR', 'Admin'), ('uc021-qa12143.ie', 'XBC189-IEDR', 'Billing'), ('uc021-qa12143.ie', 'XBC189-IEDR', 'Creator'), ('uc021-qa12143.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc021-qa12143.ie'), 'uc021-qa12143.ie', 'ns1.uc021-qa12143.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc021-qa12143.ie'), 'uc021-qa12143.ie', 'ns2.uc021-qa12143.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc021-qa12143.ie'), 'uc021-qa12143.ie', 'ns3.uc021-qa12143.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc021-qa12143.ie', 'ns1.uc021-qa12143.ie', '10.10.121.23', NULL, '1'), ('uc021-qa12143.ie', 'ns2.uc021-qa12143.ie', '1.3.4.2', NULL, '2'), ('uc021-qa12143.ie', 'ns3.uc021-qa12143.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc021-qa12144.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '81', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc021-qa12144.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', '81', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc021-qa12144.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc021-qa12144.ie'), 'uc021-qa12144.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc021-qa12144.ie'), 'uc021-qa12144.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc021-qa12144.ie'), 'uc021-qa12144.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc021-qa12144.ie'), 'uc021-qa12144.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc021-qa12144.ie', 'XBC189-IEDR', 'Admin'), ('uc021-qa12144.ie', 'XBC189-IEDR', 'Billing'), ('uc021-qa12144.ie', 'XBC189-IEDR', 'Creator'), ('uc021-qa12144.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc021-qa12144.ie'), 'uc021-qa12144.ie', 'ns1.uc021-qa12144.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc021-qa12144.ie'), 'uc021-qa12144.ie', 'ns2.uc021-qa12144.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc021-qa12144.ie'), 'uc021-qa12144.ie', 'ns3.uc021-qa12144.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc021-qa12144.ie', 'ns1.uc021-qa12144.ie', '10.10.121.23', NULL, '1'), ('uc021-qa12144.ie', 'ns2.uc021-qa12144.ie', '1.3.4.2', NULL, '2'), ('uc021-qa12144.ie', 'ns3.uc021-qa12144.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc021-qa12147.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XDD274-IEDR', NOW(), 'NO', '28', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc021-qa12147.ie', 'Test Holder 0001', 3, 2, NULL, 1, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', '28', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc021-qa12147.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc021-qa12147.ie'), 'uc021-qa12147.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc021-qa12147.ie'), 'uc021-qa12147.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc021-qa12147.ie'), 'uc021-qa12147.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc021-qa12147.ie'), 'uc021-qa12147.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc021-qa12147.ie', 'XDD274-IEDR', 'Admin'), ('uc021-qa12147.ie', 'XDD274-IEDR', 'Billing'), ('uc021-qa12147.ie', 'XDD274-IEDR', 'Creator'), ('uc021-qa12147.ie', 'XDD274-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc021-qa12147.ie'), 'uc021-qa12147.ie', 'ns1.uc021-qa12147.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc021-qa12147.ie'), 'uc021-qa12147.ie', 'ns2.uc021-qa12147.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc021-qa12147.ie'), 'uc021-qa12147.ie', 'ns3.uc021-qa12147.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc021-qa12147.ie', 'ns1.uc021-qa12147.ie', '10.10.121.23', NULL, '1'), ('uc021-qa12147.ie', 'ns2.uc021-qa12147.ie', '1.3.4.2', NULL, '2'), ('uc021-qa12147.ie', 'ns3.uc021-qa12147.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc021-qa12148.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XDD274-IEDR', NOW(), 'NO', '25', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc021-qa12148.ie', 'Test Holder 0001', 3, 2, NULL, 1, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', '25', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc021-qa12148.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc021-qa12148.ie'), 'uc021-qa12148.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc021-qa12148.ie'), 'uc021-qa12148.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc021-qa12148.ie'), 'uc021-qa12148.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc021-qa12148.ie'), 'uc021-qa12148.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc021-qa12148.ie', 'XDD274-IEDR', 'Admin'), ('uc021-qa12148.ie', 'XDD274-IEDR', 'Billing'), ('uc021-qa12148.ie', 'XDD274-IEDR', 'Creator'), ('uc021-qa12148.ie', 'XDD274-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc021-qa12148.ie'), 'uc021-qa12148.ie', 'ns1.uc021-qa12148.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc021-qa12148.ie'), 'uc021-qa12148.ie', 'ns2.uc021-qa12148.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc021-qa12148.ie'), 'uc021-qa12148.ie', 'ns3.uc021-qa12148.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc021-qa12148.ie', 'ns1.uc021-qa12148.ie', '10.10.121.23', NULL, '1'), ('uc021-qa12148.ie', 'ns2.uc021-qa12148.ie', '1.3.4.2', NULL, '2'), ('uc021-qa12148.ie', 'ns3.uc021-qa12148.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc021-qa12149.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 603), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XNV498-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc021-qa12149.ie', 'Test Holder 0001', 3, 2, NULL, 603, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc021-qa12149.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc021-qa12149.ie'), 'uc021-qa12149.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc021-qa12149.ie'), 'uc021-qa12149.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc021-qa12149.ie'), 'uc021-qa12149.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc021-qa12149.ie'), 'uc021-qa12149.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc021-qa12149.ie', 'XNV498-IEDR', 'Admin'), ('uc021-qa12149.ie', 'XNV498-IEDR', 'Billing'), ('uc021-qa12149.ie', 'XNV498-IEDR', 'Creator'), ('uc021-qa12149.ie', 'XNV498-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc021-qa12149.ie'), 'uc021-qa12149.ie', 'ns1.uc021-qa12149.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc021-qa12149.ie'), 'uc021-qa12149.ie', 'ns2.uc021-qa12149.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc021-qa12149.ie'), 'uc021-qa12149.ie', 'ns3.uc021-qa12149.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc021-qa12149.ie', 'ns1.uc021-qa12149.ie', '10.10.121.23', NULL, '1'), ('uc021-qa12149.ie', 'ns2.uc021-qa12149.ie', '1.3.4.2', NULL, '2'), ('uc021-qa12149.ie', 'ns3.uc021-qa12149.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc021-qa121411.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '1', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, CURDATE() - Interval 9 Month, CURDATE() + Interval 3 Month);
INSERT INTO Domain VALUES ('uc021-qa121411.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', '1', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, CURDATE() - Interval 9 Month, CURDATE() + Interval 3 Month, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc021-qa121411.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc021-qa121411.ie'), 'uc021-qa121411.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc021-qa121411.ie'), 'uc021-qa121411.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc021-qa121411.ie'), 'uc021-qa121411.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc021-qa121411.ie'), 'uc021-qa121411.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc021-qa121411.ie', 'XBC189-IEDR', 'Admin'), ('uc021-qa121411.ie', 'XBC189-IEDR', 'Billing'), ('uc021-qa121411.ie', 'XBC189-IEDR', 'Creator'), ('uc021-qa121411.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc021-qa121411.ie'), 'uc021-qa121411.ie', 'ns1.uc021-qa121411.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc021-qa121411.ie'), 'uc021-qa121411.ie', 'ns2.uc021-qa121411.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc021-qa121411.ie'), 'uc021-qa121411.ie', 'ns3.uc021-qa121411.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc021-qa121411.ie', 'ns1.uc021-qa121411.ie', '10.10.121.23', NULL, '1'), ('uc021-qa121411.ie', 'ns2.uc021-qa121411.ie', '1.3.4.2', NULL, '2'), ('uc021-qa121411.ie', 'ns3.uc021-qa121411.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc021-qa12145.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc021-qa12145.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc021-qa12145.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc021-qa12145.ie'), 'uc021-qa12145.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc021-qa12145.ie'), 'uc021-qa12145.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc021-qa12145.ie'), 'uc021-qa12145.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc021-qa12145.ie'), 'uc021-qa12145.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc021-qa12145.ie', 'XBC189-IEDR', 'Admin'), ('uc021-qa12145.ie', 'XBC189-IEDR', 'Billing'), ('uc021-qa12145.ie', 'XBC189-IEDR', 'Creator'), ('uc021-qa12145.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc021-qa12145.ie'), 'uc021-qa12145.ie', 'ns1.uc021-qa12145.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc021-qa12145.ie'), 'uc021-qa12145.ie', 'ns2.uc021-qa12145.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc021-qa12145.ie'), 'uc021-qa12145.ie', 'ns3.uc021-qa12145.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc021-qa12145.ie', 'ns1.uc021-qa12145.ie', '10.10.121.23', NULL, '1'), ('uc021-qa12145.ie', 'ns2.uc021-qa12145.ie', '1.3.4.2', NULL, '2'), ('uc021-qa12145.ie', 'ns3.uc021-qa12145.ie', '10.12.13.14', NULL, '3');

