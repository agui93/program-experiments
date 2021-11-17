package com.pratice.philosophy.demos.demo003.tips02;

import com.pratice.philosophy.demos.demo003.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface Student_mapper {
    final String dropExistTable = "DROP TABLE IF EXISTS STUDENT";
    final String createNewTable = "  CREATE TABLE STUDENT(" +
            "           ID int(10) NOT NULL AUTO_INCREMENT," +
            "           NAME varchar(100) NOT NULL," +
            "           BRANCH varchar(255) NOT NULL," +
            "           PERCENTAGE int(3) NOT NULL," +
            "           PHONE int(11) NOT NULL," +
            "           EMAIL varchar(255) NOT NULL," +
            "           PRIMARY KEY (`ID`)" +
            "        );";
    final String getAll = "SELECT * FROM STUDENT";
    final String getById = "SELECT * FROM STUDENT WHERE ID = #{id}";
    final String deleteById = "DELETE from STUDENT WHERE ID = #{id}";
    final String insert = "INSERT INTO STUDENT (NAME, BRANCH, PERCENTAGE, PHONE, EMAIL ) VALUES (#{name}, #{branch}, #{percentage}, #{phone}, #{email})";
    final String update = "UPDATE STUDENT SET EMAIL = #{email}, NAME = #{name}, BRANCH = #{branch}, PERCENTAGE = #{percentage}, PHONE = #{phone} WHERE ID = #{id}";


    @Update(dropExistTable)
    void dropExistTable();

    @Update(createNewTable)
    void createNewTable();


    @Select(getAll)
    @Results(value = {
            @Result(property = "id", column = "ID"),
            @Result(property = "name", column = "NAME"),
            @Result(property = "branch", column = "BRANCH"),
            @Result(property = "percentage", column = "PERCENTAGE"),
            @Result(property = "phone", column = "PHONE"),
            @Result(property = "email", column = "EMAIL")
    })
    List<Student> getAll();

    @Select(getById)
    @Results(value = {
            @Result(property = "id", column = "ID"),
            @Result(property = "name", column = "NAME"),
            @Result(property = "branch", column = "BRANCH"),
            @Result(property = "percentage", column = "PERCENTAGE"),
            @Result(property = "phone", column = "PHONE"),
            @Result(property = "email", column = "EMAIL")
    })
    Student getById(int id);

    @Update(update)
    void update(Student student);

    @Delete(deleteById)
    void deleteById(int id);

    @Insert(insert)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Student student);
}
