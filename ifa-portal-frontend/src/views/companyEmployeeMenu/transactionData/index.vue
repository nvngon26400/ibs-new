<template>
  <div class="width-style">
    <ifa-menu :menu-list="menuList"></ifa-menu>
  </div>
</template>

<script>
import { getTransactionDataMenuConfig } from '@/views/companyEmployeeMenu/_config/menuConfig.js'

export default {
  data() {
    return {
      menuList: [],
      selectedTabName: 'ifa-ipopo-prospectus-view-data-register'
    }
  },
  created() {
    this.menuList = getTransactionDataMenuConfig()
  },
  mounted() {
    this.$refs[this.selectedTabName]?.setup?.()
  }
}
</script>

<style lang="scss" scoped>
@import "~@/styles/mixin.scss";
.width-style {
  min-width: 1230px;
  width: 100%;
}
</style>
