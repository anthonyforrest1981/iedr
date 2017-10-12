-- CRS-677

INSERT INTO Domain VALUES ('z.ie', 'Test Holder 0001', 'Sole Trader', 'Personal Trading Name', '00000666','Active', CURDATE(), '2014-04-17', CURDATE(), NOW(), 'N', 'Y', '','Test Remark', 'N', '17', null, null, null, null, 'authcode', CURDATE() + Interval 1 Day, 0);
INSERT INTO Contact VALUES ('z.ie','APIT1-IEDR', 'A'), ('z.ie','APITEST-IEDR', 'B'), ('z.ie','APITEST-IEDR', 'C'), ('z.ie','APIT1-IEDR', 'T');
INSERT INTO DNS VALUES ('z.ie', 'ns1.z.ie', '10.10.121.23', NULL, '1'), ('z.ie', 'ns2.z.ie', '1.3.4.2', NULL, '2'), ('z.ie', 'ns3.z.ie', '10.12.13.14', NULL, '3');
