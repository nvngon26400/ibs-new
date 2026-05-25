package com.sbisec.helios.ap.common.dao;

import org.springframework.stereotype.Component;

import com.sbisec.helios.ap.common.model.MediateBranch;

@Component
public interface MediateBranchDao {

	public MediateBranch getMediateBranch(String brokerCode, String brokerBranchCode) throws Exception;
	// public DataList<OfferInfoModel> getOfferInfoList(String brokerBranchCode) throws Exception;

}
