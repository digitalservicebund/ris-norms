import { userEvent } from "@testing-library/user-event"
import { render, screen, within } from "@testing-library/vue"
import { GlobalMountOptions } from "@vue/test-utils/dist/types"
import { afterAll, beforeAll, describe, expect, test, vi } from "vitest"
import { defineComponent } from "vue"
import { createRouter, createWebHashHistory } from "vue-router"
import RisHeader, { HEADER_ACTION_TARGET } from "./RisHeader.vue"

describe("RisHeader", () => {
  let global: GlobalMountOptions

  const { routerBackMock } = vi.hoisted(() => ({
    routerBackMock: vi.fn(),
  }))

  vi.mock("vue-router", async (importActual) => {
    const router = await importActual<typeof import("vue-router")>()
    return {
      ...router,
      useRouter: vi.fn().mockReturnValue({
        back: routerBackMock,
      }),
    }
  })

  beforeAll(() => {
    const component = defineComponent({ template: "<div></div>" })

    const dummyRouter = createRouter({
      history: createWebHashHistory(),
      routes: [{ path: "/", name: "Home", component }],
    })

    global = { plugins: [dummyRouter] }
  })

  afterAll(() => {
    vi.resetAllMocks()
  })

  test("renders", () => {
    // Given
    render(RisHeader, { global, props: { breadcrumbs: [] } })

    // Then
    expect(screen.getByRole("banner")).toBeInTheDocument()
  })

  describe("back button", () => {
    test("renders the history back button", () => {
      // Given
      render(RisHeader, {
        global,
        props: { backDestination: "history-back", breadcrumbs: [] },
      })

      // Then
      expect(screen.getByRole("button", { name: "Zurück" })).toBeInTheDocument()
    })

    test("goes back on clicking the history back button", async () => {
      // Given
      render(RisHeader, {
        global,
        props: { backDestination: "history-back", breadcrumbs: [] },
      })
      const user = userEvent.setup()

      // When
      await user.click(screen.getByRole("button", { name: "Zurück" }))

      // Then
      expect(routerBackMock).toHaveBeenCalled()
    })

    test("renders a link when a custom back destination is provided", () => {
      // Given
      render(RisHeader, {
        global,
        props: { backDestination: { name: "Home" }, breadcrumbs: [] },
      })
      const link = screen.getByRole("link", { name: "Zurück" })

      // Then
      expect(link).toBeInTheDocument()
      expect(link).toHaveAttribute("href", "#/")
    })
  })

  describe("breadcrumbs", () => {
    test("renders breadcrumbs", () => {
      // Given
      render(RisHeader, {
        global,
        props: {
          breadcrumbs: [
            { key: "0", title: "Foo", to: "/" },
            { key: "1", title: "Bar", to: "/" },
          ],
        },
      })
      const links = within(screen.getByRole("navigation")).getAllByRole("link")

      // Then
      expect(links[0]).toHaveTextContent("Foo")
      expect(links[1]).toHaveTextContent("Bar")
    })

    test("renders a text-only breadcrumb", () => {
      // Given
      render(RisHeader, {
        global,
        props: {
          breadcrumbs: [{ key: "0", title: "Foo" }],
        },
      })
      const navigation = screen.getByRole("navigation")

      // Then
      expect(navigation).toHaveTextContent("Foo")
      expect(within(navigation).queryByRole("link")).toBeFalsy()
    })

    test("renders a link breadcrumb", () => {
      // Given
      render(RisHeader, {
        global,
        props: {
          breadcrumbs: [{ key: "0", title: "Foo", to: "/" }],
        },
      })
      const link = within(screen.getByRole("navigation")).getByRole("link")

      // Then
      expect(link).toHaveTextContent("Foo")
    })
  })

  describe("action slot", () => {
    test("renders slot content in the actions section", () => {
      // Given
      const component = defineComponent({
        components: { RisHeader },
        template: `
        <RisHeader :breadcrumbs="[]">
          <template #action>
            <button>Click me</button>
          </template>
        </RisHeader>
      `,
      })
      render(component)

      // Then
      expect(
        screen.getByRole("button", { name: "Click me" }),
      ).toBeInTheDocument()
    })

    test("renders teleported content in the actions section", () => {
      // Given
      const component = defineComponent({
        components: { RisHeader },
        setup() {
          return { HEADER_ACTION_TARGET }
        },
        template: `
        <RisHeader :breadcrumbs="[]"></RisHeader>
        <Teleport :to="HEADER_ACTION_TARGET">
          <button>Click me</button>
        </Teleport>
      `,
      })
      render(component)

      // Then
      expect(
        screen.getByRole("button", { name: "Click me" }),
      ).toBeInTheDocument()
    })
  })
})
