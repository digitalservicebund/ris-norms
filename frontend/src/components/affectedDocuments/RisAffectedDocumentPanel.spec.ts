import RisAffectedDocumentPanel from "@/components/affectedDocuments/RisAffectedDocumentPanel.vue"
import { render, screen } from "@testing-library/vue"
import { describe, expect, test, vi } from "vitest"
import { createRouter, createWebHashHistory } from "vue-router"

describe("RisAffectedDocumentPanel", () => {
  vi.mock("@/composables/useEliPathParameter", () => ({
    useEliPathParameter: vi.fn().mockReturnValue("eliParam"),
  }))

  const router = createRouter({
    history: createWebHashHistory(),
    routes: [{ path: "/:catchAll(.*)*", name: "Route", component: {} }],
  })

  const eli = "eli/bund/bgbl-1/1968/s537/1968-05-19/18/deu/regelungstext-1"
  const title = "Some title"

  test("should show title, eli and edit metadata button", () => {
    render(RisAffectedDocumentPanel, {
      props: { eli, title },
      global: { plugins: [router] },
    })

    expect(screen.getByText(title)).toBeInTheDocument()
    expect(screen.getByText(eli)).toBeInTheDocument()
    expect(screen.getByText("Metadaten bearbeiten")).toBeInTheDocument()
  })

  test("should render fna", () => {
    render(RisAffectedDocumentPanel, {
      props: { eli, title, fna: "1234" },
      global: { plugins: [router] },
    })

    expect(document.body).toHaveTextContent("FNA 1234")
    expect(document.body).not.toHaveTextContent("FNA 1234-")
  })

  test("should render as a list item", () => {
    render(RisAffectedDocumentPanel, {
      props: { eli, title, asListItem: true },
      global: { plugins: [router] },
    })

    expect(screen.getByRole("listitem")).toBeInTheDocument()
  })

  test("should render as a regular container if not told to be a list item", () => {
    render(RisAffectedDocumentPanel, {
      props: { eli, title, asListItem: false },
      global: { plugins: [router] },
    })

    expect(screen.queryByRole("listitem")).not.toBeInTheDocument()
  })

  test("should render fna and short title", () => {
    render(RisAffectedDocumentPanel, {
      props: { eli, title, fna: "1234", shortTitle: "SomeShortTitle" },
      global: { plugins: [router] },
    })

    expect(document.body).toHaveTextContent("FNA 1234-SomeShortTitle")
  })

  test("should render short title without fna", () => {
    render(RisAffectedDocumentPanel, {
      props: { eli, title, shortTitle: "SomeShortTitle" },
      global: { plugins: [router] },
    })

    expect(document.body).toHaveTextContent("SomeShortTitle")
    expect(document.body).not.toHaveTextContent("FNA")
  })

  test("should link to the editor", () => {
    render(RisAffectedDocumentPanel, {
      props: { eli: "foo", title },
      global: { plugins: [router] },
    })

    expect(screen.getByRole("link")).toHaveAttribute(
      "href",
      // Due to the mocking the ELI param is undefined, but we don't care
      // about the specific value anyways
      "#/amending-laws/undefined/affected-documents/foo/edit",
    )
  })
})
