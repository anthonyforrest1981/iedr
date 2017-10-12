 /* CREATE A NEW DIRECT ACCOUNT */
INSERT INTO NicHandleHist VALUES (NULL, 'XDD274-IEDR', 'Direct McRegistrant', (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), 'Holy Shirts and Skirts Limited', '1 The Road, Some Street', 14, 121, 'ciara@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'XDD274-IEDR', 'A', 'vatregid', 'YES', 'PORTAL', NOW());
INSERT INTO NicHandle VALUES ('XDD274-IEDR', 'Direct McRegistrant', 1, 'Holy Shirts and Skirts Limited', '1 The Road, Some Street', 14, 121, 'ciara@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'XDD274-IEDR', 'A', 'vatregid', 'YES', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'));
INSERT INTO TelecomHist VALUES
((SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'XDD274-IEDR', '+35312345345', 'Phone', 0),
((SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 'XDD274-IEDR', '+35312345300', 'Fax', 0);
INSERT INTO Telecom VALUES ('XDD274-IEDR', '+35312345345', 'Phone', 0), ('XDD274-IEDR', '+35312345300', 'Fax', 0);
INSERT INTO NicHandleHist VALUES (NULL, 'XDD275-IEDR', 'John Admin', (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 1), 'Holy Shirts and Skirts Limited', '1 The Road, Some Street', 14, 121, 'ciara@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'XDD274-IEDR', 'A', 'vatregid', 'NO', 'XDD274-IEDR', NOW());
INSERT INTO NicHandle VALUES ('XDD275-IEDR', 'John Admin', 1, 'Holy Shirts and Skirts Limited', '1 The Road, Some Street', 14, 121, 'ciara@iedr.ie', 'Active', NOW(), NOW(), NOW(), 'NO', 'test data generation', 'XDD274-IEDR', 'A', 'vatregid', 'NO', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD275-IEDR'));
INSERT INTO TelecomHist VALUES
((SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD275-IEDR'), 'XDD275-IEDR', '+35312345345', 'Phone', 0),
((SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD275-IEDR'), 'XDD275-IEDR', '+35312345300', 'Fax', 0);
INSERT INTO Telecom VALUES ('XDD275-IEDR', '+35312345345', 'Phone', 0), ('XDD275-IEDR', '+35312345300', 'Fax', 0);

 /* SET DIRECT PERMISSIONS AND VALUES */
INSERT INTO AccessHist VALUES (NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR'), 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 2048, 'NO', 'PORTAL', NOW(), '.Z805gDk7mXJJfbcIwZJde', NOW(), 'NO', 'NO', '');
INSERT INTO Access VALUES ('XDD274-IEDR', 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 2048, 'NO', '.Z805gDk7mXJJfbcIwZJde', NOW(), 'NO', 'NO', '', (SELECT MAX(A.Chng_Id) FROM AccessHist A JOIN NicHandleHist NH ON A.Nic_Handle_Chng_Id = NH.Chng_Id WHERE NH.Nic_Handle = 'XDD274-IEDR'));
INSERT INTO AccessHist VALUES (NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XDD275-IEDR'), 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 0, 'NO', 'XDD274-IEDR', NOW(), '.Z805gDk7mXJJfbcIwZJde', NOW(), 'NO', 'NO', '');
INSERT INTO Access VALUES ('XDD275-IEDR', 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 0, 'NO', '.Z805gDk7mXJJfbcIwZJde', NOW(), 'NO', 'NO', '', (SELECT MAX(A.Chng_Id) FROM AccessHist A JOIN NicHandleHist NH ON A.Nic_Handle_Chng_Id = NH.Chng_Id WHERE NH.Nic_Handle = 'XDD275-IEDR'));

