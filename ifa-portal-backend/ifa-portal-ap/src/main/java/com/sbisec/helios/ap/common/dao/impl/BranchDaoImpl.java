package com.sbisec.helios.ap.common.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sbisec.helios.ap.common.dao.BranchDao;
import com.sbisec.helios.ap.common.dao.mapper.BranchMapper;
import com.sbisec.helios.ap.common.model.Branch;

@Component
public class BranchDaoImpl implements BranchDao {

	@Autowired
	protected BranchMapper mapper;

	@Override
	public Branch getBranch(String branchCode) throws Exception {
		return mapper.getBranch(branchCode);
	}

}
