import { afterEach, describe, expect, it, vi } from "vitest"
import { detectEnv, RisEnvironment } from "./env"

describe("env", () => {
  afterEach(() => {
    vi.unstubAllEnvs()
    vi.unstubAllGlobals()
  })

  it("detects development", () => {
    vi.stubEnv("DEV", true)

    expect(detectEnv()).toBe(RisEnvironment.DEVELOPMENT)
  })

  it("detects local environment", () => {
    vi.stubEnv("DEV", false)

    vi.stubGlobal("window", {
      ...window,
      location: { hostname: "localhost" },
    })

    expect(detectEnv()).toBe(RisEnvironment.LOCAL)
  })

  it("detects production environment", () => {
    vi.stubEnv("DEV", false)

    vi.stubGlobal("window", {
      ...window,
      location: { hostname: "foo.bar.prod.example.dev" },
    })

    expect(detectEnv()).toBe(RisEnvironment.PRODUCTION)
  })

  it("detects staging environment", () => {
    vi.stubEnv("DEV", false)

    vi.stubGlobal("window", {
      ...window,
      location: { hostname: "foo.bar.dev.example.dev" },
    })

    expect(detectEnv()).toBe(RisEnvironment.STAGING)
  })

  it("detects uat environment", () => {
    vi.stubEnv("DEV", false)

    vi.stubGlobal("window", {
      ...window,
      location: { hostname: "foo.bar-uat.example.dev" },
    })

    expect(detectEnv()).toBe(RisEnvironment.UAT)
  })

  it("returns a fallback environment", () => {
    vi.stubEnv("DEV", false)

    vi.stubGlobal("window", {
      ...window,
      location: { hostname: "foo.bar.baz.dev.example.dev" },
    })

    expect(detectEnv()).toBe(RisEnvironment.UNKNOWN)
  })
})
