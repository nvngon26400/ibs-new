package com.sbisec.helios.ap.common.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sbisec.helios.ap.common.dao.IfaAliveMonitorDao;
import com.sbisec.helios.ap.common.service.IfaAliveMonitorService;

@Component(value = "ifaAliveMonitorService")
public class IfaAliveMonitorServiceImpl implements IfaAliveMonitorService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(IfaAliveMonitorServiceImpl.class);
    
    @Autowired
    protected IfaAliveMonitorDao dao;
    
    @Override
    public long watch(String dummy) throws Exception {
        
        LOGGER.debug("AliveServiceImplL.alive:[]");
        
        long blReceivedTime = System.currentTimeMillis();
        
        dao.watch();
        
        return blReceivedTime;
    }
}
