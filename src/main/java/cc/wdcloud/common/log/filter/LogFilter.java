package cc.wdcloud.common.log.filter;

import cc.wdcloud.common.log.ULogger;
import com.alibaba.fastjson.JSON;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @Author daocers
 * @Date 2020/3/10:15:18
 * @Description:
 */
@WebFilter(urlPatterns = "/*")
@Component
public class LogFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String requestURI = request.getRequestURI();
        boolean matches = Pattern.matches("^((?!\\.[jpg]$).)*$", requestURI);
        if (requestURI.endsWith(".html") || requestURI.endsWith(".css") || requestURI.endsWith(".js") || requestURI.endsWith(".png")
                || requestURI.endsWith(".jpg")) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            String traceId = request.getHeader("trade_id");
            LogUtil.setTraceId(traceId);
            LogUtil.setHttp();

//        filterChain.doFilter(new ParamHttpRequestWrapper(request), servletResponse);

            ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper((HttpServletRequest) servletRequest);
            ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper((HttpServletResponse) servletResponse);

            processRequest(requestWrapper);
            filterChain.doFilter(requestWrapper, responseWrapper);

            byte[] bytes = responseWrapper.getContentAsByteArray();
            String result = IOUtils.toString(bytes, "utf-8");


            StringBuilder builder = new StringBuilder();
            builder.append("[logType: HTTP_RESPONSE]").append("[method:")
                    .append(request.getMethod())
                    .append("][host:")
                    .append(request.getRemoteHost())
                    .append("][uri:")
                    .append(request.getRequestURI())
                    .append("]")
                    .append("[result:")
                    .append(result)
                    .append("]");
            ULogger.info(builder.toString());
            PrintWriter writer = responseWrapper.getWriter();
            writer.write(result);
            writer.flush();
            writer.close();
            LogUtil.cleanHttp();
        }

    }

    @Override
    public void destroy() {

    }

    private void processRequest(ContentCachingRequestWrapper requestWrapper) {
        try {
            Map<String, String[]> params = requestWrapper.getParameterMap();
            String bodyStr;
            byte[] contentAsByteArray = requestWrapper.getContentAsByteArray();
            if (null == contentAsByteArray || contentAsByteArray.length == 0) {
                bodyStr = "";
            } else {
                bodyStr = IOUtils.toString(contentAsByteArray, requestWrapper.getCharacterEncoding());
            }

            String uri = requestWrapper.getRequestURI();
            String method = requestWrapper.getMethod();
            String host = requestWrapper.getRemoteAddr();
            Map<String, String> headers = new HashMap<>();
            Enumeration<String> headerNames = requestWrapper.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String header = headerNames.nextElement();
                if (header.startsWith("HT_")) {
                    String value = requestWrapper.getHeader(header);
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

            ULogger.info(builder.toString());

        } catch (Exception e) {
            ULogger.error("getInputStream failed, cause: ", e);
//            LOGGER.error("getInputStream failed, cause: [{}]", ExceptionUtils.getFullStackTrace(e));
        }
    }
}
