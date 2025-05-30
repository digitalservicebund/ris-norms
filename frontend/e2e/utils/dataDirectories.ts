import path from "node:path"
import { fileURLToPath } from "node:url"

const baseDir = path.dirname(fileURLToPath(import.meta.url))

export const samplesDirectory = path.resolve(
  baseDir,
  "../../../LegalDocML.de/1.7.2/samples",
)

export const frontendTestDataDirectory = path.resolve(baseDir, "../testData")
