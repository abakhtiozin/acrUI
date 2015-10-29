package db;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class DataBase {
    // ------------------------------ FIELDS ------------------------------
    public static final String ZERO_DATE = "0000-00-00 00:00:00";

    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String ZERO_DATE_ERROR = "S1009";

    private static Connection connection;
    private static volatile Statement statement;

    private static String host;
    private static String dbName;
    private static String backEndDbName;
    private static String login;
    private static String password;

    private static volatile DataBase instance;

// -------------------------- STATIC M --------------------------

    private DataBase() {
    }

    public static DataBase getInstance() {
        DataBase localInstance = instance;
        if (null == localInstance) {
            synchronized (DataBase.class) {
                localInstance = instance;
                if (null == localInstance) {
                    instance = localInstance = new DataBase();
                }
            }
        }
        return localInstance;
    }

    public static DataBase getInstance(String host, String dbName, String backEndDbName, String login, String password) throws
            SQLException {
        DataBase localInstance = instance;
        if (null == localInstance) {
            synchronized (DataBase.class) {
                localInstance = instance;
                if (null == localInstance) {
                    instance = new DataBase();
                    DataBase.host = host;
                    DataBase.login = login;
                    DataBase.password = password;
                    DataBase.dbName = dbName;
                    DataBase.backEndDbName = backEndDbName;

                    setConnection();
                    createStatement();
                }
            }
        }
        return instance;
    }

    public static DataBase getInstance(String host, String dbName, String login, String password) throws
            SQLException {
        DataBase localInstance = instance;
        if (null == localInstance) {
            synchronized (DataBase.class) {
                localInstance = instance;
                if (null == localInstance) {
                    instance = new DataBase();
                    DataBase.host = host;
                    DataBase.login = login;
                    DataBase.password = password;
                    DataBase.dbName = dbName;

                    setConnection();
                    createStatement();
                }
            }
        }
        return instance;
    }

    private static void setConnection() {
        try {
            Class.forName(DRIVER);
            try {
                connection = DriverManager.getConnection(host, login, password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


// --------------------------- CONSTRUCTORS ---------------------------

    private static void createStatement() throws SQLException {
        statement = connection.createStatement();
    }

// -------------------------- METHODS --------------------------

    public static String getDbName() {
        return dbName;
    }

    public static String getBackEndDbName() {
        return backEndDbName;
    }

    public ResultSet executeQuery(String aSQLCommand) {
        try {
            return statement.executeQuery(aSQLCommand);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String executeQuery(String aSQLCommand, String aColumnName) {
        String return_db_data = null;
        try {
            ResultSet rs = statement.executeQuery(aSQLCommand);
            while (rs.next()) {
                return_db_data = rs.getString(aColumnName);
            }
        } catch (SQLException sqlEx) {
            /* do not remove, fix for data fields with zeros */
            if (sqlEx.getSQLState().equals(ZERO_DATE_ERROR)) {
                return_db_data = ZERO_DATE;
            } else {
                sqlEx.printStackTrace();
            }
        }
        return return_db_data;
    }

    public byte[] executeQueryBytes(String aSQLCommand, String aColumnName) {
        byte[] return_db_data = null;
        try {
            ResultSet rs = statement.executeQuery(aSQLCommand);
            while (rs.next()) {
                return_db_data = rs.getBytes(aColumnName);
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        return return_db_data;
    }

    public int executeUpdate(String aSQLCommand) {
        try {
            return statement.executeUpdate(aSQLCommand);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<HashMap<String, String>> getList(String aSQLCommand) {
        ResultSet rs = executeQuery(aSQLCommand);
        ResultSetMetaData md = null;
        List<HashMap<String, String>> list = new ArrayList<>();
        try {
            md = rs.getMetaData();
            int columns = md.getColumnCount();
            list = new ArrayList<>();
            while (rs.next()) {
                HashMap<String, String> row = new HashMap<>(columns);
                for (int i = 1; i <= columns; ++i) {
                    try {
                        row.put(md.getColumnLabel(i), rs.getObject(i).toString());
                    } catch (NullPointerException e) {
                        row.put(md.getColumnLabel(i), "null");
                    }
                }
                list.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public HashMap<String, String> getHashMap(String aSQLCommand) {
        List<HashMap<String, String>> list = getList(aSQLCommand);
        return (list.size() > 0) ? list.get(0) : null;
    }

// ------------------------ CANONICAL METHODS ------------------------

    @Override
    protected void finalize() throws Throwable {
        closeConnection();
        super.finalize();
    }

    private void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        closeConnection();
    }

}
