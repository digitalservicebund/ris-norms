import type { MaybeRefOrGetter } from "vue"
import { toValue, watchEffect } from "vue"
import { useRouter } from "vue-router"

export function use404Redirect(
  errors: MaybeRefOrGetter<Array<{ status?: number } | undefined>>,
) {
  const { replace } = useRouter()

  watchEffect(() => {
    const errorsVal = toValue(errors)
    if (errorsVal.some((e) => e?.status === 404)) replace({ name: "NotFound" })
  })
}
