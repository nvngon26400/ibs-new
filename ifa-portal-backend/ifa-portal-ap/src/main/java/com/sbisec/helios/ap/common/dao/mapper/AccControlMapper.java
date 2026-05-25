package com.sbisec.helios.ap.common.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sbisec.helios.ap.common.model.AccControl;

@Mapper
public interface AccControlMapper {

	public List<AccControl> getAccControl(@Param("userId") String userId) throws Exception;

}
