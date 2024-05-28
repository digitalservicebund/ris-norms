export type ModType =
  | "aenderungsbefehl-einfuegen"
  | "aenderungsbefehl-ersetzen"
  | "aenderungsbefehl-streichen"
  | "aenderungsbefehl-neufassung"
  | "aenderungsbefehl-ausserkrafttreten"

export interface ModData {
  refersTo: string
  timeBoundaryEid?: string
  destinationHref: string
  oldText: string
  newText: string
}
