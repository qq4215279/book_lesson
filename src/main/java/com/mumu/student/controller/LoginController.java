package com.mumu.student.controller;

import com.mumu.pojo.Student;
import com.mumu.pojo.Teacher;
import com.mumu.student.mapper.LoginMapper;
import com.mumu.student.service.LoginService;
import com.mumu.utils.CookiesUtils;
import com.mumu.utils.ToolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private LoginMapper loginMapper;

    @RequestMapping("/checkUser")
    @ResponseBody
    public int checkUser(String username, String password, int userType, String checkCode, HttpServletRequest request, HttpServletResponse response){
        //校验验证码
//        String checkcode_server = (String) request.getSession().getAttribute("CHECKCODE_SERVER");
//        if (ToolUtil.isEmpty(checkcode_server) || !checkcode_server.equalsIgnoreCase(checkCode))
//            return -1;
        //根据用户名、密码、用户类型查询用户是否存在
        int res = loginService.checkUser(username, password, userType);
        String name = "";
        if (userType == 1) {//学生类型
            Student student = loginMapper.queryStuMessageByUsername(username);
            if (!ToolUtil.isEmpty(student))
                name = student.getName();

        } else if (userType == 2) {//讲师类型
            Teacher teacher = loginMapper.queryTeaMessageByUsername(username);
            if (ToolUtil.isNotEmpty(teacher))
                name = teacher.getName();
        } else {//管理员类型
            name = "Admin管理员";
        }
        if (ToolUtil.isEmpty(name)){
            name = "kong";
        }
        try {
            if (res > 0){
                HttpSession session = request.getSession();
                session.setAttribute("name", name);
                session.setAttribute("username", username);
                CookiesUtils.setToken( username + password, CookiesUtils.Token_Name, response );
            }
        } catch (Exception e) {
            res = 0;
            e.printStackTrace();
        }
        return res;
    }



    @PostMapping("/register")
    @ResponseBody
    public void register(Student student){
        loginService.register(student);
    }

//    @PostMapping("/register")
//    public Result register(Student student){
//        loginService.register(student);
//
//        return new Result(true, StatusCode.OK, "注册成功！");
//    }


    /**
     * 验证码
     */
    @RequestMapping("/checkCode")
    @ResponseBody
    public void checkCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //服务器通知浏览器不要缓存
        response.setHeader("pragma","no-cache");
        response.setHeader("cache-control","no-cache");
        response.setHeader("expires","0");

        //在内存中创建一个长80，宽30的图片，默认黑色背景
        //参数一：长
        //参数二：宽
        //参数三：颜色
        int width = 80;
        int height = 30;
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);

        //获取画笔
        Graphics g = image.getGraphics();
        //设置画笔颜色为灰色
        g.setColor(Color.GRAY);
        //填充图片
        g.fillRect(0,0, width,height);

        //产生4个随机验证码，12Ey
        String checkCode = getCheckCode();
        //将验证码放入HttpSession中
        request.getSession().setAttribute("CHECKCODE_SERVER",checkCode);

        //设置画笔颜色为黄色
        g.setColor(Color.YELLOW);
        //设置字体的小大
        g.setFont(new Font("黑体",Font.BOLD,24));
        //向图片上写入验证码
        g.drawString(checkCode,15,25);

        //将内存中的图片输出到浏览器
        //参数一：图片对象
        //参数二：图片的格式，如PNG,JPG,GIF
        //参数三：图片输出到哪里去
        ImageIO.write(image,"PNG",response.getOutputStream());
    }

    /**
     * 产生4位随机字符串
     */
    private String getCheckCode() {
        String base = "0123456789ABCDEFGabcdefg";
        int size = base.length();
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        for(int i=1;i<=4;i++){
            //产生0到size-1的随机值
            int index = r.nextInt(size);
            //在base字符串中获取下标为index的字符
            char c = base.charAt(index);
            //将c放入到StringBuffer中去
            sb.append(c);
        }
        return sb.toString();
    }



}
