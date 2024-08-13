import { Ref, ref, toValue, watch } from "vue"

/**
 * Finds the eIds of all elements between the startEId and the endEId.
 *
 * @param startEId the eId of the first element
 * @param endEId the eId of the last element (including)
 * @param normHtml the html render of the norm in which the elements should be found.
 */
function getAllEidsBetween(startEId: string, endEId: string, normHtml: string) {
  const htmlRender = document.createElement("div")
  htmlRender.innerHTML = normHtml

  const eids: string[] = []
  let collect = false

  const elements = htmlRender.querySelectorAll("[data-eid]")
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
 */
export function useEIdRange(
  startEId: Ref<string>,
  endEId: Ref<string>,
  previewHtml: Ref<string | undefined>,
) {
  const selectedElements = ref<string[]>([])

  watch(
    [startEId, endEId, previewHtml],
    async () => {
      if (startEId.value == null) {
        selectedElements.value = []
        return
      }
      if (endEId.value == "") {
        selectedElements.value = [startEId.value]
        return
      }

      selectedElements.value = getAllEidsBetween(
        startEId.value,
        endEId.value,
        toValue(previewHtml) ?? "",
      )
    },
    { immediate: true },
  )

  return selectedElements
}
