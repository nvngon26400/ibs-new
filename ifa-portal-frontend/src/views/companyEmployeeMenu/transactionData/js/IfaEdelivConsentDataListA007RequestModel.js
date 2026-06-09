import Logger from '@/utils/ifaLog.js'

export class IfaEdelivConsentDataListA007RequestModel {
  constructor(obj) {
    Logger.debug(obj)
    this.edelivConsentDataList = obj || []
  }
}
