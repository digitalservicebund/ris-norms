import { userEvent } from "@testing-library/user-event"
import { render, screen, within } from "@testing-library/vue"
import { afterAll, beforeAll, describe, expect, it, vi } from "vitest"
import { defineComponent, nextTick, onUnmounted, ref } from "vue"
import type { Router } from "vue-router"
import { createRouter, createWebHashHistory } from "vue-router"
import RisHeader, { useHeaderContext } from "./RisHeader.vue"

describe("risHeader", () => {
  let global = {}
  let router: Router

  beforeAll(async () => {
    const component = defineComponent({ template: "<div></div>" })

    router = createRouter({
      history: createWebHashHistory(),
      routes: [
        { path: "/", name: "Home", component },
        { path: "/foo", name: "Foo", component },
        { path: "/bar", name: "Bar", component },
      ],
    })

    await router.push({ name: "Home" })
    await router.isReady()

    global = { plugins: [router] }
  })

  afterAll(() => {
    vi.resetAllMocks()
  })

  it("renders", () => {
    // Given
    render(RisHeader, { global, props: { breadcrumbs: [] } })

    // Then
    expect(screen.getByRole("banner")).toBeInTheDocument()
  })

  describe("back button", () => {
    it("renders the history back button", () => {
      // Given
      render(RisHeader, {
        global,
        props: { backDestination: "history-back", breadcrumbs: [] },
      })

      // Then
      expect(screen.getByRole("button", { name: "Zurück" })).toBeInTheDocument()
    })

    it("goes back on clicking the history back button", async () => {
      // Given
      render(RisHeader, {
        global,
        props: { backDestination: "history-back", breadcrumbs: [] },
      })
      const user = userEvent.setup()
      const routerBack = vi.spyOn(router, "back")

      // When
      await user.click(screen.getByRole("button", { name: "Zurück" }))

      // Then
      expect(routerBack).toHaveBeenCalled()
    })

    it("renders a link when a custom back destination is provided", () => {
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

    it("renders the link to a previous breadcrumb", async () => {
      // Given
      render(RisHeader, {
        global,
        props: {
          backDestination: "breadcrumb-back",
          breadcrumbs: [
            { key: "1", title: "Home", to: { name: "Home" } },
            { key: "2", title: "Foo", to: { name: "Foo" } },
            { key: "3", title: "Bar" },
          ],
        },
      })
      const link = screen.getByRole("link", { name: "Zurück" })

      // Then
      expect(link).toBeInTheDocument()
      expect(link).toHaveAttribute("href", "#/foo")
    })
  })
  describe("breadcrumbs", () => {
    it("renders breadcrumbs", () => {
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

    it("renders a text-only breadcrumb", () => {
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

    it("renders a link breadcrumb", () => {
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

    it("renders a breadcrumb with a dynamic title", async () => {
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

    it("updates breadcrumbs when the prop value changes", async () => {
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

    it("allows adding breadcrumbs from child components", async () => {
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

      render(dummyComponent, { global })

      // Then
      await vi.waitFor(() => {
        const breadcrumb = screen.getByTestId("text-only-breadcrumb")
        expect(breadcrumb).toHaveTextContent("From child")
      })
    })

    it("cleans up breadcrumbs from children when they're unmounted", async () => {
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

      render(dummyComponent, { global })

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

    it("shows dynamic titles on breadcrumbs from children", async () => {
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

      render(dummyComponent, { global })

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

    it("renders parent and child breadcrumbs", async () => {
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

      render(dummyComponent, { global })

      // Then
      await vi.waitFor(() => {
        const breadcrumbs = screen.getAllByTestId("text-only-breadcrumb")
        expect(breadcrumbs).toHaveLength(2)
        expect(breadcrumbs[0]).toHaveTextContent("From parent")
        expect(breadcrumbs[1]).toHaveTextContent("From child")
      })
    })

    it("renders breadcrumbs from nested children", async () => {
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

      render(dummyComponent, { global })

      // Then
      await vi.waitFor(() => {
        const breadcrumbs = screen.getAllByTestId("text-only-breadcrumb")
        expect(breadcrumbs).toHaveLength(3)
        expect(breadcrumbs[0]).toHaveTextContent("From parent")
        expect(breadcrumbs[1]).toHaveTextContent("From outer child")
        expect(breadcrumbs[2]).toHaveTextContent("From inner child")
      })
    })

    it("renders link as plain text if the link points to the current route", async () => {
      // Given
      render(RisHeader, {
        global,
        props: {
          breadcrumbs: [
            { key: "0", title: "Home", to: { name: "Home" } },
            { key: "1", title: "Foo", to: { name: "Foo" } },
          ],
        },
      })

      // When
      await router.push({ name: "Foo" })

      // Then
      expect(screen.getByTestId("current-route-breadcrumb")).toBeTruthy()
    })
  })

  describe("action slot", () => {
    it("renders slot content in the actions section", () => {
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
      render(component, { global })

      // Then
      expect(
        screen.getByRole("button", { name: "Click me" }),
      ).toBeInTheDocument()
    })

    it("renders teleported content in the actions section", async () => {
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
      render(component, { global })

      // Then
      await vi.waitFor(() => {
        const button = screen.getByRole("button", { name: "Click me" })
        expect(button).toBeInTheDocument()
      })
    })
  })
})
