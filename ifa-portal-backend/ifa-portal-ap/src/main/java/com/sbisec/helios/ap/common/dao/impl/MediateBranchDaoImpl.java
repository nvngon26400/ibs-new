package com.sbisec.helios.ap.common.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sbisec.helios.ap.common.dao.MediateBranchDao;
import com.sbisec.helios.ap.common.dao.mapper.MediateBranchMapper;
import com.sbisec.helios.ap.common.model.MediateBranch;

@Component
public class MediateBranchDaoImpl implements MediateBranchDao {

	@Autowired
	protected MediateBranchMapper mapper;

	@Override
	public MediateBranch getMediateBranch(String brokerCode, String brokerBranchCode) throws Exception {
		return mapper.getMediateBranch(brokerCode, brokerBranchCode);
	}
//	@Override
//	public DataList<OfferInfoModel> getOfferInfoList(String brokerBranchCode) throws Exception {
//		logger.debug("CounterBrandMasterInfoListDaoImpl.getOfferInfoList: String brokerBranchCode");
//
//		DataList<OfferInfoModel> result = new DataList<OfferInfoModel>();
//		result.setDataList(mapper.getOfferInfoList(brokerBranchCode));
//		return result;
//	}
}
