import { render, screen } from "@testing-library/vue"
import { beforeAll, beforeEach, describe, expect, test, vi } from "vitest"
import { createRouter, createWebHashHistory } from "vue-router"
import { ref } from "vue"
import { Norm } from "@/types/norm"

describe("RisAffectedDocumentPanel", () => {
  const data = ref<Norm | null>(null)
  const isFetching = ref(false)
  const error = ref<Error | null>(null)

  vi.mock("@/composables/useEliPathParameter", () => ({
    useEliPathParameter: vi.fn().mockReturnValue("eliParam"),
  }))

  beforeAll(() => {
    vi.doMock("@/services/normService", () => ({
      useGetNorm: vi.fn().mockReturnValue({
        data,
        isFetching,
        error,
      }),
    }))
  })

  beforeEach(() => {
    data.value = null
    isFetching.value = false
    error.value = null
  })

  const router = createRouter({
    history: createWebHashHistory(),
    routes: [{ path: "/:catchAll(.*)*", name: "Route", component: {} }],
  })

  const eli = "eli/bund/bgbl-1/1968/s537/1968-05-19/18/deu/regelungstext-1"

  test("should show title, eli and edit metadata button", async () => {
    data.value = {
      title: "Some title",
      eli: "eli/bund/bgbl-1/1968/s537/1968-05-19/18/deu/regelungstext-1",
    }

    const { default: RisAffectedDocumentPanel } = await import(
      "@/components/affectedDocuments/RisAffectedDocumentPanel.vue"
    )

    render(RisAffectedDocumentPanel, {
      props: { eli },
      global: { plugins: [router] },
    })

    expect(screen.getByText("Some title")).toBeInTheDocument()
    expect(screen.getByText(eli)).toBeInTheDocument()
    expect(
      screen.getByRole("link", { name: "Metadaten dokumentieren" }),
    ).toBeInTheDocument()
    expect(
      screen.getByRole("link", { name: "Inhaltliche Auszeichnungen" }),
    ).toBeInTheDocument()
  })

  test("should render fna", async () => {
    data.value = {
      eli: "eli/bund/bgbl-1/1968/s537/1968-05-19/18/deu/regelungstext-1",
      fna: "1234",
    }

    const { default: RisAffectedDocumentPanel } = await import(
      "@/components/affectedDocuments/RisAffectedDocumentPanel.vue"
    )

    render(RisAffectedDocumentPanel, {
      props: { eli },
      global: { plugins: [router] },
    })

    expect(document.body).toHaveTextContent("FNA 1234")
    expect(document.body).not.toHaveTextContent("FNA 1234-")
  })

  test("should render as a list item", async () => {
    data.value = {
      eli: "eli/bund/bgbl-1/1968/s537/1968-05-19/18/deu/regelungstext-1",
    }

    const { default: RisAffectedDocumentPanel } = await import(
      "@/components/affectedDocuments/RisAffectedDocumentPanel.vue"
    )

    render(RisAffectedDocumentPanel, {
      props: { eli, asListItem: true },
      global: { plugins: [router] },
    })

    expect(screen.getByRole("listitem")).toBeInTheDocument()
  })

  test("should render as a regular container if not told to be a list item", async () => {
    data.value = {
      eli: "eli/bund/bgbl-1/1968/s537/1968-05-19/18/deu/regelungstext-1",
    }

    const { default: RisAffectedDocumentPanel } = await import(
      "@/components/affectedDocuments/RisAffectedDocumentPanel.vue"
    )

    render(RisAffectedDocumentPanel, {
      props: { eli, asListItem: false },
      global: { plugins: [router] },
    })

    expect(screen.queryByRole("listitem")).not.toBeInTheDocument()
  })

  test("should render fna and short title", async () => {
    data.value = {
      eli: "eli/bund/bgbl-1/1968/s537/1968-05-19/18/deu/regelungstext-1",
      fna: "1234",
      shortTitle: "SomeShortTitle",
    }

    const { default: RisAffectedDocumentPanel } = await import(
      "@/components/affectedDocuments/RisAffectedDocumentPanel.vue"
    )

    render(RisAffectedDocumentPanel, {
      props: { eli },
      global: { plugins: [router] },
    })

    expect(document.body).toHaveTextContent("FNA 1234-SomeShortTitle")
  })

  test("should render short title without fna", async () => {
    data.value = {
      eli: "eli/bund/bgbl-1/1968/s537/1968-05-19/18/deu/regelungstext-1",
      shortTitle: "SomeShortTitle",
    }

    const { default: RisAffectedDocumentPanel } = await import(
      "@/components/affectedDocuments/RisAffectedDocumentPanel.vue"
    )

    render(RisAffectedDocumentPanel, {
      props: { eli },
      global: { plugins: [router] },
    })

    expect(document.body).toHaveTextContent("SomeShortTitle")
    expect(document.body).not.toHaveTextContent("FNA")
  })

  test("should link to the metadata editor", async () => {
    data.value = {
      eli: "eli/bund/bgbl-1/1968/s537/1968-05-19/18/deu/regelungstext-1",
    }

    const { default: RisAffectedDocumentPanel } = await import(
      "@/components/affectedDocuments/RisAffectedDocumentPanel.vue"
    )

    render(RisAffectedDocumentPanel, {
      props: { eli: "bar" },
      global: { plugins: [router] },
    })

    expect(
      screen.getByRole("link", { name: "Metadaten dokumentieren" }),
    ).toHaveAttribute(
      "href",
      // Due to the mocking the ELI param is undefined, but we don't care
      // about the specific value anyways
      "#/amending-laws/undefined/affected-documents/bar/edit",
    )
  })

  test("should link to the reference editor", async () => {
    data.value = {
      eli: "eli/bund/bgbl-1/1968/s537/1968-05-19/18/deu/regelungstext-1",
    }

    const { default: RisAffectedDocumentPanel } = await import(
      "@/components/affectedDocuments/RisAffectedDocumentPanel.vue"
    )

    render(RisAffectedDocumentPanel, {
      props: { eli: "foo" },
      global: { plugins: [router] },
    })

    expect(
      screen.getByRole("link", { name: "Inhaltliche Auszeichnungen" }),
    ).toHaveAttribute(
      "href",
      // Due to the mocking the ELI param is undefined, but we don't care
      // about the specific value anyways
      "#/amending-laws/undefined/affected-documents/foo/references",
    )
  })

  test("should show an error message", async () => {
    error.value = new Error("A error message")

    const { default: RisAffectedDocumentPanel } = await import(
      "@/components/affectedDocuments/RisAffectedDocumentPanel.vue"
    )

    render(RisAffectedDocumentPanel, {
      props: { eli: "foo" },
      global: { plugins: [router] },
    })

    expect(document.body).toHaveTextContent(
      "Ein unbekannter Fehler ist aufgetreten.",
    )
  })

  test("should show a loading indicator", async () => {
    isFetching.value = true

    const { default: RisAffectedDocumentPanel } = await import(
      "@/components/affectedDocuments/RisAffectedDocumentPanel.vue"
    )

    render(RisAffectedDocumentPanel, {
      props: { eli: "foo" },
      global: { plugins: [router] },
    })

    expect(screen.getByLabelText("Lädt...")).toBeInTheDocument()
  })
})
