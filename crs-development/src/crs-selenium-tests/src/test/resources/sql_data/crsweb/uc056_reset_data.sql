DELETE FROM Telecom WHERE Nic_Handle IN (SELECT Nic_Handle FROM NicHandle WHERE NH_Name = 'UC056 test user');
DELETE FROM TelecomHist WHERE Nic_Handle IN (SELECT Nic_Handle FROM NicHandle WHERE NH_Name = 'UC056 test user');
DELETE FROM Access WHERE Nic_Handle IN (SELECT Nic_Handle FROM NicHandle WHERE NH_Name = 'UC056 test user');
DELETE FROM AccessHist WHERE Nic_Handle_Chng_ID IN (SELECT Chng_ID FROM NicHandleHist WHERE NH_Name = 'UC056 test user');
DELETE FROM NicHandle WHERE NH_Name = 'UC056 test user';
DELETE FROM NicHandleHist WHERE NH_Name = 'UC056 test user';

DELETE FROM Account WHERE A_Name = 'UC056 test user';
DELETE FROM AccountHist WHERE A_Name = 'UC056 test user';

