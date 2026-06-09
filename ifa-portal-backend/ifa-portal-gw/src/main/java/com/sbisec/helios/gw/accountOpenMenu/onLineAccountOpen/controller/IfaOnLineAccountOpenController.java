package com.sbisec.helios.gw.accountOpenMenu.onLineAccountOpen.controller;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.sbibits.earth.servlet.annotation.ScreenId;
import com.sbibits.earth.servlet.annotation.SessionCheckTarget;
import com.sbibits.earth.util.json.JsonConverter;
import com.sbisec.helios.ap.accountOpenMenu.onLineAccountOpen.dto.IfaOnLineAccountOpenA001RequestDto;
import com.sbisec.helios.ap.accountOpenMenu.onLineAccountOpen.dto.IfaOnLineAccountOpenA001ResponseDto;
import com.sbisec.helios.ap.common.model.DataList;
import com.sbisec.helios.ap.common.util.ApiRequestUtil;
import com.sbisec.helios.gw.accountOpenMenu.onLineAccountOpen.form.IfaOnLineAccountOpenA001ApiRequest;
import com.sbisec.helios.gw.accountOpenMenu.onLineAccountOpen.form.IfaOnLineAccountOpenA001ApiResponse;
import com.sbisec.helios.gw.common.controller.BaseController;

/**
 * 画面ID：SUB0207_0201
 * 画面名：オンライン口座開設
 *
 * @author SCSK 木村
 2025/02/06 新規作成
 */
@RestController
@SessionCheckTarget(type = "httpSession")
@ScreenId(groupId = "MAIN07", id = "SUB0207_0201", screenNumber = "")
public class IfaOnLineAccountOpenController extends BaseController {

    private JsonConverter jc = JsonConverter.getInstance();

    /**
     * アクセス：/onLineAccountOpen/IfaOnLineAccountOpenInitializeA001
     * アクションID：A001
     * アクション名：初期化
     * APIリクエスト：IfaOnLineAccountOpenA001ApiRequest
     * Apiレスポンス：IfaOnLineAccountOpenA001ApiResponse
     * Dtoリクエスト：IfaOnLineAccountOpenA001RequestDto
     * Dtoレスポンス：IfaOnLineAccountOpenA001ResponseDto
     *
     * @param apiReq リクエストパラメータ
     * @return 表示に必要な情報
     * @exception Exception システムエラー
     */
    @PostMapping("/onLineAccountOpen/ifaOnLineAccountOpenInitializeA001")
    public String initializeA001(
            @RequestBody IfaOnLineAccountOpenA001ApiRequest apiReq
    ) throws Exception {

        // コントローラへのリクエストをサービスへのリクエストへコピー
        IfaOnLineAccountOpenA001RequestDto appReq = new IfaOnLineAccountOpenA001RequestDto();
        BeanUtils.copyProperties(appReq, apiReq);

        // サービスへ処理を移譲
        DataList<IfaOnLineAccountOpenA001ResponseDto> appRes = ApiRequestUtil.invoke(
                "cmpIfaOnLineAccountOpenService",
                "initializeA001",
                new TypeReference<DataList<IfaOnLineAccountOpenA001ResponseDto>>() { },
                appReq
        );

        // サービスのレスポンスをコントローラのレスポンスへコピー
        DataList<IfaOnLineAccountOpenA001ApiResponse> apiRes = new DataList<IfaOnLineAccountOpenA001ApiResponse>();
        BeanUtils.copyProperties(apiRes, appRes);

        // 画面にレスポンスを返却
        return jc.toString(apiRes);

    }
    
    @Override
    protected String getFirstViewName() {

        return null;

    }
}