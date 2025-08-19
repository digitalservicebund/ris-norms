import {
  browserTracingIntegration,
  captureConsoleIntegration,
  init,
} from "@sentry/vue"
import type { Plugin } from "vue"
import type { Router } from "vue-router"

export const Sentry: Plugin<{ environment: string; router: Router }> = {
  install(app, { environment, router }) {
    const enableSentry = ["production", "uat", "staging"].includes(environment)

    console.info(
      `Sentry reporting is ${enableSentry ? "enabled" : "disabled"} in environment "${environment}"`,
    )

    if (enableSentry) {
      init({
        app,
        environment,
        dsn: "https://bc002a52fd187905497284bed2d771c1@o1248831.ingest.us.sentry.io/4507543284613120",
        initialScope: { tags: { source: "frontend" } },
        integrations: [
          browserTracingIntegration({ router }),
          captureConsoleIntegration(),
        ],
        tracesSampleRate: 0,
        attachProps: true,
      })
    }
  },
}
