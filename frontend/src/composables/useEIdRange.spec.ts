import { describe, expect, test } from "vitest"
import { useEIdRange } from "@/composables/useEIdRange"
import { ref } from "vue"

describe("useEIdRange", () => {
  test("empty startEId -> empty range", () => {
    const eIds = useEIdRange(ref(null), ref(""), ref())
    expect(eIds.value).toHaveLength(0)
  })

  test("empty endEId -> range is only the start eId", () => {
    const eIds = useEIdRange(ref("eid-1"), ref(""), ref())
    expect(eIds.value).toHaveLength(1)
    expect(eIds.value).toContain("eid-1")
  })

  test("empty html -> range is empty", () => {
    const eIds = useEIdRange(ref("eid-1"), ref("eid-3"), ref())
    expect(eIds.value).toHaveLength(0)
  })

  test("finds all eIds between", () => {
    const eIds = useEIdRange(
      ref(
        "hauptteil-1_para-1_abs-1_untergl-1_listenelem-6_untergl-1_listenelem-a_inhalt-1_text-1_ändbefehl-1_quotstruct-1_abs-2",
      ),
      ref(
        "hauptteil-1_para-1_abs-1_untergl-1_listenelem-6_untergl-1_listenelem-a_inhalt-1_text-1_ändbefehl-1_quotstruct-1_abs-3",
      ),
      ref(`
      <div class="akn-akomaNtoso">
        <section class="akn-paragraph" data-GUID="938a814c-0df3-4a2f-aa32-a99a46ea9967" data-eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-6_untergl-1_listenelem-a_inhalt-1_text-1_ändbefehl-1_quotstruct-1_abs-2">
            <h4>
                <span class="akn-num" data-GUID="e2d46501-b0ee-4a8f-92ee-273bdfdff31b" data-eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-6_untergl-1_listenelem-a_inhalt-1_text-1_ändbefehl-1_quotstruct-1_abs-2_bezeichnung-1">
                    <span class="akn-marker" data-GUID="6069492f-d09f-4d11-8c05-2b408de38008" data-eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-6_untergl-1_listenelem-a_inhalt-1_text-1_ändbefehl-1_quotstruct-1_abs-2_bezeichnung-1_zaehlbez-1" data-name="2"></span>
                    (2)
                </span>
            </h4>
            <span class="akn-content" data-GUID="c07a8d53-e9c7-45dd-9abc-aa9372c0638a" data-eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-6_untergl-1_listenelem-a_inhalt-1_text-1_ändbefehl-1_quotstruct-1_abs-2_inhalt-1">
                <div class="akn-p" data-GUID="db281afd-0a33-4c13-acb8-a2023f1a74f3" data-eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-6_untergl-1_listenelem-a_inhalt-1_text-1_ändbefehl-1_quotstruct-1_abs-2_inhalt-1_text-1">Zur Unterstützung der Implementierung wird eine zentrale Koordinierungsstelle eingerichtet,
                die alle relevanten Aufgaben übernimmt.</div>
            </span>
        </section>
        <section class="akn-paragraph" data-GUID="c882aaef-86d6-4cae-af65-fdd1cec34721" data-eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-6_untergl-1_listenelem-a_inhalt-1_text-1_ändbefehl-1_quotstruct-1_abs-3">
            <h4>
                <span class="akn-num" data-GUID="d00ebe70-f9fa-456f-92ac-bb72ae72b742" data-eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-6_untergl-1_listenelem-a_inhalt-1_text-1_ändbefehl-1_quotstruct-1_abs-3_bezeichnung-1">
                    <span class="akn-marker" data-GUID="edeebaf0-a873-43cf-b6f7-2c449657af7e" data-eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-6_untergl-1_listenelem-a_inhalt-1_text-1_ändbefehl-1_quotstruct-1_abs-3_bezeichnung-1_zaehlbez-1" data-name="3"></span>
                    (3)
                </span>
            </h4>
            <span class="akn-content" data-GUID="3108dab6-e3d5-40ac-9ca3-44d06b945a6c" data-eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-6_untergl-1_listenelem-a_inhalt-1_text-1_ändbefehl-1_quotstruct-1_abs-3_inhalt-1">
                <div class="akn-p" data-GUID="5232cf08-8f32-4400-b5fe-1f5dfb798157" data-eId="hauptteil-1_para-1_abs-1_untergl-1_listenelem-6_untergl-1_listenelem-a_inhalt-1_text-1_ändbefehl-1_quotstruct-1_abs-3_inhalt-1_text-1">Nach Ablauf der Übergangsphase sind alle Verwaltungseinheiten verpflichtet auf die
                neuen Strukturen und Gliederungseinheiten umzustellen.</div>
            </span>
        </section>
      </div>
    `),
    )
    expect(eIds.value).toHaveLength(6)
  })
})
