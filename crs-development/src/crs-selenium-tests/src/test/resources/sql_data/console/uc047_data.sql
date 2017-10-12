INSERT INTO DomainHist VALUES (NULL, 'uc047-nosc01.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc047-nosc01.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-nosc01.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-nosc01.ie'), 'uc047-nosc01.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-nosc01.ie'), 'uc047-nosc01.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-nosc01.ie'), 'uc047-nosc01.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-nosc01.ie'), 'uc047-nosc01.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc047-nosc01.ie', 'XOE550-IEDR', 'Admin'), ('uc047-nosc01.ie', 'XBC189-IEDR', 'Billing'), ('uc047-nosc01.ie', 'XBC189-IEDR', 'Creator'), ('uc047-nosc01.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-nosc01.ie'), 'uc047-nosc01.ie', 'ns1.uc047-nosc01.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-nosc01.ie'), 'uc047-nosc01.ie', 'ns2.uc047-nosc01.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-nosc01.ie'), 'uc047-nosc01.ie', 'ns3.uc047-nosc01.ie', '10.12.13.14', NULL, '3'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-nosc01.ie'), 'uc047-nosc01.ie', 'ns4.uc047-nosc01.ie', '10.12.13.14', NULL, '4'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-nosc01.ie'), 'uc047-nosc01.ie', 'ns5.uc047-nosc01.ie', '10.12.13.14', NULL, '5'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-nosc01.ie'), 'uc047-nosc01.ie', 'ns6.uc047-nosc01.ie', '10.12.13.14', NULL, '6'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-nosc01.ie'), 'uc047-nosc01.ie', 'ns7.uc047-nosc01.ie', '10.12.13.14', NULL, '7');
INSERT INTO DNS VALUES ('uc047-nosc01.ie', 'ns1.uc047-nosc01.ie', '10.10.121.23', NULL, '1'), ('uc047-nosc01.ie', 'ns2.uc047-nosc01.ie', '1.3.4.2', NULL, '2'), ('uc047-nosc01.ie', 'ns3.uc047-nosc01.ie', '10.12.13.14', NULL, '3'), ('uc047-nosc01.ie', 'ns4.uc047-nosc01.ie', '10.12.13.14', NULL, '4'), ('uc047-nosc01.ie', 'ns5.uc047-nosc01.ie', '10.12.13.14', NULL, '5'), ('uc047-nosc01.ie', 'ns6.uc047-nosc01.ie', '10.12.13.14', NULL, '6'), ('uc047-nosc01.ie', 'ns7.uc047-nosc01.ie', '10.12.13.14', NULL, '7');

INSERT INTO DomainHist VALUES (NULL, 'uc047-nosc02.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc047-nosc02.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-nosc02.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-nosc02.ie'), 'uc047-nosc02.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-nosc02.ie'), 'uc047-nosc02.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-nosc02.ie'), 'uc047-nosc02.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-nosc02.ie'), 'uc047-nosc02.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc047-nosc02.ie', 'XOE550-IEDR', 'Admin'), ('uc047-nosc02.ie', 'XBC189-IEDR', 'Billing'), ('uc047-nosc02.ie', 'XBC189-IEDR', 'Creator'), ('uc047-nosc02.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-nosc02.ie'), 'uc047-nosc02.ie', 'ns1.uc047-nosc02.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-nosc02.ie'), 'uc047-nosc02.ie', 'ns2.uc047-nosc02.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-nosc02.ie'), 'uc047-nosc02.ie', 'ns3.uc047-nosc02.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc047-nosc02.ie', 'ns1.uc047-nosc02.ie', '10.10.121.23', NULL, '1'), ('uc047-nosc02.ie', 'ns2.uc047-nosc02.ie', '1.3.4.2', NULL, '2'), ('uc047-nosc02.ie', 'ns3.uc047-nosc02.ie', '10.12.13.14', NULL, '3');

