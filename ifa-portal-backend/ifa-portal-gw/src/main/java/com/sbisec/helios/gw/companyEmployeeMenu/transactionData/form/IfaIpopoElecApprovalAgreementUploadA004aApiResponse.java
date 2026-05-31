package com.sbisec.helios.gw.companyEmployeeMenu.transactionData.form;

public class IfaIpopoElecApprovalAgreementUploadA004aApiResponse {

    private String pdfFileName;
    private String pdfFileOutputName;

    public IfaIpopoElecApprovalAgreementUploadA004aApiResponse() {
    }

    public IfaIpopoElecApprovalAgreementUploadA004aApiResponse(String pdfFileName, String pdfFileOutputName) {
        this.pdfFileName = pdfFileName;
        this.pdfFileOutputName = pdfFileOutputName;
    }

    public String getPdfFileName() {
        return pdfFileName;
    }

    public void setPdfFileName(String pdfFileName) {
        this.pdfFileName = pdfFileName;
    }

    public String getPdfFileOutputName() {
        return pdfFileOutputName;
    }

    public void setPdfFileOutputName(String pdfFileOutputName) {
        this.pdfFileOutputName = pdfFileOutputName;
    }
}

