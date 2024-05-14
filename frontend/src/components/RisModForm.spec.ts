import { describe, expect, it } from "vitest"
import { render, screen } from "@testing-library/vue"
import RisModForm from "@/components/RisModForm.vue"

describe("RisModForm", () => {
  const textualModType = "replacement"
  const timeBoundaries = [
    { label: "31.12.2024", value: "2024-12-31" },
    { label: "01.01.2025", value: "2025-01-01" },
    { label: "15.06.2026", value: "2026-06-15" },
  ]
  const destinationHref =
    "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/para-1_abs-1/5-53.xml"
  const quotedTextFirst = "Bundesministerium des Innern, für Bau und Heimat"
  const quotedTextSecond = "Bundesministerium des Innern und für Heimat"

  it("Should render the form with only mandatory fields", async () => {
    render(RisModForm, {
      props: {
        id: "risModForm",
        textualModType,
        timeBoundaries,
        destinationHref,
      },
    })

    const formElement = screen.getByTestId("risModForm")
    expect(formElement).toBeInTheDocument()

    const textualModeTypeElement = screen
      .getByTestId("textualModeType")
      .querySelector("input")
    expect(textualModeTypeElement).toBeInTheDocument()
    expect(textualModeTypeElement).toHaveValue("ersetzen")
    expect(textualModeTypeElement).toHaveAttribute("readonly")

    const timeBoundariesElement = screen
      .getByTestId("timeBoundaries")
      .querySelector("select")
    expect(timeBoundariesElement).toBeInTheDocument()
    expect(timeBoundariesElement).toHaveValue("no_choice")
    expect(timeBoundariesElement).toHaveDisplayValue(["Keine Angabe"])
    expect(timeBoundariesElement).not.toHaveAttribute("readonly")

    const timeBoundaryOptionElements =
      timeBoundariesElement?.querySelectorAll("option")
    expect(timeBoundaryOptionElements?.length).toBe(4)
    expect(timeBoundaryOptionElements?.item(0)).toHaveValue(
      timeBoundaries[0]["value"],
    )
    expect(timeBoundaryOptionElements?.item(1)).toHaveValue(
      timeBoundaries[1]["value"],
    )
    expect(timeBoundaryOptionElements?.item(2)).toHaveValue(
      timeBoundaries[2]["value"],
    )

    const destinationHrefEliElement = screen
      .getByTestId("destinationHrefEli")
      .querySelector("input")
    expect(destinationHrefEliElement).toBeInTheDocument()
    expect(destinationHrefEliElement).toHaveValue(
      "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1",
    )
    expect(destinationHrefEliElement).toHaveAttribute("readonly")

    const destinationHrefEidElement = screen
      .getByTestId("destinationHrefEid")
      .querySelector("input")
    expect(destinationHrefEidElement).toBeInTheDocument()
    expect(destinationHrefEidElement).toHaveValue("para-1_abs-1/5-53.xml")
    expect(destinationHrefEidElement).not.toHaveAttribute("readonly")

    const quotedTextFirstElement = screen
      .getByTestId("quotedTextFirst")
      .querySelector("textarea")
    expect(quotedTextFirstElement).toBeInTheDocument()
    expect(quotedTextFirstElement).toHaveValue("")
    expect(quotedTextFirstElement).toHaveAttribute("readonly")

    const quotedTextSecondElement = screen
      .getByTestId("quotedTextSecond")
      .querySelector("textarea")
    expect(quotedTextSecondElement).toBeInTheDocument()
    expect(quotedTextSecondElement).toHaveValue("")
    expect(quotedTextSecondElement).not.toHaveAttribute("readonly")
  })

  it("Should render the form with optional fields", async () => {
    render(RisModForm, {
      props: {
        id: "risModForm",
        textualModType,
        timeBoundaries,
        destinationHref,
        quotedTextFirst,
        quotedTextSecond,
      },
    })

    const formElement = screen.getByTestId("risModForm")
    expect(formElement).toBeInTheDocument()

    const quotedTextFirstElement = screen
      .getByTestId("quotedTextFirst")
      .querySelector("textarea")
    expect(quotedTextFirstElement).toHaveValue(quotedTextFirst)

    const quotedTextSecondElement = screen
      .getByTestId("quotedTextSecond")
      .querySelector("textarea")
    expect(quotedTextSecondElement).toHaveValue(quotedTextSecond)
  })
})
