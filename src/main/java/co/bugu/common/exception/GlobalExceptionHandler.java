package co.bugu.common.exception;

import co.bugu.common.RespDto;
import co.bugu.common.log.filter.ULogger;
import org.apache.commons.lang3.StringUtils;
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
    public RespDto handle(IllegalArgumentException e) {
        ULogger.error("参数非法异常", null, e);
        String msg = e.getMessage();
        if (StringUtils.isEmpty(msg)) {
            msg = "您传入的参数有误";
        }
        return RespDto.fail(-1, "参数异常");
    }

    @ExceptionHandler(value = SQLException.class)
    @ResponseBody
    public RespDto handle(SQLException e) {
        ULogger.error("SQL异常", e);
        return RespDto.fail(-10, "系统异常");
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public RespDto handle(Exception e) {
        ULogger.error("异常", e);
        String errMsg = e.getMessage();
        if (StringUtils.isEmpty(errMsg)) {
            errMsg = "系统异常";
        }
        return RespDto.fail(-10000, "系统异常");
    }
}
