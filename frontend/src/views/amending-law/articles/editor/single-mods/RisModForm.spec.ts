import { describe, expect, it, vi } from "vitest"
import { render, screen, within } from "@testing-library/vue"
import RisModForm from "@/views/amending-law/articles/editor/single-mods/RisModForm.vue"
import { userEvent } from "@testing-library/user-event"
import { ModType } from "@/types/ModType"

const add = vi.fn()
vi.mock("primevue/usetoast", () => {
  return {
    useToast: () => ({
      add: add,
    }),
  }
})

describe("risModForm", () => {
  const textualModType: ModType = "aenderungsbefehl-ersetzen"
  const timeBoundaries = [
    { date: "2024-12-31", temporalGroupEid: "eid-1" },
    { date: "2025-01-01", temporalGroupEid: "eid-2" },
    { date: "2026-06-15", temporalGroupEid: "eid-3" },
  ]
  const destinationHref =
    "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/art-1_abs-1/5-53.xml"
  const quotedTextFirst = "Bundesministerium des Innern, für Bau und Heimat"
  const quotedTextSecond = "Bundesministerium des Innern und für Heimat"
  const quotedStructureContent = "<quotedStructure>content</quotedStructure>"
  const targetLawHtml = "<p>Target Law Content</p>"

  it("should render the form with only mandatory fields", async () => {
    const user = userEvent.setup()
    render(RisModForm, {
      props: {
        textualModType,
        timeBoundaries,
        destinationHref,
      },
    })

    // Form
    const formElement = screen.getByTestId("mod-form")
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
    expect(timeBoundariesElement).toHaveTextContent("Keine Angabe")
    expect(timeBoundariesElement).not.toHaveAttribute("readonly")

    await user.click(timeBoundariesElement)

    const timeBoundaryOptionElements = screen.getAllByRole("option")
    expect(timeBoundaryOptionElements.length).toBe(4)
    expect(timeBoundaryOptionElements[0]).toHaveTextContent("31.12.2024")
    expect(timeBoundaryOptionElements[1]).toHaveTextContent("01.01.2025")
    expect(timeBoundaryOptionElements[2]).toHaveTextContent("15.06.2026")

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
    expect(destinationHrefEidElement).toHaveValue("art-1_abs-1/5-53.xml")
    expect(destinationHrefEidElement).not.toHaveAttribute("readonly")

    // Quoted Text Second
    const quotedTextSecondElement = screen.getByRole("textbox", {
      name: "Neuer Text Inhalt",
    })
    expect(quotedTextSecondElement).toBeInTheDocument()
    expect(quotedTextSecondElement).toHaveValue("")
    expect(quotedTextSecondElement).not.toHaveAttribute("readonly")
  })

  it("should render the form with conditional fields for when textualModType === 'aenderungsbefehl-ersetzen'", () => {
    render(RisModForm, {
      props: {
        textualModType,
        timeBoundaries,
        destinationHref,
        quotedTextFirst,
        quotedTextSecond,
        targetLawHtml,
      },
    })

    expect(
      screen.getByRole("textbox", {
        name: "zu ersetzende Textstelle",
      }),
    ).toHaveValue("art-1_abs-1/5-53.xml")

    expect(screen.getByLabelText("zu ersetzender Text")).toHaveTextContent(
      "Target Law Content",
    )

    expect(
      screen.getByRole("textbox", {
        name: "Neuer Text Inhalt",
      }),
    ).toHaveValue(quotedTextSecond)

    expect(screen.queryByTestId("elementToBeReplaced")).not.toBeInTheDocument()
    expect(screen.queryByTestId("replacingElement")).not.toBeInTheDocument()
  })

  it("should render the form with conditional fields for when textualModType === 'aenderungsbefehl-ersetzen' and there is a quotedStructureContent", () => {
    render(RisModForm, {
      props: {
        textualModType,
        timeBoundaries,
        destinationHref,
        targetLawHtml,
        quotedStructureContent,
      },
    })

    expect(screen.getByTestId("elementToBeReplaced").innerHTML).toContain(
      targetLawHtml,
    )
    expect(screen.getByTestId("replacingElement")).toHaveTextContent("content")

    expect(
      screen.queryByRole("textbox", {
        name: "zu ersetzende Textstelle",
      }),
    ).not.toBeInTheDocument()

    expect(
      screen.queryByRole("textbox", {
        name: "zu ersetzender Text",
      }),
    ).not.toBeInTheDocument()

    expect(
      screen.queryByRole("textbox", {
        name: "Neuer Text Inhalt",
      }),
    ).not.toBeInTheDocument()
  })

  it("should render the form when timeBoundaries are empty", async () => {
    const user = userEvent.setup()
    render(RisModForm, {
      props: {
        textualModType,
        timeBoundaries: [],
        destinationHref,
      },
    })

    const timeBoundariesElement = screen.getByRole("combobox", {
      name: "Zeitgrenze",
    })
    await user.click(timeBoundariesElement)
    const timeBoundaryOptionElements = screen.getAllByRole("option")
    expect(timeBoundaryOptionElements.length).toBe(1)
  })

  it("should render the form when a timeBoundary is pre selected", async () => {
    const user = userEvent.setup()
    render(RisModForm, {
      props: {
        textualModType,
        timeBoundaries,
        selectedTimeBoundary: timeBoundaries[1],
        destinationHref,
      },
    })

    const timeBoundariesElement = screen.getByRole("combobox", {
      name: "Zeitgrenze",
    })

    expect(timeBoundariesElement).toHaveTextContent("01.01.2025")

    await user.click(timeBoundariesElement)
    const timeBoundaryOptionElements = screen.getAllByRole("option")
    const noChoiceOptionIndex = timeBoundaryOptionElements.findIndex(
      (option) => option.textContent === "Keine Angabe",
    )
    expect(noChoiceOptionIndex).not.toBe(-1)
    expect(timeBoundaryOptionElements[noChoiceOptionIndex]).toHaveTextContent(
      "Keine Angabe",
    )
  })

  it("should render the form with quoted structure content", () => {
    render(RisModForm, {
      props: {
        textualModType,
        timeBoundaries,
        destinationHref,
        quotedStructureContent,
        targetLawHtml,
      },
    })

    const elementToBeReplaced = screen.getByTestId("elementToBeReplaced")
    expect(elementToBeReplaced).toBeInTheDocument()
    expect(elementToBeReplaced.innerHTML).toContain("Target Law Content")

    const quotedStructureElement = screen.getByTestId("replacingElement")
    expect(quotedStructureElement).toBeInTheDocument()
    expect(quotedStructureElement).toHaveTextContent("content")
  })

  it("emits both an update & generate preview events when the dropdown value is changed", async () => {
    const user = userEvent.setup()
    const onUpdateSelectedTimeBoundary = vi.fn()
    const onGeneratePreview = vi.fn()

    const props = {
      textualModType,
      timeBoundaries,
      selectedTimeBoundary: timeBoundaries[1],
      destinationHref,
      "onUpdate:selectedTimeBoundary": onUpdateSelectedTimeBoundary,
      "onGenerate-preview": onGeneratePreview,
    }

    render(RisModForm, {
      props,
    })

    const dropdown = screen.getByRole("combobox", {
      name: "Zeitgrenze",
    })

    await user.click(dropdown)

    const timeBoundaryOptionElements = screen.getByRole("option", {
      name: "15.06.2026",
    })
    await user.click(timeBoundaryOptionElements)
    expect(props.selectedTimeBoundary).toStrictEqual(timeBoundaries[1])
    expect(onUpdateSelectedTimeBoundary).toHaveBeenCalledWith(timeBoundaries[2])
    expect(onGeneratePreview).toHaveBeenCalled()
  })

  it("emits an update when the destinationHrefEid input is changed", async () => {
    const user = userEvent.setup()
    const onUpdateDestinationHref = vi.fn()

    const props = {
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

  it("emits a generate preview event when the destinationHrefEid input is changed and then loses focus", async () => {
    const user = userEvent.setup()
    const onGeneratePreview = vi.fn()

    const props = {
      textualModType,
      timeBoundaries,
      destinationHref,
      "onGenerate-preview": onGeneratePreview,
    }

    render(RisModForm, {
      props,
    })

    const destinationHrefEidInput = screen.getByRole("textbox", {
      name: "zu ersetzende Textstelle",
    })

    await user.type(destinationHrefEidInput, "new-value")
    await user.tab()
    expect(onGeneratePreview).toHaveBeenCalled()
  })

  it("emits an update & generate preview when the a new destination is selected", async () => {
    const user = userEvent.setup()

    const { emitted } = render(RisModForm, {
      props: {
        textualModType,
        timeBoundaries,
        destinationHref:
          "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/art-1_abs-1/5-53.xml",
        targetLawHtml: "<span data-eId='span-1'>Test</span>",
        targetLaw: `<akn:akonaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/"><akn:span eId="span-1">Test</akn:span></akn:akonaNtoso>`,
      },
    })

    await user.pointer([
      {
        keys: "[MouseLeft>]",
        target: within(screen.getByLabelText("zu ersetzender Text")).getByText(
          "Test",
        ),
        offset: 1,
      },
      {
        offset: 3,
      },
      { keys: "[/MouseLeft]" },
    ])

    expect(emitted("update:destinationHref")).toHaveLength(1)
    expect(emitted("update:destinationHref")[0]).toContain(
      "eli/bund/bgbl-1/1990/s2954/2022-12-19/1/deu/regelungstext-1/span-1/1-3.xml",
    )

    expect(emitted("generate-preview")).toHaveLength(1)
  })

  it("emits an update when the quotedTextSecond input is changed", async () => {
    const user = userEvent.setup()
    const onUpdateQuotedTextSecond = vi.fn()
    const onGeneratePreview = vi.fn()

    const props = {
      textualModType,
      timeBoundaries,
      destinationHref,
      quotedTextSecond,
      "onUpdate:quotedTextSecond": onUpdateQuotedTextSecond,
      "onGenerate-preview": onGeneratePreview,
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

    await user.tab()
    expect(onGeneratePreview).toHaveBeenCalled()
  })

  it("emits a generate preview event when the quotedTextSecond input is changed and then loses focus", async () => {
    const user = userEvent.setup()
    const onGeneratePreview = vi.fn()

    const props = {
      textualModType,
      timeBoundaries,
      destinationHref,
      quotedTextSecond,
      "onGenerate-preview": onGeneratePreview,
    }

    render(RisModForm, {
      props,
    })

    const quotedTextSecondElement = screen.getByRole("textbox", {
      name: "Neuer Text Inhalt",
    })

    await user.type(quotedTextSecondElement, "new-value")
    await user.tab()
    expect(onGeneratePreview).toHaveBeenCalled()
  })

  it("emits generate-preview when the preview button is clicked", async () => {
    const user = userEvent.setup()
    const onGeneratePreview = vi.fn()

    render(RisModForm, {
      props: {
        textualModType,
        timeBoundaries,
        destinationHref,
        quotedTextSecond,
        "onGenerate-preview": onGeneratePreview,
      },
    })

    const previewButton = screen.getByRole("button", {
      name: "Vorschau",
    })
    expect(previewButton).toBeInTheDocument()

    await user.click(previewButton)

    expect(onGeneratePreview).toHaveBeenCalled()
  })

  it("emits update-mod when the save button is clicked and calls the toast", async () => {
    const user = userEvent.setup()
    const onUpdateMod = vi.fn()

    const { rerender } = render(RisModForm, {
      props: {
        textualModType,
        timeBoundaries,
        destinationHref,
        quotedTextSecond,
        "onUpdate-mod": onUpdateMod,
      },
    })

    const saveButton = screen.getByRole("button", {
      name: "Speichern",
    })
    expect(saveButton).toBeInTheDocument()

    await user.click(saveButton)

    expect(onUpdateMod).toHaveBeenCalled()

    await rerender({
      isUpdatingFinished: true,
    })

    expect(add).toHaveBeenCalled()
  })
})
