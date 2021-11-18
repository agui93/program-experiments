package cases;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Enumeration;

/**
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

        //引入mysql jdbc driver的MysqlDataSource
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            MysqlDataSource dataSource = new MysqlDataSource();
            dataSource.setServerName("localhost");
            dataSource.setPort(port);
            dataSource.setDatabaseName("test");
            dataSource.setUser(user);
            dataSource.setPassword(passwd);

            //一般直接用DataSource接口
            Connection connection = mysqlDataSource.getConnection();
            DatabaseMetaData dbMeta = connection.getMetaData();
            System.out.println("Server name: " + dbMeta.getDatabaseProductName());
            System.out.println("Server version: " + dbMeta.getDatabaseProductVersion());
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }


    public static void main(String[] args) {
        System.out.println("--------------------------------");
        System.out.println("buildConnectionByDriverManager:");
        buildConnectionByDriverManager();
        System.out.println();

        System.out.println("--------------------------------");
        System.out.println("buildConnectionByDataSource:");
        buildConnectionByDataSource();

    }

}
