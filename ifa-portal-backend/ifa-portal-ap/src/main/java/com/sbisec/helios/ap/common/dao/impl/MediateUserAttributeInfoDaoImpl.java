package com.sbisec.helios.ap.common.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sbisec.helios.ap.common.dao.MediateUserAttributeInfoDao;
import com.sbisec.helios.ap.common.dao.mapper.MediateUserAttributeInfoMapper;
import com.sbisec.helios.ap.common.model.MediateUserAttributeInfo;


@Component
public class MediateUserAttributeInfoDaoImpl implements MediateUserAttributeInfoDao{

	@Autowired
	protected MediateUserAttributeInfoMapper mapper;

	@Override
	public MediateUserAttributeInfo getMediateUserAttributeInfo(String butenCode, String accountNumber) throws Exception {
		return mapper.getMediateUserAttributeInfo(butenCode, accountNumber);
	}
}
