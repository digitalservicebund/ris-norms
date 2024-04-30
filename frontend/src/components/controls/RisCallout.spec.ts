import { userEvent } from "@testing-library/user-event"
import { render, screen } from "@testing-library/vue"
import { describe, expect, test, vi } from "vitest"
import { defineComponent } from "vue"
import RisCallout from "./RisCallout.vue"

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

  test("shows a dismiss button", () => {
    render(RisCallout, { props: { title: "Foo", allowDismiss: true } })
    expect(
      screen.getByRole("button", { name: "Hinweis schließen" }),
    ).toBeInTheDocument()
  })

  test("doesn't show the dismiss button", () => {
    render(RisCallout, { props: { title: "Foo", allowDismiss: false } })
    expect(
      screen.queryByRole("button", { name: "Hinweis schließen" }),
    ).not.toBeInTheDocument()
  })

  test("doesn't show the dismiss button by default", () => {
    render(RisCallout, { props: { title: "Foo", allowDismiss: false } })
    expect(
      screen.queryByRole("button", { name: "Hinweis schließen" }),
    ).not.toBeInTheDocument()
  })

  test("is hidden on dismiss", async () => {
    const update = vi.fn()
    render(RisCallout, {
      props: { title: "Foo", allowDismiss: true, "onUpdate:visible": update },
    })

    const user = userEvent.setup()
    await user.click(screen.getByRole("button", { name: "Hinweis schließen" }))

    expect(update).toHaveBeenCalled()
  })

  test("is visible based on the model value", () => {
    render(RisCallout, { props: { title: "Foo", visible: true } })
    expect(screen.getByText("Foo")).toBeInTheDocument()
  })

  test("is hidden based on the model value", () => {
    render(RisCallout, { props: { title: "Foo", visible: false } })
    expect(screen.queryByText("Foo")).not.toBeInTheDocument()
  })

  test("is visible by default", () => {
    render(RisCallout, { props: { title: "Foo" } })
    expect(screen.getByText("Foo")).toBeInTheDocument()
  })
})
