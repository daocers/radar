package co.bugu.radar.user.domain;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private Long id;

    private String nickname;

    private String phoneNumber;

    private Integer isDel;

    private Date createTime;

    private Date updateTime;
}