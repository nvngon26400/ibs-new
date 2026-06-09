package com.sbisec.helios.ap.common.dao;

import org.springframework.stereotype.Component;

import com.sbisec.helios.ap.common.model.MediateChargeInfo;

@Component
public interface MediateChargeInfoDao {

	public MediateChargeInfo getMediateChargeInfo(String brokerCode
												, String brokerBranchCode
												, String brokerChargeCode) throws Exception;

}
