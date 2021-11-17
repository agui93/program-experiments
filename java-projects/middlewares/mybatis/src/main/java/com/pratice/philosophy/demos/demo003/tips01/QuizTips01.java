package com.pratice.philosophy.demos.demo003.tips01;

import com.pratice.philosophy.demos.demo003.Student;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class QuizTips01 {

    private static void prepare(SqlSession sqlSession) {
        sqlSession.update("StudentMapper.dropTable");
        sqlSession.update("StudentMapper.createNewTable");
        System.out.println("create one new table!");
    }

    private static void selectById(SqlSession sqlSession, int id) {
        Student student = sqlSession.selectOne("StudentMapper.getById", id);
        System.out.println("selectById id=" + id + ":" + student);
    }

    private static void selectAll(SqlSession sqlSession) {
        List<Student> students = sqlSession.selectList("StudentMapper.getAll");
        System.out.println("process selectAll");
        for (Student st : students) {
            System.out.println("selectAll:" + st);
        }
    }

    private static void insertOne(SqlSession sqlSession) {
        Student student = new Student("Mohammad", "It", 80, 984803322, "Mohammad@gmail.com");
        sqlSession.insert("StudentMapper.insert", student);
        sqlSession.commit();
        System.out.println("Inserted one successfully.");
    }

    private static void update(SqlSession sqlSession, int id) {
        Student student = new Student("Mohammad", "It", 80, 984803322, "Mohammad@gmail.com");
        student.setId(id);
        student.setName("updateName" + id);

        sqlSession.update("StudentMapper.update", student);
        System.out.println("Record updated successfully");
        sqlSession.commit();
    }

    private static void deleteAll(SqlSession sqlSession) {
        sqlSession.delete("StudentMapper.deleteAll");
        sqlSession.commit();
        System.out.println("deleteAll");
    }

    public static void main(String[] args) throws IOException {
        Reader reader = Resources.getResourceAsReader("SqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            prepare(sqlSession);
            System.out.println();

            insertOne(sqlSession);
            System.out.println();

            update(sqlSession, 1);


            selectById(sqlSession, 1);
            selectById(sqlSession, 2);
            System.out.println();

            insertOne(sqlSession);
            insertOne(sqlSession);
            selectAll(sqlSession);
            System.out.println();

            deleteAll(sqlSession);
            System.out.println();

            selectAll(sqlSession);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
    }
}
