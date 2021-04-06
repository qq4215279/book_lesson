package com.mumu.pojo;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "lesson_course")
@Data
public class LessonCourse implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String courseName;
    private String lessonStartTime;
    private String lessonEndTime;
    private String shoolAddr;
    private String teacherName;
    private Integer teacherId;
    private Integer state;

    private Integer nums;


}
