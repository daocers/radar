package co.bugu.radar;

import com.thetransactioncompany.cors.CORSFilter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(exclude = {RedisAutoConfiguration.class})
@EnableFeignClients
@EnableSwagger2
@MapperScan("co.bugu.radar.*.dao")
public class RadarApplication {

    public static void main(String[] args) {
        SpringApplication.run(RadarApplication.class, args);
    }


    /**
     * 过滤器配置
     *
     * @param
     * @return
     * @auther daocers
     * @date 2018/12/3 10:31
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new CORSFilter());
        bean.setName("cors");
        bean.addUrlPatterns("/*");
        return bean;
    }

}
