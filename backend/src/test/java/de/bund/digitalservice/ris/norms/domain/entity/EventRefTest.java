package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.utils.XmlProcessor;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class EventRefTest {

  @Test
  void getDate() {
    final EventRef eventRef =
        EventRef.builder()
            .node(
                XmlProcessor.toNode(
                    """
                            <akn:eventRef eId="meta-1_lebzykl-1_ereignis-1" GUID="44e782b4-63ae-4ef0-bb0d-53e42696dd06" date="2017-03-15" source="attributsemantik-noch-undefiniert" type="generation" refersTo="ausfertigung" />
                           """))
            .build();

    // then
    assertThat(eventRef.getDate()).contains(LocalDate.parse("2017-03-15"));
  }

  @Test
  void getDateEmpty() {
    final EventRef eventRef =
        EventRef.builder()
            .node(
                XmlProcessor.toNode(
                    """
                                            <akn:eventRef eId="meta-1_lebzykl-1_ereignis-1" GUID="44e782b4-63ae-4ef0-bb0d-53e42696dd06" source="attributsemantik-noch-undefiniert" type="generation" refersTo="ausfertigung" />
                                           """))
            .build();

    // then
    assertThat(eventRef.getDate()).isEmpty();
  }
}
