import { ErrorResponse, ErrorResponseMapping } from "@/types/errorResponse"

export const errorMessages = {
  __fallback__: () => "Ein unbekannter Fehler ist aufgetreten.",

  "/errors/article-not-found": (
    e: ErrorResponse<{ eid: string; eli: string }>,
  ) => ({
    title: "Paragraph nicht gefunden",
    message: `Paragraph mit eId ${e.eid} in Norm ${e.eli} existiert nicht.`,
  }),

  "/errors/article-of-type-not-found": (
    e: ErrorResponse<{ eli: string; articleType: string }>,
  ) => ({
    title: "Paragraph nicht gefunden",
    message: `Paragraph vom Typ ${e.articleType} in der Norm ${e.eli} existiert nicht.`,
  }),

  "/errors/norm-not-found": (e: ErrorResponse<{ eli: string }>) => ({
    title: "Norm nicht gefunden",
    message: `Die Norm ${e.eli} existiert nicht.`,
  }),

  "/errors/announcement-not-found": (e: ErrorResponse<{ eli: string }>) => ({
    title: "Verkündung nicht gefunden",
    message: `Verkündung ${e.eli} existiert nicht.`,
  }),

  "/errors/element-not-found": (
    e: ErrorResponse<{ eli: string; eid: string }>,
  ) => ({
    title: "Element nicht gefunden",
    message: `Element mit eId ${e.eid} in Norm ${e.eli} existiert nicht.`,
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
      e.eli ? ` in der Norm mit ELI ${e.eli}` : ""
    } nicht gefunden werden.`,
  }),

  "/errors/xml-processing-error": () => ({
    title: "XML Verarbeitungsfehler",
    message: "Es gab einen Fehler bei der Verarbeitung der XML-Datei.",
  }),

  "/errors/unsupported-element-type": (
    e: ErrorResponse<{ elementType: string }>,
  ) => ({
    title: "Elementtyp nicht unterstützt",
    message: `Das Element mit dem Typ ${e.elementType} wird nicht unterstützt.`,
  }),

  "/errors/meta-mod-not-found": (
    e: ErrorResponse<{ eId: string; eli: string }>,
  ) => ({
    title: "Änderungsbefehle nicht gefunden",
    message: `Der Änderungsbefehl mit der eId "${e.eId}" wurde in der Norm "${e.eli}" nicht gefunden.`,
  }),

  "/errors/eli-not-in-href": (
    e: ErrorResponse<{ destinationHref: string }>,
  ) => ({
    title: "Zielstelle ohne ELI",
    message: `Die angegebene Bezeichnung der Zielstelle "${e.destinationHref}" enthält keinen ELI.`,
  }),

  "/errors/source-href-in-meta-mod-missing": (
    e: ErrorResponse<{ eId: string }>,
  ) => ({
    title: "Quellstelle fehlt",
    message: `Bei dem Änderungsbefehl mit der eId "${e.eId}" fehlt die Quellstelle.`,
  }),

  "/errors/target-node-not-present": (
    e: ErrorResponse<{ targetNodeEid: string; eli: string }>,
  ) => ({
    title: "Zielknoten nicht vorhanden",
    message: `Der Zielknoten mit der eId "${e.targetNodeEid}" ist in der Vorab-Fassung (ZF0) "${e.eli}" nicht vorhanden.`,
  }),

  "/errors/target-upto-node-not-present": (
    e: ErrorResponse<{ targetUpToNodeEid: string; eli: string }>,
  ) => ({
    title: '"Bis-Angabe" nicht vorhanden',
    message: `Die "Bis-Angabe" ("${e.targetUpToNodeEid}") der ausgewählten Zielelemente  existiert nicht in der Vorab-Fassung (ZF0) "${e.eli}".`,
  }),

  "/errors/target-and-upto-nodes-not-siblings": (
    e: ErrorResponse<{
      targetNodeEid: string
      targetUpToNodeEid: string
      eli: string
    }>,
  ) => ({
    title: '"Bis-Angabe" nicht in derselben Ebene',
    message: `Die ausgewählte "Bis-Angabe" "${e.targetUpToNodeEid}" der Zielelemente befindet sich nicht auf derselben Hierarchieebene wie das Referenzelement.`,
  }),

  "/errors/target-node-before-upto-node": (
    e: ErrorResponse<{
      targetNodeEid: string
      targetUpToNodeEid: string
      eli: string
    }>,
  ) => ({
    title: "Falsche Reihenfolge der Zielknoten",
    message: `Die ausgewählten "Von-" "${e.targetNodeEid}" und "Bis-Angaben" "${e.targetUpToNodeEid}" der Zielelemente sind nicht in der richtigen Reihenfolge angegeben.`,
  }),

  "/errors/character-range-not-present": (
    e: ErrorResponse<{ destinationHref: string; eId: string; eli: string }>,
  ) => ({
    title: "Zeichenbereich fehlt",
    message: `Im Zielelement mit Wert "${e.destinationHref}" des Änderungsbefehls mit der eId "${e.eId}" innerhalb der Vorab-Fassung (ZF0) "${e.eli}" ist der Zeichenbereich nicht vorhanden.`,
  }),

  "/errors/character-range-with-invalid-format": (
    e: ErrorResponse<{ characterRange: string; eId: string; eli: string }>,
  ) => ({
    title: "Ungültiges Format des Zeichenbereichs",
    message: `Der angegebene Zeichenbereich "${e.characterRange}" des Änderungsbefehls mit der eId "${e.eId}" in der Vorab-Fassung (ZF0) "${e.eli} hat kein gültiges Format.`,
  }),

  "/errors/character-range-not-resolve-to-target": (
    e: ErrorResponse<{ characterRange: string; eId: string; eli: string }>,
  ) => ({
    title: "Zeichenbereich nicht korrekt",
    message: `Der angegebene Zeichenbereich "${e.characterRange}" des Änderungsbefehls mit eId "${e.eId}"  in der Vorab-Fassung (ZF0) "${e.eli}" entspricht nicht dem Zieltext.`,
  }),

  "/errors/character-range-not-within-node-range": (
    e: ErrorResponse<{ characterRange: string; eId: string; eli: string }>,
  ) => ({
    title: "Zeichenbereich nicht innerhalb des Knotenbereichs",
    message: `Der angegebene Zeichenbereich "${e.characterRange}" des Änderungsbefehls mit eId "${e.eId}"  in der Vorab-Fassung (ZF0) "${e.eli}" liegt außerhalb des maximalen Zeichenbereichs des Zielknotens.`,
  }),

  "/errors/http-message-not-readable-exception": () => ({
    title: "Nachricht nicht lesbar",
    message: "Die Nachricht konnte nicht gelesen werden.",
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

  "/errors/norm-exists-already": (e: ErrorResponse<{ eli: string }>) => ({
    title: "Norm existiert bereits",
    message: `Norm ${e.eli} existiert bereits.`,
  }),

  "/errors/active-mod/destination/norm-not-found": (
    e: ErrorResponse<{ eli: string; destinationEli: string }>,
  ) => ({
    title: "Zielnorm nicht gefunden",
    message: `Die Zielnorm ${e.destinationEli} für die Norm ${e.eli} wurde nicht gefunden.`,
  }),

  "/errors/not-a-xml-file": (
    e: ErrorResponse<{ fileName: string; contentType: string }>,
  ) => ({
    title: "Ungültige Datei",
    message: `Die Datei ${e.fileName} ist keine XML-Datei. Der Inhaltstyp ist ${e.contentType}.`,
  }),

  "/errors/ldml-de-not-valid": () => ({
    title: "Invalide LDML.de-Datei",
    message: `Das LDML.de 1.6-Dokument ist nicht gültig.`,
  }),
} satisfies ErrorResponseMapping
