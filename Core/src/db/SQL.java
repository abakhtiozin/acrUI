package db;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;


public class SQL {

    public static String getBackedBookingStatusByBookingId(String bookingId) {
        String status = DataBase.getInstance().executeQuery(
                "SELECT status FROM " +
                        DataBase.getBackEndDbName() + ".service " +
                        "WHERE id = ( " +
                        getBackendServiceIdByBookingId(bookingId) + ");", "status");
        assert (null != status);
        return status;
    }

    public static String getRefundStatusByBookingId(String bookingId) {
        String status = DataBase.getInstance().executeQuery(
                "SELECT status FROM `" + DataBase.getDbName() + "`.`refund` WHERE `bookingId` = '" + bookingId + "' ORDER BY `id` DESC LIMIT 1;;", "status");
        assert (null != status);
        return status;
    }

    public static String getBackendServiceIdByBookingId(String bookingId) {
        String backendServiceId = DataBase.getInstance().executeQuery(
                "SELECT backendServiceId FROM " +
                        DataBase.getDbName() + ".booking " +
                        "WHERE id = " + bookingId, "backendServiceId");
        assert (null != backendServiceId);
        return backendServiceId;
    }

    public static String getBookingStatusByBookingId(String bookingId) {
        String status = DataBase.getInstance().executeQuery(
                "SELECT status FROM " + DataBase.getDbName() + ".booking WHERE id = " + bookingId, "status");
        assert (null != status);
        return status;
    }


    public static String getBookingSupplierConfirmationNumberByBookingId(String bookingId) {
        return DataBase.getInstance().executeQuery(
                "SELECT supplierReferenceNumber FROM " + DataBase.getDbName() + ".booking WHERE id = " + bookingId, "supplierReferenceNumber");
    }

    public static String getBookingSupplierByBookingId(String bookingId) {
        String token = DataBase.getInstance().executeQuery(
                "SELECT t1.token FROM " + DataBase.getDbName() + ".booking t0" +
                        " LEFT JOIN " + DataBase.getDbName() + ".supplier t1" +
                        " ON t0.supplierId = t1.id" +
                        " WHERE t0.id = '" + bookingId + "'", "token");
        assert (null != token);
        return token;
    }

    public static String getCountryCode(String num) {
        String code = DataBase.getInstance().executeQuery(
                "SELECT code FROM " + DataBase.getDbName() + ".country WHERE code !='GE-AB' AND phoneCode IS NOT NULL LIMIT " + num + ",1", "code");
        assert (null != code);
        return code;
    }

    public static String getDocumentIdByNameAndSupplier(String docName, String supplier) {
        docName = docName.replaceAll("_", " ");
        String id = DataBase.getInstance().executeQuery(
                "SELECT t0.documentTypeId FROM " + DataBase.getDbName() + ".document_type_of_supplier t0 " +
                        "JOIN " + DataBase.getDbName() + ".document_type t1 " +
                        "ON t0.documentTypeId=t1.id WHERE t0.supplierId = (SELECT id FROM " + DataBase.getDbName() + ".supplier WHERE token = '" + supplier + "') " +
                        "AND t1.name = '" + docName + "';", "documentTypeId");
        assert (null != id);
        return id;
    }

    public static String getPassengerDocumentNumByBookingIdAndPassngerN(int bookingId, int passengerNumber) {
        String documentSeriesAndNumber = DataBase.getInstance().executeQuery(
                "SELECT documentSeriesAndNumber" +
                        " FROM " + DataBase.getDbName() + ".ticket_passenger " +
                        " WHERE ticketId in (" +
                        " SELECT id FROM " +
                        " " + DataBase.getDbName() + ".ticket " +
                        " WHERE bookingId = " + bookingId + ")" +
                        " LIMIT " + passengerNumber + ",1;", "documentSeriesAndNumber");
        assert (null != documentSeriesAndNumber);
        return documentSeriesAndNumber;
    }

    public static String getBookingSupplierOrderNumber(int bookingId) {
        String supplierOrderNumber = DataBase.getInstance().executeQuery(
                "SELECT supplierOrderNumber" +
                        " FROM " + DataBase.getDbName() + ".booking " +
                        " WHERE id = " + bookingId, "supplierOrderNumber");
        assert (null != supplierOrderNumber);
        return supplierOrderNumber;
    }

    public static void changeResellerPaperTBookingRights(String resellerCode, int enable) {
        DataBase.getInstance().executeUpdate("UPDATE " + DataBase.getBackEndDbName() + ".reseller " +
                " SET contentrail_paper_tickets_booking_enabled=" + enable +
                " WHERE resellerCode='" + resellerCode + "'");
    }

    public static String getSupplierByTransporterName(String transporter) {
        String supplier = DataBase.getInstance().executeQuery("SELECT token " +
                "FROM " + DataBase.getDbName() + ".supplier " +
                "WHERE id = ( " +
                "SELECT supplierId FROM " +
                DataBase.getDbName() + ".transporter_to_supplier " +
                "WHERE transporterId = ( " +
                "SELECT id FROM " +
                DataBase.getDbName() + ".transporter " +
                "WHERE name = '" + transporter + "'));", "token");
        assert (null != supplier);
        return supplier;
    }


    public static String getCurrencyRate(String currency, String baseCurrency) {
        String currencyRate = DataBase.getInstance().executeQuery("SELECT value FROM " + DataBase.getBackEndDbName() + ".currency_rate " +
                "WHERE currency_rate_group_id = (SELECT id FROM " + DataBase.getBackEndDbName() + ".currency_rate_group " +
                "WHERE base_currency = '" + baseCurrency + "' and partner_id = 0 " +
                " ORDER BY id desc " +
                " LIMIT 1) " +
                "AND currency ='" + currency + "';", "value");
        assert (null != currencyRate);
        return currencyRate;
    }


    public static String getResellerCurrency(String resellerCode) {
        String currency = DataBase.getInstance().executeQuery("SELECT value FROM " + DataBase.getBackEndDbName() + ".reseller_preference WHERE idReseller = (" +
                "SELECT id FROM " + DataBase.getBackEndDbName() + ".reseller WHERE resellerCode = '" + resellerCode + "')" +
                " AND var = \"invoiceCurrency\"", "value");
        assert (null != currency);
        return currency;
    }

    public static HashMap<String, String> getInternalSupplierCommission(String supplierName, String marketSymbol) {
        return DataBase.getInstance().getHashMap("SELECT t0.value as VALUE, t1.work_currency as CURRENCY, t0.type as COMMISSION_TYPE " +
                "FROM " + DataBase.getBackEndDbName() + ".internal_market_markup t0 " +
                "LEFT JOIN " + DataBase.getBackEndDbName() + ".supplier t1 " +
                "on t0.provider_id = t1.id " +
                "WHERE t0.market_id = '" + marketSymbol + "' and t1.name = '" + supplierName + "'");
    }

    public static HashMap<String, String> getResellerCommissionForSupplier(String resellerCode, String supplier, String operation) {
        String query = "SELECT amount as VALUE, r.currencyCode as CURRENCY, type as COMMISSION_TYPE FROM " + DataBase.getDbName() + ".reseller_commission rc" +
                " RIGHT JOIN " + DataBase.getDbName() + ".reseller r" +
                " ON r.id = rc.resellerId" +
                " WHERE r.code = '" + resellerCode + "'" +
                " AND destination = '" + operation + "'" +
                " AND supplierCategoryId = (SELECT categoryId FROM " + DataBase.getDbName() + ".supplier" +
                " WHERE token = '" + supplier + "')";
        return DataBase.getInstance().getHashMap(query);
    }

    public static String getTransporterMinOfferPrice(String resellerCode, String userName, Date departTime, Date arrivalTime) {
        SimpleDateFormat sqlDateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String query = "SELECT min(transporterPrice)" +
                " FROM " + DataBase.getDbName() + ".sr_price WHERE id in (" +
                " SELECT minPriceId FROM " + DataBase.getDbName() + ".sr_trip_offer_group WHERE srTripId = (" +
                " SELECT distinct srTripId FROM " + DataBase.getDbName() + ".sr_trip_segment WHERE srTripId in ( SELECT id  FROM " + DataBase.getDbName() + ".sr_trip WHERE searchQueryId = (" +
                " SELECT lastSearchQueryId FROM " + DataBase.getDbName() + ".user WHERE resellerCode = '" + resellerCode + "' AND username = '" + userName + "')) " +
                " group by srTripId" +
                " having min(passengersDepartTime) = '" + sqlDateFormater.format(departTime) + "' and max(passengersArrivalTime) = '" + sqlDateFormater.format(arrivalTime) + "'));";
        String minTransporterPrice = DataBase.getInstance().executeQuery(query, "min(transporterPrice)");
        assert (null != minTransporterPrice);
        return minTransporterPrice;
    }

    @Deprecated
    private static String getResellerLastSQid(String resellerCode, String userName) {
        String lastSearchQueryId = DataBase.getInstance().executeQuery("SELECT lastSearchQueryId FROM " + DataBase.getBackEndDbName() + ".user " +
                "WHERE resellerCode = '" + resellerCode + "' AND username = '" + userName + "'", "lastSearchQueryId");
        assert (null != lastSearchQueryId);
        return lastSearchQueryId;
    }

    @Deprecated
    public static String getResellerLastBookingId(String resellerCode, String userName) {
        String lastSearchQueryId = DataBase.getInstance().executeQuery("SELECT id FROM " + DataBase.getDbName() + ".booking " +
                "WHERE resellerCode = '" + resellerCode + "' AND username = '" + userName + "' ORDER BY id desc LIMIT 0,1", "id");
        assert (null != lastSearchQueryId);
        return lastSearchQueryId;
    }
}