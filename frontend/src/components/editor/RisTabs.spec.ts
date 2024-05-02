import { describe, it, expect } from "vitest"
import { render, fireEvent, screen } from "@testing-library/vue"
import RisTabs from "./RisTabs.vue"
import { nextTick, ref } from "vue"

const ParentComponent = {
  components: { RisTabs },
  template: `
    <RisTabs :tabs="tabs" v-model:activeTab="activeTab" />
  `,
  setup() {
    const tabs = ref([
      { id: "tab1", label: "Tab 1" },
      { id: "tab2", label: "Tab 2" },
    ])
    const activeTab = ref("tab1")
    return { tabs, activeTab }
  },
}

describe("RisTabs", () => {
  it("activates the first tab by default", async () => {
    const tabs = [
      { id: "tab1", label: "Tab 1" },
      { id: "tab2", label: "Tab 2" },
    ]

    render(RisTabs, {
      props: { tabs },
    })

    await nextTick()

    const firstTab = screen.getByRole("tab", { name: "tab1" })
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

    const secondTab = screen.getByRole("tab", { name: "tab2" })
    expect(secondTab.ariaSelected).toBe("false")
    await fireEvent.click(secondTab)

    expect(secondTab.ariaSelected).toBe("true")
  })

  it("changes the tab status after updating the activeTab model", async () => {
    const parentComponent = render(ParentComponent)
    const firstTab = screen.getByRole("tab", { name: "tab1" })
    expect(firstTab.getAttribute("aria-selected")).toBe("true")
    await parentComponent.rerender({
      activeTab: "tab2",
    })
    const secondTab = screen.getByRole("tab", { name: "tab2" })
    expect(secondTab.getAttribute("aria-selected")).toBe("true")
  })

  it("aligns the tabs to the left by default", async () => {
    const tabs = [
      { id: "tab1", label: "Tab 1" },
      { id: "tab2", label: "Tab 2" },
    ]

    render(RisTabs, {
      props: { tabs },
    })

    await nextTick()

    const tabList = screen.getByRole("tablist")

    expect(tabList.classList.contains("float-right")).toBe(false)
  })

  it("aligns the tabs to the right", async () => {
    const tabs = [
      { id: "tab1", label: "Tab 1" },
      { id: "tab2", label: "Tab 2" },
    ]

    render(RisTabs, {
      props: { tabs, align: "right" },
    })

    await nextTick()

    const tabList = screen.getByRole("tablist")

    expect(tabList.classList.contains("float-right")).toBe(true)
  })
})
