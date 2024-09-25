package de.bund.digitalservice.ris.norms.config;

import io.sentry.CustomSamplingContext;
import io.sentry.SamplingContext;
import io.sentry.SentryOptions.TracesSamplerCallback;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

/** Custom filter for sentry traces. Removes all traces for the /actuator endpoints. */
@Component
public class CustomTracesSamplerCallback implements TracesSamplerCallback {

  @Override
  public Double sample(SamplingContext context) {
    final CustomSamplingContext customContext = context.getCustomSamplingContext();

    if (customContext == null) {
      return null;
    }
    final HttpServletRequest request = (HttpServletRequest) customContext.get("request");

    if (request == null) {
      return null;
    }
    final String url = request.getRequestURI();
    if (url.startsWith("/actuator")) {
      return 0d;
    } else {
      return 0.1;
    }
  }
}
