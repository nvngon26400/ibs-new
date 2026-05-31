<template>
  <div>
    <el-dialog
      v-model="vmIsVisible"
      :close-on-click-modal="false"
      :show-close="false"
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
            @app-action-handler="onDialogClose"
          ></ifa-button>
        </el-col>
      </el-row>

      <caption-card
        style="margin: 1rem 0 1rem 0;"
        caption="電子交付同意データ登録　メッセージ一覧"
        text-size="20px"
        text-color="#0058a2"
        background-color="Menu"
      >
        <el-row>
          <el-col :span="24">
            <div class="label">エラーメッセージ</div>
            <el-input
              type="textarea"
              :rows="12"
              :model-value="message"
              readonly
            ></el-input>
          </el-col>
        </el-row>
      </caption-card>
    </el-dialog>
  </div>
</template>

<script>
import { useVModel } from 'vue-composable'
import captionCard from '@/views/brokerageMenu/customerMenu/components/captionCard'

export default {
  components: { captionCard },
  props: {
    isVisible: { type: Boolean, required: true },
    message: { type: String, required: false, default: '' }
  },
  emits: ['close-modal', 'update:isVisible'],
  setup(props) {
    const vmIsVisible = useVModel(props, 'isVisible')
    return { vmIsVisible }
  },
  methods: {
    onOpen() {},
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
.label {
  margin-bottom: 6px;
  font-weight: 600;
}
</style>

