package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
class UserInfoController {

  @GetMapping("/me")
  public UserInfoDto getUserInfo() {
    OAuth2User user =
      ((OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    String name = user.getAttribute("name");
    return new UserInfoDto(name);
  }

  public record UserInfoDto(String name) {}
}
