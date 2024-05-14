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

  it("Should render the form with only mandatory fields", () => {
    render(RisModForm, {
      props: {
        id: "risModForm",
        textualModType,
        timeBoundaries,
        destinationHref,
      },
    })

    // Form
    const formElement = screen.getByRole("form")
    expect(formElement).toBeInTheDocument()

    // Textual Mode Type
    const textualModeTypeElement = screen.getByRole("textbox", {
      name: "Änderungstyp",
    })
    expect(textualModeTypeElement).toBeInTheDocument()
    expect(textualModeTypeElement).toHaveValue("ersetzen")
    expect(textualModeTypeElement).toHaveAttribute("readonly")

    // Time Boundaries
    const timeBoundariesElement = screen.getByRole("combobox", {
      name: "Zeitgrenze",
    })
    expect(timeBoundariesElement).toBeInTheDocument()
    expect(timeBoundariesElement).toHaveValue("no_choice")
    expect(timeBoundariesElement).toHaveDisplayValue(["Keine Angabe"])
    expect(timeBoundariesElement).not.toHaveAttribute("readonly")

    const timeBoundaryOptionElements = screen.getAllByRole("option")
    expect(timeBoundaryOptionElements.length).toBe(4)
    expect(timeBoundaryOptionElements[0]).toHaveValue(
      timeBoundaries[0]["value"],
    )
    expect(timeBoundaryOptionElements[1]).toHaveValue(
      timeBoundaries[1]["value"],
    )
    expect(timeBoundaryOptionElements[2]).toHaveValue(
      timeBoundaries[2]["value"],
    )

    // Destination Href Eli
    const destinationHrefEliElement = screen.getByRole("textbox", {
      name: "ELI Zielgesetz",
    })
    expect(destinationHrefEliElement).toBeInTheDocument()
    expect(destinationHrefEliElement).toHaveValue(
      "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1",
    )
    expect(destinationHrefEliElement).toHaveAttribute("readonly")

    // Destination Href Eid
    const destinationHrefEidElement = screen.getByRole("textbox", {
      name: "zu ersetzende Textstelle",
    })
    expect(destinationHrefEidElement).toBeInTheDocument()
    expect(destinationHrefEidElement).toHaveValue("para-1_abs-1/5-53.xml")
    expect(destinationHrefEidElement).not.toHaveAttribute("readonly")

    // Quoted Text First
    const quotedTextFirstElement = screen.getByRole("textbox", {
      name: "zu ersetzender Text",
    })
    expect(quotedTextFirstElement).toBeInTheDocument()
    expect(quotedTextFirstElement).toHaveValue("")
    expect(quotedTextFirstElement).toHaveAttribute("readonly")

    // Quoted Text Second
    const quotedTextSecondElement = screen.getByRole("textbox", {
      name: "Neuer Text Inhalt",
    })
    expect(quotedTextSecondElement).toBeInTheDocument()
    expect(quotedTextSecondElement).toHaveValue("")
    expect(quotedTextSecondElement).not.toHaveAttribute("readonly")
  })

  it("Should render the form with optional fields", () => {
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

    const quotedTextFirstElement = screen.getByRole("textbox", {
      name: "zu ersetzender Text",
    })
    expect(quotedTextFirstElement).toHaveValue(quotedTextFirst)

    const quotedTextSecondElement = screen.getByRole("textbox", {
      name: "Neuer Text Inhalt",
    })
    expect(quotedTextSecondElement).toHaveValue(quotedTextSecond)
  })

  it("Should render the form when timeBoundaries are empty", () => {
    render(RisModForm, {
      props: {
        id: "risModForm",
        textualModType,
        timeBoundaries: [],
        destinationHref,
      },
    })

    const timeBoundaryOptionElements = screen.getAllByRole("option")
    expect(timeBoundaryOptionElements.length).toBe(1)
  })

  it("Should render the form when a timeBoundary is pre selected", () => {
    render(RisModForm, {
      props: {
        id: "risModForm",
        textualModType,
        timeBoundaries,
        selectedTimeBoundary: timeBoundaries[1]["value"],
        destinationHref,
      },
    })

    const timeBoundariesElement = screen.getByRole("combobox", {
      name: "Zeitgrenze",
    })
    expect(timeBoundariesElement).toBeInTheDocument()
    expect(timeBoundariesElement).toHaveDisplayValue([
      timeBoundaries[1]["label"],
    ])

    const timeBoundaryOptionElements = screen.getAllByRole(
      "option",
    ) as HTMLOptionElement[]

    const noChoiceOptionIndex = timeBoundaryOptionElements.findIndex(
      (option) => option.value === "no_choice",
    )
    expect(noChoiceOptionIndex).toBeGreaterThan(-1)
    expect(timeBoundaryOptionElements[noChoiceOptionIndex]).toHaveValue(
      "no_choice",
    )
  })
})
