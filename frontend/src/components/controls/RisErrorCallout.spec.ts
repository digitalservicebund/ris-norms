import { render, screen } from "@testing-library/vue"
import { describe, expect, it, vi } from "vitest"
import { defineComponent, ref } from "vue"
import RisErrorCallout from "./RisErrorCallout.vue"
import { useSentryTraceId } from "@/composables/useSentryTraceId"
import type { ErrorResponse } from "@/types/errorResponse"

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

describe("risErrorCallout", () => {
  vi.mock("@/composables/useSentryTraceId", () => ({
    useSentryTraceId: () => ref("000000000000000000000000"),
  }))

  useSentryTraceId()

  it("renders", () => {
    render(RisErrorCallout, { props: { error: { type: "/errors/foo" } } })
    expect(screen.getByText("Error of type /errors/foo")).toBeInTheDocument()
  })

  it("shows as error variant", () => {
    render(RisErrorCallout, { props: { error: { type: "/errors/foo" } } })
    expect(screen.getByTestId("error-callout")).toHaveAttribute(
      "data-variant",
      "error",
    )
  })

  it("displays a message", () => {
    const component = defineComponent({
      components: { RisErrorCallout },
      template: `
        <RisErrorCallout :error="{ type: '/errors/foo' }">Bar</RisErrorCallout>`,
    })

    render(component)

    expect(screen.getByText("Bar")).toBeInTheDocument()
  })

  it("displays a suggestion", () => {
    const component = defineComponent({
      components: { RisErrorCallout },
      template: `
        <RisErrorCallout :error="{ type: '/errors/bar' }">Bar</RisErrorCallout>`,
    })

    render(component)

    expect(screen.getByText("Try again")).toBeInTheDocument()
  })

  it("displays sentry trace id", () => {
    render(RisErrorCallout, { props: { error: { type: "/errors/foo" } } })

    expect(screen.getByText("000000000000000000000000")).toBeInTheDocument()
  })
})
