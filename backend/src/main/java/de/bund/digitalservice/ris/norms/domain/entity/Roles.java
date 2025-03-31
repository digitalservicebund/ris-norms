package de.bund.digitalservice.ris.norms.domain.entity;

/**
 * A utility class that holds role constants used in the application.
 * This class is designed to be used as a reference for role names throughout the application.
 * <p>
 * The constructor is private to prevent instantiation, as this class is intended to be used only for its constants.
 * </p>
 */
public class Roles {

  public static final String NORMS_USER = "norms_user";
  public static final String EVERKUENDUNG_USER = "norms_e_verkuendung";

  // Private constructor to prevent instantiation
  private Roles() {
    throw new UnsupportedOperationException("Utility class should not be instantiated");
  }
}
