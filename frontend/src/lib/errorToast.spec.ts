import type { ErrorResponse } from "@/types/errorResponse"
import { afterAll, describe, expect, it, vi } from "vitest"
import { useErrorToast } from "./errorToast"

const { add } = vi.hoisted(() => ({
  add: vi.fn(),
}))

vi.mock("primevue/usetoast", () => ({
  useToast: vi.fn().mockReturnValue({ add }),
}))

vi.mock("@/lib/errorMessages", () => ({
  errorMessages: {
    __fallback__: () => "The fallback message",

    "/errors/foo": (e: ErrorResponse) => `Error of type ${e.type}`,

    "/errors/bar": (e: ErrorResponse<{ example: string }>) => ({
      title: "Bar",
      message: `Example: ${e.example}`,
      suggestion: "Try again",
    }),
  },
}))

describe("errorToast", () => {
  afterAll(() => {
    vi.resetAllMocks()
  })

  it("shows a toast with a known error message", () => {
    const source: ErrorResponse = { type: "/errors/foo" }
    const { addErrorToast } = useErrorToast()
    addErrorToast(source)

    expect(add).toHaveBeenCalledWith(
      expect.objectContaining({
        summary: "Fehler: Error of type /errors/foo",
      }),
    )
  })

  it("shows a toast with a fallback message for an unkown error type", () => {
    const source: ErrorResponse = { type: "/errors/non-existent-error" }
    const { addErrorToast } = useErrorToast()
    addErrorToast(source)

    expect(add).toHaveBeenCalledWith(
      expect.objectContaining({
        summary: "Fehler: The fallback message",
      }),
    )
  })

  it("shows a toast with a fallback message for errors of a different format", () => {
    const source = { foo: "bar" }
    const { addErrorToast } = useErrorToast()
    addErrorToast(source)

    expect(add).toHaveBeenCalledWith(
      expect.objectContaining({
        summary: "Fehler: The fallback message",
      }),
    )
  })

  it("shows a toast with a fallback message if the error is undefined", () => {
    const source = undefined
    const { addErrorToast } = useErrorToast()
    addErrorToast(source)

    expect(add).toHaveBeenCalledWith(
      expect.objectContaining({
        summary: "Fehler: The fallback message",
      }),
    )
  })

  it("includes the trace ID if provided", () => {
    const source: ErrorResponse = { type: "/errors/foo" }
    const { addErrorToast } = useErrorToast()
    addErrorToast(source, { traceId: "4711" })

    expect(add).toHaveBeenCalledWith(
      expect.objectContaining({
        detail: expect.objectContaining({ traceId: "4711" }),
      }),
    )
  })

  it("includes the message details if applicable", () => {
    const source: ErrorResponse<{ example: string }> = {
      type: "/errors/bar",
      example: "example",
    }

    const { addErrorToast } = useErrorToast()
    addErrorToast(source)

    expect(add).toHaveBeenCalledWith(
      expect.objectContaining({
        detail: expect.objectContaining({
          title: "Bar",
          message: "Example: example",
          suggestion: "Try again",
        }),
      }),
    )
  })

  it("shows a custom message", () => {
    const source = undefined
    const { addErrorToast } = useErrorToast()
    addErrorToast(source, { message: (err) => `Custom message: ${err}` })

    expect(add).toHaveBeenCalledWith(
      expect.objectContaining({
        summary: "Custom message: The fallback message",
      }),
    )
  })
})
