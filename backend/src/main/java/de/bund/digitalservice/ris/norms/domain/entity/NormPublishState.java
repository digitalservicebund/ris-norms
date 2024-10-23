package de.bund.digitalservice.ris.norms.domain.entity;

/**
 * Publish state of a norm
 */
public enum NormPublishState {
  /**
   * Norm is published and available to the portal
   */
  PUBLISHED,
  /**
   * Norm is neither published not planed to be published in the next nightly publishing run.
   */
  UNPUBLISHED,
  /**
   * Norm will be published in the next nightly publishing run.
   */
  QUEUED_FOR_PUBLISH,
}
