import { ErrorResponse } from "@/types/errorResponse"
import { describe, expect, test, vi } from "vitest"
import { useErrorToast } from "./errorToast"
import { afterEach } from "node:test"

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
  afterEach(() => {
    vi.resetAllMocks()
  })

  test("shows a toast with a known error message", () => {
    const source: ErrorResponse = { type: "/errors/foo" }
    const { addErrorToast } = useErrorToast()
    addErrorToast(source)

    expect(add).toHaveBeenCalledWith(
      expect.objectContaining({
        summary: "Fehler beim Speichern: Error of type /errors/foo",
      }),
    )
  })

  test("shows a toast with a fallback message for an unkown error type", () => {
    const source: ErrorResponse = { type: "/errors/non-existent-error" }
    const { addErrorToast } = useErrorToast()
    addErrorToast(source)

    expect(add).toHaveBeenCalledWith(
      expect.objectContaining({
        summary: "Fehler beim Speichern: The fallback message",
      }),
    )
  })

  test("shows a toast with a fallback message for errors of a different format", () => {
    const source = { foo: "bar" }
    const { addErrorToast } = useErrorToast()
    addErrorToast(source)

    expect(add).toHaveBeenCalledWith(
      expect.objectContaining({
        summary: "Fehler beim Speichern: The fallback message",
      }),
    )
  })

  test("shows a toast with a fallback message if the error is undefined", () => {
    const source = undefined
    const { addErrorToast } = useErrorToast()
    addErrorToast(source)

    expect(add).toHaveBeenCalledWith(
      expect.objectContaining({
        summary: "Fehler beim Speichern: The fallback message",
      }),
    )
  })

  test("includes the trace ID if provided", () => {
    const source: ErrorResponse = { type: "/errors/foo" }
    const { addErrorToast } = useErrorToast()
    addErrorToast(source, "4711")

    expect(add).toHaveBeenCalledWith(
      expect.objectContaining({
        detail: expect.objectContaining({ traceId: "4711" }),
      }),
    )
  })

  test("includes the message details if applicable", () => {
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
})