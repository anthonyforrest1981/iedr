INSERT INTO DomainHist VALUES (NULL, 'uc064-sc02alt.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc064-sc02alt.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc064-sc02alt.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc064-sc02alt.ie'), 'uc064-sc02alt.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc064-sc02alt.ie'), 'uc064-sc02alt.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc064-sc02alt.ie'), 'uc064-sc02alt.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc064-sc02alt.ie'), 'uc064-sc02alt.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc064-sc02alt.ie', 'XOE550-IEDR', 'Admin'), ('uc064-sc02alt.ie', 'XBC189-IEDR', 'Billing'), ('uc064-sc02alt.ie', 'XBC189-IEDR', 'Creator'), ('uc064-sc02alt.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc064-sc02alt.ie'), 'uc064-sc02alt.ie', 'ns1.uc064-sc02alt.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc064-sc02alt.ie'), 'uc064-sc02alt.ie', 'ns2.uc064-sc02alt.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc064-sc02alt.ie'), 'uc064-sc02alt.ie', 'ns3.uc064-sc02alt.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc064-sc02alt.ie', 'ns1.uc064-sc02alt.ie', '10.10.121.23', NULL, '1'), ('uc064-sc02alt.ie', 'ns2.uc064-sc02alt.ie', '1.3.4.2', NULL, '2'), ('uc064-sc02alt.ie', 'ns3.uc064-sc02alt.ie', '10.12.13.14', NULL, '3');

