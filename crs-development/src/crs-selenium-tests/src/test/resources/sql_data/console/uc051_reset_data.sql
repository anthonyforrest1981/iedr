DELETE FROM Telecom WHERE Nic_Handle LIKE 'UC051%';
DELETE FROM TelecomHist WHERE Nic_Handle LIKE 'UC051%';
DELETE FROM Access WHERE Nic_Handle LIKE 'UC051%';
DELETE FROM AccessHist WHERE Nic_Handle_Chng_ID IN (SELECT Chng_ID FROM NicHandleHist WHERE Nic_Handle LIKE 'UC051%');
DELETE FROM NicHandle WHERE Nic_Handle LIKE 'UC051%';
DELETE FROM NicHandleHist WHERE Nic_Handle LIKE 'UC051%';

