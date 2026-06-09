package com.sbisec.helios.gw.internalAdminMenu.formAcquire.form;

import jakarta.validation.constraints.NotEmpty;

import lombok.Data;

/**
 * 取引日記帳・顧客勘定元帳 A003b要求
 *
 * @author SCSK
 *
 */
@Data
public class IfaContractNoteCustomerLedgerAcquireA003bApiRequest {
    
    /** pdfファイルへのフルパス. */
    @NotEmpty(message = "pdfファイル名")
    private String pdfFileName;
    
}
