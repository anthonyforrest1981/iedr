DELETE FROM ResetPass WHERE Nic_Handle LIKE 'UC079%';
DELETE FROM Access WHERE Nic_Handle LIKE 'UC079%';
DELETE FROM AccessHist WHERE Nic_Handle_Chng_ID IN (SELECT Chng_ID FROM NicHandleHist WHERE Nic_Handle LIKE 'UC079%');
DELETE FROM NicHandle WHERE Nic_Handle LIKE 'UC079%';
DELETE FROM NicHandleHist WHERE Nic_Handle LIKE 'UC079%';

