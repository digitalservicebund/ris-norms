package de.bund.digitalservice.ris.norms.adapter.input.fontoapi.schema;

public record PutDocumentBodySchema(ContextSchema context, String documentId, String content) {}
