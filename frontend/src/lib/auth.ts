import type { KeycloakConfig } from "keycloak-js"
import Keycloak from "keycloak-js"

export type AuthenticationConfig = KeycloakConfig

function createAuthentication() {
  let keycloak: Keycloak | undefined = undefined

  /**
   * Initializes the authentication with the specified configuration. Once
   * configured, login page redirects, token refresh, etc. will automatically
   * be managed, and you can use `addAuthorizationHeader` for authenticating
   * backend requests.
   *
   * Calling this function repeatedly will replace any existing instance with
   * the new configuration.
   *
   * @param config Configuration for authentication
   * @throws Error if the authentication fails to initialize, e.g. due to an
   *  invalid configuration.
   */
  async function configure(config: AuthenticationConfig): Promise<void> {
    keycloak = new Keycloak(config)

    try {
      await keycloak.init({
        onLoad: "login-required",
        checkLoginIframe: false,
        pkceMethod: "S256",
        scope: "profile email",
      })
    } catch (e) {
      keycloak = undefined
      throw new Error("Failed to initialize authentication", { cause: e })
    }
  }

  /**
   * Whether the authentication has already been configured successfully using
   * `configure`.
   *
   * @returns True if authentication is currently active
   */
  function isConfigured(): boolean {
    return keycloak?.didInitialize ?? false
  }

  /**
   * Adds an Authorization header with the current token to a set of headers.
   * If authentication hasn't been configured yet, this method will do nothing.
   *
   * @param init If you already have some headers, provide them as `init` and
   *  this method will add the Authorization header to the list.
   * @returns New headers
   */
  function addAuthorizationHeader(init?: HeadersInit): HeadersInit {
    init ??= {}
    if (!keycloak?.token) return init
    return { ...init, Authorization: `Bearer ${keycloak?.token}` }
  }

  /**
   * Returns the name of the currently active user, if they have a name and
   * authentication has been configured. Otherwise `undefined` is returned.
   *
   * @returns Name of the logged in user if known
   */
  function getUsername(): string | undefined {
    return keycloak?.idTokenParsed?.name
  }

  /**
   * Creates a URL which, when visited, ends the current session and redirects
   * the user to the login page. Can be undefined if keycloak hasn't been configured
   * yet.
   */
  function getLogoutLink(): string | undefined {
    return keycloak?.createLogoutUrl()
  }

  let pendingRefresh: Promise<boolean> | null = null

  /**
   * Attempts to refresh the token. If the refresh fails (e.g. because the user
   * has logged out in a different tab), the user is automatically redirected to
   * the login page.
   *
   * Repeated calls to refresh the token will automatically be de-duplicated,
   * so consumers of this method don't need to worry about triggering multiple
   * token refreshes at the same time.
   *
   * @returns A boolean indicating whether a valid token exists after the
   *  attempt. This will be true if the current token can still be used,
   *  or if it has been refreshed successfully. It will be false if keycloak
   *  hasn't been initialized, or the refresh has failed.
   */
  async function tryRefresh(): Promise<boolean> {
    if (!keycloak) return false

    try {
      pendingRefresh ??= keycloak.updateToken()
      await pendingRefresh
      return true
    } catch {
      return false
    } finally {
      pendingRefresh = null
    }
  }

  return () => ({
    addAuthorizationHeader,
    configure,
    getLogoutLink,
    getUsername,
    isConfigured,
    tryRefresh,
  })
}

/**
 * Exposes utilities related to authenticating users and requests in the
 * frontend.
 */
export const useAuthentication = createAuthentication()
