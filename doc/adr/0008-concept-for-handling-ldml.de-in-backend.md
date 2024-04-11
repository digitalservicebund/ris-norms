# 6. Concept for handling LDML.de in the backend

Date: 2024-04-12

## Status

Proposed

## Context

We have identified two main issues with our current approach in the backend source code:
- The current domain model is quite artificial, depicting just one specific use case (a.k.a amending laws).
- The database model contains data that is duplicated, meaning present within the XML (xml columns) and in a separate column.
- LDML.de is not the source of truth

This ADR outlines a general concept for how to handle the XMLs in LDML.de to tackle the mentioned issues above.

## Decision

This concept consists of:
1. The database model will be re-designed. We won't have anymore `amending_law`, `article` and `target_law` but rather one table for process data
and another table for the work data. The latter will contain two column (`eli` and `xml`).
2. The `xml` column of the work data table will be of type `TEXT`. We decided against using the xml type, at least for now. If we realize that we do
a lot of filtering on the database level using specific nodes, we may migrate to xml type or also maybe use a materialized view.
3. The mapper class mapping between the DTO and the domain model will be in charge of parsing the retrieved XML into a `w3c.dom.Document`.
4. For the work data the domain model will consist of a wrapper class around the `w3c.dom.Document` class with getters for extracting using XPath
the different data needed out of the XML (e.g. the title, or the announcement date, etc.). For example:
```java
public class Norm {
    private Document xml;

    public void setXml(Document xml) {
        this.xml = xml;
    }

    public String getTitle() {
        // XPath on this.xml to extract the title
        return "TODO";
    }
}
```
5. The mapper class mapping between the domain and the controller schemas will use the new domain model to map to the schemas.
6. The controller schemas will stay as they are right now, since they depict the needed response payload.


## Consequences

With this concept we will tackle the issues mentioned above:
- The domain model will have now a general purpose and will not be just depicting one use case.
- There will not be any data duplication anymore, but the `eli` that we use as an identifier (which may be dropped later on)
- LDML.de will be positioned as the sole source of truth.

We will have a more general handling of the LDML.de in the backend and therefore a useful flexibility in case we need to adapt it again.
