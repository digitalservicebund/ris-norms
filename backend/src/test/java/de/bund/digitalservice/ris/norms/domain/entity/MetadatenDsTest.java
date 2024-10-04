package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.utils.NodeParser;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class MetadatenDsTest {

  @Nested
  class getMetadatum {

    @Test
    void getFnaAtDate() {
      final MetadatenDs metadatenDs = MetadatenDs
        .builder()
        .node(
          XmlMapper.toNode(
            """
                <ris:legalDocML.de_metadaten xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.7/">
                    <ris:fna start="1990-01-01" end="1994-12-31">111-11-1</ris:fna>
                    <ris:fna start="1995-01-01" end="2000-12-31">222-22-2</ris:fna>
                    <ris:fna start="2001-01-01" end="unbestimmt">333-33-3</ris:fna>
                </ris:legalDocML.de_metadaten>
            """
          )
        )
        .build();

      assertThat(
        metadatenDs.getSimpleMetadatum(MetadatenDs.Metadata.FNA, LocalDate.parse("1980-01-01"))
      )
        .isEmpty();

      assertThat(
        metadatenDs.getSimpleMetadatum(MetadatenDs.Metadata.FNA, LocalDate.parse("1990-01-01"))
      )
        .contains("111-11-1");
      assertThat(
        metadatenDs.getSimpleMetadatum(MetadatenDs.Metadata.FNA, LocalDate.parse("1992-01-01"))
      )
        .contains("111-11-1");
      assertThat(
        metadatenDs.getSimpleMetadatum(MetadatenDs.Metadata.FNA, LocalDate.parse("1994-12-31"))
      )
        .contains("111-11-1");

      assertThat(
        metadatenDs.getSimpleMetadatum(MetadatenDs.Metadata.FNA, LocalDate.parse("1995-01-01"))
      )
        .contains("222-22-2");
      assertThat(
        metadatenDs.getSimpleMetadatum(MetadatenDs.Metadata.FNA, LocalDate.parse("1998-01-01"))
      )
        .contains("222-22-2");
      assertThat(
        metadatenDs.getSimpleMetadatum(MetadatenDs.Metadata.FNA, LocalDate.parse("2000-12-31"))
      )
        .contains("222-22-2");

      assertThat(
        metadatenDs.getSimpleMetadatum(MetadatenDs.Metadata.FNA, LocalDate.parse("2001-01-01"))
      )
        .contains("333-33-3");
      assertThat(
        metadatenDs.getSimpleMetadatum(MetadatenDs.Metadata.FNA, LocalDate.parse("2024-01-01"))
      )
        .contains("333-33-3");
    }
  }

  @Nested
  class setMetadatum {

    @Test
    void setFnaAtDateUpdate() {
      final MetadatenDs metadatenDs = MetadatenDs
        .builder()
        .node(
          XmlMapper.toNode(
            """
                <ris:legalDocML.de_metadaten xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.7/">
                    <ris:fna start="1990-01-01" end="1994-12-31">111-11-1</ris:fna>
                    <ris:fna start="1995-01-01" end="2000-12-31">222-22-2</ris:fna>
                    <ris:fna start="2001-01-01" end="unbestimmt">333-33-3</ris:fna>
                </ris:legalDocML.de_metadaten>
            """
          )
        )
        .build();

      final LocalDate newDate = LocalDate.parse("1990-01-01");
      assertThat(metadatenDs.getNodes(MetadatenDs.Metadata.FNA.getXpath())).hasSize(3);
      assertThat(metadatenDs.getSimpleMetadatum(MetadatenDs.Metadata.FNA, newDate))
        .contains("111-11-1");

      metadatenDs.updateSimpleMetadatum(MetadatenDs.Metadata.FNA, newDate, "000-00-0");

      assertThat(metadatenDs.getSimpleMetadatum(MetadatenDs.Metadata.FNA, newDate))
        .contains("000-00-0");
      assertThat(metadatenDs.getNodes(MetadatenDs.Metadata.FNA.getXpath())).hasSize(3);
    }

    @Test
    void setFnaAtDateCreateWithUnbestimmtEnd() {
      final MetadatenDs metadatenDs = MetadatenDs
        .builder()
        .node(
          XmlMapper.toNode(
            """
                <ris:legalDocML.de_metadaten xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.7/">
                </ris:legalDocML.de_metadaten>
            """
          )
        )
        .build();

      final LocalDate newDate = LocalDate.parse("1980-01-01");

      assertThat(metadatenDs.getSimpleMetadatum(MetadatenDs.Metadata.FNA, newDate)).isEmpty();
      assertThat(metadatenDs.getNodes(MetadatenDs.Metadata.FNA.getXpath())).isEmpty();

      metadatenDs.updateSimpleMetadatum(MetadatenDs.Metadata.FNA, newDate, "000-00-0");

      assertThat(metadatenDs.getSimpleMetadatum(MetadatenDs.Metadata.FNA, newDate))
        .contains("000-00-0");
      assertThat(metadatenDs.getNodes(MetadatenDs.Metadata.FNA.getXpath())).hasSize(1);

      metadatenDs
        .getNodes(MetadatenDs.Metadata.FNA.getXpath())
        .stream()
        .filter(f -> f.getStart().isPresent() && f.getStart().get().isEqual(newDate))
        .findFirst()
        .map(m -> assertThat(m.getEnd()).contains(LocalDate.MAX));
    }

    @Test
    void setFnaAtDateCreateWithEnd() {
      final MetadatenDs metadatenDs = MetadatenDs
        .builder()
        .node(
          XmlMapper.toNode(
            """
                <ris:legalDocML.de_metadaten xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.7/">
                    <ris:fna start="1990-01-01" end="1994-12-31">111-11-1</ris:fna>
                    <ris:fna start="1995-01-01" end="2000-12-31">222-22-2</ris:fna>
                    <ris:fna start="2001-01-01" end="unbestimmt">333-33-3</ris:fna>
                </ris:legalDocML.de_metadaten>
            """
          )
        )
        .build();

      final LocalDate newDate = LocalDate.parse("1980-01-01");
      assertThat(metadatenDs.getSimpleMetadatum(MetadatenDs.Metadata.FNA, newDate)).isEmpty();
      assertThat(metadatenDs.getNodes(MetadatenDs.Metadata.FNA.getXpath())).hasSize(3);

      metadatenDs.updateSimpleMetadatum(MetadatenDs.Metadata.FNA, newDate, "000-00-0");

      assertThat(metadatenDs.getSimpleMetadatum(MetadatenDs.Metadata.FNA, newDate))
        .contains("000-00-0");
      assertThat(metadatenDs.getNodes(MetadatenDs.Metadata.FNA.getXpath())).hasSize(4);

      metadatenDs
        .getNodes(MetadatenDs.Metadata.FNA.getXpath())
        .stream()
        .filter(f -> f.getStart().isPresent() && f.getStart().get().isEqual(newDate))
        .findFirst()
        .map(m -> assertThat(m.getEnd()).contains(LocalDate.parse("1989-12-31")));
    }

    @Test
    void setFnaAtDateCreateAndUpdatePreviousUnbestimmtEnd() {
      final MetadatenDs metadatenDs = MetadatenDs
        .builder()
        .node(
          XmlMapper.toNode(
            """
                <ris:legalDocML.de_metadaten xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.7/">
                    <ris:fna start="1990-01-01" end="1994-12-31">111-11-1</ris:fna>
                    <ris:fna start="1995-01-01" end="2000-12-31">222-22-2</ris:fna>
                    <ris:fna start="2001-01-01" end="unbestimmt">333-33-3</ris:fna>
                </ris:legalDocML.de_metadaten>
            """
          )
        )
        .build();

      final LocalDate newDate = LocalDate.parse("2005-01-01");
      assertThat(metadatenDs.getSimpleMetadatum(MetadatenDs.Metadata.FNA, newDate))
        .contains("333-33-3");
      assertThat(metadatenDs.getNodes(MetadatenDs.Metadata.FNA.getXpath())).hasSize(3);

      metadatenDs.updateSimpleMetadatum(MetadatenDs.Metadata.FNA, newDate, "000-00-0");

      assertThat(metadatenDs.getSimpleMetadatum(MetadatenDs.Metadata.FNA, newDate))
        .contains("000-00-0");
      final List<SimpleProprietary> fnaValues = metadatenDs.getNodes(
        MetadatenDs.Metadata.FNA.getXpath()
      );
      assertThat(fnaValues).hasSize(4);

      fnaValues
        .stream()
        .filter(f -> f.getStart().isPresent() && f.getStart().get().isEqual(newDate))
        .findFirst()
        .map(m -> assertThat(m.getEnd()).contains(LocalDate.MAX));

      fnaValues
        .stream()
        .filter(f ->
          f.getStart().isPresent() && f.getStart().get().isEqual(LocalDate.parse("2001-01-01"))
        )
        .findFirst()
        .map(m -> assertThat(m.getEnd()).contains(newDate.minusDays(1)));
    }

    @Test
    void setSubtypAtDateCreateAndSetDefaultWithEnd() {
      final MetadatenDs metadatenDs = MetadatenDs
        .builder()
        .node(
          XmlMapper.toNode(
            """
            <ris:legalDocML.de_metadaten xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.7/">
                <ris:subtyp>subtyp0</ris:subtyp>
            </ris:legalDocML.de_metadaten>
            """
          )
        )
        .build();

      final LocalDate newDate = LocalDate.parse("2005-01-01");
      assertThat(metadatenDs.getSimpleMetadatum(MetadatenDs.Metadata.SUBTYP, newDate))
        .contains("subtyp0");
      assertThat(metadatenDs.getNodes("./subtyp")).hasSize(1);

      metadatenDs.updateSimpleMetadatum(MetadatenDs.Metadata.SUBTYP, newDate, "subtyp1");

      assertThat(metadatenDs.getSimpleMetadatum(MetadatenDs.Metadata.SUBTYP, newDate))
        .contains("subtyp1");
      final List<SimpleProprietary> subtypValues = metadatenDs.getNodes("./subtyp");
      assertThat(subtypValues).hasSize(2);

      subtypValues
        .stream()
        .filter(f -> f.getStart().isPresent() && f.getStart().get().isEqual(newDate))
        .findFirst()
        .map(m -> assertThat(m.getEnd()).contains(LocalDate.MAX));

      assertThat(metadatenDs.getSimpleMetadatum(MetadatenDs.Metadata.SUBTYP, newDate.minusDays(1)))
        .contains("subtyp0");
    }

    @Test
    void removeNodeIfSetWithNull() {
      final MetadatenDs metadatenDs = MetadatenDs
        .builder()
        .node(
          XmlMapper.toNode(
            """
                <ris:legalDocML.de_metadaten xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.7/">
                    <ris:fna start="1990-01-01" end="1994-12-31">111-11-1</ris:fna>
                    <ris:fna start="1995-01-01" end="2000-12-31">222-22-2</ris:fna>
                    <ris:fna start="2001-01-01" end="unbestimmt">333-33-3</ris:fna>
                </ris:legalDocML.de_metadaten>
            """
          )
        )
        .build();

      final LocalDate newDate = LocalDate.parse("1995-01-01");
      assertThat(metadatenDs.getSimpleMetadatum(MetadatenDs.Metadata.FNA, newDate))
        .contains("222-22-2");
      assertThat(metadatenDs.getNodes(MetadatenDs.Metadata.FNA.getXpath())).hasSize(3);

      metadatenDs.updateSimpleMetadatum(MetadatenDs.Metadata.FNA, newDate, null);

      assertThat(metadatenDs.getSimpleMetadatum(MetadatenDs.Metadata.FNA, newDate)).isEmpty();
      assertThat(metadatenDs.getNodes(MetadatenDs.Metadata.FNA.getXpath())).hasSize(2);
    }

    @Test
    void removeNothingIfNodeNotPresent() {
      final MetadatenDs metadatenDs = MetadatenDs
        .builder()
        .node(
          XmlMapper.toNode(
            """
                <ris:legalDocML.de_metadaten xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.7/">
                    <ris:fna start="1990-01-01" end="1994-12-31">111-11-1</ris:fna>
                    <ris:fna start="2001-01-01" end="unbestimmt">333-33-3</ris:fna>
                </ris:legalDocML.de_metadaten>
            """
          )
        )
        .build();

      final LocalDate newDate = LocalDate.parse("1995-01-01");
      assertThat(metadatenDs.getSimpleMetadatum(MetadatenDs.Metadata.FNA, newDate)).isEmpty();
      assertThat(metadatenDs.getNodes(MetadatenDs.Metadata.FNA.getXpath())).hasSize(2);

      metadatenDs.updateSimpleMetadatum(MetadatenDs.Metadata.FNA, newDate, null);

      assertThat(metadatenDs.getSimpleMetadatum(MetadatenDs.Metadata.FNA, newDate)).isEmpty();
      assertThat(metadatenDs.getNodes(MetadatenDs.Metadata.FNA.getXpath())).hasSize(2);
    }
  }

  @Nested
  class Attributes {

    @Test
    void getAttributeAtDate() {
      final MetadatenDs metadatenDs = MetadatenDs
        .builder()
        .node(
          XmlMapper.toNode(
            """
                <ris:legalDocML.de_metadaten xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.7/">
                    <ris:beschliessendesOrgan qualifizierteMehrheit="true" start="1990-01-01" end="1994-12-31">organ 1</ris:beschliessendesOrgan>
                    <ris:beschliessendesOrgan qualifizierteMehrheit="false" start="1995-01-01" end="2000-12-31">organ 2</ris:beschliessendesOrgan>
                    <ris:beschliessendesOrgan qualifizierteMehrheit="true" start="2001-01-01" end="unbestimmt">organ 3</ris:beschliessendesOrgan>
                </ris:legalDocML.de_metadaten>
            """
          )
        )
        .build();

      assertThat(
        metadatenDs.getAttributeOfSimpleMetadatumAt(
          MetadatenDs.Attribute.QUALIFIZIERTE_MEHRHEIT,
          LocalDate.parse("1980-01-01")
        )
      )
        .isEmpty();

      assertThat(
        metadatenDs.getAttributeOfSimpleMetadatumAt(
          MetadatenDs.Attribute.QUALIFIZIERTE_MEHRHEIT,
          LocalDate.parse("1990-01-01")
        )
      )
        .contains("true");
      assertThat(
        metadatenDs.getAttributeOfSimpleMetadatumAt(
          MetadatenDs.Attribute.QUALIFIZIERTE_MEHRHEIT,
          LocalDate.parse("1992-01-01")
        )
      )
        .contains("true");
      assertThat(
        metadatenDs.getAttributeOfSimpleMetadatumAt(
          MetadatenDs.Attribute.QUALIFIZIERTE_MEHRHEIT,
          LocalDate.parse("1994-12-31")
        )
      )
        .contains("true");

      assertThat(
        metadatenDs.getAttributeOfSimpleMetadatumAt(
          MetadatenDs.Attribute.QUALIFIZIERTE_MEHRHEIT,
          LocalDate.parse("1995-01-01")
        )
      )
        .contains("false");
      assertThat(
        metadatenDs.getAttributeOfSimpleMetadatumAt(
          MetadatenDs.Attribute.QUALIFIZIERTE_MEHRHEIT,
          LocalDate.parse("1998-01-01")
        )
      )
        .contains("false");
      assertThat(
        metadatenDs.getAttributeOfSimpleMetadatumAt(
          MetadatenDs.Attribute.QUALIFIZIERTE_MEHRHEIT,
          LocalDate.parse("2000-12-31")
        )
      )
        .contains("false");

      assertThat(
        metadatenDs.getAttributeOfSimpleMetadatumAt(
          MetadatenDs.Attribute.QUALIFIZIERTE_MEHRHEIT,
          LocalDate.parse("2001-01-01")
        )
      )
        .contains("true");
      assertThat(
        metadatenDs.getAttributeOfSimpleMetadatumAt(
          MetadatenDs.Attribute.QUALIFIZIERTE_MEHRHEIT,
          LocalDate.parse("2024-01-01")
        )
      )
        .contains("true");
    }

    @Test
    void setAttributeAtDateUpdate() {
      final MetadatenDs metadatenDs = MetadatenDs
        .builder()
        .node(
          XmlMapper.toNode(
            """
                <ris:legalDocML.de_metadaten xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.7/">
                    <ris:beschliessendesOrgan qualifizierteMehrheit="true" start="1990-01-01" end="1994-12-31">organ 1</ris:beschliessendesOrgan>
                </ris:legalDocML.de_metadaten>
            """
          )
        )
        .build();

      final LocalDate newDate = LocalDate.parse("1990-01-01");
      assertThat(
        metadatenDs.getAttributeOfSimpleMetadatumAt(
          MetadatenDs.Attribute.QUALIFIZIERTE_MEHRHEIT,
          newDate
        )
      )
        .contains("true");

      metadatenDs.setAttributeOfSimpleMetadatum(
        MetadatenDs.Attribute.QUALIFIZIERTE_MEHRHEIT,
        newDate,
        "false"
      );

      assertThat(
        metadatenDs.getAttributeOfSimpleMetadatumAt(
          MetadatenDs.Attribute.QUALIFIZIERTE_MEHRHEIT,
          newDate
        )
      )
        .contains("false");
    }

    @Test
    void setAttributeAtDateCreate() {
      final MetadatenDs metadatenDs = MetadatenDs
        .builder()
        .node(
          XmlMapper.toNode(
            """
                <ris:legalDocML.de_metadaten xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.7/">
                    <ris:beschliessendesOrgan start="1990-01-01" end="1994-12-31">organ 1</ris:beschliessendesOrgan>
                </ris:legalDocML.de_metadaten>
            """
          )
        )
        .build();

      final LocalDate newDate = LocalDate.parse("1990-01-01");
      assertThat(
        metadatenDs.getAttributeOfSimpleMetadatumAt(
          MetadatenDs.Attribute.QUALIFIZIERTE_MEHRHEIT,
          newDate
        )
      )
        .isEmpty();

      metadatenDs.setAttributeOfSimpleMetadatum(
        MetadatenDs.Attribute.QUALIFIZIERTE_MEHRHEIT,
        newDate,
        "false"
      );

      assertThat(
        metadatenDs.getAttributeOfSimpleMetadatumAt(
          MetadatenDs.Attribute.QUALIFIZIERTE_MEHRHEIT,
          newDate
        )
      )
        .contains("false");
    }

    @Test
    void setAttributeAtDateParentNotPresent() {
      final MetadatenDs metadatenDs = MetadatenDs
        .builder()
        .node(
          XmlMapper.toNode(
            """
                <ris:legalDocML.de_metadaten xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.7/">
                    <ris:beschliessendesOrgan qualifizierteMehrheit="false" start="1995-01-01" end="2000-12-31">organ 2</ris:beschliessendesOrgan>
                    <ris:beschliessendesOrgan qualifizierteMehrheit="true" start="2001-01-01" end="unbestimmt">organ 3</ris:beschliessendesOrgan>
                </ris:legalDocML.de_metadaten>
            """
          )
        )
        .build();

      final LocalDate newDate = LocalDate.parse("1990-01-01");
      assertThat(
        metadatenDs.getAttributeOfSimpleMetadatumAt(
          MetadatenDs.Attribute.QUALIFIZIERTE_MEHRHEIT,
          newDate
        )
      )
        .isEmpty();

      metadatenDs.setAttributeOfSimpleMetadatum(
        MetadatenDs.Attribute.QUALIFIZIERTE_MEHRHEIT,
        newDate,
        "true"
      );

      assertThat(
        metadatenDs.getAttributeOfSimpleMetadatumAt(
          MetadatenDs.Attribute.QUALIFIZIERTE_MEHRHEIT,
          newDate
        )
      )
        .isEmpty();
    }

    @Test
    void removeAttributeAtValueNull() {
      final MetadatenDs metadatenDs = MetadatenDs
        .builder()
        .node(
          XmlMapper.toNode(
            """
                <ris:legalDocML.de_metadaten xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.7/">
                    <ris:beschliessendesOrgan qualifizierteMehrheit="false" start="1995-01-01" end="2000-12-31">organ 2</ris:beschliessendesOrgan>
                    <ris:beschliessendesOrgan qualifizierteMehrheit="true" start="2001-01-01" end="unbestimmt"></ris:beschliessendesOrgan>
                </ris:legalDocML.de_metadaten>
            """
          )
        )
        .build();

      final LocalDate newDate = LocalDate.parse("2002-01-01");
      assertThat(
        metadatenDs.getAttributeOfSimpleMetadatumAt(
          MetadatenDs.Attribute.QUALIFIZIERTE_MEHRHEIT,
          newDate
        )
      )
        .contains("true");

      // the 3rd parameter actually doesn't matter. It will remove the attribute because content of
      // beschliessendesOrgan is empty
      metadatenDs.setAttributeOfSimpleMetadatum(
        MetadatenDs.Attribute.QUALIFIZIERTE_MEHRHEIT,
        newDate,
        null
      );

      assertThat(
        metadatenDs.getAttributeOfSimpleMetadatumAt(
          MetadatenDs.Attribute.QUALIFIZIERTE_MEHRHEIT,
          newDate
        )
      )
        .isEmpty();
    }
  }

  @Nested
  class getMetadatenDsEinzelelement {

    @Test
    void getEinzelelementArtDerNormAtDate() {
      var eid = "hauptteil-1_abschnitt-0_art-1";
      final MetadatenDs metadatenDs = MetadatenDs
        .builder()
        .node(
          XmlMapper.toNode(
            """
                <ris:legalDocML.de_metadaten xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.7/">
                            <ris:einzelelement href="#hauptteil-1_abschnitt-0_art-1">
                        <ris:artDerNorm start="1990-01-01" end="1994-12-31">SN</ris:artDerNorm>
                        <ris:artDerNorm start="1995-01-01" end="2000-12-31">ÄN</ris:artDerNorm>
                        <ris:artDerNorm start="2001-01-01">ÜN</ris:artDerNorm>
                    </ris:einzelelement>
                </ris:legalDocML.de_metadaten>
            """
          )
        )
        .build();

      assertThat(
        metadatenDs.getSingleElementSimpleMetadatum(
          Einzelelement.Metadata.ART_DER_NORM,
          eid,
          LocalDate.parse("1980-01-01")
        )
      )
        .isEmpty();

      assertThat(
        metadatenDs.getSingleElementSimpleMetadatum(
          Einzelelement.Metadata.ART_DER_NORM,
          eid,
          LocalDate.parse("1990-01-01")
        )
      )
        .contains("SN");
      assertThat(
        metadatenDs.getSingleElementSimpleMetadatum(
          Einzelelement.Metadata.ART_DER_NORM,
          eid,
          LocalDate.parse("1992-01-01")
        )
      )
        .contains("SN");
      assertThat(
        metadatenDs.getSingleElementSimpleMetadatum(
          Einzelelement.Metadata.ART_DER_NORM,
          eid,
          LocalDate.parse("1994-12-31")
        )
      )
        .contains("SN");

      assertThat(
        metadatenDs.getSingleElementSimpleMetadatum(
          Einzelelement.Metadata.ART_DER_NORM,
          eid,
          LocalDate.parse("1995-01-01")
        )
      )
        .contains("ÄN");
      assertThat(
        metadatenDs.getSingleElementSimpleMetadatum(
          Einzelelement.Metadata.ART_DER_NORM,
          eid,
          LocalDate.parse("1998-01-01")
        )
      )
        .contains("ÄN");
      assertThat(
        metadatenDs.getSingleElementSimpleMetadatum(
          Einzelelement.Metadata.ART_DER_NORM,
          eid,
          LocalDate.parse("2000-12-31")
        )
      )
        .contains("ÄN");

      assertThat(
        metadatenDs.getSingleElementSimpleMetadatum(
          Einzelelement.Metadata.ART_DER_NORM,
          eid,
          LocalDate.parse("2001-01-01")
        )
      )
        .contains("ÜN");
      assertThat(
        metadatenDs.getSingleElementSimpleMetadatum(
          Einzelelement.Metadata.ART_DER_NORM,
          eid,
          LocalDate.parse("2024-01-01")
        )
      )
        .contains("ÜN");
    }
  }

  @Nested
  class updateMetadatenDsEinzelelement {

    @Test
    void createEinzelelementNode() {
      var eid = "hauptteil-1_abschnitt-0_art-1";
      final MetadatenDs metadatenDs = MetadatenDs
        .builder()
        .node(
          XmlMapper.toNode(
            """
                <ris:legalDocML.de_metadaten xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.7/">
                </ris:legalDocML.de_metadaten>
            """
          )
        )
        .build();

      metadatenDs.updateSingleElementSimpleMetadatum(
        Einzelelement.Metadata.ART_DER_NORM,
        eid,
        LocalDate.parse("1980-01-01"),
        "SN"
      );

      assertThat(
        metadatenDs.getSingleElementSimpleMetadatum(
          Einzelelement.Metadata.ART_DER_NORM,
          eid,
          LocalDate.parse("1980-01-01")
        )
      )
        .contains("SN");
    }

    @Test
    void updateEinzelelementBetweenDates() {
      var eid = "hauptteil-1_abschnitt-0_art-1";
      final MetadatenDs metadatenDs = MetadatenDs
        .builder()
        .node(
          XmlMapper.toNode(
            """
                <ris:legalDocML.de_metadaten xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.7/">
                          <ris:einzelelement href="#hauptteil-1_abschnitt-0_art-1">
                      <ris:artDerNorm start="1990-01-01" end="1994-12-31">SN</ris:artDerNorm>
                      <ris:artDerNorm start="1995-01-01" end="2000-12-31">ÄN</ris:artDerNorm>
                      <ris:artDerNorm start="2001-01-01">ÜN</ris:artDerNorm>
                  </ris:einzelelement>
                </ris:legalDocML.de_metadaten>
            """
          )
        )
        .build();

      metadatenDs.updateSingleElementSimpleMetadatum(
        Einzelelement.Metadata.ART_DER_NORM,
        eid,
        LocalDate.parse("1992-01-01"),
        "ÄN"
      );

      assertThat(
        metadatenDs.getSingleElementSimpleMetadatum(
          Einzelelement.Metadata.ART_DER_NORM,
          eid,
          LocalDate.parse("1992-01-01")
        )
      )
        .contains("ÄN");
    }

    @Test
    void updateEinzelelementAtStartDate() {
      var eid = "hauptteil-1_abschnitt-0_art-1";
      final MetadatenDs metadatenDs = MetadatenDs
        .builder()
        .node(
          XmlMapper.toNode(
            """
                <ris:legalDocML.de_metadaten xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.7/">
                          <ris:einzelelement href="#hauptteil-1_abschnitt-0_art-1">
                      <ris:artDerNorm start="1990-01-01" end="1994-12-31">SN</ris:artDerNorm>
                      <ris:artDerNorm start="1995-01-01" end="2000-12-31">ÄN</ris:artDerNorm>
                      <ris:artDerNorm start="2001-01-01">ÜN</ris:artDerNorm>
                  </ris:einzelelement>
                </ris:legalDocML.de_metadaten>
            """
          )
        )
        .build();

      metadatenDs.updateSingleElementSimpleMetadatum(
        Einzelelement.Metadata.ART_DER_NORM,
        eid,
        LocalDate.parse("1990-01-01"),
        "ÜN"
      );

      assertThat(
        metadatenDs.getSingleElementSimpleMetadatum(
          Einzelelement.Metadata.ART_DER_NORM,
          eid,
          LocalDate.parse("1990-01-01")
        )
      )
        .contains("ÜN");
    }

    @Test
    void resetEinzelelementAtDate() {
      var eid = "hauptteil-1_abschnitt-0_art-1";
      final MetadatenDs metadatenDs = MetadatenDs
        .builder()
        .node(
          XmlMapper.toNode(
            """
                <ris:legalDocML.de_metadaten xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.7/">
                          <ris:einzelelement href="#hauptteil-1_abschnitt-0_art-1">
                      <ris:artDerNorm start="1990-01-01" end="1994-12-31">SN</ris:artDerNorm>
                      <ris:artDerNorm start="1995-01-01" end="2000-12-31">ÄN</ris:artDerNorm>
                      <ris:artDerNorm start="2001-01-01">ÜN</ris:artDerNorm>
                  </ris:einzelelement>
                </ris:legalDocML.de_metadaten>
            """
          )
        )
        .build();

      metadatenDs.updateSingleElementSimpleMetadatum(
        Einzelelement.Metadata.ART_DER_NORM,
        eid,
        LocalDate.parse("1990-01-01"),
        null
      );

      assertThat(
        metadatenDs.getSingleElementSimpleMetadatum(
          Einzelelement.Metadata.ART_DER_NORM,
          eid,
          LocalDate.parse("1990-01-01")
        )
      )
        .isEmpty();
    }

    @Test
    void resetLastEinzelelementAtDate() {
      var eid = "hauptteil-1_abschnitt-0_art-1";
      final MetadatenDs metadatenDs = MetadatenDs
        .builder()
        .node(
          XmlMapper.toNode(
            """
                <ris:legalDocML.de_metadaten xmlns:ris="http://MetadatenRIS.LegalDocML.de/1.7/">
                          <ris:einzelelement href="#hauptteil-1_abschnitt-0_art-1">
                      <ris:artDerNorm end="unbestimmt" start="1980-01-01">SN</ris:artDerNorm>
                  </ris:einzelelement>
                </ris:legalDocML.de_metadaten>
            """
          )
        )
        .build();

      metadatenDs.updateSingleElementSimpleMetadatum(
        Einzelelement.Metadata.ART_DER_NORM,
        eid,
        LocalDate.parse("1980-01-01"),
        null
      );

      assertThat(
        metadatenDs.getSingleElementSimpleMetadatum(
          Einzelelement.Metadata.ART_DER_NORM,
          eid,
          LocalDate.parse("1980-01-01")
        )
      )
        .isEmpty();

      assertThat(
        NodeParser.getNodeFromExpression(
          "./einzelelement[@href='#%s']".formatted(eid),
          metadatenDs.getNode()
        )
      )
        .isEmpty();
    }
  }
}
