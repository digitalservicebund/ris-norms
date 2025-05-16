package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import de.bund.digitalservice.ris.norms.utils.exceptions.MandatoryNodeNotFoundException;
import org.junit.jupiter.api.Test;

class MetaTest {

  @Test
  void getFRBRExpression() {
    final Meta meta = new Meta(
      XmlMapper.toElement(
        """
              <akn:meta xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
            <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
               <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                  <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1" />
                  <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="vorgaenger-version-id" value="123577e5-66ba-48f5-a6eb-db40bcfd6b87" />
                  <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-1" GUID="6c99101d-6bca-41ae-9794-250bd096fead" name="aktuelle-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f" />
                  <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87" />
               </akn:FRBRExpression>
           </akn:identification>
         </akn:meta>
        """
      )
    );

    assertThat(meta.getFRBRExpression()).isNotNull();
  }

  @Test
  void getFRBRExpressionNotFound() {
    final Meta meta = new Meta(
      XmlMapper.toElement(
        """
              <akn:meta xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
            <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
               <akn:FRBRManifestation eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                  <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1" />
                  <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="vorgaenger-version-id" value="123577e5-66ba-48f5-a6eb-db40bcfd6b87" />
                  <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-1" GUID="6c99101d-6bca-41ae-9794-250bd096fead" name="aktuelle-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f" />
                  <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87" />
               </akn:FRBRManifestation>
           </akn:identification>
         </akn:meta>
        """
      )
    );

    assertThatThrownBy(meta::getFRBRExpression).isInstanceOf(MandatoryNodeNotFoundException.class);
  }

  @Test
  void getFRBRManifestation() {
    final Meta meta = new Meta(
      XmlMapper.toElement(
        """
              <akn:meta xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
            <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
               <akn:FRBRManifestation eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                  <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1" />
                  <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="vorgaenger-version-id" value="123577e5-66ba-48f5-a6eb-db40bcfd6b87" />
                  <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-1" GUID="6c99101d-6bca-41ae-9794-250bd096fead" name="aktuelle-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f" />
                  <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87" />
               </akn:FRBRManifestation>
           </akn:identification>
         </akn:meta>
        """
      )
    );

    assertThat(meta.getFRBRManifestation()).isNotNull();
  }

  @Test
  void getFRBRManifestationNotFound() {
    final Meta meta = new Meta(
      XmlMapper.toElement(
        """
              <akn:meta xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
            <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
               <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                  <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1" />
                  <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="vorgaenger-version-id" value="123577e5-66ba-48f5-a6eb-db40bcfd6b87" />
                  <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-1" GUID="6c99101d-6bca-41ae-9794-250bd096fead" name="aktuelle-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f" />
                  <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87" />
               </akn:FRBRExpression>
           </akn:identification>
         </akn:meta>
        """
      )
    );

    assertThatThrownBy(meta::getFRBRManifestation).isInstanceOf(
      MandatoryNodeNotFoundException.class
    );
  }

  @Test
  void getFRBRWork() {
    final Meta meta = new Meta(
      XmlMapper.toElement(
        """
              <akn:meta xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
            <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
                   <akn:FRBRWork eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                      <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1" />
                      <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="vorgaenger-version-id" value="123577e5-66ba-48f5-a6eb-db40bcfd6b87" />
                      <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-1" GUID="6c99101d-6bca-41ae-9794-250bd096fead" name="aktuelle-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f" />
                      <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87" />
                   </akn:FRBRWork>
           </akn:identification>
         </akn:meta>
        """
      )
    );

    assertThat(meta.getFRBRWork()).isNotNull();
  }

  @Test
  void getFRBRWorkNotFound() {
    final Meta meta = new Meta(
      XmlMapper.toElement(
        """
              <akn:meta xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
            <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
                   <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                      <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1" />
                      <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="vorgaenger-version-id" value="123577e5-66ba-48f5-a6eb-db40bcfd6b87" />
                      <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-1" GUID="6c99101d-6bca-41ae-9794-250bd096fead" name="aktuelle-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f" />
                      <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87" />
                   </akn:FRBRExpression>
           </akn:identification>
         </akn:meta>
        """
      )
    );

    assertThatThrownBy(meta::getFRBRWork).isInstanceOf(MandatoryNodeNotFoundException.class);
  }

  @Test
  void getTemporalData() {
    final Meta meta = new Meta(
      XmlMapper.toElement(
        """
              <akn:meta xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
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
        """
      )
    );

    assertThat(meta.getTemporalData()).isNotNull();
  }

  @Test
  void getTemporalDataNotFound() {
    final Meta meta = new Meta(
      XmlMapper.toElement(
        """
              <akn:meta xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
                   <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                      <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1" />
                      <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="vorgaenger-version-id" value="123577e5-66ba-48f5-a6eb-db40bcfd6b87" />
                      <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-1" GUID="6c99101d-6bca-41ae-9794-250bd096fead" name="aktuelle-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f" />
                      <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87" />
                   </akn:FRBRExpression>
               </akn:identification>
         </akn:meta>
        """
      )
    );

    assertThatThrownBy(meta::getTemporalData).isInstanceOf(MandatoryNodeNotFoundException.class);
  }

  @Test
  void getTemporalDataCreate() {
    final Meta meta = new Meta(
      XmlMapper.toElement(
        """
              <akn:meta xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
                   <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                      <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1" />
                      <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="vorgaenger-version-id" value="123577e5-66ba-48f5-a6eb-db40bcfd6b87" />
                      <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-1" GUID="6c99101d-6bca-41ae-9794-250bd096fead" name="aktuelle-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f" />
                      <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87" />
                   </akn:FRBRExpression>
               </akn:identification>
         </akn:meta>
        """
      )
    );

    assertThat(meta.getOrCreateTemporalData()).isNotNull();
    assertThat(meta.getTemporalData()).isNotNull();
  }

  @Test
  void getLifecycle() {
    final Meta meta = new Meta(
      XmlMapper.toElement(
        """
              <akn:meta xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
              <akn:lifecycle eId="meta-1_lebzykl-1" GUID="4b31c2c4-6ecc-4f29-9f79-18149603114b" source="attributsemantik-noch-undefiniert">
                 <akn:eventRef eId="meta-1_lebzykl-1_ereignis-1" GUID="44e782b4-63ae-4ef0-bb0d-53e42696dd06" date="2023-12-29"
                    source="attributsemantik-noch-undefiniert" type="generation" refersTo="ausfertigung" />
                 <akn:eventRef eId="meta-1_lebzykl-1_ereignis-2" GUID="176435e5-1324-4718-b09a-ef4b63bcacf0" date="2023-12-30"
                    source="attributsemantik-noch-undefiniert" type="generation" refersTo="inkrafttreten" />
              </akn:lifecycle>
         </akn:meta>
        """
      )
    );

    assertThat(meta.getLifecycle()).isNotNull();
  }

  @Test
  void getLifecycleNotFound() {
    final Meta meta = new Meta(
      XmlMapper.toElement(
        """
              <akn:meta xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
                <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
                   <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                      <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1" />
                      <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="vorgaenger-version-id" value="123577e5-66ba-48f5-a6eb-db40bcfd6b87" />
                      <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-1" GUID="6c99101d-6bca-41ae-9794-250bd096fead" name="aktuelle-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f" />
                      <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87" />
                   </akn:FRBRExpression>
               </akn:identification>
         </akn:meta>
        """
      )
    );

    assertThatThrownBy(meta::getLifecycle).isInstanceOf(MandatoryNodeNotFoundException.class);
  }

  @Test
  void getOrCreateLifecycle() {
    final Meta meta = new Meta(
      XmlMapper.toElement(
        """
              <akn:meta xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
              <akn:lifecycle eId="meta-1_lebzykl-1" GUID="4b31c2c4-6ecc-4f29-9f79-18149603114b" source="attributsemantik-noch-undefiniert">
                 <akn:eventRef eId="meta-1_lebzykl-1_ereignis-1" GUID="44e782b4-63ae-4ef0-bb0d-53e42696dd06" date="2023-12-29"
                    source="attributsemantik-noch-undefiniert" type="generation" refersTo="ausfertigung" />
                 <akn:eventRef eId="meta-1_lebzykl-1_ereignis-2" GUID="176435e5-1324-4718-b09a-ef4b63bcacf0" date="2023-12-30"
                    source="attributsemantik-noch-undefiniert" type="generation" refersTo="inkrafttreten" />
              </akn:lifecycle>
         </akn:meta>
        """
      )
    );

    assertThat(meta.getOrCreateLifecycle()).isNotNull();
    assertThat(meta.getOrCreateLifecycle().getElement().getAttribute("GUID")).isEqualTo(
      "4b31c2c4-6ecc-4f29-9f79-18149603114b"
    );
  }

  @Test
  void getOrCreateLifecycleMissingLifecycle() {
    final Meta meta = new Meta(
      XmlMapper.toElement(
        """
         <akn:meta xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
           <akn:identification eId="meta-1_ident-1" GUID="100a364a-4680-4c7a-91ad-1b0ad9b68e7f" source="attributsemantik-noch-undefiniert">
              <akn:FRBRExpression eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                 <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1" />
                 <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="vorgaenger-version-id" value="123577e5-66ba-48f5-a6eb-db40bcfd6b87" />
                 <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-1" GUID="6c99101d-6bca-41ae-9794-250bd096fead" name="aktuelle-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f" />
                 <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87" />
              </akn:FRBRExpression>
          </akn:identification>
          <akn:proprietary eId="meta-1_proprietary-1" GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c" source="attributsemantik-noch-undefiniert">
           <meta:legalDocML.de_metadaten xmlns:meta="http://Metadaten.LegalDocML.de/1.7.2/">
           <meta:typ>gesetz</meta:typ>
           <meta:fna>754-28-1</meta:fna>
           <meta:fassung>verkuendungsfassung</meta:fassung>
           </meta:legalDocML.de_metadaten>
         </akn:proprietary>
        </akn:meta>
        """
      )
    );

    assertThat(meta.getOrCreateLifecycle()).isNotNull();
    assertThat(meta.getOrCreateLifecycle().getElement().getAttribute("GUID")).isNotNull();
    assertThat(meta.getOrCreateLifecycle().getElement().getAttribute("eId")).isEqualTo(
      "meta-1_lebzykl-1"
    );
    assertThat(meta.getOrCreateLifecycle().getElement().getNextSibling()).isEqualTo(
      meta.getProprietary().orElseThrow().getElement()
    );

    // calling it twice does not create another lifecycle element
    assertThat(meta.getOrCreateLifecycle().getElement()).isEqualTo(
      meta.getOrCreateLifecycle().getElement()
    );
  }

  @Test
  void getOrCreateProprietary() {
    final Meta meta = new Meta(
      XmlMapper.toElement(
        """
        <akn:meta xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
          <akn:proprietary eId="meta-1_proprietary-1" GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c" source="attributsemantik-noch-undefiniert">
            <meta:legalDocML.de_metadaten xmlns:meta="http://Metadaten.LegalDocML.de/1.7.2/">
            <meta:typ>gesetz</meta:typ>
            <meta:fna>754-28-1</meta:fna>
            <meta:fassung>verkuendungsfassung</meta:fassung>
            </meta:legalDocML.de_metadaten>
          </akn:proprietary>
        </akn:meta>
        """
      )
    );

    assertThat(meta.getOrCreateProprietary()).isNotNull();
  }

  @Test
  void returnsProprietaryEvenIfDoesNotExist() {
    // Given
    var xml =
      """
      <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
      <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/"
                      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                      xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-metadaten.xsd">
        <akn:act name="regelungstext">
          <!-- Metadaten -->
          <akn:meta eId="meta-1" GUID="000"></akn:meta>
        </akn:act>
      </akn:akomaNtoso>
      """;

    var regelungstext = new Regelungstext(XmlMapper.toDocument(xml));

    // When
    var result = regelungstext.getMeta().getOrCreateProprietary();

    // Then
    assertThat(result).isInstanceOf(Proprietary.class);
  }

  @Test
  void returnsOptionalProprietaryIfItExists() {
    final Meta meta = new Meta(
      XmlMapper.toElement(
        """
        <akn:meta xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/" eId="meta-1" GUID="82a65581-0ea7-4525-9190-35ff86c977af">
          <akn:proprietary eId="meta-1_proprietary-1" GUID="952262d3-de92-4c1d-a06d-95aa94f5f21c" source="attributsemantik-noch-undefiniert">
            <meta:legalDocML.de_metadaten xmlns:meta="http://Metadaten.LegalDocML.de/1.7.2/">
            <meta:typ>gesetz</meta:typ>
            <meta:fna>754-28-1</meta:fna>
            <meta:fassung>verkuendungsfassung</meta:fassung>
            </meta:legalDocML.de_metadaten>
          </akn:proprietary>
        </akn:meta>
        """
      )
    );

    assertThat(meta.getProprietary()).isNotEmpty();
  }

  @Test
  void returnsEmptyOptionalIfProprietaryDoesNotExist() {
    // Given
    var xml =
      """
      <?xml-model href="../../../Grammatiken/legalDocML.de.sch" schematypens="http://purl.oclc.org/dsdl/schematron"?>
      <akn:akomaNtoso xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.2/"
                      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                      xsi:schemaLocation="http://Metadaten.LegalDocML.de/1.7.2/ ../../../Grammatiken/legalDocML.de-metadaten.xsd">
        <akn:act name="regelungstext">
          <!-- Metadaten -->
          <akn:meta eId="meta-1" GUID="000"></akn:meta>
        </akn:act>
      </akn:akomaNtoso>
      """;

    var regelungstext = new Regelungstext(XmlMapper.toDocument(xml));

    // When
    var result = regelungstext.getMeta().getProprietary();

    // Then
    assertThat(result).isEmpty();
  }
}
