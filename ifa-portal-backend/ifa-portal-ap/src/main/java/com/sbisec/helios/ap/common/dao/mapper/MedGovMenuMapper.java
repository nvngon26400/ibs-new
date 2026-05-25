package com.sbisec.helios.ap.common.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sbisec.helios.ap.common.model.MedGovMenu;


@Mapper
public interface MedGovMenuMapper {

	public MedGovMenu getMedGovMenu(@Param("userId") String userId,
									@Param("menuId") String menuId) throws Exception;

	public List<MedGovMenu> getMedGovMenuList(@Param("userId") String userId) throws Exception;

	public int insertMedGovMenu(@Param("medGovMenu") MedGovMenu medGovMenu) throws Exception;

	public int deleteMedGovMenu(@Param("medGovMenu") MedGovMenu medGovMenu) throws Exception;
}
