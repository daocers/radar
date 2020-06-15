package cc.wdcloud.demo.user.service;

import cc.wdcloud.demo.user.domain.User;
import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Author daocers
 * @Date 2020/5/28:15:51
 * @Description:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
class UserServiceTest {
    @Autowired
    UserService userService;


    @Test
    public void test(){
        User user = new User();
        user.setNickname("allen");
        userService.insert(user);


    }

    @Test
    public void testGet(){
        List<User> list = userService.findByCondition(new User());
        System.out.println(JSON.toJSONString(list, true));
    }

}