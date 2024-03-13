import { beforeEach, describe, expect, test, vi } from "vitest"
import { provide as vueProvide, inject as vueInject } from "vue"

// Create a mocked provide/inject implementation that works outside of setup()
const provideData = new Map()
const provide: typeof vueProvide = (key, value) => {
  provideData.set(key, value)
}
const inject: typeof vueInject = (key, defaultValue = undefined) => {
  return provideData.get(key) ?? defaultValue
}

describe("useAlerts", () => {
  beforeEach(() => {
    vi.resetModules()
    vi.resetAllMocks()

    provideData.clear()

    vi.doMock("vue", async (importOriginal) => ({
      ...(await importOriginal<typeof import("vue")>()),
      provide,
      inject,
    }))
  })

  test("should have an alert if one is shown", async () => {
    const { useAlerts, useShowAlert } = await import("./useAlerts")

    const { alerts } = useAlerts()
    const showAlert = useShowAlert()

    showAlert({
      variant: "success",
      message: "A success!",
    })

    expect(alerts.value).toHaveLength(1)
    expect([...alerts.value.values()][0].variant).toBe("success")
    expect([...alerts.value.values()][0].message).toBe("A success!")
  })

  test("should remove an alert if one is hidden", async () => {
    const { useAlerts, useShowAlert } = await import("./useAlerts")

    const { alerts, hideAlert } = useAlerts()
    const showAlert = useShowAlert()

    showAlert({
      variant: "success",
      message: "A success!",
    })

    expect(alerts.value).toHaveLength(1)
    hideAlert([...alerts.value.keys()][0])
    expect(alerts.value).toHaveLength(0)
  })

  test("should store multiple alerts", async () => {
    const { useAlerts, useShowAlert } = await import("./useAlerts")

    const { alerts } = useAlerts()
    const showAlert = useShowAlert()

    showAlert({
      variant: "success",
      message: "A success!",
    })
    showAlert({
      variant: "error",
      message: "A failure!",
    })
    showAlert({
      variant: "success",
      message: "A success!",
    })

    expect(alerts.value).toHaveLength(3)
  })

  test("should support multiple independent useAlerts uses (with different keys)", async () => {
    const { useAlerts, useShowAlert } = await import("./useAlerts")

    const { alerts: alerts1 } = useAlerts()
    const showAlert1 = useShowAlert()

    const key = Symbol()
    const { alerts: alerts2 } = useAlerts(key)
    const showAlert2 = useShowAlert(key)

    showAlert1({
      variant: "success",
      message: "A success!",
    })
    showAlert1({
      variant: "error",
      message: "A failure!",
    })
    showAlert2({
      variant: "success",
      message: "A success!",
    })

    expect(alerts1.value).toHaveLength(2)
    expect(alerts2.value).toHaveLength(1)
  })

  test("if multiple calls to useAlerts with the same key are made only the last one is update", async () => {
    const { useAlerts, useShowAlert } = await import("./useAlerts")

    const { alerts: alerts1 } = useAlerts()
    const { alerts: alerts2 } = useAlerts()
    const showAlert = useShowAlert()

    showAlert({
      variant: "success",
      message: "A success!",
    })

    expect(alerts1.value).toHaveLength(0)
    expect(alerts2.value).toHaveLength(1)
  })
})
