package com.pratice.philosophy.demos.demo001;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

public class JdbcManager {
    public static JdbcManager INSTANCE = new JdbcManager();


    private BeanFactory beanFactory;

    private JdbcManager() {
        beanFactory = new ClassPathXmlApplicationContext("beans.xml");
    }


    public JdbcTemplate fetchJdbcTemplate() {
        return (JdbcTemplate) beanFactory.getBean("jdbcTemplate");
    }

    public NamedParameterJdbcTemplate fetchNamedParameterJdbcTemplate() {
        return (NamedParameterJdbcTemplate) beanFactory.getBean("namedParameterJdbcTemplate");
    }

    public DataSource fetchDataSource() {
        return (DataSource) beanFactory.getBean("dataSource");
    }

}
