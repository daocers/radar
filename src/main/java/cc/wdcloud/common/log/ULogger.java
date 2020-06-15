package cc.wdcloud.common.log;

import cc.wdcloud.common.log.filter.ExceptionUtil;
import cc.wdcloud.common.log.filter.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author daocers
 * @Date 2020/3/10:16:10
 * @Description:
 */
public class ULogger {
    private static Logger logger = LoggerFactory.getLogger(ULogger.class);

    public static void debug(String format, Object... arguments) {
        StringBuilder builder = new StringBuilder();
        builder.append("[trace_id:")
                .append(LogUtil.getTraceId())
                .append("] ")
                .append(format);
        logger.debug(builder.toString(), arguments);
    }


    public static void info(String format, Object... arguments) {
        StringBuilder builder = new StringBuilder();
        builder.append("[trace_id:")
                .append(LogUtil.getTraceId())
                .append("] ")
                .append(format);
        logger.info(builder.toString(), arguments);
    }

    public static void warn(String format, Object... arguments) {
        StringBuilder builder = new StringBuilder();
        builder.append("[trace_id:")
                .append(LogUtil.getTraceId())
                .append("] ")
                .append(format);
        logger.debug(builder.toString(), arguments);
    }

    public static void error(String marker, Object message, Throwable throwable) {
        StringBuilder builder = new StringBuilder();
        builder.append("[trace_id:")
                .append(LogUtil.getTraceId())
                .append("] ")
                .append(marker)
                .append(message)
                .append(ExceptionUtil.getFullStackTrace(throwable));
        logger.error(builder.toString());
    }


    public static void error(String marker, Throwable throwable) {
        error(marker, "", throwable);
    }

}
