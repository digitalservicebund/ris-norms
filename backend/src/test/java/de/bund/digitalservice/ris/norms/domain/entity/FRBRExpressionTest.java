package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentExpressionEli;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import de.bund.digitalservice.ris.norms.utils.exceptions.MandatoryNodeNotFoundException;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Element;

class FRBRExpressionTest {

  @Test
  void getFRBRaliasPreviousVersionId() {
    final FRBRExpression frbrExpression = new FRBRExpression(
      XmlMapper.toElement(
        """
        <akn:FRBRExpression xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/" eId="meta-n1_ident-n1_frbrexpression-n1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
               <akn:FRBRthis eId="meta-n1_ident-n1_frbrexpression-n1_frbrthis-n1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1" />
               <akn:FRBRalias eId="meta-n1_ident-n1_frbrexpression-n1_frbralias-n2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="vorherige-version-id" value="123577e5-66ba-48f5-a6eb-db40bcfd6b87" />
               <akn:FRBRalias eId="meta-n1_ident-n1_frbrexpression-n1_frbralias-n1" GUID="6c99101d-6bca-41ae-9794-250bd096fead" name="aktuelle-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f" />
               <akn:FRBRalias eId="meta-n1_ident-n1_frbrexpression-n1_frbralias-n2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87" />
            </akn:FRBRExpression>
        """
      )
    );

    assertThat(frbrExpression.getFRBRaliasPreviousVersionId().map(UUID::toString)).contains(
      "123577e5-66ba-48f5-a6eb-db40bcfd6b87"
    );
  }

  @Test
  void getFRBRaliasPreviousVersionIdEmpty() {
    final FRBRExpression frbrExpression = new FRBRExpression(
      XmlMapper.toElement(
        """
        <akn:FRBRExpression xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/" eId="meta-n1_ident-n1_frbrexpression-n1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
               <akn:FRBRthis eId="meta-n1_ident-n1_frbrexpression-n1_frbrthis-n1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1" />
               <akn:FRBRalias eId="meta-n1_ident-n1_frbrexpression-n1_frbralias-n1" GUID="6c99101d-6bca-41ae-9794-250bd096fead" name="aktuelle-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f" />
               <akn:FRBRalias eId="meta-n1_ident-n1_frbrexpression-n1_frbralias-n2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87" />
            </akn:FRBRExpression>
        """
      )
    );

    assertThat(frbrExpression.getFRBRaliasPreviousVersionId()).isEmpty();
  }

  @Test
  void setFRBRaliasPreviousVersionIdByReplacing() {
    final FRBRExpression frbrExpression = new FRBRExpression(
      XmlMapper.toElement(
        """
        <akn:FRBRExpression xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/" eId="meta-n1_ident-n1_frbrexpression-n1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
               <akn:FRBRthis eId="meta-n1_ident-n1_frbrexpression-n1_frbrthis-n1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1" />
               <akn:FRBRalias eId="meta-n1_ident-n1_frbrexpression-n1_frbralias-n2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="vorherige-version-id" value="123577e5-66ba-48f5-a6eb-db40bcfd6b87" />
               <akn:FRBRalias eId="meta-n1_ident-n1_frbrexpression-n1_frbralias-n1" GUID="6c99101d-6bca-41ae-9794-250bd096fead" name="aktuelle-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f" />
               <akn:FRBRalias eId="meta-n1_ident-n1_frbrexpression-n1_frbralias-n2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87" />
            </akn:FRBRExpression>
        """
      )
    );

    final UUID newPreviousVersion = UUID.randomUUID();
    frbrExpression.setFRBRaliasPreviousVersionId(newPreviousVersion);

    assertThat(frbrExpression.getFRBRaliasPreviousVersionId()).contains(newPreviousVersion);
  }

  @Test
  void setFRBRaliasPreviousVersionIdByAdding() {
    final FRBRExpression frbrExpression = new FRBRExpression(
      XmlMapper.toElement(
        """
        <akn:FRBRExpression xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/" eId="meta-n1_ident-n1_frbrexpression-n1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
          <akn:FRBRthis eId="meta-n1_ident-n1_frbrexpression-n1_frbrthis-n1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1" />
          <akn:FRBRalias eId="meta-n1_ident-n1_frbrexpression-n1_frbralias-n1" GUID="6c99101d-6bca-41ae-9794-250bd096fead" name="aktuelle-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f" />
          <akn:FRBRalias eId="meta-n1_ident-n1_frbrexpression-n1_frbralias-n2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87" />
          <akn:FRBRauthor>This would have to exist in a valid document and we need it to insert the next version ID in order</akn:FRBRauthor>
        </akn:FRBRExpression>
        """
      )
    );

    final UUID newPreviousVersion = UUID.randomUUID();
    frbrExpression.setFRBRaliasPreviousVersionId(newPreviousVersion);

    assertThat(frbrExpression.getFRBRaliasPreviousVersionId()).contains(newPreviousVersion);
  }

  @Test
  void getFRBRaliasCurrentVersionId() {
    final FRBRExpression frbrExpression = new FRBRExpression(
      XmlMapper.toElement(
        """
        <akn:FRBRExpression xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/" eId="meta-n1_ident-n1_frbrexpression-n1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
               <akn:FRBRthis eId="meta-n1_ident-n1_frbrexpression-n1_frbrthis-n1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1" />
               <akn:FRBRalias eId="meta-n1_ident-n1_frbrexpression-n1_frbralias-n2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="vorherige-version-id" value="123577e5-66ba-48f5-a6eb-db40bcfd6b87" />
               <akn:FRBRalias eId="meta-n1_ident-n1_frbrexpression-n1_frbralias-n1" GUID="6c99101d-6bca-41ae-9794-250bd096fead" name="aktuelle-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f" />
               <akn:FRBRalias eId="meta-n1_ident-n1_frbrexpression-n1_frbralias-n2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87" />
            </akn:FRBRExpression>
        """
      )
    );

    assertThat(frbrExpression.getFRBRaliasCurrentVersionId().toString()).hasToString(
      "ba44d2ae-0e73-44ba-850a-932ab2fa553f"
    );
  }

  @Test
  void getFRBRaliasCurrentVersionIdNotFound() {
    final FRBRExpression frbrExpression = new FRBRExpression(
      XmlMapper.toElement(
        """
        <akn:FRBRExpression xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/" eId="meta-n1_ident-n1_frbrexpression-n1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
               <akn:FRBRthis eId="meta-n1_ident-n1_frbrexpression-n1_frbrthis-n1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1" />
               <akn:FRBRalias eId="meta-n1_ident-n1_frbrexpression-n1_frbralias-n2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="vorherige-version-id" value="123577e5-66ba-48f5-a6eb-db40bcfd6b87" />
               <akn:FRBRalias eId="meta-n1_ident-n1_frbrexpression-n1_frbralias-n2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87" />
            </akn:FRBRExpression>
        """
      )
    );

    assertThatThrownBy(frbrExpression::getFRBRaliasCurrentVersionId).isInstanceOf(
      MandatoryNodeNotFoundException.class
    );
  }

  @Test
  void setFRBRaliasCurrentVersionId() {
    final FRBRExpression frbrExpression = new FRBRExpression(
      XmlMapper.toElement(
        """
        <akn:FRBRExpression xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/" eId="meta-n1_ident-n1_frbrexpression-n1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
               <akn:FRBRthis eId="meta-n1_ident-n1_frbrexpression-n1_frbrthis-n1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1" />
               <akn:FRBRalias eId="meta-n1_ident-n1_frbrexpression-n1_frbralias-n2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="vorherige-version-id" value="123577e5-66ba-48f5-a6eb-db40bcfd6b87" />
               <akn:FRBRalias eId="meta-n1_ident-n1_frbrexpression-n1_frbralias-n1" GUID="6c99101d-6bca-41ae-9794-250bd096fead" name="aktuelle-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f" />
               <akn:FRBRalias eId="meta-n1_ident-n1_frbrexpression-n1_frbralias-n2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87" />
            </akn:FRBRExpression>
        """
      )
    );

    final UUID newCurrentVersion = UUID.randomUUID();
    frbrExpression.setFRBRaliasCurrentVersionId(newCurrentVersion);

    assertThat(frbrExpression.getFRBRaliasCurrentVersionId()).isEqualTo(newCurrentVersion);
  }

  @Test
  void getFRBRaliasNextVersionId() {
    final FRBRExpression frbrExpression = new FRBRExpression(
      XmlMapper.toElement(
        """
        <akn:FRBRExpression xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/" eId="meta-n1_ident-n1_frbrexpression-n1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
               <akn:FRBRthis eId="meta-n1_ident-n1_frbrexpression-n1_frbrthis-n1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1" />
               <akn:FRBRalias eId="meta-n1_ident-n1_frbrexpression-n1_frbralias-n2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="vorherige-version-id" value="123577e5-66ba-48f5-a6eb-db40bcfd6b87" />
               <akn:FRBRalias eId="meta-n1_ident-n1_frbrexpression-n1_frbralias-n1" GUID="6c99101d-6bca-41ae-9794-250bd096fead" name="aktuelle-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f" />
               <akn:FRBRalias eId="meta-n1_ident-n1_frbrexpression-n1_frbralias-n2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87" />
            </akn:FRBRExpression>
        """
      )
    );

    assertThat(frbrExpression.getFRBRaliasNextVersionId().get().toString()).hasToString(
      "931577e5-66ba-48f5-a6eb-db40bcfd6b87"
    );
  }

  @Test
  void getFRBRaliasNextVersionIdNotFound() {
    final FRBRExpression frbrExpression = new FRBRExpression(
      XmlMapper.toElement(
        """
        <akn:FRBRExpression xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/" eId="meta-n1_ident-n1_frbrexpression-n1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
               <akn:FRBRthis eId="meta-n1_ident-n1_frbrexpression-n1_frbrthis-n1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1" />
               <akn:FRBRalias eId="meta-n1_ident-n1_frbrexpression-n1_frbralias-n2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="vorherige-version-id" value="123577e5-66ba-48f5-a6eb-db40bcfd6b87" />
               <akn:FRBRalias eId="meta-n1_ident-n1_frbrexpression-n1_frbralias-n1" GUID="6c99101d-6bca-41ae-9794-250bd096fead" name="aktuelle-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f" />
            </akn:FRBRExpression>
        """
      )
    );

    assertThat(frbrExpression.getFRBRaliasNextVersionId()).isEmpty();
  }

  @Test
  void setFRBRaliasNextVersionId() {
    final FRBRExpression frbrExpression = new FRBRExpression(
      XmlMapper.toElement(
        """
        <akn:FRBRExpression xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/" eId="meta-n1_ident-n1_frbrexpression-n1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
               <akn:FRBRthis eId="meta-n1_ident-n1_frbrexpression-n1_frbrthis-n1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1" />
               <akn:FRBRalias eId="meta-n1_ident-n1_frbrexpression-n1_frbralias-n2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="vorherige-version-id" value="123577e5-66ba-48f5-a6eb-db40bcfd6b87" />
               <akn:FRBRalias eId="meta-n1_ident-n1_frbrexpression-n1_frbralias-n1" GUID="6c99101d-6bca-41ae-9794-250bd096fead" name="aktuelle-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f" />
               <akn:FRBRalias eId="meta-n1_ident-n1_frbrexpression-n1_frbralias-n2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87" />
            </akn:FRBRExpression>
        """
      )
    );

    final UUID newNextVersion = UUID.randomUUID();
    frbrExpression.setFRBRaliasNextVersionId(newNextVersion);

    assertThat(frbrExpression.getFRBRaliasNextVersionId()).contains(newNextVersion);
  }

  @Test
  void createsFRBRaliasNextVersionId() {
    final FRBRExpression frbrExpression = new FRBRExpression(
      XmlMapper.toElement(
        """
        <akn:FRBRExpression xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/" eId="meta-n1_ident-n1_frbrexpression-n1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
          <akn:FRBRthis eId="meta-n1_ident-n1_frbrexpression-n1_frbrthis-n1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1" />
          <akn:FRBRalias eId="meta-n1_ident-n1_frbrexpression-n1_frbralias-n2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="vorherige-version-id" value="123577e5-66ba-48f5-a6eb-db40bcfd6b87" />
          <akn:FRBRalias eId="meta-n1_ident-n1_frbrexpression-n1_frbralias-n1" GUID="6c99101d-6bca-41ae-9794-250bd096fead" name="aktuelle-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f" />
          <akn:FRBRauthor>This would have to exist in a valid document and we need it to insert the next version ID in order</akn:FRBRauthor>
        </akn:FRBRExpression>
        """
      )
    );

    final UUID newNextVersion = UUID.randomUUID();
    frbrExpression.setFRBRaliasNextVersionId(newNextVersion);

    assertThat(frbrExpression.getFRBRaliasNextVersionId()).contains(newNextVersion);
  }

  @Test
  void deleteAliasNextVersionId() {
    final FRBRExpression frbrExpression = new FRBRExpression(
      XmlMapper.toElement(
        """
        <akn:FRBRExpression xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/" eId="meta-n1_ident-n1_frbrexpression-n1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
               <akn:FRBRthis eId="meta-n1_ident-n1_frbrexpression-n1_frbrthis-n1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1" />
               <akn:FRBRalias eId="meta-n1_ident-n1_frbrexpression-n1_frbralias-n2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="vorherige-version-id" value="123577e5-66ba-48f5-a6eb-db40bcfd6b87" />
               <akn:FRBRalias eId="meta-n1_ident-n1_frbrexpression-n1_frbralias-n1" GUID="6c99101d-6bca-41ae-9794-250bd096fead" name="aktuelle-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f" />
               <akn:FRBRalias eId="meta-n1_ident-n1_frbrexpression-n1_frbralias-n2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87" />
            </akn:FRBRExpression>
        """
      )
    );

    frbrExpression.deleteAliasNextVersionId();

    assertThat(frbrExpression.getFRBRaliasNextVersionId()).isEmpty();
  }

  @Test
  void deleteAliasNextVersionIdDoesNothing() {
    final FRBRExpression frbrExpression = new FRBRExpression(
      XmlMapper.toElement(
        """
            <akn:FRBRExpression xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/" eId="meta-n1_ident-n1_frbrexpression-n1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
               <akn:FRBRthis eId="meta-n1_ident-n1_frbrexpression-n1_frbrthis-n1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1" />
               <akn:FRBRalias eId="meta-n1_ident-n1_frbrexpression-n1_frbralias-n2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="vorherige-version-id" value="123577e5-66ba-48f5-a6eb-db40bcfd6b87" />
               <akn:FRBRalias eId="meta-n1_ident-n1_frbrexpression-n1_frbralias-n1" GUID="6c99101d-6bca-41ae-9794-250bd096fead" name="aktuelle-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f" />
            </akn:FRBRExpression>
        """
      )
    );

    frbrExpression.deleteAliasNextVersionId();

    assertThat(frbrExpression.getFRBRaliasNextVersionId()).isEmpty();
  }

  @Test
  void getFRBRthisEli() {
    final FRBRExpression frbrExpression = new FRBRExpression(
      XmlMapper.toElement(
        """
        <akn:FRBRExpression xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/" eId="meta-n1_ident-n1_frbrexpression-n1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
               <akn:FRBRthis eId="meta-n1_ident-n1_frbrexpression-n1_frbrthis-n1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1" />
               <akn:FRBRalias eId="meta-n1_ident-n1_frbrexpression-n1_frbralias-n2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="vorherige-version-id" value="123577e5-66ba-48f5-a6eb-db40bcfd6b87" />
               <akn:FRBRalias eId="meta-n1_ident-n1_frbrexpression-n1_frbralias-n1" GUID="6c99101d-6bca-41ae-9794-250bd096fead" name="aktuelle-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f" />
               <akn:FRBRalias eId="meta-n1_ident-n1_frbrexpression-n1_frbralias-n2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87" />
            </akn:FRBRExpression>
        """
      )
    );

    assertThat(frbrExpression.getEli()).hasToString(
      "eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1"
    );
  }

  @Test
  void setFRBRthisEli() {
    final FRBRExpression frbrExpression = new FRBRExpression(
      XmlMapper.toElement(
        """
        <akn:FRBRExpression xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/" eId="meta-n1_ident-n1_frbrexpression-n1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
               <akn:FRBRthis eId="meta-n1_ident-n1_frbrexpression-n1_frbrthis-n1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1" />
               <akn:FRBRalias eId="meta-n1_ident-n1_frbrexpression-n1_frbralias-n2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="vorherige-version-id" value="123577e5-66ba-48f5-a6eb-db40bcfd6b87" />
               <akn:FRBRalias eId="meta-n1_ident-n1_frbrexpression-n1_frbralias-n1" GUID="6c99101d-6bca-41ae-9794-250bd096fead" name="aktuelle-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f" />
               <akn:FRBRalias eId="meta-n1_ident-n1_frbrexpression-n1_frbralias-n2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87" />
            </akn:FRBRExpression>
        """
      )
    );

    frbrExpression.setEli(
      DokumentExpressionEli.fromString(
        "eli/bund/bgbl-1/2025/1/2025-01-01/1/deu/regelungstext-verkuendung-1"
      )
    );

    assertThat(frbrExpression.getEli()).hasToString(
      "eli/bund/bgbl-1/2025/1/2025-01-01/1/deu/regelungstext-verkuendung-1"
    );
  }

  @Test
  void getFBRDate() {
    final FRBRExpression frbrExpression = new FRBRExpression(
      XmlMapper.toElement(
        """
        <akn:FRBRExpression xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/" eId="meta-n1_ident-n1_frbrexpression-n1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
               <akn:FRBRthis eId="meta-n1_ident-n1_frbrexpression-n1_frbrthis-n1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1" />
               <akn:FRBRalias eId="meta-n1_ident-n1_frbrexpression-n1_frbralias-n2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="vorherige-version-id" value="123577e5-66ba-48f5-a6eb-db40bcfd6b87" />
               <akn:FRBRalias eId="meta-n1_ident-n1_frbrexpression-n1_frbralias-n1" GUID="6c99101d-6bca-41ae-9794-250bd096fead" name="aktuelle-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f" />
               <akn:FRBRalias eId="meta-n1_ident-n1_frbrexpression-n1_frbralias-n2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87" />
               <akn:FRBRdate eId="meta-n1_ident-n1_frbrexpression-n1_frbrdate-n1" GUID="5a628f8c-65d0-4854-87cc-6fd01a2d7a9a" date="1964-08-05" name="verkuendungsfassung" />
            </akn:FRBRExpression>
        """
      )
    );

    assertThat(frbrExpression.getFBRDate()).isEqualTo("1964-08-05");
  }

  @Test
  void getFBRDateNotFound() {
    final FRBRExpression frbrExpression = new FRBRExpression(
      XmlMapper.toElement(
        """
        <akn:FRBRExpression xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/" eId="meta-n1_ident-n1_frbrexpression-n1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
               <akn:FRBRthis eId="meta-n1_ident-n1_frbrexpression-n1_frbrthis-n1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1" />
               <akn:FRBRalias eId="meta-n1_ident-n1_frbrexpression-n1_frbralias-n2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="vorherige-version-id" value="123577e5-66ba-48f5-a6eb-db40bcfd6b87" />
               <akn:FRBRalias eId="meta-n1_ident-n1_frbrexpression-n1_frbralias-n1" GUID="6c99101d-6bca-41ae-9794-250bd096fead" name="aktuelle-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f" />
            </akn:FRBRExpression>
        """
      )
    );

    assertThatThrownBy(frbrExpression::getFBRDate).isInstanceOf(
      MandatoryNodeNotFoundException.class
    );
  }

  @Test
  void setFBRDate() {
    final FRBRExpression frbrExpression = new FRBRExpression(
      XmlMapper.toElement(
        """
        <akn:FRBRExpression xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/" eId="meta-n1_ident-n1_frbrexpression-n1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
               <akn:FRBRthis eId="meta-n1_ident-n1_frbrexpression-n1_frbrthis-n1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1" />
               <akn:FRBRalias eId="meta-n1_ident-n1_frbrexpression-n1_frbralias-n2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="vorherige-version-id" value="123577e5-66ba-48f5-a6eb-db40bcfd6b87" />
               <akn:FRBRalias eId="meta-n1_ident-n1_frbrexpression-n1_frbralias-n1" GUID="6c99101d-6bca-41ae-9794-250bd096fead" name="aktuelle-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f" />
               <akn:FRBRalias eId="meta-n1_ident-n1_frbrexpression-n1_frbralias-n2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87" />
               <akn:FRBRdate eId="meta-n1_ident-n1_frbrexpression-n1_frbrdate-n1" GUID="5a628f8c-65d0-4854-87cc-6fd01a2d7a9a" date="1964-08-05" name="verkuendungsfassung" />
            </akn:FRBRExpression>
        """
      )
    );

    frbrExpression.setFBRDate("2002-02-02", "test");

    assertThat(frbrExpression.getFBRDate()).isEqualTo("2002-02-02");
  }

  @Test
  void getFRBRVersionNumber() {
    final FRBRExpression frbrExpression = new FRBRExpression(
      XmlMapper.toElement(
        """
        <akn:FRBRExpression xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/" eId="meta-n1_ident-n1_frbrexpression-n1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
               <akn:FRBRthis eId="meta-n1_ident-n1_frbrexpression-n1_frbrthis-n1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1" />
               <akn:FRBRalias eId="meta-n1_ident-n1_frbrexpression-n1_frbralias-n2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="vorherige-version-id" value="123577e5-66ba-48f5-a6eb-db40bcfd6b87" />
               <akn:FRBRalias eId="meta-n1_ident-n1_frbrexpression-n1_frbralias-n1" GUID="6c99101d-6bca-41ae-9794-250bd096fead" name="aktuelle-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f" />
               <akn:FRBRalias eId="meta-n1_ident-n1_frbrexpression-n1_frbralias-n2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87" />
               <akn:FRBRdate eId="meta-n1_ident-n1_frbrexpression-n1_frbrdate-n1" GUID="5a628f8c-65d0-4854-87cc-6fd01a2d7a9a" date="1964-08-05" name="verkuendungsfassung" />
               <akn:FRBRversionNumber eId="meta-n1_ident-n1_frbrexpression-n1_frbrversionnumber-n1" GUID="ff890d5d-cbec-4c88-82ed-e20c82faf180" value="1"/>
            </akn:FRBRExpression>
        """
      )
    );

    assertThat(frbrExpression.getFRBRVersionNumber()).contains(1);
  }

  @Test
  void setFRBRVersionNumber() {
    final FRBRExpression frbrExpression = new FRBRExpression(
      XmlMapper.toElement(
        """
        <akn:FRBRExpression xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/" eId="meta-n1_ident-n1_frbrexpression-n1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
               <akn:FRBRthis eId="meta-n1_ident-n1_frbrexpression-n1_frbrthis-n1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1" />
               <akn:FRBRalias eId="meta-n1_ident-n1_frbrexpression-n1_frbralias-n2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="vorherige-version-id" value="123577e5-66ba-48f5-a6eb-db40bcfd6b87" />
               <akn:FRBRalias eId="meta-n1_ident-n1_frbrexpression-n1_frbralias-n1" GUID="6c99101d-6bca-41ae-9794-250bd096fead" name="aktuelle-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f" />
               <akn:FRBRalias eId="meta-n1_ident-n1_frbrexpression-n1_frbralias-n2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87" />
               <akn:FRBRdate eId="meta-n1_ident-n1_frbrexpression-n1_frbrdate-n1" GUID="5a628f8c-65d0-4854-87cc-6fd01a2d7a9a" date="1964-08-05" name="verkuendungsfassung" />
               <akn:FRBRversionNumber eId="meta-n1_ident-n1_frbrexpression-n1_frbrversionnumber-n1" GUID="ff890d5d-cbec-4c88-82ed-e20c82faf180" value="1"/>
            </akn:FRBRExpression>
        """
      )
    );

    frbrExpression.setFRBRVersionNumber(2);

    assertThat(frbrExpression.getFRBRVersionNumber()).contains(2);
  }

  @Test
  void setFRBRAuthor() {
    final Element element = XmlMapper.toElement(
      """
      <akn:FRBRExpression xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/" eId="meta-n1_ident-n1_frbrexpression-n1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
          <akn:FRBRauthor eId="meta-n1_ident-n1_frbrexpression-n1_frbrauthor-n1" GUID="27fa3047-26e1-4c59-8701-76dd34043d71" href="recht.bund.de/institution/bundesregierung"/>
      </akn:FRBRExpression>
      """
    );
    final FRBRExpression frbrExpression = new FRBRExpression(element);

    frbrExpression.setFRBRAuthor("recht.bund.de/institution/bundespraesident");

    assertThat(NodeParser.getValueFromExpression("//FRBRauthor/@href", element)).contains(
      "recht.bund.de/institution/bundespraesident"
    );
  }

  @Test
  void getFBRLanguage() {
    final FRBRExpression frbrExpression = new FRBRExpression(
      XmlMapper.toElement(
        """
        <akn:FRBRExpression xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/" eId="meta-n1_ident-n1_frbrexpression-n1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
           <akn:FRBRlanguage eId="meta-n1_ident-n1_frbrexpression-n1_frbrlanguage-n1" GUID="d236da21-6b95-4406-be19-9092611fa0c7" language="deu"/>
        </akn:FRBRExpression>
        """
      )
    );

    assertThat(frbrExpression.getFRBRlanguage()).contains("deu");
  }
}
