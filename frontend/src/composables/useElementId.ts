import { Ref, readonly, ref } from "vue"

function createUseElementId(): (prefix?: string) => Readonly<Ref<string>> {
  /**
   * Used for keeping track of existing IDs in the unlikely event that
   * we generate a duplicate one.
   */
  const currentIds = new Set<string>()

  function getIdentifier(prefix = "element") {
    let id = Math.round(Math.random() * 10 ** 8)
      .toString()
      .padEnd(8, "0")

    id = `${prefix}-${id}`

    // Generate a new ID in the unlikely event that the current one is already
    // in use.
    if (currentIds.has(id)) id = getIdentifier()

    currentIds.add(id)

    return id
  }

  return (prefix) => readonly(ref(getIdentifier(prefix)))
}

/**
 * Returns a string that can be used as an identifier for HTML elements,
 * for example for creating associations between different elements via
 * ID for accessibility purposes such as aria-labelledby or aria-describedby.
 *
 * The identifier is guaranteed to be unique for everything currently
 * rendered on the page. It's not suitable as a GUID.
 */
export const useElementId = createUseElementId()
