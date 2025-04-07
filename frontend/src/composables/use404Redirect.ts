import type { MaybeRefOrGetter } from "vue"
import { toValue, watchEffect } from "vue"
import { useRouter } from "vue-router"

/**
 * Takes a list of errors and redirects to the 404 page as soon as any of them
 * has a status of 404. Undefined values will be ignored.
 *
 * @param errors List of errors
 */
export function use404Redirect(
  errors: MaybeRefOrGetter<Array<{ status?: number } | undefined>>,
) {
  const { replace } = useRouter()

  watchEffect(() => {
    const errorsVal = toValue(errors)
    if (errorsVal.some((e) => e?.status === 404)) replace({ name: "NotFound" })
  })
}
