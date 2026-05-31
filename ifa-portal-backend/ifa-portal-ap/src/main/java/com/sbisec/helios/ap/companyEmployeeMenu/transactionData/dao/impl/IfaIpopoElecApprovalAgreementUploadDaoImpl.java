package com.sbisec.helios.ap.companyEmployeeMenu.transactionData.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sbisec.helios.ap.companyEmployeeMenu.transactionData.dao.IfaIpopoElecApprovalAgreementUploadDao;
import com.sbisec.helios.ap.companyEmployeeMenu.transactionData.dao.mapper.IfaIpopoElecApprovalAgreementUploadMapper;

@Component
public class IfaIpopoElecApprovalAgreementUploadDaoImpl implements IfaIpopoElecApprovalAgreementUploadDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(IfaIpopoElecApprovalAgreementUploadDaoImpl.class);

    @Autowired
    private IfaIpopoElecApprovalAgreementUploadMapper mapper;

    @Override
    public int upsertAgreement(String butenCode, String accountNumber, String agreementDate, String agreementKbn, String userId) throws Exception {
        LOGGER.debug("upsertAgreement butenCode={}, accountNumber={}", butenCode, accountNumber);
        return mapper.upsertAgreement(butenCode, accountNumber, agreementDate, agreementKbn, userId);
    }
}

