package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class MetadatenDsTest {

  @Test
  void getFnaAtDate() {
    final MetadatenDs metadatenDs =
        MetadatenDs.builder()
            .node(
                XmlMapper.toNode(
                    """
                                <meta:legalDocML.de_metadaten_ds xmlns:meta="http://DS.Metadaten.LegalDocML.de/1.6/">
                                    <meta:fna start="1990-01-01" end="1994-12-31">111-11-1</meta:fna>
                                    <meta:fna start="1995-01-01" end="2000-12-31">222-22-2</meta:fna>
                                    <meta:fna start="2001-01-01" end="unbestimmt">333-33-3</meta:fna>
                                </meta:legalDocML.de_metadaten_ds>
                            """))
            .build();

    assertThat(
            metadatenDs.getSimpleMetadatum(
                MetadatenDs.SimpleMetadatum.FNA, LocalDate.parse("1980-01-01")))
        .isEmpty();

    assertThat(
            metadatenDs.getSimpleMetadatum(
                MetadatenDs.SimpleMetadatum.FNA, LocalDate.parse("1990-01-01")))
        .contains("111-11-1");
    assertThat(
            metadatenDs.getSimpleMetadatum(
                MetadatenDs.SimpleMetadatum.FNA, LocalDate.parse("1992-01-01")))
        .contains("111-11-1");
    assertThat(
            metadatenDs.getSimpleMetadatum(
                MetadatenDs.SimpleMetadatum.FNA, LocalDate.parse("1994-12-31")))
        .contains("111-11-1");

    assertThat(
            metadatenDs.getSimpleMetadatum(
                MetadatenDs.SimpleMetadatum.FNA, LocalDate.parse("1995-01-01")))
        .contains("222-22-2");
    assertThat(
            metadatenDs.getSimpleMetadatum(
                MetadatenDs.SimpleMetadatum.FNA, LocalDate.parse("1998-01-01")))
        .contains("222-22-2");
    assertThat(
            metadatenDs.getSimpleMetadatum(
                MetadatenDs.SimpleMetadatum.FNA, LocalDate.parse("2000-12-31")))
        .contains("222-22-2");

    assertThat(
            metadatenDs.getSimpleMetadatum(
                MetadatenDs.SimpleMetadatum.FNA, LocalDate.parse("2001-01-01")))
        .contains("333-33-3");
    assertThat(
            metadatenDs.getSimpleMetadatum(
                MetadatenDs.SimpleMetadatum.FNA, LocalDate.parse("2024-01-01")))
        .contains("333-33-3");
  }

  static Stream<Arguments> provideTestArguments() {
    return Stream.of(
        Arguments.of("000-00-0", "000-00-0"), Arguments.of(null, ""), Arguments.of("", ""));
  }

  @ParameterizedTest
  @MethodSource("provideTestArguments")
  void setFnaAtDateUpdate(final String updateValue, final String expectedValue) {
    final MetadatenDs metadatenDs =
        MetadatenDs.builder()
            .node(
                XmlMapper.toNode(
                    """
                                              <meta:legalDocML.de_metadaten_ds xmlns:meta="http://DS.Metadaten.LegalDocML.de/1.6/">
                                                  <meta:fna start="1990-01-01" end="1994-12-31">111-11-1</meta:fna>
                                                  <meta:fna start="1995-01-01" end="2000-12-31">222-22-2</meta:fna>
                                                  <meta:fna start="2001-01-01" end="unbestimmt">333-33-3</meta:fna>
                                              </meta:legalDocML.de_metadaten_ds>
                                          """))
            .build();

    final LocalDate newDate = LocalDate.parse("1990-01-01");
    assertThat(metadatenDs.getNodes("./fna")).hasSize(3);
    assertThat(metadatenDs.getSimpleMetadatum(MetadatenDs.SimpleMetadatum.FNA, newDate))
        .contains("111-11-1");

    metadatenDs.setSimpleMetadatum(MetadatenDs.SimpleMetadatum.FNA, newDate, updateValue);

    assertThat(metadatenDs.getSimpleMetadatum(MetadatenDs.SimpleMetadatum.FNA, newDate))
        .contains(expectedValue);
    assertThat(metadatenDs.getNodes("./fna")).hasSize(3);
  }

  @Test
  void setFnaAtDateCreateWithoutEnd() {
    final MetadatenDs metadatenDs =
        MetadatenDs.builder()
            .node(
                XmlMapper.toNode(
                    """
                                                              <meta:legalDocML.de_metadaten_ds xmlns:meta="http://DS.Metadaten.LegalDocML.de/1.6/">
                                                              </meta:legalDocML.de_metadaten_ds>
                                                          """))
            .build();

    final LocalDate newDate = LocalDate.parse("1980-01-01");

    assertThat(metadatenDs.getSimpleMetadatum(MetadatenDs.SimpleMetadatum.FNA, newDate)).isEmpty();
    assertThat(metadatenDs.getNodes("./fna")).isEmpty();

    metadatenDs.setSimpleMetadatum(MetadatenDs.SimpleMetadatum.FNA, newDate, "000-00-0");

    assertThat(metadatenDs.getSimpleMetadatum(MetadatenDs.SimpleMetadatum.FNA, newDate))
        .contains("000-00-0");
    assertThat(metadatenDs.getNodes("./fna")).hasSize(1);

    metadatenDs.getNodes("./fna").stream()
        .filter(f -> f.getStart().isPresent() && f.getStart().get().isEqual(newDate))
        .findFirst()
        .map(m -> assertThat(m.getEnd()).contains(LocalDate.MAX));
  }

  @Test
  void setFnaAtDateCreateWitEnd() {
    final MetadatenDs metadatenDs =
        MetadatenDs.builder()
            .node(
                XmlMapper.toNode(
                    """
                                              <meta:legalDocML.de_metadaten_ds xmlns:meta="http://DS.Metadaten.LegalDocML.de/1.6/">
                                                  <meta:fna start="1990-01-01" end="1994-12-31">111-11-1</meta:fna>
                                                  <meta:fna start="1995-01-01" end="2000-12-31">222-22-2</meta:fna>
                                                  <meta:fna start="2001-01-01" end="unbestimmt">333-33-3</meta:fna>
                                              </meta:legalDocML.de_metadaten_ds>
                                          """))
            .build();

    final LocalDate newDate = LocalDate.parse("1980-01-01");
    assertThat(metadatenDs.getSimpleMetadatum(MetadatenDs.SimpleMetadatum.FNA, newDate)).isEmpty();
    assertThat(metadatenDs.getNodes("./fna")).hasSize(3);

    metadatenDs.setSimpleMetadatum(MetadatenDs.SimpleMetadatum.FNA, newDate, "000-00-0");

    assertThat(metadatenDs.getSimpleMetadatum(MetadatenDs.SimpleMetadatum.FNA, newDate))
        .contains("000-00-0");
    assertThat(metadatenDs.getNodes("./fna")).hasSize(4);

    metadatenDs.getNodes("./fna").stream()
        .filter(f -> f.getStart().isPresent() && f.getStart().get().isEqual(newDate))
        .findFirst()
        .map(m -> assertThat(m.getEnd()).contains(LocalDate.parse("1989-12-31")));
  }

  @Test
  void setFnaAtDateCreateAndSetEndPrevious() {
    final MetadatenDs metadatenDs =
        MetadatenDs.builder()
            .node(
                XmlMapper.toNode(
                    """
                                                              <meta:legalDocML.de_metadaten_ds xmlns:meta="http://DS.Metadaten.LegalDocML.de/1.6/">
                                                                  <meta:fna start="1990-01-01" end="1994-12-31">111-11-1</meta:fna>
                                                                  <meta:fna start="1995-01-01" end="2000-12-31">222-22-2</meta:fna>
                                                                  <meta:fna start="2001-01-01" end="unbestimmt">333-33-3</meta:fna>
                                                              </meta:legalDocML.de_metadaten_ds>
                                                          """))
            .build();

    final LocalDate newDate = LocalDate.parse("2005-01-01");
    assertThat(metadatenDs.getSimpleMetadatum(MetadatenDs.SimpleMetadatum.FNA, newDate))
        .contains("333-33-3");
    assertThat(metadatenDs.getNodes("./fna")).hasSize(3);

    metadatenDs.setSimpleMetadatum(MetadatenDs.SimpleMetadatum.FNA, newDate, "000-00-0");

    assertThat(metadatenDs.getSimpleMetadatum(MetadatenDs.SimpleMetadatum.FNA, newDate))
        .contains("000-00-0");
    final List<SimpleProprietary> fnaValues = metadatenDs.getNodes("./fna");
    assertThat(fnaValues).hasSize(4);

    fnaValues.stream()
        .filter(f -> f.getStart().isPresent() && f.getStart().get().isEqual(newDate))
        .findFirst()
        .map(m -> assertThat(m.getEnd()).contains(LocalDate.MAX));

    fnaValues.stream()
        .filter(
            f ->
                f.getStart().isPresent()
                    && f.getStart().get().isEqual(LocalDate.parse("2001-01-01")))
        .findFirst()
        .map(m -> assertThat(m.getEnd()).contains(newDate.minusDays(1)));
  }

  @Test
  void getSubtypAtDate() {
    final MetadatenDs metadatenDs =
        MetadatenDs.builder()
            .node(
                XmlMapper.toNode(
                    """
                                                <meta:legalDocML.de_metadaten_ds xmlns:meta="http://DS.Metadaten.LegalDocML.de/1.6/">
                                                    <meta:subtyp>subtyp0</meta:subtyp>
                                                    <meta:subtyp start="1990-01-01" end="1994-12-31">subtyp1</meta:subtyp>
                                                    <meta:subtyp start="1995-01-01" end="2000-12-31">subtyp2</meta:subtyp>
                                                    <meta:subtyp start="2001-01-01" end="unbestimmt">subtyp3</meta:subtyp>
                                                </meta:legalDocML.de_metadaten_ds>
                                            """))
            .build();

    assertThat(
            metadatenDs.getSimpleMetadatum(
                MetadatenDs.SimpleMetadatum.SUBTYP, LocalDate.parse("1980-01-01")))
        .contains("subtyp0");

    assertThat(
            metadatenDs.getSimpleMetadatum(
                MetadatenDs.SimpleMetadatum.SUBTYP, LocalDate.parse("1990-01-01")))
        .contains("subtyp1");
    assertThat(
            metadatenDs.getSimpleMetadatum(
                MetadatenDs.SimpleMetadatum.SUBTYP, LocalDate.parse("1992-01-01")))
        .contains("subtyp1");
    assertThat(
            metadatenDs.getSimpleMetadatum(
                MetadatenDs.SimpleMetadatum.SUBTYP, LocalDate.parse("1994-12-31")))
        .contains("subtyp1");

    assertThat(
            metadatenDs.getSimpleMetadatum(
                MetadatenDs.SimpleMetadatum.SUBTYP, LocalDate.parse("1995-01-01")))
        .contains("subtyp2");
    assertThat(
            metadatenDs.getSimpleMetadatum(
                MetadatenDs.SimpleMetadatum.SUBTYP, LocalDate.parse("1998-01-01")))
        .contains("subtyp2");
    assertThat(
            metadatenDs.getSimpleMetadatum(
                MetadatenDs.SimpleMetadatum.SUBTYP, LocalDate.parse("2000-12-31")))
        .contains("subtyp2");

    assertThat(
            metadatenDs.getSimpleMetadatum(
                MetadatenDs.SimpleMetadatum.SUBTYP, LocalDate.parse("2001-01-01")))
        .contains("subtyp3");
    assertThat(
            metadatenDs.getSimpleMetadatum(
                MetadatenDs.SimpleMetadatum.SUBTYP, LocalDate.parse("2024-01-01")))
        .contains("subtyp3");
  }

  @Test
  void setSubtypAtDateCreateAndSetDefaultWithEnd() {
    final MetadatenDs metadatenDs =
        MetadatenDs.builder()
            .node(
                XmlMapper.toNode(
                    """
                                            <meta:legalDocML.de_metadaten_ds xmlns:meta="http://DS.Metadaten.LegalDocML.de/1.6/">
                                                <meta:subtyp>subtyp0</meta:subtyp>
                                            </meta:legalDocML.de_metadaten_ds>
                                            """))
            .build();

    final LocalDate newDate = LocalDate.parse("2005-01-01");
    assertThat(metadatenDs.getSimpleMetadatum(MetadatenDs.SimpleMetadatum.SUBTYP, newDate))
        .contains("subtyp0");
    assertThat(metadatenDs.getNodes("./subtyp")).hasSize(1);

    metadatenDs.setSimpleMetadatum(MetadatenDs.SimpleMetadatum.SUBTYP, newDate, "subtyp1");

    assertThat(metadatenDs.getSimpleMetadatum(MetadatenDs.SimpleMetadatum.SUBTYP, newDate))
        .contains("subtyp1");
    final List<SimpleProprietary> subtypValues = metadatenDs.getNodes("./subtyp");
    assertThat(subtypValues).hasSize(2);

    subtypValues.stream()
        .filter(f -> f.getStart().isPresent() && f.getStart().get().isEqual(newDate))
        .findFirst()
        .map(m -> assertThat(m.getEnd()).contains(LocalDate.MAX));

    assertThat(
            metadatenDs.getSimpleMetadatum(
                MetadatenDs.SimpleMetadatum.SUBTYP, newDate.minusDays(1)))
        .contains("subtyp0");
  }

  @Nested
  class Attributes {

    @Test
    void getAttributeAtDate() {
      final MetadatenDs metadatenDs =
          MetadatenDs.builder()
              .node(
                  XmlMapper.toNode(
                      """
                                                  <meta:legalDocML.de_metadaten_ds xmlns:meta="http://DS.Metadaten.LegalDocML.de/1.6/">
                                                      <meta:beschliessendesOrgan qualifizierteMehrheit="true" start="1990-01-01" end="1994-12-31">organ 1</meta:beschliessendesOrgan>
                                                      <meta:beschliessendesOrgan qualifizierteMehrheit="false" start="1995-01-01" end="2000-12-31">organ 2</meta:beschliessendesOrgan>
                                                      <meta:beschliessendesOrgan qualifizierteMehrheit="true" start="2001-01-01" end="unbestimmt">organ 3</meta:beschliessendesOrgan>
                                                  </meta:legalDocML.de_metadaten_ds>
                                              """))
              .build();

      assertThat(
              metadatenDs.getAttributeOfSimpleMetadatumAt(
                  MetadatenDs.Attribute.QUALIFIZIERTE_MEHRHEIT, LocalDate.parse("1980-01-01")))
          .isEmpty();

      assertThat(
              metadatenDs.getAttributeOfSimpleMetadatumAt(
                  MetadatenDs.Attribute.QUALIFIZIERTE_MEHRHEIT, LocalDate.parse("1990-01-01")))
          .contains("true");
      assertThat(
              metadatenDs.getAttributeOfSimpleMetadatumAt(
                  MetadatenDs.Attribute.QUALIFIZIERTE_MEHRHEIT, LocalDate.parse("1992-01-01")))
          .contains("true");
      assertThat(
              metadatenDs.getAttributeOfSimpleMetadatumAt(
                  MetadatenDs.Attribute.QUALIFIZIERTE_MEHRHEIT, LocalDate.parse("1994-12-31")))
          .contains("true");

      assertThat(
              metadatenDs.getAttributeOfSimpleMetadatumAt(
                  MetadatenDs.Attribute.QUALIFIZIERTE_MEHRHEIT, LocalDate.parse("1995-01-01")))
          .contains("false");
      assertThat(
              metadatenDs.getAttributeOfSimpleMetadatumAt(
                  MetadatenDs.Attribute.QUALIFIZIERTE_MEHRHEIT, LocalDate.parse("1998-01-01")))
          .contains("false");
      assertThat(
              metadatenDs.getAttributeOfSimpleMetadatumAt(
                  MetadatenDs.Attribute.QUALIFIZIERTE_MEHRHEIT, LocalDate.parse("2000-12-31")))
          .contains("false");

      assertThat(
              metadatenDs.getAttributeOfSimpleMetadatumAt(
                  MetadatenDs.Attribute.QUALIFIZIERTE_MEHRHEIT, LocalDate.parse("2001-01-01")))
          .contains("true");
      assertThat(
              metadatenDs.getAttributeOfSimpleMetadatumAt(
                  MetadatenDs.Attribute.QUALIFIZIERTE_MEHRHEIT, LocalDate.parse("2024-01-01")))
          .contains("true");
    }

    @Test
    void setAttributeAtDateUpdate() {
      final MetadatenDs metadatenDs =
          MetadatenDs.builder()
              .node(
                  XmlMapper.toNode(
                      """
                                                  <meta:legalDocML.de_metadaten_ds xmlns:meta="http://DS.Metadaten.LegalDocML.de/1.6/">
                                                      <meta:beschliessendesOrgan qualifizierteMehrheit="true" start="1990-01-01" end="1994-12-31">organ 1</meta:beschliessendesOrgan>
                                                  </meta:legalDocML.de_metadaten_ds>
                                              """))
              .build();

      final LocalDate newDate = LocalDate.parse("1990-01-01");
      assertThat(
              metadatenDs.getAttributeOfSimpleMetadatumAt(
                  MetadatenDs.Attribute.QUALIFIZIERTE_MEHRHEIT, newDate))
          .contains("true");

      metadatenDs.setAttributeOfSimpleMetadatum(
          MetadatenDs.Attribute.QUALIFIZIERTE_MEHRHEIT, newDate, "false");

      assertThat(
              metadatenDs.getAttributeOfSimpleMetadatumAt(
                  MetadatenDs.Attribute.QUALIFIZIERTE_MEHRHEIT, newDate))
          .contains("false");
    }

    @Test
    void setAttributeAtDateCreate() {
      final MetadatenDs metadatenDs =
          MetadatenDs.builder()
              .node(
                  XmlMapper.toNode(
                      """
                                                  <meta:legalDocML.de_metadaten_ds xmlns:meta="http://DS.Metadaten.LegalDocML.de/1.6/">
                                                      <meta:beschliessendesOrgan start="1990-01-01" end="1994-12-31">organ 1</meta:beschliessendesOrgan>
                                                  </meta:legalDocML.de_metadaten_ds>
                                              """))
              .build();

      final LocalDate newDate = LocalDate.parse("1990-01-01");
      assertThat(
              metadatenDs.getAttributeOfSimpleMetadatumAt(
                  MetadatenDs.Attribute.QUALIFIZIERTE_MEHRHEIT, newDate))
          .isEmpty();

      metadatenDs.setAttributeOfSimpleMetadatum(
          MetadatenDs.Attribute.QUALIFIZIERTE_MEHRHEIT, newDate, "false");

      assertThat(
              metadatenDs.getAttributeOfSimpleMetadatumAt(
                  MetadatenDs.Attribute.QUALIFIZIERTE_MEHRHEIT, newDate))
          .contains("false");
    }

    @Test
    void setAttributeAtDateParentNotPresent() {
      final MetadatenDs metadatenDs =
          MetadatenDs.builder()
              .node(
                  XmlMapper.toNode(
                      """
                                                  <meta:legalDocML.de_metadaten_ds xmlns:meta="http://DS.Metadaten.LegalDocML.de/1.6/">
                                                      <meta:beschliessendesOrgan qualifizierteMehrheit="false" start="1995-01-01" end="2000-12-31">organ 2</meta:beschliessendesOrgan>
                                                      <meta:beschliessendesOrgan qualifizierteMehrheit="true" start="2001-01-01" end="unbestimmt">organ 3</meta:beschliessendesOrgan>
                                                  </meta:legalDocML.de_metadaten_ds>
                                              """))
              .build();

      final LocalDate newDate = LocalDate.parse("1990-01-01");
      assertThat(
              metadatenDs.getAttributeOfSimpleMetadatumAt(
                  MetadatenDs.Attribute.QUALIFIZIERTE_MEHRHEIT, newDate))
          .isEmpty();

      metadatenDs.setAttributeOfSimpleMetadatum(
          MetadatenDs.Attribute.QUALIFIZIERTE_MEHRHEIT, newDate, "true");

      assertThat(
              metadatenDs.getAttributeOfSimpleMetadatumAt(
                  MetadatenDs.Attribute.QUALIFIZIERTE_MEHRHEIT, newDate))
          .isEmpty();
    }

    @Test
    void removeAttributeAtValueNull() {
      final MetadatenDs metadatenDs =
          MetadatenDs.builder()
              .node(
                  XmlMapper.toNode(
                      """
                                                  <meta:legalDocML.de_metadaten_ds xmlns:meta="http://DS.Metadaten.LegalDocML.de/1.6/">
                                                      <meta:beschliessendesOrgan qualifizierteMehrheit="false" start="1995-01-01" end="2000-12-31">organ 2</meta:beschliessendesOrgan>
                                                      <meta:beschliessendesOrgan qualifizierteMehrheit="true" start="2001-01-01" end="unbestimmt"></meta:beschliessendesOrgan>
                                                  </meta:legalDocML.de_metadaten_ds>
                                              """))
              .build();

      final LocalDate newDate = LocalDate.parse("2002-01-01");
      assertThat(
              metadatenDs.getAttributeOfSimpleMetadatumAt(
                  MetadatenDs.Attribute.QUALIFIZIERTE_MEHRHEIT, newDate))
          .contains("true");

      // the 3rd parameter actually doesn't matter. It will remove the attribute because content of
      // beschliessendesOrgan is empty
      metadatenDs.setAttributeOfSimpleMetadatum(
          MetadatenDs.Attribute.QUALIFIZIERTE_MEHRHEIT, newDate, null);

      assertThat(
              metadatenDs.getAttributeOfSimpleMetadatumAt(
                  MetadatenDs.Attribute.QUALIFIZIERTE_MEHRHEIT, newDate))
          .isEmpty();
    }
  }
}
