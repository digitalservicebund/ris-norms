import { describe, expect, it } from "vitest"
import RisZielnormForm from "./RisZielnormForm.vue"
import { render, screen } from "@testing-library/vue"
import userEvent from "@testing-library/user-event"
import { INDETERMINATE_VALUE } from "@/composables/useZielnormReferences"

describe("risZielnormForm", () => {
  it("renders the component", () => {
    render(RisZielnormForm, {
      props: {
        modelValue: { geltungszeit: "", zielnorm: "" },
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
        modelValue: { geltungszeit: "", zielnorm: "" },
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
    expect(options[0]).toHaveTextContent("29.04.2025 (Inkrafttreten)")
    expect(options[1]).toHaveTextContent("30.04.2025 (Außerkrafttreten)")
  })

  it("shows an empty state if there are no Zeitgrenzen", async () => {
    const user = userEvent.setup()

    render(RisZielnormForm, {
      props: {
        modelValue: { geltungszeit: "", zielnorm: "" },
        zeitgrenzen: [],
      },
    })

    await user.click(
      screen.getByRole("combobox", { name: "Geltungszeitregel" }),
    )

    expect(screen.getByText("Keine Zeitgrenzen gefunden")).toBeVisible()
  })

  it("populates the form with the model data", () => {
    render(RisZielnormForm, {
      props: {
        modelValue: { geltungszeit: "gz-1", zielnorm: "eli" },
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
    ).toHaveTextContent("29.04.2025 (Inkrafttreten)")
  })

  it("updates the model when the ELI is changed", async () => {
    const user = userEvent.setup()

    const { emitted } = render(RisZielnormForm, {
      props: {
        modelValue: { geltungszeit: "gz-1", zielnorm: "eli" },
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
      { geltungszeit: "gz-1", zielnorm: "other/eli" },
    ])
  })

  it("updates the model when the Zeitgrenze is changed", async () => {
    const user = userEvent.setup()

    const { emitted } = render(RisZielnormForm, {
      props: {
        modelValue: { geltungszeit: "gz-1", zielnorm: "eli" },
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
    await user.click(
      screen.getByRole("option", { name: "30.04.2025 (Außerkrafttreten)" }),
    )

    expect(emitted("update:modelValue")).toContainEqual([
      { geltungszeit: "gz-2", zielnorm: "eli" },
    ])
  })

  it("shows an indeterminate Zeitgrenze", async () => {
    render(RisZielnormForm, {
      props: {
        modelValue: { geltungszeit: INDETERMINATE_VALUE, zielnorm: "eli" },
        zeitgrenzen: [
          { id: "gz-1", art: "INKRAFT", date: "2025-04-29" },
          { id: "gz-2", art: "AUSSERKRAFT", date: "2025-04-30" },
        ],
      },
    })

    expect(
      screen.getByRole("combobox", { name: "Geltungszeitregel" }),
    ).toHaveTextContent("Mehrere ausgewählt")
  })

  it("shows an indeterminate Zielnorm", async () => {
    render(RisZielnormForm, {
      props: {
        modelValue: { geltungszeit: "gz-1", zielnorm: INDETERMINATE_VALUE },
        zeitgrenzen: [
          { id: "gz-1", art: "INKRAFT", date: "2025-04-29" },
          { id: "gz-2", art: "AUSSERKRAFT", date: "2025-04-30" },
        ],
      },
    })

    const textbox = screen.getByRole("textbox", {
      name: "ELI Zielnormenkomplex",
    })

    expect(textbox).toHaveAttribute("placeholder", "Mehrere")
    expect(textbox).toHaveValue("")
  })

  it("updates indeterminate values", async () => {
    const user = userEvent.setup()

    const { emitted } = render(RisZielnormForm, {
      props: {
        modelValue: {
          geltungszeit: INDETERMINATE_VALUE,
          zielnorm: INDETERMINATE_VALUE,
        },
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
      { geltungszeit: INDETERMINATE_VALUE, zielnorm: "other/eli" },
    ])

    await user.click(
      screen.getByRole("combobox", {
        name: "Geltungszeitregel",
      }),
    )
    await user.click(
      screen.getByRole("option", { name: "30.04.2025 (Außerkrafttreten)" }),
    )

    expect(emitted("update:modelValue")).toContainEqual([
      { geltungszeit: "gz-2", zielnorm: "other/eli" },
    ])
  })

  it("emits an event on save", async () => {
    const user = userEvent.setup()

    const { emitted } = render(RisZielnormForm, {
      props: {
        modelValue: { geltungszeit: "gz-1", zielnorm: "eli" },
      },
    })

    await user.click(screen.getByRole("button", { name: "Speichern" }))

    expect(emitted("save")).toBeTruthy()
  })

  it("sets the updating state", async () => {
    render(RisZielnormForm, {
      props: {
        modelValue: { geltungszeit: "gz-1", zielnorm: "eli" },
        updating: true,
      },
    })

    expect(screen.getByRole("button", { name: "Speichern" })).toBeDisabled()
    expect(
      screen.getByRole("button", { name: "Einträge entfernen" }),
    ).toBeDisabled()
  })

  it("does not set the updating state", async () => {
    render(RisZielnormForm, {
      props: {
        modelValue: { geltungszeit: "gz-1", zielnorm: "eli" },
        updating: false,
      },
    })

    expect(screen.getByRole("button", { name: "Speichern" })).not.toBeDisabled()
    expect(
      screen.getByRole("button", { name: "Einträge entfernen" }),
    ).not.toBeDisabled()
  })

  it("sets the deleting state", async () => {
    render(RisZielnormForm, {
      props: {
        modelValue: { geltungszeit: "gz-1", zielnorm: "eli" },
        deleting: true,
      },
    })

    expect(screen.getByRole("button", { name: "Speichern" })).toBeDisabled()
    expect(
      screen.getByRole("button", { name: "Einträge entfernen" }),
    ).toBeDisabled()
  })

  it("does not set the deleting state", async () => {
    render(RisZielnormForm, {
      props: {
        modelValue: { geltungszeit: "gz-1", zielnorm: "eli" },
        deleting: false,
      },
    })

    expect(screen.getByRole("button", { name: "Speichern" })).not.toBeDisabled()
    expect(
      screen.getByRole("button", { name: "Einträge entfernen" }),
    ).not.toBeDisabled()
  })

  it("emits an event on delete", async () => {
    const user = userEvent.setup()

    const { emitted } = render(RisZielnormForm, {
      props: {
        modelValue: { geltungszeit: "gz-1", zielnorm: "eli" },
      },
    })

    await user.click(screen.getByRole("button", { name: "Einträge entfernen" }))

    expect(emitted("delete")).toBeTruthy()
  })
})
