package com.mumu.teacher.mapper;

import com.mumu.pojo.Dict;
import com.mumu.pojo.LessonCourse;
import com.mumu.pojo.Student;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.ArrayList;

@org.apache.ibatis.annotations.Mapper
public interface TeaLessonMapper extends Mapper<LessonCourse> {

    @Select(" SELECT * FROM lesson_course WHERE teacher_id = #{teaId} AND state = 1 ")
    ArrayList<LessonCourse> queryNowPaiLesson(Integer teaId);
    @Select(" SELECT COUNT(*) FROM lesson_choose WHERE lesson_id = #{lessonId} and state != 0 ")
    Integer queryNums(Integer lessonId);

    @Select(" SELECT * FROM lesson_course WHERE teacher_id = #{teaId} AND state = 2 ")
    ArrayList<LessonCourse> queryHistoryLesson(Integer teaId);

    @Insert(" INSERT INTO lesson_course(course_name,lesson_start_time,lesson_end_time,shool_addr,teacher_name,teacher_id,state) VALUES(#{lessonName},#{lessonStartTime},#{lessonEndTime},#{school},#{teaName},#{teaId},1) ")
    void addLesson(String lessonName, String lessonStartTime, String lessonEndTime, String school, String teaName, Integer teaId);

    @Select(" SELECT * FROM student WHERE id IN(SELECT student_id FROM lesson_choose WHERE lesson_id = #{lessonId} and state != 0) ")
    ArrayList<Student> getBookStuMessage(int lessonId);

}
