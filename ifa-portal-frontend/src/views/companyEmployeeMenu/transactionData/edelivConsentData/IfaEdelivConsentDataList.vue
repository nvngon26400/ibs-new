<template>
  <div>
    <ifa-requester
      id="ifaEdelivConsentDataListRegisterA007"
      action-id="SUB0504_02-01#A007"
      action-type="requestAction"
      :request-model="ifaEdelivConsentDataListA007RequestModel"
      @response-handler="handleRegisterA007Response"
    ></ifa-requester>
    <screen-title :text="formModel.screenTitle.name"></screen-title>
    <el-card
      class="content-card"
      shadow="always"
    >
      <el-form
        ref="form"
        label-position="right"
        label-width="208px"
        :inline="true"
      >
        <el-row style="height: 150px">
          <el-col>
            <el-row style="margin-bottom: 20px;">
              <el-row class="fileupload_form">
                <el-col :span="23">
                  <div class="upload-action__section">
                    <ifa-file-select
                      ref="select"
                      text="ファイルを選択"
                      small
                      msg-title="ファイルを選択"
                      :show-file-list="true"
                      @before-remove="handleClearSingleSelectedFile"
                      @change="handleChangeSingleFileSelection"
                    ></ifa-file-select>
                    <ifa-button
                      id="btnConfirm"
                      name="btnConfirm"
                      text="確認"
                      small
                      msg-title="電子交付同意データ登録"
                      :disabled="uploadSingleFileButtonDisabled"
                      action-type="uploadAction"
                      action-id="SUB0504_02-01#A002"
                      :request-model="uploadSingleFileModel"
                      @response-handler="handleUploadSingleFileResponse"
                      @response-error-handler="handleUploadSingleFileResponseError"
                    ></ifa-button>
                    <ifa-button
                      text="クリア"
                      color="secondary"
                      small
                      action-type="originalAction"
                      @app-action-handler="handleClear"
                    ></ifa-button>
                  </div>
                </el-col>
              </el-row>
            </el-row>
          </el-col>
        </el-row>
        <el-row>
          <grid-table
            ref="edelivConsentGridTable"
            :options="optionss"
            :auto-refresh="false"
            @click="handleClick"
          ></grid-table>
        </el-row>
        <el-row style="margin-top: 10px;">
          <ifa-button
            id="btnRegister"
            name="btnRegister"
            text="登録"
            msg-title="電子交付同意データ登録"
            :disabled="disabled.btnRegister"
            color="primary"
            action-type="originalAction"
            @app-action-handler="handleRegisterA003"
          ></ifa-button>
          <ifa-button
            id="btnNgDownload"
            name="btnNgDownload"
            text="NGダウンロード"
            msg-title="電子交付同意データ登録"
            :disabled="disabled.btnNgDownload"
            color="secondary"
            small
            style="margin-left: 10px;"
            action-type="originalAction"
            @app-action-handler="handleNgDownloadA004"
          ></ifa-button>
        </el-row>
      </el-form>
    </el-card>
    <ifa-ok-cancel-dialog
      :is-visible="isOkCancelDialogVisible"
      title="電子交付同意データ登録"
      message="電子交付同意データを登録します。よろしいですか？"
      @close-modal-ok="handleRegisterA007"
      @close-modal-cancel="isOkCancelDialogVisible = false"
    ></ifa-ok-cancel-dialog>
    <ifa-edeliv-consent-data-register-msg-list
      :is-visible="dialogMsgListVisible"
      :error-message="errorMessage"
      @close-modal="dialogMsgListVisible = false"
    ></ifa-edeliv-consent-data-register-msg-list>
  </div>
</template>

<script>
import * as XLSX from 'xlsx'
import GridTable from '@/components/GridTable'
import IfaOkCancelDialog from '@/components/Dialog/IfaOkCancelDialog.vue'
import { IfaEdelivConsentDataListFormModel } from '../js/IfaEdelivConsentDataListFormModel'
import { IfaEdelivConsentDataListA007RequestModel } from '../js/IfaEdelivConsentDataListA007RequestModel'
import IfaEdelivConsentDataRegisterMsgList from './IfaEdelivConsentDataRegisterMsgList'

export default {
  components: {
    GridTable,
    IfaOkCancelDialog,
    IfaEdelivConsentDataRegisterMsgList
  },
  data() {
    return {
      isOkCancelDialogVisible: false,
      formModel: new IfaEdelivConsentDataListFormModel(),
      dialogMsgListVisible: false,
      errorMessage: '',
      optionss: {
        showTop: false,
        flexHeight: false,
        flexWidth: false,
        collapsible: false,
        showTitle: true,
        title: '電子交付同意データ一覧',
        numberCell: { show: false },
        selectionModel: { type: 'row', mode: 'single' },
        topVisible: false,
        wrap: false,
        reactive: true,
        locale: 'en',
        height: 'flex',
        columnTemplate: { width: 100 },
        colModel: colModel,
        dataModel: {
          data: []
        },
        maxHeight: 750,
        editable: false
      },
      disabled: {
        btnRegister: true,
        btnNgDownload: true
      },
      uploadSingleFile: null
    }
  },
  computed: {
    uploadSingleFileButtonDisabled() {
      return this.uploadSingleFile === null
    },
    uploadSingleFileModel() {
      return {
        filename: 'uploadFile',
        file: this.uploadSingleFile
      }
    },
    ifaEdelivConsentDataListA007RequestModel() {
      const requestList = this.formModel.edelivConsentDataList.map(
        ({ checkResultSetList, errorMessage, ...rest }) => rest
      )
      return new IfaEdelivConsentDataListA007RequestModel(requestList)
    }
  },
  created() {
    this.optionss.wrap = true
  },
  methods: {
    handleRegisterA003() {
      this.isOkCancelDialogVisible = true
    },
    handleRegisterA007() {
      this.isOkCancelDialogVisible = false
      document.getElementById('ifaEdelivConsentDataListRegisterA007').click()
    },
    handleRegisterA007Response(response) {
      const registeredList = response.dataList[0]?.edelivConsentDataList || []
      registeredList.forEach(registeredRow => {
        const index = this.formModel.edelivConsentDataList.findIndex(
          item => item.butenCode === registeredRow.butenCode
            && item.accountNumber === registeredRow.accountNumber
        )
        if (index !== -1) {
          this.formModel.edelivConsentDataList[index].checkResult = registeredRow.checkResult
        }
      })
      const list = [...this.formModel.edelivConsentDataList]
      this.optionss.dataModel.data = list.length > 1000 ? list.slice(0, 1000) : list
      this.$nextTick(() => {
        this.$refs['edelivConsentGridTable'].refreshView()
      })
      this.updateButtonStates(list)
    },
    handleClear() {
      this.optionss.dataModel.data = []
      this.formModel.edelivConsentDataList = []
      this.$refs['edelivConsentGridTable'].refreshView()
      this.disabled.btnRegister = true
      this.disabled.btnNgDownload = true
      this.handleClearSingleSelectedFile()
    },
    handleClick(ui) {
      if (ui.dataIndx === 'checkResult' && ui.rowData.checkResult === 'NGあり') {
        this.errorMessage = this.buildErrorMessage(ui.rowData)
        this.dialogMsgListVisible = true
      }
    },
    buildErrorMessage(rowData) {
      if (rowData.errorMessage) {
        return rowData.errorMessage
      }
      const checkResultSetList = rowData.checkResultSetList || []
      return checkResultSetList.map(item => item.msg).filter(Boolean).join('\n')
    },
    handleChangeSingleFileSelection(file) {
      this.uploadSingleFile = file
    },
    handleUploadSingleFileResponse(response) {
      const list = response.dataList[0]?.edelivConsentDataList || []
      Object.assign(this.formModel.edelivConsentDataList, list)
      if (list.length > 1000) {
        this.optionss.dataModel.data = list.slice(0, 1000)
      } else {
        this.optionss.dataModel.data = list
      }
      this.$nextTick(() => {
        this.$refs['edelivConsentGridTable'].refreshView()
      })
      this.updateButtonStates(list)
      this.handleClearSingleSelectedFile()
    },
    handleUploadSingleFileResponseError() {
      this.$_logDebug('handleUploadSingleFileResponseError')
    },
    handleClearSingleSelectedFile() {
      this.$refs['select'].clearFiles()
      this.uploadSingleFile = null
    },
    updateButtonStates(list) {
      this.disabled.btnRegister = !list.some(item => item.checkResult === 'OK')
      this.disabled.btnNgDownload = !list.some(item => item.checkResult === 'NGあり')
    },
    handleNgDownloadA004() {
      const ngRows = this.formModel.edelivConsentDataList.filter(item => item.checkResult === 'NGあり')
      if (ngRows.length === 0) {
        return
      }
      const header = ['部店', '口座番号', '電子交付承諾日付', '電子交付承諾区分']
      const data = ngRows.map(row => [
        row.butenCode || '',
        row.accountNumber || '',
        row.edelivAgreementDate || '',
        row.edelivAgreementKbn || ''
      ])
      const worksheet = XLSX.utils.aoa_to_sheet([header, ...data])
      const workbook = XLSX.utils.book_new()
      XLSX.utils.book_append_sheet(workbook, worksheet, '電子交付同意')
      const timestamp = this.formatTimestamp(new Date())
      XLSX.writeFile(workbook, `NG電子交付同意_${timestamp}.xlsx`)
    },
    formatTimestamp(date) {
      const pad = (n) => String(n).padStart(2, '0')
      return `${date.getFullYear()}${pad(date.getMonth() + 1)}${pad(date.getDate())}${pad(date.getHours())}${pad(date.getMinutes())}${pad(date.getSeconds())}`
    }
  }
}

const colModel = [
  {
    title: '部店',
    dataIndx: 'butenCode',
    width: 80,
    dataType: 'string',
    editable: false,
    halign: 'center',
    align: 'center'
  },
  {
    title: '口座番号',
    dataIndx: 'accountNumber',
    minWidth: 100,
    dataType: 'string',
    editable: false,
    halign: 'center',
    align: 'left'
  },
  {
    title: '電子交付承諾日付',
    dataIndx: 'edelivAgreementDate',
    width: 150,
    dataType: 'string',
    editable: false,
    halign: 'center',
    align: 'left'
  },
  {
    title: '電子交付承諾区分',
    dataIndx: 'edelivAgreementKbn',
    width: 130,
    dataType: 'string',
    editable: false,
    halign: 'center',
    align: 'center'
  },
  {
    title: 'チェック結果',
    dataIndx: 'checkResult',
    width: 120,
    dataType: 'string',
    editable: false,
    halign: 'center',
    align: 'left',
    render: function(ui) {
      const checkResult = ui.rowData.checkResult ? ui.rowData.checkResult : '-'
      const checkResultSetList = ui.rowData.checkResultSetList ? ui.rowData.checkResultSetList : []
      if (checkResult === 'NGあり') {
        return `<a class="el-link el-link--primary"><span class="el-link--inner">` + checkResult + `</span><sup class="el-badge__content item">` + checkResultSetList.length + `</sup></a>
        <style>
        .item {
          position: absolute;
          top: 0;
          right: 10px;
          -webkit-transform: translateY(-50%) translateX(100%);
          transform: translateY(-50%) translateX(100%);
          background-color: #f56c6c;
          border-radius: 10px;
          color: #fff;
          display: inline-block;
          height: 18px;
          padding: 0 6px;
          text-align: center;
          white-space: nowrap;
          border: 1px solid #fff;
          margin-top: 5px;
          margin-right: -10px;
        }
        </style>`
      } else {
        return checkResult
      }
    }
  },
  {
    title: 'チェック結果セット',
    dataIndx: 'checkResultSetList',
    dataType: 'string',
    hidden: true
  },
  {
    title: 'エラーメッセージ',
    dataIndx: 'errorMessage',
    dataType: 'string',
    hidden: true
  }
]
</script>

<style lang="scss" scoped>
@import '@/styles/bbApplyCsvBatchUpload.scss';
.upload-action__section {
  display: flex;
  .selected-file-info {
    display: flex;
    border: 1px solid blue;
    align-items: center;
    min-width: 1000px;
    height: 2em;
    margin-left: 0.5em;
  }
}
:deep(.el-upload-list.el-upload-list--text) {
  width: 42rem!important;
}
:deep(.upload-action__section>.ifa-file-select__wrapper) {
  width: 133px!important;
}
:deep(.el-link) {
  text-decoration: underline !important;
}
:deep(.el-link:hover) {
  opacity: 0.7;
}
</style>
