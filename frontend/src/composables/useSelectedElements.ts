import { nextTick, Ref, ref, watch } from "vue"
import RisLawPreview from "@/components/RisLawPreview.vue"

/**
 * Finds the eIds of all elements between the startEId and the endEId.
 *
 * @param startEId the eId of the first element
 * @param endEId the eId of the last element (including)
 * @param previewElement the preview element in which the html render of the norm in which the eIds should be found is rendered.
 */
function getAllEidsBetween(
  startEId: string,
  endEId: string,
  previewElement: InstanceType<typeof RisLawPreview>,
) {
  const eids: string[] = []
  let collect = false

  const elements = (previewElement.$el as HTMLElement).querySelectorAll(
    "[data-eid]",
  )
  elements.forEach((el) => {
    const eid = el.getAttribute("data-eid")
    if (eid === startEId || eid === endEId) {
      if (!collect) {
        eids.push(eid)
        collect = true
      } else {
        eids.push(eid!)
        collect = false
      }
    } else if (collect && eid) {
      eids.push(eid)
    }
  })
  return eids
}

/**
 * Provides the eIds of the elements between the startEId and the endEId.
 *
 * @param startEId the eId of the first element of the range
 * @param endEId the eId of the last element of the range (including). If empty the range is just the startEId.
 * @param previewHtml the html render of the norm in which the range should be found.
 * @param previewElement the preview element in which the html is rendered.
 */
export function useEIdRange(
  startEId: Ref<string>,
  endEId: Ref<string>,
  previewHtml: Ref<string | undefined>,
  previewElement: Ref<InstanceType<typeof RisLawPreview> | null>,
) {
  const selectedElements = ref<string[]>([])

  watch(
    [startEId, endEId, previewElement, () => previewHtml],
    async () => {
      if (startEId.value == null) {
        selectedElements.value = []
        return
      }
      if (endEId.value == "") {
        selectedElements.value = [startEId.value]
        return
      }

      if (previewElement.value == null) {
        return
      }

      await nextTick()

      selectedElements.value = getAllEidsBetween(
        startEId.value,
        endEId.value,
        previewElement.value,
      )
    },
    { immediate: true },
  )

  return selectedElements
}
