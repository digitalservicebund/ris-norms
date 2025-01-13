import { devices, PlaywrightTestConfig } from "@playwright/test"
import dotenv from "dotenv"

dotenv.config({ path: [".env.local", ".env"] })

const config: PlaywrightTestConfig = {
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
      testMatch: /.*global-setup\.ts/,
    },
    {
      name: "setup-firefox",
      use: { ...devices["Desktop Firefox"] },
      testMatch: /.*global-setup\.ts/,
    },
    {
      name: "setup-msedge",
      use: { ...devices["Desktop Edge"], channel: "msedge" },
      testMatch: /.*global-setup\.ts/,
    },
    {
      name: "chromium",
      use: {
        ...devices["Desktop Chrome"],
        storageState: "e2e/setup/.auth/user.json",
      },
      dependencies: ["setup-chromium"],
    },
    {
      name: "firefox",
      use: {
        ...devices["Desktop Firefox"],
        storageState: "e2e/setup/.auth/user.json",
      },
      dependencies: ["setup-firefox"],
    },
    {
      name: "msedge",
      use: {
        ...devices["Desktop Edge"],
        channel: "msedge",
        storageState: "e2e/setup/.auth/user.json",
      },
      timeout: 30000,
      dependencies: ["setup-msedge"],
    },
  ],
}

export default config
