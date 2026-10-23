package com.github.mizo432.xplanner.supporting.module;

import com.github.mizo432.xplanner.supporting.LoginModule;

/**
 * Simplified LoginModuleLoader for Spring Boot.
 * Returns an empty array of login modules by default.
 */
public class LoginModuleLoader {
    
    public static final String LOGIN_MODULE_PROPERTY_PREFIX = "xplanner.login.module";
    
    public LoginModule[] loadLoginModules() throws ConfigurationException {
        // Spring Bootではデフォルトの認証を使用
        return new LoginModule[0];
    }
}
