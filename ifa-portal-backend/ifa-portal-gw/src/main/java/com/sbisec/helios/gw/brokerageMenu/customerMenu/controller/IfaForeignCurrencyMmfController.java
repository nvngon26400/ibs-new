package com.sbisec.helios.gw.brokerageMenu.customerMenu.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.sbibits.earth.model.DataList;
import com.sbibits.earth.servlet.annotation.ScreenId;
import com.sbibits.earth.servlet.annotation.SessionCheckTarget;
import com.sbibits.earth.util.StringUtil;
import com.sbibits.earth.util.json.JsonConverter;
import com.sbisec.helios.ap.common.constants.ServiceNameConstants;
import com.sbisec.helios.ap.common.dto.LinkNewMainSiteParamRequestDto;
import com.sbisec.helios.ap.common.dto.LinkNewMainSiteParamResponseDto;
import com.sbisec.helios.ap.common.dto.LinkUrlDto;
import com.sbisec.helios.ap.common.enums.ErrorLevel;
import com.sbisec.helios.ap.common.util.ApiRequestUtil;
import com.sbisec.helios.ap.common.util.IfaCommonUtil;
import com.sbisec.helios.gw.brokerageMenu.customerMenu.form.IfaForeignCurrencyMmfA001ApiRequest;
import com.sbisec.helios.gw.brokerageMenu.customerMenu.form.IfaForeignCurrencyMmfA001ApiResponse;
import com.sbisec.helios.gw.common.controller.BaseController;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 外貨建MMF
 * 2025/01/28 新規作成
 *
 * @author 大連 葉
 */
@RestController
@SessionCheckTarget(type = "httpSession")
@ScreenId(groupId = "MAIN02", id = "SUB0202_0404", screenNumber = "")
public class IfaForeignCurrencyMmfController extends BaseController {

    JsonConverter jc = JsonConverter.getInstance();

    /**
     * アクセス：/brokerageMenu/customerMenu/ifaForeignCurrencyMmfInitializeA001
     * アクションID：A001
     * アクション名：新メインサイトの設定
     * APIリクエスト：IfaForeignCurrencyMmfA001ApiRequest
     * Apiレスポンス：IfaForeignCurrencyMmfA001ApiResponse
     *
     * @param apiReq {@code IfaForeignCurrencyMmfA001ApiRequest}
     * @return {@code String}
     * @exception Exception 初期化処理で例外が発生した場合
     */
    @PostMapping(value = "/brokerageMenu/customerMenu/ifaForeignCurrencyMmfInitializeA001")
    public String getNewMainSiteA001(@RequestBody IfaForeignCurrencyMmfA001ApiRequest apiReq) throws Exception{

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
        DataList<IfaForeignCurrencyMmfA001ApiResponse> apiRes = new DataList<IfaForeignCurrencyMmfA001ApiResponse>();
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
}
