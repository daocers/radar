package co.bugu.radar.wx.feign;

import co.bugu.radar.wx.dto.WxLoginDto;
import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author daocers
 * @Date 2020/5/9:14:29
 * @Description:
 */
@FeignClient(url = "${service.wx.url}")
public interface WxClient {

    /**
     * 客户端登录code换unionid，openid信息
     *
     * @param
     * @return
     * @auther daocers
     * @date 2020/5/9 14:46
     */
    @GetMapping("/sns/jscode2session")
    WxLoginDto code2session(String appid, String secret, String js_code, String grant_type);


    /**
     * 获取全局token
     *
     * @param
     * @return
     * @auther daocers
     * @date 2020/5/9 14:47
     */
    @GetMapping("/cgi-bin/token")
    JSONObject getGlobalToken(String appid, String secret, String grant_type);
}
