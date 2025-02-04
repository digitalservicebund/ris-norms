import { render, screen } from "@testing-library/vue"
import { describe, expect, it, vi } from "vitest"
import RisNavbar from "./RisNavbar.vue"
import { createRouter, createWebHashHistory } from "vue-router"
import { defineComponent } from "vue"

vi.mock("@/lib/auth", () => ({
  useAuthentication: () => ({
    getUsername: () => "User Name",
    getLogoutLink: () => "http://example.com",
  }),
}))

describe("risNavbar", () => {
  it("should show 'Rechtsinformationen' and 'des Bundes'", () => {
    const router = createRouter({
      history: createWebHashHistory(),
      routes: [{ name: "Home", path: "/", component: defineComponent({}) }],
    })

    render(RisNavbar, { global: { plugins: [router] } })
    expect(screen.getByText("Rechtsinformationen")).toBeInTheDocument()
    expect(screen.getByText("des Bundes")).toBeInTheDocument()
  })

  it("should render the logout link", () => {
    const router = createRouter({
      history: createWebHashHistory(),
      routes: [{ name: "Home", path: "/", component: defineComponent({}) }],
    })

    render(RisNavbar, { global: { plugins: [router] } })
    const logoutLink = screen.getByRole("link", { name: "Abmelden" })
    expect(logoutLink).toBeInTheDocument()
    expect(logoutLink).toHaveAttribute("href", "http://example.com")
  })
})
