package com.mumu.teacher.controller;

import com.mumu.pojo.LessonCourse;
import com.mumu.pojo.Student;
import com.mumu.pojo.Teacher;
import com.mumu.student.mapper.LoginMapper;
import com.mumu.teacher.service.TeaLessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.ArrayList;

@Controller
public class TeaMenuController {

    @Autowired
    private TeaLessonService teaLessonService;
    @Autowired
    private LoginMapper loginMapper;

    @GetMapping("/nowPaiLesson")
    public String nowPaiLesson(Model model, HttpServletRequest request) throws ParseException {
        String username = (String) request.getSession().getAttribute("username");
        Integer teaId = loginMapper.queryTeaIdByUsername(username);
        ArrayList<LessonCourse> lessonCourseList = teaLessonService.queryNowPaiLesson(teaId);
        model.addAttribute("lessonCourseList",lessonCourseList);
        return "teacher/nowPaiLesson";
    }

    @GetMapping("/getBookStuMessage")
    public String getBookStuMessage(Model model,@RequestParam(value = "lessonId",required = true) Integer lessonId){
        ArrayList<Student> bookStuMessage = teaLessonService.getBookStuMessage(lessonId);
        model.addAttribute("bookStuMessage",bookStuMessage);
        return "teacher/bookStuMessage";
    }

    @GetMapping("/historyPaiLesson")
    public String historyPaiLesson(Model model, HttpServletRequest request) throws ParseException {
        String username = (String) request.getSession().getAttribute("username");
        Integer teaId = loginMapper.queryTeaIdByUsername(username);
        ArrayList<LessonCourse> historyCourseList = teaLessonService.queryHistoryLesson(teaId);
        model.addAttribute("historyCourseList",historyCourseList);
        return "teacher/historyPaiLesson";
    }

    @GetMapping("/paiLesson")
    public String paiLesson(Model model, HttpServletRequest request) {
        String username = (String) request.getSession().getAttribute("username");
        String[] lessonNames = loginMapper.queryTeaLessonNameByUsername(username);
        model.addAttribute("lessonNames",lessonNames);

        return "teacher/paiLesson";
    }

    @PostMapping("/addLesson")
    @ResponseBody
    public int addLesson(@RequestParam(value = "lessonName", required = true) String lessonName,
                            @RequestParam(value = "lessonStartTime", required = true) String lessonStartTime,
                            @RequestParam(value = "lessonEndTime", required = true) String lessonEndTime,
                            @RequestParam(value = "school", required = true) String school, HttpServletRequest request) {
        String username = (String) request.getSession().getAttribute("username");
        //根据用户名查询讲师id
        Integer teaId = loginMapper.queryTeaIdByUsername(username);
        //查询讲师姓名
        String teaName = loginMapper.queryTeaMessageByUsername(username).getName();
        //向课程表中插入该条数据
        teaLessonService.addLesson(lessonName,lessonStartTime,lessonEndTime,school,teaName,teaId);
        return 1;
    }

    @GetMapping("/teacherMessage")
    public String teacherMessage(Model model, HttpServletRequest request){
        String username = (String) request.getSession().getAttribute("username");
        Teacher teacher = loginMapper.queryTeaMessageByUsername(username);
        model.addAttribute("teacher", teacher);
        return "teacher/teacherMessage";
    }

    @RequestMapping("/chageTeaPassword")
    public String changePass(){
        return "teacher/changePass";
    }


}
