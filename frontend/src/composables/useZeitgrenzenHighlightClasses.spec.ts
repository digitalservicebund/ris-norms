import { describe, expect, it } from "vitest"
import {
  getHighlightClasses,
  useZeitgrenzenHighlightClasses,
} from "./useZeitgrenzenHighlightClasses"

describe("getHighlightClasses", () => {
  it("returns highlight classes for the first allowed index", () => {
    expect(getHighlightClasses(0)).toEqual({
      selected: ["bg-highlight-1-selected"],
      default: [
        "bg-highlight-1-default",
        "hover:bg-highlight-1-hover",
        "focus:bg-highlight-1-hover",
      ],
    })
  })

  it("returns classes for the last allowed index", () => {
    expect(getHighlightClasses(9)).toEqual({
      selected: ["bg-highlight-10-selected"],
      default: [
        "bg-highlight-10-default",
        "hover:bg-highlight-10-hover",
        "focus:bg-highlight-10-hover",
      ],
    })
  })

  it("returns the default classes for an out of bound index", () => {
    expect(getHighlightClasses(10)).toEqual({
      selected: ["bg-highlight-default-selected"],
      default: [
        "bg-highlight-default-default",
        "hover:bg-highlight-default-hover",
        "focus:bg-highlight-default-hover",
      ],
    })
  })

  it("returns default classes for a negative index", () => {
    expect(getHighlightClasses(-1)).toEqual({
      selected: ["bg-highlight-default-selected"],
      default: [
        "bg-highlight-default-default",
        "hover:bg-highlight-default-hover",
        "focus:bg-highlight-default-hover",
      ],
    })
  })
})

describe("useZeitgrenzenHighlightClasses", () => {
  it("if there are no highlight elements no classes are returned", () => {
    const classes = useZeitgrenzenHighlightClasses([], () => false)
    expect(classes.value).toEqual({})
  })

  it("get classes in order of temporal group eIds", () => {
    const classes = useZeitgrenzenHighlightClasses(
      [
        { eId: "eid-1", geltungszeit: "gz-2" },
        { eId: "eid-2", geltungszeit: "gz-1" },
      ],
      () => false,
    )

    expect(classes.value["eid-1"]).toEqual([
      "px-2",
      "outline-blue-800",
      "bg-highlight-2-default",
      "hover:bg-highlight-2-hover",
      "focus:bg-highlight-2-hover",
      "outline-dotted",
      "outline",
      "outline-1",
      "hover:outline-2",
    ])
    expect(classes.value["eid-2"]).toEqual([
      "px-2",
      "outline-blue-800",
      "bg-highlight-1-default",
      "hover:bg-highlight-1-hover",
      "focus:bg-highlight-1-hover",
      "outline-dotted",
      "outline",
      "outline-1",
      "hover:outline-2",
    ])
  })

  it("when more than 10 dates exist the later dates get the default color", () => {
    const classes = useZeitgrenzenHighlightClasses(
      [
        { eId: "eid-1", geltungszeit: "gz-1" },
        { eId: "eid-2", geltungszeit: "gz-2" },
        { eId: "eid-3", geltungszeit: "gz-3" },
        { eId: "eid-4", geltungszeit: "gz-4" },
        { eId: "eid-5", geltungszeit: "gz-5" },
        { eId: "eid-6", geltungszeit: "gz-6" },
        { eId: "eid-7", geltungszeit: "gz-7" },
        { eId: "eid-8", geltungszeit: "gz-8" },
        { eId: "eid-9", geltungszeit: "gz-9" },
        { eId: "eid-10", geltungszeit: "gz-10" },
        { eId: "eid-11", geltungszeit: "gz-11" },
        { eId: "eid-12", geltungszeit: "gz-12" },
      ],
      () => false,
    )

    expect(classes.value["eid-11"]).toEqual([
      "px-2",
      "outline-blue-800",
      "bg-highlight-default-default",
      "hover:bg-highlight-default-hover",
      "focus:bg-highlight-default-hover",
      "outline-dotted",
      "outline",
      "outline-1",
      "hover:outline-2",
    ])
    expect(classes.value["eid-12"]).toEqual([
      "px-2",
      "outline-blue-800",
      "bg-highlight-default-default",
      "hover:bg-highlight-default-hover",
      "focus:bg-highlight-default-hover",
      "outline-dotted",
      "outline",
      "outline-1",
      "hover:outline-2",
    ])
  })

  it("missing time boundaries get the last color", () => {
    const classes = useZeitgrenzenHighlightClasses(
      [{ eId: "eid-1", geltungszeit: "gz-1" }, { eId: "eid-2" }],
      () => false,
    )

    expect(classes.value["eid-1"]).toEqual([
      "px-2",
      "outline-blue-800",
      "bg-highlight-1-default",
      "hover:bg-highlight-1-hover",
      "focus:bg-highlight-1-hover",
      "outline-dotted",
      "outline",
      "outline-1",
      "hover:outline-2",
    ])
    expect(classes.value["eid-2"]).toEqual([
      "px-2",
      "outline-blue-800",
      "bg-highlight-default-default",
      "hover:bg-highlight-default-hover",
      "focus:bg-highlight-default-hover",
      "outline-dotted",
      "outline",
      "outline-1",
      "hover:outline-2",
    ])
  })

  it("elements with the same time boundary get the same color", () => {
    const classes = useZeitgrenzenHighlightClasses(
      [
        { eId: "eid-1", geltungszeit: "gz-2" },
        { eId: "eid-2", geltungszeit: "gz-2" },
        { eId: "eid-3", geltungszeit: "gz-1" },
      ],
      () => false,
    )

    expect(classes.value["eid-1"]).toEqual([
      "px-2",
      "outline-blue-800",
      "bg-highlight-2-default",
      "hover:bg-highlight-2-hover",
      "focus:bg-highlight-2-hover",
      "outline-dotted",
      "outline",
      "outline-1",
      "hover:outline-2",
    ])
    expect(classes.value["eid-2"]).toEqual([
      "px-2",
      "outline-blue-800",
      "bg-highlight-2-default",
      "hover:bg-highlight-2-hover",
      "focus:bg-highlight-2-hover",
      "outline-dotted",
      "outline",
      "outline-1",
      "hover:outline-2",
    ])
    expect(classes.value["eid-3"]).toEqual([
      "px-2",
      "outline-blue-800",
      "bg-highlight-1-default",
      "hover:bg-highlight-1-hover",
      "focus:bg-highlight-1-hover",
      "outline-dotted",
      "outline",
      "outline-1",
      "hover:outline-2",
    ])
  })

  it("selected elements get different classes", () => {
    const classes = useZeitgrenzenHighlightClasses(
      [
        { eId: "eid-1", geltungszeit: "gz-2" },
        { eId: "eid-2", geltungszeit: "gz-2" },
        { eId: "eid-3", geltungszeit: "gz-1" },
      ],
      () => true,
    )

    expect(classes.value["eid-1"]).toEqual([
      "px-2",
      "outline-blue-800",
      "bg-highlight-2-selected",
      "outline-2",
      "outline",
    ])
    expect(classes.value["eid-2"]).toEqual([
      "px-2",
      "outline-blue-800",
      "bg-highlight-2-selected",
      "outline-2",
      "outline",
    ])
    expect(classes.value["eid-3"]).toEqual([
      "px-2",
      "outline-blue-800",
      "bg-highlight-1-selected",
      "outline-2",
      "outline",
    ])
  })
})
