package co.bugu.radar;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(exclude = {RedisAutoConfiguration.class})
@EnableFeignClients
@EnableSwagger2
@MapperScan("co.bugu.radar.*.dao")
public class RadarApplication {

    public static void main(String[] args) {
        SpringApplication.run(RadarApplication.class, args);
    }

}
