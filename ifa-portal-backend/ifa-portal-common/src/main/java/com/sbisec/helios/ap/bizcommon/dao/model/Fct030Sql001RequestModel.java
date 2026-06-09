package com.sbisec.helios.ap.bizcommon.dao.model;

import com.sbibits.earth.model.ModelBase;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Fct030Sql001RequestModel extends ModelBase {
	private static final long serialVersionUID = 2432304659815244520L;

    //ユーザ共通情報.SBI証券支店コード
	private String branchCode;
	
	//ユーザ共通情報.権限コード
	private String privId;
}
