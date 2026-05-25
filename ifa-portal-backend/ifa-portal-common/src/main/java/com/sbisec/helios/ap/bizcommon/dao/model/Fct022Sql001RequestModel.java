package com.sbisec.helios.ap.bizcommon.dao.model;

import java.util.List;

import com.sbibits.earth.model.ModelBase;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * FCT002.sql001 国内投信基準価額取得
 *
 * @author 陳
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Fct022Sql001RequestModel extends ModelBase {
    
    private static final long serialVersionUID = 1L;
    //協会コード
    private List<String> kyoukaiCd;
    
}
