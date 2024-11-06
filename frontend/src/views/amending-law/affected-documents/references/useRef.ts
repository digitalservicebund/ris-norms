import {
  computed,
  MaybeRefOrGetter,
  Ref,
  toValue,
  WritableComputedRef,
} from "vue"
import {
  isElement,
  xmlNodeToString,
  xmlStringToDocument,
} from "@/services/xmlService"

/**
 * Possible values for the `refersTo` attribute of an akn:ref element.
 */
export type RefRefersTo = "zitierung"

/**
 * Composable for modifying the xml of an akn:ref element.
 *
 * @param xmlSnippet The current XML String (LDML.de) of the akn:ref element. Will only be read.
 * @param update Method called with the new xml when the xml of the akn:ref element should be changed.
 */
export function useRef(
  xmlSnippet: MaybeRefOrGetter<string | undefined>,
  update: (newXmlSnippet: string) => void,
): {
  /**
   * Ref that contains the current value of the `refersTo` attribute.
   * Writing to it updates the attribute in the xml.
   */
  refersTo: WritableComputedRef<RefRefersTo | "">
  /**
   * Ref that contains the current value of the `href` attribute.
   * Writing to it updates the attribute in the xml.
   */
  href: WritableComputedRef<string>
} {
  const node: Ref<Element | null> = computed(() => {
    const xml = toValue(xmlSnippet)
    if (!xml) {
      return null
    }

    const doc = xmlStringToDocument(xml)

    if (!doc.firstChild || !isElement(doc.firstChild)) {
      return null
    }

    return doc.firstChild
  })

  function updateXmlSnippet() {
    if (node.value) {
      update(xmlNodeToString(node.value))
    }
  }

  const refersTo = computed<RefRefersTo | "">({
    get: () => {
      return (node.value?.getAttribute("refersTo") as RefRefersTo) ?? ""
    },
    set: (newValue) => {
      if (!node.value) {
        return
      }

      node.value?.setAttribute("refersTo", newValue)

      updateXmlSnippet()
    },
  })

  const href = computed<string>({
    get: () => {
      return node.value?.getAttribute("href") ?? ""
    },
    set: (newValue) => {
      if (!node.value) {
        return
      }

      node.value?.setAttribute("href", newValue)

      updateXmlSnippet()
    },
  })

  return {
    refersTo,
    href,
  }
}
