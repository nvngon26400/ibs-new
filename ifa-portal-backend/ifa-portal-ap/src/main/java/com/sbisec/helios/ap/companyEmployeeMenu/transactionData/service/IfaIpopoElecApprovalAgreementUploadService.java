package com.sbisec.helios.ap.companyEmployeeMenu.transactionData.service;

import java.util.List;

import com.sbisec.helios.gw.companyEmployeeMenu.transactionData.form.IfaIpopoElecApprovalAgreementUploadRowApiRequest;

public interface IfaIpopoElecApprovalAgreementUploadService {

    boolean existsButenAccount(String butenCode, String accountNumber) throws Exception;

    int upsertAgreements(List<IfaIpopoElecApprovalAgreementUploadRowApiRequest> okList, String userId) throws Exception;
}

