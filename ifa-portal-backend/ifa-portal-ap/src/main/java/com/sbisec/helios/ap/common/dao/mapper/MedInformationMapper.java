package com.sbisec.helios.ap.common.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sbisec.helios.ap.common.dao.model.MedInformation;

@Mapper
public interface MedInformationMapper {

	public List<MedInformation> getMedInformationList() throws Exception;
}
