package co.bugu.radar.wxUser.api;

import co.bugu.common.RespDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @Author daocers
 * @Date 2020/5/9:11:58
 * @Description:
 */
@RequestMapping("/wxUser/api")
@RestController
@ApiIgnore
public class WxUserApi {
    private Logger logger = LoggerFactory.getLogger(WxUserApi.class);

    @RequestMapping("/login")
    public RespDto<?> login(String code){
        return null;
    }
}
