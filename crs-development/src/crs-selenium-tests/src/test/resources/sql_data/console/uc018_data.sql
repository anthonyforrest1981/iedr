INSERT INTO DomainHist VALUES (NULL, 'uc018-nosc01.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '18', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc018-nosc01.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '18', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc018-nosc01.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc018-nosc01.ie'), 'uc018-nosc01.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc018-nosc01.ie'), 'uc018-nosc01.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc018-nosc01.ie'), 'uc018-nosc01.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc018-nosc01.ie'), 'uc018-nosc01.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc018-nosc01.ie', 'XOE550-IEDR', 'Admin'), ('uc018-nosc01.ie', 'XBC189-IEDR', 'Billing'), ('uc018-nosc01.ie', 'XBC189-IEDR', 'Creator'), ('uc018-nosc01.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc018-nosc01.ie'), 'uc018-nosc01.ie', 'ns1.uc018-nosc01.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc018-nosc01.ie'), 'uc018-nosc01.ie', 'ns2.uc018-nosc01.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc018-nosc01.ie'), 'uc018-nosc01.ie', 'ns3.uc018-nosc01.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc018-nosc01.ie', 'ns1.uc018-nosc01.ie', '10.10.121.23', NULL, '1'), ('uc018-nosc01.ie', 'ns2.uc018-nosc01.ie', '1.3.4.2', NULL, '2'), ('uc018-nosc01.ie', 'ns3.uc018-nosc01.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc018-nosc02.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc018-nosc02.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc018-nosc02.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc018-nosc02.ie'), 'uc018-nosc02.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc018-nosc02.ie'), 'uc018-nosc02.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc018-nosc02.ie'), 'uc018-nosc02.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc018-nosc02.ie'), 'uc018-nosc02.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc018-nosc02.ie', 'XOE550-IEDR', 'Admin'), ('uc018-nosc02.ie', 'XBC189-IEDR', 'Billing'), ('uc018-nosc02.ie', 'XBC189-IEDR', 'Creator'), ('uc018-nosc02.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc018-nosc02.ie'), 'uc018-nosc02.ie', 'ns1.uc018-nosc02.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc018-nosc02.ie'), 'uc018-nosc02.ie', 'ns2.uc018-nosc02.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc018-nosc02.ie'), 'uc018-nosc02.ie', 'ns3.uc018-nosc02.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc018-nosc02.ie', 'ns1.uc018-nosc02.ie', '10.10.121.23', NULL, '1'), ('uc018-nosc02.ie', 'ns2.uc018-nosc02.ie', '1.3.4.2', NULL, '2'), ('uc018-nosc02.ie', 'ns3.uc018-nosc02.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc018-nosc03.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc018-nosc03.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc018-nosc03.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc018-nosc03.ie'), 'uc018-nosc03.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc018-nosc03.ie'), 'uc018-nosc03.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc018-nosc03.ie'), 'uc018-nosc03.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc018-nosc03.ie'), 'uc018-nosc03.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc018-nosc03.ie', 'XOE550-IEDR', 'Admin'), ('uc018-nosc03.ie', 'XBC189-IEDR', 'Billing'), ('uc018-nosc03.ie', 'XBC189-IEDR', 'Creator'), ('uc018-nosc03.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc018-nosc03.ie'), 'uc018-nosc03.ie', 'ns1.uc018-nosc03.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc018-nosc03.ie'), 'uc018-nosc03.ie', 'ns2.uc018-nosc03.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc018-nosc03.ie'), 'uc018-nosc03.ie', 'ns3.uc018-nosc03.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc018-nosc03.ie', 'ns1.uc018-nosc03.ie', '10.10.121.23', NULL, '1'), ('uc018-nosc03.ie', 'ns2.uc018-nosc03.ie', '1.3.4.2', NULL, '2'), ('uc018-nosc03.ie', 'ns3.uc018-nosc03.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc018-nosc04.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc018-nosc04.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc018-nosc04.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc018-nosc04.ie'), 'uc018-nosc04.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc018-nosc04.ie'), 'uc018-nosc04.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc018-nosc04.ie'), 'uc018-nosc04.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc018-nosc04.ie'), 'uc018-nosc04.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc018-nosc04.ie', 'XOE550-IEDR', 'Admin'), ('uc018-nosc04.ie', 'XBC189-IEDR', 'Billing'), ('uc018-nosc04.ie', 'XBC189-IEDR', 'Creator'), ('uc018-nosc04.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc018-nosc04.ie'), 'uc018-nosc04.ie', 'ns1.uc018-nosc04.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc018-nosc04.ie'), 'uc018-nosc04.ie', 'ns2.uc018-nosc04.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc018-nosc04.ie'), 'uc018-nosc04.ie', 'ns3.uc018-nosc04.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc018-nosc04.ie', 'ns1.uc018-nosc04.ie', '10.10.121.23', NULL, '1'), ('uc018-nosc04.ie', 'ns2.uc018-nosc04.ie', '1.3.4.2', NULL, '2'), ('uc018-nosc04.ie', 'ns3.uc018-nosc04.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc018-nosc05.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc018-nosc05.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc018-nosc05.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc018-nosc05.ie'), 'uc018-nosc05.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc018-nosc05.ie'), 'uc018-nosc05.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc018-nosc05.ie'), 'uc018-nosc05.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc018-nosc05.ie'), 'uc018-nosc05.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc018-nosc05.ie', 'XOE550-IEDR', 'Admin'), ('uc018-nosc05.ie', 'XBC189-IEDR', 'Billing'), ('uc018-nosc05.ie', 'XBC189-IEDR', 'Creator'), ('uc018-nosc05.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc018-nosc05.ie'), 'uc018-nosc05.ie', 'ns1.uc018-nosc05.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc018-nosc05.ie'), 'uc018-nosc05.ie', 'ns2.uc018-nosc05.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc018-nosc05.ie'), 'uc018-nosc05.ie', 'ns3.uc018-nosc05.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc018-nosc05.ie', 'ns1.uc018-nosc05.ie', '10.10.121.23', NULL, '1'), ('uc018-nosc05.ie', 'ns2.uc018-nosc05.ie', '1.3.4.2', NULL, '2'), ('uc018-nosc05.ie', 'ns3.uc018-nosc05.ie', '10.12.13.14', NULL, '3');

