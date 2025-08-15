import type { DokumentManifestationEli } from "@/lib/eli/DokumentManifestationEli"
import type { NormExpressionEli } from "@/lib/eli/NormExpressionEli"
import { NormWorkEli } from "@/lib/eli/NormWorkEli"
import {
  useDeleteZielnormReferences,
  useGetZielnormReferences,
  usePostZielnormReferences,
} from "@/services/zielnormReferenceService"
import type { ZielnormReference } from "@/types/zielnormReference"
import type { DeepReadonly, MaybeRefOrGetter, Ref } from "vue"
import { readonly, toValue, watch } from "vue"

/**
 * Provides a unified interface to loading and changing Zielnormen references.
 */
export type ZielnormReferencesStore = {
  /**
   * List of all Zielnormen references, or null if it hasn't been loaded from
   * the API yet.
   */
  zielnormReferences: DeepReadonly<Ref<ZielnormReference[] | null>>

  /**
   * Returns a Zielnormen reference that can be used as a backing model for
   * editing it in the UI. It will be populated with data based on the provided
   * eIds. Any properties that have an identical value between all eIds will
   * have that value. If values are different, the property will be empty. If
   * at least one of the eIds doesn't exist, all properties will be empty.
   *
   * @param eIds eIds of the elements to look up
   * @returns Editable Zielnormen reference with data from the eIds
   */
  zielnormReferencesForEid: (...eIds: string[]) => EditableZielnormReference

  /**
   * Returns a Zielnormen reference that can be used as a backing model for
   * editing references related to eingebundene Stammformen in the UI. It will
   * be populated with data based on the provided eId.
   *
   * @param eId eId of the element to look up
   * @param baseEli ELI of the Dokument that contains the eingebundene Stammform.
   *  This will be used for constructing the Zielnorm ELI, which is deterministic
   *  and does not need to be specified manually by the user.
   * @returns Editable Zielnorm reference with data from the eId
   */
  zielnormReferencesEingebundeneStammformForEid: (
    eId: string,
    baseEli: DokumentManifestationEli,
  ) => EditableZielnormReference

  /**
   * Updates all Zielnormen references related to the specified eIds.
   * References for eIds that don't exist yet will be created.
   *
   * @param newVal New value for all references
   * @param eIds eIds of the elements for which the references should be updated
   */
  updateZielnormReferences: (
    newVal: EditableZielnormReference,
    ...eIds: string[]
  ) => Promise<void>

  /**
   * Deletes all Zielnormen references related to the specified eIds.
   *
   * @param eIds List of eIds for which the references should be deleted.
   */
  deleteZielnormReferences: (...eIds: string[]) => Promise<void>

  /**
   * True if while loading data.
   */
  isLoadingZielnormReferences: Ref<boolean>

  /**
   * True if while updating data.
   */
  isUpdatingZielnormReferences: Ref<boolean>

  /**
   * True if while deleting data.
   */
  isDeletingZielnormReferences: Ref<boolean>

  /**
   * Any errors returned from loading the data.
   */
  loadZielnormReferencesError: Ref<any> // eslint-disable-line @typescript-eslint/no-explicit-any -- Errors are any

  /**
   * Any errors returned from updating the data.
   */
  updateZielnormReferencesError: Ref<any> // eslint-disable-line @typescript-eslint/no-explicit-any -- Errors are any

  /**
   * Any errors returned from deleting data.
   */
  deleteZielnormReferencesError: Ref<any> // eslint-disable-line @typescript-eslint/no-explicit-any -- Errors are any
}

export type EditableZielnormReference = Omit<
  ZielnormReference,
  "eId" | "typ" | "isNewWork"
> & {
  /** Will be set automatically by the service */
  readonly isNewWork?: boolean
}

/**
 * Used in place of an actual value when editing multiple references. A value
 * will be indeterminate if the selected references have different values. If
 * the value is not touched in the UI, i.e. still indeterminate when being sent
 * to the API, it will not be changed.
 */
export const INDETERMINATE_VALUE = "__indeterminate__"

/**
 * Provides a unified interface to loading and changing Zielnormen references.
 *
 * @returns Functionality for loading and changing Zielnormen references
 */
export function useZielnormReferences(
  eli: MaybeRefOrGetter<NormExpressionEli>,
): ZielnormReferencesStore {
  // Data ---------------------------------------------------

  const { data: references, isFetching, error } = useGetZielnormReferences(eli)

  function zielnormReferencesForEid(
    ...eIds: string[]
  ): EditableZielnormReference {
    const existingData = (references.value ?? [])
      .filter((i) => eIds.includes(i.eId))
      .map<EditableZielnormReference>((i) => ({
        geltungszeit: i.geltungszeit,
        zielnorm: i.zielnorm,
      }))

    // Editing a single element for which we don't have data yet = return
    // completely empty object
    if (!existingData.length) {
      return {
        geltungszeit: "",
        zielnorm: "",
      }
    }
    // Editing a mix of existing and new elements = assume the data will
    // be different
    else if (existingData.length !== eIds.length) {
      return {
        geltungszeit: INDETERMINATE_VALUE,
        zielnorm: INDETERMINATE_VALUE,
      }
    }

    const [first, ...rest] = existingData
    if (!rest.length) return first

    const newReference = rest.reduce((all, current) => {
      const keys = ["geltungszeit", "zielnorm"] as const

      keys.forEach((k) => {
        all[k] = all[k] === current[k] ? all[k] : INDETERMINATE_VALUE
      })

      return all
    }, first)

    return newReference
  }

  function getNewWorkEli(baseEli: DokumentManifestationEli): NormWorkEli {
    // The ELI of a new work is the counter of the subtype minus 1, see LDML.de
    // 1.8.2 section 6.3.2
    const match = /-(\d+)$/.exec(baseEli.subtype)
    let naturalIdentifier = baseEli.naturalIdentifier
    if (match) {
      const subtypeCounter = Number.parseInt(match[1]) - 1
      if (subtypeCounter > 0) naturalIdentifier += `-${subtypeCounter}`
    }

    return new NormWorkEli(baseEli.agent, baseEli.year, naturalIdentifier)
  }

  function zielnormReferencesEingebundeneStammformForEid(
    eId: string,
    baseEli: DokumentManifestationEli,
  ): EditableZielnormReference {
    const existingData = (references.value ?? []).find((i) => i.eId === eId)

    return {
      isNewWork: true,
      zielnorm: existingData?.zielnorm ?? getNewWorkEli(baseEli).toString(),
      geltungszeit: existingData?.geltungszeit ?? "",
    }
  }

  // Update -------------------------------------------------

  let toUpdate: ZielnormReference[] = []

  const {
    data: referencesAfterUpdate,
    execute: execUpdate,
    isFetching: isUpdating,
    error: updateError,
  } = usePostZielnormReferences(() => toUpdate, eli)

  function cleanIndeterminate(
    data: EditableZielnormReference,
  ): EditableZielnormReference {
    const cleaned = Object.entries(data).map(([k, v]) => [
      k,
      v === INDETERMINATE_VALUE ? "" : v,
    ])

    return Object.fromEntries(cleaned)
  }

  function lastSavedOrNewValue(
    lastSavedValue: string,
    newValue: string,
  ): string {
    if (newValue === INDETERMINATE_VALUE) return lastSavedValue
    else return newValue
  }

  function restoreLastSavedValue(
    data: EditableZielnormReference,
    eId: string,
  ): EditableZielnormReference {
    const saved = references.value?.find((i) => i.eId === eId)
    if (!saved) return cleanIndeterminate(data)

    return {
      geltungszeit: lastSavedOrNewValue(saved.geltungszeit, data.geltungszeit),
      zielnorm: lastSavedOrNewValue(saved.zielnorm, data.zielnorm),
    }
  }

  async function update(
    data: MaybeRefOrGetter<EditableZielnormReference>,
    ...eIds: string[]
  ) {
    const dataVal = toValue(data)

    toUpdate = eIds.map<ZielnormReference>((eId) => ({
      ...restoreLastSavedValue(dataVal, eId),
      isNewWork: dataVal.isNewWork ?? false,
      typ: "Änderungsvorschrift",
      eId,
    }))

    await execUpdate()
  }

  watch(referencesAfterUpdate, (newVal, oldVal) => {
    // null in this case means the request failed, keep old data in that case
    if (oldVal && newVal === null) return
    references.value = newVal
  })

  // Remove -------------------------------------------------

  const toRemove = new Set<string>()

  const {
    data: referencesAfterDelete,
    execute: execDelete,
    isFetching: isDeleting,
    error: deleteError,
  } = useDeleteZielnormReferences(() => Array.from(toRemove), eli)

  async function remove(...eids: string[]) {
    eids.forEach((i) => toRemove.add(i))
    await execDelete()
  }

  watch(referencesAfterDelete, (newVal, oldVal) => {
    // null in this case means the request failed, keep old data in that case
    if (oldVal && newVal === null) return
    references.value = newVal
  })

  return {
    zielnormReferences: readonly(references),
    zielnormReferencesForEid,
    zielnormReferencesEingebundeneStammformForEid,
    updateZielnormReferences: update,
    deleteZielnormReferences: remove,
    isLoadingZielnormReferences: isFetching,
    isUpdatingZielnormReferences: isUpdating,
    isDeletingZielnormReferences: isDeleting,
    loadZielnormReferencesError: error,
    updateZielnormReferencesError: updateError,
    deleteZielnormReferencesError: deleteError,
  }
}
