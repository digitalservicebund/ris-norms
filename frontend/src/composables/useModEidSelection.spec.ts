import { describe, test, expect, vi, beforeEach } from "vitest"
import { nextTick, ref } from "vue"

describe("useModEidSelection", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
  })

  test("values is initially empty", async () => {
    vi.doMock("./useModEidPathParameter", () => ({
      useModEidPathParameter: vi.fn().mockReturnValue(ref("")),
    }))

    const { useModEidSelection } = await import("./useModEidSelection")

    const { values } = useModEidSelection()
    expect(values.value).toHaveLength(0)
  })

  test("selects new value on mouse click", async () => {
    vi.doMock("./useModEidPathParameter", () => ({
      useModEidPathParameter: vi.fn().mockReturnValue(ref("")),
    }))

    const { useModEidSelection } = await import("./useModEidSelection")

    const { values, handleAknModClick } = useModEidSelection()

    handleAknModClick({
      eid: "foo",
      originalEvent: new MouseEvent("click"),
    })

    expect(values.value).toHaveLength(1)
    expect(values.value).toContain("foo")
  })

  test("selects new value on keyboard event", async () => {
    vi.doMock("./useModEidPathParameter", () => ({
      useModEidPathParameter: vi.fn().mockReturnValue(ref("")),
    }))

    const { useModEidSelection } = await import("./useModEidSelection")

    const { values, handleAknModClick } = useModEidSelection()

    handleAknModClick({
      eid: "foo",
      originalEvent: new KeyboardEvent("keydown"),
    })

    expect(values.value).toHaveLength(1)
    expect(values.value).toContain("foo")
  })

  test("normal click replaces the selection", async () => {
    vi.doMock("./useModEidPathParameter", () => ({
      useModEidPathParameter: vi.fn().mockReturnValue(ref("")),
    }))

    const { useModEidSelection } = await import("./useModEidSelection")

    const { values, handleAknModClick } = useModEidSelection()

    handleAknModClick({
      eid: "foo",
      originalEvent: new MouseEvent("click"),
    })

    handleAknModClick({
      eid: "bar",
      originalEvent: new MouseEvent("click"),
    })

    expect(values.value).toHaveLength(1)
    expect(values.value).toContain("bar")
  })

  test("extends the selection if the meta key is pressed", async () => {
    vi.doMock("./useModEidPathParameter", () => ({
      useModEidPathParameter: vi.fn().mockReturnValue(ref("")),
    }))

    const { useModEidSelection } = await import("./useModEidSelection")

    const { values, handleAknModClick } = useModEidSelection()

    handleAknModClick({
      eid: "foo",
      originalEvent: new KeyboardEvent("keydown"),
    })

    handleAknModClick({
      eid: "bar",
      originalEvent: new KeyboardEvent("keydown", { metaKey: true }),
    })

    expect(values.value).toHaveLength(2)
    expect(values.value).toContain("foo")
    expect(values.value).toContain("bar")
  })

  test("extends the selection if the ctrl key is pressed", async () => {
    vi.doMock("./useModEidPathParameter", () => ({
      useModEidPathParameter: vi.fn().mockReturnValue(ref("")),
    }))

    const { useModEidSelection } = await import("./useModEidSelection")

    const { values, handleAknModClick } = useModEidSelection()

    handleAknModClick({
      eid: "foo",
      originalEvent: new MouseEvent("click"),
    })

    handleAknModClick({
      eid: "bar",
      originalEvent: new MouseEvent("click", { ctrlKey: true }),
    })

    expect(values.value).toHaveLength(2)
    expect(values.value).toContain("foo")
    expect(values.value).toContain("bar")
  })

  test("deselectAll clears the selection", async () => {
    vi.doMock("./useModEidPathParameter", () => ({
      useModEidPathParameter: vi.fn().mockReturnValue(ref("")),
    }))

    const { useModEidSelection } = await import("./useModEidSelection")

    const { values, handleAknModClick, deselectAll } = useModEidSelection()

    handleAknModClick({
      eid: "foo",
      originalEvent: new KeyboardEvent("keydown"),
    })

    deselectAll()

    expect(values.value).toHaveLength(0)
  })

  describe("path parameter", () => {
    test("values takes initial value from path", async () => {
      vi.doMock("./useModEidPathParameter", () => ({
        useModEidPathParameter: vi.fn().mockReturnValue(ref("test-eid")),
      }))

      const { useModEidSelection } = await import("./useModEidSelection")

      const { values } = useModEidSelection()
      await nextTick()
      expect(values.value).toHaveLength(1)
      expect(values.value).toContain("test-eid")
    })

    test("path parameter includes the selected eid if only one is selected", async () => {
      const pathParameter = ref("")
      vi.doMock("./useModEidPathParameter", () => ({
        useModEidPathParameter: vi.fn().mockReturnValue(pathParameter),
      }))

      const { useModEidSelection } = await import("./useModEidSelection")

      const { handleAknModClick } = useModEidSelection()

      handleAknModClick({
        eid: "foo",
        originalEvent: new MouseEvent("click"),
      })

      await nextTick()

      expect(pathParameter.value).toBe("foo")
    })

    test("path parameter is empty when multiple eids are selected", async () => {
      const pathParameter = ref("")
      vi.doMock("./useModEidPathParameter", () => ({
        useModEidPathParameter: vi.fn().mockReturnValue(pathParameter),
      }))

      const { useModEidSelection } = await import("./useModEidSelection")

      const { handleAknModClick } = useModEidSelection()

      handleAknModClick({
        eid: "foo",
        originalEvent: new MouseEvent("click"),
      })
      handleAknModClick({
        eid: "bar",
        originalEvent: new MouseEvent("click", { metaKey: true }),
      })

      await nextTick()

      expect(pathParameter.value).toBe("")
    })
  })
})
