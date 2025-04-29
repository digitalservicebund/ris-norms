import { describe, expect, it } from "vitest"
import RisZielnormForm from "./RisZielnormForm.vue"
import { render, screen } from "@testing-library/vue"
import userEvent from "@testing-library/user-event"

describe("risZielnormForm", () => {
  it("renders the component", () => {
    render(RisZielnormForm, {
      props: {
        modelValue: { geltungszeit: "", typ: "", zielnorm: "" },
      },
    })

    expect(
      screen.getByRole("form", {
        name: "ELIs und Geltungszeitregeln verknüpfen",
      }),
    ).toBeVisible()
  })

  it("populates the select with Zeitgrenzen", async () => {
    const user = userEvent.setup()

    render(RisZielnormForm, {
      props: {
        modelValue: { geltungszeit: "", typ: "", zielnorm: "" },
        zeitgrenzen: [
          { id: "gz-1", art: "INKRAFT", date: "2025-04-29" },
          { id: "gz-2", art: "AUSSERKRAFT", date: "2025-04-30" },
        ],
      },
    })

    await user.click(
      screen.getByRole("combobox", { name: "Geltungszeitregel" }),
    )

    const options = screen.getAllByRole("option")

    expect(options).toHaveLength(2)
    expect(options[0]).toHaveTextContent("29.04.2025")
    expect(options[1]).toHaveTextContent("30.04.2025")
  })

  it("shows an empty state if there are no Zeitgrenzen", async () => {
    const user = userEvent.setup()

    render(RisZielnormForm, {
      props: {
        modelValue: { geltungszeit: "", typ: "", zielnorm: "" },
        zeitgrenzen: [],
      },
    })

    await user.click(
      screen.getByRole("combobox", { name: "Geltungszeitregel" }),
    )

    expect(screen.getByText("Keine Geltungszeiten gefunden")).toBeVisible()
  })

  it("populates the form with the model data", () => {
    render(RisZielnormForm, {
      props: {
        modelValue: { geltungszeit: "gz-1", typ: "", zielnorm: "eli" },
        zeitgrenzen: [
          { id: "gz-1", art: "INKRAFT", date: "2025-04-29" },
          { id: "gz-2", art: "AUSSERKRAFT", date: "2025-04-30" },
        ],
      },
    })

    expect(
      screen.getByRole("textbox", { name: "ELI Zielnormenkomplex" }),
    ).toHaveValue("eli")
    expect(
      screen.getByRole("combobox", { name: "Geltungszeitregel" }),
    ).toHaveTextContent("29.04.2025")
  })

  it("updates the model when the ELI is changed", async () => {
    const user = userEvent.setup()

    const { emitted } = render(RisZielnormForm, {
      props: {
        modelValue: { geltungszeit: "gz-1", typ: "", zielnorm: "eli" },
        zeitgrenzen: [
          { id: "gz-1", art: "INKRAFT", date: "2025-04-29" },
          { id: "gz-2", art: "AUSSERKRAFT", date: "2025-04-30" },
        ],
      },
    })

    const eliInput = screen.getByRole("textbox", {
      name: "ELI Zielnormenkomplex",
    })

    await user.type(eliInput, "other/eli", {
      initialSelectionStart: 0,
      initialSelectionEnd: 3,
    })

    expect(emitted("update:modelValue")).toContainEqual([
      { geltungszeit: "gz-1", typ: "", zielnorm: "other/eli" },
    ])
  })

  it("updates the model when the Zeitgrenze is changed", async () => {
    const user = userEvent.setup()

    const { emitted } = render(RisZielnormForm, {
      props: {
        modelValue: { geltungszeit: "gz-1", typ: "", zielnorm: "eli" },
        zeitgrenzen: [
          { id: "gz-1", art: "INKRAFT", date: "2025-04-29" },
          { id: "gz-2", art: "AUSSERKRAFT", date: "2025-04-30" },
        ],
      },
    })

    await user.click(
      screen.getByRole("combobox", {
        name: "Geltungszeitregel",
      }),
    )
    await user.click(screen.getByRole("option", { name: "30.04.2025" }))

    expect(emitted("update:modelValue")).toContainEqual([
      { geltungszeit: "gz-2", typ: "", zielnorm: "eli" },
    ])
  })

  it("emits an event on save", async () => {
    const user = userEvent.setup()

    const { emitted } = render(RisZielnormForm, {
      props: {
        modelValue: { geltungszeit: "gz-1", typ: "", zielnorm: "eli" },
      },
    })

    await user.click(screen.getByRole("button", { name: "Speichern" }))

    expect(emitted("save")).toBeTruthy()
  })

  it("sets the loading state", async () => {
    render(RisZielnormForm, {
      props: {
        modelValue: { geltungszeit: "gz-1", typ: "", zielnorm: "eli" },
        loading: true,
      },
    })

    expect(screen.getByRole("button", { name: "Speichern" })).toBeDisabled()
  })

  it("does not set the loading state", async () => {
    render(RisZielnormForm, {
      props: {
        modelValue: { geltungszeit: "gz-1", typ: "", zielnorm: "eli" },
        loading: false,
      },
    })

    expect(screen.getByRole("button", { name: "Speichern" })).not.toBeDisabled()
  })

  it("emits an event on delete", async () => {
    const user = userEvent.setup()

    const { emitted } = render(RisZielnormForm, {
      props: {
        modelValue: { geltungszeit: "gz-1", typ: "", zielnorm: "eli" },
      },
    })

    await user.click(screen.getByRole("button", { name: "Einträge entfernen" }))

    expect(emitted("delete")).toBeTruthy()
  })
})
