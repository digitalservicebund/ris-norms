import { describe, expect, it } from "vitest"
import {
  hasElementAsParentElement,
  limitSelectionToOneElement,
} from "@/composables/useAknTextSelection"

describe("useAknTextSelection", () => {
  describe("hasElementAsParentElement", () => {
    it("returns true if the parent element is the direct parent", () => {
      const htmlElement = document.createElement("div")
      htmlElement.innerHTML = `<span>Test</span>`

      const result = hasElementAsParentElement(
        htmlElement.firstChild!.firstChild!,
        htmlElement.firstChild!,
      )
      expect(result).toBe(true)
    })

    it("returns false if the parent element is a child", () => {
      const htmlElement = document.createElement("div")
      htmlElement.innerHTML = `<span>Test</span>`

      const result = hasElementAsParentElement(
        htmlElement.firstChild!,
        htmlElement.firstChild!.firstChild!,
      )
      expect(result).toBe(false)
    })

    it("returns true if the parent element is a grandparent", () => {
      const htmlElement = document.createElement("div")
      htmlElement.innerHTML = `<span>Test</span>`

      const result = hasElementAsParentElement(
        htmlElement.firstChild!.firstChild!,
        htmlElement,
      )
      expect(result).toBe(true)
    })
  })

  describe("limitSelectionToOneElement", () => {
    it("selection stays the same if it only has one node", () => {
      document.documentElement.innerHTML = `<span id="test">Test</span>`
      const textNode = document.getElementById("test")!.firstChild!

      const selection = document.getSelection()!
      selection.setPosition(textNode, 1)
      selection.extend(textNode, 3)

      limitSelectionToOneElement(selection)
      expect(selection.anchorNode).toEqual(textNode)
      expect(selection.focusNode).toEqual(textNode)
      expect(selection.anchorOffset).toEqual(1)
      expect(selection.focusOffset).toEqual(3)
    })

    it("selection ends at the end of the first selected node if the other node is behind it", () => {
      document.documentElement.innerHTML = `<span id="test-1">Test</span><span id="test-2">No. 2</span>`
      const textNode1 = document.getElementById("test-1")!.firstChild!
      const textNode2 = document.getElementById("test-2")!.firstChild!

      const selection = document.getSelection()!
      selection.setPosition(textNode1, 1)
      selection.extend(textNode2, 2)

      limitSelectionToOneElement(selection)

      expect(selection.anchorNode).toEqual(textNode1)
      expect(selection.focusNode).toEqual(textNode1)
      expect(selection.anchorOffset).toEqual(1)
      expect(selection.focusOffset).toEqual(4)
    })

    it("selection beginns at the start of the first selected node if the other node is in front of it", () => {
      document.documentElement.innerHTML = `<span id="test-1">Test</span><span id="test-2">No. 2</span>`
      const textNode1 = document.getElementById("test-1")!.firstChild!
      const textNode2 = document.getElementById("test-2")!.firstChild!

      const selection = document.getSelection()!
      selection.setPosition(textNode2, 2)
      selection.extend(textNode1, 2)

      limitSelectionToOneElement(selection)

      expect(selection.anchorNode).toEqual(textNode2)
      expect(selection.focusNode).toEqual(textNode2)
      expect(selection.anchorOffset).toEqual(2)
      expect(selection.focusOffset).toEqual(0)
    })
  })

  it("selection is not limited if selecting over another node", () => {
    document.documentElement.innerHTML = `<span id="test">Test <span>No.</span> 2</span>`
    const textNode1 = document.getElementById("test")!.firstChild!
    const textNode2 = document.getElementById("test")!.lastChild!

    const selection = document.getSelection()!
    selection.setPosition(textNode1, 1)
    selection.extend(textNode2, 2)

    limitSelectionToOneElement(selection)

    expect(selection.anchorNode).toEqual(textNode1)
    expect(selection.focusNode).toEqual(textNode2)
    expect(selection.anchorOffset).toEqual(1)
    expect(selection.focusOffset).toEqual(2)
  })
})
