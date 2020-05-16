package co.bugu.radar.category.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Category {
    private Long id;

    private String name;

    private String value;

    private String memo;

    private Integer level;

    private Long superiorId;

    private Integer isDel;

    private Date createTime;

    private Date updateTime;

    private Long createUserId;
}