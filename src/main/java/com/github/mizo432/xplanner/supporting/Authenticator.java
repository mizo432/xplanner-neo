package com.github.mizo432.xplanner.supporting;

import jakarta.servlet.http.HttpServletRequest;

public interface Authenticator {
   void authenticate(HttpServletRequest request, String userId, String password) throws AuthenticationException;

   void logout(HttpServletRequest request, int principalId) throws AuthenticationException;
}
