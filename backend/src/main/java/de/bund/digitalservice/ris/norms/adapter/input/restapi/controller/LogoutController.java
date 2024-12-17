package de.bund.digitalservice.ris.norms.adapter.input.restapi.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling user logout requests.
 * <p>
 * This controller provides an endpoint for logging out authenticated users by invalidating their session.
 * Upon logout, the session data stored in Redis is removed, effectively ending the user's session.
 */
@RestController
@RequestMapping("/api/v1/logout")
public class LogoutController {

  /**
   * Logs out the current user by invalidating their session.
   * <p>
   * This endpoint is designed to be called by authenticated users who wish to log out.
   * It invalidates the user's session, which removes the associated data from the Redis session store.
   *
   * @param session The current HTTP session of the user making the logout request.
   * @return A confirmation message indicating that the user has been logged out successfully.
   */
  @PostMapping
  public String logout(final HttpSession session) {
    if (session != null) {
      session.invalidate(); // Invalidate the session (removes it from Redis)
    }
    return "Logged out successfully";
  }
}
