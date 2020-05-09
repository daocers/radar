package co.bugu.common.log.aspect;

import co.bugu.common.log.filter.LogUtil;
import co.bugu.common.log.filter.ULogger;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author daocers
 * @Date 2020/3/2:14:06
 * @Description:
 */
//@Aspect
//@Component
public class LogAspect {
    private Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void requestPointcut() {

    }


    @Around("requestPointcut()")
    public Object doBefore(ProceedingJoinPoint joinPoint) throws Throwable {
        Class clazz = joinPoint.getSignature().getDeclaringType();
        FeignClient feignClient = (FeignClient) clazz.getDeclaredAnnotation(FeignClient.class);
        if (null == feignClient) {
            return joinPoint.proceed();
        }
        RequestMapping requestMapping = ((MethodSignature) (joinPoint.getSignature())).getMethod().getAnnotation(RequestMapping.class);
        if (null == requestMapping) {
            return joinPoint.proceed();
        }


        String host = feignClient.url();
        String[] url = requestMapping.value();
        RequestMethod[] method = requestMapping.method();

        StringBuilder builder = new StringBuilder();
        builder.append("[seq:")
                .append(LogUtil.getAndIncrementSeq())
                .append("][logType: feign-request]")
                .append("[host:")
                .append(host)
                .append("][url:")
                .append(url[0])
                .append("][params:");

        String[] params = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        Object[] args = joinPoint.getArgs();

        Map<String, Object> paramInfo = new HashMap<>();
        if (ArrayUtils.isNotEmpty(params)) {
            for (int i = 0; i < params.length; i++) {
                paramInfo.put(params[i], args[i]);
            }
            builder.append(JSON.toJSONString(paramInfo));
        } else {
            builder.append(JSON.toJSONString(args));
        }
        builder.append("]");


        ULogger.info(builder.toString());
        Object res = joinPoint.proceed();
        builder = new StringBuilder();
        builder.append("[seq:")
                .append(LogUtil.getSeq())
                .append("][logType: feign-response]")
                .append("[host:")
                .append(host)
                .append("][url:")
                .append(url[0])
                .append("][result:")
                .append(res)
                .append("]");

        ULogger.info(builder.toString());
//        System.out.println(target);
        return res;
    }

    //    @AfterReturning(value = "requestPointcut()", returning = "result")
    public void doAfterReturning(Object result) {
        StringBuilder builder = new StringBuilder();
        builder.append("[logType: feign-response]")
                .append("[result:")
                .append(result)
                .append("]");
        ULogger.info(builder.toString());
//        logger.info("返回结果：{}", result);
    }

    @AfterThrowing(value = "requestPointcut()", throwing = "ex")
    public void AfterThrowing(JoinPoint joinPoint, Exception ex) {
//        todo 计算参数
        String methodName = joinPoint.getSignature().toString();
        Object[] args = joinPoint.getArgs();

        StringBuilder builder = new StringBuilder();
        builder.append("[method:")
                .append(methodName)
                .append("][args:")
                .append(JSON.toJSONString(args))
                .append("]");
        ULogger.error(builder.toString(), "", ex);
    }
}
