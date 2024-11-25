import { beforeEach, describe, expect, it, vi } from "vitest"
import { render, screen } from "@testing-library/vue"
import { nextTick, ref } from "vue"
import RisRefSelectionPanel from "@/views/amending-law/affected-documents/references/RisRefSelectionPanel.vue"
import { userEvent } from "@testing-library/user-event"
import { xmlStringToDocument } from "@/services/xmlService"
import { getNodeByEid } from "@/services/ldmldeService"

const renderData = ref<string>()
const renderIsFetching = ref<boolean>()
const renderError = ref<string>()

vi.mock("@/composables/useNormRender", () => ({
  useNormRenderHtml: () => ({
    data: renderData,
    isFetching: renderIsFetching,
    error: renderError,
  }),
}))

describe("risRefSelectionPanel", () => {
  beforeEach(() => {
    renderData.value =
      "<div class='akn-quotedText' data-eId='quot-1'>Render of <div class='akn-ref' data-eId='quot-1_ref-1'>a ref</div> and <div class='akn-ref' data-eId='quot-1_ref-2'>a second ref</div> and <p class='akn-p' data-eId='quot-1_p-1'>place for a third ref</p></div>"
    renderIsFetching.value = false
    renderError.value = undefined
  })

  it("should render the html of the xml snippet", async () => {
    render(RisRefSelectionPanel, {
      props: {
        xmlSnippet: "<xml></xml>",
      },
    })

    await nextTick()

    const renderedRef = screen.getByText("a ref")
    expect(renderedRef).toBeInTheDocument()
  })

  it("should show loading state while loading", async () => {
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

    await expect.poll(() => loadingIndicator).not.toBeInTheDocument()
  })

  it("should show error when one exists", async () => {
    renderError.value = "A error"

    render(RisRefSelectionPanel, {
      props: {
        xmlSnippet: "<xml></xml>",
      },
    })

    await nextTick()

    const errorMessage = screen.getByText(
      "Ein unbekannter Fehler ist aufgetreten.",
    )
    expect(errorMessage).toBeInTheDocument()
  })

  it("highlighting a part of the text creates a new akn:ref element, the xmlSnippet is updated and the newly created element is selected", async () => {
    const { emitted } = render(RisRefSelectionPanel, {
      props: {
        xmlSnippet:
          "<akn:quotedText xmlns:akn=\"http://Inhaltsdaten.LegalDocML.de/1.7.1/\" eId='quot-1'>Render of <akn:ref eId='quot-1_ref-1'>a ref</akn:ref> and <akn:ref eId='quot-1_ref-2'>a second ref</akn:ref> and <akn:p eId='quot-1_p-1'>place for a third ref</akn:p></akn:quotedText>",
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

    const updateXmlSnippetEvents = emitted("update:xmlSnippet")
    expect(updateXmlSnippetEvents).toHaveLength(1)

    const updatedXml = updateXmlSnippetEvents[0] as string
    const updatedXmlNode = xmlStringToDocument(updatedXml)

    const newRef = getNodeByEid(updatedXmlNode, "quot-1_p-1_ref-1")
    expect(newRef).toBeTruthy()
    expect(newRef?.textContent).toEqual("for a third")

    const selectedRefUpdateEvents = emitted("update:selectedRef")
    expect(selectedRefUpdateEvents).toHaveLength(1)
    expect(selectedRefUpdateEvents[0]).toEqual(["quot-1_p-1_ref-1"])
  })

  it("selects a akn:ref element if it is clicked on", async () => {
    const { emitted } = render(RisRefSelectionPanel, {
      props: {
        xmlSnippet: "<xml></xml>",
      },
    })

    await nextTick()

    await userEvent.click(screen.getByText("a ref"))

    const selectedRefUpdateEvents = emitted("update:selectedRef")
    expect(selectedRefUpdateEvents).toHaveLength(1)
    expect(selectedRefUpdateEvents[0]).toEqual(["quot-1_ref-1"])
  })

  it("highlights the selected element", async () => {
    render(RisRefSelectionPanel, {
      props: {
        xmlSnippet: "<xml></xml>",
        selectedRef: "quot-1_ref-2",
      },
    })

    await nextTick()

    expect(screen.getByText("a second ref")).toHaveClass("selected")
  })

  it("click on the delete icon deletes the akn:ref element", async () => {
    const { emitted } = render(RisRefSelectionPanel, {
      props: {
        xmlSnippet:
          "<akn:quotedText xmlns:akn=\"http://Inhaltsdaten.LegalDocML.de/1.7.1/\" eId='quot-1'>Render of <akn:ref eId='quot-1_ref-1'>a ref</akn:ref> and <akn:ref eId='quot-1_ref-2'>a second ref</akn:ref></akn:quotedText>",
      },
    })

    await nextTick()

    await userEvent.click(screen.getByText("a ref"))
    await userEvent.click(screen.getByRole("button", { name: "Löschen" }))

    const updateXmlSnippetEvents = emitted("update:xmlSnippet")
    expect(updateXmlSnippetEvents).toHaveLength(1)
    expect(updateXmlSnippetEvents[0]).toEqual([
      `<akn:quotedText xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.1/" eId="quot-1">Render of a ref and <akn:ref eId="quot-1_ref-2">a second ref</akn:ref></akn:quotedText>`,
    ])
  })
})
