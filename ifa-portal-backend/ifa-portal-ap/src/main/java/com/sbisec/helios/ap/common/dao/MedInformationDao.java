package com.sbisec.helios.ap.common.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.sbisec.helios.ap.common.dao.model.MedInformation;

@Component
public interface MedInformationDao {

	public List<MedInformation> getMedInformationList() throws Exception;
}
