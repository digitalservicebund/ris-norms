import { render, screen, fireEvent } from "@testing-library/vue"
import { describe, expect, it, vi } from "vitest"
import RisNavbar from "./RisNavbar.vue"
import { createRouter, createWebHashHistory } from "vue-router"

const add = vi.fn()
vi.mock("primevue/usetoast", () => {
  return {
    useToast: () => ({
      add: add,
    }),
  }
})

const pushMock = vi.fn()
vi.mock("vue-router", async () => {
  const actual = await vi.importActual("vue-router")
  return {
    ...actual,
    useRouter: () => ({
      push: pushMock,
    }),
  }
})

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

  it("should redirect to Home on logout button click", async () => {
    const router = createRouter({
      history: createWebHashHistory(),
      routes: [{ name: "Home", path: "/", component: () => {} }],
    })

    render(RisNavbar, { global: { plugins: [router] } })

    const logoutButton = screen.getByRole("button", { name: "Logout" })
    await fireEvent.click(logoutButton)

    expect(pushMock).toHaveBeenCalledWith({ name: "Home" })
  })
})
