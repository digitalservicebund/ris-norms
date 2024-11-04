import { describe, it, expect, vi, beforeEach } from "vitest"
import { nextTick, ref } from "vue"

describe("useModEidSelection", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
  })

  it("values is initially empty", async () => {
    vi.doMock("./useModEidPathParameter", () => ({
      useModEidPathParameter: vi.fn().mockReturnValue(ref("")),
    }))

    const { useModEidSelection } = await import("./useModEidSelection")

    const { values } = useModEidSelection([])
    expect(values.value).toHaveLength(0)
  })

  it("selects new value on mouse click", async () => {
    vi.doMock("./useModEidPathParameter", () => ({
      useModEidPathParameter: vi.fn().mockReturnValue(ref("")),
    }))

    const { useModEidSelection } = await import("./useModEidSelection")

    const { values, handleAknModClick } = useModEidSelection(["eid-1", "eid-2"])

    handleAknModClick({
      eid: "eid-1",
      originalEvent: new MouseEvent("click"),
    })

    expect(values.value).toHaveLength(1)
    expect(values.value).toContain("eid-1")
  })

  it("selects new value on keyboard event", async () => {
    vi.doMock("./useModEidPathParameter", () => ({
      useModEidPathParameter: vi.fn().mockReturnValue(ref("")),
    }))

    const { useModEidSelection } = await import("./useModEidSelection")

    const { values, handleAknModClick } = useModEidSelection(["eid-1", "eid-2"])

    handleAknModClick({
      eid: "eid-1",
      originalEvent: new KeyboardEvent("keydown"),
    })

    expect(values.value).toHaveLength(1)
    expect(values.value).toContain("eid-1")
  })

  it("normal click replaces the selection", async () => {
    vi.doMock("./useModEidPathParameter", () => ({
      useModEidPathParameter: vi.fn().mockReturnValue(ref("")),
    }))

    const { useModEidSelection } = await import("./useModEidSelection")

    const { values, handleAknModClick } = useModEidSelection(["eid-1", "eid-2"])

    handleAknModClick({
      eid: "eid-1",
      originalEvent: new MouseEvent("click"),
    })

    handleAknModClick({
      eid: "eid-2",
      originalEvent: new MouseEvent("click"),
    })

    expect(values.value).toHaveLength(1)
    expect(values.value).toContain("eid-2")
  })

  it("extends the selection if the meta key is pressed", async () => {
    vi.doMock("./useModEidPathParameter", () => ({
      useModEidPathParameter: vi.fn().mockReturnValue(ref("")),
    }))

    const { useModEidSelection } = await import("./useModEidSelection")

    const { values, handleAknModClick } = useModEidSelection([
      "eid-1",
      "eid-2",
      "eid-3",
    ])

    handleAknModClick({
      eid: "eid-1",
      originalEvent: new KeyboardEvent("keydown"),
    })

    handleAknModClick({
      eid: "eid-2",
      originalEvent: new KeyboardEvent("keydown", { metaKey: true }),
    })

    expect(values.value).toHaveLength(2)
    expect(values.value).toContain("eid-1")
    expect(values.value).toContain("eid-2")
  })

  it("extends the selection if the ctrl key is pressed", async () => {
    vi.doMock("./useModEidPathParameter", () => ({
      useModEidPathParameter: vi.fn().mockReturnValue(ref("")),
    }))

    const { useModEidSelection } = await import("./useModEidSelection")

    const { values, handleAknModClick } = useModEidSelection([
      "eid-1",
      "eid-2",
      "eid-3",
    ])

    handleAknModClick({
      eid: "eid-1",
      originalEvent: new MouseEvent("click"),
    })

    handleAknModClick({
      eid: "eid-2",
      originalEvent: new MouseEvent("click", { ctrlKey: true }),
    })

    expect(values.value).toHaveLength(2)
    expect(values.value).toContain("eid-1")
    expect(values.value).toContain("eid-2")
  })

  it("deselects just the clicked element if the ctrl key is pressed", async () => {
    vi.doMock("./useModEidPathParameter", () => ({
      useModEidPathParameter: vi.fn().mockReturnValue(ref("")),
    }))

    const { useModEidSelection } = await import("./useModEidSelection")

    const { values, handleAknModClick } = useModEidSelection([
      "eid-1",
      "eid-2",
      "eid-3",
    ])

    handleAknModClick({
      eid: "eid-1",
      originalEvent: new MouseEvent("click"),
    })

    handleAknModClick({
      eid: "eid-3",
      originalEvent: new MouseEvent("click", { shiftKey: true }),
    })

    handleAknModClick({
      eid: "eid-2",
      originalEvent: new MouseEvent("click", { ctrlKey: true }),
    })

    expect(values.value).toHaveLength(2)
    expect(values.value).toContain("eid-1")
    expect(values.value).toContain("eid-3")
  })

  it("deselectAll clears the selection", async () => {
    vi.doMock("./useModEidPathParameter", () => ({
      useModEidPathParameter: vi.fn().mockReturnValue(ref("")),
    }))

    const { useModEidSelection } = await import("./useModEidSelection")

    const { values, handleAknModClick, deselectAll } = useModEidSelection([
      "eid-1",
      "eid-2",
    ])

    handleAknModClick({
      eid: "eid-1",
      originalEvent: new KeyboardEvent("keydown"),
    })

    deselectAll()

    expect(values.value).toHaveLength(0)
  })

  it("shift click allows to select a range", async () => {
    vi.doMock("./useModEidPathParameter", () => ({
      useModEidPathParameter: vi.fn().mockReturnValue(ref("")),
    }))

    const { useModEidSelection } = await import("./useModEidSelection")

    const { values, handleAknModClick } = useModEidSelection([
      "eid-1",
      "eid-2",
      "eid-3",
      "eid-4",
      "eid-5",
    ])

    handleAknModClick({
      eid: "eid-1",
      originalEvent: new MouseEvent("click"),
    })

    handleAknModClick({
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
    vi.doMock("./useModEidPathParameter", () => ({
      useModEidPathParameter: vi.fn().mockReturnValue(ref("")),
    }))

    const { useModEidSelection } = await import("./useModEidSelection")

    const { values, handleAknModClick } = useModEidSelection([
      "eid-1",
      "eid-2",
      "eid-3",
      "eid-4",
      "eid-5",
    ])

    handleAknModClick({
      eid: "eid-4",
      originalEvent: new MouseEvent("click"),
    })

    handleAknModClick({
      eid: "eid-2",
      originalEvent: new MouseEvent("click", { shiftKey: true }),
    })

    expect(values.value).toHaveLength(3)
    expect(values.value).toContain("eid-2")
    expect(values.value).toContain("eid-3")
    expect(values.value).toContain("eid-4")
  })

  it("shift click starts range selection add last clicked element", async () => {
    vi.doMock("./useModEidPathParameter", () => ({
      useModEidPathParameter: vi.fn().mockReturnValue(ref("")),
    }))

    const { useModEidSelection } = await import("./useModEidSelection")

    const { values, handleAknModClick } = useModEidSelection([
      "eid-1",
      "eid-2",
      "eid-3",
      "eid-4",
      "eid-5",
    ])

    handleAknModClick({
      eid: "eid-3",
      originalEvent: new MouseEvent("click"),
    })

    handleAknModClick({
      eid: "eid-1",
      originalEvent: new MouseEvent("click", { ctrlKey: true }),
    })

    handleAknModClick({
      eid: "eid-2",
      originalEvent: new MouseEvent("click", { ctrlKey: true }),
    })

    handleAknModClick({
      eid: "eid-4",
      originalEvent: new MouseEvent("click", { shiftKey: true }),
    })

    expect(values.value).toHaveLength(3)
    expect(values.value).toContain("eid-2")
    expect(values.value).toContain("eid-3")
    expect(values.value).toContain("eid-4")
  })

  it("shift click starts range selection add last clicked element, even if it was deselected", async () => {
    vi.doMock("./useModEidPathParameter", () => ({
      useModEidPathParameter: vi.fn().mockReturnValue(ref("")),
    }))

    const { useModEidSelection } = await import("./useModEidSelection")

    const { values, handleAknModClick } = useModEidSelection([
      "eid-1",
      "eid-2",
      "eid-3",
      "eid-4",
      "eid-5",
    ])

    handleAknModClick({
      eid: "eid-2",
      originalEvent: new MouseEvent("click"),
    })

    handleAknModClick({
      eid: "eid-2",
      originalEvent: new MouseEvent("click", { ctrlKey: true }),
    })

    handleAknModClick({
      eid: "eid-4",
      originalEvent: new MouseEvent("click", { shiftKey: true }),
    })

    expect(values.value).toHaveLength(3)
    expect(values.value).toContain("eid-2")
    expect(values.value).toContain("eid-3")
    expect(values.value).toContain("eid-4")
  })

  it("shift + ctrl click allows to add selection of a range", async () => {
    vi.doMock("./useModEidPathParameter", () => ({
      useModEidPathParameter: vi.fn().mockReturnValue(ref("")),
    }))

    const { useModEidSelection } = await import("./useModEidSelection")

    const { values, handleAknModClick } = useModEidSelection([
      "eid-1",
      "eid-2",
      "eid-3",
      "eid-4",
      "eid-5",
    ])

    handleAknModClick({
      eid: "eid-1",
      originalEvent: new MouseEvent("click"),
    })

    handleAknModClick({
      eid: "eid-3",
      originalEvent: new MouseEvent("click", { ctrlKey: true }),
    })

    handleAknModClick({
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
    vi.doMock("./useModEidPathParameter", () => ({
      useModEidPathParameter: vi.fn().mockReturnValue(ref("")),
    }))

    const { useModEidSelection } = await import("./useModEidSelection")

    const { values, handleAknModClick } = useModEidSelection([
      "eid-1",
      "eid-2",
      "eid-3",
      "eid-4",
      "eid-5",
    ])

    handleAknModClick({
      eid: "eid-1",
      originalEvent: new MouseEvent("click"),
    })

    handleAknModClick({
      eid: "eid-5",
      originalEvent: new MouseEvent("click", { shiftKey: true }),
    })

    handleAknModClick({
      eid: "eid-2",
      originalEvent: new MouseEvent("click", { ctrlKey: true }),
    })

    handleAknModClick({
      eid: "eid-4",
      originalEvent: new MouseEvent("click", { ctrlKey: true, shiftKey: true }),
    })

    expect(values.value).toHaveLength(2)
    expect(values.value).toContain("eid-1")
    expect(values.value).toContain("eid-5")
  })

  it("shift + ctrl click allows to add selection of a range over already selected elements", async () => {
    vi.doMock("./useModEidPathParameter", () => ({
      useModEidPathParameter: vi.fn().mockReturnValue(ref("")),
    }))

    const { useModEidSelection } = await import("./useModEidSelection")

    const { values, handleAknModClick } = useModEidSelection([
      "eid-1",
      "eid-2",
      "eid-3",
      "eid-4",
      "eid-5",
    ])

    handleAknModClick({
      eid: "eid-3",
      originalEvent: new MouseEvent("click"),
    })

    handleAknModClick({
      eid: "eid-1",
      originalEvent: new MouseEvent("click", { ctrlKey: true }),
    })

    handleAknModClick({
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

  describe("path parameter", () => {
    it("values takes initial value from path", async () => {
      vi.doMock("./useModEidPathParameter", () => ({
        useModEidPathParameter: vi.fn().mockReturnValue(ref("eid-1")),
      }))

      const { useModEidSelection } = await import("./useModEidSelection")

      const { values } = useModEidSelection(["eid-1", "eid-2"])
      await nextTick()
      expect(values.value).toHaveLength(1)
      expect(values.value).toContain("eid-1")
    })

    it("path parameter includes the selected eid if only one is selected", async () => {
      const pathParameter = ref("")
      vi.doMock("./useModEidPathParameter", () => ({
        useModEidPathParameter: vi.fn().mockReturnValue(pathParameter),
      }))

      const { useModEidSelection } = await import("./useModEidSelection")

      const { handleAknModClick } = useModEidSelection(["eid-1", "eid-2"])

      handleAknModClick({
        eid: "eid-1",
        originalEvent: new MouseEvent("click"),
      })

      await nextTick()

      expect(pathParameter.value).toBe("eid-1")
    })

    it("path parameter is empty when multiple eids are selected", async () => {
      const pathParameter = ref("")
      vi.doMock("./useModEidPathParameter", () => ({
        useModEidPathParameter: vi.fn().mockReturnValue(pathParameter),
      }))

      const { useModEidSelection } = await import("./useModEidSelection")

      const { handleAknModClick } = useModEidSelection(["eid-1", "eid-2"])

      handleAknModClick({
        eid: "eid-1",
        originalEvent: new MouseEvent("click"),
      })
      handleAknModClick({
        eid: "eid-2",
        originalEvent: new MouseEvent("click", { metaKey: true }),
      })

      await nextTick()

      expect(pathParameter.value).toBe("")
    })
  })
})
