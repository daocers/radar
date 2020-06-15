package cc.wdcloud.common.log.configuration;

import cc.wdcloud.common.log.ULogger;
import cc.wdcloud.common.log.aspect.LogAspect;
import cc.wdcloud.common.log.filter.LogFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author daocers
 * @Date 2020/4/7:18:10
 * @Description:
 */
@Configuration
public class LogAutoConfiguration {


//    @ConditionalOnWebApplication
    @Bean
    public LogFilter logFilter() {
        ULogger.info("启动logfilter");
        LogFilter logFilter = new LogFilter();
        FilterRegistrationBean bean = filterRegistrationBean();
        bean.setFilter(logFilter);
        bean.setName("logFilter");
        bean.addUrlPatterns("/*");
        return logFilter;
    }

    @ConditionalOnClass(FeignClient.class)
    @Bean
    public LogAspect logAspect() {
        ULogger.info("启动logAspect");
        return new LogAspect();
    }

    @ConditionalOnMissingBean(FilterRegistrationBean.class)
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        return bean;
    }


}
