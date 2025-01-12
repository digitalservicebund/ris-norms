import { render, screen, fireEvent } from "@testing-library/vue"
import { describe, expect, it, vi } from "vitest"
import RisNavbar from "./RisNavbar.vue"
import { createRouter, createWebHashHistory } from "vue-router"

describe("risNavbar", () => {
  it("should show 'Rechtsinformationen' and 'des Bundes'", () => {
    const router = createRouter({
      history: createWebHashHistory(),
      routes: [
        { name: "Home", path: "/", component: () => {} },
        { name: "Logout", path: "/logout", component: () => {} },
      ],
    })

    render(RisNavbar, { global: { plugins: [router] } })
    expect(screen.getByText("Rechtsinformationen")).toBeInTheDocument()
    expect(screen.getByText("des Bundes")).toBeInTheDocument()
  })

  it("should render the logout link", () => {
    const router = createRouter({
      history: createWebHashHistory(),
      routes: [
        { name: "Home", path: "/", component: () => {} },
        { name: "Logout", path: "/logout", component: () => {} },
      ],
    })

    render(RisNavbar, { global: { plugins: [router] } })
    expect(screen.getByRole("link", { name: "Ausloggen" })).toBeInTheDocument()
  })

  it("should navigate to '/logout' when the logout link is clicked", async () => {
    const router = createRouter({
      history: createWebHashHistory(),
      routes: [
        { name: "Home", path: "/", component: () => {} },
        { name: "Logout", path: "/logout", component: () => {} },
      ],
    })

    const pushSpy = vi.spyOn(router, "push")

    render(RisNavbar, { global: { plugins: [router] } })

    const logoutLink = screen.getByRole("link", { name: "Ausloggen" })
    await fireEvent.click(logoutLink)

    expect(pushSpy).toHaveBeenCalledWith("/logout")
  })
})
