export function xmlStringToDocument(xmlString: string): Document {
  return new DOMParser().parseFromString(xmlString, "application/xml")
}

export function xmlDocumentToString(xmlDoc: Document): string {
  return new XMLSerializer().serializeToString(xmlDoc)
}
