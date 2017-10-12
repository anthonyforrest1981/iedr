-- CRS-1241

ALTER TABLE DNS_Check_Notification
    DROP COLUMN Nic_Handle,
    DROP COLUMN Email,
    ADD COLUMN Ticket_Number INT(10) AFTER Domain_Name;

UPDATE DNS_Check_Notification DNS JOIN Ticket T ON T.D_Name = DNS.Domain_Name SET DNS.Ticket_Number = T.T_Number;

-- CRS-1317

INSERT INTO EmailHist(E_ID, E_Text, E_Subject, E_To, E_CC, E_BCC, E_From, active, Html, E_To_Internal, E_CC_Internal, E_BCC_Internal, EG_Chng_ID, E_Change_TS, E_Suppressible, E_Send_Reason, E_Non_Suppressible_Reason, Chng_NH, Chng_TS) VALUES
  ('213', '\nPLEASE DO NOT REPLY TO THIS EMAIL. THIS IS AN AUTOMATED EMAIL\n\nRemarks : $REMARK$\n\nKind Regards\nRegistration Services Team\nIE Domain Registry CLG\nTel: +353 (1) 2365400\nFax: +353 (1) 2300365 \nWeb: www.iedr.ie\n\n-------------------------------------------------------------------------------------------------------\nRegistered Office: Harbour Square, Block 2, 4th Floor, Dun Laoghaire, Co. Dublin.\nRegistered in Ireland. No: 315315.\n-------------------------------------------------------------------------------------------------------\n\n\n', '(Buy request Query) Re: $DOMAIN$ ', '$NIC_EMAIL$', NULL, NULL, 'registrations@iedr.ie', 'YES', 'NO', NULL, 'registrations@iedr.ie', 'hostmaster-archive@domainregistry.ie, registrations@iedr.ie', NULL, '2017-05-19 15:54:01', 'NO', '', '', '', NOW());
INSERT INTO `Email`(E_ID, E_Text, E_Subject, E_To, E_CC, E_BCC, E_From, active, Html, E_To_Internal, E_CC_Internal, E_BCC_Internal, EG_ID, E_Change_TS, E_Suppressible, E_Send_Reason, E_Non_Suppressible_Reason, E_Suppressed_By_Gaining, Chng_ID) VALUES
  ('213', '\nPLEASE DO NOT REPLY TO THIS EMAIL. THIS IS AN AUTOMATED EMAIL\n\nRemarks : $REMARK$\n\nKind Regards\nRegistration Services Team\nIE Domain Registry CLG\nTel: +353 (1) 2365400\nFax: +353 (1) 2300365 \nWeb: www.iedr.ie\n\n-------------------------------------------------------------------------------------------------------\nRegistered Office: Harbour Square, Block 2, 4th Floor, Dun Laoghaire, Co. Dublin.\nRegistered in Ireland. No: 315315.\n-------------------------------------------------------------------------------------------------------\n\n\n', '(Buy request Query) Re: $DOMAIN$ ', '$NIC_EMAIL$', NULL, NULL, 'registrations@iedr.ie', 'YES', 'NO', NULL, 'registrations@iedr.ie', 'hostmaster-archive@domainregistry.ie, registrations@iedr.ie', NULL, NOW(), 'NO', '', '', 'YES', (SELECT Chng_ID FROM EmailHist WHERE E_Id = 213));

-- CRS-1408

CREATE TABLE Owner_Type (
  id INT(11) NOT NULL PRIMARY KEY,
  name VARCHAR(128) NOT NULL UNIQUE,
  class_id INT(11) NOT NULL,
  category_id INT(11) NOT NULL,
  charity ENUM('YES','NO') NOT NULL,
  CONSTRAINT fk_class_category FOREIGN KEY (class_id, category_id) REFERENCES Class_to_Category(class_id, category_id)
);

INSERT INTO Owner_Type VALUES
  (1, 'Company', 2, 11, 'NO'),
  (2, 'Business Owner', 3, 11, 'NO'),
  (3, 'Club/Band/Local Group', 5, 11, 'NO'),
  (4, 'School/College', 4, 11, 'NO'),
  (5, 'State Agency', 6, 11, 'NO'),
  (6, 'Charity', 5, 11, 'YES'),
  (7, 'Blogger/Other', 1, 11, 'NO');

INSERT INTO EmailHist
    SELECT NULL, e.E_ID,
        REPLACE(REPLACE(REPLACE(REPLACE(e.E_Text,
            '$DOMAIN_CATEGORY$\n', ''),
            '$DOMAIN_CLASS$\n', ''),
            '$DOMAIN_CATEGORY_OLD$\n', ''),
            '$DOMAIN_CLASS_OLD$\n', ''),
        e.E_Subject, e.E_To, e.E_CC, e.E_BCC, e.E_From, e.active, e.Html, e.E_To_Internal, e.E_CC_Internal,
        e.E_BCC_Internal, eg.Chng_ID, e.E_Change_TS, e.E_Suppressible, e.E_Send_Reason, e.E_Non_Suppressible_Reason,
        'IH4-IEDR', NOW()
    FROM Email e
    LEFT JOIN EmailGroup eg ON eg.EG_ID = e.EG_ID
    WHERE e.E_ID IN (130, 131, 132, 133);

UPDATE Email e
  JOIN EmailHist eh ON eh.Chng_ID = (SELECT max(Chng_ID) FROM EmailHist ieh where ieh.E_ID = e.E_ID)
  LEFT JOIN EmailGroupHist eg ON eh.EG_Chng_ID = eg.Chng_ID
SET
  e.Chng_ID = eh.Chng_ID,
  e.E_Text = eh.E_Text,
  e.E_Subject = eh.E_Subject,
  e.E_To = eh.E_To,
  e.E_CC = eh.E_CC,
  e.E_BCC = eh.E_BCC,
  e.E_From = eh.E_From,
  e.active = eh.active,
  e.Html = eh.Html,
  e.E_To_Internal = eh.E_To_Internal,
  e.E_CC_Internal = eh.E_CC_Internal,
  e.E_BCC_Internal = eh.E_BCC_Internal,
  e.EG_ID = eg.EG_ID,
  e.E_Change_TS = eh.E_Change_TS,
  e.E_Suppressible = eh.E_Suppressible,
  e.E_Send_Reason = eh.E_Send_Reason,
  e.E_Non_Suppressible_Reason = eh.E_Non_Suppressible_Reason
WHERE e.E_ID IN (130, 131, 132, 133);
