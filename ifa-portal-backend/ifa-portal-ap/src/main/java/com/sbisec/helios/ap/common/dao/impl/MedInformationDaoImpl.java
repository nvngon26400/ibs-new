package com.sbisec.helios.ap.common.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sbisec.helios.ap.common.dao.MedInformationDao;
import com.sbisec.helios.ap.common.dao.mapper.MedInformationMapper;
import com.sbisec.helios.ap.common.dao.model.MedInformation;


@Component
public class MedInformationDaoImpl implements MedInformationDao {

	@Autowired
	protected MedInformationMapper mapper;

	@Override
	public List<MedInformation> getMedInformationList() throws Exception {
		return mapper.getMedInformationList();
	}
}
