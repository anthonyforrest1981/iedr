DELETE FROM Access WHERE Nic_Handle LIKE 'UC024%';
DELETE FROM AccessHist WHERE Nic_Handle_Chng_ID IN (SELECT Chng_ID FROM NicHandleHist WHERE Nic_Handle LIKE 'UC024%');
DELETE FROM NicHandle WHERE Nic_Handle LIKE 'UC024%';
DELETE FROM NicHandleHist WHERE Nic_Handle LIKE 'UC024%';
DELETE FROM Account WHERE A_Number = 723;
DELETE FROM AccountHist WHERE A_Number = 723;
DELETE FROM Deposit WHERE Nic_Handle LIKE 'UC024%';
DELETE FROM DepositHist WHERE Nic_Handle_Chng_Id IN (SELECT Chng_Id FROM NicHandleHist WHERE Nic_Handle LIKE 'UC024%');

