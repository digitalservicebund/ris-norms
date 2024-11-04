import RisCheckboxInput from "@/components/controls/RisCheckboxInput.vue"
import { userEvent } from "@testing-library/user-event"
import { render, screen } from "@testing-library/vue"
import { describe, expect, it } from "vitest"

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

describe("risCheckboxInput", () => {
  it("shows a checkbox input element", () => {
    renderComponent()
    const input = screen.queryByRole("checkbox")
    expect(input).toBeInTheDocument()
  })

  it("shows checked", () => {
    renderComponent({ modelValue: true })
    const input: HTMLInputElement = screen.getByRole("checkbox")
    expect(input).toBeChecked()
  })

  it("shows unchecked", () => {
    renderComponent({ modelValue: false })
    const input: HTMLInputElement = screen.getByRole("checkbox")
    expect(input).not.toBeChecked()
  })

  it("emits model update event when user checks the box", async () => {
    const { emitted } = renderComponent()
    const input = screen.getByRole("checkbox")
    await userEvent.click(input)
    expect(emitted("update:modelValue")).toEqual([[true]])
  })

  it("emits model update event when user unchecks the box", async () => {
    const { emitted } = renderComponent({ modelValue: true })
    const input = screen.getByRole("checkbox")
    await userEvent.click(input)
    expect(emitted("update:modelValue")).toEqual([[false]])
  })

  it("renders the checkbox as disabled", () => {
    renderComponent({ readOnly: true })
    const input = screen.getByRole("checkbox")
    expect(input).toBeDisabled()
  })

  it("renders the checkbox as enabled by default", () => {
    renderComponent()
    const input = screen.getByRole("checkbox")
    expect(input).toBeEnabled()
  })

  it("renders the mini variant by default", () => {
    renderComponent()
    const input = screen.getByRole("checkbox")
    expect(input).toHaveClass("ds-checkbox-mini")
  })

  it("renders the regular variant when specified", () => {
    renderComponent({ size: "regular" })
    const input = screen.getByRole("checkbox")
    expect(input).not.toHaveClass("ds-checkbox-small")
  })

  it("renders the small variant when specified", () => {
    renderComponent({ size: "small" })
    const input = screen.getByRole("checkbox")
    expect(input).toHaveClass("ds-checkbox-small")
  })

  it("renders the mini variant when specified", () => {
    renderComponent({ size: "mini" })
    const input = screen.getByRole("checkbox")
    expect(input).toHaveClass("ds-checkbox-mini")
  })

  it("renders the label", () => {
    renderComponent({ label: "Text" })
    const input = screen.getByRole("checkbox")
    expect(input).toHaveAccessibleName("Text")
  })
})
