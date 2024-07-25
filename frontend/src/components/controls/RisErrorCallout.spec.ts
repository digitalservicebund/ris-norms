import { render, screen } from "@testing-library/vue"
import { describe, expect, test, vi } from "vitest"
import { defineComponent, ref } from "vue"
import RisErrorCallout from "./RisErrorCallout.vue"
import { useSentryTraceId } from "@/composables/useSentryTraceId"

export default defineComponent({
  components: { RisErrorCallout },
})

describe("RisErrorCallout", () => {
  vi.mock("@/composables/useSentryTraceId", () => ({
    useSentryTraceId: () => ref("000000000000000000000000"),
  }))

  useSentryTraceId()

  test("renders", () => {
    render(RisErrorCallout, { props: { title: "Foo" } })
    expect(screen.getByText("Foo")).toBeInTheDocument()
  })

  test("shows as error variant", () => {
    render(RisErrorCallout, { props: { title: "Foo" } })
    expect(screen.getByTestId("callout-wrapper")).toHaveAttribute(
      "data-variant",
      "error",
    )
  })

  test("displays a message", () => {
    const component = defineComponent({
      components: { RisErrorCallout },
      template: `
        <RisErrorCallout title="Foo">Bar</RisErrorCallout>`,
    })

    render(component)

    expect(screen.getByText("Bar")).toBeInTheDocument()
  })

  test("displays sentry trace id", () => {
    render(RisErrorCallout, { props: { title: "Foo" } })

    expect(screen.getByText("000000000000000000000000")).toBeInTheDocument()
  })
})
