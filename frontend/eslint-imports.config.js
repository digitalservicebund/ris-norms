import { globSync } from "node:fs"
import { normalize, sep } from "node:path"
import { config as defineConfig } from "typescript-eslint"
import baseConfig from "./eslint.config.js"

/**
 * Generates zones for restricting imports based on the rules described
 * by ADR 13 (Organize feature specific things by page in frontend):
 *
 * > Access to parents is allowed but access to siblings and children is not.
 *
 * @param {string} path
 */
function getZoneFromPath(path) {
  path = normalize(path)
  if (path.endsWith(sep)) path = path.substring(0, path.length - 1)

  // Restrict all subfolders of the folder while allowing imports of
  // files in the folder, i.e. allow "./mod.ts" but not "./child/mod.ts"
  const restrictChildren = `${path}/*/**/`

  // Restrict all subfolders of the parent folder except the folder
  // itself, i.e. don't allow imports from siblings like "../sibling/mod.ts".
  const segments = path.split("/")
  const basename = segments.pop()
  const restrictSiblings = [...segments, `!(${basename})`, "**/"].join("/")

  return {
    target: `${path}/*`,
    from: [restrictChildren, restrictSiblings],
  }
}

const zones = globSync("src/views/*/**/").map((path) => getZoneFromPath(path))

// This config is just like our regular ESLint config, except that it also
// adds dynamically generated rules for what is allowed to be imported from
// where. The reason this isn't part of the regular config is that 1) those
// rules seem to be quite slow and 2) dynamically generating them breaks
// ESLint highlighting in IDEs.
//
// So we're extracting this to a separate config that can be used on demand
// (run: `node --run eslint:check:imports`). It will perform all the usual
// checks and in addition verify the import rules.
export default defineConfig(...baseConfig, {
  rules: {
    "import/no-restricted-paths": ["error", { zones }],
  },
})
