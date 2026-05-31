package com.sbisec.helios.ap.companyEmployeeMenu.transactionData.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IfaIpopoElecApprovalAgreementUploadMapper {

    int upsertAgreement(
        @Param("butenCode") String butenCode,
        @Param("accountNumber") String accountNumber,
        @Param("agreementDate") String agreementDate,
        @Param("agreementKbn") String agreementKbn,
        @Param("userId") String userId
    ) throws Exception;
}

