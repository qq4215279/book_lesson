package com.mumu.teacher.service;

import com.mumu.pojo.LessonCourse;
import com.mumu.pojo.Student;
import com.mumu.student.service.LessonService;
import com.mumu.teacher.mapper.TeaLessonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;

@Service
public class TeaLessonService {

    @Autowired
    private TeaLessonMapper teaLessonMapper;
    @Autowired
    private LessonService lessonService;

    public ArrayList<LessonCourse> queryNowPaiLesson(Integer teaId) throws ParseException {
        ArrayList<LessonCourse> lessonCourseList0 = teaLessonMapper.queryNowPaiLesson(teaId);
        lessonService.changeCourseState(lessonCourseList0);//根据时间改变已排课是否过期
        ArrayList<LessonCourse> lessonCourseArrayList = teaLessonMapper.queryNowPaiLesson(teaId);
        lessonCourseArrayList.forEach((lessonCourse)->{
            Integer id = lessonCourse.getId();
            Integer nums = teaLessonMapper.queryNums(id);
            lessonCourse.setNums(nums);
        });

        return lessonCourseArrayList;
    }

    public ArrayList<LessonCourse> queryHistoryLesson(Integer teaId) throws ParseException {
        ArrayList<LessonCourse> historyCourseList0 = teaLessonMapper.queryHistoryLesson(teaId);
        lessonService.changeCourseState(historyCourseList0);//根据时间改变已排课是否过期
        ArrayList<LessonCourse> lessonCourseArrayList = teaLessonMapper.queryHistoryLesson(teaId);
        lessonCourseArrayList.forEach((lessonCourse)->{
            Integer id = lessonCourse.getId();
            Integer nums = teaLessonMapper.queryNums(id);
            lessonCourse.setNums(nums);
        });
        return lessonCourseArrayList;
    }

    public void addLesson(String lessonName, String lessonStartTime, String lessonEndTime, String school, String teaName, Integer teaId) {
        teaLessonMapper.addLesson(lessonName, lessonStartTime, lessonEndTime, school, teaName, teaId);
    }

    public ArrayList<Student> getBookStuMessage(int lessonId) {
        return teaLessonMapper.getBookStuMessage(lessonId);
    }
}
