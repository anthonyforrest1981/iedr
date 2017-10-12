INSERT INTO NicHandleHist VALUES (NULL, 'UC087-IEDR', 'Registrar (Vat Account)', (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc087_aa@íedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'UC087-IEDR', 'A', NULL, 'YES', 'XBC189-IEDR', NOW());
INSERT INTO NicHandle VALUES ('UC087-IEDR', 'Registrar (Vat Account)', '001', 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'uc087_aa@íedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'UC087-IEDR', 'A', NULL, 'YES', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC087-IEDR'));
INSERT INTO AccessHist VALUES (NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC087-IEDR'), 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 2, 'NO', 'UC087-IEDR', NOW(), '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '');
INSERT INTO Access VALUES ('UC087-IEDR', 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 2, 'NO', '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '', (SELECT MAX(A.Chng_Id) FROM AccessHist A JOIN NicHandleHist NH ON A.Nic_Handle_Chng_Id = NH.Chng_Id WHERE NH.Nic_Handle = 'UC087-IEDR'));
INSERT INTO AccountHist VALUES (NULL, 601, 'Test Registrar', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC087-IEDR'), 'www.testregistrar.ie', 'Active', '2011-12-08', '2011-12-08', 'NO', 'NO', '2011-12-08 15:00:49', 'test data generation script', NULL, '2011-12-08 15:00:49', 'IH4-IEDR');
INSERT INTO Account VALUES (601, 'Test Registrar', 'XNV498-IEDR', 'www.testregistrar.ie', 'Active', '2011-12-08', '2011-12-08', 'NO', 'NO', '2011-12-08 15:00:49', 'test data generation script', NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 601));
UPDATE NicHandleHist SET Account_Chng_ID = (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 601) WHERE Nic_Handle = 'UC087-IEDR';
UPDATE NicHandle SET A_Number = 601 WHERE Nic_Handle = 'UC087-IEDR';

INSERT INTO DomainHist VALUES (NULL, 'uc087-áa.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 603), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', 25, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc087-áa.ie', 'Test Holder 0001', 3, 2, NULL, 603, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', 25, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_ID) FROM DomainHist WHERE D_Name = 'uc087-áa.ie'));
INSERT INTO ContactHist VALUES
  ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc087-áa.ie'), 'uc087-áa.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Admin'),
  ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc087-áa.ie'), 'uc087-áa.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Billing'),
  ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc087-áa.ie'), 'uc087-áa.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Creator'),
  ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc087-áa.ie'), 'uc087-áa.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc087-áa.ie', 'XNV498-IEDR', 'Admin'), ('uc087-áa.ie', 'XNV498-IEDR', 'Billing'), ('uc087-áa.ie', 'XNV498-IEDR', 'Creator'), ('uc087-áa.ie', 'XNV498-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
  ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc087-áa.ie'), 'uc087-áa.ie', 'ns1.uc087-áa.ie', '10.10.121.23', NULL, '1'),
  ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc087-áa.ie'), 'uc087-áa.ie', 'ns2.uc087-áa.ie', '1.3.4.2', NULL, '2'),
  ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc087-áa.ie'), 'uc087-áa.ie', 'ns3.uc087-áa.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc087-áa.ie', 'ns1.uc087-áa.ie', '10.10.121.23', NULL, '1'), ('uc087-áa.ie', 'ns2.uc087-áa.ie', '1.3.4.2', NULL, '2'), ('uc087-áa.ie', 'ns3.uc087-áa.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'uc087-ác.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', 25, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc087-ác.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', 25, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_ID) FROM DomainHist WHERE D_Name = 'uc087-ác.ie'));
INSERT INTO ContactHist VALUES
  ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc087-ác.ie'), 'uc087-ác.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
  ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc087-ác.ie'), 'uc087-ác.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
  ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc087-ác.ie'), 'uc087-ác.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
  ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc087-ác.ie'), 'uc087-ác.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc087-ác.ie', 'XOE550-IEDR', 'Admin'), ('uc087-ác.ie', 'XBC189-IEDR', 'Billing'), ('uc087-ác.ie', 'XBC189-IEDR', 'Creator'), ('uc087-ác.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
  ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc087-ác.ie'), 'uc087-ác.ie', 'ns1.uc087-ác.ie', '10.10.121.23', NULL, '1'),
  ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc087-ác.ie'), 'uc087-ác.ie', 'ns2.uc087-ác.ie', '1.3.4.2', NULL, '2'),
  ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc087-ác.ie'), 'uc087-ác.ie', 'ns3.uc087-ác.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc087-ác.ie', 'ns1.uc087-ác.ie', '10.10.121.23', NULL, '1'), ('uc087-ác.ie', 'ns2.uc087-ác.ie', '1.3.4.2', NULL, '2'), ('uc087-ác.ie', 'ns3.uc087-ác.ie', '10.12.13.14', NULL, '3');

INSERT INTO TicketIndex VALUES (NULL);
INSERT INTO Ticket VALUES ((SELECT MAX(id) FROM TicketIndex), 'Registration', 'uc087-ád.ie', NULL, 'Test Holder 0001', NULL, 600, NULL, 3, 2, NULL, 'Domain reg request.', 'XBC189-IEDR', NULL, NULL, NULL, 'XBC189-IEDR', NULL, 'XBC189-IEDR', NULL, 'XBC189-IEDR', 'New', NOW(), 'New', NOW(), 'NO', NULL, NOW(), NOW(), '', NULL, NULL, NOW(), 'NO', 1, NULL, 'NO', 'New', NOW(), 'New', Now(), 1);
INSERT INTO TicketHist VALUES (NULL, (SELECT T_Number FROM Ticket WHERE D_Name = 'uc087-ád.ie'), 'Registration', 'uc087-ád.ie', NULL, 'Test Holder 0001', NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), NULL, 3, 2, NULL, 'Domain reg request.', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), NULL, NULL, NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'New', NOW(), 'New', NOW(), 'NO', NULL, NOW(), NOW(), '', NOW(), 'XNV498-IEDR', NULL, NULL, NOW(), 'NO', 1, NULL, 'NO', 'New', NOW(), 'New', NOW());
UPDATE Ticket T SET Chng_Id = (SELECT MAX(Chng_Id) FROM TicketHist WHERE T_Number = T.T_Number) WHERE D_Name = 'uc087-ád.ie';
INSERT INTO TicketNameserverHist VALUES
  ((SELECT Chng_Id FROM Ticket WHERE D_Name = 'uc087-ád.ie'), (SELECT T_Number FROM Ticket WHERE D_Name = 'uc087-ád.ie'), 'ns1.abc1.ie', NULL, NULL, NULL, NULL, NULL, 0, NOW()),
  ((SELECT Chng_Id FROM Ticket WHERE D_Name = 'uc087-ád.ie'), (SELECT T_Number FROM Ticket WHERE D_Name = 'uc087-ád.ie'), 'ns2.abc1.ie', NULL, NULL, NULL, NULL, NULL, 1, NOW()),
  ((SELECT Chng_Id FROM Ticket WHERE D_Name = 'uc087-ád.ie'), (SELECT T_Number FROM Ticket WHERE D_Name = 'uc087-ád.ie'), 'ns3.abc1.ie', NULL, NULL, NULL, NULL, NULL, 2, NOW());
INSERT INTO TicketNameserver VALUES
  ((SELECT T_Number FROM Ticket WHERE D_Name = 'uc087-ád.ie'), 'ns1.abc1.ie', NULL, NULL, NULL, NULL, NULL, 0, NOW()),
  ((SELECT T_Number FROM Ticket WHERE D_Name = 'uc087-ád.ie'), 'ns2.abc1.ie', NULL, NULL, NULL, NULL, NULL, 1, NOW()),
  ((SELECT T_Number FROM Ticket WHERE D_Name = 'uc087-ád.ie'), 'ns3.abc1.ie', NULL, NULL, NULL, NULL, NULL, 2, NOW());

INSERT INTO DomainHist VALUES (NULL, 'uc087-ag.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XNV498-IEDR', NOW(), 'NO', 25, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc087-ag.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', 25, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_ID) FROM DomainHist WHERE D_Name = 'uc087-ag.ie'));
INSERT INTO ContactHist VALUES
  ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc087-ag.ie'), 'uc087-ag.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
  ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc087-ag.ie'), 'uc087-ag.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
  ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc087-ag.ie'), 'uc087-ag.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
  ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc087-ag.ie'), 'uc087-ag.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc087-ag.ie', 'XOE550-IEDR', 'Admin'), ('uc087-ag.ie', 'XBC189-IEDR', 'Billing'), ('uc087-ag.ie', 'XBC189-IEDR', 'Creator'), ('uc087-ag.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
  ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc087-ag.ie'), 'uc087-ag.ie', 'ns1.uc087-ag.ie', '10.10.121.23', NULL, '1'),
  ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc087-ag.ie'), 'uc087-ag.ie', 'ns2.uc087-ag.ie', '1.3.4.2', NULL, '2'),
  ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc087-ag.ie'), 'uc087-ag.ie', 'ns3.uc087-ag.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc087-ag.ie', 'ns1.uc087-ág.ie', '10.10.121.23', NULL, '1'), ('uc087-ag.ie', 'ns2.uc087-ág.ie', '1.3.4.2', NULL, '2'), ('uc087-ag.ie', 'ns3.uc087-ág.ie', '10.12.13.14', NULL, '3');

INSERT INTO TicketIndex VALUES (NULL);
INSERT INTO Ticket VALUES ((SELECT MAX(id) FROM TicketIndex), 'Modification', 'uc087-ag.ie', NULL, 'Test Holder 0001', NULL, 600, NULL, 3, 2, NULL, 'Domain reg request.', 'XBC189-IEDR', NULL, NULL, NULL, 'XBC189-IEDR', NULL, 'XBC189-IEDR', NULL, 'XBC189-IEDR', 'New', NOW(), 'New', NOW(), 'NO', NULL, NOW(), NOW(), '', NULL, NULL, NOW(), 'NO', 1, NULL, 'NO', 'New', NOW(), 'New', Now(), 1);
INSERT INTO TicketHist VALUES (NULL, (SELECT T_Number FROM Ticket WHERE D_Name = 'uc087-ag.ie'), 'Modification', 'uc087-ag.ie', NULL, 'Test Holder 0001', NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), NULL, 3, 2, NULL, 'Domain reg request.', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), NULL, NULL, NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'New', NOW(), 'New', NOW(), 'NO', NULL, NOW(), NOW(), '', NOW(), 'XNV498-IEDR', NULL, NULL, NOW(), 'NO', 1, NULL, 'NO', 'New', NOW(), 'New', NOW());
UPDATE Ticket T SET Chng_Id = (SELECT MAX(Chng_Id) FROM TicketHist WHERE T_Number = T.T_Number) WHERE D_Name = 'uc087-ag.ie';
INSERT INTO TicketNameserverHist VALUES
  ((SELECT Chng_Id FROM Ticket WHERE D_Name = 'uc087-ag.ie'), (SELECT T_Number FROM Ticket WHERE D_Name = 'uc087-ag.ie'), 'ns1.abc1.ie', NULL, NULL, NULL, NULL, NULL, 0, NOW()),
  ((SELECT Chng_Id FROM Ticket WHERE D_Name = 'uc087-ag.ie'), (SELECT T_Number FROM Ticket WHERE D_Name = 'uc087-ag.ie'), 'ns2.abc1.ie', NULL, NULL, NULL, NULL, NULL, 1, NOW()),
  ((SELECT Chng_Id FROM Ticket WHERE D_Name = 'uc087-ag.ie'), (SELECT T_Number FROM Ticket WHERE D_Name = 'uc087-ag.ie'), 'ns3.abc1.ie', NULL, NULL, NULL, NULL, NULL, 2, NOW());
INSERT INTO TicketNameserver VALUES
  ((SELECT T_Number FROM Ticket WHERE D_Name = 'uc087-ag.ie'), 'ns1.ábc1.ie', NULL, NULL, NULL, NULL, NULL, 0, NOW()),
  ((SELECT T_Number FROM Ticket WHERE D_Name = 'uc087-ag.ie'), 'ns2.ábc1.ie', NULL, NULL, NULL, NULL, NULL, 1, NOW()),
  ((SELECT T_Number FROM Ticket WHERE D_Name = 'uc087-ag.ie'), 'ns3.ábc1.ie', NULL, NULL, NULL, NULL, NULL, 2, NOW());

INSERT INTO DomainHist VALUES (NULL, 'uc087-ah.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 601), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', 25, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('uc087-ah.ie', 'Test Holder 0001', 3, 2, NULL, 601, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', 25, NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_ID) FROM DomainHist WHERE D_Name = 'uc087-ah.ie'));
INSERT INTO ContactHist VALUES
  ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc087-ah.ie'), 'uc087-ah.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC087-IEDR'), 'Admin'),
  ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc087-ah.ie'), 'uc087-ah.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC087-IEDR'), 'Billing'),
  ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc087-ah.ie'), 'uc087-ah.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC087-IEDR'), 'Creator'),
  ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc087-ah.ie'), 'uc087-ah.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'UC087-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('uc087-ah.ie', 'UC087-IEDR', 'Admin'), ('uc087-ah.ie', 'UC087-IEDR', 'Billing'), ('uc087-ah.ie', 'UC087-IEDR', 'Creator'), ('uc087-ah.ie', 'UC087-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
  ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc087-ah.ie'), 'uc087-ah.ie', 'ns1.uc087-ah.ie', '10.10.121.23', NULL, '1'),
  ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc087-ah.ie'), 'uc087-ah.ie', 'ns2.uc087-ah.ie', '1.3.4.2', NULL, '2'),
  ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'uc087-ah.ie'), 'uc087-ah.ie', 'ns3.uc087-ah.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('uc087-ah.ie', 'ns1.uc087-ah.ie', '10.10.121.23', NULL, '1'), ('uc087-ah.ie', 'ns2.uc087-ah.ie', '1.3.4.2', NULL, '2'), ('uc087-ah.ie', 'ns3.uc087-ah.ie', '10.12.13.14', NULL, '3');

INSERT INTO TicketIndex VALUES (NULL);
INSERT INTO Ticket VALUES ((SELECT MAX(id) FROM TicketIndex), 'Modification', 'uc087-ah.ie', NULL, 'Test Holder 0001', NULL, 601, NULL, 3, 2, NULL, 'Domain reg request.', 'XBC189-IEDR', NULL, NULL, NULL, 'XBC189-IEDR', NULL, 'XBC189-IEDR', NULL, 'XBC189-IEDR', 'New', NOW(), 'New', NOW(), 'NO', NULL, NOW(), NOW(), '', NULL, NULL, NOW(), 'NO', 1, NULL, 'NO', 'New', NOW(), 'New', Now(), 1);
INSERT INTO TicketHist VALUES (NULL, (SELECT T_Number FROM Ticket WHERE D_Name = 'uc087-ah.ie'), 'Modification', 'uc087-ah.ie', NULL, 'Test Holder 0001', NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 601), NULL, 3, 2, NULL, 'Domain reg request.', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), NULL, NULL, NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'New', NOW(), 'New', NOW(), 'NO', NULL, NOW(), NOW(), '', NOW(), 'XNV498-IEDR', NULL, NULL, NOW(), 'NO', 1, NULL, 'NO', 'New', NOW(), 'New', NOW());
UPDATE Ticket T SET Chng_Id = (SELECT MAX(Chng_Id) FROM TicketHist WHERE T_Number = T.T_Number) WHERE D_Name = 'uc087-ah.ie';
INSERT INTO TicketNameserverHist VALUES
  ((SELECT Chng_Id FROM Ticket WHERE D_Name = 'uc087-ah.ie'), (SELECT T_Number FROM Ticket WHERE D_Name = 'uc087-ah.ie'), 'ns1.abc1.ie', NULL, NULL, NULL, NULL, NULL, 0, NOW()),
  ((SELECT Chng_Id FROM Ticket WHERE D_Name = 'uc087-ah.ie'), (SELECT T_Number FROM Ticket WHERE D_Name = 'uc087-ah.ie'), 'ns2.abc1.ie', NULL, NULL, NULL, NULL, NULL, 1, NOW()),
  ((SELECT Chng_Id FROM Ticket WHERE D_Name = 'uc087-ah.ie'), (SELECT T_Number FROM Ticket WHERE D_Name = 'uc087-ah.ie'), 'ns3.abc1.ie', NULL, NULL, NULL, NULL, NULL, 2, NOW());
INSERT INTO TicketNameserver VALUES
  ((SELECT T_Number FROM Ticket WHERE D_Name = 'uc087-ah.ie'), 'ns1.abc1.ie', NULL, NULL, NULL, NULL, NULL, 0, NOW()),
  ((SELECT T_Number FROM Ticket WHERE D_Name = 'uc087-ah.ie'), 'ns2.abc1.ie', NULL, NULL, NULL, NULL, NULL, 1, NOW()),
  ((SELECT T_Number FROM Ticket WHERE D_Name = 'uc087-ah.ie'), 'ns3.abc1.ie', NULL, NULL, NULL, NULL, NULL, 2, NOW());
