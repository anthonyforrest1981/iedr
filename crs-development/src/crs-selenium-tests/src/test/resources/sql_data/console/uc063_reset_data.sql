DELETE FROM Contact WHERE D_Name LIKE 'uc063%';
DELETE FROM ContactHist WHERE D_Name LIKE 'uc063%';
DELETE FROM DNS WHERE D_Name LIKE 'uc063%';
DELETE FROM DNSHist WHERE D_Name LIKE 'uc063%';
DELETE FROM Domain WHERE D_Name LIKE 'uc063%';
DELETE FROM DomainHist WHERE D_Name LIKE 'uc063%';

DELETE ah.* FROM AccessHist ah JOIN NicHandleHist nhh ON ah.Nic_Handle_Chng_Id = nhh.Chng_Id WHERE nhh.NH_Name = 'UC063 new nic';
DELETE a.* FROM Access a JOIN NicHandle nh ON a.Nic_Handle = nh.Nic_Handle WHERE nh.NH_Name = 'UC063 new nic';
DELETE t.* FROM Telecom t JOIN NicHandle nh ON t.Nic_Handle = nh.Nic_Handle WHERE nh.NH_Name = 'UC063 new nic';
DELETE th.* FROM TelecomHist th JOIN NicHandle nh ON th.Nic_Handle = nh.Nic_Handle WHERE nh.NH_Name = 'UC063 new nic';
DELETE FROM NicHandle WHERE NH_Name = 'UC063 new nic';
DELETE FROM NicHandleHist WHERE NH_Name = 'UC063 new nic';

