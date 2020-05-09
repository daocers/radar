package co.bugu.common.log;


import org.slf4j.Logger;

/**
 * @Author daocers
 * @Date 2020/2/11:11:17
 * @Description:
 */
public class InfoFeignLogger extends feign.Logger {
    private final Logger logger;

    public InfoFeignLogger(Logger logger) {
        this.logger = logger;
    }

    @Override
    protected void log(String configKey, String format, Object... args) {
        if (logger.isInfoEnabled()) {
            logger.info(String.format(methodTag(configKey) + format, args));
        }
    }
}
