/*!40101 SET NAMES utf8 */;
/*!40101 SET CHARACTER_SET_CLIENT=utf8 */;
/*!40101 SET CHARACTER_SET_RESULTS=utf8 */;

INSERT INTO `Ticket` (`T_Number`, `T_Type`, `D_Name`, `DN_Fail_Cd`, `D_Holder`, `DH_Fail_Cd`, `A_Number`, `AC_Fail_Cd`, `T_Class`, `T_Category`, `T_Remark`, `Admin_NH1`, `ANH1_Fail_Cd`, `Admin_NH2`, `ANH2_Fail_Cd`, `Tech_NH`, `TNH_Fail_Cd`, `Bill_NH`, `BNH_Fail_Cd`, `Creator_NH`, `Admin_Status`, `Ad_StatusDt`, `Tech_Status`, `T_Status_Dt`, `CheckedOut`, `CheckedOutTo`, `T_Reg_Dt`, `T_Ren_Dt`, `T_TStamp`, `H_Remark`, `T_Class_Fail_Cd`, `T_Category_Fail_Cd`, `T_Created_TS`, `Opt_Cert`, `T_ClikPaid`, `_Fee_DECOM`, `_VAT_DECOM`, `Period`, `CharityCode`, `Financial_Status`, `F_Status_Dt`, `Customer_Status`, `C_Status_Dt`) VALUES
(400000,
'R',
CONCAT('unnormalizeddomainnamea', UNHEX('CC88'), '.ie'),
0,
CONCAT('U', UNHEX('CC88'), 'ser'),
0,
00000001,
0,
CONCAT('Sole pa', UNHEX('CC88'), 'le class'),
CONCAT('Sole pa', UNHEX('CC88'), 'le category'),
CONCAT('Sole pa', UNHEX('CC88'), 'le remark'),
CONCAT('IDL2-IE', UNHEX('CC88'), 'DP'),
0,
null,
0,
CONCAT('IDL2-IE', UNHEX('CC88'), 'DP'),
0,
CONCAT('IDL2-IE', UNHEX('CC88'), 'DP'),
0,
CONCAT('IDL2-IE', UNHEX('CC88'), 'DP'),
0,
'0000-00-00',
0,
'0000-00-00',
'Y',
CONCAT('IDL2-IE', UNHEX('CC88'), 'DP'),
null,
null,
CURRENT_TIMESTAMP,
CONCAT('Sole pa', UNHEX('CC88'), 'le h remark'),
0,
0,
'0000-00-00 00:00:00',
'N',
'Y',
null,
null,
1,
CONCAT('chari', UNHEX('CC89'), 'tu', UNHEX('CC88'), 'code'),
0,
'0000-00-00 00:00:00',
0,
'0000-00-00 00:00:00'),
(400001,
'R',
'normalizeddomainnameä.ie',
0,
'Üser',
0,
00000001,
0,
'Sole päle class',
'Sole päle category',
'Sole päle remark',
'IDË2-IEDR',
0,
null,
0,
'IDË2-IEDR',
0,
'IDË2-IEDR',
0,
'IDË2-IEDR',
0,
'0000-00-00',
0,
'0000-00-00',
'Y',
'IDË2-IEDR',
null,
null,
CURRENT_TIMESTAMP,
'Sole päle h remark',
0,
0,
'0000-00-00 00:00:00',
'N',
'Y',
null,
null,
1,
'charỉtücode',
0,
'0000-00-00 00:00:00',
0,
'0000-00-00 00:00:00');

INSERT INTO `TicketNameserver`
(`T_Number`, `TN_Name`, `TN_Order`)
VALUES
(400000, CONCAT('ns1.domainnamea', UNHEX('CC88'), '.ie'), 1),
(400000, CONCAT('ns2.domainnamea', UNHEX('CC88'), '.ie'), 2),
(400001, "ns1.domainnameä.ie", 1),
(400001, "ns2.domainnameä.ie", 2);

INSERT INTO `NicHandle`
(`Nic_Handle`, `NH_Name`, `A_Number`, `Co_Name`, `NH_Address`, `NH_County`, `NH_Country`, `NH_Email`, `NH_Status`, `NH_Status_Dt`, `NH_Reg_Dt`, `NH_TStamp`, `NH_BillC_Ind`, `NH_Remark`, `NH_Creator`, `Vat_Category`) VALUES
(CONCAT('IDL2-IE', UNHEX('CC88'), 'DP'),
CONCAT('NicHandle', UNHEX('CC88'), ' name'),
00000001,
CONCAT('NicHandle', UNHEX('CC88'), ' company'),
CONCAT('NicHandle', UNHEX('CC88'), ' address'),
CONCAT('NicHandle', UNHEX('CC88'), ' county'),
CONCAT('NicHandle', UNHEX('CC88'), ' country'),
CONCAT('NicHandle', UNHEX('CC88'), '@email.ie'),
'Active',
CURDATE(),
CURDATE(),
CURRENT_TIMESTAMP,
'Y',
CONCAT('NicHandle', UNHEX('CC88'), ' remark'),
null,
'A'),
('IDË2-IEDR',
'NicHandlë name',
00000001,
'NicHandlë company',
'NicHandlë address',
'NicHandlë county',
'NicHandlë country',
'NicHandlë@email.ie',
'Active',
CURDATE(),
CURDATE(),
CURRENT_TIMESTAMP,
'Y',
'NicHandlë  remark',
null,
'A');

INSERT INTO `TicketResponse` (`Response_ID`, `Response_Title`, `Response_Text`) VALUES
(100, CONCAT('Te', UNHEX('CC88'), 'st unnormalized response'), CONCAT('Te', UNHEX('CC88'), 'st unnormalized text')),
(101, 'Tëst normalized response', 'Tëst normalized text');

INSERT INTO TicketHist (
    T_Number, T_Type, D_Name, DN_Fail_Cd, D_Holder, DH_Fail_Cd, A_Number, AC_Fail_Cd, T_Class, T_Class_Fail_Cd, T_Category, T_Category_Fail_Cd, Admin_NH1, ANH1_Fail_Cd, Admin_NH2, ANH2_Fail_Cd, Tech_NH, TNH_Fail_Cd, Bill_NH,
    BNH_Fail_Cd, Creator_NH, Admin_Status, Ad_StatusDt, Tech_Status, T_Status_Dt, Financial_Status, F_Status_Dt, Customer_Status, C_Status_Dt, CheckedOut, CheckedOutTo,
    T_Reg_Dt, T_Ren_Dt, T_TStamp, T_Remark, H_Remark, T_Created_TS, Opt_Cert, T_ClikPaid, Period, CharityCode, Chng_Dt, Chng_NH)
SELECT
    T.T_Number, T.T_Type, T.D_Name, T.DN_Fail_Cd, T.D_Holder, T.DH_Fail_Cd, T.A_Number, T.AC_Fail_Cd, T.T_Class, T.T_Class_Fail_Cd, T.T_Category,
    T.T_Category_Fail_Cd, T.Admin_NH1, T.ANH1_Fail_Cd, T.Admin_NH2, T.ANH2_Fail_Cd, T.Tech_NH, T.TNH_Fail_Cd, T.Bill_NH, T.BNH_Fail_Cd, T.Creator_NH,
    T.Admin_Status, T.Ad_StatusDt, T.Tech_Status, T.T_Status_Dt, T.Financial_Status, T.F_Status_Dt, T.Customer_Status, T.C_Status_Dt, T.CheckedOut,
    T.CheckedOutTo, T.T_Reg_Dt, T.T_Ren_Dt, T.T_TStamp, T.T_Remark, T.H_Remark, T.T_Created_TS, T.Opt_Cert, T.T_ClikPaid, T.Period, T.CharityCode,
    '2015-01-01',
    CONCAT('IDL2-IE', UNHEX('CC88'), 'DP')
FROM
    Ticket AS T
WHERE
    T.T_Number = 400000;

INSERT INTO TicketHist (
    T_Number, T_Type, D_Name, DN_Fail_Cd, D_Holder, DH_Fail_Cd, A_Number, AC_Fail_Cd, T_Class, T_Class_Fail_Cd, T_Category, T_Category_Fail_Cd, Admin_NH1, ANH1_Fail_Cd, Admin_NH2, ANH2_Fail_Cd, Tech_NH, TNH_Fail_Cd, Bill_NH,
    BNH_Fail_Cd, Creator_NH, Admin_Status, Ad_StatusDt, Tech_Status, T_Status_Dt, Financial_Status, F_Status_Dt, Customer_Status, C_Status_Dt, CheckedOut, CheckedOutTo,
    T_Reg_Dt, T_Ren_Dt, T_TStamp, T_Remark, H_Remark, T_Created_TS, Opt_Cert, T_ClikPaid, Period, CharityCode, Chng_Dt, Chng_NH)
SELECT
    T.T_Number, T.T_Type, T.D_Name, T.DN_Fail_Cd, T.D_Holder, T.DH_Fail_Cd, T.A_Number, T.AC_Fail_Cd, T.T_Class, T.T_Class_Fail_Cd, T.T_Category,
    T.T_Category_Fail_Cd, T.Admin_NH1, T.ANH1_Fail_Cd, T.Admin_NH2, T.ANH2_Fail_Cd, T.Tech_NH, T.TNH_Fail_Cd, T.Bill_NH, T.BNH_Fail_Cd, T.Creator_NH,
    T.Admin_Status, T.Ad_StatusDt, T.Tech_Status, T.T_Status_Dt, T.Financial_Status, T.F_Status_Dt, T.Customer_Status, T.C_Status_Dt, T.CheckedOut,
    T.CheckedOutTo, T.T_Reg_Dt, T.T_Ren_Dt, T.T_TStamp, T.T_Remark, T.H_Remark, T.T_Created_TS, T.Opt_Cert, T.T_ClikPaid, T.Period, T.CharityCode,
    '2015-01-01',
    'IDË2-IEDR'
FROM
    Ticket AS T
WHERE
    T.T_Number = 400001;

INSERT INTO TicketHist (
    T_Number, T_Type, D_Name, DN_Fail_Cd, D_Holder, DH_Fail_Cd, A_Number, AC_Fail_Cd, T_Class, T_Class_Fail_Cd, T_Category, T_Category_Fail_Cd, Admin_NH1, ANH1_Fail_Cd, Admin_NH2, ANH2_Fail_Cd, Tech_NH, TNH_Fail_Cd, Bill_NH,
    BNH_Fail_Cd, Creator_NH, Admin_Status, Ad_StatusDt, Tech_Status, T_Status_Dt, Financial_Status, F_Status_Dt, Customer_Status, C_Status_Dt, CheckedOut, CheckedOutTo,
    T_Reg_Dt, T_Ren_Dt, T_TStamp, T_Remark, H_Remark, T_Created_TS, Opt_Cert, T_ClikPaid, Period, CharityCode, Chng_Dt, Chng_NH)
SELECT
    T.T_Number, T.T_Type, T.D_Name, T.DN_Fail_Cd, T.D_Holder, T.DH_Fail_Cd, T.A_Number, T.AC_Fail_Cd, T.T_Class, T.T_Class_Fail_Cd, T.T_Category,
    T.T_Category_Fail_Cd, T.Admin_NH1, T.ANH1_Fail_Cd, T.Admin_NH2, T.ANH2_Fail_Cd, T.Tech_NH, T.TNH_Fail_Cd, T.Bill_NH, T.BNH_Fail_Cd, T.Creator_NH,
    T.Admin_Status, T.Ad_StatusDt, T.Tech_Status, T.T_Status_Dt, T.Financial_Status, T.F_Status_Dt, T.Customer_Status, T.C_Status_Dt, T.CheckedOut,
    T.CheckedOutTo, T.T_Reg_Dt, T.T_Ren_Dt, T.T_TStamp, T.T_Remark, T.H_Remark, T.T_Created_TS, T.Opt_Cert, T.T_ClikPaid, T.Period, T.CharityCode,
    '2015-01-01',
    'IDË2-IEDR'
FROM
    Ticket AS T
WHERE
    T.T_Number = 400001;

-- Test data for CRS-602 and CRS-603 --
INSERT INTO `Category` VALUES
(98, CONCAT('Normalized Cate', UNHEX('C7B5C58D'), 'ry')),
(99, CONCAT('Unnormalized Categ', UNHEX('CC81'), 'o', UNHEX('CC84'), 'ry'));

INSERT INTO `Class` VALUES
(98, CONCAT('Normalized Cl', UNHEX('C3A3'), 'ss')),
(99, CONCAT('Unnormalized Cla', UNHEX('CC83'), 'ss'));

INSERT INTO `Class_to_Category` VALUES
(98, 98),
(99, 99);

INSERT INTO `Counties` VALUES
(998, CONCAT('Normalized co', UNHEX('C3BA'), 'n', UNHEX('C5A3'), 'y')),
(999, CONCAT('Unnormalized cou', UNHEX('CC81'), 'nt', UNHEX('CCA7'), 'y'));

INSERT INTO `Countries` VALUES
(998, CONCAT('Normalized c', UNHEX('C3B4'), 'u', UNHEX('E1B985'), 'try'), 'B'),
(999, CONCAT('Unnormalized co', UNHEX('CC82'), 'un', UNHEX('CC87'), 'try'), 'B');

INSERT INTO `NicHandle`(`Nic_Handle`, `NH_Name`, `A_Number`, `Co_Name`, `NH_Address`, `NH_County`, `NH_Country`, `NH_Email`, `NH_Status`, `NH_Status_Dt`, `NH_Reg_Dt`, `NH_TStamp`, `NH_BillC_Ind`, `NH_Remark`, `NH_Creator`, `Vat_Category`) VALUES
-- normalized - to test searching with criteria including UTF-8 values
(CONCAT('XX', UNHEX('E1BA8C'), '7-IEDR'), CONCAT(UNHEX('C38CE1B983E1B883C3A1C885'), 'l'), 00000001, CONCAT(UNHEX('E1B884C49CE1B982C598'), ' Ltd.'), CONCAT(UNHEX('C590C887E1BBA5C4B5C895'), ' Street'), CONCAT('Normalized co', UNHEX('C3BA'), 'n', UNHEX('C5A3'), 'y'), CONCAT('Normalized c', UNHEX('C3B4'), 'u', UNHEX('E1B985'), 'try'), CONCAT(UNHEX('C3A9E1B8BFC485E1BB89E1B8BD'), '@server.xxx'), 'Active', '2008-08-13', '2008-08-13', '2008-08-13 16:32:38', '', UNHEX('E1BA99C49FE1B981C89FE1B8B5'), CONCAT('XX', UNHEX('E1BA8C'), '7-IEDR'), 'A'),
(CONCAT('XX', UNHEX('C5B8'), '7-IEDR'), CONCAT(UNHEX('C38CE1B983E1B883C3BAC885'), 'l'), 00000797, CONCAT(UNHEX('E1B88CC49CE1B982C598'), ' Ltd.'), CONCAT(UNHEX('C590C887E1BBA5C4B5C895'), ' Street'), CONCAT('Normalized co', UNHEX('C3BA'), 'n', UNHEX('C5A3'), 'y'), CONCAT('Normalized c', UNHEX('C3B4'), 'u', UNHEX('E1B985'), 'try'), CONCAT('f', UNHEX('E1B8BFC485E1BB89E1B8BD'), '@server.xxx') /* originally: CONCAT(UNHEX('E1BAB9E1B8BFC485E1BB89E1B8BD'), '@server.xxx') - 'ẹḿąỉḽ@server.xxx', but that is considered equal to 'email@server.xxx' while searching - shoud be restored after solving the problem */, 'Active', '2008-08-13', '2008-08-13', '2008-08-13 16:32:38', '', UNHEX('E1BA99C49FE1B981C89FE1B8B5'), CONCAT('XX', UNHEX('C5B8'), '7-IEDR'), 'A'),
-- with unnormalized NicHanle value - to test normalizing NicHandle after performing "find" by name
(CONCAT('XYY', UNHEX('CC88'), '8-IEDR'), 'Name for UTF-8 Test', 00000798, 'FGMR Ltd.' /* originally 'DGMR Ltd.', but that is considered equal to 'ḌĜṂŘ Ltd.' while searching - shoud be restored after solving the problem */, 'Oeuju Street', 'Co. Dublin', 'Ireland', 'email@server.xxx', 'Active', '2008-08-13', '2008-08-13', '2008-08-13 16:32:38', '', 'Ygmhk', CONCAT('XYY', UNHEX('CC88'), '8-IEDR'), 'A'),
-- with unnormalized details - to test normalizing after getting the record by NicHandle
('YYX9-IEDR', CONCAT('I', UNHEX('CC80'), 'm', UNHEX('CCA3'), 'b', UNHEX('CC87'), 'a', UNHEX('CC81'), 'e', UNHEX('CC8F'), 'l'), 00000001, CONCAT('B', UNHEX('CCA3'), 'G', UNHEX('CC82'), 'M', UNHEX('CCA3'), 'R', UNHEX('CC8C'), ' Ltd.'), CONCAT('O', UNHEX('CC8B'), 'e', UNHEX('CC91'), 'u', UNHEX('CCA3'), 'j', UNHEX('CC82'), 'u', UNHEX('CC8F'), ' Street'), CONCAT('Unnormalized cou', UNHEX('CC81'), 'nt', UNHEX('CCA7'), 'y'), CONCAT('Unnormalized co', UNHEX('CC82'), 'un', UNHEX('CC87'), 'try'), CONCAT('e', UNHEX('CC81'), 'm', UNHEX('CC81'), 'a', UNHEX('CCA8'), 'i', UNHEX('CC89'), 'l', UNHEX('CCAD'), '@server.xxx'), 'Active', '2008-08-13', '2008-08-13', '2008-08-13 16:32:38', '', CONCAT('y', UNHEX('CC8A'), 'g', UNHEX('CC86'), 'm', UNHEX('CC87'), 'h', UNHEX('CC8C'), 'k', UNHEX('CCB1')), 'YYX9-IEDR', 'A'),
('YYY9-IEDR', CONCAT('I', UNHEX('CC80'), 'm', UNHEX('CCA3'), 'b', UNHEX('CC87'), 'u', UNHEX('CC81'), 'e', UNHEX('CC8F'), 'l'), 00000799, CONCAT('D', UNHEX('CCA3'), 'G', UNHEX('CC82'), 'M', UNHEX('CCA3'), 'R', UNHEX('CC8C'), ' Ltd.'), CONCAT('O', UNHEX('CC8B'), 'e', UNHEX('CC91'), 'u', UNHEX('CCA3'), 'j', UNHEX('CC82'), 'u', UNHEX('CC8F'), ' Street'), CONCAT('Unnormalized cou', UNHEX('CC81'), 'nt', UNHEX('CCA7'), 'y'), CONCAT('Unnormalized co', UNHEX('CC82'), 'un', UNHEX('CC87'), 'try'), CONCAT('e', UNHEX('CCA3'), 'm', UNHEX('CC81'), 'a', UNHEX('CCA8'), 'i', UNHEX('CC89'), 'l', UNHEX('CCAD'), '@server.xxx'), 'Active', '2008-08-13', '2008-08-13', '2008-08-13 16:32:38', '', CONCAT('y', UNHEX('CC8A'), 'g', UNHEX('CC86'), 'm', UNHEX('CC87'), 'h', UNHEX('CC8C'), 'k', UNHEX('CCB1')), CONCAT('XYY', UNHEX('CC88'), '8-IEDR'), 'A');

INSERT INTO `Telecom` VALUES
(CONCAT('XX', UNHEX('C5B8'), '7-IEDR'), CONCAT('Tele', UNHEX('E1B995'), 'h', UNHEX ('C6A1'), 'ne'), 'P'),
(CONCAT('XX', UNHEX('C5B8'), '7-IEDR'), CONCAT('Tele', UNHEX('E1B89F'), 'a', UNHEX ('E1BA8D')), 'F'),
('YYY9-IEDR', CONCAT('Telep', UNHEX('CC81'), 'ho', UNHEX ('CC9B'), 'ne'), 'P'),
('YYY9-IEDR', CONCAT('Telef', UNHEX('CC87'), 'ax', UNHEX ('CC88')), 'F');

INSERT INTO `NicHandleHist` VALUES
(NULL, CONCAT('XX', UNHEX('C5B8'), '7-IEDR'), CONCAT(UNHEX('C38CE1B983E1B883C3BAC885'), 'l'), 00000797, CONCAT(UNHEX('E1B88CC49CE1B982C598'), ' Ltd.'), CONCAT(UNHEX('C590C887E1BBA5C4B5C895'), ' Street'), CONCAT('Normalized co', UNHEX('C3BA'), 'n', UNHEX('C5A3'), 'y'), CONCAT('Normalized c', UNHEX('C3B4'), 'u', UNHEX('E1B985'), 'try'), CONCAT(UNHEX('E1BAB9E1B8BFC485E1BB89E1B8BD'), '@server.xxx'), 'Active', '2008-08-13', '2008-08-13', '2008-08-13 16:32:38', '', UNHEX('C5AAC4A3E1B983E1BA96C7A9'), CONCAT('XX', UNHEX('C5B8'), '7-IEDR'), CURDATE() - interval 2 month, CONCAT('XX', UNHEX('C5B8'), '7-IEDR'), 'A'),
(NULL, CONCAT('XYY', UNHEX('CC88'), '8-IEDR'), 'Name for UTF-8 Test', 00000798, 'DGMR Ltd.', 'Oeuju Street', 'Co. Dublin', 'Ireland', 'email@server.xxx', 'Active', '2008-08-13', '2008-08-13', '2008-08-13 16:32:38', '', 'Ugmhk', CONCAT('XYY', UNHEX('CC88'), '8-IEDR'), CURDATE() - interval 2 month, CONCAT('XYY', UNHEX('CC88'), '8-IEDR'), 'A'),
(NULL, 'YYY9-IEDR', CONCAT('I', UNHEX('CC80'), 'm', UNHEX('CCA3'), 'b', UNHEX('CC87'), 'u', UNHEX('CC81'), 'e', UNHEX('CC8F'), 'l'), 00000799, CONCAT('D', UNHEX('CCA3'), 'G', UNHEX('CC82'), 'M', UNHEX('CCA3'), 'R', UNHEX('CC8C'), ' Ltd.'), CONCAT('O', UNHEX('CC8B'), 'e', UNHEX('CC91'), 'u', UNHEX('CCA3'), 'j', UNHEX('CC82'), 'u', UNHEX('CC8F'), ' Street'), CONCAT('Unnormalized cou', UNHEX('CC81'), 'nt', UNHEX('CCA7'), 'y'), CONCAT('Unnormalized co', UNHEX('CC82'), 'un', UNHEX('CC87'), 'try'), CONCAT('e', UNHEX('CCA3'), 'm', UNHEX('CC81'), 'a', UNHEX('CCA8'), 'i', UNHEX('CC89'), 'l', UNHEX('CCAD'), '@server.xxx'), 'Active', '2008-08-13', '2008-08-13', '2008-08-13 16:32:38', '', CONCAT('U', UNHEX('CC84'), 'g', UNHEX('CCA7'), 'm', UNHEX('CCA3'), 'h', UNHEX('CCB1'), 'k', UNHEX('CC8C')), CONCAT('XYY', UNHEX('CC88'), '8-IEDR'), CURDATE() - interval 2 month, CONCAT('XYY', UNHEX('CC88'), '8-IEDR'), 'A');

INSERT INTO `Account` VALUES
('00000797', CONCAT('Normalized Regi', UNHEX('E1B9A1'), 'tra', UNHEX('E1B99F')), CONCAT('XX', UNHEX('C5B8'), '7-IEDR'), CONCAT(UNHEX('C590C887E1BBA5C4B5C895'), ' Street'), CONCAT('Normalized co', UNHEX('C3BA'), 'n', UNHEX('C5A3'), 'y'), CONCAT('Normalized c', UNHEX('C3B4'), 'u', UNHEX('E1B985'), 'try'), CONCAT(UNHEX('E1BA81E1BA89E1BA98'), '.server.xxx'), CONCAT('Tele', UNHEX('E1B995'), 'h', UNHEX ('C6A1'), 'ne'), CONCAT('Tele', UNHEX('E1B89F'), 'a', UNHEX ('E1BA8D')), 'Active', CURDATE() - interval 3 month, CONCAT('Normalized ta', UNHEX('C891E1BB89'), 'ff'), 0, CURDATE() - interval 3 month, CURDATE() - interval 2 month, CONCAT('Fr', UNHEX('C493'), 'q'), CONCAT('M', UNHEX('C7AB'), 'n', UNHEX('C5A3'), 'h'), UNHEX('E1BA99C49FE1B981C89FE1B8B5'), 'A'),
('00000798', CONCAT('Unnormalized-nm Registrar'), CONCAT('XYY', UNHEX('CC88'), '8-IEDR'), 'Oeuju Street', 'Co. Dublin', 'Ireland', 'www.server.xxx', '1234', '12345', 'Active', CURDATE() - interval 3 month, 'ASCII-charred tariff', 0, CURDATE() - interval 3 month, CURDATE() - interval 2 month, 'Freq', 'Month', 'Ygmhk', 'A'),
('00000799', CONCAT('Unnormalized-dt Regis', UNHEX('CC87'), 'trar', UNHEX('CCB1')), 'YYY9-IEDR', CONCAT('O', UNHEX('CC8B'), 'e', UNHEX('CC91'), 'u', UNHEX('CCA3'), 'j', UNHEX('CC82'), 'u', UNHEX('CC8F'), ' Street'), CONCAT('Unnormalized cou', UNHEX('CC81'), 'nt', UNHEX('CCA7'), 'y'), CONCAT('Unnormalized co', UNHEX('CC82'), 'un', UNHEX('CC87'), 'try'), CONCAT('w', UNHEX('CC80'), 'w', UNHEX('CCA3'), 'w', UNHEX('CC8A'), '.server.xxx'), CONCAT('Telep', UNHEX('CC81'), 'ho', UNHEX ('CC9B'), 'ne'), CONCAT('Telef', UNHEX('CC87'), 'ax', UNHEX ('CC88')), 'Active', CURDATE() - interval 3 month, CONCAT('Unnormalized tar', UNHEX('CC8F'), 'i', UNHEX('CC89'), 'ff'), 0, CURDATE() - interval 3 month, CURDATE() - interval 2 month, CONCAT('Fre', UNHEX('CC84'), 'q'), CONCAT('Mo', UNHEX('CCA8'), 'nt', UNHEX('CCA7'), 'h'), CONCAT('y', UNHEX('CC8A'), 'g', UNHEX('CC86'), 'm', UNHEX('CC87'), 'h', UNHEX('CC8C'), 'k', UNHEX('CCB1')), 'A');

INSERT INTO `AccountHist` VALUES
(NULL, '00000797', CONCAT('Normalized Regi', UNHEX('E1B9A1'), 'tra', UNHEX('E1B99F')), CONCAT('XX', UNHEX('C5B8'), '7-IEDR'), CONCAT(UNHEX('C590C887E1BBA5C4B5C895'), ' Street'), CONCAT('Normalized co', UNHEX('C3BA'), 'n', UNHEX('C5A3'), 'y'), CONCAT('Normalized c', UNHEX('C3B4'), 'u', UNHEX('E1B985'), 'try'), CONCAT(UNHEX('E1BA81E1BA89E1BA98'), '.server.xxx'), CONCAT('Tele', UNHEX('E1B995'), 'h', UNHEX ('C6A1'), 'ne'), CONCAT('Tele', UNHEX('E1B89F'), 'a', UNHEX ('E1BA8D')), 'Active', CURDATE() - interval 3 month, CONCAT('Previous normalized ta', UNHEX('C891E1BB89'), 'ff'), 0, CURDATE() - interval 3 month, CURDATE() - interval 3 month, CONCAT('Fr', UNHEX('C493'), 'q'), CONCAT('M', UNHEX('C7AB'), 'n', UNHEX('C5A3'), 'h'), UNHEX('C5AAC4A3E1B983E1BA96C7A9'), CURDATE() - interval 2 month, CONCAT('XX', UNHEX('C5B8'), '7-IEDR')),
(NULL, '00000798', CONCAT('Unnormalized-nm Registrar'), CONCAT('XYY', UNHEX('CC88'), '8-IEDR'), 'Oeuju Street', 'Co. Dublin', 'Ireland', 'www.server.xxx', '1234', '12345', 'Active', CURDATE() - interval 3 month, 'Previous ASCII-charred tariff', 0, CURDATE() - interval 3 month, CURDATE() - interval 3 month, 'Freq', 'Month', 'Ugmhk', CURDATE() - interval 2 month, CONCAT('XYY', UNHEX('CC88'), '8-IEDR')),
(NULL, '00000799', CONCAT('Unnormalized-dt Regis', UNHEX('CC87'), 'trar', UNHEX('CCB1')), 'YYY9-IEDR', CONCAT('O', UNHEX('CC8B'), 'e', UNHEX('CC91'), 'u', UNHEX('CCA3'), 'j', UNHEX('CC82'), 'u', UNHEX('CC8F'), ' Street'), CONCAT('Unnormalized cou', UNHEX('CC81'), 'nt', UNHEX('CCA7'), 'y'), CONCAT('Unnormalized co', UNHEX('CC82'), 'un', UNHEX('CC87'), 'try'), CONCAT('w', UNHEX('CC80'), 'w', UNHEX('CCA3'), 'w', UNHEX('CC8A'), '.server.xxx'), CONCAT('Telep', UNHEX('CC81'), 'ho', UNHEX ('CC9B'), 'ne'), CONCAT('Telef', UNHEX('CC87'), 'ax', UNHEX ('CC88')), 'Active', CURDATE() - interval 3 month, CONCAT('Previous unnormalized tar', UNHEX('CC8F'), 'i', UNHEX('CC89'), 'ff'), 0, CURDATE() - interval 3 month, CURDATE() - interval 3 month, CONCAT('Fre', UNHEX('CC84'), 'q'), CONCAT('Mo', UNHEX('CCA8'), 'nt', UNHEX('CCA7'), 'h'), CONCAT('U', UNHEX('CC84'), 'g', UNHEX('CCA7'), 'm', UNHEX('CCA3'), 'h', UNHEX('CCB1'), 'k', UNHEX('CC8C')), CURDATE() - interval 2 month, CONCAT('XYY', UNHEX('CC88'), '8-IEDR'));

INSERT INTO `Domain` VALUES
-- normalized - to test searching with criteria including UTF-8 values
(CONCAT('normalized-correct-', UNHEX('C3B5E1BABB'), '.ie'), CONCAT('Normalized ', UNHEX('C4A4E1BB8D'), 'lder'), CONCAT('Normalized Cl', UNHEX('C3A3'), 'ss'), CONCAT('Normalized Cate', UNHEX('C7B5C58D'), 'ry'), 00000797, NULL, NULL, '2005-08-29', '2010-08-29', '2008-08-11 15:34:21', 'N', '', 'N', UNHEX('C8B2C4A3E1B983E1BA96C7A9'), 'N', 17, NULL, NULL, NULL, NULL, CONCAT(UNHEX('E1B89E'), '9', UNHEX('E1B99C'), '9VO'), CURDATE() + interval 12 day, NULL),
-- with unnormalized name - to test normalizing name after performing "find" by prefix
(CONCAT('unnormalized-name-o', UNHEX('CC83'), 'e', UNHEX('CC89'), '.ie'), 'ASCII-charred Holder', 'Body Corporate (Ltd,PLC,Company)', 'Registered Business Name', 00000001, NULL, NULL, '2005-08-29', '2010-08-29', '2008-08-11 15:34:21', 'N', '', 'N', 'Ygmhk', 'N', 17, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
-- with unnormalized details - to test normalizing after getting the domain by name
('unnormalized-details.ie', CONCAT('Unnormalized H', UNHEX('CC82'), 'o', UNHEX('CCA3'), 'lder'), CONCAT('Unnormalized Cla', UNHEX('CC83'), 'ss'), CONCAT('Unnormalized Categ', UNHEX('CC81'), 'o', UNHEX('CC84'), 'ry'), 00000799, NULL, NULL, '2005-08-29', '2010-08-29', '2008-08-11 15:34:21', 'N', '', 'N', CONCAT('Y', UNHEX('CC84'), 'g', UNHEX('CCA7'), 'm', UNHEX('CCA3'), 'h', UNHEX('CCB1'), 'k', UNHEX('CC8C')), 'N', 17, NULL, NULL, NULL, NULL, CONCAT('F', UNHEX('CC87'), '9R', UNHEX('CCA3CC84'), '9VO'), CURDATE() + interval 12 day, NULL);

INSERT INTO `DomainHist` VALUES
-- historical records of existing domains - to test queries on historical data (normalized, with unnormalized name, with unnormalized details respectively)
(NULL, CONCAT('normalized-correct-', UNHEX('C3B5E1BABB'), '.ie'), CONCAT('Previous normalized ', UNHEX('C4A4E1BB8D'), 'lder'), CONCAT('Normalized Cl', UNHEX('C3A3'), 'ss'), CONCAT('Normalized Cate', UNHEX('C7B5C58D'), 'ry'), 00000797, NULL, CURDATE() - interval 2 month, '2005-08-29', '2010-08-29', '2008-08-11 15:34:21', 'N', '', 'N', UNHEX('C5AAC4A3E1B983E1BA96C7A9'), CONCAT('XX', UNHEX('C5B8'), '7-IEDR'), CURDATE() - interval 2 month, 'N', 17, NULL, NULL, CURDATE() - interval 2 month, NULL, NULL, NULL, NULL),
(NULL, CONCAT('unnormalized-name-o', UNHEX('CC83'), 'e', UNHEX('CC89'), '.ie'), 'Previous ASCII-charred Holder', 'Body Corporate (Ltd,PLC,Company)', 'Registered Business Name', 00000001, NULL, CURDATE() - interval 2 month, '2005-08-29', '2010-08-29', '2008-08-11 15:34:21', 'N', '', 'N', 'Ugmhk', 'AAA22-IEDR', CURDATE() - interval 2 month, 'N', 17, NULL, NULL, CURDATE() - interval 2 month, NULL, NULL, NULL, NULL),
(NULL, 'unnormalized-details.ie', CONCAT('Previous unnormalized H', UNHEX('CC82'), 'o', UNHEX('CCA3'), 'lder'), CONCAT('Unnormalized Cla', UNHEX('CC83'), 'ss'), CONCAT('Unnormalized Categ', UNHEX('CC81'), 'o', UNHEX('CC84'), 'ry'), 00000799, NULL, CURDATE() - interval 2 month, '2005-08-29', '2010-08-29', '2008-08-11 15:34:21', 'N', '', 'N', CONCAT('U', UNHEX('CC84'), 'g', UNHEX('CCA7'), 'm', UNHEX('CCA3'), 'h', UNHEX('CCB1'), 'k', UNHEX('CC8C')), CONCAT('XYY', UNHEX('CC88'), '8-IEDR'), CURDATE() - interval 2 month, 'N', 17, NULL, NULL, CURDATE() - interval 2 month, NULL, NULL, NULL, NULL),
-- historical records of deleted domains - to test deleted domain queries
(NULL, CONCAT('normalized-deleted-', UNHEX('C3B5E1BABB'), '.ie'), CONCAT('Normalized ', UNHEX('C4A4E1BB8D'), 'lder'), 'Body Corporate (Ltd,PLC,Company)', 'Registered Business Name', 00000001, NULL, CURDATE() - interval 2 month, '2005-08-29', '2010-08-29', '2008-08-11 15:34:21', 'N', '', 'N', UNHEX('C8B2C4A3E1B983E1BA96C7A9'), CONCAT('XX', UNHEX('C5B8'), '7-IEDR'), CURDATE() - interval 2 month, 'N', 387, NULL, NULL, CURDATE() - interval 2 month, NULL, NULL, NULL, NULL),
(NULL, CONCAT('unnormalized-deleted1-o', UNHEX('CC83'), 'e', UNHEX('CC89'), '.ie'), CONCAT('Unnormalized H', UNHEX('CC82'), 'o', UNHEX('CCA3'), 'lder'), CONCAT('Unnormalized Cla', UNHEX('CC83'), 'ss'), CONCAT('Unnormalized Categ', UNHEX('CC81'), 'o', UNHEX('CC84'), 'ry'), 00000001, NULL, CURDATE() - interval 2 month, '2005-08-29', '2010-08-29', '2008-08-11 15:34:21', 'N', '', 'N', CONCAT('Y', UNHEX('CC84'), 'g', UNHEX('CCA7'), 'm', UNHEX('CCA3'), 'h', UNHEX('CCB1'), 'k', UNHEX('CC8C')), CONCAT('XY', 'Y', UNHEX('CC88'), '8-IEDR'), CURDATE() - interval 2 month, 'N', 387, NULL, NULL, CURDATE() - interval 2 month, NULL, NULL, NULL, NULL),
(NULL, CONCAT('unnormalized-deleted2-o', UNHEX('CC83'), 'e', UNHEX('CC89'), '.ie'), CONCAT('Unnormalized H', UNHEX('CC82'), 'o', UNHEX('CCA3'), 'lder'), 'Body Corporate (Ltd,PLC,Company)', 'Registered Business Name', 00000001, NULL, CURDATE() - interval 2 month, '2005-08-29', '2010-08-29', '2008-08-11 15:34:21', 'N', '', 'N', CONCAT('Y', UNHEX('CC84'), 'g', UNHEX('CCA7'), 'm', UNHEX('CCA3'), 'h', UNHEX('CCB1'), 'k', UNHEX('CC8C')),'YYY9-IEDR', CURDATE() - interval 2 month, 'N', 387, NULL, NULL, CURDATE() - interval 2 month, NULL, NULL, NULL, NULL);

INSERT INTO `DNS` VALUES
(CONCAT('normalized-correct-', UNHEX('C3B5E1BABB'), '.ie'), CONCAT(UNHEX('C584E1B9A1'), '1.n', UNHEX('C88D'), 'rmali', UNHEX('E1BA91'), 'ed.ie'), '', '', 1),
(CONCAT('normalized-correct-', UNHEX('C3B5E1BABB'), '.ie'), CONCAT(UNHEX('C588C59F'), '2.n', UNHEX('C88F'), 'rmali', UNHEX('C5BE'), 'ed.ie'), '', '', 2),
(CONCAT('unnormalized-name-o', UNHEX('CC83'), 'e', UNHEX('CC89'), '.ie'), 'ns1.latin-charred.ie', '', '', 1),
(CONCAT('unnormalized-name-o', UNHEX('CC83'), 'e', UNHEX('CC89'), '.ie'), 'ns2.latin-charred.ie', '', '', 2),
('unnormalized-details.ie', CONCAT('n', UNHEX('CC81'), 's', UNHEX('CC87'), '1.unno', UNHEX('CC8F'), 'rmaliz', UNHEX('CC82'), 'ed.ie'), '', '', 1),
('unnormalized-details.ie', CONCAT('n', UNHEX('CC8C'), 's', UNHEX('CCA7'), '2.unno', UNHEX('CC91'), 'rmaliz', UNHEX('CC87'), 'ed.ie'), '', '', 2);

INSERT INTO `Contact` VALUES
(CONCAT('normalized-correct-', UNHEX('C3B5E1BABB'), '.ie'), CONCAT('XX', UNHEX('C5B8'), '7-IEDR'), 'A'),
(CONCAT('normalized-correct-', UNHEX('C3B5E1BABB'), '.ie'), CONCAT('XX', UNHEX('C5B8'), '7-IEDR'), 'B'),
(CONCAT('normalized-correct-', UNHEX('C3B5E1BABB'), '.ie'), CONCAT('XX', UNHEX('C5B8'), '7-IEDR'), 'C'),
(CONCAT('normalized-correct-', UNHEX('C3B5E1BABB'), '.ie'), CONCAT('XX', UNHEX('C5B8'), '7-IEDR'), 'T'),
(CONCAT('unnormalized-name-o', UNHEX('CC83'), 'e', UNHEX('CC89'), '.ie'), 'AAA22-IEDR', 'A'),
(CONCAT('unnormalized-name-o', UNHEX('CC83'), 'e', UNHEX('CC89'), '.ie'), 'AAA22-IEDR', 'B'),
(CONCAT('unnormalized-name-o', UNHEX('CC83'), 'e', UNHEX('CC89'), '.ie'), 'AAA22-IEDR', 'C'),
(CONCAT('unnormalized-name-o', UNHEX('CC83'), 'e', UNHEX('CC89'), '.ie'), 'AAA22-IEDR', 'T'),
('unnormalized-details.ie', CONCAT('XY', 'Y', UNHEX('CC88'), '8-IEDR'), 'A'),
('unnormalized-details.ie', 'YYY9-IEDR', 'B'),
('unnormalized-details.ie', 'YYY9-IEDR', 'C'),
('unnormalized-details.ie', 'YYY9-IEDR', 'T');

INSERT INTO `ContactHist` VALUES
((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` LIKE 'normalized-correct%'), CONCAT('normalized-correct-', UNHEX('C3B5E1BABB'), '.ie'), CONCAT('XX', UNHEX('C5B8'), '7-IEDR'), 'A'),
((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` LIKE 'normalized-correct%'), CONCAT('normalized-correct-', UNHEX('C3B5E1BABB'), '.ie'), CONCAT('XX', UNHEX('C5B8'), '7-IEDR'), 'B'),
((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` LIKE 'normalized-correct%'), CONCAT('normalized-correct-', UNHEX('C3B5E1BABB'), '.ie'), CONCAT('XX', UNHEX('C5B8'), '7-IEDR'), 'C'),
((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` LIKE 'normalized-correct%'), CONCAT('normalized-correct-', UNHEX('C3B5E1BABB'), '.ie'), CONCAT('XX', UNHEX('C5B8'), '7-IEDR'), 'T'),
((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` LIKE 'unnormalized-name%'), CONCAT('unnormalized-name-o', UNHEX('CC83'), 'e', UNHEX('CC89'), '.ie'), 'AAA22-IEDR', 'A'),
((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` LIKE 'unnormalized-name%'), CONCAT('unnormalized-name-o', UNHEX('CC83'), 'e', UNHEX('CC89'), '.ie'), 'AAA22-IEDR', 'B'),
((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` LIKE 'unnormalized-name%'), CONCAT('unnormalized-name-o', UNHEX('CC83'), 'e', UNHEX('CC89'), '.ie'), 'AAA22-IEDR', 'C'),
((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` LIKE 'unnormalized-name%'), CONCAT('unnormalized-name-o', UNHEX('CC83'), 'e', UNHEX('CC89'), '.ie'), 'AAA22-IEDR', 'T'),
((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` LIKE 'unnormalized-details%'), 'unnormalized-details.ie', CONCAT('XY', 'Y', UNHEX('CC88'), '8-IEDR'), 'A'),
((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` LIKE 'unnormalized-details%'), 'unnormalized-details.ie', 'YYY9-IEDR', 'B'),
((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` LIKE 'unnormalized-details%'), 'unnormalized-details.ie', 'YYY9-IEDR', 'C'),
((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` LIKE 'unnormalized-details%'), 'unnormalized-details.ie', 'YYY9-IEDR', 'T'),
((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` LIKE 'normalized-deleted%'), CONCAT('normalized-deleted-', UNHEX('C3B5E1BABB'), '.ie'), CONCAT('XX', UNHEX('E1BA8C'), '7-IEDR'), 'A'),
((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` LIKE 'normalized-deleted%'), CONCAT('normalized-deleted-', UNHEX('C3B5E1BABB'), '.ie'), CONCAT('XX', UNHEX('E1BA8C'), '7-IEDR'), 'B'),
((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` LIKE 'normalized-deleted%'), CONCAT('normalized-deleted-', UNHEX('C3B5E1BABB'), '.ie'), CONCAT('XX', UNHEX('E1BA8C'), '7-IEDR'), 'C'),
((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` LIKE 'normalized-deleted%'), CONCAT('normalized-deleted-', UNHEX('C3B5E1BABB'), '.ie'), CONCAT('XX', UNHEX('E1BA8C'), '7-IEDR'), 'T'),
((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` LIKE 'unnormalized-deleted1%'), CONCAT('unnormalized-deleted1-o', UNHEX('CC83'), 'e', UNHEX('CC89'), '.ie'), CONCAT('XY', 'Y', UNHEX('CC88'), '8-IEDR'), 'A'),
((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` LIKE 'unnormalized-deleted1%'), CONCAT('unnormalized-deleted1-o', UNHEX('CC83'), 'e', UNHEX('CC89'), '.ie'), CONCAT('XY', 'Y', UNHEX('CC88'), '8-IEDR'), 'B'),
((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` LIKE 'unnormalized-deleted1%'), CONCAT('unnormalized-deleted1-o', UNHEX('CC83'), 'e', UNHEX('CC89'), '.ie'), CONCAT('XY', 'Y', UNHEX('CC88'), '8-IEDR'), 'C'),
((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` LIKE 'unnormalized-deleted1%'), CONCAT('unnormalized-deleted1-o', UNHEX('CC83'), 'e', UNHEX('CC89'), '.ie'), CONCAT('XY', 'Y', UNHEX('CC88'), '8-IEDR'), 'T'),
((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` LIKE 'unnormalized-deleted2%'), CONCAT('unnormalized-deleted2-o', UNHEX('CC83'), 'e', UNHEX('CC89'), '.ie'), 'YYX9-IEDR', 'A'),
((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` LIKE 'unnormalized-deleted2%'), CONCAT('unnormalized-deleted2-o', UNHEX('CC83'), 'e', UNHEX('CC89'), '.ie'), 'YYX9-IEDR', 'B'),
((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` LIKE 'unnormalized-deleted2%'), CONCAT('unnormalized-deleted2-o', UNHEX('CC83'), 'e', UNHEX('CC89'), '.ie'), 'YYX9-IEDR', 'C'),
((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` LIKE 'unnormalized-deleted2%'), CONCAT('unnormalized-deleted2-o', UNHEX('CC83'), 'e', UNHEX('CC89'), '.ie'), 'YYX9-IEDR', 'T');

INSERT INTO `TopLevelDomain` VALUES
(CONCAT('UNNOR', UNHEX('CC91'), 'MALIZ', UNHEX('CC8C'), 'ED'));

INSERT INTO `Account`
(`A_Number`, `A_Name`, `Billing_NH`, `Address`, `County`, `Country`, `Web_Address`, `Phone`, `Fax`, `A_Status`, `A_Status_Dt`, `A_Tariff`, `Credit_Limit`, `A_Reg_Dt`, `A_TStamp`, `Invoice_Freq`, `Next_Invoice_Month`, `A_Remarks`, `Vat_Category`)
VALUES
('00000990',
CONCAT('Irish Doma', UNHEX('CC88'), 'ins'),
CONCAT('IDL2-IE', UNHEX('CC88'), 'DP'),
CONCAT('ACA', UNHEX('CC88'), 'ddress000052'),
CONCAT('Co', UNHEX('CC88'), '. Dublin'),
CONCAT('Ire', UNHEX('CC88'), 'land'),
CONCAT('www.doma', UNHEX('CC88'), 'inaddress.xxx'),
CONCAT('pho', UNHEX('CC88'), 'ne'),
CONCAT('fa', UNHEX('CC88'), 'x'),
'Active',
'2002-09-07',
'TradeAChar',
'0.00',
'2002-09-07',
'2014-03-13 10:10:24',
CONCAT('Mo', UNHEX('CC88'), 'nthly'),
CONCAT('Ja', UNHEX('CC88'), 'nuary'),
CONCAT('Te', UNHEX('CC88'), 'st Change'),
'A'),
('00000991',
'Irish Domäins',
'IDË2-IEDR',
'ACÄddress000052',
'Cö. Dublin',
'Irëland',
'www.domäinaddress.xxx',
'phöne',
'fäx',
'Active',
'2002-09-07',
'TradeAChar',
'0.00',
'2002-09-07',
'2014-03-13 10:10:24',
'Mönthly',
'Jänuary',
'Tëst Change',
'A');

INSERT INTO `Bulk_Transfer`
(`ID`, `Losing_Account`, `Gaining_Account`, `Remarks`)
VALUES
('990', '00000101', '00000990', CONCAT('Unnormalized re', UNHEX('CC88'), 'mark')),
('991', '00000101', '00000991', 'Normalized rëmark');

INSERT INTO `Bulk_Transfer_Domain`
(`Bulk_Transfer_ID`, `Domain_Name`)
VALUES
('990', CONCAT('www.doma', UNHEX('CC88'), 'inaddress.xxx')),
('991', 'www.domäinaddress.xxx');

INSERT INTO `Counties`
(`CountryCode`, `County`)
VALUES
(121, CONCAT('Co. Du', UNHEX('CC88'), 'blin1')),
(121, 'Cö. Dooblin');
INSERT INTO `Countries`
(`CountryCode`, `Country`, `vat_category`)
VALUES
(300, CONCAT('Ire', UNHEX('CC88'), 'land1'), 'A'),
(301, 'Irealänd', 'A');

INSERT INTO `INCOMING_DOC` VALUES
(20, '2015-01-01 09:00:00', 'fax', CONCAT('fa', UNHEX('CC88'), 'x001.tif'), 'Bill-C Transfer', CONCAT('Indigo', UNHEX('CC88'), ' NOC'), '00000990', CONCAT('IDL2-IE', UNHEX('CC88'), 'DP')),
(21, '2015-01-01 09:00:00', 'fax', 'fäx002.tif', 'Bill-C Transfer', 'Indigä NOC', '00000991', 'IDË2-IEDR');
INSERT INTO `INCOMING_DOC_DOMAINS` (`DOC_ID`, `DOMAIN_NAME`) VALUES
(20, CONCAT('ns1.domainnamea', UNHEX('CC88'), '.ie')),
(20, CONCAT('ns2.domainnamea', UNHEX('CC88'), '.ie')),
(21, "ns1.domainnameä.ie"),
(21, "ns2.domainnameä.ie");

INSERT INTO `Email` VALUES
(950,
CONCAT('Unnormalize', UNHEX('CC88'), 'd test email'),
CONCAT('Te', UNHEX('CC88'), 'st content'),
CONCAT('Te', UNHEX('CC88'), 'st subject'),
CONCAT('to@do', UNHEX('CC88'), 'main.ie'),
CONCAT('cc@do', UNHEX('CC88'), 'main.ie'),
CONCAT('bcc@do', UNHEX('CC88'), 'main.ie'),
CONCAT('fro', UNHEX('CC88'), 'm@domain.ie'),
'1', '0',
CONCAT('ito@do', UNHEX('CC88'), 'main.ie'),
CONCAT('icc@do', UNHEX('CC88'), 'main.ie'),
CONCAT('ibcc@do', UNHEX('CC88'), 'main.ie'),
'5', '2015-03-13 14:44:08', 'Y',
CONCAT('Se', UNHEX('CC88'), 'nd reason'),
CONCAT('No', UNHEX('CC88'), 'n-suppressible reason'), 'N'),
(951,
'Nörmalized test email',
'Tëst content',
'Tëst subject',
'to@dömain.ie',
'cc@dömain.ie',
'bcc@dömain.ie',
'from@dömain.ie',
'1', '0',
'ito@dömain.ie',
'icc@dömain.ie',
'ibcc@dömain.ie',
'5', '2015-03-13 14:44:08', 'Y',
'Sënd reason',
'Non-supprëssible reason', 'N');

INSERT INTO `EmailGroup`
(`EG_ID`, `EG_Name`, `EG_Visible`)
VALUES
(100, CONCAT('Unno', UNHEX('CC88'), 'rmalized group'), 'N'),
(101, 'Nörmalized group', 'N');

INSERT INTO `EmailDisabler` VALUES
(950,CONCAT('IDL2-IE', UNHEX('CC88'), 'DP'),'Y','2014-04-10 19:02:59');
INSERT INTO `EmailDisabler` VALUES
(951,'IDË2-IEDR','Y','2014-04-10 19:03:07');

INSERT INTO EmailDisablerHist (ED_Email_E_ID, ED_Nic_Handle, ED_Disabled, ED_TS, Chng_NH, Chng_Dt)
SELECT
    ED_Email_E_ID, ED_Nic_Handle, ED_Disabled, ED_TS, CONCAT('IDL2-IE', UNHEX('CC88'), 'DP'), '2015-01-01 09:00'
FROM EmailDisabler
WHERE
    ED_Email_E_ID = 950
AND
    ED_Nic_Handle = CONCAT('IDL2-IE', UNHEX('CC88'), 'DP');

INSERT INTO EmailDisablerHist (ED_Email_E_ID, ED_Nic_Handle, ED_Disabled, ED_TS, Chng_NH, Chng_Dt)
SELECT
    ED_Email_E_ID, ED_Nic_Handle, ED_Disabled, ED_TS, 'IDË2-IEDR', '2015-01-01 09:00'
FROM EmailDisabler
WHERE
    ED_Email_E_ID = 951
AND
    ED_Nic_Handle = 'IDË2-IEDR';

INSERT INTO EmailHist (E_ID, E_Name, E_Text, E_Subject, E_To, E_CC, E_BCC, E_To_Internal, E_CC_Internal, E_BCC_Internal, E_From, active, Html, E_Suppressible, E_Send_Reason, E_Non_Suppressible_Reason, EG_ID, Chng_NH, Chng_Dt)
SELECT
    E_ID,
    E_Name,
    E_Text,
    E_Subject,
    E_To,
    E_CC,
    E_BCC,
    E_To_Internal,
    E_CC_Internal,
    E_BCC_Internal,
    E_From,
    active,
    Html,
    E_Suppressible,
    E_Send_Reason,
    E_Non_Suppressible_Reason,
    EG_ID,
    CONCAT('IDL2-IE', UNHEX('CC88'), 'DP'),
    '2015-01-01 09:00'
FROM Email
WHERE E_ID = 950;

INSERT INTO EmailHist (E_ID, E_Name, E_Text, E_Subject, E_To, E_CC, E_BCC, E_To_Internal, E_CC_Internal, E_BCC_Internal, E_From, active, Html, E_Suppressible, E_Send_Reason, E_Non_Suppressible_Reason, EG_ID, Chng_NH, Chng_Dt)
SELECT
    E_ID,
    E_Name,
    E_Text,
    E_Subject,
    E_To,
    E_CC,
    E_BCC,
    E_To_Internal,
    E_CC_Internal,
    E_BCC_Internal,
    E_From,
    active,
    Html,
    E_Suppressible,
    E_Send_Reason,
    E_Non_Suppressible_Reason,
    EG_ID,
    'IDË2-IEDR',
    '2015-01-01 09:00'
FROM Email
WHERE E_ID = 951;

INSERT INTO EmailGroupHist (EG_ID, EG_Name, EG_Visible, EG_TS, Chng_NH, Chng_Dt)
SELECT
    EG_ID,
    EG_Name,
    EG_Visible,
    EG_TS,
    CONCAT('IDL2-IE', UNHEX('CC88'), 'DP'),
    '2015-01-01 09:00'
FROM EmailGroup
WHERE EG_ID = 100;

INSERT INTO EmailGroupHist (EG_ID, EG_Name, EG_Visible, EG_TS, Chng_NH, Chng_Dt)
SELECT
    EG_ID,
    EG_Name,
    EG_Visible,
    EG_TS,
    'IDË2-IEDR',
    '2015-01-01 09:00'
FROM EmailGroup
WHERE EG_ID = 101;

INSERT INTO `login_attempts` (`nic`, `client_ip`) VALUES
(CONCAT('IDE', UNHEX('CC88'), '2-IEDR-LAU'), CONCAT('unno', UNHEX('CC88'), 'rmalized_ip')),
('IDË2-IEDR-LAN', 'nörmalized_ip');

INSERT INTO `DNS_Check_Notification`
(`ID`, `Domain_Name`, `DNS_Name`, `Error_Message`, `Nic_Handle`, `Email`)
VALUES
(10, CONCAT('unormno', UNHEX('CC88'), 'tifdomaine.ie'), CONCAT('ns.unormno', UNHEX('CC88'), 'tifdomaine.ie'), CONCAT('unorm no', UNHEX('CC88'), 'tif error'), CONCAT('IDL2-IE', UNHEX('CC88'), 'DP'), CONCAT('unormno', UNHEX('CC88'), 'tif@domain.ie')),
(11, 'normnötifdomaine.ie', 'ns.normnötifdomaine.ie', 'norm nötif error', 'IDË2-IEDR', 'normnötif@domain.ie');
INSERT INTO `DNS_Check_Notification_Date`
(`Nic_Handle`, `Next_Notification_Date`)
VALUES
(CONCAT('IDL2-IE', UNHEX('CC88'), 'DP'), '2015-01-01 9:00'),
('IDË2-IEDR', '2015-01-01 9:01');

-- Test data for CRS-604 --

INSERT INTO `NicHandle`(`Nic_Handle`, `NH_Name`, `A_Number`, `Co_Name`, `NH_Address`, `NH_County`, `NH_Country`, `NH_Email`, `NH_Status`, `NH_Status_Dt`, `NH_Reg_Dt`, `NH_TStamp`, `NH_BillC_Ind`, `NH_Remark`, `NH_Creator`, `Vat_Category`) VALUES
-- Unnormalized NH, unnormalized NH_Name
(CONCAT('A',UNHEX('CC88'),'IDL2-IEDR'),CONCAT('A',UNHEX('CC88'),'IRISH DOMAINS LTD'),00000101,'ACCOUNTS','NHAddress000901','Co. Dublin','Ireland','NHEmail000901@server.xxx','Active','2006-12-20','2006-12-20','2007-11-14 12:15:09','Y','Sorted name','IDL2-IEDR','A'),
-- Unnormalized NH, normalized NH_Name
('ÜIDL2-IEDR',CONCAT('U',UNHEX('CC88'),'IRISH DOMAINS LTD'),00000101,'ACCOUNTS','NHAddress000901','Co. Dublin','Ireland','NHEmail000901@server.xxx','Active','2006-12-20','2006-12-20','2007-11-14 12:15:09','Y','Sorted name','IDL2-IEDR','A'),
-- Normalized NH, normalized NH_Name
('ËIDL2-IEDR','ËIRISH DOMAINS LTD',00000101,'ACCOUNTS','NHAddress000901','Co. Dublin','Ireland','NHEmail000901@server.xxx','Active','2006-12-20','2006-12-20','2007-11-14 12:15:09','Y','Sorted name','IDL2-IEDR','A'),
-- Normalized NH, normalized NH_Name, no deposit entry
('ÏIDL2-IEDR','ÏIRISH DOMAINS LTD',00000101,'ACCOUNTS','NHAddress000901','Co. Dublin','Ireland','NHEmail000901@server.xxx','Active','2006-12-20','2006-12-20','2007-11-14 12:15:09','Y','Sorted name','IDL2-IEDR','A');

INSERT INTO `Deposit` VALUES
-- Unnormalized Deposit, normalized NH
('ÜIDL2-IEDR','2008-07-08 09:42:05',7000.00,2594.00,CONCAT('U',UNHEX('CC88'),'20101202171646-D-102'),'N',-100.00,'SETTLEMENT',CONCAT('U',UNHEX('CC88'),'IDL2-IEDR'),CONCAT('U',UNHEX('CC88'),'Normalized NicHandle')),
-- Unnormalized Deposit, unnormalized NH
(CONCAT('A',UNHEX('CC88'),'IDL2-IEDR'),'2008-07-08 09:42:05',7000.00,2594.00,CONCAT('A',UNHEX('CC88'),'20101202171646-D-102'),'N',-100.00,'SETTLEMENT',CONCAT('A',UNHEX('CC88'),'IDL2-IEDR'),CONCAT('A',UNHEX('CC88'),'Unnormalized NicHandle')),
-- Normalized Deposit
('ËIDL2-IEDR','2008-07-08 09:42:05',7000.00,2594.00,'Ë20101202171646-D-102','N',-100.00,'SETTLEMENT','ËIDL2-IEDR','ËNormalized Deposit');
INSERT INTO DepositHist(Nic_Handle,Op_Dt,Trans_Dt,Open_Bal,Close_Bal,Order_ID,Notification_Sent,Trans_Amount,Trans_Type,Corrector_NH,Remark) VALUES
-- TopUp with unnormalized order ID
('ËIDL2-IEDR',CURDATE(),CURDATE(),7000.00,2594.00,CONCAT('E',UNHEX('CC88'),'20101202171646-D-102'),'N',100.00,'TOPUP','ËIDL2-IEDR','ËTopup with unnormalized orderId');

INSERT INTO `Product` VALUES
-- Unnormalized product
(CONCAT('A',UNHEX('CC88'),'Std9Year'),'Unused',CONCAT('A',UNHEX('CC88'),'Registration for 9 Years'),40.00,9,1,0,CURDATE()-INTERVAL 1 DAY,CURDATE()+INTERVAL 1 DAY,1,1,1,0),
-- Normalized product
('ÄStd10Year','Unused','ÄRegistration for 10 Years',40.00,10,1,0,CURDATE()-INTERVAL 1 DAY,CURDATE()+INTERVAL 1 DAY,1,1,1,0);

-- Domain for utf payment tests
INSERT INTO `Domain` VALUES (CONCAT('A',UNHEX('CC88'),'paymentutf8daotest.ie'),'API Tester','Body Corporate (Ltd,PLC,Company)','Registered Business Name',00000259,NULL,NULL,'2014-03-13','2015-03-13','2014-03-13 00:00:00','N','','','API tests domain','N',0,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
-- Normalized invoice, hist transaction, hist reservation
INSERT INTO `Invoice` VALUES (999,'ÜTEST999','ÜAPI TESTS',101,'ÜAddress1','ÜAddress2','ÜAddress3','ÜIDL2-IEDR','N','ÜIreland','ÜCo. Dublin','Ü3379',CURDATE(),'ÜMD5',49774,41000,8774);
INSERT INTO `TransactionHist` VALUES (999,'N','N',4960,'N',NULL,NULL,999,4550,410,'ÜorderId999','N',NULL,NULL);
INSERT INTO `ReservationHist` VALUES (999,'ÜIDL2-IEDR','Äpaymentutf8daotest.ie',12,CURDATE(),65.00,3,13.91,'N','N',NULL,256815,999,'ÄStd10Year','registration',NULL,NULL,'Deposit');
-- Unnormalized invoice, hist transaction, hist reservation
INSERT INTO `Invoice` VALUES (998,CONCAT('A',UNHEX('CC88'),'TEST998'),CONCAT('A',UNHEX('CC88'),'API TESTS'),101,CONCAT('A',UNHEX('CC88'),'Address1'),CONCAT('A',UNHEX('CC88'),'Address2'),CONCAT('A',UNHEX('CC88'),'Address3'),CONCAT('A',UNHEX('CC88'),'IDL2-IEDR'),'N',CONCAT('A',UNHEX('CC88'),'Ireland'),CONCAT('A',UNHEX('CC88'),'Co. Dublin'),CONCAT('A',UNHEX('CC88'),'3379'),CURDATE(),CONCAT('A',UNHEX('CC88'),'MD5'),49774,41000,8774);
INSERT INTO `TransactionHist` VALUES (998,'N','N',4960,'N',NULL,NULL,998,4550,410,CONCAT('A',UNHEX('CC88'),'orderId998'),'N',NULL,NULL);
INSERT INTO `ReservationHist` VALUES (998,CONCAT('A',UNHEX('CC88'),'IDL2-IEDR'),CONCAT('A',UNHEX('CC88'),'paymentutf8daotest.ie'),12,CURDATE(),65.00,3,13.91,'N','N',NULL,256815,998,CONCAT('A',UNHEX('CC88'),'Std9Year'),'registration',NULL,NULL,'Deposit');
-- Unnormalized invoice with normalized number, unnormalized hist transaction, hist reservation
INSERT INTO `Invoice` VALUES (997,'ÄTEST997',CONCAT('A',UNHEX('CC88'),'API TESTS'),101,CONCAT('A',UNHEX('CC88'),'Address1'),CONCAT('A',UNHEX('CC88'),'Address2'),CONCAT('A',UNHEX('CC88'),'Address3'),CONCAT('A',UNHEX('CC88'),'IDL2-IEDR'),'N',CONCAT('A',UNHEX('CC88'),'Ireland'),CONCAT('A',UNHEX('CC88'),'Co. Dublin'),CONCAT('A',UNHEX('CC88'),'3379'),CURDATE(),CONCAT('A',UNHEX('CC88'),'MD5'),49774,41000,8774);
INSERT INTO `TransactionHist` VALUES (997,'N','N',4960,'N',NULL,NULL,997,4550,410,CONCAT('A',UNHEX('CC88'),'orderId997'),'N',NULL,NULL);
INSERT INTO `ReservationHist` VALUES (997,CONCAT('A',UNHEX('CC88'),'IDL2-IEDR'),CONCAT('A',UNHEX('CC88'),'paymentutf8daotest.ie'),12,CURDATE(),65.00,3,13.91,'N','N',NULL,256815,997,CONCAT('A',UNHEX('CC88'),'Std9Year'),'registration',NULL,NULL,'Deposit');
-- Normalized Transaction, Reservation
INSERT INTO `Transaction` VALUES (999,'N','N',4960,'N',NULL,NULL,999,4550,410,'ÄorderId999','N',NULL,NULL,'ÄRealexAuthcode','ÄRealexPassref');
INSERT INTO `Reservation` VALUES (999,'ÜIDL2-IEDR','Äpaymentutf8daotest.ie',CURDATE(),65.00,3,13.91,'N',NULL,256815,12,'N',999,'registration','ÄStd10Year',NULL,NULL,'Deposit');
-- Unnormalized Transaction, Reservation
INSERT INTO `Transaction` VALUES (998,'N','N',4960,'N',NULL,NULL,998,4550,410,CONCAT('A',UNHEX('CC88'),'orderId998'),'N',NULL,NULL,CONCAT('A',UNHEX('CC88'),'RealexAuthcode'),CONCAT('A',UNHEX('CC88'),'RealexPassref'));
INSERT INTO `Reservation` VALUES (998,CONCAT('A',UNHEX('CC88'),'IDL2-IEDR'),CONCAT('A',UNHEX('CC88'),'paymentutf8daotest.ie'),CURDATE(),65.00,3,13.91,'N',NULL,256815,12,'N',998,'registration',CONCAT('A',UNHEX('CC88'),'Std9Year'),NULL,NULL,'Deposit');

INSERT INTO `Domain` VALUES ('generate-invoice-test.ie','áąéíóúèçëòôöùàąâäąöüß簡简글あ','Body Corporate (Ltd,PLC,Company)','Registered Business Name',00000259,NULL,NULL,'2014-03-13','2015-03-13','2014-03-13 00:00:00','N','','','API tests domain','N',0,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
-- Normalized invoice, hist transaction, hist reservation
INSERT INTO `Invoice` VALUES (996,'TEST999','API TESTS',101,'ÄAddress1','ÄAddress2','ÄAddress3','IDL2-IEDR','N','ÄIreland','ÄCo. Dublin','Ä3379',CURDATE(),'ÄMD5',49774,41000,8774);
INSERT INTO `TransactionHist` VALUES (996,'N','N',4960,'N',NULL,CURDATE(),996,4550,410,'ÄorderId996','N',NULL,NULL);
INSERT INTO `ReservationHist` VALUES (996,'IDL2-IEDR','generate-invoice-test.ie',12,CURDATE(),65.00,3,13.91,'N','N',NULL,256815,996,'Std1Year','registration',NULL,NULL,'Deposit');

-- CRS-798

INSERT INTO `AccessHist` (`Nic_Handle`, `NH_Password`, `NH_Level`, `Chng_NH`, `Chng_Dt`, `Salt`, `use_two_factor_authentication`, `secret`)
VALUES
(
CONCAT('AA11-IEDR', UNHEX('01')),
CONCAT('t1X7oZ2/bFDr0cyVoH442gyNoNSib/a', UNHEX('01')),
'0',
CONCAT('IDL2-IEDR', UNHEX('01')),
'2015-07-22',
CONCAT('.Z805gDk7mXJJfbcIwZJde', UNHEX('01')),
'N',
CONCAT('a', UNHEX('01'))
);

INSERT INTO `DomainHist` VALUES
(NULL,
CONCAT('badutf8', UNHEX('01'), '.ie'),
CONCAT('Previous bad utf8 Holder', UNHEX('01')),
CONCAT('BadUtf8 Class', UNHEX('01')),
CONCAT('BadUtf8 Category', UNHEX('01')),
00000797, NULL, CURDATE() - interval 2 month, '2005-08-29', '2010-08-29', '2008-08-11 15:34:21', 'N', '', 'N',
CONCAT('Remark', UNHEX('01')),
CONCAT('XXE7-IEDR', UNHEX('01')), CURDATE() - interval 2 month, 'N', 387, NULL, NULL, CURDATE() - interval 2 month, NULL,
CONCAT('authcode', UNHEX('01')), CURDATE() + interval 1 month, NULL);

INSERT INTO `ContactHist` VALUES
((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` LIKE 'badutf8%'), CONCAT('badutf8', UNHEX('01'), '.ie'), CONCAT('XXX7-IEDR', UNHEX('01')), 'A'),
((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` LIKE 'badutf8%'), CONCAT('badutf8', UNHEX('01'), '.ie'), CONCAT('XXX7-IEDR', UNHEX('01')), 'B'),
((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` LIKE 'badutf8%'), CONCAT('badutf8', UNHEX('01'), '.ie'), CONCAT('XXX7-IEDR', UNHEX('01')), 'C'),
((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` LIKE 'badutf8%'), CONCAT('badutf8', UNHEX('01'), '.ie'), CONCAT('XXX7-IEDR', UNHEX('01')), 'T');

INSERT INTO `DNSHist` VALUES
((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` LIKE 'badutf8%'), CONCAT('badutf8', UNHEX('01'), '.ie'), CONCAT('ns1.badutf8', UNHEX('01'), '.ie'), CONCAT('192.168.1.1', UNHEX('01')), CONCAT('::1', UNHEX('01')), '1'),
((SELECT `Chng_ID` FROM `DomainHist` WHERE `D_Name` LIKE 'badutf8%'), CONCAT('badutf8', UNHEX('01'), '.ie'), CONCAT('ns2.badutf8', UNHEX('01'), '.ie'), CONCAT('192.168.1.2', UNHEX('01')), CONCAT('::2', UNHEX('01')), '2');

INSERT INTO `TicketHist` (`T_Number`, `T_Type`, `D_Name`, `DN_Fail_Cd`, `D_Holder`, `DH_Fail_Cd`, `A_Number`, `AC_Fail_Cd`, `T_Class`, `T_Category`, `T_Remark`, `Admin_NH1`, `ANH1_Fail_Cd`, `Admin_NH2`, `ANH2_Fail_Cd`, `Tech_NH`, `TNH_Fail_Cd`, `Bill_NH`, `BNH_Fail_Cd`, `Creator_NH`, `Admin_Status`, `Ad_StatusDt`, `Tech_Status`, `T_Status_Dt`, `CheckedOut`, `CheckedOutTo`, `T_Reg_Dt`, `T_Ren_Dt`, `T_TStamp`, `H_Remark`, `T_Class_Fail_Cd`, `T_Category_Fail_Cd`, `T_Created_TS`, `Opt_Cert`, `T_ClikPaid`, `Period`, `CharityCode`, `Financial_Status`, `F_Status_Dt`, `Customer_Status`, `C_Status_Dt`, `Chng_Dt`, `Chng_NH`) VALUES
(500000,
'R',
CONCAT('badutf8domainname', UNHEX('01'), '.ie'),
0,
CONCAT('BadUTF8 User', UNHEX('01')),
0,
00000001,
0,
CONCAT('BadUTF8 Sole pale class', UNHEX('01')),
CONCAT('BadUTF8 Sole pale category', UNHEX('01')),
CONCAT('BadUTF8 Sole pale remark', UNHEX('01')),
CONCAT('IDL2-IEDR', UNHEX('01')),
0,
null,
0,
CONCAT('IDL2-IEDR', UNHEX('01')),
0,
CONCAT('IDL2-IEDR', UNHEX('01')),
0,
CONCAT('IDL2-IEDR', UNHEX('01')),
0,
'0000-00-00',
0,
'0000-00-00',
'Y',
CONCAT('IDL2-IEDR', UNHEX('01')),
null,
null,
CURRENT_TIMESTAMP,
CONCAT('Sole pale h remark', UNHEX('01')),
0,
0,
'0000-00-00 00:00:00',
'N',
'Y',
1,
CONCAT('charity code', UNHEX('01')),
0,
'0000-00-00 00:00:00',
0,
'0000-00-00 00:00:00',
'2015-01-01',
CONCAT('IDL2-IEDR', UNHEX('01')));

INSERT INTO `TicketNameserverHist`
(`Chng_ID`, `T_Number`, `TN_Name`, `TN_IPv4`, `TN_IPv6`, `TN_Order`)
VALUES
((SELECT `Chng_ID` FROM `TicketHist` WHERE `T_Number` = 500000 LIMIT 1), 500000, CONCAT('ns1.badutf8domainname', UNHEX('01'), '.ie'), CONCAT('192.168.1.1', UNHEX('01')), CONCAT('::1', UNHEX('01')), 1),
((SELECT `Chng_ID` FROM `TicketHist` WHERE `T_Number` = 500000 LIMIT 1), 500000, CONCAT('ns2.badutf8domainname', UNHEX('01'), '.ie'), CONCAT('192.168.1.2', UNHEX('01')), CONCAT('::2', UNHEX('01')), 2);

INSERT INTO `AccountHist` VALUES
(NULL, '00000797',
CONCAT('Bad utf8 Registrar', UNHEX('01')),
CONCAT('XX7-IEDR', UNHEX('01')),
CONCAT('Bad utf8 Street ', UNHEX('01')),
CONCAT('Bad utf8 county', UNHEX('01')),
CONCAT('Bad utf8 country', UNHEX('01')),
CONCAT('web.server.xxx', UNHEX('01')),
CONCAT('phone 00112233', UNHEX('01')),
CONCAT('fax 00112233', UNHEX('01')),
'Active', CURDATE() - interval 3 month,
CONCAT('Bad utf8 tariff', UNHEX('01')),
0,
CURDATE() - interval 3 month, CURDATE() - interval 3 month,
CONCAT('Freq', UNHEX('01')),
CONCAT('Month', UNHEX('01')),
CONCAT('Remark', UNHEX('01')),
CURDATE() - interval 2 month,
CONCAT('XX7-IEDR', UNHEX('01')));

INSERT INTO EmailDisablerHist (Chng_ID, ED_Email_E_ID, ED_Nic_Handle, ED_Disabled, ED_TS, Chng_NH, Chng_Dt)
VALUES (100, 951, CONCAT("IDL2-IEDR", UNHEX('01')), 'Y', '2015-01-01 09:00', CONCAT("IDL2-IEDR", UNHEX('01')), '2015-01-01 09:00');

INSERT INTO EmailGroupHist (EG_ID, EG_Name, EG_Visible, EG_TS, Chng_NH, Chng_Dt)
VALUES (
    100,
    CONCAT("Email group", UNHEX('01')),
    'Y',
    '2015-01-01 09:00',
    CONCAT('IDL2-IEDR', UNHEX('01')),
    '2015-01-01 09:00');

INSERT INTO `EmailHist` (`E_ID`, `E_Name`, `E_Text`, `E_Subject`, `E_To`, `E_CC`, `E_BCC`, `E_From`, `active`, `Html`, `E_To_Internal`, `E_CC_Internal`, `E_BCC_Internal`, `EG_ID`, `E_TS`, `E_Suppressible`, `E_Send_Reason`, `E_Non_Suppressible_Reason`, `Chng_NH`, `Chng_Dt`)
VALUES
(100,
CONCAT('Bad utf8 email', UNHEX('01')),
CONCAT('Bad utf8 email text', UNHEX('01')),
CONCAT('Bad utf8 email subject', UNHEX('01')),
CONCAT('to1@email.com,to2@email', UNHEX('01'), '.com'),
CONCAT('cc1@email.com,cc2@email', UNHEX('01'), '.com'),
CONCAT('bcc1@email.com,bcc2@email', UNHEX('01'), '.com'),
CONCAT('from@email', UNHEX('01'), '.com'),
1,
0,
CONCAT('ito1@email.com,ito2@email', UNHEX('01'), '.com'),
CONCAT('icc1@email.com,icc2@email', UNHEX('01'), '.com'),
CONCAT('ibcc1@email.com,ibcc2@email', UNHEX('01'), '.com'),
100,
CURRENT_TIMESTAMP,
'Y',
CONCAT('Bad utf8 send reason', UNHEX('01')),
CONCAT('Bad utf8 non-supressible reason', UNHEX('01')),
CONCAT('IDL2-IEDR', UNHEX('01')),
CURDATE());

INSERT INTO `NicHandleHist` VALUES
(NULL, CONCAT('XX7-IEDR', UNHEX('01')),
CONCAT('Bad utf8 NH name', UNHEX('01')),
00000797,
CONCAT('Bad utf8 co name', UNHEX('01')),
CONCAT('Bad utf8 address', UNHEX('01')),
CONCAT('Bad utf8 county', UNHEX('01')),
CONCAT('Bad utf8 country', UNHEX('01')),
CONCAT('email', UNHEX('01'), '@server.xxx'),
'Active', '2008-08-13', '2008-08-13', '2008-08-13 16:32:38', '',
CONCAT('Remark', UNHEX('01')),
CONCAT('XX7-IEDR', UNHEX('01')),
CURDATE() - interval 2 month,
CONCAT('XX7-IEDR', UNHEX('01')),
'A');

INSERT INTO `TelecomHist` VALUES
((SELECT Chng_ID FROM NicHandleHist WHERE Nic_Handle = CONCAT('XX7-IEDR', UNHEX('01'))), CONCAT('XX7-IEDR', UNHEX('01')), CONCAT('Telephone', UNHEX('01')), 'P'),
((SELECT Chng_ID FROM NicHandleHist WHERE Nic_Handle = CONCAT('XX7-IEDR', UNHEX('01'))), CONCAT('XX7-IEDR', UNHEX('01')), CONCAT('Fax', UNHEX('01')), 'F');

INSERT INTO `Invoice` VALUES (1001,
CONCAT('BadUtf8Order', UNHEX('01')),
CONCAT('Bad Utf8 Account name', UNHEX('01')),
101,
CONCAT('BadUtf8 Address 1', UNHEX('01')),
CONCAT('BadUtf8 Address 2', UNHEX('01')),
CONCAT('BadUtf8 Address 3', UNHEX('01')),
CONCAT('IDL2-IEDR', UNHEX('01')),
'N',
CONCAT('BadUtf8 Country', UNHEX('01')),
CONCAT('BadUtf8 County', UNHEX('01')),
CONCAT('BadUtf8 CRS Version', UNHEX('01')),
CURDATE(),
CONCAT('BadUtf8 MD5', UNHEX('01')),
49774,41000,8774);
INSERT INTO `TransactionHist` VALUES (1001,'N','N',4960,'N',NULL,NULL,1001,4550,410,CONCAT('BadUtf8Order', UNHEX('01')),'N',NULL,NULL);
INSERT INTO `ReservationHist` VALUES (1001,
CONCAT('IDL3-IEDR', UNHEX('01')),
CONCAT('badutf8domainname', UNHEX('01'), '.ie'),
12,CURDATE(),65.00,3,13.91,'N','N',NULL,256815,1001,
'ÄStd10Year','registration',NULL,NULL,'Deposit');

INSERT INTO TransfersHist (D_Name, Tr_Date, Old_Nic_Handle, New_Nic_Handle)
VALUES (CONCAT('badutf8', UNHEX('01'), '.ie'),
CURDATE() - INTERVAL 3 MONTH,
CONCAT('IDL2-IEDR', UNHEX('01')),
CONCAT('XX7-IEDR', UNHEX('01')));

INSERT INTO DepositHist(Nic_Handle,Op_Dt,Trans_Dt,Open_Bal,Close_Bal,Order_ID,Notification_Sent,Trans_Amount,Trans_Type,Corrector_NH,Remark) VALUES
(CONCAT('IDL2-IEDR', UNHEX('01')),CURDATE(),CURDATE(),7000.00,2594.00,CONCAT('BadUtf8Order', UNHEX('01')),'N',100.00,'TOPUP',
CONCAT('IDL2-IEDR', UNHEX('01')),
CONCAT('Topup with badutf8 orderId', UNHEX('01')));
