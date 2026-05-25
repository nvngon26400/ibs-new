package com.sbisec.helios.gw.common.filter.wrapper;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import com.sbisec.helios.gw.common.constants.AppConstants;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

public class BodyRequestWrapper extends HttpServletRequestWrapper {

    private final byte[] body;

    public BodyRequestWrapper(HttpServletRequest request, String bodyStr) throws IOException {
        super(request);
        body = bodyStr.getBytes(Charset.forName(AppConstants.ENCODE));
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream bais = new ByteArrayInputStream(body);
        return new ServletInputStream() {
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
                return bais.read();
            }
        };
    }

}
