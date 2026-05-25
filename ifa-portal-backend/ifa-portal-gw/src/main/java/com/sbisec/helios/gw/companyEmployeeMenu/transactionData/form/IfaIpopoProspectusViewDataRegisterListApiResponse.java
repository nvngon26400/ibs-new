package com.sbisec.helios.gw.companyEmployeeMenu.transactionData.form;

import java.util.List;

import lombok.Data;

/**
 * 画面ID：SUB0504_03-01
 */
@Data
public class IfaIpopoProspectusViewDataRegisterListApiResponse {

    private String productCode;
    private String presentationFrom;
    private String butenCode;
    private String accountNumber;
    private String readTime;
    private String checkResult;
    private String displayMessage;
    private String documentId;
    private String versionNumber;
    private String mailBmFlg;
    private String sysId;
    private List<IfaIpopoProspectusViewDataRegisterCheckResultSetApiResponse> checkResultSetList;
}
