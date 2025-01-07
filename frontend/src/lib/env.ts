export enum RisEnvironment {
  /** Development mode = unbundled app served through dev server or unit tests */
  DEVELOPMENT = "development",
  /** Preview mode = production bundle is served on the local machine */
  LOCAL = "local",
  /** Production mode = production bundle is served from production environment */
  PRODUCTION = "prod",
  /** Staging mode = production bundle is served from staging environment */
  STAGING = "staging",
  /** UAT mode = production bundle is served from UAT environment */
  UAT = "uat",
  /** Any other environment */
  UNKNOWN = "unknown",
}

/**
 * Infers the environment that the app is running in during runtime based on
 * build metadata and the domain the app is served from.
 *
 * @returns The environment the app is running in
 */
export function detectEnv(): RisEnvironment {
  if (import.meta.env.DEV) {
    return RisEnvironment.DEVELOPMENT
  } else if (window.location.hostname.includes("-uat.")) {
    return RisEnvironment.UAT
  } else if (window.location.hostname.includes(".prod.")) {
    return RisEnvironment.PRODUCTION
  } else if (window.location.hostname.includes(".dev.")) {
    return window.location.hostname.split(".").length > 5
      ? RisEnvironment.UNKNOWN
      : RisEnvironment.STAGING
  } else if (window.location.hostname.includes("localhost")) {
    return RisEnvironment.LOCAL
  } else return RisEnvironment.UNKNOWN
}
