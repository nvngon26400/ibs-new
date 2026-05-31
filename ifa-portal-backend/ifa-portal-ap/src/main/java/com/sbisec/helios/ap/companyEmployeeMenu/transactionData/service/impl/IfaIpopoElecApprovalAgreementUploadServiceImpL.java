package com.sbisec.helios.ap.companyEmployeeMenu.transactionData.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;

import com.sbibits.earth.util.StringUtil;
import com.sbisec.helios.ap.common.annotation.dao.EtintraTransactional;
import com.sbisec.helios.ap.common.dao.MediateUserAttributeInfoDao;
import com.sbisec.helios.ap.companyEmployeeMenu.transactionData.dao.IfaIpopoElecApprovalAgreementUploadDao;
import com.sbisec.helios.ap.companyEmployeeMenu.transactionData.service.IfaIpopoElecApprovalAgreementUploadService;
import com.sbisec.helios.gw.companyEmployeeMenu.transactionData.form.IfaIpopoElecApprovalAgreementUploadRowApiRequest;

@Component(value = "cmpIfaIpopoElecApprovalAgreementUploadService")
public class IfaIpopoElecApprovalAgreementUploadServiceImpL implements IfaIpopoElecApprovalAgreementUploadService {

    @Autowired
    private MediateUserAttributeInfoDao mediateUserAttributeInfoDao;

    @Autowired
    private IfaIpopoElecApprovalAgreementUploadDao dao;

    @Override
    public boolean existsButenAccount(String butenCode, String accountNumber) throws Exception {
        if (StringUtil.isNullOrEmpty(butenCode) || StringUtil.isNullOrEmpty(accountNumber)) {
            return false;
        }
        return mediateUserAttributeInfoDao.getMediateUserAttributeInfo(butenCode, accountNumber) != null;
    }

    @Override
    @EtintraTransactional(propagation = Propagation.REQUIRES_NEW)
    public int upsertAgreements(List<IfaIpopoElecApprovalAgreementUploadRowApiRequest> okList, String userId) throws Exception {
        int count = 0;
        for (IfaIpopoElecApprovalAgreementUploadRowApiRequest r : okList) {
            count += dao.upsertAgreement(r.getButenCode(), r.getAccountNumber(), r.getEdelivAgreementDate(), r.getEdelivAgreementKbn(), userId);
        }
        return count;
    }
}

