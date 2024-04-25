package de.bund.digitalservice.ris.norms.domain.entity;

import java.time.LocalDate;
import lombok.Builder;

/**
 * A record representing the time boundaries that should change in a norm.
 *
 * @param eid The eId used to identify the timeInterval in the query.
 * @param date The new date the time boundary should be updated to.
 */
@Builder
public record TimeBoundaryChangeData(String eid, LocalDate date) {}
