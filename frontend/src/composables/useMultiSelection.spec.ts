import { useMultiSelection } from "@/composables/useMultiSelection"
import { describe, it, expect } from "vitest"

describe("useMultiSelection", () => {
  it("values is initially empty", () => {
    const { values } = useMultiSelection<string>()
    expect(values.value).toHaveLength(0)
  })

  describe("select", () => {
    it("selects the provided value", () => {
      const { values, select } = useMultiSelection<string>()
      select("foo")
      expect(values.value).toHaveLength(1)
      expect(values.value).toContain("foo")
    })

    it("does not overwrite existing selection", () => {
      const { values, select } = useMultiSelection<string>()
      select("foo")
      select("bar")
      expect(values.value).toHaveLength(2)
      expect(values.value).toContain("foo")
      expect(values.value).toContain("bar")
    })
  })

  describe("toggle", () => {
    it("selects the value is it's not selected", () => {
      const { values, toggle } = useMultiSelection<string>()
      toggle("foo")
      expect(values.value).toHaveLength(1)
      expect(values.value).toContain("foo")
    })

    it("deselects the value if it is selected", () => {
      const { values, toggle, selectAll } = useMultiSelection<string>()
      selectAll(["foo", "bar", "baz"])
      toggle("bar")
      expect(values.value).toHaveLength(2)
      expect(values.value).toContain("foo")
      expect(values.value).toContain("baz")
    })
  })

  describe("selectAll", () => {
    it("selects multiple values", () => {
      const { values, selectAll } = useMultiSelection<string>()
      selectAll(["foo", "bar"])
      expect(values.value).toHaveLength(2)
      expect(values.value).toContain("foo")
      expect(values.value).toContain("bar")
    })

    it("does not overwrite existing selection", () => {
      const { values, select, selectAll } = useMultiSelection<string>()
      select("foo")
      selectAll(["bar", "baz"])
      expect(values.value).toHaveLength(3)
      expect(values.value).toContain("foo")
      expect(values.value).toContain("bar")
      expect(values.value).toContain("baz")
    })
  })

  describe("clear", () => {
    it("clears the selection", () => {
      const { values, selectAll, clear } = useMultiSelection<string>()
      selectAll(["foo", "bar"])
      clear()
      expect(values.value).toHaveLength(0)
    })
  })

  describe("deselectAll", () => {
    it("deselects multiple values", () => {
      const { values, selectAll, deselectAll } = useMultiSelection<string>()
      selectAll(["foo", "bar", "baz"])
      deselectAll(["foo", "bar"])
      expect(values.value).toHaveLength(1)
      expect(values.value).toContain("baz")
    })
  })
})
