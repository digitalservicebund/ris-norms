import { render, screen } from "@testing-library/vue"
import { describe, expect, it } from "vitest"
import RisNavbar from "./RisNavbar.vue"
import { createRouter, createWebHashHistory } from "vue-router"

describe("risNavbar", () => {
  it("should show 'Rechtsinformationen' and 'des Bundes'", () => {
    const router = createRouter({
      history: createWebHashHistory(),
      routes: [{ name: "Home", path: "/", component: () => {} }],
    })

    render(RisNavbar, { global: { plugins: [router] } })
    expect(screen.getByText("Rechtsinformationen")).toBeInTheDocument()
    expect(screen.getByText("des Bundes")).toBeInTheDocument()
  })

  it("should render the logout button", () => {
    const router = createRouter({
      history: createWebHashHistory(),
      routes: [{ name: "Home", path: "/", component: () => {} }],
    })

    render(RisNavbar, { global: { plugins: [router] } })
    expect(screen.getByRole("button", { name: "Logout" })).toBeInTheDocument()
  })

  //   navigate to /logout by calling windwo.location assing.
})
