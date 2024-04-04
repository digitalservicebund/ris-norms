package de.bund.digitalservice.ris.norms.adapter.output.database.mapper;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.TargetLawDto;
import de.bund.digitalservice.ris.norms.application.service.TimeMachineService;
import de.bund.digitalservice.ris.norms.domain.entity.TargetLaw;

/** Mapper class for converting between {@link TargetLawDto} and {@link TargetLaw}. */
public class TargetLawMapper {

  // TODO: the XML <--> String mapping should be in XmlDocumentService.
  // Also: harmonize the function names like "mapStringToDocumen" and "mapDocumentToString"
  static TimeMachineService timeMachineService;

  // Private constructor to hide the implicit public one and prevent instantiation
  private TargetLawMapper(
    TimeMachineService timeMachineService
  ) {
    this.timeMachineService = timeMachineService;
  }

  /**
   * Maps a {@link TargetLawDto} to the domain {@link TargetLaw}.
   *
   * @param targetLawDto The input {@link TargetLawDto} to be mapped.
   * @return A new {@link TargetLaw} mapped from the input {@link TargetLawDto}.
   */
  public static TargetLaw mapToDomain(final TargetLawDto targetLawDto) {
    return TargetLaw.builder()
        .eli(targetLawDto.getEli())
        .title(targetLawDto.getTitle())
        .xml(timeMachineService.stringToXmlDocument(targetLawDto.getXml()))
        .fna(targetLawDto.getFna())
        .shortTitle(targetLawDto.getShortTitle())
        .build();
  }

  /**
   * Maps a domain {@link TargetLaw} to {@link TargetLawDto}.
   *
   * @param targetLaw The input {@link TargetLaw} to be mapped.
   * @return A new {@link TargetLawDto} mapped from the input {@link TargetLaw}.
   */
  public static TargetLawDto mapToDto(final TargetLaw targetLaw) {
    return TargetLawDto.builder()
        .eli(targetLaw.getEli())
        .title(targetLaw.getTitle())
        .xml(timeMachineService.convertDocumentToString(targetLaw.getXml()))
        .fna(targetLaw.getFna())
        .shortTitle(targetLaw.getShortTitle())
        .build();
  }
}
