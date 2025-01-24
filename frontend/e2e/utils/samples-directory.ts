import path from "node:path"
import { fileURLToPath } from "node:url"

export const samplesDirectory = path.resolve(
  path.dirname(fileURLToPath(import.meta.url)),
  "../../../LegalDocML.de/1.7.2/samples",
)
