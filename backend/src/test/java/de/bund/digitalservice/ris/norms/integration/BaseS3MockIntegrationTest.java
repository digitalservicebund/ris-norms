package de.bund.digitalservice.ris.norms.integration;

import de.bund.digitalservice.ris.norms.domain.entity.Dokument;
import de.bund.digitalservice.ris.norms.domain.entity.eli.DokumentManifestationEli;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;

public abstract class BaseS3MockIntegrationTest extends BaseIntegrationTest {

  protected static final String PUBLIC_BUCKET = "public";
  protected static final String PRIVATE_BUCKET = "private";
  protected static final String PORTAL_PROTOTYPE_BUCKET = "portal-prototype";
  protected static final String EVERKUENDUG_BUCKET = "everkuendung";

  @AfterEach
  void emptyDir() throws Exception {
    FileUtils.cleanDirectory(new File(LOCAL_STORAGE_PATH));
  }

  @AfterAll
  static void removeDir() throws Exception {
    FileUtils.deleteDirectory(new File(LOCAL_STORAGE_PATH));
  }

  protected static Path getPublicPath() {
    return Paths.get(LOCAL_STORAGE_PATH, PUBLIC_BUCKET);
  }

  protected static Path getPublicPath(DokumentManifestationEli eli) {
    return Paths.get(LOCAL_STORAGE_PATH, PUBLIC_BUCKET, eli.toString());
  }

  protected static Path getEverkuendungPath() {
    return Paths.get(LOCAL_STORAGE_PATH, EVERKUENDUG_BUCKET);
  }

  protected static Path getPrivatePath() {
    return Paths.get(LOCAL_STORAGE_PATH, PRIVATE_BUCKET);
  }

  protected static Path getPrivatePath(Dokument regelungstext) {
    return Paths.get(
      LOCAL_STORAGE_PATH,
      PRIVATE_BUCKET,
      regelungstext.getManifestationEli().toString()
    );
  }

  protected static Path getPortalPrototypePath(Dokument regelungstext) {
    return Paths.get(
      LOCAL_STORAGE_PATH,
      PORTAL_PROTOTYPE_BUCKET,
      regelungstext.getManifestationEli().toString()
    );
  }
}
