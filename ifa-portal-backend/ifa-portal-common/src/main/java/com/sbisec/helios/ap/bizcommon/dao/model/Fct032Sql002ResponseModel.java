package com.sbisec.helios.ap.bizcommon.dao.model;

import com.sbibits.earth.model.ModelBase;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * ユーザ権限情報取得（仲介業者）レスポンス
 *
 * @author SCSK
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Fct032Sql002ResponseModel extends ModelBase {
    
    private static final long serialVersionUID = -8736164483407535604L;
    // 件数
    private int count;
    
}
