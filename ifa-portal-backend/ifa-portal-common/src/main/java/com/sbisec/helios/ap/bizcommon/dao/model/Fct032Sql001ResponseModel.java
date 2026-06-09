package com.sbisec.helios.ap.bizcommon.dao.model;

import com.sbibits.earth.model.ModelBase;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * ユーザ権限情報取得（仲介業者支店）レスポンス
 *
 * @author SCSK
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Fct032Sql001ResponseModel extends ModelBase {
    
    private static final long serialVersionUID = -3944635569178050284L;
    // 件数
    private int count;
    
}
