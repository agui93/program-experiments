package tips;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class Utils {

    static DataSource parseDataSource(String databaseName, String user, String passwd) {
        com.mysql.jdbc.jdbc2.optional.MysqlDataSource ds = new com.mysql.jdbc.jdbc2.optional.MysqlDataSource();
        ds.setServerName("localhost");
        ds.setPortNumber(3306);
        ds.setDatabaseName(databaseName);
        ds.setUser(user);
        ds.setPassword(passwd);
        return ds;
    }

    static Connection parseConnection(boolean useServerPrepStmts, String databaseName, String user, String passwd) throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/" + databaseName + "?" + "user=" + user + "&password=" + passwd + "&useServerPrepStmts=" + useServerPrepStmts);
    }

    static void dropTable(DataSource ds) {
        Connection con = null;
        try {
            // Getting a connection object
            con = ds.getConnection();

            // Creating a database table
            Statement sta = con.createStatement();
            sta.executeUpdate("DROP TABLE IF EXISTS Profile ");
            System.out.println("DROP Table IF EXISTS Profile.");
            sta.close();

            con.close();
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    static void createTableWithAutoIncrement(DataSource ds) {
        Connection con = null;
        try {
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

}
