type EidPart = "ändbefehl" | "bezugsdoc" | "text" | "intro"

function takeUntil<T>(xs: T[], predicate: (x: T) => boolean): T[] {
  const res = []
  for (const x of xs) {
    res.push(x)
    if (predicate(x)) {
      break
    }
  }
  return res
}

export function reduceEidToPart(eid: string, part: EidPart): string {
  return takeUntil(eid.split("_"), (eidPart) => eidPart.startsWith(part)).join(
    "_",
  )
}

export function eidHasPart(eid: string, part: EidPart): boolean {
  return eid.includes(part)
}
