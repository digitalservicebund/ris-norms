import { computed, ComputedRef, ShallowReactive, shallowReactive } from "vue"

/**
 * Selection of multiple elements
 */
export function useMultiSelection<T>(): {
  /**
   * The currently selected elements
   */
  values: ComputedRef<T[]>
  /**
   * Toggle the selection of the given element. If it is selected it will be deselected otherwise it will be selected.
   * @param value the value to toggle
   */
  toggle: (value: T) => void
  /**
   * Remove all elements from the selection.
   */
  clear: () => void
  /**
   * Select the given element.
   * @param value the value to select
   */
  select: (value: T) => void
  /**
   * Select all given elements.
   * @param values an array of elements to select
   */
  selectAll: (values: T[]) => void
  /**
   * Deselect all given element.
   * @param values an array of elements to deselect
   */
  deselectAll: (values: T[]) => void
} {
  const selectedValues: ShallowReactive<Set<T>> = shallowReactive(new Set<T>())

  function toggle(value: T) {
    if (selectedValues.has(value)) {
      selectedValues.delete(value)
    } else {
      selectedValues.add(value)
    }
  }

  function clear() {
    selectedValues.clear()
  }

  function select(value: T) {
    selectedValues.add(value)
  }

  function selectAll(values: T[]) {
    values.forEach((value) => selectedValues.add(value))
  }

  function deselectAll(values: T[]) {
    values.forEach((value) => selectedValues.delete(value))
  }

  return {
    values: computed(() => [...selectedValues]),
    toggle,
    select,
    selectAll,
    deselectAll,
    clear,
  }
}
