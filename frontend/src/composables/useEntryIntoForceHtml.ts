import { ref, watch, toValue, MaybeRefOrGetter, Ref } from "vue"
import { getEntryIntoForceHtml } from "@/services/temporalDataService"

export function useEntryIntoForceHtml(eli: MaybeRefOrGetter<string>): {
  htmlContent: Ref<string>
  loadHtmlContent: () => Promise<void>
} {
  const htmlContent = ref<string>("")

  async function loadHtmlContent() {
    try {
      htmlContent.value = await getEntryIntoForceHtml(toValue(eli))
    } catch (error) {
      console.error("Error fetching entry into force HTML content:", error)
    }
  }

  watch(() => toValue(eli), loadHtmlContent, { immediate: true })

  return {
    htmlContent,
    loadHtmlContent,
  }
}
