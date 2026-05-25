<template>
  <div>
    <div class="message">別ウィンドウで画面が起動しております｡</div>
    <ifa-requester
      id="ifaForeignCurrencyMmfInitializeA001"
      action-id="SUB0202_0404#A001"
      action-type="requestAction"
      :request-model="ifaLinkRequestModel"
      @response-handler="responseHandlerGetNewMainSiteA001($event)"
    ></ifa-requester>
  </div>
</template>

<script>
import { IfaForeignCurrencyMmfModel } from './js/IfaForeignCurrencyMmfModel'
import { IfaLinkRequestModel } from './js/IfaLinkRequestModel'
import { notifyMessage } from '@/utils/errorHandler'

export default {
  data() {
    return {
      form: new IfaForeignCurrencyMmfModel(),
      request: '',
      requestProps: {},
      ifaLinkRequestModel: {}
    }
  },
  computed: {
    target() {
      return 'link' + (this.requestProps.urlId === 0 ? '' : this.requestProps.urlId.toString())
    }
  },
  methods: {
    onShow() {
      // 初期表示時のみA001初期化処理を実行
      this.$nextTick(() => {
        this.ifaForeignCurrencyMmfGetNewMainSiteA001(46, '1', 'POST')
      })
    },
    ifaForeignCurrencyMmfGetNewMainSiteA001(urlId, patternId, httpMethod) {
      const newData = {
        urlId: urlId,
        patternId: patternId,
        httpMethod: httpMethod
      }
      this.requestProps = newData
      this.ifaLinkRequestModel = new IfaLinkRequestModel(this.requestProps)
      document.getElementById('ifaForeignCurrencyMmfInitializeA001').click()
    },
    responseHandlerGetNewMainSiteA001(response) {
      Object.assign(this.form, response?.dataList?.[0])
      if (this.form.postRequest) {
        this.request = this.form.postRequest
      }
      this.openWindow()
    },
    // メインサイト画面表示
    openWindow() {
      this.linkUrl = this.form.linkUrl
      this.paramObject = this.form.newMainSiteParamList[0]
      const features = 'left=30000, top=30000, menubar=no, toolbar=no, scrollbars=no, resizable=yes'
      const newWindow = window.open('', this.target, features)
      if (newWindow) {
        const linkForm = document.createElement('form')
        linkForm.target = this.target
        linkForm.method = 'POST'
        linkForm.action = this.linkUrl
        // request と paramObject をマージする｡
        // key が同じ場合は､ paramObject を優先する
        const objs = Object.assign({}, this.request, this.paramObject)
        const params = Object.entries(objs)
          .map(e => ({ name: e[0], value: e[1] }))
        if (params) {
          for (const param of params) {
            const linkInput = document.createElement('input')
            linkInput.type = 'hidden'
            linkInput.name = param.name
            linkInput.value = param.value
            linkForm.appendChild(linkInput)
          }
        }
        document.body.appendChild(linkForm)
        linkForm.submit()
        document.body.removeChild(linkForm)
      } else {
        const label = this.$store.getters.pageInfo.label
        notifyMessage(2, 'ポップアップを許可してください｡', label)
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.message {
  padding: 2rem;
  font-size: 18px;
}
</style>
