package co.bugu.common.log.filter;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * @Author daocers
 * @Date 2020/3/10:17:17
 * @Description:
 */
public class LogHttpResponseWrapper extends HttpServletResponseWrapper {
    private ByteArrayOutputStream bytes = new ByteArrayOutputStream();

    private PrintWriter printWriter;

    public LogHttpResponseWrapper(HttpServletResponse response) throws IOException {
        super(response);
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return new MyServletOutputStream();
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        try {
            printWriter = new PrintWriter(new OutputStreamWriter(bytes, "utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return printWriter;
    }

    public byte[] getBytes(){
        if(null != printWriter){
            printWriter.close();
            return bytes.toByteArray();
        }
        if(null != bytes){
            try{
                bytes.flush();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return bytes.toByteArray();
    }

    private class MyServletOutputStream extends ServletOutputStream {
        @Override
        public void write(int b) throws IOException {
            bytes.write(b); // 将数据写到 stream　中
        }
        @Override
        public boolean isReady() {
            return false;
        }
        @Override
        public void setWriteListener(WriteListener writeListener) {
        }
    }
}
