import { getEidsOfElementType } from "@/lib/ldmlde"

/**
 * Find the eIds of all akn:mod elements.
 */
export function getModEIds(node: Node): string[] {
  return getEidsOfElementType(node, "mod")
}
