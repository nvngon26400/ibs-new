package com.sbisec.helios.ap.brokerageMenu.customerMenu.enums;

import org.apache.commons.lang3.Strings;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

/**
 * OCO指成区分
 * 
 * @author 趙韫慧
 *
 */
@JsonFormat(shape = Shape.OBJECT)
public enum OcoSasinariKbn {

    NARIYUKI("N", "成行"), HUNARI("F", "不成(F)");

    private final String id;
    private final String label;

    private OcoSasinariKbn(String id, String label) {

        this.id = id;
        this.label = label;
    }

    public String getId() {

        return id;
    }

    public String getLabel() {

        return label;
    }

    public static OcoSasinariKbn valueOfId(String id) {

        OcoSasinariKbn[] enums = values();

        for (int i = 0; i < enums.length; i++) {
            if (Strings.CS.equals(enums[i].getId(), id))
                return enums[i];
        }

        return null;
    }
}
