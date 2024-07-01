import { useMultiSelection } from "@/composables/useMultiSelection"
import { describe, test, expect } from "vitest"

describe("useMultiSelection", () => {
  test("values is initially empty", () => {
    const { values } = useMultiSelection<string>()
    expect(values.value).toHaveLength(0)
  })

  describe("select", () => {
    test("selects the provided value", () => {
      const { values, select } = useMultiSelection<string>()
      select("foo")
      expect(values.value).toHaveLength(1)
      expect(values.value).toContain("foo")
    })

    test("does not overwrite existing selection", () => {
      const { values, select } = useMultiSelection<string>()
      select("foo")
      select("bar")
      expect(values.value).toHaveLength(2)
      expect(values.value).toContain("foo")
      expect(values.value).toContain("bar")
    })
  })

  describe("toggle", () => {
    test("selects the value is it's not selected", () => {
      const { values, toggle } = useMultiSelection<string>()
      toggle("foo")
      expect(values.value).toHaveLength(1)
      expect(values.value).toContain("foo")
    })

    test("deselects the value if it is selected", () => {
      const { values, toggle, selectAll } = useMultiSelection<string>()
      selectAll(["foo", "bar", "baz"])
      toggle("bar")
      expect(values.value).toHaveLength(2)
      expect(values.value).toContain("foo")
      expect(values.value).toContain("baz")
    })
  })

  describe("selectAll", () => {
    test("selects multiple values", () => {
      const { values, selectAll } = useMultiSelection<string>()
      selectAll(["foo", "bar"])
      expect(values.value).toHaveLength(2)
      expect(values.value).toContain("foo")
      expect(values.value).toContain("bar")
    })

    test("does not overwrite existing selection", () => {
      const { values, select, selectAll } = useMultiSelection<string>()
      select("foo")
      selectAll(["bar", "baz"])
      expect(values.value).toHaveLength(3)
      expect(values.value).toContain("foo")
      expect(values.value).toContain("bar")
      expect(values.value).toContain("baz")
    })
  })

  describe("deselectAll", () => {
    test("clears the selection", () => {
      const { values, selectAll, deselectAll } = useMultiSelection<string>()
      selectAll(["foo", "bar"])
      deselectAll()
      expect(values.value).toHaveLength(0)
    })
  })
})
