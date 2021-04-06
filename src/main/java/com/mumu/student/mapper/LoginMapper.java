package com.mumu.student.mapper;

import com.mumu.pojo.Dict;
import com.mumu.pojo.Student;
import com.mumu.pojo.Teacher;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

import java.util.ArrayList;

@org.apache.ibatis.annotations.Mapper
public interface LoginMapper extends Mapper<Student> {

    @Select(" SELECT * FROM student ")
    Student queryAll();
    @Select(" SELECT * FROM teacher ")
    ArrayList<Teacher> queryAllTea();

    @Select(" SELECT phone FROM student WHERE username = #{username} ")
    String queryStuPhone(String username);
    @Select(" SELECT phone FROM teacher WHERE username = #{username} ")
    String queryTeaPhone(String username);
    @Select(" SELECT COUNT(*) FROM student WHERE username = #{username} ")
    int judgeStuOrTea(String username);

    @Select(" SELECT COUNT(*) FROM student WHERE username = #{username} AND PASSWORD = #{password} ")
    int checkUser1(String username, String password);
    @Select(" SELECT COUNT(*) FROM teacher WHERE username = #{username} AND PASSWORD = #{password} ")
    int checkUser2(String username, String password);
    @Select(" SELECT COUNT(*) FROM admin WHERE username = #{username} AND PASSWORD = #{password} ")
    int checkUser3(String username, String password);

    @Select(" SELECT NAME FROM dict WHERE group_code = 'lesson_code' AND CODE IN(SELECT lesson_code FROM stu_lesson_code WHERE username = #{username}) ")
    ArrayList<String> queryStuLessonNameByUsername(String username);
    @Select(" SELECT NAME FROM dict WHERE group_code = 'lesson_code' AND CODE IN(SELECT lesson_code FROM tea_lesson_code WHERE username = #{username}) ")
    String[] queryTeaLessonNameByUsername(String username);
    @Select("SELECT * FROM dict WHERE group_code = 'lesson_code'")
    ArrayList<Dict> queryAllLessonName();
    @Select(" SELECT COUNT(*) FROM stu_lesson_code WHERE username = #{username} AND lesson_code = #{code} ")
    int isJoinLesson(String username, int code);
    @Insert(" INSERT INTO stu_lesson_code(username,lesson_code) VALUES(#{username},#{code}) ")
    void joinLesson(String username, int code);
    @Insert(" INSERT INTO tea_lesson_code(username,lesson_code) VALUES(#{username},#{code}) ")
    void insertLesson(String username, int code);

    @Select(" SELECT id FROM student WHERE username = #{username} ")
    Integer queryStuIdByUsername(String username);
    @Select(" SELECT id FROM teacher WHERE username = #{username} ")
    Integer queryTeaIdByUsername(String username);

    @Insert(" INSERT INTO student(username,PASSWORD,NAME,gender,age,email,phone,school) VALUES(#{username}, #{password}, #{name}, #{gender}, #{age}, #{email}, #{phone}, #{school}) ")
    void register(String username, String password, String name, String gender, Integer age, String email, String phone, String school);

    @Select(" SELECT * FROM student where username = #{username} ")
    Student queryStuMessageByUsername(String username);
    @Select(" SELECT * FROM teacher where username = #{username} ")
    Teacher queryTeaMessageByUsername(String username);

    @Update(" UPDATE student SET PASSWORD = #{password} WHERE username = #{username} ")
    void changeStuPasswordByUsername(String username, String password);
    @Update(" UPDATE teacher SET PASSWORD = #{password} WHERE username = #{username} ")
    void changeTeaPasswordByUsername(String username, String password);

    @Select(" SELECT school FROM student WHERE id = #{stuId} ")
    String queryStuSchoolByStuId(Integer stuId);
}
