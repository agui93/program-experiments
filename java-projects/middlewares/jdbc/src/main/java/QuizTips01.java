import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.sql.*;
import java.util.Enumeration;
import java.util.Random;

//http://www.herongyang.com/JDBC/MySQL-JDBC-Driver.html

/**
 * CREATE DATABASE IF NOT EXISTS jdbc_test;
 */
public class QuizTips01 {
    private final static String databaseName = "jdbc_test";
    private final static String user = "root";
    private final static String passwd = "123456";

    private static void printDrivers() throws ClassNotFoundException {
        System.out.println("Before loading SQLServerDriver:");
        Enumeration<?> driverList = DriverManager.getDrivers();
        while (driverList.hasMoreElements()) {
            Driver driverClass = (Driver) driverList.nextElement();
            System.out.println("   " + driverClass.getClass().getName());
        }

        Class.forName("com.mysql.jdbc.Driver");

        System.out.println("After loading SQLServerDriver:");
        driverList = DriverManager.getDrivers();
        while (driverList.hasMoreElements()) {
            Driver driverClass = (Driver) driverList.nextElement();
            System.out.println("   " + driverClass.getClass().getName());
        }
    }

    private static void connectionByDriverManager() throws ClassNotFoundException, SQLException {

        // Loading a JDBC driver
        Class.forName("com.mysql.jdbc.Driver");


        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + databaseName + "?" + "user=" + user + "&password=" + passwd);
        System.out.println("Connected with host:port/database.");
        con.close();

        con = DriverManager.getConnection("jdbc:mysql://:3306/" + databaseName + "?" + "user=" + user + "&password=" + passwd);
        System.out.println("Connected with default host.");
        con.close();

        con = DriverManager.getConnection("jdbc:mysql://localhost/" + databaseName + "?" + "user=" + user + "&password=" + passwd);
        System.out.println("Connected with default port.");
        con.close();

        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/?" + "user=" + user + "&password=" + passwd);
        System.out.println("Connected with no database.");
        con.close();

        con = DriverManager.getConnection("jdbc:mysql:///?" + "user=" + user + "&password=" + passwd);
        System.out.println("Connected with properties only.");
        con.close();
    }

    private static void connectionByDataSource() throws SQLException {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setServerName("localhost");
        dataSource.setPort(3306);
        dataSource.setDatabaseName(databaseName);
        dataSource.setUser(user);
        dataSource.setPassword(passwd);


        Connection connection = dataSource.getConnection();

        DatabaseMetaData meta = connection.getMetaData();
        System.out.println("Server name: " + meta.getDatabaseProductName());
        System.out.println("Server version: " + meta.getDatabaseProductVersion());

        connection.close();
    }

    private static void checkDriverServerInfo() {
        Connection con;
        try {
            // Setting up the DataSource object
            com.mysql.jdbc.jdbc2.optional.MysqlDataSource ds = new com.mysql.jdbc.jdbc2.optional.MysqlDataSource();
            ds.setServerName("localhost");
            ds.setPortNumber(3306);
            ds.setDatabaseName(databaseName);
            ds.setUser(user);
            ds.setPassword(passwd);

            // Getting a connection object
            con = ds.getConnection();

            // Getting driver and database info
            DatabaseMetaData meta = con.getMetaData();
            System.out.println("Server name: " + meta.getDatabaseProductName());
            System.out.println("Server version: " + meta.getDatabaseProductVersion());
            System.out.println("Driver name: " + meta.getDriverName());
            System.out.println("Driver version: " + meta.getDriverVersion());
            System.out.println("JDBC major version: " + meta.getJDBCMajorVersion());
            System.out.println("JDBC minor version: " + meta.getJDBCMinorVersion());

            // Closing the connection
            con.close();
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    private static void createTableWithAutoIncrement() {
        Connection con;
        try {
            // Setting up the DataSource object
            com.mysql.jdbc.jdbc2.optional.MysqlDataSource ds
                    = new com.mysql.jdbc.jdbc2.optional.MysqlDataSource();
            ds.setServerName("localhost");
            ds.setPortNumber(3306);
            ds.setDatabaseName(databaseName);
            ds.setUser(user);
            ds.setPassword(passwd);

            // Getting a connection object
            con = ds.getConnection();

            // Creating a database table
            Statement sta = con.createStatement();
            int count = sta.executeUpdate(
                    "CREATE TABLE Profile ("
                            + " ID INTEGER PRIMARY KEY AUTO_INCREMENT,"
                            + " FirstName VARCHAR(20) NOT NULL,"
                            + " LastName VARCHAR(20),"
                            + " Point REAL DEFAULT 0.0,"
                            + " BirthDate DATE DEFAULT '1998-12-31',"
                            + " ModTime TIMESTAMP DEFAULT '2016-12-31 23:59:59.999')");
            System.out.println("Table created.");
            sta.close();

            con.close();
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    //前提数据库和表已创建
    private static void insertStatement() {
        Connection con;
        try {
            // Setting up the DataSource object
            com.mysql.jdbc.jdbc2.optional.MysqlDataSource ds = new com.mysql.jdbc.jdbc2.optional.MysqlDataSource();
            ds.setServerName("localhost");
            ds.setPortNumber(3306);
            ds.setDatabaseName(databaseName);
            ds.setUser(user);
            ds.setPassword(passwd);


            // Getting a connection object and statement object
            con = ds.getConnection();
            Statement sta = con.createStatement();
            int count = 0;

            // insert a single row using default values
            count += sta.executeUpdate("INSERT INTO Profile (FirstName) VALUES ('Herong')");

            // insert a single row using provided values
            count += sta.executeUpdate("INSERT INTO Profile (FirstName, LastName, Point, BirthDate) VALUES ('Janet', 'Gates', 999.99, '1984-10-13')");


            // insert rows with loop with random values
            Random r = new Random();
            for (int i = 0; i < 10; i++) {
                float points = 1000 * r.nextFloat();
                String firstName = Integer.toHexString(r.nextInt(9999));
                String lastName = Integer.toHexString(r.nextInt(999999));
                count += sta.executeUpdate("INSERT INTO Profile" + " (FirstName, LastName, Point)"
                        + " VALUES ('" + firstName + "', '" + lastName + "', " + points + ")");
            }

            // How many rows were inserted
            System.out.println("Number of rows inserted: " + count);

            // Checking inserted rows
            ResultSet res = sta.executeQuery("SELECT * FROM Profile");
            System.out.println("List of Profiles: ");
            while (res.next()) {
                System.out.println("  " + res.getInt("ID")
                        + ", " + res.getString("FirstName")
                        + ", " + res.getString("LastName")
                        + ", " + res.getDouble("Point")
                        + ", " + res.getDate("BirthDate")
                        + ", " + res.getTimestamp("ModTime"));
            }
            res.close();

            sta.close();
            con.close();
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }


    private static void dropTable() {
        Connection con;
        try {
            // Setting up the DataSource object
            com.mysql.jdbc.jdbc2.optional.MysqlDataSource ds = new com.mysql.jdbc.jdbc2.optional.MysqlDataSource();
            ds.setServerName("localhost");
            ds.setPortNumber(3306);
            ds.setDatabaseName(databaseName);
            ds.setUser(user);
            ds.setPassword(passwd);

            // Getting a connection object
            con = ds.getConnection();

            // Creating a database table
            Statement sta = con.createStatement();
            int count = sta.executeUpdate("DROP TABLE IF EXISTS Profile ");
            System.out.println("DROP Table IF EXISTS Profile.");
            sta.close();

            con.close();
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }


    public static void main(String[] args) throws Exception {
        System.out.println("-----------------");

        printDrivers();
        System.out.println("-----------------");

        connectionByDriverManager();
        System.out.println("-----------------");

        connectionByDataSource();
        System.out.println("-----------------");

        checkDriverServerInfo();
        System.out.println("-----------------");


        dropTable();
        System.out.println("-----------------");

        createTableWithAutoIncrement();
        System.out.println("-----------------");

        insertStatement();
        System.out.println("-----------------");
    }


}
