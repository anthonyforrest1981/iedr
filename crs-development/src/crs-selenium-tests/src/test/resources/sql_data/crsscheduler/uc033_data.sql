INSERT INTO DomainHist VALUES (NULL, 'uc033-sc01.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), CURDATE() - Interval 12 Month, CURDATE() - Interval 2 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '19', NULL, CURDATE(), NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc033-sc01.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE() - Interval 2 Month, NOW(), 'Test Remark', 'NO', '19', NULL, CURDATE(), NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc033-sc01.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc033-sc01.ie'), 'uc033-sc01.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc033-sc01.ie'), 'uc033-sc01.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc033-sc01.ie'), 'uc033-sc01.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc033-sc01.ie'), 'uc033-sc01.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc033-sc01.ie', 'XBC189-IEDR', 'Admin'), ('uc033-sc01.ie', 'XBC189-IEDR', 'Billing'), ('uc033-sc01.ie', 'XBC189-IEDR', 'Creator'), ('uc033-sc01.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc033-sc01.ie'), 'uc033-sc01.ie', 'ns1.uc033-sc01.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc033-sc01.ie'), 'uc033-sc01.ie', 'ns2.uc033-sc01.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc033-sc01.ie'), 'uc033-sc01.ie', 'ns3.uc033-sc01.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc033-sc01.ie', 'ns1.uc033-sc01.ie', '10.10.121.23', NULL, '1'), ('uc033-sc01.ie', 'ns2.uc033-sc01.ie', '1.3.4.2', NULL, '2'), ('uc033-sc01.ie', 'ns3.uc033-sc01.ie', '10.12.13.14', NULL, '3');

