package co.bugu.radar.wx.feign;

import co.bugu.radar.wx.dto.WxLoginDto;
import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author daocers
 * @Date 2020/5/9:14:29
 * @Description:
 */
@Service
@FeignClient(url = "${service.wx.url}", name = "wxClient")
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
    WxLoginDto code2session(@RequestParam String appid, @RequestParam String secret, @RequestParam String js_code, @RequestParam String grant_type);


    /**
     * 获取全局token
     *
     * @param
     * @return
     * @auther daocers
     * @date 2020/5/9 14:47
     */
    @GetMapping("/cgi-bin/token")
    JSONObject getGlobalToken(@RequestParam String appid, @RequestParam String secret, @RequestParam String grant_type);
}
