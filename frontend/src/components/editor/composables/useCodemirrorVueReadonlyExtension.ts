import { MaybeRefOrGetter, toValue, watch } from "vue"
import { EditorView } from "codemirror"
import { Compartment, EditorState, Extension } from "@codemirror/state"

/**
 * Provides a codemirror extension that syncs the read-only state of the editor with the given reference
 *
 * @param editorView a reference to the editor that uses this extension
 * @param readOnly a reference to the value the read-only state of the editor should have
 */
export function useCodemirrorVueReadonlyExtension(
  editorView: MaybeRefOrGetter<EditorView | null>,
  readOnly: MaybeRefOrGetter<boolean>,
): Extension {
  const compartment = new Compartment()

  /**
   * Update the read-only state of the editor whenever it changes
   */
  watch(
    () => toValue(readOnly),
    (readOnly) => {
      toValue(editorView)?.dispatch({
        effects: compartment.reconfigure(EditorState.readOnly.of(readOnly)),
      })
    },
  )

  return compartment.of(EditorState.readOnly.of(toValue(readOnly)))
}
