import { describe, expect, test } from "vitest"
import RisCallout from "./RisCallout.vue"
import { render, screen } from "@testing-library/vue"
import { defineComponent } from "vue"

describe("RisCallout", () => {
  test("renders", () => {
    render(RisCallout, { props: { title: "Foo" } })
    expect(screen.getByText("Foo")).toBeInTheDocument()
  })

  test("shows as neutral variant", () => {
    render(RisCallout, { props: { title: "Foo", variant: "neutral" } })
    expect(screen.getByTestId("callout-wrapper")).toHaveAttribute(
      "data-variant",
      "neutral",
    )
  })

  test("shows as error variant", () => {
    render(RisCallout, { props: { title: "Foo", variant: "error" } })
    expect(screen.getByTestId("callout-wrapper")).toHaveAttribute(
      "data-variant",
      "error",
    )
  })

  test("shows as warning variant", () => {
    render(RisCallout, { props: { title: "Foo", variant: "warning" } })
    expect(screen.getByTestId("callout-wrapper")).toHaveAttribute(
      "data-variant",
      "warning",
    )
  })

  test("shows as success variant", () => {
    render(RisCallout, { props: { title: "Foo", variant: "success" } })
    expect(screen.getByTestId("callout-wrapper")).toHaveAttribute(
      "data-variant",
      "success",
    )
  })

  test("shows as neutral variant by default", () => {
    render(RisCallout, { props: { title: "Foo" } })
    expect(screen.getByTestId("callout-wrapper")).toHaveAttribute(
      "data-variant",
      "neutral",
    )
  })

  test("displays a message", () => {
    const component = defineComponent({
      components: { RisCallout },
      template: `<RisCallout title="Foo">Bar</RisCallout>`,
    })

    render(component)

    expect(screen.getByText("Bar")).toBeInTheDocument()
  })

  test("displays a custom icon", () => {
    const component = defineComponent({
      components: { RisCallout },
      template: `
        <RisCallout title="Foo">
          <template #icon>🐸</template>
        </RisCallout>
      `,
    })

    render(component)

    expect(screen.getByText("🐸")).toBeInTheDocument()
  })
})
