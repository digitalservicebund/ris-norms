package de.bund.digitalservice.ris.norms.config;

import io.sentry.CustomSamplingContext;
import io.sentry.SamplingContext;
import io.sentry.SentryOptions.TracesSamplerCallback;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;

/** Custom filter for sentry traces. Removes all traces for the actuator endpoints. */
@Component
public class CustomTracesSamplerCallback implements TracesSamplerCallback {
  @Override
  public @Nullable Double sample(@NotNull SamplingContext context) {

    CustomSamplingContext customContext = context.getCustomSamplingContext();

    if (customContext == null) {
      return null;
    }

    Boolean parentSampled = context.getTransactionContext().getParentSampled();

    // it's sad that it can be null at all
    if (parentSampled != null) {
      // if parent gets sampled, sample child; otherwise drop it
      return parentSampled ? 1.0 : 0.0;
    }

    // route specific sampling / dropping
    if ("/actuator".equals(customContext.get("url"))) {
      // drop actuator traces
      return 0.0;
    } else {
      // default sample rate
      return 0.1;
    }
  }
}
