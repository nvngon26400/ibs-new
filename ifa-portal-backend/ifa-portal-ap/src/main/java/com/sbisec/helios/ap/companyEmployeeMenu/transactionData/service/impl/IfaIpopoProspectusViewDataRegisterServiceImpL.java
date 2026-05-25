package com.sbisec.helios.ap.companyEmployeeMenu.transactionData.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sbisec.helios.ap.common.annotation.dao.CordysTransactional;
import com.sbisec.helios.ap.companyEmployeeMenu.transactionData.dao.IfaIpopoProspectusViewDataRegisterDao;
import com.sbisec.helios.ap.companyEmployeeMenu.transactionData.model.IpopoProspectusViewDataUploadModel;
import com.sbisec.helios.ap.companyEmployeeMenu.transactionData.service.IfaIpopoProspectusViewDataRegisterService;

/**
 * 画面ID：SUB0504_03-01
 */
@Component(value = "cmpIfaIpopoProspectusViewDataRegisterService")
public class IfaIpopoProspectusViewDataRegisterServiceImpL implements IfaIpopoProspectusViewDataRegisterService {

    private static final Logger logger = LoggerFactory.getLogger(IfaIpopoProspectusViewDataRegisterServiceImpL.class);

    @Autowired
    private IfaIpopoProspectusViewDataRegisterDao dao;

    @Override
    public int getBbAcceptInfoCount(String productCode, String presentationFromYmd, String butenCode,
            String accountNumber) throws Exception {
        logger.debug("IfaIpopoProspectusViewDataRegisterServiceImpL.getBbAcceptInfoCount");
        return dao.getBbAcceptInfoCount(productCode, presentationFromYmd, butenCode, accountNumber);
    }

    @Override
    @CordysTransactional
    public int mergeProspectusViewData(List<IpopoProspectusViewDataUploadModel> registerList) throws Exception {
        logger.debug("IfaIpopoProspectusViewDataRegisterServiceImpL.mergeProspectusViewData");
        return dao.mergeProspectusViewData(registerList);
    }
}
