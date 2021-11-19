package tips;

import javax.sql.DataSource;
import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


/**
 * MySQL JdbcRowSet
 */
public class QuizTip {

    private static final String databaseName = "jdbc_test";
    private static final String user = "root";
    private static final String passwd = "123456";


    private static void checkApiImpl() {
        try {
            RowSetFactory f = RowSetProvider.newFactory();
            JdbcRowSet s = f.createJdbcRowSet();
            System.out.println("RowSetFactory: " + f.getClass().getName());
            System.out.println("JdbcRowSet: " + s.getClass().getName());
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    private static void withConnectionUrl() {
        try {

            // RowSetFactory should be used now
            //JdbcRowSet jrs = new com.sun.rowset.JdbcRowSetImpl();
            JdbcRowSet jrs = RowSetProvider.newFactory().createJdbcRowSet();

            // Set the connection URL for the DriverManager
            jrs.setUrl("jdbc:mysql://localhost:3306/" + databaseName + "?" + "user=" + user + "&password=" + passwd);
            // Set a SQL statement
            jrs.setCommand("SELECT 'Hello world!'");

            // Connect and run the statement
            jrs.execute();

            // Get the result
            jrs.next();
            System.out.println("JdbcRowSet WithConnectionUrl Result: " + jrs.getString(1));

            // Close resource
            jrs.close();
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }

    }

    private static void withPredefinedConnectObject() {
        Connection con = null;
        try {

            // Create a Connection object
            con = Utils.parseConnection(false, databaseName, user, passwd);

            // Pass the Connection object to the new JdbcRowSet object
            JdbcRowSet jrs = new com.sun.rowset.JdbcRowSetImpl(con);

            // Set a SQL statement
            jrs.setCommand("SELECT 'Hello world!'");

            // Connect and run the statement
            jrs.execute();

            // Get the result
            jrs.next();
            System.out.println("JdbcRowSet withPredifinedConnectObject Result: " + jrs.getString(1));

            // Close resources
            jrs.close();
            con.close();
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    private static void withPredefinedResultSetObject() {
        Connection con = null;
        try {

            // Create a Connection object
            con = Utils.parseConnection(false, databaseName, user, passwd);

            // Execute a SQL statement to generate a Result object
            Statement sta = con.createStatement();
            ResultSet res = sta.executeQuery("SELECT 'Hello world!'");

            // Pass the Connection object to the new JdbcRowSet object
            JdbcRowSet jrs = new com.sun.rowset.JdbcRowSetImpl(res);

            // Get the result
            jrs.next();
            System.out.println("JdbcRowSet withPredefinedResultSetObject Result: " + jrs.getString(1));

            // Close resources
            jrs.close();
            res.close();
            sta.close();
            con.close();
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    private static void withJNDIDirectoryService() {
        Connection con = null;
        try {

            // RowSetFactory should be used now
            JdbcRowSet jrs = RowSetProvider.newFactory().createJdbcRowSet();

            // Set the DataSource name for JNDI lookup
            jrs.setDataSourceName("jdbc/mysqlDS");

            // Set a SQL statement
            jrs.setCommand("SELECT 'Hello world!'");

            // Connect and run the statement
            jrs.execute();

            // Get the result
            jrs.next();
            System.out.println("JdbcRowSet withJNDIDirectoryService Result: " + jrs.getString(1));

            // Close resources
            jrs.close();
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void insertRows() {
        try {
            // Get a new JdbcRowSet object with the default implementation
            JdbcRowSet jrs = RowSetProvider.newFactory().createJdbcRowSet();

            // Set the connection URL for the DriverManager
            jrs.setUrl("jdbc:mysql://localhost:3306/" + databaseName + "?" + "user=" + user + "&password=" + passwd);

            // Set the connection URL for the DriverManager
            jrs.setCommand("SELECT * FROM Profile WHERE ID = 1");

            // Connect and run the statement
            jrs.execute();
            if (jrs.next()) {
                System.out.println("ID=1:" + jrs.getString("FirstName") + " " + jrs.getString("LastName"));
            }

            // Move to the insert row
            jrs.moveToInsertRow();

            // Set column values and insert
            jrs.updateString("FirstName", "Herong");
            jrs.updateString("LastName", "Yang");
            jrs.insertRow();

            // Repeat for another row
            jrs.updateString("FirstName", "Bush");
            jrs.updateString("LastName", "Gate");
            jrs.insertRow();

            System.out.println("2 rows inserted.");

            // Close resource
            jrs.close();
        } catch (Exception e) {
            System.err.println("insertRows Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void queryStatementWithParameters() {
        try {

            // RowSetFactory should be used now
            JdbcRowSet jrs = RowSetProvider.newFactory().createJdbcRowSet();

            // Set the connection URL for the DriverManager
            jrs.setUrl("jdbc:mysql://localhost:3306/" + databaseName + "?" + "user=" + user + "&password=" + passwd);

            // Set a SQL statement with a parameter
            jrs.setCommand("SELECT * FROM Profile WHERE ID = ?");

            // Set parameter values
            jrs.setInt(1, 1);

            // Connect and run the statement
            jrs.execute();
            if (jrs.next()) {
                System.out.println("queryStatementWithParameters User #1: " + jrs.getString("FirstName") + " " + jrs.getString("LastName"));
            }

            // Repeating for another parameter value
            jrs.setInt(1, 2);
            jrs.execute();
            if (jrs.next()) {
                System.out.println("queryStatementWithParameters User #2: " + jrs.getString("FirstName") + " " + jrs.getString("LastName"));
            }

            // Close resource
            jrs.close();
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        checkApiImpl();
        System.out.println();

        withConnectionUrl();
        System.out.println();

        withPredefinedConnectObject();
        System.out.println();

        withPredefinedResultSetObject();
        System.out.println();

        //failed to set up the JNDI directory service. So got an exception when running this program
//        withJNDIDirectoryService();
//        System.out.println();


        DataSource dataSource = Utils.parseDataSource(databaseName, user, passwd);
        Utils.dropTable(dataSource);
        Utils.createTableWithAutoIncrement(dataSource);

        System.out.println();
        insertRows();


        queryStatementWithParameters();
        System.out.println();
    }
}
