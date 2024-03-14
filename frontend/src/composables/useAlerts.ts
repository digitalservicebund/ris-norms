import { inject, InjectionKey, provide, readonly, Ref, ref } from "vue"

/**
 * An alert message
 */
export type Alert = {
  /**
   * The variant of the alert, determines the visual appearance
   */
  variant: "success" | "error"
  /**
   * The message of the alert
   */
  message: string
}

export type ShowAlertInjectionKey = InjectionKey<(alert: Alert) => unknown>

const showAlertInjectionKey: ShowAlertInjectionKey = Symbol(
  "Injection symbol for the function to show an alert",
)

/**
 * Provides access to alerts created by {@link useShowAlert}.
 *
 * If this composable is used multiple times only the latest created composable is receiving the alerts. Unless different
 * {@link ShowAlertInjectionKey} are used.
 *
 * @param injectionKey an optional {@link ShowAlertInjectionKey} to use for providing the showAlert function. When
 *   providing this key all {@link useShowAlert} composables that should send there alerts to this composable need to
 *   use the same key. By default, the global alerts are used.
 */
export function useAlerts(
  injectionKey: ShowAlertInjectionKey = showAlertInjectionKey,
): {
  /**
   * The currently visible alerts
   */
  alerts: Readonly<Ref<ReadonlyMap<symbol, Alert>>>
  /**
   * Hide a specific alert
   *
   * @param key the key of the alert from the alerts Map
   */
  hideAlert: (key: symbol) => unknown
} {
  const alerts = ref<Map<symbol, Alert>>(new Map())

  function hideAlert(key: symbol) {
    alerts.value.delete(key)
  }

  provide(injectionKey, (alert) => alerts.value.set(Symbol(), alert))

  return {
    alerts: readonly(alerts),
    hideAlert,
  }
}

/**
 * Provides a function to show alerts.
 *
 * The alerts are handled by the latest created {@link useAlerts} composable
 *
 * @param injectionKey To specify which {@link useAlerts} composable should handle the alerts created by the function a
 *   {@link ShowAlertInjectionKey} can be provided. This must be the same {@link ShowAlertInjectionKey} that was used when
 *   using the {@link useAlerts} composable. By default, the global alerts showAlerts function is provided.
 */
export function useShowAlert(
  injectionKey: ShowAlertInjectionKey = showAlertInjectionKey,
) {
  return inject(injectionKey, () => {
    console.warn(
      "Trying to show alert without alerts consumer for injectionKey: ",
      injectionKey,
    )
  })
}
