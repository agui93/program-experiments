import javax.sql.DataSource;
import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

//http://www.herongyang.com/JDBC/MySQL-PreparedStatement.html
public class QuizTips02 {
    private static String databaseName = "jdbc";
    private static String user = "root";
    private static String passwd = "123456";


    //批量insert10条数据
    private static void batchInsert(DataSource dataSource) throws SQLException {
        Connection con = null;
        try {
            con = dataSource.getConnection();

            // PreparedStatement
            PreparedStatement ps = con.prepareStatement("INSERT INTO Profile (FirstName, LastName) VALUES (?, ?)");

            // Provide values to parameters for copy 1
            ps.setString(1, "John");
            ps.setString(2, "First");
            // Create copy 1
            ps.addBatch();


            // Provide values to parameters for copy 2
            ps.setString(1, "Bill");
            ps.setString(2, "Second");
            // Create copy 2
            ps.addBatch();


            // Provide values to parameters for copy 3
            ps.setString(1, "Mark");
            ps.setString(2, "Third");
            // Create copy 3
            ps.addBatch();


            // Provide values to parameters for copy 4
            ps.setString(1, "Jack");
            ps.setString(2, "Last");
            // Create copy 4
            ps.addBatch();


            for (int i = 5; i < 11; i++) {
                // Provide values to parameters for copy i
                ps.setString(1, "first" + i);
                ps.setString(2, "last" + i);
                // Create copy i
                ps.addBatch();
            }


            // Execute all 4 copies
            int[] counts = ps.executeBatch();
            int count = 0;
            for (int i = 0; i < counts.length; i++) {
                count += counts[i];
            }
            System.out.println("Total effected rows: " + count);


            // Close the PreparedStatement object
            ps.close();

        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }

    private static void insertOne(DataSource dataSource) throws SQLException {
        Connection con = null;
        try {
            con = dataSource.getConnection();

            // PreparedStatement
            PreparedStatement ps = con.prepareStatement("INSERT INTO Profile (FirstName, LastName) VALUES (?, ?)");

            // Provide values to parameters for copy 1
            ps.setString(1, "HelloA");
            ps.setString(2, "HelloB");


            int count = ps.executeUpdate();
            System.out.println("Total effected rows: " + count);


            // Close the PreparedStatement object
            ps.close();

        } catch (
                Exception e) {
            System.err.println("Exception: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.close();
            }
        }

    }

    private static void selectByPreparedStatement(DataSource dataSource, int id) throws SQLException {
        Connection con = null;
        try {
            con = dataSource.getConnection();

            // PreparedStatement for SELECT statement
            PreparedStatement sta = con.prepareStatement("SELECT * FROM Profile WHERE ID = " + id);

            // Execute the PreparedStatement as a query
            ResultSet res = sta.executeQuery();
            if (!res.next()) {
                System.out.println("not found");
                return;
            }
            // Get values out of the ResultSet
            String firstName = res.getString("FirstName");
            String lastName = res.getString("LastName");
            System.out.println("User ID " + id + ": " + firstName + ' ' + lastName);

            // Close ResultSet and PreparedStatement
            res.close();
            sta.close();
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }

    private static void selectByPreparedStatementWithParameters(DataSource dataSource, int id) throws SQLException {
        Connection con = null;
        try {
            con = dataSource.getConnection();

            // PreparedStatement for SELECT statement with one parameter
            PreparedStatement sta = con.prepareStatement("SELECT * FROM Profile WHERE ID = ?");

            // Provide a value to the parameter
            sta.setInt(1, id);

            // Execute the PreparedStatement as a query
            ResultSet res = sta.executeQuery();
            if (!res.next()) {
                System.out.println("not found");
                return;
            }

            // Get values out of the ResultSet
            String firstName = res.getString("FirstName");
            String lastName = res.getString("LastName");
            System.out.println("User ID " + id + ": " + firstName + ' ' + lastName);


            // Close ResultSet and PreparedStatement
            res.close();
            sta.close();

        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }

    private static void performanceWithPreparedStatement(boolean useServerPrepStmts, int count) throws SQLException {
        Connection con = Utils.parseConnection(useServerPrepStmts, databaseName, user, passwd);
        try {
            // Delete all rows from the table
            Statement sta = con.createStatement();
            sta.executeUpdate("DELETE FROM Profile");

            // Start the test
            long t1 = System.currentTimeMillis();

            // PreparedStatement to insert rows
            PreparedStatement ps = con.prepareStatement("INSERT INTO Profile (FirstName, LastName) VALUES (?, ?)");
            Random r = new Random();
            for (int i = 0; i < count; i++) {
                ps.setString(1, Integer.toHexString(r.nextInt(9999)));
                ps.setString(2, Integer.toHexString(r.nextInt(999999)));
                ps.executeUpdate();
            }
            ps.close();

            // End the test
            long t2 = System.currentTimeMillis();
            System.out.println("PreparedStatement with useServerPrepStmts=" + useServerPrepStmts + " insert " + count + " rows with " + (t2 - t1) + " milliseconds");
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }

    private static void performanceWithRegularStatement(DataSource dataSource, int count) throws SQLException {
        Connection con = null;
        try {
            con = dataSource.getConnection();
            // Delete all rows from the table
            Statement sta = con.createStatement();
            sta.executeUpdate("DELETE FROM Profile");


            // Start the test
            long t1 = System.currentTimeMillis();

            // Regular Statement to insert rows
            Statement rs = con.createStatement();
            Random r = new Random();
            for (int i = 0; i < count; i++) {
                rs.executeUpdate("INSERT INTO Profile (FirstName, LastName)"
                        + " VALUES ('" + Integer.toHexString(r.nextInt(9999))
                        + "', '" + Integer.toHexString(r.nextInt(999999)) + "')");
            }
            rs.close();

            // End the test
            long t2 = System.currentTimeMillis();
            System.out.println("Regular Statement insert " + count + " rows with " + (t2 - t1) + " milliseconds");
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.close();
            }
        }

    }

    private static void performanceWithResultSet(DataSource dataSource, int count) throws SQLException {
        Connection con = null;
        try {
            con = dataSource.getConnection();
            // Delete all rows from the table
            Statement sta = con.createStatement();
            sta.executeUpdate("DELETE FROM Profile");

            // Start the test
            long t1 = System.currentTimeMillis();

            // ResultSet to insert rows
            Statement rs = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);


            ResultSet res = rs.executeQuery("SELECT * FROM Profile");
            res.moveToInsertRow();
            Random r = new Random();
            for (int i = 0; i < count; i++) {
                res.updateString("FirstName", Integer.toHexString(r.nextInt(9999)));
                res.updateString("LastName", Integer.toHexString(r.nextInt(999999)));
                res.insertRow();
            }
            rs.close();

            // End the test
            long t2 = System.currentTimeMillis();
            System.out.println("ResultSet insert " + count + " rows with " + (t2 - t1) + " milliseconds");
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.close();
            }
        }
    }

    public static void main(String[] args) throws SQLException {

        DataSource dataSource = Utils.parseDataSource(databaseName, user, passwd);

        //准备:建表
        Utils.dropTable(dataSource);
        Utils.createTableWithAutoIncrement(dataSource);


        batchInsert(dataSource);

        insertOne(dataSource);

        selectByPreparedStatement(dataSource, 1);
        selectByPreparedStatementWithParameters(dataSource, 11);

        //For multiple repeating insert operations, PreparedStatement performs better than regular INSERT statement.
        System.out.println();


        List<Integer> counts = Arrays.asList(1000, 5000, 10000, 20000, 30000);
        for (Integer count : counts) {
            performanceWithPreparedStatement(true, count);
            performanceWithPreparedStatement(false, count);
            performanceWithRegularStatement(dataSource, count);
            performanceWithResultSet(dataSource, count);
        }
    }
}



