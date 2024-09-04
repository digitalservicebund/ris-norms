package de.bund.digitalservice.ris.norms.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import de.bund.digitalservice.ris.norms.application.exception.MalformedAttributeValidationException;
import de.bund.digitalservice.ris.norms.application.exception.MissingAttributeValidationException;
import de.bund.digitalservice.ris.norms.application.exception.ValidationException;
import de.bund.digitalservice.ris.norms.application.port.output.LoadNormPort;
import de.bund.digitalservice.ris.norms.domain.entity.Analysis;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.domain.entity.NormFixtures;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class AmendingLawValidatorTest {

  private final LoadNormPort loadNormPort = mock(LoadNormPort.class);
  private final LoadZf0Service loadZf0Service = mock(LoadZf0Service.class);
  private final SingleModValidator singleModValidator = mock(SingleModValidator.class);

  private final AmendingLawValidator underTest =
      new AmendingLawValidator(loadNormPort, loadZf0Service, singleModValidator);

  @Test
  void emptyActiveModificationDestinationHref() {
    // given
    final Norm amendingNorm = NormFixtures.loadFromDisk("NormWithMods.xml");
    amendingNorm
        .getNodeByEId("hauptteil-1_art-1")
        .get()
        .getAttributes()
        .getNamedItem("refersTo")
        .setTextContent("");

    // when
    Throwable thrown = catchThrowable(() -> underTest.destinationIsSet(amendingNorm));

    // then
    assertThat(thrown)
        .isInstanceOf(MissingAttributeValidationException.class)
        .hasMessageContaining(
            "In the norm eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1 the element with eId hauptteil-1_art-1 is missing the attribute refersTo");
  }

  @Test
  void emptyArticleRefersTo() {
    // given
    final Norm amendingNorm =
        NormFixtures.loadFromDisk("NormWithEmptyActiveModificationDestinationHref.xml");

    // when
    Throwable thrown = catchThrowable(() -> underTest.destinationIsSet(amendingNorm));

    // then
    assertThat(thrown)
        .isInstanceOf(MissingAttributeValidationException.class)
        .hasMessageContaining(
            "In the norm eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1 the element with eId meta-1_analysis-1_activemod-1_textualmod-1 is missing the attribute href");
  }

  @Test
  void brokenActiveModificationDestinationHref() {
    // given
    final Norm amendingNorm = NormFixtures.loadFromDisk("NormWithMods.xml");
    amendingNorm
        .getMeta()
        .getAnalysis()
        .map(Analysis::getActiveModifications)
        .orElse(Collections.emptyList())
        .getFirst()
        .setDestinationHref("#THIS_IS_NOT_OK_A_HREF_IS_NEVER_RELATIVE");

    // when
    Throwable thrown = catchThrowable(() -> underTest.destinationIsSet(amendingNorm));

    // then
    assertThat(thrown)
        .isInstanceOf(MalformedAttributeValidationException.class)
        .hasMessageContaining(
            "In the norm eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1 the element with eId meta-1_analysis-1_activemod-1_textualmod-1 has a malformed attribute value #THIS_IS_NOT_OK_A_HREF_IS_NEVER_RELATIVE on attribute");
  }

  @Test
  void activeModificationTextualModHasNoEid() {
    // given
    final Norm amendingNorm = NormFixtures.loadFromDisk("NormWithMods.xml");
    amendingNorm
        .getMeta()
        .getAnalysis()
        .map(Analysis::getActiveModifications)
        .orElse(Collections.emptyList())
        .getFirst()
        .setDestinationHref("");

    amendingNorm
        .getMeta()
        .getAnalysis()
        .map(Analysis::getActiveModifications)
        .orElse(Collections.emptyList())
        .getFirst()
        .getNode()
        .getAttributes()
        .getNamedItem("eId")
        .setTextContent("");

    // when
    Throwable thrown = catchThrowable(() -> underTest.destinationIsSet(amendingNorm));

    // then
    assertThat(thrown)
        .isInstanceOf(MissingAttributeValidationException.class)
        .hasMessageContaining(
            "In the norm eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1 the element with eId  is missing the attribute href"); // TODO why is eid missing?
  }

  @Test
  void emptyAknModHrefEli() {

    // given
    final Norm amendingNorm = NormFixtures.loadFromDisk("NormWithMods.xml");
    amendingNorm
        .getArticles()
        .getFirst()
        .getMods()
        .getFirst()
        .setTargetRefHref("#THIS_IS_NOT_OK_A_HREF_IS_NEVER_RELATIVE");

    // when
    Throwable thrown = catchThrowable(() -> underTest.destinationIsSet(amendingNorm));

    // then
    assertThat(thrown)
        .isInstanceOf(MissingAttributeValidationException.class)
        .hasMessageContaining(
            "In the norm eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1 the element with eId hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1 is missing the attribute href");
  }

  @Test
  void emptyAffectedDocumentHref() {

    // given
    final Norm amendingNorm = NormFixtures.loadFromDisk("NormWithMods.xml");
    amendingNorm.getArticles().getFirst().setAffectedDocumentEli("");

    // when
    Throwable thrown = catchThrowable(() -> underTest.destinationIsSet(amendingNorm));

    // then
    assertThat(thrown)
        .isInstanceOf(ValidationException.class)
        .hasMessageContaining(
            "For norm with Eli (eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1): AffectedDocument href is empty in article with eId hauptteil-1_art-1");
  }

  @Test
  void destinationEliIsConsistent() {
    // given
    final Norm amendingNorm = NormFixtures.loadFromDisk("NormWithMods.xml");

    // when/then
    assertThatCode(() -> underTest.destinationEliIsConsistent(amendingNorm))
        .doesNotThrowAnyException();
  }

  @Test
  void ThrowExceptionIfDestinationEliIsNotConsistent() {
    // given
    final Norm amendingNorm = NormFixtures.loadFromDisk("NormWithInconsistentEli.xml");

    // when
    Throwable thrown = catchThrowable(() -> underTest.destinationEliIsConsistent(amendingNorm));

    // then
    assertThat(thrown)
        .isInstanceOf(ValidationException.class)
        .hasMessageContaining(
            "For norm with Eli (eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1): Elis are not consistent");
  }

  @Test
  void destinationHrefIsConsistent() {
    // given
    final Norm amendingNorm = NormFixtures.loadFromDisk("NormWithMods.xml");

    // when/then
    assertThatCode(() -> underTest.destinationHrefIsConsistent(amendingNorm))
        .doesNotThrowAnyException();
  }

  @Test
  void ThrowExceptionIfDestinationEidIsNotConsistent() {
    // given
    final Norm amendingNorm = NormFixtures.loadFromDisk("NormWithInconsistentEid.xml");

    // when
    Throwable thrown = catchThrowable(() -> underTest.destinationHrefIsConsistent(amendingNorm));

    // then
    assertThat(thrown)
        .isInstanceOf(ValidationException.class)
        .hasMessageContaining(
            "For norm with Eli (eli/bund/bgbl-1/2017/s419/2017-03-15/1/deu/regelungstext-1): Eids are not consistent");
  }

  @Test
  void validateXmlConsistency() {
    // given
    final Norm amendingNorm = NormFixtures.loadFromDisk("NormWithMods.xml");
    final Norm zf0Norm = NormFixtures.loadFromDisk("NormWithPassiveModifications.xml");
    when(loadNormPort.loadNorm(any())).thenReturn(Optional.of(amendingNorm));
    when(loadZf0Service.loadZf0(any(), any())).thenReturn(zf0Norm);

    // when/then
    assertThatCode(() -> underTest.validate(amendingNorm)).doesNotThrowAnyException();
  }
}
