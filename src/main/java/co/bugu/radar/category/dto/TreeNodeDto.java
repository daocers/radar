package co.bugu.radar.category.dto;

import lombok.Data;

import java.util.List;

/**
 * @Author daocers
 * @Date 2020/5/13:16:53
 * @Description:
 */
@Data
public class TreeNodeDto {
    private Long id;
    private String name;
    private Long superiorId;
    private List<TreeNodeDto> children;
}
