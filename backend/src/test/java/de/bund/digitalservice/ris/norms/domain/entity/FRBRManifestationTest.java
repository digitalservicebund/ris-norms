package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import de.bund.digitalservice.ris.norms.domain.entity.eli.ManifestationEli;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import de.bund.digitalservice.ris.norms.utils.exceptions.MandatoryNodeNotFoundException;
import java.net.URI;
import org.junit.jupiter.api.Test;

class FRBRManifestationTest {

  @Test
  void getFRBRthisEli() {
    final FRBRManifestation frbrManifestation = new FRBRManifestation(
      XmlMapper.toElement(
        """
         <akn:FRBRManifestation xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.1/" eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                   <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/2020-01-01/regelungstext-1.xml"/>
               <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="vorherige-version-id" value="123577e5-66ba-48f5-a6eb-db40bcfd6b87"/>
               <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-1" GUID="6c99101d-6bca-41ae-9794-250bd096fead" name="aktuelle-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f"/>
               <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87"/>
           </akn:FRBRManifestation>
        """
      )
    );

    assertThat(frbrManifestation.getEli())
      .hasToString("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/2020-01-01/regelungstext-1.xml");
  }

  @Test
  void setFRBRthisEli() {
    final FRBRManifestation frbrManifestation = new FRBRManifestation(
      XmlMapper.toElement(
        """
        <akn:FRBRManifestation xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.1/" eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                     <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1" />
                     <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="vorherige-version-id" value="123577e5-66ba-48f5-a6eb-db40bcfd6b87" />
                     <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-1" GUID="6c99101d-6bca-41ae-9794-250bd096fead" name="aktuelle-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f" />
                     <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87" />
                  </akn:FRBRManifestation>
                  """
      )
    );

    frbrManifestation.setEli(
      ManifestationEli.fromString(
        "eli/bund/bgbl-1/2025/1/2025-01-01/1/deu/2025-01-01/regelungstext-1.xml"
      )
    );

    assertThat(frbrManifestation.getEli())
      .hasToString("eli/bund/bgbl-1/2025/1/2025-01-01/1/deu/2025-01-01/regelungstext-1.xml");
  }

  @Test
  void getFRBRuri() {
    final FRBRManifestation frbrManifestation = new FRBRManifestation(
      XmlMapper.toElement(
        """
         <akn:FRBRManifestation xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.1/" eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
               <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"/>
               <akn:FRBRuri GUID="ad94eb82-1a61-4210-939f-7e423fbc78d4" eId="meta-1_ident-1_frbrexpression-1_frbruri-1" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu"/>
               <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="vorherige-version-id" value="123577e5-66ba-48f5-a6eb-db40bcfd6b87"/>
               <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-1" GUID="6c99101d-6bca-41ae-9794-250bd096fead" name="aktuelle-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f"/>
               <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87"/>
           </akn:FRBRManifestation>
        """
      )
    );

    assertThat(frbrManifestation.getURI())
      .isEqualTo(URI.create("eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu"));
  }

  @Test
  void setFRBRuri() {
    final FRBRManifestation frbrManifestation = new FRBRManifestation(
      XmlMapper.toElement(
        """
         <akn:FRBRManifestation xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.1/" eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
               <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1"/>
               <akn:FRBRuri GUID="ad94eb82-1a61-4210-939f-7e423fbc78d4" eId="meta-1_ident-1_frbrexpression-1_frbruri-1" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu"/>
               <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="vorherige-version-id" value="123577e5-66ba-48f5-a6eb-db40bcfd6b87"/>
               <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-1" GUID="6c99101d-6bca-41ae-9794-250bd096fead" name="aktuelle-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f"/>
               <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87"/>
           </akn:FRBRManifestation>
        """
      )
    );

    frbrManifestation.setURI(URI.create("eli/bund/bgbl-1/1964/s593/2024-02-02/1/deu"));

    assertThat(frbrManifestation.getURI())
      .hasToString("eli/bund/bgbl-1/1964/s593/2024-02-02/1/deu");
  }

  @Test
  void getFBRDate() {
    final FRBRManifestation frbrManifestation = new FRBRManifestation(
      XmlMapper.toElement(
        """
        <akn:FRBRManifestation xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.1/" eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                     <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1" />
                     <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="vorherige-version-id" value="123577e5-66ba-48f5-a6eb-db40bcfd6b87" />
                     <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-1" GUID="6c99101d-6bca-41ae-9794-250bd096fead" name="aktuelle-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f" />
                     <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87" />
                      <akn:FRBRdate eId="meta-1_ident-1_frbrexpression-1_frbrdate-1" GUID="5a628f8c-65d0-4854-87cc-6fd01a2d7a9a" date="1964-08-05" name="verkuendungsfassung" />
                  </akn:FRBRManifestation>
                  """
      )
    );

    assertThat(frbrManifestation.getFBRDate()).isEqualTo("1964-08-05");
  }

  @Test
  void getFBRDateNotFound() {
    final FRBRManifestation frbrManifestation = new FRBRManifestation(
      XmlMapper.toElement(
        """
        <akn:FRBRManifestation xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.1/" eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                    <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1" />
                    <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="vorherige-version-id" value="123577e5-66ba-48f5-a6eb-db40bcfd6b87" />
                    <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-1" GUID="6c99101d-6bca-41ae-9794-250bd096fead" name="aktuelle-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f" />
                  </akn:FRBRManifestation>
                  """
      )
    );

    assertThatThrownBy(frbrManifestation::getFBRDate)
      .isInstanceOf(MandatoryNodeNotFoundException.class);
  }

  @Test
  void setFBRDate() {
    final FRBRManifestation frbrManifestation = new FRBRManifestation(
      XmlMapper.toElement(
        """
        <akn:FRBRManifestation xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.7.1/" eId="meta-1_ident-1_frbrexpression-1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                     <akn:FRBRthis eId="meta-1_ident-1_frbrexpression-1_frbrthis-1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-1" />
                     <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="vorherige-version-id" value="123577e5-66ba-48f5-a6eb-db40bcfd6b87" />
                     <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-1" GUID="6c99101d-6bca-41ae-9794-250bd096fead" name="aktuelle-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f" />
                     <akn:FRBRalias eId="meta-1_ident-1_frbrexpression-1_frbralias-2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87" />
                     <akn:FRBRdate eId="meta-1_ident-1_frbrexpression-1_frbrdate-1" GUID="5a628f8c-65d0-4854-87cc-6fd01a2d7a9a" date="1964-08-05" name="verkuendungsfassung" />
                  </akn:FRBRManifestation>
                  """
      )
    );

    frbrManifestation.setFBRDate("2002-02-02", "test");

    assertThat(frbrManifestation.getFBRDate()).isEqualTo("2002-02-02");
  }
}
