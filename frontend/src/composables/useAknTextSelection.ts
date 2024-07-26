import { useEventListener } from "@vueuse/core"
import { MaybeRefOrGetter, ref, toValue } from "vue"

/**
 * Determines if the given node is nested (in any depth) under the given parent element.
 *
 * @param node the node to check
 * @param parentElement the parent element that might exist
 */
export function hasElementAsParentElement(
  node: Node,
  parentElement: Node,
): boolean {
  let currentNode = node
  while (currentNode.parentElement) {
    if (currentNode.parentElement === parentElement) {
      return true
    }
    currentNode = currentNode.parentElement
  }

  return false
}

/**
 * Restricts the selection to one element, such that the selection can always be surrounded by a new element.
 * @param selection the selection to restrict
 */
export function limitSelectionToOneElement(selection: Selection) {
  if (selection.type !== "Range" || selection.rangeCount !== 1) {
    return
  }

  const range = selection.getRangeAt(0)

  // can we handle a selection that starts at '|' in `<div> bla <span>|sjda</span> asd` to extend the range out of the
  // span by moving the start in front of the span tag?

  if (
    (range.commonAncestorContainer === range.startContainer &&
      range.commonAncestorContainer === range.endContainer) ||
    // if both nodes are text nodes it is enough if they have the same parent element
    (range.startContainer.nodeType === Node.TEXT_NODE &&
      range.endContainer.nodeType === Node.TEXT_NODE &&
      range.commonAncestorContainer === range.startContainer.parentElement &&
      range.commonAncestorContainer === range.endContainer.parentElement)
  ) {
    return
  }

  if (
    !selection.anchorNode ||
    !selection.focusNode ||
    selection.anchorNode.nodeType !== Node.TEXT_NODE
  ) {
    return
  }

  switch (selection.anchorNode?.compareDocumentPosition(selection.focusNode)) {
    case Node.DOCUMENT_POSITION_FOLLOWING:
      selection.setBaseAndExtent(
        selection.anchorNode,
        selection.anchorOffset,
        selection.anchorNode,
        selection.anchorNode.textContent?.length ?? 0,
      )
      break
    case Node.DOCUMENT_POSITION_PRECEDING:
      selection.setBaseAndExtent(
        selection.anchorNode,
        selection.anchorOffset,
        selection.anchorNode,
        0,
      )
      break
  }
}

export function useAknTextSelection(
  container: MaybeRefOrGetter<HTMLElement | undefined | null>,
) {
  const selection = ref<Range>()

  useEventListener(document, "selectionchange", () => {
    const containerValue = toValue(container)
    const currentSelection = getSelection()

    if (
      !containerValue ||
      !currentSelection?.anchorNode ||
      !hasElementAsParentElement(currentSelection.anchorNode, containerValue)
    ) {
      selection.value = undefined
      return
    }

    limitSelectionToOneElement(currentSelection)

    const range = getSelection()?.getRangeAt(0)
    if (range) {
      selection.value = range
    }
  })

  return selection
}
