package de.bund.digitalservice.ris.norms.adapter.output.database.mapper;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.NormDto;
import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import de.bund.digitalservice.ris.norms.utils.XmlMapper;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;

/** Mapper class for converting between {@link NormDto} and {@link Norm}. */
public class NormMapper {

  // Private constructor to hide the implicit public one and prevent instantiation
  private NormMapper() {}

  /**
   * Maps a {@link NormDto} to the domain {@link Norm}.
   *
   * @param normDto The input {@link NormDto} to be mapped.
   * @return A new {@link Norm} mapped from the input {@link NormDto}.
   */
  public static Norm mapToDomain(final NormDto normDto) {
    return Norm.builder().document(XmlMapper.toDocument(normDto.getXml())).build();
  }

  /**
   * Maps a domain {@link Norm} to {@link NormDto}.
   *
   * @param norm The input {@link Norm} to be mapped.
   * @return A new {@link NormDto} mapped from the input {@link Norm}.
   */
  public static NormDto mapToDto(final Norm norm)
      throws XPathExpressionException, TransformerException {
    return NormDto.builder()
        .xml(XmlMapper.toString(norm.getDocument()))
        .eli(norm.getEli().orElseThrow())
        .guid(norm.getGuid().orElseThrow())
        .build();
  }
}
