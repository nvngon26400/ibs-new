const getDefaultState = () => {
  return {
    codeValue: [],
    codeList: []
  }
}

const state = getDefaultState()

const mutations = {
  // 状態を初期値に戻す
  resetState: (state) => {
    Object.assign(state, getDefaultState())
  },
  // 💡 CRITICAL PERFORMANCE NOTE FOR MUTATIONS:
  // We must assign the `codeValue` object REFERENCE directly rather than using
  // the spread operator `[...codeValue]`.
  //
  // Because the data generated from `codeListLoader.js` is already deeply locked
  // via `Object.freeze()`. If we use `[...codeValue]`, it breaks the freeze by
  // creating a brand-new, unprotected array shell. Vue would then recursively
  // hijack this massive array, leading to high memory usage and long login lag.
  setCodeValue: (state, codeValue) => {
    state.codeValue = codeValue
  },
  setCodeList: (state, codeList) => {
    state.codeList = codeList
  }
}

const actions = {
  // 状態を初期値に戻す
  resetState({ commit }) {
    commit('resetState')
  },
  setCodeValue({ commit }, codeValue) {
    commit('setCodeValue', codeValue)
  },
  setCodeList({ commit }, codeList) {
    commit('setCodeList', codeList)
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
