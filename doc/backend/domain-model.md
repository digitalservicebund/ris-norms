
classDiagram
direction BT
class Analysis {
- Element element
  }
  class Announcement {
- NormExpressionEli eli
- List~Release~ releases
  }
  class Article {
- String AFFECTED_DOCUMENT_XPATH
- Element element
  }

class BinaryFile {
- DokumentManifestationEli dokumentManifestationEli
- byte[] content
  }
  class CharacterRange {
  ~ int ABSOLUTE_POSITION_OF_START
- String characterRange
  ~ int ABSOLUTE_POSITION_OF_END
  }
  class Dokument {
  <<abstract>>
- Document document
  }
  class DokumentEli {
  <<Interface>>
  }

class DokumentExpressionEli {
- String agent
- String year
- String naturalIdentifier
- String subtype
- Integer version
- String language
- LocalDate pointInTime
  }
  class DokumentManifestationEli {
- String agent
- String naturalIdentifier
- Integer version
- String format
- String year
- String language
- LocalDate pointInTime
- LocalDate pointInTimeManifestation
- String subtype
  }
  class DokumentWorkEli {
- String naturalIdentifier
- String year
- String subtype
- String agent
  }
  class EId {
- String value
  }
  class EIdPart {
- String value
  }
  class EIdPartType {
  <<enumeration>>
+  META
+  SCHLUSSTEXT
+  TABELLE
+  FRBRCOUNTRY
+  UNTERGL
   %% Further values omitted%%
   }
   class Einzelelement
   class EventRef {
- Element element
  }
  class EventRefType {
  <<enumeration>>
  ~ String value
+  GENERATION
+  AMENDMENT
+  REPEAL
   }
   class FRBR {
- Element element
- String VALUE_ATTIBUTE
  }
  class FRBRExpression
  class FRBRManifestation
  class FRBRWork
  class Href {
  ~ int ABSOLUTE_POSITION_OF_EID
  ~ int RELATIVE_POSITION_OF_CHARACTER_RANGE
- String value
  ~ int ABSOLUTE_POSITION_OF_CHARACTER_RANGE
  ~ int NUMBER_OF_ELI_PARTS
  ~ int RELATIVE_POSITION_OF_EID
  }
  class Lifecycle {
- Element element
  }
  class Meta {
- Element element
- String ATTRIBUTSEMANTIK_NOCH_UNDEFINIERT
- String SOURCE_ATTIBUTE
  }
  class MetadataInterface {
  <<Interface>>

}
class Metadaten~T~ {
- String startAttribute
- Element element
- String endAttribute
- Namespace namespace
  }
  class MetadatenBund
  class MetadatenDe
  class MetadatenDs
  class MigrationLog {
- Instant createdAt
- Integer size
  }
  class Mod {
- String REF_XPATH
- String RREF_XPATH
- Element element
  }
  class Namespace {
  <<enumeration>>
+  METADATEN_BUNDESREGIERUNG
- String namespaceUri
+  METADATEN_RIS
+  INHALTSDATEN
+  METADATEN
- String prefix
  }
  class Norm {
  +NormPublishState publishState
  +Set<Dokument> dokumente
  +Set<BinaryFile> binaryFiles
  }
  class NormEli {
  <<Interface>>

}
class NormExpressionEli {
- String year
- String naturalIdentifier
- String agent
- LocalDate pointInTime
- Integer version
- String language
  }
  class NormManifestationEli {
- LocalDate pointInTimeManifestation
- String year
- String agent
- String language
- Integer version
- LocalDate pointInTime
- String naturalIdentifier
  }
  class NormPublishState {
  <<enumeration>>
+  UNPUBLISHED
+  PUBLISHED
+  QUEUED_FOR_PUBLISH
   }
   class NormWorkEli {
- String naturalIdentifier
- String year
- String agent
  }
  class OffeneStruktur
  class Proprietary {
- Element element
  }
  class Regelungstext
  class Release {
- Instant releasedAt
- List~Norm~ publishedNorms
  }
  class SimpleProprietary {
- Element element
  }
  class TemporalData {
- Element element
  }
  class TemporalGroup {
- Element element
  }
  class TextualMod {
- Element element
  }
  class TimeBoundary {
- EventRef eventRef
- TemporalGroup temporalGroup
- TimeInterval timeInterval
  }
  class TimeBoundaryChangeData {
- String eid
- LocalDate date
  }
  class TimeInterval {
- Element element
  }


NormExpressionEli  ..|>  NormEli
NormManifestationEli  ..|>  NormEli
NormWorkEli  ..|>  NormEli

DokumentWorkEli ..|> DokumentEli
DokumentExpressionEli ..|> DokumentEli
DokumentManifestationEli ..|> DokumentEli

FRBRWork --|> FRBR
FRBRExpression --|> FRBR
FRBRManifestation --|> FRBR

Regelungstext --|> Dokument
OffeneStruktur --|> Dokument

Metadaten~T~  ..|>  MetadataInterface
MetadatenDe --|> Metadaten~T~
MetadatenDs --|> Metadaten~T~
MetadatenBund --|> Metadaten~T~
Einzelelement --|> Metadaten~T~
MetadatenDe "1" *-- "many" SimpleProprietary

Norm "1" *-- "1" NormPublishState
Norm "1" *-- "many" Dokument
Norm "1" *-- "many" BinaryFile

Announcement "1" -- "1" NormExpressionEli
Announcement "1" *-- "many" Release

Dokument "1" *-- "1" DokumentWorkEli
Dokument "1" *-- "1" DokumentExpressionEli
Dokument "1" -- "1" DokumentManifestationEli
BinaryFile "1" -- "1" DokumentManifestationEli

Regelungstext "1" *-- "many" Article

EId "1" *-- "many" EIdPart
Href "1" *-- "1" CharacterRange
TextualMod "1" -- "1" Href
TimeInterval "1" -- "1" Href

Dokument "1" *-- "1" Meta
Meta "1" *-- "1" FRBRWork
Meta "1" *-- "1" FRBRExpression
Meta "1" *-- "1" FRBRManifestation
Meta "1" o-- "0..1" Analysis
Analysis "1" *-- "many" TextualMod
Meta "1" *-- "1" Lifecycle
Lifecycle "1" *-- "many" EventRef
Meta "1" *-- "1" TemporalData
TemporalData "1" *-- "many" TemporalGroup
TemporalGroup "1" *-- "1" TimeInterval
Meta "1" o-- "0..1" Proprietary

Regelungstext "0" *-- "many" Mod
Article "1" o-- "0..1" Mod

FRBR "1" -- "1" Namespace
Dokument "0" *-- "many" TimeBoundary
