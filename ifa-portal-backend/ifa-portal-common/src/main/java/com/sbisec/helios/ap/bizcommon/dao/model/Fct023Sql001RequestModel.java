package com.sbisec.helios.ap.bizcommon.dao.model;

import com.sbibits.earth.model.ModelBase;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 共通関数Request Model：FCT023.SQL001
 *
 * @author 陳
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Fct023Sql001RequestModel extends ModelBase {

    private static final long serialVersionUID = 1L;
    // NRIコード
    private String nriCd;
}
