package de.bund.digitalservice.ris.norms.integration;

import de.bund.digitalservice.ris.norms.domain.entity.Norm;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;

public abstract class BaseS3MockIntegrationTest extends BaseIntegrationTest {

  protected static final String PUBLIC_BUCKET = "public";
  protected static final String PRIVATE_BUCKET = "private";

  @AfterEach
  void emptyDir() throws Exception {
    FileUtils.cleanDirectory(new File(LOCAL_STORAGE_PATH));
  }

  @AfterAll
  static void removeDir() throws Exception {
    FileUtils.deleteDirectory(new File(LOCAL_STORAGE_PATH));
  }

  protected static Path getPublicPath(Norm norm) {
    return Paths.get(LOCAL_STORAGE_PATH, PUBLIC_BUCKET, norm.getManifestationEli().toString());
  }

  protected static Path getPrivatePath(Norm norm) {
    return Paths.get(LOCAL_STORAGE_PATH, PRIVATE_BUCKET, norm.getManifestationEli().toString());
  }
}
