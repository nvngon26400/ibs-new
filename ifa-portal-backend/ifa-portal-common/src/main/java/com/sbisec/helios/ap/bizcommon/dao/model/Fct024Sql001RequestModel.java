package com.sbisec.helios.ap.bizcommon.dao.model;

import com.sbibits.earth.model.ModelBase;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 共通関数FCT024 SQL001リクエストモデル
 *
 * @author SCSK
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Fct024Sql001RequestModel extends ModelBase {
    
    private static final long serialVersionUID = 1L;
    // 協会コード
    private String kyoukaiCd;
}
