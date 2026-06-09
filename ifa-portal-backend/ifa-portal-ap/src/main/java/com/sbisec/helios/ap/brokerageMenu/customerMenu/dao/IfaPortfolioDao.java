package com.sbisec.helios.ap.brokerageMenu.customerMenu.dao;

import com.sbibits.earth.model.DataList;
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
 *
 */
public interface IfaPortfolioDao {
    
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
    public DataList<IfaPortfolioSql017ResponseModel> selectIfaPortfolioSql017(
            IfaPortfolioSql017RequestModel req
    ) throws Exception;
    
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
    public DataList<IfaPortfolioSql018ResponseModel> selectIfaPortfolioSql018(
            IfaPortfolioSql018RequestModel req
    ) throws Exception;
    
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
    public DataList<IfaPortfolioSql019ResponseModel> selectIfaPortfolioSql019(
            IfaPortfolioSql019RequestModel req
    ) throws Exception;
    
    /**
     * SQLID：Sql020
     * SQL名：T_BALANCE_PREV更新ジョブステータス取得
     * SQLタイプ：select
     *
     * @return String ジョブステータス
     * @exception Exception SQLExceptionなど
     */
    public String selectIfaPortfolioSql020() throws Exception;

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
    public DataList<IfaPortfolioSql021ResponseModel> selectIfaPortfolioSql021(
            IfaPortfolioSql021RequestModel req
    ) throws Exception;

     /**
     * SQLID：Sql022
     * SQL名：基準価額、基準価額単位取得
     * SQLタイプ：select
     * リクエストクラス：IfaPortfolioSql022RequestModel
     * レスポンスクラス：IfaPortfolioSql022ResponseModel
     *
     * @param req リクエスト
     * @return レスポンス
     * @exception Exception 基準価額単位取得時に例外が発生した場合
     */
    public DataList<IfaPortfolioSql022ResponseModel> selectIfaPortfolioSql022(
            IfaPortfolioSql022RequestModel req
    ) throws Exception;

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
    public DataList<IfaPortfolioSql023ResponseModel> selectIfaPortfolioSql023(
            IfaPortfolioSql023RequestModel req
    ) throws Exception;

    /**
     * SQLID：Sql025
     * SQL名：信用建玉銘柄名取得
     * SQLタイプ：select
     * リクエストクラス：IfaPortfolioSql025RequestModel
     * レスポンスクラス：IfaPortfolioSql025ResponseModel
     * 
     * @param req リクエスト
     * @return レスポンス
     * @throws Exception 信用建玉銘柄名取得時に例外が発生した場合
     */
    public DataList<IfaPortfolioSql025ResponseModel> selectIfaPortfolioSql025(
            IfaPortfolioSql025RequestModel req
    ) throws Exception;

    /**
     * SQLID：Sql026
     * SQL名：債券銘柄用補助コード取得
     * SQLタイプ：select
     * リクエストクラス：IfaPortfolioSql026RequestModel
     * レスポンスクラス：IfaPortfolioSql026ResponseModel
     * 
     * @param req リクエスト
     * @return レスポンス
     * @throws Exception 債券銘柄用補助コード取得時に例外が発生した場合
     */
    public DataList<IfaPortfolioSql026ResponseModel> selectIfaPortfolioSql026(
            IfaPortfolioSql026RequestModel req
    ) throws Exception;

    /**
     * SQLID：Sql027
     * SQL名：債券ST銘柄コード取得
     * SQLタイプ：select
     * リクエストクラス：IfaPortfolioSql027RequestModel
     * レスポンスクラス：IfaPortfolioSql027ResponseModel
     * 
     * @param req リクエスト
     * @return レスポンス
     * @throws Exception 債券ST銘柄コード取得時に例外が発生した場合
     */
    public DataList<IfaPortfolioSql027ResponseModel> selectIfaPortfolioSql027(
            IfaPortfolioSql027RequestModel req
    ) throws Exception;

    /**
     * SQLID：Sql028
     * SQL名：先OP(保証金等)の資産情報取得
     * SQLタイプ：select
     * リクエストクラス：IfaPortfolioSql028RequestModel
     * レスポンスクラス：IfaPortfolioSql028ResponseModel
     * 
     * @param req リクエスト
     * @return レスポンス
     * @throws Exception 先OP(保証金等)の資産情報取得時に例外が発生した場合
     */
    public DataList<IfaPortfolioSql028ResponseModel> selectIfaPortfolioSql028(
            IfaPortfolioSql028RequestModel req
    ) throws Exception;

}
