import { userEvent } from "@testing-library/user-event"
import { render, screen } from "@testing-library/vue"
import { describe, expect, test } from "vitest"
import RisDropdowninput, {
  DropdownInputModelType,
  DropdownItem,
} from "@/components/controls/RisDropdowninput.vue"

function renderComponent(options?: {
  items?: DropdownItem[]
  modelValue?: DropdownInputModelType
  placeholder?: string
}) {
  const user = userEvent.setup()
  const props = {
    modelValue: options?.modelValue,
    items: options?.items ?? [
      { label: "testItem1", value: "t1" },
      { label: "testItem2", value: "t2" },
      { label: "testItem3", value: "t3" },
    ],
    placeholder: options?.placeholder,
  }
  const utils = render(RisDropdowninput, { props })
  return { user, props, ...utils }
}

describe("Dropdown Input", () => {
  test("renders a dropdown", () => {
    renderComponent()
    const input = screen.getByRole("combobox")
    expect(input).toBeInTheDocument()
  })

  test("renders the items", () => {
    renderComponent()
    const items = screen.getAllByRole("option")
    expect(items).toHaveLength(3)
  })

  test("renders the selected item", () => {
    renderComponent({ modelValue: "t2" })
    const input = screen.getByRole("combobox")
    expect(input).toHaveValue("t2")
  })

  test("renders a placeholder", () => {
    renderComponent({ placeholder: "test placeholder", modelValue: "" })
    const input = screen.getByRole("combobox")
    expect(input).toHaveAttribute("data-placeholder", "true")
  })

  test("does not render a placeholder if an item is selected", () => {
    renderComponent({ placeholder: "test placeholder", modelValue: "t2" })
    const input = screen.getByRole("combobox")
    expect(input).not.toHaveAttribute("data-placeholder")
  })

  test("does not render a placeholder if none exists", () => {
    renderComponent({ modelValue: "" })
    const input = screen.getByRole("combobox")
    expect(input).not.toHaveAttribute("data-placeholder")
  })

  test("does not contain a placeholder option if an item is selected", async () => {
    const { rerender } = renderComponent({
      placeholder: "test placeholder",
      modelValue: "",
    })

    const placeholder = screen.getByRole("option", { name: "test placeholder" })
    expect(placeholder).toBeInTheDocument()

    await rerender({ modelValue: "t2", placeholder: "test placeholder" })
    expect(placeholder).not.toBeInTheDocument()
  })

  test("placeholder is disabled", () => {
    renderComponent({ placeholder: "test placeholder" })
    expect(
      screen.getByRole("option", { name: "test placeholder" }),
    ).toBeDisabled()
  })

  test("emits a model update", async () => {
    const user = userEvent.setup()
    const { emitted } = renderComponent({ modelValue: "t1" })

    const input = screen.getByRole("combobox")
    expect(input).toHaveValue("t1")

    await user.selectOptions(input, "t2")
    expect(emitted("update:modelValue")).toEqual([["t2"]])
  })
})
