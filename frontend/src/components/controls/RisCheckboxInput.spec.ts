import RisCheckboxInput from "@/components/controls/RisCheckboxInput.vue"
import { userEvent } from "@testing-library/user-event"
import { render, screen } from "@testing-library/vue"
import { describe, expect, test } from "vitest"

function renderComponent(options?: {
  modelValue?: boolean
  label?: string
  size?: InstanceType<typeof RisCheckboxInput>["$props"]["size"]
  readOnly?: boolean
}) {
  const user = userEvent.setup()
  const props = {
    id: "identifier",
    modelValue: options?.modelValue,
    label: options?.label,
    size: options?.size,
    readOnly: options?.readOnly,
  }
  const utils = render(RisCheckboxInput, { props })
  return { user, props, ...utils }
}

describe("RisCheckboxInput", () => {
  test("shows a checkbox input element", () => {
    renderComponent()
    const input = screen.queryByRole("checkbox")
    expect(input).toBeInTheDocument()
  })

  test("shows checked", () => {
    renderComponent({ modelValue: true })
    const input: HTMLInputElement = screen.getByRole("checkbox")
    expect(input).toBeChecked()
  })

  test("shows unchecked", () => {
    renderComponent({ modelValue: false })
    const input: HTMLInputElement = screen.getByRole("checkbox")
    expect(input).not.toBeChecked()
  })

  test("emits model update event when user checks the box", async () => {
    const { emitted } = renderComponent()
    const input = screen.getByRole("checkbox")
    await userEvent.click(input)
    expect(emitted("update:modelValue")).toEqual([[true]])
  })

  test("emits model update event when user unchecks the box", async () => {
    const { emitted } = renderComponent({ modelValue: true })
    const input = screen.getByRole("checkbox")
    await userEvent.click(input)
    expect(emitted("update:modelValue")).toEqual([[false]])
  })

  test("renders the checkbox as disabled", () => {
    renderComponent({ readOnly: true })
    const input = screen.getByRole("checkbox")
    expect(input).toBeDisabled()
  })

  test("renders the checkbox as enabled by default", () => {
    renderComponent()
    const input = screen.getByRole("checkbox")
    expect(input).toBeEnabled()
  })

  test("renders the mini variant by default", () => {
    renderComponent()
    const input = screen.getByRole("checkbox")
    expect(input).toHaveClass("ds-checkbox-mini")
  })

  test("renders the regular variant when specified", () => {
    renderComponent({ size: "regular" })
    const input = screen.getByRole("checkbox")
    expect(input).not.toHaveClass("ds-checkbox-small")
  })

  test("renders the small variant when specified", () => {
    renderComponent({ size: "small" })
    const input = screen.getByRole("checkbox")
    expect(input).toHaveClass("ds-checkbox-small")
  })

  test("renders the mini variant when specified", () => {
    renderComponent({ size: "mini" })
    const input = screen.getByRole("checkbox")
    expect(input).toHaveClass("ds-checkbox-mini")
  })

  test("renders the label", () => {
    renderComponent({ label: "Text" })
    const input = screen.getByRole("checkbox")
    expect(input).toHaveAccessibleName("Text")
  })
})
