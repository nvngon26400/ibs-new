
package com.sbisec.helios.ap.brokerageMenu.ipoPo.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sbisec.helios.ap.brokerageMenu.ipoPo.model.BBInvestorAttModel;

@Mapper
public interface BBInvestorAttMapper {

    public List<BBInvestorAttModel> getBBInvestorAttInfoList(@Param("bbProductCode") String bbProductCode,
            @Param("bbPresentationFrom") String bbPresentationFrom) throws Exception;
}