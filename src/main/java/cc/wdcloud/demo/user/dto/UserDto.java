package cc.wdcloud.demo.user.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

/**
 * @Author daocers
 * @Date 2020/6/15:23:35
 * @Description:
 */
@Data
public class UserDto {
    private Long id;

    @NotNull
    private String nickname;

    @Max(value = 3)
    private String phoneNumber;
}
