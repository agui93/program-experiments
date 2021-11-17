package com.pratice.philosophy.demos.demo001;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public class Demo {
    private static RowMapper<Actor> rowMapper = (rs, rowNum) -> {
        Actor actor1 = new Actor();
        actor1.setId(rs.getInt("id"));
        actor1.setFirstName(rs.getString("first_name"));
        actor1.setLastName(rs.getString("last_name"));
        return actor1;
    };

    private static void basicOperations() {
        JdbcTemplate jdbcTemplate = JdbcManager.INSTANCE.fetchJdbcTemplate();


        jdbcTemplate.execute("drop table  if  exists t_actor");
        String sql_createTable = "create table t_actor (id Integer , first_name varchar(100),last_name varchar (100));";
        jdbcTemplate.execute(sql_createTable);
        System.out.println();
        System.out.println(sql_createTable);


        System.out.println();
        String sql_select = "select id,first_name, last_name from t_actor";
        List<Actor> actors = jdbcTemplate.query(sql_select, rowMapper);
        System.out.println(sql_select + "#####" + actors);


        System.out.println();
        String sql_insert = "insert into t_actor (id, first_name, last_name) values (?, ?, ?)";
        jdbcTemplate.update(sql_insert, 1212L, "Joe", "Watling");
        System.out.println(sql_insert + "#####" + 1212L + ";Joe" + ";Watling");


        System.out.println();
        sql_select = "select count(*) from t_actor";
        int rowCount = jdbcTemplate.queryForObject(sql_select, Integer.class);
        System.out.println(sql_select + "#####" + rowCount);


        System.out.println();
        sql_select = "select count(*) from t_actor where first_name = ?";
        int countOfActorsNamedJoe = jdbcTemplate.queryForObject(sql_select, Integer.class, "Joe");
        System.out.println(sql_select + "#####" + "Joe" + "#####" + countOfActorsNamedJoe);


        System.out.println();
        sql_select = "select last_name from t_actor where id = ?";
        String lastName = jdbcTemplate.queryForObject(sql_select, new Object[]{1212L}, String.class);
        System.out.println(sql_select + "#####" + 1212 + "#####" + lastName);


        System.out.println();
        sql_select = "select first_name, last_name from t_actor where id = ?";
        Actor actor = jdbcTemplate.queryForObject(sql_select, new Object[]{1212L}, ((rs, rowNum) -> {
            Actor actor1 = new Actor();
            actor1.setFirstName(rs.getString("first_name"));
            actor1.setLastName(rs.getString("last_name"));
            return actor1;
        }));
        System.out.println(sql_select + "#####" + 1212 + "#####" + actor);


        System.out.println();
        sql_insert = "insert into t_actor (id, first_name, last_name) values (?, ?, ?)";
        jdbcTemplate.update(sql_insert, 1313L, "John", "Watling");
        System.out.println(sql_insert + "#####" + 1313L + ";John" + ";Watling");


        System.out.println();
        sql_select = "select id,first_name, last_name from t_actor";
        actors = jdbcTemplate.query(sql_select, rowMapper);
        System.out.println(sql_select + "#####" + actors);


        System.out.println();
        String sql_update = "update t_actor set last_name = ? where id = ?";
        jdbcTemplate.update(sql_update, "Banjo", 1313L);
        System.out.println(sql_update + "#####" + "Banjo" + ";" + 1313L);


        System.out.println();
        sql_select = "select id,first_name, last_name from t_actor";
        actors = jdbcTemplate.query(sql_select, rowMapper);
        System.out.println(sql_select + "#####" + actors);

        System.out.println();
        String sql_delete = "delete from t_actor where id = ?";
        jdbcTemplate.update(sql_delete, 1313);
        System.out.println(sql_delete + "#####" + 1313);


        System.out.println();
        sql_select = "select id,first_name, last_name from t_actor";
        actors = jdbcTemplate.query(sql_select, rowMapper);
        System.out.println(sql_select + "#####" + actors);


        NamedParameterJdbcTemplate namedParameterJdbcTemplate = JdbcManager.INSTANCE.fetchNamedParameterJdbcTemplate();
        sql_select = "select count(*) from t_actor where first_name = :first_name";
        SqlParameterSource sqlNamedParameters = new MapSqlParameterSource("first_name", "Joe");
        rowCount = namedParameterJdbcTemplate.queryForObject(sql_select, sqlNamedParameters, Integer.class);
        System.out.println();
        System.out.println(sql_select + "#####" + "Joe" + "#####" + rowCount);


        sql_select = "select count(*) from t_actor where first_name = :first_name";
        Map<String, String> namedParameters = Collections.singletonMap("first_name", "Joe");
        rowCount = namedParameterJdbcTemplate.queryForObject(sql_select, namedParameters, Integer.class);
        System.out.println();
        System.out.println(sql_select + "#####" + "Joe" + "#####" + rowCount);


        sql_select = "select count(*) from t_actor where first_name = :firstName and last_name = :lastName";
        actor = new Actor();
        actor.setFirstName("Joe");
        actor.setLastName("Watling");
        sqlNamedParameters = new BeanPropertySqlParameterSource(actor);
        int row = namedParameterJdbcTemplate.queryForObject(sql_select, sqlNamedParameters, Integer.class);
        System.out.println();
        System.out.println(sql_select + "#####" + "Joe;Watling" + "#####" + row);


    }

    private static void batchOperations() {
        JdbcTemplate jdbcTemplate = JdbcManager.INSTANCE.fetchJdbcTemplate();


        jdbcTemplate.execute("drop table  if  exists t_actor");
        String sql_createTable = "create table t_actor (id Integer , first_name varchar(100),last_name varchar (100));";
        jdbcTemplate.execute(sql_createTable);
        System.out.println();
        System.out.println(sql_createTable);


        List<Actor> actors = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            actors.add(new Actor(i, "firstName" + i, "lastName" + i));
        }
        List<Actor> finalActors = actors;


        int[] batchUpdateResult = jdbcTemplate.batchUpdate("insert into t_actor (id, first_name, last_name) values (?, ?, ?)",
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setLong(1, finalActors.get(i).getId());
                        ps.setString(2, finalActors.get(i).getFirstName());
                        ps.setString(3, finalActors.get(i).getLastName());
                    }

                    public int getBatchSize() {
                        return finalActors.size();
                    }
                });
        System.out.println();
        String sql_select = "select id,first_name, last_name from t_actor";
        actors = jdbcTemplate.query(sql_select, rowMapper);
        System.out.println(actors);


        batchUpdateResult = jdbcTemplate.batchUpdate("update t_actor set first_name = ?, last_name = ? where id = ?",
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, finalActors.get(i).getFirstName() + i);
                        ps.setString(2, finalActors.get(i).getLastName() + i);
                        ps.setLong(3, finalActors.get(i).getId());
                    }

                    public int getBatchSize() {
                        return finalActors.size();
                    }
                });
        System.out.println();
        sql_select = "select id,first_name, last_name from t_actor";
        actors = jdbcTemplate.query(sql_select, rowMapper);
        System.out.println(actors);


        List<Object[]> batch = new ArrayList<>();
        for (Actor actor : actors) {
            Object[] values = new Object[]{actor.getFirstName() + "Test", actor.getLastName() + "Test", actor.getId()};
            batch.add(values);
        }
        batchUpdateResult = jdbcTemplate.batchUpdate("update t_actor set first_name = ?, last_name = ? where id = ?", batch);
        System.out.println();
        sql_select = "select id,first_name, last_name from t_actor";
        actors = jdbcTemplate.query(sql_select, rowMapper);
        System.out.println(actors);


        NamedParameterJdbcTemplate namedParameterJdbcTemplate = JdbcManager.INSTANCE.fetchNamedParameterJdbcTemplate();
        batchUpdateResult = namedParameterJdbcTemplate.batchUpdate("update t_actor set first_name = :firstName, last_name = :lastName where id = :id",
                SqlParameterSourceUtils.createBatch(finalActors.toArray()));
        System.out.println();
        sql_select = "select id,first_name, last_name from t_actor";
        actors = jdbcTemplate.query(sql_select, rowMapper);
        System.out.println(actors);


        int[][] updateCounts = jdbcTemplate.batchUpdate("update t_actor set first_name = ?, last_name = ? where id = ?",
                actors, 2,
                (ps, argument) -> {
                    ps.setString(1, argument.getFirstName() + "Hello");
                    ps.setString(2, argument.getLastName() + "Hello");
                    ps.setLong(3, argument.getId().longValue());
                });
        System.out.println();
        sql_select = "select id,first_name, last_name from t_actor";
        actors = jdbcTemplate.query(sql_select, rowMapper);
        System.out.println(actors);

    }

    private static void simpleJdbcInsert() {
        JdbcTemplate jdbcTemplate = JdbcManager.INSTANCE.fetchJdbcTemplate();
        jdbcTemplate.execute("drop table  if  exists t_actor");
        String sql_createTable = "create table t_actor (id Integer  auto_increment primary key, first_name varchar(100),last_name varchar (100));";
        jdbcTemplate.execute(sql_createTable);
        System.out.println();
        System.out.println(sql_createTable);


        DataSource dataSource = JdbcManager.INSTANCE.fetchDataSource();
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("t_actor");


        Map<String, Object> parameters = new HashMap<String, Object>(3);
        parameters.put("id", 1);
        parameters.put("first_name", "first01");
        parameters.put("last_name", "last01");
        simpleJdbcInsert.execute(parameters);
        System.out.println();
        String sql_select = "select id,first_name, last_name from t_actor";
        List<Actor> actors = jdbcTemplate.query(sql_select, rowMapper);
        System.out.println(actors);


        System.out.println();
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("t_actor").usingGeneratedKeyColumns("id");
        parameters = new HashMap<>(2);
        parameters.put("first_name", "first022");
        parameters.put("last_name", "last022");
        Number newId = simpleJdbcInsert.executeAndReturnKey(parameters);
        System.out.println(newId);
        sql_select = "select id,first_name, last_name from t_actor";
        actors = jdbcTemplate.query(sql_select, rowMapper);
        System.out.println(actors);

        System.out.println();
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("t_actor")
                .usingColumns("first_name")
                .usingGeneratedKeyColumns("id");
        parameters = new HashMap<>(2);
        parameters.put("first_name", "first033");
        parameters.put("last_name", "last033");
        newId = simpleJdbcInsert.executeAndReturnKey(parameters);
        System.out.println(newId);
        sql_select = "select id,first_name, last_name from t_actor";
        actors = jdbcTemplate.query(sql_select, rowMapper);
        System.out.println(actors);


        System.out.println();
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("t_actor")
                .usingGeneratedKeyColumns("id");
        Actor actor = new Actor("first04", "last04");
        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(actor);
        newId = simpleJdbcInsert.executeAndReturnKey(sqlParameterSource);
        System.out.println(newId);
        sql_select = "select id,first_name, last_name from t_actor";
        actors = jdbcTemplate.query(sql_select, rowMapper);
        System.out.println(actors);


        sqlParameterSource = new MapSqlParameterSource()
                .addValue("first_name", "first05")
                .addValue("last_name", "last05");
        newId = simpleJdbcInsert.executeAndReturnKey(sqlParameterSource);
        System.out.println(newId);
        sql_select = "select id,first_name, last_name from t_actor";
        actors = jdbcTemplate.query(sql_select, rowMapper);
        System.out.println(actors);


    }

    private static void simpleJdbcCall() {
        //https://docs.spring.io/spring/docs/5.1.9.RELEASE/spring-framework-reference/data-access.html#jdbc-simple-jdbc-call-1
    }

    public static void main(String[] args) {
        basicOperations();
//        batchOperations();
//        simpleJdbcInsert();
    }
}
