function* generateId() {
  let count = 0
  while (true) yield (++count).toString()
}

function createUseElementId(): (prefix?: string) => Record<string, string> {
  const id = generateId()

  function getIdentifier(prefix = "element") {
    return `${prefix}-${id.next().value}`
  }

  function getIdMap(prefix?: string): Record<string, string> {
    return new Proxy({} as Record<string | symbol, string>, {
      get: (target, name) => {
        if (!target[name]) target[name] = getIdentifier(prefix)
        return target[name]
      },
    })
  }

  return getIdMap
}

/**
 * Returns an object that can be used to retrieve identifiers for HTML elements,
 * for example for creating associations between different elements via
 * ID for accessibility purposes such as aria-labelledby or aria-describedby.
 *
 * The identifier is guaranteed to be unique for everything currently
 * rendered on the page. It's not suitable as a GUID.
 *
 * This object works in such a way that getting any property name will work and
 * contain an ID. That way, you only need to use `useElementId` once per
 * component and get any number of IDs from it. For example:
 *
 * ```ts
 * // Via destructuring
 * const { idOne, idTwo, idThree } = createUseElementId()
 *
 * // Via object access
 * const ids = createUseElementId()
 * const idOne = ids.idOne
 * ```
 */
export const useElementId = createUseElementId()
