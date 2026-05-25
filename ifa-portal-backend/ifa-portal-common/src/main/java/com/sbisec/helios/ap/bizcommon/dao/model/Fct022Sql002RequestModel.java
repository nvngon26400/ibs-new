package com.sbisec.helios.ap.bizcommon.dao.model;

import java.util.List;

import com.sbibits.earth.model.ModelBase;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * FCT022.sql002 国内投信販売手数料取得
 *
 * @author 陳
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Fct022Sql002RequestModel extends ModelBase {
    
    private static final long serialVersionUID = 1L;
    //NRIコード
    private List<String> nriCd;
}
