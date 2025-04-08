import userEvent from "@testing-library/user-event"
import { render, screen, within } from "@testing-library/vue"
import { describe, expect, it } from "vitest"
import RisZeitgrenzenList from "./RisZeitgrenzenList.vue"

describe("risZeitgrenzenList", () => {
  it("shows an empty state when the items list is empty", () => {
    render(RisZeitgrenzenList, {
      props: {
        modelValue: [],
      },
    })

    expect(
      screen.getByText("Es wurden noch keine Geltungszeiten angelegt."),
    ).toBeVisible()
  })

  it("shows a list of items", () => {
    render(RisZeitgrenzenList, {
      props: {
        modelValue: [
          { date: "2025-04-08", art: "inkrafttreten" },
          { date: "2025-04-10", art: "ausserkrafttreten" },
        ],
      },
    })

    const items = screen.getAllByRole("listitem")
    expect(items).toHaveLength(2)

    expect(
      within(items[0]).getByRole("checkbox", { name: "unbestimmt" }),
    ).not.toBeChecked()
    expect(
      within(items[0]).getByRole("textbox", { name: "Geltungszeit" }),
    ).toHaveValue("2025-04-08")
    expect(
      within(items[0]).getByRole("combobox", { name: "Inkrafttreten" }),
    ).toBeVisible()

    expect(
      within(items[1]).getByRole("checkbox", { name: "unbestimmt" }),
    ).not.toBeChecked()
    expect(
      within(items[1]).getByRole("textbox", { name: "Geltungszeit" }),
    ).toHaveValue("2025-04-10")
    expect(
      within(items[1]).getByRole("combobox", { name: "Außerkrafttreten" }),
    ).toBeVisible()

    expect(
      screen.queryByText("Es wurden noch keine Geltungszeiten angelegt."),
    ).not.toBeInTheDocument()
  })

  it("emits the updated list of items when an item is changed", async () => {
    const user = userEvent.setup()
    const { emitted } = render(RisZeitgrenzenList, {
      props: {
        modelValue: [
          { date: "2025-04-08", art: "inkrafttreten" },
          { date: "2025-04-10", art: "ausserkrafttreten" },
        ],
      },
    })

    const [item] = screen.getAllByRole("listitem")
    const textbox = within(item).getByRole("textbox", { name: "Geltungszeit" })
    await user.clear(textbox)
    await user.type(textbox, "2025-05-30")

    expect(emitted("update:modelValue")).toContainEqual([
      [
        { date: "2025-05-30", art: "inkrafttreten" },
        { date: "2025-04-10", art: "ausserkrafttreten" },
      ],
    ])
  })

  it("removes an item from the list", async () => {
    const user = userEvent.setup()
    const { emitted } = render(RisZeitgrenzenList, {
      props: {
        modelValue: [
          { date: "2025-04-08", art: "inkrafttreten" },
          { date: "2025-04-10", art: "ausserkrafttreten" },
        ],
      },
    })

    const [, item] = screen.getAllByRole("listitem")
    await user.click(
      within(item).getByRole("button", {
        name: "Zeitgrenze vom 10.04.2025 entfernen",
      }),
    )

    expect(emitted("update:modelValue")).toContainEqual([
      [{ date: "2025-04-08", art: "inkrafttreten" }],
    ])
  })

  it("adds an item to the list", async () => {
    const user = userEvent.setup()
    const { emitted } = render(RisZeitgrenzenList, {
      props: {
        modelValue: [
          { date: "2025-04-08", art: "inkrafttreten" },
          { date: "2025-04-10", art: "ausserkrafttreten" },
        ],
      },
    })

    await user.click(
      screen.getByRole("button", { name: "Geltungszeit hinzufügen" }),
    )

    expect(emitted("update:modelValue")).toContainEqual([
      [
        { date: "2025-04-08", art: "inkrafttreten" },
        { date: "2025-04-10", art: "ausserkrafttreten" },
        { date: "", art: "inkrafttreten" },
      ],
    ])
  })

  it("adds an item to an empty list", async () => {
    const user = userEvent.setup()
    const { emitted } = render(RisZeitgrenzenList, {
      props: {
        modelValue: [],
      },
    })

    await user.click(
      screen.getByRole("button", { name: "Geltungszeit hinzufügen" }),
    )

    expect(emitted("update:modelValue")).toContainEqual([
      [{ date: "", art: "inkrafttreten" }],
    ])
  })

  it("should focus the Geltungszeit textbox when adding a new item", async () => {
    const { rerender } = render(RisZeitgrenzenList, {
      props: {
        modelValue: [],
      },
    })

    await rerender({ modelValue: [{ date: "", art: "inkrafttreten" }] })

    expect(screen.getByRole("textbox", { name: "Geltungszeit" })).toHaveFocus()
  })

  it("does not allow adding more than 100 entries", () => {
    render(RisZeitgrenzenList, {
      props: {
        modelValue: Array(100).fill({
          date: "2025-04-08",
          art: "inkrafttreten",
        }),
      },
    })

    expect(
      screen.getByRole("button", { name: "Geltungszeit hinzufügen" }),
    ).toBeDisabled()
  })
})
