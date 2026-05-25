package com.sbisec.helios.ap.bizcommon.dao.model;

import com.sbibits.earth.model.ModelBase;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * ユーザ権限情報取得（仲介業者）リクエスト
 *
 * @author SCSK
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Fct032Sql002RequestModel extends ModelBase {
    
    private static final long serialVersionUID = -4099967523522552544L;

    // ユーザーID
    private String userId;
    
    // 仲介業者コード  
    private String brokerId;
    
}
