<template>
  <div>
    <el-dialog
      v-model="vmIsVisible"
      :close-on-click-modal="false"
      :show-close="false"
      :before-close="onDialogClose"
      @open="onOpen"
    >
      <el-row>
        <el-col
          :span="23"
          class="form-button__wrapper"
          style="margin-right: 0px;"
        >
          <ifa-button
            color="secondary"
            text="戻る"
            action-type="originalAction"
            action-id="SUB0504_02-03#A001"
            @app-action-handler="onDialogClose"
          ></ifa-button>
        </el-col>
      </el-row>
      <caption-card
        style="margin: 1rem 0 1rem 0;"
        :caption="formModel.title.name"
        text-size="20px"
        text-color="#0058a2"
        background-color="Menu"
      >
        <el-row>
          <el-input
            type="textarea"
            :model-value="errorMessage"
            readonly
            :rows="12"
          ></el-input>
        </el-row>
      </caption-card>
    </el-dialog>
  </div>
</template>

<script>
import { useVModel } from 'vue-composable'
import captionCard from '@/views/brokerageMenu/customerMenu/components/captionCard'
import { IfaEdelivConsentDataRegisterMsgListFormModel } from '../js/IfaEdelivConsentDataRegisterMsgListFormModel'

export default {
  components: {
    captionCard
  },
  props: {
    isVisible: {
      type: Boolean,
      required: true
    },
    errorMessage: {
      type: String,
      required: false,
      default: ''
    }
  },
  emits: ['close-modal', 'update:isVisible'],
  setup(props) {
    const vmIsVisible = useVModel(props, 'isVisible')
    return {
      vmIsVisible
    }
  },
  data() {
    return {
      formModel: new IfaEdelivConsentDataRegisterMsgListFormModel()
    }
  },
  methods: {
    onOpen() {
    },
    onDialogClose() {
      this.$emit('close-modal')
    }
  }
}
</script>

<style lang="scss" scoped>
.form-button__wrapper {
  display: flex;
  justify-content: flex-end;
  margin: -30px 0.5rem 0 auto;
}
:deep(.el-dialog) {
  top: 60px !important;
  margin-top: 0.5vh !important;
  padding-top: 0.5rem;
  width: 960px;
}
:deep(.el-dialog__header) {
  padding: 0;
}
</style>
