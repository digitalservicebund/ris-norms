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
@Table(name = "article")
public class ArticleDto {
  @Id @GeneratedValue private UUID id;

  @NotNull @Column private String enumeration;

  @NotNull @Column private String eid;

  @Column private String title;

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "target_law_id", referencedColumnName = "id", nullable = false)
  private TargetLawDto targetLawDto;
}
