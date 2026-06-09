package com.sbisec.helios.ap.brokerageMenu.commFee.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sbibits.earth.dao.RowSelectableDao;
import com.sbibits.earth.model.DataList;
import com.sbisec.helios.ap.brokerageMenu.commFee.dao.IfaSbiWrapManageFeeDao;
import com.sbisec.helios.ap.brokerageMenu.commFee.dao.mapper.IfaSbiWrapManageFeeMapper;
import com.sbisec.helios.ap.brokerageMenu.commFee.dao.model.IfaSbiWrapManageFeeSql001RequestModel;
import com.sbisec.helios.ap.brokerageMenu.commFee.dao.model.IfaSbiWrapManageFeeSql001ResponseModel;

/**
 * 画面ID：SUB020504-01
 * 画面名：SBIラップ管理報酬
 *
 * @author BASE 李
 2024/06/18 新規作成
 */
@Component
public class IfaSbiWrapManageFeeDaoImpL extends RowSelectableDao implements IfaSbiWrapManageFeeDao {
    
    @Autowired
    private IfaSbiWrapManageFeeMapper mapper;
    
    /**
     * SqlID：Sql001
     * Sql名：SBIラップ管理報酬情報データリスト取得
     * Sqlタイプ：select
     * リクエストクラス：IfaSbiWrapManageFeeSql001RequestModel
     * レスポンスクラス：IfaSbiWrapManageFeeSql001ResponseModel
     *
     * @param req リクエスト
     * @return res レスポンス
     * @exception exception システムエラー
     */
    public DataList<IfaSbiWrapManageFeeSql001ResponseModel> selectIfaSbiWrapManageFeeSql001(
            IfaSbiWrapManageFeeSql001RequestModel req) throws Exception {
        
        DataList<IfaSbiWrapManageFeeSql001ResponseModel> res = new DataList<IfaSbiWrapManageFeeSql001ResponseModel>();
        
        res.setDataList(mapper.selectIfaSbiWrapManageFeeSql001(req));
        return res;
    }
    
}
