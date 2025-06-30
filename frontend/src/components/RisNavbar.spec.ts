import { render, screen } from "@testing-library/vue"
import { describe, expect, it, vi } from "vitest"
import RisNavbar from "./RisNavbar.vue"
import { createRouter, createWebHashHistory } from "vue-router"
import { defineComponent, nextTick } from "vue"

vi.mock("@/lib/auth", () => ({
  useAuthentication: () => ({
    getUsername: () => "User Name",
    getLogoutLink: () => "http://example.com",
  }),
}))

describe("risNavbar", () => {
  it("should show logo text and 'Verkündungen'/'Bestand' links", () => {
    const router = createRouter({
      history: createWebHashHistory(),
      routes: [
        { name: "Home", path: "/", component: defineComponent({}) },
        {
          name: "Verkuendungen",
          path: "/verkuendungen",
          component: defineComponent({}),
        },
        { name: "Bestand", path: "/bestand", component: defineComponent({}) },
      ],
    })

    render(RisNavbar, { global: { plugins: [router] } })
    expect(screen.getByText("Rechtsinformationen")).toBeInTheDocument()
    expect(screen.getByText("des Bundes")).toBeInTheDocument()
    expect(
      screen.getByRole("link", { name: "Verkündungen" }),
    ).toBeInTheDocument()
    expect(screen.getByRole("link", { name: "Bestand" })).toBeInTheDocument()
  })

  it("should render the logout link", () => {
    const router = createRouter({
      history: createWebHashHistory(),
      routes: [
        { name: "Home", path: "/", component: defineComponent({}) },
        {
          name: "Verkuendungen",
          path: "/verkuendungen",
          component: defineComponent({}),
        },
        { name: "Bestand", path: "/bestand", component: defineComponent({}) },
      ],
    })

    render(RisNavbar, { global: { plugins: [router] } })
    const logoutLink = screen.getByRole("link", { name: "Abmelden" })
    expect(logoutLink).toBeInTheDocument()
    expect(logoutLink).toHaveAttribute("href", "http://example.com")
  })

  it("should highlight the correct tab based on route", async () => {
    const router = createRouter({
      history: createWebHashHistory(),
      routes: [
        { name: "Home", path: "/", component: defineComponent({}) },
        {
          name: "Verkuendungen",
          path: "/verkuendungen",
          component: defineComponent({}),
        },
        { name: "Bestand", path: "/bestand", component: defineComponent({}) },
      ],
    })

    await router.push({ name: "Verkuendungen" })
    render(RisNavbar, { global: { plugins: [router] } })
    const verkuendungenTab = screen.getByRole("link", { name: "Verkündungen" })
    const bestandTab = screen.getByRole("link", { name: "Bestand" })
    expect(verkuendungenTab).toHaveAttribute("aria-selected", "true")
    expect(bestandTab).toHaveAttribute("aria-selected", "false")

    await router.push({ name: "Bestand" })
    render(RisNavbar, { global: { plugins: [router] } })
    expect(bestandTab).toHaveAttribute("aria-selected", "true")
  })

  it("should navigate to the correct route when a tab is clicked", async () => {
    const router = createRouter({
      history: createWebHashHistory(),
      routes: [
        { name: "Home", path: "/", component: defineComponent({}) },
        {
          name: "Verkuendungen",
          path: "/verkuendungen",
          component: defineComponent({}),
        },
        { name: "Bestand", path: "/bestand", component: defineComponent({}) },
      ],
    })

    render(RisNavbar, { global: { plugins: [router] } })
    screen.getByRole("link", { name: "Bestand" }).click()
    await router.isReady()
    await nextTick()
    expect(router.currentRoute.value.name).toBe("Bestand")
  })
})
