/**
 * Details about the environment the application is running in. Can be used
 * to make additional configuration at runtime.
 */
export type Env = {
  /** Name of the environment. */
  name: string
  /** Client ID that should be used when authenticating. */
  authClientId: string
  /** Realm that should be used when authenticating. */
  authRealm: string
  /** URL of the authentication service. */
  authUrl: string
  /** The short-id of the latest git commit */
  commit: string
}
