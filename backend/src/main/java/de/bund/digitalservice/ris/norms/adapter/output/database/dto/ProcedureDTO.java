package de.bund.digitalservice.ris.norms.adapter.output.database.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "procedure")
public class ProcedureDTO {
  @Id @GeneratedValue private UUID id;

  @NotNull @Column private String state;

  @NotNull @Column private String eli;

  @Column(name = "print_announcement_gazette")
  @NotNull
  private String printAnnouncementGazette;

  @Column(name = "print_announcement_year")
  @NotNull
  private String printAnnouncementYear;

  @Column(name = "print_announcement_page")
  @NotNull
  private String printAnnouncementPage;
}
