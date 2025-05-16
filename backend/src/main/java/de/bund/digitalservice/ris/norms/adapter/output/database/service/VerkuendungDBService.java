package de.bund.digitalservice.ris.norms.adapter.output.database.service;

import de.bund.digitalservice.ris.norms.adapter.output.database.dto.VerkuendungDto;
import de.bund.digitalservice.ris.norms.adapter.output.database.mapper.VerkuendungMapper;
import de.bund.digitalservice.ris.norms.adapter.output.database.repository.VerkuendungRepository;
import de.bund.digitalservice.ris.norms.application.port.output.DeleteVerkuendungByNormEliPort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadAllVerkuendungenPort;
import de.bund.digitalservice.ris.norms.application.port.output.LoadVerkuendungByNormEliPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateOrSaveVerkuendungPort;
import de.bund.digitalservice.ris.norms.application.port.output.UpdateVerkuendungPort;
import de.bund.digitalservice.ris.norms.domain.entity.Verkuendung;
import de.bund.digitalservice.ris.norms.domain.entity.Verkuendung;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * DBService for {@link Verkuendung} / {@link VerkuendungDto}
 */
@Service
public class VerkuendungDBService
  extends DBService
  implements
    LoadVerkuendungByNormEliPort,
    LoadAllVerkuendungenPort,
    UpdateVerkuendungPort,
    UpdateOrSaveVerkuendungPort,
    DeleteVerkuendungByNormEliPort {

  private final VerkuendungRepository verkuendungRepository;

  public VerkuendungDBService(VerkuendungRepository verkuendungRepository) {
    this.verkuendungRepository = verkuendungRepository;
  }

  @Override
  public Optional<Verkuendung> loadVerkuendungByNormEli(
    LoadVerkuendungByNormEliPort.Command command
  ) {
    return verkuendungRepository
      .findByEliNormExpression(command.eli().toString())
      .map(VerkuendungMapper::mapToDomain);
  }

  @Override
  public List<Verkuendung> loadAllVerkuendungen() {
    return verkuendungRepository
      .findAll()
      .stream()
      .map(VerkuendungMapper::mapToDomain)
      .sorted(
        Comparator.comparing((Verkuendung verkuendung) -> verkuendung.getEli().getPointInTime()
        ).reversed()
      )
      .toList();
  }

  @Override
  public Optional<Verkuendung> updateVerkuendung(UpdateVerkuendungPort.Command command) {
    return verkuendungRepository
      .findByEliNormExpression(command.verkuendung().getEli().toString())
      // There is no field in an Verkuendung that can change, so we do nothing here
      .map(VerkuendungMapper::mapToDomain);
  }

  @Override
  @Transactional(propagation = Propagation.NOT_SUPPORTED)
  public Verkuendung updateOrSaveVerkuendung(UpdateOrSaveVerkuendungPort.Command command) {
    final Optional<Verkuendung> updatedVerkuendung = updateVerkuendung(
      new UpdateVerkuendungPort.Command(command.verkuendung())
    );
    if (updatedVerkuendung.isEmpty()) {
      final VerkuendungDto verkuendungDto = VerkuendungMapper.mapToDto(command.verkuendung());
      return VerkuendungMapper.mapToDomain(verkuendungRepository.save(verkuendungDto));
    } else {
      return updatedVerkuendung.get();
    }
  }

  @Override
  public void deleteVerkuendungByNormEli(DeleteVerkuendungByNormEliPort.Command command) {
    verkuendungRepository.deleteByEliNormExpression(command.eli().toString());
  }
}
