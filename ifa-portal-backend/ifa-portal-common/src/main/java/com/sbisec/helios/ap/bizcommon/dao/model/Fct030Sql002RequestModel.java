package com.sbisec.helios.ap.bizcommon.dao.model;

import com.sbibits.earth.model.ModelBase;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Fct030Sql002RequestModel extends ModelBase {
	private static final long serialVersionUID = -1993053416878076858L;

    //ユーザ共通情報.ユーザID
	private String userId;
	
	//ユーザ共通情報.仲介業者支店コード
	private String subBrokerCode;
	
	//ユーザ共通情報.仲介業者コード
	private String brokerCode;
	
	//ユーザ共通情報.権限コード
	private String privId;
}
