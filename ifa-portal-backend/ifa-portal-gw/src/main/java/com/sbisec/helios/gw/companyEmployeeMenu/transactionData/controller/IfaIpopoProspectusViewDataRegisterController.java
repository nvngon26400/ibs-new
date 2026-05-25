package com.sbisec.helios.gw.companyEmployeeMenu.transactionData.controller;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.Strings;
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
import com.sbisec.helios.ap.common.model.UserAccount;
import com.sbisec.helios.ap.common.util.ApiRequestUtil;
import com.sbisec.helios.ap.common.util.IfaCommonUtil;
import com.sbisec.helios.ap.companyEmployeeMenu.transactionData.model.IpopoProspectusViewDataCheckResultModel;
import com.sbisec.helios.ap.companyEmployeeMenu.transactionData.model.IpopoProspectusViewDataUploadModel;
import com.sbisec.helios.gw.common.controller.BaseController;
import com.sbisec.helios.gw.common.util.CheckUtil;
import com.sbisec.helios.gw.companyEmployeeMenu.transactionData.form.IfaIpopoProspectusViewDataRegisterA002ApiResponse;
import com.sbisec.helios.gw.companyEmployeeMenu.transactionData.form.IfaIpopoProspectusViewDataRegisterA003ApiRequest;
import com.sbisec.helios.gw.companyEmployeeMenu.transactionData.form.IfaIpopoProspectusViewDataRegisterA003ApiResponse;
import com.sbisec.helios.gw.companyEmployeeMenu.transactionData.form.IfaIpopoProspectusViewDataRegisterCheckResultSetApiResponse;
import com.sbisec.helios.gw.companyEmployeeMenu.transactionData.form.IfaIpopoProspectusViewDataRegisterListApiRequest;
import com.sbisec.helios.gw.companyEmployeeMenu.transactionData.form.IfaIpopoProspectusViewDataRegisterListApiResponse;

/**
 * 画面ID：SUB0504_03-01
 * 画面名：目論見書閲覧データ登録
 */
@RestController
@SessionCheckTarget(type = "httpSession")
@ScreenId(groupId = "MAIN05", id = "SUB0504_03-01", screenNumber = StringUtil.EMPTY_STRING)
public class IfaIpopoProspectusViewDataRegisterController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(IfaIpopoProspectusViewDataRegisterController.class);

    private final JsonConverter jc = JsonConverter.getInstance();

    private static final String SHEET_NAME = "目論見書閲覧";
    private static final String DETAIL_LABEL = "明細";
    private static final int LIMIT = 2000;
    private static final String SCREEN_ID = "SUB0504";
    private static final String DEFAULT_DOCUMENT_ID = "00000";
    private static final String DEFAULT_VERSION_NUMBER = "000000";
    private static final String REGISTERED_LABEL = "登録済";
    private static final String ERRORS_BB_ACCEPT_INFO_NOT_EXIST = "errors.bbAcceptInfoNotExist";
    private static final String READ_TIME_FORMAT = "yyyy/mm/dd hh:mm:ss";

    private static final Pattern READ_TIME_PATTERN = Pattern
            .compile("^\\d{4}/\\d{2}/\\d{2} \\d{2}:\\d{2}:\\d{2}$");

    /**
     * A002 確認押下
     */
    @PostMapping(value = "/companyEmployeeMenu/transactionData/ifaIpopoProspectusViewDataRegisterConfirmA002")
    @ResponseBody
    @ResponseJson
    public String confirmA002(@RequestParam("uploadFile") MultipartFile file) throws Exception {

        final long start = System.currentTimeMillis();
        logger.debug("IfaIpopoProspectusViewDataRegisterController.confirmA002 >> {}", hashCode());

        UserAccount userAccount = IfaCommonUtil.getUserAccount();
        List<IfaIpopoProspectusViewDataRegisterA002ApiResponse> apiResList = new ArrayList<>();
        IfaIpopoProspectusViewDataRegisterA002ApiResponse apiRes = new IfaIpopoProspectusViewDataRegisterA002ApiResponse();
        List<IfaIpopoProspectusViewDataRegisterListApiResponse> dataList = new ArrayList<>();

        if (!checkFileName(file.getOriginalFilename())) {
            DataList<IfaIpopoProspectusViewDataRegisterA002ApiResponse> apiResDataList = IfaCommonUtil.createDataList(
                    apiResList, ErrorLevel.FATAL, ERRORS_NOT_EXCEL_FILE, getMessage(ERRORS_NOT_EXCEL_FILE, new String[] {}));
            return jc.toString(apiResDataList);
        }

        try (InputStream is = file.getInputStream()) {
            Workbook workbook = WorkbookFactory.create(is);
            Sheet sheet = workbook.getSheet(SHEET_NAME);
            if (sheet == null) {
                DataList<IfaIpopoProspectusViewDataRegisterA002ApiResponse> apiResDataList = IfaCommonUtil.createDataList(
                        apiResList, ErrorLevel.FATAL, ERRORS_SHEET_NOT_EXIST,
                        getMessage(ERRORS_SHEET_NOT_EXIST, new String[] { SHEET_NAME }));
                return jc.toString(apiResDataList);
            }

            int dataRowCount = getDataRowCount(sheet);
            if (dataRowCount < 1) {
                DataList<IfaIpopoProspectusViewDataRegisterA002ApiResponse> apiResDataList = IfaCommonUtil.createDataList(
                        apiResList, ErrorLevel.INFO, INFO_ORDERED_DATA_NOT_EXIST,
                        getMessage(INFO_ORDERED_DATA_NOT_EXIST, new String[] { DETAIL_LABEL }));
                return jc.toString(apiResDataList);
            }
            if (dataRowCount >= LIMIT) {
                DataList<IfaIpopoProspectusViewDataRegisterA002ApiResponse> apiResDataList = IfaCommonUtil.createDataList(
                        apiResList, ErrorLevel.FATAL, ERRORS_MAX_UPLOAD,
                        getMessage(ERRORS_MAX_UPLOAD, new String[] { String.valueOf(LIMIT) }));
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
                IpopoProspectusViewDataUploadModel csvModel = new IpopoProspectusViewDataUploadModel();
                IpopoProspectusViewDataCheckResultModel msgModel = new IpopoProspectusViewDataCheckResultModel();
                parseRow(arr, csvModel);
                validateRow(csvModel, msgModel, userAccount);
                IfaIpopoProspectusViewDataRegisterListApiResponse rowRes = new IfaIpopoProspectusViewDataRegisterListApiResponse();
                setApiResponse(rowRes, csvModel, msgModel);
                dataList.add(rowRes);
            }
            apiRes.setProspectusViewDataList(dataList);
            apiResList.add(apiRes);

        } catch (EncryptedDocumentException e) {
            logger.info("EncryptedDocumentException occured.", e);
            DataList<IfaIpopoProspectusViewDataRegisterA002ApiResponse> apiResDataList = IfaCommonUtil.createDataList(
                    apiResList, ErrorLevel.FATAL, ERRORS_HAS_PASSWORD, getMessage(ERRORS_HAS_PASSWORD, new String[] {}));
            return jc.toString(apiResDataList);
        } catch (Exception e) {
            logger.error("Exception occured.", e);
            DataList<IfaIpopoProspectusViewDataRegisterA002ApiResponse> apiResDataList = IfaCommonUtil.createDataList(
                    apiResList, ErrorLevel.FATAL, ERRORS_SERVERERROR, getMessage(ERRORS_SERVERERROR, new String[] {}));
            return jc.toString(apiResDataList);
        }

        logger.debug("cost -> {}", (System.currentTimeMillis() - start));
        DataList<IfaIpopoProspectusViewDataRegisterA002ApiResponse> apiResDataList = IfaCommonUtil.createDataList(
                apiResList, ErrorLevel.SUCCESS, ErrorLevel.SUCCESS.name(), "");
        return jc.toString(apiResDataList);
    }

    /**
     * A003 登録押下
     */
    @PostMapping(value = "/companyEmployeeMenu/transactionData/ifaIpopoProspectusViewDataRegisterRegisterA003")
    @ResponseBody
    @ResponseJson
    public String registerA003(@RequestBody IfaIpopoProspectusViewDataRegisterA003ApiRequest apiReq) throws Exception {

        final long start = System.currentTimeMillis();
        logger.debug("IfaIpopoProspectusViewDataRegisterController.registerA003 >> {}", hashCode());

        UserAccount userAccount = IfaCommonUtil.getUserAccount();
        List<IfaIpopoProspectusViewDataRegisterA003ApiResponse> apiResList = new ArrayList<>();
        IfaIpopoProspectusViewDataRegisterA003ApiResponse apiRes = new IfaIpopoProspectusViewDataRegisterA003ApiResponse();
        List<IfaIpopoProspectusViewDataRegisterListApiResponse> responseList = new ArrayList<>();

        List<IfaIpopoProspectusViewDataRegisterListApiRequest> requestList = apiReq.getProspectusViewDataList();
        List<IpopoProspectusViewDataUploadModel> registerList = new ArrayList<>();

        if (requestList != null) {
            for (IfaIpopoProspectusViewDataRegisterListApiRequest req : requestList) {
                if (ErrorType.OK.getLabel().equals(req.getCheckResult())) {
                    IpopoProspectusViewDataUploadModel model = toUploadModel(req, userAccount);
                    registerList.add(model);
                }
            }
        }

        if (registerList.isEmpty()) {
            if (requestList != null) {
                for (IfaIpopoProspectusViewDataRegisterListApiRequest req : requestList) {
                    responseList.add(toListResponse(req));
                }
            }
            apiRes.setProspectusViewDataList(responseList);
            apiResList.add(apiRes);
            DataList<IfaIpopoProspectusViewDataRegisterA003ApiResponse> apiResDataList = IfaCommonUtil.createDataList(
                    apiResList, ErrorLevel.INFO, INFO_ORDERED_DATA_NOT_EXIST,
                    getMessage(INFO_ORDERED_DATA_NOT_EXIST, new String[] { "OK" }));
            return jc.toString(apiResDataList);
        }

        ApiRequestUtil.invoke("cmpIfaIpopoProspectusViewDataRegisterService", "mergeProspectusViewData",
                new TypeReference<Integer>() {}, registerList);

        int insertCount = 0;
        if (requestList != null) {
            for (IfaIpopoProspectusViewDataRegisterListApiRequest req : requestList) {
                IfaIpopoProspectusViewDataRegisterListApiResponse res = toListResponse(req);
                if (ErrorType.OK.getLabel().equals(req.getCheckResult())) {
                    res.setCheckResult(REGISTERED_LABEL);
                    insertCount++;
                }
                responseList.add(res);
            }
        }

        apiRes.setProspectusViewDataList(responseList);
        apiResList.add(apiRes);

        String msg = getMessage(INFO_UPLOAD_INSERT_COMPLETED, new String[] { String.valueOf(insertCount) });
        DataList<IfaIpopoProspectusViewDataRegisterA003ApiResponse> apiResDataList = IfaCommonUtil.createDataList(
                apiResList, ErrorLevel.INFO, INFO_UPLOAD_INSERT_COMPLETED, msg);

        logger.debug("cost -> {}", (System.currentTimeMillis() - start));
        return jc.toString(apiResDataList);
    }

    private void parseRow(String[] arr, IpopoProspectusViewDataUploadModel csvModel) {
        csvModel.setProductCode(getCell(arr, 0));
        csvModel.setPresentationFrom(getCell(arr, 1));
        String butenCode = getCell(arr, 2);
        if (!StringUtil.isNullOrEmpty(butenCode)) {
            butenCode = butenCode.toUpperCase();
        }
        csvModel.setButenCode(butenCode);
        csvModel.setAccountNumber(getCell(arr, 3));
        csvModel.setReadTime(getCell(arr, 4));
        String documentId = getCell(arr, 5);
        String versionNumber = getCell(arr, 6);
        csvModel.setDocumentId(StringUtil.isNullOrEmpty(documentId) ? DEFAULT_DOCUMENT_ID : documentId);
        csvModel.setVersionNumber(StringUtil.isNullOrEmpty(versionNumber) ? DEFAULT_VERSION_NUMBER : versionNumber);
    }

    private String getCell(String[] arr, int index) {
        if (arr.length <= index || arr[index] == null) {
            return StringUtil.EMPTY_STRING;
        }
        return arr[index].trim();
    }

    private void validateRow(IpopoProspectusViewDataUploadModel csvModel,
            IpopoProspectusViewDataCheckResultModel msgModel, UserAccount userAccount) throws Exception {

        uploadValidationCheck(csvModel, msgModel);

        if (hasInputError(msgModel)) {
            csvModel.setCheckResult(ErrorType.ERROR.getLabel());
            buildDisplayMessage(csvModel, msgModel);
            return;
        }

        String presentationFromYmd = csvModel.getPresentationFrom().replace("/", "");
        int bbCount = ApiRequestUtil.invoke("cmpIfaIpopoProspectusViewDataRegisterService", "getBbAcceptInfoCount",
                new TypeReference<Integer>() {}, csvModel.getProductCode(), presentationFromYmd,
                csvModel.getButenCode(), csvModel.getAccountNumber());

        if (bbCount == 0) {
            csvModel.setCheckResult(ErrorType.ERROR.getLabel());
            msgModel.setExistMsg(getMessage(ERRORS_BB_ACCEPT_INFO_NOT_EXIST,
                    new String[] { csvModel.getProductCode(), csvModel.getPresentationFrom(), csvModel.getButenCode(),
                            csvModel.getAccountNumber() }));
            msgModel.setExistMsgStatus(ErrorType.ERROR.getLabel());
            buildDisplayMessage(csvModel, msgModel);
            return;
        }

        csvModel.setCheckResult(ErrorType.OK.getLabel());
        csvModel.setCreateUser(userAccount.getUserId());
        csvModel.setUpdateUser(userAccount.getUserId());
        csvModel.setSysId(SCREEN_ID);
    }

    private void uploadValidationCheck(IpopoProspectusViewDataUploadModel csvModel,
            IpopoProspectusViewDataCheckResultModel msgModel) throws Exception {

        String productCode = csvModel.getProductCode();
        if (StringUtil.isNullOrEmpty(productCode)) {
            msgModel.setProductCode(getMessage(ERRORS_REQUIRED, new String[] { "銘柄コード" }));
            msgModel.setProductCodeStatus(ErrorType.ERROR.getLabel());
        } else if (productCode.length() > 12) {
            msgModel.setProductCode(getMessage(ERRORS_MAX_SIZE, new String[] { "銘柄コード", "12" }));
            msgModel.setProductCodeStatus(ErrorType.ERROR.getLabel());
        } else if (!isAlphaNumber(productCode)) {
            msgModel.setProductCode(getMessage(ERRORS_TYPE, new String[] { "銘柄コード", "半角英数字" }));
            msgModel.setProductCodeStatus(ErrorType.ERROR.getLabel());
        }

        String presentationFrom = csvModel.getPresentationFrom();
        if (StringUtil.isNullOrEmpty(presentationFrom)) {
            msgModel.setPresentationFrom(getMessage(ERRORS_REQUIRED, new String[] { "ブックビルディング申込期間（開始）" }));
            msgModel.setPresentationFromStatus(ErrorType.ERROR.getLabel());
        } else if (!CheckUtil.checkDate(presentationFrom)) {
            msgModel.setPresentationFrom(
                    getMessage(ERRORS_TYPE, new String[] { "ブックビルディング申込期間（開始）", "yyyy/mm/dd" }));
            msgModel.setPresentationFromStatus(ErrorType.ERROR.getLabel());
        }

        String butenCode = csvModel.getButenCode();
        if (StringUtil.isNullOrEmpty(butenCode)) {
            msgModel.setButenCode(getMessage(ERRORS_REQUIRED, new String[] { "部店" }));
            msgModel.setButenCodeStatus(ErrorType.ERROR.getLabel());
        } else if (butenCode.length() > 3) {
            msgModel.setButenCode(getMessage(ERRORS_MAX_SIZE, new String[] { "部店", "3" }));
            msgModel.setButenCodeStatus(ErrorType.ERROR.getLabel());
        } else if (!isAlphaNumber(butenCode)) {
            msgModel.setButenCode(getMessage(ERRORS_TYPE, new String[] { "部店", "半角英数字" }));
            msgModel.setButenCodeStatus(ErrorType.ERROR.getLabel());
        }

        String accountNumber = csvModel.getAccountNumber();
        if (StringUtil.isNullOrEmpty(accountNumber)) {
            msgModel.setAccountNumber(getMessage(ERRORS_REQUIRED, new String[] { "口座番号" }));
            msgModel.setAccountNumberStatus(ErrorType.ERROR.getLabel());
        } else if (accountNumber.length() > 6) {
            msgModel.setAccountNumber(getMessage(ERRORS_MAX_SIZE, new String[] { "口座番号", "6" }));
            msgModel.setAccountNumberStatus(ErrorType.ERROR.getLabel());
        } else if (!isNumber(accountNumber)) {
            msgModel.setAccountNumber(getMessage(ERRORS_TYPE, new String[] { "口座番号", "半角数字" }));
            msgModel.setAccountNumberStatus(ErrorType.ERROR.getLabel());
        }

        String readTime = csvModel.getReadTime();
        if (!StringUtil.isNullOrEmpty(readTime) && !isValidReadTime(readTime)) {
            msgModel.setReadTime(getMessage(ERRORS_DATE_SPECIFY_FORMAT, new String[] { "閲覧日時", READ_TIME_FORMAT }));
            msgModel.setReadTimeStatus(ErrorType.ERROR.getLabel());
        }
    }

    private boolean hasInputError(IpopoProspectusViewDataCheckResultModel msgModel) {
        return !StringUtil.isNullOrEmpty(msgModel.getProductCode()) || !StringUtil.isNullOrEmpty(msgModel.getPresentationFrom())
                || !StringUtil.isNullOrEmpty(msgModel.getButenCode()) || !StringUtil.isNullOrEmpty(msgModel.getAccountNumber())
                || !StringUtil.isNullOrEmpty(msgModel.getReadTime()) || !StringUtil.isNullOrEmpty(msgModel.getExistMsg());
    }

    private void buildDisplayMessage(IpopoProspectusViewDataUploadModel csvModel,
            IpopoProspectusViewDataCheckResultModel msgModel) {
        StringBuilder sb = new StringBuilder();
        appendMessage(sb, msgModel.getProductCode());
        appendMessage(sb, msgModel.getPresentationFrom());
        appendMessage(sb, msgModel.getButenCode());
        appendMessage(sb, msgModel.getAccountNumber());
        appendMessage(sb, msgModel.getReadTime());
        appendMessage(sb, msgModel.getExistMsg());
        csvModel.setDisplayMessage(sb.toString());
    }

    private void appendMessage(StringBuilder sb, String message) {
        if (!StringUtil.isNullOrEmpty(message)) {
            if (sb.length() > 0) {
                sb.append(LF);
            }
            sb.append(message);
        }
    }

    private void setApiResponse(IfaIpopoProspectusViewDataRegisterListApiResponse rowRes,
            IpopoProspectusViewDataUploadModel csvModel, IpopoProspectusViewDataCheckResultModel msgModel) {

        rowRes.setProductCode(csvModel.getProductCode());
        rowRes.setPresentationFrom(csvModel.getPresentationFrom());
        rowRes.setButenCode(csvModel.getButenCode());
        rowRes.setAccountNumber(csvModel.getAccountNumber());
        rowRes.setReadTime(csvModel.getReadTime());
        rowRes.setCheckResult(csvModel.getCheckResult());
        rowRes.setDisplayMessage(csvModel.getDisplayMessage());
        rowRes.setDocumentId(csvModel.getDocumentId());
        rowRes.setVersionNumber(csvModel.getVersionNumber());
        rowRes.setSysId(csvModel.getSysId());

        List<IfaIpopoProspectusViewDataRegisterCheckResultSetApiResponse> checkResultSetList = new ArrayList<>();
        addCheckResult(checkResultSetList, msgModel.getProductCode(), msgModel.getProductCodeStatus());
        addCheckResult(checkResultSetList, msgModel.getPresentationFrom(), msgModel.getPresentationFromStatus());
        addCheckResult(checkResultSetList, msgModel.getButenCode(), msgModel.getButenCodeStatus());
        addCheckResult(checkResultSetList, msgModel.getAccountNumber(), msgModel.getAccountNumberStatus());
        addCheckResult(checkResultSetList, msgModel.getReadTime(), msgModel.getReadTimeStatus());
        addCheckResult(checkResultSetList, msgModel.getExistMsg(), msgModel.getExistMsgStatus());
        rowRes.setCheckResultSetList(checkResultSetList);
    }

    private void addCheckResult(List<IfaIpopoProspectusViewDataRegisterCheckResultSetApiResponse> list, String msg,
            String status) {
        if (!StringUtil.isNullOrEmpty(msg)) {
            IfaIpopoProspectusViewDataRegisterCheckResultSetApiResponse item =
                    new IfaIpopoProspectusViewDataRegisterCheckResultSetApiResponse();
            item.setMsg(msg);
            item.setStatus(status);
            list.add(item);
        }
    }

    private IpopoProspectusViewDataUploadModel toUploadModel(IfaIpopoProspectusViewDataRegisterListApiRequest req,
            UserAccount userAccount) {
        IpopoProspectusViewDataUploadModel model = new IpopoProspectusViewDataUploadModel();
        model.setProductCode(req.getProductCode());
        model.setPresentationFrom(req.getPresentationFrom());
        model.setButenCode(req.getButenCode());
        model.setAccountNumber(req.getAccountNumber());
        model.setReadTime(req.getReadTime());
        model.setDocumentId(
                StringUtil.isNullOrEmpty(req.getDocumentId()) ? DEFAULT_DOCUMENT_ID : req.getDocumentId());
        model.setVersionNumber(
                StringUtil.isNullOrEmpty(req.getVersionNumber()) ? DEFAULT_VERSION_NUMBER : req.getVersionNumber());
        model.setCreateUser(userAccount.getUserId());
        model.setUpdateUser(userAccount.getUserId());
        model.setSysId(SCREEN_ID);
        return model;
    }

    private IfaIpopoProspectusViewDataRegisterListApiResponse toListResponse(
            IfaIpopoProspectusViewDataRegisterListApiRequest req) {
        IfaIpopoProspectusViewDataRegisterListApiResponse res = new IfaIpopoProspectusViewDataRegisterListApiResponse();
        res.setProductCode(req.getProductCode());
        res.setPresentationFrom(req.getPresentationFrom());
        res.setButenCode(req.getButenCode());
        res.setAccountNumber(req.getAccountNumber());
        res.setReadTime(req.getReadTime());
        res.setCheckResult(req.getCheckResult());
        res.setDisplayMessage(req.getDisplayMessage());
        res.setDocumentId(req.getDocumentId());
        res.setVersionNumber(req.getVersionNumber());
        return res;
    }

    private int getDataRowCount(Sheet sheet) {
        int count = 0;
        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                continue;
            }
            if (toArray(row) != null) {
                count++;
                if (count >= LIMIT) {
                    return count;
                }
            }
        }
        return count;
    }

    private String[] toArray(Row row) {
        String[] arr = new String[7];
        Cell cell;
        DecimalFormat df = new DecimalFormat("#.#########");
        boolean isNull = true;
        for (int i = 0; i < arr.length; i++) {
            arr[i] = StringUtil.EMPTY_STRING;
            cell = row.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            if (cell != null) {
                isNull = false;
                CellType cellType = cell.getCellType();
                String value = StringUtil.EMPTY_STRING;
                switch (cellType) {
                case NUMERIC:
                    if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
                        value = cell.getLocalDateTimeCellValue()
                                .format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
                    } else {
                        value = df.format(cell.getNumericCellValue());
                    }
                    break;
                case STRING:
                    value = cell.getStringCellValue();
                    break;
                default:
                    value = StringUtil.EMPTY_STRING;
                    break;
                }
                arr[i] = value == null ? StringUtil.EMPTY_STRING : value.trim();
            }
        }
        return isNull ? null : arr;
    }

    private boolean isNumber(String str) {
        Pattern pattern = Pattern.compile("^[0-9]*$");
        return pattern.matcher(str).matches();
    }

    private boolean isAlphaNumber(String str) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9]*$");
        return pattern.matcher(str).matches();
    }

    private boolean isValidReadTime(String readTime) {
        if (!READ_TIME_PATTERN.matcher(readTime).matches()) {
            return false;
        }
        try {
            LocalDateTime.parse(readTime, DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
            return true;
        } catch (Exception e) {
            return false;
        }
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
        return Strings.CS.equals("xlsx", ext) || Strings.CS.equals("xls", ext);
    }
}
