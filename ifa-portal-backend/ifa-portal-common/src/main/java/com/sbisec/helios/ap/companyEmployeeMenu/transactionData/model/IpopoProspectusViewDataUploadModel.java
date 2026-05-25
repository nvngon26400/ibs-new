package com.sbisec.helios.ap.companyEmployeeMenu.transactionData.model;

import com.sbibits.earth.model.ModelBase;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 画面ID：SUB0504_03-01
 * 目論見書閲覧データ登録 取込行モデル
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class IpopoProspectusViewDataUploadModel extends ModelBase {

    private static final long serialVersionUID = 1L;

    /** 銘柄コード */
    private String productCode;

    /** ブックビルディング申込期間（開始） */
    private String presentationFrom;

    /** 部店 */
    private String butenCode;

    /** 口座番号 */
    private String accountNumber;

    /** 閲覧日時 */
    private String readTime;

    /** 文書ID */
    private String documentId;

    /** 文書枝番 */
    private String versionNumber;

    /** チェック結果 */
    private String checkResult;

    /** メッセージを表示する */
    private String displayMessage;

    /** 作成者 */
    private String createUser;

    /** 更新者 */
    private String updateUser;

    /** 機能ID */
    private String sysId;
}
