# 14. Storage of links between time boundaries, affected documents and amending statements

Date: 2025-03-03

## Status

Draft

## Context

Until now, we have been relying on the so-called "mods" when handling amending laws. These mods (both in the `body` as well as in the `metadata` parts of the XML) conveyed the link between the origin of an amendment (amending statement, using the `eId` reference of the `akn:mod`), the date on which the amendment should take effect (time boundary) and the specific expression of the target law (affected document) that should be amended.

Unfortunately, we have now realized that the quality of these "mods" cannot be guaranteed, meaning we can no longer rely on them. However, this data linking is essential for the user workflow and must be gathered in the initial steps (first, the time boundaries; then, the affected documents and the amending statements) when a new announcement arrives. This data must then be retrieved again in later steps (text consolidation and metadata).

To address this, we have decided to introduce a new approach for maintaining this data linking throughout the amendment process.

## Decision

We will use a custom metadata structure under the `akn:proprietary` node with the following characteristics:

- A new custom namespace `http://MetadatenMods.LegalDocML.de/1.7.2/`
- A list of `<mod>` elements with the following children:
  - `<time-boundary>`: the date value or "unbestimmt"
  - `<eid>`: the eId of the node within the `akn:body` containing the amending statement.
  - `<affected-document>`, the Work-ELI of the target norm

Example:
```
<mods:legalDocML.de_metadaten xmlns:mods="http://MetadatenMods.LegalDocML.de/1.7.2/">
    <mods:mod>
        <mods:time-boundary>2020-01-01</mods:time-boundary>
        <mods:eid>hauptteil-1_art-1_abs-1_untergl-1_listenelem-1</mods:eid>
        <mods:affected-document>eli/bund/bgbl-1/2021/123</mods:affected-document>
    </mods:mod>
    <mods:mod>
        <mods:time-boundary>2024-12-12</mods:time-boundary>
        <mods:eid>hauptteil-1_art-1_abs-1_untergl-1_listenelem-2</mods:eid>
        <mods:affected-document>eli/bund/bgbl-1/2019/789</mods:affected-document>
    </mods:mod>
        <mods:mod>
        <mods:time-boundary>2020-01-01</mods:time-boundary>
        <mods:eid>hauptteil-1_art-1_abs-1_untergl-1_listenelem-3</mods:eid>
        <mods:affected-document>eli/bund/bgbl-1/1990/456</mods:affected-document>
    </mods:mod>
</mods:legalDocML.de_metadaten>
```

Implications of this approach:
- We will have as many `<mod>` nodes as there are amending statements.
- Time boundaries will be duplicated due to the point above. Meaning if at the same time boundary there are several amending statements, the time boundary will be present so many times.


## Consequences

- **Self-contained XML documents**: This avoids the need to combine different data sources (XML and database).

- **Simpler structure**:
  - Localized within the metadata, making it easy to extract and update.
  - Easy to maintain and extend.
  - Simple to clean up, if needed (using the namespace).

- **More readable structure**: Direct access to the XML (e.g., for manual testing) becomes more intuitive.

- **Independence from the LDML.de standard**: We have full flexibility to implement the approach according to our needs.

- **Potential for reuse**: The approach could also be partially reused for other types of announcements (e.g., new laws or _Mantelgesetze_), particularly for handling time boundaries.


## Alternatives Considered

The alternatives are ordered from most to least feasible/beneficial:

1. **Annotate the amending statements with two new custom attributes** (`time-boundary` and `affected-document`):
   - More "logical" since the amending statements themselves would contain the necessary information.
   - Less centralized.
   - Would require iterating through the entire `akn:body` since the elements containing the annotations (`akn:point`, `akn:paragraph`, etc.) are not known in advance.

2. **Continuing using the "mods"**:
   - Already implemented (code and knowledge exist), making it more future-proof.
   - However:
     - It is uncertain whether we will receive mods as input (at least within the current project timeline).
     - Possible conceptual dependency on the LDML.de standard.
     - Not all use cases are covered by the standard (e.g., "unbestimmt" cases). To work around this, we would need to misuse the standard by placing "unbestimmt" cases under the `date` attribute in `eventRef`, which is intended for actual dates. Even though XSD validity is not a concern, this deviation would need to be documented.
     - Cleanup would be more complex, as multiple nodes (active-mods, lifecycle, temporal-data) would need to be handled, making errors more likely.

3. **Using a separate data structure outside the XML**:
   - The main drawback is that XMLs would no longer be self-contained.
   - Given that the XML-based solutions do not have significant downsides, there is no compelling reason to adopt this approach.
