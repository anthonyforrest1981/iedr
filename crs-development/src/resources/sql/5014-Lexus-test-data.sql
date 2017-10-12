DELETE FROM DNS_Check_Notification WHERE ID IN (8, 10, 11);

INSERT INTO DNS_Check_Notification (ID, Domain_Name, DNS_Name, Error_Message) VALUES
(10, CONCAT('unnormalized-name-o', UNHEX('CC83'), 'e', UNHEX('CC89'), '.ie'),
    CONCAT('ns.unnormalized-name-o', UNHEX('CC83'), 'e', UNHEX('CC89'), '.ie'),
    CONCAT('unorm no', UNHEX('CC88'), 'tif error')),
(11, 'normalized-correct-õẻ.ie', 'ns.normalized-correct-õẻ.ie', 'norm nötif error');

UPDATE ContactHist SET Nic_Handle_Chng_ID = (SELECT max(Chng_ID) from NicHandleHist where Nic_Handle = 'IDL2-IEDR') WHERE D_Name = 'domain-with-subcategory-to-transfer.ie';
UPDATE Contact SET Contact_NH = 'IDL2-IEDR' WHERE D_Name = 'domain-with-subcategory-to-transfer.ie';
