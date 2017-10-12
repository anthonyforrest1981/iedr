# UC#011: Remove Domain from Voluntary NRP - UC011-SC02: Direct Happy Path
INSERT INTO DomainHist VALUES (NULL, 'uc011-sc02.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1),  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark',  'XDD274-IEDR', NOW(), 'NO', '28', CURDATE() + Interval 14 Day, CURDATE() + Interval 28 Day, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc011-sc02.ie', 'Test Holder 0001', 3, 2, NULL, 1,  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '28', CURDATE() + Interval 14 Day, CURDATE() + Interval 28 Day, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc011-sc02.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc011-sc02.ie'), 'uc011-sc02.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc011-sc02.ie'), 'uc011-sc02.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc011-sc02.ie'), 'uc011-sc02.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc011-sc02.ie'), 'uc011-sc02.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc011-sc02.ie', 'XOE550-IEDR', 'Admin'), ('uc011-sc02.ie', 'XDD274-IEDR', 'Billing'), ('uc011-sc02.ie', 'XDD274-IEDR', 'Creator'), ('uc011-sc02.ie', 'XDD274-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc011-sc02.ie'), 'uc011-sc02.ie', 'ns1.uc011-sc02.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc011-sc02.ie'), 'uc011-sc02.ie', 'ns2.uc011-sc02.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc011-sc02.ie'), 'uc011-sc02.ie', 'ns3.uc011-sc02.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc011-sc02.ie', 'ns1.uc011-sc02.ie', '10.10.121.23', NULL, '1'), ('uc011-sc02.ie', 'ns2.uc011-sc02.ie', '1.3.4.2', NULL, '2'), ('uc011-sc02.ie', 'ns3.uc011-sc02.ie', '10.12.13.14', NULL, '3');

# UC#011: Remove Domain from Voluntary NRP - UC011-SC04: Charity
INSERT INTO DomainHist VALUES (NULL, 'uc011-sc04.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1),  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark',  'XDD274-IEDR', NOW(), 'NO', '124', CURDATE() + Interval 14 Day, CURDATE() + Interval 28 Day, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc011-sc04.ie', 'Test Holder 0001', 3, 2, NULL, 1,  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '124', CURDATE() + Interval 14 Day, CURDATE() + Interval 28 Day, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc011-sc04.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc011-sc04.ie'), 'uc011-sc04.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc011-sc04.ie'), 'uc011-sc04.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc011-sc04.ie'), 'uc011-sc04.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc011-sc04.ie'), 'uc011-sc04.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc011-sc04.ie', 'XOE550-IEDR', 'Admin'), ('uc011-sc04.ie', 'XDD274-IEDR', 'Billing'), ('uc011-sc04.ie', 'XDD274-IEDR', 'Creator'), ('uc011-sc04.ie', 'XDD274-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc011-sc04.ie'), 'uc011-sc04.ie', 'ns1.uc011-sc04.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc011-sc04.ie'), 'uc011-sc04.ie', 'ns2.uc011-sc04.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc011-sc04.ie'), 'uc011-sc04.ie', 'ns3.uc011-sc04.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc011-sc04.ie', 'ns1.uc011-sc04.ie', '10.10.121.23', NULL, '1'), ('uc011-sc04.ie', 'ns2.uc011-sc04.ie', '1.3.4.2', NULL, '2'), ('uc011-sc04.ie', 'ns3.uc011-sc04.ie', '10.12.13.14', NULL, '3');

# UC#011: Remove Domain from Voluntary NRP - UC011-SC04: Charity with past renewal date (Alternative)
INSERT INTO DomainHist VALUES (NULL, 'uc011-sc04alt.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1),  CURDATE() - Interval 6 Month, CURDATE() - Interval 3 Month, NOW(), 'Test Remark',  'XDD274-IEDR', NOW(), 'NO', '124', CURDATE() + Interval 14 Day, CURDATE() + Interval 28 Day, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc011-sc04alt.ie', 'Test Holder 0001', 3, 2, NULL, 1,  CURDATE() - Interval 6 Month, CURDATE() - Interval 3 Month, NOW(), 'Test Remark', 'NO', '124', CURDATE() + Interval 14 Day, CURDATE() + Interval 28 Day, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc011-sc04alt.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc011-sc04alt.ie'), 'uc011-sc04alt.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc011-sc04alt.ie'), 'uc011-sc04alt.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc011-sc04alt.ie'), 'uc011-sc04alt.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc011-sc04alt.ie'), 'uc011-sc04alt.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc011-sc04alt.ie', 'XOE550-IEDR', 'Admin'), ('uc011-sc04alt.ie', 'XDD274-IEDR', 'Billing'), ('uc011-sc04alt.ie', 'XDD274-IEDR', 'Creator'), ('uc011-sc04alt.ie', 'XDD274-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc011-sc04alt.ie'), 'uc011-sc04alt.ie', 'ns1.uc011-sc04alt.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc011-sc04alt.ie'), 'uc011-sc04alt.ie', 'ns2.uc011-sc04alt.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc011-sc04alt.ie'), 'uc011-sc04alt.ie', 'ns3.uc011-sc04alt.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc011-sc04alt.ie', 'ns1.uc011-sc04alt.ie', '10.10.121.23', NULL, '1'), ('uc011-sc04alt.ie', 'ns2.uc011-sc04alt.ie', '1.3.4.2', NULL, '2'), ('uc011-sc04alt.ie', 'ns3.uc011-sc04alt.ie', '10.12.13.14', NULL, '3');

# UC#011: Remove Domain from Voluntary NRP - AT - NRP(VM)-RC-NP-17 VM Renewal Re-activation (Registrar Without Payment Option)
# DSM: 20
# UC#011: Remove Domain from Voluntary NRP - AT - NRP(VS)-RC-NP-18 VS Renewal Re-activation (Registrar Without Payment Option)
# DSM: 21
# UC#011: Remove Domain from Voluntary NRP - AT - NRP(VS)-DC-NP-20 VS Renewal Re-activation (Directs Without Payment Option)
# DSM: 29
# UC#011: Remove Domain from Voluntary NRP - AT - NRP(VM)-RC-CHY-21 VM Charity Re-activation (Registrar Without Payment Option)
# DSM: 116
# UC#011: Remove Domain from Voluntary NRP - AT - NRP(VS)-RC-CHY-22 VS Charity Re-activation (Registrar Without Payment Option)
# DSM: 117
# UC#011: Remove Domain from Voluntary NRP - AT - NRP(VM)-RC-CHY-23 VM Non-Billable Re-activation (Registrar Without Payment Option)
# DSM: 308
# UC#011: Remove Domain from Voluntary NRP - AT - NRP(VS)-RC-CHY-24 VS Non-Billable Re-activation (Registrar Without Payment Option)
# DSM: 309
# UC#011: Remove Domain from Voluntary NRP - AT - NRP(VM)-DC-CHY-24 VM Non-Billabke Re-activation (Directs Without Payment Option)
# DSM: 316
# UC#011: Remove Domain from Voluntary NRP - AT - NRP(VS)-DC-CHY-25 VS Non-Billabke Re-activation (Directs Without Payment Option)
# DSM: 317
# UC#011: Remove Domain from Voluntary NRP - AT - NRPW(VM)-RC-NP-58 VM Renewal Re-activation (Registrar Without Payment Option) - WIPO
# DSM: 4
# UC#011: Remove Domain from Voluntary NRP - AT - NRPW(VS)-RC-NP-59 VS Renewal Re-activation (Registrar Without Payment Option) - WIPO
# DSM: 5
# UC#011: Remove Domain from Voluntary NRP - AT - NRPW(VM)-DC-NP-60 VM Renewal Re-activation (Directs - Without Payment Option) - WIPO
# DSM: 12
# UC#011: Remove Domain from Voluntary NRP - AT - NRPW(VS)-DC-NP-61 VS Renewal Re-activation (Directs Without Payment Option) - WIPO
# DSM: 13

INSERT INTO DomainHist VALUES (NULL, 'uc011-nrpdc.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1),  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark',  'XDD274-IEDR', NOW(), 'NO', '25', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc011-nrpdc.ie', 'Test Holder 0001', 3, 2, NULL, 1,  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '25', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc011-nrpdc.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc011-nrpdc.ie'), 'uc011-nrpdc.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc011-nrpdc.ie'), 'uc011-nrpdc.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc011-nrpdc.ie'), 'uc011-nrpdc.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc011-nrpdc.ie'), 'uc011-nrpdc.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc011-nrpdc.ie', 'XOE550-IEDR', 'Admin'), ('uc011-nrpdc.ie', 'XDD274-IEDR', 'Billing'), ('uc011-nrpdc.ie', 'XDD274-IEDR', 'Creator'), ('uc011-nrpdc.ie', 'XDD274-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc011-nrpdc.ie'), 'uc011-nrpdc.ie', 'ns1.uc011-nrpdc.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc011-nrpdc.ie'), 'uc011-nrpdc.ie', 'ns2.uc011-nrpdc.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc011-nrpdc.ie'), 'uc011-nrpdc.ie', 'ns3.uc011-nrpdc.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc011-nrpdc.ie', 'ns1.uc011-nrpdc.ie', '10.10.121.23', NULL, '1'), ('uc011-nrpdc.ie', 'ns2.uc011-nrpdc.ie', '1.3.4.2', NULL, '2'), ('uc011-nrpdc.ie', 'ns3.uc011-nrpdc.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc011-nrprc.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600),  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark',  'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc011-nrprc.ie', 'Test Holder 0001', 3, 2, NULL, 600,  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc011-nrprc.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc011-nrprc.ie'), 'uc011-nrprc.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc011-nrprc.ie'), 'uc011-nrprc.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc011-nrprc.ie'), 'uc011-nrprc.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc011-nrprc.ie'), 'uc011-nrprc.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc011-nrprc.ie', 'XOE550-IEDR', 'Admin'), ('uc011-nrprc.ie', 'XBC189-IEDR', 'Billing'), ('uc011-nrprc.ie', 'XBC189-IEDR', 'Creator'), ('uc011-nrprc.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc011-nrprc.ie'), 'uc011-nrprc.ie', 'ns1.uc011-nrprc.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc011-nrprc.ie'), 'uc011-nrprc.ie', 'ns2.uc011-nrprc.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc011-nrprc.ie'), 'uc011-nrprc.ie', 'ns3.uc011-nrprc.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc011-nrprc.ie', 'ns1.uc011-nrprc.ie', '10.10.121.23', NULL, '1'), ('uc011-nrprc.ie', 'ns2.uc011-nrprc.ie', '1.3.4.2', NULL, '2'), ('uc011-nrprc.ie', 'ns3.uc011-nrprc.ie', '10.12.13.14', NULL, '3');

