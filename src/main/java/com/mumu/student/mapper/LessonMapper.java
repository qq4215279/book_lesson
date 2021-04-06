package com.mumu.student.mapper;

import com.mumu.entity.PageResult;
import com.mumu.pojo.LessonCourse;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

import java.util.ArrayList;

@org.apache.ibatis.annotations.Mapper
public interface LessonMapper extends Mapper<LessonCourse> {


    ArrayList<LessonCourse> queryAllLesson(@Param("lessonNames")ArrayList<String> lessonNames,String teacher,String lesson,String selectAddr);
    //分页：
//    PageResult<LessonCourse> queryAllLessonByPage(ArrayList<String> lessonNames);

    @Update(" UPDATE lesson_choose SET state = 3 WHERE lesson_id = #{lessonId} and student_id = #{stuId} ")
    void changeChooseState(Integer lessonId, Integer stuId);

    @Update(" UPDATE lesson_course SET state = 2 WHERE id = #{lessonId} ")
    void changeCourseState(Integer lessonId);

    ArrayList<LessonCourse> queryNowLesson(Integer stuId);

    ArrayList<LessonCourse> queryHistoryLesson(Integer stuId);

    @Select(" SELECT COUNT(*) FROM lesson_choose WHERE student_id = #{stuId} AND lesson_id = #{lessonId} AND state != 0 ")
    int isBook(Integer stuId, Integer lessonId);

    @Select(" SELECT teacher_id FROM lesson_course WHERE id = #{lessonId} ")
    int queryTeacherIdByLessonId(Integer lessonId);

    void bookLesson(Integer lessonId, Integer stuId, Integer teacherId);

    void cancelLesson(Integer stuId, Integer lessonId);
}
