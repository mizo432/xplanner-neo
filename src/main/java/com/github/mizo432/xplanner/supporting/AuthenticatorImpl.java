package com.github.mizo432.xplanner.supporting;

import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.mizo432.xplanner.supporting.module.ConfigurationException;

public class AuthenticatorImpl implements Authenticator {
   private static final Logger LOG = LoggerFactory.getLogger(AuthenticatorImpl.class);
   public static final String LOGIN_CONTEXT_SESSION_KEY = "LOGIN_CONTEXT";
   static final String GUESTS_KEY = "xplanner.security.guests";

   private LoginContext loginContext;
   public int NO_PARENT = 0;

   public AuthenticatorImpl(LoginContext loginContext) {
      this.loginContext = loginContext;
   }

   public AuthenticatorImpl() {
   }

   public void authenticate(HttpServletRequest request, String userId, String password)
         throws AuthenticationException {
      LoginContext loginContext = getLoginContext();
      if (SecurityHelper.isUserAuthenticated(request)) {
          try {
              loginContext.logout(request);
          } catch (ConfigurationException e) {
              throw new AuthenticationException(e.getMessage());
          }
      }
      try {
          loginContext.authenticate(userId, password);
      } catch (ConfigurationException e) {
          throw new AuthenticationException(e.getMessage());
      }
      SecurityHelper.setSubject(request, loginContext.getSubject());

      setLoginContext(request, loginContext);
   }

   public LoginContext getLoginContext() {
      return loginContext;
   }

   public static LoginContext getLoginContext(HttpServletRequest request) {
      return (LoginContext) request.getSession().getAttribute(LOGIN_CONTEXT_SESSION_KEY);
   }

   public static void setLoginContext(HttpServletRequest request, LoginContext context) {
      request.getSession().setAttribute(LOGIN_CONTEXT_SESSION_KEY, context);
   }

   public static LoginModule getLoginModule(HttpServletRequest request) {
      LoginContext context = getLoginContext(request);
      if (context == null) return null;
      LoginModule loginModule = null;
      try {
         loginModule = context.getLoginModule();
      } catch (ConfigurationException | RuntimeException e) {
         LOG.error("Error getting login module", e);
      }
      return loginModule;
   }

   public void logout(HttpServletRequest request, int principalId) throws AuthenticationException {
      LoginModule loginModule = getLoginModule(request);
      if (loginModule != null) {
         loginModule.logout(request);
      }

   }

}
