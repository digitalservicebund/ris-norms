package de.bund.digitalservice.ris.norms.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import org.junit.jupiter.api.Test;

class LifecycleTest {

  @Test
  void getEventRefEId() {
    // given
    final Lifecycle lifecycle =
        Lifecycle.builder()
            .node(
                XmlMapper.toNode(
                    """
                             <akn:lifecycle eId="meta-1_lebzykl-1" GUID="4b31c2c4-6ecc-4f29-9f79-18149603114b" source="attributsemantik-noch-undefiniert">
                                <akn:eventRef eId="meta-1_lebzykl-1_ereignis-1" GUID="44e782b4-63ae-4ef0-bb0d-53e42696dd06" date="2017-03-15" source="attributsemantik-noch-undefiniert" type="generation" refersTo="ausfertigung" />
                                <akn:eventRef eId="meta-1_lebzykl-1_ereignis-2" GUID="176435e5-1324-4718-b09a-ef4b63bcacf0" date="2017-03-16" source="attributsemantik-noch-undefiniert" type="generation" refersTo="inkrafttreten" />
                             </akn:lifecycle>
                           """))
            .build();

    // when
    var eventRefs = lifecycle.getEventRefs();

    // then
    assertThat(eventRefs).hasSize(2);
  }

  @Test
  void getEventRefEIdEmpty() {
    // given
    final Lifecycle lifecycle =
        Lifecycle.builder()
            .node(
                XmlMapper.toNode(
                    """
                                             <akn:lifecycle eId="meta-1_lebzykl-1" GUID="4b31c2c4-6ecc-4f29-9f79-18149603114b" source="attributsemantik-noch-undefiniert">
                                              </akn:lifecycle>
                                           """))
            .build();

    // when
    var eventRefs = lifecycle.getEventRefs();

    // then
    assertThat(eventRefs).isEmpty();
  }
}
