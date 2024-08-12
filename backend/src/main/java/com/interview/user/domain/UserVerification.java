package com.interview.user.domain;

import com.interview.app.exception.ForbiddenException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserVerification {

  private final CurrentUserDetailsService userDetailsService;

  public void verifyUserIsShopOwner() {
    verifyUserHasRole("SHOP_OWNER");
  }

  public void verifyUserIsBuyer() {
    verifyUserHasRole("BUYER");
  }

  private void verifyUserHasRole(String role) {
    if (!userDetailsService.userHasRole(role)) {
      throw new ForbiddenException("You do not have the required role: " + role);
    }
  }
}
