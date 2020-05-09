package co.bugu.radar.student.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Student {
    private Long id;

    private String name;

    private String nickname;

    private Integer gender;

    private Integer grade;

    private String schoolName;

    private Integer isDel;

    private Date createTime;

    private Date updateTime;
}