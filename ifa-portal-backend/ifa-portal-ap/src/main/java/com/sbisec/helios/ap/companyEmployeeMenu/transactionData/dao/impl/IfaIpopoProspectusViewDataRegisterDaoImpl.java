package com.sbisec.helios.ap.companyEmployeeMenu.transactionData.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sbisec.helios.ap.companyEmployeeMenu.transactionData.dao.IfaIpopoProspectusViewDataRegisterDao;
import com.sbisec.helios.ap.companyEmployeeMenu.transactionData.dao.mapper.IfaIpopoProspectusViewDataRegisterMapper;
import com.sbisec.helios.ap.companyEmployeeMenu.transactionData.model.IpopoProspectusViewDataUploadModel;
import com.sbisec.helios.ap.common.dao.RowSelectableDao;

/**
 * 画面ID：SUB0504_03-01
 */
@Repository
public class IfaIpopoProspectusViewDataRegisterDaoImpl extends RowSelectableDao
        implements IfaIpopoProspectusViewDataRegisterDao {

    private static final Logger logger = LoggerFactory.getLogger(IfaIpopoProspectusViewDataRegisterDaoImpl.class);

    @Autowired
    private IfaIpopoProspectusViewDataRegisterMapper mapper;

    @Override
    public int getBbAcceptInfoCount(String productCode, String presentationFrom, String butenCode,
            String accountNumber) throws Exception {
        logger.debug(
                "IfaIpopoProspectusViewDataRegisterDaoImpl.getBbAcceptInfoCount: productCode={}, presentationFrom={}, butenCode={}, accountNumber={}",
                productCode, presentationFrom, butenCode, accountNumber);
        return mapper.getBbAcceptInfoCount(productCode, presentationFrom, butenCode, accountNumber);
    }

    @Override
    public int mergeProspectusViewData(List<IpopoProspectusViewDataUploadModel> registerList) throws Exception {
        logger.debug("IfaIpopoProspectusViewDataRegisterDaoImpl.mergeProspectusViewData: size={}",
                registerList == null ? 0 : registerList.size());
        int count = 0;
        if (registerList != null) {
            for (IpopoProspectusViewDataUploadModel model : registerList) {
                count += mapper.mergeProspectusViewData(model);
            }
        }
        return count;
    }
}
