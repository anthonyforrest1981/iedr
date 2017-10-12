INSERT INTO DomainHist VALUES (NULL, 'uc013-cdht.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600),  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc013-cdht.ie', 'Test Holder 0001', 3, 2, NULL, 600,  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc013-cdht.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc013-cdht.ie'), 'uc013-cdht.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc013-cdht.ie'), 'uc013-cdht.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc013-cdht.ie'), 'uc013-cdht.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc013-cdht.ie'), 'uc013-cdht.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc013-cdht.ie', 'XOE550-IEDR', 'Admin'), ('uc013-cdht.ie', 'XBC189-IEDR', 'Billing'), ('uc013-cdht.ie', 'XBC189-IEDR', 'Creator'), ('uc013-cdht.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc013-cdht.ie'), 'uc013-cdht.ie', 'ns1.dns.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc013-cdht.ie'), 'uc013-cdht.ie', 'ns2.dns.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc013-cdht.ie'), 'uc013-cdht.ie', 'ns3.dns.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('uc013-cdht.ie', 'ns1.dns.ie', NULL, NULL, '1'), ('uc013-cdht.ie', 'ns2.dns.ie', NULL, NULL, '2'), ('uc013-cdht.ie', 'ns3.dns.ie', NULL, NULL, '3');

# UC#013: Change Domain Holder Type - UC013-SC01: Billable to Charity
# DSM: 81
# UC#013: Change Domain Holder Type - UC013-SC02: Billable to IEDR - Unpublished
# DSM: 49
# UC#013: Change Domain Holder Type - UC013-SC03: Charity to Billable
# DSM: 113
# UC#013: Change Domain Holder Type - UC013-SC04: Charity to Non-billable
# DSM: 116
# UC#013: Change Domain Holder Type - UC013-SC05: Non-billable to Billable - Vol NRP
# DSM: 309
# UC#013: Change Domain Holder Type - UC013-SC06: IEDR to Non-billable
# DSM: 386
# UC#013: Change Domain Holder Type - UC013-SC07: Billable GIBO Pre-audit to Charity
# DSM: 23
# UC#013: Change Domain Holder Type - UC013-SC08: Billable Inv NRP to Non-billable
# DSM: 82
# UC#013: Change Domain Holder Type - UC013-SC09: WIPO disputed
# DSM: 65
# UC#013: Change Domain Holder Type - UC013-SC10: Charity To Billable - Transfer Pending - Active
# DSM: 402
# UC#013: Change Domain Holder Type - UC013-SC11: To Charity - Transfer Pending - Vol. NRP
# DSM: 486
# UC#013: Change Domain Holder Type - UC013-SC12: To Charity - Transfer Pending - Inv. NRP
# DSM: 438
# UC#013: Change Domain Holder Type - UC013-SC13: Billable GIBO Post-audit to Charity
# DSM: 24
# UC#013: Change Domain Holder Type - UC013-SC14: Billable to IEDR - Published
# DSM: 49
