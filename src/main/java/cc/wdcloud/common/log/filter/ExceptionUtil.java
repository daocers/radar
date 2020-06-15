package cc.wdcloud.common.log.filter;

/**
 * @Author daocers
 * @Date 2020/3/10:17:07
 * @Description:
 */
public class ExceptionUtil {

    /**
     * 获取全部堆栈信息，打印10条
     *
     * @param
     * @return
     * @auther daocers
     * @date 2020/3/10 17:17
     */
    public static String getFullStackTrace(Throwable e) {
        StackTraceElement[] stackTrace = e.getStackTrace();

        StringBuilder builder = new StringBuilder();

        int count = 1;
        for (StackTraceElement item : stackTrace) {
            count++;
            if (count > 10) {
                break;
            }
            builder.append(item.toString());
            builder.append("\t");
        }
        return builder.toString();
    }
}
