import { defineConfig, devices } from "@playwright/test"
import dotenv from "dotenv"

dotenv.config({ path: [".env.local", ".env"] })

const config = defineConfig<{
  appCredentials: { username: string; password: string }
}>({
  testDir: "./e2e",
  timeout: 10000,
  retries: process.env.CI === "true" ? 1 : 0,
  workers: 1,
  reporter:
    process.env.CI === "true"
      ? [["dot"], ["blob", { outputFile: "./blob-report/test-report.zip" }]]
      : "list",
  use: {
    viewport: { width: 1440, height: 1080 },
    baseURL: process.env.E2E_BASE_URL,
    screenshot: { mode: "only-on-failure", fullPage: true },
    timezoneId: "Europe/Berlin",
    trace: "retain-on-first-failure",
  },
  projects: [
    {
      name: "setup-chromium",
      use: { ...devices["Desktop Chrome"] },
      testMatch: /.*.setup.ts$/,
    },
    {
      name: "setup-firefox",
      use: { ...devices["Desktop Firefox"] },
      testMatch: /.*.setup.ts$/,
    },
    {
      name: "setup-msedge",
      use: { ...devices["Desktop Edge"], channel: "msedge" },
      testMatch: /.*.setup.ts$/,
    },
    {
      name: "chromium",
      use: {
        ...devices["Desktop Chrome"],
        storageState: "e2e/storage/state.json",
      },
      timeout: 30000,
      testIgnore: "e2e/loginAndLogout.spec.ts",
      dependencies: ["setup-chromium"],
    },
    {
      name: "firefox",
      use: {
        ...devices["Desktop Firefox"],
        storageState: "e2e/storage/state.json",
      },
      timeout: 30000,
      testIgnore: "e2e/loginAndLogout.spec.ts",
      dependencies: ["setup-firefox"],
    },
    {
      name: "msedge",
      use: {
        ...devices["Desktop Edge"],
        channel: "msedge",
        storageState: "e2e/storage/state.json",
      },
      timeout: 30000,
      testIgnore: "e2e/loginAndLogout.spec.ts",
      dependencies: ["setup-msedge"],
    },

    // Accessibility Test Project
    {
      name: "a11y",
      testDir: "./a11y",
      use: {
        ...devices["Desktop Chrome"],
        storageState: "e2e/storage/state.json",
      },
      dependencies: ["setup-chromium"],
    },

    // Login-logout test projects
    {
      name: "login-logout-test-chromium",
      use: { ...devices["Desktop Chrome"] },
      testMatch: "e2e/loginAndLogout.spec.ts",
    },
    {
      name: "login-logout-test-firefox",
      use: { ...devices["Desktop Firefox"] },
      testMatch: "e2e/loginAndLogout.spec.ts",
    },
    {
      name: "login-logout-test-msedge",
      use: { ...devices["Desktop Edge"], channel: "msedge" },
      timeout: 30000,
      testMatch: "e2e/loginAndLogout.spec.ts",
    },
  ],
})

export default config
