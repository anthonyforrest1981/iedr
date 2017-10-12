<?php

include_once("db.php");

class DBUtils {

    public static function getProductPrice($productCode) {
        $queryResult = self::executeQuery("Select P_Price from Product where P_Guest='NO' and NOW() BETWEEN P_Valid_From_Dt and P_Valid_To_Dt and P_Active='YES' and P_Code = '$productCode'");
        return $queryResult->fetchColumn();
    }

    public static function getVatRate() {
        return self::getVatRateForUser("XBC189-IEDR");
    }

    public static function getVatRateForUser($user) {
        $queryResult = self::executeQuery("select Rate from NicHandle left join Vat on Category = Vat_Category where Nic_Handle='$user' and Valid='YES' and From_Dt < CURDATE() order by From_Dt DESC limit 1");
        return $queryResult->fetchColumn();
    }

    public static function getAccountPayStatus($user, $dom, $rate, $type, $pay_type) {
        $queryResult =
            self::executeQuery("Select Operation_Type,'$user' as user, '$rate' as rate, '$type' as type, Nic_Handle, Domain_Name, date_format(Creation_TS, '%Y-%m-%d') as Creation_Date, CURDATE() as today, Amount, Vat_Amount, Settled, format(Duration_Months/12,0) as Years, Payment_Method as payM, '$pay_type' as pay_type, example_data.D_Ren_Dt as Orig_Ren_Dt, D_Ren_Dt as D_Ren_Dt from Reservation left join example_data on Domain_Name=D_Name where Domain_Name='$dom' and Payment_method='$pay_type'");
        return $queryResult->fetch();
    }

    public static function getDepositValue($nic) {
        $queryResult = self::executeQuery(
            "select Open_Bal as o, Close_Bal as c, Trans_Amount as a, Trans_Type as type from Deposit where Nic_Handle='$nic'");
        return $queryResult->fetch();
    }

    public static function getTicketData($dom, $period) {
        $queryResult = self::executeQuery(
            "select D_Name, CharityCode, Period, Admin_Status, '$period' as expPeriod, T_Type, D_Holder, Class.name as T_Class, Category.name as T_Category, DATE_FORMAT(T_Ren_Dt, '%Y') as T_Ren_Dt, CheckedOut, Year(CURDATE() + Interval $period Year) as Ren, T_Remark, Admin_NH1, Tech_NH, Bill_NH, AutorenewMode as autorenew 
             from Ticket
             left join Class ON Ticket.Class_Id = Class.id
             left join Category ON Ticket.Category_Id = Category.id
             where D_Name='$dom'");
        return $queryResult->fetch();
    }

    public static function getTicketDNS($dom) {
        $queryResult = self::executeQuery(
            "SELECT TN_Name AS dns, TN_IPv4 AS ipv4, TN_IPv6 AS ipv6, TN_Order as 'order'
             FROM TicketNameserver JOIN Ticket USING(T_Number)
             WHERE D_Name = \"$dom\"
             ORDER BY TN_Order ASC"
        );
        return $queryResult->fetchAll();
    }

    public static function getModTicketData($dom) {
        $queryResult = self::executeQuery(
            "select T_Remark, T_Type, tcls.name as T_Class, tctg.name as T_Category, CharityCode, t.D_Holder as THolder, Creator_NH, Tech_NH, Admin_NH1, CharityCode, d.D_Name, d.D_Holder as DHolder, t.Period as TPeriod, dcls.name as D_Class, dctg.name as D_Category, c1.Contact_NH AS BillNic, c2.Contact_NH As AdminNic, c3.Contact_NH As TechNic, c4.Contact_NH As CreatorNic
             from Domain d
             left join Contact c1 on c1.D_Name = d.D_Name AND c1.Type = 'Billing'
             left join Contact c2 on c2.D_Name = d.D_Name AND c2.Type = 'Admin'
             left join Contact c3 on c3.D_Name = d.D_Name AND c3.Type = 'Tech'
             left join Contact c4 on c4.D_Name = d.D_Name AND c4.Type = 'Creator'
             left join Ticket t on t.D_Name = d.D_Name
             left join Class dcls on dcls.id = d.Class_Id
             left join Category dctg on dctg.id = d.Category_Id
             left join Class tcls on tcls.id = t.Class_Id
             left join Category tctg on tctg.id = t.Category_Id
             where d.D_Name='$dom'");
        return $queryResult->fetch();
    }

    public static function getBuyRequestData($buyRequestId) {
        $queryResult = self::executeQuery("SELECT
              Id, D_Name, Creator_NH, A_Number, D_Holder, Class_Id, Category_Id, Remark, H_Remark, Admin_Name, Admin_Email, Admin_Co_Name, Admin_Address, Admin_Country_Id, Admin_County_Id, Created_TS, Authcode, Authcode_TS, Status, CheckedOutTo_NH, Chng_ID
              FROM SecondaryMarketBuyRequest WHERE Id = $buyRequestId");
        return $queryResult->fetch();
    }

    public static function getBuyRequestTelecomData($buyRequestId) {
        $queryResult = self::executeQuery("SELECT Number, Type
              FROM SecondaryMarketBuyRequestTelecom WHERE BuyRequest_Id = $buyRequestId ORDER BY `Order` ASC, Type DESC");
        return $queryResult->fetchAll(PDO::FETCH_ASSOC);
    }

    public static function getReservationStatus($dom, $rate) {
        $queryResult = self::executeQuery(
            "Select '$rate' as rate, r.Domain_Name, r.Nic_Handle, r.Id as Order_ID, r.Amount as Amt, r.VAT_ID as VATID, r.VAT_Amount as Vat_Amount, r.Ticket_ID from Reservation r left join Ticket t on Ticket_ID=t.T_Number where r.Domain_Name='$dom' and t.D_name=r.Domain_Name");
        return $queryResult->fetch();
    }

    public static function getState($dom) {
        $queryResult = self::executeQuery(
            "select D_Name, State from Domain left join DSM_State on Domain.DSM_State=State where D_Name='$dom'");
        return $queryResult->fetch();
    }

    public static function getDNS($dom) {
        $queryResult = self::executeQuery("select DNS_Name as dns1 from DNS where D_name='$dom' and DNS_Order='1'");
        return $queryResult->fetch();
    }

    public static function getAllDNS($domainName) {
        $queryResult = self::executeQuery("select DNS_Name as 'name', DNS_IPv4_Addr as 'ipv4', DNS_IPv6_Addr as 'ipv6' from DNS where D_Name='$domainName'");
        return $queryResult->fetchAll();
    }

    public static function getTelecoms($nicHandle) {
        $result = array();
        $phonesResult = self::executeQuery("select Phone, Type, `Order` from Telecom where Nic_Handle='$nicHandle' and Type='Phone'");
        $faxesResult = self::executeQuery("select Phone, Type, `Order` from Telecom where Nic_Handle='$nicHandle' and Type='Fax'");
        $result['phones'] = $phonesResult->fetchAll();
        $result['faxes'] = $faxesResult->fetchAll();
        return $result;
    }

    private static function getConnection() {
        return new PDO('mysql:host=' . Config::DB_HOSTNAME . ';dbname=' . Config::DB_NAME . ';charset=utf8',
            Config::DB_USERNAME, Config::DB_PASSWORD);
    }

    private static function executeQuery($query) {
        $connection = self::getConnection();

        $result = $connection->query($query);

        if ($result === FALSE) {
            die($connection->errorCode());
        }

        if ($result->rowCount() < 1) {
            error_log("Query \"$query\" returned 0 rows");
            return NULL;
        }

        return $result;
    }
}
