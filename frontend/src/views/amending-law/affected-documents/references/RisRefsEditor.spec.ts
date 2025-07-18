import { beforeEach, describe, expect, it, vi } from "vitest"
import { render, screen, within } from "@testing-library/vue"
import { nextTick, reactive, ref } from "vue"
import { userEvent } from "@testing-library/user-event"
import type { RouteLocationRaw } from "vue-router"

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

vi.mock("primevue", () => {
  return {
    useToast: () => ({
      add: vi.fn(),
    }),
  }
})

describe("risRefsEditor", () => {
  beforeEach(() => {
    vi.resetAllMocks()
    vi.resetModules()

    renderData.value =
      "<div class='akn-quotedText' data-eId='mod-1_quot-2'>Render of <div class='akn-ref' data-eId='mod-1_quot-2_ref-1'>a ref</div> and <div class='akn-ref' data-eId='mod-1_quot-2_ref-2'>a second ref</div> and <p class='akn-p' data-eId='mod-1_quot-2_p-1'>place for a third ref</p></div>"
    renderIsFetching.value = false
    renderError.value = undefined
  })

  it("should render the html of the second quotedText of the selected mod", async () => {
    vi.doMock("vue-router", () => ({
      useRoute: vi.fn().mockReturnValue({
        params: {
          refEid: undefined,
        },
      }),
      useRouter: vi.fn(),
    }))

    const { default: RisModRefsEditor } = await import(
      "@/views/amending-law/affected-documents/references/RisRefsEditor.vue"
    )

    render(RisModRefsEditor, {
      props: {
        normXml: `
          <akn:act xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/">
            <akn:mod eId='mod-1'>
               <akn:quotedText eId='mod-1_quot-1'>First mod old text</akn:quotedText>
               <akn:quotedText eId='mod-1_quot-2'>Render of <akn:ref eId='mod-1_quot-2_ref-1'>a ref</akn:ref> and <akn:ref eId='mod-1_quot-2_ref-2'>a second ref</akn:ref> and <akn:p eId='mod-1_quot-2_p-1'>place for a third ref</akn:p></akn:quotedText>
            </akn:mod>
            <akn:mod eId='mod-2'>
               <akn:quotedText eId='mod-2_quot-1'>Second mod old text</akn:quotedText>
               <akn:quotedText eId='mod-2_quot-2'>Second mod new text</akn:quotedText>
            </akn:mod>
          </akn:act>
        `,
        eId: "mod-1",
        isSaving: false,
        hasSaved: false,
        saveError: null,
      },
    })

    await nextTick()

    const renderedRef = screen.getByText("a ref")
    expect(renderedRef).toBeInTheDocument()
  })

  it("should save the updated xml from the RisRefSelectionPanel", async () => {
    vi.doMock("vue-router", () => ({
      useRoute: vi.fn().mockReturnValue({
        params: {
          refEid: undefined,
        },
      }),
      useRouter: vi.fn(),
    }))

    const { default: RisModRefsEditor } = await import(
      "@/views/amending-law/affected-documents/references/RisRefsEditor.vue"
    )
    const user = userEvent.setup()

    const { emitted } = render(RisModRefsEditor, {
      props: {
        normXml: `
          <akn:act xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/">
            <akn:mod eId='mod-1'>
               <akn:quotedText eId='mod-1_quot-1'>First mod old text</akn:quotedText>
               <akn:quotedText eId='mod-1_quot-2'>Render of <akn:ref eId='mod-1_quot-2_ref-1'>a ref</akn:ref> and <akn:ref eId='mod-1_quot-2_ref-2'>a second ref</akn:ref> and <akn:p eId='mod-1_quot-2_p-1'>place for a third ref</akn:p></akn:quotedText>
            </akn:mod>
            <akn:mod eId='mod-2'>
               <akn:quotedText eId='mod-2_quot-1'>Second mod old text</akn:quotedText>
               <akn:quotedText eId='mod-2_quot-2'>Second mod new text</akn:quotedText>
            </akn:mod>
          </akn:act>
        `,
        eId: "mod-1_quot-2",
        isSaving: false,
        hasSaved: false,
        saveError: null,
      },
      global: {
        stubs: {
          // we are stubbing the RisRefSelectionPanel here to not need to model the complex interactions in this unit test
          RisRefSelectionPanel: {
            template:
              "<button @click='handleClick'>RisRefSelectionPanel</button>",
            methods: {
              handleClick() {
                this.$emit(
                  "update:xml-snippet",
                  `<akn:quotedText xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/" eId="mod-1_quot-2">NEW XML SNIPPET</akn:quotedText>`,
                )
              },
            },
          },
        },
      },
    })

    await user.click(screen.getByText("RisRefSelectionPanel"))
    await user.click(screen.getByRole("button", { name: "Speichern" }))

    expect(emitted("save")).toHaveLength(1)
    expect(emitted("save")[0]).toEqual([
      `<akn:act xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/">
            <akn:mod eId="mod-1">
               <akn:quotedText eId="mod-1_quot-1">First mod old text</akn:quotedText>
               <akn:quotedText eId="mod-1_quot-2">NEW XML SNIPPET</akn:quotedText>
            </akn:mod>
            <akn:mod eId="mod-2">
               <akn:quotedText eId="mod-2_quot-1">Second mod old text</akn:quotedText>
               <akn:quotedText eId="mod-2_quot-2">Second mod new text</akn:quotedText>
            </akn:mod>
          </akn:act>`,
    ])
  })

  it("should save the updated xml from the RisRefEditorTable", async () => {
    vi.doMock("vue-router", () => ({
      useRoute: vi.fn().mockReturnValue({
        params: {
          refEid: undefined,
        },
      }),
      useRouter: vi.fn(),
    }))

    const { default: RisModRefsEditor } = await import(
      "@/views/amending-law/affected-documents/references/RisRefsEditor.vue"
    )
    const user = userEvent.setup()

    const { emitted } = render(RisModRefsEditor, {
      props: {
        normXml: `
          <akn:act xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/">
            <akn:mod eId='mod-1'>
               <akn:quotedText eId='mod-1_quot-1'>First mod old text</akn:quotedText>
               <akn:quotedText eId='mod-1_quot-2'>Render of <akn:ref eId='mod-1_quot-2_ref-1'>a ref</akn:ref> and <akn:ref eId='mod-1_quot-2_ref-2'>a second ref</akn:ref> and <akn:p eId='mod-1_quot-2_p-1'>place for a third ref</akn:p></akn:quotedText>
            </akn:mod>
            <akn:mod eId='mod-2'>
               <akn:quotedText eId='mod-2_quot-1'>Second mod old text</akn:quotedText>
               <akn:quotedText eId='mod-2_quot-2'>Second mod new text</akn:quotedText>
            </akn:mod>
          </akn:act>
        `,
        eId: "mod-1_quot-2",
        isSaving: false,
        hasSaved: false,
        saveError: null,
      },
      global: {
        stubs: {
          // we are stubbing the RisRefEditorTable here to not need to model the complex interactions in this unit test
          RisRefEditorTable: {
            template: "<button @click='handleClick'>RisRefEditorTable</button>",
            methods: {
              handleClick() {
                this.$emit(
                  "update:xml-snippet",
                  `<akn:quotedText xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/" eId="mod-1_quot-2">NEW XML SNIPPET</akn:quotedText>`,
                )
              },
            },
          },
        },
      },
    })

    await user.click(screen.getByText("RisRefEditorTable"))
    await user.click(screen.getByRole("button", { name: "Speichern" }))

    expect(emitted("save")).toHaveLength(1)
    expect(emitted("save")[0]).toEqual([
      `<akn:act xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/">
            <akn:mod eId="mod-1">
               <akn:quotedText eId="mod-1_quot-1">First mod old text</akn:quotedText>
               <akn:quotedText eId="mod-1_quot-2">NEW XML SNIPPET</akn:quotedText>
            </akn:mod>
            <akn:mod eId="mod-2">
               <akn:quotedText eId="mod-2_quot-1">Second mod old text</akn:quotedText>
               <akn:quotedText eId="mod-2_quot-2">Second mod new text</akn:quotedText>
            </akn:mod>
          </akn:act>`,
    ])
  })

  it("should sync selected refs", async () => {
    const route: RouteLocationRaw = reactive({
      params: {
        refEid: undefined,
      },
    })

    vi.doMock("vue-router", () => ({
      useRoute: vi.fn().mockReturnValue(route),
      useRouter: vi.fn().mockReturnValue({
        replace: (newRoute: { params: { refEid: string | undefined } }) => {
          route.params = newRoute?.params ?? {}
        },
      }),
    }))

    const { default: RisModRefsEditor } = await import(
      "@/views/amending-law/affected-documents/references/RisRefsEditor.vue"
    )
    render(RisModRefsEditor, {
      props: {
        normXml: `
          <akn:act xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/">
            <akn:mod eId='mod-1'>
               <akn:quotedText eId='mod-1_quot-1'>First mod old text</akn:quotedText>
               <akn:quotedText eId='mod-1_quot-2'>Render of <akn:ref eId='mod-1_quot-2_ref-1'>a ref</akn:ref> and <akn:ref eId='mod-1_quot-2_ref-2'>a second ref</akn:ref> and <akn:p eId='mod-1_quot-2_p-1'>place for a third ref</akn:p></akn:quotedText>
            </akn:mod>
            <akn:mod eId='mod-2'>
               <akn:quotedText eId='mod-2_quot-1'>Second mod old text</akn:quotedText>
               <akn:quotedText eId='mod-2_quot-2'>Second mod new text</akn:quotedText>
            </akn:mod>
          </akn:act>
        `,
        eId: "mod-1",
        isSaving: false,
        hasSaved: false,
        saveError: null,
      },
    })

    await nextTick()

    const ref1Region = screen.getByRole("region", { name: "a ref" })
    const ref2Region = screen.getByRole("region", { name: "a second ref" })

    const ref1Highlight = screen.getByRole("button", { name: "a ref" })
    const ref2Highlight = screen.getByRole("button", { name: "a second ref" })

    await userEvent.click(ref2Highlight)

    expect(ref1Region).not.toHaveClass("bg-blue-300")
    expect(ref2Region).toHaveClass("bg-blue-300")
    expect(ref1Highlight).not.toHaveClass("selected")
    expect(ref2Highlight).toHaveClass("selected")

    await userEvent.click(
      within(ref1Region).getByRole("textbox", { name: "ELI mit Zielstelle" }),
    )

    expect(ref1Region).toHaveClass("bg-blue-300")
    expect(ref2Region).not.toHaveClass("bg-blue-300")
    expect(ref1Highlight).toHaveClass("selected")
    expect(ref2Highlight).not.toHaveClass("selected")
  })
})
