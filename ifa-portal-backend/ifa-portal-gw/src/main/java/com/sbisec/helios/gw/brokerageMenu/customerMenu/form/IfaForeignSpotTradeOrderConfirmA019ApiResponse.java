package com.sbisec.helios.gw.brokerageMenu.customerMenu.form;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sbisec.helios.ap.common.dto.NewMainSiteParamResponseDto;

import lombok.Data;

/**
 * 外国現物取引注文確認
 * 2026/02/06 新規作成
 *
 * @author 大連 趙
 */
@Data
public class IfaForeignSpotTradeOrderConfirmA019ApiResponse {

    /** 新メインサイト用パラメータ生成 */
    private List<NewMainSiteParamResponseDto> newMainSiteParamList;

    /** POSTリクエスト */
    private Map<String, String> postRequest = new HashMap<String, String>();

    /** リンクURL */
    private String linkUrl;
}
