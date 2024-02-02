package de.bund.digitalservice.ris.norms.adapter.output.database.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) class representing a procedure entity. This class is annotated with
 * Lombok annotations for generating getters, setters, constructors, and builder methods.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "procedure")
public class ProcedureDTO {
  @Id @GeneratedValue private UUID id;

  @NotNull
  @Size(max = 50)
  @Column
  private String state;

  @NotNull
  @Size(max = 255)
  @Column
  private String eli;

  @NotNull
  @Size(max = 10)
  @Column(name = "print_announcement_gazette")
  private String printAnnouncementGazette;

  @NotNull
  @Size(max = 4)
  @Column(name = "print_announcement_year")
  private String printAnnouncementYear;

  @NotNull
  @Size(max = 5)
  @Column(name = "print_announcement_page")
  private String printAnnouncementPage;
}
