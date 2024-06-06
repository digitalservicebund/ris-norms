package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import de.bund.digitalservice.ris.norms.utils.exceptions.MandatoryNodeNotFound;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class MetaTest {

  @Test
  void getFRBRExpression() {
    final Meta meta =
        Meta.builder()
            .node(
                XmlMapper.toNode(
                    """
                       <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                           <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
                              <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                                 <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1" />
                                 <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="vorgaenger-version-id" value="123577e5-66ba-48f5-a6eb-db40bcfd6b87" />
                                 <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-1" GUID="6c99101d-6bca-41ae-9794-250bd096fead" name="aktuelle-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f" />
                                 <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87" />
                              </akn:FRBRExpression>
                          </akn:identification>
                        </akn:meta>
                       """))
            .build();

    assertThat(meta.getFRBRExpression()).isNotNull();
  }

  @Test
  void getFRBRExpressionNotFound() {
    final Meta meta =
        Meta.builder()
            .node(
                XmlMapper.toNode(
                    """
                       <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                           <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
                              <akn:FRBRManifestation eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                                 <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1" />
                                 <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="vorgaenger-version-id" value="123577e5-66ba-48f5-a6eb-db40bcfd6b87" />
                                 <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-1" GUID="6c99101d-6bca-41ae-9794-250bd096fead" name="aktuelle-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f" />
                                 <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87" />
                              </akn:FRBRManifestation>
                          </akn:identification>
                        </akn:meta>
                       """))
            .build();

    assertThrows(MandatoryNodeNotFound.class, meta::getFRBRExpression);
  }

  @Test
  void getFRBRManifestation() {
    final Meta meta =
        Meta.builder()
            .node(
                XmlMapper.toNode(
                    """
                       <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                           <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
                              <akn:FRBRManifestation eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                                 <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1" />
                                 <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="vorgaenger-version-id" value="123577e5-66ba-48f5-a6eb-db40bcfd6b87" />
                                 <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-1" GUID="6c99101d-6bca-41ae-9794-250bd096fead" name="aktuelle-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f" />
                                 <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87" />
                              </akn:FRBRManifestation>
                          </akn:identification>
                        </akn:meta>
                       """))
            .build();

    assertThat(meta.getFRBRManifestation()).isNotNull();
  }

  @Test
  void getFRBRManifestationNotFound() {
    final Meta meta =
        Meta.builder()
            .node(
                XmlMapper.toNode(
                    """
                       <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                           <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
                              <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                                 <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1" />
                                 <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="vorgaenger-version-id" value="123577e5-66ba-48f5-a6eb-db40bcfd6b87" />
                                 <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-1" GUID="6c99101d-6bca-41ae-9794-250bd096fead" name="aktuelle-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f" />
                                 <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87" />
                              </akn:FRBRExpression>
                          </akn:identification>
                        </akn:meta>
                       """))
            .build();

    assertThrows(MandatoryNodeNotFound.class, meta::getFRBRManifestation);
  }

  @Test
  void getFRBRWork() {
    final Meta meta =
        Meta.builder()
            .node(
                XmlMapper.toNode(
                    """
                       <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                           <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
                                  <akn:FRBRWork eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                                     <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1" />
                                     <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="vorgaenger-version-id" value="123577e5-66ba-48f5-a6eb-db40bcfd6b87" />
                                     <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-1" GUID="6c99101d-6bca-41ae-9794-250bd096fead" name="aktuelle-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f" />
                                     <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87" />
                                  </akn:FRBRWork>
                          </akn:identification>
                        </akn:meta>
                       """))
            .build();

    assertThat(meta.getFRBRWork()).isNotNull();
  }

  @Test
  void getFRBRWorkNotFound() {
    final Meta meta =
        Meta.builder()
            .node(
                XmlMapper.toNode(
                    """
                       <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                           <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
                                  <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                                     <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1" />
                                     <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="vorgaenger-version-id" value="123577e5-66ba-48f5-a6eb-db40bcfd6b87" />
                                     <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-1" GUID="6c99101d-6bca-41ae-9794-250bd096fead" name="aktuelle-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f" />
                                     <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87" />
                                  </akn:FRBRExpression>
                          </akn:identification>
                        </akn:meta>
                       """))
            .build();

    assertThrows(MandatoryNodeNotFound.class, meta::getFRBRWork);
  }

  @Test
  void getTemporalData() {
    final Meta meta =
        Meta.builder()
            .node(
                XmlMapper.toNode(
                    """
                       <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                                <akn:temporalData eId="meta-1_geltzeiten-1" GUID="58a31120-e277-4a33-a093-6a3637fd603d" source="attributsemantik-noch-undefiniert">
                                    <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-1" GUID="ee45119b-2485-4115-b587-da54b95e3ebd">
                                       <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-1_gelzeitintervall-1" GUID="a43d0287-920d-4fbb-91d1-42fd7e03fe16"
                                                         start="#meta-1_lebzykl-1_ereignis-2" refersTo="geltungszeit"/>
                                    </akn:temporalGroup>
                                    <akn:temporalGroup eId="meta-1_geltzeiten-1_geltungszeitgr-2" GUID="0477223f-0f4e-4f79-9656-5ff7d2afd9c4">
                                       <akn:timeInterval eId="meta-1_geltzeiten-1_geltungszeitgr-2_gelzeitintervall-1" GUID="ebd52dd5-7122-4000-93e8-b6e96d0ac75f"
                                                         start="#meta-1_lebzykl-1_ereignis-4" refersTo="geltungszeit"/>
                                    </akn:temporalGroup>
                                </akn:temporalData>
                        </akn:meta>
                       """))
            .build();

    assertThat(meta.getTemporalData()).isNotNull();
  }

  @Test
  void getTemporalDataNotFound() {
    final Meta meta =
        Meta.builder()
            .node(
                XmlMapper.toNode(
                    """
                       <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                               <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
                                  <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                                     <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1" />
                                     <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="vorgaenger-version-id" value="123577e5-66ba-48f5-a6eb-db40bcfd6b87" />
                                     <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-1" GUID="6c99101d-6bca-41ae-9794-250bd096fead" name="aktuelle-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f" />
                                     <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87" />
                                  </akn:FRBRExpression>
                              </akn:identification>
                        </akn:meta>
                       """))
            .build();

    assertThrows(MandatoryNodeNotFound.class, meta::getTemporalData);
  }

  @Test
  void getTemporalDataCreate() {
    final Meta meta =
        Meta.builder()
            .node(
                XmlMapper.toNode(
                    """
                       <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                               <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
                                  <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                                     <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1" />
                                     <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="vorgaenger-version-id" value="123577e5-66ba-48f5-a6eb-db40bcfd6b87" />
                                     <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-1" GUID="6c99101d-6bca-41ae-9794-250bd096fead" name="aktuelle-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f" />
                                     <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87" />
                                  </akn:FRBRExpression>
                              </akn:identification>
                        </akn:meta>
                       """))
            .build();

    assertThat(meta.getOrCreateTemporalDataNode()).isNotNull();
    assertThat(meta.getTemporalData()).isNotNull();
  }

  @Test
  void getAnalysis() {
    final Meta meta =
        Meta.builder()
            .node(
                XmlMapper.toNode(
                    """
                      <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                          <akn:analysis eId="meta-1_analysis-1" GUID="c0eb49c8-bf39-4a4a-b324-3b0feb88c1f1" source="attributsemantik-noch-undefiniert">
                              <akn:activeModifications eId="meta-1_analysis-1_activemod-1" GUID="cd241744-ace4-436c-a0e3-dc1ee8caf3ac">
                                  <akn:textualMod eId="meta-1_analysis-1_activemod-1_textualmod-2" GUID="8992dd02-ab87-42e8-bee2-86b76f587f81" type="substitution">
                                      <akn:source eId="meta-1_analysis-1_activemod-1_textualmod-2_source-1" GUID="7537d65c-2a3b-440c-80ec-257073b1d1d3" href="#hauptteil-1_art-1_abs-1_untergl-1_listenelem-2_inhalt-1_text-1_ändbefehl-1"/>
                                      <akn:destination eId="meta-1_analysis-1_activemod-1_textualmod-2_destination-1" GUID="83a4e169-ec57-4981-b191-84afe42130c8" href="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1/para-20_abs-1/100-126.xml"/>
                                      <akn:force eId="meta-1_analysis-1_activemod-1_textualmod-2_gelzeitnachw-1" GUID="9180eb9f-9da2-4fa4-b57f-803d4ddcdbc9" period="#meta-1_geltzeiten-1_geltungszeitgr-1"/>
                                  </akn:textualMod>
                              </akn:activeModifications>
                          </akn:analysis>
                      </akn:meta>
                      """))
            .build();

    final Optional<Analysis> analysis = meta.getAnalysis();

    assertThat(analysis).isNotEmpty();
  }

  @Test
  void getAnalysisEmpty() {

    final Meta meta =
        Meta.builder()
            .node(
                XmlMapper.toNode(
                    """
                      <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                                   <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
                                      <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                                         <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1" />
                                         <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="vorgaenger-version-id" value="123577e5-66ba-48f5-a6eb-db40bcfd6b87" />
                                         <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-1" GUID="6c99101d-6bca-41ae-9794-250bd096fead" name="aktuelle-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f" />
                                         <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87" />
                                      </akn:FRBRExpression>
                                  </akn:identification>
                      </akn:meta>
                      """))
            .build();

    final Optional<Analysis> analysis = meta.getAnalysis();

    assertThat(analysis).isEmpty();
  }

  @Test
  void getAnalysisCreate() {
    final Meta meta =
        Meta.builder()
            .node(
                XmlMapper.toNode(
                    """
                      <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                                   <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
                                      <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                                         <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1" />
                                         <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="vorgaenger-version-id" value="123577e5-66ba-48f5-a6eb-db40bcfd6b87" />
                                         <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-1" GUID="6c99101d-6bca-41ae-9794-250bd096fead" name="aktuelle-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f" />
                                         <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87" />
                                      </akn:FRBRExpression>
                                  </akn:identification>
                      </akn:meta>
                      """))
            .build();

    final Analysis analysis = meta.getOrCreateAnalysis();

    assertThat(analysis).isNotNull();
    assertThat(meta.getAnalysis()).isNotEmpty();
  }

  @Test
  void getLifecycle() {
    final Meta meta =
        Meta.builder()
            .node(
                XmlMapper.toNode(
                    """
                       <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                             <akn:lifecycle eId="meta-1_lebzykl-1" GUID="4b31c2c4-6ecc-4f29-9f79-18149603114b" source="attributsemantik-noch-undefiniert">
                                <akn:eventRef eId="meta-1_lebzykl-1_ereignis-1" GUID="44e782b4-63ae-4ef0-bb0d-53e42696dd06" date="2023-12-29"
                                   source="attributsemantik-noch-undefiniert" type="generation" refersTo="ausfertigung" />
                                <akn:eventRef eId="meta-1_lebzykl-1_ereignis-2" GUID="176435e5-1324-4718-b09a-ef4b63bcacf0" date="2023-12-30"
                                   source="attributsemantik-noch-undefiniert" type="generation" refersTo="inkrafttreten" />
                             </akn:lifecycle>
                        </akn:meta>
                       """))
            .build();

    assertThat(meta.getLifecycle()).isNotNull();
  }

  @Test
  void getLifecycleNotFound() {
    final Meta meta =
        Meta.builder()
            .node(
                XmlMapper.toNode(
                    """
                       <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                               <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
                                  <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                                     <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1" />
                                     <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="vorgaenger-version-id" value="123577e5-66ba-48f5-a6eb-db40bcfd6b87" />
                                     <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-1" GUID="6c99101d-6bca-41ae-9794-250bd096fead" name="aktuelle-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f" />
                                     <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87" />
                                  </akn:FRBRExpression>
                              </akn:identification>
                        </akn:meta>
                       """))
            .build();

    assertThrows(MandatoryNodeNotFound.class, meta::getLifecycle);
  }

  @Test
  void getOrCreateProprietary() {
    final Meta meta =
        Meta.builder()
            .node(
                XmlMapper.toNode(
                    """
                                         <akn:meta eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                                            <akn:proprietary eId="meta-1_proprietary-1"
                                                             GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c"
                                                             source="attributsemantik-noch-undefiniert">
                                                <meta:legalDocML.de_metadaten xmlns:meta="http://Metadaten.LegalDocML.de/1.6/">
                                                    <meta:typ>gesetz</meta:typ>
                                                    <meta:fna>754-28-1</meta:fna>
                                                    <meta:fassung>verkuendungsfassung</meta:fassung>
                                                </meta:legalDocML.de_metadaten>
                                            </akn:proprietary>
                                          </akn:meta>
                                         """))
            .build();

    assertThat(meta.getOrCreateProprietary()).isNotNull();
  }

  @Test
  void returnsProprietaryEvenIfDoesNotExistInNorm() {
    // Given
    var normXml =
        """
                  <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
                  <akn:akomaNtoso
                    xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.6/"
                    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                    xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.6/ ../../../Grammatiken/legalDocML.de-metadaten.xsd">
                    <akn:act name="regelungstext">
                      <!-- Metadaten -->
                      <akn:meta eId="meta-1" GUID="000">
                      </akn:meta>
                    </akn:act>
                  </akn:akomaNtoso>
                  """;

    var norm = new Norm(XmlMapper.toDocument(normXml));

    // When
    var result = norm.getMeta().getOrCreateProprietary();

    // Then
    assertThat(result).isInstanceOf(Proprietary.class);
  }
}
