package de.bund.digitalservice.ris.norms.utils;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.domain.entity.Fixtures;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.builder.Input;
import org.xmlunit.diff.Diff;

class EidConsistencyGuardianTest {

  @Test
  void itDoesNotCorrectAnything() {
    var sampleXml =
      """
      <root>
      <akn:meta xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" eId="meta-1">
           <akn:p eId="meta-1_text-1">
               <akn:ref eId="meta-1_text-1_ref-1"></akn:ref>
               <akn:ref eId="meta-1_text-1_ref-2"></akn:ref>
           </akn:p>
           <akn:p eId="meta-1_text-2">
               <akn:ref eId="meta-1_text-2_ref-1"></akn:ref>
               <akn:ref eId="meta-1_text-2_ref-2"></akn:ref>
           </akn:p>
        </akn:meta>
      </root>
      """;

    // When
    final Document document = XmlMapper.toDocument(sampleXml);
    final Document correctedDocument = XmlMapper.toDocument(sampleXml);
    EidConsistencyGuardian.correctEids(correctedDocument);

    // Then
    final Diff diff = DiffBuilder.compare(Input.from(correctedDocument))
      .withTest(Input.from(document))
      .ignoreWhitespace()
      .build();
    assertThat(diff.hasDifferences()).isFalse();
  }

  @Test
  void itCorrectsEidGaps() {
    var sampleXml =
      """
      <root>
      <akn:meta xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" eId="meta-1">
           <akn:p eId="meta-1_text-1">
               <akn:ref eId="meta-1_text-1_ref-1"></akn:ref>
               <akn:ref eId="meta-1_text-1_ref-2"></akn:ref>
           </akn:p>
           <akn:p eId="meta-1_text-3">
               <akn:ref eId="meta-1_text-3_ref-1"></akn:ref>
               <akn:ref eId="meta-1_text-3_ref-4"></akn:ref>
           </akn:p>
        </akn:meta>
      </root>
      """;

    // When
    final Document correctedDocument = XmlMapper.toDocument(sampleXml);
    EidConsistencyGuardian.correctEids(correctedDocument);

    // Then
    var expectedXml =
      """
      <root>
      <akn:meta xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" eId="meta-1">
           <akn:p eId="meta-1_text-1">
               <akn:ref eId="meta-1_text-1_ref-1"></akn:ref>
               <akn:ref eId="meta-1_text-1_ref-2"></akn:ref>
           </akn:p>
           <akn:p eId="meta-1_text-2">
               <akn:ref eId="meta-1_text-2_ref-1"></akn:ref>
               <akn:ref eId="meta-1_text-2_ref-2"></akn:ref>
           </akn:p>
        </akn:meta>
      </root>
      """;

    final Diff diff = DiffBuilder.compare(Input.from(correctedDocument))
      .withTest(Input.from(XmlMapper.toDocument(expectedXml)))
      .ignoreWhitespace()
      .build();
    assertThat(diff.hasDifferences()).isFalse();
  }

  @Test
  void itCorrectsEidOrder() {
    var sampleXml =
      """
          <root>
      <akn:meta xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" eId="meta-1">
               <akn:p eId="meta-1_text-2">
                   <akn:ref eId="meta-1_text-2_ref-1"></akn:ref>
                   <akn:ref eId="meta-1_text-2_ref-2"></akn:ref>
               </akn:p>
               <akn:p eId="meta-1_text-1">
                 <akn:ref eId="meta-1_text-1_ref-1"></akn:ref>
                 <akn:ref eId="meta-1_text-1_ref-2"></akn:ref>
               </akn:p>
            </akn:meta>
          </root>
      """;

    // When
    final Document correctedDocument = XmlMapper.toDocument(sampleXml);
    EidConsistencyGuardian.correctEids(correctedDocument);

    // Then
    var expectedXml =
      """
          <root>
      <akn:meta xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" eId="meta-1">
               <akn:p eId="meta-1_text-1">
                   <akn:ref eId="meta-1_text-1_ref-1"></akn:ref>
                   <akn:ref eId="meta-1_text-1_ref-2"></akn:ref>
               </akn:p>
               <akn:p eId="meta-1_text-2">
                   <akn:ref eId="meta-1_text-2_ref-1"></akn:ref>
                   <akn:ref eId="meta-1_text-2_ref-2"></akn:ref>
               </akn:p>
            </akn:meta>
          </root>
      """;

    final Diff diff = DiffBuilder.compare(Input.from(correctedDocument))
      .withTest(Input.from(XmlMapper.toDocument(expectedXml)))
      .ignoreWhitespace()
      .build();

    assertThat(diff.hasDifferences()).isFalse();
  }

  @Test
  void itCorrectsEidTypes() {
    var sampleXml =
      """
          <root>
      <akn:meta xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" eId="meta-1">
               <akn:p eId="meta-1_text-1">
                   <akn:ref eId="meta-1_text-1_text-1"></akn:ref>
                   <akn:ref eId="meta-1_text-1_text-2"></akn:ref>
               </akn:p>
            </akn:meta>
          </root>
      """;

    // When
    final Document correctedDocument = XmlMapper.toDocument(sampleXml);
    EidConsistencyGuardian.correctEids(correctedDocument);

    // Then
    var expectedXml =
      """
          <root>
      <akn:meta xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" eId="meta-1">
               <akn:p eId="meta-1_text-1">
                   <akn:ref eId="meta-1_text-1_ref-1"></akn:ref>
                   <akn:ref eId="meta-1_text-1_ref-2"></akn:ref>
               </akn:p>
            </akn:meta>
          </root>
      """;

    final Diff diff = DiffBuilder.compare(Input.from(correctedDocument))
      .withTest(Input.from(XmlMapper.toDocument(expectedXml)))
      .ignoreWhitespace()
      .build();

    assertThat(diff.hasDifferences()).isFalse();
  }

  @Test
  void itCorrectsMissingEids() {
    var sampleXml =
      """
          <root>
      <akn:meta xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" eId="meta-1">
               <akn:p>
                   <akn:ref></akn:ref>
                   <akn:ref></akn:ref>
               </akn:p>
            </akn:meta>
          </root>
      """;

    // When
    final Document correctedDocument = XmlMapper.toDocument(sampleXml);
    EidConsistencyGuardian.correctEids(correctedDocument);

    // Then
    var expectedXml =
      """
          <root>
      <akn:meta xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" eId="meta-1">
               <akn:p eId="meta-1_text-1">
                   <akn:ref eId="meta-1_text-1_ref-1"></akn:ref>
                   <akn:ref eId="meta-1_text-1_ref-2"></akn:ref>
               </akn:p>
            </akn:meta>
          </root>
      """;

    final Diff diff = DiffBuilder.compare(Input.from(correctedDocument))
      .withTest(Input.from(XmlMapper.toDocument(expectedXml)))
      .ignoreWhitespace()
      .build();

    assertThat(diff.hasDifferences()).isFalse();
  }

  @Test
  void itCorrectComplexLDML() {
    // Given
    var text = Fixtures.loadTextFromDisk(
      EidConsistencyGuardianTest.class,
      "complex-ldml-with-incorrect-eids.xml"
    );

    // When
    final Document correctedDocument = XmlMapper.toDocument(text);
    EidConsistencyGuardian.eliminateDeadReferences(correctedDocument);
    EidConsistencyGuardian.correctEids(correctedDocument);

    // Then
    var exectedResult = Fixtures.loadTextFromDisk(
      EidConsistencyGuardianTest.class,
      "complex-ldml-with-corrected-eids.xml"
    );

    final Diff diff = DiffBuilder.compare(Input.from(correctedDocument))
      .withTest(Input.from(XmlMapper.toDocument(exectedResult)))
      .ignoreWhitespace()
      .build();

    assertThat(diff.hasDifferences()).isFalse();
  }
}
