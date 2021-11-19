package cases;

import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * JDBC PreparedStatement Insert Performance
 * <p>
 * PreparedStatement vs Statement vs ResultSet
 *
 * @author agui93
 * @since 2021/11/19
 */
public class JdbcCase5 {

    private static final String host = "127.0.0.1";
    private static final int port = 3306;
    private static final String user = "root";
    private static final String passwd = "123456";

    //CREATE DATABASE IF NOT EXISTS jdbc_test;
    private static final String dataBaseName = "jdbc_test";

    private static final String url = "jdbc:mysql://" + host + ":" + port + "/" + dataBaseName + "?"
            + "user=" + user + "&password=" + passwd + "&useSSL=false"
            + "&useServerPrepStmts=true";

    private static final String dropOldTableSql = "DROP TABLE IF EXISTS Profile;";

    private static final String createSql = "CREATE TABLE Profile ("
            + " ID INTEGER PRIMARY KEY AUTO_INCREMENT,"
            + " UserName VARCHAR(20) NOT NULL,"
            + " BirthDate DATE DEFAULT '2021-11-19')";

    private static final String selectSql = "SELECT * FROM Profile";

    private static final String selectCountSql = "SELECT COUNT(*) FROM Profile";

    private static final String insertSql = "INSERT INTO Profile (UserName, BirthDate) VALUES (?,?)";

    private static Connection buildConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Exception: " + e.getMessage());
        }
        return connection;
    }

    private static void dropOldTable() {
        Connection connection = buildConnection();
        if (connection == null) {
            System.out.println("dropOldTable failed because of null-connection");
            return;
        }

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(dropOldTableSql);
            preparedStatement.executeUpdate(dropOldTableSql);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static void createTable() {
        Connection connection = buildConnection();
        if (connection == null) {
            System.out.println("createTable failed because of null-connection");
            return;
        }

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(createSql);
            preparedStatement.executeUpdate(createSql);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static int selectCount() {
        Connection connection = buildConnection();
        if (connection == null) {
            System.out.println("searchProfiles failed because of null-connection");
            return -1;
        }
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(selectCountSql);
            resultSet = preparedStatement.executeQuery(selectCountSql);
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            try {
                connection.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return -1;
    }


    private static void batchInsertByPreparedStatement(Connection connection, int count) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(insertSql);
            Random r = new Random();
            for (int i = 0; i < count; i++) {
                preparedStatement.setString(1, Integer.toHexString(r.nextInt(999999)));
                preparedStatement.setString(2, "2021-01-01");
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private static void insertByPreparedStatement(Connection connection, int count) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(insertSql);
            Random r = new Random();
            for (int i = 0; i < count; i++) {
                preparedStatement.setString(1, Integer.toHexString(r.nextInt(999999)));
                preparedStatement.setString(2, "2021-01-01");
                preparedStatement.executeUpdate();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private static void insertByStatement(Connection connection, int count) {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            Random r = new Random();
            for (int i = 0; i < count; i++) {
                statement.executeUpdate("INSERT INTO Profile (UserName,BirthDate)"
                        + " VALUES ('"
                        + Integer.toHexString(r.nextInt(999999))
                        + "','"
                        + "2021-01-01"
                        + "')");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private static void insertByResultSet(Connection connection, int count) {
        ResultSet resultSet = null;
        Statement statement = null;

        try {
            statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
            resultSet = statement.executeQuery(selectSql);

            resultSet.moveToInsertRow();
            Random r = new Random();
            for (int i = 0; i < count; i++) {
                resultSet.updateString("UserName", Integer.toHexString(r.nextInt(999999)));
                resultSet.updateString("BirthDate", "2021-01-01");
                resultSet.insertRow();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private interface InsertExecutor {
        void execute(Connection connection, int count);
    }

    private static long performance(int count, InsertExecutor executor) {
        dropOldTable();
        createTable();
        Connection connection = buildConnection();

        try {
            long begin = System.currentTimeMillis();
            executor.execute(connection, count);
            return System.currentTimeMillis() - begin;
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static void comparePerformance(int count, int times) {
        long delta;

        delta = 0;
        for (int i = 0; i < times; i++) {
            delta += performance(count, (JdbcCase5::insertByResultSet));
        }
        delta = delta / times;
        System.out.printf("%5d ms used when insert %5d records by ResultSet.\n", delta, selectCount());

        delta = 0;
        for (int i = 0; i < times; i++) {
            delta += performance(count, (JdbcCase5::insertByStatement));
        }
        delta = delta / times;
        System.out.printf("%5d ms used when insert %5d records by Statement.\n", delta, selectCount());


        delta = 0;
        for (int i = 0; i < times; i++) {
            delta += performance(count, (JdbcCase5::insertByPreparedStatement));
        }
        delta = delta / times;
        System.out.printf("%5d ms used when insert %5d records by PreparedStatement.\n", delta, selectCount());


        delta = 0;
        for (int i = 0; i < times; i++) {
            delta += performance(count, (JdbcCase5::batchInsertByPreparedStatement));
        }
        delta = delta / times;
        System.out.printf("%5d ms used when insert %5d records by PreparedStatement-BatchInsert.\n", delta, selectCount());
    }

    public static void main(String[] args) {
        System.out.println("----------------------------------------------------");
        for (int count : Arrays.asList(100, 200, 500, 1000, 2000)) {
            comparePerformance(count, 5);
            System.out.println("=====================");
        }
        System.out.println("----------------------------------------------------");
    }

}
