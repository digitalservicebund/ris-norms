import { userEvent } from "@testing-library/user-event"
import { render, screen } from "@testing-library/vue"
import { describe, expect, test, vi } from "vitest"
import { defineComponent } from "vue"
import RisTooltip from "./RisTooltip.vue"

describe("RisTooltip", () => {
  test("renders", () => {
    render(RisTooltip, { props: { title: "Foo" } })
    expect(screen.getByRole("tooltip")).toBeInTheDocument()
  })

  test("renders with an embedded element", () => {
    const component = defineComponent({
      components: { RisTooltip },
      template: `
        <RisTooltip title="Foo">
          <button>Bar</button>
        </RisTooltip>
      `,
    })
    render(component)

    expect(screen.getByRole("button", { name: "Bar" })).toBeInTheDocument()
  })

  test("adds a description to the embedded element", () => {
    const component = defineComponent({
      components: { RisTooltip },
      template: `
        <RisTooltip title="Foo" v-slot="{ ariaDescribedby }">
          <button :aria-describedby>Bar</button>
        </RisTooltip>
      `,
    })
    render(component)

    expect(
      screen.getByRole("button", { name: "Bar" }),
    ).toHaveAccessibleDescription("Foo")
  })

  test("displays the title", () => {
    render(RisTooltip, { props: { title: "Foo" } })
    expect(screen.getByRole("tooltip", { name: "Foo" })).toBeInTheDocument()
  })

  test("shows as neutral variant", () => {
    render(RisTooltip, { props: { title: "Foo", variant: "neutral" } })
    expect(screen.getByTestId("callout-wrapper")).toHaveAttribute(
      "data-variant",
      "neutral",
    )
  })

  test("shows as error variant", () => {
    render(RisTooltip, { props: { title: "Foo", variant: "error" } })
    expect(screen.getByTestId("callout-wrapper")).toHaveAttribute(
      "data-variant",
      "error",
    )
  })

  test("shows as warning variant", () => {
    render(RisTooltip, { props: { title: "Foo", variant: "warning" } })
    expect(screen.getByTestId("callout-wrapper")).toHaveAttribute(
      "data-variant",
      "warning",
    )
  })

  test("shows as success variant", () => {
    render(RisTooltip, { props: { title: "Foo", variant: "success" } })
    expect(screen.getByTestId("callout-wrapper")).toHaveAttribute(
      "data-variant",
      "success",
    )
  })

  test("shows as neutral variant by default", () => {
    render(RisTooltip, { props: { title: "Foo" } })
    expect(screen.getByTestId("callout-wrapper")).toHaveAttribute(
      "data-variant",
      "neutral",
    )
  })

  test("aligns left", () => {
    render(RisTooltip, { props: { title: "Foo", alignment: "left" } })
    expect(screen.getByRole("tooltip")).toHaveAttribute(
      "data-alignment",
      "left",
    )
  })

  test("aligns right", () => {
    render(RisTooltip, { props: { title: "Foo", alignment: "right" } })
    expect(screen.getByRole("tooltip")).toHaveAttribute(
      "data-alignment",
      "right",
    )
  })

  test("aligns left by default", () => {
    render(RisTooltip, { props: { title: "Foo" } })
    expect(screen.getByRole("tooltip")).toHaveAttribute(
      "data-alignment",
      "left",
    )
  })

  test("attaches top", () => {
    render(RisTooltip, { props: { title: "Foo", attachment: "top" } })
    expect(screen.getByRole("tooltip")).toHaveAttribute(
      "data-attachment",
      "top",
    )
  })

  test("attaches bottom", () => {
    render(RisTooltip, { props: { title: "Foo", attachment: "bottom" } })
    expect(screen.getByRole("tooltip")).toHaveAttribute(
      "data-attachment",
      "bottom",
    )
  })

  test("attaches top by default", () => {
    render(RisTooltip, { props: { title: "Foo" } })
    expect(screen.getByRole("tooltip")).toHaveAttribute(
      "data-attachment",
      "top",
    )
  })

  test("shows a dimiss button", () => {
    render(RisTooltip, { props: { title: "Foo", allowDismiss: true } })
    expect(
      screen.getByRole("button", { name: "Hinweis schließen" }),
    ).toBeInTheDocument()
  })

  test("doesn't show the dismiss button", () => {
    render(RisTooltip, { props: { title: "Foo", allowDismiss: false } })
    expect(
      screen.queryByRole("button", { name: "Hinweis schließen" }),
    ).not.toBeInTheDocument()
  })

  test("doesn't show the dismiss button by default", () => {
    render(RisTooltip, { props: { title: "Foo", allowDismiss: false } })
    expect(
      screen.queryByRole("button", { name: "Hinweis schließen" }),
    ).not.toBeInTheDocument()
  })

  test("is hidden on dismiss", async () => {
    const update = vi.fn()
    render(RisTooltip, {
      props: { title: "Foo", allowDismiss: true, "onUpdate:visible": update },
    })

    const user = userEvent.setup()
    await user.click(screen.getByRole("button", { name: "Hinweis schließen" }))

    expect(update).toHaveBeenCalled()
  })

  test("is visible based on the model value", () => {
    render(RisTooltip, { props: { title: "Foo", visible: true } })
    expect(screen.getByRole("tooltip")).toBeVisible()
  })

  test("is hidden based on the model value", () => {
    render(RisTooltip, { props: { title: "Foo", visible: false } })
    expect(screen.queryByRole("tooltip")).not.toBeInTheDocument()
  })

  test("is visible by default", () => {
    render(RisTooltip, { props: { title: "Foo" } })
    expect(screen.getByRole("tooltip")).toBeVisible()
  })

  test("shows a message in the tooltip", () => {
    const component = defineComponent({
      components: { RisTooltip },
      template: `
        <RisTooltip title="Foo">
          <template #message>
            <p>Hello world</p>
          </template>
        </RisTooltip>
      `,
    })
    render(component)

    expect(screen.getByText("Hello world")).toBeInTheDocument()
  })
})
