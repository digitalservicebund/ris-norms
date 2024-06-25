import { render, screen } from "@testing-library/vue"
import { describe, expect, test } from "vitest"
import { createRouter, createWebHistory } from "vue-router"
import type { Router, RouteRecordRaw, RouteLocationRaw } from "vue-router"
import RisNavbarSide from "./RisNavbarSide.vue"

describe("NavbarSide", () => {
  test("it displays the go back label with related route", async () => {
    await renderComponent({
      goBackLabel: "Zur Übersicht",
    })

    const goBackItem = screen.getByText("Zur Übersicht")
    expect(goBackItem).toBeVisible()
  })

  test("it renders sidenav with multiple items and correct routes", async () => {
    const menuItems = [
      { label: "first item", route: "/first-route" },
      { label: "second item", route: "/second-route" },
    ]

    await renderComponent({ menuItems })
    const firstItem = screen.getByLabelText("first item") as HTMLElement
    const secondItem = screen.getByLabelText("second item") as HTMLElement

    expect(firstItem).toBeVisible()
    expect(secondItem).toBeVisible()

    expect(firstItem).toHaveAttribute("href", "/first-route")
    expect(secondItem).toHaveAttribute("href", "/second-route")
  })

  test("it shows all level two items of an active level one item", async () => {
    const menuItems = [
      {
        label: "first level one",
        route: "/matching",
        children: [
          { label: "first level two", route: "/not-matching" },
          { label: "second level two", route: "/not-matching" },
        ],
      },
    ]

    await renderComponent({
      menuItems,
      activeRoute: "/matching",
    })

    expect(screen.queryByText("first level one")).toBeVisible()
    expect(screen.queryByText("first level two")).toBeVisible()
    expect(screen.queryByText("second level two")).toBeVisible()
  })

  test("it underlines the expanded level one item with children", async () => {
    const menuItems = [
      {
        label: "underlined level one",
        route: "/route",
        children: [
          { label: "child one", route: "/child-one" },
          { label: "child two", route: "/child-two" },
        ],
      },
    ]

    await renderComponent({ menuItems, activeRoute: "/child-one" })

    expect(screen.getByLabelText("underlined level one")).toHaveClass(
      "underline",
    )
    expect(screen.getByLabelText("child one")).toHaveClass("underline")
  })

  test("it applies special class to menu item which matches current route", async () => {
    const menuItems = [
      { label: "active item", route: { path: "/matching" } },
      { label: "passive item", route: { path: "/not-matching" } },
    ]
    await renderComponent({
      menuItems,
      activeRoute: { path: "/matching" },
    })

    expect(screen.getByLabelText("active item")).toHaveClass("bg-blue-200")
    expect(screen.getByLabelText("passive item")).not.toHaveClass("bg-blue-200")
  })

  test("hides the back button if no route is provided", () => {
    renderComponent({ goBackRoute: undefined })

    expect(screen.queryByLabelText("Zurück")).not.toBeInTheDocument()
  })
})

const ALPHABET_CHARACTERS = "abcdefghijklmnopqrstuvwxyz"

export function generateRandomNumber(minimum = 0, maximum = 10): number {
  return Math.floor(Math.random() * (maximum - minimum) + minimum)
}

export function pickRandomBoolean(): boolean {
  return Math.random() < 0.5
}

export function generateString(options?: {
  characterSet?: string
  length?: number
  prefix?: string
}): string {
  const characterSet = options?.characterSet ?? ALPHABET_CHARACTERS
  const length = options?.length ?? 5
  let output = options?.prefix ?? ""

  for (let i = 0; i < length; i++) {
    output += characterSet.charAt(
      generateRandomNumber(0, characterSet.length - 1),
    )
  }

  return output
}

interface MenuItem {
  label: string
  route: RouteLocationRaw
  children?: MenuItem[]
  isDisabled?: boolean
}

async function renderComponent(options?: {
  goBackLabel?: string
  goBackRoute?: RouteLocationRaw
  menuItems?: MenuItem[]
  activeRoute?: RouteLocationRaw
}) {
  const goBackRoute = options?.goBackRoute ?? "/go-back-route"
  const menuItems = options?.menuItems ?? []
  const activeRoute = options?.activeRoute ?? "/"
  const router = buildRouter(goBackRoute, menuItems)
  await router.replace(activeRoute)
  await router.isReady()
  const global = { plugins: [router] }
  const props = {
    goBackLabel: options?.goBackLabel ?? "go back label",
    goBackRoute,
    menuItems: options?.menuItems ?? [],
  }
  return render(RisNavbarSide, { props, global })
}

function buildRouter(
  goBackRoute: RouteLocationRaw,
  menuItems: MenuItem[],
): Router {
  const routes = []
  routes.push(generateRouterRoute({ path: "/" }))
  routes.push(generateRouterRoute(goBackRoute))

  for (const item of menuItems) {
    routes.push(generateRouterRoute(item.route))

    for (const child of item.children ?? []) {
      routes.push(generateRouterRoute(child.route))
    }
  }

  return createRouter({ routes, history: createWebHistory() })
}

function generateRouterRoute(routeLocation: RouteLocationRaw): RouteRecordRaw {
  if (typeof routeLocation === "string") {
    const routeAsUrl = new URL(routeLocation, "https://fake.com")
    return { path: routeAsUrl.pathname, component: {} }
  } else {
    const path =
      "path" in routeLocation && routeLocation.path !== undefined
        ? routeLocation.path
        : generateString({ prefix: "/path-" })
    return { ...routeLocation, path, component: {} }
  }
}
