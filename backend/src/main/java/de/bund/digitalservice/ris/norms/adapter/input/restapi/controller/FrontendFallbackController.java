package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
   * @return Forward to index.html.
   */
  @GetMapping(value = { "/{path:^(?!assets|api|.*\\.).*}", "/{path:^(?!assets|api|.*\\.).*}/**" })
  public String serveIndexHtml() {
    return "forward:/index.html";
  }
}
