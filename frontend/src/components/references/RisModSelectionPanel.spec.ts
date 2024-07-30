import { describe, expect, it, vi } from "vitest"
import { render, screen } from "@testing-library/vue"
import RisModSelectionPanel from "@/components/references/RisModSelectionPanel.vue"
import { nextTick, ref } from "vue"

const renderData = ref(
  "<div>Render <div class='akn-mod' data-eId='eid-1'>a mod</div> and <div class='akn-mod' data-eId='eid-2'>a second mod</div></div>",
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

describe("RisModSelectionPanel", () => {
  it("Should render the html of the norm", async () => {
    render(RisModSelectionPanel, {
      props: {
        normXml: "<xml></xml>",
      },
    })

    await nextTick()

    const renderedMod = screen.getByText("a mod")
    expect(renderedMod).toBeInTheDocument()
  })

  it("Should show loading state while loading", async () => {
    renderIsFetching.value = true

    render(RisModSelectionPanel, {
      props: {
        normXml: "<xml></xml>",
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

    render(RisModSelectionPanel, {
      props: {
        normXml: "<xml></xml>",
      },
    })

    await nextTick()

    const errorMessage = screen.getByText(
      "Die Vorschau konnte nicht geladen werden.",
    )
    expect(errorMessage).toBeInTheDocument()
  })

  it("Clicking on a akn:mod element emits an update for the model with the eid of the clicked on akn:mod element", async () => {
    const renderResult = render(RisModSelectionPanel, {
      props: {
        normXml: "<xml></xml>",
      },
    })

    await nextTick()

    screen.getByText("a mod").click()
    screen.getByText("a second mod").click()

    const updateModelValueEvents = renderResult.emitted("update:modelValue")
    expect(updateModelValueEvents).toHaveLength(2)
    expect(updateModelValueEvents[0]).toEqual(["eid-1"])
    expect(updateModelValueEvents[1]).toEqual(["eid-2"])
  })

  it("The value of the model is selected", async () => {
    render(RisModSelectionPanel, {
      props: {
        normXml: "<xml></xml>",
        modelValue: "eid-2",
      },
    })

    await nextTick()

    const aknModElement = screen.getByText("a second mod")
    expect(aknModElement).toHaveClass("selected")
  })
})
