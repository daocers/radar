package co.bugu.radar.wxUser.domain;

import lombok.Data;

import java.util.Date;

@Data
public class WxUser {
    private Long id;

    private String unionId;

    private String openId;

    private Integer isDel;

    private Date createTime;

    private Date updateTime;
}