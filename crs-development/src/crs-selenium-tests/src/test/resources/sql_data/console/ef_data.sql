INSERT INTO DomainHist VALUES (NULL, 'ef-nosc01.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 603), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XNV498-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('ef-nosc01.ie', 'Test Holder 0001', 3, 2, NULL, 603, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc01.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc01.ie'), 'ef-nosc01.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc01.ie'), 'ef-nosc01.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc01.ie'), 'ef-nosc01.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc01.ie'), 'ef-nosc01.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('ef-nosc01.ie', 'XOE550-IEDR', 'Admin'), ('ef-nosc01.ie', 'XNV498-IEDR', 'Billing'), ('ef-nosc01.ie', 'XNV498-IEDR', 'Creator'), ('ef-nosc01.ie', 'XNV498-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc01.ie'), 'ef-nosc01.ie', 'ns1.ef-nosc01.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc01.ie'), 'ef-nosc01.ie', 'ns2.ef-nosc01.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc01.ie'), 'ef-nosc01.ie', 'ns3.ef-nosc01.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('ef-nosc01.ie', 'ns1.ef-nosc01.ie', '10.10.121.23', NULL, '1'), ('ef-nosc01.ie', 'ns2.ef-nosc01.ie', '1.3.4.2', NULL, '2'), ('ef-nosc01.ie', 'ns3.ef-nosc01.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'ef-nosc02.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XNV498-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('ef-nosc02.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc02.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc02.ie'), 'ef-nosc02.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc02.ie'), 'ef-nosc02.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc02.ie'), 'ef-nosc02.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc02.ie'), 'ef-nosc02.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('ef-nosc02.ie', 'XOE550-IEDR', 'Admin'), ('ef-nosc02.ie', 'XBC189-IEDR', 'Billing'), ('ef-nosc02.ie', 'XBC189-IEDR', 'Creator'), ('ef-nosc02.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc02.ie'), 'ef-nosc02.ie', 'ns1.ef-nosc02.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc02.ie'), 'ef-nosc02.ie', 'ns2.ef-nosc02.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc02.ie'), 'ef-nosc02.ie', 'ns3.ef-nosc02.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('ef-nosc02.ie', 'ns1.ef-nosc02.ie', '10.10.121.23', NULL, '1'), ('ef-nosc02.ie', 'ns2.ef-nosc02.ie', '1.3.4.2', NULL, '2'), ('ef-nosc02.ie', 'ns3.ef-nosc02.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'ef-nosc03.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XNV498-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('ef-nosc03.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc03.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc03.ie'), 'ef-nosc03.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc03.ie'), 'ef-nosc03.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc03.ie'), 'ef-nosc03.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc03.ie'), 'ef-nosc03.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('ef-nosc03.ie', 'XOE550-IEDR', 'Admin'), ('ef-nosc03.ie', 'XBC189-IEDR', 'Billing'), ('ef-nosc03.ie', 'XBC189-IEDR', 'Creator'), ('ef-nosc03.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc03.ie'), 'ef-nosc03.ie', 'ns1.ef-nosc03.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc03.ie'), 'ef-nosc03.ie', 'ns2.ef-nosc03.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc03.ie'), 'ef-nosc03.ie', 'ns3.ef-nosc03.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('ef-nosc03.ie', 'ns1.ef-nosc03.ie', '10.10.121.23', NULL, '1'), ('ef-nosc03.ie', 'ns2.ef-nosc03.ie', '1.3.4.2', NULL, '2'), ('ef-nosc03.ie', 'ns3.ef-nosc03.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'ef-nosc05.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XNV498-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('ef-nosc05.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc05.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc05.ie'), 'ef-nosc05.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc05.ie'), 'ef-nosc05.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc05.ie'), 'ef-nosc05.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc05.ie'), 'ef-nosc05.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('ef-nosc05.ie', 'XOE550-IEDR', 'Admin'), ('ef-nosc05.ie', 'XBC189-IEDR', 'Billing'), ('ef-nosc05.ie', 'XBC189-IEDR', 'Creator'), ('ef-nosc05.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc05.ie'), 'ef-nosc05.ie', 'ns1.ef-nosc05.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc05.ie'), 'ef-nosc05.ie', 'ns2.ef-nosc05.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc05.ie'), 'ef-nosc05.ie', 'ns3.ef-nosc05.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('ef-nosc05.ie', 'ns1.ef-nosc05.ie', '10.10.121.23', NULL, '1'), ('ef-nosc05.ie', 'ns2.ef-nosc05.ie', '1.3.4.2', NULL, '2'), ('ef-nosc05.ie', 'ns3.ef-nosc05.ie', '10.12.13.14', NULL, '3');

DROP PROCEDURE IF EXISTS insertbigdata;
DELIMITER $$
CREATE PROCEDURE insertbigdata()
BEGIN
    DECLARE x INT;
    DECLARE domain VARCHAR(66);
    DECLARE old_hist_id INT(10);
    DECLARE new_hist_id INT(10);
    SET x = 0;
    WHILE x < 250 DO
        # Current renewals
        SET domain = CONCAT('ef-nosc08-a', '-', x, '.ie');
        INSERT INTO DomainHist VALUES (NULL, domain, 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE() - Interval 1 Day, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
        INSERT INTO Domain VALUES (domain, 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE() - Interval 1 Day, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = domain));
        INSERT INTO ContactHist VALUES
        ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = domain), domain, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
        ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = domain), domain, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
        ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = domain), domain, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
        ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = domain), domain, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
        INSERT INTO Contact VALUES (domain,'XOE550-IEDR', 'Admin'), (domain,'XBC189-IEDR', 'Billing'), (domain,'XBC189-IEDR', 'Creator'), (domain,'XBC189-IEDR', 'Tech');
        INSERT INTO DNSHist VALUES
        ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = domain), domain, 'ns1.dns.ie', NULL, NULL, '1'),
        ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = domain), domain, 'ns2.dns.ie', NULL, NULL, '2'),
        ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = domain), domain, 'ns3.dns.ie', NULL, NULL, '3');
        INSERT INTO DNS VALUES (domain, 'ns1.dns.ie', NULL, NULL, '1'), (domain, 'ns2.dns.ie', NULL, NULL, '2'), (domain, 'ns3.dns.ie', NULL, NULL, '3');

        # Future renewals, Newly registered
        SET domain = CONCAT('ef-nosc08-b', '-', x, '.ie');
        INSERT INTO DomainHist VALUES (NULL, domain, 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE(), CURDATE() + Interval 13 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
        INSERT INTO Domain VALUES (domain, 'Test Holder 0001', 3, 2, NULL, 600, CURDATE(), CURDATE() + Interval 13 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = domain));
        INSERT INTO ContactHist VALUES
        ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = domain), domain, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
        ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = domain), domain, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
        ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = domain), domain, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
        ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = domain), domain, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
        INSERT INTO Contact VALUES (domain,'XOE550-IEDR', 'Admin'), (domain,'XBC189-IEDR', 'Billing'), (domain,'XBC189-IEDR', 'Creator'), (domain,'XBC189-IEDR', 'Tech');
        INSERT INTO DNSHist VALUES
        ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = domain), domain, 'ns1.dns.ie', NULL, NULL, '1'),
        ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = domain), domain, 'ns2.dns.ie', NULL, NULL, '2'),
        ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = domain), domain, 'ns3.dns.ie', NULL, NULL, '3');
        INSERT INTO DNS VALUES (domain, 'ns1.dns.ie', NULL, NULL, '1'), (domain, 'ns2.dns.ie', NULL, NULL, '2'), (domain, 'ns3.dns.ie', NULL, NULL, '3');

        # Autorenew domains
        SET domain = CONCAT('ef-nosc08-c', '-', x, '.ie');
        INSERT INTO DomainHist VALUES (NULL, domain, 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE(), CURDATE() + Interval 12 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '81', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
        INSERT INTO Domain VALUES (domain, 'Test Holder 0001', 3, 2, NULL, 600, CURDATE(), CURDATE() + Interval 12 Month, NOW(), 'Test Remark', 'NO', '81', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = domain));
        INSERT INTO ContactHist VALUES
        ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = domain), domain, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
        ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = domain), domain, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
        ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = domain), domain, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
        ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = domain), domain, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
        INSERT INTO Contact VALUES (domain,'XOE550-IEDR', 'Admin'), (domain,'XBC189-IEDR', 'Billing'), (domain,'XBC189-IEDR', 'Creator'), (domain,'XBC189-IEDR', 'Tech');
        INSERT INTO DNSHist VALUES
        ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = domain), domain, 'ns1.dns.ie', NULL, NULL, '1'),
        ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = domain), domain, 'ns2.dns.ie', NULL, NULL, '2'),
        ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = domain), domain, 'ns3.dns.ie', NULL, NULL, '3');
        INSERT INTO DNS VALUES (domain, 'ns1.dns.ie', NULL, NULL, '1'), (domain, 'ns2.dns.ie', NULL, NULL, '2'), (domain, 'ns3.dns.ie', NULL, NULL, '3');

        # Charity domains
        SET domain = CONCAT('ef-nosc08-d', '-', x, '.ie');
        INSERT INTO DomainHist VALUES (NULL, domain, 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE(), CURDATE() + Interval 12 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '113', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
        INSERT INTO Domain VALUES (domain, 'Test Holder 0001', 3, 2, NULL, 600, CURDATE(), CURDATE() + Interval 12 Month, NOW(), 'Test Remark', 'NO', '113', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = domain));
        INSERT INTO ContactHist VALUES
        ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = domain), domain, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
        ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = domain), domain, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
        ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = domain), domain, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
        ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = domain), domain, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
        INSERT INTO Contact VALUES (domain,'XOE550-IEDR', 'Admin'), (domain,'XBC189-IEDR', 'Billing'), (domain,'XBC189-IEDR', 'Creator'), (domain,'XBC189-IEDR', 'Tech');
        INSERT INTO DNSHist VALUES
        ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = domain), domain, 'ns1.dns.ie', NULL, NULL, '1'),
        ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = domain), domain, 'ns2.dns.ie', NULL, NULL, '2'),
        ((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = domain), domain, 'ns3.dns.ie', NULL, NULL, '3');
        INSERT INTO DNS VALUES (domain, 'ns1.dns.ie', NULL, NULL, '1'), (domain, 'ns2.dns.ie', NULL, NULL, '2'), (domain, 'ns3.dns.ie', NULL, NULL, '3');

        # Transfers In
        SET domain = CONCAT('ef-nosc08-e', '-', x, '.ie');
        INSERT INTO DomainHist VALUES (NULL, domain, CONCAT('Test Holder 0', x), 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval FLOOR(x / 10) Day, CURDATE() - Interval FLOOR(x / 10) Day + Interval 12 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW() - Interval 1 Day, 'NO', '113', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
	SET old_hist_id = LAST_INSERT_ID();
        INSERT INTO DomainHist VALUES (NULL, domain, CONCAT('Test Holder 0', x), 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval FLOOR(x / 10) Day, CURDATE() - Interval FLOOR(x / 10) Day + Interval 12 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '113', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
	SET new_hist_id = LAST_INSERT_ID();
        INSERT INTO Domain VALUES (domain, CONCAT('Test Holder 0', x), 3, 2, NULL, 600, CURDATE() - Interval FLOOR(x / 10) Day, CURDATE() - Interval FLOOR(x / 10) Day + Interval 12 Month, NOW(), 'Test Remark', 'NO', '113', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, new_hist_id);
        INSERT INTO ContactHist VALUES
        (old_hist_id, domain, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
        (old_hist_id, domain, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Billing'),
        (old_hist_id, domain, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Creator'),
        (old_hist_id, domain, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Tech'),
        (new_hist_id, domain, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
        (new_hist_id, domain, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
        (new_hist_id, domain, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
        (new_hist_id, domain, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
        INSERT INTO Contact VALUES (domain,'XOE550-IEDR', 'Admin'), (domain,'XBC189-IEDR', 'Billing'), (domain,'XBC189-IEDR', 'Creator'), (domain,'XBC189-IEDR', 'Tech');
        INSERT INTO DNSHist VALUES
        (old_hist_id, domain, 'ns1.dns.ie', NULL, NULL, '1'),
        (old_hist_id, domain, 'ns2.dns.ie', NULL, NULL, '2'),
        (old_hist_id, domain, 'ns3.dns.ie', NULL, NULL, '3'),
        (new_hist_id, domain, 'ns1.dns.ie', NULL, NULL, '1'),
        (new_hist_id, domain, 'ns2.dns.ie', NULL, NULL, '2'),
        (new_hist_id, domain, 'ns3.dns.ie', NULL, NULL, '3');
        INSERT INTO DNS VALUES (domain, 'ns1.dns.ie', NULL, NULL, '1'), (domain, 'ns2.dns.ie', NULL, NULL, '2'), (domain, 'ns3.dns.ie', NULL, NULL, '3');
        INSERT INTO TransfersHist VALUES (NULL, domain, CURDATE() - Interval FLOOR(x / 10) Day, 'XNV498-IEDR', 'XBC189-IEDR', old_hist_id);
        # Transfers Away
        SET domain = CONCAT('ef-nosc08-f', '-', x, '.ie');
        INSERT INTO DomainHist VALUES (NULL, domain, CONCAT('Test Holder 0', x), 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval FLOOR(x / 10) Day, CURDATE() - Interval FLOOR(x / 10) Day + Interval 12 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW() - Interval 1 Day, 'NO', '113', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
	SET old_hist_id = LAST_INSERT_ID();
        INSERT INTO DomainHist VALUES (NULL, domain, CONCAT('Test Holder 0', x), 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval FLOOR(x / 10) Day, CURDATE() - Interval FLOOR(x / 10) Day + Interval 12 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '113', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
	SET new_hist_id = LAST_INSERT_ID();
        INSERT INTO Domain VALUES (domain, CONCAT('Test Holder 0', x), 3, 2, NULL, 600, CURDATE() - Interval FLOOR(x / 10) Day, CURDATE() - Interval FLOOR(x / 10) Day + Interval 12 Month, NOW(), 'Test Remark', 'NO', '113', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, new_hist_id);
        INSERT INTO ContactHist VALUES
        (old_hist_id, domain, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
        (old_hist_id, domain, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
        (old_hist_id, domain, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
        (old_hist_id, domain, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech'),
        (new_hist_id, domain, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
        (new_hist_id, domain, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Billing'),
        (new_hist_id, domain, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Creator'),
        (new_hist_id, domain, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Tech');
        INSERT INTO Contact VALUES (domain,'XOE550-IEDR', 'Admin'), (domain,'XNV498-IEDR', 'Billing'), (domain,'XNV498-IEDR', 'Creator'), (domain,'XNV498-IEDR', 'Tech');
        INSERT INTO DNSHist VALUES
        (old_hist_id, domain, 'ns1.dns.ie', NULL, NULL, '1'),
        (old_hist_id, domain, 'ns2.dns.ie', NULL, NULL, '2'),
        (old_hist_id, domain, 'ns3.dns.ie', NULL, NULL, '3'),
        (new_hist_id, domain, 'ns1.dns.ie', NULL, NULL, '1'),
        (new_hist_id, domain, 'ns2.dns.ie', NULL, NULL, '2'),
        (new_hist_id, domain, 'ns3.dns.ie', NULL, NULL, '3');
        INSERT INTO DNS VALUES (domain, 'ns1.dns.ie', NULL, NULL, '1'), (domain, 'ns2.dns.ie', NULL, NULL, '2'), (domain, 'ns3.dns.ie', NULL, NULL, '3');
        INSERT INTO TransfersHist VALUES (NULL, domain, CURDATE() - Interval FLOOR(x / 10) Day, 'XBC189-IEDR', 'XNV498-IEDR', old_hist_id);

        SET x = x + 1;
    END WHILE;
    SET x = 0;
    WHILE x < 251 DO
        # Tickets
        SET domain = CONCAT('ef-nosc08-a', '-', x, '.ie');
        INSERT INTO TicketIndex VALUES (NULL);
        INSERT INTO Ticket VALUES ((SELECT MAX(id) FROM TicketIndex), 'Modification', domain, NULL, 'Test Holder 0001 - Modified', NULL, 600, NULL, 2, 3, NULL, 'Domain modify request.', 'XBC189-IEDR', NULL, NULL, NULL, 'XBC189-IEDR', NULL, 'XBC189-IEDR', NULL, 'XBC189-IEDR', 'New', NOW(), 'New', NOW(), 'NO', NULL, NOW(), NOW(), '', NULL, NULL, NOW(), 'NO', 1, NULL, 'NO', 'New', NOW(), 'New', NOW(), 1);
        INSERT INTO TicketHist VALUES (NULL, (SELECT T_Number FROM Ticket WHERE D_Name = domain), 'Modification', domain, NULL, 'Test Holder 0001 - Modified', NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), NULL, 2, 3, NULL, 'Domain modify request.', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), NULL, NULL, NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'New', NOW(), 'New', NOW(), 'NO', NULL, NOW(), NOW(), '', NOW(), 'XBC189-IEDR', NULL, NULL, NOW(), 'NO', 1, NULL, 'NO', 'New', NOW(), 'New', NOW());
        UPDATE Ticket T SET Chng_Id = (SELECT MAX(Chng_Id) FROM TicketHist WHERE T_Number = T.T_Number) WHERE D_Name = domain;
        INSERT INTO TicketNameserverHist VALUES
        ((SELECT Chng_Id FROM Ticket WHERE D_Name = domain), (SELECT T_Number FROM Ticket WHERE D_Name = domain), 'ns1.abc1.ie', NULL, NULL, NULL, NULL, NULL, 0, NOW()),
        ((SELECT Chng_Id FROM Ticket WHERE D_Name = domain), (SELECT T_Number FROM Ticket WHERE D_Name = domain), 'ns2.abc1.ie', NULL, NULL, NULL, NULL, NULL, 1, NOW());
        INSERT INTO TicketNameserver VALUES
        ((SELECT T_Number FROM Ticket WHERE D_Name = domain), 'ns1.abc1.ie', NULL, NULL, NULL, NULL, NULL, 0, NOW()),
        ((SELECT T_Number FROM Ticket WHERE D_Name = domain), 'ns2.abc1.ie', NULL, NULL, NULL, NULL, NULL, 1, NOW());
        SET x = x + 1;
    END WHILE;
END;$$
DELIMITER ;
CALL insertbigdata();

INSERT INTO NicHandleHist VALUES (NULL, 'EF10AA-IEDR', 'Registrar', (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'ef10aa@iedr.ie', 'Active', NOW(), NOW(), NOW(),'NO', 'test data generation', 'EF10AA-IEDR', 'A', NULL, 'YES', 'XBC189-IEDR', NOW());
INSERT INTO NicHandle VALUES ('EF10AA-IEDR', 'Registrar', 600, 'Registrar co Limited', '1 The Road, Some Street', 14, 121, 'ef10aa@iedr.ie', 'Active', NOW(), NOW(), NOW(),'NO', 'test data generation', 'EF10AA-IEDR', 'A', NULL, 'YES', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'EF10AA-IEDR'));
INSERT INTO AccessHist VALUES (NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'EF10AA-IEDR'), 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 2, 'NO', 'XBC189-IEDR', NOW(), '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '');
INSERT INTO Access VALUES ('EF10AA-IEDR', 't1X7oZ2/bFDr0cyVoH442gyNoNSib/a', 2, 'NO', '.Z805gDk7mXJJfbcIwZJde', CURDATE(), 'NO', 'NO', '', (SELECT MAX(A.Chng_Id) FROM AccessHist A JOIN NicHandleHist NH  ON A.Nic_Handle_Chng_Id = NH.Chng_Id WHERE NH.Nic_Handle = 'EF10AA-IEDR'));
INSERT INTO Reseller_Defaults VALUES ('EF10AA-IEDR', 'EF10AA-IEDR', NULL, 'both');
INSERT INTO ResellerDefaultNameservers VALUES ('EF10AA-IEDR', 'ns1.blacknightsolutions.com', 0, NOW());
INSERT INTO ResellerDefaultNameservers VALUES ('EF10AA-IEDR', 'ns2.blacknightsolutions.com', 1, NOW());

INSERT INTO DomainHist VALUES (NULL, 'ef-nosc11-reg.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('ef-nosc11-reg.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc11-reg.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc11-reg.ie'), 'ef-nosc11-reg.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc11-reg.ie'), 'ef-nosc11-reg.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc11-reg.ie'), 'ef-nosc11-reg.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc11-reg.ie'), 'ef-nosc11-reg.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('ef-nosc11-reg.ie', 'XBC189-IEDR', 'Admin'), ('ef-nosc11-reg.ie', 'XBC189-IEDR', 'Billing'), ('ef-nosc11-reg.ie', 'XBC189-IEDR', 'Creator'), ('ef-nosc11-reg.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc11-reg.ie'), 'ef-nosc11-reg.ie', 'ns1.ef-nosc11-reg.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc11-reg.ie'), 'ef-nosc11-reg.ie', 'ns2.ef-nosc11-reg.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc11-reg.ie'), 'ef-nosc11-reg.ie', 'ns3.ef-nosc11-reg.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('ef-nosc11-reg.ie', 'ns1.abc1.ie', '', NULL, '1'), ('ef-nosc11-reg.ie', 'ns2.abc1.ie', '', NULL, '2'), ('ef-nosc11-reg.ie', 'ns3.abc1.ie', '', NULL, '3');
INSERT INTO TicketIndex VALUES (NULL);
INSERT INTO Ticket VALUES ((SELECT MAX(id) FROM TicketIndex), 'Registration', 'ef-nosc11-reg.ie', NULL, 'Test Holder 0001 - Modified', NULL, 600, NULL, 3, 2, NULL, 'Domain reg request.', 'XBC189-IEDR', NULL, NULL, NULL, 'XBC189-IEDR', NULL, 'XBC189-IEDR', NULL, 'XBC189-IEDR', 'New', NOW(), 'New', NOW(), 'NO', NULL, NOW(), NOW(), '', NULL, NULL, NOW(), 'NO', 1, NULL, 'NO', 'New', NOW(), 'New', NOW(), 1);
INSERT INTO TicketHist VALUES (NULL, (SELECT T_Number FROM Ticket WHERE D_Name = 'ef-nosc11-reg.ie'), 'Registration', 'ef-nosc11-reg.ie', NULL, 'Test Holder 0001 - Modified', NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), NULL, 3, 2, NULL, 'Domain reg request.', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), NULL, NULL, NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'New', NOW(), 'New', NOW(), 'NO', NULL, NOW(), NOW(), '', NOW(), 'XBC189-IEDR', NULL, NULL, NOW(), 'NO', 1, NULL, 'NO', 'New', NOW(), 'New', NOW());
UPDATE Ticket T SET Chng_Id = (SELECT MAX(Chng_Id) FROM TicketHist WHERE T_Number = T.T_Number) WHERE D_Name = 'ef-nosc11-reg.ie';
INSERT INTO TicketNameserverHist VALUES
((SELECT Chng_Id FROM Ticket WHERE D_Name = 'ef-nosc11-reg.ie'), (SELECT T_Number FROM Ticket WHERE D_Name = 'ef-nosc11-reg.ie'), 'ns1.abc1.ie', NULL, NULL, NULL, NULL, NULL, 0, NOW()),
((SELECT Chng_Id FROM Ticket WHERE D_Name = 'ef-nosc11-reg.ie'), (SELECT T_Number FROM Ticket WHERE D_Name = 'ef-nosc11-reg.ie'), 'ns2.abc1.ie', NULL, NULL, NULL, NULL, NULL, 1, NOW());
INSERT INTO TicketNameserver VALUES
((SELECT T_Number FROM Ticket WHERE D_Name = 'ef-nosc11-reg.ie'), 'ns1.abc1.ie', NULL, NULL, NULL, NULL, NULL, 0, NOW()),
((SELECT T_Number FROM Ticket WHERE D_Name = 'ef-nosc11-reg.ie'), 'ns2.abc1.ie', NULL, NULL, NULL, NULL, NULL, 1, NOW());

INSERT INTO DomainHist VALUES (NULL, 'ef-nosc11-xfer.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('ef-nosc11-xfer.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc11-xfer.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc11-xfer.ie'), 'ef-nosc11-xfer.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc11-xfer.ie'), 'ef-nosc11-xfer.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc11-xfer.ie'), 'ef-nosc11-xfer.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc11-xfer.ie'), 'ef-nosc11-xfer.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('ef-nosc11-xfer.ie', 'XBC189-IEDR', 'Admin'), ('ef-nosc11-xfer.ie', 'XBC189-IEDR', 'Billing'), ('ef-nosc11-xfer.ie', 'XBC189-IEDR', 'Creator'), ('ef-nosc11-xfer.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc11-xfer.ie'), 'ef-nosc11-xfer.ie', 'ns1.ef-nosc11-xfer.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc11-xfer.ie'), 'ef-nosc11-xfer.ie', 'ns2.ef-nosc11-xfer.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc11-xfer.ie'), 'ef-nosc11-xfer.ie', 'ns3.ef-nosc11-xfer.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('ef-nosc11-xfer.ie', 'ns1.abc1.ie', '', NULL, '1'), ('ef-nosc11-xfer.ie', 'ns2.abc1.ie', '', NULL, '2'), ('ef-nosc11-xfer.ie', 'ns3.abc1.ie', '', NULL, '3');
INSERT INTO TicketIndex VALUES (NULL);
INSERT INTO Ticket VALUES ((SELECT MAX(id) FROM TicketIndex), 'Transfer', 'ef-nosc11-xfer.ie', NULL, 'Test Holder 0001', NULL, 600, NULL, 3, 2, NULL, 'Domain reg request.', 'XBC189-IEDR', NULL, NULL, NULL, 'XBC189-IEDR', NULL, 'XBC189-IEDR', NULL, 'XBC189-IEDR', 'New', NOW(), 'New', NOW(), 'NO', NULL, NOW(), NOW(), '', NULL, NULL, NOW(), 'NO', 1, NULL, 'NO', 'New', NOW(), 'New', NOW(), 1);
INSERT INTO TicketHist VALUES (NULL, (SELECT T_Number FROM Ticket WHERE D_Name = 'ef-nosc11-xfer.ie'), 'Transfer', 'ef-nosc11-xfer.ie', NULL, 'Test Holder 0001', NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), NULL, 3, 2, NULL, 'Domain reg request.', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), NULL, NULL, NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'New', NOW(), 'New', NOW(), 'NO', NULL, NOW(), NOW(), '', NOW(), 'XBC189-IEDR', NULL, NULL, NOW(), 'NO', 1, NULL, 'NO', 'New', NOW(), 'New', NOW());
UPDATE Ticket T SET Chng_Id = (SELECT MAX(Chng_Id) FROM TicketHist WHERE T_Number = T.T_Number) WHERE D_Name = 'ef-nosc11-xfer.ie';
INSERT INTO TicketNameserverHist VALUES
((SELECT Chng_Id FROM Ticket WHERE D_Name = 'ef-nosc11-xfer.ie'), (SELECT T_Number FROM Ticket WHERE D_Name = 'ef-nosc11-xfer.ie'), 'ns1.abc1.ie', NULL, NULL, NULL, NULL, NULL, 0, NOW()),
((SELECT Chng_Id FROM Ticket WHERE D_Name = 'ef-nosc11-xfer.ie'), (SELECT T_Number FROM Ticket WHERE D_Name = 'ef-nosc11-xfer.ie'), 'ns2.abc1.ie', NULL, NULL, NULL, NULL, NULL, 1, NOW());
INSERT INTO TicketNameserver VALUES
((SELECT T_Number FROM Ticket WHERE D_Name = 'ef-nosc11-xfer.ie'), 'ns1.abc1.ie', NULL, NULL, NULL, NULL, NULL, 0, NOW()),
((SELECT T_Number FROM Ticket WHERE D_Name = 'ef-nosc11-xfer.ie'), 'ns2.abc1.ie', NULL, NULL, NULL, NULL, NULL, 1, NOW());

INSERT INTO DomainHist VALUES (NULL, 'ef-nosc11-mod.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'XBC189-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('ef-nosc11-mod.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 6 Month, CURDATE() + Interval 6 Month, NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc11-mod.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc11-mod.ie'), 'ef-nosc11-mod.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc11-mod.ie'), 'ef-nosc11-mod.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc11-mod.ie'), 'ef-nosc11-mod.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc11-mod.ie'), 'ef-nosc11-mod.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('ef-nosc11-mod.ie', 'XBC189-IEDR', 'Admin'), ('ef-nosc11-mod.ie', 'XBC189-IEDR', 'Billing'), ('ef-nosc11-mod.ie', 'XBC189-IEDR', 'Creator'), ('ef-nosc11-mod.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc11-mod.ie'), 'ef-nosc11-mod.ie', 'ns1.ef-nosc11-mod.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc11-mod.ie'), 'ef-nosc11-mod.ie', 'ns2.ef-nosc11-mod.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc11-mod.ie'), 'ef-nosc11-mod.ie', 'ns3.ef-nosc11-mod.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('ef-nosc11-mod.ie', 'ns1.abc1.ie', '', NULL, '1'), ('ef-nosc11-mod.ie', 'ns2.abc1.ie', '', NULL, '2'), ('ef-nosc11-mod.ie', 'ns3.abc1.ie', '', NULL, '3');
INSERT INTO TicketIndex VALUES (NULL);
INSERT INTO Ticket VALUES ((SELECT MAX(id) FROM TicketIndex), 'Modification', 'ef-nosc11-mod.ie', NULL, 'Test Holder 0001 - Modified', NULL, 600, NULL, 3, 2, NULL, 'Domain reg request.', 'XBC189-IEDR', NULL, NULL, NULL, 'XBC189-IEDR', NULL, 'XBC189-IEDR', NULL, 'XBC189-IEDR', 'New', NOW(), 'New', NOW(), 'NO', NULL, NOW(), NOW(), '', NULL, NULL, NOW(), 'NO', 1, NULL, 'NO', 'New', NOW(), 'New', NOW(), 1);
INSERT INTO TicketHist VALUES (NULL, (SELECT T_Number FROM Ticket WHERE D_Name = 'ef-nosc11-mod.ie'), 'Modification', 'ef-nosc11-mod.ie', NULL, 'Test Holder 0001 - Modified', NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), NULL, 3, 2, NULL, 'Domain reg request.', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), NULL, NULL, NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), NULL, (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'New', NOW(), 'New', NOW(), 'NO', NULL, NOW(), NOW(), '', NOW(), 'XBC189-IEDR', NULL, NULL, NOW(), 'NO', 1, NULL, 'NO', 'New', NOW(), 'New', NOW());
UPDATE Ticket T SET Chng_Id = (SELECT MAX(Chng_Id) FROM TicketHist WHERE T_Number = T.T_Number) WHERE D_Name = 'ef-nosc11-mod.ie';
INSERT INTO TicketNameserverHist VALUES
((SELECT Chng_Id FROM Ticket WHERE D_Name = 'ef-nosc11-mod.ie'), (SELECT T_Number FROM Ticket WHERE D_Name = 'ef-nosc11-mod.ie'), 'ns1.abc1.ie', NULL, NULL, NULL, NULL, NULL, 0, NOW()),
((SELECT Chng_Id FROM Ticket WHERE D_Name = 'ef-nosc11-mod.ie'), (SELECT T_Number FROM Ticket WHERE D_Name = 'ef-nosc11-mod.ie'), 'ns2.abc1.ie', NULL, NULL, NULL, NULL, NULL, 1, NOW());
INSERT INTO TicketNameserver VALUES
((SELECT T_Number FROM Ticket WHERE D_Name = 'ef-nosc11-mod.ie'), 'ns1.abc1.ie', NULL, NULL, NULL, NULL, NULL, 0, NOW()),
((SELECT T_Number FROM Ticket WHERE D_Name = 'ef-nosc11-mod.ie'), 'ns2.abc1.ie', NULL, NULL, NULL, NULL, NULL, 1, NOW());

INSERT INTO DomainHist VALUES (NULL, 'ef-nosc13.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XNV498-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('ef-nosc13.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc13.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc13.ie'), 'ef-nosc13.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc13.ie'), 'ef-nosc13.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc13.ie'), 'ef-nosc13.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc13.ie'), 'ef-nosc13.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('ef-nosc13.ie', 'XOE550-IEDR', 'Admin'), ('ef-nosc13.ie', 'XBC189-IEDR', 'Billing'), ('ef-nosc13.ie', 'XBC189-IEDR', 'Creator'), ('ef-nosc13.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc13.ie'), 'ef-nosc13.ie', 'ns1.ef-nosc13.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc13.ie'), 'ef-nosc13.ie', 'ns2.ef-nosc13.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc13.ie'), 'ef-nosc13.ie', 'ns3.ef-nosc13.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('ef-nosc13.ie', 'ns1.ef-nosc13.ie', '10.10.121.23', NULL, '1'), ('ef-nosc13.ie', 'ns2.ef-nosc13.ie', '1.3.4.2', NULL, '2'), ('ef-nosc13.ie', 'ns3.ef-nosc13.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'ef-nosc14.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 603), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XNV498-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('ef-nosc14.ie', 'Test Holder 0001', 3, 2, NULL, 603, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc14.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc14.ie'), 'ef-nosc14.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc14.ie'), 'ef-nosc14.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc14.ie'), 'ef-nosc14.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc14.ie'), 'ef-nosc14.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('ef-nosc14.ie', 'XOE550-IEDR', 'Admin'), ('ef-nosc14.ie', 'XNV498-IEDR', 'Billing'), ('ef-nosc14.ie', 'XNV498-IEDR', 'Creator'), ('ef-nosc14.ie', 'XNV498-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc14.ie'), 'ef-nosc14.ie', 'ns1.ef-nosc14.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc14.ie'), 'ef-nosc14.ie', 'ns2.ef-nosc14.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc14.ie'), 'ef-nosc14.ie', 'ns3.ef-nosc14.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('ef-nosc14.ie', 'ns1.ef-nosc14.ie', '10.10.121.23', NULL, '1'), ('ef-nosc14.ie', 'ns2.ef-nosc14.ie', '1.3.4.2', NULL, '2'), ('ef-nosc14.ie', 'ns3.ef-nosc14.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'ef-nosc15.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XNV498-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('ef-nosc15.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc15.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc15.ie'), 'ef-nosc15.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc15.ie'), 'ef-nosc15.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc15.ie'), 'ef-nosc15.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc15.ie'), 'ef-nosc15.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('ef-nosc15.ie', 'XBC189-IEDR', 'Admin'), ('ef-nosc15.ie', 'XBC189-IEDR', 'Billing'), ('ef-nosc15.ie', 'XBC189-IEDR', 'Creator'), ('ef-nosc15.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc15.ie'), 'ef-nosc15.ie', 'ns1.ef-nosc15.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc15.ie'), 'ef-nosc15.ie', 'ns2.ef-nosc15.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc15.ie'), 'ef-nosc15.ie', 'ns3.ef-nosc15.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('ef-nosc15.ie', 'ns1.ef-nosc15.ie', '10.10.121.23', NULL, '1'), ('ef-nosc15.ie', 'ns2.ef-nosc15.ie', '1.3.4.2', NULL, '2'), ('ef-nosc15.ie', 'ns3.ef-nosc15.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'ef-nosc15-admin.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XNV498-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('ef-nosc15-admin.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc15-admin.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc15-admin.ie'), 'ef-nosc15-admin.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc15-admin.ie'), 'ef-nosc15-admin.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'EF10AA-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc15-admin.ie'), 'ef-nosc15-admin.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'EF10AA-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc15-admin.ie'), 'ef-nosc15-admin.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'EF10AA-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('ef-nosc15-admin.ie', 'XBC189-IEDR', 'Admin'), ('ef-nosc15-admin.ie', 'EF10AA-IEDR', 'Billing'), ('ef-nosc15-admin.ie', 'EF10AA-IEDR', 'Creator'), ('ef-nosc15-admin.ie', 'EF10AA-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc15-admin.ie'), 'ef-nosc15-admin.ie', 'ns1.ef-nosc15-admin.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc15-admin.ie'), 'ef-nosc15-admin.ie', 'ns2.ef-nosc15-admin.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc15-admin.ie'), 'ef-nosc15-admin.ie', 'ns3.ef-nosc15-admin.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('ef-nosc15-admin.ie', 'ns1.ef-nosc15-admin.ie', '10.10.121.23', NULL, '1'), ('ef-nosc15-admin.ie', 'ns2.ef-nosc15-admin.ie', '1.3.4.2', NULL, '2'), ('ef-nosc15-admin.ie', 'ns3.ef-nosc15-admin.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'ef-nosc15-bill.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XNV498-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('ef-nosc15-bill.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc15-bill.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc15-bill.ie'), 'ef-nosc15-bill.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'EF10AA-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc15-bill.ie'), 'ef-nosc15-bill.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc15-bill.ie'), 'ef-nosc15-bill.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'EF10AA-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc15-bill.ie'), 'ef-nosc15-bill.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'EF10AA-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('ef-nosc15-bill.ie', 'EF10AA-IEDR', 'Admin'), ('ef-nosc15-bill.ie', 'XBC189-IEDR', 'Billing'), ('ef-nosc15-bill.ie', 'EF10AA-IEDR', 'Creator'), ('ef-nosc15-bill.ie', 'EF10AA-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc15-bill.ie'), 'ef-nosc15-bill.ie', 'ns1.ef-nosc15-bill.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc15-bill.ie'), 'ef-nosc15-bill.ie', 'ns2.ef-nosc15-bill.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc15-bill.ie'), 'ef-nosc15-bill.ie', 'ns3.ef-nosc15-bill.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('ef-nosc15-bill.ie', 'ns1.ef-nosc15-bill.ie', '10.10.121.23', NULL, '1'), ('ef-nosc15-bill.ie', 'ns2.ef-nosc15-bill.ie', '1.3.4.2', NULL, '2'), ('ef-nosc15-bill.ie', 'ns3.ef-nosc15-bill.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'ef-nosc15-tech.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XNV498-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('ef-nosc15-tech.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc15-tech.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc15-tech.ie'), 'ef-nosc15-tech.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'EF10AA-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc15-tech.ie'), 'ef-nosc15-tech.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'EF10AA-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc15-tech.ie'), 'ef-nosc15-tech.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'EF10AA-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc15-tech.ie'), 'ef-nosc15-tech.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('ef-nosc15-tech.ie', 'EF10AA-IEDR', 'Admin'), ('ef-nosc15-tech.ie', 'EF10AA-IEDR', 'Billing'), ('ef-nosc15-tech.ie', 'EF10AA-IEDR', 'Creator'), ('ef-nosc15-tech.ie', 'XBC189-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc15-tech.ie'), 'ef-nosc15-tech.ie', 'ns1.ef-nosc15-tech.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc15-tech.ie'), 'ef-nosc15-tech.ie', 'ns2.ef-nosc15-tech.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc15-tech.ie'), 'ef-nosc15-tech.ie', 'ns3.ef-nosc15-tech.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('ef-nosc15-tech.ie', 'ns1.ef-nosc15-tech.ie', '10.10.121.23', NULL, '1'), ('ef-nosc15-tech.ie', 'ns2.ef-nosc15-tech.ie', '1.3.4.2', NULL, '2'), ('ef-nosc15-tech.ie', 'ns3.ef-nosc15-tech.ie', '10.12.13.14', NULL, '3');

INSERT INTO DomainHist VALUES (NULL, 'ef-nosc16.ie', 'Test Holder 0001', 3, 2, NULL, (SELECT MAX(Chng_Id) FROM AccountHist WHERE A_Number = 600), CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'XNV498-IEDR', NOW(), 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL);
INSERT INTO Domain VALUES ('ef-nosc16.ie', 'Test Holder 0001', 3, 2, NULL, 600, CURDATE() - Interval 12 Month, CURDATE(), NOW(), 'Test Remark', 'NO', '17', NULL, NULL, NULL, 'authcode', CURDATE() + Interval 1 Day, 0, NULL, NULL, (SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc16.ie'));
INSERT INTO ContactHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc16.ie'), 'ef-nosc16.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR'), 'Admin'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc16.ie'), 'ef-nosc16.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Billing'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc16.ie'), 'ef-nosc16.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Creator'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc16.ie'), 'ef-nosc16.ie', (SELECT MAX(Chng_Id) FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR'), 'Tech');
INSERT INTO Contact VALUES ('ef-nosc16.ie', 'XOE550-IEDR', 'Admin'), ('ef-nosc16.ie', 'XBC189-IEDR', 'Billing'), ('ef-nosc16.ie', 'XBC189-IEDR', 'Creator'), ('ef-nosc16.ie', 'XOE550-IEDR', 'Tech');
INSERT INTO DNSHist VALUES
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc16.ie'), 'ef-nosc16.ie', 'ns1.ef-nosc16.ie', '10.10.121.23', NULL, '1'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc16.ie'), 'ef-nosc16.ie', 'ns2.ef-nosc16.ie', '1.3.4.2', NULL, '2'),
((SELECT MAX(Chng_Id) FROM DomainHist WHERE D_Name = 'ef-nosc16.ie'), 'ef-nosc16.ie', 'ns3.ef-nosc16.ie', '10.12.13.14', NULL, '3');
INSERT INTO DNS VALUES ('ef-nosc16.ie', 'ns1.ef-nosc16.ie', '10.10.121.23', NULL, '1'), ('ef-nosc16.ie', 'ns2.ef-nosc16.ie', '1.3.4.2', NULL, '2'), ('ef-nosc16.ie', 'ns3.ef-nosc16.ie', '10.12.13.14', NULL, '3');

