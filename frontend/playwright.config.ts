import { defineConfig, devices } from "@playwright/test"
import dotenv from "dotenv"

dotenv.config({ path: [".env.local", ".env"] })

const config = defineConfig<{
  appCredentials: { username: string; password: string }
}>({
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
      testDir: "./e2e/globalSetup",
    },
    {
      name: "setup-firefox",
      use: { ...devices["Desktop Firefox"] },
      testMatch: /.*.setup.ts$/,
      testDir: "./e2e/globalSetup",
    },
    {
      name: "setup-msedge",
      use: { ...devices["Desktop Edge"], channel: "msedge" },
      testMatch: /.*.setup.ts$/,
      testDir: "./e2e/globalSetup",
    },

    {
      name: "chromium",
      use: {
        ...devices["Desktop Chrome"],
        storageState: "e2e/storage/state.json",
      },
      timeout: 30000,
      dependencies: ["setup-chromium"],
      testDir: "./e2e/application",
    },
    {
      name: "firefox",
      use: {
        ...devices["Desktop Firefox"],
        storageState: "e2e/storage/state.json",
      },
      timeout: 30000,
      dependencies: ["setup-firefox"],
      testDir: "./e2e/application",
    },
    {
      name: "msedge",
      use: {
        ...devices["Desktop Edge"],
        channel: "msedge",
        storageState: "e2e/storage/state.json",
      },
      timeout: 30000,
      dependencies: ["setup-msedge"],
      testDir: "./e2e/application",
    },

    // Accessibility Test Project
    {
      name: "a11y",
      use: {
        ...devices["Desktop Chrome"],
        storageState: "e2e/storage/state.json",
      },
      dependencies: ["setup-chromium"],
      testDir: "./e2e/a11y",
    },
  ],
})

export default config
