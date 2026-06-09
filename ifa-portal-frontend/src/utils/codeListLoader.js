import { IfaCodeListDisplayPatternModel } from '@/components/Input/js/IfaCodeListDisplayPatternModel'
import { IfaCodeListSelectPatternModel } from '@/components/Input/js/IfaCodeListSelectPatternModel'

/**
 * Merges legacy array configurations with newly added incremental JSON files,
 * removes duplicate keys, and outputs a single, deeply frozen array to maximize performance.
 *
 * @param {Array} oldArray - The original huge legacy configuration array
 * @param {Function} requireContext - Webpack require.context function for scanning new JSON files
 * @returns {Array} A deeply frozen merged array that retains the original data structure
 */
const mergePatternConfigs = (oldArray, requireContext) => {
  const tempMap = {}

  // Convert the legacy array data into a Map for O(1) deduplication and merging
  if (Array.isArray(oldArray)) {
    for (let i = 0; i < oldArray.length; i++) {
      const item = oldArray[i]
      if (item && item.codeListId) {
        tempMap[item.codeListId] = item
      }
    }
  }

  // Scan and merge newly added standalone JSON files.
  // If a duplicate codeListId exists, the new JSON config will seamlessly overwrite the old one.
  if (requireContext && typeof requireContext.keys === 'function') {
    requireContext.keys().forEach(fileName => {
      // =========================================================================
      // 💡 NOTE ON WEBPACK JSON PARSING MECHANISM:
      // Webpack automatically intercepts and parses all '.json' files at compile-time.
      // The function call below does NOT return a raw JSON string. Instead, Webpack's
      // internal loader performs an automatic `JSON.parse` under the hood and bundles
      // the contents as a standard, native JavaScript Object directly into the JS chunk.
      // =========================================================================
      const configData = requireContext(fileName)
      if (configData && configData.codeListId) {
        tempMap[configData.codeListId] = configData
      }
    })
  }

  // Restore to the original array structure via Object.values() for backward compatibility,
  // and deeply freeze it using Object.freeze() to prevent Vue from creating expensive reactive traps.
  return Object.freeze(Object.values(tempMap))
}

// Extract the raw legacy configuration arrays
const oldDisplayPatterns = IfaCodeListDisplayPatternModel.displayPatternModel
const oldSelectPatterns = IfaCodeListSelectPatternModel.selectPatternModel

// Declare container variables for Webpack context
let newDisplayPatterns = null
let newSelectPatterns = null

// ─── Compile-Time Isolation (Try-Catch) ───
// Wrap with try-catch blocks to prevent compilation or runtime errors
// if these physical folders do not exist in certain deployment environments (e.g., Git empty folders).
try {
  newDisplayPatterns = require.context('@/resource/codeList/displayPatterns', false, /\.json$/)
} catch (e) {
  newDisplayPatterns = null
  console.warn('⚠️ displayPatterns directory not found. Falling back to legacy data safely.')
}

try {
  newSelectPatterns = require.context('@/resource/codeList/selectPatterns', false, /\.json$/)
} catch (e) {
  newSelectPatterns = null
  console.warn('⚠️ selectPatterns directory not found. Falling back to legacy data safely.')
}

const displayPatternConfigs = mergePatternConfigs(oldDisplayPatterns, newDisplayPatterns)
const selectPatternConfigs = mergePatternConfigs(oldSelectPatterns, newSelectPatterns)

// Export named objects to ensure a mirrored structure with Vuex store helpers
export {
  displayPatternConfigs,
  selectPatternConfigs
}
