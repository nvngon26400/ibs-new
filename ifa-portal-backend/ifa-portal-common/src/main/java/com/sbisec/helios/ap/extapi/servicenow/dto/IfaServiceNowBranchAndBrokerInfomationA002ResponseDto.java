package com.sbisec.helios.ap.extapi.servicenow.dto;

import java.util.List;

import com.sbisec.helios.ap.extapi.servicenow.dto.common.IfaServiceNowBrokerDto;
import com.sbisec.helios.ap.extapi.servicenow.dto.common.IfaServiceNowDataList;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * SBI証券本支店の仲介業者支店を取得 レスポンスDto
 *
 * @author SCSK
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class IfaServiceNowBranchAndBrokerInfomationA002ResponseDto extends IfaServiceNowDataList {
    
    private static final long serialVersionUID = 4226821038589478754L;
    /** 仲介業者リスト. */
    private List<IfaServiceNowBrokerDto> brokerList;
    
}
