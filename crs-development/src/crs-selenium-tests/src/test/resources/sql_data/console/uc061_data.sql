INSERT INTO DomainHist VALUES (NULL, 'uc061-sc01a.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XDD274-IEDR', NOW(), 'NO', '25', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc061-sc01a.ie', 'Test Holder 0001', 3, 2, NULL, 1, CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '25', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc061-sc01a.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc061-sc01a.ie'), 'uc061-sc01a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc061-sc01a.ie'), 'uc061-sc01a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc061-sc01a.ie'), 'uc061-sc01a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc061-sc01a.ie'), 'uc061-sc01a.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc061-sc01a.ie', 'XDD274-IEDR', 'Admin'), ('uc061-sc01a.ie', 'XDD274-IEDR', 'Billing'), ('uc061-sc01a.ie', 'XDD274-IEDR', 'Creator'), ('uc061-sc01a.ie', 'XDD274-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc061-sc01a.ie'), 'uc061-sc01a.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc061-sc01a.ie'), 'uc061-sc01a.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc061-sc01a.ie'), 'uc061-sc01a.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('uc061-sc01a.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('uc061-sc01a.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('uc061-sc01a.ie', 'ns3.abc1.ie', NULL, NULL, '3');

INSERT INTO TicketIndex VALUES (NULL);
INSERT INTO Ticket VALUES ((SELECT MAX(id) FROM TicketIndex), 'Modification', 'uc061-sc01a.ie', NULL, 'Test Holder 0001 - Modified', NULL, 1, NULL, 2, 3, NULL, 'Domain modify request.', 'XDD274-IEDR', NULL, NULL, NULL, 'XDD274-IEDR', NULL, 'XDD274-IEDR', NULL, 'XDD274-IEDR', 'New', NOW(), 'New', NOW(), 'NO', NULL, NOW(), NOW(), '', NULL, NULL, NOW(), 'NO', 1, NULL, 'NO', 'New', NOW(), 'New', CURDATE(), 1);
INSERT INTO TicketHist VALUES (NULL, (SELECT T_Number FROM Ticket WHERE D_Name = 'uc061-sc01a.ie'), 'Modification', 'uc061-sc01a.ie', NULL, 'Test Holder 0001 - Modified', NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), NULL, 2, 3, NULL, 'Domain modify request.', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), NULL, NULL, NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'New', NOW(), 'New', NOW(), 'NO', NULL, NOW(), NOW(), '', NOW(), 'PORTAL', NULL, NULL, NOW(), 'NO', 1, NULL, 'NO', 'New', NOW(), 'New', CURDATE());
UPDATE Ticket T SET Chng_Id = (SELECT MAX(Chng_Id) FROM TicketHist WHERE T_Number = T.T_Number) WHERE D_Name = 'uc061-sc01a.ie';
INSERT INTO TicketNameserverHist VALUES
((SELECT Chng_Id FROM Ticket WHERE D_Name = 'uc061-sc01a.ie'), (SELECT T_Number FROM Ticket WHERE D_Name = 'uc061-sc01a.ie'), 'ns1.abc1.ie', NULL, NULL, NULL, NULL, NULL, 0, NOW()),
((SELECT Chng_Id FROM Ticket WHERE D_Name = 'uc061-sc01a.ie'), (SELECT T_Number FROM Ticket WHERE D_Name = 'uc061-sc01a.ie'), 'ns2.abc1.ie', NULL, NULL, NULL, NULL, NULL, 1, NOW());
INSERT INTO TicketNameserver VALUES ((SELECT T_Number FROM Ticket WHERE D_Name = 'uc061-sc01a.ie'), 'ns1.abc1.ie', NULL, NULL, NULL, NULL, NULL, 0, NOW()), ((SELECT T_Number FROM Ticket WHERE D_Name = 'uc061-sc01a.ie'), 'ns2.abc1.ie', NULL, NULL, NULL, NULL, NULL, 1, NOW());

INSERT INTO DomainHist VALUES (NULL, 'uc061-sc01b.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1),  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XDD274-IEDR', NOW(), 'NO', '25', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc061-sc01b.ie', 'Test Holder 0001', 3, 2, NULL, 1,  CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '25', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc061-sc01b.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc061-sc01b.ie'), 'uc061-sc01b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc061-sc01b.ie'), 'uc061-sc01b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc061-sc01b.ie'), 'uc061-sc01b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc061-sc01b.ie'), 'uc061-sc01b.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc061-sc01b.ie', 'XDD274-IEDR', 'Admin'), ('uc061-sc01b.ie', 'XDD274-IEDR', 'Billing'), ('uc061-sc01b.ie', 'XDD274-IEDR', 'Creator'), ('uc061-sc01b.ie', 'XDD274-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc061-sc01b.ie'), 'uc061-sc01b.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc061-sc01b.ie'), 'uc061-sc01b.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc061-sc01b.ie'), 'uc061-sc01b.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('uc061-sc01b.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('uc061-sc01b.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('uc061-sc01b.ie', 'ns3.abc1.ie', NULL, NULL, '3');

INSERT INTO TicketIndex VALUES (NULL);
INSERT INTO Ticket VALUES ((SELECT MAX(id) FROM TicketIndex), 'Modification', 'uc061-sc01b.ie', NULL, 'Test Holder 0001 - Modified', NULL, 1, NULL, 2, 3, NULL, 'Domain modify request.', 'XDD274-IEDR', NULL, NULL, NULL, 'XDD274-IEDR', NULL, 'XDD274-IEDR', NULL, 'XDD274-IEDR', 'New', NOW(), 'New', NOW(), 'NO', NULL, NOW(), NOW(), '', NULL, NULL, NOW(), 'NO', 1, NULL, 'NO', 'New', NOW(), 'New', CURDATE(), 1);
INSERT INTO TicketHist VALUES (NULL, (SELECT T_Number FROM Ticket WHERE D_Name = 'uc061-sc01b.ie'), 'Modification', 'uc061-sc01b.ie', NULL, 'Test Holder 0001 - Modified', NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), NULL, 2, 3, NULL, 'Domain modify request.', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), NULL, NULL, NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'New', NOW(), 'New', NOW(), 'NO', NULL, NOW(), NOW(), '', NOW(), 'PORTAL', NULL, NULL, NOW(), 'NO', 1, NULL, 'NO', 'New', NOW(), 'New', CURDATE());
UPDATE Ticket T SET Chng_Id = (SELECT MAX(Chng_Id) FROM TicketHist WHERE T_Number = T.T_Number) WHERE D_Name = 'uc061-sc01b.ie';
INSERT INTO TicketNameserverHist VALUES
((SELECT Chng_Id FROM Ticket WHERE D_Name = 'uc061-sc01b.ie'), (SELECT T_Number FROM Ticket WHERE D_Name = 'uc061-sc01b.ie'), 'ns1.abc1.ie', NULL, NULL, NULL, NULL, NULL, 0, NOW()),
((SELECT Chng_Id FROM Ticket WHERE D_Name = 'uc061-sc01b.ie'), (SELECT T_Number FROM Ticket WHERE D_Name = 'uc061-sc01b.ie'), 'ns2.abc1.ie', NULL, NULL, NULL, NULL, NULL, 1, NOW());
INSERT INTO TicketNameserver VALUES ((SELECT T_Number FROM Ticket WHERE D_Name = 'uc061-sc01b.ie'), 'ns1.abc1.ie', NULL, NULL, NULL, NULL, NULL, 0, NOW()), ((SELECT T_Number FROM Ticket WHERE D_Name = 'uc061-sc01b.ie'), 'ns2.abc1.ie', NULL, NULL, NULL, NULL, NULL, 1, NOW());

