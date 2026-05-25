package com.sbisec.helios.ap.bizcommon.dao.model;

import com.sbibits.earth.model.ModelBase;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 遡る時間の取得レスポンス
 * @author SCSK
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Fct004Sql001ResponseModel extends ModelBase {
    
    private static final long serialVersionUID = 1L;
    // 名称
    private String name;
    
}
