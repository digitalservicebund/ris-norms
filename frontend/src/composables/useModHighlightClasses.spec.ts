import { describe, test, expect, vi, beforeEach } from "vitest"

describe("useModEidSelection", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()
  })

  test("if there are no mods no classes are returned", async () => {
    vi.doMock("@/services/ldmldeModService", () => ({
      getModEIds: vi.fn().mockReturnValue([]),
      getTimeBoundaryDate: vi.fn(),
    }))

    const { useModHighlightClasses } = await import("./useModHighlightClasses")

    const classes = useModHighlightClasses(new Document(), () => false)
    expect(classes.value).toEqual({})
  })

  test("mods get classes in order of dates (oldest gets the first color)", async () => {
    vi.doMock("@/services/ldmldeModService", () => ({
      getModEIds: vi.fn().mockReturnValue(["eid-1", "eid-2"]),
      getTimeBoundaryDate: vi
        .fn()
        .mockReturnValueOnce({
          date: "2023-01-01",
          temporalGroupEid: "temporal-group-eid-1",
        })
        .mockReturnValueOnce({
          date: "2021-01-01",
          temporalGroupEid: "temporal-group-eid-2",
        }),
    }))

    const { useModHighlightClasses } = await import("./useModHighlightClasses")

    const classes = useModHighlightClasses(new Document(), () => false)

    expect(classes.value["eid-1"]).toEqual([
      "border",
      "px-2",
      `bg-highlight-mod-2-default`,
      `hover:bg-highlight-mod-2-hover`,
      `focus:bg-highlight-mod-2-hover`,
      "border-dotted",
      "border-gray-900",
    ])
    expect(classes.value["eid-2"]).toEqual([
      "border",
      "px-2",
      `bg-highlight-mod-1-default`,
      `hover:bg-highlight-mod-1-hover`,
      `focus:bg-highlight-mod-1-hover`,
      "border-dotted",
      "border-gray-900",
    ])
  })

  test("when more than 10 dates exist the later dates get the default color", async () => {
    vi.doMock("@/services/ldmldeModService", () => ({
      getModEIds: vi
        .fn()
        .mockReturnValue([
          "eid-1",
          "eid-2",
          "eid-3",
          "eid-4",
          "eid-5",
          "eid-6",
          "eid-7",
          "eid-8",
          "eid-9",
          "eid-10",
          "eid-11",
          "eid-12",
        ]),
      getTimeBoundaryDate: vi
        .fn()
        .mockReturnValueOnce({
          date: "2021-01-01",
          temporalGroupEid: "temporal-group-eid-1",
        })
        .mockReturnValueOnce({
          date: "2021-01-02",
          temporalGroupEid: "temporal-group-eid-2",
        })
        .mockReturnValueOnce({
          date: "2021-01-03",
          temporalGroupEid: "temporal-group-eid-3",
        })
        .mockReturnValueOnce({
          date: "2021-01-04",
          temporalGroupEid: "temporal-group-eid-4",
        })
        .mockReturnValueOnce({
          date: "2021-01-05",
          temporalGroupEid: "temporal-group-eid-5",
        })
        .mockReturnValueOnce({
          date: "2021-01-06",
          temporalGroupEid: "temporal-group-eid-6",
        })
        .mockReturnValueOnce({
          date: "2021-01-07",
          temporalGroupEid: "temporal-group-eid-7",
        })
        .mockReturnValueOnce({
          date: "2021-01-08",
          temporalGroupEid: "temporal-group-eid-8",
        })
        .mockReturnValueOnce({
          date: "2021-01-09",
          temporalGroupEid: "temporal-group-eid-9",
        })
        .mockReturnValueOnce({
          date: "2021-01-10",
          temporalGroupEid: "temporal-group-eid-10",
        })
        .mockReturnValueOnce({
          date: "2021-01-11",
          temporalGroupEid: "temporal-group-eid-11",
        })
        .mockReturnValueOnce({
          date: "2021-01-12",
          temporalGroupEid: "temporal-group-eid-12",
        }),
    }))

    const { useModHighlightClasses } = await import("./useModHighlightClasses")

    const classes = useModHighlightClasses(new Document(), () => false)

    expect(classes.value["eid-11"]).toEqual([
      "border",
      "px-2",
      `bg-highlight-mod-default-default`,
      `hover:bg-highlight-mod-default-hover`,
      `focus:bg-highlight-mod-default-hover`,
      "border-dotted",
      "border-gray-900",
    ])
    expect(classes.value["eid-12"]).toEqual([
      "border",
      "px-2",
      `bg-highlight-mod-default-default`,
      `hover:bg-highlight-mod-default-hover`,
      `focus:bg-highlight-mod-default-hover`,
      "border-dotted",
      "border-gray-900",
    ])
  })

  test("missing time boundaries get the last color", async () => {
    vi.doMock("@/services/ldmldeModService", () => ({
      getModEIds: vi.fn().mockReturnValue(["eid-1", "eid-2"]),
      getTimeBoundaryDate: vi
        .fn()
        .mockReturnValueOnce({
          date: "2023-01-01",
          temporalGroupEid: "temporal-group-eid-1",
        })
        .mockReturnValueOnce(null),
    }))

    const { useModHighlightClasses } = await import("./useModHighlightClasses")

    const classes = useModHighlightClasses(new Document(), () => false)

    expect(classes.value["eid-1"]).toEqual([
      "border",
      "px-2",
      `bg-highlight-mod-1-default`,
      `hover:bg-highlight-mod-1-hover`,
      `focus:bg-highlight-mod-1-hover`,
      "border-dotted",
      "border-gray-900",
    ])
    expect(classes.value["eid-2"]).toEqual([
      "border",
      "px-2",
      `bg-highlight-mod-2-default`,
      `hover:bg-highlight-mod-2-hover`,
      `focus:bg-highlight-mod-2-hover`,
      "border-dotted",
      "border-gray-900",
    ])
  })

  test("time boundaries with the same date but different temporal groups get different colors", async () => {
    vi.doMock("@/services/ldmldeModService", () => ({
      getModEIds: vi.fn().mockReturnValue(["eid-1", "eid-2", "eid-3"]),
      getTimeBoundaryDate: vi
        .fn()
        .mockReturnValueOnce({
          date: "2023-01-01",
          temporalGroupEid: "temporal-group-eid-1",
        })
        .mockReturnValueOnce({
          date: "2023-01-01",
          temporalGroupEid: "temporal-group-eid-2",
        })
        .mockReturnValueOnce({
          date: "2022-01-01",
          temporalGroupEid: "temporal-group-eid-3",
        }),
    }))

    const { useModHighlightClasses } = await import("./useModHighlightClasses")

    const classes = useModHighlightClasses(new Document(), () => false)

    expect(classes.value["eid-1"]).toEqual([
      "border",
      "px-2",
      `bg-highlight-mod-2-default`,
      `hover:bg-highlight-mod-2-hover`,
      `focus:bg-highlight-mod-2-hover`,
      "border-dotted",
      "border-gray-900",
    ])
    expect(classes.value["eid-2"]).toEqual([
      "border",
      "px-2",
      `bg-highlight-mod-3-default`,
      `hover:bg-highlight-mod-3-hover`,
      `focus:bg-highlight-mod-3-hover`,
      "border-dotted",
      "border-gray-900",
    ])
    expect(classes.value["eid-3"]).toEqual([
      "border",
      "px-2",
      `bg-highlight-mod-1-default`,
      `hover:bg-highlight-mod-1-hover`,
      `focus:bg-highlight-mod-1-hover`,
      "border-dotted",
      "border-gray-900",
    ])
  })

  test("mods with the same time boundary get the same color", async () => {
    vi.doMock("@/services/ldmldeModService", () => ({
      getModEIds: vi.fn().mockReturnValue(["eid-1", "eid-2", "eid-3"]),
      getTimeBoundaryDate: vi
        .fn()
        .mockReturnValueOnce({
          date: "2023-01-01",
          temporalGroupEid: "temporal-group-eid-1",
        })
        .mockReturnValueOnce({
          date: "2023-01-01",
          temporalGroupEid: "temporal-group-eid-1",
        })
        .mockReturnValueOnce({
          date: "2022-01-01",
          temporalGroupEid: "temporal-group-eid-2",
        }),
    }))

    const { useModHighlightClasses } = await import("./useModHighlightClasses")

    const classes = useModHighlightClasses(new Document(), () => false)

    expect(classes.value["eid-1"]).toEqual([
      "border",
      "px-2",
      `bg-highlight-mod-2-default`,
      `hover:bg-highlight-mod-2-hover`,
      `focus:bg-highlight-mod-2-hover`,
      "border-dotted",
      "border-gray-900",
    ])
    expect(classes.value["eid-2"]).toEqual([
      "border",
      "px-2",
      `bg-highlight-mod-2-default`,
      `hover:bg-highlight-mod-2-hover`,
      `focus:bg-highlight-mod-2-hover`,
      "border-dotted",
      "border-gray-900",
    ])
    expect(classes.value["eid-3"]).toEqual([
      "border",
      "px-2",
      `bg-highlight-mod-1-default`,
      `hover:bg-highlight-mod-1-hover`,
      `focus:bg-highlight-mod-1-hover`,
      "border-dotted",
      "border-gray-900",
    ])
  })

  test("selected mods get different classes", async () => {
    vi.doMock("@/services/ldmldeModService", () => ({
      getModEIds: vi.fn().mockReturnValue(["eid-1", "eid-2", "eid-3"]),
      getTimeBoundaryDate: vi
        .fn()
        .mockReturnValueOnce({
          date: "2023-01-01",
          temporalGroupEid: "temporal-group-eid-1",
        })
        .mockReturnValueOnce({
          date: "2023-01-01",
          temporalGroupEid: "temporal-group-eid-1",
        })
        .mockReturnValueOnce({
          date: "2022-01-01",
          temporalGroupEid: "temporal-group-eid-2",
        }),
    }))

    const { useModHighlightClasses } = await import("./useModHighlightClasses")

    const classes = useModHighlightClasses(new Document(), () => true)

    expect(classes.value["eid-1"]).toEqual([
      "border",
      "px-2",
      `bg-highlight-mod-2-selected`,
      "border-solid",
      "border-highlight-mod-2-selected",
    ])
    expect(classes.value["eid-2"]).toEqual([
      "border",
      "px-2",
      `bg-highlight-mod-2-selected`,
      "border-solid",
      "border-highlight-mod-2-selected",
    ])
    expect(classes.value["eid-3"]).toEqual([
      "border",
      "px-2",
      `bg-highlight-mod-1-selected`,
      "border-solid",
      "border-highlight-mod-1-selected",
    ])
  })
})
