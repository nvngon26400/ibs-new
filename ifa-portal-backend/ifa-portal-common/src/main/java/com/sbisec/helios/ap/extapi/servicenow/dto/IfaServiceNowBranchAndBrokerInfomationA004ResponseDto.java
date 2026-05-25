package com.sbisec.helios.ap.extapi.servicenow.dto;

import java.util.List;

import com.sbisec.helios.ap.extapi.servicenow.dto.common.IfaServiceNowDataList;
import com.sbisec.helios.ap.extapi.servicenow.dto.common.IfaServiceNowEmployeeDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 仲介業社支店一覧取得 レスポンスDto
 *
 * @author SCSK
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class IfaServiceNowBranchAndBrokerInfomationA004ResponseDto extends IfaServiceNowDataList {
    
    private static final long serialVersionUID = 4160813616144156129L;
    /**
     * 営業員リスト
     */
    private List<IfaServiceNowEmployeeDto> employeeList;
    
}
