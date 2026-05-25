package com.sbisec.helios.ap.bizcommon.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 共通関数DTO：FCT001
 *
 * @author SCSK
 */

@Data
@EqualsAndHashCode(callSuper=false)
public class OutputFct001Dto extends BaseOutputDto {
    
    // 対象顧客参照権限有無
    private String targetCustomerRefAuthFlag;
    
    // 取引停止フラグ
    private String tradeSuspendFlag;
    
}
