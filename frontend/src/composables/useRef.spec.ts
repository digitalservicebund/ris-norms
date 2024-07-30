import { describe, expect, test, vi } from "vitest"
import { useRef } from "@/composables/useRef"
import { ref } from "vue"

describe("useRef", () => {
  test("should provide the data about a ref", async () => {
    const { refersTo, href } = useRef(
      `<akn:ref xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" eId="quot-1_ref-1" href="eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/hauptteil-1_abschnitt-erster_para-4_abs-3.xml" refersTo="zitierung">§4 Abs. 3 StVO</akn:ref>`,
      () => {},
    )

    expect(refersTo.value).toBe("zitierung")
    expect(href.value).toBe(
      "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/hauptteil-1_abschnitt-erster_para-4_abs-3.xml",
    )
  })

  test("should call the update when the refersTo or href change", async () => {
    const updateFn = vi.fn()
    const { refersTo, href } = useRef(
      `<akn:ref xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" eId="quot-1_ref-1" href="eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/hauptteil-1_abschnitt-erster_para-4_abs-3.xml">§4 Abs. 3 StVO</akn:ref>`,
      updateFn,
    )

    refersTo.value = "zitierung"
    expect(updateFn).toBeCalledWith(
      `<akn:ref xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" eId="quot-1_ref-1" href="eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/hauptteil-1_abschnitt-erster_para-4_abs-3.xml" refersTo="zitierung">§4 Abs. 3 StVO</akn:ref>`,
    )

    href.value =
      "eli/bund/bgbl-1/2001/s1/2022-12-19/1/deu/regelungstext-1/para-4_abs-3.xml"
    expect(updateFn).toBeCalledWith(
      `<akn:ref xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" eId="quot-1_ref-1" href="eli/bund/bgbl-1/2001/s1/2022-12-19/1/deu/regelungstext-1/para-4_abs-3.xml" refersTo="zitierung">§4 Abs. 3 StVO</akn:ref>`,
    )

    expect(updateFn).toBeCalledTimes(2)
  })

  test("should react to changes of the xmlSnippet", async () => {
    const xmlSnippet = ref(
      `<akn:ref xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/" eId="quot-1_ref-1" href="eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/hauptteil-1_abschnitt-erster_para-4_abs-3.xml" refersTo="zitierung">§4 Abs. 3 StVO</akn:ref>`,
    )

    const { href } = useRef(xmlSnippet, (newValue) => {
      xmlSnippet.value = newValue
    })

    expect(href.value).toBe(
      "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/hauptteil-1_abschnitt-erster_para-4_abs-3.xml",
    )

    href.value =
      "eli/bund/bgbl-1/2001/s1/2022-12-19/1/deu/regelungstext-1/para-4_abs-3.xml"

    expect(href.value).toBe(
      "eli/bund/bgbl-1/2001/s1/2022-12-19/1/deu/regelungstext-1/para-4_abs-3.xml",
    )
  })
})
