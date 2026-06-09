package com.sbisec.helios.ap.brokerageMenu.customerMenu.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
@Mapper
public interface IfaPortfolioMapper {
    
    /**
     * SQLID：Sql017
     * SQL名：SBIラップ口座分の現金情報取得
     * SQLタイプ：select
     * リクエストクラス：IfaPortfolioSql017RequestModel
     * レスポンスクラス：IfaPortfolioSql017ResponseModel
     *
     * @param req リクエストパラメータ
     * @return SBIラップ口座分の現金
     * @exception Exception SQLExceptionなど
     */
    public List<IfaPortfolioSql017ResponseModel> selectIfaPortfolioSql017(
            @Param("req") IfaPortfolioSql017RequestModel req
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
    public List<IfaPortfolioSql018ResponseModel> selectIfaPortfolioSql018(
            @Param("req") IfaPortfolioSql018RequestModel req
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
    public List<IfaPortfolioSql019ResponseModel> selectIfaPortfolioSql019(
            @Param("req") IfaPortfolioSql019RequestModel req
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
    public List<IfaPortfolioSql021ResponseModel> selectIfaPortfolioSql021(
            @Param("req") IfaPortfolioSql021RequestModel req) throws Exception;

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
    public List<IfaPortfolioSql022ResponseModel> selectIfaPortfolioSql022(
            @Param("req") IfaPortfolioSql022RequestModel req) throws Exception;

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
    public List<IfaPortfolioSql023ResponseModel> selectIfaPortfolioSql023(
            @Param("req") IfaPortfolioSql023RequestModel req) throws Exception;

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
    public List<IfaPortfolioSql025ResponseModel> selectIfaPortfolioSql025(
            @Param("req") IfaPortfolioSql025RequestModel req) throws Exception;

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
    public List<IfaPortfolioSql026ResponseModel> selectIfaPortfolioSql026(
            @Param("req") IfaPortfolioSql026RequestModel req) throws Exception;

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
    public List<IfaPortfolioSql027ResponseModel> selectIfaPortfolioSql027(
            @Param("req") IfaPortfolioSql027RequestModel req) throws Exception;

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
    public List<IfaPortfolioSql028ResponseModel> selectIfaPortfolioSql028(
            @Param("req") IfaPortfolioSql028RequestModel req) throws Exception;

}
