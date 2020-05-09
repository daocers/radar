package co.bugu.radar.userStudentX.service;

import co.bugu.common.service.BaseService;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import co.bugu.common.enums.DelFlagEnum;
import co.bugu.radar.userStudentX.dao.UserStudentXDao;
import co.bugu.radar.userStudentX.domain.UserStudentX;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author daocers
 * @Date 2020-05-09 11:52
 * @Description:
 */
@Service
public class UserStudentXService extends BaseService<UserStudentX> {
    @Autowired
    UserStudentXDao userStudentXDao;


}
