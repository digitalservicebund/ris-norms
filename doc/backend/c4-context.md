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
      title System Context Diagram for RIS-Norms

    Enterprise_Boundary(boundaryBmJ, "BmJ"){
        Boundary(office1, "Office 1", "location"){
            Person(documentalistB, "Documentalist B")
            System(browserB, "Web Browser B", "on work PC B")
            Rel(documentalistB, browserB, "uses")

            Person(documentalistC, "Documentalist C")
            System(browserC, "Web Browser C", "on work PC C")
            Rel(documentalistC, browserC, "uses")
        }

        Boundary(office2, "Office 2", "location"){
            Person(documentalistA, "Documentalist A")
            System(browserA, "Web Browser A", "on work PC A")
            Rel(documentalistA, browserA, "uses")
        }

    }
        
    Enterprise_Boundary(boundaryDS, "DigitalService","restricted"){
        Boundary(boundaryRisNorms, "RIS Norms Web Application") {

            System(frontend, "Front end", "Single-Page-App")
            Rel(browserA, frontend, "visit, interact", "HTTPS")
            Rel(browserB, frontend, "visit, interact", "HTTPS")
            Rel(browserC, frontend, "visit, interact", "HTTPS")

            System(backend, "Back end", "Web Sever")
            Rel(frontend, backend, "sends / retrieves data", "HTTP")

            SystemDb(relationalDB, "Relational Database", "PostgreSQL")
            Rel(backend, relationalDB, "sends/retrieves data", "HTTPS")
        }
    }

UpdateLayoutConfig($c4ShapeInRow="4", $c4BoundaryInRow="2")
```