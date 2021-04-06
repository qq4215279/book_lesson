package com.mumu.admin.controller;

import com.mumu.admin.mapper.AdminMunuMapper;
import com.mumu.admin.service.AdminMenuService;
import com.mumu.pojo.Dict;
import com.mumu.pojo.Student;
import com.mumu.pojo.Teacher;
import com.mumu.student.mapper.LoginMapper;
import com.mumu.teacher.mapper.TeaLessonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
public class AdminMenuController {

    @Autowired
    private AdminMunuMapper adminMunuMapper;
    @Autowired
    private AdminMenuService adminMenuService;
    @Autowired
    private TeaLessonMapper teaLessonMapper;
    @Autowired
    private LoginMapper loginMapper;

    @GetMapping("/stuMessage")
    public String studentMessage(Model model) {
        ArrayList<Student> studentList = adminMenuService.queryAllStuMsg();
        model.addAttribute("studentList", studentList);
        return "admin/studentMessage";
    }

    @GetMapping("/teaMessage")
    public String teacherMessage(Model model) {
        ArrayList<Teacher> teacherList = adminMenuService.queryAllTeaMsg();
        model.addAttribute("teacherList", teacherList);
        return "admin/teacherMessage";
    }

    @GetMapping("/addTeacherMsg")
    public String addTeacherMsg(Model model){
        ArrayList<Dict> dicts = loginMapper.queryAllLessonName();
        model.addAttribute("dicts", dicts);
        return "admin/addTeacherMsg";
    }

    @GetMapping("/updateTeacherMsg")
    public String updateTeacherMsg(@RequestParam(value = "teaId",required = true) int teaId, Model model){
        Teacher teacher = adminMenuService.queryTeaMsgById(teaId);
        model.addAttribute("teacher", teacher);
        return "admin/updateTeacherMsg";
    }

//    @PostMapping("/updateTeaMsg")
    @GetMapping("/updateTeaMsg")
    public void updateTeaMsg(int id,String username,String password, String name,String gender, Integer age,String email,String phone,String school){
        adminMenuService.updateTeaMsg(id, username, password, name, gender, age, email, phone, school);
        System.out.println("=======");
    }


    @PostMapping("/addTeaMsg")
    @ResponseBody
    public int addTeaMsg(Teacher teacher,int lessonCode){
        System.out.println("==============>=======>  " + lessonCode);
        String username = teacher.getUsername();
        loginMapper.insertLesson(username,lessonCode);
        adminMenuService.addTeaMsg(teacher);
        System.out.println("=======");
        return 1;
    }

    @PostMapping("/deleteStu")
    @ResponseBody
    public int deleteStu(@RequestParam(name = "stuId",required = true)int stuId){
        adminMenuService.deleteStu(stuId);
        return 1;
    }

    @PostMapping("/deleteTea")
    @ResponseBody
    public int deleteTea(@RequestParam(name = "teaId",required = true)int teaId){
        adminMenuService.deleteTea(teaId);
        return 1;
    }

    @RequestMapping("/addLessonName")
    public String addLessonName(Model model){
        ArrayList<Dict> dicts = adminMunuMapper.queryAllDict();
        model.addAttribute("dicts",dicts);
        return "admin/addLessonName";
    }

    @PostMapping("/addLessonToName")
    @ResponseBody
    public int addLessonToName(String lessonName){
        //查询code代码编号为多少，在此基础代码编号+1
        int i = adminMunuMapper.queryMaxCode();
        //向字典序中插入改条课程
        adminMenuService.addLessonToName(lessonName, i+1);
        return 1;
    }


    @RequestMapping("/addLessonTea")
    public String addLessonTea(Model model){
        ArrayList<Teacher> teacherList = loginMapper.queryAllTea();
        ArrayList<Dict> dicts = adminMunuMapper.queryAllDict();
        model.addAttribute("teacherList",teacherList);
        model.addAttribute("dicts",dicts);
        return "admin/addLessonTea";
    }

    @PostMapping("/addLessonTeacher")
    @ResponseBody
    public int addLessonTeacher(int lessonCode,String teacherUserName){
        adminMenuService.addLessonTeacher(lessonCode,teacherUserName);
        return 1;
    }

    @RequestMapping("/allLessonAdmin")
    public String allLessonAdmin(Model model){

        return "admin/allLessonTable";
    }

}
