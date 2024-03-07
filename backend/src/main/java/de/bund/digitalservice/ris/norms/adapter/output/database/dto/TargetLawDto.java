package de.bund.digitalservice.ris.norms.adapter.output.database.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) class representing an article entity. This class is annotated with
 * Lombok annotations for generating getters, setters, constructors, and builder methods.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "target_law")
public class TargetLawDto {
  @Id @GeneratedValue private UUID id;

  @NotNull @Column private String eli;

  @NotNull @Column private String title;

  @NotNull @Column private String xml;
}
