package co.bugu.radar.categoryGoodsX.domain;

import lombok.Data;

import java.util.Date;

@Data
public class CategoryGoodsX {
    private Long id;

    private String goodsName;

    private Long categoryId;

    private Integer isDel;

    private Date createTime;

    private Date updateTime;
}