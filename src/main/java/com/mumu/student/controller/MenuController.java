package com.mumu.student.controller;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.mumu.config.SmsProperties;
import com.mumu.entity.PageResult;
import com.mumu.pojo.Dict;
import com.mumu.pojo.LessonCourse;
import com.mumu.pojo.Student;
import com.mumu.student.mapper.LoginMapper;
import com.mumu.student.service.LessonService;
import com.mumu.student.service.LoginService;
import com.mumu.utils.NumberUtils;
import com.mumu.utils.SmsUtils;
import com.sun.xml.internal.bind.v2.TODO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.ArrayList;

@Controller
public class MenuController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private LessonService lessonService;
    @Autowired
    private LoginMapper loginMapper;

    @Autowired
    private SmsUtils smsUtils;
    @Autowired
    private SmsProperties prop;


    @RequestMapping("/test")
    public String index(){
        return "test";
    }

    @RequestMapping("/test2")
    public String index2(){
        return "test2";
    }

    @RequestMapping("/")
    public String login0(){
        return "/login";
    }

    @RequestMapping("/login")
    public String login(){
        return "/login";
    }

    @RequestMapping("/findPass")
    public String findPass(){
        return "/findPass";
    }

    @GetMapping("/sendMsg")
    @ResponseBody
    public int sendMsg(String username,String phone,Model model) throws ClientException {
        String phoneData = "";
        phoneData = loginMapper.queryStuPhone(username);
        if (StringUtils.isEmpty(phoneData)){
            phoneData = loginMapper.queryTeaPhone(username);
        }
        if (StringUtils.isEmpty(phoneData) || !phoneData.equals(phone)){
            return 1;   //请输入正确的用户名和手机号
        } else {
            String phoneCode = NumberUtils.generateCode(6);
            int phoneCodeInt = Integer.parseInt(phoneCode);
            model.addAttribute("phoneCode",phoneCode);
            // 发送短信消息
            SendSmsResponse resp = this.smsUtils.sendSms(phone, phoneCode,
                    prop.getSignName(),
                    prop.getVerifyCodeTemplate());
            return phoneCodeInt;
        }
    }
    @PostMapping("/findPassWord")
    @ResponseBody
    public int findPassWord(String username, String password){
        //判断是学生账号还是讲师账号。 0：讲师；  1：学生
        int i = this.loginMapper.judgeStuOrTea(username);
        if (i == 1){
            this.loginMapper.changeStuPasswordByUsername(username,password);
        }
        if (i == 0){
            this.loginMapper.changeStuPasswordByUsername(username,password);
        }
        return 1;
    }

    @RequestMapping("/toRegister")
    public String register(){
        return "studentRegist";
    }

    @RequestMapping("/lessonChoose")
    public String lessonChoose(Model model, HttpServletRequest request){
        String username = (String) request.getSession().getAttribute("username");
        ArrayList<String> lessonNames = loginMapper.queryStuLessonNameByUsername(username);
        model.addAttribute("lessonNames",lessonNames);

        ArrayList<Dict> dicts = loginMapper.queryAllLessonName();
        model.addAttribute("dicts", dicts);

        return "student/lessonChoose";
    }

    @PostMapping("/joinLesson")
    @ResponseBody
    public int joinLesson(int code, HttpServletRequest request){
        String username = (String) request.getSession().getAttribute("username");
        int num = loginMapper.isJoinLesson(username, code);
        if (num == 1){
            return 0;//不能重复加入
        } else {
            loginMapper.joinLesson(username, code);
            return 1;
        }
    }

    //课程表分页：
    @GetMapping("/lessonTable")
    public String lessonTable(
            @RequestParam(value = "page", required = false,defaultValue = "1")int page,
            @RequestParam(value = "rows", required = false, defaultValue = "5")int rows,
            @RequestParam(value = "teacher",required = false )String teacher,
            @RequestParam(value = "lesson",required = false )String lesson,
            @RequestParam(value = "selectAddr", required = false)String selectAddr,
            Model model, HttpServletRequest request) throws ParseException {

        String username = (String) request.getSession().getAttribute("username");
//        ArrayList<LessonCourse> lessonList = lessonService.queryAllLesson(page, rows,username);
        if (StringUtils.isNotEmpty(selectAddr) & "请选择".equals(selectAddr)){
            selectAddr = "";
        }
        PageResult<LessonCourse> result = lessonService.queryAllLesson(page, rows,username,teacher,lesson,selectAddr);
        if (StringUtils.isNotEmpty(teacher))
            model.addAttribute("teacher",teacher);
        if (StringUtils.isNotEmpty(teacher))
            model.addAttribute("lesson",lesson);
        if (StringUtils.isNotEmpty(teacher))
            model.addAttribute("selectAddr",selectAddr);

        model.addAttribute("result", result);
        return "student/lessonTable";
    }





    @RequestMapping("/nowLesson")
    public String nowLesson(Model model, HttpServletRequest request) throws ParseException {
        String username = (String) request.getSession().getAttribute("username");
        Integer stuId = loginMapper.queryStuIdByUsername(username);
        ArrayList<LessonCourse> nowLessonList = lessonService.queryNowLesson(stuId);
        model.addAttribute("nowLessonList", nowLessonList);
        return "student/nowLesson";
    }

    @RequestMapping("/historyLesson")
    public String historyLesson(Model model, HttpServletRequest request) throws ParseException {
        String username = (String) request.getSession().getAttribute("username");
        Integer stuId = loginMapper.queryStuIdByUsername(username);
        ArrayList<LessonCourse> historyLessonList = lessonService.queryHistoryLesson(stuId);
        model.addAttribute("historyLessonList", historyLessonList);
        return "student/historyLesson";
    }

    @RequestMapping("/studentMessage")
    public String studentMessage(Model model, HttpServletRequest request){
        String username = (String) request.getSession().getAttribute("username");
        Student student = loginService.queryMessage(username);
        System.out.println(student.toString());
        model.addAttribute("stu", student);
        return "student/studentMessage";
    }

    @RequestMapping("/chageStuPassword")
    public String changePass(){
        return "student/changePass";
    }

    @RequestMapping("/chagePassword2")
    @ResponseBody
    public int changePass2(String password,int userType, HttpServletRequest request){
        String username = (String) request.getSession().getAttribute("username");
        loginService.changePasswordByUsername(username, password, userType);
        return 1;
    }






}
