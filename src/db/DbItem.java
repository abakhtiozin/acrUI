package db;

import java.sql.*;

/**
 * Created by bakhtiozin on 08.01.2015.
 */
public class DbItem {

    private static final String PERMISSION_QUERY = "SELECT permission FROM users where username =?";
    private static final String USERNAME_QUERY = "SELECT username FROM users where username =?";
    private static final String FILM_TITLE_QUERY = "SELECT name FROM movies where name =?";
    private static final String DELETE_FILM_QUERY = "DELETE FROM movies where name =?";
    private static final String DELETE_USERS = "DELETE FROM users\n"+
            "WHERE username in ('Guest','Editor','admin2');";
    private static final String INSERT_USERS = "INSERT INTO users (email,username, password, permission) \n"+
            "VALUES ('Editor@getRailTrips.com', 'Editor', '098f6bcd4621d373cade4e832627b4f6', 1),\n"+
            "('Guest@getRailTrips.com', 'Guest', '098f6bcd4621d373cade4e832627b4f6', 0),\n"+
            "('admin2@getRailTrips.com', 'admin2', '098f6bcd4621d373cade4e832627b4f6', 2);";
    private static final String CURRENCY_RATE_QUERY = "SELECT `value` FROM `contentinn`.`currency_rate` WHERE currency = ? AND currency_rate_group_id = (SELECT id FROM `contentinn`.`currency_rate_group` WHERE add_admin_user_id IS NOT NULL ORDER BY Id DESC LIMIT 1) ORDER BY id DESC LIMIT 1;";
    private static final String TRANSPORTER_MIN_PRICE = "SELECT \n" +
            "MIN(minTransporterPrice) AS price\n" +
            "FROM\n" +
            "  `contentrail_dev`.`sr_trip_offer_group` \n" +
            "  WHERE srTripId = (SELECT \n" +
            "  `id` \n" +
            "FROM\n" +
            "  `contentrail_dev`.`sr_trip` \n" +
            "  WHERE searchQueryId = (SELECT \n" +
            "  id\n" +
            "FROM\n" +
            "  `contentrail_dev`.`search_query` \n" +
            "  WHERE arrivalCityId = (SELECT `id` FROM  `contentrail_dev`.`city` WHERE NAME = ?) \n" +
            "  AND departCityId = (SELECT `id` FROM  `contentrail_dev`.`city` WHERE NAME = ?)\n" +
            "  AND toInternationalSystem = 1\n" +
            "ORDER BY Id DESC\n" +
            "LIMIT 1)\n" +
            "LIMIT 1)\n";

    private static final String OFFER_NAME = "SELECT   \n"+
            "  offerName  \n"+
            "FROM\n"+
            "  `contentrail_dev`.`sr_trip_offer` \n"+
            "  WHERE minTransporterPrice = ?\n"+
            "  ORDER BY Id DESC\n"+
            "LIMIT 1;";

    private Connection con;
    private static DbItem _instance;
    private DbItem() {
        String url = "jdbc:mysql://172.19.5.222:3306/contentinn";
        String name = "unicontent";
        String password = "2454";
        try {
            con = DriverManager.getConnection(url, name, password);
        } catch (Exception e) {
            System.err.println(e);
        }
    }
    public static DbItem get_instance(){
        if (_instance == null){
            _instance = new DbItem();
        }
        return _instance;
    }
    public void closeConnection(){
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean isUserExistInBase(String userName){
        boolean result = false;
        try {
            PreparedStatement st = con.prepareStatement(USERNAME_QUERY, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            st.setString(1, userName);
            ResultSet rs = st.executeQuery();
            result = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public double getCurrencyRate(String currency){
        double currencyValue = 0.0;
        try {
            PreparedStatement st = con.prepareStatement(CURRENCY_RATE_QUERY, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            st.setString(1, currency);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                currencyValue = rs.getDouble("value");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return currencyValue;
    }

    public String getOfferName(String minPrice){
        String offerName = "";
        try {
            PreparedStatement st = con.prepareStatement(OFFER_NAME, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            st.setString(1, minPrice);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                offerName = rs.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return offerName;
    }

    public double getTransporterMinPrice(String arrivalCity, String destCity){
        double price = 0.0;
        try {
            PreparedStatement st = con.prepareStatement(TRANSPORTER_MIN_PRICE, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            st.setString(1, arrivalCity);
            st.setString(2, destCity);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                price = rs.getDouble("price");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return price;
    }

    public boolean isFilmExsistInDB(String filmTitle){
        PreparedStatement st = null;
        boolean result = false;
        try {
            st = con.prepareStatement(FILM_TITLE_QUERY, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            st.setString(1, filmTitle);
            ResultSet rs = st.executeQuery();
            result = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    public boolean deleteFilmByName(String filmTitle){
        PreparedStatement st = null;
        try {
            st = con.prepareStatement(DELETE_FILM_QUERY, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            st.setString(1,filmTitle);
            st.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isFilmExsistInDB(filmTitle);
    }
    public void deleteUsers(){
        PreparedStatement st = null;
        try {
            st = con.prepareStatement(DELETE_USERS, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void createUsers(){
        PreparedStatement st = null;
        try {
            st = con.prepareStatement(INSERT_USERS, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
