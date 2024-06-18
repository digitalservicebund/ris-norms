import { ValidationError } from "@/types/validationError"
import { userEvent } from "@testing-library/user-event"
import { render, screen } from "@testing-library/vue"
import { describe, expect, test } from "vitest"
import { nextTick } from "vue"
import RisDateInput from "./RisDateInput.vue"

function renderComponent(options?: {
  modelValue?: string
  validationError?: ValidationError
  isReadOnly?: boolean
  size?: "regular" | "medium" | "small"
}) {
  const user = userEvent.setup()
  const props = {
    id: "identifier",
    modelValue: options?.modelValue,
    validationError: options?.validationError,
    isReadOnly: options?.isReadOnly,
    size: options?.size,
  }
  const utils = render(RisDateInput, { props })
  return { user, props, ...utils }
}

describe("DateInput", () => {
  test("shows an date input element", () => {
    renderComponent()
    const input = screen.getByRole<HTMLInputElement>("textbox")

    expect(input).toBeInTheDocument()
    expect(input?.type).toBe("text")
  })

  test("allows to type date inside input", async () => {
    renderComponent()
    const input = screen.getByRole("textbox")

    await userEvent.type(input, "12.05.2020")

    expect(input).toHaveValue("12.05.2020")
  })

  test("displays modelValue in correct format", async () => {
    renderComponent({ modelValue: "2022-05-13" })
    const input = screen.getByRole("textbox")

    expect(input).toHaveValue("13.05.2022")
  })

  test("emits model update event when input completed and valid", async () => {
    const { emitted } = renderComponent({
      modelValue: "2022-05-13T18:08:14.036Z",
    })
    const input = screen.getByRole("textbox")
    expect(input).toHaveValue("13.05.2022")
    await userEvent.clear(input)
    await userEvent.type(input, "14.05.2022")
    await nextTick()

    expect(input).toHaveValue("14.05.2022")

    expect(emitted()["update:modelValue"]).toEqual([
      [undefined],
      ["2022-05-14"],
    ])
  })

  test("updates when the model is changed to empty string", async () => {
    const { rerender } = renderComponent({ modelValue: "2024-04-22" })

    const input = screen.getByRole("textbox")
    expect(input).toHaveValue("22.04.2024")

    await rerender({ modelValue: "" })
    expect(input).toHaveValue("")
  })

  test("updates when the model is changed to undefined", async () => {
    const { rerender } = renderComponent({ modelValue: "2024-04-22" })

    const input = screen.getByRole("textbox")
    expect(input).toHaveValue("22.04.2024")

    await rerender({ modelValue: undefined })
    expect(input).toHaveValue("")
  })

  test("removes validation errors on backspace delete", async () => {
    const { emitted } = renderComponent({
      modelValue: "2022-05-13",
    })
    const input = screen.getByRole("textbox")
    expect(input).toHaveValue("13.05.2022")
    await userEvent.type(input, "{backspace}")

    expect(emitted()["update:validationError"]).toBeTruthy()
  })

  test("does not allow invalid dates", async () => {
    const { emitted } = renderComponent()
    const input = screen.getByRole("textbox")
    await userEvent.type(input, "29.02.2001")
    await nextTick()

    expect(input).toHaveValue("29.02.2001")

    expect(emitted()["update:modelValue"]).not.toBeTruthy()

    expect(emitted()["update:validationError"]).toBeTruthy()

    const array = emitted()["update:validationError"] as ValidationError[][]

    expect(
      array.filter((element) => element[0] !== undefined)[0][0].message,
    ).toBe("Kein valides Datum")
  })

  test("does not allow letters", async () => {
    renderComponent()
    const input = screen.getByRole("textbox")

    await userEvent.type(input, "AB.CD.EFGH")
    await nextTick()

    expect(input).toHaveValue("")
  })

  test("does not allow incomplete dates", async () => {
    const { emitted } = renderComponent()
    const input = screen.getByRole("textbox")

    await userEvent.type(input, "03")
    await userEvent.type(input, "{tab}")
    await nextTick()

    expect(emitted()["update:modelValue"]).not.toBeTruthy()
    expect(emitted()["update:validationError"]).toEqual([
      [
        {
          message: "Unvollständiges Datum",
          instance: "identifier",
        },
      ],
    ])
  })

  test("sets the input to readonly", () => {
    renderComponent({ isReadOnly: true })
    expect(screen.getByRole("textbox")).toHaveAttribute("readonly")
  })

  test("sets the input to editable", () => {
    renderComponent({ isReadOnly: false })
    expect(screen.getByRole("textbox")).not.toHaveAttribute("readonly")
  })

  test("shows error message block for external validation errors", async () => {
    const validationError = {
      message: "Externes Fehler",
      instance: "identifier",
    }
    renderComponent({ validationError })

    const errorBlock = screen.getByText("Externes Fehler")
    expect(errorBlock).toBeInTheDocument()
  })

  test("applies has-error class for external validation errors", async () => {
    const validationError = {
      message: "Externes Fehler",
      instance: "identifier",
    }
    renderComponent({ validationError })

    const input = screen.getByRole("textbox")
    expect(input).toHaveClass("has-error")
  })

  test("renders the small variant by default", () => {
    renderComponent()
    const input = screen.getByRole("textbox")
    expect(input).not.toHaveClass("ds-input-medium")
    expect(input).toHaveClass("ds-input-small")
  })

  test("renders the regular variant", () => {
    renderComponent({ size: "regular" })
    const input = screen.getByRole("textbox")
    expect(input).not.toHaveClass("ds-input-medium")
    expect(input).not.toHaveClass("ds-input-small")
  })

  test("renders the medium variant", () => {
    renderComponent({ size: "medium" })
    const input = screen.getByRole("textbox")
    expect(input).toHaveClass("ds-input-medium")
    expect(input).not.toHaveClass("ds-input-small")
  })

  test("renders the small variant", () => {
    renderComponent({ size: "small" })
    const input = screen.getByRole("textbox")
    expect(input).not.toHaveClass("ds-input-medium")
    expect(input).toHaveClass("ds-input-small")
  })
})
