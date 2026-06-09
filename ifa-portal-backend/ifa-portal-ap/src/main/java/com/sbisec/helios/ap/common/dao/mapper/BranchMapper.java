package com.sbisec.helios.ap.common.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sbisec.helios.ap.common.model.Branch;

@Mapper
public interface BranchMapper {

	public Branch getBranch(@Param("branchCode") String branchCode) throws Exception;

}
