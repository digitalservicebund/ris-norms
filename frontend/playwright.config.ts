import { devices, PlaywrightTestConfig } from "@playwright/test"

const config: PlaywrightTestConfig = {
  testDir: "./e2e",
  timeout: 10000,
  retries: process.env.CI === "true" ? 1 : 0,
  use: {
    viewport: { width: 1280, height: 720 },
    baseURL: process.env.E2E_BASE_URL,
    httpCredentials: {
      username: process.env.E2E_TEST_BASIC_AUTH_USER,
      password: process.env.E2E_TEST_BASIC_AUTH_PASSWORD,
    },
    screenshot: { mode: "only-on-failure", fullPage: true },
    timezoneId: "Europe/Berlin",
  },
  projects: [
    {
      name: "chromium",
      use: { ...devices["Desktop Chrome"], channel: "chrome" },
    },
    {
      name: "firefox",
      use: { ...devices["Desktop Firefox"] },
    },
    {
      name: "webkit",
      use: { ...devices["Desktop Safari"] },
    },
  ],
}

export default config
