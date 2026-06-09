package com.sbisec.helios.ap.bizcommon.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 共通関数DTO：FCT029
 * @author base 熊
 */

@Data
@EqualsAndHashCode(callSuper=false)
public class OutputFct029Dto extends BaseOutputDto{

	//英文開示銘柄判定
	private String issuesDisclosedInEnglishBrandJudge;

}
