import type { ErrorResponse } from "@/types/errorResponse"
import { afterAll, describe, expect, it, vi } from "vitest"
import { useToast } from "./useToast"

const { add, remove, removeGroup, removeAllGroups } = vi.hoisted(() => ({
  add: vi.fn(),
  remove: vi.fn(),
  removeGroup: vi.fn(),
  removeAllGroups: vi.fn(),
}))

vi.mock("primevue", () => ({
  useToast: vi
    .fn()
    .mockReturnValue({ add, remove, removeGroup, removeAllGroups }),
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

describe("useToast", () => {
  afterAll(() => {
    vi.resetAllMocks()
  })

  it("shows success toasts with a shorter lifetime", () => {
    const { add: addToast } = useToast()
    addToast({ summary: "Example", severity: "success" })

    expect(add).toHaveBeenCalledWith(
      expect.objectContaining({
        summary: "Example",
        life: 5_000,
      }),
    )
  })

  it("shows info toasts with a shorter lifetime", () => {
    const { add: addToast } = useToast()
    addToast({ summary: "Example", severity: "info" })

    expect(add).toHaveBeenCalledWith(
      expect.objectContaining({
        summary: "Example",
        life: 5_000,
      }),
    )
  })

  it("shows warning toasts with a longer lifetime", () => {
    const { add: addToast } = useToast()
    addToast({ summary: "Example", severity: "warn" })

    expect(add).toHaveBeenCalledWith(
      expect.objectContaining({
        summary: "Example",
        life: 10_000,
      }),
    )
  })

  it("shows sticky toasts", () => {
    const { add: addToast } = useToast()
    addToast({ summary: "Example", severity: "warn", life: null })

    expect(add).toHaveBeenCalledWith(
      expect.objectContaining({
        summary: "Example",
        life: undefined,
      }),
    )
  })

  it("shows toasts with a custom lifetime", () => {
    const { add: addToast } = useToast()
    addToast({ summary: "Example", severity: "warn", life: 123 })

    expect(add).toHaveBeenCalledWith(
      expect.objectContaining({
        summary: "Example",
        life: 123,
      }),
    )
  })

  it("removes a toast", () => {
    const { remove: removeToast } = useToast()
    removeToast({ summary: "Example" })

    expect(remove).toHaveBeenCalledWith(
      expect.objectContaining({
        summary: "Example",
      }),
    )
  })

  it("removes a group", () => {
    const { removeGroup: removeToastGroup } = useToast()
    removeToastGroup("example")

    expect(removeGroup).toHaveBeenCalledWith("example")
  })

  it("removes all groups", () => {
    const { removeAllGroups: removeAllToastGroups } = useToast()
    removeAllToastGroups()

    expect(removeAllGroups).toHaveBeenCalledWith()
  })

  describe("addError", () => {
    it("shows a toast with a known error message", () => {
      const source: ErrorResponse = { type: "/errors/foo" }
      const { addError } = useToast()
      addError(source)

      expect(add).toHaveBeenCalledWith(
        expect.objectContaining({
          summary: "Fehler: Error of type /errors/foo",
          life: undefined,
        }),
      )
    })

    it("shows a toast with a fallback message for an unkown error type", () => {
      const source: ErrorResponse = { type: "/errors/non-existent-error" }
      const { addError } = useToast()
      addError(source)

      expect(add).toHaveBeenCalledWith(
        expect.objectContaining({
          summary: "Fehler: The fallback message",
        }),
      )
    })

    it("shows a toast with a fallback message for errors of a different format", () => {
      const source = { foo: "bar" }
      const { addError } = useToast()
      addError(source)

      expect(add).toHaveBeenCalledWith(
        expect.objectContaining({
          summary: "Fehler: The fallback message",
        }),
      )
    })

    it("shows a toast with a fallback message if the error is undefined", () => {
      const source = undefined
      const { addError } = useToast()
      addError(source)

      expect(add).toHaveBeenCalledWith(
        expect.objectContaining({
          summary: "Fehler: The fallback message",
        }),
      )
    })

    it("includes the trace ID if provided", () => {
      const source: ErrorResponse = { type: "/errors/foo" }
      const { addError } = useToast()
      addError(source, { traceId: "4711" })

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

      const { addError } = useToast()
      addError(source)

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
      const { addError } = useToast()
      addError(source, { message: (err) => `Custom message: ${err}` })

      expect(add).toHaveBeenCalledWith(
        expect.objectContaining({
          summary: "Custom message: The fallback message",
        }),
      )
    })
  })
})
