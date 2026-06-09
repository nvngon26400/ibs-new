package com.sbisec.helios.gw.brokerageMenu.customerMenu.controller;

import com.sbibits.earth.model.DataList;
import com.sbibits.earth.util.StringUtil;
import com.sbisec.helios.ap.common.constants.ServiceNameConstants;
import com.sbisec.helios.ap.common.dto.LinkNewMainSiteParamRequestDto;
import com.sbisec.helios.ap.common.dto.LinkNewMainSiteParamResponseDto;
import com.sbisec.helios.ap.common.dto.LinkUrlDto;
import com.sbisec.helios.ap.common.enums.ErrorLevel;
import com.sbisec.helios.ap.common.util.IfaCommonUtil;
import com.sbisec.helios.gw.brokerageMenu.customerMenu.form.*;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.sbibits.earth.servlet.annotation.ScreenId;
import com.sbibits.earth.servlet.annotation.SessionCheckTarget;
import com.sbibits.earth.util.json.JsonConverter;
import com.sbisec.helios.ap.brokerageMenu.customerMenu.dto.IfaOrderStatusListA002DtoRequest;
import com.sbisec.helios.ap.brokerageMenu.customerMenu.dto.IfaOrderStatusListA002DtoResponse;
import com.sbisec.helios.ap.brokerageMenu.customerMenu.dto.IfaOrderStatusListA004DtoRequest;
import com.sbisec.helios.ap.brokerageMenu.customerMenu.dto.IfaOrderStatusListX001DtoRequest;
import com.sbisec.helios.ap.brokerageMenu.customerMenu.dto.IfaOrderStatusListX001DtoResponse;

import com.sbisec.helios.ap.common.util.ApiRequestUtil;
import com.sbisec.helios.gw.common.controller.BaseController;
import com.sbisec.helios.gw.common.util.IfaGwCommonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 画面ID：SUB0202_0104-01 画面名：注文状況一覧
 *
 * @author 齋藤
 *
 *         2023/10/16 新規作成
 */
@RestController
@SessionCheckTarget(type = "httpSession")
@ScreenId(groupId = "MAIN02", id = "SUB0202_0104-01", screenNumber = "")
public class IfaOrderStatusListController extends BaseController {
    
    private JsonConverter jc = JsonConverter.getInstance();
    
    /**
     * アクセス：/brokerageMenu/customerMenu/ifaOrderStatusListInitializeX001
     * アクションID：X001
     * アクション名：初期化
     * APIリクエスト：IfaOrderStatusListX001ApiRequest
     * Apiレスポンス：IfaOrderStatusListX001ApiResponse
     * Dtoリクエスト：IfaOrderStatusListX001DtoRequest
     * Dtoレスポンス：IfaOrderStatusListX001DtoResponse
     *
     * @param apiReq IfaOrderStatusListX001ApiRequest
     * @return String
     * @exception Exception SQLExceptionなど
     */
    @PostMapping("/brokerageMenu/customerMenu/ifaOrderStatusListInitializeX001")
    public String initializeX001(@RequestBody IfaOrderStatusListX001ApiRequest apiReq) throws Exception {
        
        IfaOrderStatusListX001DtoRequest appReq = new IfaOrderStatusListX001DtoRequest();
        
        // Beanコピー用共通部品。
        BeanUtils.copyProperties(appReq, apiReq);
        IfaGwCommonUtil.setCustomerCommonToRequestScope();
        DataList<IfaOrderStatusListX001DtoResponse> appRes = ApiRequestUtil.invoke("cmpIfaOrderStatusListService",
                "initializeX001", new TypeReference<DataList<IfaOrderStatusListX001DtoResponse>>() {
                }, appReq);
        
        DataList<IfaOrderStatusListX001ApiResponse> apiRes = new DataList<IfaOrderStatusListX001ApiResponse>();
        
        BeanUtils.copyProperties(apiRes, appRes);
        
        return jc.toString(apiRes);
    }
    
    /**
     * アクセス：/brokerageMenu/customerMenu/ifaOrderStatusListInitializeOrderListA002
     * アクションID：A002
     * アクション名：初期化（注文一覧）
     * APIリクエスト：IfaOrderStatusListA002ApiRequest
     * Apiレスポンス：IfaOrderStatusListA002ApiResponse
     * Dtoリクエスト：IfaOrderStatusListA002DtoRequest
     * Dtoレスポンス：IfaOrderStatusListA002DtoResponse
     *
     * @param apiReq IfaOrderStatusListA002ApiRequest
     * @return String
     * @exception Exception SQLExceptionなど
     */
    @PostMapping("/brokerageMenu/customerMenu/ifaOrderStatusListInitializeOrderListA002")
    public String initializeOrderListA002(@RequestBody IfaOrderStatusListA002ApiRequest apiReq) throws Exception {
        
        IfaOrderStatusListA002DtoRequest appReq = new IfaOrderStatusListA002DtoRequest();
        
        // Beanコピー用共通部品。
        BeanUtils.copyProperties(appReq, apiReq);
        IfaGwCommonUtil.setCustomerCommonToRequestScope();
        DataList<IfaOrderStatusListA002DtoResponse> appRes = ApiRequestUtil.invoke("cmpIfaOrderStatusListService",
                "initializeOrderListA002", new TypeReference<DataList<IfaOrderStatusListA002DtoResponse>>() {
                }, appReq);
        
        DataList<IfaOrderStatusListA002ApiResponse> apiRes = new DataList<IfaOrderStatusListA002ApiResponse>();
        
        BeanUtils.copyProperties(apiRes, appRes);
        
        return jc.toString(apiRes);
    }
    
    /**
     * アクセス：/brokerageMenu/customerMenu/ifaOrderStatusListForeignStockCounterDetailDisplayA004
     * アクションID：A004
     * アクション名：外国株式店頭詳細表示
     * APIリクエスト：brokerageMenu.customerMenuA004ApiRequest
     * Apiレスポンス：brokerageMenu.customerMenuA004ApiResponse
     * Dtoリクエスト：brokerageMenu.customerMenuA004DtoRequest
     * Dtoレスポンス：brokerageMenu.customerMenuA004DtoResponse
     *
     * @param apiReq IfaOrderStatusListA004ApiRequest
     * @return String
     * @exception Exception SQLExceptionなど
     */
    @PostMapping("/brokerageMenu/customerMenu/ifaOrderStatusListForeignStockCounterDetailDisplayA004")
    public String foreignStockCounterDetailDisplayA004(@RequestBody IfaOrderStatusListA004ApiRequest apiReq)
            throws Exception {
        
        IfaOrderStatusListA004DtoRequest appReq = new IfaOrderStatusListA004DtoRequest();
        
        // Beanコピー用共通部品。
        BeanUtils.copyProperties(appReq, apiReq);
        DataList<IfaOrderStatusListA004ApiResponse> appRes = ApiRequestUtil.invoke("cmpIfaOrderStatusListService",
                "foreignStockCounterDetailDisplayA004",
                new TypeReference<DataList<IfaOrderStatusListA004ApiResponse>>() {
                }, appReq);
        
        DataList<IfaOrderStatusListA004ApiResponse> apiRes = new DataList<IfaOrderStatusListA004ApiResponse>();
        
        BeanUtils.copyProperties(apiRes, appRes);
        
        return jc.toString(apiRes);
    }

    /**
     * アクセス：/brokerageMenu/customerMenu/ifaOrderStatusListGetNewMainSiteA019
     * アクションID：A019
     * アクション名：新メインサイトの設定
     * APIリクエスト：IfaOrderStatusListA019ApiRequest
     * Apiレスポンス：IfaOrderStatusListA019ApiResponse
     *
     * @param apiReq {@code IfaOrderStatusListA019ApiRequest}
     * @return {@code String}
     * @exception Exception 初期化処理で例外が発生した場合
     */
    @PostMapping(value = "/brokerageMenu/customerMenu/ifaOrderStatusListGetNewMainSiteA019")
    public String getNewMainSiteA019(@RequestBody IfaOrderStatusListA019ApiRequest apiReq) throws Exception{

        // リンクURL取得
        LinkUrlDto result = ApiRequestUtil.invoke(ServiceNameConstants.LINK_URL_SERVICE, "getLinkUrl",
                new TypeReference<LinkUrlDto>() {
                }, apiReq.getUrlId(), apiReq.getPatternId(), apiReq.getHttpMethod());

        // 新メインサイト用パラメータ取得
        LinkNewMainSiteParamRequestDto appReq = new LinkNewMainSiteParamRequestDto();
        BeanUtils.copyProperties(appReq, apiReq);
        LinkNewMainSiteParamResponseDto paramResult = ApiRequestUtil.invoke(ServiceNameConstants.LINK_NEW_MAIN_SITE_PARAM_SERVICE,
                "getNewMainSiteParam", new TypeReference<LinkNewMainSiteParamResponseDto>() {
                }, appReq);
        DataList<IfaOrderStatusListA019ApiResponse> apiRes = new DataList<IfaOrderStatusListA019ApiResponse>();
        DataList<LinkNewMainSiteParamResponseDto> appRes = new DataList<LinkNewMainSiteParamResponseDto>();
        LinkNewMainSiteParamResponseDto linkRes = new LinkNewMainSiteParamResponseDto();
        // URLを取得する
        linkRes.setLinkUrl(result.getLinkUrl());
        // データベースからパラメータを取得する
        linkRes.setPostRequest(result.getPostRequest());
        // 新メインサイトを取得する
        linkRes.setNewMainSiteParamList(paramResult.getNewMainSiteParamList());
        List<LinkNewMainSiteParamResponseDto> linkList = new ArrayList<LinkNewMainSiteParamResponseDto>();
        linkList.add(linkRes);
        appRes = IfaCommonUtil.createDataList(linkList, ErrorLevel.SUCCESS, ErrorLevel.SUCCESS.name(), StringUtil.EMPTY_STRING);
        BeanUtils.copyProperties(apiRes, appRes);

        return jc.toString(apiRes);
    }
    
    @Override
    protected String getFirstViewName() {
        
        return null;
    }
}
