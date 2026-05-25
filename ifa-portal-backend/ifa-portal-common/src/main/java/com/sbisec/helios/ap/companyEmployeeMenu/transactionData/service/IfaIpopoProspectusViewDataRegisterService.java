package com.sbisec.helios.ap.companyEmployeeMenu.transactionData.service;

import java.util.List;

import com.sbibits.earth.service.Service;
import com.sbisec.helios.ap.companyEmployeeMenu.transactionData.model.IpopoProspectusViewDataUploadModel;

/**
 * 画面ID：SUB0504_03-01
 * 目論見書閲覧データ登録
 */
public interface IfaIpopoProspectusViewDataRegisterService extends Service {

    /**
     * SQL001 外債承認データ件数取得
     */
    int getBbAcceptInfoCount(String productCode, String presentationFromYmd, String butenCode, String accountNumber)
            throws Exception;

    /**
     * SQL002 目論見書閲覧状況登録更新
     */
    int mergeProspectusViewData(List<IpopoProspectusViewDataUploadModel> registerList) throws Exception;
}
