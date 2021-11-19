package cases;

import java.sql.*;
import java.util.*;

/**
 * JDBC PreparedStatement
 *
 * @author agui93
 * @since 2021/11/19
 */
public class JdbcCase4 {
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

    private static final String insertSql = "INSERT INTO Profile (UserName, BirthDate) VALUES (?,?)";

    private static final String selectSql = "SELECT * FROM Profile";

    private static final String selectByIdSql = "SELECT * FROM Profile WHERE ID = ?";

    private static final String selectCountSql = "SELECT COUNT(*) FROM Profile";

    private static final int preCount = 3;
    private static final List<String> preUserNames = Arrays.asList("a", "b", "c");
    private static final List<String> preBirthdays = Arrays.asList("2001-01-01", "2001-02-01", "2001-03-01");

    private static final int batchCount = 5;
    private static final List<String> batchUserNames = Arrays.asList("m", "n", "x", "y", "z");
    private static final List<String> batchBirthdays = Arrays.asList("2001-04-01", "2001-05-01", "2001-06-01", "2001-07-01", "2001-08-01");


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
            System.out.println("dropOldTable executed.");
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
            System.out.println("createTable executed.");
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
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(insertSql);

            for (int i = 0; i < preCount; i++) {
                preparedStatement.setString(1, preUserNames.get(i));
                preparedStatement.setString(2, preBirthdays.get(i));
                preparedStatement.executeUpdate();
                System.out.println("inserted one record : " + preUserNames.get(i) + "," + preBirthdays.get(i));
            }

            System.out.println("insertProfiles executed.");
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
            try {
                connection.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private static void selectById(int id) {
        Connection connection = buildConnection();
        if (connection == null) {
            System.out.println("searchProfiles failed because of null-connection");
            return;
        }
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(selectByIdSql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            System.out.println("selectById executed, id=" + id);
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("ID")
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
    }

    private static void batchInsert() {
        Connection connection = buildConnection();
        if (connection == null) {
            System.out.println("insertProfiles failed because of null-connection");
            return;
        }
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(insertSql);

            for (int i = 0; i < batchCount; i++) {
                preparedStatement.setString(1, batchUserNames.get(i));
                preparedStatement.setString(2, batchBirthdays.get(i));
                preparedStatement.addBatch();
            }

            int[] counts = preparedStatement.executeBatch();
            int effectedRows = 0;
            for (int count : counts) {
                effectedRows += count;
            }

            System.out.println("batchInsert executed, effectedRows=" + effectedRows + ".");
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
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(selectSql);
            resultSet = preparedStatement.executeQuery(selectSql);
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

    public static void main(String[] args) {
        System.out.println("----------------------------------------------------");
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
        selectById(2);
        System.out.println("----------------------------------------------------");
        System.out.println("selectCount=" + selectCount());
        selectProfiles();
        System.out.println("----------------------------------------------------");


        batchInsert();
        System.out.println("----------------------------------------------------");
        System.out.println("selectCount=" + selectCount());
        selectProfiles();
        System.out.println("----------------------------------------------------");
    }

}
