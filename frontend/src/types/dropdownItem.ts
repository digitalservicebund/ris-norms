/**
 * Describes a selectable option in a dropdown menu.
 */
export interface DropdownItem {
  /** The label displayed for the item. */
  label: string
  /** The underlying value of the item. */
  value: string
  /** Specifies whether the item is selectable. */
  disabled?: boolean
}
