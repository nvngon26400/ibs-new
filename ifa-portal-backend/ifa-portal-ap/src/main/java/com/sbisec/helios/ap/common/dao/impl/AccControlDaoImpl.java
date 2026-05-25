package com.sbisec.helios.ap.common.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sbisec.helios.ap.common.dao.AccControlDao;
import com.sbisec.helios.ap.common.dao.mapper.AccControlMapper;
import com.sbisec.helios.ap.common.model.AccControl;

@Component
public class AccControlDaoImpl implements AccControlDao {

	@Autowired
	protected AccControlMapper mapper;

	@Override
	public List<AccControl> getAccControl(String userId) throws Exception {
		return mapper.getAccControl(userId);
	}

}
