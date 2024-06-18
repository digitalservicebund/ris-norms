import { render, screen } from "@testing-library/vue"
import { describe, expect, test } from "vitest"
import RisNavbar from "./RisNavbar.vue"
import { createRouter, createWebHashHistory } from "vue-router"

describe("RisNavbar", () => {
  test("should show 'Rechtsinformationen' and 'des Bundes'", () => {
    const router = createRouter({
      history: createWebHashHistory(),
      routes: [{ name: "Home", path: "/", component: () => {} }],
    })

    render(RisNavbar, { global: { plugins: [router] } })
    expect(screen.getByText("Rechtsinformationen")).toBeInTheDocument()
    expect(screen.getByText("des Bundes")).toBeInTheDocument()
  })
})
