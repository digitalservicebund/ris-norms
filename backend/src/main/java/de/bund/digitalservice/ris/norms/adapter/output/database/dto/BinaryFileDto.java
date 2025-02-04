package de.bund.digitalservice.ris.norms.adapter.output.database.dto;

import static org.hibernate.generator.EventType.INSERT;
import static org.hibernate.generator.EventType.UPDATE;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Generated;

/**
 * Data Transfer Object (DTO) class representing a binary file of a norm.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "binary_files")
public class BinaryFileDto {

  @Column(name = "eli_dokument_manifestation", updatable = false)
  @Id
  private String eliDokumentManifestation;

  @Generated(event = { INSERT, UPDATE })
  @Column(name = "eli_norm_manifestation", updatable = false, insertable = false)
  private String eliNormManifestation;

  @NotNull
  @Column
  private byte[] content;
}
