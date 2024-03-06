<!-- Additional Macros (based on context diagram macros):

    Container(alias, label, ?techn, ?descr, ?sprite, ?tags, ?link, ?baseShape)
    ContainerDb(alias, label, ?techn, ?descr, ?sprite, ?tags, ?link)
    ContainerQueue(alias, label, ?techn, ?descr, ?sprite, ?tags, ?link)
    Container_Ext(alias, label, ?techn, ?descr, ?sprite, ?tags, ?link, ?baseShape)
    ContainerDb_Ext(alias, label, ?techn, ?descr, ?sprite, ?tags, ?link)
    ContainerQueue_Ext(alias, label, ?techn, ?descr, ?sprite, ?tags, ?link)
    Container_Boundary(alias, label, ?tags, ?link)
 -->

```mermaid
C4Container
      title Container: RIS-Norms
      Person_Ext("documentalist", "Documentalist", "A staff member of the<br> 'Normendokumentation (BfJ)'")

      Container_Ext("portal", "Portal", "Gives the public access to norms")
      Rel("backend", "portal", "Makes new norms accessible to the portal")

      System_Boundary("ris-norms", "RIS-Norms"){
        Container("frontend", "Single Page Web App", "Vue, TypeScript", "Provides all RIS-Norms functionality to <br>users via their web browser")
        Rel("documentalist", "frontend", "Adjusts new amending laws,<br> adjusts metadata <br> and publishes the new norms<br> (or versions of norms)")

        Container("backend", "API Server", "Java, Spring Boot", "Provides functionality via a HTTPS/JSON API")
        Rel("frontend", "backend", "Makes API calls to", "HTTPS/JSON/XML")

        ContainerDb("relationalDB", "Relational DB", "PostgreSQL", "Stores process data")
        Rel("backend", "relationalDB", "Reads from and writes to", "SQL/TCP")
      }


      UpdateLayoutConfig($c4ShapeInRow="2", $c4BoundaryInRow="2")

```
