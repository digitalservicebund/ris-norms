package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for the security.txt
 *
 * All resources are served by default under the /app/ route but the security.txt must be served under /.well-known/
 */
@RestController
@RequestMapping("/.well-known/security.txt")
public class SecurityTxtController {

  Resource securityTxt;

  public SecurityTxtController(
    @Value("classpath:/public/.well-known/security.txt") Resource securityTxt
  ) {
    this.securityTxt = securityTxt;
  }

  @GetMapping
  public ResponseEntity<Resource> getSecurityTxt() {
    return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body(securityTxt);
  }
}
