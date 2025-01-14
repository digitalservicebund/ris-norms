package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import org.junit.jupiter.api.Test;

class MetadatenDeTest {

  @Test
  void getFna() {
    final MetadatenDe metadatenDe = new MetadatenDe(
      XmlMapper.toElement(
        """
                <meta:legalDocML.de_metadaten xmlns:meta="http://Metadaten.LegalDocML.de/1.7.1/">
                <meta:fna>111-11-1</meta:fna>
            </meta:legalDocML.de_metadaten>
        """
      )
    );

    assertThat(metadatenDe.getFna()).contains("111-11-1");
  }

  @Test
  void getFnaEmpty() {
    final MetadatenDe metadatenDe = new MetadatenDe(
      XmlMapper.toElement(
        """
                <meta:legalDocML.de_metadaten xmlns:meta="http://Metadaten.LegalDocML.de/1.7.1/">
            </meta:legalDocML.de_metadaten>
        """
      )
    );

    assertThat(metadatenDe.getFna()).isEmpty();
  }

  @Test
  void setFnaWhenFnaExists() {
    final MetadatenDe metadatenDe = new MetadatenDe(
      XmlMapper.toElement(
        """
                <meta:legalDocML.de_metadaten xmlns:meta="http://Metadaten.LegalDocML.de/1.7.1/">
                <meta:fna>111-11-1</meta:fna>
            </meta:legalDocML.de_metadaten>
        """
      )
    );

    metadatenDe.setFna("222-22-2");

    assertThat(metadatenDe.getFna()).contains("222-22-2");
  }

  @Test
  void setFnaWhenEmpty() {
    final MetadatenDe metadatenDe = new MetadatenDe(
      XmlMapper.toElement(
        """
        <meta:legalDocML.de_metadaten xmlns:meta="http://Metadaten.LegalDocML.de/1.7.1/">
        </meta:legalDocML.de_metadaten>
        """
      )
    );

    metadatenDe.setFna("222-22-2");

    assertThat(metadatenDe.getFna()).contains("222-22-2");
  }

  @Test
  void getArt() {
    final MetadatenDe metadatenDe = new MetadatenDe(
      XmlMapper.toElement(
        """
                <meta:legalDocML.de_metadaten xmlns:meta="http://Metadaten.LegalDocML.de/1.7.1/">
                <meta:art>test art</meta:art>
            </meta:legalDocML.de_metadaten>
        """
      )
    );

    assertThat(metadatenDe.getArt()).contains("test art");
  }

  @Test
  void getArtEmpty() {
    final MetadatenDe metadatenDe = new MetadatenDe(
      XmlMapper.toElement(
        """
                <meta:legalDocML.de_metadaten xmlns:meta="http://Metadaten.LegalDocML.de/1.7.1/">
            </meta:legalDocML.de_metadaten>
        """
      )
    );

    assertThat(metadatenDe.getArt()).isEmpty();
  }

  @Test
  void getTyp() {
    final MetadatenDe metadatenDe = new MetadatenDe(
      XmlMapper.toElement(
        """
                <meta:legalDocML.de_metadaten xmlns:meta="http://Metadaten.LegalDocML.de/1.7.1/">
                <meta:typ>typi1</meta:typ>
            </meta:legalDocML.de_metadaten>
        """
      )
    );

    assertThat(metadatenDe.getTyp()).contains("typi1");
  }

  @Test
  void getTypEmpty() {
    final MetadatenDe metadatenDe = new MetadatenDe(
      XmlMapper.toElement(
        """
                <meta:legalDocML.de_metadaten xmlns:meta="http://Metadaten.LegalDocML.de/1.7.1/">
            </meta:legalDocML.de_metadaten>
        """
      )
    );

    assertThat(metadatenDe.getTyp()).isEmpty();
  }

  @Test
  void getGesta() {
    final MetadatenDe metadatenDe = new MetadatenDe(
      XmlMapper.toElement(
        """
            <meta:legalDocML.de_metadaten xmlns:meta="http://Metadaten.LegalDocML.de/1.7.1/">
                <meta:gesta>nicht-vorhanden</meta:gesta>
            </meta:legalDocML.de_metadaten>
        """
      )
    );

    assertThat(metadatenDe.getGesta()).contains("nicht-vorhanden");
  }

  @Test
  void setGestaWhenGestaExists() {
    final MetadatenDe metadatenDe = new MetadatenDe(
      XmlMapper.toElement(
        """
                <meta:legalDocML.de_metadaten xmlns:meta="http://Metadaten.LegalDocML.de/1.7.1/">
                <meta:gesta>nicht-vorhanden</meta:gesta>
            </meta:legalDocML.de_metadaten>
        """
      )
    );

    metadatenDe.setGesta("a-gesta");

    assertThat(metadatenDe.getGesta()).contains("a-gesta");
  }

  @Test
  void setGestaWhenEmpty() {
    final MetadatenDe metadatenDe = new MetadatenDe(
      XmlMapper.toElement(
        """
        <meta:legalDocML.de_metadaten xmlns:meta="http://Metadaten.LegalDocML.de/1.7.1/">
        </meta:legalDocML.de_metadaten>
        """
      )
    );

    metadatenDe.setGesta("nicht-vorhanden");

    assertThat(metadatenDe.getGesta()).contains("nicht-vorhanden");
  }

  @Test
  void getFassung() {
    final MetadatenDe metadatenDe = new MetadatenDe(
      XmlMapper.toElement(
        """
            <meta:legalDocML.de_metadaten xmlns:meta="http://Metadaten.LegalDocML.de/1.7.1/">
                <meta:fassung>verkuendungsfassung</meta:fassung>
            </meta:legalDocML.de_metadaten>
        """
      )
    );

    assertThat(metadatenDe.getFassung()).contains("verkuendungsfassung");
  }

  @Test
  void setFassungWhenFassungExists() {
    final MetadatenDe metadatenDe = new MetadatenDe(
      XmlMapper.toElement(
        """
            <meta:legalDocML.de_metadaten xmlns:meta="http://Metadaten.LegalDocML.de/1.7.1/">
                <meta:fassung>verkuendungsfassung</meta:fassung>
            </meta:legalDocML.de_metadaten>
        """
      )
    );

    metadatenDe.setFassung("entwurfsfassung");

    assertThat(metadatenDe.getFassung()).contains("entwurfsfassung");
  }

  @Test
  void setFassungWhenEmpty() {
    final MetadatenDe metadatenDe = new MetadatenDe(
      XmlMapper.toElement(
        """
        <meta:legalDocML.de_metadaten xmlns:meta="http://Metadaten.LegalDocML.de/1.7.1/">
        </meta:legalDocML.de_metadaten>
        """
      )
    );

    metadatenDe.setFassung("verkuendungsfassung");

    assertThat(metadatenDe.getFassung()).contains("verkuendungsfassung");
  }
}
