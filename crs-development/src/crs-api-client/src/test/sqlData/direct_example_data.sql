CREATE TABLE IF NOT EXISTS `example_data` (
 `D_Name` varchar(66) NOT NULL default '',
 `D_Reg_Dt` date default NULL,
 `D_Ren_Dt` date default NULL,
 `New_D_Ren_Dt` date default NULL,
 `Billing_Status` varchar(1) default NULL,
 `Orig_DSM_State` int(11) default NULL
) ENGINE = InnoDB DEFAULT CHARSET = utf8 COLLATE utf8_irish_accent_ci;
INSERT INTO example_data(D_Name, D_Reg_Dt, D_Ren_Dt, New_D_Ren_Dt, Orig_DSM_State) SELECT Domain.D_Name, D_Reg_TS, D_Ren_Dt, D_Ren_Dt, DSM_State FROM Domain WHERE A_Number = '001';

