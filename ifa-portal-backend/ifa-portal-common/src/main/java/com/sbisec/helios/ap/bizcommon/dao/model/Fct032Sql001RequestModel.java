package com.sbisec.helios.ap.bizcommon.dao.model;

import com.sbibits.earth.model.ModelBase;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * ユーザ権限情報取得（仲介業者支店）リクエスト
 *
 * @author SCSK
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Fct032Sql001RequestModel extends ModelBase {
    
    private static final long serialVersionUID = -4749163397640907526L;

    // ユーザーID
    private String userId;
    
    // 仲介業者コード  
    private String brokerId;

    // 仲介業者支店コード  
    private String subBrokerId;
    
}
