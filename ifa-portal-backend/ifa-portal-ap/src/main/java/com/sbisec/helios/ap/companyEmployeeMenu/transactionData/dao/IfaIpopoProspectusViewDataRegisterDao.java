package com.sbisec.helios.ap.companyEmployeeMenu.transactionData.dao;

import java.util.List;

import com.sbisec.helios.ap.companyEmployeeMenu.transactionData.model.IpopoProspectusViewDataUploadModel;

/**
 * 画面ID：SUB0504_03-01
 */
public interface IfaIpopoProspectusViewDataRegisterDao {

    int getBbAcceptInfoCount(String productCode, String presentationFrom, String butenCode, String accountNumber)
            throws Exception;

    int mergeProspectusViewData(List<IpopoProspectusViewDataUploadModel> registerList) throws Exception;
}
