INSERT INTO DomainHist VALUES (NULL, 'uc086-ab.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', 1, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NOW(), NOW() + INTERVAL 1 YEAR);
INSERT INTO Domain VALUES ('uc086-ab.ie', 'Test Holder 0001', 3, 2, NULL, '00000600', CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', 1, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NOW(), NOW() + INTERVAL 1 YEAR, (SELECT MAX(Chng_ID) FROM DomainHist WHERE D_Name = 'uc086-ab.ie'));
INSERT INTO ContactHist VALUES
  ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc086-ab.ie'), 'uc086-ab.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
  ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc086-ab.ie'), 'uc086-ab.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
  ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc086-ab.ie'), 'uc086-ab.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
  ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc086-ab.ie'), 'uc086-ab.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc086-ab.ie', 'XOE550-IEDR', 'Admin'), ('uc086-ab.ie', 'XBC189-IEDR', 'Billing'), ('uc086-ab.ie', 'XBC189-IEDR', 'Creator'), ('uc086-ab.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
  ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc086-ab.ie'), 'uc086-ab.ie', 'ns1.dns.ie', NULL, NULL, '1'),
  ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc086-ab.ie'), 'uc086-ab.ie', 'ns2.dns.ie', NULL, NULL, '2'),
  ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc086-ab.ie'), 'uc086-ab.ie', 'ns3.dns.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('uc086-ab.ie', 'ns1.dns.ie', NULL, NULL, '1'), ('uc086-ab.ie', 'ns2.dns.ie', NULL, NULL, '2'), ('uc086-ab.ie', 'ns3.dns.ie', NULL, NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc086-ac.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', 1, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NOW(), NOW() + INTERVAL 1 YEAR);
INSERT INTO Domain VALUES ('uc086-ac.ie', 'Test Holder 0001', 3, 2, NULL, '00000600', CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', 1, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NOW(), NOW() + INTERVAL 1 YEAR, (SELECT MAX(Chng_ID) FROM DomainHist WHERE D_Name = 'uc086-ac.ie'));
INSERT INTO ContactHist VALUES
  ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc086-ac.ie'), 'uc086-ac.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
  ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc086-ac.ie'), 'uc086-ac.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
  ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc086-ac.ie'), 'uc086-ac.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
  ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc086-ac.ie'), 'uc086-ac.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc086-ac.ie', 'XOE550-IEDR', 'Admin'), ('uc086-ac.ie', 'XBC189-IEDR', 'Billing'), ('uc086-ac.ie', 'XBC189-IEDR', 'Creator'), ('uc086-ac.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
  ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc086-ac.ie'), 'uc086-ac.ie', 'ns1.dns.ie', NULL, NULL, '1'),
  ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc086-ac.ie'), 'uc086-ac.ie', 'ns2.dns.ie', NULL, NULL, '2'),
  ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc086-ac.ie'), 'uc086-ac.ie', 'ns3.dns.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('uc086-ac.ie', 'ns1.dns.ie', NULL, NULL, '1'), ('uc086-ac.ie', 'ns2.dns.ie', NULL, NULL, '2'), ('uc086-ac.ie', 'ns3.dns.ie', NULL, NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc086-ad.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', 1, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NOW(), NOW() + INTERVAL 1 YEAR);
INSERT INTO Domain VALUES ('uc086-ad.ie', 'Test Holder 0001', 3, 2, NULL, '00000600', CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', 1, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NOW(), NOW() + INTERVAL 1 YEAR, (SELECT MAX(Chng_ID) FROM DomainHist WHERE D_Name = 'uc086-ad.ie'));
INSERT INTO ContactHist VALUES
  ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc086-ad.ie'), 'uc086-ad.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
  ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc086-ad.ie'), 'uc086-ad.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
  ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc086-ad.ie'), 'uc086-ad.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
  ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc086-ad.ie'), 'uc086-ad.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc086-ad.ie', 'XOE550-IEDR', 'Admin'), ('uc086-ad.ie', 'XBC189-IEDR', 'Billing'), ('uc086-ad.ie', 'XBC189-IEDR', 'Creator'), ('uc086-ad.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
  ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc086-ad.ie'), 'uc086-ad.ie', 'ns1.dns.ie', NULL, NULL, '1'),
  ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc086-ad.ie'), 'uc086-ad.ie', 'ns2.dns.ie', NULL, NULL, '2'),
  ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc086-ad.ie'), 'uc086-ad.ie', 'ns3.dns.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('uc086-ad.ie', 'ns1.dns.ie', NULL, NULL, '1'), ('uc086-ad.ie', 'ns2.dns.ie', NULL, NULL, '2'), ('uc086-ad.ie', 'ns3.dns.ie', NULL, NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc086-ae.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', 1, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NOW(), NOW() + INTERVAL 1 YEAR);
INSERT INTO Domain VALUES ('uc086-ae.ie', 'Test Holder 0001', 3, 2, NULL, '00000600', CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', 1, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NOW(), NOW() + INTERVAL 1 YEAR, (SELECT MAX(Chng_ID) FROM DomainHist WHERE D_Name = 'uc086-ae.ie'));
INSERT INTO ContactHist VALUES
  ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc086-ae.ie'), 'uc086-ae.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
  ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc086-ae.ie'), 'uc086-ae.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
  ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc086-ae.ie'), 'uc086-ae.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
  ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc086-ae.ie'), 'uc086-ae.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc086-ae.ie', 'XOE550-IEDR', 'Admin'), ('uc086-ae.ie', 'XBC189-IEDR', 'Billing'), ('uc086-ae.ie', 'XBC189-IEDR', 'Creator'), ('uc086-ae.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
  ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc086-ae.ie'), 'uc086-ae.ie', 'ns1.dns.ie', NULL, NULL, '1'),
  ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc086-ae.ie'), 'uc086-ae.ie', 'ns2.dns.ie', NULL, NULL, '2'),
  ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc086-ae.ie'), 'uc086-ae.ie', 'ns3.dns.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('uc086-ae.ie', 'ns1.dns.ie', NULL, NULL, '1'), ('uc086-ae.ie', 'ns2.dns.ie', NULL, NULL, '2'), ('uc086-ae.ie', 'ns3.dns.ie', NULL, NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc086-af.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc086-af.ie', 'Test Holder 0001', 3, 2, NULL, '00000600', CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_ID) FROM DomainHist WHERE D_Name = 'uc086-af.ie'));
INSERT INTO ContactHist VALUES
  ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc086-af.ie'), 'uc086-af.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
  ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc086-af.ie'), 'uc086-af.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
  ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc086-af.ie'), 'uc086-af.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
  ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc086-af.ie'), 'uc086-af.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc086-af.ie', 'XOE550-IEDR', 'Admin'), ('uc086-af.ie', 'XBC189-IEDR', 'Billing'), ('uc086-af.ie', 'XBC189-IEDR', 'Creator'), ('uc086-af.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
  ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc086-af.ie'), 'uc086-af.ie', 'ns1.dns.ie', NULL, NULL, '1'),
  ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc086-af.ie'), 'uc086-af.ie', 'ns2.dns.ie', NULL, NULL, '2'),
  ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc086-af.ie'), 'uc086-af.ie', 'ns3.dns.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('uc086-af.ie', 'ns1.dns.ie', NULL, NULL, '1'), ('uc086-af.ie', 'ns2.dns.ie', NULL, NULL, '2'), ('uc086-af.ie', 'ns3.dns.ie', NULL, NULL, '3');
