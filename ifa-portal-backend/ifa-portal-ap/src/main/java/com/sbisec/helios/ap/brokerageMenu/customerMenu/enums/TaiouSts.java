package com.sbisec.helios.ap.brokerageMenu.customerMenu.enums;

import org.apache.commons.lang3.Strings;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

/**
 * 対応ステータス
 * 
 * @author 趙韫慧
 *
 */
@JsonFormat(shape = Shape.OBJECT)
public enum TaiouSts {

    YORISASHI("0", "未対応"), PRICE("1", "対応済");

    private final String id;
    private final String label;

    private TaiouSts(String id, String label) {

        this.id = id;
        this.label = label;
    }

    public String getId() {

        return id;
    }

    public String getLabel() {

        return label;
    }

    public static TaiouSts valueOfId(String id) {

        TaiouSts[] enums = values();

        for (int i = 0; i < enums.length; i++) {
            if (Strings.CS.equals(enums[i].getId(), id))
                return enums[i];
        }

        return null;
    }
}
