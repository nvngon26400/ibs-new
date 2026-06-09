package com.sbisec.helios.ap.companyEmployeeMenu.transactionData.edelivConsentData.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sbibits.earth.dao.RowSelectableDao;
import com.sbisec.helios.ap.companyEmployeeMenu.transactionData.edelivConsentData.dao.IfaEdelivConsentDataListDao;
import com.sbisec.helios.ap.companyEmployeeMenu.transactionData.edelivConsentData.dao.mapper.IfaEdelivConsentDataListMapper;
import com.sbisec.helios.ap.companyEmployeeMenu.transactionData.edelivConsentData.dao.model.IfaEdelivConsentDataListSql002RequestModel;

/**
 * 画面ID：SUB0504_02-01
 * 画面名：電子交付同意データ一覧
 */
@Component
public class IfaEdelivConsentDataListDaoImpL extends RowSelectableDao implements IfaEdelivConsentDataListDao {

    private static final Logger logger = LoggerFactory.getLogger(IfaEdelivConsentDataListDaoImpL.class);

    private static final int BATCH_SIZE = 50;

    @Autowired
    private IfaEdelivConsentDataListMapper mapper;

    @Override
    public int countCustomerAttributeInfo(String butenCode, String accountNumber) throws Exception {
        logger.debug("IfaEdelivConsentDataListDaoImpL.countCustomerAttributeInfo: butenCode={}, accountNumber={}",
                butenCode, accountNumber);
        return mapper.countCustomerAttributeInfo(butenCode, accountNumber);
    }

    @Override
    public int mergeEdelivAgreementInfo(List<IfaEdelivConsentDataListSql002RequestModel> registerList)
            throws Exception {
        logger.debug("IfaEdelivConsentDataListDaoImpL.mergeEdelivAgreementInfo: size={}",
                registerList == null ? 0 : registerList.size());
        if (registerList == null || registerList.isEmpty()) {
            return 0;
        }

        List<IfaEdelivConsentDataListSql002RequestModel> divisionList = new ArrayList<>();
        int cnt = 1;
        for (int i = 0; i < registerList.size(); i++) {
            if (cnt % BATCH_SIZE == 0) {
                mapper.mergeEdelivAgreementInfo(divisionList);
                divisionList.clear();
                cnt = 1;
            }
            divisionList.add(registerList.get(i));
            cnt++;
        }
        if (!divisionList.isEmpty()) {
            mapper.mergeEdelivAgreementInfo(divisionList);
        }
        return 1;
    }

}
