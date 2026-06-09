package com.sbisec.helios.gw.companyEmployeeMenu.transactionData.edelivConsentData.controller;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.sbibits.earth.model.DataList;
import com.sbibits.earth.servlet.annotation.ResponseJson;
import com.sbibits.earth.servlet.annotation.ScreenId;
import com.sbibits.earth.servlet.annotation.SessionCheckTarget;
import com.sbibits.earth.util.StringUtil;
import com.sbibits.earth.util.json.JsonConverter;
import com.sbisec.helios.ap.common.enums.ErrorLevel;
import com.sbisec.helios.ap.common.enums.ipopo.ErrorType;
import com.sbisec.helios.ap.common.util.ApiRequestUtil;
import com.sbisec.helios.ap.common.util.IfaCommonUtil;
import com.sbisec.helios.ap.companyEmployeeMenu.transactionData.edelivConsentData.dto.IfaEdelivConsentDataListA007RequestDto;
import com.sbisec.helios.ap.companyEmployeeMenu.transactionData.edelivConsentData.dto.IfaEdelivConsentDataListA007ResponseDto;
import com.sbisec.helios.ap.companyEmployeeMenu.transactionData.edelivConsentData.dto.IfaEdelivConsentDataListCheckResultSetDto;
import com.sbisec.helios.ap.companyEmployeeMenu.transactionData.edelivConsentData.dto.IfaEdelivConsentDataListEdelivConsentDataDto;
import com.sbisec.helios.gw.common.controller.BaseController;
import com.sbisec.helios.gw.common.util.CheckUtil;
import com.sbisec.helios.gw.companyEmployeeMenu.transactionData.edelivConsentData.form.IfaEdelivConsentDataListA002ApiResponse;
import com.sbisec.helios.gw.companyEmployeeMenu.transactionData.edelivConsentData.form.IfaEdelivConsentDataListA007ApiRequest;
import com.sbisec.helios.gw.companyEmployeeMenu.transactionData.edelivConsentData.form.IfaEdelivConsentDataListA007ApiResponse;
import com.sbisec.helios.gw.companyEmployeeMenu.transactionData.edelivConsentData.form.IfaEdelivConsentDataListCheckResultSetApiResponse;
import com.sbisec.helios.gw.companyEmployeeMenu.transactionData.edelivConsentData.form.IfaEdelivConsentDataListEdelivConsentDataApiRequest;
import com.sbisec.helios.gw.companyEmployeeMenu.transactionData.edelivConsentData.form.IfaEdelivConsentDataListEdelivConsentDataApiResponse;

/**
 * 画面ID：SUB0504_02-01
 * 画面名：電子交付同意データ一覧
 */
@RestController
@SessionCheckTarget(type = "httpSession")
@ScreenId(groupId = "MAIN05", id = "SUB0504_02-01", screenNumber = StringUtil.EMPTY_STRING)
public class IfaEdelivConsentDataListController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(IfaEdelivConsentDataListController.class);

    private static final String ERRORS_SHEET_NOT_EXIST = "errors.sheetNotExist";

    private static final String SHEET_NAME = "電子交付同意";

    private static final int LIMIT = 2000;

    private static final int COLUMN_COUNT = 4;

    private JsonConverter jc = JsonConverter.getInstance();

    /**
     * A002 確認
     *
     * @param file アップロードファイル
     * @return 確認結果
     * @throws Exception 確認処理で例外が発生した場合
     */
    @PostMapping(value = "/companyEmployeeMenu/transactionData/ifaEdelivConsentDataListConfirmA002")
    @ResponseBody
    @ResponseJson
    public String confirmA002(@RequestParam("uploadFile") MultipartFile file) throws Exception {

        final long start = System.currentTimeMillis();
        logger.debug("IfaEdelivConsentDataListController.confirmA002 >> {}", hashCode());

        String errorMessage = StringUtil.EMPTY_STRING;
        DataList<IfaEdelivConsentDataListA002ApiResponse> apiResDataList = new DataList<>();
        List<IfaEdelivConsentDataListA002ApiResponse> apiResList = new ArrayList<>();
        IfaEdelivConsentDataListA002ApiResponse apiRes = new IfaEdelivConsentDataListA002ApiResponse();
        List<IfaEdelivConsentDataListEdelivConsentDataApiResponse> edelivConsentDataList = new ArrayList<>();

        if (!checkFileName(file.getOriginalFilename())) {
            errorMessage = getMessage(ERRORS_NOT_EXCEL_FILE, new String[] {});
            apiResDataList = IfaCommonUtil.createDataList(apiResList, ErrorLevel.FATAL, ERRORS_NOT_EXCEL_FILE,
                    errorMessage);
            return jc.toString(apiResDataList);
        }

        try (InputStream is = file.getInputStream()) {
            Workbook workbook = WorkbookFactory.create(is);
            Sheet sheet = workbook.getSheet(SHEET_NAME);
            if (sheet == null) {
                errorMessage = getMessage(ERRORS_SHEET_NOT_EXIST, new String[] { SHEET_NAME });
                apiResDataList = IfaCommonUtil.createDataList(apiResList, ErrorLevel.FATAL, ERRORS_SHEET_NOT_EXIST,
                        errorMessage);
                return jc.toString(apiResDataList);
            }

            int dataRowCount = getDataRowCount(sheet);
            if (dataRowCount < 1) {
                errorMessage = getMessage(INFO_ORDERED_DATA_NOT_EXIST, new String[] { "明細" });
                apiResDataList = IfaCommonUtil.createDataList(apiResList, ErrorLevel.INFO, INFO_ORDERED_DATA_NOT_EXIST,
                        errorMessage);
                return jc.toString(apiResDataList);
            }
            if (dataRowCount > LIMIT) {
                errorMessage = getMessage(ERRORS_MAX_UPLOAD, new String[] { String.valueOf(LIMIT) });
                apiResDataList = IfaCommonUtil.createDataList(apiResList, ErrorLevel.FATAL, ERRORS_MAX_UPLOAD,
                        errorMessage);
                return jc.toString(apiResDataList);
            }

            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue;
                }
                String[] arr = toArray(row);
                if (arr == null) {
                    continue;
                }
                edelivConsentDataList.add(validateRow(arr));
            }

            apiRes.setEdelivConsentDataList(edelivConsentDataList);
            apiResList.add(apiRes);

        } catch (EncryptedDocumentException e) {
            logger.info("EncryptedDocumentException occured.", e);
            errorMessage = getMessage(ERRORS_HAS_PASSWORD, new String[] {});
            apiResDataList = IfaCommonUtil.createDataList(apiResList, ErrorLevel.FATAL, ERRORS_HAS_PASSWORD,
                    errorMessage);
            return jc.toString(apiResDataList);
        } catch (Exception e) {
            logger.error("Exception occured.");
            logger.info("Exception occured.", e);
            errorMessage = getMessage(ERRORS_SERVERERROR, new String[] {});
            apiResDataList = IfaCommonUtil.createDataList(apiResList, ErrorLevel.FATAL, ERRORS_SERVERERROR,
                    errorMessage);
            return jc.toString(apiResDataList);
        }

        logger.debug("cost -> {}", (System.currentTimeMillis() - start));
        apiResDataList = IfaCommonUtil.createDataList(apiResList, ErrorLevel.SUCCESS, ErrorLevel.SUCCESS.name(), "");
        return jc.toString(apiResDataList);
    }

    /**
     * A007 登録
     *
     * @param apiReq リクエスト
     * @return 登録結果
     * @throws Exception 登録処理で例外が発生した場合
     */
    @PostMapping(value = "/companyEmployeeMenu/transactionData/ifaEdelivConsentDataListRegisterA007")
    @ResponseJson
    @ResponseBody
    public String registerA007(@RequestBody IfaEdelivConsentDataListA007ApiRequest apiReq) throws Exception {

        final long start = System.currentTimeMillis();
        logger.debug("IfaEdelivConsentDataListController.registerA007 >> {}", hashCode());

        IfaEdelivConsentDataListA007RequestDto appReq = new IfaEdelivConsentDataListA007RequestDto();
        List<IfaEdelivConsentDataListEdelivConsentDataDto> edelivConsentDataList = new ArrayList<>();
        if (apiReq.getEdelivConsentDataList() != null) {
            for (IfaEdelivConsentDataListEdelivConsentDataApiRequest row : apiReq.getEdelivConsentDataList()) {
                IfaEdelivConsentDataListEdelivConsentDataDto dto = new IfaEdelivConsentDataListEdelivConsentDataDto();
                BeanUtils.copyProperties(dto, row);
                edelivConsentDataList.add(dto);
            }
        }
        appReq.setEdelivConsentDataList(edelivConsentDataList);

        DataList<IfaEdelivConsentDataListA007ResponseDto> appRes = ApiRequestUtil.invoke(
                "cmpIfaEdelivConsentDataListService",
                "registerA007",
                new TypeReference<DataList<IfaEdelivConsentDataListA007ResponseDto>>() { },
                appReq);

        DataList<IfaEdelivConsentDataListA007ApiResponse> apiResDataList = new DataList<>();
        BeanUtils.copyProperties(apiResDataList, appRes);

        if (appRes.getDataList() != null && !appRes.getDataList().isEmpty()) {
            IfaEdelivConsentDataListA007ResponseDto appResDto = appRes.getDataList().get(0);
            IfaEdelivConsentDataListA007ApiResponse apiResDto = new IfaEdelivConsentDataListA007ApiResponse();
            List<IfaEdelivConsentDataListEdelivConsentDataApiResponse> apiRowList = new ArrayList<>();
            if (appResDto.getEdelivConsentDataList() != null) {
                for (IfaEdelivConsentDataListEdelivConsentDataDto row : appResDto.getEdelivConsentDataList()) {
                    IfaEdelivConsentDataListEdelivConsentDataApiResponse apiRow =
                            new IfaEdelivConsentDataListEdelivConsentDataApiResponse();
                    BeanUtils.copyProperties(apiRow, row);
                    if (row.getCheckResultSetList() != null) {
                        List<IfaEdelivConsentDataListCheckResultSetApiResponse> checkResultSetList = new ArrayList<>();
                        for (IfaEdelivConsentDataListCheckResultSetDto checkResultSet : row.getCheckResultSetList()) {
                            IfaEdelivConsentDataListCheckResultSetApiResponse apiCheckResultSet =
                                    new IfaEdelivConsentDataListCheckResultSetApiResponse();
                            BeanUtils.copyProperties(apiCheckResultSet, checkResultSet);
                            checkResultSetList.add(apiCheckResultSet);
                        }
                        apiRow.setCheckResultSetList(checkResultSetList);
                    }
                    apiRowList.add(apiRow);
                }
            }
            apiResDto.setEdelivConsentDataList(apiRowList);
            List<IfaEdelivConsentDataListA007ApiResponse> apiResList = new ArrayList<>();
            apiResList.add(apiResDto);
            apiResDataList.setDataList(apiResList);
        }

        logger.debug("cost -> {}", (System.currentTimeMillis() - start));
        return jc.toString(apiResDataList);
    }

    private IfaEdelivConsentDataListEdelivConsentDataApiResponse validateRow(String[] nextLine) throws Exception {

        String butenCode = nextLine[0];
        String accountNumber = nextLine[1];
        String edelivAgreementDate = nextLine[2];
        String edelivAgreementKbn = nextLine[3];

        if (!StringUtil.isNullOrEmpty(butenCode)) {
            butenCode = butenCode.toUpperCase();
        }

        IfaEdelivConsentDataListEdelivConsentDataApiResponse row =
                new IfaEdelivConsentDataListEdelivConsentDataApiResponse();
        row.setButenCode(butenCode);
        row.setAccountNumber(accountNumber);
        row.setEdelivAgreementDate(edelivAgreementDate);
        row.setEdelivAgreementKbn(edelivAgreementKbn);

        List<IfaEdelivConsentDataListCheckResultSetApiResponse> checkResultSetList = new ArrayList<>();
        String butenCodeMsg = null;
        String accountNumberMsg = null;
        String edelivAgreementDateMsg = null;
        String edelivAgreementKbnMsg = null;
        String existMsg = null;

        if (StringUtil.isNullOrEmpty(butenCode)) {
            butenCodeMsg = getMessage(ERRORS_REQUIRED, new String[] { "部店" });
        } else if (butenCode.length() > 3) {
            butenCodeMsg = getMessage(ERRORS_MAX_SIZE, new String[] { "部店", "3" });
        } else if (!isAlphaNumber(butenCode)) {
            butenCodeMsg = getMessage(ERRORS_TYPE, new String[] { "部店", "半角英数字" });
        }

        if (StringUtil.isNullOrEmpty(accountNumber)) {
            accountNumberMsg = getMessage(ERRORS_REQUIRED, new String[] { "口座番号" });
        } else if (accountNumber.length() > 6) {
            accountNumberMsg = getMessage(ERRORS_MAX_SIZE, new String[] { "口座番号", "6" });
        } else if (!isNumber(accountNumber)) {
            accountNumberMsg = getMessage(ERRORS_TYPE, new String[] { "口座番号", "半角数字" });
        }

        if (!StringUtil.isNullOrEmpty(edelivAgreementDate) && !"-".equals(edelivAgreementDate)) {
            if (!CheckUtil.checkDate(edelivAgreementDate)) {
                edelivAgreementDateMsg = getMessage(ERRORS_DATE_SPECIFY_FORMAT,
                        new String[] { "電子交付承諾日付", "YYYY/MM/DD" });
            }
        }

        if (StringUtil.isNullOrEmpty(edelivAgreementKbn)) {
            edelivAgreementKbnMsg = getMessage(ERRORS_REQUIRED, new String[] { "電子交付承諾区分" });
        } else if (!"0".equals(edelivAgreementKbn) && !"1".equals(edelivAgreementKbn)) {
            edelivAgreementKbnMsg = getMessage(ERRORS_NUMBER_FORMAT,
                    new String[] { "電子交付承諾区分", "0、1" });
        }

        if (butenCodeMsg == null && accountNumberMsg == null) {
            int count = ApiRequestUtil.invoke(
                    "cmpIfaEdelivConsentDataListService",
                    "countCustomerAttributeInfo",
                    new TypeReference<Integer>() { },
                    butenCode,
                    accountNumber);
            if (count == null || count == 0) {
                existMsg = getMessage(ERRORS_BUTEN_ACCOUNT_NOT_EXIST, new String[] { butenCode, accountNumber });
            }
        }

        addCheckResultSet(checkResultSetList, butenCodeMsg);
        addCheckResultSet(checkResultSetList, accountNumberMsg);
        addCheckResultSet(checkResultSetList, edelivAgreementDateMsg);
        addCheckResultSet(checkResultSetList, edelivAgreementKbnMsg);
        addCheckResultSet(checkResultSetList, existMsg);

        if (checkResultSetList.isEmpty()) {
            row.setCheckResult(ErrorType.OK.getLabel());
        } else {
            row.setCheckResult(ErrorType.ERROR.getLabel());
            StringBuilder displayMessage = new StringBuilder();
            for (IfaEdelivConsentDataListCheckResultSetApiResponse checkResultSet : checkResultSetList) {
                if (!StringUtil.isNullOrEmpty(checkResultSet.getMsg())) {
                    if (displayMessage.length() > 0) {
                        displayMessage.append('\n');
                    }
                    displayMessage.append(checkResultSet.getMsg().replace("<br>", "\n").replace("<br/>", "\n"));
                }
            }
            row.setErrorMessage(displayMessage.toString());
        }
        row.setCheckResultSetList(checkResultSetList);
        return row;
    }

    private void addCheckResultSet(List<IfaEdelivConsentDataListCheckResultSetApiResponse> checkResultSetList,
            String msg) {
        if (!StringUtil.isNullOrEmpty(msg)) {
            IfaEdelivConsentDataListCheckResultSetApiResponse checkResultSet =
                    new IfaEdelivConsentDataListCheckResultSetApiResponse();
            checkResultSet.setMsg(msg);
            checkResultSet.setStatus(ErrorType.ERROR.getLabel());
            checkResultSetList.add(checkResultSet);
        }
    }

    private int getDataRowCount(Sheet sheet) {
        int count = 0;
        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                continue;
            }
            String[] arr = toArray(row);
            if (arr == null) {
                continue;
            }
            count++;
            if (count > LIMIT) {
                return count;
            }
        }
        return count;
    }

    private String[] toArray(Row row) {
        String[] arr = new String[COLUMN_COUNT];
        DecimalFormat df = new DecimalFormat("#.#########");
        boolean isNull = true;
        for (int i = 0; i < arr.length; i++) {
            arr[i] = StringUtil.EMPTY_STRING;
            Cell cell = row.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            if (cell != null) {
                isNull = false;
                CellType cellType = cell.getCellType();
                String value = StringUtil.EMPTY_STRING;
                switch (cellType) {
                case NUMERIC:
                    value = df.format(cell.getNumericCellValue());
                    break;
                case STRING:
                    value = cell.getStringCellValue();
                    break;
                default:
                    value = StringUtil.EMPTY_STRING;
                    break;
                }
                arr[i] = value;
            }
        }
        if (isNull) {
            return null;
        }
        return arr;
    }

    private boolean isNumber(String str) {
        Pattern pattern = Pattern.compile("^[0-9]*$");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    private boolean isAlphaNumber(String str) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9]*$");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    private boolean checkFileName(String fileName) {
        if (StringUtil.isNullOrEmpty(fileName)) {
            return false;
        }
        fileName = fileName.toLowerCase();
        int index = fileName.lastIndexOf(".");
        if (index < 0) {
            return false;
        }
        String ext = fileName.substring(index + 1);
        return "xlsx".equals(ext) || "xls".equals(ext);
    }

    @Override
    protected String getFirstViewName() {
        return null;
    }

}
