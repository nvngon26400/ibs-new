package com.sbisec.helios.ap.bizcommon.dao.model;

import java.math.BigDecimal;

import com.sbibits.earth.model.ModelBase;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 外債買付代金合計の算出レスポンス
 * @author SCSK
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Fct004Sql002ResponseModel extends ModelBase {
    
    private static final long serialVersionUID = 1L;
    // 金額
    private BigDecimal totalAmount;
    
}
