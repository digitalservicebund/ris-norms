import type { Locator } from "playwright/test"

/**
 * Makes a text selection inside an element. Note that if the locator includes
 * multiple text nodes or a mix of text and DOM nodes, you'll need to specify
 * the index of the text node that includes the text in order to make a selection.
 *
 * @param element Element within which the text should be selected
 * @param text Text that should be selected
 * @param [childNodeIndex=0] Position of the child node that includes the text
 */
export async function selectText(
  element: Locator,
  text: string,
  childNodeIndex = 0,
) {
  const handle = await element.elementHandle()
  if (!handle) throw new Error("Could not get element handle for the locator")

  await handle.evaluate(
    (node, args) => {
      const text = node.childNodes[args.childNodeIndex].textContent

      if (!text) throw new Error("The selected element has no text content")
      if (text.indexOf(args.text) < 0) {
        throw new Error(
          `Element with text "${text}" does not include desired selection "${args.text}"`,
        )
      }

      const range = document.createRange()
      range.setStart(
        node.childNodes[args.childNodeIndex],
        text.indexOf(args.text),
      )
      range.setEnd(
        node.childNodes[args.childNodeIndex],
        text.indexOf(args.text) + args.text.length,
      )

      const selection = window.getSelection()
      if (!selection) {
        throw new Error("Changing selections is not supported by your browser")
      }
      selection.removeAllRanges()
      selection.addRange(range)

      document.dispatchEvent(new Event("selectionchange", { bubbles: true }))
    },
    { text, childNodeIndex },
  )
}
