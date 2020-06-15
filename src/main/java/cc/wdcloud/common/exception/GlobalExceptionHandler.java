package cc.wdcloud.common.exception;

import cc.wdcloud.common.Resp;
import cc.wdcloud.common.log.ULogger;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;

/**
 * 统一异常处理
 *
 * @Author daocers
 * @Date 2019/5/5:16:25
 * @Description:
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = IllegalArgumentException.class)
    @ResponseBody
    public Resp handle(IllegalArgumentException e) {
        ULogger.error("参数非法异常", null, e);
        String msg = e.getMessage();
        if (StringUtils.isEmpty(msg)) {
            msg = "您传入的参数有误";
        }
        return Resp.fail(-1, "参数异常");
    }

    @ExceptionHandler(value = SQLException.class)
    @ResponseBody
    public Resp handle(SQLException e) {
        ULogger.error("SQL异常", e);
        return Resp.fail(-10, "系统异常");
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Resp handle(Exception e) {
        ULogger.error("异常", e);
        String errMsg = e.getMessage();
        if (StringUtils.isEmpty(errMsg)) {
            errMsg = "系统异常";
        }
        return Resp.fail(-10000, "系统异常");
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public Resp handle(MethodArgumentNotValidException e) {
        return Resp.fail(-101, "请求参数校验失败");
    }
}
