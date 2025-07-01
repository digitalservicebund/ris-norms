# 20. Extending the concept of amended-norm-expressions for the orphan removal process

**Date**: 2025-07-01

## Status

Accepted

## Context

For the orphan removal process to work, we need to keep track of which expressions were created by the system, which
happen in two different cases:
1. the announcement is producing a new expression that is before another already existing expression
2. the announcement is producing a new expression on exactly the same date as an already existing expression

In both cases the already existing expressions become _gegenstandslos_. To be able to not mark the newly-created replacing
expressions as orphans, we need to keep the information about how they were created. This is needed because currently
orphans are only marked as such, if the announcement does not contain the _zielnorm_ reference anymore (meaning the time boundary
is no longer relevant for the targeted norm and therefore the created expression is no longer needed).

## Decision

We will extend the element `<norms:amended-norm-expressions>` of the custom metadata namespace `http://MetadatenMods.LegalDocML.de/1.8.1/`
introduced in ADR-0016.

The elements `<norms:amended-norm-expression>` will include a new attribute `creation` with three possible values:
- NEW: for all newly created expressions that are not included under the use cases 1. and 2. from above
- SYSTEM: for the expressions created under use case 1.
- REPLACING: : for the expressions created under use case 2.

Example:
```
<norms:legalDocML.de_metadaten xmlns:norms="http://MetadatenMods.LegalDocML.de/1.8.1/">
    <norms:amended-norm-expressions>
        <norms:norm-expression creation="NEW">eli/bund/bgbl-1/1964/s593/2017-03-16/1/deu</norms:norm-expression>
        <norms:norm-expression creation="SYSTEM">eli/bund/bgbl-1/1964/s593/2021-01-01/2/deu</norms:norm-expression>
        <norms:norm-expression creation="REPLACING">eli/bund/bgbl-1/1964/s593/2022-02-02/2/deu</norms:norm-expression>
    </norms:amended-norm-expressions>
</norms:legalDocML.de_metadaten>
```

Using this attribute in the algorithm for orphan removal would work like this:

- NEW: remove the orphan if the _zielnorm_ reference is gone (current state)
- SYSTEM: remove the orphan only if there are no newly created expressions before (within `norms:amended-norm-expressions`)
- REPLACING: remove the orphan only if the _zielnorm_ reference is gone AND there is no newly created expressions before (meaning combining conditions of NEW and SYSTEM)

## Consequences

- **Orphans being correctly recognized**: With this approach, the expressions created under use cases 1. and 2. won't be wrongly marked as orphans.
- **Extending existing element**: We re-use the existing element, that is specifically used for holding the information about created expressions.
- **Consistency**: Adding the attribute to all `norms:norm-expression` and not only for the use cases 1. and 2.

