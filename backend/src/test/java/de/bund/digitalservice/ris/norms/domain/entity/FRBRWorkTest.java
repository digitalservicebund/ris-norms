package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentWorkEli;
import de.bund.digitalservice.ris.norms.utils.NodeParser;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import de.bund.digitalservice.ris.norms.utils.exceptions.MandatoryNodeNotFoundException;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Element;

class FRBRWorkTest {

  @Test
  void getFRBRthisEli() {
    final FRBRWork frbrWork = new FRBRWork(
      XmlMapper.toElement(
        """
         <akn:FRBRWork xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/" eId="meta-n1_ident-n1_frbrexpression-n1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                   <akn:FRBRthis eId="meta-n1_ident-n1_frbrexpression-n1_frbrthis-n1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/regelungstext-verkuendung-1"/>
               <akn:FRBRalias eId="meta-n1_ident-n1_frbrexpression-n1_frbralias-n2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="vorherige-version-id" value="123577e5-66ba-48f5-a6eb-db40bcfd6b87"/>
               <akn:FRBRalias eId="meta-n1_ident-n1_frbrexpression-n1_frbralias-n1" GUID="6c99101d-6bca-41ae-9794-250bd096fead" name="aktuelle-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f"/>
               <akn:FRBRalias eId="meta-n1_ident-n1_frbrexpression-n1_frbralias-n2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87"/>
           </akn:FRBRWork>
        """
      )
    );

    assertThat(frbrWork.getEli()).hasToString(
      "eli/bund/bgbl-1/1964/s593/regelungstext-verkuendung-1"
    );
  }

  @Test
  void setFRBRthisEli() {
    final FRBRWork frbrWork = new FRBRWork(
      XmlMapper.toElement(
        """
        <akn:FRBRWork xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/" eId="meta-n1_ident-n1_frbrexpression-n1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
               <akn:FRBRthis eId="meta-n1_ident-n1_frbrexpression-n1_frbrthis-n1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1" />
               <akn:FRBRalias eId="meta-n1_ident-n1_frbrexpression-n1_frbralias-n2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="vorherige-version-id" value="123577e5-66ba-48f5-a6eb-db40bcfd6b87" />
               <akn:FRBRalias eId="meta-n1_ident-n1_frbrexpression-n1_frbralias-n1" GUID="6c99101d-6bca-41ae-9794-250bd096fead" name="aktuelle-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f" />
               <akn:FRBRalias eId="meta-n1_ident-n1_frbrexpression-n1_frbralias-n2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87" />
            </akn:FRBRWork>
            """
      )
    );

    frbrWork.setEli(
      DokumentWorkEli.fromString("eli/bund/bgbl-1/2025/1/regelungstext-verkuendung-1")
    );

    assertThat(frbrWork.getEli()).hasToString("eli/bund/bgbl-1/2025/1/regelungstext-verkuendung-1");
  }

  @Test
  void getFBRDate() {
    final FRBRWork frbrWork = new FRBRWork(
      XmlMapper.toElement(
        """
        <akn:FRBRWork xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/" eId="meta-n1_ident-n1_frbrexpression-n1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                     <akn:FRBRthis eId="meta-n1_ident-n1_frbrexpression-n1_frbrthis-n1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1" />
                     <akn:FRBRalias eId="meta-n1_ident-n1_frbrexpression-n1_frbralias-n2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="vorherige-version-id" value="123577e5-66ba-48f5-a6eb-db40bcfd6b87" />
                     <akn:FRBRalias eId="meta-n1_ident-n1_frbrexpression-n1_frbralias-n1" GUID="6c99101d-6bca-41ae-9794-250bd096fead" name="aktuelle-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f" />
                     <akn:FRBRalias eId="meta-n1_ident-n1_frbrexpression-n1_frbralias-n2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87" />
                      <akn:FRBRdate eId="meta-n1_ident-n1_frbrexpression-n1_frbrdate-n1" GUID="5a628f8c-65d0-4854-87cc-6fd01a2d7a9a" date="1964-08-05" name="verkuendungsfassung" />
                  </akn:FRBRWork>
                  """
      )
    );

    assertThat(frbrWork.getFBRDate()).isEqualTo("1964-08-05");
  }

  @Test
  void getFBRDateNotFound() {
    final FRBRWork frbrWork = new FRBRWork(
      XmlMapper.toElement(
        """
        <akn:FRBRWork xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/" eId="meta-n1_ident-n1_frbrexpression-n1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                    <akn:FRBRthis eId="meta-n1_ident-n1_frbrexpression-n1_frbrthis-n1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1" />
                    <akn:FRBRalias eId="meta-n1_ident-n1_frbrexpression-n1_frbralias-n2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="vorherige-version-id" value="123577e5-66ba-48f5-a6eb-db40bcfd6b87" />
                    <akn:FRBRalias eId="meta-n1_ident-n1_frbrexpression-n1_frbralias-n1" GUID="6c99101d-6bca-41ae-9794-250bd096fead" name="aktuelle-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f" />
                  </akn:FRBRWork>
                  """
      )
    );

    assertThatThrownBy(frbrWork::getFBRDate).isInstanceOf(MandatoryNodeNotFoundException.class);
  }

  @Test
  void setFBRDate() {
    final FRBRWork frbrWork = new FRBRWork(
      XmlMapper.toElement(
        """
        <akn:FRBRWork xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/" eId="meta-n1_ident-n1_frbrexpression-n1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
                     <akn:FRBRthis eId="meta-n1_ident-n1_frbrexpression-n1_frbrthis-n1" GUID="c01334e2-f12b-4055-ac82-15ac03c74c78" value="eli/bund/bgbl-1/1964/s593/1964-08-05/1/deu/regelungstext-verkuendung-1" />
                     <akn:FRBRalias eId="meta-n1_ident-n1_frbrexpression-n1_frbralias-n2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="vorherige-version-id" value="123577e5-66ba-48f5-a6eb-db40bcfd6b87" />
                     <akn:FRBRalias eId="meta-n1_ident-n1_frbrexpression-n1_frbralias-n1" GUID="6c99101d-6bca-41ae-9794-250bd096fead" name="aktuelle-version-id" value="ba44d2ae-0e73-44ba-850a-932ab2fa553f" />
                     <akn:FRBRalias eId="meta-n1_ident-n1_frbrexpression-n1_frbralias-n2" GUID="2c2df2b6-31ce-4876-9fbb-fe38102aeb37" name="nachfolgende-version-id" value="931577e5-66ba-48f5-a6eb-db40bcfd6b87" />
                     <akn:FRBRdate eId="meta-n1_ident-n1_frbrexpression-n1_frbrdate-n1" GUID="5a628f8c-65d0-4854-87cc-6fd01a2d7a9a" date="1964-08-05" name="verkuendungsfassung" />
                  </akn:FRBRWork>
                  """
      )
    );

    frbrWork.setFBRDate("2002-02-02", "test");

    assertThat(frbrWork.getFBRDate()).isEqualTo("2002-02-02");
  }

  @Test
  void getFRBRname() {
    final FRBRWork frbrWork = new FRBRWork(
      XmlMapper.toElement(
        """
        <akn:FRBRWork xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/" eId="meta-n1_ident-n1_frbrwork-n1" GUID="3385defa-f0e5-4c6d-a2d4-17388afd5d51">
                        <akn:FRBRnumber eId="meta-n1_ident-n1_frbrwork-n1_frbrnumber-n1" GUID="b82cc174-8fff-43bf-a434-5646de09e807" value="s593"/>
                        <akn:FRBRname eId="meta-n1_ident-n1_frbrwork-n1_frbrname-n1" GUID="374e5873-9c62-4e3d-9dbe-1b865ba0b327" value="BGBl. I"/>
                        <akn:FRBRdate eId="meta-n1_ident-n1_frbrwork-n1_frbrdate-n1" GUID="5a628f8c-65d0-4854-87cc-6fd01a2d7a9a" date="1964-08-05" name="verkuendungsfassung"/>
                    </akn:FRBRWork>
                       """
      )
    );

    assertThat(frbrWork.getFRBRname()).contains("BGBl. I");
  }

  @Test
  void getFRBRnameEmpty() {
    final FRBRWork frbrWork = new FRBRWork(
      XmlMapper.toElement(
        """
        <akn:FRBRWork xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/" eId="meta-n1_ident-n1_frbrwork-n1" GUID="3385defa-f0e5-4c6d-a2d4-17388afd5d51">
                        <akn:FRBRnumber eId="meta-n1_ident-n1_frbrwork-n1_frbrnumber-n1" GUID="b82cc174-8fff-43bf-a434-5646de09e807" value="s593"/>
                        <akn:FRBRdate eId="meta-n1_ident-n1_frbrwork-n1_frbrdate-n1" GUID="5a628f8c-65d0-4854-87cc-6fd01a2d7a9a" date="1964-08-05" name="verkuendungsfassung"/>
                    </akn:FRBRWork>
                       """
      )
    );

    assertThat(frbrWork.getFRBRname()).isEmpty();
  }

  @Test
  void getFRBRnumber() {
    final FRBRWork frbrWork = new FRBRWork(
      XmlMapper.toElement(
        """
        <akn:FRBRWork xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/" eId="meta-n1_ident-n1_frbrwork-n1" GUID="3385defa-f0e5-4c6d-a2d4-17388afd5d51">
                        <akn:FRBRnumber eId="meta-n1_ident-n1_frbrwork-n1_frbrnumber-n1" GUID="b82cc174-8fff-43bf-a434-5646de09e807" value="s593"/>
                        <akn:FRBRname eId="meta-n1_ident-n1_frbrwork-n1_frbrname-n1" GUID="374e5873-9c62-4e3d-9dbe-1b865ba0b327" value="BGBl. I"/>
                        <akn:FRBRdate eId="meta-n1_ident-n1_frbrwork-n1_frbrdate-n1" GUID="5a628f8c-65d0-4854-87cc-6fd01a2d7a9a" date="1964-08-05" name="verkuendungsfassung"/>
                    </akn:FRBRWork>
                       """
      )
    );

    assertThat(frbrWork.getFRBRnumber()).contains("s593");
  }

  @Test
  void getFRBRnumberEmpty() {
    final FRBRWork frbrWork = new FRBRWork(
      XmlMapper.toElement(
        """
        <akn:FRBRWork xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/" eId="meta-n1_ident-n1_frbrwork-n1" GUID="3385defa-f0e5-4c6d-a2d4-17388afd5d51">
                        <akn:FRBRname eId="meta-n1_ident-n1_frbrwork-n1_frbrname-n1" GUID="374e5873-9c62-4e3d-9dbe-1b865ba0b327" value="BGBl. I"/>
                        <akn:FRBRdate eId="meta-n1_ident-n1_frbrwork-n1_frbrdate-n1" GUID="5a628f8c-65d0-4854-87cc-6fd01a2d7a9a" date="1964-08-05" name="verkuendungsfassung"/>
                    </akn:FRBRWork>
                       """
      )
    );

    assertThat(frbrWork.getFRBRnumber()).isEmpty();
  }

  @Test
  void getFRBRSubtype() {
    final FRBRWork frbrWork = new FRBRWork(
      XmlMapper.toElement(
        """
        <akn:FRBRWork xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/" eId="meta-n1_ident-n1_frbrwork-n1" GUID="3385defa-f0e5-4c6d-a2d4-17388afd5d51">
                    <akn:FRBRsubtype value="rechtsetzungsdokument-1"/>
                    </akn:FRBRWork>
                       """
      )
    );

    assertThat(frbrWork.getFRBRsubtype()).contains("rechtsetzungsdokument-1");
  }

  @Test
  void setFRBRName() {
    final FRBRWork frbrWork = new FRBRWork(
      XmlMapper.toElement(
        """
        <akn:FRBRWork xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/" eId="meta-n1_ident-n1_frbrexpression-n1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
           <akn:FRBRname eId="meta-n1_ident-n1_frbrwork-n1_frbrname-n1" GUID="374e5873-9c62-4e3d-9dbe-1b865ba0b327" value="bgbl-1"/>
        </akn:FRBRWork>
        """
      )
    );

    frbrWork.setFRBRName("bgbl-2");

    assertThat(frbrWork.getFRBRname()).contains("BGBl. II");
  }

  @Test
  void setFRBRAuthor() {
    final Element element = XmlMapper.toElement(
      """
      <akn:FRBRWork xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/" eId="meta-n1_ident-n1_frbrexpression-n1" GUID="4cce38bb-236b-4947-bee1-e90f3b6c2b8d">
          <akn:FRBRauthor eId="meta-n1_ident-n1_frbrwork-n1_frbrauthor-n1" GUID="27fa3047-26e1-4c59-8701-76dd34043d71" href="recht.bund.de/institution/bundesregierung"/>
      </akn:FRBRWork>
      """
    );
    final FRBRWork frbrWork = new FRBRWork(element);

    frbrWork.setFRBRAuthor("recht.bund.de/institution/bundespraesident");

    assertThat(NodeParser.getValueFromExpression("//FRBRauthor/@href", element)).contains(
      "recht.bund.de/institution/bundespraesident"
    );
  }

  @Test
  void setFRBRsubtype() {
    final Element element = XmlMapper.toElement(
      """
      <akn:FRBRWork xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/" eId="meta-n1_ident-n1_frbrexpression-n1">
          <akn:FRBRsubtype value="gesetzblaetter"/>
      </akn:FRBRWork>
      """
    );
    final FRBRWork frbrWork = new FRBRWork(element);

    frbrWork.setFRBRsubtype("amtliche-veroeffentlichung");

    assertThat(NodeParser.getValueFromExpression("//FRBRsubtype/@value", element)).contains(
      "amtliche-veroeffentlichung"
    );
  }

  @Test
  void setFRBRnumber() {
    final Element element = XmlMapper.toElement(
      """
      <akn:FRBRWork xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/" eId="meta-n1_ident-n1_frbrexpression-n1">
          <akn:FRBRnumber value="1"/>
      </akn:FRBRWork>
      """
    );
    final FRBRWork frbrWork = new FRBRWork(element);

    frbrWork.setFRBRnumber("42");

    assertThat(NodeParser.getValueFromExpression("//FRBRnumber/@value", element)).contains("42");
  }

  @Test
  void setUebergreifendeId() {
    final Element element = XmlMapper.toElement(
      """
      <akn:FRBRWork xmlns:akn="http://Inhaltsdaten.LegalDocML.de/1.8.1/" eId="meta-n1_ident-n1_frbrexpression-n1">
          <akn:FRBRalias name="übergreifende-id" value="00000000-0000-0000-0000-000000000000"/>
      </akn:FRBRWork>
      """
    );
    final FRBRWork frbrWork = new FRBRWork(element);

    final UUID newId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
    frbrWork.setUebergreifendeId(newId);

    assertThat(
      NodeParser.getValueFromExpression("//FRBRalias[@name='übergreifende-id']/@value", element)
    ).contains("123e4567-e89b-12d3-a456-426614174000");
  }
}
