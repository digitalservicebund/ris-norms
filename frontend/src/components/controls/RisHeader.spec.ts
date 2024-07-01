import { userEvent } from "@testing-library/user-event"
import { render, screen, within } from "@testing-library/vue"
import { GlobalMountOptions } from "@vue/test-utils/dist/types"
import { afterAll, beforeAll, describe, expect, test, vi } from "vitest"
import { defineComponent, nextTick, onUnmounted, ref } from "vue"
import { createRouter, createWebHashHistory } from "vue-router"
import RisHeader, { useHeaderContext } from "./RisHeader.vue"

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
        currentRoute: ref({ name: "Foo", path: "/foo" }),
        resolve: vi.fn().mockImplementation((route) => route.path),
      }),
    }
  })

  beforeAll(() => {
    const component = defineComponent({ template: "<div></div>" })

    const dummyRouter = createRouter({
      history: createWebHashHistory(),
      routes: [
        { path: "/", name: "Home", component },
        { path: "/foo", name: "Foo", component },
        { path: "/bar", name: "Bar", component },
      ],
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

    test("renders the link to a previous breadcrumb", () => {
      // Given
      render(RisHeader, {
        global,
        props: {
          backDestination: "breadcrumb-back",
          breadcrumbs: [
            { key: "1", title: "Home", to: { path: "/" } },
            { key: "2", title: "Foo", to: { path: "/foo" } },
            { key: "3", title: "Bar" },
          ],
        },
      })
      const link = screen.getByRole("link", { name: "Zurück" })

      // Then
      expect(link).toBeInTheDocument()
      expect(link).toHaveAttribute("href", "#/")
    })

    test("renders a separator between back button and breadcrumbs", () => {
      // Given
      render(RisHeader, {
        global,
        props: {
          breadcrumbs: [{ title: "Bar", key: "0", to: { path: "/bar" } }],
        },
      })

      // Then
      expect(screen.getByTestId("back-button-separator")).toBeInTheDocument()
    })

    test("renders no separator if no back button exists", () => {
      // Given
      render(RisHeader, {
        global,
        props: {
          breadcrumbs: [{ title: "Foo", key: "0", to: { path: "/foo" } }],
        },
      })

      // Then
      expect(screen.queryByTestId("back-button-separator")).toBeFalsy()
    })

    test("renders no separator if no breadcrumbs exists", () => {
      // Given
      render(RisHeader, {
        global,
        props: {
          backDestination: "history-back",
          breadcrumbs: [],
        },
      })

      // Then
      expect(screen.queryByTestId("back-button-separator")).toBeFalsy()
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

    test("renders a breadcrumb with a dynamic title", async () => {
      // Given
      const title = ref("Foo")
      render(RisHeader, {
        global,
        props: {
          breadcrumbs: [{ key: "0", title, to: "/" }],
        },
      })
      const link = within(screen.getByRole("navigation")).getByRole("link")
      expect(link).toHaveTextContent("Foo")

      // When
      title.value = "Bar"
      await nextTick()

      // Then
      expect(link).toHaveTextContent("Bar")
    })

    test("updates breadcrumbs when the prop value changes", async () => {
      // Given
      const { rerender } = render(RisHeader, {
        global,
        props: {
          breadcrumbs: [
            { key: "0", title: "Foo", to: "/" },
            { key: "1", title: "Bar", to: "/" },
          ],
        },
      })

      let links = within(screen.getByRole("navigation")).getAllByRole("link")
      expect(links).toHaveLength(2)
      expect(links[0]).toHaveTextContent("Foo")
      expect(links[1]).toHaveTextContent("Bar")

      // When
      await rerender({ breadcrumbs: [{ key: "2", title: "Baz", to: "/" }] })

      // Then
      links = within(screen.getByRole("navigation")).getAllByRole("link")
      expect(links).toHaveLength(1)
      expect(links[0]).toHaveTextContent("Baz")
    })

    test("allows adding breadcrumbs from child components", async () => {
      // Given
      const dummyChild = defineComponent({
        setup() {
          const { pushBreadcrumb } = useHeaderContext()
          pushBreadcrumb({ title: "From child" })
        },
        template: "<span />",
      })

      const dummyComponent = defineComponent({
        components: { dummyChild, RisHeader },
        template: `
          <RisHeader>
            <dummyChild />
          </RisHeader>
        `,
      })

      render(dummyComponent)

      // Then
      await vi.waitFor(() => {
        const breadcrumb = screen.getByTestId("text-only-breadcrumb")
        expect(breadcrumb).toHaveTextContent("From child")
      })
    })

    test("cleans up breadcrumbs from children when they're unmounted", async () => {
      // Given
      const user = userEvent.setup()

      const dummyChild = defineComponent({
        setup() {
          const { pushBreadcrumb } = useHeaderContext()
          const cleanup = pushBreadcrumb({ title: "From child" })
          onUnmounted(() => cleanup())
        },
        template: "<span />",
      })

      const dummyComponent = defineComponent({
        components: { dummyChild, RisHeader },
        setup() {
          const showChild = ref(true)
          return { showChild }
        },
        template: `
          <RisHeader>
            <dummyChild v-if="showChild" />
            <button @click="showChild = false">Hide child</button>
          </RisHeader>
        `,
      })

      render(dummyComponent)

      await vi.waitFor(() => {
        const breadcrumb = screen.getByTestId("text-only-breadcrumb")
        expect(breadcrumb).toHaveTextContent("From child")
      })

      // When
      await user.click(screen.getByRole("button", { name: "Hide child" }))
      await nextTick()

      // Then
      await vi.waitFor(() => {
        const breadcrumb = screen.queryByTestId("text-only-breadcrumb")
        expect(breadcrumb).not.toBeInTheDocument()
      })
    })

    test("shows dynamic titles on breadcrumbs from children", async () => {
      // Given
      const user = userEvent.setup()

      const dummyChild = defineComponent({
        setup() {
          const title = ref("From child 1")

          function setTitle() {
            title.value = "From child 2"
          }

          const { pushBreadcrumb } = useHeaderContext()
          pushBreadcrumb({ title })

          return { setTitle }
        },
        template: `
          <button @click="setTitle()">Change title</button>
        `,
      })

      const dummyComponent = defineComponent({
        components: { dummyChild, RisHeader },
        template: `
          <RisHeader>
            <dummyChild />
          </RisHeader>
        `,
      })

      render(dummyComponent)

      await vi.waitFor(() => {
        const breadcrumb = screen.getByTestId("text-only-breadcrumb")
        expect(breadcrumb).toHaveTextContent("From child 1")
      })

      // When
      await user.click(screen.getByRole("button", { name: "Change title" }))
      await nextTick()

      // Then
      await vi.waitFor(() => {
        const breadcrumb = screen.getByTestId("text-only-breadcrumb")
        expect(breadcrumb).toHaveTextContent("From child 2")
      })
    })

    test("renders parent and child breadcrumbs", async () => {
      // Given
      const dummyChild = defineComponent({
        setup() {
          const { pushBreadcrumb } = useHeaderContext()
          pushBreadcrumb({ title: "From child" })
        },
        template: "<span />",
      })

      const dummyComponent = defineComponent({
        components: { dummyChild, RisHeader },
        template: `
          <RisHeader :breadcrumbs="[{ title: 'From parent' }]">
            <dummyChild />
          </RisHeader>
        `,
      })

      render(dummyComponent)

      // Then
      await vi.waitFor(() => {
        const breadcrumbs = screen.getAllByTestId("text-only-breadcrumb")
        expect(breadcrumbs).toHaveLength(2)
        expect(breadcrumbs[0]).toHaveTextContent("From parent")
        expect(breadcrumbs[1]).toHaveTextContent("From child")
      })
    })

    test("renders breadcrumbs from nested children", async () => {
      // Given
      const dummyInnerChild = defineComponent({
        setup() {
          const { pushBreadcrumb } = useHeaderContext()
          pushBreadcrumb({ title: "From inner child" })
        },
        template: "<span />",
      })

      const dummyOuterChild = defineComponent({
        components: { dummyInnerChild },
        setup() {
          const { pushBreadcrumb } = useHeaderContext()
          pushBreadcrumb({ title: "From outer child" })
        },
        template: "<dummyInnerChild />",
      })

      const dummyComponent = defineComponent({
        components: { dummyOuterChild, RisHeader },
        template: `
          <RisHeader :breadcrumbs="[{ title: 'From parent' }]">
            <dummyOuterChild />
          </RisHeader>
        `,
      })

      render(dummyComponent)

      // Then
      await vi.waitFor(() => {
        const breadcrumbs = screen.getAllByTestId("text-only-breadcrumb")
        expect(breadcrumbs).toHaveLength(3)
        expect(breadcrumbs[0]).toHaveTextContent("From parent")
        expect(breadcrumbs[1]).toHaveTextContent("From outer child")
        expect(breadcrumbs[2]).toHaveTextContent("From inner child")
      })
    })
  })

  describe("action slot", () => {
    test("renders slot content in the actions section", () => {
      // Given
      const component = defineComponent({
        components: { RisHeader },
        template: `
          <RisHeader>
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
      const child = defineComponent({
        setup() {
          const { actionTeleportTarget } = useHeaderContext()
          return { actionTeleportTarget }
        },
        template: `
          <Teleport v-if="actionTeleportTarget" :to="actionTeleportTarget">
            <button>Click me</button>
          </Teleport>
        `,
      })

      const component = defineComponent({
        components: { RisHeader, child },
        template: `<RisHeader><child /></RisHeader>`,
      })
      render(component)

      // Then
      vi.waitFor(() => {
        const button = screen.getByRole("button", { name: "Click me" })
        expect(button).toBeInTheDocument()
      })
    })
  })
})
