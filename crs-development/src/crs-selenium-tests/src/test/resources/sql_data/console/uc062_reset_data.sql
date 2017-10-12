DELETE FROM Contact WHERE D_Name LIKE 'uc062%';
DELETE FROM ContactHist WHERE D_Name LIKE 'uc062%';
DELETE FROM DNS WHERE D_Name LIKE 'uc062%';
DELETE FROM DNSHist WHERE D_Name LIKE 'uc062%';
DELETE FROM Domain WHERE D_Name LIKE 'uc062%';
DELETE FROM DomainHist WHERE D_Name LIKE 'uc062%';

DELETE FROM Access WHERE Nic_Handle LIKE 'UC062%';
DELETE FROM AccessHist WHERE Nic_Handle_Chng_ID IN (SELECT Chng_ID FROM NicHandleHist WHERE Nic_Handle LIKE 'UC062%');
DELETE FROM NicHandle WHERE Nic_Handle LIKE 'UC062%';
DELETE FROM NicHandleHist WHERE Nic_Handle LIKE 'UC062%';
DELETE FROM Telecom WHERE Nic_Handle LIKE 'UC062%';
DELETE FROM TelecomHist WHERE Nic_Handle LIKE 'UC062%';