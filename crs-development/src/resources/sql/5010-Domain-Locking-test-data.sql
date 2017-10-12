-- CRS-869

UPDATE `Domain` SET `D_Locking_Dt` = CURDATE() - interval 6 month, `D_LockingRenewal_Dt` = CURDATE() + interval 6 month WHERE `D_Name` = 'autorenewdomain.ie';
UPDATE `Domain` SET `D_Name` = 'payDomainLocked.ie', `D_Locking_Dt` = CURDATE() - interval 8 month, `D_LockingRenewal_Dt` = CURDATE() + interval 4 month WHERE `D_Name` = 'payDomainWIPODisputed.ie';
UPDATE `Domain` SET `D_Name` = 'payDomainLockedAutorenew.ie', `D_Locking_Dt` = CURDATE() - interval 2 month, `D_LockingRenewal_Dt` = CURDATE() + interval 10 month WHERE `D_Name` = 'payDomainWIPODisputedAutorenew.ie';

INSERT INTO `Domain` VALUES
('temporarily-unlocked.ie', 'API Tester', 'Body Corporate (Ltd,PLC,Company)', 'Registered Business Name', 00000666, NULL, NULL, '2008-04-30',' 2009-04-30', '2008-05-30 14:06:55', 'N', '', '',' API tests domain', 'N', 17, NULL, NULL, NULL, NULL, NULL, NULL, CURDATE() - interval 18 month, CURDATE() + interval 6 month);

INSERT INTO `DNS` VALUES
('temporarily-unlocked.ie', 'ns1.dnsireland.com', '', '', 1),
('temporarily-unlocked.ie', 'ns2.dnsireland.com', '', '', 2);

INSERT INTO `Contact` VALUES
('temporarily-unlocked.ie', 'APIT1-IEDR', 'A'),
('temporarily-unlocked.ie', 'APIT1-IEDR', 'C'),
('temporarily-unlocked.ie', 'APIT2-IEDR', 'T'),
('temporarily-unlocked.ie', 'APITEST-IEDR', 'B');

-- CRS-873

INSERT INTO Domain VALUES
('iedrpublished.ie', 'Test Holder 0001', 'Sole Trader', 'Personal Trading Name', '00000666','Active', CURDATE(), '2014-04-17', CURDATE(), NOW(), 'N', 'Y', '','Test Remark', 'N', '628', null, null, null, 'authcode', CURDATE() + Interval 1 Day, 0, null, null);

INSERT INTO Contact VALUES
('iedrpublished.ie','APIT1-IEDR', 'A'),
('iedrpublished.ie','APITEST-IEDR', 'B'),
('iedrpublished.ie','APITEST-IEDR', 'C'),
('iedrpublished.ie','APIT1-IEDR', 'T');

INSERT INTO DNS VALUES
('iedrpublished.ie', 'ns1.iedrpublished.ie', '10.10.121.23', '0:0:0:0:0:ffff:a0a:7917', '1'),
('iedrpublished.ie', 'ns2.iedrpublished.ie', '1.3.4.2', '0:0:0:0:0:ffff:103:402', '2');

-- CRS-881

INSERT INTO Domain VALUES
('suspendedLockedDomain.ie', 'Castlebar Golf Club', 'Unincorporated Association', 'Unincorporated Association Name', '00000122','Active', CURDATE(), '2003-06-04', '2008-11-05', NOW(), 'N', 'Y', '','Test Remark', 'N', '2', '2008-01-01', null, null, null, null, 0, null, null),
('deletedLockedDomain.ie', 'Castlebar Golf Club', 'Unincorporated Association', 'Unincorporated Association Name', '00000122','Active', CURDATE(), '2003-06-04', '2008-11-05', NOW(), 'N', 'Y', '','Test Remark', 'N', '3', null, '2007-01-01', null, null, null, 0, null, null);

INSERT INTO Contact VALUES
('suspendedLockedDomain.ie','APIT1-IEDR', 'A'),
('suspendedLockedDomain.ie','APIT1-IEDR', 'B'),
('suspendedLockedDomain.ie','APIT1-IEDR', 'C'),
('suspendedLockedDomain.ie','APIT1-IEDR', 'T'),
('deletedLockedDomain.ie','APIT1-IEDR', 'A'),
('deletedLockedDomain.ie','APIT1-IEDR', 'B'),
('deletedLockedDomain.ie','APIT1-IEDR', 'C'),
('deletedLockedDomain.ie','APIT1-IEDR', 'T');

INSERT INTO DNS VALUES
('suspendedLockedDomain.ie', 'dns1.myhostns.ie', '', '', '1'),
('suspendedLockedDomain.ie', 'dns2.myhostns.ie', '', '', '2'),
('deletedLockedDomain.ie', 'dns1.myhostns.ie', '', '', '1'),
('deletedLockedDomain.ie', 'dns2.myhostns.ie', '', '', '2');

-- CRS-893

DELETE FROM `Ticket` WHERE `D_Name` = 'mhcb.ie';

