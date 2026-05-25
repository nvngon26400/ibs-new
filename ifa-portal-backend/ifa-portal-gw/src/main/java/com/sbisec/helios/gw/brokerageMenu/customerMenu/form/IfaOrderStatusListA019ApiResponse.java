package com.sbisec.helios.gw.brokerageMenu.customerMenu.form;

import com.sbisec.helios.ap.common.dto.NewMainSiteParamResponseDto;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 注文状況一覧
 * 2025/01/26 新規作成
 *
 * @author 大連 葉
 */
@Data
public class IfaOrderStatusListA019ApiResponse {

    /** 新メインサイト用パラメータ生成 */
    private List<NewMainSiteParamResponseDto> newMainSiteParamList;

    /** POSTリクエスト */
    private Map<String, String> postRequest = new HashMap<String, String>();

    /** リンクURL */
    private String linkUrl;
}
