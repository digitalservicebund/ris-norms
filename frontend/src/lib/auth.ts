import Keycloak, { KeycloakConfig } from "keycloak-js"

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

  return () => ({
    addAuthorizationHeader,
    configure,
    isConfigured,
  })
}

/**
 * Exposes utilities related to authenticating users and requests in the
 * frontend.
 */
export const useAuthentication = createAuthentication()
