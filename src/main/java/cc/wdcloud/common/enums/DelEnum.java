package cc.wdcloud.common.enums;

import lombok.Getter;

/**
 * @Author daocers
 * @Date 2020/6/15:23:23
 * @Description:
 */
@Getter
public enum DelEnum {
    NOT(0, "未删除"), DELETED(1, "已删除");
    private int code;
    private String name;

    DelEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }


}
