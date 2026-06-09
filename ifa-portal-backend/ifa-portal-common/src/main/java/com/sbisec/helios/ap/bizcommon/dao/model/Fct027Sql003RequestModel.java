package com.sbisec.helios.ap.bizcommon.dao.model;

import com.sbibits.earth.model.ModelBase;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 国内株式情報取得 PTS銘柄マスタ取得 リクエストDTO

 * @author SCSK

 * @see ORACLE - > ETI -> CORDYS -> PTS_BRAND_MASTER(PTS銘柄マスタ).xlsx
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Fct027Sql003RequestModel extends ModelBase {
    
    private static final long serialVersionUID = -8726404527636694720L;
    // 銘柄コード
    private String brandCodeFirst;

}
