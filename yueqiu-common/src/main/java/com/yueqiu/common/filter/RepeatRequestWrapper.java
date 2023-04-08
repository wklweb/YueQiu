package com.yueqiu.common.filter;
import com.yueqiu.common.constant.Constants;
import com.yueqiu.common.utils.HttpUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * 修饰重复提交的请求对象
 */
public class RepeatRequestWrapper extends HttpServletRequestWrapper {

    private final byte[] body;

    public RepeatRequestWrapper(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        super(request);
        this.body = HttpUtils.getBodyString(request).getBytes(Constants.UTF);
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {

        final ByteArrayInputStream inputStream = new ByteArrayInputStream(body);

        return new ServletInputStream(){
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() throws IOException {
                return inputStream.read();
            }

            @Override
            public int available() throws IOException {
                return super.available();
            }
        };
    }
}
