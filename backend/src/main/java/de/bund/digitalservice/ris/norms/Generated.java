package de.bund.digitalservice.ris.norms;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom annotation indicating that the code element (type or method) has been generated. This
 * annotation is marked with {@link Documented}, and its retention policy is set to {@link
 * RetentionPolicy#RUNTIME}. It can be applied to both types (classes, interfaces, enums) and
 * methods.
 */
@Documented
@Retention(RUNTIME)
@Target({ TYPE, METHOD })
public @interface Generated {
}
