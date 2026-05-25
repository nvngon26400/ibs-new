package com.sbisec.helios.ap.common.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sbisec.helios.ap.common.dao.IfaAliveMonitorDao;
import com.sbisec.helios.ap.common.dao.mapper.IfaAliveMonitorMapper;

@Component
public class IfaAliveMonitorDaoImpl implements IfaAliveMonitorDao {
    
    @Autowired
    private IfaAliveMonitorMapper mapper;
    
    @Override
    public void watch() throws Exception {
        
        mapper.watch();
    }
}
