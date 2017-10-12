# Renewal Date Passes - Registrar
INSERT INTO DomainHist VALUES (NULL, 'uc012-ren-r.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE() - Interval 1 Day, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc012-ren-r.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE() - Interval 1 Day, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc012-ren-r.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc012-ren-r.ie'), 'uc012-ren-r.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc012-ren-r.ie'), 'uc012-ren-r.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc012-ren-r.ie'), 'uc012-ren-r.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc012-ren-r.ie'), 'uc012-ren-r.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc012-ren-r.ie', 'XBC189-IEDR', 'Admin'), ('uc012-ren-r.ie', 'XBC189-IEDR', 'Billing'), ('uc012-ren-r.ie', 'XBC189-IEDR', 'Creator'), ('uc012-ren-r.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc012-ren-r.ie'), 'uc012-ren-r.ie', 'ns1.uc012-ren-r.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc012-ren-r.ie'), 'uc012-ren-r.ie', 'ns2.uc012-ren-r.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc012-ren-r.ie'), 'uc012-ren-r.ie', 'ns3.uc012-ren-r.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc012-ren-r.ie', 'ns1.uc012-ren-r.ie', '10.10.121.23', NULL, '1'), ('uc012-ren-r.ie', 'ns2.uc012-ren-r.ie', '1.3.4.2', NULL, '2'), ('uc012-ren-r.ie', 'ns3.uc012-ren-r.ie', '10.12.13.14', NULL, '3');

# Renewal Date Passes - Registrar - 2 admins
INSERT INTO DomainHist VALUES (NULL, 'uc012-ren-r-2.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE() - Interval 1 Day, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc012-ren-r-2.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE() - Interval 1 Day, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc012-ren-r-2.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc012-ren-r-2.ie'), 'uc012-ren-r-2.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc012-ren-r-2.ie'), 'uc012-ren-r-2.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc012-ren-r-2.ie'), 'uc012-ren-r-2.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc012-ren-r-2.ie'), 'uc012-ren-r-2.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc012-ren-r-2.ie'), 'uc012-ren-r-2.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc012-ren-r-2.ie', 'XBC189-IEDR', 'Admin'), ('uc012-ren-r-2.ie', 'XOE550-IEDR', 'Admin'), ('uc012-ren-r-2.ie', 'XBC189-IEDR', 'Billing'), ('uc012-ren-r-2.ie', 'XBC189-IEDR', 'Creator'), ('uc012-ren-r-2.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc012-ren-r-2.ie'), 'uc012-ren-r-2.ie', 'ns1.uc012-ren-r-2.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc012-ren-r-2.ie'), 'uc012-ren-r-2.ie', 'ns2.uc012-ren-r-2.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc012-ren-r-2.ie'), 'uc012-ren-r-2.ie', 'ns3.uc012-ren-r-2.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc012-ren-r-2.ie', 'ns1.uc012-ren-r-2.ie', '10.10.121.23', NULL, '1'), ('uc012-ren-r-2.ie', 'ns2.uc012-ren-r-2.ie', '1.3.4.2', NULL, '2'), ('uc012-ren-r-2.ie', 'ns3.uc012-ren-r-2.ie', '10.12.13.14', NULL, '3');

# Renewal Date Passes - Direct
INSERT INTO DomainHist VALUES (NULL, 'uc012-ren-d-a.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), CURDATE() - Interval 12 Month, CURDATE() - Interval 1 Day, NOW(), 'Test Remark', 'XDD274-IEDR', NOW(), 'NO', '25', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc012-ren-d-a.ie', 'Test Holder 0001', 3, 2, NULL, 1, CURDATE() - Interval 12 Month, CURDATE() - Interval 1 Day, NOW(), 'Test Remark', 'NO', '25', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc012-ren-d-a.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc012-ren-d-a.ie'), 'uc012-ren-d-a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc012-ren-d-a.ie'), 'uc012-ren-d-a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc012-ren-d-a.ie'), 'uc012-ren-d-a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc012-ren-d-a.ie'), 'uc012-ren-d-a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc012-ren-d-a.ie', 'XDD274-IEDR', 'Admin'), ('uc012-ren-d-a.ie', 'XDD274-IEDR', 'Billing'), ('uc012-ren-d-a.ie', 'XDD274-IEDR', 'Creator'), ('uc012-ren-d-a.ie', 'XDD274-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc012-ren-d-a.ie'), 'uc012-ren-d-a.ie', 'ns1.uc012-ren-d-a.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc012-ren-d-a.ie'), 'uc012-ren-d-a.ie', 'ns2.uc012-ren-d-a.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc012-ren-d-a.ie'), 'uc012-ren-d-a.ie', 'ns3.uc012-ren-d-a.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc012-ren-d-a.ie', 'ns1.uc012-ren-d-a.ie', '10.10.121.23', NULL, '1'), ('uc012-ren-d-a.ie', 'ns2.uc012-ren-d-a.ie', '1.3.4.2', NULL, '2'), ('uc012-ren-d-a.ie', 'ns3.uc012-ren-d-a.ie', '10.12.13.14', NULL, '3');

# Renewal Date Passes - Direct - adminC != billC
INSERT INTO DomainHist VALUES (NULL, 'uc012-ren-d-b.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), CURDATE() - Interval 12 Month, CURDATE() - Interval 1 Day, NOW(), 'Test Remark', 'XDD274-IEDR', NOW(), 'NO', '25', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc012-ren-d-b.ie', 'Test Holder 0001', 3, 2, NULL, 1, CURDATE() - Interval 12 Month, CURDATE() - Interval 1 Day, NOW(), 'Test Remark', 'NO', '25', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc012-ren-d-b.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc012-ren-d-b.ie'), 'uc012-ren-d-b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc012-ren-d-b.ie'), 'uc012-ren-d-b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc012-ren-d-b.ie'), 'uc012-ren-d-b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc012-ren-d-b.ie'), 'uc012-ren-d-b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc012-ren-d-b.ie', 'XBC189-IEDR', 'Admin'), ('uc012-ren-d-b.ie', 'XDD274-IEDR', 'Billing'), ('uc012-ren-d-b.ie', 'XDD274-IEDR', 'Creator'), ('uc012-ren-d-b.ie', 'XDD274-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc012-ren-d-b.ie'), 'uc012-ren-d-b.ie', 'ns1.uc012-ren-d-b.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc012-ren-d-b.ie'), 'uc012-ren-d-b.ie', 'ns2.uc012-ren-d-b.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc012-ren-d-b.ie'), 'uc012-ren-d-b.ie', 'ns3.uc012-ren-d-b.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc012-ren-d-b.ie', 'ns1.uc012-ren-d-b.ie', '10.10.121.23', NULL, '1'), ('uc012-ren-d-b.ie', 'ns2.uc012-ren-d-b.ie', '1.3.4.2', NULL, '2'), ('uc012-ren-d-b.ie', 'ns3.uc012-ren-d-b.ie', '10.12.13.14', NULL, '3');

# Suspension Date Passes - Registrar
INSERT INTO DomainHist VALUES (NULL, 'uc012-susp-r.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '18', CURDATE() - Interval 1 Day, CURDATE() + Interval 30 Day, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc012-susp-r.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '18', CURDATE() - Interval 1 Day, CURDATE() + Interval 30 Day, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc012-susp-r.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc012-susp-r.ie'), 'uc012-susp-r.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc012-susp-r.ie'), 'uc012-susp-r.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc012-susp-r.ie'), 'uc012-susp-r.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc012-susp-r.ie'), 'uc012-susp-r.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc012-susp-r.ie', 'XBC189-IEDR', 'Admin'), ('uc012-susp-r.ie', 'XBC189-IEDR', 'Billing'), ('uc012-susp-r.ie', 'XBC189-IEDR', 'Creator'), ('uc012-susp-r.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc012-susp-r.ie'), 'uc012-susp-r.ie', 'ns1.uc012-susp-r.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc012-susp-r.ie'), 'uc012-susp-r.ie', 'ns2.uc012-susp-r.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc012-susp-r.ie'), 'uc012-susp-r.ie', 'ns3.uc012-susp-r.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc012-susp-r.ie', 'ns1.uc012-susp-r.ie', '10.10.121.23', NULL, '1'), ('uc012-susp-r.ie', 'ns2.uc012-susp-r.ie', '1.3.4.2', NULL, '2'), ('uc012-susp-r.ie', 'ns3.uc012-susp-r.ie', '10.12.13.14', NULL, '3');

# Suspension Date Passes - Direct
INSERT INTO DomainHist VALUES (NULL, 'uc012-susp-d.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE() - Interval 1 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '1', CURDATE() - Interval 1 Day, CURDATE() + Interval 30 Day, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, CURDATE() - Interval 9 Month, CURDATE() + Interval 3 Month);
INSERT INTO Domain VALUES ('uc012-susp-d.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE() - Interval 1 Month, NOW(), 'Test Remark', 'NO', '1', CURDATE() - Interval 1 Day, CURDATE() + Interval 30 Day, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, CURDATE() - Interval 9 Month, CURDATE() + Interval 3 Month, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc012-susp-d.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc012-susp-d.ie'), 'uc012-susp-d.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc012-susp-d.ie'), 'uc012-susp-d.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc012-susp-d.ie'), 'uc012-susp-d.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc012-susp-d.ie'), 'uc012-susp-d.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc012-susp-d.ie', 'XDD274-IEDR', 'Admin'), ('uc012-susp-d.ie', 'XDD274-IEDR', 'Billing'), ('uc012-susp-d.ie', 'XDD274-IEDR', 'Creator'), ('uc012-susp-d.ie', 'XDD274-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc012-susp-d.ie'), 'uc012-susp-d.ie', 'ns1.uc012-susp-d.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc012-susp-d.ie'), 'uc012-susp-d.ie', 'ns2.uc012-susp-d.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc012-susp-d.ie'), 'uc012-susp-d.ie', 'ns3.uc012-susp-d.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc012-susp-d.ie', 'ns1.uc012-susp-d.ie', '10.10.121.23', NULL, '1'), ('uc012-susp-d.ie', 'ns2.uc012-susp-d.ie', '1.3.4.2', NULL, '2'), ('uc012-susp-d.ie', 'ns3.uc012-susp-d.ie', '10.12.13.14', NULL, '3');

# Deletion Date Passes - Registrar
INSERT INTO DomainHist VALUES (NULL, 'uc012-del-r.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE() - Interval 2 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '19', NULL, CURDATE() - Interval 1 Day, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc012-del-r.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE() - Interval 2 Month, NOW(), 'Test Remark', 'NO', '19', NULL, CURDATE() - Interval 1 Day, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc012-del-r.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc012-del-r.ie'), 'uc012-del-r.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc012-del-r.ie'), 'uc012-del-r.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc012-del-r.ie'), 'uc012-del-r.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc012-del-r.ie'), 'uc012-del-r.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc012-del-r.ie', 'XBC189-IEDR', 'Admin'), ('uc012-del-r.ie', 'XBC189-IEDR', 'Billing'), ('uc012-del-r.ie', 'XBC189-IEDR', 'Creator'), ('uc012-del-r.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc012-del-r.ie'), 'uc012-del-r.ie', 'ns1.uc012-del-r.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc012-del-r.ie'), 'uc012-del-r.ie', 'ns2.uc012-del-r.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc012-del-r.ie'), 'uc012-del-r.ie', 'ns3.uc012-del-r.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc012-del-r.ie', 'ns1.uc012-del-r.ie', '10.10.121.23', NULL, '1'), ('uc012-del-r.ie', 'ns2.uc012-del-r.ie', '1.3.4.2', NULL, '2'), ('uc012-del-r.ie', 'ns3.uc012-del-r.ie', '10.12.13.14', NULL, '3');

# Deletion Date Passes - Direct
INSERT INTO DomainHist VALUES (NULL, 'uc012-del-d.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE() - Interval 2 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '19', NULL, CURDATE() - Interval 1 Day, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc012-del-d.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE() - Interval 2 Month, NOW(), 'Test Remark', 'NO', '19', NULL, CURDATE() - Interval 1 Day, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc012-del-d.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc012-del-d.ie'), 'uc012-del-d.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc012-del-d.ie'), 'uc012-del-d.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc012-del-d.ie'), 'uc012-del-d.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc012-del-d.ie'), 'uc012-del-d.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc012-del-d.ie', 'XDD274-IEDR', 'Admin'), ('uc012-del-d.ie', 'XDD274-IEDR', 'Billing'), ('uc012-del-d.ie', 'XDD274-IEDR', 'Creator'), ('uc012-del-d.ie', 'XDD274-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc012-del-d.ie'), 'uc012-del-d.ie', 'ns1.uc012-del-d.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc012-del-d.ie'), 'uc012-del-d.ie', 'ns2.uc012-del-d.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc012-del-d.ie'), 'uc012-del-d.ie', 'ns3.uc012-del-d.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc012-del-d.ie', 'ns1.uc012-del-d.ie', '10.10.121.23', NULL, '1'), ('uc012-del-d.ie', 'ns2.uc012-del-d.ie', '1.3.4.2', NULL, '2'), ('uc012-del-d.ie', 'ns3.uc012-del-d.ie', '10.12.13.14', NULL, '3');

