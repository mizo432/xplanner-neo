package com.github.mizo432.xplanner.supporting;

import java.util.HashMap;
import java.util.Map;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * This exception should be used when a user's identity can't be verified.
 */
@SuppressFBWarnings(value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"}, justification = "Getters return mutable objects, acceptable for this use case")
public class AuthenticationException extends Exception {
   private Map<String, String> errorByModule = new HashMap<>();

   public AuthenticationException() {
   }

    public AuthenticationException(String message) {
        super(message);
        errorByModule.put("Default", message);
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
        errorByModule.put("", message);
    }

    public AuthenticationException(Throwable cause) {
        super(cause);
    }

   public AuthenticationException(Map<String, String> errorByModule) {
      this.errorByModule = errorByModule;
   }

   public Map<String, String> getErrorsByModule()
   {
       return this.errorByModule;
   }


}
