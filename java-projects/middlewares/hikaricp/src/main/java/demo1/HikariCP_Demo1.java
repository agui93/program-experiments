package demo1;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * **********************************************************
 * CREATE DATABASE IF NOT EXISTS hikari_test;
 * use hikari_test;
 * DROP TABLE IF EXISTS Cars;
 * CREATE TABLE Cars(Id INTEGER PRIMARY KEY, Name VARCHAR(50), Price INTEGER);
 * INSERT INTO Cars VALUES(1, 'Audi', 52642);
 * INSERT INTO Cars VALUES(2, 'Mercedes', 57127);
 * INSERT INTO Cars VALUES(3, 'Skoda', 9000);
 * INSERT INTO Cars VALUES(4, 'Volvo', 29000);
 * INSERT INTO Cars VALUES(5, 'Bentley', 350000);
 * INSERT INTO Cars VALUES(6, 'Citroen', 21000);
 * INSERT INTO Cars VALUES(7, 'Hummer', 41400);
 * INSERT INTO Cars VALUES(8, 'Volkswagen', 21600);
 * **********************************************************
 *
 * @author agui93
 * @since 2021/11/17
 */
public class HikariCP_Demo1 {

    private static HikariDataSource getDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/hikari_test?useSSL=false");
        config.setUsername("root");
        config.setPassword("123456");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        return new HikariDataSource(config);
    }

    private static Connection getConnection(HikariDataSource dataSource) throws SQLException {
        return dataSource.getConnection();
    }

    private static List<Car> findCars(HikariDataSource dataSource) throws SQLException {
        String querySql = "select * from Cars";
        List<Car> cars;
        try (Connection con = getConnection(dataSource);
             PreparedStatement pst = con.prepareStatement(querySql);
             ResultSet rs = pst.executeQuery()
        ) {
            cars = new ArrayList<>();
            Car car;
            while (rs.next()) {
                car = new Car();
                car.setId(rs.getLong("id"));
                car.setName(rs.getString("name"));
                car.setPrice(rs.getInt("price"));
                cars.add(car);
            }
        }
        return cars;
    }


    public static void main(String[] args) {
        try (HikariDataSource dataSource = getDataSource()) {

            List<Car> cars = findCars(dataSource);
            for (Car car : cars) {
                System.out.println(car);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
