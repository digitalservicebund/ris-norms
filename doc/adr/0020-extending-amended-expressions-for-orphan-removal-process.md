# 20. Extending the concept of amended-norm-expressions for the orphan removal process

**Date**: 2025-07-02

## Status

Accepted

## Context

For the orphan removal process to work correctly, we need to track which expressions were created by the system. This
occurs in two specific cases:

1. The announcement is producing a new expression that precedes an already existing one.
2. The announcement is producing a new expression on the exact same date as an already existing one.

In both cases the already existing expressions become _gegenstandslos_. To avoid mistakenly marking the newly-created
replacing
expressions as orphans, we need to retain information about how they were created. This is needed because currently
orphans are only marked as such, if the announcement does not contain the _zielnorm_ reference anymore (meaning the time
boundary
is no longer relevant for the targeted norm and therefore the created expression is no longer needed). But this current
approach
leads to the expressions created in use case 1. and 2. to be incorrectly marked as orphans.

## Decision

We will extend the element `<norms:amended-norm-expressions>` of the custom metadata namespace
`http://MetadatenMods.LegalDocML.de/1.8.1/`
introduced in ADR-0016.

Each `<norms:amended-norm-expression>` element will include two new mandatory attributes:

- `created-by-zeitgrenze` (true/false)
- `created-by-replacing-existing-expression` (true/false)

By combining these two attributes, we can distinguish between the different use cases:

| `created-by-zeitgrenze` | `created-by-replacing-existing-expression` | use case                                                                              |
|-------------------------|--------------------------------------------|---------------------------------------------------------------------------------------|
| true                    | false                                      | Expressions newly created due to _zeitgrenze_, not replacing any existing expression. |
| false                   | true                                       | Expressions created by replacing an existing one (use case 1).                        |
| true                    | true                                       | Expressions created by _zeitgrenze_ that also replace an existing one (use case 2).   |

Example:

```
<norms:legalDocML.de_metadaten xmlns:norms="http://MetadatenMods.LegalDocML.de/1.8.1/">
    <norms:amended-norm-expressions>
        <!-- New "normal" expression -->
        <norms:norm-expression created-by-zeitgrenze="true" created-by-replacing-existing-expression="false">eli/bund/bgbl-1/1964/s593/2017-03-16/1/deu</norms:norm-expression>
        <!-- Created by SYSTEM because already existing expression-->
        <norms:norm-expression created-by-zeitgrenze="false" created-by-replacing-existing-expression="true">eli/bund/bgbl-1/1964/s593/2021-01-01/2/deu</norms:norm-expression>
        <!-- New "normal" expression -->
        <norms:norm-expression created-by-zeitgrenze="true" created-by-replacing-existing-expression="false">eli/bund/bgbl-1/1964/s593/2022-02-02/2/deu</norms:norm-expression>
        <!-- Created because of time boundary AND replacing existing expression-->
        <norms:norm-expression created-by-zeitgrenze="true" created-by-replacing-existing-expression="true">eli/bund/bgbl-1/1964/s593/2022-02-02/2/deu</norms:norm-expression>
    </norms:amended-norm-expressions>
</norms:legalDocML.de_metadaten>
```

Using these attributes in the algorithm for orphan removal process, we would iterate over
`norms:amended-norm-expressions` from the oldest to the most recent, performing the following steps:

1. If `created-by-zeitgrenze=true` and `created-by-replacing-existing-expression=false` and the _zeitgrenze_ is no
   longer present under the _zielnorm_ references, the entry is deleted
2. If `created-by-zeitgrenze=false` and `created-by-replacing-existing-expression=true` and there are no preceding
   expressions with `created-by-zeitgrenze=true`, the entry is deleted
3. If `created-by-zeitgrenze=true` and `created-by-replacing-existing-expression=true` and neither the _zeitgrenze_ is
   present under the _zielnorm_ references nor are there preceding expressions with `created-by-zeitgrenze=true`, the
   entry is deleted

## Consequences

- **Orphans being correctly recognized**: With this approach, the expressions created under use cases 1. and 2. will not
  be incorrectly marked as orphans.
- **Reusing existing element**: We reuse and extend the existing element specifically used for holding the information
  about created expressions.
- **Consistency**: Attributes are added to all `norms:norm-expression` and not only for the use cases 1. and 2.
- **Readability**: Using readable attribute names to more easily understand the meaning.

