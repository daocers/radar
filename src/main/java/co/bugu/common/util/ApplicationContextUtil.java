package co.bugu.common.util;

import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Author daocers
 * @Date 2019/9/3:17:52
 * @Description:
 */
@Data
@Component
public class ApplicationContextUtil implements ApplicationContextAware {
    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    public static  <T> T getBean(Class<T> tClass){
        return context.getBean(tClass);
    }
}
