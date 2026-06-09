package com.sbisec.helios.ap.bizcommon.dao.model;

import java.math.BigDecimal;

import com.sbibits.earth.model.ModelBase;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * FCT022.sql001 国内投信基準価額取得
 *
 * @author 陳
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Fct022Sql001ResponseModel extends ModelBase {
    
    private static final long serialVersionUID = 1L;

    //基準価額
    private BigDecimal basePrice;
    
    //前日比
    private BigDecimal diff;
    
    //協会コード
    private String kyoukaiCd;
}
