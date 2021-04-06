package com.mumu.student.controller;

import com.mumu.student.mapper.LoginMapper;
import com.mumu.student.service.LessonService;
import com.mumu.student.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LessonController {

    @Autowired
    private LessonService lessonService;
    @Autowired
    private LoginMapper loginMapper;

    @PostMapping("/bookLesson")
    @ResponseBody
    public int bookLesson(Integer lessonId, String shoolAddr, HttpServletRequest request){
        String username = (String) request.getSession().getAttribute("username");
        Integer stuId = loginMapper.queryStuIdByUsername(username);
        int i = lessonService.bookLesson(stuId, lessonId, shoolAddr);
        return i;
    }

    @PostMapping("/cancelLesson")
    @ResponseBody
    public int cancelLesson(@RequestParam(value = "lessonId", required = true) Integer lessonId, HttpServletRequest request){
        String username = (String) request.getSession().getAttribute("username");
        Integer stuId = loginMapper.queryStuIdByUsername(username);
        int i = lessonService.cancelLesson(stuId, lessonId);
        return i;
    }

}
