INSERT INTO DomainHist VALUES (NULL, 'uc047-sc01.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE() - Interval 2 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, CURDATE(), NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc047-sc01.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE() - Interval 2 Month, NOW(), 'Test Remark', 'NO', '17', NULL, CURDATE(), NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-sc01.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-sc01.ie'), 'uc047-sc01.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-sc01.ie'), 'uc047-sc01.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-sc01.ie'), 'uc047-sc01.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-sc01.ie'), 'uc047-sc01.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc047-sc01.ie', 'XBC189-IEDR', 'Admin'), ('uc047-sc01.ie', 'XBC189-IEDR', 'Billing'), ('uc047-sc01.ie', 'XBC189-IEDR', 'Creator'), ('uc047-sc01.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-sc01.ie'), 'uc047-sc01.ie', 'ns1.uc047-sc01.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-sc01.ie'), 'uc047-sc01.ie', 'ns2.uc047-sc01.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-sc01.ie'), 'uc047-sc01.ie', 'ns3.uc047-sc01.ie', '10.12.13.14', NULL, '3'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-sc01.ie'), 'uc047-sc01.ie', 'ns4.uc047-sc01.ie', '10.12.13.14', NULL, '4'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-sc01.ie'), 'uc047-sc01.ie', 'ns5.uc047-sc01.ie', '10.12.13.14', NULL, '5'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-sc01.ie'), 'uc047-sc01.ie', 'ns6.uc047-sc01.ie', '10.12.13.14', NULL, '6'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-sc01.ie'), 'uc047-sc01.ie', 'ns7.uc047-sc01.ie', '10.12.13.14', NULL, '7');
INSERT INTO DNS VALUES ('uc047-sc01.ie', 'ns1.uc047-sc01.ie', '10.10.121.23', NULL, '1'), ('uc047-sc01.ie', 'ns2.uc047-sc01.ie', '1.3.4.2', NULL, '2'), ('uc047-sc01.ie', 'ns3.uc047-sc01.ie', '10.12.13.14', NULL, '3'), ('uc047-sc01.ie', 'ns4.uc047-sc01.ie', '10.12.13.14', NULL, '4'), ('uc047-sc01.ie', 'ns5.uc047-sc01.ie', '10.12.13.14', NULL, '5'), ('uc047-sc01.ie', 'ns6.uc047-sc01.ie', '10.12.13.14', NULL, '6'), ('uc047-sc01.ie', 'ns7.uc047-sc01.ie', '10.12.13.14', NULL, '7');

INSERT INTO DomainHist VALUES (NULL, 'uc047-sc05.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE() - Interval 2 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, CURDATE(), NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc047-sc05.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE() - Interval 2 Month, NOW(), 'Test Remark', 'NO', '17', NULL, CURDATE(), NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-sc05.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-sc05.ie'), 'uc047-sc05.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-sc05.ie'), 'uc047-sc05.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-sc05.ie'), 'uc047-sc05.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-sc05.ie'), 'uc047-sc05.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc047-sc05.ie', 'XBC189-IEDR', 'Admin'), ('uc047-sc05.ie', 'XBC189-IEDR', 'Billing'), ('uc047-sc05.ie', 'XBC189-IEDR', 'Creator'), ('uc047-sc05.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-sc05.ie'), 'uc047-sc05.ie', 'ns1.uc047-sc05.ie', NULL, '::ffff:10.10.1.1', '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-sc05.ie'), 'uc047-sc05.ie', 'ns2.uc047-sc05.ie', NULL, '::ffff:10.10.1.2', '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-sc05.ie'), 'uc047-sc05.ie', 'ns3.uc047-sc05.ie', NULL, '::ffff:10.10.1.3', '3');
INSERT INTO DNS VALUES ('uc047-sc05.ie', 'ns1.uc047-sc05.ie', NULL, '::ffff:10.10.1.1', '1'), ('uc047-sc05.ie', 'ns2.uc047-sc05.ie', NULL, '::ffff:10.10.1.2', '2'), ('uc047-sc05.ie', 'ns3.uc047-sc05.ie', NULL, '::ffff:10.10.1.3', '3');

