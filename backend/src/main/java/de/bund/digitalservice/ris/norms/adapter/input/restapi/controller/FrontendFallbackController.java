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
   * When calling "/", redirect to "/app". This allows users to access the frontend by calling
   * just the domain at which the app is hosted, but still have all frontend-related resources
   * collected inside "/app".
   *
   * @return Redirect to the app.
   */
  @GetMapping(value = { "/" })
  public String redirectToApp() {
    return "redirect:/app/";
  }

  /**
   * Serves index.html for all unmatched routes inside "/app", excluding requests for static
   * resources. This ensures Vue Router can handle client-side routing.
   *
   * @return Forward to index.html.
   */
  @GetMapping(value = { "/app/", "/app/{path:[^\\.]*}" })
  public String serveIndexHtml() {
    return "forward:/app/index.html";
  }
}
