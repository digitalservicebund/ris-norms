import { render, screen } from "@testing-library/vue"
import { userEvent } from "@testing-library/user-event"
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
  it("should show logo text and 'Verkündungen'/'Datenbank' links", () => {
    const router = createRouter({
      history: createWebHashHistory(),
      routes: [
        { name: "Home", path: "/", component: defineComponent({}) },
        {
          name: "Verkuendungen",
          path: "/verkuendungen",
          component: defineComponent({}),
        },
        {
          name: "Datenbank",
          path: "/datenbank",
          component: defineComponent({}),
        },
      ],
    })

    render(RisNavbar, { global: { plugins: [router] } })
    expect(screen.getByText("Rechtsinformationen")).toBeInTheDocument()
    expect(screen.getByText("des Bundes")).toBeInTheDocument()
    expect(
      screen.getByRole("link", { name: "Verkündungen" }),
    ).toBeInTheDocument()
    expect(screen.getByRole("link", { name: "Datenbank" })).toBeInTheDocument()
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
        {
          name: "Datenbank",
          path: "/datenbank",
          component: defineComponent({}),
        },
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
        {
          name: "Datenbank",
          path: "/datenbank",
          component: defineComponent({}),
        },
      ],
    })

    await router.push({ name: "Verkuendungen" })
    render(RisNavbar, { global: { plugins: [router] } })
    const verkuendungenTab = screen.getByRole("link", { name: "Verkündungen" })
    const datenbankTab = screen.getByRole("link", { name: "Datenbank" })
    expect(verkuendungenTab).toHaveAttribute("aria-current", "page")
    expect(datenbankTab).not.toHaveAttribute("aria-current")

    await router.push({ name: "Datenbank" })
    render(RisNavbar, { global: { plugins: [router] } })
    expect(datenbankTab).toHaveAttribute("aria-current", "page")
    expect(verkuendungenTab).not.toHaveAttribute("aria-current")
  })

  it("should navigate to the correct route when a tab is clicked", async () => {
    const user = userEvent.setup()
    const router = createRouter({
      history: createWebHashHistory(),
      routes: [
        { name: "Home", path: "/", component: defineComponent({}) },
        {
          name: "Verkuendungen",
          path: "/verkuendungen",
          component: defineComponent({}),
        },
        {
          name: "Datenbank",
          path: "/datenbank",
          component: defineComponent({}),
        },
      ],
    })

    render(RisNavbar, { global: { plugins: [router] } })
    await user.click(screen.getByRole("link", { name: "Datenbank" }))
    await router.isReady()
    await nextTick()
    expect(router.currentRoute.value.name).toBe("Datenbank")
  })
})
