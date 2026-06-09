package com.sbisec.helios.ap.bizcommon.dao.model;

import com.sbibits.earth.model.ModelBase;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Fct030Sql003RequestModel extends ModelBase {
	private static final long serialVersionUID = 3245431281851215511L;

    //ユーザ共通情報.ユーザID
	private String userId;
		
	//ユーザ共通情報.仲介業者コード
	private String brokerCode;
	
	//ユーザ共通情報.権限コード
	private String privId;

}
