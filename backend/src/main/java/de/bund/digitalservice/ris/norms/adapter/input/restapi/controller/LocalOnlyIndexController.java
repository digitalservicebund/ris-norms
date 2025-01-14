package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Controller to redirect to a locally running frontend. Used only for local developing when starting the backend
 * independently of the frontend.
 */
@Profile("local")
@RestController
public class LocalOnlyIndexController {

  @GetMapping(path = "/")
  public RedirectView getIndex(@Value("${app.local-frontend-host}") String frontendHost) {
    return new RedirectView(frontendHost);
  }
}
