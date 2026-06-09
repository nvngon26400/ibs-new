package com.sbisec.helios.ap.bizcommon.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 共通関数DTO：FCT035
 * @author base 熊
 */

@Data
@EqualsAndHashCode(callSuper=false)
public class InputFct035Dto extends BaseOutputDto{

	//部店コード
	private String butenCode;
	
	//口座番号
	private String accountNumber;
	

}
