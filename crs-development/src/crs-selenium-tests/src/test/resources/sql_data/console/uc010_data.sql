# UC#010: Place Domain in Voluntary NRP - UC010-SC02: Direct Happy Path
INSERT INTO DomainHist VALUES (NULL, 'uc010-sc02.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1),  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XDD274-IEDR', NOW(), 'NO', '25', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc010-sc02.ie', 'Test Holder 0001', 3, 2, NULL, 1,  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '25', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc02.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc02.ie'), 'uc010-sc02.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc02.ie'), 'uc010-sc02.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc02.ie'), 'uc010-sc02.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc02.ie'), 'uc010-sc02.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc010-sc02.ie', 'XOE550-IEDR', 'Admin'), ('uc010-sc02.ie', 'XDD274-IEDR', 'Billing'), ('uc010-sc02.ie', 'XDD274-IEDR', 'Creator'), ('uc010-sc02.ie', 'XDD274-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc02.ie'), 'uc010-sc02.ie', 'ns1.uc010-sc02.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc02.ie'), 'uc010-sc02.ie', 'ns2.uc010-sc02.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc02.ie'), 'uc010-sc02.ie', 'ns3.uc010-sc02.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc010-sc02.ie', 'ns1.uc010-sc02.ie', '10.10.121.23', NULL, '1'), ('uc010-sc02.ie', 'ns2.uc010-sc02.ie', '1.3.4.2', NULL, '2'), ('uc010-sc02.ie', 'ns3.uc010-sc02.ie', '10.12.13.14', NULL, '3');

# UC#010: Place Domain in Voluntary NRP - UC010-SC03: Autorenewed
INSERT INTO DomainHist VALUES (NULL, 'uc010-sc03.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1),  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '81', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc010-sc03.ie', 'Test Holder 0001', 3, 2, NULL, 600,  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '81', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc03.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc03.ie'), 'uc010-sc03.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc03.ie'), 'uc010-sc03.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc03.ie'), 'uc010-sc03.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc03.ie'), 'uc010-sc03.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc010-sc03.ie', 'XOE550-IEDR', 'Admin'), ('uc010-sc03.ie', 'XBC189-IEDR', 'Billing'), ('uc010-sc03.ie', 'XBC189-IEDR', 'Creator'), ('uc010-sc03.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc03.ie'), 'uc010-sc03.ie', 'ns1.uc010-sc03.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc03.ie'), 'uc010-sc03.ie', 'ns2.uc010-sc03.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc03.ie'), 'uc010-sc03.ie', 'ns3.uc010-sc03.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc010-sc03.ie', 'ns1.uc010-sc03.ie', '10.10.121.23', NULL, '1'), ('uc010-sc03.ie', 'ns2.uc010-sc03.ie', '1.3.4.2', NULL, '2'), ('uc010-sc03.ie', 'ns3.uc010-sc03.ie', '10.12.13.14', NULL, '3');

# UC#010: Place Domain in Voluntary NRP - UC010-SC04: Charity
INSERT INTO DomainHist VALUES (NULL, 'uc010-sc04.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1),  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XDD274-IEDR', NOW(), 'NO', '121', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc010-sc04.ie', 'Test Holder 0001', 3, 2, NULL, 1,  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '121', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc04.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc04.ie'), 'uc010-sc04.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc04.ie'), 'uc010-sc04.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc04.ie'), 'uc010-sc04.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc04.ie'), 'uc010-sc04.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc010-sc04.ie', 'XDD274-IEDR', 'Admin'), ('uc010-sc04.ie', 'XDD274-IEDR', 'Billing'), ('uc010-sc04.ie', 'XDD274-IEDR', 'Creator'), ('uc010-sc04.ie', 'XDD274-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc04.ie'), 'uc010-sc04.ie', 'ns1.uc010-sc04.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc04.ie'), 'uc010-sc04.ie', 'ns2.uc010-sc04.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc04.ie'), 'uc010-sc04.ie', 'ns3.uc010-sc04.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc010-sc04.ie', 'ns1.uc010-sc04.ie', '10.10.121.23', NULL, '1'), ('uc010-sc04.ie', 'ns2.uc010-sc04.ie', '1.3.4.2', NULL, '2'), ('uc010-sc04.ie', 'ns3.uc010-sc04.ie', '10.12.13.14', NULL, '3');

# UC#010: Place Domain in Voluntary NRP - UC010-SC06: Locked - Direct VNRP Request Fails
INSERT INTO DomainHist VALUES (NULL, 'uc010-sc06.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1),  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XDD274-IEDR', NOW(), 'NO', '297', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, CURDATE() - Interval 9 Month, CURDATE() + Interval 3 Month);
INSERT INTO Domain VALUES ('uc010-sc06.ie', 'Test Holder 0001', 3, 2, NULL, 1,  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '297', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, CURDATE() - Interval 9 Month, CURDATE() + Interval 3 Month, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc06.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc06.ie'), 'uc010-sc06.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc06.ie'), 'uc010-sc06.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc06.ie'), 'uc010-sc06.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc06.ie'), 'uc010-sc06.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc010-sc06.ie', 'XOE550-IEDR', 'Admin'), ('uc010-sc06.ie', 'XDD274-IEDR', 'Billing'), ('uc010-sc06.ie', 'XDD274-IEDR', 'Creator'), ('uc010-sc06.ie', 'XDD274-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc06.ie'), 'uc010-sc06.ie', 'ns1.uc010-sc06.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc06.ie'), 'uc010-sc06.ie', 'ns2.uc010-sc06.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc06.ie'), 'uc010-sc06.ie', 'ns3.uc010-sc06.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc010-sc06.ie', 'ns1.uc010-sc06.ie', '10.10.121.23', NULL, '1'), ('uc010-sc06.ie', 'ns2.uc010-sc06.ie', '1.3.4.2', NULL, '2'), ('uc010-sc06.ie', 'ns3.uc010-sc06.ie', '10.12.13.14', NULL, '3');

# UC#010: Place Domain in Voluntary NRP - UC010-SC09: Voluntary Mailed - VNRP Request Fails
INSERT INTO DomainHist VALUES (NULL, 'uc010-sc09.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1),  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '20', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc010-sc09.ie', 'Test Holder 0001', 3, 2, NULL, 600,  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '20', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc09.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc09.ie'), 'uc010-sc09.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc09.ie'), 'uc010-sc09.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc09.ie'), 'uc010-sc09.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc09.ie'), 'uc010-sc09.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc010-sc09.ie', 'XOE550-IEDR', 'Admin'), ('uc010-sc09.ie', 'XBC189-IEDR', 'Billing'), ('uc010-sc09.ie', 'XBC189-IEDR', 'Creator'), ('uc010-sc09.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc09.ie'), 'uc010-sc09.ie', 'ns1.uc010-sc09.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc09.ie'), 'uc010-sc09.ie', 'ns2.uc010-sc09.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc09.ie'), 'uc010-sc09.ie', 'ns3.uc010-sc09.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc010-sc09.ie', 'ns1.uc010-sc09.ie', '10.10.121.23', NULL, '1'), ('uc010-sc09.ie', 'ns2.uc010-sc09.ie', '1.3.4.2', NULL, '2'), ('uc010-sc09.ie', 'ns3.uc010-sc09.ie', '10.12.13.14', NULL, '3');

# UC#010: Place Domain in Voluntary NRP - UC010-SC10: Voluntary Suspended - VNRP Request Fails
INSERT INTO DomainHist VALUES (NULL, 'uc010-sc10.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1),  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '21', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc010-sc10.ie', 'Test Holder 0001', 3, 2, NULL, 600,  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '21', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc10.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc10.ie'), 'uc010-sc10.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc10.ie'), 'uc010-sc10.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc10.ie'), 'uc010-sc10.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc10.ie'), 'uc010-sc10.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc010-sc10.ie', 'XOE550-IEDR', 'Admin'), ('uc010-sc10.ie', 'XBC189-IEDR', 'Billing'), ('uc010-sc10.ie', 'XBC189-IEDR', 'Creator'), ('uc010-sc10.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc10.ie'), 'uc010-sc10.ie', 'ns1.uc010-sc10.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc10.ie'), 'uc010-sc10.ie', 'ns2.uc010-sc10.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc10.ie'), 'uc010-sc10.ie', 'ns3.uc010-sc10.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc010-sc10.ie', 'ns1.uc010-sc10.ie', '10.10.121.23', NULL, '1'), ('uc010-sc10.ie', 'ns2.uc010-sc10.ie', '1.3.4.2', NULL, '2'), ('uc010-sc10.ie', 'ns3.uc010-sc10.ie', '10.12.13.14', NULL, '3');

# UC#010: Place Domain in Voluntary NRP - UC010-SC11: Involuntary Mailed - VNRP Request Fails
INSERT INTO DomainHist VALUES (NULL, 'uc010-sc11.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1),  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '18', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc010-sc11.ie', 'Test Holder 0001', 3, 2, NULL, 600,  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '18', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc11.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc11.ie'), 'uc010-sc11.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc11.ie'), 'uc010-sc11.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc11.ie'), 'uc010-sc11.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc11.ie'), 'uc010-sc11.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc010-sc11.ie', 'XOE550-IEDR', 'Admin'), ('uc010-sc11.ie', 'XBC189-IEDR', 'Billing'), ('uc010-sc11.ie', 'XBC189-IEDR', 'Creator'), ('uc010-sc11.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc11.ie'), 'uc010-sc11.ie', 'ns1.uc010-sc11.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc11.ie'), 'uc010-sc11.ie', 'ns2.uc010-sc11.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc11.ie'), 'uc010-sc11.ie', 'ns3.uc010-sc11.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc010-sc11.ie', 'ns1.uc010-sc11.ie', '10.10.121.23', NULL, '1'), ('uc010-sc11.ie', 'ns2.uc010-sc11.ie', '1.3.4.2', NULL, '2'), ('uc010-sc11.ie', 'ns3.uc010-sc11.ie', '10.12.13.14', NULL, '3');

# UC#010: Place Domain in Voluntary NRP - UC010-SC12: Involuntary Suspended - VNRP Request Fails
INSERT INTO DomainHist VALUES (NULL, 'uc010-sc12.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1),  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '19', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc010-sc12.ie', 'Test Holder 0001', 3, 2, NULL, 600,  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '19', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc12.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc12.ie'), 'uc010-sc12.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc12.ie'), 'uc010-sc12.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc12.ie'), 'uc010-sc12.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc12.ie'), 'uc010-sc12.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc010-sc12.ie', 'XOE550-IEDR', 'Admin'), ('uc010-sc12.ie', 'XBC189-IEDR', 'Billing'), ('uc010-sc12.ie', 'XBC189-IEDR', 'Creator'), ('uc010-sc12.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc12.ie'), 'uc010-sc12.ie', 'ns1.uc010-sc12.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc12.ie'), 'uc010-sc12.ie', 'ns2.uc010-sc12.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc12.ie'), 'uc010-sc12.ie', 'ns3.uc010-sc12.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc010-sc12.ie', 'ns1.uc010-sc12.ie', '10.10.121.23', NULL, '1'), ('uc010-sc12.ie', 'ns2.uc010-sc12.ie', '1.3.4.2', NULL, '2'), ('uc010-sc12.ie', 'ns3.uc010-sc12.ie', '10.12.13.14', NULL, '3');

# UC#010: Place Domain in Voluntary NRP - UC010-SC13: Transfer Pending - Active
INSERT INTO DomainHist VALUES (NULL, 'uc010-sc13.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1),  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '390', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc010-sc13.ie', 'Test Holder 0001', 3, 2, NULL, 600,  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '390', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc13.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc13.ie'), 'uc010-sc13.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc13.ie'), 'uc010-sc13.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc13.ie'), 'uc010-sc13.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc13.ie'), 'uc010-sc13.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc010-sc13.ie', 'XOE550-IEDR', 'Admin'), ('uc010-sc13.ie', 'XBC189-IEDR', 'Billing'), ('uc010-sc13.ie', 'XBC189-IEDR', 'Creator'), ('uc010-sc13.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc13.ie'), 'uc010-sc13.ie', 'ns1.uc010-sc13.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc13.ie'), 'uc010-sc13.ie', 'ns2.uc010-sc13.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc13.ie'), 'uc010-sc13.ie', 'ns3.uc010-sc13.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc010-sc13.ie', 'ns1.uc010-sc13.ie', '10.10.121.23', NULL, '1'), ('uc010-sc13.ie', 'ns2.uc010-sc13.ie', '1.3.4.2', NULL, '2'), ('uc010-sc13.ie', 'ns3.uc010-sc13.ie', '10.12.13.14', NULL, '3');

# UC#010: Place Domain in Voluntary NRP - UC010-SC14: Transfer Pending - Involuntary NRP - VNRP Request Fails
INSERT INTO DomainHist VALUES (NULL, 'uc010-sc14.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1),  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '438', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc010-sc14.ie', 'Test Holder 0001', 3, 2, NULL, 600,  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '438', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc14.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc14.ie'), 'uc010-sc14.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc14.ie'), 'uc010-sc14.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc14.ie'), 'uc010-sc14.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc14.ie'), 'uc010-sc14.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc010-sc14.ie', 'XOE550-IEDR', 'Admin'), ('uc010-sc14.ie', 'XBC189-IEDR', 'Billing'), ('uc010-sc14.ie', 'XBC189-IEDR', 'Creator'), ('uc010-sc14.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc14.ie'), 'uc010-sc14.ie', 'ns1.uc010-sc14.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc14.ie'), 'uc010-sc14.ie', 'ns2.uc010-sc14.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc14.ie'), 'uc010-sc14.ie', 'ns3.uc010-sc14.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc010-sc14.ie', 'ns1.uc010-sc14.ie', '10.10.121.23', NULL, '1'), ('uc010-sc14.ie', 'ns2.uc010-sc14.ie', '1.3.4.2', NULL, '2'), ('uc010-sc14.ie', 'ns3.uc010-sc14.ie', '10.12.13.14', NULL, '3');

# UC#010: Place Domain in Voluntary NRP - UC010-SC15: Transfer Pending - Voluntary NRP - VNRP Request Fails
INSERT INTO DomainHist VALUES (NULL, 'uc010-sc15.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1),  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '486', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc010-sc15.ie', 'Test Holder 0001', 3, 2, NULL, 600,  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '486', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc15.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc15.ie'), 'uc010-sc15.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc15.ie'), 'uc010-sc15.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc15.ie'), 'uc010-sc15.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc15.ie'), 'uc010-sc15.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc010-sc15.ie', 'XOE550-IEDR', 'Admin'), ('uc010-sc15.ie', 'XBC189-IEDR', 'Billing'), ('uc010-sc15.ie', 'XBC189-IEDR', 'Creator'), ('uc010-sc15.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc15.ie'), 'uc010-sc15.ie', 'ns1.uc010-sc15.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc15.ie'), 'uc010-sc15.ie', 'ns2.uc010-sc15.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-sc15.ie'), 'uc010-sc15.ie', 'ns3.uc010-sc15.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc010-sc15.ie', 'ns1.uc010-sc15.ie', '10.10.121.23', NULL, '1'), ('uc010-sc15.ie', 'ns2.uc010-sc15.ie', '1.3.4.2', NULL, '2'), ('uc010-sc15.ie', 'ns3.uc010-sc15.ie', '10.12.13.14', NULL, '3');

# UC#010: Place Domain in Voluntary NRP - Direct - Admin-C = Bill-C
INSERT INTO DomainHist VALUES (NULL, 'uc010-nosc01.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1),  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XDD274-IEDR', NOW(), 'NO', '25', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc010-nosc01.ie', 'Test Holder 0001', 3, 2, NULL, 1,  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '25', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-nosc01.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-nosc01.ie'), 'uc010-nosc01.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-nosc01.ie'), 'uc010-nosc01.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-nosc01.ie'), 'uc010-nosc01.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-nosc01.ie'), 'uc010-nosc01.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc010-nosc01.ie', 'XDD274-IEDR', 'Admin'), ('uc010-nosc01.ie', 'XDD274-IEDR', 'Billing'), ('uc010-nosc01.ie', 'XDD274-IEDR', 'Creator'), ('uc010-nosc01.ie', 'XDD274-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-nosc01.ie'), 'uc010-nosc01.ie', 'ns1.uc010-nosc01.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-nosc01.ie'), 'uc010-nosc01.ie', 'ns2.uc010-nosc01.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-nosc01.ie'), 'uc010-nosc01.ie', 'ns3.uc010-nosc01.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc010-nosc01.ie', 'ns1.uc010-nosc01.ie', '10.10.121.23', NULL, '1'), ('uc010-nosc01.ie', 'ns2.uc010-nosc01.ie', '1.3.4.2', NULL, '2'), ('uc010-nosc01.ie', 'ns3.uc010-nosc01.ie', '10.12.13.14', NULL, '3');

# UC#010: Place Domain in Voluntary NRP - Registrar - Admin-C = Bill-C
INSERT INTO DomainHist VALUES (NULL, 'uc010-nosc02.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1),  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc010-nosc02.ie', 'Test Holder 0001', 3, 2, NULL, 600,  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-nosc02.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-nosc02.ie'), 'uc010-nosc02.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-nosc02.ie'), 'uc010-nosc02.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-nosc02.ie'), 'uc010-nosc02.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-nosc02.ie'), 'uc010-nosc02.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc010-nosc02.ie', 'XBC189-IEDR', 'Admin'), ('uc010-nosc02.ie', 'XBC189-IEDR', 'Billing'), ('uc010-nosc02.ie', 'XBC189-IEDR', 'Creator'), ('uc010-nosc02.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-nosc02.ie'), 'uc010-nosc02.ie', 'ns1.uc010-nosc02.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-nosc02.ie'), 'uc010-nosc02.ie', 'ns2.uc010-nosc02.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc010-nosc02.ie'), 'uc010-nosc02.ie', 'ns3.uc010-nosc02.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc010-nosc02.ie', 'ns1.uc010-nosc02.ie', '10.10.121.23', NULL, '1'), ('uc010-nosc02.ie', 'ns2.uc010-nosc02.ie', '1.3.4.2', NULL, '2'), ('uc010-nosc02.ie', 'ns3.uc010-nosc02.ie', '10.12.13.14', NULL, '3');

