package com.mumu.student.service;

import com.mumu.student.mapper.LoginMapper;
import com.mumu.pojo.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private LoginMapper loginMapper;

    public Student login() {

//        Student student = (Student) loginMapper.selectAll();
        Student student = (Student) loginMapper.queryAll();
        System.out.println(student);
        return student;

    }

    public int checkUser(String username, String password, int userType) {
        if (userType == 1) {
            return loginMapper.checkUser1(username, password);
        } else if (userType == 2) {
            return loginMapper.checkUser2(username, password);
        } else {
            return loginMapper.checkUser3(username, password);
        }

    }


    public void register(Student student) {

        String username = student.getUsername();
        String password = student.getPassword();
        String name = student.getName();
        String gender = student.getGender();
        Integer age = student.getAge();
        String email = student.getEmail();
        String phone = student.getPhone();
        String school = student.getSchool();

        loginMapper.register(username, password, name, gender, age, email, phone, school);
    }

    public Student queryMessage(String username) {
        Student student = loginMapper.queryStuMessageByUsername(username);
        return student;
    }

    public void changePasswordByUsername(String username, String password, int userType) {
        if (userType == 1){
            loginMapper.changeStuPasswordByUsername(username, password);
        }
        if (userType == 2){
            loginMapper.changeTeaPasswordByUsername(username,password);
        }
    }
}
