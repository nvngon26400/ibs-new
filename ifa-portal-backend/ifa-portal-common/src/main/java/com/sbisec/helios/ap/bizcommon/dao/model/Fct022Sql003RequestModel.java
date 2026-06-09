package com.sbisec.helios.ap.bizcommon.dao.model;

import java.util.List;

import com.sbibits.earth.model.ModelBase;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * FCT022.sql003 扱者個別国内投信販売手数料取得
 *
 * @author 陳
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Fct022Sql003RequestModel extends ModelBase {
    
    private static final long serialVersionUID = 1L;

    //扱者コード
    private String dealerNumber;
    
    private List<Sql003ReqModel> sql003ReqList;
    
    /**
     * Entityクラス
     */
    @Data
    public static class Sql003ReqModel {
        
        //ファンドタイプ
        private String fundType;
        
        //協会コード
        private String kyoukaiCd;
        
        /**
         * Sql003ReqModelコンストラクション
         */
        public Sql003ReqModel(String fundType, String kyoukaiCd) {
            
            this.fundType = fundType;
            this.kyoukaiCd = kyoukaiCd;
        }
        
        public Sql003ReqModel() {
            
        }
    }
    
}
