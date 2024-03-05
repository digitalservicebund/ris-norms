import { describe, expect, test } from "vitest"
import { render, screen } from "@testing-library/vue"
import RisAffectedDocumentPanel from "@/components/affectedDocuments/RisAffectedDocumentPanel.vue"

describe("RisAffectedDocumentPanel", () => {
  const eli = "eli/bund/bgbl-1/1968/s537/1968-05-19/18/deu/regelungstext-1"
  const title = "Some title"

  test("should show title, eli and edit metadata button", () => {
    render(RisAffectedDocumentPanel, {
      props: { eli, title },
    })

    expect(screen.getByText(title)).toBeInTheDocument()
    expect(screen.getByText(eli)).toBeInTheDocument()
    expect(screen.getByText("Metadaten bearbeiten")).toBeInTheDocument()
  })

  test("should render fna", () => {
    render(RisAffectedDocumentPanel, {
      props: { eli, title, fna: "1234" },
    })

    expect(document.body).toHaveTextContent("FNA 1234")
    expect(document.body).not.toHaveTextContent("FNA 1234-")
  })

  test("should render as a list item", () => {
    render(RisAffectedDocumentPanel, {
      props: { eli, title, asListItem: true },
    })

    expect(screen.getByRole("listitem")).toBeInTheDocument()
  })

  test("should render as a regular container if not told to be a list item", () => {
    render(RisAffectedDocumentPanel, {
      props: { eli, title, asListItem: false },
    })

    expect(screen.queryByRole("listitem")).not.toBeInTheDocument()
  })

  test("should render fna and short title", () => {
    render(RisAffectedDocumentPanel, {
      props: { eli, title, fna: "1234", shortTitle: "SomeShortTitle" },
    })

    expect(document.body).toHaveTextContent("FNA 1234-SomeShortTitle")
  })

  test("should render short title without fna", () => {
    render(RisAffectedDocumentPanel, {
      props: { eli, title, shortTitle: "SomeShortTitle" },
    })

    expect(document.body).toHaveTextContent("SomeShortTitle")
    expect(document.body).not.toHaveTextContent("FNA")
  })
})
