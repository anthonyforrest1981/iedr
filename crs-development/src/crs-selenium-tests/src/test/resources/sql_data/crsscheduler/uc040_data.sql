INSERT INTO DomainHist VALUES (NULL, 'uc040-sc01.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), CURDATE() - Interval 12 Month, CURDATE() - Interval 2 Month, NOW(), 'Test Remark', 'XDD274-IEDR', NOW(), 'NO', '25', NULL, CURDATE(), NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc040-sc01.ie', 'Test Holder 0001', 3, 2, NULL, 1, CURDATE() - Interval 12 Month, CURDATE() - Interval 2 Month, NOW(), 'Test Remark', 'NO', '25', NULL, CURDATE(), NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc040-sc01.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc040-sc01.ie'), 'uc040-sc01.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc040-sc01.ie'), 'uc040-sc01.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc040-sc01.ie'), 'uc040-sc01.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc040-sc01.ie'), 'uc040-sc01.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc040-sc01.ie', 'XDD274-IEDR', 'Admin'), ('uc040-sc01.ie', 'XDD274-IEDR', 'Billing'), ('uc040-sc01.ie', 'XDD274-IEDR', 'Creator'), ('uc040-sc01.ie', 'XDD274-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc040-sc01.ie'), 'uc040-sc01.ie', 'ns1.uc040-sc01.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc040-sc01.ie'), 'uc040-sc01.ie', 'ns2.uc040-sc01.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc040-sc01.ie'), 'uc040-sc01.ie', 'ns3.uc040-sc01.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc040-sc01.ie', 'ns1.uc040-sc01.ie', '10.10.121.23', NULL, '1'), ('uc040-sc01.ie', 'ns2.uc040-sc01.ie', '1.3.4.2', NULL, '2'), ('uc040-sc01.ie', 'ns3.uc040-sc01.ie', '10.12.13.14', NULL, '3');

