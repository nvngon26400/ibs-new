package com.sbisec.helios.ap.bizcommon.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sbisec.helios.ap.bizcommon.dao.Fct003Dao;
import com.sbisec.helios.ap.bizcommon.dao.mapper.Fct003Mapper;
import com.sbisec.helios.ap.bizcommon.dao.model.Fct003Sql001RequestModel;
import com.sbisec.helios.ap.bizcommon.dao.model.Fct003Sql001ResponseModel;
import com.sbisec.helios.ap.bizcommon.dao.model.Fct003Sql002RequestModel;
import com.sbisec.helios.ap.bizcommon.dao.model.Fct003Sql002ResponseModel;

@Component
public class Fct003DaoImpl implements Fct003Dao {
    
    @Autowired
    private Fct003Mapper fct003Mapper;

	@Override
	public List<Fct003Sql001ResponseModel> getFct003Sql001(Fct003Sql001RequestModel fct003Sql001RequestModel) {
		return fct003Mapper.getFct003Sql001(fct003Sql001RequestModel);
	}

	@Override
	public List<Fct003Sql002ResponseModel> getFct003Sql002(Fct003Sql002RequestModel fct003Sql002RequestModel) {
		return fct003Mapper.getFct003Sql002(fct003Sql002RequestModel);
	}

}
