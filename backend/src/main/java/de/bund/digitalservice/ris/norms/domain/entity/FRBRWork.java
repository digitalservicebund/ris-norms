package de.bund.digitalservice.ris.norms.domain.entity;

import de.bund.digitalservice.ris.norms.utils.NodeParser;
import java.util.Optional;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.w3c.dom.Node;

/** Class representing the akn:FRBRWork */
@Getter
@SuperBuilder(toBuilder = true)
public class FRBRWork extends FRBR {

  public FRBRWork(final Node node) {
    super(node);
  }

  /**
   * Returns a FRBRname as {@link String} from a the FRBRWork in a {@link Norm}.
   *
   * @return The FRBRname
   */
  public Optional<String> getFRBRname() {
    Optional<String> fRBRname = NodeParser.getValueFromExpression("./FRBRname/@value", getNode());

    return fRBRname.map(s ->
      s.replace("bgbl-1", "BGBl. I").replace("bgbl-2", "BGBl. II").replace("banz-at", "BAnz AT")
    );
  }

  /**
   * Returns a FRBRnumber as {@link String} from the FRBRWork in a {@link Norm}.
   *
   * @return The FRBRnumber
   */
  public Optional<String> getFRBRnumber() {
    return NodeParser.getValueFromExpression("./FRBRnumber/@value", getNode());
  }
}
