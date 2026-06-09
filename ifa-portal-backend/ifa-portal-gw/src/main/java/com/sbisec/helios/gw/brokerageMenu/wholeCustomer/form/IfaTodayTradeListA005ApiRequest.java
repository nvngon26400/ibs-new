package com.sbisec.helios.gw.brokerageMenu.wholeCustomer.form;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class IfaTodayTradeListA005ApiRequest {
    
    /** CSVファイル名 */
    private String csvDownloadFile;

    /** CSV出力データ. */
    private List<IfaTodayTradeListA005TodayTradeApiResponse> csvData = new ArrayList<IfaTodayTradeListA005TodayTradeApiResponse>();

}
