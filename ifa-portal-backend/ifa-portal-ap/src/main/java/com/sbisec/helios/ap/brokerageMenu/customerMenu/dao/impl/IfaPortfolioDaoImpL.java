package com.sbisec.helios.ap.brokerageMenu.customerMenu.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sbibits.earth.dao.RowSelectableDao;
import com.sbibits.earth.model.DataList;
import com.sbisec.helios.ap.brokerageMenu.customerMenu.dao.IfaPortfolioDao;
import com.sbisec.helios.ap.brokerageMenu.customerMenu.dao.mapper.IfaPortfolioMapper;
import com.sbisec.helios.ap.brokerageMenu.customerMenu.dao.model.IfaPortfolioSql017RequestModel;
import com.sbisec.helios.ap.brokerageMenu.customerMenu.dao.model.IfaPortfolioSql017ResponseModel;
import com.sbisec.helios.ap.brokerageMenu.customerMenu.dao.model.IfaPortfolioSql018RequestModel;
import com.sbisec.helios.ap.brokerageMenu.customerMenu.dao.model.IfaPortfolioSql018ResponseModel;
import com.sbisec.helios.ap.brokerageMenu.customerMenu.dao.model.IfaPortfolioSql019RequestModel;
import com.sbisec.helios.ap.brokerageMenu.customerMenu.dao.model.IfaPortfolioSql019ResponseModel;
import com.sbisec.helios.ap.brokerageMenu.customerMenu.dao.model.IfaPortfolioSql021RequestModel;
import com.sbisec.helios.ap.brokerageMenu.customerMenu.dao.model.IfaPortfolioSql021ResponseModel;
import com.sbisec.helios.ap.brokerageMenu.customerMenu.dao.model.IfaPortfolioSql022RequestModel;
import com.sbisec.helios.ap.brokerageMenu.customerMenu.dao.model.IfaPortfolioSql022ResponseModel;
import com.sbisec.helios.ap.brokerageMenu.customerMenu.dao.model.IfaPortfolioSql023RequestModel;
import com.sbisec.helios.ap.brokerageMenu.customerMenu.dao.model.IfaPortfolioSql023ResponseModel;
import com.sbisec.helios.ap.brokerageMenu.customerMenu.dao.model.IfaPortfolioSql025RequestModel;
import com.sbisec.helios.ap.brokerageMenu.customerMenu.dao.model.IfaPortfolioSql025ResponseModel;
import com.sbisec.helios.ap.brokerageMenu.customerMenu.dao.model.IfaPortfolioSql026RequestModel;
import com.sbisec.helios.ap.brokerageMenu.customerMenu.dao.model.IfaPortfolioSql026ResponseModel;
import com.sbisec.helios.ap.brokerageMenu.customerMenu.dao.model.IfaPortfolioSql027RequestModel;
import com.sbisec.helios.ap.brokerageMenu.customerMenu.dao.model.IfaPortfolioSql027ResponseModel;
import com.sbisec.helios.ap.brokerageMenu.customerMenu.dao.model.IfaPortfolioSql028RequestModel;
import com.sbisec.helios.ap.brokerageMenu.customerMenu.dao.model.IfaPortfolioSql028ResponseModel;

/**
 * 画面ID：SUB0202_0101-01
 * 画面名：資産状況
 * 2023/12/26 新規作成
 *
 * @author SCSK 江口
 */
@Component
public class IfaPortfolioDaoImpL extends RowSelectableDao implements IfaPortfolioDao {

    @Autowired
    private IfaPortfolioMapper mapper;
    
    /**
     * SQLID：Sql017
     * SQL名：SBIラップ口座分の現金情報取得
     * SQLタイプ：select
     * リクエストクラス：IfaPortfolioSql017RequestModel
     * レスポンスクラス：IfaPortfolioSql017ResponseModel
     *
     * @param req リクエストパラメータ
     * @return SBIラップ口座分の現金情報
     * @exception Exception SQLExceptionなど
     */
    @Override
    public DataList<IfaPortfolioSql017ResponseModel> selectIfaPortfolioSql017(
            IfaPortfolioSql017RequestModel req
    ) throws Exception {
        
        DataList<IfaPortfolioSql017ResponseModel> res = new DataList<IfaPortfolioSql017ResponseModel>();
        
        res.setDataList(mapper.selectIfaPortfolioSql017(req));
        return res;
    }
    
    /**
     * SQLID：Sql018
     * SQL名：トータルリターン取得処理
     * SQLタイプ：select
     * リクエストクラス：IfaPortfolioSql018RequestModel
     * レスポンスクラス：IfaPortfolioSql018ResponseModel
     *
     * @param req リクエストパラメータ
     * @return トータルリターン
     * @exception Exception SQLExceptionなど
     */
    @Override
    public DataList<IfaPortfolioSql018ResponseModel> selectIfaPortfolioSql018(
            IfaPortfolioSql018RequestModel req
    ) throws Exception {
        
        DataList<IfaPortfolioSql018ResponseModel> res = new DataList<IfaPortfolioSql018ResponseModel>();
        
        res.setDataList(mapper.selectIfaPortfolioSql018(req));
        return res;
    }
    
    /**
     * SQLID：Sql019
     * SQL名：投資信託(SBIラップ投信)、STの商品明細取得
     * SQLタイプ：select
     * リクエストクラス：IfaPortfolioSql019RequestModel
     * レスポンスクラス：IfaPortfolioSql019ResponseModel
     *
     * @param req リクエストパラメータ
     * @return 投資信託(SBIラップ投信)、STの商品明細
     * @exception Exception SQLExceptionなど
     */
    @Override
    public DataList<IfaPortfolioSql019ResponseModel> selectIfaPortfolioSql019(
            IfaPortfolioSql019RequestModel req
    ) throws Exception {
        
        DataList<IfaPortfolioSql019ResponseModel> res = new DataList<IfaPortfolioSql019ResponseModel>();
        
        res.setDataList(mapper.selectIfaPortfolioSql019(req));
        return res;
    }
    
    /**
     * SQLID：Sql020
     * SQL名：T_BALANCE_PREV更新ジョブステータス取得
     * SQLタイプ：select
     *
     * @return String ジョブステータス
     * @exception Exception SQLExceptionなど
     */
    @Override
    public String selectIfaPortfolioSql020() throws Exception {
        
        return mapper.selectIfaPortfolioSql020();
    }

    /**
     * SQLID：Sql021
     * SQL名：優先市場取得
     * SQLタイプ：select
     * リクエストクラス：IfaPortfolioSql021RequestModel
     * レスポンスクラス：IfaPortfolioSql021ResponseModel
     *
     * @param req リクエスト
     * @return レスポンス
     * @exception Exception 優先市場取得時に例外が発生した場合
     */
    @Override
    public DataList<IfaPortfolioSql021ResponseModel> selectIfaPortfolioSql021(IfaPortfolioSql021RequestModel req)
            throws Exception {
        
        DataList<IfaPortfolioSql021ResponseModel> res = new DataList<IfaPortfolioSql021ResponseModel>();
        
        try {
            List<IfaPortfolioSql021ResponseModel> resList = mapper.selectIfaPortfolioSql021(req);
            res.setDataList(resList);
        } catch (Exception e) {
            throw e;
        }
        return res;
    }
  
    /**
     * SQLID：Sql022
     * SQL名：基準価額、基準価額単位取得
     * SQLタイプ：select
     * リクエストクラス：IfaPortfolioSql022RequestModel
     * レスポンスクラス：IfaPortfolioSql022ResponseModel
     *
     * @param req リクエスト
     * @return レスポンス
     * @exception Exception 基準価額、基準価額単位取得時に例外が発生した場合
     */
    @Override
    public DataList<IfaPortfolioSql022ResponseModel> selectIfaPortfolioSql022(IfaPortfolioSql022RequestModel req)
            throws Exception {
        
        DataList<IfaPortfolioSql022ResponseModel> res = new DataList<IfaPortfolioSql022ResponseModel>();
        
        try {
            List<IfaPortfolioSql022ResponseModel> resList = mapper.selectIfaPortfolioSql022(req);
            res.setDataList(resList);
        } catch (Exception e) {
            throw e;
        }
        return res;
    }

    /**
     * SQLID：Sql023
     * SQL名：外国債券（外貨建）銘柄情報取得
     * SQLタイプ：select
     * リクエストクラス：IfaPortfolioSql023RequestModel
     * レスポンスクラス：IfaPortfolioSql023ResponseModel
     *
     * @param req リクエスト
     * @return レスポンス
     * @exception Exception 外国債券（外貨建）銘柄情報取得時に例外が発生した場合
     */
    @Override
    public DataList<IfaPortfolioSql023ResponseModel> selectIfaPortfolioSql023(IfaPortfolioSql023RequestModel req)
            throws Exception {
        
        DataList<IfaPortfolioSql023ResponseModel> res = new DataList<IfaPortfolioSql023ResponseModel>();
        
        try {
            List<IfaPortfolioSql023ResponseModel> resList = mapper.selectIfaPortfolioSql023(req);
            res.setDataList(resList);
        } catch (Exception e) {
            throw e;
        }
        return res;
    }

    /**
     * SQLID：Sql025
     * SQL名：信用建玉銘柄名取得
     * SQLタイプ：select
     * リクエストクラス：IfaPortfolioSql025RequestModel
     * レスポンスクラス：IfaPortfolioSql025ResponseModel
     *
     * @param req リクエスト
     * @return レスポンス
     * @exception Exception 信用建玉銘柄名取得時に例外が発生した場合
     */
    @Override
    public DataList<IfaPortfolioSql025ResponseModel> selectIfaPortfolioSql025(IfaPortfolioSql025RequestModel req)
            throws Exception {
        
        DataList<IfaPortfolioSql025ResponseModel> res = new DataList<IfaPortfolioSql025ResponseModel>();
        
        try {
            List<IfaPortfolioSql025ResponseModel> resList = mapper.selectIfaPortfolioSql025(req);
            res.setDataList(resList);
        } catch (Exception e) {
            throw e;
        }
        return res;
    }

    /**
     * SQLID：Sql026
     * SQL名：債券銘柄用補助コード取得
     * SQLタイプ：select
     * リクエストクラス：IfaPortfolioSql026RequestModel
     * レスポンスクラス：IfaPortfolioSql026ResponseModel
     *
     * @param req リクエスト
     * @return レスポンス
     * @exception Exception 債券銘柄用補助コード取得時に例外が発生した場合
     */
    @Override
    public DataList<IfaPortfolioSql026ResponseModel> selectIfaPortfolioSql026(IfaPortfolioSql026RequestModel req)
            throws Exception {
        
        DataList<IfaPortfolioSql026ResponseModel> res = new DataList<IfaPortfolioSql026ResponseModel>();
        
        try {
            List<IfaPortfolioSql026ResponseModel> resList = mapper.selectIfaPortfolioSql026(req);
            res.setDataList(resList);
        } catch (Exception e) {
            throw e;
        }
        return res;
    }

    /**
     * SQLID：Sql027
     * SQL名：債券ST銘柄コード取得
     * SQLタイプ：select
     * リクエストクラス：IfaPortfolioSql027RequestModel
     * レスポンスクラス：IfaPortfolioSql027ResponseModel
     *
     * @param req リクエスト
     * @return レスポンス
     * @exception Exception 債券ST銘柄コード取得時に例外が発生した場合
     */
    @Override
    public DataList<IfaPortfolioSql027ResponseModel> selectIfaPortfolioSql027(IfaPortfolioSql027RequestModel req)
            throws Exception {
        
        DataList<IfaPortfolioSql027ResponseModel> res = new DataList<IfaPortfolioSql027ResponseModel>();
        
        try {
            List<IfaPortfolioSql027ResponseModel> resList = mapper.selectIfaPortfolioSql027(req);
            res.setDataList(resList);
        } catch (Exception e) {
            throw e;
        }
        return res;
    }

    /**
     * SQLID：Sql028
     * SQL名：先OP(保証金等)の資産情報取得
     * SQLタイプ：select
     * リクエストクラス：IfaPortfolioSql028RequestModel
     * レスポンスクラス：IfaPortfolioSql028ResponseModel
     *
     * @param req リクエスト
     * @return レスポンス
     * @exception Exception 先OP(保証金等)の資産情報取得時に例外が発生した場合
     */
    @Override
    public DataList<IfaPortfolioSql028ResponseModel> selectIfaPortfolioSql028(IfaPortfolioSql028RequestModel req)
            throws Exception {
        
        DataList<IfaPortfolioSql028ResponseModel> res = new DataList<IfaPortfolioSql028ResponseModel>();
        
        try {
            List<IfaPortfolioSql028ResponseModel> resList = mapper.selectIfaPortfolioSql028(req);
            res.setDataList(resList);
        } catch (Exception e) {
            throw e;
        }
        return res;
    }

}
