DELETE FROM Contact WHERE D_Name LIKE 'uc018%';
DELETE FROM Contact WHERE D_Name LIKE 'uc018%';
DELETE FROM DNS WHERE D_Name LIKE 'uc018%';
DELETE FROM DNS WHERE D_Name LIKE 'uc018%';
DELETE FROM Domain WHERE D_Name LIKE 'uc018%';
DELETE FROM DomainHist WHERE D_Name LIKE 'uc018%';

DELETE FROM Access WHERE Nic_Handle LIKE 'UC018%';
DELETE FROM AccessHist WHERE Nic_Handle_Chng_ID IN (SELECT Chng_ID FROM NicHandleHist WHERE Nic_Handle LIKE 'UC018%');
DELETE FROM NicHandle WHERE Nic_Handle LIKE 'UC018%';
DELETE FROM NicHandleHist WHERE Nic_Handle LIKE 'UC018%';
