package com.sbisec.helios.ap.common.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sbisec.helios.ap.common.dao.MediateChargeInfoDao;
import com.sbisec.helios.ap.common.dao.mapper.MediateChargeInfoMapper;
import com.sbisec.helios.ap.common.model.MediateChargeInfo;

@Component
public class MediateChargeInfoDaoImpl implements MediateChargeInfoDao {

	@Autowired
	protected MediateChargeInfoMapper mapper;

	@Override
	public MediateChargeInfo getMediateChargeInfo(String brokerCode, String brokerBranchCode, String brokerChargeCode) throws Exception{
		return mapper.getMediateChargeInfo(brokerCode, brokerBranchCode, brokerChargeCode);
	}
}
