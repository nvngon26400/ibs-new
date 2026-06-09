package com.sbisec.helios.ap.brokerageMenu.customerMenu.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.sbibits.earth.model.DataList;
import com.sbibits.earth.util.StringUtil;
import com.sbisec.helios.ap.api.athena.ifa.ForeignAccountService;
import com.sbisec.helios.ap.api.athena.protocol.account.CashDeposit;
import com.sbisec.helios.ap.api.athena.protocol.account.ListForeignScheduleCashBalancesResp;
import com.sbisec.helios.ap.api.athena.protocol.account.ListMarginPositionsResp;
import com.sbisec.helios.ap.api.athena.protocol.account.ListSecuritiesBalancesResp;
import com.sbisec.helios.ap.api.athena.protocol.account.dto.CurrencyCashBalance;
import com.sbisec.helios.ap.api.athena.protocol.account.dto.ForeignCashBalance;
import com.sbisec.helios.ap.api.athena.protocol.account.dto.Position;
import com.sbisec.helios.ap.api.athena.protocol.account.dto.SecuritiesBalances;
import com.sbisec.helios.ap.api.athena.protocol.fstock.order.GetMarginPowerHeadlineResp;
import com.sbisec.helios.ap.api.athena.utils.AthenaBusinessException;
import com.sbisec.helios.ap.bizcommon.component.Fct001;
import com.sbisec.helios.ap.bizcommon.component.Fct020;
import com.sbisec.helios.ap.bizcommon.model.InputFct001Dto;
import com.sbisec.helios.ap.bizcommon.model.InputFct020Dto;
import com.sbisec.helios.ap.bizcommon.model.OutputFct001Dto;
import com.sbisec.helios.ap.bizcommon.model.OutputFct020Dto;
import com.sbisec.helios.ap.brokerageMenu.customerMenu.dao.IfaPortfolioDao;
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
import com.sbisec.helios.ap.brokerageMenu.customerMenu.dto.IfaPortfolioA001HoldingSecurityDomesticStockResponseDto;
import com.sbisec.helios.ap.brokerageMenu.customerMenu.dto.IfaPortfolioA001HoldingSecurityListCashResponseDto;
import com.sbisec.helios.ap.brokerageMenu.customerMenu.dto.IfaPortfolioA001HoldingSecurityListDomesticClaimResponseDto;
import com.sbisec.helios.ap.brokerageMenu.customerMenu.dto.IfaPortfolioA001HoldingSecurityListForeignClaimForeignResponseDto;
import com.sbisec.helios.ap.brokerageMenu.customerMenu.dto.IfaPortfolioA001HoldingSecurityListForeignClaimForeignStructuredBondResponseDto;
import com.sbisec.helios.ap.brokerageMenu.customerMenu.dto.IfaPortfolioA001HoldingSecurityListForeignClaimYenBaseResponseDto;
import com.sbisec.helios.ap.brokerageMenu.customerMenu.dto.IfaPortfolioA001HoldingSecurityListForeignDepositResponseDto;
import com.sbisec.helios.ap.brokerageMenu.customerMenu.dto.IfaPortfolioA001HoldingSecurityListForeignMmfResponseDto;
import com.sbisec.helios.ap.brokerageMenu.customerMenu.dto.IfaPortfolioA001HoldingSecurityListForeignStockResponseDto;
import com.sbisec.helios.ap.brokerageMenu.customerMenu.dto.IfaPortfolioA001HoldingSecurityListMarginPositionResponseDto;
import com.sbisec.helios.ap.brokerageMenu.customerMenu.dto.IfaPortfolioA001HoldingSecurityListMutualFundResponseDto;
import com.sbisec.helios.ap.brokerageMenu.customerMenu.dto.IfaPortfolioA001HoldingSecurityListMutualFundTotalReturnResponseDto;
import com.sbisec.helios.ap.brokerageMenu.customerMenu.dto.IfaPortfolioA001HoldingSecurityListSbiRapAccountCashResponseDto;
import com.sbisec.helios.ap.brokerageMenu.customerMenu.dto.IfaPortfolioA001HoldingSecurityListSecurityTokenResponseDto;
import com.sbisec.helios.ap.brokerageMenu.customerMenu.dto.IfaPortfolioA001HoldingSecurityListSweepAccountResponseDto;
import com.sbisec.helios.ap.brokerageMenu.customerMenu.dto.IfaPortfolioA001HoldingSecurityListUsStockMarginPositionResponseDto;
import com.sbisec.helios.ap.brokerageMenu.customerMenu.dto.IfaPortfolioA001PortfolioSummaryResponseDto;
import com.sbisec.helios.ap.brokerageMenu.customerMenu.dto.IfaPortfolioA001RequestDto;
import com.sbisec.helios.ap.brokerageMenu.customerMenu.dto.IfaPortfolioA001ResponseDto;
import com.sbisec.helios.ap.brokerageMenu.customerMenu.service.IfaPortfolioService;
import com.sbisec.helios.ap.brokerageMenu.wholeCustomer.enums.AutoSweepKbn;
import com.sbisec.helios.ap.brokerageMenu.wholeCustomer.enums.CapabilitySetKbn;
import com.sbisec.helios.ap.brokerageMenu.wholeCustomer.enums.DeficitSetKbn;
import com.sbisec.helios.ap.common.enums.DomesticMarginAccountType;
import com.sbisec.helios.ap.common.enums.ErrorLevel;
import com.sbisec.helios.ap.common.enums.ForeignMarginAccountType;
import com.sbisec.helios.ap.common.enums.ForeignSecurityTradeAccountOpenStatus;
import com.sbisec.helios.ap.common.enums.ForeignStockTradeAccountOpenStatus;
import com.sbisec.helios.ap.common.enums.RtnCdEnum;
import com.sbisec.helios.ap.common.model.CustomerCommon;
import com.sbisec.helios.ap.common.service.CodeListService;
import com.sbisec.helios.ap.common.service.CometCommonService;
import com.sbisec.helios.ap.common.util.ApiErrorUtil;
import com.sbisec.helios.ap.common.util.ApiWrapper;
import com.sbisec.helios.ap.common.util.IfaCommonUtil;

import jp.co.sbisec.pcenter.dto.yanap.AccountSumWebData;
import jp.co.sbisec.pcenter.dto.yanap.QueryAccountBalanceIn;
import jp.co.sbisec.pcenter.dto.yanap.QueryAccountBalanceInData;
import jp.co.sbisec.pcenter.dto.yanap.QueryAccountBalanceOutData;
import jp.co.sbisec.pcenter.dto.yanap.QueryAccountPositionSumWebInData;
import jp.co.sbisec.pcenter.dto.yanap.QueryAccountPositionSumWebOutData;
import jp.co.sbisec.pcenter.dto.yanap.QueryMarginContract0In;
import jp.co.sbisec.pcenter.dto.yanap.QueryMarginContract0InData;
import jp.co.sbisec.pcenter.dto.yanap.QueryMarginContract0OutData;
import jp.co.sbisec.pcenter.dto.yanap.QueryMarginContract0OutVec;
import jp.co.sbisec.pcenter.dto.yanap.QueryMgEstCapabilityWebHtsOutData;
import jp.co.sbisec.pcenter.dto.yanap.QueryMgEstCapabilityWebIn;
import jp.co.sbisec.pcenter.dto.yanap.QueryMgEstCapabilityWebInData;
import jp.co.sbisec.pcenter.dto.yanap.QueryMgEstCapabilityWebSettlementDateT;
/**
 * 画面ID：SUB0202_0101-01
 * 画面名：資産状況
 * 2023/12/26 新規作成
 *
 * @author SCSK 江口
 */
@Component(value = "cmpIfaPortfolioService")
public class IfaPortfolioServiceImpL implements IfaPortfolioService {
    
    @Autowired
    private IfaPortfolioDao dao;
    
    @Autowired
    private Fct001 fct001;

    @Autowired
    private Fct020 fct020;
   
    @Autowired
    private ApiWrapper apiWrapper;
  
    @Autowired
    private ForeignAccountService foreignAccountService;

    @Autowired
    private CometCommonService cometCommonService;

    @Autowired
    private CodeListService codelistservice;

    /** 顧客に対する権限なしエラー */
    private static final String ERRORS_BUTEN_ACCOUNT_NOT_EXIST = "errors.butenAccountNotExist";
    
    /** {0}が失敗しました。 */
    private static final String ERRORS_PROCESSINGFAILED = "errors.processingFailed";

    /** 区分.対象顧客参照権限有無.権限あり */
    private static final String TARGET_CUSTOMER_REFERENCE_AUTHORITY_FLAG_AUTHORIZED = "1";

    /** T_BALANCE_PREV更新ジョブ 稼働中 */
    private static final String JOB_STATUS_RUNNING = "1";

    /** ロガー */
    private static final Logger LOGGER = LoggerFactory.getLogger(IfaPortfolioServiceImpL.class);
  
    /** 商品タイプ 全商品 */
    private static final String SEC_TYPE_ALL = "  ";
    
    /** 半角スペース */
    private static final String REQUEST_TYPE_ALL = " ";

    /** 日付最大値 */
    private static final String FINALDATE = "99991231";
    
    /** 無期限 */
    private static final String UNLIMITED = "無期限";

    /** 日付値「9999-12-31」 */
    private static final String INVALID_DATE = "9999-12-31";

    /** 日付値「----/--/--」 */
    private static final String INVALID_DATE_DASH = "----/--/--";

    /** 通貨コードリスト */
    public static final List<String> CURRENCY_CODES = List.of("USD", "HKD", "EUR", "AUD", "NZD", "CAD",
            "ZAR", "MXN", "TRY", "SGD", "KRW", "RUB", "VND", "IDR", "THB", "MYR", "CNY");

    /** 外貨金銭残高スケジュールの未設定に含まれている通貨コードリスト */
    public static final List<String> INCLUDE_CURRENCY_CODES = List.of("HKD","IDR","KRW","MYR","RUB","SGD","THB","USD","VND");

    /** 入力パラメータ：国(籍)コード */
    private static final String COUNTRY_CODE_US = "US";

    /** 取得日数上限 */
    private static final int UPPER_DAYS = 6;
    
    /** NRI_QueryAccountPositionSumWeb API 1回の最大検索数 */
    private static final int MAX_QUERY_ACCOUNT_POSITION_SUM_WEB_OUT_DATA = 100;

    /** NRI_QueryMarginContract0 API 1回の最大検索数*/
    private static final int MAX_QUERY_MARGIN_CONTRACT0_OUT_DATA = 50;

    /** 商品タイプ名 国内株式 判定値 */
    private static final String SEC_TYPE_NAME_DOMESTIC_STOCK = "国内株式";

    /** 商品タイプ名 国内投信 判定値 */
    private static final String SEC_TYPE_NAME_DOMESTIC_MUTUAL_FUND = "国内投信";

    /** 商品タイプ 外国投信 判定値 */
    private static final String SEC_TYPE_NAME_FOREIGN_MUTUAL_FUND = "外国投信";

    /** 商品区分 国内株式 判定値 */
    private static final String SEC_ID_DOMESTIC_STOCK = "K";

    /** 商品区分 投信(国内(一般型)/外国) 判定値 */
    private static final String SEC_ID_MUTUAL_FUND = "T";

    /** 商品区分 国内投信(汎用累投) 判定値 */
    private static final String SEC_ID_DOMESTIC_MUTUAL_FUND = "Y";

    /** 商品区分 外国債券 判定値 */
    private static final String SEC_ID_FOREIGN_BONDS = "S";

    /** 銘柄情報.商品コード 外国株式 判定値 */
    private static final String PRODUCT_CODE_FOREIGN_STOCK = "FOREIGN_STOCK";

    /** 銘柄情報.商品コード 外貨建MMF 判定値 */
    private static final String PRODUCT_CODE_FOREIGN_MMF = "FOREIGN_MMF";

    /** 銘柄情報.商品コード 外国債券（外貨建） 判定値 */
    private static final String PRODUCT_CODE_FOREIGN_BOND = "FOREIGN_BOND";

    /** 商品検索ｺｰﾄﾞ(大分類) 国内債券 判定値 */
    private static final String BRAND_SEARCH_CODE1_DOMESTIC_CLAIM = "B";

    /** 商品検索ｺｰﾄﾞ(大分類) 外国債券 判定値 */
    private static final String BRAND_SEARCH_CODE1_FOREIGN_BONDS = "I";

    /** 商品検索ｺｰﾄﾞ(中分類) 国内債券 判定値 */
    private static final String BRAND_SEARCH_CODE2_DOMESTIC_CLAIM = "B04";

    /** 商品検索ｺｰﾄﾞ(小分類) 外国債券 判定値 */
    private static final String BRAND_SEARCH_CODE3_FOREIGN_BONDS = "I0118";

    /** 証券種別コード（商品分類コード） 国内投信 判定値   */
    private static final String SECURITY_CLASS_CODE_DOMESTIC_MUTUAL_FUND = "06";

    /** 証券種別コード（商品分類コード）ST 判定値 */
    private static final String SECURITY_CLASS_CODE_SECURITY_TOKEN = "33";

    /** 商品区分.株式 */
    private static final String SECURITY_TYPE_STOCK = "1 ";

    /** 区分名 売買区分（建玉） */
    private final static String POSITION_SELL_BUY_TYPE = "POSITION_SELL_BUY_TYPE";

    /** 区分名 新規市場 */
    private final static String NEW_MARKET = "NEW_MARKET";

    /** 現金リスト 名称 */
    private static final String CASH_ACCOUNT_NAME = "SBI証券口座分";

    /** スイープ専用銀行口座リスト 名称 */
    private static final String SWEEP_ACCOUNT_NAME = "スィープ専用銀行口座";

    /** SBIラップ口座現金 名称 */
    private static final String SBIRAP_ACCOUNT_CASH_NAME = "SBIラップ口座現金";

    /** SBIラップ投信 商品タイプ名 */
    private static final String SBIRAP_MUTUAL_FUND = "SBIラップ投信";

    /** 通貨コード USドル */
    private static final String CURRENCY_CODE_US = "USD";

    /**
     * 機能文字列の定数
     */
    private enum FunctionType {
        DOMESTIC_STOCK("国内株式､国内債券､投資信託､外国債券(円建)の明細の取得"),
        CASH("現金明細の取得"),
        DOMESTIC_MARGIN("信用維持率の取得"),
        MARGIN_POSITION("信用建玉明細の取得");

        private String name;

        private FunctionType(final String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }

    /**
     * 商品マップの定数
     */
    private enum ProductMapType {
        DOMESTIC_STOCK,
        FOREIGN_STOCK,
        SBIRAP_SECURITY_TOKEN,
        CASH,
        SBIRAP_ACCOUNT_CASH,
        FOREIGN_DEPOSIT,
        DOMESTIC_MARGIN,
        FOREIGN_MARGIN,
        TOTAL_RETURN,
        FUTURES_OP;
    }

    /**
     * 明細マップの定数
     */
    private enum DetailsMapType {
        API001,
        FCT020,
        SQL022,
        API009_FOREIGN_STOCK,
        API009_FOREIGN_MFF,
        API009_FOREIGN_BOND,
        SQL023,
        API007,
        API008,
        API003,
        API002,
        SQL025,
        API006,
        API005;
    }

    /**
     * 利払区分
     */
    private enum IntersetPaymentType {
        /** 利払区分.割引債 */
        DISCOUNT("0", "0 ", "-"),
        /** 利払区分.年1回 */
        ANNUAL("1", "1 ", "年1回："),
        /** 利払区分.年2回 */
        SEMI_ANNUAL("2","2 ", "年2回："),
        /** 利払区分.年4回 */
        QUARTERLY("3","3 ", "年4回(四半期1回)："),
        /** 利払区分.年12回(毎月) */
        MONTHLY("4", "4 ", "年12回(毎月)：");

        private String id;
        private String value;
        private String name;

        private IntersetPaymentType(String id,  String value, String name) {
            this.id = id;
            this.value = value;
            this.name = name;
        }

        public String getID() {
            return this.id;
        }
        public String getValue() {
            return this.value;
        }

        public String getName() {
            return this.name;
        }
    }

    /**
     * 利払日
     */
    private enum IntersetPaymentDateType {
        LAST_DAY("99", "末日");

        private String id;
        private String name;

        private IntersetPaymentDateType(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getID() {
            return this.id;
        }

        public String getName() {
            return this.name;
        }
    }

    /**
     * 預かり区分
     */
    private enum DepositType {
        ALL_PREFERENTIAL_TREATMENT("027", "特優"),
        PREFERENTIAL_TREATMENT("028", "マル優");

        private String id;
        private String name;

        private DepositType(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getID() {
            return this.id;
        }

        public String getName() {
            return this.name;
        }
    }

    /**
     * 国内株式の預かり区分
     */
    private enum SpecificAccountDomesticStock {
        SPECIFIC("0"),
        GENERAL("1"),
        NISA("4"),
        JR_GENERAL("5"),
        JR_SPECIFIC("6"),
        JR_NISA("7"),
        GROWTH_INVESTMENT("H"),
        CONTINUOUS_MANAGEMENT("J");

        SpecificAccountDomesticStock(String code) {
            this.code = code;
        }

        private static final Map<String, SpecificAccountDomesticStock>BY_CODE =
            Stream.of(values()).collect(Collectors.toMap(SpecificAccountDomesticStock::getCode, e -> e));

        private static final Set<String> PASSTHROUGH_CODE = Set.of("0", "4", "5", "6", "7", "H", "J");
 
        private String code;

        public String getCode() {
            return this.code;
        }

        public static SpecificAccountDomesticStock getByCode(String code) {
            String normalized = PASSTHROUGH_CODE.contains(code) ? code : "1";
            return BY_CODE.getOrDefault(normalized, null);
        }
    }

    /**
     * 債券の預かり区分
     */
    private enum SpecificAccountClaim {
        SPECIFIC("0"),
        GENERAL("1"),
        JR_GENERAL("5"),
        JR_SPECIFIC("6");

        SpecificAccountClaim(String code) {
            this.code = code;
        }

        private static final Map<String, SpecificAccountClaim>BY_CODE =
            Stream.of(values()).collect(Collectors.toMap(SpecificAccountClaim::getCode, e -> e));

        private static final Set<String> PASSTHROUGH_CODE = Set.of("0", "5", "6");
 
        private String code;

        public String getCode() {
            return this.code;
        }

        public static SpecificAccountClaim getByCode(String code) {
            String normalized = PASSTHROUGH_CODE.contains(code) ? code : "1";
            return BY_CODE.getOrDefault(normalized, null);
        }
    }

    /**
     * 投資信託の預かり区分
     */
    private enum SpecificAccountMutualFund {
        SPECIFIC("0"),
        GENERAL("1"),
        NISA("4"),
        JR_GENERAL("5"),
        JR_SPECIFIC("6"),
        JR_NISA("7"),
        NISA_RESERVE("8"),
        GROWTH_INVESTMENT("H"),
        ACCUMULATION_NISA("I"),
        CONTINUOUS_MANAGEMENT("J");

        SpecificAccountMutualFund(String code) {
            this.code = code;
        }

        private static final Map<String, SpecificAccountMutualFund>BY_CODE =
            Stream.of(values()).collect(Collectors.toMap(SpecificAccountMutualFund::getCode, e -> e));

        private static final Set<String> PASSTHROUGH_CODE = Set.of("0", "4", "5", "6", "7", "8", "H", "I", "J");
 
        private String code;

        public String getCode() {
            return this.code;
        }

        public static SpecificAccountMutualFund getByCode(String code) {
            String normalized = PASSTHROUGH_CODE.contains(code) ? code : "1";
            return BY_CODE.getOrDefault(normalized, null);
        }
    }

    /**
     * ジュニアISA契約区分
     */
    private enum JrIsaContractType {
        /** 契約 */
        CONTRACTED("1", "2"),
        /** 未契約 */
        UNCONTRACTED(" ", " "),
        /** 閉鎖済 */
        CLOSED("9", " ");

        String code;
        String accountKind;

        private JrIsaContractType(String code, String accountKind) {
            this.code = code;
            this.accountKind = accountKind;
        }

        private static final Map<String, JrIsaContractType>BY_CODE =
            Stream.of(values()).collect(Collectors.toMap(JrIsaContractType::getCode, e -> e));


        public String getCode() {
            return this.code;
        }

        public String getAccountKind() {
            return this.accountKind;
        }

        public static JrIsaContractType getByCode(String code) {
            String normalized = Strings.CS.equals(code, "1") ? "1" : " ";
            return BY_CODE.getOrDefault(normalized, UNCONTRACTED);
        }
    }

    /**
     * 口座分類
     */
    private enum ForeignAccountKind {
        GENERAL("GENERAL"),
        JR_NISA("JR_NISA");

        String value;

        private ForeignAccountKind(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 商品区分（集計）
     */
    private enum ProductType {
        DOMESTIC_STOCK("国内株式"),
        DOMESTIC_CLAIM("国内債券"),
        MUTUAL_FUND("投資信託"),
        FOREIGN_CLAIM_YEN_BASE("外国債券(円建)"),
        FOREIGN_STOCK("外国株式"),
        FOREIGN_MMF("外貨建MMF"),
        FOREIGN_CLAIM_FOREIGN("外国債券(外貨建)"),
        SECURITY_TOKEN("ST"),
        CASH("現金(円貨)"),
        FOREIGN_DEPOSIT("現金(外貨)"),
        MARGIN_POSITION("信用建玉"),
        US_STOCK_MARGIN_POSITION("米株信用建玉"),
        FUTURES_OP("先OP(保証金等)");

        String name;

        public String getName() {
            return this.name;
        }

        private ProductType(String name) {
            this.name = name;
        }
    }

    /**
     * アクションID：A001
     * アクション名：初期化
     * Dto リクエスト：IfaPortfolioA001RequestDto
     * Dto レスポンス：IfaPortfolioA001ResponseDto
     *
     * @param dtoReq リクエストパラメータ
     * @return 資産状況画面の初期化に必要な情報
     * @exception Exception システムエラー
     */
    @Override
    public DataList<IfaPortfolioA001ResponseDto> initializeA001(IfaPortfolioA001RequestDto dtoReq) throws Exception {
        
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("IfaPortfolioServiceImplL.initializeA001");
        }
        
        // レスポンスの内容を格納する変数
        IfaPortfolioA001ResponseDto data = new IfaPortfolioA001ResponseDto();
        
        /* ====================================================================== */
        /* T_BALANCE_PREV更新ジョブのステータスを取得する                            */
        /* ====================================================================== */

        String jobStatus = dao.selectIfaPortfolioSql020();
        data.setJobStatus(jobStatus);

        /* ====================================================================== */
        /* 利用者の口座に対する権限有無を確認(FCT001を呼び出し)                     */
        /* ====================================================================== */
        
        // FCT001リクエストDTOの作成
        String butenCode = IfaCommonUtil.getCustomerCommon().getButenCode();
        String accountNumber = IfaCommonUtil.getCustomerCommon().getAccountNumber();
        
        InputFct001Dto fct001InputDto = new InputFct001Dto();
        fct001InputDto.setButenCode(butenCode);
        fct001InputDto.setAccountNumber(accountNumber);
        
        // FCT001リクエストの実行
        OutputFct001Dto fct001OutputDto = fct001.doCheck(fct001InputDto);
        
        // 利用者の口座に対する権限を持っていない場合エラーレスポンスを返す
        if (fct001OutputDto == null || !Strings.CS.equals(fct001OutputDto.getTargetCustomerRefAuthFlag(),
                TARGET_CUSTOMER_REFERENCE_AUTHORITY_FLAG_AUTHORIZED)) {
            
            DataList<IfaPortfolioA001ResponseDto> dtoRes = IfaCommonUtil.createDataList(new ArrayList<>(),
                    ErrorLevel.FATAL, ERRORS_BUTEN_ACCOUNT_NOT_EXIST, IfaCommonUtil
                            .getMessage(ERRORS_BUTEN_ACCOUNT_NOT_EXIST, new String[] { butenCode, accountNumber }));
            
            return dtoRes;
        }

        ApiErrorUtil apiErrorUtil = new ApiErrorUtil();
        // ③ 商品ごとの資産情報を取得する 
        Map<ProductMapType, Object> securitiesBalancesMap;
        try {
            securitiesBalancesMap = getSecuritiesBalances(data, apiErrorUtil);
        } catch (AthenaBusinessException e) {
            return cometCommonService.checkBussinessException(IfaCommonUtil.createDataList(new ArrayList<>(),
                    ErrorLevel.FATAL, ErrorLevel.FATAL.toString(), null), e);
        }
        if (securitiesBalancesMap == null ) {
            return apiErrorUtil.createDataList(new ArrayList<>(), null);
        }

        // ④ 取得した商品ごとの資産情報を保有商品一覧の明細に展開する
        createHoldingSecurityList(securitiesBalancesMap, data);

        // ⑤ 取得した商品ごとの評価額合計と評価損益合計およびサマリーを算出する
        String futuresOpValuation = (String)securitiesBalancesMap.get(ProductMapType.FUTURES_OP);
        summarizeSecuriteiesBalances(data, futuresOpValuation);
        
        /* ====================================================================== */
        /* 正常終了のレスポンスを返す。                                              */
        /* ====================================================================== */
        DataList<IfaPortfolioA001ResponseDto> dtoRes = IfaCommonUtil.createDataList(Arrays.asList(data),
                ErrorLevel.SUCCESS, RtnCdEnum.SUCCESS.getText(), "");
        
        return dtoRes;
    }
    
    /**
     * ③ 商品ごとの資産情報を取得する
     * 
     * @param responseData
     * @param apiErrorUtil
     * @return 商品ごとの明細、null:NRI APIエラー
     * @throws Exception
     */
    private Map<ProductMapType, Object> getSecuritiesBalances(IfaPortfolioA001ResponseDto responseData, ApiErrorUtil apiErrorUtil) throws Exception {
        Map<ProductMapType, Object> result = Collections.synchronizedMap(new TreeMap<ProductMapType, Object>());

        CustomerCommon cc = IfaCommonUtil.getCustomerCommon();

        // ③-1 国内株式､国内債券､投資信託､外国債券(円建)の明細を取得する
        Map<DetailsMapType, Object> domesticStockMap = getDomesticStock(apiErrorUtil);
        if (domesticStockMap == null) {
            // NRI APIエラーの場合
            return null;
        }
        if (!domesticStockMap.isEmpty()) {
            result.put(ProductMapType.DOMESTIC_STOCK, domesticStockMap);
        }
        
        if (Strings.CS.equals(cc.getForeignStockTradeAccountOpenStatus(), ForeignStockTradeAccountOpenStatus.OPEN.getId())
            || Strings.CS.equals(cc.getForeignSecurityTradeAccountOpenStatus(), ForeignSecurityTradeAccountOpenStatus.OPEN.getId())) {
    
            // 顧客共通情報.外国株式取引口座開設状況=1（開設済）または顧客共通情報.外貨建商品取引口座開設状況=1（開設済）の場合
            // ③-2 外国株式､外貨建MMF､外国債券(外貨建)､外国債券(外貨建仕組債)の明細を取得する
            Map<DetailsMapType, Object> foreignStockMap = getForeignStock();
            if (!CollectionUtils.isEmpty(foreignStockMap)) {
                result.put(ProductMapType.FOREIGN_STOCK, foreignStockMap);
            }
        }

        if (!Strings.CS.equals(responseData.getJobStatus(), JOB_STATUS_RUNNING)) {
            // ジョブステータスが"1"（稼働中）以外の場合
            // ③-3 SBIラップ投信、ST（セキュリティ・トークン）の商品明細を取得する
            DataList<IfaPortfolioSql019ResponseModel> sbirapSecurityTokenList = getSbirapSecurityToken();
            if (sbirapSecurityTokenList != null && sbirapSecurityTokenList.size() > 0) {
                result.put(ProductMapType.SBIRAP_SECURITY_TOKEN, sbirapSecurityTokenList);
            }
        }

        // ③-4 現金明細を取得する
        QueryAccountBalanceOutData cashResponse = getCash(apiErrorUtil);
        if (cashResponse == null) {
            // NRI APIエラーの場合
            return null;
        }

        if (isCash(cashResponse)) {
            result.put(ProductMapType.CASH, cashResponse);
        }
        
        if (!Strings.CS.equals(responseData.getJobStatus(), JOB_STATUS_RUNNING)) {
            // ジョブステータスが"1"（稼働中）以外の場合
            // ③-5 SBIラップ口座分の現金情報を取得する
            DataList<IfaPortfolioSql017ResponseModel> sbiRapAccountCash = getSbiRapAccountCash();
            if (sbiRapAccountCash != null && sbiRapAccountCash.size() > 0 && sbiRapAccountCash.getDataList().size() > 0) {
                result.put(ProductMapType.SBIRAP_ACCOUNT_CASH, sbiRapAccountCash);
            }
        }

        if (Strings.CS.equals(cc.getForeignStockTradeAccountOpenStatus(), ForeignStockTradeAccountOpenStatus.OPEN.getId())
            || Strings.CS.equals(cc.getForeignSecurityTradeAccountOpenStatus(), ForeignSecurityTradeAccountOpenStatus.OPEN.getId())) {
    
            // 顧客共通情報.外国株式取引口座開設状況=1（開設済）または顧客共通情報.外貨建商品取引口座開設状況=1（開設済）の場合
            // ③-6 預り金(外貨)一括を取得する
            Map<DetailsMapType, Object> foreignDepositMap = getForeignDeposit();
            if (!CollectionUtils.isEmpty(foreignDepositMap)) {
                result.put(ProductMapType.FOREIGN_DEPOSIT, foreignDepositMap);
            }
        }

        if (Strings.CS.equals(cc.getDomesticMarginAccountType(), DomesticMarginAccountType.MARGIN_ACCOUNT.getId())) {
            // 顧客共通情報.信用口座区分（国内）=1:信用口座の場合
            // ③-7 信用維持率と信用建玉を取得する
            Map<DetailsMapType, Object> domesticMarginMap = getDomesticMargin(apiErrorUtil);
            if (domesticMarginMap == null) {
                // NRI APIエラーの場合
                return null;
            }
            if (!domesticMarginMap.isEmpty()) {
                result.put(ProductMapType.DOMESTIC_MARGIN, domesticMarginMap);
            }
        }

        if (Strings.CS.equals(cc.getForeignMarginAccountType(), ForeignMarginAccountType.MARGIN.getId())) {
            //顧客共通情報.信用口座区分（外国）=1:米株信用口座の場合
            // ③-8 米株信用維持率と米株信用建玉を取得する
            Map<DetailsMapType, Object> usMarginMap = getUSMargin();
            if (!CollectionUtils.isEmpty(usMarginMap)) {
                result.put(ProductMapType.FOREIGN_MARGIN, usMarginMap);
            }
        }

        // ③-9 トータルリターン取得処理
        DataList<IfaPortfolioSql018ResponseModel> mutualFundTotalReturnMap = getMutualFundTotalReturn();
        if (mutualFundTotalReturnMap != null && mutualFundTotalReturnMap.size() > 0) {
            result.put(ProductMapType.TOTAL_RETURN, mutualFundTotalReturnMap);
        }

        if (!Strings.CS.equals(responseData.getJobStatus(), JOB_STATUS_RUNNING)) {
            // ジョブステータスが"1"（稼働中）以外の場合
            // ③-10 先OP(保証金等)の資産情報取得
            String futuresOpValuation = getFuturesOp();
            result.put(ProductMapType.FUTURES_OP, futuresOpValuation);
        }

        return result;
    }

    /**
     * ③-1 国内株式､国内債券､投資信託､外国債券(円建)の明細を取得する
     * 
     * @param apiErrorUtil
     * @return 国内株式､国内債券､投資信託､外国債券(円建)の明細
     * @throws Exception
     */
    private Map<DetailsMapType, Object> getDomesticStock(ApiErrorUtil apiErrorUtil) throws Exception {
        CustomerCommon cc = IfaCommonUtil.getCustomerCommon();
        Map<DetailsMapType, Object> result = Collections.synchronizedMap(new TreeMap<DetailsMapType, Object>());

        // API001 預り残高一覧リクエスト（サマリー）(次期Web）
        QueryAccountPositionSumWebInData api001Req = new QueryAccountPositionSumWebInData();
        // 顧客共通情報.部店コード
        api001Req.setButenCd(cc.getButenCode());
        // 顧客共通情報.口座番号
        String accountNumber = String.format("%7s", cc.getAccountNumber()).replace(" ", "0");
        api001Req.setKozaNo(accountNumber);
        // 商品タイプ
        api001Req.setSecType(SEC_TYPE_ALL);
        // リクエスト区分
        api001Req.setRequestType(REQUEST_TYPE_ALL);
        // 検索番号指定FROM、検索番号指定TOはAPI内でセット
        // 取得口座区分
        String accountGetKbn = JrIsaContractType.getByCode(cc.getJrIsaContractType()).getAccountKind();
        api001Req.setAccountGetKbn(accountGetKbn);

        List<QueryAccountPositionSumWebOutData> api001ResList = new ArrayList<QueryAccountPositionSumWebOutData>();
        try {
            api001ResList = apiWrapper.queryAccountPositionSumWeb(api001Req);
        } catch (Exception e) {
            // 業務以外エラー
            apiErrorUtil.addError(ERRORS_PROCESSINGFAILED, new String[]{FunctionType.DOMESTIC_STOCK.getName()}, ErrorLevel.FATAL);
            return null;
        }

        for (QueryAccountPositionSumWebOutData api001Res : api001ResList) {
            apiErrorUtil.checkApiResponse(api001Res.getShubetu(), api001Res.getCode(), api001Res.getMessage());
        }

        if (apiErrorUtil.isFatal()) {
            return null;
        }

        Map<String, String> fct020Map = Collections.synchronizedMap(new TreeMap<String, String>());
        Map<String, DataList<IfaPortfolioSql022ResponseModel>> sql022Map = Collections.synchronizedMap(new TreeMap<String, DataList<IfaPortfolioSql022ResponseModel>>());

        QueryAccountPositionSumWebOutData api001Record = new QueryAccountPositionSumWebOutData();
        if (api001ResList != null && api001ResList.size() > 0) {
            BeanUtils.copyProperties(api001Record, api001ResList.get(0));
            if (MAX_QUERY_ACCOUNT_POSITION_SUM_WEB_OUT_DATA < Integer.parseInt(api001ResList.get(0).getHitNumber())) {
                for (int i = 1; i < api001ResList.size(); i++) {
                    for (AccountSumWebData data : api001ResList.get(i).getAccountSumWebData()) {
                        api001Record.getAccountSumWebData().add(data);
                    }
                }
            }

            for (Iterator<AccountSumWebData> accountSumWebDataIterator = api001Record.getAccountSumWebData().iterator(); accountSumWebDataIterator.hasNext();) {
                AccountSumWebData accountSumWebData = accountSumWebDataIterator.next();
                if (isDomesticStock(accountSumWebData)) {
                    // 国内株式
                    String brandCode = accountSumWebData.getCompanyCode().substring(0, 4) + accountSumWebData.getNewOldId();

                    if (fct020Map.containsKey(brandCode)) {
                        continue;
                    }

                    // SQL021 優先市場取得
                    IfaPortfolioSql021RequestModel sql021Req = new IfaPortfolioSql021RequestModel();
                    sql021Req.setIpmProductCode(brandCode);
                    DataList<IfaPortfolioSql021ResponseModel> sql021ResList = dao.selectIfaPortfolioSql021(sql021Req);
                    
                    String currentValueForEvaluation = null;
                    if (sql021ResList.getDataList().size() > 0) {
                        String ipmSeInvestmentsCode = sql021ResList.get(0).getIpmSeInvestmentsCode();
                        
                        // FCT020 国内株リアル時価取得
                        InputFct020Dto fct020Req = new InputFct020Dto();
                        fct020Req.setBrandCode(brandCode);
                        fct020Req.setMarketCode(ipmSeInvestmentsCode.trim());
                        OutputFct020Dto fct020Res = new OutputFct020Dto();
                        fct020Res = fct020.getData(fct020Req);
                        if (fct020Res != null) {
                            currentValueForEvaluation = fct020Res.getCurrentValueForEvaluation();
                        }
                    }
                    fct020Map.put(brandCode, currentValueForEvaluation);

                } else if (isDomesticClaim(accountSumWebData)) {
                    // 国内債券
                    if (isSTBrandCode(accountSumWebData)) {
                        // 債券ST銘柄除外
                        accountSumWebDataIterator.remove();
                        continue;
                    }

                } else if (isDomesticMutualFund(accountSumWebData)) {
                    // 投資信託
                    String serNo = !ObjectUtils.isEmpty(accountSumWebData.getSerNo()) ? accountSumWebData.getSerNo() : "";
                    String subCode2 = !ObjectUtils.isEmpty(accountSumWebData.getSubCode2()) ? accountSumWebData.getSubCode2() : "";
                    String brandCode = String.format("%-8s", serNo + " " + subCode2);

                    if (sql022Map.containsKey(brandCode)) {
                        continue;
                    }

                    // SQL022 基準価額、基準価額単位取得
                    IfaPortfolioSql022RequestModel sql022Req = new IfaPortfolioSql022RequestModel();
                    sql022Req.setBrandCode(brandCode);
                    DataList<IfaPortfolioSql022ResponseModel> sql022ResList = dao.selectIfaPortfolioSql022(sql022Req);

                    if (sql022ResList != null && sql022ResList.getDataList().size() > 0) {
                        sql022Map.put(brandCode, sql022ResList);
                    }
                }
            }

            result.put(DetailsMapType.API001, api001Record);
            if (!CollectionUtils.isEmpty(fct020Map)) {
                result.put(DetailsMapType.FCT020, fct020Map);
            }
            if (!CollectionUtils.isEmpty(sql022Map)) {
                result.put(DetailsMapType.SQL022, sql022Map);
            }
        }

        return result;
    }

    /**
     *  債券ST銘柄除外の判定
     * 
     * @param accountSumWebData
     * @return
     * @throws Exception
     */
    private boolean isSTBrandCode(AccountSumWebData accountSumWebData) throws Exception {
        boolean result = false;
        // 銘柄コード1
        String brandCode1 = formatWithZeroPadding(accountSumWebData.getCompanyCode(), 4);

        // 銘柄コード2
        String brandCode2 = formatWithZeroPadding(accountSumWebData.getSerNo(), 3);

        // 銘柄コード3
        String subCode2 = accountSumWebData.getSubCode2();
        String brandCode3 = "";
        if (!StringUtils.isBlank(subCode2)) {
            // SQL026 債券銘柄用補助コード取得
            IfaPortfolioSql026RequestModel sql026Req = new IfaPortfolioSql026RequestModel();
            sql026Req.setIssue2(subCode2.trim());
            DataList<IfaPortfolioSql026ResponseModel> sql026ResList = dao.selectIfaPortfolioSql026(sql026Req);

            if (sql026ResList != null && sql026ResList.getDataList().size() > 0) {
                brandCode3 = String.format(".%s", sql026ResList.get(0).getTradeCodePare());
            }
        }

        String brandCode = String.format("%s.%s%s", brandCode1, brandCode2, brandCode3);
        // SQL027 債券ST銘柄コード取得
        IfaPortfolioSql027RequestModel sql027Req = new IfaPortfolioSql027RequestModel();
        sql027Req.setBrandCode(brandCode);
        DataList<IfaPortfolioSql027ResponseModel> sql027ResList = dao.selectIfaPortfolioSql027(sql027Req);
        
        // 債券ST銘柄コードが存在するかどうか
        result = sql027ResList != null && sql027ResList.getDataList().size() > 0;
        return result;
    }

    /**
     * 指定桁数を取得（先頭0埋め）
     * 指定桁数以上の場合、下位指定桁数を返却
     * @param value
     * @param lenght
     * @return
     */
    private String formatWithZeroPadding(String value, int length) {
        if (ObjectUtils.isEmpty(value)) {
            return String.format("%0" + length + "d", 0);
        }

        value = value.trim();
        if (value.length() < length) {
            return String.format("%" + length + "s", value).replace(' ', '0');
        }

        return value.substring(value.length() - length);
    }

    /**
     * ③-2 外国株式､外貨建MMF､外国債券(外貨建)､外国債券(外貨建仕組債)の明細を取得する
     * 
     * @return 外国株式､外貨建MMF､外国債券(外貨建)､外国債券(外貨建仕組債)の明細
     * @throws Exception
     */
    private Map<DetailsMapType, Object> getForeignStock() throws Exception {
        Map<DetailsMapType, Object> result = Collections.synchronizedMap(new TreeMap<DetailsMapType, Object>());

        CustomerCommon cc = IfaCommonUtil.getCustomerCommon();
        String butenCode = cc.getButenCode();
        String accountNumber = String.format("%7s", cc.getAccountNumber()).replace(" ", "0");

        // 外国株式情報
        // API009 外貨建商品保有証券一覧取得API 
        ListSecuritiesBalancesResp api009ForeignStockRes = null;
        try {
            api009ForeignStockRes = foreignAccountService.listSecuritiesBalances(butenCode.substring(0, 3),
                    accountNumber, null, PRODUCT_CODE_FOREIGN_STOCK, null, null, null, null);
        } catch (Exception e) {
            throw e;
        }

        if (api009ForeignStockRes != null && !ObjectUtils.isEmpty(api009ForeignStockRes.getSecuritiesBalances())
            && api009ForeignStockRes.getSecuritiesBalances().size() > 0) {
            result.put(DetailsMapType.API009_FOREIGN_STOCK, api009ForeignStockRes);
        }

        // 外貨建MMF
        // API009 外貨建商品保有証券一覧取得API 
        ListSecuritiesBalancesResp api009ForeignMffRes = null;
        try {
            api009ForeignMffRes = foreignAccountService.listSecuritiesBalances(butenCode.substring(0, 3),
                    accountNumber, null, PRODUCT_CODE_FOREIGN_MMF, null, null, null, null);
        } catch (Exception e) {
            throw e;
        }

        if (api009ForeignMffRes != null && !ObjectUtils.isEmpty(api009ForeignMffRes.getSecuritiesBalances())
            && api009ForeignMffRes.getSecuritiesBalances().size() > 0) {
            result.put(DetailsMapType.API009_FOREIGN_MFF, api009ForeignMffRes);
        }

        // 外国債券(外貨建)、外国債券(外貨建仕組債)
        // API009 外貨建商品保有証券一覧取得API 
        ListSecuritiesBalancesResp api009ForeignBondRes = null;
        try {
            api009ForeignBondRes = foreignAccountService.listSecuritiesBalances(butenCode.substring(0, 3),
                    accountNumber, null, PRODUCT_CODE_FOREIGN_BOND, null, null, null, null);
        } catch (Exception e) {
            throw e;
        }

        Map<String, DataList<IfaPortfolioSql023ResponseModel>> sql023Map = Collections.synchronizedMap(new TreeMap<String, DataList<IfaPortfolioSql023ResponseModel>>());

        if (api009ForeignBondRes != null && !ObjectUtils.isEmpty(api009ForeignBondRes.getSecuritiesBalances())
                && api009ForeignBondRes.getSecuritiesBalances().size() > 0) {

            result.put(DetailsMapType.API009_FOREIGN_BOND, api009ForeignBondRes);
            
            for (SecuritiesBalances securitiesBalances : api009ForeignBondRes.getSecuritiesBalances()) {
                if (ObjectUtils.isEmpty(securitiesBalances.getSecurities())) {
                    continue;
                }

                String brandCode = securitiesBalances.getSecurities().getSecuritiesCode();

                if (sql023Map.containsKey(brandCode)) {
                    continue;
                }

                // SQL023 外国債券（外貨建）銘柄情報取得
                IfaPortfolioSql023RequestModel sql023Req = new IfaPortfolioSql023RequestModel();
                sql023Req.setBrandCode(brandCode);
                DataList<IfaPortfolioSql023ResponseModel> sql023ResList = dao.selectIfaPortfolioSql023(sql023Req);

                if (sql023ResList != null && sql023ResList.size() > 0) {
                    sql023Map.put(brandCode, sql023ResList);
                }
            }

            if (!CollectionUtils.isEmpty(sql023Map)) {
                result.put(DetailsMapType.SQL023, sql023Map);
            }
        }

        return result;
    }

    /**
     * ③-3 SBIラップ投信、ST（セキュリティ・トークン）の商品明細を取得する
     * 
     * @return SBIラップ投信、ST（セキュリティ・トークン）の商品明細
     * @throws Exception
     */
    private DataList<IfaPortfolioSql019ResponseModel> getSbirapSecurityToken() throws Exception {
        
        CustomerCommon cc = IfaCommonUtil.getCustomerCommon();
        String butenCode = cc.getButenCode();
        String accountNumber = cc.getAccountNumber();

        // SQL019 投資信託(SBIラップ投信)、STの商品明細取得
        IfaPortfolioSql019RequestModel sql019Req = new IfaPortfolioSql019RequestModel();
        sql019Req.setButenCode(butenCode);
        sql019Req.setAccountNumber(accountNumber);
        DataList<IfaPortfolioSql019ResponseModel> sql019ResList = dao.selectIfaPortfolioSql019(sql019Req);
        
        return sql019ResList;
    }

    /**
     * ③-4 現金明細を取得する
     * 
     * @param apiErrorUtil
     * @return 現金明細
     * @throws Exception
     */
    private QueryAccountBalanceOutData getCash(ApiErrorUtil apiErrorUtil) throws Exception {
        CustomerCommon cc = IfaCommonUtil.getCustomerCommon();
        String butenCode = cc.getButenCode();
        String accountNumber = String.format("%7s", cc.getAccountNumber()).replace(" ", "0");

        // API004 買付余力照会
        QueryAccountBalanceIn api004Req = new QueryAccountBalanceIn();
        QueryAccountBalanceInData api004ReqData = new QueryAccountBalanceInData(butenCode, accountNumber);
        api004Req.setIndata(api004ReqData);

        QueryAccountBalanceOutData api004Res = new QueryAccountBalanceOutData();
        try {
            api004Res = apiWrapper.queryAccountBalance(api004Req);
        } catch (Exception e) {
            // 業務以外エラー
            apiErrorUtil.addError(ERRORS_PROCESSINGFAILED, new String[]{FunctionType.CASH.getName()}, ErrorLevel.FATAL);
            return null;
        }
        apiErrorUtil.checkApiResponse(api004Res.getShubetu(), api004Res.getCode(), api004Res.getMessage());
        if (apiErrorUtil.isFatal()) {
            return null;
        }

        return api004Res;
    }

    /**
     *  ③-5 SBIラップ口座分の現金情報を取得する
     * 
     * @return SBIラップ口座分の現金情報を取得
     * @throws Exception
     */
    private DataList<IfaPortfolioSql017ResponseModel> getSbiRapAccountCash() throws Exception {

        CustomerCommon cc = IfaCommonUtil.getCustomerCommon();
        String butenCode = cc.getButenCode();
        String accountNumber = cc.getAccountNumber();

        // SQL017 SBIラップ口座分の現金情報取得
        IfaPortfolioSql017RequestModel sql017Req = new IfaPortfolioSql017RequestModel();
        sql017Req.setButenCode(butenCode);
        sql017Req.setAccountNumber(accountNumber);
        DataList<IfaPortfolioSql017ResponseModel> sql017Res = dao.selectIfaPortfolioSql017(sql017Req);

        return sql017Res;
    }

    /**
     * ③-6 預り金(外貨)一括を取得する
     * 
     * @return 預り金(外貨)一括
     * @throws Exception
     */
    private Map<DetailsMapType, Object> getForeignDeposit() throws Exception {
        Map<DetailsMapType, Object> result = Collections.synchronizedMap(new TreeMap<DetailsMapType, Object>());

        CustomerCommon cc = IfaCommonUtil.getCustomerCommon();
        String butenCode = cc.getButenCode();
        String accountNumber = String.format("%7s", cc.getAccountNumber()).replace(" ", "0");

        // API007 預り金一括取得API
        List<CashDeposit> api007ResList = new ArrayList<>();
        try {
            for (int i = 0; i < UPPER_DAYS; i++) {
                api007ResList.addAll(foreignAccountService.multiGetCashDeposits(butenCode, accountNumber, ForeignAccountKind.GENERAL.getValue(), i + 1, CURRENCY_CODES).getCashDeposits());
            }

            if (JrIsaContractType.getByCode(cc.getJrIsaContractType()) == JrIsaContractType.CONTRACTED
                && Strings.CS.equals(cc.getWithdrawalRestrictionCancelFlag(), "1")) {
                // 顧客共通情報.ジュニアISA契約区分＝1かつ顧客共通情報.払出制限解除フラグ＝1（払出制限中）の場合
                for (int i = 0; i < UPPER_DAYS; i++) {
                    api007ResList.addAll(foreignAccountService.multiGetCashDeposits(butenCode, accountNumber, ForeignAccountKind.JR_NISA.getValue(), i + 1, CURRENCY_CODES).getCashDeposits());
                }
            }
        } catch (Exception e) {
            throw e;
        }

        // 預り金リスト.預り金が0でない
        List<CashDeposit> cashDeposits = api007ResList.stream()
                                                .filter(c -> {
                                                    BigDecimal t = StringUtil.parseBigDecimal(c.getDepositAmount());
                                                    return t.compareTo(BigDecimal.ZERO) != 0;
                                                })
                                                .collect(Collectors.toMap(
                                                    CashDeposit::getCurrencyCode,
                                                    c -> c,   
                                                    (c1, c2) -> c1                                               
                                                ))
                                                .values()
                                                .stream()
                                                .collect(Collectors.toList());
        if (cashDeposits.size() > 0) {
            result.put(DetailsMapType.API007, cashDeposits);
        }

        // API008 外貨金銭残高スケジュール取得API 
        List<ListForeignScheduleCashBalancesResp> api008ResList = new ArrayList<>();
        boolean matched = cashDeposits.stream()
                                        .map(CashDeposit::getCurrencyCode)
                                        .anyMatch(INCLUDE_CURRENCY_CODES::contains);

        if (matched) {
            try {
                api008ResList.add(foreignAccountService.listForeignScheduleCashBalances(butenCode, accountNumber, null, null, UPPER_DAYS));
            } catch (Exception e) {
                throw e;
            }
        }

        List<String> notMatchCurrencyCodes = cashDeposits.stream()
                                                        .map(CashDeposit::getCurrencyCode)
                                                        .filter(c -> !INCLUDE_CURRENCY_CODES.contains(c))
                                                        .collect(Collectors.toList());

        for (String currencyCode : notMatchCurrencyCodes) {
            try {
                api008ResList.add(foreignAccountService.listForeignScheduleCashBalances(butenCode, accountNumber, currencyCode, null, UPPER_DAYS));
            } catch (Exception e) {
                throw e;
            }
        }

        if (!CollectionUtils.isEmpty(api008ResList)) {
            result.put(DetailsMapType.API008, api008ResList);
        }

        return result;
    }

    /**
     * ③-7 信用維持率と信用建玉を取得する
     * 
     * @param apiErrorUtil
     * @return 信用維持率と信用建玉
     * @throws Exception
     */
    private Map<DetailsMapType, Object> getDomesticMargin(ApiErrorUtil apiErrorUtil) throws Exception {
        Map<DetailsMapType, Object> result = Collections.synchronizedMap(new TreeMap<DetailsMapType, Object>());

        CustomerCommon cc = IfaCommonUtil.getCustomerCommon();
        String butenCode = cc.getButenCode();
        String accountNumber = String.format("%7s", cc.getAccountNumber()).replace(" ", "0");

        // API003 信用建玉余力リクエスト（簡易版）
        QueryMgEstCapabilityWebIn api003Req = new QueryMgEstCapabilityWebIn();
        QueryMgEstCapabilityWebInData api003Data = new QueryMgEstCapabilityWebInData();
        api003Data.setButenCd(butenCode);
        api003Data.setKozaNo(accountNumber);
        api003Data.setRequestKbn1(CapabilitySetKbn.ALL.getCode());
        api003Data.setRequestKbn2(DeficitSetKbn.ALL.getCode());
        api003Req.setIndata(api003Data);

        QueryMgEstCapabilityWebHtsOutData api003Res;

        try {
            api003Res = apiWrapper.queryMgEstCapabilityWebHts(api003Req);
        } catch (Exception e) {
            // 業務以外エラー
            apiErrorUtil.addError(ERRORS_PROCESSINGFAILED, new String[]{FunctionType.DOMESTIC_MARGIN.getName()}, ErrorLevel.FATAL);
            return null;
        }

        if (apiErrorUtil.isError(api003Res.getShubetu(), api003Res.getCode(), api003Res.getMessage())) {
            return null;
        }

        if (api003Res.getSettlementDateT().size() > 0) {
            result.put(DetailsMapType.API003, api003Res);
        }
        
        // API002 建玉残高一覧サマリー
        QueryMarginContract0In api002Req = new QueryMarginContract0In();
        QueryMarginContract0InData api002Data = new QueryMarginContract0InData();
        api002Data.setButenCd(butenCode);
        api002Data.setKozaNo(accountNumber);
        api002Req.setIndata(api002Data);

        List<QueryMarginContract0OutData> api002ResList;
        try {
            api002ResList = apiWrapper.queryMarginContract0(api002Req);
        } catch (Exception e) {
            // 業務以外エラー
            apiErrorUtil.addError(ERRORS_PROCESSINGFAILED, new String[]{FunctionType.MARGIN_POSITION.getName()}, ErrorLevel.FATAL);
            return null;
        }

        for (QueryMarginContract0OutData api002Res : api002ResList) {
            apiErrorUtil.checkApiResponse(api002Res.getShubetu(), api002Res.getCode(), api002Res.getMessage());
        }
        if (apiErrorUtil.isFatal()) {
            return null;
        }

        QueryMarginContract0OutData api002Records = new QueryMarginContract0OutData();
        Map<String, DataList<IfaPortfolioSql025ResponseModel>> sql025Map = Collections.synchronizedMap(new TreeMap<String, DataList<IfaPortfolioSql025ResponseModel>>());
        Map<String, String> fct020Map = Collections.synchronizedMap(new TreeMap<String, String>());

        if (api002ResList != null && api002ResList.size() > 0) {
            BeanUtils.copyProperties(api002Records, api002ResList.get(0));
            if (MAX_QUERY_MARGIN_CONTRACT0_OUT_DATA < Integer.parseInt(api002ResList.get(0).getHitNumber())) {
                for (int i = 1; i < api002ResList.size(); i++) {
                    for (QueryMarginContract0OutVec vec : api002ResList.get(i).getQueryMarginContract0Data()) {
                        api002Records.getQueryMarginContract0Data().add(vec);
                    }
                }
            }

            for (QueryMarginContract0OutVec vec : api002Records.getQueryMarginContract0Data()) {
                String brandCode = vec.getBrandCd();
                // SQL025 信用建玉銘柄名取得
                IfaPortfolioSql025RequestModel sql025Req = new IfaPortfolioSql025RequestModel();
                sql025Req.setBrandCodeFirst(brandCode.substring(0, 4));
                sql025Req.setBrandCodeLast(brandCode.substring(brandCode.length() - 1));
                DataList<IfaPortfolioSql025ResponseModel> sql025ResList = dao.selectIfaPortfolioSql025(sql025Req);
                sql025Map.put(brandCode, sql025ResList);

                // FCT020 国内株リアル時価取得
                InputFct020Dto fct020Req = new InputFct020Dto();
                fct020Req.setBrandCode(brandCode);
                fct020Req.setRightType(vec.getStRightId());
                fct020Req.setCtNightBatchEndFlag(api002Records.getNightBatchEndFlg());
                OutputFct020Dto fct020Res = new OutputFct020Dto();
                fct020Res = fct020.getData(fct020Req);
                String currentValueForEvaluation = fct020Res.getCurrentValueForEvaluation();
                fct020Map.put(brandCode, currentValueForEvaluation);
            }

            result.put(DetailsMapType.API002, api002Records);
            if (!CollectionUtils.isEmpty(sql025Map)) {
                result.put(DetailsMapType.SQL025, sql025Map);
            }
            if (!CollectionUtils.isEmpty(fct020Map)) {
                result.put(DetailsMapType.FCT020, fct020Map);
            }
        }

        return result;
    }

    /**
     * ③-8 米株信用維持率と米株信用建玉を取得する
     * 
     * @return 米株信用維持率と米株信用建玉
     * @throws Exception
     */
    private Map<DetailsMapType, Object> getUSMargin() throws Exception {
        Map<DetailsMapType, Object> result = Collections.synchronizedMap(new TreeMap<DetailsMapType, Object>());

        CustomerCommon cc = IfaCommonUtil.getCustomerCommon();
        String butenCode = cc.getButenCode();
        String accountNumber = String.format("%7s", cc.getAccountNumber()).replace(" ", "0");

        // API006 外国株式信用建余力取得API
        GetMarginPowerHeadlineResp api006Res = new GetMarginPowerHeadlineResp();
        try {
            api006Res = foreignAccountService.getMarginPowerHeadline(butenCode, accountNumber, COUNTRY_CODE_US);
        } catch (Exception e) {
            throw e;
        }

        result.put(DetailsMapType.API006, api006Res);

        // API005 外国株式信用建玉明細一覧取得API
        ListMarginPositionsResp api005Res = new ListMarginPositionsResp();
        try {
            api005Res = foreignAccountService.listMarginPositions(butenCode, accountNumber, COUNTRY_CODE_US, null, null,
                    null, null, null, null, null, null);

        } catch (Exception e) {
            throw e;
        }

        result.put(DetailsMapType.API005, api005Res);

        return result;
    }

    /**
     * ③-9 トータルリターン取得処理
     * 
     * @return トータルリターン
     * @throws Exception
     */
    private DataList<IfaPortfolioSql018ResponseModel> getMutualFundTotalReturn() throws Exception {
        CustomerCommon cc = IfaCommonUtil.getCustomerCommon();
        String butenCode = cc.getButenCode();
        String accountNumber = cc.getAccountNumber();

        // トータルリターン取得処理
        IfaPortfolioSql018RequestModel sql018Req = new IfaPortfolioSql018RequestModel();
        sql018Req.setButenCode(butenCode);
        sql018Req.setAccountNumber(accountNumber);
        DataList<IfaPortfolioSql018ResponseModel> sql018Res = dao.selectIfaPortfolioSql018(sql018Req);

        return sql018Res;
    }

    /**
     * 先OP(保証金等)の資産情報取得
     * 
     * @return 先OP(保証金等)
     * @throws Exception
     */
    private String getFuturesOp() throws Exception {
        CustomerCommon cc = IfaCommonUtil.getCustomerCommon();
        String butenCode = cc.getButenCode();
        String accountNumber = cc.getAccountNumber();

        // 先OP(保証金等)の資産情報取得
        IfaPortfolioSql028RequestModel sql028Req = new IfaPortfolioSql028RequestModel();
        sql028Req.setButenCode(butenCode);
        sql028Req.setAccountNumber(accountNumber);
        DataList<IfaPortfolioSql028ResponseModel> sql028Res = dao.selectIfaPortfolioSql028(sql028Req);

        String result = null;
        if (sql028Res != null && sql028Res.getDataList().size() > 0) {
            result = sql028Res.get(0).getValuation();
        }

        return result;
    }

    /**
     * 保有商品一覧リストの作成
     * 
     * @param securitiesBalancesMap
     * @param responseData
     * @throws Exception
     */
    private void createHoldingSecurityList(Map<ProductMapType, Object> securitiesBalancesMap, IfaPortfolioA001ResponseDto responseData) throws Exception {
        // 保有商品一覧_国内株式リスト
        ArrayList<IfaPortfolioA001HoldingSecurityDomesticStockResponseDto> holdingSecurityDomesticStockList = new ArrayList<>();
        // 保有商品一覧_国内債券リスト
        ArrayList<IfaPortfolioA001HoldingSecurityListDomesticClaimResponseDto> holdingSecurityListDomesticClaimList = new ArrayList<>();
        // 保有商品一覧_投資信託リスト
        ArrayList<IfaPortfolioA001HoldingSecurityListMutualFundResponseDto> holdingSecurityListMutualFundList = new ArrayList<>();
        // 保有商品一覧_外国債券(円建)リスト
        ArrayList<IfaPortfolioA001HoldingSecurityListForeignClaimYenBaseResponseDto> holdingSecurityListForeignClaimYenBaseList = new ArrayList<>();
        // 保有商品一覧_STリスト
        ArrayList<IfaPortfolioA001HoldingSecurityListSecurityTokenResponseDto> holdingSecurityListSecurityTokenList = new ArrayList<>();
        // 保有商品一覧_外国株式リスト
        ArrayList<IfaPortfolioA001HoldingSecurityListForeignStockResponseDto> holdingSecurityListForeignStockList = new ArrayList<>();
        // 保有商品一覧_外貨建MMFリスト
        ArrayList<IfaPortfolioA001HoldingSecurityListForeignMmfResponseDto> holdingSecurityListForeignMmfList = new ArrayList<>();
        // 保有商品一覧_外国債券(外貨建)リスト
        ArrayList<IfaPortfolioA001HoldingSecurityListForeignClaimForeignResponseDto> holdingSecurityListForeignClaimForeignList = new ArrayList<>();
        // 保有商品一覧_外国債券(外貨建仕組債)リスト
        ArrayList<IfaPortfolioA001HoldingSecurityListForeignClaimForeignStructuredBondResponseDto> holdingSecurityListForeignClaimForeignStructuredBondList = new ArrayList<>();
        // 保有商品一覧_現金リスト
        ArrayList<IfaPortfolioA001HoldingSecurityListCashResponseDto> holdingSecurityListCashList = new ArrayList<>();
        // 保有商品一覧_スイープ専用銀行口座リスト 
        ArrayList<IfaPortfolioA001HoldingSecurityListSweepAccountResponseDto> holdingSecurityListSweepAccountList = new ArrayList<>();
        // 保有商品一覧_SBIラップ口座現金リスト
        ArrayList<IfaPortfolioA001HoldingSecurityListSbiRapAccountCashResponseDto> holdingSecurityListSbiRapAccountCashList = new ArrayList<>();
        // 保有商品一覧_外貨預金リスト
        ArrayList<IfaPortfolioA001HoldingSecurityListForeignDepositResponseDto> holdingSecurityListForeignDepositList = new ArrayList<>();
        // 保有商品一覧_信用建玉リスト
        ArrayList<IfaPortfolioA001HoldingSecurityListMarginPositionResponseDto> holdingSecurityListMarginPositionList = new ArrayList<>();
        // 保有商品一覧_米株信用建玉リスト
        ArrayList<IfaPortfolioA001HoldingSecurityListUsStockMarginPositionResponseDto> holdingSecurityListUsStockMarginPositionList = new ArrayList<>();
        // 保有商品一覧_投資信託トータルリターンリスト
        ArrayList<IfaPortfolioA001HoldingSecurityListMutualFundTotalReturnResponseDto> holdingSecurityListMutualFundTotalReturnList = new ArrayList<>();

        if (securitiesBalancesMap.containsKey(ProductMapType.DOMESTIC_STOCK)) {
            Map<DetailsMapType, Object> domesticStockMap = (Map<DetailsMapType, Object>)securitiesBalancesMap.get(ProductMapType.DOMESTIC_STOCK);

            if (domesticStockMap.containsKey(DetailsMapType.API001)) {
                QueryAccountPositionSumWebOutData api001Record = (QueryAccountPositionSumWebOutData)domesticStockMap.get(DetailsMapType.API001);
               
                Map<String, String> fct020Map = (Map<String, String>)domesticStockMap.get(DetailsMapType.FCT020);
                Map<String, DataList<IfaPortfolioSql022ResponseModel>> sql022Map = (Map<String, DataList<IfaPortfolioSql022ResponseModel>>)domesticStockMap.get(DetailsMapType.SQL022);

                for (AccountSumWebData accountSumWebData : api001Record.getAccountSumWebData()) {
                    if (isDomesticStock(accountSumWebData)) {
                        // 保有商品一覧_国内株式リスト編集
                        holdingSecurityDomesticStockList.add(expandDomesticStock(api001Record, accountSumWebData, fct020Map));
                    } else if (isDomesticClaim(accountSumWebData)) {
                        // 保有商品一覧_国内債券リスト編集
                        holdingSecurityListDomesticClaimList.add(expandDomesticClaim(accountSumWebData));
                    } else if (isDomesticMutualFund(accountSumWebData)) {
                        // 保有商品一覧_投資信託リスト編集(国内投信)
                        holdingSecurityListMutualFundList.add(expandDomesticMutualFund(accountSumWebData, sql022Map));
                    } else if (isForeignMutualFund(accountSumWebData)) {
                        // 保有商品一覧_投資信託リスト編集(外国投信)
                        holdingSecurityListMutualFundList.add(expandForeignMutualFund(accountSumWebData));
                    } else if (isForeignClaimYen(accountSumWebData)) {
                        // 保有商品一覧_外国債券(円建)リスト編集
                        holdingSecurityListForeignClaimYenBaseList.add(expandForeignClaimYen(accountSumWebData));
                    }
                }
                responseData.setHoldingSecurityDomesticStock(holdingSecurityDomesticStockList);
                responseData.setHoldingSecurityListDomesticClaim(holdingSecurityListDomesticClaimList);
                responseData.setHoldingSecurityListMutualFund(holdingSecurityListMutualFundList);
                responseData.setHoldingSecurityListForeignClaimYenBase(holdingSecurityListForeignClaimYenBaseList);
            }
        }

        if (securitiesBalancesMap.containsKey(ProductMapType.SBIRAP_SECURITY_TOKEN)) {
            DataList<IfaPortfolioSql019ResponseModel> sbirapSecurityTokenList = (DataList<IfaPortfolioSql019ResponseModel>)securitiesBalancesMap.get(ProductMapType.SBIRAP_SECURITY_TOKEN);
            for (IfaPortfolioSql019ResponseModel record: sbirapSecurityTokenList.getDataList()) {
                if (isDomesticMutualFundSbiRap(record)) {
                    // 保有商品一覧_投資信託リスト編集(国内投信(SBIラップ投信))
                    holdingSecurityListMutualFundList.add(expandSbiRapMutualFund(record));
                } else if (isSecurityToken(record)) {
                    // 保有商品一覧_STリスト編集
                    holdingSecurityListSecurityTokenList.add(expandSecurityToken(record));
                }
            }
            responseData.setHoldingSecurityListMutualFund(holdingSecurityListMutualFundList);
            responseData.setHoldingSecurityListSecurityToken(holdingSecurityListSecurityTokenList);
        }

        if (securitiesBalancesMap.containsKey(ProductMapType.FOREIGN_STOCK)) {
            Map<DetailsMapType, Object> foreignStockMap = (Map<DetailsMapType, Object>)securitiesBalancesMap.get(ProductMapType.FOREIGN_STOCK);

            if (foreignStockMap.containsKey(DetailsMapType.API009_FOREIGN_STOCK)) {
                ListSecuritiesBalancesResp api009ForeignStockRes = (ListSecuritiesBalancesResp)foreignStockMap.get(DetailsMapType.API009_FOREIGN_STOCK);

                for (SecuritiesBalances securitiesBalances : api009ForeignStockRes.getSecuritiesBalances()) {
                    // 保有商品一覧_外国株式リスト編集
                    holdingSecurityListForeignStockList.add(expandForeignStock(securitiesBalances));
                }
                responseData.setHoldingSecurityListForeignStock(holdingSecurityListForeignStockList);
            }

            if (foreignStockMap.containsKey(DetailsMapType.API009_FOREIGN_MFF)) {
                ListSecuritiesBalancesResp api009ForeignMffRes = (ListSecuritiesBalancesResp)foreignStockMap.get(DetailsMapType.API009_FOREIGN_MFF);

                for (SecuritiesBalances securitiesBalances : api009ForeignMffRes.getSecuritiesBalances()) {
                    // 保有商品一覧_外貨建MMFリスト編集
                    holdingSecurityListForeignMmfList.add(expandFpreignMmf(securitiesBalances));
                }
                responseData.setHoldingSecurityListForeignMmf(holdingSecurityListForeignMmfList);
            }

            if (foreignStockMap.containsKey(DetailsMapType.API009_FOREIGN_BOND)) {
                ListSecuritiesBalancesResp api009ForeignBondRes = (ListSecuritiesBalancesResp)foreignStockMap.get(DetailsMapType.API009_FOREIGN_BOND);
                
                // SQL023
                Map<String, DataList<IfaPortfolioSql023ResponseModel>> sql023Map = (Map<String, DataList<IfaPortfolioSql023ResponseModel>>)foreignStockMap.get(DetailsMapType.SQL023);
                
                for (SecuritiesBalances securitiesBalances : api009ForeignBondRes.getSecuritiesBalances()) {
                    if (ObjectUtils.isEmpty(securitiesBalances.getSecurities())) {
                        continue;
                    }

                    DataList<IfaPortfolioSql023ResponseModel> sql023ResList = null;
                    if (sql023Map != null) {
                        String brandCode = securitiesBalances.getSecurities().getSecuritiesCode();
                        sql023ResList = sql023Map.get(brandCode);
                    }
                    
                    IfaPortfolioSql023ResponseModel record = (sql023ResList != null && sql023ResList.getDataList().size() != 0) ? sql023ResList.getDataList().get(0) : null;

                    if (record != null && Strings.CS.equals(record.getStructuredBondClassification(), SECURITY_TYPE_STOCK)) {
                        // 仕組債区分 = '1△'(仕組債)の場合
                        // 保有商品一覧_外国債券(外貨建仕組債)リスト編集
                        holdingSecurityListForeignClaimForeignStructuredBondList.add(expandForeignClaimForeignStructured(securitiesBalances, record));
                    } else {
                        // 取得できなかった または 取得件数0 または 仕組債区分 = '1△'(仕組債)以外の場合
                        // 保有商品一覧_外国債券(外貨建)リスト編集
                        holdingSecurityListForeignClaimForeignList.add(expandForeignClaimForeign(securitiesBalances, record));
                    }
                }
                responseData.setHoldingSecurityListForeignClaimForeign(holdingSecurityListForeignClaimForeignList);
                responseData.setHoldingSecurityListForeignClaimForeignStructuredBond(holdingSecurityListForeignClaimForeignStructuredBondList);
            }
        }

        // API004
        if (securitiesBalancesMap.containsKey(ProductMapType.CASH)) {
            QueryAccountBalanceOutData api004Res = (QueryAccountBalanceOutData)securitiesBalancesMap.get(ProductMapType.CASH);
            // 保有商品一覧_現金リスト編集
            String valuationCash = calcValuationTotalCash(api004Res);
            if (!Strings.CS.equals(valuationCash, "0")) {
                holdingSecurityListCashList.add(expandCashJrNisaUnAccount(api004Res, valuationCash));
                responseData.setHoldingSecurityListCash(holdingSecurityListCashList);
            }

            if (isSweepAccount(api004Res)) {
                // 保有商品一覧_スイープ専用銀行口座リスト編集
                holdingSecurityListSweepAccountList.add(expandSweepAccount(api004Res));
                responseData.setHoldingSecurityListSweepAccountList(holdingSecurityListSweepAccountList);
            }
        }

        // SQL017
        if (securitiesBalancesMap.containsKey(ProductMapType.SBIRAP_ACCOUNT_CASH)) {
            DataList<IfaPortfolioSql017ResponseModel> sql017ResList = (DataList<IfaPortfolioSql017ResponseModel>)securitiesBalancesMap.get(ProductMapType.SBIRAP_ACCOUNT_CASH);
            for (IfaPortfolioSql017ResponseModel record : sql017ResList.getDataList()) {
                // 保有商品一覧_SBIラップ口座現金リスト編集
                holdingSecurityListSbiRapAccountCashList.add(expandSbiRapAccountCash(record));
            }
            responseData.setHoldingSecurityListSbiRapAccountCash(holdingSecurityListSbiRapAccountCashList);
        }

        if (securitiesBalancesMap.containsKey(ProductMapType.FOREIGN_DEPOSIT)) {
            Map<ProductMapType, Object> foreignDepositMap = (Map<ProductMapType, Object>)securitiesBalancesMap.get(ProductMapType.FOREIGN_DEPOSIT);

            // API008
            Map<String, String> foreignValuationMap = Collections.synchronizedMap(new TreeMap<String, String>());
            if (foreignDepositMap.containsKey(DetailsMapType.API008)) {
                List<ListForeignScheduleCashBalancesResp> api008ResList = (List<ListForeignScheduleCashBalancesResp>)foreignDepositMap.get(DetailsMapType.API008);
                foreignValuationMap = expandForeignValuation(api008ResList);
            }

            if (foreignDepositMap.containsKey(DetailsMapType.API007)) {
                // API007
                List<CashDeposit> cashDeposits = (List<CashDeposit>)foreignDepositMap.get(DetailsMapType.API007);
                for (CashDeposit cashDeposit : cashDeposits) {
                    String currencyCode = cashDeposit.getCurrencyCode();

                    String foreignValuation = null;
                    if (foreignValuationMap.containsKey(currencyCode)) {
                        foreignValuation = foreignValuationMap.get(currencyCode);
                    }

                    // 保有商品一覧_外貨預金リスト編集
                    holdingSecurityListForeignDepositList.add(expandForeignDeposit(cashDeposit, foreignValuation));
                }
                responseData.setHoldingSecurityListForeignDeposit(holdingSecurityListForeignDepositList);
            }
        }

        if (securitiesBalancesMap.containsKey(ProductMapType.DOMESTIC_MARGIN)) {
            Map<DetailsMapType, Object> domesticMarginMap = (Map<DetailsMapType, Object>)securitiesBalancesMap.get(ProductMapType.DOMESTIC_MARGIN);
            
            if (domesticMarginMap.containsKey(DetailsMapType.API003)) {
                QueryMgEstCapabilityWebHtsOutData api003Res = (QueryMgEstCapabilityWebHtsOutData)domesticMarginMap.get(DetailsMapType.API003);
                // 維持率（国内信用）
                responseData.setDomesticMarginActualGrntRate(expandDomesticMarginActualGrntRate(api003Res));
            }

            // API002
            if (domesticMarginMap.containsKey(DetailsMapType.API002)) {
                QueryMarginContract0OutData api002Record = (QueryMarginContract0OutData)domesticMarginMap.get(DetailsMapType.API002);
                Map<String, DataList<IfaPortfolioSql025ResponseModel>> sql025Map = (Map<String, DataList<IfaPortfolioSql025ResponseModel>>)domesticMarginMap.get(DetailsMapType.SQL025);
                Map<String, String> fct020Map = (Map<String, String>)domesticMarginMap.get(DetailsMapType.FCT020);

                for(QueryMarginContract0OutVec vec : api002Record.getQueryMarginContract0Data()) {
                    // 保有商品一覧_信用建玉リスト編集
                    holdingSecurityListMarginPositionList.add(expandDomesticMarginPosition(vec, sql025Map, fct020Map));
                }
                responseData.setHoldingSecurityListMarginPosition(holdingSecurityListMarginPositionList);
            }
        }

        if (securitiesBalancesMap.containsKey(ProductMapType.FOREIGN_MARGIN)) {
            Map<DetailsMapType, Object> foreignMaginMap = (Map<DetailsMapType, Object>)securitiesBalancesMap.get(ProductMapType.FOREIGN_MARGIN);

            if (foreignMaginMap.containsKey(DetailsMapType.API006)) {
                GetMarginPowerHeadlineResp apiResponse = (GetMarginPowerHeadlineResp)foreignMaginMap.get(DetailsMapType.API006);
                // 維持率（米株信用）
                responseData.setAmericaMarginActualGrntRate(expandUSMarginActualGrntRate(apiResponse));
            }

            if (foreignMaginMap.containsKey(DetailsMapType.API005)) {
                ListMarginPositionsResp apiResponse = (ListMarginPositionsResp)foreignMaginMap.get(DetailsMapType.API005);
                for (Position position : apiResponse.getPositions()) {
                    // 保有商品一覧_米株信用建玉リスト編集
                    holdingSecurityListUsStockMarginPositionList.add(expandUSStockMarginPosition(position));
                }
                responseData.setHoldingSecurityListUsStockMarginPositionList(holdingSecurityListUsStockMarginPositionList);
            }
        }

        if (securitiesBalancesMap.containsKey(ProductMapType.TOTAL_RETURN)) {
            DataList<IfaPortfolioSql018ResponseModel> sql018ResList = (DataList<IfaPortfolioSql018ResponseModel>)securitiesBalancesMap.get(ProductMapType.TOTAL_RETURN);
            for (IfaPortfolioSql018ResponseModel record : sql018ResList.getDataList()) {
                // 保有商品一覧_投資信託トータルリターンリスト編集
                holdingSecurityListMutualFundTotalReturnList.add(expandMutualFundTotalReturn(record));
            }
            responseData.setHoldingSecurityListMutualFundTotalReturnList(holdingSecurityListMutualFundTotalReturnList);
        }
    }

    /**
     * 保有商品一覧_国内株式リスト編集
     * 
     * @param api001Record
     * @param accountSumWebData
     * @param fct020Map
     * @return 保有商品一覧_国内株式リスト
     * @throws Exception
     */
    private IfaPortfolioA001HoldingSecurityDomesticStockResponseDto expandDomesticStock(QueryAccountPositionSumWebOutData api001Record, AccountSumWebData accountSumWebData, Map<String, String> fct020Map) throws Exception {
        IfaPortfolioA001HoldingSecurityDomesticStockResponseDto responseDto = new IfaPortfolioA001HoldingSecurityDomesticStockResponseDto();
        String brandCode = accountSumWebData.getCompanyCode().substring(0, 4) + accountSumWebData.getNewOldId();

        BigDecimal currentValueForEvaluation = null;
        if (fct020Map != null && fct020Map.containsKey(brandCode)) {
            if (!StringUtil.isNullOrEmpty((String)fct020Map.get(brandCode))) {
                currentValueForEvaluation = new BigDecimal((String)fct020Map.get(brandCode));
            }
        }

        // 銘柄コード
        responseDto.setBrandCode(brandCode);
        // 銘柄名
        responseDto.setBrandName(accountSumWebData.getSecName());
        // 預り区分
        responseDto.setDepositType(SpecificAccountDomesticStock.getByCode(accountSumWebData.getHitokuteiKbn()).getCode());
        // 約定基準残高
        BigDecimal position = ObjectUtils.isEmpty(accountSumWebData.getPosition()) ? null : new BigDecimal(accountSumWebData.getPosition());
        responseDto.setContractStandardDeposit(accountSumWebData.getPosition());
        // 取得単価
        SpecificAccountDomesticStock specificAccountOpenPrice = SpecificAccountDomesticStock.getByCode(accountSumWebData.getHitokuteiKbn());

        BigDecimal openPrice;
        if (specificAccountOpenPrice == SpecificAccountDomesticStock.GENERAL) {
            // 一般預りの場合、加重平均単価
            openPrice = ObjectUtils.isEmpty(accountSumWebData.getWghAveragePrice()) ? null : new BigDecimal(accountSumWebData.getWghAveragePrice());
        } else {
            // 特定預りの場合、移動平均単価
            openPrice = ObjectUtils.isEmpty(accountSumWebData.getAveragePrice()) ? null : new BigDecimal(accountSumWebData.getAveragePrice());
        }

        if (openPrice != null) {
            // 小数点第3位以下切捨て
            String openPriceValue = openPrice.setScale(2, RoundingMode.DOWN).toPlainString();
            responseDto.setOpenPrice(openPriceValue);
        }

        // 時価
        if (currentValueForEvaluation != null) {
            String priceValue = currentValueForEvaluation.setScale(2, RoundingMode.DOWN).toPlainString();
            responseDto.setPrice(priceValue);
        }

        // 評価額（円貨）
        if (position != null && currentValueForEvaluation != null) {
            BigDecimal valuation = position.multiply(currentValueForEvaluation);
            // 計算結果の小数点第3位を四捨五入
            String valuationValue = valuation.setScale(2, RoundingMode.HALF_UP).toPlainString();
            responseDto.setValuation(valuationValue);
        }

        // 評価損益
        if (currentValueForEvaluation != null && openPrice != null && position != null) {
            BigDecimal profitAndLoss = currentValueForEvaluation.subtract(openPrice).multiply(position);
            String profitAndLossValue = profitAndLoss.setScale(2, RoundingMode.HALF_UP).toPlainString();
            responseDto.setProfitAndLoss(profitAndLossValue);
        }

        return responseDto;
    }

    /**
     * 保有商品一覧_国内債券リスト編集
     * 
     * @param accountSumWebData
     * @return 保有商品一覧_国内債券リスト
     * @throws Exception
     */
    private IfaPortfolioA001HoldingSecurityListDomesticClaimResponseDto expandDomesticClaim(AccountSumWebData accountSumWebData) throws Exception {
        IfaPortfolioA001HoldingSecurityListDomesticClaimResponseDto responseDto = new IfaPortfolioA001HoldingSecurityListDomesticClaimResponseDto();

        // 銘柄コード
        String brandCode = !ObjectUtils.isEmpty(accountSumWebData.getCompanyCode()) ?  accountSumWebData.getCompanyCode() : "";
        if (!StringUtils.isBlank(accountSumWebData.getSerNo())) {
            brandCode = String.format("%s.%s", brandCode, accountSumWebData.getSerNo());
        }
        responseDto.setBrandCode(brandCode);
        // 銘柄名
        responseDto.setBrandName(accountSumWebData.getSecName());
        // 預り区分
        responseDto.setDepositType(SpecificAccountClaim.getByCode(accountSumWebData.getHitokuteiKbn()).getCode());
        // 利率
        responseDto.setCompoundInterest(accountSumWebData.getRateInterest());
        // 償還日
        responseDto.setRedemptionDate(accountSumWebData.getRedemptionDate());
        // 利払日
        responseDto.setInterestPaymentDate(getClaimInterestPaymentDate(accountSumWebData));
        // 取得単価
        SpecificAccountClaim specificAccountOpenPrice = SpecificAccountClaim.getByCode(accountSumWebData.getHitokuteiKbn());

        BigDecimal openPrice;
        if (specificAccountOpenPrice == SpecificAccountClaim.GENERAL) {
            // 一般預りの場合、加重平均単価
            openPrice = ObjectUtils.isEmpty(accountSumWebData.getWghAveragePrice()) ? null : new BigDecimal(accountSumWebData.getWghAveragePrice());
        } else {
            // 特定預りの場合、移動平均単価
            openPrice = ObjectUtils.isEmpty(accountSumWebData.getAveragePrice()) ? null : new BigDecimal(accountSumWebData.getAveragePrice());
        }

        if (openPrice != null) {
            // 小数点第3位以下切捨て
            String openPriceValue = openPrice.setScale(2, RoundingMode.DOWN).toPlainString();
            responseDto.setOpenPrice(openPriceValue);
        }

        // 約定基準残高
        BigDecimal position = ObjectUtils.isEmpty(accountSumWebData.getPosition()) ? null : new BigDecimal(accountSumWebData.getPosition());
        String positionValue = accountSumWebData.getPosition();
        if (Strings.CS.equals(DepositType.ALL_PREFERENTIAL_TREATMENT.getID(), accountSumWebData.getPositionId())) {
            positionValue = String.format("%s %s", DepositType.ALL_PREFERENTIAL_TREATMENT.getName(), positionValue);
        } else if (Strings.CS.equals(DepositType.PREFERENTIAL_TREATMENT.getID(), accountSumWebData.getPositionId())) {
            positionValue = String.format("%s %s", DepositType.PREFERENTIAL_TREATMENT.getName(), positionValue);
        }
        responseDto.setContractStandardDeposit(positionValue);

        // 評価額（円貨）
        if (openPrice != null && openPrice.compareTo(BigDecimal.ZERO) != 0) {
            // 約定基準残高 * 平均単価(上記で取得) / 100 * 参考為替
            // 計算結果の小数点以下切り捨て
            BigDecimal valuation = position.multiply(openPrice);
            valuation = valuation.divide(BigDecimal.valueOf(100));
            valuation = valuation.multiply(new BigDecimal(accountSumWebData.getStandardRate()));
            valuation = valuation.setScale(0, RoundingMode.DOWN);
            responseDto.setValuation(valuation.toPlainString());
        }

        return responseDto;
    }

    /**
     * 保有商品一覧_投資信託リスト編集(国内投信)
     * 
     * @param accountSumWebData
     * @return 保有商品一覧_投資信託リスト(国内投信)
     * @throws Exception
     */
    private IfaPortfolioA001HoldingSecurityListMutualFundResponseDto expandDomesticMutualFund(AccountSumWebData accountSumWebData, Map<String, DataList<IfaPortfolioSql022ResponseModel>> sql022Map) throws Exception {
        IfaPortfolioA001HoldingSecurityListMutualFundResponseDto responseDto = new IfaPortfolioA001HoldingSecurityListMutualFundResponseDto();

        String brandCodeKey = String.format("%-8s", accountSumWebData.getSerNo() + " " + accountSumWebData.getSubCode2());
        DataList<IfaPortfolioSql022ResponseModel> sql022ResList = new DataList<IfaPortfolioSql022ResponseModel>();
        
        if (sql022Map != null && sql022Map.containsKey(brandCodeKey)) {
            sql022ResList = (DataList<IfaPortfolioSql022ResponseModel>)sql022Map.get(brandCodeKey);
        }

        // 銘柄コード
        String brandCode = String.format("%s.%s", accountSumWebData.getSerNo(), accountSumWebData.getSubCode2());
        responseDto.setBrandCode(brandCode);
        // 銘柄名
        responseDto.setBrandName(accountSumWebData.getSecName());
        // 預り区分
        responseDto.setDepositType(SpecificAccountMutualFund.getByCode(accountSumWebData.getHitokuteiKbn()).getCode());
        // 約定基準残高
        BigDecimal position = ObjectUtils.isEmpty(accountSumWebData.getPosition()) ? null : new BigDecimal(accountSumWebData.getPosition());
        responseDto.setContractStandardDeposit(accountSumWebData.getPosition());

        // 取得単価
        SpecificAccountMutualFund specificAccountOpenPrice = SpecificAccountMutualFund.getByCode(accountSumWebData.getHitokuteiKbn());

        BigDecimal openPrice;
        if (specificAccountOpenPrice == SpecificAccountMutualFund.GENERAL) {
            // 一般預りの場合、加重平均単価
            openPrice = ObjectUtils.isEmpty(accountSumWebData.getWghAveragePrice()) ? null : new BigDecimal(accountSumWebData.getWghAveragePrice());
        } else {
            // 特定預りの場合、移動平均単価
            openPrice = ObjectUtils.isEmpty(accountSumWebData.getAveragePrice()) ? null : new BigDecimal(accountSumWebData.getAveragePrice());
        }

        if (openPrice != null) {
            // 小数点第3位以下切捨て
            String openPriceValue = openPrice.setScale(2, RoundingMode.DOWN).toPlainString();
            responseDto.setOpenPrice(openPriceValue);
        }

        BigDecimal price = null;
        BigDecimal valuation = null;
        BigDecimal basePriceUnit = null;
        if (sql022ResList != null) {
            // 時価
            price = (sql022ResList.size() > 0 && !StringUtil.isNullOrEmpty(sql022ResList.get(0).getBasePrice()) && !sql022ResList.get(0).getBasePrice().trim().isEmpty())
                                ? new BigDecimal(sql022ResList.get(0).getBasePrice()) : null;
            if (price != null) {
                responseDto.setPrice(price.toPlainString());
            }

            // 基準価額単位
            basePriceUnit = (sql022ResList.size() > 0 && !StringUtil.isNullOrEmpty(sql022ResList.get(0).getBasePriceUnit()) && !sql022ResList.get(0).getBasePriceUnit().trim().isEmpty())
                                ? new BigDecimal(sql022ResList.get(0).getBasePriceUnit()) : null;

            // 評価額（円貨）
            if (basePriceUnit != null && basePriceUnit.compareTo(BigDecimal.ZERO) != 0 && price != null && price.compareTo(BigDecimal.ZERO) > 0 && position != null) {
                valuation = price.multiply(position).divide(basePriceUnit).setScale(0, RoundingMode.DOWN);
                responseDto.setValuation(valuation.toPlainString());
            }
        }

        // 評価損益
        if (basePriceUnit != null && valuation != null && openPrice != null && position != null &&
                basePriceUnit.compareTo(BigDecimal.ZERO) > 0 && valuation.compareTo(BigDecimal.ZERO) > 0) {

            BigDecimal profitAndLoss = valuation.subtract(openPrice.multiply(position).divide(basePriceUnit, 0, RoundingMode.DOWN));
            responseDto.setProfitAndLoss(profitAndLoss.toPlainString());
        }

        // 商品タイプ名
        responseDto.setSecTypeName(accountSumWebData.getSecTypeName());

        return responseDto;
    }

    /**
     * 保有商品一覧_投資信託リスト編集(外国投信)
     * 
     * @param accountSumWebData
     * @return 保有商品一覧_投資信託リスト(外国投信)
     * @throws Exception
     */
    private IfaPortfolioA001HoldingSecurityListMutualFundResponseDto expandForeignMutualFund(AccountSumWebData accountSumWebData) throws Exception {
        IfaPortfolioA001HoldingSecurityListMutualFundResponseDto responseDto = new IfaPortfolioA001HoldingSecurityListMutualFundResponseDto();

        // 銘柄コード
        String brandCode = accountSumWebData.getSecType1() + accountSumWebData.getCompanyCode().substring(accountSumWebData.getCompanyCode().length() - 4);
        responseDto.setBrandCode(brandCode);
        // 銘柄名
        responseDto.setBrandName(accountSumWebData.getSecName());
        // 預り区分
        responseDto.setDepositType(SpecificAccountMutualFund.getByCode(accountSumWebData.getHitokuteiKbn()).getCode());
        // 約定基準残高
        responseDto.setContractStandardDeposit(accountSumWebData.getPosition());
        // 商品タイプ名
        responseDto.setSecTypeName(accountSumWebData.getSecTypeName());

        return responseDto;
    }

    /**
     * 保有商品一覧_外国債券(円建)リスト編集
     * 
     * @param accountSumWebData
     * @return 保有商品一覧_外国債券(円建)リスト
     * @throws Exception
     */
    private IfaPortfolioA001HoldingSecurityListForeignClaimYenBaseResponseDto expandForeignClaimYen(AccountSumWebData accountSumWebData) throws Exception {
        IfaPortfolioA001HoldingSecurityListForeignClaimYenBaseResponseDto responseDto = new IfaPortfolioA001HoldingSecurityListForeignClaimYenBaseResponseDto();
        
        // 銘柄コード
        String brandCode = accountSumWebData.getSecType1() + accountSumWebData.getCompanyCode().substring(accountSumWebData.getCompanyCode().length() - 4);
        responseDto.setBrandCode(brandCode);
        // 銘柄名
        responseDto.setBrandName(accountSumWebData.getSecName());
        // 預り区分
        responseDto.setDepositType(SpecificAccountClaim.getByCode(accountSumWebData.getHitokuteiKbn()).getCode());
        // 利率
        responseDto.setCompoundInterest(accountSumWebData.getRateInterest());
        // 通貨
        responseDto.setCurrency(accountSumWebData.getIssuedCcyCode());
        // 為替レート
        responseDto.setFxRate(accountSumWebData.getStandardRate());
        // 償還日
        responseDto.setRedemptionDate(accountSumWebData.getRedemptionDate());
        // 利払日
        responseDto.setInterestPaymentDate(getClaimInterestPaymentDate(accountSumWebData));
        // 取得単価
        SpecificAccountClaim specificAccountOpenPrice = SpecificAccountClaim.getByCode(accountSumWebData.getHitokuteiKbn());

        BigDecimal openPrice;
        if (specificAccountOpenPrice == SpecificAccountClaim.GENERAL) {
            // 一般預りの場合、加重平均単価
            openPrice = ObjectUtils.isEmpty(accountSumWebData.getWghAveragePrice()) ? null : new BigDecimal(accountSumWebData.getWghAveragePrice());
        } else {
            // 特定預りの場合、移動平均単価
            openPrice = ObjectUtils.isEmpty(accountSumWebData.getAveragePrice()) ? null : new BigDecimal(accountSumWebData.getAveragePrice());
        }
        BigDecimal averagePrice = openPrice;

        if (openPrice != null) {
            // 小数点第3位以下切捨て
            String openPriceValue = openPrice.setScale(2, RoundingMode.DOWN).toPlainString();
            responseDto.setOpenPrice(openPriceValue);
        }

        // 約定基準残高
        BigDecimal position = ObjectUtils.isEmpty(accountSumWebData.getPosition()) ? null : new BigDecimal(accountSumWebData.getPosition());
        responseDto.setContractStandardDeposit(accountSumWebData.getPosition());
        
        // 評価額（円貨）
        if (averagePrice != null && averagePrice.compareTo(BigDecimal.ZERO) != 0) {
            // 約定基準残高 * 平均単価(上記で取得) / 100 * 参考為替
            // 計算結果の小数点以下切り捨て
            BigDecimal valuation = position.multiply(averagePrice);
            valuation = valuation.divide(BigDecimal.valueOf(100));
            valuation = valuation.multiply(new BigDecimal(accountSumWebData.getStandardRate()));
            valuation = valuation.setScale(0, RoundingMode.DOWN);
            responseDto.setValuation(valuation.toPlainString());
        }

        return responseDto;
    }

    /**
     * 保有商品一覧_投資信託リスト編集(国内投信(SBIラップ投信))
     * 
     * @param record
     * @return 保有商品一覧_投資信託リスト(国内投信(SBIラップ投信))
     * @throws Exception
     */
    private IfaPortfolioA001HoldingSecurityListMutualFundResponseDto expandSbiRapMutualFund(IfaPortfolioSql019ResponseModel record) throws Exception {
        IfaPortfolioA001HoldingSecurityListMutualFundResponseDto responseDto = new IfaPortfolioA001HoldingSecurityListMutualFundResponseDto();

        BeanUtils.copyProperties(responseDto, record);

        // 商品タイプ名
        responseDto.setSecTypeName(SBIRAP_MUTUAL_FUND);

        return responseDto;
    }

    /**
     * 保有商品一覧_STリスト編集
     * 
     * @param record
     * @return 保有商品一覧_STリスト
     * @throws Exception
     */
    private IfaPortfolioA001HoldingSecurityListSecurityTokenResponseDto expandSecurityToken(IfaPortfolioSql019ResponseModel record) throws Exception { 
        IfaPortfolioA001HoldingSecurityListSecurityTokenResponseDto responseDto = new IfaPortfolioA001HoldingSecurityListSecurityTokenResponseDto();

        BeanUtils.copyProperties(responseDto, record);
        return responseDto;
    }

    /**
     * 保有商品一覧_外国株式リスト編集
     * 
     * @param securitiesBalances
     * @return 保有商品一覧_外国株式リスト
     * @throws Exception
     */
    private IfaPortfolioA001HoldingSecurityListForeignStockResponseDto expandForeignStock(SecuritiesBalances securitiesBalances) throws Exception {
        IfaPortfolioA001HoldingSecurityListForeignStockResponseDto responseDto = new IfaPortfolioA001HoldingSecurityListForeignStockResponseDto();

        if (!ObjectUtils.isEmpty(securitiesBalances.getSecurities())) {
            // 銘柄コード
            responseDto.setBrandCode(securitiesBalances.getSecurities().getSecuritiesCode());
            // 銘柄名
            responseDto.setBrandName(securitiesBalances.getSecurities().getSecuritiesName());
        }

        // 預り区分
        responseDto.setDepositType(securitiesBalances.getSpecificAccountCode());
        // 約定基準残高
        responseDto.setContractStandardDeposit(securitiesBalances.getSecuritiesQuantity());
        // 通貨
        responseDto.setCurrency(securitiesBalances.getCurrencyCode());
        // 取得単価
        responseDto.setOpenPrice(securitiesBalances.getFrnAcquisitionPrice());

        if (!ObjectUtils.isEmpty(securitiesBalances.getStockPrice())) {
            // 時価（現地通貨）
            responseDto.setMarketValueForeign(securitiesBalances.getStockPrice().getLastToPrevClose());
        }

        if (!ObjectUtils.isEmpty(securitiesBalances.getEvaluationProfitLoss())) {
            // 為替レート
            responseDto.setFxRate(securitiesBalances.getEvaluationProfitLoss().getEvaluationExchangeRate());
            // 評価額（円貨）
            responseDto.setValuation(securitiesBalances.getEvaluationProfitLoss().getEvaluationAmount());
            // 評価損益
            responseDto.setProfitAndLoss(securitiesBalances.getEvaluationProfitLoss().getEvaluationProfitLoss());
        }

        return responseDto;
    }

    /**
     * 保有商品一覧_外貨建MMFリスト編集
     * 
     * @param securitiesBalances
     * @return 保有商品一覧_外貨建MMFリスト
     * @throws Exception
     */
    private IfaPortfolioA001HoldingSecurityListForeignMmfResponseDto expandFpreignMmf(SecuritiesBalances securitiesBalances) throws Exception {
        IfaPortfolioA001HoldingSecurityListForeignMmfResponseDto responseDto = new IfaPortfolioA001HoldingSecurityListForeignMmfResponseDto();

        if (!ObjectUtils.isEmpty(securitiesBalances.getSecurities())) {
            // 銘柄コード
            responseDto.setBrandCode(securitiesBalances.getSecurities().getSecuritiesCode());
            // 銘柄名
            responseDto.setBrandName(securitiesBalances.getSecurities().getSecuritiesName());
        }

        // 約定基準残高
        responseDto.setContractStandardDeposit(securitiesBalances.getSecuritiesQuantity());
        // 通貨
        responseDto.setCurrency(securitiesBalances.getCurrencyCode());

        if (!ObjectUtils.isEmpty(securitiesBalances.getEvaluationProfitLoss())) {
            // 為替レート
            responseDto.setFxRate(securitiesBalances.getEvaluationProfitLoss().getEvaluationExchangeRate());
            // 評価額（円貨）
            responseDto.setValuation(securitiesBalances.getEvaluationProfitLoss().getEvaluationAmount());
            // 評価損益
            responseDto.setProfitAndLoss(securitiesBalances.getEvaluationProfitLoss().getEvaluationProfitLoss());
        }

        return responseDto;
    }

    /**
     * 保有商品一覧_外国債券(外貨建)リスト編集
     * 
     * @param securitiesBalances
     * @param record
     * @return 保有商品一覧_外国債券(外貨建)リスト
     * @throws Exception
     */
    private IfaPortfolioA001HoldingSecurityListForeignClaimForeignResponseDto expandForeignClaimForeign(SecuritiesBalances securitiesBalances, IfaPortfolioSql023ResponseModel record) throws Exception {
        IfaPortfolioA001HoldingSecurityListForeignClaimForeignResponseDto responseDto = new IfaPortfolioA001HoldingSecurityListForeignClaimForeignResponseDto();

        if (!ObjectUtils.isEmpty(securitiesBalances.getSecurities())) {
            // 銘柄コード
            responseDto.setBrandCode(securitiesBalances.getSecurities().getSecuritiesCode());
            // 銘柄名
            responseDto.setBrandName(securitiesBalances.getSecurities().getSecuritiesName());
        }

        // 預り区分
        responseDto.setDepositType(securitiesBalances.getSpecificAccountCode());

        if (record != null) {
            // 利率
            responseDto.setCompoundInterest(record.getCompoundInterest());
            // 償還日
            responseDto.setRedemptionDate(record.getRedemptionDate());
            // 利払日
            responseDto.setInterestPaymentDate(getClaimInterestPaymentDate(record));
        }

        // 約定基準残高
        responseDto.setContractStandardDeposit(securitiesBalances.getSecuritiesQuantity());
        // 取得単価
        responseDto.setOpenPrice(securitiesBalances.getFrnAcquisitionPrice());
        // 通貨コード
        responseDto.setCurrency(securitiesBalances.getCurrencyCode());

        if (!ObjectUtils.isEmpty(securitiesBalances.getEvaluationProfitLoss())) {
            // 為替レート
            responseDto.setFxRate(securitiesBalances.getEvaluationProfitLoss().getEvaluationExchangeRate());
            // 評価額（円貨）
            responseDto.setValuation(securitiesBalances.getEvaluationProfitLoss().getEvaluationAmount());
        }

        return responseDto;
    }

    /**
     * 保有商品一覧_外国債券(外貨建仕組債)リスト編集
     * 
     * @param securitiesBalances
     * @param record
     * @return 保有商品一覧_外国債券(外貨建仕組債)リスト
     * @throws Exception
     */
    private IfaPortfolioA001HoldingSecurityListForeignClaimForeignStructuredBondResponseDto expandForeignClaimForeignStructured(SecuritiesBalances securitiesBalances, IfaPortfolioSql023ResponseModel record) throws Exception {
        IfaPortfolioA001HoldingSecurityListForeignClaimForeignStructuredBondResponseDto responseDto = new IfaPortfolioA001HoldingSecurityListForeignClaimForeignStructuredBondResponseDto();

        if (!ObjectUtils.isEmpty(securitiesBalances.getSecurities())) {
            // 銘柄コード
            responseDto.setBrandCode(securitiesBalances.getSecurities().getSecuritiesCode());
            // 銘柄名
            responseDto.setBrandName(securitiesBalances.getSecurities().getSecuritiesName());
        }
        // 預り区分
        responseDto.setDepositType(securitiesBalances.getSpecificAccountCode());
        // 利率
        responseDto.setCompoundInterest(record.getCompoundInterest());
        // 償還日
        responseDto.setRedemptionDate(record.getRedemptionDate());
        // 利払日
        responseDto.setInterestPaymentDate(getClaimInterestPaymentDate(record));
        // 約定基準残高
        responseDto.setContractStandardDeposit(securitiesBalances.getSecuritiesQuantity());
        // 取得単価
        responseDto.setOpenPrice(securitiesBalances.getFrnAcquisitionPrice());
        // 通貨コード
        responseDto.setCurrency(securitiesBalances.getCurrencyCode());

        if (!ObjectUtils.isEmpty(securitiesBalances.getEvaluationProfitLoss())) {
            // 為替レート
            responseDto.setFxRate(securitiesBalances.getEvaluationProfitLoss().getEvaluationExchangeRate());
            // 評価額（円貨）
            responseDto.setValuation(securitiesBalances.getEvaluationProfitLoss().getEvaluationAmount());
        }

        return responseDto;
    }

    /**
     * 保有商品一覧_現金リスト編集
     * 
     * @param apiResponse
     * @param valuation
     * @return 保有商品一覧_現金リスト
     * @throws Exception
     */
    private IfaPortfolioA001HoldingSecurityListCashResponseDto expandCashJrNisaUnAccount(QueryAccountBalanceOutData apiResponse, String valuation) throws Exception {
        IfaPortfolioA001HoldingSecurityListCashResponseDto responseDto = new IfaPortfolioA001HoldingSecurityListCashResponseDto();

        // 名称
        responseDto.setName(CASH_ACCOUNT_NAME);
        // 評価額合計
        responseDto.setValuationTotal(valuation);

        return responseDto;
    }

    /**
     * 保有商品一覧_スイープ専用銀行口座リスト編集
     * 
     * @param apiResponse
     * @return 保有商品一覧_スイープ専用銀行口座リスト
     * @throws Exception
     */
    private IfaPortfolioA001HoldingSecurityListSweepAccountResponseDto expandSweepAccount(QueryAccountBalanceOutData apiResponse) throws Exception {
        IfaPortfolioA001HoldingSecurityListSweepAccountResponseDto responseDto = new IfaPortfolioA001HoldingSecurityListSweepAccountResponseDto();

        // 名称
        responseDto.setName(SWEEP_ACCOUNT_NAME);
        // 評価額合計
        responseDto.setValuationTotal(calcValuationTotalSweep(apiResponse));

        return responseDto;
    }

    /**
     * 保有商品一覧_SBIラップ口座現金リスト編集
     * 
     * @param record
     * @return 保有商品一覧_SBIラップ口座現金リスト
     * @throws Exception
     */
    private IfaPortfolioA001HoldingSecurityListSbiRapAccountCashResponseDto expandSbiRapAccountCash(IfaPortfolioSql017ResponseModel record) throws Exception {
        IfaPortfolioA001HoldingSecurityListSbiRapAccountCashResponseDto responseDto = new IfaPortfolioA001HoldingSecurityListSbiRapAccountCashResponseDto();

        // 証券種別コード（商品分類コード）
        responseDto.setSecurityClassCode(SBIRAP_ACCOUNT_CASH_NAME);
        // 評価額（円貨）
        responseDto.setValuation(record.getValuation());

        return responseDto;
    }

    /**
     * 保有商品一覧_外貨預金リスト編集
     * 
     * @param cashDeposit
     * @param foreignValuation
     * @return 保有商品一覧_外貨預金リスト
     * @throws Exception
     */
    private IfaPortfolioA001HoldingSecurityListForeignDepositResponseDto expandForeignDeposit(CashDeposit cashDeposit, String foreignValuation) throws Exception {
        IfaPortfolioA001HoldingSecurityListForeignDepositResponseDto responseDto = new IfaPortfolioA001HoldingSecurityListForeignDepositResponseDto();

        String currencyCode = cashDeposit.getCurrencyCode();

        // 通貨
        responseDto.setCurrency(currencyCode);
        // 為替レート
        responseDto.setFxRate(cashDeposit.getExchangeRate());
        // 評価額（外貨）
        responseDto.setForeignValuation(foreignValuation);
        // 評価額（円貨）
        if (!ObjectUtils.isEmpty(cashDeposit.getExchangeRate())) {
            BigDecimal exchangeRate = new BigDecimal(cashDeposit.getExchangeRate());
            if (foreignValuation != null && exchangeRate != null) {
                BigDecimal valueation = new BigDecimal(foreignValuation).multiply(exchangeRate).setScale(0, RoundingMode.DOWN);
                responseDto.setValuation(valueation.toPlainString());
            }
        }

        return responseDto;
    }

    /**
     * 通貨コード, 評価額(外貨)を展開する
     * 
     * @param api008ResList
     * @return
     */
    private Map<String, String> expandForeignValuation(List<ListForeignScheduleCashBalancesResp> api008ResList) {
        Map<String, String> result = Collections.synchronizedMap(new TreeMap<String, String>());

        Map<String, BigDecimal> generalMap = Collections.synchronizedMap(new TreeMap<String, BigDecimal>());
        Map<String, BigDecimal> jrNisaMap = Collections.synchronizedMap(new TreeMap<String, BigDecimal>());

        for (ListForeignScheduleCashBalancesResp api008Res : api008ResList) {
            for (ForeignCashBalance fcb : api008Res.getForeignCashBalances()) {
                // 口座分類
                if (Strings.CS.equals(fcb.getAccountKind(), ForeignAccountKind.GENERAL.getValue())) {
                    for (CurrencyCashBalance ccb : fcb.getCurrencyCashBalances()) {
                        // 通貨コード
                        generalMap.put(ccb.getCurrencyCode(), calcForeignValuation(ccb));
                    }
                } else {
                    // ジュニアNISA
                    for (CurrencyCashBalance ccb : fcb.getCurrencyCashBalances()) {
                        jrNisaMap.put(ccb.getCurrencyCode(), calcForeignValuation(ccb));
                    }
                }
            }
        }

        // 通貨コード, 評価額(外貨)のマップを作成する
        for (Map.Entry<String, BigDecimal> entry : generalMap.entrySet()) {
            String currencyCode = entry.getKey();
            BigDecimal value = entry.getValue();

            if (jrNisaMap.containsKey(currencyCode)) {
                // JR_NISAが存在する場合、評価額(外貨)を合算する
                value = value.add(jrNisaMap.get(currencyCode));
            }
            result.put(currencyCode, value.toString());
        }

        return result;
    }

    /**
     * 口座分類の評価額(外貨)を算出する
     * @param ccb
     * @return
     */
    private BigDecimal calcForeignValuation(CurrencyCashBalance ccb) {
        // 残高
        BigDecimal remainingBuyPossibleAmount = BigDecimal.ZERO;
        if (!ObjectUtils.isEmpty(new BigDecimal(ccb.getForeignScheduleCashBalances().get(0).getRemainingBuyPossibleAmount()))) {
            remainingBuyPossibleAmount = new BigDecimal(ccb.getForeignScheduleCashBalances().get(0).getRemainingBuyPossibleAmount());
        }
        BigDecimal cashReceipt = BigDecimal.ZERO;
        BigDecimal receiveAmountValue = BigDecimal.ZERO;
        BigDecimal fixedDayTradeAmount = BigDecimal.ZERO;
        BigDecimal transferEstimatedMarginAmount = BigDecimal.ZERO;
        BigDecimal paymentAmount = BigDecimal.ZERO;
        BigDecimal amountPayValue = BigDecimal.ZERO;

        for (int i = 0; i < UPPER_DAYS - 1; i++) {
            // 入金額
            if (!ObjectUtils.isEmpty(new BigDecimal(ccb.getForeignScheduleCashBalances().get(i + 1).getCashReceipt()))) {
                cashReceipt = cashReceipt.add(new BigDecimal(ccb.getForeignScheduleCashBalances().get(i + 1).getCashReceipt()));
            }
            // 受取額
            if (!ObjectUtils.isEmpty(new BigDecimal(ccb.getForeignScheduleCashBalances().get(i + 1).getReceiveAmountValue()))) {
                receiveAmountValue = receiveAmountValue.add(new BigDecimal(ccb.getForeignScheduleCashBalances().get(i + 1).getReceiveAmountValue()));
            }
            // 受取額(日計り分)
            if (!ObjectUtils.isEmpty(new BigDecimal(ccb.getForeignScheduleCashBalances().get(i + 1).getFixedDayTradeAmount()))) {
                fixedDayTradeAmount = fixedDayTradeAmount.add(new BigDecimal(ccb.getForeignScheduleCashBalances().get(i + 1).getFixedDayTradeAmount()));
            }
            // 振替予定額(信用口座→現物口座)
            if (!ObjectUtils.isEmpty(new BigDecimal(ccb.getForeignScheduleCashBalances().get(i + 1).getTransferEstimatedMarginAmount()))) {
                transferEstimatedMarginAmount = transferEstimatedMarginAmount.add(new BigDecimal(ccb.getForeignScheduleCashBalances().get(i + 1).getTransferEstimatedMarginAmount()));
            }
            // 支払額
            if (!ObjectUtils.isEmpty(new BigDecimal(ccb.getForeignScheduleCashBalances().get(i + 1).getPaymentAmount()))) {
                paymentAmount = paymentAmount.add(new BigDecimal(ccb.getForeignScheduleCashBalances().get(i + 1).getPaymentAmount()));
            }
            // 出金額
            if (!ObjectUtils.isEmpty(new BigDecimal(ccb.getForeignScheduleCashBalances().get(i + 1).getAmountPayValue()))) {
                amountPayValue = amountPayValue.add(new BigDecimal(ccb.getForeignScheduleCashBalances().get(i + 1).getAmountPayValue()));
            }
        } 

        BigDecimal value = remainingBuyPossibleAmount.add(cashReceipt).add(receiveAmountValue).add(fixedDayTradeAmount).add(transferEstimatedMarginAmount).add(paymentAmount).add(amountPayValue);

        return value;
    }

    /**
     * 維持率（国内信用）編集
     * 
     * @param apiResponse
     * @return
     * @throws Exception
     */
    private String expandDomesticMarginActualGrntRate(QueryMgEstCapabilityWebHtsOutData apiResponse) throws Exception {
        AutoSweepKbn autoSweepKbn = AutoSweepKbn.getInstanceByCompleted(apiResponse.getAutoSweepKbn());
        List<QueryMgEstCapabilityWebSettlementDateT> settlementList = apiResponse.getSettlementDateT();
        QueryMgEstCapabilityWebSettlementDateT settlement = settlementList.get(0);

        BigDecimal value = BigDecimal.ZERO;

        if (autoSweepKbn == null) {
            // 預り金自動スイープ未開設
            value = StringUtil.parseBigDecimal(settlement.getActualGrntRate());
        } else {
            value = StringUtil.parseBigDecimal(settlement.getEtKeepRate());
        }

        return value.divide(BigDecimal.valueOf(100), 2, RoundingMode.DOWN).toString();
    }

    /**
     * 保有商品一覧_信用建玉リスト編集
     * 
     * @param vec
     * @param sql025Map
     * @param fct020Map
     * @return 保有商品一覧_信用建玉リスト
     * @throws Exception
     */
    private IfaPortfolioA001HoldingSecurityListMarginPositionResponseDto expandDomesticMarginPosition(QueryMarginContract0OutVec vec, Map<String, DataList<IfaPortfolioSql025ResponseModel>> sql025Map, Map<String, String> fct020Map) throws Exception {
        IfaPortfolioA001HoldingSecurityListMarginPositionResponseDto responseDto = new IfaPortfolioA001HoldingSecurityListMarginPositionResponseDto();

        DataList<IfaPortfolioSql025ResponseModel> sql025ResList = new DataList<IfaPortfolioSql025ResponseModel>();

        String brandCode = vec.getBrandCd();

        if (sql025Map != null && sql025Map.containsKey(brandCode)) {
            sql025ResList = (DataList<IfaPortfolioSql025ResponseModel>)sql025Map.get(brandCode);
        }

        BigDecimal currentValueForEvaluation = null;
        if (fct020Map != null && fct020Map.containsKey(brandCode)) {
            if (!StringUtil.isNullOrEmpty((String)fct020Map.get(brandCode))) {
                currentValueForEvaluation = new BigDecimal((String)fct020Map.get(brandCode));
            }
        }

        // 銘柄コード
        responseDto.setBrandCode(brandCode);
        // 銘柄名
        String brandNameValue = null;
        if (sql025ResList != null && sql025ResList.size() > 0) {
            brandNameValue = sql025ResList.get(0).getBrandName();
        }
        responseDto.setBrandName(brandNameValue);
        // 取引種別名 
        String tradTypeNameValue = codelistservice.getValue(POSITION_SELL_BUY_TYPE, vec.getOpenTradeKbn());
        responseDto.setTradeTypeName(tradTypeNameValue);
        // 指定扱区分
        responseDto.setDesignationDealtClassification(vec.getPaymentLimit());
        // 市場
        String marketValue = codelistservice.getValue(NEW_MARKET, vec.getOpenMarket());
        responseDto.setMarket(marketValue);
        // 返済期限
        int bargainNumber = Integer.parseInt(vec.getBargainNumber());
        if (bargainNumber == 1) {
            String lastTradeDateValue = "";
            if (Strings.CS.equals(vec.getLastTradeDate(), FINALDATE)) {
                lastTradeDateValue = UNLIMITED;
            } else {
                lastTradeDateValue = vec.getLastTradeDate();
            }
            responseDto.setLastTradeDate(lastTradeDateValue);
        } else if (bargainNumber > 1) {
            responseDto.setLastTradeDate(INVALID_DATE_DASH);
        }
        // 約定基準残高
        BigDecimal position = ObjectUtils.isEmpty(vec.getContPositionTotal()) ? null : new BigDecimal(vec.getContPositionTotal());
        responseDto.setContractStandardDeposit(vec.getContPositionTotal());
        // 取得単価
        if (bargainNumber == 1) {
            BigDecimal openPrice = ObjectUtils.isEmpty(vec.getOpenPrice()) ? null :new BigDecimal(vec.getOpenPrice());
            if (openPrice != null) {
                String openPriceValue = openPrice.divide(BigDecimal.valueOf(100)).toPlainString();
                responseDto.setOpenPrice(openPriceValue);
            }
        }

        String profitAndLossValue = null;
        if (currentValueForEvaluation != null) {
            // 時価
            responseDto.setPrice(currentValueForEvaluation.setScale(2, RoundingMode.DOWN).toPlainString());

            // 評価額（円貨）
            BigDecimal valueation = currentValueForEvaluation.multiply(position);
            String valuationValue = valueation.setScale(2, RoundingMode.DOWN).toPlainString();
            responseDto.setValuation(valuationValue);

            // 評価損益
            // 建代金
            BigDecimal openAmmount = ObjectUtils.isEmpty(vec.getOpenAmount()) ? null : new BigDecimal(vec.getOpenAmount());
            // 諸経費
            BigDecimal cost = ObjectUtils.isEmpty(vec.getCost()) ? null : new BigDecimal(vec.getCost());
            if (Strings.CS.equals(vec.getOpenTradeKbn(), "0")) {
                // 繰返部.新規売買区分 = '0'(買建)の場合
                profitAndLossValue = valueation.subtract(openAmmount).subtract(cost).setScale(2, RoundingMode.DOWN).toPlainString();
            } else {
                // 繰返部.新規売買区分 = '1'(売建)の場合
                profitAndLossValue = openAmmount.subtract(valueation).subtract(cost).setScale(2, RoundingMode.DOWN).toPlainString();
            }
            responseDto.setProfitAndLoss(profitAndLossValue);
        }
  
        return responseDto;
    }

    /**
     * 維持率（米株信用）
     * 
     * @param apiResponse
     * @return 預託率
     * @throws Exception
     */
    private String expandUSMarginActualGrntRate(GetMarginPowerHeadlineResp apiResponse) throws Exception {
        return apiResponse.getDepositRate();
    }

    /**
     * 保有商品一覧_米株信用建玉リスト編集
     * 
     * @param position
     * @return 保有商品一覧_米株信用建玉リスト
     * @throws Exception
     */
    private IfaPortfolioA001HoldingSecurityListUsStockMarginPositionResponseDto expandUSStockMarginPosition(Position position) throws Exception {
        IfaPortfolioA001HoldingSecurityListUsStockMarginPositionResponseDto responseDto = new IfaPortfolioA001HoldingSecurityListUsStockMarginPositionResponseDto();
        
        if (!ObjectUtils.isEmpty(position.getSecurities())) {
            // 銘柄コード
            responseDto.setBrandCode(position.getSecurities().getSecuritiesCode());
            // 銘柄名
            responseDto.setBrandName(position.getSecurities().getSecuritiesName());
        }

        // 取引種別名
        String tradTypeNameValue = codelistservice.getValue(POSITION_SELL_BUY_TYPE, position.getBuySellCode());
        responseDto.setTradeTypeName(tradTypeNameValue);
        // 指定扱区分
        responseDto.setDesignationDealtClassification(position.getMarginCloseLimitType());
        // 返済期限
        if (!ObjectUtils.isEmpty(position.getFrnCloseLimitDate())) {
            String lastTradeDateValue = INVALID_DATE_DASH;
            if (!Strings.CS.equals(position.getFrnCloseLimitDate(), INVALID_DATE)) {
                lastTradeDateValue = position.getFrnCloseLimitDate().replace("-", "");
            }
            responseDto.setLastTradeDate(lastTradeDateValue);
        }

        // 約定基準残高
        responseDto.setContractStandardDeposit(position.getQuantity());
        // 通貨 定数
        responseDto.setCurrency(CURRENCY_CODE_US);
        // 取得単価 
        responseDto.setOpenPrice(position.getFrnPositionPrice());

        // 時価
        if (!ObjectUtils.isEmpty(position.getPriceData())) {
            responseDto.setPrice(position.getPriceData().getLastToPrevClose());
        }

        if (!ObjectUtils.isEmpty(position.getEvaluationProfitLoss())) {
            // 為替レート
            responseDto.setFxRate(position.getEvaluationProfitLoss().getEvaluationExchangeRate());
            // 評価額（円貨）
            responseDto.setValuation(position.getEvaluationProfitLoss().getEvaluationAmount());
            // 評価損益
            responseDto.setProfitAndLoss(position.getEvaluationProfitLoss().getEvaluationProfitLoss());
        }

        return responseDto;
    }

    /**
     * 保有商品一覧_投資信託トータルリターンリスト編集
     * 
     * @param record
     * @return 保有商品一覧_投資信託トータルリターンリスト
     * @throws Exception
     */
    private IfaPortfolioA001HoldingSecurityListMutualFundTotalReturnResponseDto expandMutualFundTotalReturn(IfaPortfolioSql018ResponseModel record) throws Exception {
        IfaPortfolioA001HoldingSecurityListMutualFundTotalReturnResponseDto responseDto = new IfaPortfolioA001HoldingSecurityListMutualFundTotalReturnResponseDto();

        // 共通の項目をコピー
        BeanUtils.copyProperties(responseDto, record);

        // NRIコード
        if (record.getNriCd() != null) {
            String nriCd = record.getNriCd();
            String convertNriCd = nriCd.substring(0, 4) + "." + nriCd.substring(5).trim();
            responseDto.setNriCd(convertNriCd);
        }

        // トータルリターン（率）
        // トータルリターン（率）の小数第3位を切り捨てて、格納値の正負に関わらず絶対値の方向に切り捨てる
        if (record.getTotalReturnRate() != null) {
            BigDecimal totalReturnRateScaled = record.getTotalReturnRate().setScale(2, RoundingMode.DOWN);
            responseDto.setTotalReturnRate(totalReturnRateScaled.toPlainString());
        }

        return responseDto;
    }

    /**
     * 評価額合計の算出(現金)
     * 
     * @param apiResponse
     * @return
     */
    private String calcValuationTotalCash(QueryAccountBalanceOutData apiResponse) {
        CustomerCommon cc = IfaCommonUtil.getCustomerCommon();

        // ジュニアNISA口座未開設の場合
        // Ｔ日の合計額残高
        BigDecimal accountTotal = new BigDecimal(apiResponse.getTotalBalance());

        // 受渡日(T+0)～受渡日(T+5).支払い合計金額（買い）
        BigDecimal unsettledBuyTotal = new BigDecimal(apiResponse.getT0().getUnsettledBuyTotal())
                                        .add(new BigDecimal(apiResponse.getT1().getUnsettledBuyTotal()))
                                        .add(new BigDecimal(apiResponse.getT2().getUnsettledBuyTotal()))
                                        .add(new BigDecimal(apiResponse.getT3().getUnsettledBuyTotal())) 
                                        .add(new BigDecimal(apiResponse.getT4().getUnsettledBuyTotal())) 
                                        .add(new BigDecimal(apiResponse.getT5().getUnsettledBuyTotal())); 
        // 受渡日(T+0)～受渡日(T+5).受取り合計金額（売り） 
        BigDecimal unsettledSellTotal =  new BigDecimal(apiResponse.getT0().getUnsettledSellTotal())
                                        .add(new BigDecimal(apiResponse.getT1().getUnsettledSellTotal()))
                                        .add(new BigDecimal(apiResponse.getT2().getUnsettledSellTotal()))
                                        .add(new BigDecimal(apiResponse.getT3().getUnsettledSellTotal()))
                                        .add(new BigDecimal(apiResponse.getT4().getUnsettledSellTotal()))
                                        .add(new BigDecimal(apiResponse.getT5().getUnsettledSellTotal()));

        // 受渡日(T+0)～受渡日(T+5).出金指示合計金額
        BigDecimal cashPaymentTotal = new BigDecimal(apiResponse.getT0().getCashPaymentTotal())
                                        .add(new BigDecimal(apiResponse.getT1().getCashPaymentTotal()))
                                        .add(new BigDecimal(apiResponse.getT2().getCashPaymentTotal()))
                                        .add(new BigDecimal(apiResponse.getT3().getCashPaymentTotal()))
                                        .add(new BigDecimal(apiResponse.getT4().getCashPaymentTotal()))
                                        .add(new BigDecimal(apiResponse.getT5().getCashPaymentTotal()));
        // 受渡日(T+1)～受渡日(T+5).入金合計金額
        // ※ 受渡日(T+0).入金合計金額はAPI004.Ｔ日の合計額残高に含まれるため除外する。
        BigDecimal cashReceiptTotal = new BigDecimal(apiResponse.getT1().getCashReceiptTotal())
                                        .add(new BigDecimal(apiResponse.getT2().getCashReceiptTotal()))
                                        .add(new BigDecimal(apiResponse.getT3().getCashReceiptTotal()))
                                        .add(new BigDecimal(apiResponse.getT4().getCashReceiptTotal()))
                                        .add(new BigDecimal(apiResponse.getT5().getCashReceiptTotal()));

        BigDecimal value = accountTotal.subtract(unsettledBuyTotal).add(unsettledSellTotal).subtract(cashPaymentTotal).add(cashReceiptTotal);

        if (JrIsaContractType.getByCode(cc.getJrIsaContractType()) == JrIsaContractType.CONTRACTED) {
            // ジュニアNISA口座開設の場合
            // Ｔ日の合計額残高(JrNISA)
            BigDecimal accountTotalJrNisa = new BigDecimal(apiResponse.getTotalBalanceJrnisa());

            // 受渡日(T+0)(JrNISA)～受渡日(T+5)(JrNISA).支払い合計金額（買い）(JrNISA)
            BigDecimal unsettledBuyTotalJrNisa = new BigDecimal(apiResponse.getT0Jr().getUnsettledBuyTotalJrnisa())
                                                    .add(new BigDecimal(apiResponse.getT1Jr().getUnsettledBuyTotalJrnisa()))
                                                    .add(new BigDecimal(apiResponse.getT2Jr().getUnsettledBuyTotalJrnisa()))
                                                    .add(new BigDecimal(apiResponse.getT3Jr().getUnsettledBuyTotalJrnisa()))
                                                    .add(new BigDecimal(apiResponse.getT4Jr().getUnsettledBuyTotalJrnisa()))
                                                    .add(new BigDecimal(apiResponse.getT5Jr().getUnsettledBuyTotalJrnisa()));
            // 受渡日(T+0)(JrNISA)～受渡日(T+5)(JrNISA).受取り合計金額（売り）(JrNISA)
            BigDecimal unsettledSellTotalJrNisa = new BigDecimal(apiResponse.getT0Jr().getUnsettledSellTotalJrnisa())
                                                    .add(new BigDecimal(apiResponse.getT1Jr().getUnsettledSellTotalJrnisa()))
                                                    .add(new BigDecimal(apiResponse.getT2Jr().getUnsettledSellTotalJrnisa()))
                                                    .add(new BigDecimal(apiResponse.getT3Jr().getUnsettledSellTotalJrnisa()))
                                                    .add(new BigDecimal(apiResponse.getT4Jr().getUnsettledSellTotalJrnisa()))
                                                    .add(new BigDecimal(apiResponse.getT5Jr().getUnsettledSellTotalJrnisa()));
            // 受渡日(T+0)(JrNISA)～受渡日(T+5)(JrNISA).出金指示合計金額(JrNISA)
            BigDecimal cashPaymentTotalJrNisa = new BigDecimal(apiResponse.getT0Jr().getCashPaymentTotalJrnisa())
                                                    .add(new BigDecimal(apiResponse.getT1Jr().getCashPaymentTotalJrnisa()))
                                                    .add(new BigDecimal(apiResponse.getT2Jr().getCashPaymentTotalJrnisa()))
                                                    .add(new BigDecimal(apiResponse.getT3Jr().getCashPaymentTotalJrnisa()))
                                                    .add(new BigDecimal(apiResponse.getT4Jr().getCashPaymentTotalJrnisa()))
                                                    .add(new BigDecimal(apiResponse.getT5Jr().getCashPaymentTotalJrnisa()));
            // 受渡日(T+1)(JrNISA)～受渡日(T+5)(JrNISA).入金合計金額(JrNISA)
            // ※ 受渡日(T+0)(JrNISA).入金合計金額はAPI004.Ｔ日の合計額残高(JrNISA)に含まれるため除外する。
            BigDecimal cashReceiptTotalJrNisa = new BigDecimal(apiResponse.getT1Jr().getCashReceiptTotalJrnisa())
                                                    .add(new BigDecimal(apiResponse.getT2Jr().getCashReceiptTotalJrnisa()))
                                                    .add(new BigDecimal(apiResponse.getT3Jr().getCashReceiptTotalJrnisa()))
                                                    .add(new BigDecimal(apiResponse.getT4Jr().getCashReceiptTotalJrnisa()))
                                                    .add(new BigDecimal(apiResponse.getT5Jr().getCashReceiptTotalJrnisa()));
            // 受渡日(T+0)(JrNISA)～受渡日(T+5)(JrNISA).ジュニアNISA振替予定額
            BigDecimal transJrnisaAmount = new BigDecimal(apiResponse.getT0Jr().getTransJrnisaAmount())
                                                    .add(new BigDecimal(apiResponse.getT1Jr().getTransJrnisaAmount()))
                                                    .add(new BigDecimal(apiResponse.getT2Jr().getTransJrnisaAmount()))
                                                    .add(new BigDecimal(apiResponse.getT3Jr().getTransJrnisaAmount()))
                                                    .add(new BigDecimal(apiResponse.getT4Jr().getTransJrnisaAmount()))
                                                    .add(new BigDecimal(apiResponse.getT5Jr().getTransJrnisaAmount()));

            value = value.add(accountTotalJrNisa).subtract(unsettledBuyTotalJrNisa).add(unsettledSellTotalJrNisa).subtract(cashPaymentTotalJrNisa).add(cashReceiptTotalJrNisa).add(transJrnisaAmount);
        }
        
        return value.toPlainString();
    }

    /**
     * 評価額合計の算出(スイープ専用銀行口座)
     * 
     * @param apiResponse
     * @return
     */
    private String calcValuationTotalSweep(QueryAccountBalanceOutData apiResponse) {
        CustomerCommon cc = IfaCommonUtil.getCustomerCommon();
        
        BigDecimal value = null;

        // ジュニアNISA口座未開設の場合
        // 受渡日(T+0).残高(決済専用銀行口座)
        if (!ObjectUtils.isEmpty(apiResponse.getT0().getSettleBkBalance())) {
            value = new BigDecimal(apiResponse.getT0().getSettleBkBalance());
        }

        if (JrIsaContractType.getByCode(cc.getJrIsaContractType()) == JrIsaContractType.CONTRACTED) {
            // 顧客共通情報.ジュニアISA契約区分='1'(契約)の場合
            // ジュニアNISA口座開設の場合
            // 受渡日(T+0).残高(決済専用銀行口座) + 受渡日(T+0)(JrNISA).合計額残高(JrNISA)
            if (!ObjectUtils.isEmpty(apiResponse.getT0Jr().getSettleBkBalanceJrnisa())) {
                value = value == null ? BigDecimal.ZERO : value;
                value = value.add(new BigDecimal(apiResponse.getT0Jr().getSettleBkBalanceJrnisa()));
            }
        }

        return value == null ? null : value.toPlainString();
    }

    /**
     * 国内株式判定
     * 
     * @param accountSumWebData
     * @return
     */
    private boolean isDomesticStock(AccountSumWebData accountSumWebData) {
        boolean result = false;

        BigDecimal position = ObjectUtils.isEmpty(accountSumWebData.getPosition()) ? null : new BigDecimal(accountSumWebData.getPosition());
        if (position != null) {
            if (Strings.CS.equals(accountSumWebData.getSecTypeName(), SEC_TYPE_NAME_DOMESTIC_STOCK)
                    && Strings.CS.equals(accountSumWebData.getSecId(), SEC_ID_DOMESTIC_STOCK)
                    && position.compareTo(BigDecimal.ZERO) == 1) {
                // 商品タイプ名 '国内株式'
                // 商品区分  'K'(株式（国内/外国）)
                result = true;
            }
        }
        
        return result;
    }

    /**
     * 国内投信判定
     * 
     * @param accountSumWebData
     * @return
     */
    private boolean isDomesticMutualFund(AccountSumWebData accountSumWebData) {
        boolean result = false;
        
        BigDecimal position = ObjectUtils.isEmpty(accountSumWebData.getPosition()) ? null : new BigDecimal(accountSumWebData.getPosition());
        if (position != null) {
            if (Strings.CS.equals(accountSumWebData.getSecTypeName(), SEC_TYPE_NAME_DOMESTIC_MUTUAL_FUND)
                    && (Strings.CS.equals(accountSumWebData.getSecId(), SEC_ID_MUTUAL_FUND)
                            || Strings.CS.equals(accountSumWebData.getSecId(), SEC_ID_DOMESTIC_MUTUAL_FUND))
                    && position.compareTo(BigDecimal.ZERO) == 1) {
                result = true;
            }
        }
        
        return result;
    }

    /**
     * 外国投信判定
     * 
     * @param accountSumWebData
     * @return
     */
    private boolean isForeignMutualFund(AccountSumWebData accountSumWebData) {
        boolean result = false;

        result = Strings.CS.equals(accountSumWebData.getSecTypeName(), SEC_TYPE_NAME_FOREIGN_MUTUAL_FUND);

        return result;
    }

    /**
     * 国内債券判定
     * 
     * @param accountSumWebData
     * @return
     */
    private boolean isDomesticClaim(AccountSumWebData accountSumWebData) {
        boolean result = false;

        if (Strings.CS.equals(accountSumWebData.getBrandSearchCode1(), BRAND_SEARCH_CODE1_DOMESTIC_CLAIM)
                                && !Strings.CS.equals(accountSumWebData.getBrandSearchCode2(), BRAND_SEARCH_CODE2_DOMESTIC_CLAIM)) {
            result = true;
        }

        return result;
    }

    /**
     * 外国債券(円建)判定
     * 
     * @param accountSumWebData
     * @return
     */
    private boolean isForeignClaimYen(AccountSumWebData accountSumWebData) {
        boolean result = false;

        if (Strings.CS.equals(accountSumWebData.getSecId(), SEC_ID_FOREIGN_BONDS)
                && Strings.CS.equals(accountSumWebData.getBrandSearchCode1(), BRAND_SEARCH_CODE1_FOREIGN_BONDS)
                && !Strings.CS.equals(accountSumWebData.getBrandSearchCode3(), BRAND_SEARCH_CODE3_FOREIGN_BONDS)) {
            result = true;
        }

        return result;
    }

    /**
     * 国内投信(SBIラップ)判定
     * 
     * @param record
     * @return
     */
    private boolean isDomesticMutualFundSbiRap(IfaPortfolioSql019ResponseModel record) {
        boolean result = false;

        result = Strings.CS.equals(record.getSecurityClassCode(), SECURITY_CLASS_CODE_DOMESTIC_MUTUAL_FUND);

        return result;
    }

    /**
     * ST判定
     * 
     * @param record
     * @return
     */
    private boolean isSecurityToken(IfaPortfolioSql019ResponseModel record) {
        boolean result = false;

        result = Strings.CS.equals(record.getSecurityClassCode(), SECURITY_CLASS_CODE_SECURITY_TOKEN);

        return result;
    }

    /**
     * 現金判定
     * @param apiResponse
     * @return
     */
    private boolean isCash(QueryAccountBalanceOutData apiResponse) {
        CustomerCommon cc = IfaCommonUtil.getCustomerCommon();

        if (ObjectUtils.isEmpty(apiResponse.getT0()) || ObjectUtils.isEmpty(apiResponse.getT1()) || ObjectUtils.isEmpty(apiResponse.getT2()) || ObjectUtils.isEmpty(apiResponse.getT3()) || ObjectUtils.isEmpty(apiResponse.getT4()) || ObjectUtils.isEmpty(apiResponse.getT5())) {
            return false;     
        }

        if (JrIsaContractType.getByCode(cc.getJrIsaContractType()) == JrIsaContractType.CONTRACTED) {
            // 顧客共通情報.ジュニアISA契約区分='1'(契約)の場合
            if (ObjectUtils.isEmpty(apiResponse.getT0Jr()) || ObjectUtils.isEmpty(apiResponse.getT1Jr()) || ObjectUtils.isEmpty(apiResponse.getT2Jr()) || ObjectUtils.isEmpty(apiResponse.getT3Jr()) || ObjectUtils.isEmpty(apiResponse.getT4Jr()) || ObjectUtils.isEmpty(apiResponse.getT5Jr())) {
                return false;
            }
        }
        return true;
    }

    /**
     * スイープ専用銀行口座判定
     * 
     * @param apiResponse
     * @return
     */
    private boolean isSweepAccount(QueryAccountBalanceOutData apiResponse) {
        CustomerCommon cc = IfaCommonUtil.getCustomerCommon();
        boolean result = false;

        if (JrIsaContractType.getByCode(cc.getJrIsaContractType()) == JrIsaContractType.CONTRACTED) {
            // 顧客共通情報.ジュニアISA契約区分='1'(契約)の場合
            if (!(ObjectUtils.isEmpty(apiResponse.getT0().getSettleBkBalance())
                    && ObjectUtils.isEmpty(apiResponse.getT0Jr().getSettleBkBalanceJrnisa()))
                && (!Strings.CS.equals(apiResponse.getT0().getSettleBkBalance(), "0")
                    || !Strings.CS.equals(apiResponse.getT0Jr().getSettleBkBalanceJrnisa(), "0"))) {
                result = true;
            }
        } else {
            // 顧客共通情報.ジュニアISA契約区分=''(未契約)または'9'(閉鎖済)
            if (!ObjectUtils.isEmpty(apiResponse.getT0().getSettleBkBalance())
                    && !Strings.CS.equals(apiResponse.getT0().getSettleBkBalance(), "0")) {
                result = true;
            }
        }
        return result;
    }

    /**
     * 利払日（債券）
     * 
     * @param accountSumWebData
     * @return 利払日（債券）
     */
    private String getClaimInterestPaymentDate(AccountSumWebData accountSumWebData) {
        String result = "";

        String couponPayDate1 = accountSumWebData.getCouponPayDate1();
        String couponPayDate2 = accountSumWebData.getCouponPayDate2();
        String couponPayDate3 = accountSumWebData.getCouponPayDate3();
        String couponPayDate4 = accountSumWebData.getCouponPayDate4();
        String date1 = convertDate(couponPayDate1);
        String date2 = convertDate(couponPayDate2);
        String date3 = convertDate(couponPayDate3);
        String date4 = convertDate(couponPayDate4);

        String couponPayID = accountSumWebData.getCouponPayId();
        if (Strings.CS.equals(couponPayID, IntersetPaymentType.ANNUAL.getID())) {
            if (ObjectUtils.isEmpty(date1)) {
                return null;
            }
            result = String.format("%s%s", IntersetPaymentType.ANNUAL.getName(), date1);
        } else if (Strings.CS.equals(couponPayID, IntersetPaymentType.SEMI_ANNUAL.getID())) {
            if (ObjectUtils.isEmpty(date1) || ObjectUtils.isEmpty(date2)) {
                return null;
            }
            result = String.format("%s%s,%s", IntersetPaymentType.SEMI_ANNUAL.getName(), date1, date2);
        } else if (Strings.CS.equals(couponPayID, IntersetPaymentType.QUARTERLY.getID())) {
            if (ObjectUtils.isEmpty(date1) || ObjectUtils.isEmpty(date2) || ObjectUtils.isEmpty(date3) || ObjectUtils.isEmpty(date4)) {
                return null;
            }
            result = String.format("%s%s,%s,%s,%s", IntersetPaymentType.QUARTERLY.getName(), date1, date2, date3, date4);
        } else if (Strings.CS.equals(couponPayID, IntersetPaymentType.MONTHLY.getID())) {
            String day1 = couponPayDate1.substring(2, 4);
            String day2 = couponPayDate2.substring(2, 4);
            String day3 = couponPayDate3.substring(2, 4);
            String day4 = couponPayDate4.substring(2, 4);

            if (ObjectUtils.isEmpty(day1) || ObjectUtils.isEmpty(day2) || ObjectUtils.isEmpty(day3) || ObjectUtils.isEmpty(day4)) {
                return null;
            }

            if (Strings.CS.equals(day1, day2) && Strings.CS.equals(day1, day3) && Strings.CS.equals(day1, day4)) {
                if (Strings.CS.equals(day1, IntersetPaymentDateType.LAST_DAY.getID())) {
                    result = String.format("%s%s", IntersetPaymentType.MONTHLY.getName(), IntersetPaymentDateType.LAST_DAY.getName());
                } else {
                    result = String.format("%s%s日", IntersetPaymentType.MONTHLY.getName(), day1);
                }
            } else {
                result = IntersetPaymentType.MONTHLY.getName();
            }
        }

        return result;
    }

    /**
     * 利払日（外国債券）
     * 
     * @param record
     * @return
     */
    private String getClaimInterestPaymentDate(IfaPortfolioSql023ResponseModel record) {
        String result = "";

        String couponPayDate1 = record.getInterestPaymentDate1();
        String couponPayDate2 = record.getInterestPaymentDate2();
        String couponPayDate3 = record.getInterestPaymentDate3();
        String couponPayDate4 = record.getInterestPaymentDate4();
        String date1 = convertDate(couponPayDate1);
        String date2 = convertDate(couponPayDate2);
        String date3 = convertDate(couponPayDate3);
        String date4 = convertDate(couponPayDate4);

        if (Strings.CS.equals(record.getInterestPaymentKbn(), IntersetPaymentType.DISCOUNT.getValue())) {
            // (割引債)の場合
            result = IntersetPaymentType.DISCOUNT.getName();
        } else if(Strings.CS.equals(record.getInterestPaymentKbn(), IntersetPaymentType.ANNUAL.getValue())) {
            if (ObjectUtils.isEmpty(date1)) {
                return null;
            }
            result = String.format("%s%s", IntersetPaymentType.ANNUAL.getName(), date1);
        } else if(Strings.CS.equals(record.getInterestPaymentKbn(), IntersetPaymentType.SEMI_ANNUAL.getValue())) {
            if (ObjectUtils.isEmpty(date1) || ObjectUtils.isEmpty(date2)) {
                return null;
            }
            result = String.format("%s%s,%s", IntersetPaymentType.SEMI_ANNUAL.getName(), date1, date2);
        } else if(Strings.CS.equals(record.getInterestPaymentKbn(), IntersetPaymentType.QUARTERLY.getValue())) {
            if (ObjectUtils.isEmpty(date1) || ObjectUtils.isEmpty(date2) || ObjectUtils.isEmpty(date3) || ObjectUtils.isEmpty(date4)) {
                return null;
            }
            result = String.format("%s%s,%s,%s,%s", IntersetPaymentType.QUARTERLY.getName(), date1, date2, date3, date4);
        } else if(Strings.CS.equals(record.getInterestPaymentKbn(), IntersetPaymentType.MONTHLY.getValue())) {
            String day1 = record.getInterestPaymentDay1();
            String day2 = record.getInterestPaymentDay2();
            String day3 = record.getInterestPaymentDay3();
            String day4 = record.getInterestPaymentDay4();

            if (ObjectUtils.isEmpty(day1) || ObjectUtils.isEmpty(day2) || ObjectUtils.isEmpty(day3) || ObjectUtils.isEmpty(day4)) {
                return null;
            }

            if (Strings.CS.equals(day1, day2) && Strings.CS.equals(day1, day3) && Strings.CS.equals(day1, day4)) {
                if (Strings.CS.equals(day1, IntersetPaymentDateType.LAST_DAY.getID())) {
                    result = String.format("%s%s", IntersetPaymentType.MONTHLY.getName(), IntersetPaymentDateType.LAST_DAY.getName());
                } else {
                    result = String.format("%s%s日", IntersetPaymentType.MONTHLY.getName(), day1);
                }
            } else {
                result = IntersetPaymentType.MONTHLY.getName();
            }            
        }
        return result;
    }

    /**
     * 日付文字列の変換
     * 
     * @param date
     * @return "MMDD"を"MM/DD"、または"99"を"末日"、引数がnull or 空白の場合は null
     */
    private String convertDate(String date) {
        if (ObjectUtils.isEmpty(date)) {
            return null;
        }
    
        String month = date.substring(0, 2);
        String day = date.substring(2,4);
        if (Strings.CS.equals(day, IntersetPaymentDateType.LAST_DAY.getID())) {
            day = IntersetPaymentDateType.LAST_DAY.getName();
        }
        String result = String.format("%s/%s", month, day);

        return result;
    }

    /**
     * ⑥取得した商品ごとの評価額合計と評価損益合計およびサマリーを算出する
     * 
     * @param responseData
     * @oaram futuresOpValuation
     * @throws Exception
     */
    private void summarizeSecuriteiesBalances(IfaPortfolioA001ResponseDto responseData, String futuresOpValuation) throws Exception {

        Map<ProductType, IfaPortfolioA001PortfolioSummaryResponseDto> portfolioSummaryMap = Collections.synchronizedMap(new TreeMap<ProductType, IfaPortfolioA001PortfolioSummaryResponseDto>());

        // 国内株式の集計
        IfaPortfolioA001PortfolioSummaryResponseDto portfolioSummary;
        portfolioSummary = summarizeDomesticStock(responseData);
        if (portfolioSummary != null) {
            portfolioSummaryMap.put(ProductType.DOMESTIC_STOCK, portfolioSummary);
        }
        // 国内債券の集計
        portfolioSummary = summarizeDomesticClaim(responseData);
        if (portfolioSummary != null) {
            portfolioSummaryMap.put(ProductType.DOMESTIC_CLAIM, portfolioSummary);
        }
        // 投資信託の集計
        portfolioSummary = summarizeMutualFund(responseData);
        if (portfolioSummary != null) {
            portfolioSummaryMap.put(ProductType.MUTUAL_FUND, portfolioSummary);
        }
        // 外国債券(円建)の集計
        portfolioSummary = summarizeForeignClaimYenBase(responseData);
        if (portfolioSummary != null) {
            portfolioSummaryMap.put(ProductType.FOREIGN_CLAIM_YEN_BASE, portfolioSummary);
        }
        // 外国株式の集計
        portfolioSummary = summarizeForeignStock(responseData);
        if (portfolioSummary != null) {
            portfolioSummaryMap.put(ProductType.FOREIGN_STOCK, portfolioSummary);
        }
        // 外貨建MMFの集計
        portfolioSummary = summarizeForeignMmf(responseData);
        if (portfolioSummary != null) {
            portfolioSummaryMap.put(ProductType.FOREIGN_MMF, portfolioSummary);
        }
        // 外国債券(外貨建)
        portfolioSummary = summarizeForeignClaimForeign(responseData);
        if (portfolioSummary != null) {
            portfolioSummaryMap.put(ProductType.FOREIGN_CLAIM_FOREIGN, portfolioSummary);
        }
        // ST
        portfolioSummary = summarizeSecurityToken(responseData);
        if (portfolioSummary != null) {
            portfolioSummaryMap.put(ProductType.SECURITY_TOKEN, portfolioSummary);
        }
        // 現金(円貨)の集計
        portfolioSummary = summarizeCash(responseData);
        if (portfolioSummary != null) {
            portfolioSummaryMap.put(ProductType.CASH, portfolioSummary);
        }
        // 現金(外貨)の集計
        portfolioSummary = summarizeForeignDeposit(responseData);
        if (portfolioSummary != null) {
            portfolioSummaryMap.put(ProductType.FOREIGN_DEPOSIT, portfolioSummary);
        }
        // 信用建玉の集計
        portfolioSummary = summarizeMarginPosition(responseData);
        if (portfolioSummary != null) {
            portfolioSummaryMap.put(ProductType.MARGIN_POSITION, portfolioSummary);
        }
        // 米株信用建玉の集計
        portfolioSummary = summarizeUsStockMarginPosition(responseData);
        if (portfolioSummary != null) {
            portfolioSummaryMap.put(ProductType.US_STOCK_MARGIN_POSITION, portfolioSummary);
        }

        // 先OP(保証金等)の集計
        portfolioSummary = summarizeFuturesOp(futuresOpValuation);
        if (portfolioSummary != null) {
            portfolioSummaryMap.put(ProductType.FUTURES_OP, portfolioSummary);
        }

        // 各商品の合計の集計
        summarizeTotal(responseData, portfolioSummaryMap);

        // 投資信託トータルリターンの集計
        // ※投資信託トータルリターンは資産状況に含めない
        summarizeMutualFundTotalReturn(responseData);

    }

    /**
     * 国内株式の集計
     * 
     * @param responseData
     * @return
     * @throws Exception
     */
    private IfaPortfolioA001PortfolioSummaryResponseDto summarizeDomesticStock(IfaPortfolioA001ResponseDto responseData) throws Exception {
        IfaPortfolioA001PortfolioSummaryResponseDto portfolioSummary = new IfaPortfolioA001PortfolioSummaryResponseDto();

        List<IfaPortfolioA001HoldingSecurityDomesticStockResponseDto> domesticStockList = responseData.getHoldingSecurityDomesticStock();

        if (CollectionUtils.isEmpty(domesticStockList)) {
            return null;
        }

        // 証券種別
        portfolioSummary.setSecurityClass(ProductType.DOMESTIC_STOCK.getName());
        // 評価額
        BigDecimal valuation = sumValue(domesticStockList, IfaPortfolioA001HoldingSecurityDomesticStockResponseDto::getValuation);
        portfolioSummary.setValuation(valuation.toPlainString());
        // 評価損益
        boolean noneNull = isNoneNull(domesticStockList, IfaPortfolioA001HoldingSecurityDomesticStockResponseDto::getProfitAndLoss);
        if (noneNull) {
            // Nullが1つも存在しない場合
            BigDecimal profitAndLossTotal = sumValue(domesticStockList, IfaPortfolioA001HoldingSecurityDomesticStockResponseDto::getProfitAndLoss);
            portfolioSummary.setProfitAndLoss(profitAndLossTotal.toPlainString());
            responseData.setDomesticStockProfitAndLossTotal(profitAndLossTotal.toPlainString());
        }

        return portfolioSummary;
    }

    /**
     * 国内債券の集計
     * 
     * @param responseData
     * @return
     * @throws Exception
     */
    private IfaPortfolioA001PortfolioSummaryResponseDto summarizeDomesticClaim(IfaPortfolioA001ResponseDto responseData) throws Exception {
        IfaPortfolioA001PortfolioSummaryResponseDto portfolioSummary = new IfaPortfolioA001PortfolioSummaryResponseDto();
        List<IfaPortfolioA001HoldingSecurityListDomesticClaimResponseDto> domesticClaimList = responseData.getHoldingSecurityListDomesticClaim();

        if (CollectionUtils.isEmpty(domesticClaimList)) {
            return null;
        }

        // 証券種別
        portfolioSummary.setSecurityClass(ProductType.DOMESTIC_CLAIM.getName());
        // 評価額
        BigDecimal valuation = sumValue(domesticClaimList, IfaPortfolioA001HoldingSecurityListDomesticClaimResponseDto::getValuation);
        portfolioSummary.setValuation(valuation.toPlainString());

        return portfolioSummary;
    }

    /**
     * 投資信託の集計
     * 
     * @param responseData
     * @return
     * @throws Exception
     */
    private IfaPortfolioA001PortfolioSummaryResponseDto summarizeMutualFund(IfaPortfolioA001ResponseDto responseData) throws Exception {
        IfaPortfolioA001PortfolioSummaryResponseDto portfolioSummary = new IfaPortfolioA001PortfolioSummaryResponseDto();
        List<IfaPortfolioA001HoldingSecurityListMutualFundResponseDto> mutualFundList = responseData.getHoldingSecurityListMutualFund();

        if (CollectionUtils.isEmpty(mutualFundList)) {
            return null;
        }

        // 証券種別
        portfolioSummary.setSecurityClass(ProductType.MUTUAL_FUND.getName());
        // 評価額
        BigDecimal valuation = sumValue(mutualFundList, IfaPortfolioA001HoldingSecurityListMutualFundResponseDto::getValuation);
        portfolioSummary.setValuation(valuation.toPlainString());
        // 評価損益
        boolean noneNull = isNoneNull(mutualFundList, IfaPortfolioA001HoldingSecurityListMutualFundResponseDto::getProfitAndLoss);
        if (noneNull) {
            // Nullが1つも存在しない場合
            BigDecimal profitAndLossTotal = sumValue(mutualFundList, IfaPortfolioA001HoldingSecurityListMutualFundResponseDto::getProfitAndLoss);
            portfolioSummary.setProfitAndLoss(profitAndLossTotal.toPlainString());
            responseData.setMutualFundProfitAndLossTotal(profitAndLossTotal.toPlainString());
        }

        return portfolioSummary;
    }

    /**
     * 外国債券(円建)の集計
     * 
     * @param responseData
     * @return
     * @throws Exception
     */
    private IfaPortfolioA001PortfolioSummaryResponseDto summarizeForeignClaimYenBase(IfaPortfolioA001ResponseDto responseData) throws Exception {
        IfaPortfolioA001PortfolioSummaryResponseDto portfolioSummary = new IfaPortfolioA001PortfolioSummaryResponseDto();
        List<IfaPortfolioA001HoldingSecurityListForeignClaimYenBaseResponseDto> foreignClaimYenBaseList = responseData.getHoldingSecurityListForeignClaimYenBase();

        if (CollectionUtils.isEmpty(foreignClaimYenBaseList)) {
            return null;
        }

        // 証券種別
        portfolioSummary.setSecurityClass(ProductType.FOREIGN_CLAIM_YEN_BASE.getName());
        // 評価額
        BigDecimal valuation = sumValue(foreignClaimYenBaseList, IfaPortfolioA001HoldingSecurityListForeignClaimYenBaseResponseDto::getValuation);
        portfolioSummary.setValuation(valuation.toPlainString());

        return portfolioSummary;
    }

    /**
     * 外国株式の集計
     * 
     * @param responseData
     * @return
     * @throws Exception
     */
    private IfaPortfolioA001PortfolioSummaryResponseDto summarizeForeignStock(IfaPortfolioA001ResponseDto responseData) throws Exception {
        IfaPortfolioA001PortfolioSummaryResponseDto portfolioSummary = new IfaPortfolioA001PortfolioSummaryResponseDto();

        List<IfaPortfolioA001HoldingSecurityListForeignStockResponseDto> foreignStockList = responseData.getHoldingSecurityListForeignStock();

        if (CollectionUtils.isEmpty(foreignStockList)) {
            return null;
        }

        // 証券種別
        portfolioSummary.setSecurityClass(ProductType.FOREIGN_STOCK.getName());
        // 評価額
        BigDecimal valuation = sumValue(foreignStockList, IfaPortfolioA001HoldingSecurityListForeignStockResponseDto::getValuation);
        portfolioSummary.setValuation(valuation.toPlainString());
        // 評価損益
        boolean noneNull = isNoneNull(foreignStockList, IfaPortfolioA001HoldingSecurityListForeignStockResponseDto::getProfitAndLoss);
        if (noneNull) {
            // Nullが1つも存在しない場合
            BigDecimal profitAndLossTotal = sumValue(foreignStockList, IfaPortfolioA001HoldingSecurityListForeignStockResponseDto::getProfitAndLoss);
            portfolioSummary.setProfitAndLoss(profitAndLossTotal.toPlainString());
            responseData.setForeignStockProfitAndLossTotal(profitAndLossTotal.toPlainString());
        }

        return portfolioSummary;
    }

    /**
     * 外貨建MMFの集計
     * 
     * @param responseData
     * @return
     * @throws Exception
     */
    private IfaPortfolioA001PortfolioSummaryResponseDto summarizeForeignMmf(IfaPortfolioA001ResponseDto responseData) throws Exception {
        IfaPortfolioA001PortfolioSummaryResponseDto portfolioSummary = new IfaPortfolioA001PortfolioSummaryResponseDto();

        List<IfaPortfolioA001HoldingSecurityListForeignMmfResponseDto> foreignMmfList = responseData.getHoldingSecurityListForeignMmf();

        if (CollectionUtils.isEmpty(foreignMmfList)) {
            return null;
        }

        // 証券種別
        portfolioSummary.setSecurityClass(ProductType.FOREIGN_MMF.getName());
        // 評価額
        BigDecimal valuation = sumValue(foreignMmfList, IfaPortfolioA001HoldingSecurityListForeignMmfResponseDto::getValuation);
        portfolioSummary.setValuation(valuation.toPlainString());
        // 評価損益
        boolean noneNull = isNoneNull(foreignMmfList, IfaPortfolioA001HoldingSecurityListForeignMmfResponseDto::getProfitAndLoss);
        if (noneNull) {
            // Nullが1つも存在しない場合
            BigDecimal profitAndLossTotal = sumValue(foreignMmfList, IfaPortfolioA001HoldingSecurityListForeignMmfResponseDto::getProfitAndLoss);
            portfolioSummary.setProfitAndLoss(profitAndLossTotal.toPlainString());
            responseData.setForeignMmfProfitAndLossTotal(profitAndLossTotal.toPlainString());
        }

        return portfolioSummary;
    }

    /**
     * 外国債券(外貨建)の集計
     *
     * @param responseData
     * @return
     * @throws Exception
     */
    private IfaPortfolioA001PortfolioSummaryResponseDto summarizeForeignClaimForeign(IfaPortfolioA001ResponseDto responseData) throws Exception {
        IfaPortfolioA001PortfolioSummaryResponseDto portfolioSummary = new IfaPortfolioA001PortfolioSummaryResponseDto();

        BigDecimal valuationForeignClaimForeign = BigDecimal.ZERO;
        BigDecimal valuationForeignClaimForeignStructuredBond = BigDecimal.ZERO;

        // 外国債券(外貨建)
        List<IfaPortfolioA001HoldingSecurityListForeignClaimForeignResponseDto> foreignClaimForeignList = responseData.getHoldingSecurityListForeignClaimForeign();
        if (!CollectionUtils.isEmpty(foreignClaimForeignList)) {
            // 評価額
            valuationForeignClaimForeign = sumValue(foreignClaimForeignList, IfaPortfolioA001HoldingSecurityListForeignClaimForeignResponseDto::getValuation);
        }
        // 外国債券(外貨建仕組債)
        List<IfaPortfolioA001HoldingSecurityListForeignClaimForeignStructuredBondResponseDto> foreignClaimForeignStructuredBondList = responseData.getHoldingSecurityListForeignClaimForeignStructuredBond();
        if (!CollectionUtils.isEmpty(foreignClaimForeignStructuredBondList)) {
            // 評価額
            valuationForeignClaimForeignStructuredBond = sumValue(foreignClaimForeignStructuredBondList, IfaPortfolioA001HoldingSecurityListForeignClaimForeignStructuredBondResponseDto::getValuation);
        }

        if (!CollectionUtils.isEmpty(foreignClaimForeignList) || !CollectionUtils.isEmpty(foreignClaimForeignStructuredBondList)) {
            // 証券種別
            portfolioSummary.setSecurityClass(ProductType.FOREIGN_CLAIM_FOREIGN.getName());
            // 評価額
            BigDecimal valuation = valuationForeignClaimForeign.add(valuationForeignClaimForeignStructuredBond);
            portfolioSummary.setValuation(valuation.toPlainString());
        } else {
            return null;
        }

        return portfolioSummary;
    }

    /**
     * STの集計
     * @param responseData
     * @return
     * @throws Exception
     */
    private IfaPortfolioA001PortfolioSummaryResponseDto summarizeSecurityToken(IfaPortfolioA001ResponseDto responseData) throws Exception {
        IfaPortfolioA001PortfolioSummaryResponseDto portfolioSummary = new IfaPortfolioA001PortfolioSummaryResponseDto();

        List<IfaPortfolioA001HoldingSecurityListSecurityTokenResponseDto> securityTokenList = responseData.getHoldingSecurityListSecurityToken();

        if (CollectionUtils.isEmpty(securityTokenList)) {
            return null;
        }

        // 証券種別
        portfolioSummary.setSecurityClass(ProductType.SECURITY_TOKEN.getName());
        // 評価額
        BigDecimal valuation = sumValue(securityTokenList, IfaPortfolioA001HoldingSecurityListSecurityTokenResponseDto::getValuation);
        portfolioSummary.setValuation(valuation.toPlainString());
        // 評価損益
        boolean noneNull = isNoneNull(securityTokenList, IfaPortfolioA001HoldingSecurityListSecurityTokenResponseDto::getProfitAndLoss);
        if (noneNull) {
            // Nullが1つも存在しない場合
            BigDecimal profitAndLossTotal = sumValue(securityTokenList, IfaPortfolioA001HoldingSecurityListSecurityTokenResponseDto::getProfitAndLoss);
            portfolioSummary.setProfitAndLoss(profitAndLossTotal.toPlainString());
            responseData.setSecurityTokenProfitAndLossTotal(profitAndLossTotal.toPlainString());
        }

        return portfolioSummary;
    }

    /**
     * 現金(円貨)の集計
     * 
     * @param responseData
     * @return
     * @throws Exception
     */
    private IfaPortfolioA001PortfolioSummaryResponseDto summarizeCash(IfaPortfolioA001ResponseDto responseData) throws Exception {
        IfaPortfolioA001PortfolioSummaryResponseDto portfolioSummary = new IfaPortfolioA001PortfolioSummaryResponseDto();

        BigDecimal valuationCash = BigDecimal.ZERO;
        BigDecimal valuationSweepAccount = BigDecimal.ZERO;
        BigDecimal valuationSbiRapAccountCash = BigDecimal.ZERO;

        // 保有商品一覧_現金リスト
        List<IfaPortfolioA001HoldingSecurityListCashResponseDto> cashList = responseData.getHoldingSecurityListCash();
        if (!CollectionUtils.isEmpty(cashList)) {
            valuationCash = sumValue(cashList, IfaPortfolioA001HoldingSecurityListCashResponseDto::getValuationTotal);
        }

        // 保有商品一覧_スイープ専用銀行口座リスト 
        List<IfaPortfolioA001HoldingSecurityListSweepAccountResponseDto> sweepAccountList = responseData.getHoldingSecurityListSweepAccountList();
        if (!CollectionUtils.isEmpty(sweepAccountList)) {
            valuationSweepAccount = sumValue(sweepAccountList, IfaPortfolioA001HoldingSecurityListSweepAccountResponseDto::getValuationTotal);
        }

        // 保有商品一覧_SBIラップ口座現金リスト
        List<IfaPortfolioA001HoldingSecurityListSbiRapAccountCashResponseDto> sbiRapAccountCashList = responseData.getHoldingSecurityListSbiRapAccountCash();
        if (!CollectionUtils.isEmpty(sbiRapAccountCashList)) {
            valuationSbiRapAccountCash = sumValue(sbiRapAccountCashList, IfaPortfolioA001HoldingSecurityListSbiRapAccountCashResponseDto::getValuation);
        }

        if (!CollectionUtils.isEmpty(cashList) || !CollectionUtils.isEmpty(sweepAccountList) || !CollectionUtils.isEmpty(sbiRapAccountCashList)) {
            // 証券種別
            portfolioSummary.setSecurityClass(ProductType.CASH.getName());
            // 評価額
            BigDecimal valuation = valuationCash.add(valuationSweepAccount).add(valuationSbiRapAccountCash);
            portfolioSummary.setValuation(valuation.toPlainString());
        } else {
            return null;
        }

        return portfolioSummary;
    }

    /**
     * 現金(外貨)の集計
     * 
     * @param responseData
     * @return
     * @throws Exception
     */
    private IfaPortfolioA001PortfolioSummaryResponseDto summarizeForeignDeposit(IfaPortfolioA001ResponseDto responseData) throws Exception {
        IfaPortfolioA001PortfolioSummaryResponseDto portfolioSummary = new IfaPortfolioA001PortfolioSummaryResponseDto();

        List<IfaPortfolioA001HoldingSecurityListForeignDepositResponseDto> foreignDepositList = responseData.getHoldingSecurityListForeignDeposit();
        
        if (CollectionUtils.isEmpty(foreignDepositList)) {
            return null;
        }

        // 証券種別
        portfolioSummary.setSecurityClass(ProductType.FOREIGN_DEPOSIT.getName());
        // 評価額
        BigDecimal valuation = sumValue(foreignDepositList, IfaPortfolioA001HoldingSecurityListForeignDepositResponseDto::getValuation);
        portfolioSummary.setValuation(valuation.toPlainString());

        return portfolioSummary;
    }

    /**
     * 信用建玉の集計
     * 
     * @param responseData
     * @return
     * @throws Exception
     */
    private IfaPortfolioA001PortfolioSummaryResponseDto summarizeMarginPosition(IfaPortfolioA001ResponseDto responseData) throws Exception {
        IfaPortfolioA001PortfolioSummaryResponseDto portfolioSummary = new IfaPortfolioA001PortfolioSummaryResponseDto();

        // 保有商品一覧_信用建玉リスト
        List<IfaPortfolioA001HoldingSecurityListMarginPositionResponseDto> marginPositionList = responseData.getHoldingSecurityListMarginPosition();
        
        if (CollectionUtils.isEmpty(marginPositionList)) {
            return null;
        }

        // 証券種別
        portfolioSummary.setSecurityClass(ProductType.MARGIN_POSITION.getName());
        // 評価損益
        boolean noneNull = isNoneNull(marginPositionList, IfaPortfolioA001HoldingSecurityListMarginPositionResponseDto::getProfitAndLoss);
        if (noneNull) {
            // Nullが1つも存在しない場合
            BigDecimal profitAndLossTotal = sumValue(marginPositionList, IfaPortfolioA001HoldingSecurityListMarginPositionResponseDto::getProfitAndLoss);
            portfolioSummary.setProfitAndLoss(profitAndLossTotal.toPlainString());
            responseData.setMarginPositionProfitAndLossTotal(profitAndLossTotal.toPlainString());
        }

        return portfolioSummary;
    }

    /**
     * 米株信用建玉の集計
     * 
     * @param responseData
     * @return
     * @throws Exception
     */
    private IfaPortfolioA001PortfolioSummaryResponseDto summarizeUsStockMarginPosition(IfaPortfolioA001ResponseDto responseData) throws Exception {
        IfaPortfolioA001PortfolioSummaryResponseDto portfolioSummary = new IfaPortfolioA001PortfolioSummaryResponseDto();

        // 保有商品一覧_米株信用建玉リスト
        List<IfaPortfolioA001HoldingSecurityListUsStockMarginPositionResponseDto> usStockMarginPositionList = responseData.getHoldingSecurityListUsStockMarginPositionList();

        if (CollectionUtils.isEmpty(usStockMarginPositionList)) {
            return null;
        }

        // 証券種別
        portfolioSummary.setSecurityClass(ProductType.US_STOCK_MARGIN_POSITION.getName());
        // 評価損益
        boolean noneNull = isNoneNull(usStockMarginPositionList, IfaPortfolioA001HoldingSecurityListUsStockMarginPositionResponseDto::getProfitAndLoss);
        if (noneNull) {
            // Nullが1つも存在しない場合
            BigDecimal profitAndLossTotal = sumValue(usStockMarginPositionList, IfaPortfolioA001HoldingSecurityListUsStockMarginPositionResponseDto::getProfitAndLoss);
            portfolioSummary.setProfitAndLoss(profitAndLossTotal.toPlainString());
            responseData.setUsStockMarginPositionProfitAndLossTotal(profitAndLossTotal.toPlainString());
        }

        return portfolioSummary;
    }

    /**
     * 先OP(保証金等)の集計
     * 
     * @param futuresOpValuation
     * @return
     * @throws Exception
     */
    private IfaPortfolioA001PortfolioSummaryResponseDto summarizeFuturesOp(String futuresOpValuation) throws Exception {
        IfaPortfolioA001PortfolioSummaryResponseDto portfolioSummary = new IfaPortfolioA001PortfolioSummaryResponseDto();

        if (StringUtil.isNullOrEmpty(futuresOpValuation)) {
            return null;
        }

        // 証券種別
        portfolioSummary.setSecurityClass(ProductType.FUTURES_OP.getName());
        // 評価額
        portfolioSummary.setValuation(futuresOpValuation);

        return portfolioSummary;
    }

    /**
     * 合計を算出（null,空文字列は0として扱う = 除外する）
     * 
     * @param <T>
     * @param items
     * @param extractor
     * @return 合計
     * @throws Exception
     */
    private <T> BigDecimal sumValue(Collection<T> items, Function<T, String> extractor) throws Exception {
        return items.stream()
                    .map(extractor)
                    .filter(Objects::nonNull)
                    .filter(v -> !v.trim().isEmpty())
                    .map(BigDecimal::new)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Nullが存在するかの判定（空文字列はNullと同じ扱いとする）
     * 
     * @param <T>
     * @param items
     * @param extractor
     * @return true:1つも存在しない, false:存在する
     */
    private <T> boolean isNoneNull(Collection<T> items, Function<T, String> extractor) {
        return items.stream()
                    .map(extractor)
                    .noneMatch(v -> v == null || v.trim().isEmpty());
    }

    /**
     * 各商品ごとの合計の集計
     * ※資産比率は、先に評価額合計を算出しておく
     * 
     * @param responseData
     * @param portfolioSummaryMap
     * @throws Exception
     */
    private void summarizeTotal(IfaPortfolioA001ResponseDto responseData, Map<ProductType, IfaPortfolioA001PortfolioSummaryResponseDto> portfolioSummaryMap) throws Exception {
        List<IfaPortfolioA001PortfolioSummaryResponseDto> portfolioSummaryList = new ArrayList<IfaPortfolioA001PortfolioSummaryResponseDto>();

        // 国内株式
        IfaPortfolioA001PortfolioSummaryResponseDto portfolioSummaryDomesticStock = null;
        BigDecimal valuationDomesticStock = BigDecimal.ZERO;
        BigDecimal profitAndLossDomesticStock = BigDecimal.ZERO;
        if (portfolioSummaryMap.containsKey(ProductType.DOMESTIC_STOCK)) {
            portfolioSummaryDomesticStock = (IfaPortfolioA001PortfolioSummaryResponseDto)portfolioSummaryMap.get(ProductType.DOMESTIC_STOCK);
            valuationDomesticStock = new BigDecimal(portfolioSummaryDomesticStock.getValuation());
            if (portfolioSummaryDomesticStock.getProfitAndLoss() != null ) {
                profitAndLossDomesticStock = new BigDecimal(portfolioSummaryDomesticStock.getProfitAndLoss());
            }
        }

        // 国内債権
        IfaPortfolioA001PortfolioSummaryResponseDto portfolioSummaryDomesticClaim = null;
        BigDecimal valuationDomesticClaim = BigDecimal.ZERO;
        if (portfolioSummaryMap.containsKey(ProductType.DOMESTIC_CLAIM)) {
            portfolioSummaryDomesticClaim = (IfaPortfolioA001PortfolioSummaryResponseDto)portfolioSummaryMap.get(ProductType.DOMESTIC_CLAIM);
            valuationDomesticClaim = new BigDecimal(portfolioSummaryDomesticClaim.getValuation());
        }

        // 投資信託
        IfaPortfolioA001PortfolioSummaryResponseDto portfolioSummaryMutualFund = null;
        BigDecimal valuationMutualFund = BigDecimal.ZERO;
        BigDecimal profitAndLossMutualFund = BigDecimal.ZERO;
        if (portfolioSummaryMap.containsKey(ProductType.MUTUAL_FUND)) {
            portfolioSummaryMutualFund = (IfaPortfolioA001PortfolioSummaryResponseDto)portfolioSummaryMap.get(ProductType.MUTUAL_FUND);
            valuationMutualFund = new BigDecimal(portfolioSummaryMutualFund.getValuation());
            if (portfolioSummaryMutualFund.getProfitAndLoss() != null) {
                profitAndLossMutualFund = new BigDecimal(portfolioSummaryMutualFund.getProfitAndLoss());
            }
        }

        // 外国債券(円建)
        IfaPortfolioA001PortfolioSummaryResponseDto portfolioSummaryForeignClaimYenBase = null;
        BigDecimal valuationForeignClaimYenBase = BigDecimal.ZERO;
        if (portfolioSummaryMap.containsKey(ProductType.FOREIGN_CLAIM_YEN_BASE)) {
            portfolioSummaryForeignClaimYenBase = (IfaPortfolioA001PortfolioSummaryResponseDto)portfolioSummaryMap.get(ProductType.FOREIGN_CLAIM_YEN_BASE);
            valuationForeignClaimYenBase = new BigDecimal(portfolioSummaryForeignClaimYenBase.getValuation());
        }

        // 外国株式
        IfaPortfolioA001PortfolioSummaryResponseDto portfolioSummaryForeignStock = null;
        BigDecimal valuationForeignStock = BigDecimal.ZERO;
        BigDecimal profitAndLossForeignStock = BigDecimal.ZERO;
        if (portfolioSummaryMap.containsKey(ProductType.FOREIGN_STOCK)) {
            portfolioSummaryForeignStock = (IfaPortfolioA001PortfolioSummaryResponseDto)portfolioSummaryMap.get(ProductType.FOREIGN_STOCK);
            valuationForeignStock = new BigDecimal(portfolioSummaryForeignStock.getValuation());
            if (portfolioSummaryForeignStock.getProfitAndLoss() != null) {
                profitAndLossForeignStock = new BigDecimal(portfolioSummaryForeignStock.getProfitAndLoss());
            }
        }

        // 外貨建MMF
        IfaPortfolioA001PortfolioSummaryResponseDto portfolioSummaryForeignMmf = null;
        BigDecimal valuationForeignMmf = BigDecimal.ZERO;
        BigDecimal profitAndLossForeignMmf = BigDecimal.ZERO;
        if (portfolioSummaryMap.containsKey(ProductType.FOREIGN_MMF)) {
            portfolioSummaryForeignMmf = (IfaPortfolioA001PortfolioSummaryResponseDto)portfolioSummaryMap.get(ProductType.FOREIGN_MMF);
            valuationForeignMmf = new BigDecimal(portfolioSummaryForeignMmf.getValuation());
            if (portfolioSummaryForeignMmf.getProfitAndLoss() != null) {
                profitAndLossForeignMmf = new BigDecimal(portfolioSummaryForeignMmf.getProfitAndLoss());
            }
        }

        // 外国債券(外貨建)
        IfaPortfolioA001PortfolioSummaryResponseDto portfolioSummaryForeignClaimForeign = null;
        BigDecimal valuationForeignClaimForeign = BigDecimal.ZERO;
        if (portfolioSummaryMap.containsKey(ProductType.FOREIGN_CLAIM_FOREIGN)) {
            portfolioSummaryForeignClaimForeign = (IfaPortfolioA001PortfolioSummaryResponseDto)portfolioSummaryMap.get(ProductType.FOREIGN_CLAIM_FOREIGN);
            valuationForeignClaimForeign = new BigDecimal(portfolioSummaryForeignClaimForeign.getValuation());
        }

        // ST
        IfaPortfolioA001PortfolioSummaryResponseDto portfolioSummarySecurityToken = null;
        BigDecimal valuationSecurityToken = BigDecimal.ZERO;
        BigDecimal profitAndLossSecurityToken = BigDecimal.ZERO;
        if (portfolioSummaryMap.containsKey(ProductType.SECURITY_TOKEN)) {
            portfolioSummarySecurityToken = (IfaPortfolioA001PortfolioSummaryResponseDto)portfolioSummaryMap.get(ProductType.SECURITY_TOKEN);
            valuationSecurityToken = new BigDecimal(portfolioSummarySecurityToken.getValuation());
            if (portfolioSummarySecurityToken.getProfitAndLoss() != null) {
                profitAndLossSecurityToken = new BigDecimal(portfolioSummarySecurityToken.getProfitAndLoss());
            }
        }

        // 現金(円貨)
        IfaPortfolioA001PortfolioSummaryResponseDto portfolioSummaryCash = null;
        BigDecimal valuationCash = BigDecimal.ZERO;
        if (portfolioSummaryMap.containsKey(ProductType.CASH)) {
            portfolioSummaryCash = (IfaPortfolioA001PortfolioSummaryResponseDto)portfolioSummaryMap.get(ProductType.CASH);
            valuationCash = new BigDecimal(portfolioSummaryCash.getValuation());
        }

        // 現金(外貨)
        IfaPortfolioA001PortfolioSummaryResponseDto portfolioSummaryForeignDeposit = null;
        BigDecimal valuationForeignDeposit = BigDecimal.ZERO;
        if (portfolioSummaryMap.containsKey(ProductType.FOREIGN_DEPOSIT)) {
            portfolioSummaryForeignDeposit = (IfaPortfolioA001PortfolioSummaryResponseDto)portfolioSummaryMap.get(ProductType.FOREIGN_DEPOSIT);
            valuationForeignDeposit = new BigDecimal(portfolioSummaryForeignDeposit.getValuation());
        }

        // 信用建玉
        IfaPortfolioA001PortfolioSummaryResponseDto portfolioSummaryMarginPosition = null;
        BigDecimal profitAndLossMarginPosition = BigDecimal.ZERO;
        if (portfolioSummaryMap.containsKey(ProductType.MARGIN_POSITION)) {
            portfolioSummaryMarginPosition = (IfaPortfolioA001PortfolioSummaryResponseDto)portfolioSummaryMap.get(ProductType.MARGIN_POSITION);
            if (portfolioSummaryMarginPosition.getProfitAndLoss() != null) {
                profitAndLossMarginPosition = new BigDecimal(portfolioSummaryMarginPosition.getProfitAndLoss());
            }
        }

        // 米株信用建玉
        IfaPortfolioA001PortfolioSummaryResponseDto portfolioSummaryUSStockMarginPosition = null;
        BigDecimal profitAndLossUSStockMarginPosition = BigDecimal.ZERO;
        if (portfolioSummaryMap.containsKey(ProductType.US_STOCK_MARGIN_POSITION)) {
            portfolioSummaryUSStockMarginPosition = (IfaPortfolioA001PortfolioSummaryResponseDto)portfolioSummaryMap.get(ProductType.US_STOCK_MARGIN_POSITION);
            if (portfolioSummaryUSStockMarginPosition.getProfitAndLoss() != null) {
                profitAndLossUSStockMarginPosition = new BigDecimal(portfolioSummaryUSStockMarginPosition.getProfitAndLoss());
            }
        }

        // 先OP(保証金等)
        IfaPortfolioA001PortfolioSummaryResponseDto portfolioSummaryFuturesOp = null;
        BigDecimal valuationFuturesOp = BigDecimal.ZERO;
        if (portfolioSummaryMap.containsKey(ProductType.FUTURES_OP)) {
            portfolioSummaryFuturesOp = (IfaPortfolioA001PortfolioSummaryResponseDto)portfolioSummaryMap.get(ProductType.FUTURES_OP);
            valuationFuturesOp = new BigDecimal(portfolioSummaryFuturesOp.getValuation());
        }

        // 資産状況サマリ評価額合計
        BigDecimal totalValuation = valuationDomesticStock.add(valuationDomesticClaim)
                                                            .add(valuationMutualFund)
                                                            .add(valuationForeignClaimYenBase)
                                                            .add(valuationForeignStock)
                                                            .add(valuationForeignMmf)
                                                            .add(valuationForeignClaimForeign)
                                                            .add(valuationSecurityToken)
                                                            .add(valuationCash)
                                                            .add(valuationForeignDeposit)
                                                            .add(valuationFuturesOp);
        responseData.setPortfolioSummaryValuationTotal(totalValuation.toPlainString());

        // 資産状況サマリ評価損益合計
        BigDecimal totalProfitAndLoss = profitAndLossDomesticStock.add(profitAndLossMutualFund)
                                                                    .add(profitAndLossForeignStock)
                                                                    .add(profitAndLossForeignMmf)
                                                                    .add(profitAndLossSecurityToken)
                                                                    .add(profitAndLossMarginPosition)
                                                                    .add(profitAndLossUSStockMarginPosition);
        responseData.setPortfolioSummaryProfitAndLossTotal(totalProfitAndLoss.toPlainString());

        String byProductAssetsRatioValue = "";
        // 国内株式
        if (portfolioSummaryDomesticStock != null) {
            // 資産比率
            byProductAssetsRatioValue = calcByProductAssetsRatio(totalValuation, valuationDomesticStock, RoundingMode.FLOOR);
            portfolioSummaryDomesticStock.setByProductAssetsRatio(byProductAssetsRatioValue);
            // 資産状況サマリリスト
            portfolioSummaryList.add(portfolioSummaryDomesticStock);
        }

        // 国内債権
        if (portfolioSummaryDomesticClaim != null) {
            // 資産比率
            byProductAssetsRatioValue = calcByProductAssetsRatio(totalValuation, valuationDomesticClaim, RoundingMode.FLOOR);
            portfolioSummaryDomesticClaim.setByProductAssetsRatio(byProductAssetsRatioValue);
            // 資産状況サマリリスト
            portfolioSummaryList.add(portfolioSummaryDomesticClaim);
        }

        // 投資信託
        if (portfolioSummaryMutualFund != null) {
            // 資産比率
            byProductAssetsRatioValue = calcByProductAssetsRatio(totalValuation, valuationMutualFund, RoundingMode.FLOOR);
            portfolioSummaryMutualFund.setByProductAssetsRatio(byProductAssetsRatioValue);
            // 資産状況サマリリスト
            portfolioSummaryList.add(portfolioSummaryMutualFund);
        }

        // 外国債券(円建) 資産比率
        if (portfolioSummaryForeignClaimYenBase != null) {
            // 資産比率
            byProductAssetsRatioValue = calcByProductAssetsRatio(totalValuation, valuationForeignClaimYenBase, RoundingMode.FLOOR);
            portfolioSummaryForeignClaimYenBase.setByProductAssetsRatio(byProductAssetsRatioValue);
            // 資産状況サマリリスト
            portfolioSummaryList.add(portfolioSummaryForeignClaimYenBase);
        }

        // 外国株式 資産比率
        if (portfolioSummaryForeignStock != null) {
            // 資産比率
            byProductAssetsRatioValue = calcByProductAssetsRatio(totalValuation, valuationForeignStock, RoundingMode.FLOOR);
            portfolioSummaryForeignStock.setByProductAssetsRatio(byProductAssetsRatioValue);
            // 資産状況サマリリスト
            portfolioSummaryList.add(portfolioSummaryForeignStock);
        }

        // 外貨建MMF 資産比率
        if (portfolioSummaryForeignMmf != null) {
            // 資産比率
            byProductAssetsRatioValue = calcByProductAssetsRatio(totalValuation, valuationForeignMmf, RoundingMode.FLOOR);
            portfolioSummaryForeignMmf.setByProductAssetsRatio(byProductAssetsRatioValue);
            // 資産状況サマリリスト
            portfolioSummaryList.add(portfolioSummaryForeignMmf);
        }

        // 外国債券(外貨建) 資産比率
        if (portfolioSummaryForeignClaimForeign != null) {
            // 資産比率
            byProductAssetsRatioValue = calcByProductAssetsRatio(totalValuation, valuationForeignClaimForeign, RoundingMode.FLOOR);
            portfolioSummaryForeignClaimForeign.setByProductAssetsRatio(byProductAssetsRatioValue);
            // 資産状況サマリリスト
            portfolioSummaryList.add(portfolioSummaryForeignClaimForeign);
        }

        // ST 資産比率
        if (portfolioSummarySecurityToken != null) {
            // 資産比率
            byProductAssetsRatioValue = calcByProductAssetsRatio(totalValuation, valuationSecurityToken, RoundingMode.FLOOR);
            portfolioSummarySecurityToken.setByProductAssetsRatio(byProductAssetsRatioValue);
            // 資産状況サマリリスト
            portfolioSummaryList.add(portfolioSummarySecurityToken);
        }

        // 現金(円貨) 資産比率
        if (portfolioSummaryCash != null) {
            // 資産比率
            byProductAssetsRatioValue = calcByProductAssetsRatio(totalValuation, valuationCash, RoundingMode.FLOOR);
            portfolioSummaryCash.setByProductAssetsRatio(byProductAssetsRatioValue);
            // 資産状況サマリリスト
            portfolioSummaryList.add(portfolioSummaryCash);
        }

        // 現金(外貨) 資産比率
        if (portfolioSummaryForeignDeposit != null) {
            // 資産比率
            byProductAssetsRatioValue = calcByProductAssetsRatio(totalValuation, valuationForeignDeposit, RoundingMode.FLOOR);
            portfolioSummaryForeignDeposit.setByProductAssetsRatio(byProductAssetsRatioValue);
            // 資産状況サマリリスト
            portfolioSummaryList.add(portfolioSummaryForeignDeposit);
        }

        // 信用建玉
        if (portfolioSummaryMarginPosition != null) {
            // 資産状況サマリリスト
            portfolioSummaryList.add(portfolioSummaryMarginPosition);
        }

        // 米株信用建玉
        if (portfolioSummaryUSStockMarginPosition != null) {
            // 資産状況サマリリスト
            portfolioSummaryList.add(portfolioSummaryUSStockMarginPosition);
        }

        // 先OP(保証金等)
        if (portfolioSummaryFuturesOp != null) {
            // 資産比率
            byProductAssetsRatioValue = calcByProductAssetsRatio(totalValuation, valuationFuturesOp, RoundingMode.FLOOR);
            portfolioSummaryFuturesOp.setByProductAssetsRatio(byProductAssetsRatioValue);
            // 資産状況サマリリスト
            portfolioSummaryList.add(portfolioSummaryFuturesOp);
        }

        // 資産状況サマリリスト
        responseData.setPortfolioSummaryList(portfolioSummaryList);
    }

    /**
     * 投資信託トータルリターンの集計
     * 
     * @param responseData
     * @throws Exception
     */
    private void summarizeMutualFundTotalReturn(IfaPortfolioA001ResponseDto responseData) throws Exception {
        List<IfaPortfolioA001HoldingSecurityListMutualFundTotalReturnResponseDto> holdingSecurityListMutualFundTotalReturnList = responseData.getHoldingSecurityListMutualFundTotalReturnList();
        if (holdingSecurityListMutualFundTotalReturnList == null || holdingSecurityListMutualFundTotalReturnList.isEmpty()) {
            return;
        }

        // 投資信託トータルリターン評価金額「円」
        BigDecimal mutualFundTotalReturnValuationTotal = sumValue(holdingSecurityListMutualFundTotalReturnList, IfaPortfolioA001HoldingSecurityListMutualFundTotalReturnResponseDto::getDepositTransferMarketValueToday);
        responseData.setMutualFundTotalReturnValuationTotal(mutualFundTotalReturnValuationTotal.toPlainString());
        
        // 投資信託トータルリターン売却金額「円」
        BigDecimal sellPriceTotal = sumValue(holdingSecurityListMutualFundTotalReturnList, IfaPortfolioA001HoldingSecurityListMutualFundTotalReturnResponseDto::getAmountSellRedemptionDeliverOutTotal);
        responseData.setSellPriceTotal(sellPriceTotal.toPlainString());
        
        // 投資信託トータルリターン分配金額「円」
        BigDecimal dividendTotal = sumValue(holdingSecurityListMutualFundTotalReturnList, IfaPortfolioA001HoldingSecurityListMutualFundTotalReturnResponseDto::getCouponRevenueTotal);
        responseData.setDividendTotal(dividendTotal.toPlainString());
        
        // 投資信託トータルリターン買付金額「円」合計
        BigDecimal buyPriceTotal = sumValue(holdingSecurityListMutualFundTotalReturnList, IfaPortfolioA001HoldingSecurityListMutualFundTotalReturnResponseDto::getAmountBuyReinvestSubscriptDeliverInTotal);
        responseData.setBuyPriceTotal(buyPriceTotal.toPlainString());
        
        // 投資信託トータルリターントータルリターン「円」合計
        BigDecimal totalReturnYenTotal = sumValue(holdingSecurityListMutualFundTotalReturnList, IfaPortfolioA001HoldingSecurityListMutualFundTotalReturnResponseDto::getTotalReturnYen);
        responseData.setTotalReturnYenTotal(totalReturnYenTotal.toPlainString());
        
        // 投資信託トータルリターントータルリターン「率」(小数点第3位以下切り捨て)
        if (buyPriceTotal != null && totalReturnYenTotal != null) {
            // 値の正負に関わらず絶対値の方向に切り捨てる
            String totalReturnRateTotalValue = calcByProductAssetsRatio(buyPriceTotal, totalReturnYenTotal, RoundingMode.DOWN);
            responseData.setTotalReturnRateTotal(totalReturnRateTotalValue);
        }
    }

    /**
     * 商品毎の資産比率を算出
     * 
     * @param totalValuation
     * @param securityValuation
     * @param roundingMode 
     * @return 商品毎の資産比率
     * @throws Exception
     */
    private String calcByProductAssetsRatio(BigDecimal totalValuation, BigDecimal securityValuation, RoundingMode roundingMode) throws Exception {
        String byProductAssetsRatioValue = null;
        if (totalValuation != null) {
            if (totalValuation.compareTo(BigDecimal.ZERO) != 0) {
                // ゼロ除算にならない場合
                byProductAssetsRatioValue = securityValuation.multiply(new BigDecimal("100"))
                                                                .divide(totalValuation, 2, roundingMode).toPlainString();
            } else {
                byProductAssetsRatioValue = "0.00";
            }
        }

        return byProductAssetsRatioValue;
    }
}
