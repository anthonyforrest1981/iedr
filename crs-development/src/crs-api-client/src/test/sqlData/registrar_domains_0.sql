 /* CREATE 1000 DOMAINS ON THE USERS ACCOUNT */
INSERT INTO DomainHist VALUES (NULL, 'example0001.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0002.ie', 'Test Holder 0002', 3, 5, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), LAST_DAY(CURDATE() + Interval 10 Month) - Interval 15 Day- Interval 1 Year, LAST_DAY(CURDATE() + Interval 10 Month) - Interval 15 Day, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0003.ie', 'Test Holder 0003', 7, 6, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), LAST_DAY(CURDATE() + Interval 11 Month) - Interval 2 Day- Interval 1 Year, LAST_DAY(CURDATE() + Interval 11 Month) - Interval 2 Day, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0004.ie', 'Test Holder 0004', 5, 11, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE(), CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0005.ie', 'Test Holder 0005', 2, 11, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE(), CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0006.ie', 'Test Holder 0006', 7, 11, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE(), CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '1', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0007.ie', 'Test Holder 0007', 8, 11, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE(), CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0008.ie', 'Test Holder 0008', 4, 8, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE(), CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0009.ie', 'Test Holder 0009', 3, 5, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE(), CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0010.ie', 'Test Holder 0010', 8, 11, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE(), CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0011.ie', 'Test Holder 0011', 5, 10, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE(), CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, '68OM3LQV7C3F', CURDATE() + Interval 12 day, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0012.ie', 'Test Holder 0012', 1, 9, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE(), CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0013.ie', 'Test Holder 0013', 8, 7, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE(), CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '113', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0014.ie', 'Test Holder 0014', 8, 7, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), DATE_FORMAT(CURDATE() - Interval 12 Month, '%Y%-%m%-23'), DATE_FORMAT(CURDATE(), '%Y%-%m%-23'), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0015.ie', 'Test Holder 0015', 6, 6, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), DATE_FORMAT(CURDATE() - Interval 12 Month, '%Y%-%m%-23'), DATE_FORMAT(CURDATE(), '%Y%-%m%-23'), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0016.ie', 'Test Holder 0016', 3, 11, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), DATE_FORMAT(CURDATE() - Interval 37 Month, '%Y%-%m%-07'), DATE_FORMAT(CURDATE() - Interval 1 Month, '%Y%-%m%-07'), NOW(), 'Test Case: Domain was IN MSD (and therefore not invoiced) and then xfered to this account', 'XBC189-IEDR', NOW(), 'NO', '11', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0017.ie', 'Test Holder 0017', 5, 7, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), DATE_FORMAT(CURDATE() - Interval 12 Month, '%Y%-%m%-23'), DATE_FORMAT(CURDATE(), '%Y%-%m%-23'), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0018.ie', 'Test Holder 0018', 2, 4, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), DATE_FORMAT(CURDATE() - Interval 12 Month, '%Y%-%m%-23'), DATE_FORMAT(CURDATE(), '%Y%-%m%-23'), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0019.ie', 'Test Holder 0019', 8, 7, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), DATE_FORMAT(CURDATE() - Interval 12 Month, '%Y%-%m%-23'), DATE_FORMAT(CURDATE(), '%Y%-%m%-23'), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '33', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0020.ie', 'Test Holder 0020', 5, 11, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE(), CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0021.ie', 'Test Holder 0021', 2, 11, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE(), CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '40', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0022.ie', 'Test Holder 0022', 1, 4, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), DATE_FORMAT(CURDATE() - Interval 1 Month, '%Y%-%m%-23'), DATE_FORMAT(CURDATE() - Interval 1 Month, '%Y%-%m%-23'), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '65', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0023.ie', 'Test Holder 0023', 7, 6, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '125', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0024.ie', 'Test Holder 0024', 2, 11, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), DATE_FORMAT(CURDATE() - Interval 25 Month, '%Y%-%m%-04'), DATE_FORMAT(CURDATE() - Interval 1 Month, '%Y%-%m%-04'), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0025.ie', 'Test Holder 0025', 1, 1, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), DATE_FORMAT(CURDATE() - Interval 12 Month, '%Y%-%m%-23'), DATE_FORMAT(CURDATE(), '%Y%-%m%-23'), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '97', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0026.ie', 'Test Holder 0026', 1, 9, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), DATE_FORMAT(CURDATE() - Interval 12 Month, '%Y%-%m%-23'), DATE_FORMAT(CURDATE(), '%Y%-%m%-23'), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '116', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0027.ie', 'Test Holder 0027', 8, 11, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), DATE_FORMAT(CURDATE() - Interval 12 Month, '%Y%-%m%-23'), DATE_FORMAT(CURDATE(), '%Y%-%m%-23'), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '297', NULL, NULL, NULL, '68OM3LQV7C3F', CURDATE() + Interval 12 day, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0028.ie', 'Test Holder 0028', 6, 6, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), DATE_FORMAT(CURDATE() - Interval 12 Month, '%Y%-%m%-23'), DATE_FORMAT(CURDATE(), '%Y%-%m%-23'), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '523', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0029.ie', 'Test Holder 0029', 8, 11, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), DATE_FORMAT(CURDATE() - Interval 12 Month, '%Y%-%m%-23'), DATE_FORMAT(CURDATE(), '%Y%-%m%-23'), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0030.ie', 'Test Holder 0030', 5, 7, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), DATE_FORMAT(CURDATE() - Interval 12 Month, '%Y%-%m%-23'), DATE_FORMAT(CURDATE(), '%Y%-%m%-23'), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '390', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0031.ie', 'Test Holder 0031', 4, 8, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), DATE_FORMAT(CURDATE() - Interval 1 Month, '%Y%-%m%-23'), DATE_FORMAT(CURDATE() - Interval 1 Month, '%Y%-%m%-23'), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '81', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0032.ie', 'Test Holder 0032', 8, 11, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), DATE_FORMAT(CURDATE() - Interval 1 Month, '%Y%-%m%-24'), DATE_FORMAT(CURDATE() - Interval 1 Month, '%Y%-%m%-24'), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0033.ie', 'Test Holder 0033', 4, 7, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), DATE_FORMAT(CURDATE() - Interval 25 Month, '%Y%-%m%-04'), DATE_FORMAT(CURDATE() - Interval 1 Month, '%Y%-%m%-04'), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0034.ie', 'Test Holder 0034', 2, 11, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), DATE_FORMAT(CURDATE() - Interval 25 Month, '%Y%-%m%-04'), DATE_FORMAT(CURDATE() - Interval 1 Month, '%Y%-%m%-04'), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0035.ie', 'Test Holder 0035', 2, 11, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), DATE_FORMAT(CURDATE() - Interval 25 Month, '%Y%-%m%-04'), DATE_FORMAT(CURDATE() - Interval 1 Month, '%Y%-%m%-04'), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0036.ie', 'Test Holder 0036', 2, 4, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 22 Month, CURDATE() + Interval 2 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0037.ie', 'Test Holder 0037', 8, 7, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 22 Month, CURDATE() + Interval 2 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0038.ie', 'Test Holder 0038', 2, 3, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), LAST_DAY(CURDATE() + Interval 5 Month) - Interval 4 Year, LAST_DAY(CURDATE() + Interval 5 Month) , NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0039.ie', 'Test Holder 0039', 6, 11, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), LAST_DAY(CURDATE() + Interval 5 Month) - Interval 4 Year, LAST_DAY(CURDATE() + Interval 5 Month) , NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0040.ie', 'Test Holder 0040', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0041.ie', 'Test Holder 0041', 3, 5, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), LAST_DAY(CURDATE() + Interval 12 Month) - Interval 2 Year, LAST_DAY(CURDATE() + Interval 12 Month) , NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0042.ie', 'Test Holder 0042', 3, 5, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), LAST_DAY(CURDATE() + Interval 12 Month) - Interval 2 Year, LAST_DAY(CURDATE() + Interval 12 Month) , NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0043.ie', 'Test Holder 0043', 2, 3, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), LAST_DAY(CURDATE() + Interval 12 Month) - Interval 2 Day- Interval 2 Year, LAST_DAY(CURDATE() + Interval 12 Month) - Interval 2 Day, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0044.ie', 'Test Holder 0044', 3, 5, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), LAST_DAY(CURDATE() + Interval 12 Month) - Interval 3 Day- Interval 2 Year, LAST_DAY(CURDATE() + Interval 12 Month) - Interval 3 Day, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0045.ie', 'Test Holder 0045', 3, 5, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), LAST_DAY(CURDATE() + Interval 5 Month) - Interval 10 Day- Interval 5 Year, LAST_DAY(CURDATE() + Interval 5 Month) - Interval 10 Day, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0046.ie', 'Test Holder 0046', 2, 3, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), LAST_DAY(CURDATE() + Interval 5 Month) - Interval 15 Day- Interval 5 Year, LAST_DAY(CURDATE() + Interval 5 Month) - Interval 15 Day, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0047.ie', 'Test Holder 0047', 1, 1, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 2 Month, CURDATE() + Interval 14 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0048.ie', 'Test Holder 0048', 3, 5, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), LAST_DAY(CURDATE() + Interval 5 Month) - Interval 22 Day- Interval 5 Year, LAST_DAY(CURDATE() + Interval 5 Month) - Interval 22 Day, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0049.ie', 'Test Holder 0049', 7, 6, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), LAST_DAY(CURDATE() + Interval 5 Month) - Interval 29 Day- Interval 5 Year, LAST_DAY(CURDATE() + Interval 5 Month) - Interval 29 Day, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0050.ie', 'Test Holder 0050', 1, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 37 Month, CURDATE() - Interval 1 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0051.ie', 'Test Holder 0051', 2, 5, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 37 Month, CURDATE() - Interval 1 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0052.ie', 'Test Holder 0052', 1, 4, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 37 Month, CURDATE() - Interval 1 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0053.ie', 'Test Holder 0053', 7, 6, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 37 Month, CURDATE() - Interval 1 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0054.ie', 'Test Holder 0054', 8, 11, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 37 Month, CURDATE() - Interval 1 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0055.ie', 'Test Holder 0055', 6, 6, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 37 Month, CURDATE() - Interval 1 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0056.ie', 'Test Holder 0056', 4, 8, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 37 Month, CURDATE() - Interval 1 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0057.ie', 'Test Holder 0057', 2, 5, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), DATE_FORMAT(CURDATE() - Interval 37 Month, '%Y%-%m%-07'), DATE_FORMAT(CURDATE() - Interval 1 Month, '%Y%-%m%-07'), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0058.ie', 'Test Holder 0058', 7, 6, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), LAST_DAY(CURDATE() + Interval 7 Month) - Interval 29 Day- Interval 8 Year, LAST_DAY(CURDATE() + Interval 7 Month) - Interval 29 Day, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0059.ie', 'Test Holder 0059', 2, 5, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), DATE_FORMAT(CURDATE() - Interval 37 Month, '%Y%-%m%-07'), DATE_FORMAT(CURDATE() - Interval 1 Month, '%Y%-%m%-07'), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0060.ie', 'Test Holder 0060', 7, 6, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), LAST_DAY(CURDATE() + Interval 8 Month) - Interval 8 Year, LAST_DAY(CURDATE() + Interval 8 Month) , NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0061.ie', 'Test Holder 0061', 8, 7, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), LAST_DAY(CURDATE() + Interval 8 Month) - Interval 8 Year, LAST_DAY(CURDATE() + Interval 8 Month) , NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0062.ie', 'Test Holder 0062', 3, 5, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), LAST_DAY(CURDATE() + Interval 8 Month) - Interval 2 Day- Interval 9 Year, LAST_DAY(CURDATE() + Interval 8 Month) - Interval 2 Day, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0063.ie', 'Test Holder 0063', 5, 4, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), LAST_DAY(CURDATE() + Interval 8 Month) - Interval 3 Day- Interval 9 Year, LAST_DAY(CURDATE() + Interval 8 Month) - Interval 3 Day, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0064.ie', 'Test Holder 0064', 2, 4, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), LAST_DAY(CURDATE() + Interval 8 Month) - Interval 15 Day- Interval 9 Year, LAST_DAY(CURDATE() + Interval 8 Month) - Interval 15 Day, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0065.ie', 'Test Holder 0065', 2, 4, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), LAST_DAY(CURDATE() + Interval 8 Month) - Interval 22 Day- Interval 9 Year, LAST_DAY(CURDATE() + Interval 8 Month) - Interval 22 Day, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0066.ie', 'Test Holder 0066', 7, 7, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), LAST_DAY(CURDATE() + Interval 8 Month) - Interval 4 Day- Interval 9 Year, LAST_DAY(CURDATE() + Interval 8 Month) - Interval 4 Day, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0067.ie', 'Test Holder 0067', 4, 8, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), LAST_DAY(CURDATE() + Interval 9 Month) - Interval 4 Day- Interval 10 Year, LAST_DAY(CURDATE() + Interval 9 Month) - Interval 4 Day, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0068.ie', 'Test Holder 0068', 3, 7, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), LAST_DAY(CURDATE() + Interval 9 Month) - Interval 22 Day- Interval 10 Year, LAST_DAY(CURDATE() + Interval 9 Month) - Interval 22 Day, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0069.ie', 'Test Holder 0069', 4, 8, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), LAST_DAY(CURDATE() + Interval 9 Month) - Interval 29 Day- Interval 10 Year, LAST_DAY(CURDATE() + Interval 9 Month) - Interval 29 Day, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0070.ie', 'Test Holder 0070', 8, 11, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), LAST_DAY(CURDATE() + Interval 9 Month) - Interval 10 Year, LAST_DAY(CURDATE() + Interval 9 Month) , NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0071.ie', 'Test Holder 0071', 4, 7, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), LAST_DAY(CURDATE() + Interval 9 Month) - Interval 10 Year, LAST_DAY(CURDATE() + Interval 9 Month) , NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0072.ie', 'Test Holder 0072', 2, 11, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), LAST_DAY(CURDATE() + Interval 9 Month) - Interval 2 Day- Interval 10 Year, LAST_DAY(CURDATE() + Interval 9 Month) - Interval 2 Day, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0073.ie', 'Test Holder 0073', 3, 11, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), LAST_DAY(CURDATE() + Interval 9 Month) - Interval 3 Day- Interval 10 Year, LAST_DAY(CURDATE() + Interval 9 Month) - Interval 3 Day, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0074.ie', 'Test Holder 0074', 2, 11, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), LAST_DAY(CURDATE() + Interval 9 Month) - Interval 15 Day- Interval 10 Year, LAST_DAY(CURDATE() + Interval 9 Month) - Interval 15 Day, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0075.ie', 'Test Holder 0075', 2, 3, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), LAST_DAY(CURDATE() + Interval 9 Month) - Interval 22 Day- Interval 10 Year, LAST_DAY(CURDATE() + Interval 9 Month) - Interval 22 Day, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0076.ie', 'Test Holder 0076', 6, 11, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, LAST_DAY(CURDATE() + Interval 9 Month) - Interval 4 Day, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0077.ie', 'Test Holder 0077', 7, 7, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), DATE_FORMAT(CURDATE() - Interval 1 Month, '%Y%-%m%-14'), DATE_FORMAT(CURDATE() - Interval 1 Month, '%Y%-%m%-14'), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0078.ie', 'Test Holder 0078', 4, 8, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), DATE_FORMAT(CURDATE() - Interval 1 Month, '%Y%-%m%-15'), DATE_FORMAT(CURDATE() - Interval 1 Month, '%Y%-%m%-15'), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0079.ie', 'Test Holder 0079', 3, 7, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), DATE_FORMAT(CURDATE() - Interval 1 Month, '%Y%-%m%-15'), DATE_FORMAT(CURDATE() - Interval 1 Month, '%Y%-%m%-15'), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0080.ie', 'Test Holder 0080', 7, 11, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE(), CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0081.ie', 'Test Holder 0081', 8, 11, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE(), CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0082.ie', 'Test Holder 0082', 4, 8, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE(), CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0083.ie', 'Test Holder 0083', 3, 5, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE(), CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0084.ie', 'Test Holder 0084', 8, 7, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE(), CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0085.ie', 'Test Holder 0085', 5, 10, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE(), CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0086.ie', 'Test Holder 0086', 1, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE(), CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0087.ie', 'Test Holder 0087', 2, 5, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE(), CURDATE(), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0088.ie', 'Test Holder 0088', 2, 5, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), DATE_FORMAT(CURDATE() - Interval 37 Month, '%Y%-%m%-07'), DATE_FORMAT(CURDATE() - Interval 1 Month, '%Y%-%m%-07'), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0089.ie', 'Test Holder 0089', 7, 6, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), LAST_DAY(CURDATE() + Interval 4 Month) - Interval 4 Year, LAST_DAY(CURDATE() + Interval 4 Month) , NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0090.ie', 'Test Holder 0090', 2, 5, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), DATE_FORMAT(CURDATE() - Interval 37 Month, '%Y%-%m%-08'), DATE_FORMAT(CURDATE() - Interval 1 Month, '%Y%-%m%-08'), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0091.ie', 'Test Holder 0091', 7, 6, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), LAST_DAY(CURDATE() + Interval 4 Month) - Interval 2 Day- Interval 4 Year, LAST_DAY(CURDATE() + Interval 4 Month) - Interval 2 Day, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0092.ie', 'Test Holder 0092', 1, 1, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 2 Month, CURDATE() + Interval 14 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0093.ie', 'Test Holder 0093', 8, 11, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0094.ie', 'Test Holder 0094', 6, 6, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), DATE_FORMAT(CURDATE() - Interval 37 Month, '%Y%-%m%-08'), DATE_FORMAT(CURDATE() - Interval 1 Month, '%Y%-%m%-08'), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0095.ie', 'Test Holder 0095', 4, 8, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), DATE_FORMAT(CURDATE() - Interval 37 Month, '%Y%-%m%-08'), DATE_FORMAT(CURDATE() - Interval 1 Month, '%Y%-%m%-08'), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0096.ie', 'Test Holder 0096', 8, 7, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), DATE_FORMAT(CURDATE() - Interval 37 Month, '%Y%-%m%-08'), DATE_FORMAT(CURDATE() - Interval 1 Month, '%Y%-%m%-08'), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0097.ie', 'Test Holder 0097', 3, 5, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0098.ie', 'Test Holder 0098', 5, 4, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0099.ie', 'Test Holder 0099', 2, 4, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), DATE_FORMAT(CURDATE() - Interval 37 Month, '%Y%-%m%-08'), DATE_FORMAT(CURDATE() - Interval 1 Month, '%Y%-%m%-08'), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO DomainHist VALUES (NULL, 'example0100.ie', 'Test Holder 0100', 2, 4, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), DATE_FORMAT(CURDATE() - Interval 37 Month, '%Y%-%m%-08'), DATE_FORMAT(CURDATE() - Interval 1 Month, '%Y%-%m%-08'), NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);

INSERT INTO Domain VALUES ('example0001.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0001.ie'));
INSERT INTO Domain VALUES ('example0002.ie', 'Test Holder 0002', 3, 5, NULL, 600, LAST_DAY(CURDATE() + Interval 10 Month) - Interval 15 Day- Interval 1 Year, LAST_DAY(CURDATE() + Interval 10 Month) - Interval 15 Day, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0002.ie'));
INSERT INTO Domain VALUES ('example0003.ie', 'Test Holder 0003', 7, 6, NULL, 600, LAST_DAY(CURDATE() + Interval 11 Month) - Interval 2 Day- Interval 1 Year, LAST_DAY(CURDATE() + Interval 11 Month) - Interval 2 Day, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0003.ie'));
INSERT INTO Domain VALUES ('example0004.ie', 'Test Holder 0004', 5, 11, NULL, 600, CURDATE(), CURDATE(), NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0004.ie'));
INSERT INTO Domain VALUES ('example0005.ie', 'Test Holder 0005', 2, 11, NULL, 600, CURDATE(), CURDATE(), NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0005.ie'));
INSERT INTO Domain VALUES ('example0006.ie', 'Test Holder 0006', 7, 11, NULL, 600, CURDATE(), CURDATE(), NOW(), 'Test Remark', 'NO', '1', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0006.ie'));
INSERT INTO Domain VALUES ('example0007.ie', 'Test Holder 0007', 8, 11, NULL, 600, CURDATE(), CURDATE(), NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0007.ie'));
INSERT INTO Domain VALUES ('example0008.ie', 'Test Holder 0008', 4, 8, NULL, 600, CURDATE(), CURDATE(), NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0008.ie'));
INSERT INTO Domain VALUES ('example0009.ie', 'Test Holder 0009', 3, 5, NULL, 600, CURDATE(), CURDATE(), NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0009.ie'));
INSERT INTO Domain VALUES ('example0010.ie', 'Test Holder 0010', 8, 11, NULL, 600, CURDATE(), CURDATE(), NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0010.ie'));
INSERT INTO Domain VALUES ('example0011.ie', 'Test Holder 0011', 5, 10, NULL, 600, CURDATE(), CURDATE(), NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, '68OM3LQV7C3F', CURDATE() + Interval 12 day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0011.ie'));
INSERT INTO Domain VALUES ('example0012.ie', 'Test Holder 0012', 1, 9, NULL, 600, CURDATE(), CURDATE(), NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0012.ie'));
INSERT INTO Domain VALUES ('example0013.ie', 'Test Holder 0013', 8, 7, NULL, 600, CURDATE(), CURDATE(), NOW(), 'Test Remark', 'NO', '113', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0013.ie'));
INSERT INTO Domain VALUES ('example0014.ie', 'Test Holder 0014', 8, 7, NULL, 600, DATE_FORMAT(CURDATE() - Interval 12 Month, '%Y%-%m%-23'), DATE_FORMAT(CURDATE(), '%Y%-%m%-23'), NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0014.ie'));
INSERT INTO Domain VALUES ('example0015.ie', 'Test Holder 0015', 6, 6, NULL, 600, DATE_FORMAT(CURDATE() - Interval 12 Month, '%Y%-%m%-23'), DATE_FORMAT(CURDATE(), '%Y%-%m%-23'), NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0015.ie'));
INSERT INTO Domain VALUES ('example0016.ie', 'Test Holder 0016', 3, 11, NULL, 600, DATE_FORMAT(CURDATE() - Interval 37 Month, '%Y%-%m%-07'), DATE_FORMAT(CURDATE() - Interval 1 Month, '%Y%-%m%-07'), NOW(), 'Test Case: Domain was IN MSD (and therefore not invoiced) and then xfered to this account', 'NO', '11', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0016.ie'));
INSERT INTO Domain VALUES ('example0017.ie', 'Test Holder 0017', 5, 7, NULL, 600, DATE_FORMAT(CURDATE() - Interval 12 Month, '%Y%-%m%-23'), DATE_FORMAT(CURDATE(), '%Y%-%m%-23'), NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0017.ie'));
INSERT INTO Domain VALUES ('example0018.ie', 'Test Holder 0018', 2, 4, NULL, 600, DATE_FORMAT(CURDATE() - Interval 12 Month, '%Y%-%m%-23'), DATE_FORMAT(CURDATE(), '%Y%-%m%-23'), NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0018.ie'));
INSERT INTO Domain VALUES ('example0019.ie', 'Test Holder 0019', 8, 7, NULL, 600, DATE_FORMAT(CURDATE() - Interval 12 Month, '%Y%-%m%-23'), DATE_FORMAT(CURDATE(), '%Y%-%m%-23'), NOW(), 'Test Remark', 'NO', '33', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0019.ie'));
INSERT INTO Domain VALUES ('example0020.ie', 'Test Holder 0020', 5, 11, NULL, 600, CURDATE(), CURDATE(), NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0020.ie'));
INSERT INTO Domain VALUES ('example0021.ie', 'Test Holder 0021', 2, 11, NULL, 600, CURDATE(), CURDATE(), NOW(), 'Test Remark', 'NO', '40', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0021.ie'));
INSERT INTO Domain VALUES ('example0022.ie', 'Test Holder 0022', 1, 4, NULL, 600, DATE_FORMAT(CURDATE() - Interval 1 Month, '%Y%-%m%-23'), DATE_FORMAT(CURDATE() - Interval 1 Month, '%Y%-%m%-23'), NOW(), 'Test Remark', 'NO', '65', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0022.ie'));
INSERT INTO Domain VALUES ('example0023.ie', 'Test Holder 0023', 7, 6, NULL, 600, CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '125', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0023.ie'));
INSERT INTO Domain VALUES ('example0024.ie', 'Test Holder 0024', 2, 11, NULL, 600, DATE_FORMAT(CURDATE() - Interval 25 Month, '%Y%-%m%-04'), DATE_FORMAT(CURDATE() - Interval 1 Month, '%Y%-%m%-04'), NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0024.ie'));
INSERT INTO Domain VALUES ('example0025.ie', 'Test Holder 0025', 1, 1, NULL, 600, DATE_FORMAT(CURDATE() - Interval 12 Month, '%Y%-%m%-23'), DATE_FORMAT(CURDATE(), '%Y%-%m%-23'), NOW(), 'Test Remark', 'NO', '97', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0025.ie'));
INSERT INTO Domain VALUES ('example0026.ie', 'Test Holder 0026', 1, 9, NULL, 600, DATE_FORMAT(CURDATE() - Interval 12 Month, '%Y%-%m%-23'), DATE_FORMAT(CURDATE(), '%Y%-%m%-23'), NOW(), 'Test Remark', 'NO', '116', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0026.ie'));
INSERT INTO Domain VALUES ('example0027.ie', 'Test Holder 0027', 8, 11, NULL, 600, DATE_FORMAT(CURDATE() - Interval 12 Month, '%Y%-%m%-23'), DATE_FORMAT(CURDATE(), '%Y%-%m%-23'), NOW(), 'Test Remark', 'NO', '297', NULL, NULL, NULL, '68OM3LQV7C3F', CURDATE() + Interval 12 day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0027.ie'));
INSERT INTO Domain VALUES ('example0028.ie', 'Test Holder 0028', 6, 6, NULL, 600, DATE_FORMAT(CURDATE() - Interval 12 Month, '%Y%-%m%-23'), DATE_FORMAT(CURDATE(), '%Y%-%m%-23'), NOW(), 'Test Remark', 'NO', '523', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0028.ie'));
INSERT INTO Domain VALUES ('example0029.ie', 'Test Holder 0029', 8, 11, NULL, 600, DATE_FORMAT(CURDATE() - Interval 12 Month, '%Y%-%m%-23'), DATE_FORMAT(CURDATE(), '%Y%-%m%-23'), NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0029.ie'));
INSERT INTO Domain VALUES ('example0030.ie', 'Test Holder 0030', 5, 7, NULL, 600, DATE_FORMAT(CURDATE() - Interval 12 Month, '%Y%-%m%-23'), DATE_FORMAT(CURDATE(), '%Y%-%m%-23'), NOW(), 'Test Remark', 'NO', '390', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0030.ie'));
INSERT INTO Domain VALUES ('example0031.ie', 'Test Holder 0031', 4, 8, NULL, 600, DATE_FORMAT(CURDATE() - Interval 1 Month, '%Y%-%m%-23'), DATE_FORMAT(CURDATE() - Interval 1 Month, '%Y%-%m%-23'), NOW(), 'Test Remark', 'NO', '81', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0031.ie'));
INSERT INTO Domain VALUES ('example0032.ie', 'Test Holder 0032', 8, 11, NULL, 600, DATE_FORMAT(CURDATE() - Interval 1 Month, '%Y%-%m%-24'), DATE_FORMAT(CURDATE() - Interval 1 Month, '%Y%-%m%-24'), NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0032.ie'));
INSERT INTO Domain VALUES ('example0033.ie', 'Test Holder 0033', 4, 7, NULL, 600, DATE_FORMAT(CURDATE() - Interval 25 Month, '%Y%-%m%-04'), DATE_FORMAT(CURDATE() - Interval 1 Month, '%Y%-%m%-04'), NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0033.ie'));
INSERT INTO Domain VALUES ('example0034.ie', 'Test Holder 0034', 2, 11, NULL, 600, DATE_FORMAT(CURDATE() - Interval 25 Month, '%Y%-%m%-04'), DATE_FORMAT(CURDATE() - Interval 1 Month, '%Y%-%m%-04'), NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0034.ie'));
INSERT INTO Domain VALUES ('example0035.ie', 'Test Holder 0035', 2, 11, NULL, 600, DATE_FORMAT(CURDATE() - Interval 25 Month, '%Y%-%m%-04'), DATE_FORMAT(CURDATE() - Interval 1 Month, '%Y%-%m%-04'), NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0035.ie'));
INSERT INTO Domain VALUES ('example0036.ie', 'Test Holder 0036', 2, 4, NULL, 600, CURDATE() - Interval 22 Month, CURDATE() + Interval 2 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0036.ie'));
INSERT INTO Domain VALUES ('example0037.ie', 'Test Holder 0037', 8, 7, NULL, 600, CURDATE() - Interval 22 Month, CURDATE() + Interval 2 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0037.ie'));
INSERT INTO Domain VALUES ('example0038.ie', 'Test Holder 0038', 2, 3, NULL, 600, LAST_DAY(CURDATE() + Interval 5 Month) - Interval 4 Year, LAST_DAY(CURDATE() + Interval 5 Month) , NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0038.ie'));
INSERT INTO Domain VALUES ('example0039.ie', 'Test Holder 0039', 6, 11, NULL, 600, LAST_DAY(CURDATE() + Interval 5 Month) - Interval 4 Year, LAST_DAY(CURDATE() + Interval 5 Month) , NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0039.ie'));
INSERT INTO Domain VALUES ('example0040.ie', 'Test Holder 0040', 3, 2, NULL, 600, CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0040.ie'));
INSERT INTO Domain VALUES ('example0041.ie', 'Test Holder 0041', 3, 5, NULL, 600, LAST_DAY(CURDATE() + Interval 12 Month) - Interval 2 Year, LAST_DAY(CURDATE() + Interval 12 Month) , NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0041.ie'));
INSERT INTO Domain VALUES ('example0042.ie', 'Test Holder 0042', 3, 5, NULL, 600, LAST_DAY(CURDATE() + Interval 12 Month) - Interval 2 Year, LAST_DAY(CURDATE() + Interval 12 Month) , NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0042.ie'));
INSERT INTO Domain VALUES ('example0043.ie', 'Test Holder 0043', 2, 3, NULL, 600, LAST_DAY(CURDATE() + Interval 12 Month) - Interval 2 Day- Interval 2 Year, LAST_DAY(CURDATE() + Interval 12 Month) - Interval 2 Day, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0043.ie'));
INSERT INTO Domain VALUES ('example0044.ie', 'Test Holder 0044', 3, 5, NULL, 600, LAST_DAY(CURDATE() + Interval 12 Month) - Interval 3 Day- Interval 2 Year, LAST_DAY(CURDATE() + Interval 12 Month) - Interval 3 Day, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0044.ie'));
INSERT INTO Domain VALUES ('example0045.ie', 'Test Holder 0045', 3, 5, NULL, 600, LAST_DAY(CURDATE() + Interval 5 Month) - Interval 10 Day- Interval 5 Year, LAST_DAY(CURDATE() + Interval 5 Month) - Interval 10 Day, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0045.ie'));
INSERT INTO Domain VALUES ('example0046.ie', 'Test Holder 0046', 2, 3, NULL, 600, LAST_DAY(CURDATE() + Interval 5 Month) - Interval 15 Day- Interval 5 Year, LAST_DAY(CURDATE() + Interval 5 Month) - Interval 15 Day, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0046.ie'));
INSERT INTO Domain VALUES ('example0047.ie', 'Test Holder 0047', 1, 1, NULL, 600, CURDATE() - Interval 2 Month, CURDATE() + Interval 14 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0047.ie'));
INSERT INTO Domain VALUES ('example0048.ie', 'Test Holder 0048', 3, 5, NULL, 600, LAST_DAY(CURDATE() + Interval 5 Month) - Interval 22 Day- Interval 5 Year, LAST_DAY(CURDATE() + Interval 5 Month) - Interval 22 Day, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0048.ie'));
INSERT INTO Domain VALUES ('example0049.ie', 'Test Holder 0049', 7, 6, NULL, 600, LAST_DAY(CURDATE() + Interval 5 Month) - Interval 29 Day- Interval 5 Year, LAST_DAY(CURDATE() + Interval 5 Month) - Interval 29 Day, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0049.ie'));
INSERT INTO Domain VALUES ('example0050.ie', 'Test Holder 0050', 1, 2, NULL, 600, CURDATE() - Interval 37 Month, CURDATE() - Interval 1 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0050.ie'));
INSERT INTO Domain VALUES ('example0051.ie', 'Test Holder 0051', 2, 5, NULL, 600, CURDATE() - Interval 37 Month, CURDATE() - Interval 1 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0051.ie'));
INSERT INTO Domain VALUES ('example0052.ie', 'Test Holder 0052', 1, 4, NULL, 600, CURDATE() - Interval 37 Month, CURDATE() - Interval 1 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0052.ie'));
INSERT INTO Domain VALUES ('example0053.ie', 'Test Holder 0053', 7, 6, NULL, 600, CURDATE() - Interval 37 Month, CURDATE() - Interval 1 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0053.ie'));
INSERT INTO Domain VALUES ('example0054.ie', 'Test Holder 0054', 8, 11, NULL, 600, CURDATE() - Interval 37 Month, CURDATE() - Interval 1 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0054.ie'));
INSERT INTO Domain VALUES ('example0055.ie', 'Test Holder 0055', 6, 6, NULL, 600, CURDATE() - Interval 37 Month, CURDATE() - Interval 1 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0055.ie'));
INSERT INTO Domain VALUES ('example0056.ie', 'Test Holder 0056', 4, 8, NULL, 600, CURDATE() - Interval 37 Month, CURDATE() - Interval 1 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0056.ie'));
INSERT INTO Domain VALUES ('example0057.ie', 'Test Holder 0057', 2, 5, NULL, 600, DATE_FORMAT(CURDATE() - Interval 37 Month, '%Y%-%m%-07'), DATE_FORMAT(CURDATE() - Interval 1 Month, '%Y%-%m%-07'), NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0057.ie'));
INSERT INTO Domain VALUES ('example0058.ie', 'Test Holder 0058', 7, 6, NULL, 600, LAST_DAY(CURDATE() + Interval 7 Month) - Interval 29 Day- Interval 8 Year, LAST_DAY(CURDATE() + Interval 7 Month) - Interval 29 Day, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0058.ie'));
INSERT INTO Domain VALUES ('example0059.ie', 'Test Holder 0059', 2, 5, NULL, 600, DATE_FORMAT(CURDATE() - Interval 37 Month, '%Y%-%m%-07'), DATE_FORMAT(CURDATE() - Interval 1 Month, '%Y%-%m%-07'), NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0059.ie'));
INSERT INTO Domain VALUES ('example0060.ie', 'Test Holder 0060', 7, 6, NULL, 600, LAST_DAY(CURDATE() + Interval 8 Month) - Interval 8 Year, LAST_DAY(CURDATE() + Interval 8 Month) , NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0060.ie'));
INSERT INTO Domain VALUES ('example0061.ie', 'Test Holder 0061', 8, 7, NULL, 600, LAST_DAY(CURDATE() + Interval 8 Month) - Interval 8 Year, LAST_DAY(CURDATE() + Interval 8 Month) , NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0061.ie'));
INSERT INTO Domain VALUES ('example0062.ie', 'Test Holder 0062', 3, 5, NULL, 600, LAST_DAY(CURDATE() + Interval 8 Month) - Interval 2 Day- Interval 9 Year, LAST_DAY(CURDATE() + Interval 8 Month) - Interval 2 Day, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0062.ie'));
INSERT INTO Domain VALUES ('example0063.ie', 'Test Holder 0063', 5, 4, NULL, 600, LAST_DAY(CURDATE() + Interval 8 Month) - Interval 3 Day- Interval 9 Year, LAST_DAY(CURDATE() + Interval 8 Month) - Interval 3 Day, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0063.ie'));
INSERT INTO Domain VALUES ('example0064.ie', 'Test Holder 0064', 2, 4, NULL, 600, LAST_DAY(CURDATE() + Interval 8 Month) - Interval 15 Day- Interval 9 Year, LAST_DAY(CURDATE() + Interval 8 Month) - Interval 15 Day, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0064.ie'));
INSERT INTO Domain VALUES ('example0065.ie', 'Test Holder 0065', 2, 4, NULL, 600, LAST_DAY(CURDATE() + Interval 8 Month) - Interval 22 Day- Interval 9 Year, LAST_DAY(CURDATE() + Interval 8 Month) - Interval 22 Day, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0065.ie'));
INSERT INTO Domain VALUES ('example0066.ie', 'Test Holder 0066', 7, 7, NULL, 600, LAST_DAY(CURDATE() + Interval 8 Month) - Interval 4 Day- Interval 9 Year, LAST_DAY(CURDATE() + Interval 8 Month) - Interval 4 Day, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0066.ie'));
INSERT INTO Domain VALUES ('example0067.ie', 'Test Holder 0067', 4, 8, NULL, 600, LAST_DAY(CURDATE() + Interval 9 Month) - Interval 4 Day- Interval 10 Year, LAST_DAY(CURDATE() + Interval 9 Month) - Interval 4 Day, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0067.ie'));
INSERT INTO Domain VALUES ('example0068.ie', 'Test Holder 0068', 3, 7, NULL, 600, LAST_DAY(CURDATE() + Interval 9 Month) - Interval 22 Day- Interval 10 Year, LAST_DAY(CURDATE() + Interval 9 Month) - Interval 22 Day, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0068.ie'));
INSERT INTO Domain VALUES ('example0069.ie', 'Test Holder 0069', 4, 8, NULL, 600, LAST_DAY(CURDATE() + Interval 9 Month) - Interval 29 Day- Interval 10 Year, LAST_DAY(CURDATE() + Interval 9 Month) - Interval 29 Day, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0069.ie'));
INSERT INTO Domain VALUES ('example0070.ie', 'Test Holder 0070', 8, 11, NULL, 600, LAST_DAY(CURDATE() + Interval 9 Month) - Interval 10 Year, LAST_DAY(CURDATE() + Interval 9 Month) , NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0070.ie'));
INSERT INTO Domain VALUES ('example0071.ie', 'Test Holder 0071', 4, 7, NULL, 600, LAST_DAY(CURDATE() + Interval 9 Month) - Interval 10 Year, LAST_DAY(CURDATE() + Interval 9 Month) , NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0071.ie'));
INSERT INTO Domain VALUES ('example0072.ie', 'Test Holder 0072', 2, 11, NULL, 600, LAST_DAY(CURDATE() + Interval 9 Month) - Interval 2 Day- Interval 10 Year, LAST_DAY(CURDATE() + Interval 9 Month) - Interval 2 Day, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0072.ie'));
INSERT INTO Domain VALUES ('example0073.ie', 'Test Holder 0073', 3, 11, NULL, 600, LAST_DAY(CURDATE() + Interval 9 Month) - Interval 3 Day- Interval 10 Year, LAST_DAY(CURDATE() + Interval 9 Month) - Interval 3 Day, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0073.ie'));
INSERT INTO Domain VALUES ('example0074.ie', 'Test Holder 0074', 2, 11, NULL, 600, LAST_DAY(CURDATE() + Interval 9 Month) - Interval 15 Day- Interval 10 Year, LAST_DAY(CURDATE() + Interval 9 Month) - Interval 15 Day, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0074.ie'));
INSERT INTO Domain VALUES ('example0075.ie', 'Test Holder 0075', 2, 3, NULL, 600, LAST_DAY(CURDATE() + Interval 9 Month) - Interval 22 Day- Interval 10 Year, LAST_DAY(CURDATE() + Interval 9 Month) - Interval 22 Day, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0075.ie'));
INSERT INTO Domain VALUES ('example0076.ie', 'Test Holder 0076', 6, 11, NULL, 600, CURDATE() - Interval 12 Month, LAST_DAY(CURDATE() + Interval 9 Month) - Interval 4 Day, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0076.ie'));
INSERT INTO Domain VALUES ('example0077.ie', 'Test Holder 0077', 7, 7, NULL, 600, DATE_FORMAT(CURDATE() - Interval 1 Month, '%Y%-%m%-14'), DATE_FORMAT(CURDATE() - Interval 1 Month, '%Y%-%m%-14'), NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0077.ie'));
INSERT INTO Domain VALUES ('example0078.ie', 'Test Holder 0078', 4, 8, NULL, 600, DATE_FORMAT(CURDATE() - Interval 1 Month, '%Y%-%m%-15'), DATE_FORMAT(CURDATE() - Interval 1 Month, '%Y%-%m%-15'), NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0078.ie'));
INSERT INTO Domain VALUES ('example0079.ie', 'Test Holder 0079', 3, 7, NULL, 600, DATE_FORMAT(CURDATE() - Interval 1 Month, '%Y%-%m%-15'), DATE_FORMAT(CURDATE() - Interval 1 Month, '%Y%-%m%-15'), NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0079.ie'));
INSERT INTO Domain VALUES ('example0080.ie', 'Test Holder 0080', 7, 11, NULL, 600, CURDATE(), CURDATE(), NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0080.ie'));
INSERT INTO Domain VALUES ('example0081.ie', 'Test Holder 0081', 8, 11, NULL, 600, CURDATE(), CURDATE(), NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0081.ie'));
INSERT INTO Domain VALUES ('example0082.ie', 'Test Holder 0082', 4, 8, NULL, 600, CURDATE(), CURDATE(), NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0082.ie'));
INSERT INTO Domain VALUES ('example0083.ie', 'Test Holder 0083', 3, 5, NULL, 600, CURDATE(), CURDATE(), NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0083.ie'));
INSERT INTO Domain VALUES ('example0084.ie', 'Test Holder 0084', 8, 7, NULL, 600, CURDATE(), CURDATE(), NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0084.ie'));
INSERT INTO Domain VALUES ('example0085.ie', 'Test Holder 0085', 5, 10, NULL, 600, CURDATE(), CURDATE(), NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0085.ie'));
INSERT INTO Domain VALUES ('example0086.ie', 'Test Holder 0086', 1, 2, NULL, 600, CURDATE(), CURDATE(), NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0086.ie'));
INSERT INTO Domain VALUES ('example0087.ie', 'Test Holder 0087', 2, 5, NULL, 600, CURDATE(), CURDATE(), NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0087.ie'));
INSERT INTO Domain VALUES ('example0088.ie', 'Test Holder 0088', 2, 5, NULL, 600, DATE_FORMAT(CURDATE() - Interval 37 Month, '%Y%-%m%-07'), DATE_FORMAT(CURDATE() - Interval 1 Month, '%Y%-%m%-07'), NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0088.ie'));
INSERT INTO Domain VALUES ('example0089.ie', 'Test Holder 0089', 7, 6, NULL, 600, LAST_DAY(CURDATE() + Interval 4 Month) - Interval 4 Year, LAST_DAY(CURDATE() + Interval 4 Month) , NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0089.ie'));
INSERT INTO Domain VALUES ('example0090.ie', 'Test Holder 0090', 2, 5, NULL, 600, DATE_FORMAT(CURDATE() - Interval 37 Month, '%Y%-%m%-08'), DATE_FORMAT(CURDATE() - Interval 1 Month, '%Y%-%m%-08'), NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0090.ie'));
INSERT INTO Domain VALUES ('example0091.ie', 'Test Holder 0091', 7, 6, NULL, 600, LAST_DAY(CURDATE() + Interval 4 Month) - Interval 2 Day- Interval 4 Year, LAST_DAY(CURDATE() + Interval 4 Month) - Interval 2 Day, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0091.ie'));
INSERT INTO Domain VALUES ('example0092.ie', 'Test Holder 0092', 1, 1, NULL, 600, CURDATE() - Interval 2 Month, CURDATE() + Interval 14 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0092.ie'));
INSERT INTO Domain VALUES ('example0093.ie', 'Test Holder 0093', 8, 11, NULL, 600, CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0093.ie'));
INSERT INTO Domain VALUES ('example0094.ie', 'Test Holder 0094', 6, 6, NULL, 600, DATE_FORMAT(CURDATE() - Interval 37 Month, '%Y%-%m%-08'), DATE_FORMAT(CURDATE() - Interval 1 Month, '%Y%-%m%-08'), NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0094.ie'));
INSERT INTO Domain VALUES ('example0095.ie', 'Test Holder 0095', 4, 8, NULL, 600, DATE_FORMAT(CURDATE() - Interval 37 Month, '%Y%-%m%-08'), DATE_FORMAT(CURDATE() - Interval 1 Month, '%Y%-%m%-08'), NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0095.ie'));
INSERT INTO Domain VALUES ('example0096.ie', 'Test Holder 0096', 8, 7, NULL, 600, DATE_FORMAT(CURDATE() - Interval 37 Month, '%Y%-%m%-08'), DATE_FORMAT(CURDATE() - Interval 1 Month, '%Y%-%m%-08'), NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0096.ie'));
INSERT INTO Domain VALUES ('example0097.ie', 'Test Holder 0097', 3, 5, NULL, 600, CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0097.ie'));
INSERT INTO Domain VALUES ('example0098.ie', 'Test Holder 0098', 5, 4, NULL, 600, CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0098.ie'));
INSERT INTO Domain VALUES ('example0099.ie', 'Test Holder 0099', 2, 4, NULL, 600, DATE_FORMAT(CURDATE() - Interval 37 Month, '%Y%-%m%-08'), DATE_FORMAT(CURDATE() - Interval 1 Month, '%Y%-%m%-08'), NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0099.ie'));
INSERT INTO Domain VALUES ('example0100.ie', 'Test Holder 0100', 2, 4, NULL, 600, DATE_FORMAT(CURDATE() - Interval 37 Month, '%Y%-%m%-08'), DATE_FORMAT(CURDATE() - Interval 1 Month, '%Y%-%m%-08'), NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0100.ie'));

INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0001.ie'), 'example0001.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0001.ie'), 'example0001.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0001.ie'), 'example0001.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0001.ie'), 'example0001.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0002.ie'), 'example0002.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0002.ie'), 'example0002.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0002.ie'), 'example0002.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0002.ie'), 'example0002.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0003.ie'), 'example0003.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0003.ie'), 'example0003.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0003.ie'), 'example0003.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0003.ie'), 'example0003.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0004.ie'), 'example0004.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0004.ie'), 'example0004.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0004.ie'), 'example0004.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0004.ie'), 'example0004.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0005.ie'), 'example0005.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0005.ie'), 'example0005.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0005.ie'), 'example0005.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0005.ie'), 'example0005.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0006.ie'), 'example0006.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0006.ie'), 'example0006.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0006.ie'), 'example0006.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0006.ie'), 'example0006.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0007.ie'), 'example0007.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0007.ie'), 'example0007.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0007.ie'), 'example0007.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0007.ie'), 'example0007.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0008.ie'), 'example0008.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0008.ie'), 'example0008.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0008.ie'), 'example0008.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0008.ie'), 'example0008.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0009.ie'), 'example0009.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0009.ie'), 'example0009.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0009.ie'), 'example0009.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0009.ie'), 'example0009.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0010.ie'), 'example0010.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0010.ie'), 'example0010.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0010.ie'), 'example0010.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0010.ie'), 'example0010.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0011.ie'), 'example0011.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0011.ie'), 'example0011.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0011.ie'), 'example0011.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0011.ie'), 'example0011.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0012.ie'), 'example0012.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0012.ie'), 'example0012.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0012.ie'), 'example0012.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0012.ie'), 'example0012.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0013.ie'), 'example0013.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0013.ie'), 'example0013.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0013.ie'), 'example0013.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0013.ie'), 'example0013.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0014.ie'), 'example0014.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0014.ie'), 'example0014.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0014.ie'), 'example0014.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0014.ie'), 'example0014.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0015.ie'), 'example0015.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0015.ie'), 'example0015.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0015.ie'), 'example0015.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0015.ie'), 'example0015.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0016.ie'), 'example0016.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0016.ie'), 'example0016.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0016.ie'), 'example0016.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0016.ie'), 'example0016.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0017.ie'), 'example0017.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0017.ie'), 'example0017.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0017.ie'), 'example0017.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0017.ie'), 'example0017.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0018.ie'), 'example0018.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0018.ie'), 'example0018.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0018.ie'), 'example0018.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0018.ie'), 'example0018.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0019.ie'), 'example0019.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0019.ie'), 'example0019.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0019.ie'), 'example0019.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0019.ie'), 'example0019.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0020.ie'), 'example0020.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0020.ie'), 'example0020.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0020.ie'), 'example0020.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0020.ie'), 'example0020.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0021.ie'), 'example0021.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0021.ie'), 'example0021.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0021.ie'), 'example0021.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0021.ie'), 'example0021.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0022.ie'), 'example0022.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0022.ie'), 'example0022.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0022.ie'), 'example0022.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0022.ie'), 'example0022.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0023.ie'), 'example0023.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0023.ie'), 'example0023.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0023.ie'), 'example0023.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0023.ie'), 'example0023.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0024.ie'), 'example0024.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0024.ie'), 'example0024.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0024.ie'), 'example0024.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0024.ie'), 'example0024.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0025.ie'), 'example0025.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0025.ie'), 'example0025.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0025.ie'), 'example0025.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0025.ie'), 'example0025.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0026.ie'), 'example0026.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0026.ie'), 'example0026.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0026.ie'), 'example0026.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0026.ie'), 'example0026.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0027.ie'), 'example0027.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0027.ie'), 'example0027.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0027.ie'), 'example0027.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0027.ie'), 'example0027.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0028.ie'), 'example0028.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0028.ie'), 'example0028.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0028.ie'), 'example0028.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0028.ie'), 'example0028.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0029.ie'), 'example0029.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0029.ie'), 'example0029.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0029.ie'), 'example0029.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0029.ie'), 'example0029.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0030.ie'), 'example0030.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0030.ie'), 'example0030.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0030.ie'), 'example0030.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0030.ie'), 'example0030.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0031.ie'), 'example0031.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0031.ie'), 'example0031.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0031.ie'), 'example0031.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0031.ie'), 'example0031.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0032.ie'), 'example0032.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0032.ie'), 'example0032.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0032.ie'), 'example0032.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0032.ie'), 'example0032.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0033.ie'), 'example0033.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0033.ie'), 'example0033.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0033.ie'), 'example0033.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0033.ie'), 'example0033.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0034.ie'), 'example0034.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0034.ie'), 'example0034.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0034.ie'), 'example0034.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0034.ie'), 'example0034.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0035.ie'), 'example0035.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0035.ie'), 'example0035.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0035.ie'), 'example0035.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0035.ie'), 'example0035.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0036.ie'), 'example0036.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0036.ie'), 'example0036.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0036.ie'), 'example0036.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0036.ie'), 'example0036.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0037.ie'), 'example0037.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0037.ie'), 'example0037.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0037.ie'), 'example0037.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0037.ie'), 'example0037.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0038.ie'), 'example0038.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0038.ie'), 'example0038.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0038.ie'), 'example0038.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0038.ie'), 'example0038.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0039.ie'), 'example0039.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0039.ie'), 'example0039.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0039.ie'), 'example0039.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0039.ie'), 'example0039.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0040.ie'), 'example0040.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0040.ie'), 'example0040.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0040.ie'), 'example0040.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0040.ie'), 'example0040.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0041.ie'), 'example0041.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0041.ie'), 'example0041.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0041.ie'), 'example0041.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0041.ie'), 'example0041.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0042.ie'), 'example0042.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0042.ie'), 'example0042.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0042.ie'), 'example0042.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0042.ie'), 'example0042.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0043.ie'), 'example0043.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0043.ie'), 'example0043.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0043.ie'), 'example0043.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0043.ie'), 'example0043.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0044.ie'), 'example0044.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0044.ie'), 'example0044.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0044.ie'), 'example0044.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0044.ie'), 'example0044.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0045.ie'), 'example0045.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0045.ie'), 'example0045.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0045.ie'), 'example0045.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0045.ie'), 'example0045.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0046.ie'), 'example0046.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0046.ie'), 'example0046.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0046.ie'), 'example0046.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0046.ie'), 'example0046.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0047.ie'), 'example0047.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0047.ie'), 'example0047.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0047.ie'), 'example0047.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0047.ie'), 'example0047.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0048.ie'), 'example0048.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0048.ie'), 'example0048.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0048.ie'), 'example0048.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0048.ie'), 'example0048.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0049.ie'), 'example0049.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0049.ie'), 'example0049.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0049.ie'), 'example0049.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0049.ie'), 'example0049.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0050.ie'), 'example0050.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0050.ie'), 'example0050.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0050.ie'), 'example0050.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0050.ie'), 'example0050.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0051.ie'), 'example0051.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0051.ie'), 'example0051.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0051.ie'), 'example0051.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0051.ie'), 'example0051.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0052.ie'), 'example0052.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0052.ie'), 'example0052.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0052.ie'), 'example0052.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0052.ie'), 'example0052.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0053.ie'), 'example0053.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0053.ie'), 'example0053.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0053.ie'), 'example0053.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0053.ie'), 'example0053.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0054.ie'), 'example0054.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0054.ie'), 'example0054.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0054.ie'), 'example0054.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0054.ie'), 'example0054.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0055.ie'), 'example0055.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0055.ie'), 'example0055.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0055.ie'), 'example0055.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0055.ie'), 'example0055.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0056.ie'), 'example0056.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0056.ie'), 'example0056.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0056.ie'), 'example0056.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0056.ie'), 'example0056.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0057.ie'), 'example0057.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0057.ie'), 'example0057.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0057.ie'), 'example0057.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0057.ie'), 'example0057.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0058.ie'), 'example0058.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0058.ie'), 'example0058.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0058.ie'), 'example0058.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0058.ie'), 'example0058.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0059.ie'), 'example0059.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0059.ie'), 'example0059.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0059.ie'), 'example0059.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0059.ie'), 'example0059.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0060.ie'), 'example0060.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0060.ie'), 'example0060.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0060.ie'), 'example0060.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0060.ie'), 'example0060.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0061.ie'), 'example0061.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0061.ie'), 'example0061.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0061.ie'), 'example0061.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0061.ie'), 'example0061.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0062.ie'), 'example0062.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0062.ie'), 'example0062.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0062.ie'), 'example0062.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0062.ie'), 'example0062.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0063.ie'), 'example0063.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0063.ie'), 'example0063.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0063.ie'), 'example0063.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0063.ie'), 'example0063.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0064.ie'), 'example0064.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0064.ie'), 'example0064.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0064.ie'), 'example0064.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0064.ie'), 'example0064.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0065.ie'), 'example0065.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0065.ie'), 'example0065.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0065.ie'), 'example0065.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0065.ie'), 'example0065.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0066.ie'), 'example0066.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0066.ie'), 'example0066.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0066.ie'), 'example0066.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0066.ie'), 'example0066.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0067.ie'), 'example0067.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0067.ie'), 'example0067.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0067.ie'), 'example0067.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0067.ie'), 'example0067.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0068.ie'), 'example0068.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0068.ie'), 'example0068.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0068.ie'), 'example0068.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0068.ie'), 'example0068.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0069.ie'), 'example0069.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0069.ie'), 'example0069.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0069.ie'), 'example0069.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0069.ie'), 'example0069.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0070.ie'), 'example0070.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0070.ie'), 'example0070.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0070.ie'), 'example0070.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0070.ie'), 'example0070.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0071.ie'), 'example0071.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0071.ie'), 'example0071.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0071.ie'), 'example0071.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0071.ie'), 'example0071.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0072.ie'), 'example0072.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0072.ie'), 'example0072.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0072.ie'), 'example0072.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0072.ie'), 'example0072.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0073.ie'), 'example0073.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0073.ie'), 'example0073.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0073.ie'), 'example0073.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0073.ie'), 'example0073.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0074.ie'), 'example0074.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0074.ie'), 'example0074.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0074.ie'), 'example0074.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0074.ie'), 'example0074.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0075.ie'), 'example0075.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0075.ie'), 'example0075.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0075.ie'), 'example0075.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0075.ie'), 'example0075.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0076.ie'), 'example0076.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0076.ie'), 'example0076.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0076.ie'), 'example0076.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0076.ie'), 'example0076.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0077.ie'), 'example0077.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0077.ie'), 'example0077.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0077.ie'), 'example0077.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0077.ie'), 'example0077.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0078.ie'), 'example0078.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0078.ie'), 'example0078.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0078.ie'), 'example0078.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0078.ie'), 'example0078.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0079.ie'), 'example0079.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0079.ie'), 'example0079.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0079.ie'), 'example0079.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0079.ie'), 'example0079.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0080.ie'), 'example0080.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0080.ie'), 'example0080.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0080.ie'), 'example0080.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0080.ie'), 'example0080.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0081.ie'), 'example0081.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0081.ie'), 'example0081.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0081.ie'), 'example0081.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0081.ie'), 'example0081.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0082.ie'), 'example0082.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0082.ie'), 'example0082.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0082.ie'), 'example0082.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0082.ie'), 'example0082.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0083.ie'), 'example0083.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0083.ie'), 'example0083.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0083.ie'), 'example0083.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0083.ie'), 'example0083.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0084.ie'), 'example0084.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0084.ie'), 'example0084.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0084.ie'), 'example0084.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0084.ie'), 'example0084.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0085.ie'), 'example0085.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0085.ie'), 'example0085.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0085.ie'), 'example0085.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0085.ie'), 'example0085.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0086.ie'), 'example0086.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0086.ie'), 'example0086.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0086.ie'), 'example0086.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0086.ie'), 'example0086.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0087.ie'), 'example0087.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0087.ie'), 'example0087.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0087.ie'), 'example0087.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0087.ie'), 'example0087.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0088.ie'), 'example0088.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0088.ie'), 'example0088.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0088.ie'), 'example0088.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0088.ie'), 'example0088.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0089.ie'), 'example0089.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0089.ie'), 'example0089.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0089.ie'), 'example0089.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0089.ie'), 'example0089.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0090.ie'), 'example0090.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0090.ie'), 'example0090.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0090.ie'), 'example0090.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0090.ie'), 'example0090.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0091.ie'), 'example0091.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0091.ie'), 'example0091.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0091.ie'), 'example0091.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0091.ie'), 'example0091.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0092.ie'), 'example0092.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0092.ie'), 'example0092.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0092.ie'), 'example0092.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0092.ie'), 'example0092.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0093.ie'), 'example0093.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0093.ie'), 'example0093.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0093.ie'), 'example0093.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0093.ie'), 'example0093.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0094.ie'), 'example0094.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0094.ie'), 'example0094.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0094.ie'), 'example0094.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0094.ie'), 'example0094.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0095.ie'), 'example0095.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0095.ie'), 'example0095.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0095.ie'), 'example0095.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0095.ie'), 'example0095.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0096.ie'), 'example0096.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0096.ie'), 'example0096.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0096.ie'), 'example0096.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0096.ie'), 'example0096.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0097.ie'), 'example0097.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0097.ie'), 'example0097.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0097.ie'), 'example0097.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0097.ie'), 'example0097.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0098.ie'), 'example0098.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0098.ie'), 'example0098.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0098.ie'), 'example0098.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0098.ie'), 'example0098.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0099.ie'), 'example0099.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0099.ie'), 'example0099.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0099.ie'), 'example0099.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0099.ie'), 'example0099.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0100.ie'), 'example0100.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0100.ie'), 'example0100.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0100.ie'), 'example0100.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0100.ie'), 'example0100.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');

INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0001.ie', 'XOE550-IEDR', 'Admin'), ('example0001.ie', 'XBC189-IEDR', 'Billing'), ('example0001.ie', 'XBC189-IEDR', 'Creator'), ('example0001.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0002.ie', 'XOE550-IEDR', 'Admin'), ('example0002.ie', 'XBC189-IEDR', 'Billing'), ('example0002.ie', 'XBC189-IEDR', 'Creator'), ('example0002.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0003.ie', 'XOE550-IEDR', 'Admin'), ('example0003.ie', 'XBC189-IEDR', 'Billing'), ('example0003.ie', 'XBC189-IEDR', 'Creator'), ('example0003.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0004.ie', 'XOE550-IEDR', 'Admin'), ('example0004.ie', 'XBC189-IEDR', 'Billing'), ('example0004.ie', 'XBC189-IEDR', 'Creator'), ('example0004.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0005.ie', 'XOE550-IEDR', 'Admin'), ('example0005.ie', 'XBC189-IEDR', 'Billing'), ('example0005.ie', 'XBC189-IEDR', 'Creator'), ('example0005.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0006.ie', 'XOE550-IEDR', 'Admin'), ('example0006.ie', 'XBC189-IEDR', 'Billing'), ('example0006.ie', 'XBC189-IEDR', 'Creator'), ('example0006.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0007.ie', 'XOE550-IEDR', 'Admin'), ('example0007.ie', 'XBC189-IEDR', 'Billing'), ('example0007.ie', 'XBC189-IEDR', 'Creator'), ('example0007.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0008.ie', 'XOE550-IEDR', 'Admin'), ('example0008.ie', 'XBC189-IEDR', 'Billing'), ('example0008.ie', 'XBC189-IEDR', 'Creator'), ('example0008.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0009.ie', 'XOE550-IEDR', 'Admin'), ('example0009.ie', 'XBC189-IEDR', 'Billing'), ('example0009.ie', 'XBC189-IEDR', 'Creator'), ('example0009.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0010.ie', 'XOE550-IEDR', 'Admin'), ('example0010.ie', 'XBC189-IEDR', 'Billing'), ('example0010.ie', 'XBC189-IEDR', 'Creator'), ('example0010.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0011.ie', 'XOE550-IEDR', 'Admin'), ('example0011.ie', 'XBC189-IEDR', 'Billing'), ('example0011.ie', 'XBC189-IEDR', 'Creator'), ('example0011.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0012.ie', 'XOE550-IEDR', 'Admin'), ('example0012.ie', 'XBC189-IEDR', 'Billing'), ('example0012.ie', 'XBC189-IEDR', 'Creator'), ('example0012.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0013.ie', 'XOE550-IEDR', 'Admin'), ('example0013.ie', 'XBC189-IEDR', 'Billing'), ('example0013.ie', 'XBC189-IEDR', 'Creator'), ('example0013.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0014.ie', 'XOE550-IEDR', 'Admin'), ('example0014.ie', 'XBC189-IEDR', 'Billing'), ('example0014.ie', 'XBC189-IEDR', 'Creator'), ('example0014.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0015.ie', 'XOE550-IEDR', 'Admin'), ('example0015.ie', 'XBC189-IEDR', 'Billing'), ('example0015.ie', 'XBC189-IEDR', 'Creator'), ('example0015.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0016.ie', 'XOE550-IEDR', 'Admin'), ('example0016.ie', 'XBC189-IEDR', 'Billing'), ('example0016.ie', 'XBC189-IEDR', 'Creator'), ('example0016.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0017.ie', 'XOE550-IEDR', 'Admin'), ('example0017.ie', 'XBC189-IEDR', 'Billing'), ('example0017.ie', 'XBC189-IEDR', 'Creator'), ('example0017.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0018.ie', 'XOE550-IEDR', 'Admin'), ('example0018.ie', 'XBC189-IEDR', 'Billing'), ('example0018.ie', 'XBC189-IEDR', 'Creator'), ('example0018.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0019.ie', 'XOE550-IEDR', 'Admin'), ('example0019.ie', 'XBC189-IEDR', 'Billing'), ('example0019.ie', 'XBC189-IEDR', 'Creator'), ('example0019.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0020.ie', 'XOE550-IEDR', 'Admin'), ('example0020.ie', 'XBC189-IEDR', 'Billing'), ('example0020.ie', 'XBC189-IEDR', 'Creator'), ('example0020.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0021.ie', 'XOE550-IEDR', 'Admin'), ('example0021.ie', 'XBC189-IEDR', 'Billing'), ('example0021.ie', 'XBC189-IEDR', 'Creator'), ('example0021.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0022.ie', 'XOE550-IEDR', 'Admin'), ('example0022.ie', 'XBC189-IEDR', 'Billing'), ('example0022.ie', 'XBC189-IEDR', 'Creator'), ('example0022.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0023.ie', 'XOE550-IEDR', 'Admin'), ('example0023.ie', 'XBC189-IEDR', 'Billing'), ('example0023.ie', 'XBC189-IEDR', 'Creator'), ('example0023.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0024.ie', 'XOE550-IEDR', 'Admin'), ('example0024.ie', 'XBC189-IEDR', 'Billing'), ('example0024.ie', 'XBC189-IEDR', 'Creator'), ('example0024.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0025.ie', 'XOE550-IEDR', 'Admin'), ('example0025.ie', 'XBC189-IEDR', 'Billing'), ('example0025.ie', 'XBC189-IEDR', 'Creator'), ('example0025.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0026.ie', 'XOE550-IEDR', 'Admin'), ('example0026.ie', 'XBC189-IEDR', 'Billing'), ('example0026.ie', 'XBC189-IEDR', 'Creator'), ('example0026.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0027.ie', 'XOE550-IEDR', 'Admin'), ('example0027.ie', 'XBC189-IEDR', 'Billing'), ('example0027.ie', 'XBC189-IEDR', 'Creator'), ('example0027.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0028.ie', 'XOE550-IEDR', 'Admin'), ('example0028.ie', 'XBC189-IEDR', 'Billing'), ('example0028.ie', 'XBC189-IEDR', 'Creator'), ('example0028.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0029.ie', 'XOE550-IEDR', 'Admin'), ('example0029.ie', 'XBC189-IEDR', 'Billing'), ('example0029.ie', 'XBC189-IEDR', 'Creator'), ('example0029.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0030.ie', 'XOE550-IEDR', 'Admin'), ('example0030.ie', 'XBC189-IEDR', 'Billing'), ('example0030.ie', 'XBC189-IEDR', 'Creator'), ('example0030.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0031.ie', 'XOE550-IEDR', 'Admin'), ('example0031.ie', 'XBC189-IEDR', 'Billing'), ('example0031.ie', 'XBC189-IEDR', 'Creator'), ('example0031.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0032.ie', 'XOE550-IEDR', 'Admin'), ('example0032.ie', 'XBC189-IEDR', 'Billing'), ('example0032.ie', 'XBC189-IEDR', 'Creator'), ('example0032.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0033.ie', 'XOE550-IEDR', 'Admin'), ('example0033.ie', 'XBC189-IEDR', 'Billing'), ('example0033.ie', 'XBC189-IEDR', 'Creator'), ('example0033.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0034.ie', 'XOE550-IEDR', 'Admin'), ('example0034.ie', 'XBC189-IEDR', 'Billing'), ('example0034.ie', 'XBC189-IEDR', 'Creator'), ('example0034.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0035.ie', 'XOE550-IEDR', 'Admin'), ('example0035.ie', 'XBC189-IEDR', 'Billing'), ('example0035.ie', 'XBC189-IEDR', 'Creator'), ('example0035.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0036.ie', 'XOE550-IEDR', 'Admin'), ('example0036.ie', 'XBC189-IEDR', 'Billing'), ('example0036.ie', 'XBC189-IEDR', 'Creator'), ('example0036.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0037.ie', 'XOE550-IEDR', 'Admin'), ('example0037.ie', 'XBC189-IEDR', 'Billing'), ('example0037.ie', 'XBC189-IEDR', 'Creator'), ('example0037.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0038.ie', 'XOE550-IEDR', 'Admin'), ('example0038.ie', 'XBC189-IEDR', 'Billing'), ('example0038.ie', 'XBC189-IEDR', 'Creator'), ('example0038.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0039.ie', 'XOE550-IEDR', 'Admin'), ('example0039.ie', 'XBC189-IEDR', 'Billing'), ('example0039.ie', 'XBC189-IEDR', 'Creator'), ('example0039.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0040.ie', 'XOE550-IEDR', 'Admin'), ('example0040.ie', 'XBC189-IEDR', 'Billing'), ('example0040.ie', 'XBC189-IEDR', 'Creator'), ('example0040.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0041.ie', 'XOE550-IEDR', 'Admin'), ('example0041.ie', 'XBC189-IEDR', 'Billing'), ('example0041.ie', 'XBC189-IEDR', 'Creator'), ('example0041.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0042.ie', 'XOE550-IEDR', 'Admin'), ('example0042.ie', 'XBC189-IEDR', 'Billing'), ('example0042.ie', 'XBC189-IEDR', 'Creator'), ('example0042.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0043.ie', 'XOE550-IEDR', 'Admin'), ('example0043.ie', 'XBC189-IEDR', 'Billing'), ('example0043.ie', 'XBC189-IEDR', 'Creator'), ('example0043.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0044.ie', 'XOE550-IEDR', 'Admin'), ('example0044.ie', 'XBC189-IEDR', 'Billing'), ('example0044.ie', 'XBC189-IEDR', 'Creator'), ('example0044.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0045.ie', 'XOE550-IEDR', 'Admin'), ('example0045.ie', 'XBC189-IEDR', 'Billing'), ('example0045.ie', 'XBC189-IEDR', 'Creator'), ('example0045.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0046.ie', 'XOE550-IEDR', 'Admin'), ('example0046.ie', 'XBC189-IEDR', 'Billing'), ('example0046.ie', 'XBC189-IEDR', 'Creator'), ('example0046.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0047.ie', 'XOE550-IEDR', 'Admin'), ('example0047.ie', 'XBC189-IEDR', 'Billing'), ('example0047.ie', 'XBC189-IEDR', 'Creator'), ('example0047.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0048.ie', 'XOE550-IEDR', 'Admin'), ('example0048.ie', 'XBC189-IEDR', 'Billing'), ('example0048.ie', 'XBC189-IEDR', 'Creator'), ('example0048.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0049.ie', 'XOE550-IEDR', 'Admin'), ('example0049.ie', 'XBC189-IEDR', 'Billing'), ('example0049.ie', 'XBC189-IEDR', 'Creator'), ('example0049.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0050.ie', 'XOE550-IEDR', 'Admin'), ('example0050.ie', 'XBC189-IEDR', 'Billing'), ('example0050.ie', 'XBC189-IEDR', 'Creator'), ('example0050.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0051.ie', 'XOE550-IEDR', 'Admin'), ('example0051.ie', 'XBC189-IEDR', 'Billing'), ('example0051.ie', 'XBC189-IEDR', 'Creator'), ('example0051.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0052.ie', 'XOE550-IEDR', 'Admin'), ('example0052.ie', 'XBC189-IEDR', 'Billing'), ('example0052.ie', 'XBC189-IEDR', 'Creator'), ('example0052.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0053.ie', 'XOE550-IEDR', 'Admin'), ('example0053.ie', 'XBC189-IEDR', 'Billing'), ('example0053.ie', 'XBC189-IEDR', 'Creator'), ('example0053.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0054.ie', 'XOE550-IEDR', 'Admin'), ('example0054.ie', 'XBC189-IEDR', 'Billing'), ('example0054.ie', 'XBC189-IEDR', 'Creator'), ('example0054.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0055.ie', 'XOE550-IEDR', 'Admin'), ('example0055.ie', 'XBC189-IEDR', 'Billing'), ('example0055.ie', 'XBC189-IEDR', 'Creator'), ('example0055.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0056.ie', 'XOE550-IEDR', 'Admin'), ('example0056.ie', 'XBC189-IEDR', 'Billing'), ('example0056.ie', 'XBC189-IEDR', 'Creator'), ('example0056.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0057.ie', 'XOE550-IEDR', 'Admin'), ('example0057.ie', 'XBC189-IEDR', 'Billing'), ('example0057.ie', 'XBC189-IEDR', 'Creator'), ('example0057.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0058.ie', 'XOE550-IEDR', 'Admin'), ('example0058.ie', 'XBC189-IEDR', 'Billing'), ('example0058.ie', 'XBC189-IEDR', 'Creator'), ('example0058.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0059.ie', 'XOE550-IEDR', 'Admin'), ('example0059.ie', 'XBC189-IEDR', 'Billing'), ('example0059.ie', 'XBC189-IEDR', 'Creator'), ('example0059.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0060.ie', 'XOE550-IEDR', 'Admin'), ('example0060.ie', 'XBC189-IEDR', 'Billing'), ('example0060.ie', 'XBC189-IEDR', 'Creator'), ('example0060.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0061.ie', 'XOE550-IEDR', 'Admin'), ('example0061.ie', 'XBC189-IEDR', 'Billing'), ('example0061.ie', 'XBC189-IEDR', 'Creator'), ('example0061.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0062.ie', 'XOE550-IEDR', 'Admin'), ('example0062.ie', 'XBC189-IEDR', 'Billing'), ('example0062.ie', 'XBC189-IEDR', 'Creator'), ('example0062.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0063.ie', 'XOE550-IEDR', 'Admin'), ('example0063.ie', 'XBC189-IEDR', 'Billing'), ('example0063.ie', 'XBC189-IEDR', 'Creator'), ('example0063.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0064.ie', 'XOE550-IEDR', 'Admin'), ('example0064.ie', 'XBC189-IEDR', 'Billing'), ('example0064.ie', 'XBC189-IEDR', 'Creator'), ('example0064.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0065.ie', 'XOE550-IEDR', 'Admin'), ('example0065.ie', 'XBC189-IEDR', 'Billing'), ('example0065.ie', 'XBC189-IEDR', 'Creator'), ('example0065.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0066.ie', 'XOE550-IEDR', 'Admin'), ('example0066.ie', 'XBC189-IEDR', 'Billing'), ('example0066.ie', 'XBC189-IEDR', 'Creator'), ('example0066.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0067.ie', 'XOE550-IEDR', 'Admin'), ('example0067.ie', 'XBC189-IEDR', 'Billing'), ('example0067.ie', 'XBC189-IEDR', 'Creator'), ('example0067.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0068.ie', 'XOE550-IEDR', 'Admin'), ('example0068.ie', 'XBC189-IEDR', 'Billing'), ('example0068.ie', 'XBC189-IEDR', 'Creator'), ('example0068.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0069.ie', 'XOE550-IEDR', 'Admin'), ('example0069.ie', 'XBC189-IEDR', 'Billing'), ('example0069.ie', 'XBC189-IEDR', 'Creator'), ('example0069.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0070.ie', 'XOE550-IEDR', 'Admin'), ('example0070.ie', 'XBC189-IEDR', 'Billing'), ('example0070.ie', 'XBC189-IEDR', 'Creator'), ('example0070.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0071.ie', 'XOE550-IEDR', 'Admin'), ('example0071.ie', 'XBC189-IEDR', 'Billing'), ('example0071.ie', 'XBC189-IEDR', 'Creator'), ('example0071.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0072.ie', 'XOE550-IEDR', 'Admin'), ('example0072.ie', 'XBC189-IEDR', 'Billing'), ('example0072.ie', 'XBC189-IEDR', 'Creator'), ('example0072.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0073.ie', 'XOE550-IEDR', 'Admin'), ('example0073.ie', 'XBC189-IEDR', 'Billing'), ('example0073.ie', 'XBC189-IEDR', 'Creator'), ('example0073.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0074.ie', 'XOE550-IEDR', 'Admin'), ('example0074.ie', 'XBC189-IEDR', 'Billing'), ('example0074.ie', 'XBC189-IEDR', 'Creator'), ('example0074.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0075.ie', 'XOE550-IEDR', 'Admin'), ('example0075.ie', 'XBC189-IEDR', 'Billing'), ('example0075.ie', 'XBC189-IEDR', 'Creator'), ('example0075.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0076.ie', 'XOE550-IEDR', 'Admin'), ('example0076.ie', 'XBC189-IEDR', 'Billing'), ('example0076.ie', 'XBC189-IEDR', 'Creator'), ('example0076.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0077.ie', 'XOE550-IEDR', 'Admin'), ('example0077.ie', 'XBC189-IEDR', 'Billing'), ('example0077.ie', 'XBC189-IEDR', 'Creator'), ('example0077.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0078.ie', 'XOE550-IEDR', 'Admin'), ('example0078.ie', 'XBC189-IEDR', 'Billing'), ('example0078.ie', 'XBC189-IEDR', 'Creator'), ('example0078.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0079.ie', 'XOE550-IEDR', 'Admin'), ('example0079.ie', 'XBC189-IEDR', 'Billing'), ('example0079.ie', 'XBC189-IEDR', 'Creator'), ('example0079.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0080.ie', 'XOE550-IEDR', 'Admin'), ('example0080.ie', 'XBC189-IEDR', 'Billing'), ('example0080.ie', 'XBC189-IEDR', 'Creator'), ('example0080.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0081.ie', 'XOE550-IEDR', 'Admin'), ('example0081.ie', 'XBC189-IEDR', 'Billing'), ('example0081.ie', 'XBC189-IEDR', 'Creator'), ('example0081.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0082.ie', 'XOE550-IEDR', 'Admin'), ('example0082.ie', 'XBC189-IEDR', 'Billing'), ('example0082.ie', 'XBC189-IEDR', 'Creator'), ('example0082.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0083.ie', 'XOE550-IEDR', 'Admin'), ('example0083.ie', 'XBC189-IEDR', 'Billing'), ('example0083.ie', 'XBC189-IEDR', 'Creator'), ('example0083.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0084.ie', 'XOE550-IEDR', 'Admin'), ('example0084.ie', 'XBC189-IEDR', 'Billing'), ('example0084.ie', 'XBC189-IEDR', 'Creator'), ('example0084.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0085.ie', 'XOE550-IEDR', 'Admin'), ('example0085.ie', 'XBC189-IEDR', 'Billing'), ('example0085.ie', 'XBC189-IEDR', 'Creator'), ('example0085.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0086.ie', 'XOE550-IEDR', 'Admin'), ('example0086.ie', 'XBC189-IEDR', 'Billing'), ('example0086.ie', 'XBC189-IEDR', 'Creator'), ('example0086.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0087.ie', 'XOE550-IEDR', 'Admin'), ('example0087.ie', 'XBC189-IEDR', 'Billing'), ('example0087.ie', 'XBC189-IEDR', 'Creator'), ('example0087.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0088.ie', 'XOE550-IEDR', 'Admin'), ('example0088.ie', 'XBC189-IEDR', 'Billing'), ('example0088.ie', 'XBC189-IEDR', 'Creator'), ('example0088.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0089.ie', 'XOE550-IEDR', 'Admin'), ('example0089.ie', 'XBC189-IEDR', 'Billing'), ('example0089.ie', 'XBC189-IEDR', 'Creator'), ('example0089.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0090.ie', 'XOE550-IEDR', 'Admin'), ('example0090.ie', 'XBC189-IEDR', 'Billing'), ('example0090.ie', 'XBC189-IEDR', 'Creator'), ('example0090.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0091.ie', 'XOE550-IEDR', 'Admin'), ('example0091.ie', 'XBC189-IEDR', 'Billing'), ('example0091.ie', 'XBC189-IEDR', 'Creator'), ('example0091.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0092.ie', 'XOE550-IEDR', 'Admin'), ('example0092.ie', 'XBC189-IEDR', 'Billing'), ('example0092.ie', 'XBC189-IEDR', 'Creator'), ('example0092.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0093.ie', 'XOE550-IEDR', 'Admin'), ('example0093.ie', 'XBC189-IEDR', 'Billing'), ('example0093.ie', 'XBC189-IEDR', 'Creator'), ('example0093.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0094.ie', 'XOE550-IEDR', 'Admin'), ('example0094.ie', 'XBC189-IEDR', 'Billing'), ('example0094.ie', 'XBC189-IEDR', 'Creator'), ('example0094.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0095.ie', 'XOE550-IEDR', 'Admin'), ('example0095.ie', 'XBC189-IEDR', 'Billing'), ('example0095.ie', 'XBC189-IEDR', 'Creator'), ('example0095.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0096.ie', 'XOE550-IEDR', 'Admin'), ('example0096.ie', 'XBC189-IEDR', 'Billing'), ('example0096.ie', 'XBC189-IEDR', 'Creator'), ('example0096.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0097.ie', 'XOE550-IEDR', 'Admin'), ('example0097.ie', 'XBC189-IEDR', 'Billing'), ('example0097.ie', 'XBC189-IEDR', 'Creator'), ('example0097.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0098.ie', 'XOE550-IEDR', 'Admin'), ('example0098.ie', 'XBC189-IEDR', 'Billing'), ('example0098.ie', 'XBC189-IEDR', 'Creator'), ('example0098.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0099.ie', 'XOE550-IEDR', 'Admin'), ('example0099.ie', 'XBC189-IEDR', 'Billing'), ('example0099.ie', 'XBC189-IEDR', 'Creator'), ('example0099.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO Contact(D_Name, Contact_NH, Type) VALUES ('example0100.ie', 'XOE550-IEDR', 'Admin'), ('example0100.ie', 'XBC189-IEDR', 'Billing'), ('example0100.ie', 'XBC189-IEDR', 'Creator'), ('example0100.ie', 'XBC189-IEDR', 'Tech');

INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0001.ie'), 'example0001.ie', 'ns1.example0001.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0001.ie'), 'example0001.ie', 'ns2.example0001.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0001.ie'), 'example0001.ie', 'ns3.example0001.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0002.ie'), 'example0002.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0002.ie'), 'example0002.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0002.ie'), 'example0002.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0003.ie'), 'example0003.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0003.ie'), 'example0003.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0003.ie'), 'example0003.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0004.ie'), 'example0004.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0004.ie'), 'example0004.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0004.ie'), 'example0004.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0005.ie'), 'example0005.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0005.ie'), 'example0005.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0005.ie'), 'example0005.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0006.ie'), 'example0006.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0006.ie'), 'example0006.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0006.ie'), 'example0006.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0007.ie'), 'example0007.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0007.ie'), 'example0007.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0007.ie'), 'example0007.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0008.ie'), 'example0008.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0008.ie'), 'example0008.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0008.ie'), 'example0008.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0009.ie'), 'example0009.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0009.ie'), 'example0009.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0009.ie'), 'example0009.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0010.ie'), 'example0010.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0010.ie'), 'example0010.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0010.ie'), 'example0010.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0011.ie'), 'example0011.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0011.ie'), 'example0011.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0011.ie'), 'example0011.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0012.ie'), 'example0012.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0012.ie'), 'example0012.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0012.ie'), 'example0012.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0013.ie'), 'example0013.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0013.ie'), 'example0013.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0013.ie'), 'example0013.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0014.ie'), 'example0014.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0014.ie'), 'example0014.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0014.ie'), 'example0014.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0015.ie'), 'example0015.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0015.ie'), 'example0015.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0015.ie'), 'example0015.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0016.ie'), 'example0016.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0016.ie'), 'example0016.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0016.ie'), 'example0016.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0017.ie'), 'example0017.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0017.ie'), 'example0017.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0017.ie'), 'example0017.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0018.ie'), 'example0018.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0018.ie'), 'example0018.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0018.ie'), 'example0018.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0019.ie'), 'example0019.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0019.ie'), 'example0019.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0019.ie'), 'example0019.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0020.ie'), 'example0020.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0020.ie'), 'example0020.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0020.ie'), 'example0020.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0021.ie'), 'example0021.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0021.ie'), 'example0021.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0021.ie'), 'example0021.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0022.ie'), 'example0022.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0022.ie'), 'example0022.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0022.ie'), 'example0022.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0023.ie'), 'example0023.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0023.ie'), 'example0023.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0023.ie'), 'example0023.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0024.ie'), 'example0024.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0024.ie'), 'example0024.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0024.ie'), 'example0024.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0025.ie'), 'example0025.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0025.ie'), 'example0025.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0025.ie'), 'example0025.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0026.ie'), 'example0026.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0026.ie'), 'example0026.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0026.ie'), 'example0026.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0027.ie'), 'example0027.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0027.ie'), 'example0027.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0027.ie'), 'example0027.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0028.ie'), 'example0028.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0028.ie'), 'example0028.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0028.ie'), 'example0028.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0029.ie'), 'example0029.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0029.ie'), 'example0029.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0029.ie'), 'example0029.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0030.ie'), 'example0030.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0030.ie'), 'example0030.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0030.ie'), 'example0030.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0031.ie'), 'example0031.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0031.ie'), 'example0031.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0031.ie'), 'example0031.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0032.ie'), 'example0032.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0032.ie'), 'example0032.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0032.ie'), 'example0032.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0033.ie'), 'example0033.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0033.ie'), 'example0033.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0033.ie'), 'example0033.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0034.ie'), 'example0034.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0034.ie'), 'example0034.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0034.ie'), 'example0034.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0035.ie'), 'example0035.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0035.ie'), 'example0035.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0035.ie'), 'example0035.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0036.ie'), 'example0036.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0036.ie'), 'example0036.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0036.ie'), 'example0036.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0037.ie'), 'example0037.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0037.ie'), 'example0037.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0037.ie'), 'example0037.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0038.ie'), 'example0038.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0038.ie'), 'example0038.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0038.ie'), 'example0038.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0039.ie'), 'example0039.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0039.ie'), 'example0039.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0039.ie'), 'example0039.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0040.ie'), 'example0040.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0040.ie'), 'example0040.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0040.ie'), 'example0040.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0041.ie'), 'example0041.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0041.ie'), 'example0041.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0041.ie'), 'example0041.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0042.ie'), 'example0042.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0042.ie'), 'example0042.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0042.ie'), 'example0042.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0043.ie'), 'example0043.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0043.ie'), 'example0043.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0043.ie'), 'example0043.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0044.ie'), 'example0044.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0044.ie'), 'example0044.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0044.ie'), 'example0044.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0045.ie'), 'example0045.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0045.ie'), 'example0045.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0045.ie'), 'example0045.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0046.ie'), 'example0046.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0046.ie'), 'example0046.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0046.ie'), 'example0046.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0047.ie'), 'example0047.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0047.ie'), 'example0047.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0047.ie'), 'example0047.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0048.ie'), 'example0048.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0048.ie'), 'example0048.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0048.ie'), 'example0048.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0049.ie'), 'example0049.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0049.ie'), 'example0049.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0049.ie'), 'example0049.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0050.ie'), 'example0050.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0050.ie'), 'example0050.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0050.ie'), 'example0050.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0051.ie'), 'example0051.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0051.ie'), 'example0051.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0051.ie'), 'example0051.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0052.ie'), 'example0052.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0052.ie'), 'example0052.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0052.ie'), 'example0052.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0053.ie'), 'example0053.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0053.ie'), 'example0053.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0053.ie'), 'example0053.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0054.ie'), 'example0054.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0054.ie'), 'example0054.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0054.ie'), 'example0054.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0055.ie'), 'example0055.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0055.ie'), 'example0055.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0055.ie'), 'example0055.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0056.ie'), 'example0056.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0056.ie'), 'example0056.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0056.ie'), 'example0056.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0057.ie'), 'example0057.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0057.ie'), 'example0057.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0057.ie'), 'example0057.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0058.ie'), 'example0058.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0058.ie'), 'example0058.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0058.ie'), 'example0058.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0059.ie'), 'example0059.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0059.ie'), 'example0059.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0059.ie'), 'example0059.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0060.ie'), 'example0060.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0060.ie'), 'example0060.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0060.ie'), 'example0060.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0061.ie'), 'example0061.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0061.ie'), 'example0061.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0061.ie'), 'example0061.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0062.ie'), 'example0062.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0062.ie'), 'example0062.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0062.ie'), 'example0062.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0063.ie'), 'example0063.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0063.ie'), 'example0063.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0063.ie'), 'example0063.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0064.ie'), 'example0064.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0064.ie'), 'example0064.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0064.ie'), 'example0064.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0065.ie'), 'example0065.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0065.ie'), 'example0065.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0065.ie'), 'example0065.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0066.ie'), 'example0066.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0066.ie'), 'example0066.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0066.ie'), 'example0066.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0067.ie'), 'example0067.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0067.ie'), 'example0067.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0067.ie'), 'example0067.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0068.ie'), 'example0068.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0068.ie'), 'example0068.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0068.ie'), 'example0068.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0069.ie'), 'example0069.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0069.ie'), 'example0069.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0069.ie'), 'example0069.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0070.ie'), 'example0070.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0070.ie'), 'example0070.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0070.ie'), 'example0070.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0071.ie'), 'example0071.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0071.ie'), 'example0071.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0071.ie'), 'example0071.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0072.ie'), 'example0072.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0072.ie'), 'example0072.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0072.ie'), 'example0072.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0073.ie'), 'example0073.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0073.ie'), 'example0073.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0073.ie'), 'example0073.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0074.ie'), 'example0074.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0074.ie'), 'example0074.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0074.ie'), 'example0074.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0075.ie'), 'example0075.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0075.ie'), 'example0075.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0075.ie'), 'example0075.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0076.ie'), 'example0076.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0076.ie'), 'example0076.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0076.ie'), 'example0076.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0077.ie'), 'example0077.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0077.ie'), 'example0077.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0077.ie'), 'example0077.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0078.ie'), 'example0078.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0078.ie'), 'example0078.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0078.ie'), 'example0078.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0079.ie'), 'example0079.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0079.ie'), 'example0079.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0079.ie'), 'example0079.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0080.ie'), 'example0080.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0080.ie'), 'example0080.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0080.ie'), 'example0080.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0081.ie'), 'example0081.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0081.ie'), 'example0081.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0081.ie'), 'example0081.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0082.ie'), 'example0082.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0082.ie'), 'example0082.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0082.ie'), 'example0082.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0083.ie'), 'example0083.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0083.ie'), 'example0083.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0083.ie'), 'example0083.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0084.ie'), 'example0084.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0084.ie'), 'example0084.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0084.ie'), 'example0084.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0085.ie'), 'example0085.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0085.ie'), 'example0085.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0085.ie'), 'example0085.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0086.ie'), 'example0086.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0086.ie'), 'example0086.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0086.ie'), 'example0086.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0087.ie'), 'example0087.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0087.ie'), 'example0087.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0087.ie'), 'example0087.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0088.ie'), 'example0088.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0088.ie'), 'example0088.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0088.ie'), 'example0088.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0089.ie'), 'example0089.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0089.ie'), 'example0089.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0089.ie'), 'example0089.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0090.ie'), 'example0090.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0090.ie'), 'example0090.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0090.ie'), 'example0090.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0091.ie'), 'example0091.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0091.ie'), 'example0091.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0091.ie'), 'example0091.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0092.ie'), 'example0092.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0092.ie'), 'example0092.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0092.ie'), 'example0092.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0093.ie'), 'example0093.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0093.ie'), 'example0093.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0093.ie'), 'example0093.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0094.ie'), 'example0094.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0094.ie'), 'example0094.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0094.ie'), 'example0094.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0095.ie'), 'example0095.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0095.ie'), 'example0095.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0095.ie'), 'example0095.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0096.ie'), 'example0096.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0096.ie'), 'example0096.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0096.ie'), 'example0096.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0097.ie'), 'example0097.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0097.ie'), 'example0097.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0097.ie'), 'example0097.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0098.ie'), 'example0098.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0098.ie'), 'example0098.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0098.ie'), 'example0098.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0099.ie'), 'example0099.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0099.ie'), 'example0099.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0099.ie'), 'example0099.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0100.ie'), 'example0100.ie', 'ns1.abc1.ie', NULL, NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0100.ie'), 'example0100.ie', 'ns2.abc1.ie', NULL, NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'example0100.ie'), 'example0100.ie', 'ns3.abc1.ie', NULL, NULL, '3');

INSERT INTO DNS VALUES ('example0001.ie', 'ns1.example0001.ie', '10.10.121.23', NULL, '1'), ('example0001.ie', 'ns2.example0001.ie', '1.3.4.2', NULL, '2'), ('example0001.ie', 'ns3.example0001.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('example0002.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0002.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0002.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0003.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0003.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0003.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0004.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0004.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0004.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0005.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0005.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0005.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0006.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0006.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0006.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0007.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0007.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0007.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0008.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0008.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0008.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0009.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0009.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0009.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0010.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0010.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0010.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0011.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0011.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0011.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0012.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0012.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0012.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0013.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0013.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0013.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0014.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0014.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0014.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0015.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0015.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0015.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0016.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0016.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0016.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0017.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0017.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0017.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0018.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0018.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0018.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0019.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0019.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0019.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0020.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0020.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0020.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0021.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0021.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0021.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0022.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0022.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0022.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0023.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0023.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0023.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0024.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0024.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0024.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0025.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0025.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0025.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0026.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0026.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0026.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0027.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0027.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0027.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0028.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0028.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0028.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0029.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0029.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0029.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0030.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0030.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0030.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0031.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0031.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0031.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0032.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0032.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0032.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0033.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0033.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0033.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0034.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0034.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0034.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0035.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0035.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0035.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0036.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0036.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0036.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0037.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0037.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0037.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0038.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0038.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0038.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0039.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0039.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0039.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0040.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0040.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0040.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0041.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0041.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0041.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0042.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0042.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0042.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0043.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0043.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0043.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0044.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0044.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0044.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0045.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0045.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0045.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0046.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0046.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0046.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0047.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0047.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0047.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0048.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0048.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0048.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0049.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0049.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0049.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0050.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0050.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0050.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0051.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0051.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0051.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0052.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0052.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0052.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0053.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0053.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0053.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0054.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0054.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0054.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0055.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0055.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0055.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0056.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0056.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0056.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0057.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0057.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0057.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0058.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0058.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0058.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0059.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0059.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0059.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0060.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0060.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0060.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0061.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0061.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0061.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0062.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0062.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0062.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0063.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0063.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0063.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0064.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0064.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0064.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0065.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0065.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0065.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0066.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0066.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0066.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0067.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0067.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0067.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0068.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0068.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0068.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0069.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0069.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0069.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0070.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0070.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0070.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0071.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0071.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0071.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0072.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0072.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0072.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0073.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0073.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0073.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0074.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0074.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0074.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0075.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0075.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0075.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0076.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0076.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0076.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0077.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0077.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0077.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0078.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0078.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0078.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0079.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0079.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0079.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0080.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0080.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0080.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0081.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0081.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0081.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0082.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0082.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0082.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0083.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0083.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0083.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0084.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0084.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0084.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0085.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0085.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0085.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0086.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0086.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0086.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0087.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0087.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0087.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0088.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0088.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0088.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0089.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0089.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0089.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0090.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0090.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0090.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0091.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0091.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0091.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0092.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0092.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0092.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0093.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0093.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0093.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0094.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0094.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0094.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0095.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0095.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0095.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0096.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0096.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0096.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0097.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0097.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0097.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0098.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0098.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0098.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0099.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0099.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0099.ie', 'ns3.abc1.ie', NULL, NULL, '3');
INSERT INTO DNS VALUES ('example0100.ie', 'ns1.abc1.ie', NULL, NULL, '1'), ('example0100.ie', 'ns2.abc1.ie', NULL, NULL, '2'), ('example0100.ie', 'ns3.abc1.ie', NULL, NULL, '3');

