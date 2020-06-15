package cc.wdcloud.demo.user.api;

import cc.wdcloud.common.Resp;
import cc.wdcloud.demo.user.domain.User;
import cc.wdcloud.demo.user.dto.UserDto;
import cc.wdcloud.demo.user.service.UserService;
import javafx.geometry.Pos;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Author daocers
 * @Date 2020/6/15:23:33
 * @Description:
 */
@RestController
@RequestMapping("/user/api")
public class UserApi {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Resp<Long> save(@RequestBody @Validated UserDto userDto, BindResult bindResult) {
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        userService.insert(user);
        return Resp.success(user.getId());
    }

    @RequestMapping(value = "/ex", method = RequestMethod.POST)
    public Resp<Boolean> ex(Long id) throws Exception {
        if(id != null && id == 1){
            return Resp.success(true);
        }else{
            throw new Exception("跑出异常");
        }
    }
}
