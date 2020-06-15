package cc.wdcloud.common.log.configuration;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Author daocers
 * @Date 2020/4/8:11:47
 * @Description:
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({LogAutoConfiguration.class})
@Documented
public @interface EnableCommonLog {
}
