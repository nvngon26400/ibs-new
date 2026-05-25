package com.sbisec.helios.ap.companyEmployeeMenu.transactionData.dao.mapper;

import org.apache.ibatis.annotations.Param;

import com.sbisec.helios.ap.companyEmployeeMenu.transactionData.model.IpopoProspectusViewDataUploadModel;

/**
 * 画面ID：SUB0504_03-01
 */
public interface IfaIpopoProspectusViewDataRegisterMapper {

    int getBbAcceptInfoCount(@Param("productCode") String productCode,
            @Param("presentationFrom") String presentationFrom, @Param("butenCode") String butenCode,
            @Param("accountNumber") String accountNumber);

    int mergeProspectusViewData(@Param("model") IpopoProspectusViewDataUploadModel model);
}
