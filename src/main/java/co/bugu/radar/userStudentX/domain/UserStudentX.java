package co.bugu.radar.userStudentX.domain;

import lombok.Data;

import java.util.Date;

@Data
public class UserStudentX {
    private Long id;

    private Long userId;

    private Long studentId;

    private Integer isDel;

    private Date createTime;

    private Date updateTime;

}