package main.java.db;

import db.DataBase;
import main.java.model.BookingStatus;
import main.java.model.Reseller;
import org.joda.time.DateTime;

/**
 * Created by Andrii.Bakhtiozin on 05.06.2015.
 * andrii.bakhtiozin@viaamadeus.com
 */
public class SQLBooking {

    public static String getSupplierTicketConfirmationNumber(int bookingId, int passengerIndex) {
        String query = "SELECT supplierTicketNumber FROM " + DataBase.getDbName() + ".booking_passenger_service " +
                "WHERE bookingPassengerId =  (SELECT id FROM " + DataBase.getDbName() + ".booking_passenger " +
                "WHERE bookingId = '" + bookingId + "' LIMIT " + passengerIndex + ",1 )";
        String supplierTicketNumber = DataBase.getInstance().executeQuery(
                query, "supplierTicketNumber");
        assert (null != supplierTicketNumber);
        return supplierTicketNumber;
    }

    public static String getBookingSupplierConfirmationNumber(Reseller reseller, BookingStatus bookingStatus) {
        return DataBase.getInstance().executeQuery(
                "SELECT supplierOrderNumber FROM " + DataBase.getDbName() + ".booking b " +
                        "LEFT JOIN " + DataBase.getDbName() + ".reseller r " +
                        "ON r.id = b.resellerId " +
                        "LEFT JOIN " + DataBase.getDbName() + ".user u " +
                        "ON u.resellerId = b.createdBy " +
                        "r.code = '" + reseller.getResellerCode() + "' and u.userName = '" + reseller.getUserName() + "' and b.status = '" + bookingStatus + "' " +
                        "LIMIT 1", "supplierOrderNumber");
    }


    public static String getFirstBookingPassengerDocumentNumber(Reseller reseller, BookingStatus bookingStatus) {
        return DataBase.getInstance().executeQuery(
                "Select documentSeriesAndNumber FROM " + DataBase.getDbName() + ".booking_passenger " +
                        "WHERE bookingId = ( " +
                        "SELECT id FROM " + DataBase.getDbName() + ".booking " +
                        "WHERE resellerId = '" + getResellerId(reseller.getResellerCode()) + "'" +
                        " and createdBy = '" + getUserId(reseller.getResellerCode(), reseller.getUserName()) + "'" +
                        " and status = '" + bookingStatus + "' " +
                        "LIMIT 1)", "documentSeriesAndNumber");
    }

    public static String isBeddingInBooking(String bookingId) {
        return DataBase.getInstance().executeQuery(
                "SELECT withBedClothing FROM " + DataBase.getDbName() + ".book_request " +
                        " WHERE id = (" +
                        " SELECT brId FROM " + DataBase.getDbName() + ".booking WHERE id = " + bookingId + " )", "withBedClothing");
    }

    private static String getResellerId(String resellerCode) {
        return DataBase.getInstance().executeQuery(
                "SELECT id FROM " + DataBase.getDbName() + ".reseller WHERE code='" + resellerCode + "'", "id");
    }

    private static String getUserId(String resellerCode, String userName) {
        return DataBase.getInstance().executeQuery(
                "SELECT id FROM " + DataBase.getDbName() + ".user WHERE resellerId='" + getResellerId(resellerCode) + "'" +
                        " AND userName='" + userName + "'", "id");
    }

    public static String getTicketTypeByBookingId(String bookingId) {
        String query = "SELECT distinct t2.wsCode as ticketType FROM " + DataBase.getDbName() + ".booking_passenger_service t1 " +
                " right join " + DataBase.getDbName() + ".ticket_type t2" +
                " on t1.ticketTypeId = t2.id " +
                " WHERE t1.bookingPassengerId in " +
                " (SELECT id FROM " + DataBase.getDbName() + ".booking_passenger WHERE bookingId = (SELECT id FROM " + DataBase.getDbName() + ".booking WHERE id = " + bookingId + "))";
        return DataBase.getInstance().executeQuery(query, "ticketType");
    }

    public static void updateBookingConfirmationTime(String bookingId) {
        String query = "UPDATE " + DataBase.getDbName() + ".booking" +
                " SET confirmationTime = '" + new DateTime().minusHours(5).toString("yyyy-MM-dd HH:mm:ss") + "'" +
                " WHERE " + DataBase.getDbName() + ".booking.id = '" + bookingId + "';";
        DataBase.getInstance().executeUpdate(query);
    }

    public static String getBookingBusinessModel(String bookingId) {
        return DataBase.getInstance().executeQuery(
                "SELECT businessModel FROM " + DataBase.getDbName() + ".booking WHERE id='" + bookingId + "'", "businessModel");
    }
}
