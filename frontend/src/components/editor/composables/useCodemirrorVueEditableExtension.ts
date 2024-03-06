import { MaybeRefOrGetter, toValue, watch } from "vue"
import { EditorView } from "codemirror"
import { Compartment, Extension } from "@codemirror/state"

/**
 * Provides a codemirror extension that syncs the editable state of the editor with the given reference
 *
 * @param editorView a reference to the editor that uses this extension
 * @param editable a reference to the value the editable state of the editor should have
 */
export function useCodemirrorVueEditableExtension(
  editorView: MaybeRefOrGetter<EditorView | null>,
  editable: MaybeRefOrGetter<boolean>,
): Extension {
  const compartment = new Compartment()

  /**
   * Update the editable state of the editor whenever it changes
   */
  watch(
    () => toValue(editable),
    (editable) => {
      toValue(editorView)?.dispatch({
        effects: compartment.reconfigure(EditorView.editable.of(editable)),
      })
    },
  )

  return compartment.of(EditorView.editable.of(toValue(editable)))
}
