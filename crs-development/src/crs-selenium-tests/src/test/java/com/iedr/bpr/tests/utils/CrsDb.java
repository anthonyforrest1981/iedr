package com.iedr.bpr.tests.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.iedr.bpr.tests.TestConfig;

public class CrsDb {

    private Connection con = null;

    private static class BooleanHandler {

        public static String YES = "YES";
        public static String NO = "NO";

        public static String toString(boolean b) {
            return b ? YES : NO;
        }

        static boolean toBoolean(String s) {
            if (YES.equals(s)) {
                return true;
            } else if (NO.equals(s)) {
                return false;
            } else {
                throw new IllegalArgumentException(s);
            }
        }

    }

    public CrsDb() throws SQLException, IOException {
        Properties config = TestConfig.getConfig();
        String url = config.getProperty("dburl");
        String user = config.getProperty("dbuser");
        String password = config.getProperty("dbpassword");

        con = DriverManager.getConnection(url, user, password);
    }

    public ResultSet execute(String statement) throws SQLException {
        PreparedStatement pst = con.prepareStatement(statement);
        return pst.executeQuery();
    }

    public Date getCurrentDate() throws SQLException {
        PreparedStatement pst = con.prepareStatement("select NOW()");
        ResultSet rs = pst.executeQuery();
        rs.next();
        return new Date(rs.getTimestamp(1).getTime());
    }

    public boolean isDomainCharity(final String domainName) throws SQLException {
        PreparedStatement pst = con
                .prepareStatement("SELECT dsm.D_Holder_Type FROM Domain d left join DSM_State dsm on d.DSM_State = dsm.State where d.D_Name = ?");
        pst.setString(1, domainName);
        ResultSet rs = pst.executeQuery();
        rs.next();
        return "C".equals(rs.getString(1));
    }

    public Date getSuspensionDateForDomain(String domain) throws SQLException {
        ResultSet rs = getColumnFromTable("D_Susp_Dt", "Domain", "D_Name", domain);
        return rs.getDate(1);
    }

    public Date getRenewalDateForDomain(String domain) throws SQLException {
        ResultSet rs = getColumnFromTable("D_Ren_Dt", "Domain", "D_Name", domain);
        return rs.getDate(1);
    }

    public Date getDeletionDateForDomain(String domain) throws SQLException {
        ResultSet rs = getColumnFromTable("D_Del_Dt", "Domain", "D_Name", domain);
        return rs.getDate(1);
    }

    public String getNrpStatusForDomain(String domain) throws SQLException {
        PreparedStatement pst = con.prepareStatement("select NRP_Status from Domain left join "
                + "DSM_State on DSM_State=State where D_Name=?");
        pst.setString(1, domain);
        ResultSet rs = pst.executeQuery();
        rs.next();
        return rs.getString(1);
    }

    public int getDsmStateForDomain(String domain) throws SQLException {
        ResultSet rs = getColumnFromTable("DSM_State", "Domain", "D_Name", domain);
        return rs.getInt(1);
    }

    public int getAccountForDomain(String domain) throws SQLException {
        ResultSet rs = getColumnFromTable("A_Number", "Domain", "D_Name", domain);
        return rs.getInt(1);
    }

    public String getAuthcodeForDomain(String domain) throws SQLException {
        ResultSet rs = getColumnFromTable("D_Authcode", "Domain", "D_Name", domain);
        return rs.getString(1);
    }

    public String getHolderForDomain(String domain) throws SQLException {
        ResultSet rs = getColumnFromTable("D_Holder", "Domain", "D_Name", domain);
        return rs.getString(1);
    }

    public String getContactForDomain(String domain, String type) throws SQLException {
        Map<String, String> map = getContactMapForDomain(domain);
        return map.get(type);
    }

    public Map<String, String> getContactMapForDomain(String domainName) throws SQLException {
        PreparedStatement pst = con.prepareStatement("select Type, Contact_NH from Contact where D_Name=?");
        pst.setString(1, domainName);
        ResultSet rs = pst.executeQuery();
        Map<String, String> map = new HashMap<>();
        while (rs.next()) {
            map.put(rs.getString(1), rs.getString(2));
        }
        return map;
    }

    public Map<String, String> getContactMapForTicket(int ticketId) throws SQLException {
        PreparedStatement pst = con.prepareStatement("select Admin_NH1, Tech_NH, Bill_NH from Ticket where T_Number=?");
        pst.setInt(1, ticketId);
        ResultSet rs = pst.executeQuery();
        Map<String, String> map = new HashMap<>();
        rs.next();
        map.put("A", rs.getString(1));
        map.put("T", rs.getString(2));
        map.put("B", rs.getString(3));
        return map;
    }

    public List<DomainNameServer> getDnsListForDomain(String domainName) throws SQLException {
        PreparedStatement pst = con
                .prepareStatement("select DNS_Name, DNS_IPv4_Addr, DNS_IPv6_Addr from DNS where D_Name=? order by DNS_Order");
        pst.setString(1, domainName);
        ResultSet rs = pst.executeQuery();
        List<DomainNameServer> dnsList = new ArrayList<>();
        while (rs.next()) {
            dnsList.add(new DomainNameServer(rs.getString(1), rs.getString(2), rs.getString(3)));
        }
        return dnsList;
    }

    public List<DomainNameServer> getDnsListForTicket(int ticketId) throws SQLException {
        PreparedStatement pst = con
                .prepareStatement("select TN_Name, TN_IPv4, TN_IPv6 from TicketNameserver where T_Number=?");
        pst.setInt(1, ticketId);
        ResultSet rs = pst.executeQuery();
        List<DomainNameServer> dnsList = new ArrayList<>();
        while (rs.next()) {
            dnsList.add(new DomainNameServer(rs.getString(1), rs.getString(2), rs.getString(3)));
        }
        return dnsList;
    }

    public int getDomainsCountForAccount(int accountNumber) throws SQLException {
        Map<String, String> exactCriteria = new HashMap<>();
        exactCriteria.put("A_Number", String.valueOf(accountNumber));
        return getDomainsCount(exactCriteria, new HashMap<String, List<Date>>(), true);
    }

    public int getDomainsCount(Map<String, String> exactCriteria, Map<String, List<Date>> dateRangeCriteria,
            boolean conjunction) throws SQLException {
        return getDomainsCount(exactCriteria, dateRangeCriteria, false, conjunction, false);
    }

    public int getDomainsCountWithJoin(Map<String, String> exactCriteria, Map<String, List<Date>> dateRangeCriteria,
            boolean conjunction) throws SQLException {
        return getDomainsCount(exactCriteria, dateRangeCriteria, false, conjunction, true);
    }

    public int getHistDomainsCount(Map<String, String> exactCriteria, boolean conjunction) throws SQLException {
        return getDomainsCount(exactCriteria, new HashMap<String, List<Date>>(), true, conjunction, false);
    }

    public int getHistDomainsCount(Map<String, String> exactCriteria, Map<String, List<Date>> dateRangeCriteria,
            boolean conjunction) throws SQLException {
        return getDomainsCount(exactCriteria, dateRangeCriteria, true, conjunction, false);
    }

    private int getDomainsCount(Map<String, String> exactCriteria, Map<String, List<Date>> dateRangeCriteria,
            boolean hist, boolean conjunction, boolean join) throws SQLException {
        String table = hist ? "DomainHist D left join AccountHist A on A.Chng_Id = D.Account_Chng_Id" : "Domain";
        String joinClause = join ? "left join Class CL on CL.id = Class_Id left join Category CT on CT.id = Category_Id " : "";
        String whereClause = prepareWhereClause(exactCriteria, dateRangeCriteria, conjunction);
        String statement = String.format("select count(*) from %s %s where %s", table, joinClause, whereClause);
        PreparedStatement pst = con.prepareStatement(statement);
        fillStatement(pst, exactCriteria, dateRangeCriteria);
        ResultSet rs = pst.executeQuery();
        rs.next();
        return rs.getInt(1);
    }

    private String prepareWhereClause(Map<String, String> exactCriteria, Map<String, List<Date>> betweenCriteria,
            boolean conjunction) {
        String separator = conjunction ? "and" : "or";
        List<String> conditions = new ArrayList<>();
        for (String key : exactCriteria.keySet()) {
            conditions.add(String.format("(%s=?)", key));
        }
        for (String key : betweenCriteria.keySet()) {
            conditions.add(String.format("(%s between ? and ?)", key));
        }
        return StringUtils.join(conditions, separator);
    }

    private void fillStatement(PreparedStatement pst, Map<String, String> exactCriteria,
            Map<String, List<Date>> betweenCriteria) throws SQLException {
        List<String> exactValues = new ArrayList<>(exactCriteria.values());
        for (int i = 0; i < exactValues.size(); i++) {
            pst.setString(i + 1, exactValues.get(i));
        }
        List<List<Date>> betweenValues = new ArrayList<>(betweenCriteria.values());
        int offset = exactValues.size();
        for (int i = 0; i < betweenValues.size(); i++) {
            List<Date> range = betweenValues.get(i);
            pst.setDate(offset + 2 * i + 1, new java.sql.Date(range.get(0).getTime()));
            pst.setDate(offset + 2 * i + 2, new java.sql.Date(range.get(1).getTime()));
        }
    }

    public void setDsmStateForDomain(String domain, int dsm) throws SQLException {
        PreparedStatement pst = con.prepareStatement("update Domain set DSM_State=? where D_Name=?");
        pst.setInt(1, dsm);
        pst.setString(2, domain);
        pst.executeUpdate();
    }

    public void setRenewalDateForDomain(String domain, Date date) throws SQLException {
        PreparedStatement pst = con.prepareStatement("update Domain set D_Ren_Dt=? where D_Name=?");
        pst.setDate(1, new java.sql.Date(date.getTime()));
        pst.setString(2, domain);
        pst.executeUpdate();
    }

    public Object getRenewalModeForDomain(String domain) throws SQLException {
        PreparedStatement pst = con.prepareStatement("select Renewal_Mode from Domain left join "
                + "DSM_State on DSM_State=State where D_Name=?");
        pst.setString(1, domain);
        ResultSet rs = pst.executeQuery();
        rs.next();
        return rs.getString(1);
    }

    public boolean isTicketExists(int ticketId) throws SQLException {
        PreparedStatement pst = con.prepareStatement("select count(*) from Ticket where T_Number=?");
        pst.setInt(1, ticketId);
        ResultSet rs = pst.executeQuery();
        rs.next();
        return (rs.getInt(1) > 0);
    }

    public int getTicketsCountForDomain(String domain) throws SQLException {
        PreparedStatement pst = con.prepareStatement("select count(*) from Ticket where D_Name=?");
        pst.setString(1, domain);
        ResultSet rs = pst.executeQuery();
        rs.next();
        return rs.getInt(1);
    }

    public int getTicketsCountForNicHandle(String nh) throws SQLException {
        PreparedStatement pst = con.prepareStatement("select count(*) from Ticket where " + "Admin_NH1=? or Bill_NH=?");
        pst.setString(1, nh);
        pst.setString(2, nh);
        ResultSet rs = pst.executeQuery();
        rs.next();
        return rs.getInt(1);
    }

    public int getTicketId(String domain) throws SQLException {
        ResultSet rs = getColumnFromTable("T_Number", "Ticket", "D_Name", domain);
        return rs.getInt(1);
    }

    public String getTicketChyCode(int ticketId) throws SQLException {
        ResultSet rs = getColumnFromTable("CharityCode", "Ticket", "T_Number", ticketId);
        return rs.getString(1);
    }

    public String getTicketRemark(int ticketId) throws SQLException {
        ResultSet rs = getColumnFromTable("T_Remark", "Ticket", "T_Number", ticketId);
        return rs.getString(1);
    }

    public String getTicketDomainHolder(int ticketId) throws SQLException {
        ResultSet rs = getColumnFromTable("D_Holder", "Ticket", "T_Number", ticketId);
        return rs.getString(1);
    }

    public String getTicketCustomerStatus(int ticketId) throws SQLException {
        ResultSet rs = getColumnFromTable("Customer_Status", "Ticket", "T_Number", ticketId);
        return rs.getString(1);
    }

    public String getTicketAdminStatus(int ticketId) throws SQLException {
        ResultSet rs = getColumnFromTable("Admin_Status", "Ticket", "T_Number", ticketId);
        return rs.getString(1);
    }

    public void changeTicketAdminStatus(int ticketId, String status, Date statusDate) throws SQLException {
        PreparedStatement pst = con
                .prepareStatement("update Ticket set Admin_Status=?, Ad_Status_TS=? where T_Number=?");
        pst.setString(1, status);
        pst.setTimestamp(2, new Timestamp(statusDate.getTime()));
        pst.setInt(3, ticketId);
        pst.executeUpdate();
    }

    public void changeTicketTechStatus(int ticketId, String status, Date statusDate) throws SQLException {
        PreparedStatement pst = con.prepareStatement("update Ticket set Tech_Status=?, T_Status_TS=? where T_Number=?");
        pst.setString(1, status);
        pst.setTimestamp(2, new Timestamp(statusDate.getTime()));
        pst.setInt(3, ticketId);
        pst.executeUpdate();
    }

    public String getTicketHostmasterRemark(int ticketId) throws SQLException {
        ResultSet rs = getColumnFromTable("H_Remark", "Ticket", "T_Number", ticketId);
        return rs.getString(1);
    }

    public void changeTicketFinancialStatus(int ticketId, String status, Date statusDate) throws SQLException {
        PreparedStatement pst = con
                .prepareStatement("update Ticket set Financial_Status=?, F_Status_TS=? where T_Number=?");
        pst.setString(1, status);
        pst.setTimestamp(2, new Timestamp(statusDate.getTime()));
        pst.setInt(3, ticketId);
        pst.executeUpdate();
    }

    public void changeTicketCreatedTimestamp(int ticketId, Date createdDate) throws SQLException {
        PreparedStatement pst = con.prepareStatement("update Ticket set T_Created_TS=? where T_Number=?");
        pst.setTimestamp(1, new Timestamp(createdDate.getTime()));
        pst.setInt(2, ticketId);
        pst.executeUpdate();
    }

    public int getProductId(int pDuration, boolean registration, boolean isRegistrar) throws SQLException {
        String pGuest = BooleanHandler.toString(!isRegistrar);
        String regRenCol = registration ? "P_Reg" : "P_Ren";
        String query = String.format("select Id from Product where "
                + "P_Duration=? and P_Guest=? and %s=? and P_Active=? and "
                + "CURDATE() between P_Valid_From_Dt and P_Valid_To_Dt", regRenCol);
        PreparedStatement pst = con.prepareStatement(query);
        pst.setInt(1, pDuration);
        pst.setString(2, pGuest);
        pst.setString(3, BooleanHandler.YES);
        pst.setString(4, BooleanHandler.YES);
        ResultSet rs = pst.executeQuery();
        rs.next();
        return rs.getInt(1);
    }

    public int getProductIdByCode(String pCode) throws SQLException {
        ResultSet rs = getColumnFromTable("Id", "Product", "P_Code", pCode);
        return rs.getInt(1);
    }

    public float getPriceForRegistrar(int duration, boolean registration) throws SQLException {
        return getPrice(duration, registration, true);
    }

    public float getPriceForDirect(int duration, boolean registration) throws SQLException {
        return getPrice(duration, registration, false);
    }

    private float getPrice(int pDuration, boolean registration, boolean isRegistrar) throws SQLException {
        int productId = getProductId(pDuration, registration, isRegistrar);
        ResultSet rs = getColumnFromTable("P_Price", "Product", "Id", productId);
        return rs.getFloat(1);
    }

    public String getProductCode(int productId) throws SQLException {
        ResultSet rs = getColumnFromTable("P_Code", "Product", "Id", productId);
        return rs.getString(1);
    }

    public float getProductPrice(int productId) throws SQLException {
        ResultSet rs = getColumnFromTable("P_Price", "Product", "Id", productId);
        return rs.getFloat(1);
    }

    public boolean getProductActive(int productId) throws SQLException {
        ResultSet rs = getColumnFromTable("P_Active", "Product", "Id", productId);
        return BooleanHandler.toBoolean(rs.getString(1));
    }

    public void setProductPrice(int productId, float price) throws SQLException {
        PreparedStatement pst = con.prepareStatement("update Product set P_Price=? where Id=?");
        pst.setFloat(1, price);
        pst.setInt(2, productId);
        pst.executeUpdate();
    }

    public void setProductActive(int productId, boolean active) throws SQLException {
        String activeString = BooleanHandler.toString(active);
        PreparedStatement pst = con.prepareStatement("update Product set P_Active=? where Id=?");
        pst.setString(1, activeString);
        pst.setInt(2, productId);
        pst.executeUpdate();
    }

    public float getVatRate(String userName) throws SQLException {
        PreparedStatement pst = con.prepareStatement("select Rate from NicHandle right join Vat on"
                + " Vat_Category=Category where Nic_Handle=? and" + " From_Dt <= CURRENT_DATE() and Valid=? "
                + "order by From_Dt desc limit 1");
        pst.setString(1, userName);
        pst.setString(2, BooleanHandler.YES);
        ResultSet rs = pst.executeQuery();
        rs.next();
        return rs.getFloat(1);
    }

    public String getVatCategory(String userName) throws SQLException {
        ResultSet rs = getColumnFromTable("Vat_Category", "NicHandle", "Nic_Handle", userName);
        return rs.getString(1);
    }

    public void setVatCategory(String userName, String category) throws SQLException {
        PreparedStatement pst = con.prepareStatement("update NicHandle set Vat_Category=? where Nic_Handle=?");
        pst.setString(1, category);
        pst.setString(2, userName);
        pst.executeUpdate();
    }

    public int getReservationId(String domain) throws SQLException {
        PreparedStatement pst = con
                .prepareStatement("select ID from Reservation where Domain_Name=? order by Creation_TS desc limit 1");
        pst.setString(1, domain);
        ResultSet rs = pst.executeQuery();
        rs.next();
        return rs.getInt(1);
    }

    public int getReservationHistId(String domain) throws SQLException {
        PreparedStatement pst = con
                .prepareStatement("select ID from ReservationHist left join DomainHist on Chng_ID = Domain_Chng_ID "
                        + "where D_Name = ? order by Creation_TS desc limit 1");
        pst.setString(1, domain);
        ResultSet rs = pst.executeQuery();
        rs.next();
        return rs.getInt(1);
    }

    public int getReservationsCount(String domain) throws SQLException {
        ResultSet rs = getColumnFromTable("count(*)", "Reservation", "Domain_Name", domain);
        return rs.getInt(1);
    }

    public int getReservationDuration(int reservationId) throws SQLException {
        ResultSet rs = getColumnFromTable("Duration_Months", "Reservation", "ID", reservationId);
        return rs.getInt(1);
    }

    public boolean reservationReadyForSettlement(int reservationId) throws SQLException {
        ResultSet rs = getColumnFromTable("Ready_For_Settlement", "Reservation", "ID", reservationId);
        return BooleanHandler.toBoolean(rs.getString(1));
    }

    public boolean reservationSettled(int reservationId) throws SQLException {
        ResultSet rs = getColumnFromTable("Settled", "Reservation", "ID", reservationId);
        return BooleanHandler.toBoolean(rs.getString(1));
    }

    public int getReservationTicketId(int reservationId) throws SQLException {
        ResultSet rs = getColumnFromTable("Ticket_ID", "Reservation", "ID", reservationId);
        return rs.getInt(1);
    }

    public int getReservationTransactionId(int reservationId) throws SQLException {
        ResultSet rs = getColumnFromTable("Transaction_ID", "Reservation", "ID", reservationId);
        return rs.getInt(1);
    }

    public void setReservationHistSettledDate(int reservationId, Date settledDate) throws SQLException {
        PreparedStatement pst = con.prepareStatement("update ReservationHist set Settled_TS=? where ID=?");
        pst.setTimestamp(1, new Timestamp(settledDate.getTime()));
        pst.setInt(2, reservationId);
        pst.executeUpdate();
    }

    public void deleteReservation(int reservationId) throws SQLException {
        PreparedStatement pst = con.prepareStatement("delete from Reservation where ID=?");
        pst.setInt(1, reservationId);
        pst.executeUpdate();
    }

    public boolean transactionCancelled(int transactionId) throws SQLException {
        PreparedStatement pst = con.prepareStatement("select count(*) from Transaction where ID=? and Cancelled=?");
        pst.setInt(1, transactionId);
        pst.setString(2, BooleanHandler.YES);
        ResultSet rs = pst.executeQuery();
        rs.next();
        return rs.getInt(1) == 1;
    }

    public boolean transactionFinanciallyPassed(int transactionId) throws SQLException {
        PreparedStatement pst = con
                .prepareStatement("select count(*) from Transaction where ID=? and Financially_Passed_TS is not NULL");
        pst.setInt(1, transactionId);
        ResultSet rs = pst.executeQuery();
        rs.next();
        return rs.getInt(1) == 1;
    }

    public boolean transactionExists(int transactionId) throws SQLException {
        PreparedStatement pst = con.prepareStatement("select count(*) from Transaction where ID=?");
        pst.setInt(1, transactionId);
        ResultSet rs = pst.executeQuery();
        rs.next();
        return rs.getInt(1) > 0;
    }

    public void invalidateTransaction(int transactionId, Date invalidatedDate) throws SQLException {
        PreparedStatement pst = con
                .prepareStatement("update Transaction set Invalidated=?, Invalidated_TS=? where ID=?");
        pst.setString(1, BooleanHandler.YES);
        pst.setTimestamp(2, new Timestamp(invalidatedDate.getTime()));
        pst.setString(3, String.valueOf(transactionId));
        pst.executeUpdate();
    }

    public void validateTransaction(int transactionId) throws SQLException {
        PreparedStatement pst = con
                .prepareStatement("update Transaction set Invalidated=?, Invalidated_TS=NULL where ID=?");
        pst.setString(1, BooleanHandler.NO);
        pst.setString(2, String.valueOf(transactionId));
        pst.executeUpdate();
    }

    public void invalidateTransactionRealexAuthcode(int transactionId) throws SQLException {
        PreparedStatement pst = con
                .prepareStatement("update Transaction set Realex_Authcode=CONCAT('xxx-', Realex_Authcode) where ID=?");
        pst.setString(1, String.valueOf(transactionId));
        pst.executeUpdate();
    }

    public String getSettledOrderId(String domainName) throws SQLException {
        PreparedStatement pst = con.prepareStatement("select th.Order_ID from TransactionHist th "
                + "left join ReservationHist rh on th.ID = rh.transaction_ID "
                + "left join DomainHist dh on rh.Domain_Chng_ID = dh.Chng_ID "
                + "where dh.D_Name = ? order by rh.Creation_TS desc limit 1");
        pst.setString(1, domainName);
        ResultSet rs = pst.executeQuery();
        rs.next();
        return rs.getString(1);
    }

    public String getSettledInvoiceNumber(String domainName) throws SQLException {
        PreparedStatement pst = con.prepareStatement("select i.Invoice_Number from Invoice i "
                + "left join TransactionHist th on i.ID = th.Invoice_ID "
                + "left join ReservationHist rh on th.ID = rh.Transaction_ID "
                + "left join DomainHist dh on rh.Domain_Chng_ID = dh.Chng_ID "
                + "where dh.D_Name = ? order by rh.Creation_TS desc limit 1");
        pst.setString(1, domainName);
        ResultSet rs = pst.executeQuery();
        rs.next();
        return rs.getString(1);
    }

    public void setPasswordResetTokenTimestamp(String userName, Date expiresDate) throws SQLException {
        PreparedStatement pst = con.prepareStatement("update ResetPass set Expires_TS=? where Nic_Handle=?");
        pst.setTimestamp(1, new Timestamp(expiresDate.getTime()));
        pst.setString(2, userName);
        pst.executeUpdate();
    }

    public String getAppConfigValue(String key) throws SQLException {
        ResultSet rs = getColumnFromTable("Cfg_Value", "App_Config", "Cfg_Key", key);
        return rs.getString(1);
    }

    public void setAppConfigStringValue(String key, String value) throws SQLException {
        PreparedStatement pst = con.prepareStatement("update App_Config set Cfg_Value=? where Cfg_Key=?");
        pst.setString(1, value);
        pst.setString(2, key);
        pst.executeUpdate();
    }

    public void setUserSessionTimeout(int timeout) throws SQLException {
        PreparedStatement pst = con.prepareStatement("update App_Config set Cfg_Value=? where Cfg_Key=?");
        pst.setInt(1, timeout);
        pst.setString(2, "user_session_timeout_minutes");
        pst.executeUpdate();
    }

    public int getMaxExpiringTicketNotificationPeriod() throws SQLException {
        List<String> stringPeriods =
                Arrays.asList(getAppConfigValue("ticket_expiration_notification_periods").split(","));
        List<Integer> periods = new ArrayList<>();
        for (String stringPeriod : stringPeriods) {
            periods.add(Integer.valueOf(stringPeriod));
        }
        Collections.sort(periods);
        return periods.get(periods.size() - 1);
    }

    public Map<String, String> getNicHandleMapById(String nicHandleName) throws SQLException {
        List<String> columnList = Arrays.asList("NH_Name", "NH_Email", "Co_Name", "NH_Address");
        ResultSet rs = getColumnListFromTable(columnList, "NicHandle", "Nic_Handle", nicHandleName);
        return getStringMapFromResults(rs, columnList);
    }

    public String getNicHandleByName(String nicHandleName) throws SQLException {
        ResultSet rs = getColumnFromTable("Nic_Handle", "NicHandle", "NH_Name", nicHandleName);
        return rs.getString(1);
    }

    public String getNicHandleName(String nicHandle) throws SQLException {
        ResultSet rs = getColumnFromTable("NH_Name", "NicHandle", "Nic_Handle", nicHandle);
        return rs.getString(1);
    }

    public String getNicHandleEmail(String nicHandle) throws SQLException {
        ResultSet rs = getColumnFromTable("NH_Email", "NicHandle", "Nic_Handle", nicHandle);
        return rs.getString(1);
    }

    public String getNicHandleAddress(String nicHandle) throws SQLException {
        ResultSet rs = getColumnFromTable("NH_Address", "NicHandle", "Nic_Handle", nicHandle);
        return rs.getString(1);
    }

    public int getNicHandleAccountNumber(String nicHandle) throws SQLException {
        ResultSet rs = getColumnFromTable("A_Number", "NicHandle", "Nic_handle", nicHandle);
        return rs.getInt(1);
    }

    public String getNicHandleCompanyName(String nicHandle) throws SQLException {
        ResultSet rs = getColumnFromTable("Co_Name", "NicHandle", "Nic_handle", nicHandle);
        return rs.getString(1);
    }

    public void setNicHandleAccountNumber(String nicHandle, int accountNumber) throws SQLException {
        PreparedStatement pst = con.prepareStatement("update NicHandle set A_Number=? where Nic_Handle=?");
        pst.setInt(1, accountNumber);
        pst.setString(2, nicHandle);
        pst.executeUpdate();
    }

    public String getNicHandlePhone(String nicHandle) throws SQLException {
        PreparedStatement pst = con.prepareStatement("select Phone from Telecom where Nic_Handle=? and Type='Phone'");
        pst.setString(1, nicHandle);
        ResultSet rs = pst.executeQuery();
        rs.next();
        return rs.getString(1);
    }

    public int getNicHandleLevel(String nicHandle) throws SQLException {
        ResultSet rs = getColumnFromTable("NH_Level", "Access", "Nic_Handle", nicHandle);
        return rs.getInt(1);
    }

    public void setNicHandleLevel(String nicHandle, int nhLevel) throws SQLException {
        PreparedStatement pst = con.prepareStatement("update Access set NH_Level=? where Nic_Handle=?");
        pst.setInt(1, nhLevel);
        pst.setString(2, nicHandle);
        pst.executeUpdate();
    }

    public String getBillingNicHandleForAccount(int accountNumber) throws SQLException {
        ResultSet rs = getColumnFromTable("Billing_NH", "Account", "A_Number", accountNumber);
        return rs.getString(1);
    }

    public float getDepositAmount(String userName) throws SQLException {
        ResultSet rs = getColumnFromTable("Close_Bal", "Deposit", "Nic_Handle", userName);
        return rs.getFloat(1);
    }

    public float getDepositTransactionAmount(String userName) throws SQLException {
        ResultSet rs = getColumnFromTable("Trans_Amount", "Deposit", "Nic_Handle", userName);
        return rs.getFloat(1);
    }

    public String getDepositCorrectorNh(String userName) throws SQLException {
        ResultSet rs = getColumnFromTable("Corrector_NH", "Deposit", "Nic_Handle", userName);
        return rs.getString(1);
    }

    public String getDepositRemark(String userName) throws SQLException {
        ResultSet rs = getColumnFromTable("Remark", "Deposit", "Nic_Handle", userName);
        return rs.getString(1);
    }

    public String getResellerDefaults(String nicHandle, String column) throws SQLException {
        ResultSet rs = getColumnFromTable(column, "Reseller_Defaults", "Nic_Handle", nicHandle);
        return rs.getString(1);
    }

    public List<DomainNameServer> getDnsListForReseller(String nicHandle) throws SQLException {
        PreparedStatement pst = con
                .prepareStatement("select RDN_Nameserver from ResellerDefaultNameservers where Nic_Handle=?");
        pst.setString(1, nicHandle);
        ResultSet rs = pst.executeQuery();
        List<DomainNameServer> dnsList = new ArrayList<>();
        while (rs.next()) {
            dnsList.add(new DomainNameServer(rs.getString(1), null, null));
        }
        return dnsList;
    }

    public List<String> getNicHandleDomains(String nicHandle) throws SQLException {
        PreparedStatement pst = con.prepareStatement("select D_Name from Contact where Contact_NH=? group by D_Name");
        pst.setString(1, nicHandle);
        ResultSet rs = pst.executeQuery();
        List<String> domains = new ArrayList<>();
        while (rs.next()) {
            domains.add(rs.getString(1));
        }
        return domains;
    }

    public List<String> getNicHandleDomainsInState(String nicHandle, List<Integer> dsmStates) throws SQLException {
        String sqlList = StringUtils.repeat("?", ",", dsmStates.size());
        String statement = String.format("select d.D_Name from Contact c " + "join Domain d on c.D_Name=d.D_Name "
                + "where Contact_NH=? and d.DSM_State in (%s) " + "group by D_Name", sqlList);
        PreparedStatement pst = con.prepareStatement(statement);
        pst.setString(1, nicHandle);
        for (int i = 0; i < dsmStates.size(); i++) {
            pst.setInt(2 + i, dsmStates.get(i));
        }
        ResultSet rs = pst.executeQuery();
        List<String> domains = new ArrayList<>();
        while (rs.next()) {
            domains.add(rs.getString(1));
        }
        return domains;
    }

    public List<Integer> getDsmStates(String column, String value) throws SQLException {
        ResultSet rs = getColumnFromTable("State", "DSM_State", column, value);
        List<Integer> domains = new ArrayList<>();
        domains.add(rs.getInt(1));
        while (rs.next()) {
            domains.add(rs.getInt(1));
        }
        return domains;

    }

    public float getReservedDepositFunds(String userName) throws SQLException {
        PreparedStatement pst = con
                .prepareStatement("select sum(Amount + Vat_Amount) from Reservation where Nic_Handle=? and Payment_Method='Deposit' group by Nic_Handle");
        pst.setString(1, userName);
        ResultSet rs = pst.executeQuery();
        float reservedFunds = 0;
        if (rs.next()) {
            return rs.getFloat(1);
        }
        return reservedFunds;
    }

    public void setDepositAmount(String userName, float amount) throws SQLException {
        PreparedStatement pst = con.prepareStatement("update Deposit set Close_Bal=? where Nic_Handle=?");
        pst.setFloat(1, amount);
        pst.setString(2, userName);
        pst.executeUpdate();
    }

    public String getDepositOrderId(String userName) throws SQLException {
        ResultSet rs = getColumnFromTable("Order_ID", "Deposit", "Nic_Handle", userName);
        return rs.getString(1);
    }

    public String getEmailInvoiceFormat(String userName) throws SQLException {
        ResultSet rs = getColumnFromTable("email_invoice_format", "Reseller_Defaults", "Nic_Handle", userName);
        return rs.getString(1);
    }

    public void setEmailInvoiceFormat(String userName, String format) throws SQLException {
        PreparedStatement pst = con
                .prepareStatement("update Reseller_Defaults set email_invoice_format=? where Nic_Handle=?");
        pst.setString(1, format);
        pst.setString(2, userName);
        pst.executeUpdate();
    }

    public String getCountryVatCategory(String country) throws SQLException {
        ResultSet rs = getColumnFromTable("vat_category", "Countries", "Name", country);
        return rs.getString(1);
    }

    public void setCountryVatCategory(String country, String category) throws SQLException {
        PreparedStatement pst = con.prepareStatement("update Countries set vat_category=? where Name=?");
        pst.setString(1, category);
        pst.setString(2, country);
        pst.executeUpdate();
    }

    public boolean isSchedulerJobStarted(String jobName, Date startDate) throws SQLException {
        PreparedStatement pst = con.prepareStatement("select id from SchedulerJob where job_name=? and start_TS>=?");
        pst.setString(1, jobName);
        pst.setTimestamp(2, new Timestamp(startDate.getTime()));
        ResultSet rs = pst.executeQuery();
        boolean resultsNotEmpty = rs.next();
        return resultsNotEmpty;
    }

    public boolean isSchedulerJobFinished(String jobName, Date startDate) throws SQLException {
        PreparedStatement pst = con
                .prepareStatement("select id from SchedulerJob where job_name=? and start_TS>=? and end_TS is not null");
        pst.setString(1, jobName);
        pst.setTimestamp(2, new Timestamp(startDate.getTime()));
        ResultSet rs = pst.executeQuery();
        boolean resultsNotEmpty = rs.next();
        return resultsNotEmpty;
    }

    public int getLastAccountId() throws SQLException {
        PreparedStatement pst = con.prepareStatement("select id from ModVersionIndex order by id desc limit 1");
        ResultSet rs = pst.executeQuery();
        if (!rs.next()) {
            // If ModVersionIndex is empty, first generated id will be 402.
            return 401;
        }
        return rs.getInt(1);
    }

    public String getAccountXmlFileName() throws SQLException {
        int id = getLastAccountId();
        return String.format("ACC%07d.xml", id);
    }

    public int getLastDoaId() throws SQLException {
        PreparedStatement pst = con.prepareStatement("select id from DoaVersionIndex order by id desc limit 1");
        ResultSet rs = pst.executeQuery();
        if (!rs.next()) {
            // If DoaVersionIndex is empty, first generated DoaId will
            // be 606.
            return 605;
        }
        return rs.getInt(1);
    }

    public String getDoaXmlFileName() throws SQLException {
        int id = getLastDoaId();
        return String.format("DOA%07d.xml", id);
    }

    public String getAccountName(int accountNumber) throws SQLException {
        ResultSet rs = getColumnFromTable("A_Name", "Account", "A_Number", accountNumber);
        return rs.getString(1);
    }

    public String getGuestAccountName() throws SQLException {
        return getAccountName(1);
    }

    public int countNicHandleRows(String nicHandle, String table) throws SQLException {
        return countNicHandleRows(nicHandle, table, "Nic_Handle");
    }

    public int countNicHandleRows(String nicHandle, String table, String key) throws SQLException {
        ResultSet rs = getColumnFromTable("count(*)", table, key, nicHandle);
        return rs.getInt(1);
    }

    public List<Integer> getDocumentIdsForDomain(String domainName) throws SQLException {
        PreparedStatement pst = con.prepareStatement("select DOC_ID from INCOMING_DOC_DOMAINS where DOMAIN_NAME=?");
        pst.setString(1, domainName);
        ResultSet rs = pst.executeQuery();
        List<Integer> docIds = new ArrayList<>();
        while (rs.next()) {
            docIds.add(rs.getInt(1));
        }
        return docIds;
    }

    public String getDocumentType(int docId) throws SQLException {
        ResultSet rs = getColumnFromTable("DOC_TYPE", "INCOMING_DOC", "DOC_ID", docId);
        return rs.getString(1);
    }

    public String getDocumentName(int docId) throws SQLException {
        ResultSet rs = getColumnFromTable("FILENAME", "INCOMING_DOC", "DOC_ID", docId);
        return rs.getString(1);
    }

    public List<String> getEmailTemplateValues(int eId, List<String> columns) throws SQLException {
        List<String> emailTableColumns = getEmailTableColumns();
        if (!emailTableColumns.containsAll(columns)) {
            throw new SQLException(String.format("One of column names is invalid for table Email: %s", columns));
        }
        PreparedStatement pst = con.prepareStatement(String.format("select %s from Email where E_ID=?",
                StringUtils.join(columns, ", ")));
        pst.setInt(1, eId);
        ResultSet rs = pst.executeQuery();
        rs.next();
        List<String> emailValues = new ArrayList<>();
        for (String column : columns) {
            emailValues.add(rs.getString(column));
        }
        return emailValues;
    }

    public int getDnsCheckNotificationsCountForDomain(String domainName) throws SQLException {
        PreparedStatement pst = con.prepareStatement("select count(*) from DNS_Check_Notification where Domain_Name=?");
        pst.setString(1, domainName);
        ResultSet rs = pst.executeQuery();
        rs.next();
        return rs.getInt(1);
    }

    private List<String> getEmailTableColumns() throws SQLException {
        PreparedStatement pst = con.prepareStatement("select * from Email limit 0");
        ResultSet rs = pst.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();
        List<String> names = new ArrayList<>();
        for (int i = 1; i < rsmd.getColumnCount() + 1; i++) {
            names.add(rsmd.getColumnName(i));
        }
        return names;
    }

    private ResultSet getColumnListFromTable(List<String> columns, String table, String key, String keyVal)
            throws SQLException {
        String columnString = StringUtils.join(columns, ", ");
        PreparedStatement pst = con.prepareStatement(String.format("select %s from %s where %s=?", columnString, table,
                key));
        pst.setString(1, keyVal);
        ResultSet rs = pst.executeQuery();
        rs.next();
        return rs;
    }

    private ResultSet getColumnListFromTable(List<String> columns, String table, String key, int keyVal)
            throws SQLException {
        String columnString = StringUtils.join(columns, ", ");
        PreparedStatement pst = con.prepareStatement(String.format("select %s from %s where %s=?", columnString, table,
                key));
        pst.setInt(1, keyVal);
        ResultSet rs = pst.executeQuery();
        rs.next();
        return rs;
    }

    private ResultSet getColumnFromTable(String column, String table, String key, String keyVal) throws SQLException {
        return getColumnListFromTable(Collections.singletonList(column), table, key, keyVal);
    }

    private ResultSet getColumnFromTable(String column, String table, String key, int keyVal) throws SQLException {
        return getColumnListFromTable(Collections.singletonList(column), table, key, keyVal);
    }

    private Map<String, String> getStringMapFromResults(ResultSet rs, List<String> columnList) throws SQLException {
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < columnList.size(); i++) {
            map.put(columnList.get(i), rs.getString(i + 1));
        }
        return map;
    }

    public Date getLockingRenewalDate(String domain) throws SQLException {
        ResultSet rs = getColumnFromTable("D_LockingRenewal_Dt", "Domain", "D_Name",  domain);
        return rs.getDate(1);
    }

    /**
     * Reloads data in database by executing cleaning and populating scripts.
     * Populating script is not required.
     *
     * @param clearDataScriptPath
     *            path relative to the working directory
     * @param loadDataScriptPath
     *            path relative to the working directory
     * @throws IOException
     * @throws SQLException
     */
    public void reloadData(String clearDataScriptPath, String loadDataScriptPath) throws IOException, SQLException {
        executeSqlScript(clearDataScriptPath);
        if (loadDataScriptPath != null) {
            executeSqlScript(loadDataScriptPath);
        }
    }

    private void executeSqlScript(String path) throws SQLException, IOException {
        String scriptContent = getScriptContent(path);
        List<String> statements = Arrays.asList(scriptContent.split("\n"));
        Statement st = con.createStatement();
        String delimiter = ";";
        String fullStatement = "";
        for (String statement : statements) {
            Pattern r = Pattern.compile("DELIMITER (.+)");
            Matcher m = r.matcher(statement);
            if (m.find()) {
                delimiter = m.group(1);
                continue;
            }

            fullStatement = fullStatement.equals("") ? statement : String.format("%s\n%s", fullStatement, statement);
            if (fullStatement.endsWith(delimiter)) {
                fullStatement = fullStatement.replace(delimiter, "");
                try {
                    st.executeUpdate(fullStatement);
                } catch (SQLException e) {
                    throw new SQLException(String.format("Error in SQL script: %s\nStatement: %s", path, statement), e);
                }
                fullStatement = "";
            }
        }
    }

    private String getScriptContent(String path) throws IOException {
        path = "/" + path;
        String s;
        StringBuilder sb = new StringBuilder();
        InputStream in = CrsDb.class.getResourceAsStream(path);
        BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        while ((s = br.readLine()) != null) {
            s = s.trim();
            if (!s.startsWith("#")) {
                sb.append(s).append("\n");
            }
        }
        br.close();
        return sb.toString();
    }

}
