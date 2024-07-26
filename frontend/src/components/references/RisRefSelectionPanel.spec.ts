import { describe, expect, it, vi } from "vitest"
import { render, screen } from "@testing-library/vue"
import { nextTick, ref } from "vue"
import RisRefSelectionPanel from "@/components/references/RisRefSelectionPanel.vue"
import { userEvent } from "@testing-library/user-event"
import { xmlStringToDocument } from "@/services/xmlService"
import { getNodeByEid } from "@/services/ldmldeService"

const renderData = ref(
  "<div class='akn-quotedText' data-eId='quot-1'>Render of <div class='akn-ref' data-eId='quot-1_ref-1'>a ref</div> and <div class='akn-ref' data-eId='quot-1_ref-2'>a second ref</div> and <p class='akn-p' data-eId='quot-1_p-1'>place for a third ref</p></div>",
)
const renderIsFetching = ref(false)
const renderError = ref<string>()

vi.mock("@/composables/useNormRender", () => ({
  useNormRenderHtml: () => ({
    data: renderData,
    isFetching: renderIsFetching,
    error: renderError,
  }),
}))

describe("RisRefSelectionPanel", () => {
  it("Should render the html of the xml snippet", async () => {
    render(RisRefSelectionPanel, {
      props: {
        xmlSnippet: "<xml></xml>",
      },
    })

    await nextTick()

    const renderedRef = screen.getByText("a ref")
    expect(renderedRef).toBeInTheDocument()
  })

  it("Should show loading state while loading", async () => {
    renderIsFetching.value = true

    render(RisRefSelectionPanel, {
      props: {
        xmlSnippet: "<xml></xml>",
      },
    })

    await nextTick()

    const loadingIndicator = screen.getByLabelText("Lädt...")
    expect(loadingIndicator).toBeInTheDocument()

    renderIsFetching.value = false
    await nextTick()

    expect(loadingIndicator).not.toBeInTheDocument()
  })

  it("Should show error when one exists", async () => {
    renderError.value = "A error"

    render(RisRefSelectionPanel, {
      props: {
        xmlSnippet: "<xml></xml>",
      },
    })

    await nextTick()

    const errorMessage = screen.getByText(
      "Die Vorschau konnte nicht geladen werden.",
    )
    expect(errorMessage).toBeInTheDocument()
  })

  it("Highlighting a part of the text creates a new akn:ref element and the xmlSnippet is updated", async () => {
    const renderResult = render(RisRefSelectionPanel, {
      props: {
        xmlSnippet:
          "<akn:quotedText xmlns:akn=\"http://Inhaltsdaten.LegalDocML.de/1.6/\" eId='quot-1'>Render of <akn:ref eId='quot-1_ref-1'>a ref</akn:ref> and <akn:ref eId='quot-1_ref-2'>a second ref</akn:ref> and <akn:p eId='quot-1_p-1'>place for a third ref</akn:p></akn:quotedText>",
      },
    })

    await nextTick()

    await userEvent.pointer([
      {
        keys: "[MouseLeft>]",
        target: screen.getByText("place for a third ref"),
        offset: 6,
      },
      {
        offset: 17,
      },
      { keys: "[/MouseLeft]" },
    ])

    const updateModelValueEvents = renderResult.emitted("update:xmlSnippet")
    expect(updateModelValueEvents).toHaveLength(1)

    const updatedXml = updateModelValueEvents[0] as string
    const updatedXmlNode = xmlStringToDocument(updatedXml)

    const newRef = getNodeByEid(updatedXmlNode, "quot-1_p-1_ref-1")
    expect(newRef).exist
    expect(newRef?.textContent).toEqual("for a third")
  })
})
