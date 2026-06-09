package com.sbisec.helios.ap.brokerageMenu.customerMenu.enums;

import org.apache.commons.lang3.Strings;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

/**
 * トリガ発動ゾーン
 * 
 * @author 趙韫慧
 *
 */
@JsonFormat(shape = Shape.OBJECT)
public enum TriggerZone {

    SOR("0", "以上"), TKY("1", "以下");

    private final String id;

    private final String label;

    private TriggerZone(String id, String label) {

        this.id = id;
        this.label = label;
    }

    public String getId() {

        return id;
    }

    public String getLabel() {

        return label;
    }

    public static TriggerZone valueOfId(String id) {

        TriggerZone[] enums = values();

        for (int i = 0; i < enums.length; i++) {
            if (Strings.CS.equals(enums[i].getId(), id))
                return enums[i];
        }

        return null;
    }
}
