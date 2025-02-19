import { describe, it, expect, vi, beforeEach } from "vitest"

describe("useAknElementEidSelection", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
  })

  it("values is initially empty", async () => {
    const { useAknElementEidSelection } = await import(
      "./useAknElementEidSelection"
    )

    const { values } = useAknElementEidSelection([])
    expect(values.value).toHaveLength(0)
  })

  it("selects new value on mouse click", async () => {
    const { useAknElementEidSelection } = await import(
      "./useAknElementEidSelection"
    )

    const { values, handleAknElementClick } = useAknElementEidSelection([
      "eid-1",
      "eid-2",
    ])

    handleAknElementClick({
      eid: "eid-1",
      originalEvent: new MouseEvent("click"),
    })

    expect(values.value).toHaveLength(1)
    expect(values.value).toContain("eid-1")
  })

  it("selects new value on keyboard event", async () => {
    const { useAknElementEidSelection } = await import(
      "./useAknElementEidSelection"
    )

    const { values, handleAknElementClick } = useAknElementEidSelection([
      "eid-1",
      "eid-2",
    ])

    handleAknElementClick({
      eid: "eid-1",
      originalEvent: new KeyboardEvent("keydown"),
    })

    expect(values.value).toHaveLength(1)
    expect(values.value).toContain("eid-1")
  })

  it("normal click replaces the selection", async () => {
    const { useAknElementEidSelection } = await import(
      "./useAknElementEidSelection"
    )

    const { values, handleAknElementClick } = useAknElementEidSelection([
      "eid-1",
      "eid-2",
    ])

    handleAknElementClick({
      eid: "eid-1",
      originalEvent: new MouseEvent("click"),
    })

    handleAknElementClick({
      eid: "eid-2",
      originalEvent: new MouseEvent("click"),
    })

    expect(values.value).toHaveLength(1)
    expect(values.value).toContain("eid-2")
  })

  it("extends the selection if the meta key is pressed", async () => {
    const { useAknElementEidSelection } = await import(
      "./useAknElementEidSelection"
    )

    const { values, handleAknElementClick } = useAknElementEidSelection([
      "eid-1",
      "eid-2",
      "eid-3",
    ])

    handleAknElementClick({
      eid: "eid-1",
      originalEvent: new KeyboardEvent("keydown"),
    })

    handleAknElementClick({
      eid: "eid-2",
      originalEvent: new KeyboardEvent("keydown", { metaKey: true }),
    })

    expect(values.value).toHaveLength(2)
    expect(values.value).toContain("eid-1")
    expect(values.value).toContain("eid-2")
  })

  it("extends the selection if the ctrl key is pressed", async () => {
    const { useAknElementEidSelection } = await import(
      "./useAknElementEidSelection"
    )

    const { values, handleAknElementClick } = useAknElementEidSelection([
      "eid-1",
      "eid-2",
      "eid-3",
    ])

    handleAknElementClick({
      eid: "eid-1",
      originalEvent: new MouseEvent("click"),
    })

    handleAknElementClick({
      eid: "eid-2",
      originalEvent: new MouseEvent("click", { ctrlKey: true }),
    })

    expect(values.value).toHaveLength(2)
    expect(values.value).toContain("eid-1")
    expect(values.value).toContain("eid-2")
  })

  it("deselects just the clicked element if the ctrl key is pressed", async () => {
    const { useAknElementEidSelection } = await import(
      "./useAknElementEidSelection"
    )

    const { values, handleAknElementClick } = useAknElementEidSelection([
      "eid-1",
      "eid-2",
      "eid-3",
    ])

    handleAknElementClick({
      eid: "eid-1",
      originalEvent: new MouseEvent("click"),
    })

    handleAknElementClick({
      eid: "eid-3",
      originalEvent: new MouseEvent("click", { shiftKey: true }),
    })

    handleAknElementClick({
      eid: "eid-2",
      originalEvent: new MouseEvent("click", { ctrlKey: true }),
    })

    expect(values.value).toHaveLength(2)
    expect(values.value).toContain("eid-1")
    expect(values.value).toContain("eid-3")
  })

  it("deselectAll clears the selection", async () => {
    const { useAknElementEidSelection } = await import(
      "./useAknElementEidSelection"
    )

    const { values, handleAknElementClick, deselectAll } =
      useAknElementEidSelection(["eid-1", "eid-2"])

    handleAknElementClick({
      eid: "eid-1",
      originalEvent: new KeyboardEvent("keydown"),
    })

    deselectAll()

    expect(values.value).toHaveLength(0)
  })

  it("shift click allows to select a range", async () => {
    const { useAknElementEidSelection } = await import(
      "./useAknElementEidSelection"
    )

    const { values, handleAknElementClick } = useAknElementEidSelection([
      "eid-1",
      "eid-2",
      "eid-3",
      "eid-4",
      "eid-5",
    ])

    handleAknElementClick({
      eid: "eid-1",
      originalEvent: new MouseEvent("click"),
    })

    handleAknElementClick({
      eid: "eid-4",
      originalEvent: new MouseEvent("click", { shiftKey: true }),
    })

    expect(values.value).toHaveLength(4)
    expect(values.value).toContain("eid-1")
    expect(values.value).toContain("eid-2")
    expect(values.value).toContain("eid-3")
    expect(values.value).toContain("eid-4")
  })

  it("shift click allows to select a range (inverse direction)", async () => {
    const { useAknElementEidSelection } = await import(
      "./useAknElementEidSelection"
    )

    const { values, handleAknElementClick } = useAknElementEidSelection([
      "eid-1",
      "eid-2",
      "eid-3",
      "eid-4",
      "eid-5",
    ])

    handleAknElementClick({
      eid: "eid-4",
      originalEvent: new MouseEvent("click"),
    })

    handleAknElementClick({
      eid: "eid-2",
      originalEvent: new MouseEvent("click", { shiftKey: true }),
    })

    expect(values.value).toHaveLength(3)
    expect(values.value).toContain("eid-2")
    expect(values.value).toContain("eid-3")
    expect(values.value).toContain("eid-4")
  })

  it("shift click starts range selection add last clicked element", async () => {
    const { useAknElementEidSelection } = await import(
      "./useAknElementEidSelection"
    )

    const { values, handleAknElementClick } = useAknElementEidSelection([
      "eid-1",
      "eid-2",
      "eid-3",
      "eid-4",
      "eid-5",
    ])

    handleAknElementClick({
      eid: "eid-3",
      originalEvent: new MouseEvent("click"),
    })

    handleAknElementClick({
      eid: "eid-1",
      originalEvent: new MouseEvent("click", { ctrlKey: true }),
    })

    handleAknElementClick({
      eid: "eid-2",
      originalEvent: new MouseEvent("click", { ctrlKey: true }),
    })

    handleAknElementClick({
      eid: "eid-4",
      originalEvent: new MouseEvent("click", { shiftKey: true }),
    })

    expect(values.value).toHaveLength(3)
    expect(values.value).toContain("eid-2")
    expect(values.value).toContain("eid-3")
    expect(values.value).toContain("eid-4")
  })

  it("shift click starts range selection add last clicked element, even if it was deselected", async () => {
    const { useAknElementEidSelection } = await import(
      "./useAknElementEidSelection"
    )

    const { values, handleAknElementClick } = useAknElementEidSelection([
      "eid-1",
      "eid-2",
      "eid-3",
      "eid-4",
      "eid-5",
    ])

    handleAknElementClick({
      eid: "eid-2",
      originalEvent: new MouseEvent("click"),
    })

    handleAknElementClick({
      eid: "eid-2",
      originalEvent: new MouseEvent("click", { ctrlKey: true }),
    })

    handleAknElementClick({
      eid: "eid-4",
      originalEvent: new MouseEvent("click", { shiftKey: true }),
    })

    expect(values.value).toHaveLength(3)
    expect(values.value).toContain("eid-2")
    expect(values.value).toContain("eid-3")
    expect(values.value).toContain("eid-4")
  })

  it("shift + ctrl click allows to add selection of a range", async () => {
    const { useAknElementEidSelection } = await import(
      "./useAknElementEidSelection"
    )

    const { values, handleAknElementClick } = useAknElementEidSelection([
      "eid-1",
      "eid-2",
      "eid-3",
      "eid-4",
      "eid-5",
    ])

    handleAknElementClick({
      eid: "eid-1",
      originalEvent: new MouseEvent("click"),
    })

    handleAknElementClick({
      eid: "eid-3",
      originalEvent: new MouseEvent("click", { ctrlKey: true }),
    })

    handleAknElementClick({
      eid: "eid-5",
      originalEvent: new MouseEvent("click", { shiftKey: true, ctrlKey: true }),
    })

    expect(values.value).toHaveLength(4)
    expect(values.value).toContain("eid-1")
    expect(values.value).toContain("eid-3")
    expect(values.value).toContain("eid-4")
    expect(values.value).toContain("eid-5")
  })

  it("shift + ctrl click allows to deselect a range of a selection", async () => {
    const { useAknElementEidSelection } = await import(
      "./useAknElementEidSelection"
    )

    const { values, handleAknElementClick } = useAknElementEidSelection([
      "eid-1",
      "eid-2",
      "eid-3",
      "eid-4",
      "eid-5",
    ])

    handleAknElementClick({
      eid: "eid-1",
      originalEvent: new MouseEvent("click"),
    })

    handleAknElementClick({
      eid: "eid-5",
      originalEvent: new MouseEvent("click", { shiftKey: true }),
    })

    handleAknElementClick({
      eid: "eid-2",
      originalEvent: new MouseEvent("click", { ctrlKey: true }),
    })

    handleAknElementClick({
      eid: "eid-4",
      originalEvent: new MouseEvent("click", { ctrlKey: true, shiftKey: true }),
    })

    expect(values.value).toHaveLength(2)
    expect(values.value).toContain("eid-1")
    expect(values.value).toContain("eid-5")
  })

  it("shift + ctrl click allows to add selection of a range over already selected elements", async () => {
    const { useAknElementEidSelection } = await import(
      "./useAknElementEidSelection"
    )

    const { values, handleAknElementClick } = useAknElementEidSelection([
      "eid-1",
      "eid-2",
      "eid-3",
      "eid-4",
      "eid-5",
    ])

    handleAknElementClick({
      eid: "eid-3",
      originalEvent: new MouseEvent("click"),
    })

    handleAknElementClick({
      eid: "eid-1",
      originalEvent: new MouseEvent("click", { ctrlKey: true }),
    })

    handleAknElementClick({
      eid: "eid-5",
      originalEvent: new MouseEvent("click", { shiftKey: true, ctrlKey: true }),
    })

    expect(values.value).toHaveLength(5)
    expect(values.value).toContain("eid-1")
    expect(values.value).toContain("eid-2")
    expect(values.value).toContain("eid-3")
    expect(values.value).toContain("eid-4")
    expect(values.value).toContain("eid-5")
  })
})
