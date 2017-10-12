INSERT INTO NicHandleHist VALUES (NULL, 'UTF8AA-IÉDR', 'Direct', (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'xdd274@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'UTF8AA-IÉDR', 'A', NULL, 'YES', 'PORTAL', NOW());
INSERT INTO NicHandle VALUES ('UTF8AA-IÉDR', 'Direct', 1, 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'xdd274@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'UTF8AA-IÉDR', 'A', NULL, 'YES', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UTF8AA-IÉDR'));
INSERT INTO AccessHist VALUES (NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UTF8AA-IÉDR'), '8cxBY2hOuS26mXTBishzvOXkX61bYEG', 2048, 'NO', 'PORTAL', NOW(), 'JqK6Th1tpWyypseoiWU8QO', CURDATE(), 'NO', 'NO', '');
INSERT INTO Access VALUES ('UTF8AA-IÉDR', '8cxBY2hOuS26mXTBishzvOXkX61bYEG', 2048, 'NO', 'JqK6Th1tpWyypseoiWU8QO', CURDATE(), 'NO', 'NO', '', (SELECT MAX(A.Chng_Id) FROM AccessHist A JOIN NicHandleHist NH  ON A.Nic_Handle_Chng_Id = NH.Chng_Id WHERE NH.Nic_Handle = 'UTF8AA-IÉDR'));

INSERT INTO NicHandleHist VALUES (NULL, 'UTF8ENH-IEDR', 'Test User', (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'utf8enhp@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'XBC189-IEDR', 'A', NULL, 'NO', 'XBC189-IEDR', NOW());
INSERT INTO NicHandle VALUES ('UTF8ENH-IEDR', 'Test User', '600', 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'utf8enhp@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'XBC189-IEDR', 'A', NULL, 'NO', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UTF8ENH-IEDR'));
INSERT INTO AccessHist VALUES (NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UTF8ENH-IEDR'), 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 0, 'NO', 'XBC189-IEDR', NOW(), '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '');
INSERT INTO Access VALUES ('UTF8ENH-IEDR', 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 0, 'NO', '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '', (SELECT MAX(A.Chng_Id) FROM AccessHist A JOIN NicHandleHist NH  ON A.Nic_Handle_Chng_Id = NH.Chng_Id WHERE NH.Nic_Handle = 'UTF8ENH-IEDR'));

INSERT INTO NicHandleHist VALUES (NULL, 'UTF8CPP-IEDR', 'Test User', (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'utf8cpp@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'UTF8CPP-IEDR', 'A', NULL, 'NO', 'PORTAL', NOW());
INSERT INTO NicHandle VALUES ('UTF8CPP-IEDR', 'Test User', 1, 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'utf8cpp@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'UTF8CPP-IEDR', 'A', NULL, 'NO', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UTF8CPP-IEDR'));
INSERT INTO AccessHist VALUES (NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UTF8CPP-IEDR'), 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 2048, 'NO', 'PORTAL', NOW(), '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '');
INSERT INTO Access VALUES ('UTF8CPP-IEDR', 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 2048, 'NO', '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '', (SELECT MAX(A.Chng_Id) FROM AccessHist A JOIN NicHandleHist NH  ON A.Nic_Handle_Chng_Id = NH.Chng_Id WHERE NH.Nic_Handle = 'UTF8CPP-IEDR'));

INSERT INTO NicHandleHist VALUES (NULL, 'UTF8RD-IÉDR', 'Direct', (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'utf8rd@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'XBC189-IEDR', 'A', NULL, 'YES', 'PORTAL', NOW());
INSERT INTO NicHandle VALUES ('UTF8RD-IÉDR', 'Direct', '600', 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'utf8rd@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'XBC189-IEDR', 'A', NULL, 'YES', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UTF8RD-IÉDR'));
INSERT INTO AccessHist VALUES (NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UTF8RD-IÉDR'), '8cxBY2hOuS26mXTBishzvOXkX61bYEG', 2048, 'NO', 'PORTAL', NOW(), 'JqK6Th1tpWyypseoiWU8QO', CURDATE(), 'NO', 'NO', '');
INSERT INTO Access VALUES ('UTF8RD-IÉDR', '8cxBY2hOuS26mXTBishzvOXkX61bYEG', 2048, 'NO', 'JqK6Th1tpWyypseoiWU8QO', CURDATE(), 'NO', 'NO', '', (SELECT MAX(A.Chng_Id) FROM AccessHist A JOIN NicHandleHist NH  ON A.Nic_Handle_Chng_Id = NH.Chng_Id WHERE NH.Nic_Handle = 'UTF8RD-IÉDR'));

INSERT INTO DomainHist VALUES (NULL, 'utf8-vdp.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('utf8-vdp.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'utf8-vdp.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'utf8-vdp.ie'), 'utf8-vdp.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'utf8-vdp.ie'), 'utf8-vdp.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'utf8-vdp.ie'), 'utf8-vdp.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'utf8-vdp.ie'), 'utf8-vdp.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('utf8-vdp.ie', 'XBC189-IEDR', 'Admin'), ('utf8-vdp.ie', 'XBC189-IEDR', 'Billing'), ('utf8-vdp.ie', 'XBC189-IEDR', 'Creator'), ('utf8-vdp.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'utf8-vdp.ie'), 'utf8-vdp.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'utf8-vdp.ie'), 'utf8-vdp.ie', 'ns2.abc1.ie', NULL, NULL, '2');
INSERT INTO DNS VALUES ('utf8-vdp.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('utf8-vdp.ie', 'ns2.abc1.ie', NULL, NULL, '2');

INSERT INTO TicketIndex VALUES (NULL);
INSERT INTO Ticket VALUES ((SELECT MAX(id) FROM TicketIndex), 'Registration', 'utf8-etp.ie', NULL, 'Holder', NULL, 600, NULL, 3, 2, NULL, 'Domain reg request.', 'XBC189-IEDR', NULL, NULL, NULL, 'XBC189-IEDR', NULL, 'XBC189-IEDR', NULL, 'XBC189-IEDR', 'New', NOW(), 'New', NOW(), 'NO', NULL, NOW(), NOW(), '', NULL, NULL, NOW(), 'NO', 1, NULL, 'NO', 'New', NOW(), 'New', NOW(), 1);
INSERT INTO TicketHist VALUES (NULL, (SELECT T_Number FROM Ticket WHERE D_Name = 'utf8-etp.ie'), 'Registration', 'utf8-etp.ie', NULL, 'Holder', NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), NULL, 3, 2, NULL, 'Domain reg request.', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), NULL, NULL, NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'New', NOW(), 'New', NOW(), 'NO', NULL, NOW(), NOW(), '', NOW(), 'XBC189-IEDR', NULL, NULL, NOW(), 'NO', 1, NULL, 'NO', 'New', NOW(), 'New', NOW());
UPDATE Ticket T SET Chng_Id = (SELECT MAX(Chng_Id) FROM TicketHist WHERE T_Number = T.T_Number) WHERE D_Name = 'utf8-etp.ie';
INSERT INTO TicketNameserverHist VALUES
((SELECT Chng_Id FROM Ticket WHERE D_Name = 'utf8-etp.ie'), (SELECT T_Number FROM Ticket WHERE D_Name = 'utf8-etp.ie'), 'ns1.abc1.ie', NULL, NULL, NULL, NULL, NULL, 0, NOW()),
((SELECT Chng_Id FROM Ticket WHERE D_Name = 'utf8-etp.ie'), (SELECT T_Number FROM Ticket WHERE D_Name = 'utf8-etp.ie'), 'ns2.abc1.ie', NULL, NULL, NULL, NULL, NULL, 1, NOW());
INSERT INTO TicketNameserver VALUES ((SELECT T_Number FROM Ticket WHERE D_Name = 'utf8-etp.ie'), 'ns1.abc1.ie', NULL, NULL, NULL, NULL, NULL, 0, NOW()), ((SELECT T_Number FROM Ticket WHERE D_Name = 'utf8-etp.ie'), 'ns2.abc1.ie', NULL, NULL, NULL, NULL, NULL, 1, NOW());

INSERT INTO DomainHist VALUES (NULL, 'utf8-transfer-domáin.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 603), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XNV498-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'áuthcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('utf8-transfer-domáin.ie', 'Test Holder 0001', 3, 2, NULL, 603, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'áuthcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'utf8-transfer-domáin.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'utf8-transfer-domáin.ie'), 'utf8-transfer-domáin.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'utf8-transfer-domáin.ie'), 'utf8-transfer-domáin.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'utf8-transfer-domáin.ie'), 'utf8-transfer-domáin.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'utf8-transfer-domáin.ie'), 'utf8-transfer-domáin.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('utf8-transfer-domáin.ie', 'XOE550-IEDR', 'Admin'), ('utf8-transfer-domáin.ie', 'XBC189-IEDR', 'Billing'), ('utf8-transfer-domáin.ie', 'XNV498-IEDR', 'Creator'), ('utf8-transfer-domáin.ie', 'XNV498-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'utf8-transfer-domáin.ie'), 'utf8-transfer-domáin.ie', 'ns1.utf8-transfer-domáin.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'utf8-transfer-domáin.ie'), 'utf8-transfer-domáin.ie', 'ns2.utf8-transfer-domáin.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'utf8-transfer-domáin.ie'), 'utf8-transfer-domáin.ie', 'ns3.utf8-transfer-domáin.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('utf8-transfer-domáin.ie', 'ns1.utf8-transfer-domáin.ie', '10.10.121.23', NULL, '1'), ('utf8-transfer-domáin.ie', 'ns2.utf8-transfer-domáin.ie', '1.3.4.2', NULL, '2'), ('utf8-transfer-domáin.ie', 'ns3.utf8-transfer-domáin.ie', '10.12.13.14', NULL, '3');

INSERT INTO TransactionIndex VALUES (NULL);
INSERT INTO Transaction VALUES ((SELECT MAX(id) FROM TransactionIndex), 'NO', 'NO', '7800', 'YES', CURDATE(), NULL, NULL, '6500', '1300', '20150507140846-C-4336038', 'YES', CURDATE(), NULL, '12345', '14310031020574821');
INSERT INTO ReservationIndex VALUES (NULL);
INSERT INTO Reservation VALUES ((SELECT MAX(id) FROM ReservationIndex), 'XBC189-IEDR', 'utf8-etp.ie', '12', CURDATE(), '65.00', '2', '13.00', 'NO', 'NO', NULL, (SELECT T_Number FROM Ticket WHERE D_Name = 'utf8-etp.ie'), (SELECT t.ID FROM Transaction t WHERE t.Order_ID = '20150507140846-C-4336038'), (SELECT Id FROM Product WHERE P_Active = 'YES' AND CURDATE() BETWEEN P_Valid_From_Dt AND P_Valid_To_Dt AND P_Reg = 'YES' and P_Guest = 'NO' AND P_Duration = 1), 'renewal', NULL, NULL, 'Credit Card');

