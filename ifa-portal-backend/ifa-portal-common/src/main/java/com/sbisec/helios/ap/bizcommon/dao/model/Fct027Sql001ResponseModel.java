package com.sbisec.helios.ap.bizcommon.dao.model;

import com.sbibits.earth.model.ModelBase;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 国内株式情報取得 銘柄属性情報取得 レスポンスDTO

 * @author SCSK

 * @see ORACLE - > ETI -> ETINTRA -> STAR_BRAND_MASTER(銘柄属性情報).xlsx
 *  
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Fct027Sql001ResponseModel extends ModelBase {
    
    private static final long serialVersionUID = 7811123076439286862L;
    // 銘柄名
    private String brandName;
    
}
