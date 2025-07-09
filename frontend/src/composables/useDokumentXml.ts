import type { MaybeRefOrGetter } from "vue"
import { ref, watch } from "vue"
import {
  useGetDokumentXml,
  usePutDokumentXml,
} from "@/services/dokumentService"
import type { UseFetchReturn } from "@vueuse/core"
import type { DokumentExpressionEli } from "@/lib/eli/DokumentExpressionEli"

/**
 * Get the XML of a norm.
 *
 * @param eli a reference to the eli for which the norm xml will be returned.
 *  Changing the value of the reference will load the data for the new eli.
 */
export function useDokumentXml(
  eli: MaybeRefOrGetter<DokumentExpressionEli | undefined>,
): UseFetchReturn<string>
/**
 * Get the XML of a norm. This also supports updating the xml.
 * The data is automatically also updated with the response from the update.
 *
 * @param eli a reference to the eli for which the norm xml will be returned.
 *  Changing the value of the reference will load the data for the new eli.
 * @param newXml a reference to the data that should be saved on an update.
 */
export function useDokumentXml(
  eli: MaybeRefOrGetter<DokumentExpressionEli | undefined>,
  newXml: MaybeRefOrGetter<string | undefined | null>,
): UseFetchReturn<string> & {
  update: UseFetchReturn<string>
}
export function useDokumentXml(
  eli: MaybeRefOrGetter<DokumentExpressionEli | undefined>,
  newXml?: MaybeRefOrGetter<string | undefined | null>,
): UseFetchReturn<string> & {
  update?: UseFetchReturn<string>
} {
  if (!newXml) {
    return useGetDokumentXml(eli)
  }

  // We want to also update the data with the data returned from the PUT-request.
  const data = ref<string | null>(null)
  const getDokumentXml: UseFetchReturn<string> = useGetDokumentXml(eli)
  watch(getDokumentXml.data, () => {
    data.value = getDokumentXml.data.value
  })

  const putDokumentXml = usePutDokumentXml(newXml, eli)
  watch(putDokumentXml.data, () => {
    data.value = putDokumentXml.data.value
  })

  return { ...getDokumentXml, data, update: putDokumentXml }
}
