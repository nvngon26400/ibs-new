package com.sbisec.helios.ap.bizcommon.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 共通関数DTO：FCT037
 * @author base 熊
 */

@Data
@EqualsAndHashCode(callSuper=false)
public class OutputFct037Dto extends BaseOutputDto{

    /** 注文条件 */
    private String orderConditions;
	
}
