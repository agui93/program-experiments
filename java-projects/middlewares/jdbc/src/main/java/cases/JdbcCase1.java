package cases;

import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;

/**
 * list jdbc drivers
 *
 * @author agui93
 * @since 2021/11/18
 */
public class JdbcCase1 {

    private static void listJdbcDrivers() {
        Enumeration<?> driverList = DriverManager.getDrivers();
        while (driverList.hasMoreElements()) {
            Driver driverClass = (Driver) driverList.nextElement();
            System.out.println("a jdbc driver class name is : " + driverClass.getClass().getName());
        }
    }

    public static void main(String[] args) {
        System.out.println("----------------------------------------------------");
        System.out.println("listJdbcDrivers:");
        listJdbcDrivers();
        System.out.println("----------------------------------------------------");
    }

}
