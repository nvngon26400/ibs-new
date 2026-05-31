package com.sbisec.helios.gw.companyEmployeeMenu.transactionData.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import com.sbisec.helios.ap.common.model.UserAccount;
import com.sbisec.helios.ap.common.util.ApiRequestUtil;
import com.sbisec.helios.ap.common.util.IfaCommonUtil;
import com.sbisec.helios.gw.common.controller.BaseController;
import com.sbisec.helios.gw.common.util.CheckUtil;
import com.sbisec.helios.gw.companyEmployeeMenu.transactionData.form.IfaIpopoElecApprovalAgreementUploadA004aApiResponse;
import com.sbisec.helios.gw.companyEmployeeMenu.transactionData.form.IfaIpopoElecApprovalAgreementUploadA004bApiRequest;
import com.sbisec.helios.gw.companyEmployeeMenu.transactionData.form.IfaIpopoElecApprovalAgreementUploadA007ApiRequest;
import com.sbisec.helios.gw.companyEmployeeMenu.transactionData.form.IfaIpopoElecApprovalAgreementUploadA007ApiResponse;
import com.sbisec.helios.gw.companyEmployeeMenu.transactionData.form.IfaIpopoElecApprovalAgreementUploadConfirmA002ApiResponse;
import com.sbisec.helios.gw.companyEmployeeMenu.transactionData.form.IfaIpopoElecApprovalAgreementUploadRowApiRequest;
import com.sbisec.helios.gw.companyEmployeeMenu.transactionData.form.IfaIpopoElecApprovalAgreementUploadRowApiResponse;

import jakarta.servlet.http.HttpServletResponse;

/**
 * 画面ID：SUB0504_02-01
 * 画面名：電子交付同意データ登録
 */
@RestController
@SessionCheckTarget(type = "httpSession")
@ScreenId(groupId = "MAIN05", id = "SUB0504_02-01", screenNumber = "")
public class IfaIpopoElecApprovalAgreementUploadController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IfaIpopoElecApprovalAgreementUploadController.class);

    private static final String SHEET_NAME = "電子交付同意";

    private static final int MAX_UPLOAD = 2000;

    private static final Pattern ALPHANUM = Pattern.compile("^[a-zA-Z0-9]+$");
    private static final Pattern NUMBER = Pattern.compile("^[0-9]+$");
    private static final Pattern DATE_YYYYMMDD_SLASH = Pattern.compile("^\\d{4}/\\d{2}/\\d{2}$");

    private static final String CHECK_OK = "OK";
    private static final String CHECK_NG = "NGあり";
    private static final String CHECK_REGISTERED = "登録済";

    private static final String CONTENT_TYPE_XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    private static final String HEADER_KEY_CONTENT_DISPOSITION = "Content-Disposition";
    private static final String HEADER_VALUE_ATTACHMENT = "attachment; filename=";

    private final JsonConverter jc = JsonConverter.getInstance();

    @PostMapping(value = "/companyEmployeeMenu/transactionData/ifaIpopoElecApprovalAgreementUploadConfirmA002")
    @ResponseBody
    @ResponseJson
    public String confirmA002(@RequestParam("uploadFile") MultipartFile file) throws Exception {

        long start = System.currentTimeMillis();
        LOGGER.debug("IfaIpopoElecApprovalAgreementUploadController.confirmA002 >> {}", hashCode());

        DataList<IfaIpopoElecApprovalAgreementUploadConfirmA002ApiResponse> apiResDataList = new DataList<>();
        List<IfaIpopoElecApprovalAgreementUploadConfirmA002ApiResponse> apiResList = new ArrayList<>();
        IfaIpopoElecApprovalAgreementUploadConfirmA002ApiResponse apiRes = new IfaIpopoElecApprovalAgreementUploadConfirmA002ApiResponse();

        // 拡張子チェック
        if (!checkExcelExt(file.getOriginalFilename())) {
            String msg = getMessage(ERRORS_NOT_EXCEL_FILE, new String[] {});
            apiResDataList = IfaCommonUtil.createDataList(apiResList, ErrorLevel.FATAL, ERRORS_NOT_EXCEL_FILE, msg);
            return jc.toString(apiResDataList);
        }

        try (InputStream is = file.getInputStream()) {
            Workbook workbook = WorkbookFactory.create(is);

            Sheet targetSheet = workbook.getSheet(SHEET_NAME);
            if (targetSheet == null) {
                String msg = getMessage(ERRORS_SHEET_NOT_EXIST, new String[] { "「" + SHEET_NAME + "」" });
                apiResDataList = IfaCommonUtil.createDataList(apiResList, ErrorLevel.FATAL, ERRORS_SHEET_NOT_EXIST, msg);
                return jc.toString(apiResDataList);
            }

            List<IfaIpopoElecApprovalAgreementUploadRowApiResponse> rows = parseAndCheckRows(targetSheet);
            if (rows.isEmpty()) {
                apiRes.setDataList(rows);
                apiResList.add(apiRes);
                String msg = getMessage(INFO_ORDERED_DATA_NOT_EXIST, new String[] { "明細" });
                apiResDataList = IfaCommonUtil.createDataList(apiResList, ErrorLevel.INFO, INFO_ORDERED_DATA_NOT_EXIST, msg);
                return jc.toString(apiResDataList);
            }

            if (rows.size() >= MAX_UPLOAD) {
                String msg = getMessage(ERRORS_MAX_UPLOAD, new String[] { String.valueOf(MAX_UPLOAD) });
                apiResDataList = IfaCommonUtil.createDataList(apiResList, ErrorLevel.FATAL, ERRORS_MAX_UPLOAD, msg);
                return jc.toString(apiResDataList);
            }

            // DB存在チェック（入力チェックOKのみ）
            for (IfaIpopoElecApprovalAgreementUploadRowApiResponse r : rows) {
                if (!CHECK_OK.equals(r.getCheckResult())) {
                    continue;
                }
                boolean exists = ApiRequestUtil.invoke(
                    "cmpIfaIpopoElecApprovalAgreementUploadService",
                    "existsButenAccount",
                    new TypeReference<Boolean>() {},
                    r.getButenCode(),
                    r.getAccountNumber()
                );
                if (!exists) {
                    String msg = getMessage(ERRORS_BUTEN_ACCOUNT_NOT_EXIST, new String[] { r.getButenCode(), r.getAccountNumber() });
                    r.setCheckResult(CHECK_NG);
                    r.setErrorMessage(appendMessage(r.getErrorMessage(), msg));
                }
            }

            apiRes.setDataList(rows);
            apiResList.add(apiRes);
            apiResDataList = IfaCommonUtil.createDataList(apiResList, ErrorLevel.SUCCESS, ErrorLevel.SUCCESS.name(), "");
            LOGGER.debug("cost -> {}", (System.currentTimeMillis() - start));
            return jc.toString(apiResDataList);

        } catch (EncryptedDocumentException e) {
            String msg = getMessage(ERRORS_HAS_PASSWORD, new String[] {});
            apiResDataList = IfaCommonUtil.createDataList(apiResList, ErrorLevel.FATAL, ERRORS_HAS_PASSWORD, msg);
            return jc.toString(apiResDataList);
        } catch (Exception e) {
            LOGGER.error("Exception occured.", e);
            String msg = getMessage(ERRORS_SERVERERROR, new String[] {});
            apiResDataList = IfaCommonUtil.createDataList(apiResList, ErrorLevel.FATAL, ERRORS_SERVERERROR, msg);
            return jc.toString(apiResDataList);
        }
    }

    @PostMapping(value = "/companyEmployeeMenu/transactionData/ifaIpopoElecApprovalAgreementUploadRegisterA007")
    @ResponseBody
    @ResponseJson
    public String registerA007(@RequestBody IfaIpopoElecApprovalAgreementUploadA007ApiRequest apiReq) throws Exception {

        long start = System.currentTimeMillis();
        LOGGER.debug("IfaIpopoElecApprovalAgreementUploadController.registerA007 >> {}", hashCode());

        DataList<IfaIpopoElecApprovalAgreementUploadA007ApiResponse> apiResDataList = new DataList<>();
        List<IfaIpopoElecApprovalAgreementUploadA007ApiResponse> apiResList = new ArrayList<>();
        IfaIpopoElecApprovalAgreementUploadA007ApiResponse apiRes = new IfaIpopoElecApprovalAgreementUploadA007ApiResponse();

        UserAccount userAccount = IfaCommonUtil.getUserAccount();

        List<IfaIpopoElecApprovalAgreementUploadRowApiRequest> okList = new ArrayList<>();
        for (IfaIpopoElecApprovalAgreementUploadRowApiRequest r : apiReq.getDataList()) {
            if (CHECK_OK.equals(r.getCheckResult())) {
                okList.add(r);
            }
        }

        if (okList.isEmpty()) {
            apiRes.setDataList(new ArrayList<>());
            apiResList.add(apiRes);
            String msg = getMessage(INFO_ORDERED_DATA_NOT_EXIST, new String[] { "OK" });
            apiResDataList = IfaCommonUtil.createDataList(apiResList, ErrorLevel.INFO, INFO_ORDERED_DATA_NOT_EXIST, msg);
            return jc.toString(apiResDataList);
        }

        Integer updatedCount = ApiRequestUtil.invoke(
            "cmpIfaIpopoElecApprovalAgreementUploadService",
            "upsertAgreements",
            new TypeReference<Integer>() {},
            okList,
            userAccount.getUserId()
        );

        // レスポンス：登録済に更新
        List<IfaIpopoElecApprovalAgreementUploadRowApiResponse> out = new ArrayList<>();
        for (IfaIpopoElecApprovalAgreementUploadRowApiRequest r : apiReq.getDataList()) {
            IfaIpopoElecApprovalAgreementUploadRowApiResponse o = new IfaIpopoElecApprovalAgreementUploadRowApiResponse();
            o.setButenCode(r.getButenCode());
            o.setAccountNumber(r.getAccountNumber());
            o.setEdelivAgreementDate(r.getEdelivAgreementDate());
            o.setEdelivAgreementKbn(r.getEdelivAgreementKbn());
            if (CHECK_OK.equals(r.getCheckResult())) {
                o.setCheckResult(CHECK_REGISTERED);
            } else {
                o.setCheckResult(r.getCheckResult());
            }
            o.setErrorMessage(r.getErrorMessage());
            out.add(o);
        }

        apiRes.setDataList(out);
        apiResList.add(apiRes);

        String msg = getMessage(INFO_UPLOAD_INSERT_COMPLETED, new String[] { String.valueOf(updatedCount == null ? 0 : updatedCount) });
        apiResDataList = IfaCommonUtil.createDataList(apiResList, ErrorLevel.INFO, INFO_UPLOAD_INSERT_COMPLETED, msg);
        LOGGER.debug("cost -> {}", (System.currentTimeMillis() - start));
        return jc.toString(apiResDataList);
    }

    @PostMapping(value = "/companyEmployeeMenu/transactionData/ifaIpopoElecApprovalAgreementUploadNgDownloadA004a")
    @ResponseBody
    @ResponseJson
    public String ngDownloadA004a(@RequestBody IfaIpopoElecApprovalAgreementUploadA007ApiRequest apiReq) throws Exception {

        List<IfaIpopoElecApprovalAgreementUploadRowApiRequest> ngList = new ArrayList<>();
        for (IfaIpopoElecApprovalAgreementUploadRowApiRequest r : apiReq.getDataList()) {
            if (CHECK_NG.equals(r.getCheckResult())) {
                ngList.add(r);
            }
        }

        String timestamp = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now());
        String outputName = "NG電子交付同意_" + timestamp + ".xlsx";
        File tmp = File.createTempFile("NG_EDELIV_" + timestamp + "_", ".xlsx");

        try (XSSFWorkbook wb = new XSSFWorkbook(); FileOutputStream fos = new FileOutputStream(tmp)) {
            var sheet = wb.createSheet(SHEET_NAME);
            int r = 0;
            Row header = sheet.createRow(r++);
            header.createCell(0).setCellValue("部店");
            header.createCell(1).setCellValue("口座番号");
            header.createCell(2).setCellValue("電子交付承諾日付");
            header.createCell(3).setCellValue("電子交付承諾区分");

            for (IfaIpopoElecApprovalAgreementUploadRowApiRequest it : ngList) {
                Row row = sheet.createRow(r++);
                row.createCell(0).setCellValue(StringUtil.nullToEmpty(it.getButenCode()));
                row.createCell(1).setCellValue(StringUtil.nullToEmpty(it.getAccountNumber()));
                row.createCell(2).setCellValue(StringUtil.nullToEmpty(it.getEdelivAgreementDate()));
                row.createCell(3).setCellValue(StringUtil.nullToEmpty(it.getEdelivAgreementKbn()));
            }
            wb.write(fos);
        }

        var apiRes = new IfaIpopoElecApprovalAgreementUploadA004aApiResponse(tmp.getAbsolutePath(), outputName);
        DataList<IfaIpopoElecApprovalAgreementUploadA004aApiResponse> apiResDataList =
            IfaCommonUtil.createDataList(List.of(apiRes), ErrorLevel.SUCCESS, ErrorLevel.SUCCESS.name(), "");
        return jc.toString(apiResDataList);
    }

    @PostMapping(value = "/companyEmployeeMenu/transactionData/ifaIpopoElecApprovalAgreementUploadNgDownloadA004b")
    public void ngDownloadA004b(@RequestBody IfaIpopoElecApprovalAgreementUploadA004bApiRequest apiReq, HttpServletResponse response) throws Exception {

        String filePath = apiReq.getPdfFileName();
        String outputName = apiReq.getPdfFileOutputName();
        if (StringUtil.isNullOrEmpty(filePath) || StringUtil.isNullOrEmpty(outputName)) {
            return;
        }

        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            return;
        }

        response.setContentType(CONTENT_TYPE_XLSX);
        response.setHeader(HEADER_KEY_CONTENT_DISPOSITION, HEADER_VALUE_ATTACHMENT + outputName);

        try (FileInputStream fis = new FileInputStream(file)) {
            IOUtils.copy(fis, response.getOutputStream());
        } finally {
            try {
                if (!file.delete()) {
                    LOGGER.warn("Failed to delete temporary file: {}", file.getAbsolutePath());
                }
            } catch (Exception e) {
                LOGGER.warn("Failed to delete temporary file: {}", file.getAbsolutePath(), e);
            }
        }
    }

    private static String appendMessage(String existing, String msg) {
        if (StringUtil.isNullOrEmpty(existing)) {
            return msg;
        }
        return existing + LF + msg;
    }

    private static boolean checkExcelExt(String fileName) {
        if (StringUtil.isNullOrEmpty(fileName) || !fileName.contains(".")) {
            return false;
        }
        String ext = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
        return "xlsx".equals(ext) || "xls".equals(ext);
    }

    private List<IfaIpopoElecApprovalAgreementUploadRowApiResponse> parseAndCheckRows(Sheet sheet) {
        List<IfaIpopoElecApprovalAgreementUploadRowApiResponse> out = new ArrayList<>();
        DataFormatter fmt = new DataFormatter();
        int emptyTail = 0;
        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                continue; // header
            }
            String buten = cellString(row.getCell(0), fmt);
            String account = cellString(row.getCell(1), fmt);
            String date = cellString(row.getCell(2), fmt);
            String kbn = cellString(row.getCell(3), fmt);

            if (StringUtil.isNullOrEmpty(buten) && StringUtil.isNullOrEmpty(account) && StringUtil.isNullOrEmpty(date) && StringUtil.isNullOrEmpty(kbn)) {
                emptyTail++;
                if (emptyTail > 50) {
                    break;
                }
                continue;
            }
            emptyTail = 0;

            IfaIpopoElecApprovalAgreementUploadRowApiResponse r = new IfaIpopoElecApprovalAgreementUploadRowApiResponse();
            r.setButenCode(buten);
            r.setAccountNumber(account);
            r.setEdelivAgreementDate(date);
            r.setEdelivAgreementKbn(kbn);

            String err = "";

            // 部店
            if (StringUtil.isNullOrEmpty(buten)) {
                err = appendMessage(err, getMessage(ERRORS_REQUIRED, new String[] { "部店" }));
            } else {
                if (!ALPHANUM.matcher(buten).matches()) {
                    err = appendMessage(err, getMessage(ERRORS_TYPE, new String[] { "部店コード", "半角英数字" }));
                }
                if (buten.length() > 3) {
                    err = appendMessage(err, getMessage(ERRORS_MAX_SIZE, new String[] { "部店コード", "3" }));
                }
            }

            // 口座番号（最大6桁）
            if (StringUtil.isNullOrEmpty(account)) {
                err = appendMessage(err, getMessage(ERRORS_REQUIRED, new String[] { "口座番号" }));
            } else {
                if (!NUMBER.matcher(account).matches()) {
                    err = appendMessage(err, getMessage(ERRORS_TYPE, new String[] { "口座番号", "半角数字" }));
                }
                if (account.length() > 6) {
                    err = appendMessage(err, getMessage(ERRORS_MAX_SIZE, new String[] { "口座番号", "6" }));
                }
            }

            // 電子交付承諾日付（任意）
            if (!StringUtil.isNullOrEmpty(date)) {
                if (!DATE_YYYYMMDD_SLASH.matcher(date).matches() || !CheckUtil.checkDate(date)) {
                    err = appendMessage(err, getMessage(ERRORS_DATE_SPECIFY_FORMAT, new String[] { "電子交付承諾日付", "YYYY/MM/DD" }));
                }
            }

            // 電子交付承諾区分
            if (StringUtil.isNullOrEmpty(kbn)) {
                err = appendMessage(err, getMessage(ERRORS_REQUIRED, new String[] { "電子交付承諾区分" }));
            } else if (!NUMBER.matcher(kbn).matches() || (!"0".equals(kbn) && !"1".equals(kbn))) {
                err = appendMessage(err, getMessage(ERRORS_NUMBER_FORMAT, new String[] { "電子交付承諾区分", "0/1" }));
            }

            if (StringUtil.isNullOrEmpty(err)) {
                r.setCheckResult(CHECK_OK);
                r.setErrorMessage("");
            } else {
                r.setCheckResult(CHECK_NG);
                r.setErrorMessage(err);
            }
            out.add(r);
        }
        return out;
    }

    private static String cellString(Cell cell, DataFormatter fmt) {
        if (cell == null) return "";
        String v = fmt.formatCellValue(cell);
        return v == null ? "" : v.trim();
    }
}

