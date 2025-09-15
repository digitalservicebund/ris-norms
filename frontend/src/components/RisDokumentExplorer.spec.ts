import { DokumentExpressionEli } from "@/lib/eli/DokumentExpressionEli"
import { DokumentManifestationEli } from "@/lib/eli/DokumentManifestationEli"
import type { ErrorResponse } from "@/types/errorResponse"
import type { TocItem } from "@/types/toc"
import userEvent from "@testing-library/user-event"
import { render, screen, within } from "@testing-library/vue"
import {
  afterAll,
  beforeAll,
  beforeEach,
  describe,
  expect,
  it,
  vi,
} from "vitest"
import { ref } from "vue"
import { config } from "@vue/test-utils"
import { ButtonStub } from "@/test-utils/ButtonStub"

const prevStubs = { ...(config.global.stubs || {}) }

beforeAll(() => {
  config.global.stubs = { ...prevStubs, Button: ButtonStub }
})

afterAll(() => {
  config.global.stubs = prevStubs
})

describe("risDokumentExplorer", () => {
  describe("table of contents", () => {
    beforeEach(() => {
      vi.resetModules()
    })

    it("shows the table of contents for the ELI", async () => {
      vi.doMock("@/services/tocService", () => ({
        useGetNormToc: () => ({
          data: ref<TocItem[]>([
            {
              id: "eid-1",
              marker: "§ 1",
              heading: "Test 1",
              type: "article",
              eingebundeneStammformEli: DokumentManifestationEli.fromString(
                "eli/bund/bgbl-1/2024/17/2024-01-24/1/deu/2024-01-24/regelungstext-verkuendung-2.xml",
              ),
            },
            {
              id: "eid-2",
              marker: "§ 2",
              heading: "Test 2",
              type: "article",
            },
            {
              id: "eid-3",
              marker: "§ 3",
              heading: "Test 3",
              type: "article",
            },
          ]),
          error: ref(null),
          isFetching: ref(false),
        }),
      }))

      const { default: RisDokumentExplorer } = await import(
        "./RisDokumentExplorer.vue"
      )

      render(RisDokumentExplorer, {
        props: {
          eli: DokumentExpressionEli.fromString(
            "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-verkuendung-1",
          ),
        },
      })

      const toc = screen.getByRole("tree")
      const buttons = within(toc).getAllByRole("button")

      expect(buttons[0]).toHaveTextContent("§ 1Test 1")
      expect(buttons[1]).toHaveTextContent("§ 2Test 2")
      expect(buttons[2]).toHaveTextContent("§ 3Test 3")
    })

    it("shows an error if the table of contents could not be loaded", async () => {
      vi.doMock("@/services/tocService", () => ({
        useGetNormToc: () => ({
          data: ref(null),
          error: ref<ErrorResponse>({ type: "/errors/example" }),
          isFetching: ref(false),
        }),
      }))

      const { default: RisDokumentExplorer } = await import(
        "./RisDokumentExplorer.vue"
      )

      render(RisDokumentExplorer, {
        props: {
          eli: DokumentExpressionEli.fromString(
            "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-verkuendung-1",
          ),
        },
      })

      expect(screen.queryByRole("tree")).not.toBeInTheDocument()

      expect(
        screen.getByText("Ein unbekannter Fehler ist aufgetreten."),
      ).toBeInTheDocument()
    })

    it("shows an empty state if the table of contents has no entries", async () => {
      vi.doMock("@/services/tocService", () => ({
        useGetNormToc: () => ({
          data: ref([]),
          error: ref(null),
          isFetching: ref(false),
        }),
      }))

      const { default: RisDokumentExplorer } = await import(
        "./RisDokumentExplorer.vue"
      )

      render(RisDokumentExplorer, {
        props: {
          eli: DokumentExpressionEli.fromString(
            "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-verkuendung-1",
          ),
        },
      })

      expect(screen.queryByRole("tree")).not.toBeInTheDocument()

      expect(screen.getByText("Keine Artikel gefunden.")).toBeInTheDocument()
    })

    it("shows a loading spinner while loading", async () => {
      vi.doMock("@/services/tocService", () => ({
        useGetNormToc: () => ({
          data: ref(null),
          error: ref(null),
          isFetching: ref(true),
        }),
      }))

      const { default: RisDokumentExplorer } = await import(
        "./RisDokumentExplorer.vue"
      )

      render(RisDokumentExplorer, {
        props: {
          eli: DokumentExpressionEli.fromString(
            "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-verkuendung-1",
          ),
        },
      })

      expect(screen.queryByRole("tree")).not.toBeInTheDocument()

      expect(
        screen.getByRole("status", { name: "Lädt..." }),
      ).toBeInTheDocument()
    })

    it("emits an event when an element is clicked", async () => {
      const user = userEvent.setup()

      vi.doMock("@/services/tocService", () => ({
        useGetNormToc: () => ({
          data: ref<TocItem[]>([
            {
              id: "eid-1",
              marker: "§ 1",
              heading: "Test 1",
              type: "article",
              eingebundeneStammformEli: DokumentManifestationEli.fromString(
                "eli/bund/bgbl-1/2024/17/2024-01-24/1/deu/2024-01-24/regelungstext-verkuendung-2.xml",
              ),
            },
            {
              id: "eid-2",
              marker: "§ 2",
              heading: "Test 2",
              type: "article",
            },
            {
              id: "eid-3",
              marker: "§ 3",
              heading: "Test 3",
              type: "article",
            },
          ]),
          error: ref(null),
          isFetching: ref(false),
        }),
      }))

      const { default: RisDokumentExplorer } = await import(
        "./RisDokumentExplorer.vue"
      )

      const { emitted } = render(RisDokumentExplorer, {
        props: {
          eli: DokumentExpressionEli.fromString(
            "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-verkuendung-1",
          ),
        },
      })

      const toc = screen.getByRole("tree")

      await user.click(within(toc).getByRole("button", { name: /§ 1/ }))

      expect(emitted("update:eid")).toContainEqual(["eid-1"])
    })
  })

  describe("element detail", () => {
    beforeEach(() => {
      vi.resetModules()

      vi.doMock("@/services/tocService", () => ({
        useGetNormToc: () => ({
          data: ref<TocItem[]>([
            {
              id: "eid-1",
              marker: "§ 1",
              heading: "Test 1",
              type: "article",
              eingebundeneStammformEli: DokumentManifestationEli.fromString(
                "eli/bund/bgbl-1/2024/17/2024-01-24/1/deu/2024-01-24/regelungstext-verkuendung-2.xml",
              ),
            },
            {
              id: "eid-2",
              marker: "§ 2",
              heading: "Test 2",
              type: "article",
            },
          ]),
          error: ref(null),
          isFetching: ref(false),
        }),
      }))
    })

    it("shows the element text", async () => {
      vi.doMock("@/services/elementService", () => ({
        useGetElementHtml: () => ({
          data: ref("<p>Artikel content</p>"),
          error: ref(null),
          isFetching: ref(false),
        }),
      }))

      const { default: RisDokumentExplorer } = await import(
        "./RisDokumentExplorer.vue"
      )

      render(RisDokumentExplorer, {
        props: {
          eli: DokumentExpressionEli.fromString(
            "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-verkuendung-1",
          ),
          eid: "eid-2",
        },
      })

      expect(screen.getByText("Artikel content")).toBeInTheDocument()
    })

    it("shows a placeholder for elements with eingebundener Stammform", async () => {
      const getElementHtml = vi.fn(() => ({}))
      vi.doMock("@/services/elementService", () => ({
        useGetElementHtml: getElementHtml,
      }))

      const { default: RisDokumentExplorer } = await import(
        "./RisDokumentExplorer.vue"
      )

      render(RisDokumentExplorer, {
        props: {
          eli: DokumentExpressionEli.fromString(
            "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-verkuendung-1",
          ),
          eid: "eid-1",
        },
      })

      expect(getElementHtml).toHaveBeenCalledWith(
        expect.anything(),
        expect.objectContaining({ value: undefined }),
      )

      expect(screen.getByRole("button", { name: "Test 1" })).toBeInTheDocument()
    })

    it("shows an error if the element HTML could not be loaded", async () => {
      vi.doMock("@/services/elementService", () => ({
        useGetElementHtml: () => ({
          data: ref(null),
          error: ref<ErrorResponse>({ type: "/errors/example" }),
          isFetching: ref(false),
        }),
      }))

      const { default: RisDokumentExplorer } = await import(
        "./RisDokumentExplorer.vue"
      )

      render(RisDokumentExplorer, {
        props: {
          eli: DokumentExpressionEli.fromString(
            "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-verkuendung-1",
          ),
          eid: "eid-2",
        },
      })

      expect(
        screen.getByText("Ein unbekannter Fehler ist aufgetreten."),
      ).toBeInTheDocument()
    })

    it("shows an empty state if the element HTML is empty", async () => {
      vi.doMock("@/services/elementService", () => ({
        useGetElementHtml: () => ({
          data: ref(""),
          error: ref(null),
          isFetching: ref(false),
        }),
      }))

      const { default: RisDokumentExplorer } = await import(
        "./RisDokumentExplorer.vue"
      )

      render(RisDokumentExplorer, {
        props: {
          eli: DokumentExpressionEli.fromString(
            "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-verkuendung-1",
          ),
          eid: "eid-2",
        },
      })

      expect(
        screen.getByText("Der Artikel hat keinen Inhalt."),
      ).toBeInTheDocument()
    })

    it("shows a loading spinner while loading", async () => {
      vi.doMock("@/services/elementService", () => ({
        useGetElementHtml: () => ({
          data: ref(null),
          error: ref(null),
          isFetching: ref(true),
        }),
      }))

      const { default: RisDokumentExplorer } = await import(
        "./RisDokumentExplorer.vue"
      )

      render(RisDokumentExplorer, {
        props: {
          eli: DokumentExpressionEli.fromString(
            "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-verkuendung-1",
          ),
          eid: "eid-2",
        },
      })

      expect(
        screen.getByRole("status", { name: "Lädt..." }),
      ).toBeInTheDocument()
    })

    it("navigates back to the table of contents", async () => {
      const user = userEvent.setup()

      vi.doMock("@/services/elementService", () => ({
        useGetElementHtml: () => ({
          data: ref("<p>Artikel content</p>"),
          error: ref(null),
          isFetching: ref(false),
        }),
      }))

      const { default: RisDokumentExplorer } = await import(
        "./RisDokumentExplorer.vue"
      )

      const { emitted } = render(RisDokumentExplorer, {
        props: {
          eli: DokumentExpressionEli.fromString(
            "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-verkuendung-1",
          ),
          eid: "eid-2",
        },
      })

      await user.click(
        screen.getByRole("button", { name: "Inhaltsverzeichnis anzeigen" }),
      )

      expect(emitted("update:eid")).toContainEqual([undefined])
    })

    it("shows elements as selected based on the model value", async () => {
      vi.doMock("@/services/elementService", () => ({
        useGetElementHtml: () => ({
          data: ref(`
            <ol>
              <li class="akn-point" data-eid="eid-1">1</li>
              <li class="akn-point" data-eid="eid-2">2</li>
              <li class="akn-point" data-eid="eid-3">3</li>
            </ol>
            <div class="akn-paragraph" data-eid="eid-4">4</div>
          `),
          error: ref(null),
          isFetching: ref(false),
        }),
      }))

      const { default: RisDokumentExplorer } = await import(
        "./RisDokumentExplorer.vue"
      )

      render(RisDokumentExplorer, {
        props: {
          eli: DokumentExpressionEli.fromString(
            "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-verkuendung-1",
          ),
          eid: "eid-2",
          eidsToEdit: ["eid-1"],
        },
      })

      await vi.waitFor(() => {
        expect(screen.getByRole("button", { name: "1" })).toHaveClass(
          "selected",
        )
      })
    })

    it("selects an element", async () => {
      const user = userEvent.setup()

      vi.doMock("@/services/elementService", () => ({
        useGetElementHtml: () => ({
          data: ref(`
            <ol>
              <li class="akn-point" data-eid="eid-1">1</li>
              <li class="akn-point" data-eid="eid-2">2</li>
              <li class="akn-point" data-eid="eid-3">3</li>
            </ol>
            <div class="akn-paragraph" data-eid="eid-4">4</div>
          `),
          error: ref(null),
          isFetching: ref(false),
        }),
      }))

      const { default: RisDokumentExplorer } = await import(
        "./RisDokumentExplorer.vue"
      )

      const { emitted } = render(RisDokumentExplorer, {
        props: {
          eli: DokumentExpressionEli.fromString(
            "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-verkuendung-1",
          ),
          eid: "eid-2",
        },
      })

      await vi.waitFor(async () => {
        await user.click(screen.getByRole("button", { name: "1" }))
        expect(emitted("update:eids-to-edit")).toContainEqual([["eid-1"]])
      })
    })

    it("adds an element to the selection when clicking with ctrl", async () => {
      const user = userEvent.setup()

      vi.doMock("@/services/elementService", () => ({
        useGetElementHtml: () => ({
          data: ref(`
              <ol>
                <li class="akn-point" data-eid="eid-1">1</li>
                <li class="akn-point" data-eid="eid-2">2</li>
                <li class="akn-point" data-eid="eid-3">3</li>
              </ol>
              <div class="akn-paragraph" data-eid="eid-4">4</div>
            `),
          error: ref(null),
          isFetching: ref(false),
        }),
      }))

      const { default: RisDokumentExplorer } = await import(
        "./RisDokumentExplorer.vue"
      )

      const { emitted } = render(RisDokumentExplorer, {
        props: {
          eli: DokumentExpressionEli.fromString(
            "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-verkuendung-1",
          ),
          eid: "eid-2",
        },
      })

      await vi.waitFor(async () => {
        await user.keyboard("{Control>}")
        await user.click(screen.getByRole("button", { name: "1" }))
        await user.click(screen.getByRole("button", { name: "4" }))
        await user.keyboard("{/Control}")
        expect(emitted("update:eids-to-edit")).toContainEqual([
          ["eid-1", "eid-4"],
        ])
      })
    })

    it("adds an element to the selection when clicking with meta", async () => {
      const user = userEvent.setup()

      vi.doMock("@/services/elementService", () => ({
        useGetElementHtml: () => ({
          data: ref(`
                <ol>
                  <li class="akn-point" data-eid="eid-1">1</li>
                  <li class="akn-point" data-eid="eid-2">2</li>
                  <li class="akn-point" data-eid="eid-3">3</li>
                </ol>
                <div class="akn-paragraph" data-eid="eid-4">4</div>
              `),
          error: ref(null),
          isFetching: ref(false),
        }),
      }))

      const { default: RisDokumentExplorer } = await import(
        "./RisDokumentExplorer.vue"
      )

      const { emitted } = render(RisDokumentExplorer, {
        props: {
          eli: DokumentExpressionEli.fromString(
            "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-verkuendung-1",
          ),
          eid: "eid-2",
        },
      })

      await vi.waitFor(async () => {
        await user.keyboard("{Meta>}")
        await user.click(screen.getByRole("button", { name: "1" }))
        await user.click(screen.getByRole("button", { name: "4" }))
        await user.keyboard("{/Meta}")
        expect(emitted("update:eids-to-edit")).toContainEqual([
          ["eid-1", "eid-4"],
        ])
      })
    })

    it("unselects all other elements", async () => {
      const user = userEvent.setup()

      vi.doMock("@/services/elementService", () => ({
        useGetElementHtml: () => ({
          data: ref(`
              <ol>
                <li class="akn-point" data-eid="eid-1">1</li>
                <li class="akn-point" data-eid="eid-2">2</li>
                <li class="akn-point" data-eid="eid-3">3</li>
              </ol>
              <div class="akn-paragraph" data-eid="eid-4">4</div>
            `),
          error: ref(null),
          isFetching: ref(false),
        }),
      }))

      const { default: RisDokumentExplorer } = await import(
        "./RisDokumentExplorer.vue"
      )

      const { emitted } = render(RisDokumentExplorer, {
        props: {
          eli: DokumentExpressionEli.fromString(
            "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-verkuendung-1",
          ),
          eid: "eid-2",
        },
      })

      await vi.waitFor(async () => {
        await user.keyboard("{Control>}")
        await user.click(screen.getByRole("button", { name: "1" }))
        await user.click(screen.getByRole("button", { name: "4" }))
        await user.keyboard("{/Control}")
        await user.click(screen.getByRole("button", { name: "2" }))
        expect(emitted("update:eids-to-edit")).toContainEqual([
          ["eid-1", "eid-4"],
        ])
        expect(emitted("update:eids-to-edit")).toContainEqual([["eid-2"]])
      })
    })

    it("emits an event when selecting a regular element", async () => {
      const user = userEvent.setup()

      vi.doMock("@/services/elementService", () => ({
        useGetElementHtml: () => ({
          data: ref(`
            <ol>
              <li class="akn-point" data-eid="eid-1">1</li>
              <li class="akn-point" data-eid="eid-2">2</li>
              <li class="akn-point" data-eid="eid-3">3</li>
            </ol>
            <div class="akn-paragraph" data-eid="eid-4">4</div>
          `),
          error: ref(null),
          isFetching: ref(false),
        }),
      }))

      const { default: RisDokumentExplorer } = await import(
        "./RisDokumentExplorer.vue"
      )

      const { emitted } = render(RisDokumentExplorer, {
        props: {
          eli: DokumentExpressionEli.fromString(
            "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-verkuendung-1",
          ),
          eid: "eid-2",
        },
      })

      await vi.waitFor(async () => {
        await user.click(screen.getByRole("button", { name: "1" }))
        expect(emitted("selectEingebundeneStammform")).toContainEqual([null])
      })
    })

    it("emits an event when selecting an eingebundene Stammform", async () => {
      const user = userEvent.setup()

      const { default: RisDokumentExplorer } = await import(
        "./RisDokumentExplorer.vue"
      )

      const { emitted } = render(RisDokumentExplorer, {
        props: {
          eli: DokumentExpressionEli.fromString(
            "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-verkuendung-1",
          ),
          eid: "eid-1",
        },
      })

      await vi.waitFor(async () => {
        await user.click(screen.getByRole("button", { name: "Test 1" }))
        expect(emitted("selectEingebundeneStammform")).toContainEqual([
          DokumentManifestationEli.fromString(
            "eli/bund/bgbl-1/2024/17/2024-01-24/1/deu/2024-01-24/regelungstext-verkuendung-2.xml",
          ),
        ])
      })
    })

    it("adds classes for eId highlighting", async () => {
      vi.doMock("@/services/elementService", () => ({
        useGetElementHtml: () => ({
          data: ref(`
            <ol>
              <li class="akn-point" data-eid="eid-1">1</li>
              <li class="akn-point" data-eid="eid-2">2</li>
              <li class="akn-point" data-eid="eid-3">3</li>
            </ol>
            <div class="akn-paragraph" data-eid="eid-4">4</div>
          `),
          error: ref(null),
          isFetching: ref(false),
        }),
      }))

      const { default: RisDokumentExplorer } = await import(
        "./RisDokumentExplorer.vue"
      )

      render(RisDokumentExplorer, {
        props: {
          eli: DokumentExpressionEli.fromString(
            "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-verkuendung-1",
          ),
          eid: "eid-2",
          eIdClasses: { "eid-1": ["example"] },
        },
      })

      await vi.waitFor(() => {
        expect(screen.getByRole("button", { name: "1" })).toHaveClass("example")
      })
    })

    it("adds classes for eId highlighting of eingebundene Stammform", async () => {
      const { default: RisDokumentExplorer } = await import(
        "./RisDokumentExplorer.vue"
      )

      render(RisDokumentExplorer, {
        props: {
          eli: DokumentExpressionEli.fromString(
            "eli/bund/bgbl-1/2023/413/2023-12-29/1/deu/regelungstext-verkuendung-1",
          ),
          eid: "eid-1",
          eIdClasses: { "eid-1": ["example"] },
        },
      })

      await vi.waitFor(() => {
        expect(screen.getByRole("button", { name: "Test 1" })).toHaveClass(
          "example",
        )
      })
    })
  })
})
