-- CRS-677

INSERT INTO `App_Config` (`Cfg_Key`, `Cfg_Type`, `Cfg_Value`) VALUES ('allow_one_or_two_letter_domains', 'BOOLEAN', '0');
INSERT INTO `App_Config` (`Cfg_Key`, `Cfg_Type`, `Cfg_Value`) VALUES ('allow_idn_domains', 'BOOLEAN', '0');

-- CRS-791

UPDATE NicHandle SET NH_Status = "Active" WHERE NH_Status IN ("New", "Renew");
UPDATE NicHandleHist SET NH_Status = "Active" WHERE NH_Status IN ("New", "Renew");

-- CRS-693

DROP TABLE `20MedSizeAccounts`;
DROP TABLE `AllTicks`;
DROP TABLE `DepositHist_Old`;
DROP TABLE `IPv6_Glue`;
DROP TABLE `PendingTicket`;
DROP TABLE `RealVoid`;
DROP TABLE `Reg_Accuracy`;
DROP TABLE `RenDates`;
DROP TABLE `Statement`;
DROP TABLE `_AdminC_Emails_DECOM`;
DROP TABLE `_BillStatus_DECOM`;
DROP TABLE `_ClikPaid_DECOM`;
DROP TABLE `_ClikPayNotification_DECOM`;
DROP TABLE `_Cond_Accept_DECOM`;
DROP TABLE `_Cr_Notes_DECOM`;
DROP TABLE `_Cr_Notes_Hist_DECOM`;
DROP TABLE `_DDOS_IPs_DECOM`;
DROP TABLE `_D_Audit_DECOM`;
DROP TABLE `_D_Audit_Hist_DECOM`;
DROP TABLE `_D_Locked_DECOM`;
DROP TABLE `_D_Locked_Hist_DECOM`;
DROP TABLE `_DeleteList_DECOM`;
DROP TABLE `_DeletePublic_DECOM`;
DROP TABLE `_GuestReceiptsHist_DECOM`;
DROP TABLE `_GuestReceipts_DECOM`;
DROP TABLE `_MailList_DECOM`;
DROP TABLE `_MonthEnd_DECOM`;
DROP TABLE `_NicHandleFailures_DECOM`;
DROP TABLE `_PendingMailList_DECOM`;
DROP TABLE `_PendingPaymentHist_DECOM`;
DROP TABLE `_PendingPayment_DECOM`;
DROP TABLE `_PendingSuspensionHist_DECOM`;
DROP TABLE `_PendingSuspension_DECOM`;
DROP TABLE `_RCInvoiceHist_DECOM`;
DROP TABLE `_RS_MailListHist_DECOM`;
DROP TABLE `_RS_MailList_DECOM`;
DROP TABLE `_RcptBatch_DECOM`;
DROP TABLE `_Report_DECOM`;
DROP TABLE `_STPAccount_DECOM`;
DROP TABLE `_SpecialListText_DECOM`;
DROP TABLE `_SpecialList_DECOM`;
DROP TABLE `_StaticTable_DECOM`;
DROP TABLE `_StatsAccessLog_DECOM`;
DROP TABLE `_StatsAccess_DECOM`;
DROP TABLE `_SuspendList_DECOM`;
DROP TABLE `_Transfers_DECOM`;

-- CRS-322 and couple of other fixes to table keys, in order to mirror IEDR production schema (dump from 2015-08-31)

ALTER TABLE `DNS`
    DROP PRIMARY KEY,
    ADD PRIMARY KEY (`D_Name`,`DNS_Order`),
    ADD UNIQUE KEY `D_Name` (`D_Name`,`DNS_Name`);
ALTER TABLE `ResellerDefaultNameservers` DROP INDEX `Nic_Handle_2`;
ALTER TABLE `TicketHist` DROP INDEX `D_Name`;
ALTER TABLE `TicketNameserverHist`
    DROP PRIMARY KEY,
    DROP INDEX `fk_TicketNameserverHist_TicketHist`,
    ADD PRIMARY KEY (`Chng_ID`,`TN_Name`);
