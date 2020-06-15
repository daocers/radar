package cc.wdcloud.common.enums;

import lombok.Getter;

/**
 * @Author daocers
 * @Date 2020/6/15:23:24
 * @Description:
 */
@Getter
public enum BaseEnum {
    YES(1, "是"), NO(0, "否");
    private int code;
    private String name;

    BaseEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }
}
