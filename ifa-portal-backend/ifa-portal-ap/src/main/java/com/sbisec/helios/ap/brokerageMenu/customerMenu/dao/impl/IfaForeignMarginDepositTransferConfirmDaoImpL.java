package com.sbisec.helios.ap.brokerageMenu.customerMenu.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sbibits.earth.dao.RowSelectableDao;
import com.sbisec.helios.ap.brokerageMenu.customerMenu.dao.IfaForeignMarginDepositTransferConfirmDao;
import com.sbisec.helios.ap.brokerageMenu.customerMenu.dao.mapper.IfaForeignMarginDepositTransferConfirmMapper;
import com.sbisec.helios.ap.brokerageMenu.customerMenu.dao.model.IfaForeignMarginDepositTransferConfirmSql001RequestModel;
import com.sbisec.helios.ap.brokerageMenu.customerMenu.dao.model.IfaForeignMarginDepositTransferConfirmSql002RequestModel;

/**
 * 画面ID：SUB0202_0304-01_2
 * 画面名：米株信用保証金振替確認
 *
 * @author
 */
@Component
public class IfaForeignMarginDepositTransferConfirmDaoImpL extends RowSelectableDao
        implements IfaForeignMarginDepositTransferConfirmDao {
    
    @Autowired
    private IfaForeignMarginDepositTransferConfirmMapper mapper;
    
    /**
     * SQLID：Sql001
     * SQL名：振替指示前の指示登録
     * SQLタイプ：insert
     * リクエストクラス：IfaForeignMarginDepositTransferConfirmSql001RequestModel
     * レスポンスクラス：IfaForeignMarginDepositTransferConfirmSql001ResponseModel
     *
     * @param req リクエスト
     * @return レスポンス
     * @throws Exception 初期化の際、例外が発生した場合
     */
    public int insertIfaForeignMarginDepositTransferConfirmSql001(
            IfaForeignMarginDepositTransferConfirmSql001RequestModel req) throws Exception {
        
        return mapper.insertIfaForeignMarginDepositTransferConfirmSql001(req);
    }
    
    /**
     * SQLID：Sql002
     * SQL名：振替指示後の指示更新
     * SQLタイプ：update
     * リクエストクラス：IfaForeignMarginDepositTransferConfirmSql002RequestModel
     * レスポンスクラス：IfaForeignMarginDepositTransferConfirmSql002ResponseModel
     *
     * @param req リクエスト
     * @return レスポンス
     * @throws Exception 初期化の際、例外が発生した場合
     */
    public int updateIfaForeignMarginDepositTransferConfirmSql002(
            IfaForeignMarginDepositTransferConfirmSql002RequestModel req) throws Exception {
        
        return mapper.updateIfaForeignMarginDepositTransferConfirmSql002(req);
    }
    
}
