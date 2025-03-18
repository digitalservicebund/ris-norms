import type { APIRequestContext } from "@playwright/test"
import {
  test as base /* eslint-disable-line no-restricted-imports -- We need this here to extend it */,
} from "@playwright/test"

/**
 * Authentication token returned by the OAuth flow.
 */
export type Token = {
  access_token: string
  // Omitted additional properties that are not needed for tests
}

let savedToken: Token

async function getToken(
  username: string,
  password: string,
  request: APIRequestContext,
): Promise<Token> {
  if (!savedToken) {
    const tokenRequest = await request.post(
      "http://localhost:8443/realms/ris/protocol/openid-connect/token",
      {
        form: {
          grant_type: "password",
          client_id: "ris-norms-local",
          client_secret: "ris-norms-local",
          username,
          password,
        },
      },
    )

    if (!tokenRequest.ok) {
      throw new Error("Failed to fetch a token for the E2E tests", {
        cause: tokenRequest,
      })
    }

    savedToken = await tokenRequest.json()
  }

  return savedToken
}

/**
 * Drop-in replacement for Playwright's regular test method that also handles
 * the authorization token. This should be used for all tests in order to
 * guarantee that the token is always up to date.
 */
export const test = base.extend<{
  /**
   * Provides an API context that can be used to make requests just like `page.request`,
   * except that those requests will be authenticated with a previously saved token.
   */
  authenticatedRequest: APIRequestContext

  /**
   * Username and password of the example user that should be used in E2E tests.
   */
  appCredentials: { username: string; password: string }
}>({
  appCredentials: [
    { username: "jane.doe", password: "test" },
    { option: true },
  ],

  authenticatedRequest: async (
    { playwright, request, appCredentials },
    use,
  ) => {
    const token = await getToken(
      appCredentials.username,
      appCredentials.password,
      request,
    )

    const authenticatedRequest = await playwright.request.newContext({
      extraHTTPHeaders: {
        Authorization: `Bearer ${token.access_token}`,
      },
    })

    await use(authenticatedRequest)
  },
})
