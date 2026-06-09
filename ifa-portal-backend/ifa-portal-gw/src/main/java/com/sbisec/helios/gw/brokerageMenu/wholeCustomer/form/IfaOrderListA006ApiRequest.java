package com.sbisec.helios.gw.brokerageMenu.wholeCustomer.form;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.Data;

/**
*
*
* @author BASE李
*
*/
@Data
public class IfaOrderListA006ApiRequest {

    /** IFA注文番号（数字）. */
    @Digits(integer = 18, fraction = 0, message = "IFA注文番号")
    @NotEmpty(message = "IFA注文番号")
    @Pattern(regexp="0-9", message = "IFA注文番号")
    @Size(max = 18, message = "IFA注文番号")
    private String ifaOrderNo;
    
    /** IFA注文サブ番号（数字）. */
    @Digits(integer = 3, fraction = 0, message = "IFA注文サブ番号")
    @NotEmpty(message = "IFA注文サブ番号")
    @Pattern(regexp="0-9", message = "IFA注文サブ番号")
    @Size(max = 3, message = "IFA注文サブ番号")
    private String ifaOrderSubNo;

}
