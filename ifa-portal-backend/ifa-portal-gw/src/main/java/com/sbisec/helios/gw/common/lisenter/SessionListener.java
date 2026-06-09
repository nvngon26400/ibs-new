package com.sbisec.helios.gw.common.lisenter;

import org.springframework.beans.factory.annotation.Autowired;

import com.sbisec.helios.gw.common.config.PropertyHolder;

import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

/**
 * Session listener settings.
 * 
 * @Organization SBIBITS DaLian CB Group
 * @Author kui.zhang
 * @Date 2020/07/17
 */
public class SessionListener implements HttpSessionListener {

    @Autowired
    PropertyHolder prop;

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        // Set session timeout.
        se.getSession().setMaxInactiveInterval(prop.getSysTimeout());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
    }

}
