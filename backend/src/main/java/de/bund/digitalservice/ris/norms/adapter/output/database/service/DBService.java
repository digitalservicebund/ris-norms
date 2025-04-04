package de.bund.digitalservice.ris.norms.adapter.output.database.service;

import org.springframework.transaction.annotation.Transactional;

/**
 * Abstract base class for database services that ensures all public methods are executed within a transactional context.
 * <p>
 * Intended to be extended by specific DB service implementations.
 */
@Transactional
abstract class DBService {}
