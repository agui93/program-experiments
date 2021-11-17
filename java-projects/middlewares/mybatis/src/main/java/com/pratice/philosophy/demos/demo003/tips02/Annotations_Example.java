package com.pratice.philosophy.demos.demo003.tips02;

import com.pratice.philosophy.demos.demo003.Student;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class Annotations_Example {

    public static void main(String args[]) throws IOException {
        Reader reader = Resources.getResourceAsReader("SqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession session = sqlSessionFactory.openSession();

        try {
            session.getConfiguration().addMapper(Student_mapper.class);
            Student_mapper mapper = session.getMapper(Student_mapper.class);

            mapper.dropExistTable();
            mapper.createNewTable();


            Student student = new Student("zara", "EEE", 90, 123412341, "zara@gmail.com");
            mapper.insert(student);
            System.out.println("record inserted successfully");
            session.commit();

            student = mapper.getById(1);
            System.out.println("getById id=1:" + student);
            System.out.println();

            student.setName("name111");
            mapper.update(student);
            session.commit();
            System.out.println("update");

            student = new Student("zara", "EEE", 90, 123412341, "zara@gmail.com");
            mapper.insert(student);
            session.commit();
            System.out.println("insert");

            List<Student> studentList = mapper.getAll();
            for (Student st : studentList) {
                System.out.println(st);
            }
            System.out.println();

            mapper.deleteById(1);
            System.out.println("delete id=1");

            studentList = mapper.getAll();
            for (Student st : studentList) {
                System.out.println(st);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
