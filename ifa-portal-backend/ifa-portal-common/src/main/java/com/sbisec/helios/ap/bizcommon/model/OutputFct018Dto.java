package com.sbisec.helios.ap.bizcommon.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 共通関数DTO：FCT018
 * @author base 熊
 */

@Data
@EqualsAndHashCode(callSuper=false)
public class OutputFct018Dto extends BaseOutputDto {
    
    //処理結果
    private String processResult;
    
}
