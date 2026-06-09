
package com.sbisec.helios.ap.brokerageMenu.ipoPo.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sbisec.helios.ap.brokerageMenu.ipoPo.model.IpopoUploadBBAcceptModel;
import com.sbisec.helios.ap.common.annotation.dao.EtintraMapper;

@EtintraMapper
public interface SRAcceptMapper {
    
    public int insertUploadIpopoBBApplyInfoToSrAccept(@Param("insertList") List<IpopoUploadBBAcceptModel> insertList);
}
