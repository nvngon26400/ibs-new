package com.sbisec.helios.ap.brokerageMenu.jointSubscript.util.enums;

/** 共同募集顧客.手続状況の列挙型 */
public enum EditStats {
    /** 新規申請 */
    REGISTER("0"),
    /** 修正申請 */
    CORRECT("1"),
    /** 承認 */
    APPROVE("2"),
    /** 削除 */
    DELETE("3");

    public String key;
    EditStats(String key) {this.key = key;}
}