import { describe, expect, it, vi } from "vitest"
import { render, screen } from "@testing-library/vue"
import RisModForm from "@/components/RisModForm.vue"
import { userEvent } from "@testing-library/user-event"
import { ModType } from "@/types/ModType"

describe("RisModForm", () => {
  const textualModType: ModType = "aenderungsbefehl-ersetzen"
  const timeBoundaries = ["2024-12-31", "2025-01-01", "2026-06-15"]
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
    expect(textualModeTypeElement).toHaveValue("Ersetzen")
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
    expect(timeBoundaryOptionElements[0]).toHaveValue(timeBoundaries[0])
    expect(timeBoundaryOptionElements[1]).toHaveValue(timeBoundaries[1])
    expect(timeBoundaryOptionElements[2]).toHaveValue(timeBoundaries[2])

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
        selectedTimeBoundary: timeBoundaries[1],
        destinationHref,
      },
    })

    const timeBoundariesElement = screen.getByRole("combobox", {
      name: "Zeitgrenze",
    })
    expect(timeBoundariesElement).toBeInTheDocument()
    expect(timeBoundariesElement).toHaveDisplayValue(["1.1.2025"])

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

  it("emits an update when the dropdown value is changed", async () => {
    const user = userEvent.setup()
    const onUpdateSelectedTimeBoundary = vi.fn()

    const props = {
      id: "risModForm",
      textualModType,
      timeBoundaries,
      selectedTimeBoundary: timeBoundaries[1],
      destinationHref,
      "onUpdate:selectedTimeBoundary": onUpdateSelectedTimeBoundary,
    }

    render(RisModForm, {
      props,
    })

    const dropdown = screen.getByRole("combobox", {
      name: "Zeitgrenze",
    })
    expect(dropdown).toBeInTheDocument()

    await user.selectOptions(dropdown, timeBoundaries[2])

    expect(props.selectedTimeBoundary).toStrictEqual(timeBoundaries[1])

    expect(onUpdateSelectedTimeBoundary).toHaveBeenCalledWith(timeBoundaries[2])
  })

  it("emits an update when the destinationHrefEid input is changed", async () => {
    const user = userEvent.setup()
    const onUpdateDestinationHref = vi.fn()

    const props = {
      id: "risModForm",
      textualModType,
      timeBoundaries,
      destinationHref,
      "onUpdate:destinationHref": onUpdateDestinationHref,
    }

    render(RisModForm, {
      props,
    })

    const destinationHrefEidInput = screen.getByRole("textbox", {
      name: "zu ersetzende Textstelle",
    })
    expect(destinationHrefEidInput).toBeInTheDocument()

    await user.type(destinationHrefEidInput, "new-value")

    expect(props.destinationHref).toStrictEqual(destinationHref)

    expect(onUpdateDestinationHref).toHaveBeenCalledWith(
      expect.stringMatching(`${destinationHref}new-value`),
    )
  })

  it("emits an update when the quotedTextSecond input is changed", async () => {
    const user = userEvent.setup()
    const onUpdateQuotedTextSecond = vi.fn()

    const props = {
      id: "risModForm",
      textualModType,
      timeBoundaries,
      destinationHref,
      quotedTextSecond,
      "onUpdate:quotedTextSecond": onUpdateQuotedTextSecond,
    }

    render(RisModForm, {
      props,
    })

    const quotedTextSecondElement = screen.getByRole("textbox", {
      name: "Neuer Text Inhalt",
    })
    expect(quotedTextSecondElement).toBeInTheDocument()

    await user.type(quotedTextSecondElement, "new-value")

    expect(props.quotedTextSecond).toStrictEqual(quotedTextSecond)

    expect(onUpdateQuotedTextSecond).toHaveBeenCalledWith(
      expect.stringMatching(`${quotedTextSecond}new-value`),
    )
  })
})