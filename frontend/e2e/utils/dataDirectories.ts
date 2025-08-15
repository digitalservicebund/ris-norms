import path from "node:path"
import { fileURLToPath } from "node:url"

const baseDir = path.dirname(fileURLToPath(import.meta.url))

export const samplesDirectory = path.resolve(
  baseDir,
  "../../../LegalDocML.de/1.8.2/samples",
)

export const frontendTestDataDirectory = path.resolve(baseDir, "../testData")
