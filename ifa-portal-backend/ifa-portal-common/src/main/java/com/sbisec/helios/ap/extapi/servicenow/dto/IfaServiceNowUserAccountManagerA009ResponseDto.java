package com.sbisec.helios.ap.extapi.servicenow.dto;

import java.util.List;

import com.sbisec.helios.ap.extapi.servicenow.dto.common.IfaServiceNowDataList;
import com.sbisec.helios.ap.extapi.servicenow.dto.common.IfaServiceNowPrivDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 権限一覧取得 レスポンスDto
 *
 * @author SCSK
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class IfaServiceNowUserAccountManagerA009ResponseDto extends IfaServiceNowDataList {
    
    private static final long serialVersionUID = 4160717175177280575L;
    /**
     * 権限リスト
     */
    private List<IfaServiceNowPrivDto> privList;
    
}
