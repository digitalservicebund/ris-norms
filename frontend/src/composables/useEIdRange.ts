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

  const eIds: string[] = []
  let collect = false

  const elements = htmlRender.querySelectorAll("[data-eid]")
  elements.forEach((el) => {
    const eid = el.getAttribute("data-eid")
    if (eid === startEId || eid === endEId) {
      if (!collect) {
        eIds.push(eid)
        collect = true
      } else {
        eIds.push(eid)
        collect = false
      }
    } else if (collect && eid) {
      eIds.push(eid)
    }
  })
  return eIds
}

/**
 * Provides the eIds of the elements between the startEId and the endEId.
 *
 * @param startEId the eId of the first element of the range
 * @param endEId the eId of the last element of the range (including). If empty the range is just the startEId.
 * @param previewHtml the html render of the norm in which the range should be found.
 */
export function useEIdRange(
  startEId: Ref<string | null>,
  endEId: Ref<string>,
  previewHtml: Ref<string | undefined>,
) {
  const eIds = ref<string[]>([])

  watch(
    [startEId, endEId, previewHtml],
    async () => {
      if (startEId.value == null) {
        eIds.value = []
        return
      }
      if (endEId.value == "") {
        eIds.value = [startEId.value]
        return
      }

      eIds.value = getAllEidsBetween(
        startEId.value,
        endEId.value,
        toValue(previewHtml) ?? "",
      )
    },
    { immediate: true },
  )

  return eIds
}
