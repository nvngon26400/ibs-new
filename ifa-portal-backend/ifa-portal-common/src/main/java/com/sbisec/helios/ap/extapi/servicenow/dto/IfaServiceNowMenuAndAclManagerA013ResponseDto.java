package com.sbisec.helios.ap.extapi.servicenow.dto;

import java.util.List;

import com.sbisec.helios.ap.extapi.servicenow.dto.common.IfaServiceNowDataList;
import com.sbisec.helios.ap.extapi.servicenow.dto.common.IfaServiceNowMenuDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * ユーザ利用可能メニュー一覧取得 レスポンスDto
 *
 * @author SCSK
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class IfaServiceNowMenuAndAclManagerA013ResponseDto extends IfaServiceNowDataList {
    
    private static final long serialVersionUID = 872160754415366974L;
    /**
     * メニューリスト
     */
    private List<IfaServiceNowMenuDto> menuList;
    
}
