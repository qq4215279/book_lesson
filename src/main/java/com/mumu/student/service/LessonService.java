package com.mumu.student.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mumu.entity.PageResult;
import com.mumu.pojo.LessonCourse;
import com.mumu.student.mapper.LessonMapper;
import com.mumu.student.mapper.LoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class LessonService {

    @Autowired
    private LessonMapper lessonMapper;
    @Autowired
    private LoginMapper loginMapper;

    public PageResult<LessonCourse> queryAllLesson(Integer page, Integer rows, String username,String teacher,String lesson,String selectAddr) throws ParseException {

        ArrayList<String> lessonNames = loginMapper.queryStuLessonNameByUsername(username);

        ArrayList<LessonCourse> lessonList = lessonMapper.queryAllLesson(lessonNames,teacher,lesson,selectAddr);
        changeCourseState(lessonList);//通过排课时间判断，课程是否过期

        //1.分页处理
        PageHelper.startPage(page,rows);
        //2.创建查询条件
        Page<LessonCourse> pageInfo = (Page<LessonCourse>) lessonMapper.queryAllLesson(lessonNames,teacher,lesson,selectAddr);
        //3.封装分页
        PageResult<LessonCourse> result = new PageResult<>(pageInfo.getPageNum(), pageInfo.getPageSize(),
                pageInfo.getTotal(), pageInfo, pageInfo.getPages());

        return result;
    }

    public ArrayList<LessonCourse> queryNowLesson(Integer stuId) throws ParseException {
        ArrayList<LessonCourse> nowLessonList = lessonMapper.queryNowLesson(stuId);
        changeChooseState(stuId,nowLessonList);
        return lessonMapper.queryNowLesson(stuId);
    }

    public ArrayList<LessonCourse> queryHistoryLesson(Integer stuId) throws ParseException {
        ArrayList<LessonCourse> nowLessonList = lessonMapper.queryHistoryLesson(stuId);
        changeChooseState(stuId,nowLessonList);
        return lessonMapper.queryHistoryLesson(stuId);
    }
    //通过排课时间判断，课程是否过期       得到格式： 2020-03-28T08:00
    public void changeCourseState(ArrayList<LessonCourse> lessonList) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Date dateNow = new Date();
        long dateNow2 = dateNow.getTime();
        for (LessonCourse lesson : lessonList){
            String lessonTimeesson = lesson.getLessonStartTime();
            Date lessonTimeessonDate = sdf.parse(lessonTimeesson);
            long lessonTimeessonDate2 = lessonTimeessonDate.getTime();
            if (dateNow2 >= lessonTimeessonDate2){
                Integer lessonId = lesson.getId();
                lessonMapper.changeCourseState(lessonId);
            }
        }
    }

    public void changeChooseState(Integer stuId, ArrayList<LessonCourse> nowLessonList) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Date dateNow = new Date();
        long dateNow2 = dateNow.getTime();
        for (LessonCourse lesson : nowLessonList){
            String lessonTimeesson = lesson.getLessonStartTime();
            Date lessonTimeessonDate = sdf.parse(lessonTimeesson);
            long lessonTimeessonDate2 = lessonTimeessonDate.getTime();
            if (dateNow2 >= lessonTimeessonDate2){
                Integer lessonId = lesson.getId();
                lessonMapper.changeChooseState(lessonId, stuId);
            }
        }
    }

    public int bookLesson(Integer stuId, Integer lessonId, String shoolAddr) {
        //1.
        int isBook = lessonMapper.isBook(stuId, lessonId);
        if (isBook > 0){
            return 0;//已经预约过，直接返回
        }
        //2.
        String stuSchoolAddr = loginMapper.queryStuSchoolByStuId(stuId);
        if (!shoolAddr.equals(stuSchoolAddr)){
            return 1;//不是同一校区，不能预约
        }
        //3.
        Integer teacherId = lessonMapper.queryTeacherIdByLessonId(lessonId);
        lessonMapper.bookLesson(lessonId, stuId, teacherId);
        return 2;//预约成功

    }

    public int cancelLesson(Integer stuId, Integer lessonId) {
        lessonMapper.cancelLesson(stuId, lessonId);
        return 0;
    }
}
