import { MaybeRefOrGetter, ref, watch } from "vue"
import { useGetNormXml, usePutNormXml } from "@/services/normService"
import { UseFetchReturn } from "@vueuse/core"

/**
 * Get the XML of a norm.
 *
 * @param eli a reference to the eli for which the norm xml will be returned.
 *  Changing the value of the reference will load the data for the new eli.
 */
export function useNormXml(
  eli: MaybeRefOrGetter<string | undefined>,
): UseFetchReturn<string>
/**
 * Get the XML of a norm. This also supports updating the xml.
 * The data is automatically also updated with the response from the update.
 *
 * @param eli a reference to the eli for which the norm xml will be returned.
 *  Changing the value of the reference will load the data for the new eli.
 * @param newXml a reference to the data that should be saved on an update.
 */
export function useNormXml(
  eli: MaybeRefOrGetter<string | undefined>,
  newXml: MaybeRefOrGetter<string | undefined | null>,
): UseFetchReturn<string> & {
  update: UseFetchReturn<string>
}
export function useNormXml(
  eli: MaybeRefOrGetter<string | undefined>,
  newXml?: MaybeRefOrGetter<string | undefined | null>,
): UseFetchReturn<string> & {
  update?: UseFetchReturn<string>
} {
  if (!newXml) {
    return useGetNormXml(eli)
  }

  // We want to also update the data with the data returned from the PUT-request.
  const data = ref<string | null>(null)
  const getNormXml: UseFetchReturn<string> = useGetNormXml(eli)
  watch(getNormXml.data, () => {
    data.value = getNormXml.data.value
  })

  const putNormXml = usePutNormXml(newXml, eli, undefined, { refetch: false })
  watch(putNormXml.data, () => {
    data.value = putNormXml.data.value
  })

  return { ...getNormXml, data, update: putNormXml }
}
