package com.sbisec.helios.ap.bizcommon.dao.model;

import com.sbibits.earth.model.ModelBase;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 共通関数FCT024 SQL002リクエストモデル
 *
 * @author SCSK
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Fct024Sql002RequestModel extends ModelBase {
    
    private static final long serialVersionUID = 1L;
    // NRIコード
    private String nriCd;
    
}
