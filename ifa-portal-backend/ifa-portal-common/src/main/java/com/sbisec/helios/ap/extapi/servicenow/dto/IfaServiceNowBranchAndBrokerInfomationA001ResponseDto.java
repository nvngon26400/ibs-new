package com.sbisec.helios.ap.extapi.servicenow.dto;

import java.util.List;

import com.sbisec.helios.ap.extapi.servicenow.dto.common.IfaServiceNowBranchDto;
import com.sbisec.helios.ap.extapi.servicenow.dto.common.IfaServiceNowDataList;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 本店・支店情報取得 レスポンスDto
 *
 * @author SCSK
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class IfaServiceNowBranchAndBrokerInfomationA001ResponseDto extends IfaServiceNowDataList {
    
    private static final long serialVersionUID = 6808055025514458222L;
    /**
     * 本支店リスト
     */
    private List<IfaServiceNowBranchDto> branchList;
    
}
