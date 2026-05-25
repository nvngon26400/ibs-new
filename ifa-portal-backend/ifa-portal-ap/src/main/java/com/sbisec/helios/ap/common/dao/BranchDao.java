package com.sbisec.helios.ap.common.dao;

import org.springframework.stereotype.Component;

import com.sbisec.helios.ap.common.model.Branch;

@Component
public interface BranchDao {

	public Branch getBranch(String branchCode) throws Exception;

}
