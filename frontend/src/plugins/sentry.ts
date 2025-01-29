import { RisEnvironment } from "@/types/env"
import {
  browserTracingIntegration,
  captureConsoleIntegration,
  init,
} from "@sentry/vue"
import { Plugin } from "vue"
import { Router } from "vue-router"

export const Sentry: Plugin<{
  environmentName: RisEnvironment
  router: Router
}> = {
  install(app, { environmentName: env, router }) {
    const enableSentry = ["production", "uat", "staging"].includes(env)

    console.info(
      `Sentry reporting is ${enableSentry ? "enabled" : "disabled"} in environment "${env}"`,
    )

    if (enableSentry) {
      init({
        app,
        environment: env,
        dsn: "https://bc002a52fd187905497284bed2d771c1@o1248831.ingest.us.sentry.io/4507543284613120",
        initialScope: { tags: { source: "frontend" } },
        integrations: [
          browserTracingIntegration({ router }),
          captureConsoleIntegration(),
        ],
        tracesSampleRate: 0.01,
        attachProps: true,
        logErrors: true,
      })
    }
  },
}
