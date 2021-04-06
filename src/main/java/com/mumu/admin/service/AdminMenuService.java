package com.mumu.admin.service;

import com.mumu.admin.mapper.AdminMunuMapper;
import com.mumu.pojo.Student;
import com.mumu.pojo.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AdminMenuService {

    @Autowired
    private AdminMunuMapper adminMunuMapper;


    public ArrayList<Student> queryAllStuMsg() {
        return adminMunuMapper.queryAllStuMsg();
    }

    public ArrayList<Teacher> queryAllTeaMsg() {
        return adminMunuMapper.queryAllTeaMsg();
    }


    public void deleteStu(int stuId) {
        adminMunuMapper.deleteStuById(stuId);
    }

    public void deleteTea(int teaId) {
        adminMunuMapper.deleteTeaById(teaId);
    }

    public Teacher queryTeaMsgById(int teaId) {
        return adminMunuMapper.queryTeaMsgById(teaId);
    }

    public void updateTeaMsg(int id, String username, String password, String name, String gender, Integer age, String email, String phone, String school) {
        adminMunuMapper.updateTeaMsg(id, username, password, name, gender, age, email, phone, school);
    }

    public void addTeaMsg(Teacher teacher) {
        String username = teacher.getUsername();
        String password = teacher.getPassword();
        String name = teacher.getName();
        String gender = teacher.getGender();
        Integer age = teacher.getAge();
        String email = teacher.getEmail();
        String phone = teacher.getPhone();
        String school = teacher.getSchool();
        adminMunuMapper.addTeaMsg(username,password,name,gender,age,email,phone,school);
    }

    public void addLessonTeacher(int lessonCode, String teacherUserName) {
        adminMunuMapper.addLessonTeacher(lessonCode,teacherUserName);

    }

    public void addLessonToName(String lessonName,int i) {
        adminMunuMapper.addLessonToName(lessonName, i);
    }
}
