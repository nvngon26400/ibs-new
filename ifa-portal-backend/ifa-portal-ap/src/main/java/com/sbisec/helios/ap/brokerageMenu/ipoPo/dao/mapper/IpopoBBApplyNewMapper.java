
package com.sbisec.helios.ap.brokerageMenu.ipoPo.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sbisec.helios.ap.brokerageMenu.ipoPo.model.IpopoBBCustomerInfoModel;

@Mapper
public interface IpopoBBApplyNewMapper {
    
    public IpopoBBCustomerInfoModel getIpopoBBCustomerInfo(@Param("butenCode") String butenCode,
            @Param("accountNumber") String accountNumber) throws Exception;
    
}
