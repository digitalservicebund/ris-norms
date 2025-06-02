import { describe, expect, it, vi } from "vitest"
import { render, screen } from "@testing-library/vue"
import RisModForm from "@/views/amending-law/articles/editor/single-mods/RisModForm.vue"
import { userEvent } from "@testing-library/user-event"

const add = vi.fn()
vi.mock("primevue", () => {
  return {
    useToast: () => ({
      add: add,
    }),
  }
})

describe("risModForm", () => {
  const timeBoundaries = [
    { date: "2024-12-31", temporalGroupEid: "eid-1" },
    { date: "2025-01-01", temporalGroupEid: "eid-2" },
    { date: "2026-06-15", temporalGroupEid: "eid-3" },
  ]

  it("should render the form with only mandatory fields", async () => {
    const user = userEvent.setup()
    render(RisModForm, {
      props: {
        timeBoundaries,
      },
    })

    // Form
    const formElement = screen.getByTestId("mod-form")
    expect(formElement).toBeInTheDocument()

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
    expect(destinationHrefEliElement).toHaveValue("eli/bund/bgbl-1/2000/1")
    expect(destinationHrefEliElement).toHaveAttribute("readonly")
  })

  it("should render the form when timeBoundaries are empty", async () => {
    const user = userEvent.setup()
    render(RisModForm, {
      props: {
        timeBoundaries: [],
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
        timeBoundaries,
        selectedTimeBoundary: timeBoundaries[1],
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

  it("emits an update events when the dropdown value is changed", async () => {
    const user = userEvent.setup()
    const onUpdateSelectedTimeBoundary = vi.fn()

    const props = {
      timeBoundaries,
      selectedTimeBoundary: timeBoundaries[1],
      "onUpdate:selectedTimeBoundary": onUpdateSelectedTimeBoundary,
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
  })

  it("emits update-mod when the save button is clicked and calls the toast", async () => {
    const user = userEvent.setup()
    const onUpdateMod = vi.fn()

    const { rerender } = render(RisModForm, {
      props: {
        timeBoundaries,
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
