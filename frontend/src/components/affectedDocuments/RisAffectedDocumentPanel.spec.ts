import { describe, expect, test } from "vitest"
import { render, screen } from "@testing-library/vue"
import RisAffectedDocumentPanel from "@/components/affectedDocuments/RisAffectedDocumentPanel.vue"

describe("RisAffectedDocumentPanel", () => {
  const eli = "eli/bund/bgbl-1/1968/s537/1968-05-19/18/deu/regelungstext-1"
  const title = "Some title"

  test("should show title, eli and edit metadata button", () => {
    render(RisAffectedDocumentPanel, {
      props: {
        eli,
        title,
        asListItem: true,
      },
    })
    // question: How to check, if the element is a <li>? Should we check?
    expect(screen.getByText(title)).toBeInTheDocument()
    expect(screen.getByText(eli)).toBeInTheDocument()
    expect(screen.getByText("Metadaten bearbeiten")).toBeInTheDocument()
  })

  test("should render fna", () => {
    render(RisAffectedDocumentPanel, {
      props: {
        eli,
        title,
        fna: "1234",
        asListItem: true,
      },
    })
    expect(document.body).toHaveTextContent("FNA 1234")
    expect(document.body).not.toHaveTextContent("FNA 1234-")
  })

  test("should render fna and shortTitle", () => {
    render(RisAffectedDocumentPanel, {
      props: {
        eli,
        title,
        fna: "1234",
        shortTitle: "SomeShortTitle",
        asListItem: true,
      },
    })
    expect(document.body).toHaveTextContent("FNA 1234-SomeShortTitle")
  })

  test("should render fna and shortTitle", () => {
    render(RisAffectedDocumentPanel, {
      props: {
        eli,
        title,
        shortTitle: "SomeShortTitle",
        asListItem: true,
      },
    })
    expect(document.body).toHaveTextContent("SomeShortTitle")
    expect(document.body).not.toHaveTextContent("FNA")
  })
})
