package com.sbisec.helios.ap.companyEmployeeMenu.transactionData.model;

import com.sbibits.earth.model.ModelBase;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 画面ID：SUB0504_03-01
 * 目論見書閲覧データ登録 チェック結果モデル
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class IpopoProspectusViewDataCheckResultModel extends ModelBase {

    private static final long serialVersionUID = 1L;

    private String productCode;
    private String productCodeStatus;
    private String presentationFrom;
    private String presentationFromStatus;
    private String butenCode;
    private String butenCodeStatus;
    private String accountNumber;
    private String accountNumberStatus;
    private String readTime;
    private String readTimeStatus;
    private String existMsg;
    private String existMsgStatus;
}
