import type { NormExpressionEli } from "@/lib/eli/NormExpressionEli"
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

export type EditableZielnormReference = Omit<ZielnormReference, "eId" | "typ">

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

    if (!existingData.length || existingData.length !== eIds.length) {
      return { geltungszeit: "", zielnorm: "" }
    }

    const [first, ...rest] = existingData
    if (!rest.length) return first

    const newReference = rest.reduce((all, current) => {
      const keys = ["geltungszeit", "zielnorm"] as const

      keys.forEach((k) => {
        all[k] = all[k] === current[k] ? all[k] : ""
      })

      return all
    }, first)

    return newReference
  }

  // Update -------------------------------------------------

  let toUpdate: ZielnormReference[] = []

  const {
    data: referencesAfterUpdate,
    execute: execUpdate,
    isFetching: isUpdating,
    error: updateError,
  } = usePostZielnormReferences(() => toUpdate, eli)

  async function update(
    data: MaybeRefOrGetter<EditableZielnormReference>,
    ...eIds: string[]
  ) {
    const dataVal = toValue(data)

    toUpdate = eIds.map<ZielnormReference>((eId) => ({
      ...dataVal,
      typ: "Änderungsvorschrift",
      eId,
    }))

    await execUpdate()
  }

  watch(referencesAfterUpdate, (newVal) => {
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

  watch(referencesAfterDelete, (newVal) => {
    references.value = newVal
  })

  return {
    zielnormReferences: readonly(references),
    zielnormReferencesForEid,
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
