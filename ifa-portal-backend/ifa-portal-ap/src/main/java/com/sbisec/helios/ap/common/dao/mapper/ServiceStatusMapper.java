package com.sbisec.helios.ap.common.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sbisec.helios.ap.common.model.ServiceStatusModel;

@Mapper
public interface ServiceStatusMapper {

	public List<ServiceStatusModel> getServiceStatus(@Param("screenId") String screenId) throws Exception;

}
