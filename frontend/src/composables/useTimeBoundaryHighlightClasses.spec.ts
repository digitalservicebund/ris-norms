import { describe, it, expect, vi, beforeEach } from "vitest"

describe("useTimeBoundaryHighlightClasses", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
  })

  it("if there are no highlight elements no classes are returned", async () => {
    const { useTimeBoundaryHighlightClasses } = await import(
      "./useTimeBoundaryHighlightClasses"
    )

    const classes = useTimeBoundaryHighlightClasses([], () => false)
    expect(classes.value).toEqual({})
  })

  it("get classes in order of temporal group eIds", async () => {
    const { useTimeBoundaryHighlightClasses } = await import(
      "./useTimeBoundaryHighlightClasses"
    )

    const classes = useTimeBoundaryHighlightClasses(
      [
        {
          eId: "eid-1",
          temporalGroupEid: "temporal-group-eid-2",
        },
        {
          eId: "eid-2",
          temporalGroupEid: "temporal-group-eid-1",
        },
      ],
      () => false,
    )

    expect(classes.value["eid-1"]).toEqual([
      "px-2",
      "outline-blue-800",
      `bg-highlight-2-default`,
      `hover:bg-highlight-2-hover`,
      `focus:bg-highlight-2-hover`,
      "outline-dotted",
      "outline",
      "outline-1",
      "hover:outline-2",
    ])
    expect(classes.value["eid-2"]).toEqual([
      "px-2",
      "outline-blue-800",
      `bg-highlight-1-default`,
      `hover:bg-highlight-1-hover`,
      `focus:bg-highlight-1-hover`,
      "outline-dotted",
      "outline",
      "outline-1",
      "hover:outline-2",
    ])
  })

  it("when more than 10 dates exist the later dates get the default color", async () => {
    const { useTimeBoundaryHighlightClasses } = await import(
      "./useTimeBoundaryHighlightClasses"
    )

    const classes = useTimeBoundaryHighlightClasses(
      [
        {
          eId: "eid-1",
          temporalGroupEid: "temporal-group-eid-1",
        },
        {
          eId: "eid-2",
          temporalGroupEid: "temporal-group-eid-2",
        },
        {
          eId: "eid-3",
          temporalGroupEid: "temporal-group-eid-3",
        },
        {
          eId: "eid-4",
          temporalGroupEid: "temporal-group-eid-4",
        },
        {
          eId: "eid-5",
          temporalGroupEid: "temporal-group-eid-5",
        },
        {
          eId: "eid-6",
          temporalGroupEid: "temporal-group-eid-6",
        },
        {
          eId: "eid-7",
          temporalGroupEid: "temporal-group-eid-7",
        },
        {
          eId: "eid-8",
          temporalGroupEid: "temporal-group-eid-8",
        },
        {
          eId: "eid-9",
          temporalGroupEid: "temporal-group-eid-9",
        },
        {
          eId: "eid-10",
          temporalGroupEid: "temporal-group-eid-10",
        },
        {
          eId: "eid-11",
          temporalGroupEid: "temporal-group-eid-11",
        },
        {
          eId: "eid-12",
          temporalGroupEid: "temporal-group-eid-12",
        },
      ],
      () => false,
    )

    expect(classes.value["eid-11"]).toEqual([
      "px-2",
      "outline-blue-800",
      `bg-highlight-default-default`,
      `hover:bg-highlight-default-hover`,
      `focus:bg-highlight-default-hover`,
      "outline-dotted",
      "outline",
      "outline-1",
      "hover:outline-2",
    ])
    expect(classes.value["eid-12"]).toEqual([
      "px-2",
      "outline-blue-800",
      `bg-highlight-default-default`,
      `hover:bg-highlight-default-hover`,
      `focus:bg-highlight-default-hover`,
      "outline-dotted",
      "outline",
      "outline-1",
      "hover:outline-2",
    ])
  })

  it("missing time boundaries get the last color", async () => {
    const { useTimeBoundaryHighlightClasses } = await import(
      "./useTimeBoundaryHighlightClasses"
    )

    const classes = useTimeBoundaryHighlightClasses(
      [
        {
          eId: "eid-1",
          temporalGroupEid: "temporal-group-eid-1",
        },
        {
          eId: "eid-2",
        },
      ],
      () => false,
    )

    expect(classes.value["eid-1"]).toEqual([
      "px-2",
      "outline-blue-800",
      `bg-highlight-1-default`,
      `hover:bg-highlight-1-hover`,
      `focus:bg-highlight-1-hover`,
      "outline-dotted",
      "outline",
      "outline-1",
      "hover:outline-2",
    ])
    expect(classes.value["eid-2"]).toEqual([
      "px-2",
      "outline-blue-800",
      `bg-highlight-default-default`,
      `hover:bg-highlight-default-hover`,
      `focus:bg-highlight-default-hover`,
      "outline-dotted",
      "outline",
      "outline-1",
      "hover:outline-2",
    ])
  })

  it("elements with the same time boundary get the same color", async () => {
    const { useTimeBoundaryHighlightClasses } = await import(
      "./useTimeBoundaryHighlightClasses"
    )

    const classes = useTimeBoundaryHighlightClasses(
      [
        {
          eId: "eid-1",
          temporalGroupEid: "temporal-group-eid-2",
        },
        {
          eId: "eid-2",
          temporalGroupEid: "temporal-group-eid-2",
        },
        {
          eId: "eid-3",
          temporalGroupEid: "temporal-group-eid-1",
        },
      ],
      () => false,
    )

    expect(classes.value["eid-1"]).toEqual([
      "px-2",
      "outline-blue-800",
      `bg-highlight-2-default`,
      `hover:bg-highlight-2-hover`,
      `focus:bg-highlight-2-hover`,
      "outline-dotted",
      "outline",
      "outline-1",
      "hover:outline-2",
    ])
    expect(classes.value["eid-2"]).toEqual([
      "px-2",
      "outline-blue-800",
      `bg-highlight-2-default`,
      `hover:bg-highlight-2-hover`,
      `focus:bg-highlight-2-hover`,
      "outline-dotted",
      "outline",
      "outline-1",
      "hover:outline-2",
    ])
    expect(classes.value["eid-3"]).toEqual([
      "px-2",
      "outline-blue-800",
      `bg-highlight-1-default`,
      `hover:bg-highlight-1-hover`,
      `focus:bg-highlight-1-hover`,
      "outline-dotted",
      "outline",
      "outline-1",
      "hover:outline-2",
    ])
  })

  it("selected elements get different classes", async () => {
    const { useTimeBoundaryHighlightClasses } = await import(
      "./useTimeBoundaryHighlightClasses"
    )

    const classes = useTimeBoundaryHighlightClasses(
      [
        {
          eId: "eid-1",
          temporalGroupEid: "temporal-group-eid-2",
        },
        {
          eId: "eid-2",
          temporalGroupEid: "temporal-group-eid-2",
        },
        {
          eId: "eid-3",
          temporalGroupEid: "temporal-group-eid-1",
        },
      ],
      () => true,
    )

    expect(classes.value["eid-1"]).toEqual([
      "px-2",
      "outline-blue-800",
      `bg-highlight-2-selected`,
      "outline-2",
      "outline",
    ])
    expect(classes.value["eid-2"]).toEqual([
      "px-2",
      "outline-blue-800",
      `bg-highlight-2-selected`,
      "outline-2",
      "outline",
    ])
    expect(classes.value["eid-3"]).toEqual([
      "px-2",
      "outline-blue-800",
      `bg-highlight-1-selected`,
      "outline-2",
      "outline",
    ])
  })
})
