package com.sbisec.helios.gw.companyEmployeeMenu.transactionData.form;

public class IfaIpopoElecApprovalAgreementUploadRowApiRequest {

    private String butenCode;
    private String accountNumber;
    private String edelivAgreementDate;
    private String edelivAgreementKbn;
    private String checkResult;
    private String errorMessage;

    public String getButenCode() {
        return butenCode;
    }

    public void setButenCode(String butenCode) {
        this.butenCode = butenCode;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getEdelivAgreementDate() {
        return edelivAgreementDate;
    }

    public void setEdelivAgreementDate(String edelivAgreementDate) {
        this.edelivAgreementDate = edelivAgreementDate;
    }

    public String getEdelivAgreementKbn() {
        return edelivAgreementKbn;
    }

    public void setEdelivAgreementKbn(String edelivAgreementKbn) {
        this.edelivAgreementKbn = edelivAgreementKbn;
    }

    public String getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(String checkResult) {
        this.checkResult = checkResult;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}

