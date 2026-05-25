<template>
  <div class="width-style">
    <caption-card
      :caption="formModel.title.name"
      text-size="20px"
      text-color="#0058a2"
      background-color="Menu"
    >
      <el-form
        ref="form"
        label-position="right"
        :inline="true"
      >
        <el-row class="fileupload_form">
          <el-col :span="24">
            <div class="upload-action__section">
              <ifa-file-select
                ref="select"
                text="ファイル選択"
                small
                msg-title="ファイル選択"
                :show-file-list="true"
                @before-remove="handleClearSelectedFile"
                @change="handleChangeFileSelection"
              ></ifa-file-select>
              <ifa-button
                id="btnConfirm"
                text="確認"
                small
                msg-title="目論見書閲覧データ登録"
                :disabled="uploadFileButtonDisabled"
                action-type="uploadAction"
                action-id="SUB0504_03-01#A002"
                :request-model="uploadFileModel"
                @response-handler="handleUploadResponse"
                @response-error-handler="handleUploadResponseError"
              ></ifa-button>
              <ifa-button
                text="NGダウンロード"
                color="secondary"
                small
                :disabled="disabled.btnNgDownload"
                action-type="originalAction"
                @app-action-handler="handleNgDownload"
              ></ifa-button>
              <ifa-button
                text="登録"
                color="primary"
                small
                msg-title="目論見書閲覧データ登録"
                :disabled="disabled.btnRegister"
                action-id="SUB0504_03-01#A003"
                action-type="requestAction"
                :request-model="registerRequestModel"
                @response-handler="handleRegisterResponse"
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
        <el-row style="margin-top: 10px;">
          <span class="list-title">目論見書閲覧データ一覧</span>
        </el-row>
        <el-row>
          <grid-table
            ref="prospectusViewDataGridTable"
            :options="gridOptions"
            :auto-refresh="false"
            @click="handleGridClick"
          ></grid-table>
        </el-row>
      </el-form>
    </caption-card>
    <ifa-ipopo-prospectus-view-data-register-msg-list
      :is-visible="dialogMsgListVisible"
      :msg-list="msgList"
      :error-message="selectedErrorMessage"
      @close-modal="dialogMsgListVisible = false"
    ></ifa-ipopo-prospectus-view-data-register-msg-list>
  </div>
</template>

<script>
import * as XLSX from 'xlsx'
import GridTable from '@/components/GridTable'
import captionCard from '@/views/brokerageMenu/customerMenu/components/captionCard'
import { IfaIpopoProspectusViewDataRegisterFormModel } from './js/IfaIpopoProspectusViewDataRegisterFormModel'
import { IfaIpopoProspectusViewDataRegisterA003RequestModel } from './js/IfaIpopoProspectusViewDataRegisterA003RequestModel'
import IfaIpopoProspectusViewDataRegisterMsgList from './IfaIpopoProspectusViewDataRegisterMsgList'

const NG_CHECK_RESULT = 'NGあり'
const OK_CHECK_RESULT = 'OK'

export default {
  components: {
    captionCard,
    GridTable,
    IfaIpopoProspectusViewDataRegisterMsgList
  },
  data() {
    return {
      formModel: new IfaIpopoProspectusViewDataRegisterFormModel(),
      uploadFile: null,
      dialogMsgListVisible: false,
      msgList: [],
      selectedErrorMessage: '',
      disabled: {
        btnConfirm: true,
        btnNgDownload: true,
        btnRegister: true
      },
      gridOptions: {
        showTop: false,
        flexHeight: false,
        flexWidth: false,
        collapsible: false,
        showTitle: false,
        numberCell: { show: false },
        selectionModel: { type: 'row', mode: 'single' },
        topVisible: false,
        wrap: true,
        reactive: true,
        locale: 'en',
        height: 'flex',
        columnTemplate: { width: 120 },
        colModel: colModel,
        dataModel: { data: [] },
        maxHeight: 750,
        editable: false
      }
    }
  },
  computed: {
    uploadFileButtonDisabled() {
      return this.uploadFile === null
    },
    uploadFileModel() {
      return {
        filename: 'uploadFile',
        file: this.uploadFile
      }
    },
    registerRequestModel() {
      const list = this.formModel.prospectusViewDataList.map(
        ({ checkResultSetList, ...rest }) => rest
      )
      return new IfaIpopoProspectusViewDataRegisterA003RequestModel(list)
    }
  },
  methods: {
    setup() {
      this.handleClear()
    },
    handleChangeFileSelection(file) {
      this.uploadFile = file
      this.disabled.btnConfirm = file === null
    },
    handleClearSelectedFile() {
      this.uploadFile = null
      this.disabled.btnConfirm = true
    },
    handleUploadResponse(response) {
      const list = response.dataList[0]?.prospectusViewDataList || []
      this.formModel.prospectusViewDataList = list
      this.gridOptions.dataModel.data = list
      this.$nextTick(() => {
        this.$refs.prospectusViewDataGridTable.refreshView()
      })
      this.disabled.btnRegister = !list.some(item => item.checkResult === OK_CHECK_RESULT)
      this.disabled.btnNgDownload = !list.some(item => item.checkResult === NG_CHECK_RESULT)
      this.handleClearSelectedFile()
    },
    handleUploadResponseError() {
      this.$_logDebug('handleUploadResponseError')
    },
    handleRegisterResponse(response) {
      const list = response.dataList[0]?.prospectusViewDataList || []
      this.formModel.prospectusViewDataList = list
      this.gridOptions.dataModel.data = list
      this.$nextTick(() => {
        this.$refs.prospectusViewDataGridTable.refreshView()
      })
      this.disabled.btnRegister = true
      this.disabled.btnNgDownload = !list.some(item => item.checkResult === NG_CHECK_RESULT)
    },
    handleGridClick(ui) {
      if (ui.dataIndx !== 'checkResult') {
        return
      }
      if (ui.rowData.checkResult === NG_CHECK_RESULT) {
        this.msgList = ui.rowData.checkResultSetList || []
        this.selectedErrorMessage = ui.rowData.displayMessage || ''
        this.dialogMsgListVisible = true
      }
    },
    handleNgDownload() {
      const ngRows = this.formModel.prospectusViewDataList.filter(
        item => item.checkResult === NG_CHECK_RESULT
      )
      if (ngRows.length === 0) {
        return
      }
      const header = [
        '銘柄コード',
        'ブックビルディング申込期間（開始）',
        '部店',
        '口座番号',
        '閲覧日時',
        '文書ID',
        '文書枝番'
      ]
      const data = [header]
      ngRows.forEach(row => {
        data.push([
          row.productCode || '',
          row.presentationFrom || '',
          row.butenCode || '',
          row.accountNumber || '',
          row.readTime || '',
          row.documentId || '',
          row.versionNumber || ''
        ])
      })
      const worksheet = XLSX.utils.aoa_to_sheet(data)
      const workbook = XLSX.utils.book_new()
      XLSX.utils.book_append_sheet(workbook, worksheet, '目論見書閲覧')
      const now = this.$store.getters.requestedTime || new Date()
      const pad = n => String(n).padStart(2, '0')
      const fileName = `NG目論見書閲覧_${now.getFullYear()}${pad(now.getMonth() + 1)}${pad(now.getDate())}${pad(now.getHours())}${pad(now.getMinutes())}${pad(now.getSeconds())}.xlsx`
      XLSX.writeFile(workbook, fileName)
    },
    handleClear() {
      this.formModel.prospectusViewDataList = []
      this.gridOptions.dataModel.data = []
      this.handleClearSelectedFile()
      this.disabled.btnRegister = true
      this.disabled.btnNgDownload = true
      this.disabled.btnConfirm = true
      this.$nextTick(() => {
        if (this.$refs.prospectusViewDataGridTable) {
          this.$refs.prospectusViewDataGridTable.refreshView()
        }
      })
    }
  }
}

const colModel = [
  {
    title: '銘柄コード',
    dataIndx: 'productCode',
    width: 120,
    dataType: 'string',
    halign: 'center',
    align: 'left'
  },
  {
    title: 'ブックビルディング申込期間（開始）',
    dataIndx: 'presentationFrom',
    width: 220,
    dataType: 'string',
    halign: 'center',
    align: 'left'
  },
  {
    title: '部店',
    dataIndx: 'butenCode',
    width: 80,
    dataType: 'string',
    halign: 'center',
    align: 'center'
  },
  {
    title: '口座番号',
    dataIndx: 'accountNumber',
    width: 100,
    dataType: 'string',
    halign: 'center',
    align: 'left'
  },
  {
    title: '閲覧日時',
    dataIndx: 'readTime',
    width: 180,
    dataType: 'string',
    halign: 'center',
    align: 'left'
  },
  {
    title: 'チェック結果',
    dataIndx: 'checkResult',
    width: 120,
    dataType: 'string',
    halign: 'center',
    align: 'left',
    render: function(ui) {
      const checkResult = ui.rowData.checkResult ? ui.rowData.checkResult : '-'
      const checkResultSetList = ui.rowData.checkResultSetList ? ui.rowData.checkResultSetList : []
      if (checkResult === NG_CHECK_RESULT) {
        return `<a class="el-link el-link--primary"><span class="el-link--inner">` + checkResult + `</span><sup class="el-badge__content item">` + checkResultSetList.length + `</sup></a>
        <style>
        .item {
          position: absolute;
          top: 0;
          right: 10px;
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
      }
      return checkResult
    }
  },
  {
    title: 'メッセージを表示する',
    dataIndx: 'displayMessage',
    dataType: 'string',
    hidden: true
  }
]
</script>

<style lang="scss" scoped>
@import "~@/styles/mixin.scss";
.width-style {
  min-width: 1230px;
  width: 100%;
}
.upload-action__section {
  display: flex;
  align-items: center;
  gap: 8px;
  flex-wrap: wrap;
}
.fileupload_form {
  margin-bottom: 16px;
}
.list-title {
  font-weight: bold;
  font-size: 16px;
  color: #0058a2;
}
</style>
