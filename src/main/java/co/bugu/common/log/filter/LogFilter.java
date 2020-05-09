package co.bugu.common.log.filter;

import org.apache.commons.io.IOUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author daocers
 * @Date 2020/3/10:15:18
 * @Description:
 */
//@WebFilter(urlPatterns = "/*")
//@Component
public class LogFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String traceId = request.getHeader("trade_id");
        LogUtil.setTraceId(traceId);
        LogUtil.setHttp();

//        filterChain.doFilter(new ParamHttpRequestWrapper(request), servletResponse);

        LogHttpResponseWrapper responseWrapper = new LogHttpResponseWrapper((HttpServletResponse) servletResponse);
        filterChain.doFilter(new ParamHttpRequestWrapper(request), responseWrapper);
        byte[] bytes = responseWrapper.getBytes();
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
        PrintWriter writer = servletResponse.getWriter();
        writer.write(result);
        writer.flush();
        writer.close();
        LogUtil.cleanHttp();

    }

    @Override
    public void destroy() {

    }
}
