package com.mumu.admin.mapper;

import com.mumu.pojo.Dict;
import com.mumu.pojo.Student;
import com.mumu.pojo.Teacher;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

@Mapper
public interface AdminMunuMapper {

    @Select(" SELECT * FROM student ")
    ArrayList<Student> queryAllStuMsg();

    @Select(" SELECT * FROM teacher ")
    ArrayList<Teacher> queryAllTeaMsg();

    @Delete(" DELETE FROM student WHERE id = #{stuId} ")
    void deleteStuById(int stuId);

    @Delete(" DELETE FROM teacher WHERE id = #{teaId} ")
    void deleteTeaById(int teaId);

    @Select(" SELECT * FROM teacher WHERE id = #{teaId} ")
    Teacher queryTeaMsgById(int teaId);

    @Update(" UPDATE teacher SET username = #{username},PASSWORD = #{password},NAME = #{name},gender = #{gender},age = #{age},email = #{email},phone =#{phone},school = #{school} WHERE id = #{id} ")
    void updateTeaMsg(int id, String username, String password, String name, String gender, Integer age, String email, String phone, String school);

    @Insert(" INSERT INTO teacher(username,PASSWORD,NAME,gender,age,email,phone,school) VALUES(#{username},#{password},#{name},#{gender},#{age},#{email},#{phone},#{school}) ")
    void addTeaMsg(String username, String password, String name, String gender, Integer age, String email, String phone, String school);


    @Select(" SELECT * FROM dict WHERE group_code = 'lesson_code' ")
    ArrayList<Dict> queryAllDict();

    @Select(" SELECT MAX(CODE) FROM dict ")
    int queryMaxCode();

    @Insert(" INSERT INTO tea_lesson_code(username, lesson_code) VALUES(#{teacherUserName},#{lessonCode}) ")
    void addLessonTeacher(int lessonCode, String teacherUserName);

    @Insert(" INSERT INTO dict(pid,group_code,CODE,NAME) VALUE(10,'lesson_code',#{i},#{lessonName}) ")
    void addLessonToName(String lessonName, int i);

}
