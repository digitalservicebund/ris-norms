import type { ErrorResponse, ErrorResponseMapping } from "@/types/errorResponse"

export const errorMessages = {
  __fallback__: () => "Ein unbekannter Fehler ist aufgetreten.",

  "/errors/article-not-found": (
    e: ErrorResponse<{ eid: string; eli: string }>,
  ) => ({
    title: "Paragraph nicht gefunden",
    message: `Paragraph mit eId "${e.eid}" in Norm "${e.eli}" existiert nicht.`,
  }),

  "/errors/no-articles-of-types-found": (
    e: ErrorResponse<{ eli: string; articleTypes: string[] }>,
  ) => ({
    title: "Paragraph nicht gefunden",
    message: `In der Norm "${e.eli}" existieren keine Paragraphen der Typen "${e.articleTypes?.join(", ")}".`,
  }),

  "/errors/norm-not-found": (e: ErrorResponse<{ eli: string }>) => ({
    title: "Norm nicht gefunden",
    message: `Die Norm "${e.eli}" existiert nicht.`,
  }),

  "/errors/verkuendung-not-found": (e: ErrorResponse<{ eli: string }>) => ({
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

  "/errors/input-validation-error": (e: ErrorResponse) => {
    const validationDetailTranslations: Record<string, string> = {
      "Date must not be null": "Das Datum darf nicht leer sein.",
      "Art must not be null": "Die Art muss angegeben werden.",
      "A maximum of 100 time boundaries is supported":
        "Es sind maximal 100 Zeitgrenzen erlaubt.",
      "Not all combinations of date + art are unique.":
        "Das Datum darf für dieselbe Art nur einmal vorkommen.",
    }

    const detail = e.detail ?? "Unbekannter Validierungsfehler"
    const germanDetail = validationDetailTranslations[detail] ?? detail

    return {
      title: "Eingabefehler",
      message: germanDetail,
    }
  },

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

  "/errors/normendokumentationspaket-import-failed/not-a-zip-file": () => ({
    title: "Ungültige Datei",
    message: `Die Datei ist kein ZIP-Archiv.`,
  }),

  "/errors/normendokumentationspaket-import-failed/invalid-structure-in-zip-file":
    (e) => ({
      title: "Unerwartet Struktur",
      // Normally we shouldn't use the detail for use facing messages, but here we have many different exceptions that could cause this.
      // So for now we just show the english detail message to atleast provide some context to the users.
      message: `Das ZIP-Archiv entspricht nicht der erwarteten Struktur: ${e.detail}`,
    }),

  "/errors/normendokumentationspaket-import-failed/unsupported-file-type": (
    e: ErrorResponse<{ supportedTypes: string[]; file: string }>,
  ) => ({
    title: "Nicht unterstützter Datei-Typ in ZIP-Archiv.",
    message: `Der erkannte Datei-Typ für die Datei "${e.file}" wird nicht unterstützt. Unterstützt werden: ${e.supportedTypes?.join(", ")}`,
  }),

  "/errors/not-a-ldml-de-xml-file": (
    e: ErrorResponse<{ fileName: string }>,
  ) => ({
    title: "Ungültige Datei",
    message: `Die XML-Datei "${e.fileName}" ist keine LDML.de-Datei.`,
  }),

  "/errors/ldml-de-not-valid": () => ({
    title: "Invalide LDML.de-Datei",
    message: "Das LDML.de 1.8.1-Dokument ist nicht gültig.",
  }),

  "/errors/ldml-de-not-schematron-valid": () => ({
    title: "Invalide LDML.de-Datei",
    message: "Das LDML.de 1.8.1-Dokument ist nicht gültig.",
  }),

  "/errors/invalid-eli": (
    e: ErrorResponse<{ eliType: string; eli: string }>,
  ) => ({
    title: "Invalide ELI",
    message: `Die angegebene ${e.eliType} "${e.eli}" ist nicht gültig.`,
  }),
  "/errors/verkuendung-without-norm": (e: ErrorResponse<{ eli: string }>) => ({
    title: "Verkündung hat keine Norm",
    message: `Die Verkündung "${e.eli}" existiert, aber die dazugehörige Norm fehlt.`,
  }),
} satisfies ErrorResponseMapping
