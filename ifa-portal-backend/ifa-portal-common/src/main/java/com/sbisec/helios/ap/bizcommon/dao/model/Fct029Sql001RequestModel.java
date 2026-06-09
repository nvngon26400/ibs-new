package com.sbisec.helios.ap.bizcommon.dao.model;

import com.sbibits.earth.model.ModelBase;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * SQL001 リクエスト
 *
 * @author 鄒
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Fct029Sql001RequestModel extends ModelBase {
    
    private static final long serialVersionUID = -3294126712421289099L;

    private String countryCode;
    
    private String brandCode;
    
}
