package cases;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.sql.*;

/**
 * build connection by DriverManager
 * build connection by DataSource
 *
 * @author agui93
 * @since 2021/11/18
 */
public class JdbcCase2 {

    private static final String host = "127.0.0.1";
    private static final int port = 3306;
    private static final String user = "root";
    private static final String passwd = "123456";

    private static void buildConnectionByDriverManager() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/?" + "user=" + user + "&password=" + passwd);

            DatabaseMetaData dbMeta = connection.getMetaData();
            System.out.println("Server name: " + dbMeta.getDatabaseProductName());
            System.out.println("Server version: " + dbMeta.getDatabaseProductVersion());
            System.out.println("Driver name: " + dbMeta.getDriverName());
            System.out.println("Driver version: " + dbMeta.getDriverVersion());
            System.out.println("JDBC major version: " + dbMeta.getJDBCMajorVersion());
            System.out.println("JDBC minor version: " + dbMeta.getJDBCMinorVersion());
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Exception: " + e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("Exception: " + e.getMessage());
                }
            }
        }
    }

    private static void buildConnectionByDataSource() {
        Connection connection = null;
        try {
            MysqlDataSource dataSource = new MysqlDataSource();
            dataSource.setServerName(host);
            dataSource.setPort(port);
            dataSource.setUser(user);
            dataSource.setPassword(passwd);

            connection = dataSource.getConnection();
            DatabaseMetaData dbMeta = connection.getMetaData();

            System.out.println("Server name: " + dbMeta.getDatabaseProductName());
            System.out.println("Server version: " + dbMeta.getDatabaseProductVersion());
            System.out.println("Driver name: " + dbMeta.getDriverName());
            System.out.println("Driver version: " + dbMeta.getDriverVersion());
            System.out.println("JDBC major version: " + dbMeta.getJDBCMajorVersion());
            System.out.println("JDBC minor version: " + dbMeta.getJDBCMinorVersion());
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("Exception: " + e.getMessage());
                }
            }
        }
    }


    public static void main(String[] args) {
        System.out.println("----------------------------------------------------");
        System.out.println("buildConnectionByDriverManager:");
        buildConnectionByDriverManager();

        System.out.println("----------------------------------------------------");
        System.out.println("buildConnectionByDataSource:");
        buildConnectionByDataSource();
        System.out.println("----------------------------------------------------");
    }

}
