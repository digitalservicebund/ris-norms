import { describe, it, expect } from "vitest"
import { render, fireEvent, screen } from "@testing-library/vue"
import RisTabs from "./RisTabs.vue"
import { nextTick } from "vue"

describe("RisTabs", () => {
  it("activates the first tab by default", async () => {
    const tabs = [
      { id: "tab1", label: "Tab 1" },
      { id: "tab2", label: "Tab 2" },
    ]

    render(RisTabs, {
      props: { tabs },
    })

    // Wait for the next DOM update
    await nextTick()

    // Use the aria-label attribute value to find the first tab
    const firstTab = screen.getByRole("tab", { name: "tab1" })
    // Check the `aria-selected` attribute directly
    expect(firstTab.getAttribute("aria-selected")).toBe("true")
  })

  it("switches to the clicked tab", async () => {
    const tabs = [
      { id: "tab1", label: "Tab 1" },
      { id: "tab2", label: "Tab 2" },
    ]

    render(RisTabs, {
      props: { tabs },
    })

    // Use the aria-label attribute value to find the second tab
    const secondTab = screen.getByRole("tab", { name: "tab2" })
    await fireEvent.click(secondTab)

    expect(secondTab.ariaSelected).toBe("true")
  })
})
