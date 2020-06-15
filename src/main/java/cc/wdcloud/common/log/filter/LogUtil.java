package cc.wdcloud.common.log.filter;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author daocers
 * @Date 2020/3/10:15:49
 * @Description:
 */
public class LogUtil {
    static ThreadLocal<Map<String, Object>> traceIdThreadLocal = new ThreadLocal<Map<String, Object>>() {
        @Override
        protected Map<String, Object> initialValue() {
            Map<String, Object> map = new HashMap<>(4);
            map.put("trace_id", null);
            map.put("seq", 0);
            map.put("log_type", "");
            return map;
        }
    };


    /**
     * 获取traceId
     *
     * @param
     * @return
     * @auther daocers
     * @date 2020/3/10 15:56
     */
    public static String getTraceId() {
        String traceId = (String) traceIdThreadLocal.get().get("trace_id");
        if (StringUtils.isEmpty(traceId)) {
            traceId = UUID.randomUUID().toString();
        }
        return traceId;
    }

    /**
     * 设置traceId
     *
     * @param
     * @return
     * @auther daocers
     * @date 2020/3/10 15:57
     */
    public static void setTraceId(String traceId) {
        if (StringUtils.isEmpty(traceId)) {
            traceId = UUID.randomUUID().toString();
        }
        traceIdThreadLocal.get().put("trace_id", traceId);
    }

    /**
     * 每获取一次增加一次
     *
     * @param
     * @return
     * @auther daocers
     * @date 2020/3/11 10:44
     */
    public static Integer getAndIncrementSeq() {
        Integer seq = (Integer) traceIdThreadLocal.get().get("seq");
        if (seq == null) {
            seq = 1;
        }
        seq++;
        traceIdThreadLocal.get().put("seq", seq);
        return seq;
    }


    /**
     * 获取seq，不增加
     *
     * @param
     * @return
     * @auther daocers
     * @date 2020/3/11 11:03
     */
    public static Integer getSeq() {
        Integer seq = (Integer) traceIdThreadLocal.get().get("seq");
        if (seq == null) {
            seq = 1;
            traceIdThreadLocal.get().put("seq", seq);
        }
        return seq;
    }


    public static void setHttp(){
        traceIdThreadLocal.get().put("log_type", "http");
    }

    public static void clean() {
        traceIdThreadLocal.get().put("trace_id", null);
        traceIdThreadLocal.get().put("log_type", "");
        traceIdThreadLocal.get().put("seq", 0);


    }

    public static void cleanHttp() {
        if("http".equals(traceIdThreadLocal.get().get("log_type"))){
            traceIdThreadLocal.get().put("trace_id", null);
            traceIdThreadLocal.get().put("log_type", "");
            traceIdThreadLocal.get().put("seq", 0);
        }
    }

    public static void cleanNoneHttp(){
        if(!"http".equals(traceIdThreadLocal.get().get("log_type"))){
            traceIdThreadLocal.get().put("trace_id", null);
            traceIdThreadLocal.get().put("log_type", "");
            traceIdThreadLocal.get().put("seq", 0);
        }
    }
}
