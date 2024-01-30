import { render, screen } from "@testing-library/vue"
import { describe, expect, test, vi } from "vitest"
import { createRouter, createWebHistory, RouteRecordRaw } from "vue-router"
import RisNavbarSideLayout from "./RisNavbarSideLayout.vue"

vi.mock("@/components/controls/RisNavbarSide.vue", () => {
  return {
    default: {
      template: "<div>RisNavbarSide Mock</div>",
    },
  }
})
vi.mock("@/components/controls/RisUnitInfoPanel.vue", () => {
  return {
    default: {
      template: "<div>RisUnitInfoPanel Mock</div>",
    },
  }
})

const mockRouteRecord: RouteRecordRaw = {
  path: "/procedures/:id",
  component: RisNavbarSideLayout,
}

const router = createRouter({
  history: createWebHistory(),
  routes: [mockRouteRecord],
})

describe("YourLayoutComponent", () => {
  test("renders mocked components", async () => {
    render(RisNavbarSideLayout, {
      global: {
        plugins: [router],
      },
    })

    expect(screen.getByText("RisNavbarSide Mock")).toBeInTheDocument()
    expect(screen.getByText("RisUnitInfoPanel Mock")).toBeInTheDocument()
  })
})
