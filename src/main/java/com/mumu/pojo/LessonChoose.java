package com.mumu.pojo;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "lesson_choose")
@Data
public class LessonChoose implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer lessonId;
    private Integer studentId;
    private Integer teacherId;
    private Integer state;




}
