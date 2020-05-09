package co.bugu.radar.wx.dto;

import lombok.Data;

/**
 * @Author daocers
 * @Date 2020/5/9:14:34
 * @Description:
 */
@Data
public class WxLoginDto {
    private String openid;
    private String session_key;
    private String unionid;
    private Integer errcode;
    private String errmsg;
}
