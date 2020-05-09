package co.bugu.common.log.filter;

import com.alibaba.fastjson.JSON;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Author daocers
 * @Date 2020/3/10:11:30
 * @Description:
 */
public class ParamHttpRequestWrapper extends HttpServletRequestWrapper {
    private static Logger logger = LoggerFactory.getLogger(ParamHttpRequestWrapper.class);
    private Map<String, String[]> params = new HashMap<>();

    private InputStream inputStream;


    public ParamHttpRequestWrapper(HttpServletRequest request) {
        super(request);
        if (MapUtils.isNotEmpty(request.getParameterMap())) {
            this.params.putAll(request.getParameterMap());
        }
        processRequest(request);
//        addParameter(correlationId);
//        dealRequestBody(request);
    }


    @Override
    public ServletInputStream getInputStream() throws IOException {
        return Objects.isNull(inputStream) ? null : new DefaultServletInputStream(inputStream);
    }

    @Override
    public String getParameter(String name) {
        String[] values = params.get(name);
        if (ArrayUtils.isEmpty(values)) {
            return null;
        }
        return values[0];
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return this.params;
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return super.getParameterNames();
    }

    @Override
    public String[] getParameterValues(String name) {
        return params.get(name);
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return Objects.isNull(inputStream) ? null : new BufferedReader(new InputStreamReader(inputStream));
    }

    private void processRequest(HttpServletRequest request) {
        try {
            Map<String, String[]> params = request.getParameterMap();
            String bodyStr = null;
            ServletInputStream inputStream = request.getInputStream();
            if (null != inputStream) {
                bodyStr = IOUtils.toString(inputStream, "utf-8");
            }
            if (StringUtils.isEmpty(bodyStr)) {
                bodyStr = "";
            }

            String uri = request.getRequestURI();
            String method = request.getMethod();
            String host = request.getRemoteAddr();
            Map<String, String> headers = new HashMap<>();
            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String header = headerNames.nextElement();
                if (header.startsWith("HT_")) {
                    String value = request.getHeader(header);
                    headers.put(header, value);
                }
            }
            StringBuilder builder = new StringBuilder();

            builder.append("[logType: HTTP_REQUEST]")
                    .append("[method:")
                    .append(method)
                    .append("][host:")
                    .append(host)
                    .append("][uri:")
                    .append(uri)
                    .append("]")
                    .append("[param:")
                    .append(JSON.toJSONString(params))
                    .append("]")
                    .append("[requestBody:")
                    .append(bodyStr)
                    .append("]");
            this.inputStream = IOUtils.toInputStream(bodyStr, "utf-8");

            ULogger.info(builder.toString());

        } catch (Exception e) {
            logger.error("getInputStream failed, cause: [{}]", ExceptionUtil.getFullStackTrace(e));
//            LOGGER.error("getInputStream failed, cause: [{}]", ExceptionUtils.getFullStackTrace(e));
        }
    }

    /**
     * 对map中加密字段进行解析
     * <p>
     * 加密规则将所有的字段通过AES加密到encryptContent字段中
     * 然后后端进行解密
     */
//    private void analysisMap() {
//        String[] contents = this.params.get("TRACE_ID");
//        if (GlobalUtils.isNull(contents)) {
//            return;
//        }
//
//        String content = contents[0];
//        String decrypt = EncryptionUtils.decrypt(content);
//        if (StringUtils.isEmpty(decrypt)) {
//            return;
//        }
//
//        String[] split = decrypt.split("&");
//        if (GlobalUtils.isNull(split)) {
//            return;
//        }
//
//        for (String query : split) {
//            String[] queryString = query.split("=");
//            if (!GlobalUtils.isNull(queryString) && queryString.length == 2) {
//                params.put(queryString[0], new String[] {queryString[1]});
//            }
//        }
//    }

//    /**
//     * 对map的值赋予新的值
//     *
//     * @param value 添加新的值
//     */
//    private void addParameter(Object value) {
//        //analysisMap();
//        if (params.containsKey(Constants.CORRELATION_ID)) {
//            return;
//        }
//
//        if (value != null) {
//            if (value instanceof String[]) {
//                params.put(Constants.CORRELATION_ID, (String[]) value);
//            } else if (value instanceof String) {
//                params.put(Constants.CORRELATION_ID, new String[] {(String) value});
//            } else {
//                params.put(Constants.CORRELATION_ID, new String[] {String.valueOf(value)});
//            }
//        }
//    }

    public class DefaultServletInputStream extends ServletInputStream {
        private final InputStream sourceStream;
        private boolean finished = false;

        DefaultServletInputStream(InputStream sourceStream) {
//            ParamUtils.assertNotNull(sourceStream, "inputStream must not be null");
            this.sourceStream = sourceStream;
        }

        public int read() throws IOException {
            int data = this.sourceStream.read();
            if (data == -1) {
                this.finished = true;
            }

            return data;
        }

        public int available() throws IOException {
            return this.sourceStream.available();
        }

        public void close() throws IOException {
            super.close();
            this.sourceStream.close();
        }

        public boolean isFinished() {
            return this.finished;
        }

        public boolean isReady() {
            return true;
        }

        public void setReadListener(ReadListener readListener) {
            throw new UnsupportedOperationException();
        }
    }

}
