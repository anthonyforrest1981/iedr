INSERT INTO NicHandleHist VALUES (NULL, 'EFAA-IEDR', 'Existing Functionality - Direct Admin Contact', (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'ef_aa@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'EFAA-IEDR', 'B', NULL, 'NO', 'XDD274-IEDR', NOW());
INSERT INTO NicHandle VALUES ('EFAA-IEDR', 'Existing Functionality - Direct Admin Contact', 1, 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'ef_aa@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'EFAA-IEDR', 'B', NULL, 'NO', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'EFAA-IEDR'));
INSERT INTO AccessHist VALUES (NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'EFAA-IEDR'), 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 0, 'NO', 'XDD274-IEDR', NOW(), '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '');
INSERT INTO Access VALUES ('EFAA-IEDR', 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 0, 'NO', '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '', (SELECT MAX(A.Chng_Id) FROM AccessHist A JOIN NicHandleHist NH  ON A.Nic_Handle_Chng_Id = NH.Chng_Id WHERE NH.Nic_Handle = 'EFAA-IEDR'));

INSERT INTO NicHandleHist VALUES (NULL, 'EFAB-IEDR', 'Existing Functionality - Registrar Admin Contact', (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'ef_ab@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'EFAB-IEDR', 'B', NULL, 'NO', 'XBC189-IEDR', NOW());
INSERT INTO NicHandle VALUES ('EFAB-IEDR', 'Existing Functionality - Registrar Admin Contact', 600, 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'ef_ab@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'EFAB-IEDR', 'B', NULL, 'NO', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'EFAB-IEDR'));
INSERT INTO AccessHist VALUES (NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'EFAB-IEDR'), 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 0, 'NO', 'XBC189-IEDR', NOW(), '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '');
INSERT INTO Access VALUES ('EFAB-IEDR', 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 0, 'NO', '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '', (SELECT MAX(A.Chng_Id) FROM AccessHist A JOIN NicHandleHist NH  ON A.Nic_Handle_Chng_Id = NH.Chng_Id WHERE NH.Nic_Handle = 'EFAB-IEDR'));

INSERT INTO DomainHist VALUES (NULL, 'ef-nosc01.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XDD274-IEDR', NOW(), 'NO', '25', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('ef-nosc01.ie', 'Test Holder 0001', 3, 2, NULL, 1, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', '25', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc01.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc01.ie'), 'ef-nosc01.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'EFAA-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc01.ie'), 'ef-nosc01.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc01.ie'), 'ef-nosc01.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc01.ie'), 'ef-nosc01.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('ef-nosc01.ie', 'EFAA-IEDR', 'Admin'), ('ef-nosc01.ie', 'XDD274-IEDR', 'Billing'), ('ef-nosc01.ie', 'XDD274-IEDR', 'Creator'), ('ef-nosc01.ie', 'XDD274-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc01.ie'), 'ef-nosc01.ie', 'ns1.ef-nosc01.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc01.ie'), 'ef-nosc01.ie', 'ns2.ef-nosc01.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc01.ie'), 'ef-nosc01.ie', 'ns3.ef-nosc01.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('ef-nosc01.ie', 'ns1.ef-nosc01.ie', '10.10.121.23', NULL, '1'), ('ef-nosc01.ie', 'ns2.ef-nosc01.ie', '1.3.4.2', NULL, '2'), ('ef-nosc01.ie', 'ns3.ef-nosc01.ie', '10.12.13.14', NULL, '3');
INSERT INTO TicketIndex VALUES (NULL);
INSERT INTO Ticket VALUES ((SELECT MAX(id) FROM TicketIndex), 'Modification', 'ef-nosc01.ie', NULL, 'Test Holder 0001 - Modified', NULL, 001, NULL, 2, 3, NULL, 'Holder modification', 'XDD274-IEDR', NULL, NULL, NULL, 'XDD274-IEDR', NULL, 'XDD274-IEDR', NULL, 'XDD274-IEDR', 'Passed', NOW() - INTERVAL 2 Month, 'Passed', NOW() - INTERVAL 2 Month, 'NO', NULL, NULL, NOW() - INTERVAL 2 Month, NULL, NULL, NULL, NOW() - INTERVAL 2 Month, 'NO', NULL, NULL, 'NO', 'Passed', NOW() - INTERVAL 2 Month, 'New', NOW() - INTERVAL 2 Month, 1);
INSERT INTO TicketHist VALUES (NULL, (SELECT T_Number FROM Ticket WHERE D_Name = 'ef-nosc01.ie'), 'Modification', 'ef-nosc01.ie', NULL, 'Test Holder 0001 - Modified', NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), NULL, 2, 3, NULL, 'Holder modification', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), NULL, NULL, NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Passed', NOW() - INTERVAL 2 Month, 'Passed', NOW() - INTERVAL 2 Month, 'NO', NULL, NOW() - INTERVAL 2 Month, NOW() - INTERVAL 2 Month, '', NOW() - INTERVAL 2 Month, 'XDD274-IEDR', NULL, NULL, NOW() - INTERVAL 2 Month, 'NO', 1, NULL, 'NO', 'Passed', NOW() - INTERVAL 2 Month, 'New', NOW() - INTERVAL 2 Month);
DELETE FROM Ticket WHERE D_Name = 'ef-nosc01.ie';

INSERT INTO DomainHist VALUES (NULL, 'ef-nosc02a.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '116', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('ef-nosc02a.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', '116', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc02a.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc02a.ie'), 'ef-nosc02a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc02a.ie'), 'ef-nosc02a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc02a.ie'), 'ef-nosc02a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc02a.ie'), 'ef-nosc02a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('ef-nosc02a.ie', 'XBC189-IEDR', 'Admin'), ('ef-nosc02a.ie', 'XBC189-IEDR', 'Billing'), ('ef-nosc02a.ie', 'XBC189-IEDR', 'Creator'), ('ef-nosc02a.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc02a.ie'), 'ef-nosc02a.ie', 'ns1.ef-nosc02a.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc02a.ie'), 'ef-nosc02a.ie', 'ns2.ef-nosc02a.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc02a.ie'), 'ef-nosc02a.ie', 'ns3.ef-nosc02a.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('ef-nosc02a.ie', 'ns1.ef-nosc02a.ie', '10.10.121.23', NULL, '1'), ('ef-nosc02a.ie', 'ns2.ef-nosc02a.ie', '1.3.4.2', NULL, '2'), ('ef-nosc02a.ie', 'ns3.ef-nosc02a.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'ef-nosc02b.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark Hist', 'CIARA-IEDR', CURDATE(), 'NO', '387', NULL, CURDATE(), NULL, 'authcode', CURDATE(), 0, NULL, NULL);
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc02b.ie'), 'ef-nosc02b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc02b.ie'), 'ef-nosc02b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc02b.ie'), 'ef-nosc02b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc02b.ie'), 'ef-nosc02b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc02b.ie'), 'ef-nosc02b.ie', 'ns1.ef-nosc02b.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc02b.ie'), 'ef-nosc02b.ie', 'ns2.ef-nosc02b.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc02b.ie'), 'ef-nosc02b.ie', 'ns3.ef-nosc02b.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'ef-nosc03.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '116', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('ef-nosc03.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', '116', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc03.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc03.ie'), 'ef-nosc03.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc03.ie'), 'ef-nosc03.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc03.ie'), 'ef-nosc03.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc03.ie'), 'ef-nosc03.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('ef-nosc03.ie', 'XBC189-IEDR', 'Admin'), ('ef-nosc03.ie', 'XBC189-IEDR', 'Billing'), ('ef-nosc03.ie', 'XBC189-IEDR', 'Creator'), ('ef-nosc03.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc03.ie'), 'ef-nosc03.ie', 'ns1.ef-nosc03.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc03.ie'), 'ef-nosc03.ie', 'ns2.ef-nosc03.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc03.ie'), 'ef-nosc03.ie', 'ns3.ef-nosc03.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('ef-nosc03.ie', 'ns1.ef-nosc03.ie', '10.10.121.23', NULL, '1'), ('ef-nosc03.ie', 'ns2.ef-nosc03.ie', '1.3.4.2', NULL, '2'), ('ef-nosc03.ie', 'ns3.ef-nosc03.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'ef-nosc04.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '116', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('ef-nosc04.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', '116', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc04.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc04.ie'), 'ef-nosc04.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc04.ie'), 'ef-nosc04.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc04.ie'), 'ef-nosc04.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc04.ie'), 'ef-nosc04.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('ef-nosc04.ie', 'XBC189-IEDR', 'Admin'), ('ef-nosc04.ie', 'XBC189-IEDR', 'Billing'), ('ef-nosc04.ie', 'XBC189-IEDR', 'Creator'), ('ef-nosc04.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc04.ie'), 'ef-nosc04.ie', 'ns1.ef-nosc04.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc04.ie'), 'ef-nosc04.ie', 'ns2.ef-nosc04.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc04.ie'), 'ef-nosc04.ie', 'ns3.ef-nosc04.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('ef-nosc04.ie', 'ns1.ef-nosc04.ie', '10.10.121.23', NULL, '1'), ('ef-nosc04.ie', 'ns2.ef-nosc04.ie', '1.3.4.2', NULL, '2'), ('ef-nosc04.ie', 'ns3.ef-nosc04.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'ef-nosc05.ie', 'Modified Holder', 2, 5, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 603), CURDATE() - Interval 10 Day, CURDATE() - Interval 10 Day, NOW(), 'Test Remark', 'XNV498-IEDR', NOW(), 'NO', '117', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('ef-nosc05.ie', 'Modified Holder', 2, 5, NULL, 603, CURDATE() - Interval 10 Day, CURDATE() - Interval 10 Day, NOW(), 'Test Remark', 'NO', '117', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc05.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc05.ie'), 'ef-nosc05.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'EFAB-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc05.ie'), 'ef-nosc05.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc05.ie'), 'ef-nosc05.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc05.ie'), 'ef-nosc05.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('ef-nosc05.ie', 'EFAB-IEDR', 'Admin'), ('ef-nosc05.ie', 'XNV498-IEDR', 'Billing'), ('ef-nosc05.ie', 'XNV498-IEDR', 'Creator'), ('ef-nosc05.ie', 'XNV498-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc05.ie'), 'ef-nosc05.ie', 'ns1.ef-nosc05.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc05.ie'), 'ef-nosc05.ie', 'ns2.ef-nosc05.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc05.ie'), 'ef-nosc05.ie', 'ns3.ef-nosc05.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('ef-nosc05.ie', 'ns1.ef-nosc05.ie', '10.10.121.23', NULL, '1'), ('ef-nosc05.ie', 'ns2.ef-nosc05.ie', '1.3.4.2', NULL, '2'), ('ef-nosc05.ie', 'ns3.ef-nosc05.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'ef-nosc05a.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '25', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('ef-nosc05a.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', '25', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc05a.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc05a.ie'), 'ef-nosc05a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc05a.ie'), 'ef-nosc05a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc05a.ie'), 'ef-nosc05a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc05a.ie'), 'ef-nosc05a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('ef-nosc05a.ie', 'XBC189-IEDR', 'Admin'), ('ef-nosc05a.ie', 'XBC189-IEDR', 'Billing'), ('ef-nosc05a.ie', 'XBC189-IEDR', 'Creator'), ('ef-nosc05a.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc05a.ie'), 'ef-nosc05a.ie', 'ns1.ef-nosc05a.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc05a.ie'), 'ef-nosc05a.ie', 'ns2.ef-nosc05a.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc05a.ie'), 'ef-nosc05a.ie', 'ns3.ef-nosc05a.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('ef-nosc05a.ie', 'ns1.ef-nosc05a.ie', '10.10.121.23', NULL, '1'), ('ef-nosc05a.ie', 'ns2.ef-nosc05a.ie', '1.3.4.2', NULL, '2'), ('ef-nosc05a.ie', 'ns3.ef-nosc05a.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'ef-nosc05b.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '25', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('ef-nosc05b.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', '25', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc05b.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc05b.ie'), 'ef-nosc05b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc05b.ie'), 'ef-nosc05b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc05b.ie'), 'ef-nosc05b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc05b.ie'), 'ef-nosc05b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('ef-nosc05b.ie', 'XBC189-IEDR', 'Admin'), ('ef-nosc05b.ie', 'XBC189-IEDR', 'Billing'), ('ef-nosc05b.ie', 'XBC189-IEDR', 'Creator'), ('ef-nosc05b.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc05b.ie'), 'ef-nosc05b.ie', 'ns1.ef-nosc05b.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc05b.ie'), 'ef-nosc05b.ie', 'ns2.ef-nosc05b.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc05b.ie'), 'ef-nosc05b.ie', 'ns3.ef-nosc05b.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('ef-nosc05b.ie', 'ns1.ef-nosc05b.ie', '10.10.121.23', NULL, '1'), ('ef-nosc05b.ie', 'ns2.ef-nosc05b.ie', '1.3.4.2', NULL, '2'), ('ef-nosc05b.ie', 'ns3.ef-nosc05b.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'ef-nosc05c.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '25', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('ef-nosc05c.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', '25', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc05c.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc05c.ie'), 'ef-nosc05c.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc05c.ie'), 'ef-nosc05c.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc05c.ie'), 'ef-nosc05c.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc05c.ie'), 'ef-nosc05c.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('ef-nosc05c.ie', 'XBC189-IEDR', 'Admin'), ('ef-nosc05c.ie', 'XBC189-IEDR', 'Billing'), ('ef-nosc05c.ie', 'XBC189-IEDR', 'Creator'), ('ef-nosc05c.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc05c.ie'), 'ef-nosc05c.ie', 'ns1.ef-nosc05c.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc05c.ie'), 'ef-nosc05c.ie', 'ns2.ef-nosc05c.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc05c.ie'), 'ef-nosc05c.ie', 'ns3.ef-nosc05c.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('ef-nosc05c.ie', 'ns1.ef-nosc05c.ie', '10.10.121.23', NULL, '1'), ('ef-nosc05c.ie', 'ns2.ef-nosc05c.ie', '1.3.4.2', NULL, '2'), ('ef-nosc05c.ie', 'ns3.ef-nosc05c.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'ef-nosc05d.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '25', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('ef-nosc05d.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', '25', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc05d.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc05d.ie'), 'ef-nosc05d.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc05d.ie'), 'ef-nosc05d.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc05d.ie'), 'ef-nosc05d.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc05d.ie'), 'ef-nosc05d.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('ef-nosc05d.ie', 'XBC189-IEDR', 'Admin'), ('ef-nosc05d.ie', 'XBC189-IEDR', 'Billing'), ('ef-nosc05d.ie', 'XBC189-IEDR', 'Creator'), ('ef-nosc05d.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc05d.ie'), 'ef-nosc05d.ie', 'ns1.ef-nosc05d.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc05d.ie'), 'ef-nosc05d.ie', 'ns2.ef-nosc05d.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc05d.ie'), 'ef-nosc05d.ie', 'ns3.ef-nosc05d.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('ef-nosc05d.ie', 'ns1.ef-nosc05d.ie', '10.10.121.23', NULL, '1'), ('ef-nosc05d.ie', 'ns2.ef-nosc05d.ie', '1.3.4.2', NULL, '2'), ('ef-nosc05d.ie', 'ns3.ef-nosc05d.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'ef-nosc06.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XDD274-IEDR', NOW(), 'NO', '25', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('ef-nosc06.ie', 'Test Holder 0001', 3, 2, NULL, 1, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', '25', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc06.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc06.ie'), 'ef-nosc06.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'EFAA-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc06.ie'), 'ef-nosc06.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc06.ie'), 'ef-nosc06.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc06.ie'), 'ef-nosc06.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('ef-nosc06.ie', 'EFAA-IEDR', 'Admin'), ('ef-nosc06.ie', 'XDD274-IEDR', 'Billing'), ('ef-nosc06.ie', 'XDD274-IEDR', 'Creator'), ('ef-nosc06.ie', 'XDD274-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc06.ie'), 'ef-nosc06.ie', 'ns1.ef-nosc06.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc06.ie'), 'ef-nosc06.ie', 'ns2.ef-nosc06.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc06.ie'), 'ef-nosc06.ie', 'ns3.ef-nosc06.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('ef-nosc06.ie', 'ns1.ef-nosc06.ie', '10.10.121.23', NULL, '1'), ('ef-nosc06.ie', 'ns2.ef-nosc06.ie', '1.3.4.2', NULL, '2'), ('ef-nosc06.ie', 'ns3.ef-nosc06.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'ef-nosc07.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('ef-nosc07.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc07.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc07.ie'), 'ef-nosc07.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'EFAB-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc07.ie'), 'ef-nosc07.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc07.ie'), 'ef-nosc07.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc07.ie'), 'ef-nosc07.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('ef-nosc07.ie', 'EFAB-IEDR', 'Admin'), ('ef-nosc07.ie', 'XBC189-IEDR', 'Billing'), ('ef-nosc07.ie', 'XBC189-IEDR', 'Creator'), ('ef-nosc07.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc07.ie'), 'ef-nosc07.ie', 'ns1.ef-nosc07.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc07.ie'), 'ef-nosc07.ie', 'ns2.ef-nosc07.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc07.ie'), 'ef-nosc07.ie', 'ns3.ef-nosc07.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('ef-nosc07.ie', 'ns1.ef-nosc07.ie', '10.10.121.23', NULL, '1'), ('ef-nosc07.ie', 'ns2.ef-nosc07.ie', '1.3.4.2', NULL, '2'), ('ef-nosc07.ie', 'ns3.ef-nosc07.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'ef-nosc08.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XDD274-IEDR', NOW(), 'NO', '25', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('ef-nosc08.ie', 'Test Holder 0001', 3, 2, NULL, 1, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', '25', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc08.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc08.ie'), 'ef-nosc08.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'EFAA-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc08.ie'), 'ef-nosc08.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc08.ie'), 'ef-nosc08.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc08.ie'), 'ef-nosc08.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('ef-nosc08.ie', 'EFAA-IEDR', 'Admin'), ('ef-nosc08.ie', 'XDD274-IEDR', 'Billing'), ('ef-nosc08.ie', 'XDD274-IEDR', 'Creator'), ('ef-nosc08.ie', 'XDD274-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc08.ie'), 'ef-nosc08.ie', 'ns1.ef-nosc08.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc08.ie'), 'ef-nosc08.ie', 'ns2.ef-nosc08.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc08.ie'), 'ef-nosc08.ie', 'ns3.ef-nosc08.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('ef-nosc08.ie', 'ns1.ef-nosc08.ie', '10.10.121.23', NULL, '1'), ('ef-nosc08.ie', 'ns2.ef-nosc08.ie', '1.3.4.2', NULL, '2'), ('ef-nosc08.ie', 'ns3.ef-nosc08.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'ef-nosc09.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('ef-nosc09.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc09.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc09.ie'), 'ef-nosc09.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'EFAB-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc09.ie'), 'ef-nosc09.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc09.ie'), 'ef-nosc09.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc09.ie'), 'ef-nosc09.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('ef-nosc09.ie', 'EFAB-IEDR', 'Admin'), ('ef-nosc09.ie', 'XBC189-IEDR', 'Billing'), ('ef-nosc09.ie', 'XBC189-IEDR', 'Creator'), ('ef-nosc09.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc09.ie'), 'ef-nosc09.ie', 'ns1.ef-nosc09.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc09.ie'), 'ef-nosc09.ie', 'ns2.ef-nosc09.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc09.ie'), 'ef-nosc09.ie', 'ns3.ef-nosc09.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('ef-nosc09.ie', 'ns1.ef-nosc09.ie', '10.10.121.23', NULL, '1'), ('ef-nosc09.ie', 'ns2.ef-nosc09.ie', '1.3.4.2', NULL, '2'), ('ef-nosc09.ie', 'ns3.ef-nosc09.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'ef-nosc10.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '116', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('ef-nosc10.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', '116', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc10.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc10.ie'), 'ef-nosc10.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc10.ie'), 'ef-nosc10.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc10.ie'), 'ef-nosc10.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc10.ie'), 'ef-nosc10.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('ef-nosc10.ie', 'XBC189-IEDR', 'Admin'), ('ef-nosc10.ie', 'XBC189-IEDR', 'Billing'), ('ef-nosc10.ie', 'XBC189-IEDR', 'Creator'), ('ef-nosc10.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc10.ie'), 'ef-nosc10.ie', 'ns1.ef-nosc10.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc10.ie'), 'ef-nosc10.ie', 'ns2.ef-nosc10.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc10.ie'), 'ef-nosc10.ie', 'ns3.ef-nosc10.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('ef-nosc10.ie', 'ns1.ef-nosc10.ie', '10.10.121.23', NULL, '1'), ('ef-nosc10.ie', 'ns2.ef-nosc10.ie', '1.3.4.2', NULL, '2'), ('ef-nosc10.ie', 'ns3.ef-nosc10.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'ef-nosc11-a.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '116', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('ef-nosc11-a.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', '116', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc11-a.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc11-a.ie'), 'ef-nosc11-a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc11-a.ie'), 'ef-nosc11-a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc11-a.ie'), 'ef-nosc11-a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc11-a.ie'), 'ef-nosc11-a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('ef-nosc11-a.ie', 'XBC189-IEDR', 'Admin'), ('ef-nosc11-a.ie', 'XBC189-IEDR', 'Billing'), ('ef-nosc11-a.ie', 'XBC189-IEDR', 'Creator'), ('ef-nosc11-a.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc11-a.ie'), 'ef-nosc11-a.ie', 'ns1.ef-nosc11-a.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc11-a.ie'), 'ef-nosc11-a.ie', 'ns2.ef-nosc11-a.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc11-a.ie'), 'ef-nosc11-a.ie', 'ns3.ef-nosc11-a.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('ef-nosc11-a.ie', 'ns1.ef-nosc11-a.ie', '10.10.121.23', NULL, '1'), ('ef-nosc11-a.ie', 'ns2.ef-nosc11-a.ie', '1.3.4.2', NULL, '2'), ('ef-nosc11-a.ie', 'ns3.ef-nosc11-a.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'ef-nosc11-b.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '116', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('ef-nosc11-b.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', '116', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc11-b.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc11-b.ie'), 'ef-nosc11-b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc11-b.ie'), 'ef-nosc11-b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc11-b.ie'), 'ef-nosc11-b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc11-b.ie'), 'ef-nosc11-b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('ef-nosc11-b.ie', 'XBC189-IEDR', 'Admin'), ('ef-nosc11-b.ie', 'XBC189-IEDR', 'Billing'), ('ef-nosc11-b.ie', 'XBC189-IEDR', 'Creator'), ('ef-nosc11-b.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc11-b.ie'), 'ef-nosc11-b.ie', 'ns1.ef-nosc11-b.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc11-b.ie'), 'ef-nosc11-b.ie', 'ns2.ef-nosc11-b.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc11-b.ie'), 'ef-nosc11-b.ie', 'ns3.ef-nosc11-b.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('ef-nosc11-b.ie', 'ns1.ef-nosc11-b.ie', '10.10.121.23', NULL, '1'), ('ef-nosc11-b.ie', 'ns2.ef-nosc11-b.ie', '1.3.4.2', NULL, '2'), ('ef-nosc11-b.ie', 'ns3.ef-nosc11-b.ie', '10.12.13.14', NULL, '3');

INSERT INTO INCOMING_DOC(Create_TS, DOC_TYPE, FILENAME, DOC_PURPOSE, DOC_SOURCE, ACCOUNT_NUMBER, DOC_CREATOR) VALUES (NOW(), 'attachment', 'att001.pdf', 'Bill-C Transfer', 'Source', 600, 'IDL2-IEDR');
INSERT INTO INCOMING_DOC_DOMAINS(DOC_ID, DOMAIN_NAME) VALUES ((SELECT LAST_INSERT_ID()), 'ef-nosc03.ie');
INSERT INTO INCOMING_DOC(Create_TS, DOC_TYPE, FILENAME, DOC_PURPOSE, DOC_SOURCE, ACCOUNT_NUMBER, DOC_CREATOR) VALUES (NOW(), 'fax', 'fax001.pdf', 'Bill-C Transfer', 'Source', 600, 'IDL2-IEDR');
INSERT INTO INCOMING_DOC_DOMAINS(DOC_ID, DOMAIN_NAME) VALUES ((SELECT LAST_INSERT_ID()), 'ef-nosc03.ie');
INSERT INTO INCOMING_DOC(Create_TS, DOC_TYPE, FILENAME, DOC_PURPOSE, DOC_SOURCE, ACCOUNT_NUMBER, DOC_CREATOR) VALUES (NOW(), 'paper', 'paper001.pdf', 'Bill-C Transfer', 'Source', 600, 'IDL2-IEDR');
INSERT INTO INCOMING_DOC_DOMAINS(DOC_ID, DOMAIN_NAME) VALUES ((SELECT LAST_INSERT_ID()), 'ef-nosc03.ie');
INSERT INTO INCOMING_DOC(Create_TS, DOC_TYPE, FILENAME, DOC_PURPOSE, DOC_SOURCE, ACCOUNT_NUMBER, DOC_CREATOR) VALUES (NOW(), 'attachment', 'att002.pdf', 'Bill-C Transfer', 'Source', 600, 'IDL2-IEDR');
INSERT INTO INCOMING_DOC_DOMAINS(DOC_ID, DOMAIN_NAME) VALUES ((SELECT LAST_INSERT_ID()), 'ef-nosc11-a.ie');

INSERT INTO DomainHist VALUES (NULL, 'ef-nosc12.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Current change', 'XBC189-IEDR', NOW(), 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('ef-nosc12.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc12.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc12.ie'), 'ef-nosc12.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc12.ie'), 'ef-nosc12.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc12.ie'), 'ef-nosc12.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc12.ie'), 'ef-nosc12.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('ef-nosc12.ie', 'XBC189-IEDR', 'Admin'), ('ef-nosc12.ie', 'XBC189-IEDR', 'Billing'), ('ef-nosc12.ie', 'XBC189-IEDR', 'Creator'), ('ef-nosc12.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc12.ie'), 'ef-nosc12.ie', 'ns1.ef-nosc12.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc12.ie'), 'ef-nosc12.ie', 'ns2.ef-nosc12.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc12.ie'), 'ef-nosc12.ie', 'ns3.ef-nosc12.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('ef-nosc12.ie', 'ns1.ef-nosc12.ie', '10.10.121.23', NULL, '1'), ('ef-nosc12.ie', 'ns2.ef-nosc12.ie', '1.3.4.2', NULL, '2');

SET @five_days_ago = NOW() - Interval 5 Day;
INSERT INTO DomainHist VALUES (NULL, 'ef-nosc12.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), @five_days_ago, 'Hist change #3 from top', 'CIARA-IEDR', @five_days_ago, 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'ef-nosc12.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), @five_days_ago, 'Hist change #2 from top', 'CIARA-IEDR', @five_days_ago, 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'ef-nosc12.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW() - Interval 1 Day, 'Hist change #1 from top', 'CIARA-IEDR', NOW() - Interval 1 Day, 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'ef-nosc12.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW() - Interval 10 Day, 'Hist change #4 from top', 'CIARA-IEDR', NOW() - Interval 10 Day, 'NO', 17, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
SET @five_days_ago = NULL;

