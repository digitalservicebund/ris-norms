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
    var sampleXml = """
      <root>
      <akn:meta xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.2/" eId="meta-n1">
           <akn:p eId="meta-n1_text-n1">
               <akn:ref eId="meta-n1_text-n1_ref-n1"></akn:ref>
               <akn:ref eId="meta-n1_text-n1_ref-n2"></akn:ref>
           </akn:p>
           <akn:p eId="meta-n1_text-n2">
               <akn:ref eId="meta-n1_text-n2_ref-n1"></akn:ref>
               <akn:ref eId="meta-n1_text-n2_ref-n2"></akn:ref>
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
    var sampleXml = """
      <root>
      <akn:meta xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.2/" eId="meta-n1">
           <akn:p eId="meta-n1_text-n1">
               <akn:ref eId="meta-n1_text-n1_ref-n1"></akn:ref>
               <akn:ref eId="meta-n1_text-n1_ref-n2"></akn:ref>
           </akn:p>
           <akn:p eId="meta-n1_text-n3">
               <akn:ref eId="meta-n1_text-n3_ref-n1"></akn:ref>
               <akn:ref eId="meta-n1_text-n3_ref-n4"></akn:ref>
           </akn:p>
        </akn:meta>
      </root>
      """;

    // When
    final Document correctedDocument = XmlMapper.toDocument(sampleXml);
    EidConsistencyGuardian.correctEids(correctedDocument);

    // Then
    var expectedXml = """
      <root>
      <akn:meta xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.2/" eId="meta-n1">
           <akn:p eId="meta-n1_text-n1">
               <akn:ref eId="meta-n1_text-n1_ref-n1"></akn:ref>
               <akn:ref eId="meta-n1_text-n1_ref-n2"></akn:ref>
           </akn:p>
           <akn:p eId="meta-n1_text-n2">
               <akn:ref eId="meta-n1_text-n2_ref-n1"></akn:ref>
               <akn:ref eId="meta-n1_text-n2_ref-n2"></akn:ref>
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
    var sampleXml = """
          <root>
      <akn:meta xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.2/" eId="meta-n1">
               <akn:p eId="meta-n1_text-n2">
                   <akn:ref eId="meta-n1_text-n2_ref-n1"></akn:ref>
                   <akn:ref eId="meta-n1_text-n2_ref-n2"></akn:ref>
               </akn:p>
               <akn:p eId="meta-n1_text-n1">
                 <akn:ref eId="meta-n1_text-n1_ref-n1"></akn:ref>
                 <akn:ref eId="meta-n1_text-n1_ref-n2"></akn:ref>
               </akn:p>
            </akn:meta>
          </root>
      """;

    // When
    final Document correctedDocument = XmlMapper.toDocument(sampleXml);
    EidConsistencyGuardian.correctEids(correctedDocument);

    // Then
    var expectedXml = """
          <root>
      <akn:meta xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.2/" eId="meta-n1">
               <akn:p eId="meta-n1_text-n1">
                   <akn:ref eId="meta-n1_text-n1_ref-n1"></akn:ref>
                   <akn:ref eId="meta-n1_text-n1_ref-n2"></akn:ref>
               </akn:p>
               <akn:p eId="meta-n1_text-n2">
                   <akn:ref eId="meta-n1_text-n2_ref-n1"></akn:ref>
                   <akn:ref eId="meta-n1_text-n2_ref-n2"></akn:ref>
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
    var sampleXml = """
          <root>
      <akn:meta xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.2/" eId="meta-n1">
               <akn:p eId="meta-n1_text-n1">
                   <akn:ref eId="meta-n1_text-n1_text-n1"></akn:ref>
                   <akn:ref eId="meta-n1_text-n1_text-n2"></akn:ref>
               </akn:p>
            </akn:meta>
          </root>
      """;

    // When
    final Document correctedDocument = XmlMapper.toDocument(sampleXml);
    EidConsistencyGuardian.correctEids(correctedDocument);

    // Then
    var expectedXml = """
          <root>
      <akn:meta xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.2/" eId="meta-n1">
               <akn:p eId="meta-n1_text-n1">
                   <akn:ref eId="meta-n1_text-n1_ref-n1"></akn:ref>
                   <akn:ref eId="meta-n1_text-n1_ref-n2"></akn:ref>
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
    var sampleXml = """
          <root>
      <akn:meta xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.2/" eId="meta-n1">
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
    var expectedXml = """
          <root>
      <akn:meta xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.2/" eId="meta-n1">
               <akn:p eId="meta-n1_text-n1">
                   <akn:ref eId="meta-n1_text-n1_ref-n1"></akn:ref>
                   <akn:ref eId="meta-n1_text-n1_ref-n2"></akn:ref>
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
      "complex-ldml-with-incorrect-eids/regelungstext-verkuendung-1.xml"
    );

    // When
    final Document correctedDocument = XmlMapper.toDocument(text);
    EidConsistencyGuardian.eliminateDeadReferences(correctedDocument);
    EidConsistencyGuardian.correctEids(correctedDocument);

    // Then
    var exectedResult = Fixtures.loadTextFromDisk(
      EidConsistencyGuardianTest.class,
      "complex-ldml-with-corrected-eids/regelungstext-verkuendung-1.xml"
    );

    final Diff diff = DiffBuilder.compare(Input.from(correctedDocument))
      .withTest(Input.from(XmlMapper.toDocument(exectedResult)))
      .ignoreWhitespace()
      .build();

    assertThat(diff.hasDifferences()).isFalse();
  }
}
