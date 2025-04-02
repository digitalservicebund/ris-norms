const tag = (strings: TemplateStringsArray, ...values: unknown[]) =>
  String.raw({ raw: strings }, ...values)

/**
 * Tagged template string for Tailwind classes. The tag itself doesn't do
 * anything, but using it will allow your editor to provide Tailwind
 * Intellisense for template strings in scripts, as well as automatic class
 * sorting via Prettier.
 *
 * ```ts
 * const classes = tw`px-16 bg-blue-200`
 * ```
 */
export const tw = tag
