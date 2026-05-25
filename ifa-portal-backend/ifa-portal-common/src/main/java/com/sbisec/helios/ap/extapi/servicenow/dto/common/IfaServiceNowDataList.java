package com.sbisec.helios.ap.extapi.servicenow.dto.common;

import com.sbibits.earth.model.DataList;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * ServiceNow 専用データリスト
 *
 * @author SCSK
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class IfaServiceNowDataList extends DataList<Object> {
    
    private static final long serialVersionUID = -4019550969562066743L;
    /**
     * 登録・削除・更新した件数
     */
    private int affectedRows;
    
}
