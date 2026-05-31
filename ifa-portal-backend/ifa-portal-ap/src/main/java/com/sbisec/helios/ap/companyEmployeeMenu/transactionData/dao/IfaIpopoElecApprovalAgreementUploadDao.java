package com.sbisec.helios.ap.companyEmployeeMenu.transactionData.dao;

public interface IfaIpopoElecApprovalAgreementUploadDao {

    int upsertAgreement(String butenCode, String accountNumber, String agreementDate, String agreementKbn, String userId) throws Exception;
}

