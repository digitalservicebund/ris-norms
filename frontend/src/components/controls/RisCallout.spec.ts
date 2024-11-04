import { userEvent } from "@testing-library/user-event"
import { render, screen } from "@testing-library/vue"
import { describe, expect, it, vi } from "vitest"
import { defineComponent } from "vue"
import RisCallout from "./RisCallout.vue"

describe("risCallout", () => {
  it("renders", () => {
    render(RisCallout, { props: { title: "Foo" } })
    expect(screen.getByText("Foo")).toBeInTheDocument()
  })

  it("shows as neutral variant", () => {
    render(RisCallout, { props: { title: "Foo", variant: "neutral" } })
    expect(screen.getByTestId("callout-wrapper")).toHaveAttribute(
      "data-variant",
      "neutral",
    )
  })

  it("shows as error variant", () => {
    render(RisCallout, { props: { title: "Foo", variant: "error" } })
    expect(screen.getByTestId("callout-wrapper")).toHaveAttribute(
      "data-variant",
      "error",
    )
  })

  it("shows as warning variant", () => {
    render(RisCallout, { props: { title: "Foo", variant: "warning" } })
    expect(screen.getByTestId("callout-wrapper")).toHaveAttribute(
      "data-variant",
      "warning",
    )
  })

  it("shows as success variant", () => {
    render(RisCallout, { props: { title: "Foo", variant: "success" } })
    expect(screen.getByTestId("callout-wrapper")).toHaveAttribute(
      "data-variant",
      "success",
    )
  })

  it("shows as neutral variant by default", () => {
    render(RisCallout, { props: { title: "Foo" } })
    expect(screen.getByTestId("callout-wrapper")).toHaveAttribute(
      "data-variant",
      "neutral",
    )
  })

  it("displays a message", () => {
    const component = defineComponent({
      components: { RisCallout },
      template: `<RisCallout title="Foo">Bar</RisCallout>`,
    })

    render(component)

    expect(screen.getByText("Bar")).toBeInTheDocument()
  })

  it("displays a custom icon", () => {
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

  it("shows a dismiss button", () => {
    render(RisCallout, { props: { title: "Foo", allowDismiss: true } })
    expect(
      screen.getByRole("button", { name: "Hinweis schließen" }),
    ).toBeInTheDocument()
  })

  it("doesn't show the dismiss button", () => {
    render(RisCallout, { props: { title: "Foo", allowDismiss: false } })
    expect(
      screen.queryByRole("button", { name: "Hinweis schließen" }),
    ).not.toBeInTheDocument()
  })

  it("doesn't show the dismiss button by default", () => {
    render(RisCallout, { props: { title: "Foo", allowDismiss: false } })
    expect(
      screen.queryByRole("button", { name: "Hinweis schließen" }),
    ).not.toBeInTheDocument()
  })

  it("is hidden on dismiss", async () => {
    const update = vi.fn()
    render(RisCallout, {
      props: { title: "Foo", allowDismiss: true, "onUpdate:visible": update },
    })

    const user = userEvent.setup()
    await user.click(screen.getByRole("button", { name: "Hinweis schließen" }))

    expect(update).toHaveBeenCalled()
  })

  it("is visible based on the model value", () => {
    render(RisCallout, { props: { title: "Foo", visible: true } })
    expect(screen.getByText("Foo")).toBeVisible()
  })

  it("is hidden based on the model value", () => {
    render(RisCallout, { props: { title: "Foo", visible: false } })
    expect(screen.queryByRole("Foo")).not.toBeInTheDocument()
  })

  it("is visible by default", () => {
    render(RisCallout, { props: { title: "Foo" } })
    expect(screen.getByText("Foo")).toBeVisible()
  })
})
