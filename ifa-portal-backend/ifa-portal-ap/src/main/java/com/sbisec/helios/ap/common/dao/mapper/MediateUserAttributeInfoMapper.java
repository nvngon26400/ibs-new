package com.sbisec.helios.ap.common.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sbisec.helios.ap.common.model.MediateUserAttributeInfo;
@Mapper
public interface MediateUserAttributeInfoMapper {

	public MediateUserAttributeInfo getMediateUserAttributeInfo(@Param("butenCode") String butenCode, @Param("accountNumber") String accountNumber) throws Exception;

}
