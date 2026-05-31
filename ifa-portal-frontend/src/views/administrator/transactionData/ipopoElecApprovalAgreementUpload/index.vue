<template>
  <div>
    <caption-card
      style="margin: 1rem 0 1rem 0;"
      caption="電子交付同意データ登録"
      text-size="20px"
      text-color="#0058a2"
      background-color="Menu"
    >
      <el-form
        ref="form"
        label-position="right"
        label-width="160px"
        :inline="true"
      >
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
                @response-handler="handleConfirmResponse"
              ></ifa-button>

              <ifa-button
                id="btnRegisterConfirm"
                name="btnRegisterConfirm"
                text="登録"
                msg-title="電子交付同意データ登録"
                :disabled="disabled.btnRegister"
                color="primary"
                action-type="originalAction"
                @app-action-handler="openRegisterConfirm"
              ></ifa-button>

              <ifa-button
                ref="registerButton"
                id="btnRegister"
                name="btnRegister"
                text=""
                msg-title="電子交付同意データ登録"
                :disabled="disabled.btnRegister"
                action-id="SUB0504_02-01#A007"
                action-type="requestAction"
                :request-model="registerRequestModel"
                :disable-notification="false"
                style="display:none"
                @response-handler="handleRegisterResponse"
              ></ifa-button>

              <ifa-button
                id="btnNgDownload"
                name="btnNgDownload"
                text="NGダウンロード"
                msg-title="電子交付同意データ登録"
                :disabled="disabled.btnNgDownload"
                color="secondary"
                action-id="SUB0504_02-01#A004"
                action-type="outputPdfAction"
                :request-model="ngDownloadRequestModel"
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

        <el-row style="margin-top: 8px;">
          <grid-table
            ref="gridTable"
            :options="gridOptions"
            :auto-refresh="false"
            @click="handleGridClick"
          ></grid-table>
        </el-row>
      </el-form>
    </caption-card>

    <ifa-ok-cancel-dialog
      :is-visible="isRegisterConfirmVisible"
      title="電子交付同意データ登録"
      message="電子交付同意データを登録します。よろしいですか？"
      @close-modal-ok="registerConfirmed"
      @close-modal-cancel="isRegisterConfirmVisible = false"
    ></ifa-ok-cancel-dialog>

    <ifa-ipopo-elec-approval-agreement-upload-msg-list
      :is-visible="dialogMsgVisible"
      :message="selectedErrorMessage"
      @close-modal="dialogMsgVisible = false"
    ></ifa-ipopo-elec-approval-agreement-upload-msg-list>
  </div>
</template>

<script>
import GridTable from '@/components/GridTable'
import captionCard from '@/views/brokerageMenu/customerMenu/components/captionCard'
import IfaOkCancelDialog from '@/components/Dialog/IfaOkCancelDialog.vue'
import IfaIpopoElecApprovalAgreementUploadMsgList from './msgList.vue'

export default {
  components: {
    GridTable,
    captionCard,
    IfaOkCancelDialog,
    IfaIpopoElecApprovalAgreementUploadMsgList
  },
  data() {
    return {
      uploadSingleFile: null,
      dataList: [],
      gridOptions: JSON.parse(JSON.stringify(gridOptions)),
      disabled: {
        btnRegister: true,
        btnNgDownload: true
      },
      isRegisterConfirmVisible: false,
      dialogMsgVisible: false,
      selectedErrorMessage: ''
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
    registerRequestModel() {
      return {
        dataList: this.dataList
      }
    },
    ngDownloadRequestModel() {
      return {
        dataList: this.dataList
      }
    }
  },
  created() {
    this.gridOptions.wrap = true
  },
  methods: {
    openRegisterConfirm() {
      this.isRegisterConfirmVisible = true
    },
    handleChangeSingleFileSelection(file) {
      this.uploadSingleFile = file
    },
    handleClearSingleSelectedFile() {
      this.$refs['select'].clearFiles()
      this.uploadSingleFile = null
    },
    handleConfirmResponse(response) {
      this.dataList = response.dataList?.[0]?.dataList ?? []
      this.gridOptions.dataModel.data = this.dataList
      this.$nextTick(() => {
        this.$refs['gridTable'].refreshView()
      })
      const hasOk = this.dataList.some(item => item.checkResult === 'OK')
      const hasNg = this.dataList.some(item => item.checkResult === 'NGあり')
      this.disabled.btnRegister = !hasOk
      this.disabled.btnNgDownload = !hasNg
      this.handleClearSingleSelectedFile()
    },
    handleRegisterResponse(response) {
      this.dataList = response.dataList?.[0]?.dataList ?? []
      this.gridOptions.dataModel.data = this.dataList
      this.$nextTick(() => {
        this.$refs['gridTable'].refreshView()
      })
      const hasOk = this.dataList.some(item => item.checkResult === 'OK')
      const hasNg = this.dataList.some(item => item.checkResult === 'NGあり')
      this.disabled.btnRegister = !hasOk
      this.disabled.btnNgDownload = !hasNg
    },
    registerConfirmed() {
      this.isRegisterConfirmVisible = false
      this.$refs['registerButton'].execute()
    },
    handleClear() {
      this.dataList = []
      this.gridOptions.dataModel.data = []
      this.$refs['gridTable'].refreshView()
      this.disabled.btnRegister = true
      this.disabled.btnNgDownload = true
      this.isRegisterConfirmVisible = false
      this.dialogMsgVisible = false
      this.selectedErrorMessage = ''
      this.handleClearSingleSelectedFile()
    },
    handleGridClick(ui) {
      if (ui.dataIndx !== 'checkResult') return
      const checkResult = ui.rowData.checkResult
      if (checkResult === 'NGあり' && ui.rowData.errorMessage) {
        this.selectedErrorMessage = ui.rowData.errorMessage
        this.dialogMsgVisible = true
      }
    }
  }
}

const colModel = [
  { title: '部店', dataIndx: 'butenCode', width: 80, dataType: 'string', editable: false, halign: 'center', align: 'center' },
  { title: '口座番号', dataIndx: 'accountNumber', width: 110, dataType: 'string', editable: false, halign: 'center', align: 'left' },
  { title: '電子交付承諾日付', dataIndx: 'edelivAgreementDate', width: 140, dataType: 'string', editable: false, halign: 'center', align: 'left' },
  { title: '電子交付承諾区分', dataIndx: 'edelivAgreementKbn', width: 140, dataType: 'string', editable: false, halign: 'center', align: 'left' },
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
      if (checkResult === 'NGあり') {
        return `<a class=\"el-link el-link--primary\"><span class=\"el-link--inner\">${checkResult}</span></a>`
      }
      return checkResult
    }
  }
]

const gridOptions = {
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
  colModel,
  dataModel: { data: [] },
  maxHeight: 750,
  editable: false
}
</script>

<style lang="scss" scoped>
.upload-action__section {
  display: flex;
  gap: 8px;
  align-items: center;
}
</style>

