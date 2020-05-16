package co.bugu.radar.goods.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Goods {
    private Long id;

    private String name;

    private String code;

    private Long categoryId;

    private String color;

    private String originPlace;

    private String size;

    private Integer isDel;

    private Long createUserId;

    private Date createTime;

    private Date updateTime;
}