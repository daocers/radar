package co.bugu.radar.wxUser.service;

import co.bugu.common.service.BaseService;
import co.bugu.radar.wxUser.dao.WxUserDao;
import co.bugu.radar.wxUser.domain.WxUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author daocers
 * @Date 2020-05-09 11:52
 * @Description:
 */
@Service
public class WxUserService extends BaseService<WxUser> {
    @Autowired
    WxUserDao wxUserDao;
}