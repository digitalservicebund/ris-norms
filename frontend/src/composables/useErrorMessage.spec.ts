import type { ErrorResponse } from "@/types/errorResponse"
import { describe, expect, it, vi } from "vitest"
import { useErrorMessage } from "./useErrorMessage"
import { ref } from "vue"

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

describe("useErrorMessage", () => {
  it("maps a known error type to a message", () => {
    const source: ErrorResponse = { type: "/errors/foo" }
    const result = useErrorMessage(source)
    expect(result.value).toEqual({ title: "Error of type /errors/foo" })
  })

  it("maps an unknown error type to the fallback message", () => {
    const source: ErrorResponse = { type: "/errors/non-existent-error" }
    const result = useErrorMessage(source)
    expect(result.value).toEqual({ title: "The fallback message" })
  })

  it("maps errors of a different format to the fallback message", () => {
    const source = { foo: "bar" }
    const result = useErrorMessage(source)
    expect(result.value).toEqual({ title: "The fallback message" })
  })

  it("returns undefined if there is no error to map", () => {
    const source = undefined
    const result = useErrorMessage(source)
    expect(result.value).toBeUndefined()
  })

  it("updates when the inputs change", () => {
    const source = ref<ErrorResponse>()

    const result = useErrorMessage(source)
    expect(result.value).toBeUndefined()

    source.value = { type: "/errors/foo" }
    expect(result.value).toEqual({ title: "Error of type /errors/foo" })
  })
})
