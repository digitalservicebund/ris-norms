import type { ErrorResponse } from "@/types/errorResponse"
import { describe, expect, it, vi } from "vitest"
import {
  getFallbackError,
  isErrorResponse,
  mapErrorResponse,
} from "./errorResponseMapper"

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

describe("isErrorResponse", () => {
  it("returns true if the candidate's type exists in the mapping", () => {
    const candidate = { type: "/errors/foo" }
    const result = isErrorResponse(candidate)
    expect(result).toBe(true)
  })

  it("returns false if the candidate's type does not exist in the mapping", () => {
    const candidate = { type: "/errors/non-existing-type" }
    const result = isErrorResponse(candidate)
    expect(result).toBe(false)
  })

  it("returns false if the candidate has no type", () => {
    const candidate = { foo: "bar" }
    const result = isErrorResponse(candidate)
    expect(result).toBe(false)
  })

  it("returns false if the candidate is not an object", () => {
    const candidate = "example"
    const result = isErrorResponse(candidate)
    expect(result).toBe(false)
  })
})

describe("getFallbackError", () => {
  it("returns a fallback error", () => {
    expect(getFallbackError()).toEqual({ type: "__fallback__" })
  })
})

describe("mapErrorResponse", () => {
  it("returns a mapped response from a string", () => {
    const e: ErrorResponse = { type: "/errors/foo" }
    const result = mapErrorResponse(e)
    expect(result).toEqual({ title: "Error of type /errors/foo" })
  })

  it("returns a mapped response from an object", () => {
    const e: ErrorResponse<{ example: string }> = {
      type: "/errors/bar",
      example: "Example",
    }

    const result = mapErrorResponse(e)

    expect(result).toEqual({
      title: "Bar",
      message: "Example: Example",
      suggestion: "Try again",
    })
  })

  it("returns the fallback response if the type has no mapping", () => {
    const e: ErrorResponse = { type: "/errors/non-existing-type" }
    const result = mapErrorResponse(e)
    expect(result).toEqual({ title: "The fallback message" })
  })
})
