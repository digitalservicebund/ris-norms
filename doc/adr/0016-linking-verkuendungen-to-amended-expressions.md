# 16. Storage of links between Verkündungen and amended norm-expression

**Date**: 2025-03-14

## Status

Accepted

## Context

We need to keep track which Verkündung was responsible for creating which new norm-expressions to populate the
Verkündungs-Detailseite with information about these.

## Decision

We will use a new section in the custom metadata namespace `http://MetadatenMods.LegalDocML.de/1.8.1/` introduced in
ADR-0015.

This section uses the new element `<norms:amended-norm-expressions>` in which the expression elis of the expressions
changed by the Verkündung are listed as `<norms:norm-expression>` elements.

Example:
```
<norms:legalDocML.de_metadaten xmlns:norms="http://MetadatenMods.LegalDocML.de/1.8.1/">
    <norms:amended-norm-expressions>
        <norms:norm-expression>eli/bund/bgbl-1/2023/413/2023-12-29/1/deu</norms:norm-expression>
        <norms:norm-expression>eli/bund/bgbl-1/2023/413/2024-01-11/2/deu</norms:norm-expression>
        <norms:norm-expression>eli/bund/bgbl-1/2023/413/2024-02-12/1/deu</norms:norm-expression>
        <norms:norm-expression>eli/bund/bgbl-1/2023/413/0001-01-01/24/deu</norms:norm-expression>
        <norms:norm-expression>eli/bund/bgbl-1/2002/s1181/2023-12-29/1/deu</norms:norm-expression>
    </norms:amended-norm-expressions>
</norms:legalDocML.de_metadaten>
```

## Consequences

- **Self-contained XML documents**: This avoids the need to combine different data sources (XML and database).
- **Simple structure**:
  - Localized within the metadata, making it easy to extract and update.
  - Easy to maintain and extend.
  - Simple to clean up, if needed (using the namespace).
- **More readable structure**: Direct access to the XML (e.g., for manual testing) becomes more intuitive.
- **Independence from the LDML.de standard**: We have full flexibility to implement the approach according to our needs.
