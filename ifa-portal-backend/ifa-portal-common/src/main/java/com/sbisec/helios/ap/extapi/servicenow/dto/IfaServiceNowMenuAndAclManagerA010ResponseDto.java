package com.sbisec.helios.ap.extapi.servicenow.dto;

import java.util.List;

import com.sbisec.helios.ap.extapi.servicenow.dto.common.IfaServiceNowDataList;
import com.sbisec.helios.ap.extapi.servicenow.dto.common.IfaServiceNowMenuDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * メニュー取得 レスポンスDto
 *
 * @author SCSK
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class IfaServiceNowMenuAndAclManagerA010ResponseDto extends IfaServiceNowDataList {
    
    private static final long serialVersionUID = 8269255499973387431L;
    /**
     * メニューリスト
     */
    private List<IfaServiceNowMenuDto> menuList;
    
}
