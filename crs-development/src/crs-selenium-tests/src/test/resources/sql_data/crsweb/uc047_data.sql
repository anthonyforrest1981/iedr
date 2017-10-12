INSERT INTO DomainHist VALUES (NULL, 'uc047-nosc01.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc047-nosc01.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-nosc01.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-nosc01.ie'), 'uc047-nosc01.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-nosc01.ie'), 'uc047-nosc01.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-nosc01.ie'), 'uc047-nosc01.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-nosc01.ie'), 'uc047-nosc01.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc047-nosc01.ie', 'XOE550-IEDR', 'Admin'), ('uc047-nosc01.ie', 'XBC189-IEDR', 'Billing'), ('uc047-nosc01.ie', 'XBC189-IEDR', 'Creator'), ('uc047-nosc01.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-nosc01.ie'), 'uc047-nosc01.ie', 'ns1.dns.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-nosc01.ie'), 'uc047-nosc01.ie', 'ns2.dns.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-nosc01.ie'), 'uc047-nosc01.ie', 'ns3.dns.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('uc047-nosc01.ie', 'ns1.dns.ie', NULL, NULL, '1'), ('uc047-nosc01.ie', 'ns2.dns.ie', NULL, NULL, '2'), ('uc047-nosc01.ie', 'ns3.dns.ie', NULL, NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc047-nosc02.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc047-nosc02.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-nosc02.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-nosc02.ie'), 'uc047-nosc02.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-nosc02.ie'), 'uc047-nosc02.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-nosc02.ie'), 'uc047-nosc02.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-nosc02.ie'), 'uc047-nosc02.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc047-nosc02.ie', 'XOE550-IEDR', 'Admin'), ('uc047-nosc02.ie', 'XBC189-IEDR', 'Billing'), ('uc047-nosc02.ie', 'XBC189-IEDR', 'Creator'), ('uc047-nosc02.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-nosc02.ie'), 'uc047-nosc02.ie', 'ns1.invaliddns.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-nosc02.ie'), 'uc047-nosc02.ie', 'ns2.invaliddns.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-nosc02.ie'), 'uc047-nosc02.ie', 'ns3.invaliddns.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('uc047-nosc02.ie', 'ns1.invaliddns.ie', NULL, NULL, '1'), ('uc047-nosc02.ie', 'ns2.invaliddns.ie', NULL, NULL, '2'), ('uc047-nosc02.ie', 'ns3.invaliddns.ie', NULL, NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc047-nosc03.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc047-nosc03.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-nosc03.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-nosc03.ie'), 'uc047-nosc03.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-nosc03.ie'), 'uc047-nosc03.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-nosc03.ie'), 'uc047-nosc03.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-nosc03.ie'), 'uc047-nosc03.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc047-nosc03.ie', 'XOE550-IEDR', 'Admin'), ('uc047-nosc03.ie', 'XBC189-IEDR', 'Billing'), ('uc047-nosc03.ie', 'XBC189-IEDR', 'Creator'), ('uc047-nosc03.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-nosc03.ie'), 'uc047-nosc03.ie', 'ns1.dns.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-nosc03.ie'), 'uc047-nosc03.ie', 'ns2.dns.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-nosc03.ie'), 'uc047-nosc03.ie', 'ns3.dns.ie', NULL, NULL, '3'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-nosc03.ie'), 'uc047-nosc03.ie', 'ns4.dns.ie', NULL, NULL, '4'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-nosc03.ie'), 'uc047-nosc03.ie', 'ns5.dns.ie', NULL, NULL, '5'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-nosc03.ie'), 'uc047-nosc03.ie', 'ns6.dns.ie', NULL, NULL, '6'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-nosc03.ie'), 'uc047-nosc03.ie', 'ns7.dns.ie', NULL, NULL, '7');
INSERT INTO DNS VALUES ('uc047-nosc03.ie', 'ns1.dns.ie', NULL, NULL, '1'), ('uc047-nosc03.ie', 'ns2.dns.ie', NULL, NULL, '2'), ('uc047-nosc03.ie', 'ns3.dns.ie', NULL, NULL, '3'), ('uc047-nosc03.ie', 'ns4.dns.ie', NULL, NULL, '4'), ('uc047-nosc03.ie', 'ns5.dns.ie', NULL, NULL, '5'), ('uc047-nosc03.ie', 'ns6.dns.ie', NULL, NULL, '6'), ('uc047-nosc03.ie', 'ns7.dns.ie', NULL, NULL, '7');

INSERT INTO DomainHist VALUES (NULL, 'uc047-nosc04.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc047-nosc04.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-nosc04.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-nosc04.ie'), 'uc047-nosc04.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-nosc04.ie'), 'uc047-nosc04.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-nosc04.ie'), 'uc047-nosc04.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-nosc04.ie'), 'uc047-nosc04.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc047-nosc04.ie', 'XOE550-IEDR', 'Admin'), ('uc047-nosc04.ie', 'XBC189-IEDR', 'Billing'), ('uc047-nosc04.ie', 'XBC189-IEDR', 'Creator'), ('uc047-nosc04.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-nosc04.ie'), 'uc047-nosc04.ie', 'ns1.uc047-nosc04.ie', NULL, '::ffff:10.10.1.1', '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-nosc04.ie'), 'uc047-nosc04.ie', 'ns2.uc047-nosc04.ie', NULL, '::ffff:10.10.1.2', '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc047-nosc04.ie'), 'uc047-nosc04.ie', 'ns3.uc047-nosc04.ie', NULL, '::ffff:10.10.1.3', '3');
INSERT INTO DNS VALUES ('uc047-nosc04.ie', 'ns1.uc047-nosc04.ie', NULL, '::ffff:10.10.1.1', '1'), ('uc047-nosc04.ie', 'ns2.uc047-nosc04.ie', NULL, '::ffff:10.10.1.2', '2'), ('uc047-nosc04.ie', 'ns3.uc047-nosc04.ie', NULL, '::ffff:10.10.1.3', '3');

