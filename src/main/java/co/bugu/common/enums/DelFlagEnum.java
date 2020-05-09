package co.bugu.common.enums;

import lombok.Getter;

/**
 *      删除标识枚举类
 * @author daocers
 * @date 2017/11/14 11:40
 * @param
 * @return
 */
@Getter
public enum DelFlagEnum {
    YES(2, "已删除"),
    NO(1, "未删除")

    ;
    private int code;
    private String value;

    DelFlagEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

}
