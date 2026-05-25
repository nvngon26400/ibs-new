package com.sbisec.helios.ap.bizcommon.dao.model;

import com.sbibits.earth.model.ModelBase;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 共通関数FCT024 SQL003リクエストモデル
 *
 * @author SCSK
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Fct024Sql003RequestModel extends ModelBase {
    
    private static final long serialVersionUID = 1L;

    //扱者コード
    private String dealerNumber;
    
    //ファンドタイプ
    private String fundType;
    
    //協会コード
    private String kyoukaiCd;
    
}
