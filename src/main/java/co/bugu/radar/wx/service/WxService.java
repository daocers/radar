package co.bugu.radar.wx.service;

import co.bugu.common.RespDto;
import co.bugu.radar.wx.feign.WxClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author daocers
 * @Date 2020/5/9:14:33
 * @Description:
 */
@Service
public class WxService {
    @Autowired
    WxClient wxClient;

}
