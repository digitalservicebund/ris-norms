import { render, screen } from "@testing-library/vue"
import { describe, expect, test, vi } from "vitest"
import { defineComponent, ref } from "vue"
import RisErrorCallout from "./RisErrorCallout.vue"
import { useSentryTraceId } from "@/composables/useSentryTraceId"
import { ErrorResponse } from "@/types/errorResponse"

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

describe("RisErrorCallout", () => {
  vi.mock("@/composables/useSentryTraceId", () => ({
    useSentryTraceId: () => ref("000000000000000000000000"),
  }))

  useSentryTraceId()

  test("renders", () => {
    render(RisErrorCallout, { props: { error: { type: "/errors/foo" } } })
    expect(screen.getByText("Error of type /errors/foo")).toBeInTheDocument()
  })

  test("shows as error variant", () => {
    render(RisErrorCallout, { props: { error: { type: "/errors/foo" } } })
    expect(screen.getByTestId("error-callout")).toHaveAttribute(
      "data-variant",
      "error",
    )
  })

  test("displays a message", () => {
    const component = defineComponent({
      components: { RisErrorCallout },
      template: `
        <RisErrorCallout :error="{ type: '/errors/foo' }">Bar</RisErrorCallout>`,
    })

    render(component)

    expect(screen.getByText("Bar")).toBeInTheDocument()
  })

  test("displays a suggestion", () => {
    const component = defineComponent({
      components: { RisErrorCallout },
      template: `
        <RisErrorCallout :error="{ type: '/errors/bar' }">Bar</RisErrorCallout>`,
    })

    render(component)

    expect(screen.getByText("Try again")).toBeInTheDocument()
  })

  test("displays sentry trace id", () => {
    render(RisErrorCallout, { props: { error: { type: "/errors/foo" } } })

    expect(screen.getByText("000000000000000000000000")).toBeInTheDocument()
  })
})
