<!-- 

    Available Macros on the System Context level
    (cf. https://github.com/plantuml-stdlib/C4-PlantUML/blob/master/README.md#system-context--system-landscape-diagrams)

    Person(alias, label, ?descr, ?sprite, ?tags, ?link, ?type)
    Person_Ext(alias, label, ?descr, ?sprite, ?tags, ?link, ?type)
    System(alias, label, ?descr, ?sprite, ?tags, ?link, ?type, ?baseShape)
    SystemDb(alias, label, ?descr, ?sprite, ?tags, ?link, ?type)
    SystemQueue(alias, label, ?descr, ?sprite, ?tags, ?link, ?type)
    System_Ext(alias, label, ?descr, ?sprite, ?tags, ?link, ?type, ?baseShape)
    SystemDb_Ext(alias, label, ?descr, ?sprite, ?tags, ?link, ?type)
    SystemQueue_Ext(alias, label, ?descr, ?sprite, ?tags, ?link, ?type)
    Boundary(alias, label, ?type, ?tags, ?link)
    Enterprise_Boundary(alias, label, ?tags, ?link)
    System_Boundary(alias, label, ?tags, ?link) -->

```mermaid
C4Context
    title System Context: RIS-Norms

    Person("documentalist", "Documentalist", "A staff member of the<br> 'Normendokumentation (BfJ)'")

    System("ris-norms", "RIS Norms", "Allows users to<br> consolidate and annotate<br> amending laws<br> (later also: new norms)")
    Rel("documentalist", "ris-norms", "Adjusts new amending laws,<br> adjusts metadata <br> and publishes the new norms<br> (or versions of norms)")

    System("portal", "Portal", "Gives the public access to norms")
    Rel("ris-norms", "portal", "Makes new norms accessible to the portal")

    Person("public", "General public, lawyer, law maker,<br> public servant, private enterprise...", "Searches, reads or processes norms for<br> private or professional reasons")
    Rel("public", "portal", "Searches, browses, <br>reads, downloads norms")

    UpdateLayoutConfig($c4ShapeInRow="1", $c4BoundaryInRow="2")

```