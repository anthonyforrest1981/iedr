DELETE FROM Telecom WHERE Nic_Handle = 'XBC189-IEDR';
DELETE FROM TelecomHist WHERE Nic_Handle = 'XBC189-IEDR';
DELETE FROM Deposit WHERE Nic_Handle = 'XBC189-IEDR';
DELETE FROM DepositHist WHERE Nic_Handle_Chng_Id IN (SELECT Chng_Id FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR');
DELETE FROM Reseller_Defaults WHERE Nic_Handle = 'XBC189-IEDR';
DELETE FROM ResellerDefaultNameservers WHERE Nic_Handle = 'XBC189-IEDR';
DELETE FROM Access WHERE Nic_Handle = 'XBC189-IEDR';
DELETE FROM AccessHist WHERE Nic_Handle_Chng_ID IN (SELECT Chng_ID FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR');
DELETE FROM NicHandle WHERE Nic_Handle = 'XBC189-IEDR';
DELETE FROM NicHandleHist WHERE Nic_Handle = 'XBC189-IEDR';
DELETE FROM Account WHERE A_Number = 600;
DELETE FROM AccountHist WHERE A_Number = 600;

DELETE FROM Access WHERE Nic_Handle = 'XOE550-IEDR';
DELETE FROM AccessHist WHERE Nic_Handle_Chng_ID IN (SELECT Chng_ID FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR');
DELETE FROM NicHandle WHERE Nic_Handle = 'XOE550-IEDR';
DELETE FROM NicHandleHist WHERE Nic_Handle = 'XOE550-IEDR';

DELETE FROM Telecom WHERE Nic_Handle = 'XNV498-IEDR';
DELETE FROM TelecomHist WHERE Nic_Handle = 'XNV498-IEDR';
DELETE FROM Access WHERE Nic_Handle = 'XNV498-IEDR';
DELETE FROM AccessHist WHERE Nic_Handle_Chng_ID IN (SELECT Chng_ID FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR');
DELETE FROM NicHandle WHERE Nic_Handle = 'XNV498-IEDR';
DELETE FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR';
DELETE FROM Account WHERE A_Number = 603;
DELETE FROM AccountHist WHERE A_Number = 603;
DELETE FROM Deposit WHERE Nic_Handle = 'XNV498-IEDR';
DELETE FROM DepositHist WHERE Nic_Handle_Chng_Id IN (SELECT Chng_Id FROM NicHandleHist WHERE Nic_Handle = 'XNV498-IEDR');

DELETE FROM Telecom WHERE Nic_Handle = 'XDD274-IEDR';
DELETE FROM TelecomHist WHERE Nic_Handle = 'XDD274-IEDR';
DELETE FROM Access WHERE Nic_Handle = 'XDD274-IEDR';
DELETE FROM AccessHist WHERE Nic_Handle_Chng_ID IN (SELECT Chng_ID FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR');
DELETE FROM NicHandle WHERE Nic_Handle = 'XDD274-IEDR';
DELETE FROM NicHandleHist WHERE Nic_Handle = 'XDD274-IEDR';
