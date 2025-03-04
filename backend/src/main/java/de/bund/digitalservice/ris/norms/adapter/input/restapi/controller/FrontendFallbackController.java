package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * A fallback controller for serving the frontend's index.html file.
 * <p>
 * This controller handles all requests that do not match an existing resource or API endpoint.
 * It ensures that the Vue.js single-page application (SPA) can take over client-side routing.
 */
@Controller
public class FrontendFallbackController {

  /**
   * Serves index.html for all unmatched routes, excluding requests for static resources.
   * This ensures Vue Router can handle client-side routing.
   *
   * @param path The requested path.
   * @return Forward to index.html.
   */
  @GetMapping(
    value = {
      "/{path:^(?!assets|api|environment|.*\\.).*}",
      "/{path:^(?!assets|api|environment|.*\\.).*}/**",
    }
  )
  public String serveIndexHtml(@PathVariable String path) {
    return "forward:/index.html";
  }
}
