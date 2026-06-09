<template>
  <div class="width-style">
    <ifa-menu
      :menu-list="menuList"
    ></ifa-menu>
  </div>
</template>

<script>
import { getTransactionDataMenuConfig } from '@/views/companyEmployeeMenu/_config/menuConfig.js'

export default {
  data() {
    return {
      menuList: []
    }
  },
  created() {
    const getter = getTransactionDataMenuConfig.bind(this)
    this.menuList = getter()
  }
}
</script>

<style lang="scss" scoped>
.width-style {
  min-width: 1230px;
  width: 100%;
}
</style>
