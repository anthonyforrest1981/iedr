INSERT INTO NicHandleHist VALUES (NULL, 'UC060SU-IEDR', 'Use Case 060 - Direct - Suspended', (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc060_su@iedr.ie', 'Suspended', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'UC060SU-IEDR', 'B', NULL, 'YES', 'PORTAL', NOW());
INSERT INTO NicHandle VALUES ('UC060SU-IEDR', 'Use Case 060 - Direct - Suspended', '001', 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc060_su@iedr.ie', 'Suspended', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'UC060SU-IEDR', 'B', NULL, 'YES', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC060SU-IEDR'));
INSERT INTO AccessHist VALUES (NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC060SU-IEDR'), 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 0, 'NO', 'PORTAL', NOW(), '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '');
INSERT INTO Access VALUES ('UC060SU-IEDR', 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 0, 'NO', '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '', (SELECT MAX(A.Chng_Id) FROM AccessHist A JOIN NicHandleHist NH  ON A.Nic_Handle_Chng_Id = NH.Chng_Id WHERE NH.Nic_Handle = 'UC060SU-IEDR'));

INSERT INTO DomainHist VALUES (NULL, 'uc060.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc060.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc060.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc060.ie'), 'uc060.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc060.ie'), 'uc060.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc060.ie'), 'uc060.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc060.ie'), 'uc060.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc060.ie', 'XBC189-IEDR', 'Admin'), ('uc060.ie', 'XBC189-IEDR', 'Billing'), ('uc060.ie', 'XBC189-IEDR', 'Creator'), ('uc060.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc060.ie'), 'uc060.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc060.ie'), 'uc060.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc060.ie'), 'uc060.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('uc060.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('uc060.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('uc060.ie', 'ns3.abc1.ie', NULL, NULL, '3');

INSERT INTO TicketIndex VALUES (NULL);
INSERT INTO Ticket VALUES ((SELECT MAX(id) FROM TicketIndex), 'Modification', 'uc060.ie', NULL, 'Test Holder 0001 - Modified', NULL, 600, NULL, 3, 2, NULL, 'Domain modify request.', 'XBC189-IEDR', NULL, NULL, NULL, 'XBC189-IEDR', NULL, 'XBC189-IEDR', NULL, 'XBC189-IEDR', 'New', NOW(), 'New', NOW(), 'NO', NULL, NOW(), NOW(), '', NULL, NULL, NOW(), 'NO', 1, NULL, 'NO', 'New', NOW(), 'New', CURDATE(), 1);
INSERT INTO TicketHist VALUES (NULL, (SELECT T_Number FROM Ticket WHERE D_Name = 'uc060.ie'), 'Modification', 'uc060.ie', NULL, 'Test Holder 0001 - Modified', NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), NULL, 3, 2, NULL, 'Domain modify request.', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), NULL, NULL, NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'New', NOW(), 'New', NOW(), 'NO', NULL, NOW(), NOW(), '', NOW(), 'XBC189-IEDR', NULL, NULL, NOW(), 'NO', 1, NULL, 'NO', 'New', NOW(), 'New', CURDATE());
UPDATE Ticket T SET Chng_Id = (SELECT MAX(Chng_Id) FROM TicketHist WHERE T_Number = T.T_Number) WHERE D_Name = 'uc060.ie';
INSERT INTO TicketNameserverHist VALUES
((SELECT Chng_Id FROM Ticket WHERE D_Name = 'uc060.ie'), (SELECT T_Number FROM Ticket WHERE D_Name = 'uc060.ie'), 'ns1.abc1.ie', NULL, NULL, NULL, NULL, NULL, 0, NOW()),
((SELECT Chng_Id FROM Ticket WHERE D_Name = 'uc060.ie'), (SELECT T_Number FROM Ticket WHERE D_Name = 'uc060.ie'), 'ns2.abc1.ie', NULL, NULL, NULL, NULL, NULL, 1, NOW());
INSERT INTO TicketNameserver VALUES ((SELECT T_Number FROM Ticket WHERE D_Name = 'uc060.ie'), 'ns1.abc1.ie', NULL, NULL, NULL, NULL, NULL, 0, NOW()), ((SELECT T_Number FROM Ticket WHERE D_Name = 'uc060.ie'), 'ns2.abc1.ie', NULL, NULL, NULL, NULL, NULL, 1, NOW());

