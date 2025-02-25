import { ErrorResponse, ErrorResponseMapping } from "@/types/errorResponse"

export const errorMessages = {
  __fallback__: () => "Ein unbekannter Fehler ist aufgetreten.",

  "/errors/article-not-found": (
    e: ErrorResponse<{ eid: string; eli: string }>,
  ) => ({
    title: "Paragraph nicht gefunden",
    message: `Paragraph mit eId "${e.eid}" in Norm "${e.eli}" existiert nicht.`,
  }),

  "/errors/article-of-type-not-found": (
    e: ErrorResponse<{ eli: string; articleType: string }>,
  ) => ({
    title: "Paragraph nicht gefunden",
    message: `Paragraph vom Typ "${e.articleType}" in der Norm "${e.eli}" existiert nicht.`,
  }),

  "/errors/norm-not-found": (e: ErrorResponse<{ eli: string }>) => ({
    title: "Norm nicht gefunden",
    message: `Die Norm "${e.eli}" existiert nicht.`,
  }),

  "/errors/announcement-not-found": (e: ErrorResponse<{ eli: string }>) => ({
    title: "Verkündung nicht gefunden",
    message: `Verkündung "${e.eli}" existiert nicht.`,
  }),

  "/errors/element-not-found": (
    e: ErrorResponse<{ eli: string; eid: string }>,
  ) => ({
    title: "Element nicht gefunden",
    message: `Element mit eId "${e.eid}" in Norm "${e.eli}" existiert nicht.`,
  }),

  "/errors/invalidate-update": () => ({
    title: "Ungültige Aktualisierung",
    message: "Die durchgeführte Aktualisierung ist ungültig.",
  }),

  "/errors/mandatory-node-not-found": (
    e: ErrorResponse<{ xpath: string; eli?: string; nodeName?: string }>,
  ) => ({
    title: "Element in der XML-Datei fehlt",
    message: `Das ${
      e.nodeName ? `Element "${e.nodeName}"` : "Element"
    } mit dem XML-Pfad "${e.xpath}" konnte${
      e.eli ? ` in der Norm "${e.eli}"` : ""
    } nicht gefunden werden.`,
  }),

  "/errors/xml-processing-error": () => ({
    title: "XML Verarbeitungsfehler",
    message: "Es gab einen Fehler bei der Verarbeitung der XML-Datei.",
    suggestion: "Versuchen Sie, die Datei erneut hochzuladen.",
  }),

  "/errors/http-message-not-readable-exception": () => ({
    title: "Nachricht nicht lesbar",
    message: "Die Nachricht konnte nicht gelesen werden.",
    suggestion:
      "Wenn das Problem bestehen bleibt, wenden Sie sich an den Betreiber",
  }),

  "/errors/input-validation-error": (e: ErrorResponse) => ({
    title: "Eingabefehler",
    message: `Fehler bei den eingehenden Daten: ${e.detail}`,
  }),

  "/errors/parameter-binding-error": (
    e: ErrorResponse<{ invalidValue: string }>,
  ) => ({
    title: "Parameterbindungsfehler",
    message: `Ungültiger Parameter: ${e.invalidValue}.`,
  }),

  "/errors/internal-server-error": () => ({
    title: "Serverfehler",
    message: "Ein unerwarteter Fehler ist aufgetreten",
  }),

  "/errors/norm-with-eli-exists-already": (
    e: ErrorResponse<{ eli: string }>,
  ) => ({
    title: "Norm existiert bereits",
    message: `Norm "${e.eli}" existiert bereits.`,
  }),

  "/errors/norm-with-guid-exists-already": (
    e: ErrorResponse<{ guid: string }>,
  ) => ({
    title: "Identifikator GUID bereits im System",
    message: `Es existiert bereits eine Norm mit dem GUID "${e.guid}.`,
  }),

  "/errors/active-mod/destination/norm-not-found": (
    e: ErrorResponse<{ eli: string; destinationEli: string }>,
  ) => ({
    title: "Zielnorm nicht gefunden",
    message: `Die Zielnorm "${e.destinationEli}" für die Norm "${e.eli}" wurde nicht gefunden.`,
  }),

  "/errors/not-a-xml-file": (
    e: ErrorResponse<{ fileName: string; contentType: string }>,
  ) => ({
    title: "Ungültige Datei",
    message: `Die Datei "${e.fileName}" ist keine XML-Datei. Der Inhaltstyp ist "${e.contentType}".`,
  }),

  "/errors/not-a-ldml-de-xml-file": (
    e: ErrorResponse<{ fileName: string }>,
  ) => ({
    title: "Ungültige Datei",
    message: `Die XML-Datei "${e.fileName}" ist keine LDML.de-Datei.`,
  }),

  "/errors/ldml-de-not-valid": () => ({
    title: "Invalide LDML.de-Datei",
    message: "Das LDML.de 1.7.2-Dokument ist nicht gültig.",
  }),

  "/errors/ldml-de-not-schematron-valid": () => ({
    title: "Invalide LDML.de-Datei",
    message: "Das LDML.de 1.7.2-Dokument ist nicht gültig.",
  }),
} satisfies ErrorResponseMapping
