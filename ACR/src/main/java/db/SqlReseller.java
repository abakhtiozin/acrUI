package main.java.db;

import db.DataBase;
import main.java.model.Reseller;

import java.util.HashMap;

/**
 * Created by Andrii.Bakhtiozin on 13.05.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class SqlReseller {
    public static HashMap<String, String> getUserServicesAccess(String resellerCode, String user) {
        String sql = "SELECT enable_russian_railway, enable_international_railway, enable_lowcost_flight, enable_kazakh_railway " +
                " FROM " + DataBase.getBackEndDbName() + ".reseller_user" +
                " WHERE username = '" + user + "' AND idParent = (SELECT id FROM " + DataBase.getBackEndDbName() + ".reseller WHERE resellerCode = '" + resellerCode + "');";
        return DataBase.getInstance().getHashMap(sql);
    }

    public static void setTfEnableForResellerUser(String resellerCode, String user, boolean enabled) {
        int i = enabled ? 1 : 0;
        DataBase.getInstance().executeUpdate("UPDATE " + DataBase.getBackEndDbName() + ".reseller_user" +
                " SET enable_lowcost_flight = " + i +
                " WHERE username = '" + user + "' AND idParent = (SELECT id FROM " + DataBase.getBackEndDbName() + ".reseller WHERE resellerCode = '" + resellerCode + "');");
    }

    public static void setAcpEnableForResellerUser(String resellerCode, String user, boolean enabled) {
        int i = enabled ? 1 : 0;
        DataBase.getInstance().executeUpdate("UPDATE " + DataBase.getBackEndDbName() + ".reseller_user" +
                " SET enable_international_railway = " + i +
                " WHERE username = '" + user + "' AND idParent = (SELECT id FROM " + DataBase.getBackEndDbName() + ".reseller WHERE resellerCode = '" + resellerCode + "');");
    }

    public static void setUfsEnableForResellerUser(String resellerCode, String user, boolean enabled) {
        int i = enabled ? 1 : 0;
        DataBase.getInstance().executeUpdate("UPDATE " + DataBase.getBackEndDbName() + ".reseller_user" +
                " SET enable_russian_railway = " + i +
                " WHERE username = '" + user + "' AND idParent = (SELECT id FROM " + DataBase.getBackEndDbName() + ".reseller WHERE resellerCode = '" + resellerCode + "');");
    }

    public static void setIpEnableForResellerUser(String resellerCode, String user, boolean enabled) {
        int i = enabled ? 1 : 0;
        DataBase.getInstance().executeUpdate("UPDATE " + DataBase.getBackEndDbName() + ".reseller_user" +
                " SET enable_kazakh_railway = " + i +
                " WHERE username = '" + user + "' AND idParent = (SELECT id FROM " + DataBase.getBackEndDbName() + ".reseller WHERE resellerCode = '" + resellerCode + "');");
    }

    public static byte[] getLastResellerRequestLog(Reseller reseller, String testStartTime) {
        return DataBase.getInstance().executeQueryBytes(
                "SELECT UNCOMPRESS(requestCompressed) FROM " + DataBase.getDbName() + ".supplier_communication_log " +
                        " WHERE resellerCode = '" + reseller.getResellerCode() + "'" +
                        " AND userName = '" + reseller.getUserName() + "'" +
                        " AND requestTime > '" + testStartTime + "'" +
                        " ORDER BY id desc " +
                        " LIMIT 1;", "UNCOMPRESS(requestCompressed)");
    }

    public static byte[] getLastResellerResponseLog(Reseller reseller, String testStartTime) {
        return DataBase.getInstance().executeQueryBytes(
                "SELECT UNCOMPRESS(responseCompressed) FROM " + DataBase.getDbName() + ".supplier_communication_log " +
                        " WHERE resellerCode = '" + reseller.getResellerCode() + "'" +
                        " AND userName = '" + reseller.getUserName() + "'" +
                        " AND requestTime > '" + testStartTime + "'" +
                        " ORDER BY id desc " +
                        " LIMIT 1;", "UNCOMPRESS(responseCompressed)");
    }

    public static String getLastResellerLogExecutionTime(Reseller reseller) {
        return DataBase.getInstance().executeQuery(
                "SELECT executionTime FROM " + DataBase.getDbName() + ".supplier_communication_log " +
                        " WHERE resellerCode = '" + reseller.getResellerCode() + "'" +
                        " AND userName = '" + reseller.getUserName() + "'" +
                        " ORDER BY id desc " +
                        " LIMIT 1;", "executionTime");
    }

    public static HashMap<String, String> getLastResellerCommunicationLog(Reseller reseller, String testStartTime) {
        return DataBase.getInstance().getHashMap(
                "SELECT id as logId,url,requestTime,requestType,UNCOMPRESS(responseCompressed),UNCOMPRESS(requestCompressed),executionTime FROM " + DataBase.getDbName() + ".supplier_communication_log " +
                        " WHERE resellerCode = '" + reseller.getResellerCode() + "'" +
                        " AND userName = '" + reseller.getUserName() + "'" +
                        " AND requestTime > '" + testStartTime + "'" +
                        " ORDER BY id desc " +
                        " LIMIT 1;");
    }
}
