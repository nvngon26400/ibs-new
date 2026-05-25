package com.sbisec.helios.ap.common.dao;

import com.sbisec.helios.ap.common.model.MediateUserAttributeInfo;

public interface MediateUserAttributeInfoDao {

	public MediateUserAttributeInfo getMediateUserAttributeInfo(String butenCode, String accountNumber) throws Exception;

}
