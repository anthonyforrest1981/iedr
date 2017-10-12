DELETE FROM Account WHERE A_Number IN (1111, 1112, 1113);
DELETE FROM AccountHist WHERE A_Number IN (1111, 1112, 1113);

DELETE FROM Access WHERE Nic_Handle LIKE 'UC045%';
DELETE FROM AccessHist WHERE Nic_Handle_Chng_ID IN (SELECT Chng_ID FROM NicHandleHist WHERE Nic_Handle LIKE 'UC045%');
DELETE FROM NicHandle WHERE Nic_Handle LIKE 'UC045%';
DELETE FROM NicHandleHist WHERE Nic_Handle LIKE 'UC045%';
DELETE FROM Reseller_Defaults WHERE Nic_Handle LIKE 'UC045%';
DELETE FROM ResellerDefaultNameservers WHERE Nic_Handle LIKE 'UC045%';

DELETE FROM Contact WHERE D_Name LIKE 'uc045%';
DELETE FROM Contact WHERE D_Name LIKE 'uc045%';
DELETE FROM DNS WHERE D_Name LIKE 'uc045%';
DELETE FROM DNS WHERE D_Name LIKE 'uc045%';
DELETE FROM Domain WHERE D_Name LIKE 'uc045%';
DELETE FROM DomainHist WHERE D_Name LIKE 'uc045%';

DELETE FROM Bulk_Transfer WHERE Gaining_Account = 1113;
DELETE FROM Bulk_Transfer WHERE Gaining_Account = 1111;
DELETE FROM Bulk_Transfer_Domain WHERE Domain_Name LIKE 'uc045%';
DELETE FROM Reservation WHERE Domain_Name LIKE 'uc045%';
DELETE FROM Transaction WHERE Order_ID = '20130615121643-C-3505172';
DELETE FROM TransfersHist WHERE Old_Nic_Handle LIKE 'UC045%';

