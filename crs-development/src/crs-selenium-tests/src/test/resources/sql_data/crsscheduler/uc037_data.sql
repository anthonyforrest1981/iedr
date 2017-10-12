INSERT INTO DomainHist VALUES (NULL, 'uc037-sc01.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE() - Interval 2 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, CURDATE(), NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc037-sc01.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE() - Interval 2 Month, NOW(), 'Test Remark', 'NO', '17', NULL, CURDATE(), NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc037-sc01.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc037-sc01.ie'), 'uc037-sc01.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc037-sc01.ie'), 'uc037-sc01.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc037-sc01.ie'), 'uc037-sc01.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc037-sc01.ie'), 'uc037-sc01.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc037-sc01.ie', 'XBC189-IEDR', 'Admin'), ('uc037-sc01.ie', 'XBC189-IEDR', 'Billing'), ('uc037-sc01.ie', 'XBC189-IEDR', 'Creator'), ('uc037-sc01.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc037-sc01.ie'), 'uc037-sc01.ie', 'ns1.uc037-sc01.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc037-sc01.ie'), 'uc037-sc01.ie', 'ns2.uc037-sc01.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc037-sc01.ie'), 'uc037-sc01.ie', 'ns3.uc037-sc01.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc037-sc01.ie', 'ns1.uc037-sc01.ie', '10.10.121.23', NULL, '1'), ('uc037-sc01.ie', 'ns2.uc037-sc01.ie', '1.3.4.2', NULL, '2'), ('uc037-sc01.ie', 'ns3.uc037-sc01.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc037-sc02.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 603), CURDATE() - Interval 12 Month, CURDATE() - Interval 2 Month, NOW(), 'Test Remark', 'XNV498-IEDR', NOW(), 'NO', '49', NULL, CURDATE(), NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc037-sc02.ie', 'Test Holder 0001', 3, 2, NULL, 603, CURDATE() - Interval 12 Month, CURDATE() - Interval 2 Month, NOW(), 'Test Remark', 'NO', '49', NULL, CURDATE(), NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc037-sc02.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc037-sc02.ie'), 'uc037-sc02.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc037-sc02.ie'), 'uc037-sc02.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc037-sc02.ie'), 'uc037-sc02.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc037-sc02.ie'), 'uc037-sc02.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc037-sc02.ie', 'XNV498-IEDR', 'Admin'), ('uc037-sc02.ie', 'XNV498-IEDR', 'Billing'), ('uc037-sc02.ie', 'XNV498-IEDR', 'Creator'), ('uc037-sc02.ie', 'XNV498-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc037-sc02.ie'), 'uc037-sc02.ie', 'ns1.uc037-sc02.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc037-sc02.ie'), 'uc037-sc02.ie', 'ns2.uc037-sc02.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc037-sc02.ie'), 'uc037-sc02.ie', 'ns3.uc037-sc02.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc037-sc02.ie', 'ns1.uc037-sc02.ie', '10.10.121.23', NULL, '1'), ('uc037-sc02.ie', 'ns2.uc037-sc02.ie', '1.3.4.2', NULL, '2'), ('uc037-sc02.ie', 'ns3.uc037-sc02.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc037-sc03.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 603), CURDATE() - Interval 12 Month, CURDATE() - Interval 2 Month, NOW(), 'Test Remark', 'XNV498-IEDR', NOW(), 'NO', '81', NULL, CURDATE(), NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc037-sc03.ie', 'Test Holder 0001', 3, 2, NULL, 603, CURDATE() - Interval 12 Month, CURDATE() - Interval 2 Month, NOW(), 'Test Remark', 'NO', '81', NULL, CURDATE(), NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc037-sc03.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc037-sc03.ie'), 'uc037-sc03.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc037-sc03.ie'), 'uc037-sc03.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc037-sc03.ie'), 'uc037-sc03.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc037-sc03.ie'), 'uc037-sc03.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc037-sc03.ie', 'XNV498-IEDR', 'Admin'), ('uc037-sc03.ie', 'XNV498-IEDR', 'Billing'), ('uc037-sc03.ie', 'XNV498-IEDR', 'Creator'), ('uc037-sc03.ie', 'XNV498-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc037-sc03.ie'), 'uc037-sc03.ie', 'ns1.uc037-sc03.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc037-sc03.ie'), 'uc037-sc03.ie', 'ns2.uc037-sc03.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc037-sc03.ie'), 'uc037-sc03.ie', 'ns3.uc037-sc03.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc037-sc03.ie', 'ns1.uc037-sc03.ie', '10.10.121.23', NULL, '1'), ('uc037-sc03.ie', 'ns2.uc037-sc03.ie', '1.3.4.2', NULL, '2'), ('uc037-sc03.ie', 'ns3.uc037-sc03.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc037-sc04.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), CURDATE() - Interval 12 Month, CURDATE() - Interval 2 Month, NOW(), 'Test Remark', 'XDD274-IEDR', NOW(), 'NO', '25', NULL, CURDATE(), NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc037-sc04.ie', 'Test Holder 0001', 3, 2, NULL, 1, CURDATE() - Interval 12 Month, CURDATE() - Interval 2 Month, NOW(), 'Test Remark', 'NO', '25', NULL, CURDATE(), NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc037-sc04.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc037-sc04.ie'), 'uc037-sc04.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc037-sc04.ie'), 'uc037-sc04.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc037-sc04.ie'), 'uc037-sc04.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc037-sc04.ie'), 'uc037-sc04.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc037-sc04.ie', 'XDD274-IEDR', 'Admin'), ('uc037-sc04.ie', 'XDD274-IEDR', 'Billing'), ('uc037-sc04.ie', 'XDD274-IEDR', 'Creator'), ('uc037-sc04.ie', 'XDD274-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc037-sc04.ie'), 'uc037-sc04.ie', 'ns1.uc037-sc04.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc037-sc04.ie'), 'uc037-sc04.ie', 'ns2.uc037-sc04.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc037-sc04.ie'), 'uc037-sc04.ie', 'ns3.uc037-sc04.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc037-sc04.ie', 'ns1.uc037-sc04.ie', '10.10.121.23', NULL, '1'), ('uc037-sc04.ie', 'ns2.uc037-sc04.ie', '1.3.4.2', NULL, '2'), ('uc037-sc04.ie', 'ns3.uc037-sc04.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc037-sc05.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 603), CURDATE() - Interval 12 Month, CURDATE() - Interval 2 Month, NOW(), 'Test Remark', 'XNV498-IEDR', NOW(), 'NO', '17', NULL, CURDATE(), NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc037-sc05.ie', 'Test Holder 0001', 3, 2, NULL, 603, CURDATE() - Interval 12 Month, CURDATE() - Interval 2 Month, NOW(), 'Test Remark', 'NO', '17', NULL, CURDATE(), NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc037-sc05.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc037-sc05.ie'), 'uc037-sc05.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc037-sc05.ie'), 'uc037-sc05.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc037-sc05.ie'), 'uc037-sc05.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc037-sc05.ie'), 'uc037-sc05.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc037-sc05.ie', 'XNV498-IEDR', 'Admin'), ('uc037-sc05.ie', 'XNV498-IEDR', 'Billing'), ('uc037-sc05.ie', 'XNV498-IEDR', 'Creator'), ('uc037-sc05.ie', 'XNV498-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc037-sc05.ie'), 'uc037-sc05.ie', 'ns1.uc037-sc05.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc037-sc05.ie'), 'uc037-sc05.ie', 'ns2.uc037-sc05.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc037-sc05.ie'), 'uc037-sc05.ie', 'ns3.uc037-sc05.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc037-sc05.ie', 'ns1.uc037-sc05.ie', '10.10.121.23', NULL, '1'), ('uc037-sc05.ie', 'ns2.uc037-sc05.ie', '1.3.4.2', NULL, '2'), ('uc037-sc05.ie', 'ns3.uc037-sc05.ie', '10.12.13.14', NULL, '3');

