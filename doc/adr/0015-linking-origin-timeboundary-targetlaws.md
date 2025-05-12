# 15. Storage of links between time boundaries, affected documents and amending statements

**Date**: 2025-03-05

## Status

Accepted

## Context

Until now, we have been relying on the so-called "mods" when handling amending laws. These mods (both in the `body` as well as in the `metadata` parts of the XML) conveyed the link between the origin of an amendment (amending statement, using the `eId` reference of the `akn:mod`), the date on which the amendment should take effect (time boundary), and the specific expression of the target law (affected document) that should be amended.

Unfortunately, we have now realized that the quality of these "mods" cannot be guaranteed, meaning we can no longer rely on them. However, this data linking is essential for the user workflow and must be gathered in the initial steps (first, the time boundaries; then, the affected documents and the amending statements) when a new announcement arrives. This data must then be retrieved again in later steps (text consolidation and metadata).

To address this, we have decided to introduce a new approach for maintaining this data linking throughout the amendment process.

## Decision

We will use a custom metadata structure within the ris metadata node (`<ris:legalDocML.de_metadaten xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.7.2/" />`) with the following characteristics:

- A new custom namespace with the URI identifier `http://MetadatenMods.LegalDocML.de/1.7.2/` and the prefix `norms`.
- Two main sections:
  - **`<norms:geltungszeiten>`**: Defines time boundaries related to the validity of modifications and contains a list of `<norms:geltungszeit>` with:
    - An `id` attribute, serving as a reference for modifications (UUID).
    - An `art` attribute to specify the type of validity (`inkraft`, `ausserkraft`).
    - A date value, if the time boundary is defined. If date is not yet known (`unbestimmt`), a string identifier chosen by the user will be used.
  - **`<norms:zielnorm-references>`**: Represents the links between amending statements, time boundaries, and target laws, and also includes the type of the reference. It contains a list of `<norms:zielnorm-reference>` with:
    - `<norms:typ>`: The type of the reference (e.g. `Änderungsvorschrift`, `Aufhebung`, `Teiländerung`), which is new to the whole concept.
    - `<norms:geltungszeit>`: A reference to a `norms:geltungszeit` using its id.
    - `<norms:eid>`: The eId of the node within the `akn:body` containing the amending statement.
    - `<norms:zielnorm>`: The Work-ELI of the target norm being referenced.

Example:
```
<norms:legalDocML.de_metadaten xmlns:norms="http://MetadatenMods.LegalDocML.de/1.7.2/">
    <norms:geltungszeiten>
        <norms:geltungszeit id="3b8a6a6d-9bf8-4b53-90ce-3b4dcf07d6f4" art="inkraft">2020-01-01</norms:geltungszeit>
        <norms:geltungszeit id="141945d5-b518-4ebe-815e-916fa4e00951" art="ausserkraft">2024-12-12</norms:geltungszeit>
        <norms:geltungszeit id="3f3d2782-3b54-44d5-b75a-690a83f7f163" art="inkraft">unbestimmt-1</norms:geltungszeit>
        <norms:geltungszeit id="91624270-3efe-462f-952f-f5a2149de356" art="ausserkraft">unbestimmt-2</norms:geltungszeit>
    </norms:geltungszeiten>
    <norms:zielnorm-references>
        <norms:zielnorm-reference>
            <norms:typ>Änderungsvorschrift</norms:typ>
            <norms:geltungszeit>3b8a6a6d-9bf8-4b53-90ce-3b4dcf07d6f4</norms:geltungszeit>
            <norms:eid>hauptteil-1_art-1_abs-1_untergl-1_listenelem-1</norms:eid>
            <norms:zielnorm>eli/bund/bgbl-1/2021/123</norms:zielnorm>
        </norms:zielnorm-reference>
        <norms:zielnorm-reference>
            <norms:typ>Aufhebung</norms:typ>
            <norms:geltungszeit>141945d5-b518-4ebe-815e-916fa4e00951</norms:geltungszeit>
            <norms:eid>hauptteil-1_art-1_abs-1_untergl-1_listenelem-2</norms:eid>
            <norms:zielnorm>eli/bund/bgbl-1/2019/789</norms:zielnorm>
        </norms:zielnorm-reference>
        <norms:zielnorm-reference>
            <norms:typ>Teiländerung</norms:typ>
            <norms:geltungszeit>3f3d2782-3b54-44d5-b75a-690a83f7f163</norms:geltungszeit>
            <norms:eid>hauptteil-1_art-1_abs-1_untergl-1_listenelem-3</norms:eid>
            <norms:zielnorm>eli/bund/bgbl-1/1990/456</norms:zielnorm>
        </norms:zielnorm-reference>
        <norms:zielnorm-reference>
            <norms:typ>Änderungsvorschrift</norms:typ>
            <norms:geltungszeit>3b8a6a6d-9bf8-4b53-90ce-3b4dcf07d6f4</norms:geltungszeit>
            <norms:eid>hauptteil-1_art-1_abs-1_untergl-1_listenelem-4</norms:eid>
            <norms:zielnorm>eli/bund/bgbl-1/1990/456</norms:zielnorm>
        </norms:zielnorm-reference>
    </norms:zielnorm-references>
</norms:legalDocML.de_metadaten>
```
This structure ensures:
- that time boundaries can be stored independently of the modifications (also relevant when not dealing with amending laws)
- that there will be as many `<norms:zielnorm-reference>` elements as there are amending statements

## Consequences

- **Self-contained XML documents**: This avoids the need to combine different data sources (XML and database).
- **Simpler structure**:
  - Localized within the metadata, making it easy to extract and update.
  - Easy to maintain and extend.
  - Simple to clean up, if needed (using the namespace).
- **More readable structure**: Direct access to the XML (e.g., for manual testing) becomes more intuitive.
- **Independence from the LDML.de standard**: We have full flexibility to implement the approach according to our needs.
- **Potential for reuse**: The approach can also be partially reused for other types of announcements (e.g., new laws or _Mantelgesetze_), particularly for handling time boundaries.

## Alternatives Considered

The alternatives are ordered from most to least feasible/beneficial:

1. **Annotate the amending statements with two new custom attributes** (`geltungszeit` and `zielnorm`):
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
