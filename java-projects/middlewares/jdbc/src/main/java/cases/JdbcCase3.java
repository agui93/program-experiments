package cases;

import java.sql.*;

/**
 * JDBC Statement
 *
 * @author agui93
 * @since 2021/11/19
 */
public class JdbcCase3 {
    private static final String host = "127.0.0.1";
    private static final int port = 3306;
    private static final String user = "root";
    private static final String passwd = "123456";

    //CREATE DATABASE IF NOT EXISTS jdbc_test;
    private static final String dataBaseName = "jdbc_test";

    private static final String url = "jdbc:mysql://" + host + ":" + port + "/" + dataBaseName + "?" + "user=" + user + "&password=" + passwd + "&useSSL=false";

    private static final String tableName = "Profile";

    private static final String dropOldTableSql = "DROP TABLE IF EXISTS Profile;";

    private static final String createSql = "CREATE TABLE Profile ("
            + " ID INTEGER PRIMARY KEY AUTO_INCREMENT,"
            + " UserName VARCHAR(20) NOT NULL,"
            + " BirthDate DATE DEFAULT '2021-11-19')";

    private static final String insertSql01 = "INSERT INTO Profile"
            + " (UserName, BirthDate)"
            + " VALUES ('a', '2001-01-01')";

    private static final String insertSql02 = "INSERT INTO Profile"
            + " (UserName, BirthDate)"
            + " VALUES ('b', '2001-02-01')";

    private static final String insertSql03 = "INSERT INTO Profile"
            + " (UserName, BirthDate)"
            + " VALUES ('c', '2001-03-01')";

    private static final String selectSql = "SELECT * FROM Profile";

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

        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(dropOldTableSql);
            System.out.println("dropOldTable executed.");
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
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

        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(createSql);
            System.out.println("createTable executed.");
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
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

    private static boolean examineTable() {
        Connection connection = buildConnection();
        if (connection == null) {
            System.out.println("examineTable failed because of null-connection");
            return false;
        }

        ResultSet resultSet = null;
        try {
            DatabaseMetaData databaseMeta = connection.getMetaData();
            resultSet = databaseMeta.getTables(dataBaseName, null, tableName, new String[]{"TABLE"});

            if (!resultSet.next()) {
                System.out.println("examine table failed");
                return false;
            }

            System.out.println("examineTable tableMeta:"
                    + resultSet.getString("TABLE_TYPE") + "\t"
                    + resultSet.getString("TABLE_CAT") + "\t"
                    + resultSet.getString("TABLE_NAME"));
            return true;
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

            try {
                connection.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    private static void insertProfiles() {
        Connection connection = buildConnection();
        if (connection == null) {
            System.out.println("insertProfiles failed because of null-connection");
            return;
        }
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(insertSql01);
            statement.executeUpdate(insertSql02);
            statement.executeUpdate(insertSql03);
            System.out.println("insertProfiles: insertSql01 insertSql02 insertSql03");
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
            try {
                connection.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }

    private static void selectProfiles() {
        Connection connection = buildConnection();
        if (connection == null) {
            System.out.println("searchProfiles failed because of null-connection");
            return;
        }
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(selectSql);
            System.out.println("selectProfiles:");
            while (resultSet.next()) {
                System.out.println("  "
                        + resultSet.getInt("ID")
                        + ", " + resultSet.getString("UserName")
                        + ", " + resultSet.getDate("BirthDate")
                );
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
            if (statement != null) {
                try {
                    statement.close();
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
    }

    public static void main(String[] args) {
        dropOldTable();
        System.out.println("----------------------------------------------------");
        createTable();
        System.out.println("----------------------------------------------------");
        if (!examineTable()) {
            return;
        }
        System.out.println("----------------------------------------------------");
        insertProfiles();
        System.out.println("----------------------------------------------------");
        selectProfiles();
        System.out.println("----------------------------------------------------");
    }
}
