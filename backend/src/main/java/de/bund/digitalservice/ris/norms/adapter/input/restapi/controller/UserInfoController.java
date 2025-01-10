package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
class UserInfoController {

  @GetMapping("/me")
  public UserInfoDto getUserInfo(@AuthenticationPrincipal User user) {
    return new UserInfoDto(user.getUsername());
  }

  public record UserInfoDto(String name) {}
}
