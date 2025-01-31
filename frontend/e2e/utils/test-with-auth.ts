import { test as base } from "@playwright/test"
import { readFile, writeFile } from "node:fs/promises"
import { fileURLToPath, URL } from "node:url"

/**
 * Authentication token returned by the OAuth flow.
 */
export type Token = {
  access_token: string
  // Omitted additional properties that are not needed for tests
}

export type AuthorizationHeader = { Authorization: string }

export const test = base.extend<{
  /** Provides the shared authentication token to tests. */
  token: Token

  /**
   * Provides an authorization header containing the shared authentication token
   * to tests.
   */
  authorizationHeader: AuthorizationHeader

  /**
   * Hooks into the requests made by the page. If one of them contains a new token,
   * the token will automatically be saved to a shared location, so it can be used
   * by other tests.
   *
   * This behavior is managed by Playwright automatically. The method should not be
   * called manually.
   */
  updateToken: void
}>({
  token: async ({}, use) => {
    const token = await restoreToken()
    await use(token)
  },

  authorizationHeader: async ({}, use) => {
    const token = await restoreToken()
    await use({ Authorization: `Bearer ${token.access_token}` })
  },

  updateToken: [
    async ({ page }, use) => {
      await page.route(/token$/, async (route) => {
        const response = await page.request.fetch(route.request())
        if (response.ok()) {
          saveToken(await response.json())
          console.log("Sucessfully intercepted and saved access token")
        }
        await route.fulfill({ response })
      })

      await use()
    },
    { auto: true },
  ],
})

const storagePath = fileURLToPath(
  new URL("../setup/token.json", import.meta.url),
)

/**
 * Takes a token and saves it to a shared location that can be used across tests.
 *
 * @param token Token to save
 */
export function saveToken(token: Token): Promise<void> {
  return writeFile(storagePath, JSON.stringify(token, undefined, 2), {
    encoding: "utf-8",
  })
}

/**
 * Loads a previously saved token from the shared location. This will throw if
 * no token has been saved.
 *
 * @returns Saved token
 */
export async function restoreToken(): Promise<Token> {
  const raw = await readFile(storagePath, { encoding: "utf-8" })
  return JSON.parse(raw)
}
