package com.sbisec.helios.ap.companyEmployeeMenu.transactionData.edelivConsentData.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sbibits.earth.model.DataList;
import com.sbisec.helios.ap.common.enums.ErrorLevel;
import com.sbisec.helios.ap.common.enums.ipopo.ErrorType;
import com.sbisec.helios.ap.common.model.UserAccount;
import com.sbisec.helios.ap.common.util.IfaCommonUtil;
import com.sbisec.helios.ap.companyEmployeeMenu.transactionData.edelivConsentData.dao.IfaEdelivConsentDataListDao;
import com.sbisec.helios.ap.companyEmployeeMenu.transactionData.edelivConsentData.dao.model.IfaEdelivConsentDataListSql002RequestModel;
import com.sbisec.helios.ap.companyEmployeeMenu.transactionData.edelivConsentData.dto.IfaEdelivConsentDataListA007RequestDto;
import com.sbisec.helios.ap.companyEmployeeMenu.transactionData.edelivConsentData.dto.IfaEdelivConsentDataListA007ResponseDto;
import com.sbisec.helios.ap.companyEmployeeMenu.transactionData.edelivConsentData.dto.IfaEdelivConsentDataListEdelivConsentDataDto;
import com.sbisec.helios.ap.companyEmployeeMenu.transactionData.edelivConsentData.service.IfaEdelivConsentDataListService;

/**
 * 画面ID：SUB0504_02-01
 * 画面名：電子交付同意データ一覧
 */
@Component(value = "cmpIfaEdelivConsentDataListService")
public class IfaEdelivConsentDataListServiceImpL implements IfaEdelivConsentDataListService {

    private static final Logger logger = LoggerFactory.getLogger(IfaEdelivConsentDataListServiceImpL.class);

    private static final String INFO_ORDERED_DATA_NOT_EXIST = "info.orderedDataNotExist";

    private static final String INFO_UPLOAD_INSERT_COMPLETED = "info.uploadInsertCompleted";

    private static final String CHECK_RESULT_REGISTERED = "登録済";

    @Autowired
    private IfaEdelivConsentDataListDao dao;

    @Override
    public Integer countCustomerAttributeInfo(String butenCode, String accountNumber) throws Exception {
        logger.debug("IfaEdelivConsentDataListServiceImpL.countCustomerAttributeInfo");
        return dao.countCustomerAttributeInfo(butenCode, accountNumber);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DataList<IfaEdelivConsentDataListA007ResponseDto> registerA007(
            IfaEdelivConsentDataListA007RequestDto dtoReq) throws Exception {

        logger.debug("IfaEdelivConsentDataListServiceImpL.registerA007");

        List<IfaEdelivConsentDataListEdelivConsentDataDto> allRows = dtoReq.getEdelivConsentDataList();
        if (allRows == null) {
            allRows = new ArrayList<>();
        }

        List<IfaEdelivConsentDataListEdelivConsentDataDto> okRows = allRows.stream()
                .filter(row -> ErrorType.OK.getLabel().equals(row.getCheckResult()))
                .collect(Collectors.toList());

        IfaEdelivConsentDataListA007ResponseDto dtoRes = new IfaEdelivConsentDataListA007ResponseDto();
        dtoRes.setEdelivConsentDataList(allRows);

        if (okRows.isEmpty()) {
            String msg = IfaCommonUtil.getMessage(INFO_ORDERED_DATA_NOT_EXIST, new String[] { "OK" });
            List<IfaEdelivConsentDataListA007ResponseDto> resList = new ArrayList<>();
            resList.add(dtoRes);
            return IfaCommonUtil.createDataList(
                    resList,
                    ErrorLevel.INFO,
                    INFO_ORDERED_DATA_NOT_EXIST,
                    msg);
        }

        UserAccount userAccount = IfaCommonUtil.getUserAccount();
        List<IfaEdelivConsentDataListSql002RequestModel> registerList = new ArrayList<>();
        for (IfaEdelivConsentDataListEdelivConsentDataDto row : okRows) {
            IfaEdelivConsentDataListSql002RequestModel model = new IfaEdelivConsentDataListSql002RequestModel();
            model.setButenCode(row.getButenCode());
            model.setAccountNumber(row.getAccountNumber());
            model.setEdelivAgreementDate(row.getEdelivAgreementDate());
            model.setEdelivAgreementKbn(row.getEdelivAgreementKbn());
            model.setUserId(userAccount.getUserId());
            registerList.add(model);
        }

        dao.mergeEdelivAgreementInfo(registerList);

        int insertCount = 0;
        for (IfaEdelivConsentDataListEdelivConsentDataDto row : allRows) {
            if (ErrorType.OK.getLabel().equals(row.getCheckResult())) {
                row.setCheckResult(CHECK_RESULT_REGISTERED);
                insertCount++;
            }
        }

        String msg = IfaCommonUtil.getMessage(INFO_UPLOAD_INSERT_COMPLETED, new String[] { String.valueOf(insertCount) });
        List<IfaEdelivConsentDataListA007ResponseDto> resList = new ArrayList<>();
        resList.add(dtoRes);
        return IfaCommonUtil.createDataList(
                resList,
                ErrorLevel.INFO,
                INFO_UPLOAD_INSERT_COMPLETED,
                msg);
    }

}
